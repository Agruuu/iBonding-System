package com.ibonding.module.ai.service.mindmap;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.ibonding.framework.ai.core.enums.AiModelTypeEnum;
import com.ibonding.framework.ai.core.enums.AiPlatformEnum;
import com.ibonding.framework.ai.core.util.AiUtils;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.mindmap.vo.AiMindMapGenerateReqVO;
import com.ibonding.module.ai.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import com.ibonding.module.ai.dal.dataobject.mindmap.AiMindMapDO;
import com.ibonding.module.ai.dal.dataobject.model.AiChatRoleDO;
import com.ibonding.module.ai.dal.dataobject.model.AiModelDO;
import com.ibonding.module.ai.dal.mysql.mindmap.AiMindMapMapper;
import com.ibonding.module.ai.enums.AiChatRoleEnum;
import com.ibonding.module.ai.enums.ErrorCodeConstants;
import com.ibonding.module.ai.service.model.AiChatRoleService;
import com.ibonding.module.ai.service.model.AiModelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static com.ibonding.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ibonding.framework.common.pojo.CommonResult.error;
import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.module.ai.enums.ErrorCodeConstants.*;

/**
 * AI 思维导图 Service 实现类
 *
 * @author Agaru
 */
@Service
@Slf4j
public class AiMindMapServiceImpl implements AiMindMapService {

    @Resource
    private AiModelService modalService;
    @Resource
    private AiChatRoleService chatRoleService;

    @Resource
    private AiMindMapMapper mindMapMapper;

    @Override
    public Flux<CommonResult<String>> generateMindMap(AiMindMapGenerateReqVO generateReqVO, Long userId) {
        // 1. 获取导图模型。尝试获取思维导图助手角色，如果没有则使用默认模型
        AiChatRoleDO role = CollUtil.getFirst(
                chatRoleService.getChatRoleListByName(AiChatRoleEnum.AI_MIND_MAP_ROLE.getName()));
        // 1.1 获取导图执行模型
        AiModelDO model = getModel(role);
        // 1.2 获取角色设定消息
        String systemMessage = role != null && StrUtil.isNotBlank(role.getSystemMessage())
                ? role.getSystemMessage() : AiChatRoleEnum.AI_MIND_MAP_ROLE.getSystemMessage();
        // 1.3 校验平台
        AiPlatformEnum platform = AiPlatformEnum.validatePlatform(model.getPlatform());
        ChatModel chatModel = modalService.getChatModel(model.getId());

        // 2. 插入思维导图信息
        AiMindMapDO mindMapDO = BeanUtils.toBean(generateReqVO, AiMindMapDO.class, mindMap -> mindMap.setUserId(userId)
                .setPlatform(platform.getPlatform()).setModelId(model.getId()).setModel(model.getModel()));
        mindMapMapper.insert(mindMapDO);

        // 3.1 构建 Prompt，并进行调用
        Prompt prompt = buildPrompt(generateReqVO, model, systemMessage);
        Flux<ChatResponse> streamResponse = chatModel.stream(prompt);

        // 3.2 流式返回
        StringBuffer contentBuffer = new StringBuffer();
        return streamResponse.map(chunk -> {
            String newContent = chunk.getResult() != null ? chunk.getResult().getOutput().getText() : null;
            newContent = StrUtil.nullToDefault(newContent, ""); // 避免 null 的 情况
            contentBuffer.append(newContent);
            // 响应结果
            return success(newContent);
        }).doOnComplete(() -> {
            // 忽略租户，因为 Flux 异步无法透传租户
            mindMapMapper.updateById(new AiMindMapDO().setId(mindMapDO.getId()).setGeneratedContent(contentBuffer.toString()));
        }).doOnError(throwable -> {
            log.error("[generateWriteContent][generateReqVO({}) 发生异常]", generateReqVO, throwable);
            // 忽略租户，因为 Flux 异步无法透传租户
            mindMapMapper.updateById(new AiMindMapDO().setId(mindMapDO.getId()).setErrorMessage(throwable.getMessage()));
        }).onErrorResume(error -> Flux.just(error(ErrorCodeConstants.WRITE_STREAM_ERROR)));

    }

    private Prompt buildPrompt(AiMindMapGenerateReqVO generateReqVO, AiModelDO model, String systemMessage) {
        // 1. 构建 message 列表
        List<Message> chatMessages = buildMessages(generateReqVO, systemMessage);
        // 2. 构建 options 对象
        AiPlatformEnum platform = AiPlatformEnum.validatePlatform(model.getPlatform());
        ChatOptions options = AiUtils.buildChatOptions(platform, model.getModel(), model.getTemperature(), model.getMaxTokens());
        return new Prompt(chatMessages, options);
    }

    private static List<Message> buildMessages(AiMindMapGenerateReqVO generateReqVO, String systemMessage) {
        List<Message> chatMessages = new ArrayList<>();
        // 1. 角色设定
        if (StrUtil.isNotBlank(systemMessage)) {
            chatMessages.add(new SystemMessage(systemMessage));
        }
        // 2. 用户输入
        chatMessages.add(new UserMessage(generateReqVO.getPrompt()));
        return chatMessages;
    }

    private AiModelDO getModel(AiChatRoleDO role) {
        AiModelDO model = null;
        if (role != null && role.getModelId() != null) {
            model = modalService.getModel(role.getModelId());
        }
        if (model == null) {
            model = modalService.getRequiredDefaultModel(AiModelTypeEnum.CHAT.getType());
        }
        // 校验模型存在、且合法
        if (model == null) {
            throw exception(MODEL_NOT_EXISTS);
        }
        if (ObjUtil.notEqual(model.getType(), AiModelTypeEnum.CHAT.getType())) {
            throw exception(MODEL_USE_TYPE_ERROR);
        }
        return model;
    }

    @Override
    public void deleteMindMap(Long id) {
        // 校验存在
        validateMindMapExists(id);
        // 删除
        mindMapMapper.deleteById(id);
    }

    private void validateMindMapExists(Long id) {
        if (mindMapMapper.selectById(id) == null) {
            throw exception(MIND_MAP_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<AiMindMapDO> getMindMapPage(AiMindMapPageReqVO pageReqVO) {
        return mindMapMapper.selectPage(pageReqVO);
    }

}

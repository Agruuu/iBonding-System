package com.ibonding.module.ai.service.model;

import com.ibonding.framework.ai.core.enums.AiPlatformEnum;
import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.model.vo.chatModel.AiChatModelPageReqVO;
import com.ibonding.module.ai.controller.admin.model.vo.chatModel.AiChatModelSaveReqVO;
import com.ibonding.module.ai.dal.dataobject.model.AiChatModelDO;
import com.ibonding.module.ai.dal.mysql.model.AiChatModelMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static com.ibonding.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ibonding.module.ai.enums.ErrorCodeConstants.*;

/**
 * AI 聊天模型 Service 实现类
 *
 * @author Agaru
 */
@Service
@Validated
public class AiChatModelServiceImpl implements AiChatModelService {

    @Resource
    private AiApiKeyService apiKeyService;

    @Resource
    private AiChatModelMapper chatModelMapper;

    @Override
    public Long createChatModel(AiChatModelSaveReqVO createReqVO) {
        // 1. 校验
        AiPlatformEnum.validatePlatform(createReqVO.getPlatform());
        apiKeyService.validateApiKey(createReqVO.getKeyId());

        // 2. 插入
        AiChatModelDO chatModel = BeanUtils.toBean(createReqVO, AiChatModelDO.class);
        chatModelMapper.insert(chatModel);
        return chatModel.getId();
    }

    @Override
    public void updateChatModel(AiChatModelSaveReqVO updateReqVO) {
        // 1. 校验
        validateChatModelExists(updateReqVO.getId());
        AiPlatformEnum.validatePlatform(updateReqVO.getPlatform());
        apiKeyService.validateApiKey(updateReqVO.getKeyId());

        // 2. 更新
        AiChatModelDO updateObj = BeanUtils.toBean(updateReqVO, AiChatModelDO.class);
        chatModelMapper.updateById(updateObj);
    }

    @Override
    public void deleteChatModel(Long id) {
        // 校验存在
        validateChatModelExists(id);
        // 删除
        chatModelMapper.deleteById(id);
    }

    private AiChatModelDO validateChatModelExists(Long id) {
        AiChatModelDO model = chatModelMapper.selectById(id);
        if (chatModelMapper.selectById(id) == null) {
            throw exception(CHAT_MODEL_NOT_EXISTS);
        }
        return model;
    }

    @Override
    public AiChatModelDO getChatModel(Long id) {
        return chatModelMapper.selectById(id);
    }

    @Override
    public AiChatModelDO getRequiredDefaultChatModel() {
        AiChatModelDO model = chatModelMapper.selectFirstByStatus(CommonStatusEnum.ENABLE.getStatus());
        if (model == null) {
            throw exception(CHAT_MODEL_DEFAULT_NOT_EXISTS);
        }
        return model;
    }

    @Override
    public PageResult<AiChatModelDO> getChatModelPage(AiChatModelPageReqVO pageReqVO) {
        return chatModelMapper.selectPage(pageReqVO);
    }

    @Override
    public AiChatModelDO validateChatModel(Long id) {
        AiChatModelDO model = validateChatModelExists(id);
        if (CommonStatusEnum.isDisable(model.getStatus())) {
            throw exception(CHAT_MODEL_DISABLE);
        }
        return model;
    }

    @Override
    public List<AiChatModelDO> getChatModelListByStatus(Integer status) {
        return chatModelMapper.selectList(status);
    }

    @Override
    public List<AiChatModelDO> getChatModelList(Collection<Long> ids) {
        return chatModelMapper.selectBatchIds(ids);
    }

}
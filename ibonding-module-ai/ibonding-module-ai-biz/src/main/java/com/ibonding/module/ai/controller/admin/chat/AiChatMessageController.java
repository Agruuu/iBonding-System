package com.ibonding.module.ai.controller.admin.chat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.collection.MapUtils;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.chat.vo.message.AiChatMessagePageReqVO;
import com.ibonding.module.ai.controller.admin.chat.vo.message.AiChatMessageRespVO;
import com.ibonding.module.ai.controller.admin.chat.vo.message.AiChatMessageSendReqVO;
import com.ibonding.module.ai.controller.admin.chat.vo.message.AiChatMessageSendRespVO;
import com.ibonding.module.ai.dal.dataobject.chat.AiChatConversationDO;
import com.ibonding.module.ai.dal.dataobject.chat.AiChatMessageDO;
import com.ibonding.module.ai.dal.dataobject.model.AiChatRoleDO;
import com.ibonding.module.ai.service.chat.AiChatConversationService;
import com.ibonding.module.ai.service.chat.AiChatMessageService;
import com.ibonding.module.ai.service.model.AiChatRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.collection.CollectionUtils.convertSet;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Chat Messages")
@RestController
@RequestMapping("/ai/chat/message")
@Slf4j
public class AiChatMessageController {

    @Resource
    private AiChatMessageService chatMessageService;
    @Resource
    private AiChatConversationService chatConversationService;
    @Resource
    private AiChatRoleService chatRoleService;

    @Operation(summary = "Send Messages (Segmented)", description = "Return All At Once, Slow Response")
    @PostMapping("/send")
    public CommonResult<AiChatMessageSendRespVO> sendMessage(@Valid @RequestBody AiChatMessageSendReqVO sendReqVO) {
        return success(chatMessageService.sendMessage(sendReqVO, getLoginUserId()));
    }

    @Operation(summary = "Send Messages (Streaming)", description = "Streaming Return, Faster Response")
    @PostMapping(value = "/send-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PermitAll // 解决 SSE 最终响应的时候，会被 Access Denied 拦截的问题
    public Flux<CommonResult<AiChatMessageSendRespVO>> sendChatMessageStream(@Valid @RequestBody AiChatMessageSendReqVO sendReqVO) {
        return chatMessageService.sendChatMessageStream(sendReqVO, getLoginUserId());
    }

    @Operation(summary = "Get the Message List of the Specified Conversation")
    @GetMapping("/list-by-conversation-id")
    @Parameter(name = "conversationId", required = true, description = "对话编号", example = "1024")
    public CommonResult<List<AiChatMessageRespVO>> getChatMessageListByConversationId(
            @RequestParam("conversationId") Long conversationId) {
        AiChatConversationDO conversation = chatConversationService.getChatConversation(conversationId);
        if (conversation == null || ObjUtil.notEqual(conversation.getUserId(), getLoginUserId())) {
            return success(Collections.emptyList());
        }
        List<AiChatMessageDO> messageList = chatMessageService.getChatMessageListByConversationId(conversationId);
        return success(BeanUtils.toBean(messageList, AiChatMessageRespVO.class));
    }

    @Operation(summary = "Delete Message")
    @DeleteMapping("/delete")
    @Parameter(name = "id", required = true, description = "消息编号", example = "1024")
    public CommonResult<Boolean> deleteChatMessage(@RequestParam("id") Long id) {
        chatMessageService.deleteChatMessage(id, getLoginUserId());
        return success(true);
    }

    @Operation(summary = "Delete Messages of the Specified Conversation")
    @DeleteMapping("/delete-by-conversation-id")
    @Parameter(name = "conversationId", required = true, description = "对话编号", example = "1024")
    public CommonResult<Boolean> deleteChatMessageByConversationId(@RequestParam("conversationId") Long conversationId) {
        chatMessageService.deleteChatMessageByConversationId(conversationId, getLoginUserId());
        return success(true);
    }

    // ========== 对话管理 ==========

    @GetMapping("/page")
    @Operation(summary = "Get Paginated Message", description = "For the [Conversation Management] Menu")
    @PreAuthorize("@ss.hasPermission('ai:chat-conversation:query')")
    public CommonResult<PageResult<AiChatMessageRespVO>> getChatMessagePage(AiChatMessagePageReqVO pageReqVO) {
        PageResult<AiChatMessageDO> pageResult = chatMessageService.getChatMessagePage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }
        // 拼接数据
        Map<Long, AiChatRoleDO> roleMap = chatRoleService.getChatRoleMap(
                convertSet(pageResult.getList(), AiChatMessageDO::getRoleId));
        return success(BeanUtils.toBean(pageResult, AiChatMessageRespVO.class,
                respVO -> MapUtils.findAndThen(roleMap, respVO.getRoleId(), role -> respVO.setRoleName(role.getName()))));
    }

    @Operation(summary = "Administrator Delete Message")
    @DeleteMapping("/delete-by-admin")
    @Parameter(name = "id", required = true, description = "消息编号", example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:chat-message:delete')")
    public CommonResult<Boolean> deleteChatMessageByAdmin(@RequestParam("id") Long id) {
        chatMessageService.deleteChatMessageByAdmin(id);
        return success(true);
    }

}

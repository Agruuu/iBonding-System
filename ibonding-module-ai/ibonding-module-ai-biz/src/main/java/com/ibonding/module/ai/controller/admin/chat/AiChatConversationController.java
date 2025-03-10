package com.ibonding.module.ai.controller.admin.chat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.chat.vo.conversation.AiChatConversationCreateMyReqVO;
import com.ibonding.module.ai.controller.admin.chat.vo.conversation.AiChatConversationPageReqVO;
import com.ibonding.module.ai.controller.admin.chat.vo.conversation.AiChatConversationRespVO;
import com.ibonding.module.ai.controller.admin.chat.vo.conversation.AiChatConversationUpdateMyReqVO;
import com.ibonding.module.ai.dal.dataobject.chat.AiChatConversationDO;
import com.ibonding.module.ai.service.chat.AiChatConversationService;
import com.ibonding.module.ai.service.chat.AiChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.collection.CollectionUtils.convertList;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management backend - AI Chat Conversations")
@RestController
@RequestMapping("/ai/chat/conversation")
@Validated
public class AiChatConversationController {

    @Resource
    private AiChatConversationService chatConversationService;
    @Resource
    private AiChatMessageService chatMessageService;

    @PostMapping("/create-my")
    @Operation(summary = "Create [My] Chat Conversations")
    public CommonResult<Long> createChatConversationMy(@RequestBody @Valid AiChatConversationCreateMyReqVO createReqVO) {
        return success(chatConversationService.createChatConversationMy(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update-my")
    @Operation(summary = "Update [My] Chat Conversations")
    public CommonResult<Boolean> updateChatConversationMy(@RequestBody @Valid AiChatConversationUpdateMyReqVO updateReqVO) {
        chatConversationService.updateChatConversationMy(updateReqVO, getLoginUserId());
        return success(true);
    }

    @GetMapping("/my-list")
    @Operation(summary = "Get the list of [My] Chat Conversations")
    public CommonResult<List<AiChatConversationRespVO>> getChatConversationMyList() {
        List<AiChatConversationDO> list = chatConversationService.getChatConversationListByUserId(getLoginUserId());
        return success(BeanUtils.toBean(list, AiChatConversationRespVO.class));
    }

    @GetMapping("/get-my")
    @Operation(summary = "Get [My] Chat Conversations")
    @Parameter(name = "id", required = true, description = "对话编号", example = "1024")
    public CommonResult<AiChatConversationRespVO> getChatConversationMy(@RequestParam("id") Long id) {
        AiChatConversationDO conversation = chatConversationService.getChatConversation(id);
        if (conversation != null && ObjUtil.notEqual(conversation.getUserId(), getLoginUserId())) {
            conversation = null;
        }
        return success(BeanUtils.toBean(conversation, AiChatConversationRespVO.class));
    }

    @DeleteMapping("/delete-my")
    @Operation(summary = "Delete Chat Conversations")
    @Parameter(name = "id", required = true, description = "对话编号", example = "1024")
    public CommonResult<Boolean> deleteChatConversationMy(@RequestParam("id") Long id) {
        chatConversationService.deleteChatConversationMy(id, getLoginUserId());
        return success(true);
    }

    @DeleteMapping("/delete-by-unpinned")
    @Operation(summary = "Delete Chat Conversations that are not pinned")
    public CommonResult<Boolean> deleteChatConversationMyByUnpinned() {
        chatConversationService.deleteChatConversationMyByUnpinned(getLoginUserId());
        return success(true);
    }

    // ========== 对话管理 ==========

    @GetMapping("/page")
    @Operation(summary = "Get The Paginated List of Conversations", description = "For The [Conversation Management] Menu")
    @PreAuthorize("@ss.hasPermission('ai:chat-conversation:query')")
    public CommonResult<PageResult<AiChatConversationRespVO>> getChatConversationPage(AiChatConversationPageReqVO pageReqVO) {
        PageResult<AiChatConversationDO> pageResult = chatConversationService.getChatConversationPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }
        // 拼接关联数据
        Map<Long, Integer> messageCountMap = chatMessageService.getChatMessageCountMap(
                convertList(pageResult.getList(), AiChatConversationDO::getId));
        return success(BeanUtils.toBean(pageResult, AiChatConversationRespVO.class,
                conversation -> conversation.setMessageCount(messageCountMap.getOrDefault(conversation.getId(), 0))));
    }

    @Operation(summary = "Administrator Delete Conversation")
    @DeleteMapping("/delete-by-admin")
    @Parameter(name = "id", required = true, description = "对话编号", example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:chat-conversation:delete')")
    public CommonResult<Boolean> deleteChatConversationByAdmin(@RequestParam("id") Long id) {
        chatConversationService.deleteChatConversationByAdmin(id);
        return success(true);
    }

}

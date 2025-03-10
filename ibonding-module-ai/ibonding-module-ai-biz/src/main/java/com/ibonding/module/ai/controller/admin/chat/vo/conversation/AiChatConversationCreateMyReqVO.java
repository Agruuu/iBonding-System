package com.ibonding.module.ai.controller.admin.chat.vo.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management backend - Request VO for Creating AI Chat Conversations [Mine]")
@Data
public class AiChatConversationCreateMyReqVO {

    @Schema(description = "聊天角色编号", example = "666")
    private Long roleId;

    @Schema(description = "知识库编号", example = "1204")
    private Long knowledgeId;

}

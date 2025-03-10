package com.ibonding.module.ai.controller.admin.model;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.model.vo.chatModel.AiChatModelPageReqVO;
import com.ibonding.module.ai.controller.admin.model.vo.chatModel.AiChatModelRespVO;
import com.ibonding.module.ai.controller.admin.model.vo.chatModel.AiChatModelSaveReqVO;
import com.ibonding.module.ai.dal.dataobject.model.AiChatModelDO;
import com.ibonding.module.ai.service.model.AiChatModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "Management Backstage - AI Chat Model")
@RestController
@RequestMapping("/ai/chat-model")
@Validated
public class AiChatModelController {

    @Resource
    private AiChatModelService chatModelService;

    @PostMapping("/create")
    @Operation(summary = "Create Chat Model")
    @PreAuthorize("@ss.hasPermission('ai:chat-model:create')")
    public CommonResult<Long> createChatModel(@Valid @RequestBody AiChatModelSaveReqVO createReqVO) {
        return success(chatModelService.createChatModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Chat Model")
    @PreAuthorize("@ss.hasPermission('ai:chat-model:update')")
    public CommonResult<Boolean> updateChatModel(@Valid @RequestBody AiChatModelSaveReqVO updateReqVO) {
        chatModelService.updateChatModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Chat Model")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:chat-model:delete')")
    public CommonResult<Boolean> deleteChatModel(@RequestParam("id") Long id) {
        chatModelService.deleteChatModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Chat Model")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:chat-model:query')")
    public CommonResult<AiChatModelRespVO> getChatModel(@RequestParam("id") Long id) {
        AiChatModelDO chatModel = chatModelService.getChatModel(id);
        return success(BeanUtils.toBean(chatModel, AiChatModelRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get Chat Model Pagination")
    @PreAuthorize("@ss.hasPermission('ai:chat-model:query')")
    public CommonResult<PageResult<AiChatModelRespVO>> getChatModelPage(@Valid AiChatModelPageReqVO pageReqVO) {
        PageResult<AiChatModelDO> pageResult = chatModelService.getChatModelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiChatModelRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "Get Chat Model List")
    @Parameter(name = "status", description = "状态", required = true, example = "1")
    public CommonResult<List<AiChatModelRespVO>> getChatModelSimpleList(@RequestParam("status") Integer status) {
        List<AiChatModelDO> list = chatModelService.getChatModelListByStatus(status);
        return success(convertList(list, model -> new AiChatModelRespVO().setId(model.getId())
                .setName(model.getName()).setModel(model.getModel())));
    }

}
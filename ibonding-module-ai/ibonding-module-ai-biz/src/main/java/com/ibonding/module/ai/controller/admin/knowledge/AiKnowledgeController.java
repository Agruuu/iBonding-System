package com.ibonding.module.ai.controller.admin.knowledge;

import com.ibonding.framework.common.enums.CommonStatusEnum;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeRespVO;
import com.ibonding.module.ai.controller.admin.knowledge.vo.knowledge.AiKnowledgeSaveReqVO;
import com.ibonding.module.ai.dal.dataobject.knowledge.AiKnowledgeDO;
import com.ibonding.module.ai.service.knowledge.AiKnowledgeService;
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

@Tag(name = "Management Backstage - AI Knowledge Base")
@RestController
@RequestMapping("/ai/knowledge")
@Validated
public class AiKnowledgeController {

    @Resource
    private AiKnowledgeService knowledgeService;

    @GetMapping("/page")
    @Operation(summary = "Get the Knowledge Pagination")
    @PreAuthorize("@ss.hasPermission('ai:knowledge:query')")
    public CommonResult<PageResult<AiKnowledgeRespVO>> getKnowledgePage(@Valid AiKnowledgePageReqVO pageReqVO) {
        PageResult<AiKnowledgeDO> pageResult = knowledgeService.getKnowledgePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiKnowledgeRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "获得知识库")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('ai:knowledge:query')")
    public CommonResult<AiKnowledgeRespVO> getKnowledge(@RequestParam("id") Long id) {
        AiKnowledgeDO knowledge = knowledgeService.getKnowledge(id);
        return success(BeanUtils.toBean(knowledge, AiKnowledgeRespVO.class));
    }

    @PostMapping("/create")
    @Operation(summary = "Create Knowledge Base")
    @PreAuthorize("@ss.hasPermission('ai:knowledge:create')")
    public CommonResult<Long> createKnowledge(@RequestBody @Valid AiKnowledgeSaveReqVO createReqVO) {
        return success(knowledgeService.createKnowledge(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update the Knowledge Base")
    @PreAuthorize("@ss.hasPermission('ai:knowledge:update')")
    public CommonResult<Boolean> updateKnowledge(@RequestBody @Valid AiKnowledgeSaveReqVO updateReqVO) {
        knowledgeService.updateKnowledge(updateReqVO);
        return success(true);
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得知识库的精简列表")
    public CommonResult<List<AiKnowledgeRespVO>> getKnowledgeSimpleList() {
        List<AiKnowledgeDO> list = knowledgeService.getKnowledgeSimpleListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, knowledge -> new AiKnowledgeRespVO()
                .setId(knowledge.getId()).setName(knowledge.getName())));
    }

}

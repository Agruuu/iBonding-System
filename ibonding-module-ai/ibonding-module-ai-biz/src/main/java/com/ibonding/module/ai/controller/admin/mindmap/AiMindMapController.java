package com.ibonding.module.ai.controller.admin.mindmap;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.ai.controller.admin.mindmap.vo.AiMindMapGenerateReqVO;
import com.ibonding.module.ai.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import com.ibonding.module.ai.controller.admin.mindmap.vo.AiMindMapRespVO;
import com.ibonding.module.ai.dal.dataobject.mindmap.AiMindMapDO;
import com.ibonding.module.ai.service.mindmap.AiMindMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backstage - AI Mind Map")
@RestController
@RequestMapping("/ai/mind-map")
public class AiMindMapController {

    @Resource
    private AiMindMapService mindMapService;

    @PostMapping(value = "/generate-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Mind Map Generation (Streaming)", description = "Streaming Return, Faster Response")
    public Flux<CommonResult<String>> generateMindMap(@RequestBody @Valid AiMindMapGenerateReqVO generateReqVO) {
        return mindMapService.generateMindMap(generateReqVO, getLoginUserId());
    }

    // ================ 导图管理 ================

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Mind Map")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:mind-map:delete')")
    public CommonResult<Boolean> deleteMindMap(@RequestParam("id") Long id) {
        mindMapService.deleteMindMap(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "Get Mind Map Pagination")
    @PreAuthorize("@ss.hasPermission('ai:mind-map:query')")
    public CommonResult<PageResult<AiMindMapRespVO>> getMindMapPage(@Valid AiMindMapPageReqVO pageReqVO) {
        PageResult<AiMindMapDO> pageResult = mindMapService.getMindMapPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiMindMapRespVO.class));
    }

}

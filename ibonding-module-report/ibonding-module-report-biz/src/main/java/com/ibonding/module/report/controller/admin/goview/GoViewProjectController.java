package com.ibonding.module.report.controller.admin.goview;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageParam;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import com.ibonding.module.report.controller.admin.goview.vo.project.GoViewProjectRespVO;
import com.ibonding.module.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import com.ibonding.module.report.convert.goview.GoViewProjectConvert;
import com.ibonding.module.report.dal.dataobject.goview.GoViewProjectDO;
import com.ibonding.module.report.service.goview.GoViewProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management backend - GoView Project")
@RestController
@RequestMapping("/report/go-view/project")
@Validated
public class GoViewProjectController {

    @Resource
    private GoViewProjectService goViewProjectService;

    @PostMapping("/create")
    @Operation(summary = "Create Project")
    @PreAuthorize("@ss.hasPermission('report:go-view-project:create')")
    public CommonResult<Long> createProject(@Valid @RequestBody GoViewProjectCreateReqVO createReqVO) {
        return success(goViewProjectService.createProject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Project")
    @PreAuthorize("@ss.hasPermission('report:go-view-project:update')")
    public CommonResult<Boolean> updateProject(@Valid @RequestBody GoViewProjectUpdateReqVO updateReqVO) {
        goViewProjectService.updateProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete GoView Project")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('report:go-view-project:delete')")
    public CommonResult<Boolean> deleteProject(@RequestParam("id") Long id) {
        goViewProjectService.deleteProject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Project")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('report:go-view-project:query')")
    public CommonResult<GoViewProjectRespVO> getProject(@RequestParam("id") Long id) {
        GoViewProjectDO project = goViewProjectService.getProject(id);
        return success(GoViewProjectConvert.INSTANCE.convert(project));
    }

    @GetMapping("/my-page")
    @Operation(summary = "Get My Project Pagination")
    @PreAuthorize("@ss.hasPermission('report:go-view-project:query')")
    public CommonResult<PageResult<GoViewProjectRespVO>> getMyProjectPage(@Valid PageParam pageVO) {
        PageResult<GoViewProjectDO> pageResult = goViewProjectService.getMyProjectPage(
                pageVO, getLoginUserId());
        return success(GoViewProjectConvert.INSTANCE.convertPage(pageResult));
    }

}

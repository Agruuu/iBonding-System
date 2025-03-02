package com.ibonding.module.member.controller.admin.level;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.module.member.controller.admin.level.vo.level.*;
import com.ibonding.module.member.convert.level.MemberLevelConvert;
import com.ibonding.module.member.dal.dataobject.level.MemberLevelDO;
import com.ibonding.module.member.service.level.MemberLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Membership Level")
@RestController
@RequestMapping("/member/level")
@Validated
public class MemberLevelController {

    @Resource
    private MemberLevelService levelService;

    @PostMapping("/create")
    @Operation(summary = "Create Membership Level")
    @PreAuthorize("@ss.hasPermission('member:level:create')")
    public CommonResult<Long> createLevel(@Valid @RequestBody MemberLevelCreateReqVO createReqVO) {
        return success(levelService.createLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Membership Level")
    @PreAuthorize("@ss.hasPermission('member:level:update')")
    public CommonResult<Boolean> updateLevel(@Valid @RequestBody MemberLevelUpdateReqVO updateReqVO) {
        levelService.updateLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Membership Level")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:level:delete')")
    public CommonResult<Boolean> deleteLevel(@RequestParam("id") Long id) {
        levelService.deleteLevel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get Membership Level")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:level:query')")
    public CommonResult<MemberLevelRespVO> getLevel(@RequestParam("id") Long id) {
        MemberLevelDO level = levelService.getLevel(id);
        return success(MemberLevelConvert.INSTANCE.convert(level));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get the List of Simplified Membership Level Information", description = "Get the list of simplified information of enabled membership levels, mainly used for dropdown options on the front-end")
    public CommonResult<List<MemberLevelSimpleRespVO>> getSimpleLevelList() {
        // 获用户列表，只要开启状态的
        List<MemberLevelDO> list = levelService.getEnableLevelList();
        // 排序后，返回给前端
        return success(MemberLevelConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/list")
    @Operation(summary = "Get Membership Level List")
    @PreAuthorize("@ss.hasPermission('member:level:query')")
    public CommonResult<List<MemberLevelRespVO>> getLevelList(@Valid MemberLevelListReqVO listReqVO) {
        List<MemberLevelDO> result = levelService.getLevelList(listReqVO);
        return success(MemberLevelConvert.INSTANCE.convertList(result));
    }

}

package com.ibonding.module.member.controller.admin.group;

import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.pojo.PageResult;
import com.ibonding.module.member.controller.admin.group.vo.*;
import com.ibonding.module.member.convert.group.MemberGroupConvert;
import com.ibonding.module.member.dal.dataobject.group.MemberGroupDO;
import com.ibonding.module.member.service.group.MemberGroupService;
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


@Tag(name = "Management Backend - User Group")
@RestController
@RequestMapping("/member/group")
@Validated
public class MemberGroupController {

    @Resource
    private MemberGroupService groupService;

    @PostMapping("/create")
    @Operation(summary = "Create User Group")
    @PreAuthorize("@ss.hasPermission('member:group:create')")
    public CommonResult<Long> createGroup(@Valid @RequestBody MemberGroupCreateReqVO createReqVO) {
        return success(groupService.createGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update User Group")
    @PreAuthorize("@ss.hasPermission('member:group:update')")
    public CommonResult<Boolean> updateGroup(@Valid @RequestBody MemberGroupUpdateReqVO updateReqVO) {
        groupService.updateGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete User Group")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:group:delete')")
    public CommonResult<Boolean> deleteGroup(@RequestParam("id") Long id) {
        groupService.deleteGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get User Group")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:group:query')")
    public CommonResult<MemberGroupRespVO> getGroup(@RequestParam("id") Long id) {
        MemberGroupDO group = groupService.getGroup(id);
        return success(MemberGroupConvert.INSTANCE.convert(group));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get the List of Simplified Membership Group Information", description = "Get the list of simplified information of enabled membership groups, mainly used for dropdown options on the front-end")
    public CommonResult<List<MemberGroupSimpleRespVO>> getSimpleGroupList() {
        // 获用户列表，只要开启状态的
        List<MemberGroupDO> list = groupService.getEnableGroupList();
        return success(MemberGroupConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get User Group Pagination")
    @PreAuthorize("@ss.hasPermission('member:group:query')")
    public CommonResult<PageResult<MemberGroupRespVO>> getGroupPage(@Valid MemberGroupPageReqVO pageVO) {
        PageResult<MemberGroupDO> pageResult = groupService.getGroupPage(pageVO);
        return success(MemberGroupConvert.INSTANCE.convertPage(pageResult));
    }

}

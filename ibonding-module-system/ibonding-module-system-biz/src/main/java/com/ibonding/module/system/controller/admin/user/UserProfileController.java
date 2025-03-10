package com.ibonding.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.ibonding.framework.common.enums.UserTypeEnum;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.datapermission.core.annotation.DataPermission;
import com.ibonding.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import com.ibonding.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.ibonding.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.ibonding.module.system.convert.user.UserConvert;
import com.ibonding.module.system.dal.dataobject.dept.DeptDO;
import com.ibonding.module.system.dal.dataobject.dept.PostDO;
import com.ibonding.module.system.dal.dataobject.permission.RoleDO;
import com.ibonding.module.system.dal.dataobject.social.SocialUserDO;
import com.ibonding.module.system.dal.dataobject.user.AdminUserDO;
import com.ibonding.module.system.service.dept.DeptService;
import com.ibonding.module.system.service.dept.PostService;
import com.ibonding.module.system.service.permission.PermissionService;
import com.ibonding.module.system.service.permission.RoleService;
import com.ibonding.module.system.service.social.SocialUserService;
import com.ibonding.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.ibonding.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.ibonding.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "Management Backend - User Personal Center")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;

    @GetMapping("/get")
    @Operation(summary = "Get User Profile")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        // 获得部门信息
        DeptDO dept = user.getDeptId() != null ? deptService.getDept(user.getDeptId()) : null;
        // 获得岗位信息
        List<PostDO> posts = CollUtil.isNotEmpty(user.getPostIds()) ? postService.getPostList(user.getPostIds()) : null;
        // 获得社交用户信息
        List<SocialUserDO> socialUsers = socialService.getSocialUserList(user.getId(), UserTypeEnum.ADMIN.getValue());
        return success(UserConvert.INSTANCE.convert(user, userRoles, dept, posts, socialUsers));
    }

    @PutMapping("/update")
    @Operation(summary = "Update User Profile")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "Update User Password")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar",
            method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "Update User Avatar")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}

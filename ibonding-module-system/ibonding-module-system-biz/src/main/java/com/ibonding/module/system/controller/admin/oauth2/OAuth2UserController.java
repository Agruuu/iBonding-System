package com.ibonding.module.system.controller.admin.oauth2;

import cn.hutool.core.collection.CollUtil;
import com.ibonding.framework.common.pojo.CommonResult;
import com.ibonding.framework.common.util.object.BeanUtils;
import com.ibonding.module.system.controller.admin.oauth2.vo.user.OAuth2UserInfoRespVO;
import com.ibonding.module.system.controller.admin.oauth2.vo.user.OAuth2UserUpdateReqVO;
import com.ibonding.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.ibonding.module.system.dal.dataobject.dept.DeptDO;
import com.ibonding.module.system.dal.dataobject.dept.PostDO;
import com.ibonding.module.system.dal.dataobject.user.AdminUserDO;
import com.ibonding.module.system.service.dept.DeptService;
import com.ibonding.module.system.service.dept.PostService;
import com.ibonding.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.ibonding.framework.common.pojo.CommonResult.success;
import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * It is mainly provided for external applications to call.
 *
 * 1. 1. Add the `@PreAuthorize("@ss.hasScope('user.read')")` annotation to the `getUserInfo` method to declare that the condition `scope = user.read` needs to be met.
 * 2. Add the `@PreAuthorize("@ss.hasScope('user.write')")` annotation to the `updateUserInfo` method to declare that the condition `scope = user.write` needs to be met.
 *
 * @author Agaru
 */
@Tag(name = "Management Backend - OAuth2.0 User")
@RestController
@RequestMapping("/system/oauth2/user")
@Validated
@Slf4j
public class OAuth2UserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;

    @GetMapping("/get")
    @Operation(summary = "Get User Info")
    @PreAuthorize("@ss.hasScope('user.read')") //
    public CommonResult<OAuth2UserInfoRespVO> getUserInfo() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        OAuth2UserInfoRespVO resp = BeanUtils.toBean(user, OAuth2UserInfoRespVO.class);
        // 获得部门信息
        if (user.getDeptId() != null) {
            DeptDO dept = deptService.getDept(user.getDeptId());
            resp.setDept(BeanUtils.toBean(dept, OAuth2UserInfoRespVO.Dept.class));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<PostDO> posts = postService.getPostList(user.getPostIds());
            resp.setPosts(BeanUtils.toBean(posts, OAuth2UserInfoRespVO.Post.class));
        }
        return success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "Update User Info")
    @PreAuthorize("@ss.hasScope('user.write')")
    public CommonResult<Boolean> updateUserInfo(@Valid @RequestBody OAuth2UserUpdateReqVO reqVO) {
        // 这里将 UserProfileUpdateReqVO =》UserProfileUpdateReqVO 对象，实现接口的复用。
        // 主要是，AdminUserService 没有自己的 BO 对象，所以复用只能这么做
        userService.updateUserProfile(getLoginUserId(), BeanUtils.toBean(reqVO, UserProfileUpdateReqVO.class));
        return success(true);
    }

}

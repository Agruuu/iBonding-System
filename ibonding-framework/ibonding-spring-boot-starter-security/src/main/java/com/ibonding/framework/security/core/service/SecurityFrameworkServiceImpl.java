package com.ibonding.framework.security.core.service;

import cn.hutool.core.collection.CollUtil;
import com.ibonding.framework.security.core.LoginUser;
import com.ibonding.framework.security.core.util.SecurityFrameworkUtils;
import com.ibonding.module.system.api.permission.PermissionApi;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import static com.ibonding.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 默认的 {@link SecurityFrameworkService} 实现类
 *
 * @author Agaru
 */
@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

    private final PermissionApi permissionApi;

    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {
        Long userId = getLoginUserId();
        if (userId == null) {
            return false;
        }
        return permissionApi.hasAnyPermissions(userId, permissions);
    }

    @Override
    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    @Override
    public boolean hasAnyRoles(String... roles) {
        Long userId = getLoginUserId();
        if (userId == null) {
            return false;
        }
        return permissionApi.hasAnyRoles(userId, roles);
    }

    @Override
    public boolean hasScope(String scope) {
        return hasAnyScopes(scope);
    }

    @Override
    public boolean hasAnyScopes(String... scope) {
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return false;
        }
        return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
    }

}

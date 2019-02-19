package com.frame.user.auth.info;

import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 权限信息
 * @author: duanchangqing90
 * @date: 2019/02/13
 */
@Data
public class SimpleAuthenticationInfo implements AuthenticationInfo {

    /*主体*/
    protected String principal;
    /*证书*/
    protected String credentials;
    /*名称*/
    protected String realName;
    /*属性*/
    protected Map<String, String> attr;
    /*角色**/
    protected Set<String> roles;
    /**权限*/
    protected Set<String> permissions;

    public SimpleAuthenticationInfo() {}

    public SimpleAuthenticationInfo(String principal, String credentials, String realName) {
        this.principal = principal;
        this.credentials = credentials;
        this.realName = realName;
    }

    public SimpleAuthenticationInfo(String principal, String credentials, String realName, Set<String> roles, Set<String> permissions) {
        this.principal = principal;
        this.credentials = credentials;
        this.realName = realName;
        this.roles = roles;
        this.permissions = permissions;
    }

    @Override
    public boolean hasRole(String role) {
        return Optional.ofNullable(roles).orElse(new HashSet<>()).stream().anyMatch(v -> v.equalsIgnoreCase(role));
    }

    @Override
    public boolean hasPermissions(String permission) {
        return Optional.ofNullable(permissions).orElse(new HashSet<>()).stream().anyMatch(v -> v.equalsIgnoreCase(permission));
    }
}

package com.frame.user.auth.info;

import java.io.Serializable;
import java.util.Set;

public interface AuthenticationInfo extends Serializable {

    /**
     * 主体
     * @return
     */
    String getPrincipal();

    /**
     * 证书
     * @return
     */
    String getCredentials();

    /**
     * 名称
     * @return
     */
    String getRealName();

    /**
     * 属性
     * @return
     */
    Object getAttr();

    /**
     * 角色
     * @return
     */
    Set<String> getRoles();

    /**
     * 权限
     * @return
     */
    Set<String> getPermissions();

    /**
     * 是否有角色
     * @param role
     * @return
     */
    boolean hasRole(String role);

    /**
     * 是否有权限
     * @param permission
     * @return
     */
    boolean hasPermissions(String permission);
}

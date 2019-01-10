package com.frame.user.shiro.realm;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import com.frame.user.entity.SysUserRole;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserRoleService;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象权限设置部分
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
@Slf4j
public abstract class AbstractRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Optional.ofNullable(principals).orElseThrow(() -> new AuthException(AuthMsgResult.AUTH_ERROR));
        // 获取用户名（此处会优先从缓存取）
        String username = (String) getAvailablePrincipal(principals);
        Set<String> roles = getRoles(username);
        Set<String> permissions = getPermissions(username);

        // 配置权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        log.debug("doGetAuthorizationInfo username:{}, roles:{}, permissions:{}", username, roles, permissions);
        return info;
    }

    private Set<String> getRoles(String username) {
        List<SysUserRole> sysUserRoles = Optional.ofNullable(sysUserRoleService.findByUsername(username)).orElse(Collections.emptyList());
        return sysUserRoles.stream()
                .map(SysUserRole::getRoleCode)
                .collect(Collectors.toSet());
    }

    private Set<String> getPermissions(String username) {
        // TODO
        return new HashSet<>();
    }

    /**
     * 获取用户对象
     * @param username
     * @return
     */
    protected SysUser getSysUser(String username) {
        return sysUserService.findByUsername(username);
    }

    /**
     * 校验用户状态
     * @param sysUser
     */
    protected void validUserStatus(SysUser sysUser) {
        // 校验用户状态
        UserStatus userStatus = sysUser.getUserStatus();
        Optional.ofNullable(userStatus).orElseThrow(() -> new AuthException(AuthMsgResult.USER_STATUS_ERROR));
        switch (userStatus) {
            case DELETED: throw new AuthException(AuthMsgResult.USER_DELETED_ERROR);
            case EXPIRED: throw new AuthException(AuthMsgResult.USER_EXPIRED_ERROR);
            case DISABLED: throw new AuthException(AuthMsgResult.USER_DISABLED_ERROR);
            case LOCKED: throw new AuthException(AuthMsgResult.USER_LOCKED_ERROR);
        }
    }

}

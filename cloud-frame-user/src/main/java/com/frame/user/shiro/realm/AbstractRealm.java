package com.frame.user.shiro.realm;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.bean.UserInfo;
import com.frame.user.entity.SysFunction;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysFunctionService;
import com.frame.user.service.SysModuleService;
import com.frame.user.service.SysRoleService;
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
    private SysRoleService sysRoleService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysFunctionService sysFunctionService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Optional.ofNullable(principals).orElseThrow(() -> new AuthException(AuthMsgResult.AUTH_ERROR));
        // 获取用户名（此处会优先从缓存取）
        UserInfo userinfo = (UserInfo) getAvailablePrincipal(principals);
        Set<String> roles = getRoles(userinfo.getUsername());
        Set<String> permissions = getPermissions(userinfo.getUsername());

        // 配置权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        log.debug("doGetAuthorizationInfo userinfo:{}, roles:{}, permissions:{}", userinfo, roles, permissions);
        return info;
    }

    private Set<String> getRoles(String username) {
        List<SysRole> sysRoles = Optional.ofNullable(
                sysRoleService.findByUsername(username, YesNo.Y)
            ).orElse(Collections.emptyList());

        return sysRoles.stream()
                .map(SysRole::getCode)
                .collect(Collectors.toSet());
    }

    private Set<String> getPermissions(String username) {
        // 获取角色，如果角色为空，则权限为空
        Set<String> roleCodes = getRoles(username);
        if (roleCodes == null || roleCodes.isEmpty()) {
            return null;
        }

        // 获取模块，如果模块为空，则权限为空
        List<SysModule> sysModules = Optional.ofNullable(
                sysModuleService.findByRoleCode(roleCodes, YesNo.Y)
            ).orElse(Collections.emptyList());
        if (sysModules == null || !sysModules.isEmpty()) {
            return null;
        }

        // 查询可用权限并返回
        List<SysFunction> sysFunctions = Optional.ofNullable(
                sysFunctionService.findByModuleCode(sysModules.stream().map(SysModule::getCode).collect(Collectors.toList()), YesNo.Y)
            ).orElse(Collections.emptyList());
        return sysFunctions.stream().map(SysFunction::getCode).collect(Collectors.toSet());
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

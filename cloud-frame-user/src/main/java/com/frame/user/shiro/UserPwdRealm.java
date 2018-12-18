package com.frame.user.shiro;

import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 自定义权限认证
 */
@Slf4j
public class UserPwdRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    /**
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("doGetAuthenticationInfo {}", authenticationToken);
        // 为空
        Optional.ofNullable(authenticationToken).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        // 获取用户名&密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        // 用户名或密码为空
        Optional.ofNullable(username).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        Optional.ofNullable(token.getPassword()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        SysUser sysUser = sysUserService.findByUsername(username);
        Optional.ofNullable(sysUser).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // TODO 校验用户状态
        String userStatus = sysUser.getUserStatus();

        // 构建凭证
        return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname());
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo {}", principalCollection);
        return new SimpleAuthorizationInfo();
    }
}

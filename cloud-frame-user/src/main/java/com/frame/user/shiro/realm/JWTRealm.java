package com.frame.user.shiro.realm;

import com.frame.user.bean.UserInfo;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.shiro.token.UserJWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

import java.util.Optional;

/**
 * 令牌认证
 */
@Slf4j
public class JWTRealm extends AbstractRealm {

    /**
     * 只处理TokenRealm
     * @see AuthenticatingRealm#supports(AuthenticationToken)
     * @return
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return UserJWTToken.class;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("doGetAuthenticationInfo {}", authenticationToken);// 为空
        Optional.ofNullable(authenticationToken).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 转换类型
        UserJWTToken token = (UserJWTToken) authenticationToken;

        // 用户名或密码为空
        Optional.ofNullable(token.getClientKey()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 获取用户
        SysUser sysUser = getSysUser(token.getClientKey());
        Optional.ofNullable(sysUser).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 校验用户状态
        validUserStatus(sysUser);

        // 构建凭证
        return new SimpleAuthenticationInfo(new UserInfo(sysUser.getUsername(), sysUser.getRealname()), token.getCredentials(), sysUser.getRealname());
    }
}

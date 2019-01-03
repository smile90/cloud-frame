package com.frame.user.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * 令牌认证
 */
@Slf4j
public class TokenRealm extends AbstractRealm {

    /**
     * 只处理TokenRealm
     * @see AuthenticatingRealm#supports(AuthenticationToken)
     * @return
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return TokenRealm.class;
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
        // TODO

        // 构建凭证
        return new SimpleAuthenticationInfo();
    }
}

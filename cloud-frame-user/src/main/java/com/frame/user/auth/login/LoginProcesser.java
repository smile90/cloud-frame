package com.frame.user.auth.login;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.token.LoginToken;
import com.frame.user.exception.AuthException;

/**
 * 登录处理
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
public interface LoginProcesser {

    /**
     * 登录
     * @param token
     * @throws AuthException
     * @return
     */
    AuthenticationInfo login(LoginToken token) throws AuthException;

    /**
     * 是否匹配
     * @return
     */
    Class<? extends LoginToken> getAuthenticationTokenClass();
}

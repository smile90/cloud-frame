package com.frame.user.auth.login;

import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.exception.AuthException;

/**
 * 登录处理
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
public interface LogoutProcesser {

    /**
     * 退出
     * @param token
     * @throws AuthException
     */
    void logout(AuthenticationToken token) throws AuthException;

    /**
     * 是否匹配
     * @return
     */
    Class<?> getAuthenticationTokenClass();
}

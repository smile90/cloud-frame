package com.frame.user.auth.realm;

import com.frame.user.auth.resource.Resource;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.exception.AuthException;

/**
 * 登录校验
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
public interface LoginRealm extends Realm {

    /**
     * 校验
     * @param token
     * @throws AuthException
     */
    void validLogin(AuthenticationToken token, Resource resource) throws AuthException;
}

package com.frame.user.auth.login;

import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.exception.AuthException;
import org.springframework.stereotype.Service;

/**
 * jwt登出
 * @author: duanchangqing90
 * @date: 2019/02/15
 */
@Service
public class UserJWTLogoutProcesser implements LogoutProcesser {
    @Override
    public void logout(AuthenticationToken token) throws AuthException {
        // nothing
    }

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return UserJWTToken.class;
    }
}

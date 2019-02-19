package com.frame.user.auth.matcher;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.LoginToken;
import com.frame.user.auth.token.UserPwdToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 密码校验：使用Spring BCrypt算法
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Slf4j
@Service("pwdCredentialsMatcher")
public class PwdCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(LoginToken authenticationToken, AuthenticationInfo info) {
        // 为空，则校验失败
        if (authenticationToken == null || info == null) {
            return false;
        }
        UserPwdToken token = (UserPwdToken) authenticationToken;
        if (new BCryptPasswordEncoder().matches(new String(token.getPassword()), info.getCredentials())) {
            return true;
        } else {
            return false;
        }
    }
}

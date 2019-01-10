package com.frame.user.shiro.matcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码校验：使用Spring BCrypt算法
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Slf4j
public class PwdCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        // 为空，则校验失败
        if (authenticationToken == null || info == null) {
            return false;
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (new BCryptPasswordEncoder().matches(new String(token.getPassword()), (String) info.getCredentials())) {
            return true;
        } else {
            return false;
        }
    }
}

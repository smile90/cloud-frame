package com.frame.user.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 使用Spring BCrypt算法进行密码校验
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        // 为空，则校验失败
        if (authenticationToken == null || info == null) {
            return false;
        }
        // 校验密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (new BCryptPasswordEncoder().matches(new String(token.getPassword()), (String) info.getCredentials())) {
            return true;
        } else {
            return false;
        }
    }
}

package com.frame.user.shiro.matcher;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.frame.user.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: duanchangqing90
 * @date: 2019/01/10
 */
@Slf4j
public class JWTMatcher implements CredentialsMatcher {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        String token = (String) info.getCredentials();
        try {
            jwtUtil.validToken(token);
            return true;
        } catch (TokenExpiredException e) {
            log.error("validToken: token error. token:{}, {}", token, e.getMessage());
            log.debug("validToken: token error. token:{}", token, e);
            return false;
        } catch (SignatureVerificationException e) {
            log.error("validToken: token error. token:{}, {}", token, e.getMessage());
            log.debug("validToken: token error. token:{}", token, e);
            return false;
        } catch (JWTVerificationException e) {
            log.error("validToken: token error. token:{}, {}", token, e.getMessage());
            log.debug("validToken: token error. token:{}", token, e);
            return false;
        } catch (Exception e) {
            log.error("validToken: token error. token:{}, {}", token, e.getMessage());
            log.debug("validToken: token error. token:{}", token, e);
            return false;
        }
    }
}

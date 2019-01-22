package com.frame.user.shiro;

import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;

/**
 * 复写该类的原因：原类有异常不进行处理，由于在Realm中通过support区分了类型，这里直接抛出异常
 * @author: duanchangqing90
 * @date: 2019/01/07
 */
@Slf4j
public class ShiroAtLeastOneSuccessfulStrategy extends AtLeastOneSuccessfulStrategy {

    private static final ThreadLocal<Throwable> exceptions = new ThreadLocal();

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        Throwable t = exceptions.get();
        if (t != null) {
            if (t instanceof AuthException) {
                throw (AuthException) t;
            } else if (t instanceof IncorrectCredentialsException) {
                throw new AuthException(AuthMsgResult.USER_PWD_ERROR);
            } else {
                throw new AuthException(AuthMsgResult.LOGIN_ERROR);
            }
        }
        return super.afterAllAttempts(token, aggregate);
    }

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        if (t != null) {
            exceptions.set(t);
        }
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }
}

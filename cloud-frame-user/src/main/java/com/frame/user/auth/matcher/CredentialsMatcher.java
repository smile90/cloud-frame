package com.frame.user.auth.matcher;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.token.LoginToken;

/**
 * 凭证校验
 * @author: duanchangqing90
 * @date: 2019/02/13
 */
public interface CredentialsMatcher {

    boolean doCredentialsMatch(LoginToken token, AuthenticationInfo info);
}

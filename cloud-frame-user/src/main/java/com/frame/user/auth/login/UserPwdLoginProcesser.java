package com.frame.user.auth.login;

import com.frame.user.auth.matcher.CredentialsMatcher;
import com.frame.user.auth.token.LoginToken;
import com.frame.user.auth.token.UserPwdToken;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.ValidCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户名密码登录处理
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
@Service
public class UserPwdLoginProcesser extends AbstractLoginProcesser {

    @Autowired
    @Qualifier("pwdCredentialsMatcher")
    private CredentialsMatcher credentialsMatcher;

    @Autowired
    private ValidCodeService validCodeService;

    @Override
    protected void validParam(LoginToken token) throws AuthException {
        super.validParam(token);
        // 校验验证码
        validValidCode((UserPwdToken) token);
    }

    @Override
    protected CredentialsMatcher getCredentialsMatcher() {
        return credentialsMatcher;
    }

    @Override
    public Class<? extends LoginToken> getAuthenticationTokenClass() {
        return UserPwdToken.class;
    }

    /**
     * 校验验证码
     * @param token
     * @throws AuthException
     */
    protected void validValidCode(UserPwdToken token) throws AuthException {
        // 验证验证码
        if (authProperties.getLogin().isEnableValidCode()) {
            Optional.ofNullable(token.getValidCode()).orElseThrow(() -> new AuthException(AuthMsgResult.VALID_CODE_ERROR));
            Optional.ofNullable(token.getSessionId()).orElseThrow(() -> new AuthException(AuthMsgResult.VALID_CODE_ERROR));

            // 验证不通过：验证码错误
            String validCodeKey = token.getSessionId();
            if (!validCodeService.valid(validCodeKey, token.getValidCode())) {
                throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
            }
            validCodeService.deleteValidCode(validCodeKey);
        }
    }
}

package com.frame.user.shiro.realm;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.service.SysUserService;
import com.frame.user.service.ValidCodeService;
import com.frame.user.shiro.token.UserFormToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 用户名密码认证
 */
@Slf4j
public class UserPwdRealm extends AbstractRealm {

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private ValidCodeService validCodeService;

    /**
     * 只处理UserFormToken
     * @see AuthenticatingRealm#supports(org.apache.shiro.authc.AuthenticationToken)
     * @return
     */
    @Override
    public Class getAuthenticationTokenClass() {
        return UserFormToken.class;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("doGetAuthenticationInfo {}", authenticationToken);
        Optional.ofNullable(authenticationToken).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 转换类型
        UserFormToken token = (UserFormToken) authenticationToken;

        // 验证
        if (authProperties.getLogin().isEnableValidCode()) {
            Optional.ofNullable(token.getValidCode()).orElseThrow(() -> new AuthException(AuthMsgResult.VALID_CODE_ERROR));
            // 验证不通过：验证码错误
            String validCodeKey =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getId();
            if (!validCodeService.valid(validCodeKey, token.getValidCode())) {
                throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
            }
            validCodeService.deleteValidCode(validCodeKey);
        }

        // 用户名或密码为空
        Optional.ofNullable(token.getUsername()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        Optional.ofNullable(token.getPassword()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 获取用户
        SysUser sysUser = getSysUser(token.getUsername());
        Optional.ofNullable(sysUser).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 校验用户状态
        validUserStatus(sysUser);

        // 构建凭证
        return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname());
    }
}

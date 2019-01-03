package com.frame.user.shiro;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.service.SysUserService;
import com.frame.user.service.ValidCodeService;
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
    @Autowired
    private SysUserService sysUserService;

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
        // 为空
        Optional.ofNullable(authenticationToken).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        // 转换类型
        UserFormToken token = (UserFormToken) authenticationToken;

        // 校验验证码
        Optional.ofNullable(token.getValidCode()).orElseThrow(() -> new AuthException(AuthMsgResult.VALID_CODE_ERROR));
        String validCodeKey =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getId();
        // 验证 && 验证不通过：验证码错误
        if (authProperties.getLogin().isEnableValidCode() && !validCodeService.valid(validCodeKey, token.getValidCode())) {
            throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
        } else {
            validCodeService.deleteValidCode(validCodeKey);
        }

        // 用户名或密码为空
        Optional.ofNullable(token.getUsername()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        Optional.ofNullable(token.getPassword()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 获取用户
        SysUser sysUser = sysUserService.findByUsername(token.getUsername());
        Optional.ofNullable(sysUser).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));

        // 校验用户状态
        UserStatus userStatus = sysUser.getUserStatus();
        Optional.ofNullable(userStatus).orElseThrow(() -> new AuthException(AuthMsgResult.USER_STATUS_ERROR));
        switch (userStatus) {
            case DELETED: throw new AuthException(AuthMsgResult.USER_DELETED_ERROR);
            case EXPIRED: throw new AuthException(AuthMsgResult.USER_EXPIRED_ERROR);
            case DISABLED: throw new AuthException(AuthMsgResult.USER_DISABLED_ERROR);
            case LOCKED: throw new AuthException(AuthMsgResult.USER_LOCKED_ERROR);
        }

        // 构建凭证
        return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname());
    }
}

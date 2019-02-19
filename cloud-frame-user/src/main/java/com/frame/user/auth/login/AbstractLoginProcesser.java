package com.frame.user.auth.login;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.info.SimpleAuthenticationInfo;
import com.frame.user.auth.matcher.CredentialsMatcher;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.LoginToken;
import com.frame.user.constant.RedisKeyConstant;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 用户名密码登录处理
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
public abstract class AbstractLoginProcesser implements LoginProcesser {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    protected AuthProperties authProperties;
    @Autowired
    protected SysUserService sysUserService;

    /**
     * 凭证校验
     * @return
     */
    protected abstract CredentialsMatcher getCredentialsMatcher();

    /**
     * 完善用户信息
     * @param info
     * @return
     */
    protected AuthenticationInfo completeInfo(AuthenticationInfo info) {
        // 构建权限
        if (info instanceof SimpleAuthenticationInfo) {
            ((SimpleAuthenticationInfo) info).setRoles(sysUserService.getRoles(info.getPrincipal()));
            ((SimpleAuthenticationInfo) info).setPermissions(sysUserService.getPermissions(info.getPrincipal()));
        }
        return info;
    }

    @Override
    final public AuthenticationInfo login(LoginToken token) throws AuthException {
        try {
            log.info("login begin. token:{}", token);
            // 校验登录
            AuthenticationInfo info = volidLogin(token);
            // 登录成功，清空
            clearErrorTime(token);
            // 完善信息
            completeInfo(info);

            log.info("login end. token:{}", token);
            return info;
        } catch (Exception e) {
            // 登录错误，累加
            incrementErrorTime(token);
            log.warn("login error. token:{}", token);
            throw e;
        }
    }

    /**
     * 登录校验
     * @param token
     * @throws AuthException
     */
    protected AuthenticationInfo volidLogin(LoginToken token) throws AuthException {
        // 参数校验
        validParam(token);
        // 校验错误次数
        validErrorTime(token);
        // 校验信息
        AuthenticationInfo info = validInfo(token);
        // 校验凭证
        validCredentials(token, info);
        return info;
    }

    /**
     * 参数校验
     * @param token
     * @throws AuthException
     */
    protected void validParam(LoginToken token) throws AuthException {
        Optional.ofNullable(token).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_PARAM_ERROR));
        Optional.ofNullable(token.getPrincipal()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_PARAM_ERROR));
        Optional.ofNullable(token.getCredentials()).orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_PARAM_ERROR));
    }

    /**
     * 累加错误次数
     * @param token
     */
    private void incrementErrorTime(LoginToken token) {
        if (token != null) {
            redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getPrincipal()).increment();
            redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getPrincipal()).expire(authProperties.getLogin().getErrorTimeout().getSeconds(), TimeUnit.SECONDS);
        }
    }

    /**
     * 删除累计错误次数
     * @param token
     */
    private void clearErrorTime(LoginToken token) {
        if (authProperties.getLogin().isEnableErrorTime()) {
            redisTemplate.delete(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getPrincipal());
        }
    }

    /**
     * 验证错误次数
     * @param token
     */
    protected void validErrorTime(LoginToken token) {
        // 获取错误次数，不存在为0
        Integer errorTime = Optional.ofNullable(redisTemplate.opsForValue().get(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getPrincipal())).orElse(Integer.valueOf(0));
        log.debug("login errorTime:{}, maxErrorTime:{}, token:{}", errorTime, authProperties.getLogin().getMaxErrorTime().intValue(), token);
        // 启用登录错误限制 并且 登录错误次数大于等于设定次数，直接登录失败
        if (authProperties.getLogin().isEnableErrorTime()
                && errorTime.intValue() >= authProperties.getLogin().getMaxErrorTime().intValue()) {
            throw new AuthException(AuthMsgResult.LOGIN_TIME_ERROR);
        }
    }

    /**
     * 校验信息
     * @param token
     * @throws AuthException
     */
    protected AuthenticationInfo validInfo(LoginToken token) throws AuthException {
        // 获取信息
        SysUser sysUser = Optional.ofNullable(sysUserService.findByUsername(token.getPrincipal()))
                .orElseThrow(() -> new AuthException(AuthMsgResult.USER_PWD_ERROR));
        return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname());
    }

    /**
     * 凭证校验
     * @param token
     * @param info
     */
    protected void validCredentials(LoginToken token, AuthenticationInfo info) throws AuthException {
        if (!getCredentialsMatcher().doCredentialsMatch(token, info)) {
            throw new AuthException(AuthMsgResult.USER_PWD_ERROR);
        }
    }

}

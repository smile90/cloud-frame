package com.frame.user.service;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.bean.LoginUser;
import com.frame.user.bean.UserInfo;
import com.frame.user.constant.RedisKeyConstant;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.shiro.token.UserFormToken;
import com.frame.user.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 登录Service
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@Slf4j
@Service
@EnableConfigurationProperties(AuthProperties.class)
public class SysLoginService {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 登录
     *
     * @param loginUser
     * @return
     */
    public ResponseBean login(LoginUser loginUser) {
        try {
            // 校验登录
            volidLogin(loginUser);

            // 执行用户名密码授权
            UserFormToken token = new UserFormToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.isRememberMe(), loginUser.getValidCode());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            // 执行JWT授权
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            subject = SecurityUtils.getSubject();
            UserInfo userInfo = (UserInfo) subject.getPrincipal();
            AuthenticationToken userJwtToken = jwtUtil.createAuthenticationToken(userInfo.getUsername() ,userInfo.getRealname(), loginUser.getDeviceSource(), request.getRemoteHost());
            subject.login(userJwtToken);

            // 登录成功处理
            loginSuccess(loginUser);
            // 返回结果
            return ResponseBean.successContent(JSONObject.toJSON(userJwtToken));
        } catch (AuthException e) {
            // 登录错误，累加
            incrementErrorTime(loginUser);
            if (log.isDebugEnabled()) {
                log.error("login error", e);
            } else {
                log.error("login error. {}", e.getMessage());
            }
            return ResponseBean.getInstance(e.getErrorCode(), e.getMessage(), e.getShowMsg(), null);
        } catch (Exception e) {
            // 登录错误，累加
            incrementErrorTime(loginUser);
            if (log.isDebugEnabled()) {
                log.error("login error", e);
            } else {
                log.error("login error. {}", e.getMessage());
            }
            if (e.getCause() instanceof AuthException) {
                return ResponseBean.getInstance(((AuthException) e.getCause()).getErrorCode(), e.getCause().getMessage(), ((AuthException) e.getCause()).getShowMsg(), null);
            } else {
                return ResponseBean.getInstance(AuthMsgResult.USER_PWD_ERROR);
            }
        }
    }

    /**
     * 退出
     * @return
     */
    public ResponseBean logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("logout error.", e);
            return ResponseBean.getInstance(AuthMsgResult.LOGOUT_ERROR);
        }
    }

    /**
     * 校验登录
     * @param loginUser
     * @return
     */
    private void volidLogin(LoginUser loginUser) throws AuthException {
        log.debug("enableValidCode:{}, loginUser:{}", authProperties.getLogin().isEnableValidCode(), loginUser);
        if (authProperties.getLogin().isEnableValidCode() && !StringUtils.hasText(loginUser.getValidCode())) {
            throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
        }
        if (loginUser == null || !StringUtils.hasText(loginUser.getUsername()) || !StringUtils.hasText(loginUser.getPassword())) {
            throw new AuthException(AuthMsgResult.USER_PWD_PARAM_ERROR);
        }

        // 获取错误次数，不存在为0
        Integer errorTime = Optional.ofNullable(redisTemplate.opsForValue().get(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + loginUser.getUsername())).orElse(Integer.valueOf(0));
        log.debug("login errorTime:{}, maxErrorTime:{}, loginUser:{}", errorTime, authProperties.getLogin().getMaxErrorTime().intValue(), loginUser);
        // 启用登录错误限制 并且 登录错误次数大于等于设定次数，直接登录失败
        if (authProperties.getLogin().isEnableErrorTime()
                && errorTime.intValue() >= authProperties.getLogin().getMaxErrorTime().intValue()) {
            throw new AuthException(AuthMsgResult.LOGIN_TIME_ERROR);
        }
    }

    /**
     * 累加错误次数
     * @param loginUser
     */
    private void incrementErrorTime(LoginUser loginUser) {
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + loginUser.getUsername()).increment();
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + loginUser.getUsername()).expire(authProperties.getLogin().getErrorTimeout().getSeconds(), TimeUnit.SECONDS);
    }

    /**
     * 登录成功处理
     * 删除累计错误次数
     * @param loginUser
     */
    private void loginSuccess(LoginUser loginUser) {
        if (authProperties.getLogin().isEnableErrorTime()) {
            redisTemplate.delete(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + loginUser.getUsername());
        }
    }
}

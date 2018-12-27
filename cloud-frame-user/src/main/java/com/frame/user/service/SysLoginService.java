package com.frame.user.service;

import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.bean.LoginUser;
import com.frame.user.constant.RedisKeyConstant;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.shiro.UserFormToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private AuthProperties authProperties;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 登录
     *
     * @param loginUser
     * @return
     */
    public ResponseBean login(LoginUser loginUser) {
        Subject subject = SecurityUtils.getSubject();
        UserFormToken token = new UserFormToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.isRememberMe(), loginUser.getValidCode());
        try {
            // 获取错误次数，不存在为0
            Integer time = Optional.ofNullable(redisTemplate.opsForValue().get(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getUsername())).orElse(Integer.valueOf(0));
            // 登录错误次数少于设定次数，执行登录，否则，登录失败
            if (time.intValue() < authProperties.getLogin().getErrorTime().intValue()) {
                subject.login(token);
                // 登录成功，删除错误次数
                redisTemplate.delete(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getUsername());
                return ResponseBean.success();
            } else {
                log.error("login time error.");
                return ResponseBean.getInstance(AuthMsgResult.LOGIN_TIME_ERROR);
            }
        } catch (AuthException e) {
            // 登录错误，累加
            incrementErrorTime(token);
            log.error("login error. {}", e.getMessage());
            return ResponseBean.getInstance(e.getErrorCode(), e.getMessage(), e.getShowMsg(), null);
        } catch (Exception e) {
            // 登录错误，累加
            incrementErrorTime(token);
            log.error("login error. {}", e.getMessage());
            if (e.getCause() instanceof AuthException) {
                return ResponseBean.getInstance(((AuthException) e.getCause()).getErrorCode(), e.getCause().getMessage(), ((AuthException) e.getCause()).getShowMsg(), null);
            } else {
                return ResponseBean.getInstance(AuthMsgResult.USER_PWD_ERROR);
            }
        }
    }

    /**
     * 退出
     *
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
     * 累加错误次数
     *
     * @param token
     */
    private void incrementErrorTime(UsernamePasswordToken token) {
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getUsername()).increment();
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_ERROR_TIME_PRE + token.getUsername()).expire(authProperties.getLogin().getErrorTimeout().getSeconds(), TimeUnit.SECONDS);
    }

}

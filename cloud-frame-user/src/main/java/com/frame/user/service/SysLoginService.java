package com.frame.user.service;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.info.InfoManager;
import com.frame.user.auth.login.LoginManager;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.auth.token.UserPwdToken;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 登录Service
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@Slf4j
@Service
@EnableConfigurationProperties(AuthProperties.class)
public class SysLoginService {

    @Autowired
    private LoginManager loginManager;
    @Autowired
    private InfoManager infoManager;

    /**
     * 登录
     * @param token
     * @return
     */
    public ResponseBean login(UserPwdToken token) {
        try {
            AuthenticationInfo info = loginManager.login(UserPwdToken.class, token);

            // 保存信息
            Map<AuthenticationToken, AuthenticationInfo> map = infoManager.saveInfo(info);
            if (map == null || map.isEmpty()) {
                return ResponseBean.error(AuthMsgResult.LOGIN_ERROR);
            } else {
                // 返回JWT结果
                AuthenticationToken jwtToken = map.keySet().stream()
                        .filter(m -> m.getClass().isAssignableFrom(UserJWTToken.class))
                        .findFirst().get();
                return ResponseBean.successContent(JSONObject.toJSON(jwtToken));
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("login error. token:{}", token, e);
            } else {
                log.error("login error. token:{}. {}", token, e.getMessage());
            }
            if (e instanceof AuthException) {
                return ResponseBean.error().setCode(((AuthException) e).getErrorCode()).setMsg(e.getMessage()).setShowMsg(((AuthException) e).getShowMsg());
            } else if (e.getCause() instanceof AuthException) {
                return ResponseBean.error().setCode(((AuthException) e.getCause()).getErrorCode()).setMsg(e.getCause().getMessage()).setShowMsg(((AuthException) e.getCause()).getShowMsg());
            } else {
                return ResponseBean.error(AuthMsgResult.LOGIN_ERROR);
            }
        }
    }

    /**
     * 退出
     * @return
     */
    public ResponseBean logout(AuthenticationToken token) {
        try {
            loginManager.logout(token.getClass(), token);
            return ResponseBean.success();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("logout error. token:{}", token, e);
            } else {
                log.error("logout error. token:{}. {}", token, e.getMessage());
            }
            return ResponseBean.error(AuthMsgResult.LOGOUT_ERROR);
        }
    }
}

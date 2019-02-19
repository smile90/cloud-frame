package com.frame.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.info.InfoManager;
import com.frame.user.auth.realm.RealmManager;
import com.frame.user.auth.resource.HttpResource;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.auth.token.UserPwdToken;
import com.frame.user.auth.util.JWTUtil;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.service.SysLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class SysAuthController {

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private RealmManager realmManager;
    @Autowired
    private InfoManager infoManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private SysLoginService sysLoginService;

    private AuthenticationToken getJwtToken(JSONObject data, HttpServletRequest request) {
        String jwt = data.getString(authProperties.getJwt().getTokenName());
        return jwtUtil.decodedAuthenticationToken(jwt, request);
    }
    private HttpResource getHttpResource(JSONObject data) {
        JSONObject resource = data.getJSONObject("resource");
        String method = Optional.of(resource.getString("method")).get();
        String url = Optional.of(resource.getString("url")).get();
        return new HttpResource(method, url);
    }

    /**
     * 校验用户是否可用
     * @param data
     * @return
     */
    @PostMapping("/validLogin/jwt")
    public ResponseBean validLoginJwt(@RequestBody JSONObject data, HttpServletRequest request) {
        try {
            realmManager.volidLogin(UserJWTToken.class, getJwtToken(data, request), getHttpResource(data));
            return ResponseBean.success();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("validUser error. data:{}", data, e);
            } else {
                log.error("validUser error. data:{}", data, e.getLocalizedMessage());
            }
            if (e instanceof AuthException || e.getCause() instanceof AuthException) {
                return ResponseBean.getInstance(AuthMsgResult.NOT_LOGIN_ERROR);
            } else {
                return ResponseBean.getInstance(AuthMsgResult.AUTH_ERROR);
            }
        }
    }

    /**
     * 校验请求是否可用
     * @param data
     * @return
     */
    @PostMapping("/validAuth/jwt")
    public ResponseBean validAuthJwt(@RequestBody JSONObject data, HttpServletRequest request) {
        try {
            realmManager.volidPermissions(UserJWTToken.class, getJwtToken(data, request), getHttpResource(data));
            return ResponseBean.success();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("volidPermissions error. data:{}", data, e);
            } else {
                log.error("volidPermissions error. data:{}", data, e.getLocalizedMessage());
            }
            if (e instanceof AuthException || e.getCause() instanceof AuthException) {
                return ResponseBean.getInstance(AuthMsgResult.NOT_AUTH_ERROR);
            } else {
                return ResponseBean.getInstance(AuthMsgResult.AUTH_ERROR);
            }
        }
    }

    /**
     * 获取用户信息
     * @param data
     * @return
     */
    @PostMapping("/getInfo/jwt")
    public ResponseBean getInfoJwt(@RequestBody JSONObject data, HttpServletRequest request) {
        try {
            JSONObject result = new JSONObject();
            AuthenticationInfo info = infoManager.getInfo(UserJWTToken.class, getJwtToken(data, request));
            if (info != null) {
                result.put("username", info.getPrincipal());
            }
            return ResponseBean.successContent(result);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("getInfo error. data:{}", data, e);
            } else {
                log.error("getInfo error. data:{}", data, e.getLocalizedMessage());
            }
            return ResponseBean.getInstance(AuthMsgResult.AUTH_ERROR);
        }
    }

    @PostMapping("/login")
    public Object login(@RequestBody JSONObject data) {
        try {
            return sysLoginService.login(JSONObject.parseObject(data.toJSONString(), UserPwdToken.class));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("login error. data:{}", data, e);
            } else {
                log.error("login error. data:{}", data, e.getLocalizedMessage());
            }
            return ResponseBean.getInstance(AuthMsgResult.LOGIN_ERROR);
        }
    }

    @PostMapping("/logout")
    public Object logout(@RequestBody JSONObject data, HttpServletRequest request){
        try {
            return sysLoginService.logout(getJwtToken(data, request));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("logout error. data:{}", data, e);
            } else {
                log.error("logout error. data:{}", data, e.getLocalizedMessage());
            }
            return ResponseBean.getInstance(AuthMsgResult.LOGOUT_ERROR);
        }
    }

}

package com.frame.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.frame.client.AuthClient;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.gateway.SystemMsgResult;
import com.frame.gateway.properties.AuthProperties;
import com.frame.gateway.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录处理
 * @author: duanchangqing90
 * @date: 2019/02/15
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/login")
    public Object login(@RequestParam Map<String, String> data, HttpServletRequest request) {
        try {
            JSONObject req = JSONObject.parseObject(JSONObject.toJSONString(data));
            req.put("sessionId", request.getRequestedSessionId());
            req.put("deviceSource", authUtil.getDeviceSource(request));
            ResponseBean result = authClient.login(req);
            log.debug("login end. result:{}", result);
            if (result.getSuccess() && result.getContent() != null) {
                JSONObject token = JSONObject.parseObject(JSONObject.toJSONString(result.getContent()), JSONObject.class);
                request.getSession().setAttribute(authProperties.getSession().getTokenName(), (token != null ? token.get("token") : null));
            }
            return result;
        } catch (Exception e) {
            log.error("logout error. data:{}", data, e);
            return ResponseBean.error(SystemMsgResult.SYSTEM_ERROR);
        }
    }

    @GetMapping("/logout")
    public Object logout(HttpServletRequest request) {
        try {
            JSONObject data = new JSONObject();
            String token = authUtil.getToken(request);
            data.put(authProperties.getJwt().getTokenName(), token);

            ResponseBean result = authClient.logout(data);
            if (result.getSuccess()) {
                request.getSession().setAttribute(authProperties.getSession().getTokenName(), null);
            }
            return result;
        } catch (Exception e) {
            log.error("logout error.", e);
            return ResponseBean.error(SystemMsgResult.SYSTEM_ERROR);
        }
    }
}

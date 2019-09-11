package com.frame.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.enums.OAuthMsgResult;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 * @author: duanchangqing90
 * @date: 2019/03/07
 */
@Slf4j
@RequestMapping("/pub")
@RestController
public class SysUserDetailsController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/oauth/getInfo")
    public ResponseBean user(Authentication auth, HttpServletRequest request) {
        try {
            Object user = auth.getPrincipal();
            return ResponseBean.successContent(JSON.toJSONString(user));
        } catch (Exception e) {
            log.error("get user info error.", e);
            return ResponseBean.error(OAuthMsgResult.OAUTH_GET_USER_INF_ERROR);
        }
    }
}
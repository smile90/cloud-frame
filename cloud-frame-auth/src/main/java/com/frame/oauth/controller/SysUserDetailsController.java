package com.frame.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.frame.oauth.beans.UserDetails;
import com.frame.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户服务
 * @author: duanchangqing90
 * @date: 2019/03/07
 */
@RequestMapping("/pub")
@RestController
public class SysUserDetailsController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/oauth/getInfo")
    public Object user(Authentication auth) {
        Map<String, String> map = new LinkedHashMap<>();
        UserDetails user = (UserDetails) auth.getPrincipal();
        map.putAll(JSON.parseObject(JSON.toJSONString(user), Map.class));
        return map;
    }
}
package com.frame.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.validate.DefaultGroup;
import com.frame.mybatis.validate.SaveGroup;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.UserMsgResult;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseBean user(Authentication auth) {
        try {
            Object user = auth.getPrincipal();
            return ResponseBean.successContent(JSON.parseObject(JSON.toJSONString(user)));
        } catch (Exception e) {
            log.error("get user info error.", e);
            return ResponseBean.error(AuthMsgResult.GET_USER_ERROR);
        }
    }

    @GetMapping("/user/findBySource/{source}/{sourceId}")
    public ResponseBean findBySource(@PathVariable("source") String source, @PathVariable("sourceId") String sourceId) {
        SysUser user = sysUserService.findBySource(source, sourceId);
        if (user != null) {
            Set<String> permissions = sysUserService.getPermissions(user.getUsername());
            return ResponseBean.successContent(process2JSON(user, permissions));
        } else {
            return ResponseBean.error(AuthMsgResult.USER_NOT_EXIT);
        }
    }

    @GetMapping("/user/findByUsername/{username}")
    public ResponseBean findByUsername(@PathVariable("username") String username) {
        SysUser user = sysUserService.findByUsername(username);
        if (user != null) {
            Set<String> permissions = sysUserService.getPermissions(user.getUsername());
            return ResponseBean.successContent(process2JSON(user, permissions));
        } else {
            return ResponseBean.error(AuthMsgResult.USER_NOT_EXIT);
        }
    }

    public JSONObject process2JSON(SysUser user, Set<String> permissions) {
        JSONObject result = new JSONObject();
        result.put("user", user);
        result.put("permissions", permissions);
        return result;
    }

    @PostMapping("/user/save")
    public ResponseBean save(@RequestBody @Validated({SaveGroup.class, DefaultGroup.class}) SysUser bean) {
        sysUserService.save(bean);
        return ResponseBean.successContent(JSON.parseObject(JSON.toJSONString(bean)));
    }
}
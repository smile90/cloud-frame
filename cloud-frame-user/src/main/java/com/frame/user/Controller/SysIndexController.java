package com.frame.user.controller;

import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.client.BossAuthUtil;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.service.SysMenuService;
import com.frame.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SysIndexController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/userInfo")
    public Object userInfo() {
        String username = BossAuthUtil.getUsername();
        if (!StringUtils.hasText(username)) {
            return ResponseBean.getInstance(AuthMsgResult.NOT_LOGIN_ERROR);
        } else {
            return ResponseBean.successContent(sysUserService.findByUsername(username));
        }
    }

    @GetMapping("/menu")
    public Object menu() {
        String username = BossAuthUtil.getUsername();
        if (!StringUtils.hasText(username)) {
            return ResponseBean.getInstance(AuthMsgResult.NOT_LOGIN_ERROR);
        } else {
            return ResponseBean.successContent(sysMenuService.findMenuJSONByUsername(username, YesNo.Y));
        }
    }

}

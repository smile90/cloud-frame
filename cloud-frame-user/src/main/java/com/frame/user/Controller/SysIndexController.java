package com.frame.user.controller;

import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.bean.LoginUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.service.SysLoginService;
import com.frame.user.service.SysMenuService;
import com.frame.user.shiro.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys")
public class SysIndexController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/login")
    public Object login(LoginUser loginUser) {
        return sysLoginService.login(loginUser);
    }

    @GetMapping("/logout")
    public Object logout(){
        return sysLoginService.logout();
    }

    @GetMapping("/menu")
    public Object menu(HttpServletRequest request) {
        String token = jwtUtil.getToken(request);
        if (!StringUtils.hasText(token)) {
            return ResponseBean.getInstance(AuthMsgResult.NOT_LOGIN_ERROR);
        } else {
            return ResponseBean.successContent(sysMenuService.findMenuJSONByUsername(jwtUtil.getUsername(token), YesNo.Y));
        }
    }

}

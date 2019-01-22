package com.frame.user.controller;

import com.frame.user.bean.LoginUser;
import com.frame.user.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    public Object login(LoginUser loginUser) {
        return sysLoginService.login(loginUser);
    }

    @GetMapping("/logout")
    public Object logout(){
        return sysLoginService.logout();
    }

}

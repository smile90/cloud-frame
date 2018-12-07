package com.frame.user.Controller;

import com.frame.user.bean.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @GetMapping("/login")
    public Object login(LoginUser loginUser) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(),loginUser.getPassword());
        if(!subject.isAuthenticated()){
            subject.login(token);
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return null;
    }

}

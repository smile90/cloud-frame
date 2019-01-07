package com.frame.test;

import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.shiro.SysAuthMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysAuthMatcher sysAuthByRoleMatcher;

    @RequestMapping("/test/{test}")
    public Object test(@PathVariable("test") String test) {
        return test;
    }

    @RequestMapping("/testPath/{test}")
    public Object testPath(@PathVariable("test") String test) {
        return test;
    }
    @RequestMapping("/testAuth/{test}")
    public Object testAuth(HttpServletRequest request, @PathVariable("test") String test) {
        return sysAuthByRoleMatcher.getPathConfig(request);
    }


    @GetMapping("/test1")
    public Object test1() {
        throw new RuntimeException();
    }

    @GetMapping("/test2")
    public Object test2() {
        throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
    }

}

package com.frame.user.controller;

import com.frame.user.service.TestService;
import com.frame.user.shiro.SysAuthMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private SysAuthMatcher sysAuthByRoleMatcher;

    @RequestMapping("/test/{test}")
    public Object test(@PathVariable("test") String test) {
        return testService.test(test);
    }
    @RequestMapping("/test1")
    public Object test1() {
        testService.test1("test1");
        return "test1";
    }
    @RequestMapping("/test22")
    public Object test22() {
        testService.test22("test22");
        return "test22";
    }

    @RequestMapping("/testPath/{test}")
    public Object testPath(@PathVariable("test") String test) {
        testService.test22(test);
        return test;
    }
    @RequestMapping("/testAuth/{test}")
    public Object testAuth(HttpServletRequest request, @PathVariable("test") String test) {
        return sysAuthByRoleMatcher.getPathConfig(request);
    }

}

package com.frame.controller;

import com.frame.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public Object test() {
        testService.test("test");
        return "test";
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

}

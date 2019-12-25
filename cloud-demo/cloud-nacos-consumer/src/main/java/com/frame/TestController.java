package com.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    TestRemoteService testRemoteService;

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name) throws InterruptedException {
        return testRemoteService.hello(name);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam("name") String name) {
        return testRemoteService.test(name);
    }

    @RequestMapping(value = "/testProperties", method = RequestMethod.GET)
    public Object testProperties() {
        return testRemoteService.testProperties();
    }
}

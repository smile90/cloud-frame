package com.frame;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class TestController {

    @Autowired
    private TestProperties testProperties;

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name) throws InterruptedException {
        Thread.sleep(1000L);
        return "Hello " + name;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam("name") String name) {
        return "test " + name;
    }

    @RequestMapping(value = "/testProperties", method = RequestMethod.GET)
    public Object testProperties() {
        return testProperties.toString();
    }
}

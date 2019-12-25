package com.frame;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestDubboController {

    @Reference(version = "1.0.0", protocol = "dubbo")
    private TestDubboService testDubboService;

    @RequestMapping("/test/{name}/{number}/{json}")
    public Object test1(@PathVariable("name") String name, @PathVariable("number") String number, @PathVariable("json") JSONObject json) {
        log.info("{},{},{}", name, number, json);
        testDubboService.hello(name, Integer.valueOf(number), json);
        return getTestBean();
    }
    @RequestMapping("/test/{name}/{number}")
    public Object test2(@PathVariable("name") String name, @PathVariable("number") String number) {
        log.info("{},{}", name, number);
        testDubboService.hello(name, Integer.valueOf(number));
        return getTestBean();
    }
    @RequestMapping("/test/{name}")
    public Object test3(@PathVariable("name") String name) {
        log.info("{},{}", name);
        testDubboService.hello(name);
        return getTestBean();
    }

    private TestBean getTestBean() {
        Test2Bean test2Bean = new Test2Bean();
        test2Bean.setTestInt(20);
        test2Bean.setTestStr("test2");
        TestBean testBean = new TestBean();
        testBean.setTestInt(20);
        testBean.setTestStr("test2");
        testBean.setTest2Bean(test2Bean);
        return testBean;
    }
}

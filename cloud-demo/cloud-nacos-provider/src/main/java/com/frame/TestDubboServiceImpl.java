package com.frame;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@Service(protocol = "dubbo")
public class TestDubboServiceImpl implements TestDubboService {

    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public String hello(String name, int number) {
        return "Hello " + name + " " + number;
    }

    @Override
    public String hello(String name, int number, JSONObject json) {
        return "Hello " + name + " " + number + " " + json;
    }
}

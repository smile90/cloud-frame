package com.frame;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface TestDubboService {

    String hello(String name);
    String hello(String name, int number);
    String hello(String name, int number, JSONObject json);
}

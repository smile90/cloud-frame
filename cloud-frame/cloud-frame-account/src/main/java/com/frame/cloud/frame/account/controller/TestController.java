package com.frame.cloud.frame.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frame.boot.base.utils.DateUtil;
import com.frame.cloud.frame.account.entity.Test;
import com.frame.cloud.frame.account.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test1")
    public Object test1() {
        return testService.list();
    }
    @RequestMapping("/test2")
    public Object test2(String begin, String end) throws ParseException {
        return testService.list(new QueryWrapper<Test>().between("count_date",
                DateUtil.parseDate(begin, DateUtil.FORMAT_yyyyMMdd),
                DateUtil.parseDate(end, DateUtil.FORMAT_yyyyMMdd)));
    }
    @RequestMapping("/test3")
    public Object test3(String id) throws ParseException {
        return testService.list(new QueryWrapper<Test>().eq("id", id));
    }
}

package com.frame.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchData;
import com.frame.mybatis.search.SearchType;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/user")
    public Object user(Principal user) {
        return user;
    }

    @RequestMapping("/test/{test}")
    public Object test(@PathVariable("test") String test) {
        return test;
    }

    @RequestMapping("/testPath/{test}")
    public Object testPath(@PathVariable("test") String test) {
        return test;
    }

    @GetMapping("/test1")
    public Object test1() {
        throw new RuntimeException();
    }

    @GetMapping("/test2")
    public Object test2() {
        throw new AuthException(AuthMsgResult.VALID_CODE_ERROR);
    }

    @GetMapping("/test3")
    public Object test3() {
        QueryWrapper qw = new QueryWrapper();
        qw.in("user_status", Lists.newArrayList("DISABLED", "NORMAL", "LOCKED"));
        return sysUserService.page(new Page<>(), qw);
    }

    @GetMapping("/test4")
    public Object test4() {
        SearchData searchData = new SearchData("user_status", SearchType.IN, null, ",LOCKED, DISABLED, NORMAL");
        SearchBuilder builder = new SearchBuilder<SysUser>().build(searchData);
        return sysUserService.page(new Page<>(), builder.build());
    }

}

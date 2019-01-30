package com.frame.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchType;
import com.frame.mybatis.search.ValueType;
import com.frame.user.entity.SysUser;
import com.frame.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: duanchangqing90
 * @date: 2019/01/30
 */
@RestController
@RequestMapping("/adminUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @InitBinder("page")
    public void initUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page.");
    }

    @GetMapping("/listPage")
    public Object listPage(@ModelAttribute("page") Page<SysUser> page, @RequestParam Map<String,String> map) {
        SearchBuilder<SysUser> builder = new SearchBuilder<SysUser>()
            .build("user_id", SearchType.EQ, ValueType.STRING, map.get("userId"))
            .build("user_no", SearchType.EQ, ValueType.STRING, map.get("userNo"))
            .build("username", SearchType.EQ, ValueType.STRING, map.get("username"))
            .build("realname", SearchType.LIKE, ValueType.STRING, map.get("realname"));
        return ResponseBean.successContent(sysUserService.page(page, builder.build()));
    }

    @GetMapping("/get/{id}")
    public Object one(@PathVariable("id") String id) {
        return ResponseBean.successContent(sysUserService.getById(id));
    }

}

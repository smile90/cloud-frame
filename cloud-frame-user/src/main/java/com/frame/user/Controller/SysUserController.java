package com.frame.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchType;
import com.frame.mybatis.search.ValueType;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.UserMsgResult;
import com.frame.user.properties.UserProperties;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * @author: duanchangqing90
 * @date: 2019/01/30
 */
@Slf4j
@RestController
@RequestMapping("/adminUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserProperties userProperties;

    @InitBinder("page")
    public void initUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page.");
    }

    @GetMapping("/listPage")
    public Object listPage(@ModelAttribute("page") Page<SysUser> page, @RequestParam Map<String,String> map) {
        SearchBuilder builder = new SearchBuilder<SysUser>()
            .build("user_id", SearchType.EQ, ValueType.STRING, map.get("userId"))
            .build("user_no", SearchType.EQ, ValueType.STRING, map.get("userNo"))
            .build("username", SearchType.EQ, ValueType.STRING, map.get("username"))
            .build("realname", SearchType.LIKE, ValueType.STRING, map.get("realname"))
            .build("user_status", SearchType.IN, ValueType.STRING, map.get("userStatus"))
            .build("status", SearchType.EQ, ValueType.STRING, map.get("status"));
        return ResponseBean.successContent(sysUserService.page(page, builder.build()));
    }

    @GetMapping("/get/{id}")
    public Object one(@PathVariable("id") String id) {
        return ResponseBean.successContent(sysUserService.getById(id));
    }

    @PostMapping("/save")
    public Object save(SysUser bean) {
        try {
            bean.setUserId(UUID.randomUUID().toString());
            bean.setPassword(userProperties.getDefaultPwd());
            sysUserService.save(bean);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("save error. bean:{}", bean, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }
}

package com.frame.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.boot.mybatis.search.SearchBuilder;
import com.frame.boot.mybatis.search.SearchType;
import com.frame.boot.mybatis.search.ValueType;
import com.frame.boot.spring.validate.DefaultGroup;
import com.frame.boot.spring.validate.SaveGroup;
import com.frame.boot.spring.validate.UpdateGroup;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.boot.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Slf4j
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
    public Object initPage(@ModelAttribute("page") Page<SysUser> page, @RequestParam Map<String, String> map) {
        SearchBuilder builder = new SearchBuilder<SysUser>()
                .build("user_id", SearchType.EQ, ValueType.STRING, map.get("userId"))
                .build("user_no", SearchType.EQ, ValueType.STRING, map.get("userNo"))
                .build("username", SearchType.EQ, ValueType.STRING, map.get("username"))
                .build("realname", SearchType.LIKE, ValueType.STRING, map.get("realname"))
                .build("user_status", SearchType.IN, ValueType.STRING, map.get("userStatus"))
                .build("status", SearchType.IN, ValueType.STRING, map.get("status"));
        return ResponseBean.successContent(sysUserService.page(page, builder.build()));
    }

    @GetMapping("/get/{id}")
    public Object one(@PathVariable("id") String id) {
        return ResponseBean.successContent(sysUserService.getById(id));
    }

    @GetMapping("/usernameExist/{code}")
    public Object usernameExist(@PathVariable("code") String code, @RequestParam(required = false) String id) {
        QueryWrapper qw = new QueryWrapper<SysUser>()
                .eq("username", code)
                .notIn("user_status", UserStatus.DELETED);
        if (StringUtils.hasText(id)) {
            qw.ne("id", id);
        }
        int count = sysUserService.count(qw);
        return count > 0 ? ResponseBean.successContent(true) : ResponseBean.successContent(false);
    }

    @GetMapping("/phoneNoExist/{code}")
    public Object phoneNoExist(@PathVariable("code") String code, @RequestParam(required = false) String id) {
        QueryWrapper qw = new QueryWrapper<SysUser>()
                .eq("phone_no", code)
                .notIn("user_status", UserStatus.DELETED);
        if (StringUtils.hasText(id)) {
            qw.ne("id", id);
        }
        int count = sysUserService.count(qw);
        return count > 0 ? ResponseBean.successContent(true) : ResponseBean.successContent(false);
    }

    @GetMapping("/emailExist/{code}")
    public Object emailExist(@PathVariable("code") String code, @RequestParam(required = false) String id) {
        QueryWrapper qw = new QueryWrapper<SysUser>()
                .eq("email", code)
                .notIn("user_status", UserStatus.DELETED);
        if (StringUtils.hasText(id)) {
            qw.ne("id", id);
        }
        int count = sysUserService.count(qw);
        return count > 0 ? ResponseBean.successContent(true) : ResponseBean.successContent(false);
    }

    @PostMapping("/save")
    public Object save(@Validated({SaveGroup.class, DefaultGroup.class}) SysUser bean) {
        sysUserService.save(bean);
        return ResponseBean.success();
    }

    @PostMapping("/update/{id}")
    public Object update(@PathVariable("id") String id, @Validated({UpdateGroup.class, DefaultGroup.class}) SysUser bean) {
        SysUser entity = sysUserService.getById(id);
        if (entity != null) {
            entity.setUserNo(bean.getUserNo());
            entity.setUsername(bean.getUsername());
            entity.setRealname(bean.getRealname());
            entity.setTypeCode(bean.getTypeCode());
            entity.setPhoneNo(bean.getPhoneNo());
            entity.setEmail(bean.getEmail());
            entity.setDescription(bean.getDescription());
            entity.setUserStatus(bean.getUserStatus());
            entity.setStatus(bean.getStatus());
            sysUserService.updateById(entity);
        }
        return ResponseBean.success();
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id) {
        sysUserService.deleteById(id);
        return ResponseBean.success();
    }

    @DeleteMapping("/delete")
    public Object delete(@RequestParam("ids[]") String[] ids) {
        sysUserService.deleteByIds(Arrays.asList(ids));
        return ResponseBean.success();
    }
}

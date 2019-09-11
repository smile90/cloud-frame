package com.frame.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchType;
import com.frame.mybatis.search.ValueType;
import com.frame.mybatis.validate.DefaultGroup;
import com.frame.mybatis.validate.SaveGroup;
import com.frame.mybatis.validate.UpdateGroup;
import com.frame.user.entity.SysRole;
import com.frame.user.enums.UserMsgResult;
import com.frame.user.service.SysRoleService;
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
@RequestMapping("/adminRole")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @InitBinder("page")
    public void initPage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page.");
    }

    @GetMapping("/listPage")
    public Object listPage(@ModelAttribute("page") Page<SysRole> page, @RequestParam Map<String,String> map) {
        SearchBuilder builder = new SearchBuilder<SysRole>()
            .build("code", SearchType.EQ, ValueType.STRING, map.get("code"))
            .build("name", SearchType.LIKE, ValueType.STRING, map.get("name"))
            .build("useable", SearchType.EQ, ValueType.STRING, map.get("useable"));
        return ResponseBean.successContent(roleService.page(page, builder.build()));
    }

    @GetMapping("/get/{id}")
    public Object one(@PathVariable("id") String id) {
        return ResponseBean.successContent(roleService.getById(id));
    }

    @GetMapping("/codeExist/{code}")
    public Object codeExist(@PathVariable("code") String code, @RequestParam(required = false) String id) {
        QueryWrapper qw = new QueryWrapper<SysRole>()
                .eq("code", code);
        if (StringUtils.hasText(id)) {
            qw.ne("id", id);
        }
        int count = roleService.count(qw);
        return count > 0 ? ResponseBean.successContent(true) : ResponseBean.successContent(false);
    }

    @GetMapping("/nameExist/{name}")
    public Object nameExist(@PathVariable("name") String name, @RequestParam(required = false) String id) {
        QueryWrapper qw = new QueryWrapper<SysRole>()
                .eq("name", name);
        if (StringUtils.hasText(id)) {
            qw.ne("id", id);
        }
        int count = roleService.count(qw);
        return count > 0 ? ResponseBean.successContent(true) : ResponseBean.successContent(false);
    }

    @PostMapping("/save")
    public Object save(@Validated({SaveGroup.class, DefaultGroup.class}) SysRole bean) {
        roleService.save(bean);
        return ResponseBean.success();
    }

    @PostMapping("/update/{id}")
    public Object update(@PathVariable("id") String id, @Validated({UpdateGroup.class, DefaultGroup.class}) SysRole bean) {
        SysRole entity = roleService.getById(id);
        if (entity != null) {
            entity.setCode(bean.getCode());
            entity.setTypeCode(bean.getTypeCode());
            entity.setName(bean.getName());
            entity.setUseable(bean.getUseable());
            entity.setDescription(bean.getDescription());
            entity.setStatus(bean.getStatus());
            roleService.updateById(entity);
        }
        return ResponseBean.success();
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id) {
        roleService.deleteById(id);
        return ResponseBean.success();
    }

    @DeleteMapping("/delete")
    public Object delete(@RequestParam("ids[]") String[] ids) {
        roleService.deleteByIds(Arrays.asList(ids));
        return ResponseBean.success();
    }
}

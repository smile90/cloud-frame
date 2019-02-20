package com.frame.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchType;
import com.frame.mybatis.search.ValueType;
import com.frame.user.entity.SysRole;
import com.frame.user.enums.UserMsgResult;
import com.frame.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void initUser(WebDataBinder binder) {
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

    @PostMapping("/save")
    public Object save(SysRole bean) {
        try {
            roleService.save(bean);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("save error. bean:{}", bean, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public Object update(@PathVariable("id") String id, SysRole bean) {
        try {
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
        } catch (Exception e) {
            log.error("update error. id:{},bean:{}", id, bean, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id) {
        try {
            roleService.deleteById(id);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("delete error. id:{}", id, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public Object delete(@RequestParam("ids[]") String[] ids) {
        try {
            roleService.deleteByIds(Arrays.asList(ids));
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("delete error. ids:{}", ids, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }

    }
}

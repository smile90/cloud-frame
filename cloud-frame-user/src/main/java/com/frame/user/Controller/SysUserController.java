package com.frame.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.mybatis.search.SearchBuilder;
import com.frame.mybatis.search.SearchType;
import com.frame.mybatis.search.ValueType;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.UserMsgResult;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

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
            sysUserService.save(bean);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("save error. bean:{}", bean, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public Object update(@PathVariable("id") String id, SysUser bean) {
        try {
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
        } catch (Exception e) {
            log.error("update error. id:{},bean:{}", id, bean, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id) {
        try {
            sysUserService.deleteById(id);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("delete error. id:{}", id, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public Object delete(@RequestParam("ids[]") String[] ids) {
        try {
            sysUserService.deleteByIds(Arrays.asList(ids));
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("delete error. ids:{}", ids, e);
            return ResponseBean.getInstance(UserMsgResult.SYSTEM_ERROR);
        }

    }
}

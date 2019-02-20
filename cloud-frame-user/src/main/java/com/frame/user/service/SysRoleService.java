package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.client.BossAuthUtil;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.entity.SysUserRole;
import com.frame.user.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Autowired
    private SysRoleModuleService sysRoleModuleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    public SysRole find(String roleCode, YesNo useable) {
        QueryWrapper<SysRole> query = new QueryWrapper<SysRole>().eq("code", roleCode);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return getOne(query);
    }

    public List<SysRole> find(Collection roleCodes, YesNo useable) {
        QueryWrapper<SysRole> query = new QueryWrapper<SysRole>().in("code", roleCodes);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return list(query);
    }

    public List<SysRole> findByUsername(String username, YesNo useable) {
        List<SysRole> roles = new ArrayList<>();
        List<SysUserRole> sysUserRoles = sysUserRoleService.findByUsername(username);
        if (sysUserRoles != null && !sysUserRoles.isEmpty()) {
            Set<String> roleCodes = sysUserRoles.stream()
                    .map(SysUserRole::getRoleCode)
                    .collect(Collectors.toSet());
            roles = find(roleCodes, useable);
        }
        return roles;
    }

    public List<SysRole> findByModuleCode(String moduleCode, YesNo useable) {
        List<SysRole> roles = new ArrayList<>();
        List<SysRoleModule> sysRoleModules = sysRoleModuleService.findByModuleCode(moduleCode);
        if (sysRoleModules != null && !sysRoleModules.isEmpty()) {
            Set<String> roleCodes = sysRoleModules.stream()
                    .map(SysRoleModule::getRoleCode)
                    .collect(Collectors.toSet());
            roles = find(roleCodes, useable);
        }
        return roles;
    }

    public List<SysRole> findByModuleCode(Collection moduleCodes, YesNo useable) {
        List<SysRole> roles = new ArrayList<>();
        List<SysRoleModule> sysRoleModules = sysRoleModuleService.findByModuleCode(moduleCodes);
        if (sysRoleModules != null && !sysRoleModules.isEmpty()) {
            Set<String> roleCodes = sysRoleModules.stream()
                    .map(SysRoleModule::getRoleCode)
                    .collect(Collectors.toSet());
            roles = find(roleCodes, useable);
        }
        return roles;
    }


    @Override
    public boolean save(SysRole entity) {
        entity.setCreateTime(new Date());
        entity.setCreateUser(BossAuthUtil.getUsername());
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysRole entity) {
        entity.setUpdateTime(new Date());
        entity.setUpdateUser(BossAuthUtil.getUsername());
        return super.updateById(entity);
    }

    public boolean deleteById(Serializable id) {
        SysRole entity = getById(id);
        if (entity != null) {
            sysRoleModuleService.remove(new QueryWrapper<SysRoleModule>().eq("role_code", entity.getCode()));
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("role_code", entity.getCode()));
            return super.removeById(id);
        } else {
            return false;
        }
    }

    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        Collection<SysRole> entities = listByIds(idList);
        if (entities != null && !entities.isEmpty()) {
            List<String> roleCodes = entities.stream().map(entity -> entity.getCode()).collect(Collectors.toList());
            sysRoleModuleService.remove(new QueryWrapper<SysRoleModule>().in("role_code", roleCodes));
            sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_code", roleCodes));
            return super.removeByIds(idList);
        } else {
            return false;
        }
    }

}

package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.entity.SysUserRole;
import com.frame.user.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

}

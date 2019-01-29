package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.mapper.SysModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysModuleService extends ServiceImpl<SysModuleMapper, SysModule> {

    @Autowired
    private SysRoleModuleService sysRoleModuleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Cacheable(value = "user:modules:moduleCode", key = "#moduleCode + '_' + #useable")
    public SysModule find(String moduleCode, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysModule>().eq("code", moduleCode);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return getOne(query);
    }

    @Cacheable(value = "user:modules:moduleCode", key = "#moduleCodes + '_' + #useable")
    public List<SysModule> find(Collection moduleCodes, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysModule>().in("code", moduleCodes);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return list(query);
    }

    @Cacheable(value = "user:modules:roleCode", key = "#roleCode + '_' + #useable")
    public List<SysModule> findByRoleCode(String roleCode, YesNo useable) {
        List<SysRoleModule> sysRoleModules = Optional.ofNullable(sysRoleModuleService.findByRoleCode(roleCode))
                                                .orElse(Collections.emptyList());
        Set<String> moduleCodes = sysRoleModules.stream().map(SysRoleModule::getModuleCode).collect(Collectors.toSet());
        if (moduleCodes != null && !moduleCodes.isEmpty()) {
            return find(moduleCodes, useable);
        } else {
            return null;
        }
    }

    @Cacheable(value = "user:modules:roleCode", key = "#roleCodes + '_' + #useable")
    public List<SysModule> findByRoleCode(Collection roleCodes, YesNo useable) {
        List<SysRoleModule> sysRoleModules = Optional.ofNullable(sysRoleModuleService.findByRoleCode(roleCodes))
                .orElse(Collections.emptyList());
        Set<String> moduleCodes = sysRoleModules.stream().map(SysRoleModule::getModuleCode).collect(Collectors.toSet());
        if (moduleCodes != null && !moduleCodes.isEmpty()) {
            return find(moduleCodes, useable);
        } else {
            return null;
        }
    }

    @Cacheable(value = "user:modules:username", key = "#username + '_' + #useable")
    public List<SysModule> findByUsername(String username, YesNo useable) {
        List<SysRole> roles = sysRoleService.findByUsername(username, useable);
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return findByRoleCode(roles.stream().map(SysRole::getCode).collect(Collectors.toList()), useable);
    }
}

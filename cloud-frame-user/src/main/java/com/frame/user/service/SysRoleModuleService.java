package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.mapper.SysRoleModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
class SysRoleModuleService extends ServiceImpl<SysRoleModuleMapper, SysRoleModule> {

    @Cacheable(value = "user:roleModules", key = "'allRoleModules'")
    public List<SysRoleModule> findAll() {
        return list();
    }

    @Cacheable(value = "user:roleModules:moduleCode", key = "#moduleCode")
    public List<SysRoleModule> findByModuleCode(String moduleCode) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().eq("module_code", moduleCode));
    }

    @Cacheable(value = "user:roleModules:moduleCode", key = "#moduleCodes")
    public List<SysRoleModule> findByModuleCode(Collection<String> moduleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().in("module_code", moduleCodes));
    }

    @Cacheable(value = "user:roleModules:roleCode", key = "#roleCode")
    public List<SysRoleModule> findByRoleCode(String roleCode) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().eq("role_code", roleCode));
    }

    @Cacheable(value = "user:roleModules:roleCode", key = "#roleCodes")
    public List<SysRoleModule> findByRoleCode(Collection<String> roleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().in("role_code", roleCodes));
    }
}

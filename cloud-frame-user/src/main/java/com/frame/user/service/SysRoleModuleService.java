package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.mapper.SysRoleModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
class SysRoleModuleService extends ServiceImpl<SysRoleModuleMapper, SysRoleModule> {

    public List<SysRoleModule> findAll() {
        return list();
    }

    public List<SysRoleModule> findByModuleCode(String moduleCode) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().eq("module_code", moduleCode));
    }

    public List<SysRoleModule> findByModuleCode(Collection<String> moduleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().in("module_code", moduleCodes));
    }

    public List<SysRoleModule> findByRoleCode(String roleCode) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().eq("role_code", roleCode));
    }

    public List<SysRoleModule> findByRoleCode(Collection<String> roleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysRoleModule>().in("role_code", roleCodes));
    }
}

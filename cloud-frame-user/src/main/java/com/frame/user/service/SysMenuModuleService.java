package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysMenuModule;
import com.frame.user.mapper.SysMenuModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
class SysMenuModuleService extends ServiceImpl<SysMenuModuleMapper, SysMenuModule> {

    public List<SysMenuModule> findAll() {
        return list();
    }

    public List<SysMenuModule> findByModuleCode(String moduleCode) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().eq("module_code", moduleCode));
    }

    public List<SysMenuModule> findByModuleCode(Collection<String> moduleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().in("module_code", moduleCodes));
    }

    public List<SysMenuModule> findByMenuCode(String menuCode) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().eq("menu_code", menuCode));
    }

    public List<SysMenuModule> findByMenuCode(Collection<String> menuCodes) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().in("menu_code", menuCodes));
    }
}

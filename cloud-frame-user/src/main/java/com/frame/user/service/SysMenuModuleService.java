package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysMenuModule;
import com.frame.user.entity.SysMenuModule;
import com.frame.user.mapper.SysMenuModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
class SysMenuModuleService extends ServiceImpl<SysMenuModuleMapper, SysMenuModule> {

    @Cacheable(value = "user:menuModules", key = "'allMenuModules'")
    public List<SysMenuModule> findAll() {
        return list();
    }

    @Cacheable(value = "user:menuModules:moduleCode", key = "#moduleCode")
    public List<SysMenuModule> findByModuleCode(String moduleCode) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().eq("module_code", moduleCode));
    }

    @Cacheable(value = "user:menuModules:moduleCode", key = "#moduleCodes")
    public List<SysMenuModule> findByModuleCode(Collection<String> moduleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().in("module_code", moduleCodes));
    }

    @Cacheable(value = "user:menuModules:menuCode", key = "#menuCode")
    public List<SysMenuModule> findByMenuCode(String menuCode) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().eq("menu_code", menuCode));
    }

    @Cacheable(value = "user:menuModules:menuCode", key = "#menuCodes")
    public List<SysMenuModule> findByMenuCode(Collection<String> menuCodes) {
        return baseMapper.selectList(new QueryWrapper<SysMenuModule>().in("menu_code", menuCodes));
    }
}

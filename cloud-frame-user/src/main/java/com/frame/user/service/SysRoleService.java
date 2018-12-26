package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysRole;
import com.frame.user.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    @Cacheable(value = "roles", key = "#roleCode")
    public SysRole find(String roleCode) {
        return baseMapper.selectOne(new QueryWrapper<SysRole>().eq("code", roleCode));
    }

    @Cacheable(value = "roles", key = "#roleCodes")
    public List<SysRole> find(Collection roleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysRole>().in("module_code", roleCodes));
    }

}

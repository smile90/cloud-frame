package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysUserRole;
import com.frame.user.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

    @Cacheable(value = "user:userRoles", key = "#username")
    public List<SysUserRole> findByUsername(String username) {
        return baseMapper.selectList(new QueryWrapper<SysUserRole>()
                .eq("username", username));
    }

}

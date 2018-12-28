package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.DataStatus;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.entity.SysUser;
import com.frame.user.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Cacheable(value = "user", key = "#username", unless="#result == null")
    public SysUser findByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username).eq("status", DataStatus.NORMAL.name()));
    }
}

package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.DataStatus;
import com.frame.user.client.UserUtil;
import com.frame.user.entity.SysUser;
import com.frame.user.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Cacheable(value = "user:user", key = "#username", unless="#result == null")
    public SysUser findByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username).eq("status", DataStatus.NORMAL.name()));
    }

    @Override
    public boolean save(SysUser entity) {
        entity.setCreateTime(new Date());
        entity.setCreateUser(UserUtil.getBossUsername());
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysUser entity) {
        entity.setUpdateTime(new Date());
        entity.setUpdateUser(UserUtil.getBossUsername());
        return super.updateById(entity);
    }
}

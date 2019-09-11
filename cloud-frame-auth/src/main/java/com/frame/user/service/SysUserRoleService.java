package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysUserRole;
import com.frame.user.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

    public List<SysUserRole> findByUserId(String userId) {
        return baseMapper.selectList(new QueryWrapper<SysUserRole>()
                .eq("user_id", userId));
    }

}

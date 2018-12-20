package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.EmptyWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.mapper.SysRoleModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysRoleModuleService extends ServiceImpl<SysRoleModuleMapper, SysRoleModule> {

    @Cacheable(value = "modules", key = "'allRoleModules'")
    public List<SysRoleModule> findAllRoleModule() {
        return baseMapper.selectList(new EmptyWrapper<>());
    }
}

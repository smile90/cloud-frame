package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.user.entity.SysModule;
import com.frame.user.mapper.SysModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class SysModuleService extends ServiceImpl<SysModuleMapper, SysModule> {

    @Cacheable(value = "modules", key = "#moduleCode")
    public SysModule find(String moduleCode) {
        return baseMapper.selectOne(new QueryWrapper<SysModule>().eq("code", moduleCode));
    }

    @Cacheable(value = "modules", key = "#moduleCodes")
    public List<SysModule> find(Collection moduleCodes) {
        return baseMapper.selectList(new QueryWrapper<SysModule>().in("code", moduleCodes));
    }
}

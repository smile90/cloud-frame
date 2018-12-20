package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.EmptyWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.entity.SysFunction;
import com.frame.user.mapper.SysFunctionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysFunctionService extends ServiceImpl<SysFunctionMapper, SysFunction> {

    /**
     * 查询所有启用的功能（可用 并且 需要验证）
     * @return
     */
    @Cacheable(value = "functions", key = "'allEnableFunctions'")
    public List<SysFunction> findAllEnableFunction() {
        return baseMapper.selectList(new EmptyWrapper<SysFunction>()
                .eq("useable", YesNo.Y.name()).eq("validate", YesNo.Y.name()));
    }

    /**
     * 查询所有禁用的功能
     * @return
     */
    @Cacheable(value = "functions", key = "'allDisableFunctions'")
    public List<SysFunction> findAllDisableFunction() {
        return baseMapper.selectList(new EmptyWrapper<SysFunction>()
                .eq("useable", YesNo.N.name()));
    }

    /**
     * 查询所有功能
     * @return
     */
    @Cacheable(value = "functions", key = "'allFunctions'")
    public List<SysFunction> findAllFunction() {
        return baseMapper.selectList(new EmptyWrapper<>());
    }

}

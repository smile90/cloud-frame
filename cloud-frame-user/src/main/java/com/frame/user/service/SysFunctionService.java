package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * 查询所有禁用的功能
     *
     * @return
     */
    @Cacheable(value = "user:functions", key = "'allDisableFunctions'")
    public List<SysFunction> findAllDisable() {
        return list(new QueryWrapper<SysFunction>()
                .eq("useable", YesNo.N.name()));
    }

    /**
     * 查询所有启用的功能
     *
     * @return
     */
    @Cacheable(value = "user:functions", key = "'allEnableFunctions_' + #validate")
    public List<SysFunction> findAllEnable(YesNo validate) {
        return list(new QueryWrapper<SysFunction>()
                .eq("useable", YesNo.Y.name())
                .eq("validate", validate));
    }
}

package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.entity.SysFunction;
import com.frame.user.mapper.SysFunctionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class SysFunctionService extends ServiceImpl<SysFunctionMapper, SysFunction> {

    /**
     * 查询所有禁用的功能
     *
     * @return
     */
    public List<SysFunction> findAllDisable() {
        return list(new QueryWrapper<SysFunction>()
                .eq("useable", YesNo.N.name()));
    }

    /**
     * 查询所有启用的功能
     * @return
     */
    public List<SysFunction> findAllEnable(YesNo validate) {
        QueryWrapper query = new QueryWrapper<SysFunction>()
                .eq("useable", YesNo.Y.name());
        if (validate != null) {
            query.eq("validate", validate);
        }
        return list(query);
    }

    public List<SysFunction> findByModuleCode(String moduleCode, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysFunction>().eq("module_code", moduleCode);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return list(query);
    }

    public List<SysFunction> findByModuleCode(Collection moduleCodes, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysFunction>().in("module_code", moduleCodes);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return list(query);
    }
}

package com.frame.user.shiro;

import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.constant.RoleKeyConstant;
import com.frame.user.entity.SysFunction;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysRoleModule;
import com.frame.user.service.SysFunctionService;
import com.frame.user.service.SysModuleService;
import com.frame.user.service.SysRoleModuleService;
import com.frame.user.service.SysRoleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统权限基于角色匹配
 * @author: duanchangqing90
 * @date: 2018/12/19
 */
@Slf4j
@Service
public class SysAuthByRoleMatcher implements SysAuthMatcher {

    @Setter
    protected PathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleModuleService sysRoleModuleService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysFunctionService sysFunctionService;

    /**
     * 配置路径权限
     */
    @Override
    public void processPathConfig() {
        // TODO
        log.info("processPathConfig");
    }

    @Override
    public String[] getPathConfig(ServletRequest request) {
        String path = getPathWithinApplication(request);
        String method = getHttpMethodWithinApplication(request);

        // 如果是禁用请求，只有超级管理员可以访问
        List<SysFunction> disableFunctions = sysFunctionService.findAllDisable();
        disableFunctions = Optional.ofNullable(disableFunctions).orElse(Collections.emptyList());
        boolean isDisableFunction = disableFunctions.stream()
                .anyMatch(f -> match(f, method, path));
        if (isDisableFunction) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}", path, isDisableFunction);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }

        // 如果是启用并无需验证请求
        List<SysFunction> enableNoValidateFunctions = Optional.ofNullable(sysFunctionService.findAllEnable(YesNo.N)).orElse(Collections.emptyList());
        boolean isEnableNoValidateFunction = enableNoValidateFunctions.stream()
                .anyMatch(f -> match(f, method, path));
        if (isEnableNoValidateFunction) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}", path, isDisableFunction, isEnableNoValidateFunction);
            return new String[0];
        }

        // 如果是启用并需要验证请求，获取对应角色
        List<SysFunction> enableValidateFunction = Optional.ofNullable(sysFunctionService.findAllEnable(YesNo.Y)).orElse(Collections.emptyList());
        Set<String> functionModules = enableValidateFunction.stream()
                .filter(f -> match(f, method, path))
                .map(s -> s.getModuleCode())
                .collect(Collectors.toSet());
        // 未查询到对应功能，说明未配置，只允许管理员访问
        if (functionModules == null || functionModules.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, functionModules:{}", path, isDisableFunction, isEnableNoValidateFunction, functionModules);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }
        // 未查询到对应模块，说明未配置，只允许管理员访问
        List<SysModule> modules = sysModuleService.find(functionModules);
        if (modules == null || modules.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, functionModules:{}, modules:{}", path, isDisableFunction, isEnableNoValidateFunction, functionModules, modules);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }

        // 模块如果禁用，只有超级管理员可以访问
        boolean isDisableModule = modules.stream()
                .anyMatch(m -> !YesNo.Y.equals(m.getUseable()));
        if (isDisableModule) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, functionModules:{}, modules:{}", path, isDisableFunction, isEnableNoValidateFunction, isDisableModule, functionModules, modules);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }

        // 未查询到对应角色，说明未配置，只有超级管理员可以访问；如果配置了，返回对应角色
        List<SysRoleModule> sysRoleModules = sysRoleModuleService.find(functionModules);
        if (sysRoleModules == null || sysRoleModules.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, functionModules:{}, modules:{}, sysRoleModules:{}", path, isDisableFunction, isEnableNoValidateFunction, isDisableModule, functionModules, modules, sysRoleModules);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }
        List<SysRole> roles = sysRoleService.find(sysRoleModules.stream().map(SysRoleModule::getRoleCode).collect(Collectors.toList()));
        if (roles == null || roles.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, functionModules:{}, modules:{}, sysRoleModules:{}, roles:{}", path, isDisableFunction, isEnableNoValidateFunction, isDisableModule, functionModules, modules, sysRoleModules, roles);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }
        Set<String> enableRoles = roles.stream().filter(m -> !YesNo.Y.equals(m.getUseable()))
                .map(SysRole::getCode).collect(Collectors.toSet());
        if (enableRoles == null || enableRoles.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, enableRoles:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, functionModules:{}, modules:{}, sysRoleModules:{}, roles:{}", enableRoles, path, isDisableFunction, isEnableNoValidateFunction, isDisableModule, functionModules, modules, sysRoleModules, roles);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        } else {
            log.debug("getPathConfig end. method:{}, path:{}, enableRoles:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, functionModules:{}, modules:{}, sysRoleModules:{}, roles:{}", enableRoles, path, isDisableFunction, isEnableNoValidateFunction, isDisableModule, functionModules, modules, sysRoleModules, roles);
            return enableRoles.toArray(new String[0]);
        }
    }

    protected boolean match(SysFunction function, String method, String url) {
        if (function == null) {
            return false;
        } else {
            return method.equalsIgnoreCase(function.getHttpMethod()) && pathMatcher.match(function.getUrl(), url);
        }
    }

    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }
    protected String getHttpMethodWithinApplication(ServletRequest request) {
        return ((HttpServletRequest) request).getMethod();
    }
}

package com.frame.user.auth.matcher;

import com.frame.common.frame.base.enums.YesNo;
import com.frame.user.auth.resource.HttpResource;
import com.frame.user.auth.resource.Resource;
import com.frame.user.auth.util.WebUtil;
import com.frame.user.constant.RoleKeyConstant;
import com.frame.user.entity.SysFunction;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.properties.AuthProperties;
import com.frame.user.service.SysFunctionService;
import com.frame.user.service.SysModuleService;
import com.frame.user.service.SysRoleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
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
    private AuthProperties authProperties;

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysFunctionService sysFunctionService;

    @Override
    public boolean matchPermitPath(Resource resource) {
        Optional.ofNullable(resource).orElseThrow(() -> new AuthException(AuthMsgResult.NOT_AUTH_ERROR));
        if (!(resource instanceof HttpResource)) {
            throw new AuthException(AuthMsgResult.NOT_SUPPORT_AUTH_ERROR);
        }

        String method = ((HttpResource) resource).getMethod();
        String url = ((HttpResource) resource).getUrl();

        AuthProperties.Url authUrl = authProperties.getUrl();
        // 无需校验
        if (Arrays.stream(authUrl.getPermitPaths().split(",")).anyMatch(u -> pathMatcher.match(u.trim(), url))) {
            log.debug("method:{},url:{} is permit path.", method, url);
            return true;
            // 需要校验
        } else if (Arrays.stream(authUrl.getAuthenticatePaths().split(",")).anyMatch(u -> pathMatcher.match(u.trim(), url))) {
            log.debug("method:{},url:{} is authenticate path.", method, url);
            return false;
            // 未配置
        } else {
            log.debug("method:{},url:{} is not config path.", method, url);
            return true;
        }
    }

    @Override
    public String[] getPathConfig(ServletRequest request) {
        String path = getPathWithinApplication(request);
        String method = getHttpMethodWithinApplication(request);
        return getPathConfig(method, path);
    }

    @Override
    public String[] getPathConfig(String method, String path) {
        // 请求如果禁用，只有超级管理员可以访问
        List<SysFunction> disableFunctions = Optional.ofNullable(sysFunctionService.findAllDisable()).orElse(Collections.emptyList());
        boolean isDisableFunction = disableFunctions.stream()
                .anyMatch(f -> match(f, method, path));
        if (isDisableFunction) {
            log.debug("getPathConfig end. method:{}, path:{}, isDisableFunction:{}", method, path, isDisableFunction);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }

        // 如果是启用并无需验证请求，不需要角色
        List<SysFunction> enableNoValidateFunctions = Optional.ofNullable(sysFunctionService.findAllEnable(YesNo.N)).orElse(Collections.emptyList());
        boolean isEnableNoValidateFunction = enableNoValidateFunctions.stream()
                .anyMatch(f -> match(f, method, path));
        if (isEnableNoValidateFunction) {
            log.debug("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}", method, path, isDisableFunction, isEnableNoValidateFunction);
            return new String[0];
        }

        // 如果是启用并需要验证请求，获取对应角色
        List<SysFunction> enableValidateFunction = Optional.ofNullable(sysFunctionService.findAllEnable(YesNo.Y)).orElse(Collections.emptyList());
        Set<String> moduleCodes = enableValidateFunction.stream()
                .filter(f -> match(f, method, path))
                .map(s -> s.getModuleCode())
                .collect(Collectors.toSet());
        // 未查询到启用功能，说明未配置，只允许超级管理员访问
        if (moduleCodes == null || moduleCodes.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, moduleCodes:{}", method, path, isDisableFunction, isEnableNoValidateFunction, moduleCodes);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }
        // 未查询到可用模块，说明未配置，只允许超级管理员访问
        List<SysModule> modules = sysModuleService.find(moduleCodes, YesNo.Y);
        if (modules == null || modules.isEmpty()) {
            log.warn("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, moduleCodes:{}, modules:{}", method, path, isDisableFunction, isEnableNoValidateFunction, moduleCodes, modules);
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        }
        // 未查询到可用角色，说明未配置，只有超级管理员可以访问
        List<SysRole> roles = sysRoleService.findByModuleCode(modules.stream().map(SysModule::getCode).collect(Collectors.toList()), YesNo.Y);
        log.debug("getPathConfig end. method:{}, path:{}, isDisableFunction:{}, isEnableNoValidateFunction:{}, isDisableModule:{}, moduleCodes:{}, modules:{}, roles:{}", method, path, isDisableFunction, isEnableNoValidateFunction, moduleCodes, modules, roles);
        if (roles == null || roles.isEmpty()) {
            return new String[] { RoleKeyConstant.SUPER_ADMIN };
        } else {
            return roles.stream().map(SysRole::getCode).collect(Collectors.toList()).toArray(new String[0]);
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
        return WebUtil.getPathWithinApplication((HttpServletRequest) request);
    }
    protected String getHttpMethodWithinApplication(ServletRequest request) {
        return ((HttpServletRequest) request).getMethod();
    }
}

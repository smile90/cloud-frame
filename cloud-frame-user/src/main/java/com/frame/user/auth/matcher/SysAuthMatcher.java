package com.frame.user.auth.matcher;

import com.frame.user.auth.resource.Resource;

import javax.servlet.ServletRequest;

/**
 * 用户权限匹配者
 * @author: duanchangqing90
 * @date: 2018/12/19
 */
public interface SysAuthMatcher {

    /**
     * 是否匹配允许的请求
     * @param resource
     * @return
     */
    boolean matchPermitPath(Resource resource);

    /**
     * 获取路径配置（此处是角色标识）
     * @param request
     * @return
     */
    String[] getPathConfig(ServletRequest request);

    /**
     * 获取路径配置（此处是角色标识）
     * @param method
     * @param path
     * @return
     */
    String[] getPathConfig(String method, String path);
}
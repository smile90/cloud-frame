package com.frame.user.shiro.matcher;

import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;

/**
 * 用户权限匹配者
 * @author: duanchangqing90
 * @date: 2018/12/19
 */
public interface SysAuthMatcher {

    /**
     * 获取路径配置（此处是角色标识）
     * @param request
     * @return
     */
    String[] getPathConfig(ServletRequest request);

    /**
     * 获取路径配置（此处是角色标识）
     * @param path
     * @param method
     * @return
     */
    String[] getPathConfig(String path, String method);
}
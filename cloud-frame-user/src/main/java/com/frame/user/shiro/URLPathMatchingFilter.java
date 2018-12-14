package com.frame.user.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * URL匹配拦截器
 */
@Slf4j
public class URLPathMatchingFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.info("requestURI:{}", getPathWithinApplication(request));

        return super.onPreHandle(request, response, mappedValue);
    }
}

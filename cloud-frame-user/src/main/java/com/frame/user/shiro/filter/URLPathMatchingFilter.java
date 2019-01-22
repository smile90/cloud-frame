package com.frame.user.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.shiro.matcher.SysAuthMatcher;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.PathConfigProcessor;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Set;

/**
 * URL匹配拦截器
 */
@Slf4j
public class URLPathMatchingFilter extends AuthorizationFilter implements PathConfigProcessor {

    @Setter
    private SysAuthMatcher sysAuthMatcher;

    public URLPathMatchingFilter(SysAuthMatcher sysAuthMatcher) {
        super();
        this.sysAuthMatcher = sysAuthMatcher;
    }

    /**
     * 这里启动时不做特殊处理
     * 全部在业务操作时，通过SysAuthMatcher进行处理
     * @param path
     * @param config
     * @return
     */
    @Override
    public Filter processPathConfig(String path, String config) {
        return this;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) {
        return false;
    }

    /**
     * 获取对应请求的配置，然后调用鉴权方法
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        Object mappedValue = sysAuthMatcher.getPathConfig(request);
        log.debug("mappedValue:{}", mappedValue);
        if (isAccessAllowed(request, response, mappedValue)) {
            onAccessSuccess(request, response);
        } else {
            onAccessDenied(request, response, mappedValue);
        }
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 未获取到用户
        if (subject == null) {
            log.error("not found subject.");
            return false;
        }
        String[] rolesArray = (String[]) mappedValue;
        // 路径无需角色
        if (rolesArray == null || rolesArray.length == 0) {
            log.warn("not access auth. path:{}", getPathWithinApplication(request));
            return true;
        }
        // 匹配角色：有任一角色均可访问
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        boolean match = Arrays.stream(rolesArray).anyMatch(role -> subject.hasRole(role));
        log.debug("isAccessAllowed roles:{}, principal:{}, match:{}", roles, subject.getPrincipal(), match);
        return match;
    }

    /**
     * 此处不进行跳转，直接响应成功
     * @param request
     * @param response
     * @throws Exception
     */
    protected void onAccessSuccess(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(ResponseBean.success()));
        } catch (Exception e) {
            log.error("executeChain error.", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(ResponseBean.getInstance(AuthMsgResult.NOT_AUTH_ERROR)));
        } catch (Exception e) {
            log.error("onAccessDenied error.", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return false;
    }

}
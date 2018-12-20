package com.frame.user.shiro;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.enums.AuthMsgResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathConfigProcessor;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * URL匹配拦截器
 */
@Slf4j
public class URLPathMatchingFilter extends RolesAuthorizationFilter implements PathConfigProcessor {

    @Setter
    private SysAuthMatcher sysAuthMatcher;

    /**
     * 这里启动时不做特殊处理
     * 全部在业务操作时，通过SysAuthMatcher进行自发处理
     * @param path
     * @param config
     * @return
     */
    @Override
    public Filter processPathConfig(String path, String config) {
        return this;
    }

    /**
     * 获取对应请求的配置，然后调用鉴权方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return onPreHandle(request, response, sysAuthMatcher.getPathConfig(request));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
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

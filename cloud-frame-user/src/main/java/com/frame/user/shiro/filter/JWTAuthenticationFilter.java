package com.frame.user.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.shiro.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * URL匹配拦截器
 */
@Slf4j
public class JWTAuthenticationFilter extends AuthenticatingFilter {

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(JWTUtil jwtUtil) {
        super();
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return super.isAccessAllowed(request, response, mappedValue) ||
                    (isLoginRequest(request, response) && super.executeLogin(request, response));
        } catch (Exception e) {
            log.error(null, e);
            return false;
        }
    }

    /**
     * 此处请求头中有Token，则认为是登录请求
     * 执行对应的Subject.login -> com.frame.user.shiro.realm.JWTRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        return StringUtils.hasText(jwtUtil.getToken(WebUtils.toHttp(request)));
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return jwtUtil.getAuthenticationToken(WebUtils.toHttp(request));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(ResponseBean.getInstance(AuthMsgResult.NOT_LOGIN_ERROR)));
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

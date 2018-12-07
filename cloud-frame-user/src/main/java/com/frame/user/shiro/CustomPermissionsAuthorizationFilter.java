package com.frame.user.shiro;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.user.enums.AuthResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义过滤器
 * 对没有登录的请求进行拦截, 全部返回json信息. 覆盖掉shiro原本的跳转login.jsp的拦截方式
 */
@Slf4j
public class CustomPermissionsAuthorizationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            out.println(JSONObject.toJSONString(ResponseBean.getInstance(AuthResponseResult.AUTH_ERROR)));
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

package com.frame.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.frame.client.AuthClient;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.gateway.SystemMsgResult;
import com.frame.gateway.properties.AuthProperties;
import com.frame.gateway.util.AuthUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限过滤器
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private AuthClient authClient;

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // 参数
        String method = request.getMethod();
        String url = request.getServletPath();
        String token = authUtil.getToken(request);

        JSONObject resource = new JSONObject();
        resource.put("method", method);
        resource.put("url", url);
        JSONObject data = new JSONObject();
        data.put(authProperties.getJwt().getTokenName(), token);
        data.put("resource", resource);

        log.debug("auth filter begin. token:{}, url:[{}]-[{}]", token, url, method);

        // 校验登录
        try {
            if (authProperties.isEnableValidLogin()) {
                ResponseBean result = authClient.validLoginJWT(data);
                log.debug("valid login end. data:{}, url:[{}]-[{}], result:{}", data, url, method, result);

                if (!result.isSuccess()) {
                    ctx.setSendZuulResponse(false);
                    ctx.getResponse().setStatus(HttpStatus.SC_FORBIDDEN);
                    ctx.getResponse().setContentType("application/json;charset=UTF-8");
                    ctx.setResponseBody(JSONObject.toJSONString(result));
                    log.debug("auth filter end. token:{}, url:[{}]-[{}]", token, url, method);
                    return null;
                }
            }

            // 校验权限
            if (authProperties.isEnableValidAuth()) {
                ResponseBean result = authClient.validAuthJWT(data);
                log.debug("valid auth end. data:{}, url:[{}]-[{}], result:{}", data, url, method, result);

                if (!result.isSuccess()) {
                    ctx.setSendZuulResponse(false);
                    ctx.getResponse().setContentType("application/json;charset=UTF-8");
                    ctx.setResponseBody(JSONObject.toJSONString(result));
                    log.debug("auth filter end. token:{}, url:[{}]-[{}]", token, url, method);
                    return null;
                }
            }
        } catch (Exception e) {
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            ctx.setResponseBody(JSONObject.toJSONString(ResponseBean.getInstance(SystemMsgResult.SYSTEM_ERROR)));
            log.error("valid error. token:{}, url:[{}]-[{}]", token, url, method, e);
            log.debug("auth filter end. token:{}, url:[{}]-[{}]", token, url, method);
            return null;
        }

        // 转发用户信息
        try {
            JSONObject infoData = new JSONObject();
            infoData.put(authProperties.getJwt().getTokenName(), token);
            ResponseBean result = authClient.getInfoJWT(infoData);
            log.debug("get info end. token:{}, url:[{}]-[{}], result:{}", token, url, method, result);

            RequestContext.getCurrentContext().addZuulRequestHeader(authProperties.getRequest().getAuthName(), JSONObject.toJSONString(result.getContent()));
            log.debug("auth filter end. token:{}, url:[{}]-[{}]", token, url, method);
            return null;
        } catch(Exception e) {
            log.error("auth filter: set userAuth error. {}", token);
            log.debug("auth filter end. token:{}, url:[{}]-[{}]", token, url, method);
            return null;
        }
    }
}


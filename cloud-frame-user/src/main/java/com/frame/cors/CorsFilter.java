package com.frame.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 跨域拦截器
 * @author: duanchangqing90
 * @date: 2019/01/21
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CorsFilter extends GenericFilterBean {

    @Autowired
    private CorsProperties corsProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 跨域处理
        corf((HttpServletRequest) request,(HttpServletResponse) response);
        // 跨域检查请求不做处理
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
            ((HttpServletResponse) response).setStatus(HttpStatus.OK.value());
        } else {
            filterChain.doFilter(request, response);
        }
    }

    public void corf(HttpServletRequest request,HttpServletResponse response) {
        String requestOrigin = request.getHeader("origin");
        Optional.ofNullable(corsProperties.getAllowedOrigin()).ifPresent(origins -> {
            Arrays.stream(origins.split(",")).forEach(origin -> {
                if(StringUtils.hasText(requestOrigin) && origins.contains(requestOrigin)) {
                    response.setHeader("Access-Control-Allow-Origin", requestOrigin);
                }
            });
        });

        response.setHeader("Access-Control-Allow-Methods", corsProperties.getAllowedMethod());
        response.setHeader("Access-Control-Allow-Headers", corsProperties.getAllowedHeader());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}

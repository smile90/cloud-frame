package com.frame.gateway.util;

import com.frame.gateway.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * JWT工具类
 * @author: duanchangqing90
 * @date: 2019/01/10
 */
@Slf4j
@Component
public class AuthUtil {

    @Autowired
    private AuthProperties authProperties;

    public String getDeviceSource(HttpServletRequest request) {
        // 先从请求头取
        String deviceSource = request.getHeader(authProperties.getDevice().getDeviceSourceName());
        if (StringUtils.hasText(deviceSource) && !"null".equalsIgnoreCase(deviceSource)) {
            return deviceSource;
        }
        // 再从请求参数中去
        deviceSource = request.getParameter(authProperties.getDevice().getDeviceSourceName());
        if (StringUtils.hasText(deviceSource) && !"null".equalsIgnoreCase(deviceSource)) {
            return deviceSource;
        }
        return null;
    }

    public String getToken(HttpServletRequest request) {
        // 先从请求头取
        String token = request.getHeader(authProperties.getJwt().getTokenName());
        if (StringUtils.hasText(token) && !"null".equalsIgnoreCase(token)) {
            return token;
        }
        // 再从请求参数中取
        token = request.getParameter(authProperties.getJwt().getTokenName());
        if (StringUtils.hasText(token) && !"null".equalsIgnoreCase(token)) {
            return token;
        }
        // 再从Session中取
        token = (String) request.getSession().getAttribute(authProperties.getSession().getTokenName());
        if (StringUtils.hasText(token) && !"null".equalsIgnoreCase(token)) {
            return token;
        }
        return null;
    }
}
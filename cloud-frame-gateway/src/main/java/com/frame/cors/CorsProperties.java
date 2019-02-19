package com.frame.cors;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限属性
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@ToString
@Configuration
@ConfigurationProperties(prefix = "cors", ignoreInvalidFields = true)
public class CorsProperties {
    /*域名*/
    private String allowedOrigin;
    /*头*/
    private String allowedHeader;
    /*方法*/
    private String allowedMethod;

    public String getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(String allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    public String getAllowedHeader() {
        return allowedHeader;
    }

    public void setAllowedHeader(String allowedHeader) {
        this.allowedHeader = allowedHeader;
    }

    public String getAllowedMethod() {
        return allowedMethod;
    }

    public void setAllowedMethod(String allowedMethod) {
        this.allowedMethod = allowedMethod;
    }
}

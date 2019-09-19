package com.frame.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限属性
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cors", ignoreInvalidFields = true)
public class CorsProperties {
    private String allowOrigin;
    private String allowHeader;
    private String allowMethod;
    private String allowCredentials;
    private String maxAge;
}

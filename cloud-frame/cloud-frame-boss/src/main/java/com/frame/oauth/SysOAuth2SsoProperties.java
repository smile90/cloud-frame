package com.frame.oauth;

import lombok.Data;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 增加一些附加属性
 *
 * @author: smile
 * @date: 2019/07/16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.sso")
public class SysOAuth2SsoProperties {

    private String indexPath = "/index";
    private String loginPath = "/login";
}

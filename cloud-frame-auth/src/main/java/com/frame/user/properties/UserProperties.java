package com.frame.user.properties;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 用户属性
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@ToString
@Configuration
@ConfigurationProperties(prefix = "user", ignoreInvalidFields = true)
public class UserProperties {
    /*默认密码*/
    private String defaultPwd = "123456";

    public String getDefaultPwd() {
        return defaultPwd;
    }

    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd;
    }
}

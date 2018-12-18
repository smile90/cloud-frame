package com.frame.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 权限属性
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth", ignoreInvalidFields = true)
public class AuthProperties {
    /*登录参数*/
    private Login login = new Login();

    @Data
    public class Login {
        /*失败次数*/
        private Integer errorTime = 5;
        /*超时时间（秒）：默认15分钟*/
        private Integer timeout = 15 * 60;
    }
}

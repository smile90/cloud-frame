package com.frame.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

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
        /*失败超时时间：默认15分钟*/
        private Duration errorTimeout = Duration.ofMinutes(15L);
        /*是否启用验证码*/
        private boolean enableValidCode = true;
        /*验证码超时时间：默认5分钟*/
        private Duration validCodeTimeout = Duration.ofMinutes(5L);
    }
}

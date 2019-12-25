package com.frame.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 权限属性
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth", ignoreInvalidFields = true)
public class AuthProperties {
    /*登录参数*/
    private Login login = new Login();
    /*java web token参数*/
    private Jwt jwt = new Jwt();
    /*URL参数*/
    private Url url = new Url();
    /*缓存参数*/
    private Cache cache = new Cache();
    /*设备参数*/
    private Device device = new Device();

    @Data
    public class Login {
        /*是否启用最大登录错误数限制*/
        private boolean enableErrorTime = true;
        /*最大失败次数*/
        private Integer maxErrorTime = 5;
        /*失败超时时间：默认15分钟*/
        private Duration errorTimeout = Duration.ofMinutes(15L);

        /*是否启用验证码*/
        private boolean enableValidCode = true;
        /*验证码超时时间：默认5分钟*/
        private Duration validCodeTimeout = Duration.ofMinutes(5L);

        /*记住我前端参数name*/
        private String rememberMeName = "rememberMe";
        /*是否启用记住我*/
        private boolean enableRememberMe = true;
        /*记住我超时时间：默认7天*/
        private Duration rememberMeTimeout = Duration.ofDays(7L);
    }

    @Data
    public class Jwt {
        /*秘钥*/
        private String secret = "123456";
        /*请求中，token对应name：默认为token*/
        private String tokenName = "bossToken";
        /*短超时时间：默认为30分钟*/
        private Duration shortTimeout = Duration.ofMinutes(30L);
        /*长超时时间：默认为7天*/
        private Duration longTimeout = Duration.ofDays(7L);
    }

    @Data
    public class Url {
        private String permitPaths = "/error, /druid/**, /auth/*, /user/validCode/login";
        private String authenticatePaths = "/**";
    }


    @Data
    public class Cache {
        /*超时时间：默认15分钟*/
        private Duration timeout = Duration.ofMinutes(15L);
    }

    @Data
    public class Device {
        /*设备来源name：默认为deviceSource*/
        private String deviceSourceName = "deviceSource";
    }

}

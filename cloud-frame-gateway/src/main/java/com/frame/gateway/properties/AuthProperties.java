package com.frame.gateway.properties;

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

    private boolean enableValidLogin = true;
    private boolean enableValidAuth = true;

    private Session session = new Session();
    private Jwt jwt = new Jwt();
    private Request request = new Request();
    private Device device = new Device();

    @Data
    public class Session {
        /*请求中，token对应name：默认为token*/
        private String tokenName = "bossToken";
    }

    @Data
    public class Jwt {
        /*请求中，token对应name：默认为token*/
        private String tokenName = "bossToken";
    }

    @Data
    public class Request {
        /*请求中，token对应name：默认为token*/
        private String authName = "bossAuthUser";
    }

    @Data
    public class Device {
        /*设备来源name：默认为deviceSource*/
        private String deviceSourceName = "deviceSource";
    }

}

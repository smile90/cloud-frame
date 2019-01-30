package com.frame.user.properties;

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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @ToString
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

        /*是否启用权限*/
        private boolean enableAuth = true;

        /*是否启用session*/
        private boolean enableSession = true;

        public boolean isEnableErrorTime() {
            return enableErrorTime;
        }

        public void setEnableErrorTime(boolean enableErrorTime) {
            this.enableErrorTime = enableErrorTime;
        }

        public Integer getMaxErrorTime() {
            return maxErrorTime;
        }

        public void setMaxErrorTime(Integer maxErrorTime) {
            this.maxErrorTime = maxErrorTime;
        }

        public Duration getErrorTimeout() {
            return errorTimeout;
        }

        public void setErrorTimeout(Duration errorTimeout) {
            this.errorTimeout = errorTimeout;
        }

        public boolean isEnableValidCode() {
            return enableValidCode;
        }

        public void setEnableValidCode(boolean enableValidCode) {
            this.enableValidCode = enableValidCode;
        }

        public Duration getValidCodeTimeout() {
            return validCodeTimeout;
        }

        public void setValidCodeTimeout(Duration validCodeTimeout) {
            this.validCodeTimeout = validCodeTimeout;
        }

        public String getRememberMeName() {
            return rememberMeName;
        }

        public void setRememberMeName(String rememberMeName) {
            this.rememberMeName = rememberMeName;
        }

        public boolean isEnableRememberMe() {
            return enableRememberMe;
        }

        public void setEnableRememberMe(boolean enableRememberMe) {
            this.enableRememberMe = enableRememberMe;
        }

        public Duration getRememberMeTimeout() {
            return rememberMeTimeout;
        }

        public void setRememberMeTimeout(Duration rememberMeTimeout) {
            this.rememberMeTimeout = rememberMeTimeout;
        }

        public boolean isEnableAuth() {
            return enableAuth;
        }

        public void setEnableAuth(boolean enableAuth) {
            this.enableAuth = enableAuth;
        }

        public boolean isEnableSession() {
            return enableSession;
        }

        public void setEnableSession(boolean enableSession) {
            this.enableSession = enableSession;
        }
    }

    @ToString
    public class Jwt {
        /*秘钥*/
        private String secret = "123456";
        /*请求中，token对应name：默认为token*/
        private String tokenName = "token";
        /*短超时时间：默认为30分钟*/
        private Duration shortTimeout = Duration.ofMinutes(30L);
        /*长超时时间：默认为7天*/
        private Duration longTimeout = Duration.ofDays(7L);

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public Duration getShortTimeout() {
            return shortTimeout;
        }

        public void setShortTimeout(Duration shortTimeout) {
            this.shortTimeout = shortTimeout;
        }

        public Duration getLongTimeout() {
            return longTimeout;
        }

        public void setLongTimeout(Duration longTimeout) {
            this.longTimeout = longTimeout;
        }
    }

    @ToString
    public class Url {
        /*URL映射*/
        private Map<String, String> filterChainDefinitionMap = new HashMap<>();
        /*分隔符：默认为逗号（,）*/
        private String split = ",";

        public Map<String, String> getFilterChainDefinitionMap() {
            return filterChainDefinitionMap;
        }

        public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
            this.filterChainDefinitionMap = filterChainDefinitionMap;
        }

        public String getSplit() {
            return split;
        }

        public void setSplit(String split) {
            this.split = split;
        }
    }

    @ToString
    public class Cache {
        /*Shiro超时时间：默认15分钟*/
        private Duration timeout = Duration.ofMinutes(15L);

        public Duration getTimeout() {
            return timeout;
        }

        public void setTimeout(Duration timeout) {
            this.timeout = timeout;
        }
    }

    @ToString
    public class Device {
        /*设备来源name：默认为deviceSource*/
        private String deviceSourceName = "deviceSource";

        public String getDeviceSourceName() {
            return deviceSourceName;
        }

        public void setDeviceSourceName(String deviceSourceName) {
            this.deviceSourceName = deviceSourceName;
        }
    }

}

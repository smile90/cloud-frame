package com.frame.user.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 权限属性
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
@ToString
@Configuration
@ConfigurationProperties(prefix = "auth", ignoreInvalidFields = true)
public class AuthProperties {
    /*登录参数*/
    private Login login = new Login();
    /*缓存参数*/
    private Cache cache = new Cache();

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @ToString
    public class Login {
        /*是否启用最大登录错误数限制*/
        private boolean enableErrorTime = true;
        /*最大失败次数*/
        private Integer maxErrorTime = 5;
        /*失败超时时间：默认15分钟*/
        private Duration errorTimeout = Duration.ofMinutes(15L);
        /*请求中，token对应name：默认为token*/
        private String tokenName = "token";

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

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
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
    }

    @Data
    public class Cache {
        /*Shiro超时时间：默认15分钟*/
        private Duration timeout = Duration.ofMinutes(15L);
    }
}

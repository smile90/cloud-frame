package com.frame.user.config;

import com.frame.user.properties.AuthProperties;
import com.frame.user.shiro.*;
import com.frame.user.shiro.cache.ShiroRedisCacheManager;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysAuthMatcher sysAuthByRoleMatcher;

    /**
     * 缓存管理
     * @return
     */
    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager() {
        return new ShiroRedisCacheManager(redisTemplate, authProperties.getCache().getTimeout());
    }

    /**
     * Session管理
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        return new ShiroSessionManager();
    }

    /**
     * 密码校验
     * @return
     */
    @Bean
    public CredentialsMatcher bCryptCredentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }

    /**
     * 用户密码权限
     * @return
     */
    @Bean
    public Realm userPwdRealm() {
        UserPwdRealm realm = new UserPwdRealm();
        // 密码校验类
        realm.setCredentialsMatcher(bCryptCredentialsMatcher());
        realm.setCacheManager(shiroRedisCacheManager());
        return realm;
    }

    /**
     * Token权限
     * @return
     */
    @Bean
    public Realm tokenRealm() {
        TokenRealm realm = new TokenRealm();
        return realm;
    }

    /**
     * 认证处理：只要一个或者多个Realm认证通过，则整体身份认证就会视为成功
     * @return
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new ShiroAtLeastOneSuccessfulStrategy());

        Collection<Realm> realms = new ArrayList<>();
        realms.add(userPwdRealm());
        realms.add(tokenRealm());
        authenticator.setRealms(realms);

        return authenticator;
    }

    /**
     * 记住登录Cookie策略
     * @return
     */
    @Bean
    public Cookie rememberMeCookie() {
        // cookie对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie(authProperties.getLogin().getRememberMeName());
        // cookie失效时间
        simpleCookie.setMaxAge((int) authProperties.getLogin().getRememberMeTimeout().getSeconds());
        return simpleCookie;
    }

    /**
     * 记住登录管理
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 权限管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 缓存管理
        securityManager.setCacheManager(shiroRedisCacheManager());
        // Session管理
        securityManager.setSessionManager(sessionManager());
        // 认证逻辑
        securityManager.setAuthenticator(authenticator());

        // 记住登录
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 拦截器配置
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 默认拦截器
        Map<String, Filter> filterMap = Maps.newLinkedHashMap();
        // 认证过滤器修改了未授权信息通过Response回写，而不是跳转页面
        filterMap.put("authc", new UserPwdAuthenticationFilter());
        // 增加URL过滤器
        filterMap.put("requestURL", urlPathMatchingFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // 请求配置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 无权限可访问地址
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/validCode/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/logout", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        // 记住我后可访问地址
        filterChainDefinitionMap.put("/sys/index/**", "authc,requestURL");
        // 登录后可访问地址
        filterChainDefinitionMap.put("/sys/index", "authc");
        // 必须登录授权并有权限
        filterChainDefinitionMap.put("/**", "authc,requestURL");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    private URLPathMatchingFilter urlPathMatchingFilter() {
        URLPathMatchingFilter urlPathMatchingFilter = new URLPathMatchingFilter();
        urlPathMatchingFilter.setSysAuthMatcher(sysAuthByRoleMatcher);
        return urlPathMatchingFilter;
    }
}

package com.frame.user.config;

import com.frame.user.properties.AuthProperties;
import com.frame.user.shiro.ShiroAtLeastOneSuccessfulStrategy;
import com.frame.user.shiro.cache.ShiroRedisCacheManager;
import com.frame.user.shiro.filter.JWTAuthenticationFilter;
import com.frame.user.shiro.filter.ShiroUserFilter;
import com.frame.user.shiro.filter.URLPathMatchingFilter;
import com.frame.user.shiro.matcher.JWTMatcher;
import com.frame.user.shiro.matcher.PwdCredentialsMatcher;
import com.frame.user.shiro.matcher.SysAuthMatcher;
import com.frame.user.shiro.realm.JWTRealm;
import com.frame.user.shiro.realm.UserPwdRealm;
import com.frame.user.shiro.util.JWTUtil;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
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
    @Autowired
    private JWTUtil jwtUtil;

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
        DefaultSessionManager manager = new DefaultSessionManager();
        manager.setSessionValidationSchedulerEnabled(authProperties.getLogin().isEnableSession());
        return manager;
    }

    /**
     * 密码校验
     * @return
     */
    @Bean
    public CredentialsMatcher pwdCredentialsMatcher() {
        return new PwdCredentialsMatcher();
    }

    @Bean
    public CredentialsMatcher jWTMatcher() {
        return new JWTMatcher();
    }

    /**
     * 用户密码权限
     * @return
     */
    @Bean
    public Realm userPwdRealm() {
        UserPwdRealm realm = new UserPwdRealm();
        // 密码校验类
        realm.setCredentialsMatcher(pwdCredentialsMatcher());
        realm.setCacheManager(shiroRedisCacheManager());
        return realm;
    }

    /**
     * Token权限
     * @return
     */
    @Bean
    public Realm jwtRealm() {
        JWTRealm realm = new JWTRealm();
        realm.setCredentialsMatcher(jWTMatcher());
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
        Collection<Realm> realms = new ArrayList<>();
        realms.add(userPwdRealm());
        realms.add(jwtRealm());
        securityManager.setRealms(realms);

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
//        全部使用JSON进行响应
//        shiroFilterFactoryBean.setLoginUrl("/sys/loginPage");
//        shiroFilterFactoryBean.setSuccessUrl("/sys/index");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/unAuth");

        // 权限管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 默认拦截器
        Map<String, Filter> filterMap = Maps.newLinkedHashMap();
        // 修改了未授权信息通过Response回写json，而不是跳转页面
        filterMap.put("user", new ShiroUserFilter());
//        filterMap.put("form", new ShiroFormAuthenticationFilter());

        // 重新定义登录权限校验，使用JWT进行校验
        filterMap.put("authc", new JWTAuthenticationFilter(jwtUtil));
        // 增加URL过滤器
        filterMap.put("requestURL", new URLPathMatchingFilter(sysAuthByRoleMatcher));
        shiroFilterFactoryBean.setFilters(filterMap);

        // 请求配置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 无权限可访问地址
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        filterChainDefinitionMap.put("/validCode/**", "anon");
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/logout", "anon");

        filterChainDefinitionMap.put("/sys/menu", "anon");

        filterChainDefinitionMap.put("/error", "anon");
        // 记住我后可访问地址
        filterChainDefinitionMap.put("/test/**", "user");
        filterChainDefinitionMap.put("/sys/index", "user");
        // 登录后可访问地址
        filterChainDefinitionMap.put("/sys/user/index", "authc");
        // 必须登录授权并有权限
        filterChainDefinitionMap.put("/**", "authc,requestURL");

        // 请求配置拦截器 TODO
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        authProperties.getUrl().getFilterChainDefinitionMap()
//                .entrySet().stream().forEach(entry -> {
//            // 如果Key不为空
//            Optional.ofNullable(entry.getKey()).ifPresent(key -> {
//                Arrays.stream(key.split(authProperties.getUrl().getSplit()))
//                        .forEach(url -> filterChainDefinitionMap.put(url.trim(), entry.getValue()));
//            });
//        });

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}

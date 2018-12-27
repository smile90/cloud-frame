package com.frame.user.config;

import com.frame.user.shiro.*;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private SysAuthMatcher sysAuthByRoleMatcher;

    @Bean("bCryptCredentialsMatcher")
    public CredentialsMatcher bCryptCredentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }

    @Bean("userPwdRealm")
    public Realm userPwdRealm() {
        UserPwdRealm realm = new UserPwdRealm();
        // 密码校验类
        realm.setCredentialsMatcher(bCryptCredentialsMatcher());
        return realm;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userPwdRealm());
        return securityManager;
    }

    @Bean("shiroFilterFactoryBean")
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
        // 登录后可访问地址
        filterChainDefinitionMap.put("/sys/index", "authc");
        // 登录并有权限
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

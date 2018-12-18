package com.frame.user.config;

import com.frame.user.shiro.BCryptCredentialsMatcher;
import com.frame.user.shiro.UserPwdRealm;
import com.frame.user.shiro.UserPwdAuthenticationFilter;
import com.frame.user.shiro.URLPathMatchingFilter;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("bCryptCredentialsMatcher")
    public CredentialsMatcher bCryptCredentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }

    @Bean("userPwdRealm")
    public Realm userPwdRealm(){
        UserPwdRealm realm = new UserPwdRealm();
        // 密码校验类
        realm.setCredentialsMatcher(bCryptCredentialsMatcher());
        return realm;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(userPwdRealm());
        return securityManager;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filterMap = Maps.newLinkedHashMap();
        // 认证过滤器修改了未授权信息通过Response回写，而不是跳转页面
        filterMap.put("authc", new UserPwdAuthenticationFilter());
        // 增加URL过滤器
        filterMap.put("requestURL", new URLPathMatchingFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        /*定义shiro过滤链  Map结构
         * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,默认是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
         /* 过滤链定义，从上向下顺序执行，一般将 / ** 放在最为下边:这是一个坑呢，一不小心代码就不好使了;
          authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问 */
        // 无权限可访问地址
        filterChainDefinitionMap.put("/static/**,/favicon.ico", "anon");
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

}

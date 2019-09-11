package com.frame.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 * @author: duanchangqing90
 * @date: 2019/09/11
 */
@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//            // Since we want the protected resources to be accessible in the UI as well we need
//            // session creation to be allowed (it's disabled by default in 2.0.6)
////            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
////            .and()
//            .authorizeRequests()
//                .antMatchers("/pub/**").access("#oauth2.hasScope('all')")
//                .anyRequest().authenticated();
//    }
}

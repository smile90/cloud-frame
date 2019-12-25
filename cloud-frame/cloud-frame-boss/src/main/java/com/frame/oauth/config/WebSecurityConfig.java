package com.frame.oauth.config;

import com.frame.oauth.ClientResources;
import com.frame.oauth.SysOAuth2SsoProperties;
import com.frame.oauth.impl.FrameUserInfoTokenServices;
import com.frame.oauth.impl.GetUserInfoTokenServices;
import com.frame.oauth.impl.UserInfoTokenServices;
import com.frame.oauth.impl.WeiboUserInfoTokenServices;
import com.frame.oauth.service.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限配置
 *
 * @author: duanchangqing90
 * @date: 2019/03/20
 */
@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysOAuth2SsoProperties sysOAuth2SsoProperties;
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private DefaultUserService defaultUserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // csrf 配置
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // 默认权限过滤
                .and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login/**")
                .permitAll()

                .anyRequest()
                .authenticated()

                // 增加第三方登录拦截器
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        // 异常处理
//                .exceptionHandling()
        ;
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        filters.add(ssoFilter("/login/frame",
                new FrameUserInfoTokenServices(frame(), defaultUserService, "frame", sysOAuth2SsoProperties.getIndexPath())));
        filters.add(ssoFilter("/login/baidu",
                new GetUserInfoTokenServices(baidu(), defaultUserService, "baidu", sysOAuth2SsoProperties.getIndexPath())));
        filters.add(ssoFilter("/login/qq",
                new GetUserInfoTokenServices(qq(), defaultUserService, "qq", sysOAuth2SsoProperties.getIndexPath())));
        filters.add(ssoFilter("/login/weibo",
                new WeiboUserInfoTokenServices(weibo(), defaultUserService, "weibo", sysOAuth2SsoProperties.getIndexPath())));
        filters.add(ssoFilter("/login/github",
                new GetUserInfoTokenServices(github(), defaultUserService, "github", sysOAuth2SsoProperties.getIndexPath())));

        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(String path, UserInfoTokenServices tokenServices) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(tokenServices.getClient().getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties("security.oauth2.frame")
    public ClientResources frame() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.baidu")
    public ClientResources baidu() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.qq")
    public ClientResources qq() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.weibo")
    public ClientResources weibo() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.github")
    public ClientResources github() {
        return new ClientResources();
    }


    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}

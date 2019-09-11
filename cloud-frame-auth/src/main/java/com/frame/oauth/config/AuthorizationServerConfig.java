package com.frame.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器配置
 * @author: duanchangqing90
 * @date: 2019/03/07
 *
 * /oauth/token：令牌端点。
 * /oauth/authorize：授权端点。
 * /oauth/confirm_access：用户确认授权提交端点。
 * /oauth/error：授权服务错误信息端点。
 * /oauth/check_token：用于资源服务访问的令牌解析端点。
 * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("sysUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("clientDetails")
    private ClientDetailsService clientDetails;
    @Autowired
    @Qualifier("tokenStore")
    private TokenStore tokenStore;
    @Autowired
    @Qualifier("approvalStore")
    private ApprovalStore approvalStore;
    @Autowired
    @Qualifier("authorizationCodeServices")
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authorizationCodeServices(authorizationCodeServices)
                .approvalStore(approvalStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

}

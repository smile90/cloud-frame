package com.frame.oauth.impl;

import com.frame.boot.base.utils.EmptyUtil;
import com.frame.oauth.ClientResources;
import com.frame.oauth.service.OAuthAuthoritiesExtractor;
import com.frame.oauth.service.OAuthPrincipalExtractor;
import com.frame.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 重写获取用户信息部分
 *
 * @see org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
 */
@Slf4j
public abstract class UserInfoTokenServices implements ResourceServerTokenServices {

    protected final String source;
    protected final String redirectUri;
    protected String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

    protected UserService userService;
    protected ClientResources client;
    protected OAuth2RestOperations restTemplate;
    protected AuthoritiesExtractor authoritiesExtractor = new OAuthAuthoritiesExtractor();
    protected PrincipalExtractor principalExtractor = new OAuthPrincipalExtractor();

    public UserInfoTokenServices(ClientResources client, UserService userService, String source, String redirectUri) {
        this.client = client;
        this.source = source;
        this.redirectUri = redirectUri;
        this.userService = userService;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
        this.authoritiesExtractor = authoritiesExtractor;
    }
    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
        this.principalExtractor = principalExtractor;
    }
    public ClientResources getClient() {
        return client;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken)
            throws AuthenticationException, InvalidTokenException {
        log.debug("get user info. path:{},accessToken:{}", this.client.getResource().getUserInfoUri(), accessToken);
        Map<String, Object> map = getUserInfo(accessToken);
        log.debug("get user info end. path:{},accessToken:{},map:{}", this.client.getResource().getUserInfoUri(), accessToken, map);
        if (EmptyUtil.isEmpty(map) || map.containsKey("error")) {
            log.error("user info returned error. map:", map);
            throw new InvalidTokenException(accessToken);
        }
        return extractAuthentication(map);
    }

    protected OAuth2RestOperations builderRestTemplate(String accessToken) {
        OAuth2RestOperations restTemplate = this.restTemplate;
        if (restTemplate == null) {
            BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
            resource.setClientId(this.client.getClient().getClientId());
            restTemplate = new OAuth2RestTemplate(resource);
        }
        OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
        if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
            token.setTokenType(this.tokenType);
            restTemplate.getOAuth2ClientContext().setAccessToken(token);
        }
        return restTemplate;
    }

    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = getPrincipal(map);

        // 存在：使用本地用户信息；不存在：创建
        Authentication user = userService.findUser(source, principal);
        log.debug("find user:{}", user);
        if (user == null) {
            user = userService.createUser(source, principal, map);
            log.debug("create user:{}", user);
        }
        OAuth2Request request = new OAuth2Request(null, this.client.getClient().getClientId(), null, true, null,
                null, null, null, null);
        OAuth2Authentication auth = new OAuth2Authentication(request, user);
        log.debug("return auth:{}", auth);
        return auth;
    }

    protected Object getPrincipal(Map<String, Object> map) {
        return this.principalExtractor.extractPrincipal(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    protected abstract Map<String, Object> getUserInfo(String accessToken);
}

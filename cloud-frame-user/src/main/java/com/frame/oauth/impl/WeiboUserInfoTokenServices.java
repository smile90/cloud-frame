package com.frame.oauth.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.oauth.ClientResources;
import com.frame.oauth.UserService;
import com.frame.user.enums.OAuthMsgResult;
import com.frame.user.exception.OAuthException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Post获取用户信息
 */
@Slf4j
public class WeiboUserInfoTokenServices extends UserInfoTokenServices {

    public WeiboUserInfoTokenServices(ClientResources client, UserService userService, String source, String redirectUri) {
        super(client, userService, source, redirectUri);
    }

    @Override
    protected Map<String, Object> getUserInfo(String accessToken) {
        try {
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("access_token", accessToken);
            Map<String, Object> userIdResult = builderRestTemplate(accessToken).postForEntity(client.getResource().getUserInfoUri(), map, Map.class).getBody();
            Object principal = getPrincipal(userIdResult);
            log.debug("access_token:{},principal:{}", accessToken, principal);

            // 创建Get请求
            URIBuilder builder = new URIBuilder("https://api.weibo.com/2/users/show.json");
            builder.addParameter("access_token", accessToken);
            builder.addParameter("uid", String.valueOf(principal));
            HttpGet httpGet = new HttpGet(builder.build());

            CloseableHttpResponse response = null;
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            try {
                response = Optional.of(httpClient.execute(httpGet)).orElseThrow(() -> new OAuthException(OAuthMsgResult.OAUTH_REQUEST_ERROR));
                if (response.getStatusLine() != null
                        && HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                    throw new OAuthException(OAuthMsgResult.OAUTH_REQUEST_ERROR.getCode(),
                            OAuthMsgResult.OAUTH_REQUEST_ERROR.getMsg() + "[" + response.getStatusLine().getStatusCode() + "]",
                            OAuthMsgResult.OAUTH_REQUEST_ERROR.getShowMsg() + "[" + response.getStatusLine().getStatusCode() + "]");
                }
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity);
                JSONObject userInfoResult = JSON.parseObject(responseString);
                log.debug("access_token:{},principal:{},result:{}", accessToken, principal, userInfoResult);
                return JSON.parseObject(responseString, Map.class);
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    // 释放资源
                    if (httpClient != null) {
                        httpClient.close();
                    }
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    log.error("access_token:{},principal:{},result:{}", accessToken, principal, response, e);
                }
            }
        } catch (Exception ex) {
            log.error("Could not fetch user details. access_token:{}", accessToken, ex);
            return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
        }
    }

}

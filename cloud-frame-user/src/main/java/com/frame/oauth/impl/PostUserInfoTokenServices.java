package com.frame.oauth.impl;

import com.frame.oauth.ClientResources;
import com.frame.oauth.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * Post获取用户信息
 */
@Slf4j
public class PostUserInfoTokenServices extends UserInfoTokenServices {

    public PostUserInfoTokenServices(ClientResources client, UserService userService, String source, String redirectUri) {
        super(client, userService, source, redirectUri);
    }

    @Override
    protected Map<String, Object> getUserInfo(String accessToken) {
        try {
            return builderRestTemplate(accessToken).postForEntity(client.getResource().getUserInfoUri(), null, Map.class).getBody();
        } catch (Exception ex) {
            log.error("Could not fetch user details.", ex);
            return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
        }
    }

}

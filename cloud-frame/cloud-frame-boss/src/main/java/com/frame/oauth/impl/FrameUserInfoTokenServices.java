package com.frame.oauth.impl;

import com.alibaba.fastjson.JSON;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.oauth.ClientResources;
import com.frame.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * get获取用户信息
 */
@Slf4j
public class FrameUserInfoTokenServices extends UserInfoTokenServices {

    public FrameUserInfoTokenServices(ClientResources client, UserService userService, String source, String redirectUri) {
        super(client, userService, source, redirectUri);
    }

    @Override
    protected Map<String, Object> getUserInfo(String accessToken) {
        try {
            ResponseBean userInfoResult = builderRestTemplate(accessToken).getForEntity(client.getResource().getUserInfoUri(), ResponseBean.class).getBody();
            log.debug("accessToken:{},userInfoResult:{}", accessToken, userInfoResult);
            if (!userInfoResult.getSuccess()) {
                return Collections.singletonMap("error", userInfoResult.getShowMsg());
            } else {
                return JSON.parseObject(JSON.toJSONString(userInfoResult.getContent()), Map.class);
            }
        } catch (Exception ex) {
            log.error("Could not fetch user details.", ex);
            return Collections.singletonMap("error", "Could not fetch user details");
        }
    }

}

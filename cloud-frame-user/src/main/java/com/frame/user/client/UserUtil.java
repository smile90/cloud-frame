package com.frame.user.client;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.constants.CommonConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取操作人
 * @author: duanchangqing90
 * @date: 2019/02/12
 */
public class UserUtil {

    /** 运营用户名标识 */
    public static final String BOSS_AUTH_USER_KEY = "bossAuthUser";
    public static final String BOSS_USERNAME_KEY = "username";

    public static String getBossUsername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 先从请求头取
        String bossAuthUser = request.getHeader(BOSS_AUTH_USER_KEY);
        if (StringUtils.hasText(bossAuthUser) && !"null".equalsIgnoreCase(bossAuthUser)) {
            JSONObject user = JSONObject.parseObject(bossAuthUser, JSONObject.class);
            return user.getString(BOSS_USERNAME_KEY);
        }
        // 再从请求参数中去
        bossAuthUser = request.getHeader(BOSS_AUTH_USER_KEY);
        if (StringUtils.hasText(bossAuthUser) && !"null".equalsIgnoreCase(bossAuthUser)) {
            JSONObject user = JSONObject.parseObject(bossAuthUser, JSONObject.class);
            return user.getString(BOSS_USERNAME_KEY);
        }
        return null;
    }
}

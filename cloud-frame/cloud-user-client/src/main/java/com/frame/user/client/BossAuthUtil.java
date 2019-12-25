package com.frame.user.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取操作人
 *
 * @author: duanchangqing90
 * @date: 2019/02/12
 */
public class BossAuthUtil {


    public static String getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 先从请求头取
        String bossAuthUser = request.getHeader(BossAuthConstant.BOSS_AUTH_USER_KEY);
        if (StringUtils.hasText(bossAuthUser) && !"null".equalsIgnoreCase(bossAuthUser)) {
            JSONObject user = JSONObject.parseObject(bossAuthUser, JSONObject.class);
            return user.getString(BossAuthConstant.BOSS_USER_ID_KEY);
        }
        // 再从请求参数中去
        bossAuthUser = request.getHeader(BossAuthConstant.BOSS_AUTH_USER_KEY);
        if (StringUtils.hasText(bossAuthUser) && !"null".equalsIgnoreCase(bossAuthUser)) {
            JSONObject user = JSONObject.parseObject(bossAuthUser, JSONObject.class);
            return user.getString(BossAuthConstant.BOSS_USER_ID_KEY);
        }
        return null;
    }
}

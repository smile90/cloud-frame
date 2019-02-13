package com.frame.user.client;

import com.frame.common.frame.base.constants.CommonConstant;
import com.frame.user.bean.UserInfo;
import org.apache.shiro.SecurityUtils;
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

    public static String getBossUsername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 先从请求头取
        String username = request.getHeader(CommonConstant.BOSS_USERNAME_KEY);
        if (StringUtils.hasText(username) && !"null".equalsIgnoreCase(username)) {
            return username;
        }
        // 再从请求参数中去
        username = request.getParameter(CommonConstant.BOSS_USERNAME_KEY);
        if (StringUtils.hasText(username) && !"null".equalsIgnoreCase(username)) {
            return username;
        }
        return null;
    }
}

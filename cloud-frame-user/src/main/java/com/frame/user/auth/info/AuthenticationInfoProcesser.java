package com.frame.user.auth.info;

import com.frame.user.auth.token.AuthenticationToken;

/**
 * 信息处理者
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
public interface AuthenticationInfoProcesser {

    /**
     * 保存信息
     * @param info
     * @return
     */
    AuthenticationToken saveInfo(AuthenticationInfo info);

    /**
     * 获取信息
     * @param token
     * @return
     */
    AuthenticationInfo getInfo(AuthenticationToken token);

    /**
     * 是否匹配
     * @return
     */
    Class<?> getAuthenticationTokenClass();
}

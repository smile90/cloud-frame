package com.frame.user.auth.realm;

import com.frame.user.auth.token.AuthenticationToken;

/**
 * 抽象权限设置部分
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
public interface Realm {

    /**
     * 是否匹配
     * @return
     */
    Class<? extends AuthenticationToken> getAuthenticationTokenClass();
}

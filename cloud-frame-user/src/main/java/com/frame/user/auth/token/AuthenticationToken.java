package com.frame.user.auth.token;

import java.io.Serializable;

public interface AuthenticationToken extends Serializable {

    /**
     * 主体
     * @return
     */
    String getPrincipal();

    /**
     * 标识
     * @return
     */
    String getToken();
}

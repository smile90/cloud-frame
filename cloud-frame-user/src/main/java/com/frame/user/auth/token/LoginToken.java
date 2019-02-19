package com.frame.user.auth.token;

import java.io.Serializable;

public interface LoginToken extends Serializable {

    /**
     * 主体
     * @return
     */
    String getPrincipal();

    /**
     * 证书
     * @return
     */
    String getCredentials();
}

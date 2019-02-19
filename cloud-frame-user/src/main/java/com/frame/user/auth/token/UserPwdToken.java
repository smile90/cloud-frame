package com.frame.user.auth.token;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户表单Token
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Slf4j
@Data
public class UserPwdToken implements LoginToken {

    private String username;
    private String password;
    private String validCode;
    private String sessionId;
    private String deviceSource;
    private boolean rememberMe;

    public UserPwdToken() {}

    public UserPwdToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserPwdToken(String username, String password, String validCode) {
        this.username = username;
        this.password = password;
        this.validCode = validCode;
    }

    public UserPwdToken(String username, String password, String validCode, boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.validCode = validCode;
        this.rememberMe = rememberMe;
    }

    @Override
    @JSONField(serialize = false)
    public String getPrincipal() {
        return username;
    }

    @Override
    @JSONField(serialize = false)
    public String getCredentials() {
        return password;
    }
}

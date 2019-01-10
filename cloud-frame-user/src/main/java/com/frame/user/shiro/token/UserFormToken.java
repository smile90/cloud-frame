package com.frame.user.shiro.token;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户表单Token，增加了验证码
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Slf4j
public class UserFormToken extends UsernamePasswordToken {

    @Setter
    @Getter
    /*验证码*/
    private String validCode;

    public UserFormToken() {}

    public UserFormToken(String username, String password, String validCode) {
        super(username, password);
        this.validCode = validCode;
    }

    public UserFormToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    public UserFormToken(String username, String password, boolean rememberMe, String validCode) {
        super(username, password, rememberMe);
        this.validCode = validCode;
    }
}

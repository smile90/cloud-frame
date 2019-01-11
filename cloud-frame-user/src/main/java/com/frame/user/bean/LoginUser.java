package com.frame.user.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户
 */
@Getter
@Setter
@ToString
public class LoginUser implements Serializable {

    private String username;
    private String password;
    private String deviceSource;
    private String validCode;
    private boolean rememberMe;

}

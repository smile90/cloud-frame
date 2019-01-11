package com.frame.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 * @author: duanchangqing90
 * @date: 2019/01/11
 */
@Data
public class UserInfo implements Serializable {

    /*用户名*/
    private String username;
    /*真实姓名*/
    private String realname;

    public UserInfo(String username, String realname) {
        this.username = username;
        this.realname = realname;
    }
}

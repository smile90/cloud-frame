package com.frame.oauth.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frame.boot.base.enums.DataStatus;
import com.frame.boot.base.enums.UserStatus;
import com.frame.boot.spring.validate.impl.PhoneNo;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户实体
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Data
public class SysUser implements Serializable {

    protected Long id;

    protected Long optimistic = 0L;

    protected String status = DataStatus.NORMAL.name();

    /*用户来源*/
    private String userSource;
    /*来源ID*/
    private String sourceId;
    /*用户id*/
    private String userId;
    /*用户编号*/
    private String userNo;
    /*用户名*/
    @NotNull(message = "{user.user.username.NotNull}")
    private String username;
    /*邮箱*/
//    @NotNull(message = "{user.user.email.NotNull}")
    @Email
    private String email;
    /*手机号*/
//    @NotNull(message = "{user.user.phoneNo.NotNull}")
    @PhoneNo
    private String phoneNo;
    /*真实姓名*/
    @NotNull(message = "{user.user.realname.NotNull}")
    private String realname;
    /*密码*/
    @JsonIgnore
    private String password;
    /*类别*/
    private String typeCode;
    /*状态*/
    private UserStatus userStatus;

}
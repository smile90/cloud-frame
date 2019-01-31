package com.frame.user.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frame.common.frame.base.enums.UserStatus;
import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

/**
 * 用户实体
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Data
public class SysUser extends BaseModel {
    /*用户id*/
    private String userId;
    /*用户编号*/
    private String userNo;
    /*用户名*/
    private String username;
    /*邮箱*/
    private String email;
    /*手机号*/
    private String phoneNo;
    /*真实姓名*/
    private String realname;
    /*密码*/
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;
    /*类别*/
    private String typeCode;
    /*状态*/
    private UserStatus userStatus;

}
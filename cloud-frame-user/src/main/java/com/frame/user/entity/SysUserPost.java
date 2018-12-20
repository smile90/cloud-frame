package com.frame.user.entity;


import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysUserPost extends BaseModel {

    private String username;
    private String postCode;

}
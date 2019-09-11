package com.frame.user.entity;


import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysUserOrg extends BaseModel {

    private String username;
    private String orgCode;

}
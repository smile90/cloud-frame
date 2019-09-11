package com.frame.user.entity;


import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysUserRole extends BaseModel {

    private String username;
    private String roleCode;

}
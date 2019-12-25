package com.frame.user.entity;


import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysUserRole extends BaseModel {

    private String userId;
    private String roleCode;

}
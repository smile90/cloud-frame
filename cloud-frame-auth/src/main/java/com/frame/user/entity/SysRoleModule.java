package com.frame.user.entity;

import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysRoleModule extends BaseModel {

    private String roleCode;
    private String moduleCode;
}
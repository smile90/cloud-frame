package com.frame.user.entity;

import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysMenuModule extends BaseModel {

    private String menuCode;
    private String moduleCode;
}
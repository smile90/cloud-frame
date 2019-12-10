package com.frame.user.entity;

import com.frame.boot.base.enums.YesNo;
import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

/**
 * 系统功能
 */
@Data
public class SysFunction extends BaseModel {

    private String typeCode;
    private String moduleCode;
    private String code;
    private String name;
    private YesNo validate;
    private YesNo useable;
    private String httpMethod;
    private String url;
    private Integer orders;
}
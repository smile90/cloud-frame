package com.frame.user.entity;


import com.frame.boot.base.enums.YesNo;
import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysRole extends BaseModel {

    private String typeCode;
    @NotNull(message = "{user.role.code.NotNull}")
    private String code;
    @NotNull(message = "{user.role.name.NotNull}")
    private String name;
    @NotNull(message = "{user.role.useable.NotNull}")
    private YesNo useable;
    private Integer orders;

}
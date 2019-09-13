package com.frame.user.entity;


import com.frame.common.frame.base.enums.YesNo;
import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysOrg extends BaseModel {

    private String typeCode;
    private String parentCode;
    private String parentCodes;
    private String code;
    private String name;
    private YesNo useable;

}
package com.frame.user.entity;


import com.frame.boot.base.enums.YesNo;
import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class SysPost extends BaseModel {

    private String typeCode;
    private String code;
    private String name;
    private YesNo useable;
    private Integer orders;

}
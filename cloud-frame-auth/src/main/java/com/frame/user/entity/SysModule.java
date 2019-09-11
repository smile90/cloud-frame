package com.frame.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.frame.common.frame.base.enums.YesNo;
import com.frame.mybatis.entity.BaseModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysModule extends BaseModel {

    private String typeCode;
    private String parentCode;
    private String parentCodes;
    private String code;
    private String name;
    private YesNo validate;
    private YesNo useable;
    private String url;
    private Integer orders;

    @TableField(exist = false)
    private List<SysFunction> functions = new ArrayList<>();
}
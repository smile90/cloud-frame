package com.frame.boot.mybatis.entity;

import com.frame.boot.base.enums.DataStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * 基础模型属性
 * @author:changqing.duan
 * @date:2017-03-02 下午8:50
 */
@Data
public abstract class BaseFiledModel extends BaseModel {

    private static final long serialVersionUID = -1L;

    protected DataStatus status = DataStatus.NORMAL;

    protected String createUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    protected String updateUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;
}

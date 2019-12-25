package com.frame.boot.mybatis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.frame.boot.spring.validate.SaveGroup;
import com.frame.boot.spring.validate.UpdateGroup;
import com.frame.boot.base.enums.DataStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;


/**
 * 基础模型属性
 * @author:changqing.duan
 * @date:2017-03-02 下午8:50
 */
@Data
public abstract class BaseModel extends Model implements Serializable {

    private static final long serialVersionUID = -1L;

    @JSONField(serializeUsing= ToStringSerializer.class) // layui接受long时，有精度丢失
    @TableId
    @NotNull(groups = {UpdateGroup.class}, message = "sys.base.id.NotNull")
    @Null(groups = {SaveGroup.class}, message = "sys.base.id.Null")
    protected Long id;

    @Version
    protected Long optimistic = 0L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

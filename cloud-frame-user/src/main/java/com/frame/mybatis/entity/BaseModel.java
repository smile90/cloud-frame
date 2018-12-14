package com.frame.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础模型属性
 * @author:changqing.duan
 * @date:2017-03-02 下午8:50
 */
@Data
public class BaseModel extends Model implements Serializable {

	@TableId
	protected Long id;

	@Version
	protected Long optimistic = 0L;

	protected String status;

	protected String description;

	protected String createUser;

	protected Date createTime;

	protected String updateUser;

	protected Date updateTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}

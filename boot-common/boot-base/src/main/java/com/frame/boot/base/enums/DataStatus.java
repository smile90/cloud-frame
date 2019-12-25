package com.frame.boot.base.enums;


/**
 * 数据状态
 * @author duancq
 * 2014-11-1 上午8:35:55
 */
public enum DataStatus {

	NORMAL("NORMAL", "正常"),
	DISABLED("DISABLED", "禁用"),
	DELETED("DELETED", "删除");

	private String code;
	private String text;
	private DataStatus(String code, String text) {
		this.code = code;
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
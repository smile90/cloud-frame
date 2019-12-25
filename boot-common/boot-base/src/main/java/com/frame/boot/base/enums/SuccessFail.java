package com.frame.boot.base.enums;

/**
 * 成功/失败枚举
 * @author duancq
 * 2014-11-29 上午9:34:39
 */
public enum SuccessFail {
	
	INIT("INIT", "初始化"),
	SUCCESS("SUCCESS", "成功"),
	FAIL("FAIL", "失败"),
	EXCEPTION("EXCEPTION", "异常"),
	ERROR("ERROR", "错误");

	private String code;
	private String text;
	private SuccessFail(String code, String text) {
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

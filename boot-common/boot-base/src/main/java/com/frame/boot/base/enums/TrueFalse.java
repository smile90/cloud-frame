package com.frame.boot.base.enums;

/**
 * TRUE/FALSE枚举
 * @author duancq
 * 2014-11-29 上午9:32:34
 */
public enum TrueFalse {

	TRUE("TRUE", "是"),
	FALSE("FALSE", "否");

	private String code;
	private String text;
	private TrueFalse(String code, String text) {
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

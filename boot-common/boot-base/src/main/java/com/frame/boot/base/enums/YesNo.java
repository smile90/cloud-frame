package com.frame.boot.base.enums;

/**
 * YES/NO枚举
 * @author duancq
 * 2014-11-29 上午9:32:52
 */
public enum YesNo {

	YES("YES", "是"),
	NO("NO", "否"),
	Y("Y", "是"),
	N("N", "否");

	private String code;
	private String text;
	private YesNo(String code, String text) {
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

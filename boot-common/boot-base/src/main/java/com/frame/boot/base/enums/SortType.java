package com.frame.boot.base.enums;


/**
 * 排序类别
 * @author duancq
 * 2014-11-1 上午8:35:55
 */
public enum SortType {

	ASC("ASC", "正序"),
	DESC("DESC", "倒序");

	private String code;
	private String text;
	private SortType(String code, String text) {
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
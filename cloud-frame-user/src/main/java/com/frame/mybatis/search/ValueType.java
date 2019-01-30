package com.frame.mybatis.search;


/**
 * 值类别
 * @author duancq
 * 2016年4月24日 下午2:15:33
 */
public enum ValueType {

	INTEGER("INTEGER", "整形"),
	LONG("LONG", "长整形"),
	FLOAT("FLOAT", "单精度浮点型"),
	DOUBLE("DOUBLE", "双精度浮点型"),
	STRING("STRING", "字符串"),
	DATE("DATE", "日期"),
	DATETIME("DATETIME", "日期时间"),

    LIST_INTEGER("LIST_INTEGER", "整形列表"),
    LIST_LONG("LIST_LONG", "长整形列表"),
    LIST_FLOAT("LIST_FLOAT", "单精度浮点型列表"),
    LIST_DOUBLE("LIST_DOUBLE", "双精度浮点型列表"),
    LIST_STRING("LIST_STRING", "字符列表")
	;

	private String name;
	private String text;
	private ValueType(String name, String text) {
		this.name = name;
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
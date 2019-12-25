package com.frame.boot.base.enums;

/**
 * 性别
 * @author duancq
 * 2014-11-1 上午8:37:55
 */
public enum Sex {

	MAN("MAN", "男"),
	WOMAN("WOMAN", "女"),
	UNKNOWN("UNKNOWN", "未知"),
	OTHER("OTHER", "其他");

    private String code;
    private String text;
    private Sex(String code, String text) {
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

package com.frame.boot.base.enums;

/**
 * 处理状态
 * @author duancq
 * 2016年4月4日 下午12:02:12
 */
public enum ProcessStatus {

	INIT("INIT", "初始化"),
	PENDING("PENDING", "待处理"),
	PROCESSING("PROCESSING", "处理中"),
	SUCCESS("SUCCESS", "成功"),
	FAIL("FAIL", "失败"),
	COMPLETE("COMPLETE", "完成"),
	REPEAL("REPEAL", "撤销");

	private String code;
	private String text;
	private ProcessStatus(String code, String text) {
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

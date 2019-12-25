package com.frame.boot.base.param;

import java.io.Serializable;

public class CodeParam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public CodeParam(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
}

package com.frame.boot.base.exception;

import com.frame.boot.base.interfaces.MsgResult;

/**
 * 基础异常类
 * @author duancq
 * 2015年8月10日 上午9:13:54
 */
public class BaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 错误码 */
	private String errorCode;

	/** 显示内容（提示用户） */
	private String showMsg;

    public BaseRuntimeException(String errorCode, String message) {
        this(errorCode, message, null, null);
    }

	public BaseRuntimeException(String errorCode, String message, String showMsg) {
        this(errorCode, message, showMsg, null);
	}

	public BaseRuntimeException(String errorCode, Throwable cause) {
        this(errorCode, null, null, cause);
	}

	public BaseRuntimeException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, null, cause);
	}

	public BaseRuntimeException(String errorCode, String message, String showMsg, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
		this.showMsg = showMsg;
	}

    public BaseRuntimeException(BaseRuntimeException cause) {
        this(cause.getErrorCode(), cause.getMessage(), cause.getShowMsg(), cause.getCause());
    }

    public BaseRuntimeException(MsgResult msgResult) {
        this(msgResult.getCode(), msgResult.getMsg(), msgResult.getShowMsg());
    }

    public BaseRuntimeException(MsgResult msgResult, Throwable cause) {
        this(msgResult.getCode(), msgResult.getMsg(), msgResult.getShowMsg(), cause);
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getShowMsg() {
		return showMsg;
	}

	public void setShowMsg(String showMsg) {
		this.showMsg = showMsg;
	}

	@Override
	public String getMessage() {
		return errorCode + "/" + super.getMessage();
	}

}

package com.frame.boot.mybatis.exception;


import com.frame.boot.base.exception.BaseRuntimeException;
import com.frame.boot.base.interfaces.MsgResult;

/**
 * 查询异常
 * @author duancq
 * 2013-11-16 下午11:28:58
 */
public class SearchException extends BaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public SearchException(String errorCode, String message) {
		super(errorCode, message);
	}

	public SearchException(String errorCode, String message, String showMsg) {
		super(errorCode, message, showMsg);
	}

	public SearchException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public SearchException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public SearchException(String errorCode, String message, String showMsg, Throwable cause) {
		super(errorCode, message, showMsg, cause);
	}

	public SearchException(BaseRuntimeException cause) {
		super(cause);
	}

	public SearchException(MsgResult msgResult) {
		super(msgResult);
	}

	public SearchException(MsgResult msgResult, Throwable cause) {
		super(msgResult, cause);
	}

}

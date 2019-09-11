package com.frame.user.exception;

import com.frame.common.frame.base.exception.BaseRuntimeException;
import com.frame.common.frame.base.interfaces.MsgResult;

/**
 * 权限异常
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
public class AuthException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthException(String errorCode, String message) {
        super(errorCode, message);
    }

    public AuthException(String errorCode, String message, String showMsg) {
        super(errorCode, message, showMsg);
    }

    public AuthException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public AuthException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public AuthException(String errorCode, String message, String showMsg, Throwable cause) {
        super(errorCode, message, showMsg, cause);
    }

    public AuthException(BaseRuntimeException cause) {
        super(cause);
    }

    public AuthException(MsgResult msgResult) {
        super(msgResult);
    }

    public AuthException(MsgResult msgResult, Throwable cause) {
        super(msgResult, cause);
    }
}

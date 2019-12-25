package com.frame.user.exception;

import com.frame.boot.base.exception.BaseRuntimeException;
import com.frame.boot.base.interfaces.MsgResult;

/**
 * 权限配置异常
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
public class AuthCfgException extends AuthException {

    private static final long serialVersionUID = 1L;

    public AuthCfgException(String errorCode, String message) {
        super(errorCode, message);
    }

    public AuthCfgException(String errorCode, String message, String showMsg) {
        super(errorCode, message, showMsg);
    }

    public AuthCfgException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public AuthCfgException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public AuthCfgException(String errorCode, String message, String showMsg, Throwable cause) {
        super(errorCode, message, showMsg, cause);
    }

    public AuthCfgException(BaseRuntimeException cause) {
        super(cause);
    }

    public AuthCfgException(MsgResult msgResult) {
        super(msgResult);
    }

    public AuthCfgException(MsgResult msgResult, Throwable cause) {
        super(msgResult, cause);
    }
}

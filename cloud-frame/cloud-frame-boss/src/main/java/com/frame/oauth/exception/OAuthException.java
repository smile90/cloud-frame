package com.frame.oauth.exception;

import com.frame.boot.base.exception.BaseRuntimeException;
import com.frame.boot.base.interfaces.MsgResult;

/**
 * 权限异常
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
public class OAuthException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public OAuthException(String errorCode, String message) {
        super(errorCode, message);
    }

    public OAuthException(String errorCode, String message, String showMsg) {
        super(errorCode, message, showMsg);
    }

    public OAuthException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public OAuthException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public OAuthException(String errorCode, String message, String showMsg, Throwable cause) {
        super(errorCode, message, showMsg, cause);
    }

    public OAuthException(BaseRuntimeException cause) {
        super(cause);
    }

    public OAuthException(MsgResult msgResult) {
        super(msgResult);
    }

    public OAuthException(MsgResult msgResult, Throwable cause) {
        super(msgResult, cause);
    }
}

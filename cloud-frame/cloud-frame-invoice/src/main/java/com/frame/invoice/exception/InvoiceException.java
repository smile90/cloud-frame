package com.frame.invoice.exception;

import com.frame.boot.base.exception.BaseRuntimeException;
import com.frame.boot.base.interfaces.MsgResult;

/**
 * 发票异常
 *
 * @author: duanchangqing90
 * @date: 2018/12/17
 */
public class InvoiceException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public InvoiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public InvoiceException(String errorCode, String message, String showMsg) {
        super(errorCode, message, showMsg);
    }

    public InvoiceException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public InvoiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public InvoiceException(String errorCode, String message, String showMsg, Throwable cause) {
        super(errorCode, message, showMsg, cause);
    }

    public InvoiceException(BaseRuntimeException cause) {
        super(cause);
    }

    public InvoiceException(MsgResult msgResult) {
        super(msgResult);
    }

    public InvoiceException(MsgResult msgResult, Throwable cause) {
        super(msgResult, cause);
    }
}

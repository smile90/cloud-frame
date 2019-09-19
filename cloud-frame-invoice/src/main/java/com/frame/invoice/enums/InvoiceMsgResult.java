package com.frame.invoice.enums;

import com.frame.common.frame.base.interfaces.MsgResult;
import com.frame.invoice.constant.SystemConstant;

public enum InvoiceMsgResult implements MsgResult {

    QUERY_INVOICE_FAIL(SystemConstant.SYSTEM_CODE + "0001", "query invoice error", "查询发票失败，请确认信息是否正确"),
    QUERY_INVOICE_ERROR(SystemConstant.SYSTEM_CODE + "0009", "query invoice error", "查询发票异常，请重试")
    ;

    public static final String MODULE_CODE = "0001";

    InvoiceMsgResult(String code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    private String code;
    private String msg;
    private String showMsg;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }
}

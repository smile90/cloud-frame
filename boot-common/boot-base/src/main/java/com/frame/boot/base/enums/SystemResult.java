package com.frame.boot.base.enums;

import com.frame.boot.base.interfaces.MsgResult;

/**
 * 系统结果
 */
public enum SystemResult implements MsgResult {

    SUCCESS("000000000000", "success", null),
    PARAM_ERROR("000000009998", "system param error", "系统参数错误，请重试"),
    ERROR("000000009999", "system error", "系统错误，请稍后重试");

    private String code;
    private String msg;
    private String showMsg;

    SystemResult(String code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

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

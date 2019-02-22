package com.frame.gateway;

import com.frame.common.frame.base.interfaces.MsgResult;

/**
 * 系统信息结果
 */
public enum SystemMsgResult implements MsgResult {

    SYSTEM_ERROR(SystemConstant.SYSTEM_CODE + "9999", "system error", "系统错误")
    ;


    public static final String MODULE_CODE = "9999";

    SystemMsgResult(String code, String msg, String showMsg) {
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
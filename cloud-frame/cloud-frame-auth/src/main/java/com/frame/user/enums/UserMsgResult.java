package com.frame.user.enums;

import com.frame.boot.base.interfaces.MsgResult;
import com.frame.user.constant.SystemConstant;

/**
 * 权限信息结果
 */
public enum UserMsgResult implements MsgResult {

    SYSTEM_ERROR(SystemConstant.SYSTEM_CODE + UserMsgResult.MODULE_CODE + "9999", "system error", "系统错误");

    UserMsgResult(String code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    public static final String MODULE_CODE = "0020";

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

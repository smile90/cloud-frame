package com.frame.user.enums;

/**
 * 响应结果
 */
public enum AuthResponseResult implements com.frame.common.frame.base.interfaces.ResponseResult {

    AUTH_ERROR("000000010001", "auth error", "权限错误"),
    USER_PWD_ERROR("000000010002", "user or password error", "用户名/密码错误");

    AuthResponseResult(String code, String msg, String showMsg) {
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

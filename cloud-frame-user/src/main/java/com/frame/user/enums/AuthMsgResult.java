package com.frame.user.enums;

import com.frame.common.frame.base.interfaces.MsgResult;

/**
 * 权限消息结果
 */
public enum AuthMsgResult implements MsgResult {

    LOGIN_ERROR("000000010001", "auth error", "登录错误"),
    NOT_LOGIN_ERROR("000000010002", "not login error", "用户未登录"),
    LOGIN_TIME_ERROR("000000010003", "login time error", "登录错误次数超出最大次数"),
    VALID_CODE_ERROR("000000010004", "valid code error", "验证码错误"),
    USER_PWD_ERROR("000000010005", "user or password error", "用户名/密码错误"),

    LOGOUT_ERROR("000000011000", "auth error", "退出错误"),

    AUTH_ERROR("000000012001", "auth error", "权限错误"),
    NOT_AUTH_ERROR("000000012002", "not auth error", "无操作权限")
    ;

    AuthMsgResult(String code, String msg, String showMsg) {
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

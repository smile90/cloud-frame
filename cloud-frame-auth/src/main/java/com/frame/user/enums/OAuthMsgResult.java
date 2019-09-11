package com.frame.user.enums;

import com.frame.common.frame.base.interfaces.MsgResult;
import com.frame.user.constant.SystemConstant;

/**
 * 权限信息结果
 */
public enum OAuthMsgResult implements MsgResult {

    OAUTH_REQUEST_ERROR(SystemConstant.SYSTEM_CODE + OAuthMsgResult.MODULE_CODE + "9998", "oauth request error", "权限请求错误"),
    OAUTH_ERROR(SystemConstant.SYSTEM_CODE + OAuthMsgResult.MODULE_CODE + "9999", "oauth error", "权限错误")
    ;

    OAuthMsgResult(String code, String msg, String showMsg) {
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

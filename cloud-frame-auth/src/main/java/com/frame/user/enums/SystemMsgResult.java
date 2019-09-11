package com.frame.user.enums;

import com.frame.common.frame.base.interfaces.MsgResult;
import com.frame.user.constant.SystemConstant;

/**
 * 系统信息结果
 */
public enum SystemMsgResult implements MsgResult {

    INFO_PROCESS_CFG_ERROR(SystemConstant.SYSTEM_CODE + "0001", "info process not config.", "信息处理类未配置"),
    INFO_PROCESS_CFG_SUPPORTS_ERROR(SystemConstant.SYSTEM_CODE + "0002", "info process not found supports config.", "信息处理类未找到匹配配置"),

    LOGIN_PROCESS_CFG_ERROR(SystemConstant.SYSTEM_CODE + "0011", "login process not config.", "登录处理类未配置"),
    LOGIN_PROCESS_CFG_SUPPORTS_ERROR(SystemConstant.SYSTEM_CODE + "0012", "login process not found supports config.", "登录处理类未找到匹配配置"),
    LOGOUT_PROCESS_CFG_ERROR(SystemConstant.SYSTEM_CODE + "0013", "logout process not config.", "登出处理类未配置"),
    LOGOUT_PROCESS_CFG_SUPPORTS_ERROR(SystemConstant.SYSTEM_CODE + "0014", "logout process not found supports config.", "登登出处理类未找到匹配配置"),

    LOGIN_REALMS_CFG_ERROR(SystemConstant.SYSTEM_CODE + "0021", "login realms not config.", "登录校验类未配置"),
    LOGIN_REALMS_CFG_SUPPORTS_ERROR(SystemConstant.SYSTEM_CODE + "0022", "login realms not found supports config.", "登录校验类未找到匹配配置"),

    PERMISSIONS_REALMS_CFG_ERROR(SystemConstant.SYSTEM_CODE + "0021", "permissions realms not config.", "权限校验类未配置"),
    PERMISSIONS_REALMS_CFG_SUPPORTS_ERROR(SystemConstant.SYSTEM_CODE + "0022", "permissions realms not found supports config.", "权限校验类未找到匹配配置"),

    SYSTEM_PARAM_ERROR(SystemConstant.SYSTEM_CODE + "9998", "system param", "系统参数错误，请重试"),
    SYSTEM_ERROR(SystemConstant.SYSTEM_CODE + "9999", "system error", "系统错误，请稍后重试")
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

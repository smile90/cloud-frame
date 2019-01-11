package com.frame.user.enums;

import com.frame.common.frame.base.interfaces.MsgResult;
import com.frame.user.constant.SystemConstant;

/**
 * 权限信息结果
 */
public enum AuthMsgResult implements MsgResult {

    LOGIN_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0001", "auth error", "登录错误"),
    NOT_LOGIN_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0002", "not login error", "用户未登录"),
    USER_STATUS_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0003", "user status error", "用户状态异常"),
    USER_DELETED_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0004", "user deleted error", "用户已删除"),
    USER_DISABLED_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0005", "user disabled error", "用户已禁用"),
    USER_EXPIRED_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0006", "user expired error", "用户已过期"),
    USER_LOCKED_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0007", "user locked error", "用户已锁定"),
    LOGIN_TIME_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0008", "login time error", "登录错误次数超出最大次数"),
    VALID_CODE_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0009", "valid code error", "验证码错误"),
    USER_PWD_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0010", "user or password error", "用户名/密码错误"),
    USER_PWD_PARAM_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "0012", "user or password error", "用户名/密码为空"),

    LOGOUT_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "1000", "auth error", "退出错误"),

    AUTH_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "2001", "auth error", "权限错误"),
    NOT_AUTH_ERROR(SystemConstant.SYSTEM_CODE + AuthMsgResult.MODULE_CODE + "2002", "not auth error", "无操作权限")
    ;

    AuthMsgResult(String code, String msg, String showMsg) {
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }

    public static final String MODULE_CODE = "0001";

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

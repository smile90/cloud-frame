package com.frame.boot.mybatis.enums;

import com.frame.boot.base.interfaces.MsgResult;
import com.frame.boot.mybatis.constant.SystemConstant;

/**
 * MyBatis信息结果
 */
public enum MybatisMsgResult implements MsgResult {

    SEARCH_BUILDER_ERROR(SystemConstant.SYSTEM_CODE + MybatisMsgResult.MODULE_CODE + "0001", "search builder error", "查询构建错误")
    ;

    MybatisMsgResult(String code, String msg, String showMsg) {
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

package com.frame.boot.base.bean;

import com.frame.boot.base.enums.SystemResult;
import com.frame.boot.base.interfaces.MsgResult;

import java.io.Serializable;

public class ResponseBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean success;
	private String code;
	private String msg;
	private String showMsg;
	private T content;

    public ResponseBean() {}

    public ResponseBean(boolean success) {
        this.success = success;
    }

    private ResponseBean(boolean success, String code, String msg, String showMsg, T content) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
        this.content = content;
    }

    public ResponseBean setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public ResponseBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public ResponseBean setShowMsg(String showMsg) {
        this.showMsg = showMsg;
        return this;
    }

    public T getContent() {
        return content;
    }

    public ResponseBean setContent(T content) {
        this.content = content;
        return this;
    }

    private ResponseBean(MsgResult msgResult) {
        this(msgResult, null);
    }
    private ResponseBean(MsgResult msgResult, T content) {
        this(SystemResult.SUCCESS.getCode().equalsIgnoreCase(msgResult.getCode()),
                msgResult.getCode(), msgResult.getMsg(), msgResult.getShowMsg(), content);
    }

    public static <T> ResponseBean<T> success() {
        return new ResponseBean<T>(SystemResult.SUCCESS);
    }

    public static <T> ResponseBean<T> success(MsgResult msgResult) {
        return success(msgResult, null);
    }

    public static <T> ResponseBean<T> success(MsgResult msgResult, T content) {
        return success(msgResult.getCode(), msgResult.getMsg(), msgResult.getShowMsg(), content);
    }

    public static <T> ResponseBean<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> ResponseBean<T> success(String msg, String showMsg) {
        return success(msg, showMsg, null);
    }

    public static <T> ResponseBean<T> successContent(T content) {
        return success(null, null, content);
    }

    public static <T> ResponseBean<T> success(String msg, String showMsg, T content) {
        return success(SystemResult.SUCCESS.getCode(), msg, showMsg, content);
    }

    public static <T> ResponseBean<T> success(String code, String msg, String showMsg, T content) {
        return new ResponseBean<T>(true, code, msg, showMsg, content);
    }

    public static <T> ResponseBean<T> error() {
        return new ResponseBean<T>(SystemResult.ERROR);
    }

    public static <T> ResponseBean<T> error(MsgResult msgResult) {
        return error(msgResult, null);
    }

    public static <T> ResponseBean<T> error(MsgResult msgResult, T content) {
        return error(msgResult.getCode(), msgResult.getMsg(), msgResult.getShowMsg(), content);
    }

    public static <T> ResponseBean<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> ResponseBean<T> error(String msg, String showMsg) {
        return error(msg, showMsg, null);
    }

    public static <T> ResponseBean<T> errorContent(T content) {
        return error(null, null, content);
    }

    public static <T> ResponseBean<T> error(String msg, String showMsg, T content) {
        return error(SystemResult.ERROR.getCode(), msg, showMsg, content);
    }

    public static <T> ResponseBean<T> error(String code, String msg, String showMsg, T content) {
        return new ResponseBean<T>(false, code, msg, showMsg, content);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseBean{");
        sb.append("success=").append(success);
        sb.append(", code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", showMsg='").append(showMsg).append('\'');
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
}

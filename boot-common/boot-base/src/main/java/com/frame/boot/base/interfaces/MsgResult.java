package com.frame.boot.base.interfaces;

/**
 * 异常返回结果
 */
public interface MsgResult {

    /**
     * 结果码
     * @return
     */
    String getCode();

    /**
     * 异常信息
     * @return
     */
    String getMsg();

    /**
     * 显示信息
     * @return
     */
    String getShowMsg();

}

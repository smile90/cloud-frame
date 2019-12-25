package com.frame.invoice.remote;

import com.alibaba.fastjson.JSONObject;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.invoice.bean.InvoiceBean;
import com.frame.invoice.bean.InvoiceQueryParam;

/**
 * 发票查询适配器
 *
 * @author: duanchangqing90
 * @date: 2019/9/17
 */
public interface InvoiceQueryAdapter {

    ResponseBean<JSONObject> query(InvoiceQueryParam param);

    InvoiceBean convert(JSONObject result);
}

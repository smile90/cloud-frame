package com.frame.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.common.frame.base.enums.SuccessFail;
import com.frame.invoice.bean.InvoiceBean;
import com.frame.invoice.bean.InvoiceQueryParam;
import com.frame.invoice.entity.InvoiceInfo;
import com.frame.invoice.entity.InvoiceRequest;
import com.frame.invoice.enums.InvoiceMsgResult;
import com.frame.invoice.exception.InvoiceException;
import com.frame.invoice.remote.InvoiceQueryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 发票处理类
 * @author: duanchangqing90
 * @date: 2019/9/17
 */

@Slf4j
@Service
public class InvoiceProcessService {

    @Autowired
    private InvoiceRequestService invoiceRequestService;
    @Autowired
    private InvoiceInfoService invoiceInfoService;

    @Autowired
    @Qualifier("JSSInvoiceQueryAdapter")
    private InvoiceQueryAdapter invoiceQueryAdapter;

    public InvoiceInfo query(InvoiceQueryParam param) {
        // 如果存在，直接返回
        InvoiceInfo info = invoiceInfoService.findByCodeAndNo(param.getInvoiceCode(), param.getInvoiceNo());
        if (info != null) {
            return info;
        }

        // 保存查询请求
        InvoiceRequest invoiceRequest = createRequest(param);
        try {
            // 查询
            ResponseBean<JSONObject> result = invoiceQueryAdapter.query(param);
            if (result == null) {
                updateRequest(SuccessFail.FAIL, invoiceRequest);
                throw new InvoiceException(InvoiceMsgResult.QUERY_INVOICE_ERROR);
            } else if (!result.getSuccess()) {
                // 更新内容
                invoiceRequest.setComment(result.getContent().toJSONString());
                updateRequest(SuccessFail.FAIL, invoiceRequest);
                throw new InvoiceException(InvoiceMsgResult.QUERY_INVOICE_FAIL);
            } else {
                return processResult(invoiceRequest, result);
            }
        } catch (Exception e) {
            log.error("query invoice error. param:{}", param, e);
            // 更新请求结果
            updateRequest(SuccessFail.ERROR, invoiceRequest);
            throw new InvoiceException(InvoiceMsgResult.QUERY_INVOICE_ERROR);
        }
    }

    private InvoiceInfo processResult(InvoiceRequest invoiceRequest, ResponseBean<JSONObject> result) {
        // 更新内容
        invoiceRequest.setComment(result.getContent().toJSONString());
        updateRequest(SuccessFail.SUCCESS, invoiceRequest);

        // 转换
        InvoiceBean invoiceBean = invoiceQueryAdapter.convert(result.getContent());
        // 创建发票信息
        invoiceInfoService.createInfoAndDetail(invoiceBean.getInvoiceInfo(), invoiceBean.getInvoiceDetails());
        // 返回
        return invoiceBean.getInvoiceInfo();
    }

    private InvoiceRequest createRequest(InvoiceQueryParam param) {
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoiceCode(param.getInvoiceCode());
        invoiceRequest.setInvoiceNo(param.getInvoiceNo());
        invoiceRequest.setInvoiceDate(param.getInvoiceDate());
        invoiceRequest.setNoTaxAmount(param.getNoTaxAmount());
        invoiceRequest.setRequestResult(SuccessFail.INIT.getCode());
        invoiceRequest.setCreateTime(new Date());
        invoiceRequestService.save(invoiceRequest);
        return invoiceRequest;
    }



    private InvoiceRequest updateRequest(SuccessFail status, InvoiceRequest invoiceRequest) {
        invoiceRequest.setRequestResult(status.getCode());
        return updateRequest(invoiceRequest);
    }

    private InvoiceRequest updateRequest(InvoiceRequest invoiceRequest) {
        invoiceRequest.setUpdateTime(new Date());
        invoiceRequestService.updateById(invoiceRequest);
        return invoiceRequest;
    }

}

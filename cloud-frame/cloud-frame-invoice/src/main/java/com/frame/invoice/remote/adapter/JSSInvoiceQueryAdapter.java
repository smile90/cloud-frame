package com.frame.invoice.remote.adapter;

import com.alibaba.fastjson.JSONObject;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.invoice.bean.InvoiceBean;
import com.frame.invoice.bean.InvoiceQueryParam;
import com.frame.invoice.entity.InvoiceDetail;
import com.frame.invoice.entity.InvoiceInfo;
import com.frame.invoice.remote.InvoiceQueryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 诺诺网适配器
 *
 * @author: duanchangqing90
 * @date: 2019/9/17
 */
@Slf4j
@Service("JSSInvoiceQueryAdapter")
public class JSSInvoiceQueryAdapter implements InvoiceQueryAdapter {

    private final String URL = "https://www.jss.com.cn/portal/invoiceInspection/fpcy.action";

    @Override
    public ResponseBean<JSONObject> query(InvoiceQueryParam param) {
        String invoiceDate = null;
        try {
            invoiceDate = DateFormatUtils.format(DateUtils.parseDate(param.getInvoiceDate(), "yyyyMMdd"), "yyyy-MM-dd");
        } catch (Exception e) {
            log.error("date format error. param:{}", param, e);
            throw new RuntimeException(e);
        }

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("invoiceCode", param.getInvoiceCode());
        paramMap.add("invoiceNum", param.getInvoiceNo());
        paramMap.add("invoiceDate", invoiceDate);
        paramMap.add("termCode", String.valueOf(param.getNoTaxAmount()));

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap> requestEntity = new HttpEntity(paramMap, headers);

        JSONObject result = client.postForObject(URL, requestEntity, JSONObject.class);
        if ("1000".equalsIgnoreCase(result.getString("code"))) {
            return ResponseBean.successContent(result);
        } else {
            return ResponseBean.errorContent(result);
        }
    }

    @Override
    public InvoiceBean convert(JSONObject result) {
        JSONObject invoice = result.getJSONObject("invoice");

        InvoiceInfo info = convert2Info(invoice);
        List<InvoiceDetail> details = new ArrayList<>();
        for (int i = 0; i < invoice.getJSONArray("details").size(); i++) {
            details.add(convert2Detail(info, invoice.getJSONArray("details").getJSONObject(i)));
        }
        return new InvoiceBean(info, details);
    }

    public InvoiceInfo convert2Info(JSONObject result) {
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setInvoiceNo(result.getString("invoiceNum"));
        invoiceInfo.setInvoiceCode(result.getString("invoiceCode"));
        invoiceInfo.setBuyerName(result.getString("buyerName"));
        invoiceInfo.setBuyerTaxNum(result.getString("buyerTaxNum"));
        invoiceInfo.setBuyerAccount(result.getString("buyerAccount"));
        invoiceInfo.setBuyerContact(result.getString("buyerContact"));
        invoiceInfo.setSalerName(result.getString("salerName"));
        invoiceInfo.setSalerTaxNum(result.getString("salerTaxNum"));
        invoiceInfo.setSalerAccount(result.getString("salerAccount"));
        invoiceInfo.setSalerContact(result.getString("salerContact"));
        invoiceInfo.setInvoiceDate(result.getString("invoiceDate"));
        invoiceInfo.setNoTaxAmount(result.getDouble("noTaxAmount"));
        invoiceInfo.setTaxAmount(result.getDouble("taxAmount"));
        invoiceInfo.setTotalTaxAmount(result.getDouble("totalTaxAmount"));
        invoiceInfo.setInvoiceComment(result.getString("invoiceComment"));
        invoiceInfo.setProvince(result.getString("province"));
        invoiceInfo.setInvoiceType(result.getString("invoiceType"));
        invoiceInfo.setMachineCode(result.getString("machineCode"));
        return invoiceInfo;
    }

    public InvoiceDetail convert2Detail(InvoiceInfo invoiceInfo, JSONObject json) {
        InvoiceDetail detail = new InvoiceDetail();
        detail.setInvoiceNo(invoiceInfo.getInvoiceNo());
        detail.setInvoiceCode(invoiceInfo.getInvoiceCode());
        detail.setGoodsName(json.getString("goodsName"));
        detail.setSpecifications(json.getString("specifications"));
        detail.setUnits(json.getString("measureUnits"));
        detail.setGoodsSum(json.getDouble("serialNumber"));
        detail.setGoodsPrice(json.getDouble("goodsAmount"));
        detail.setNoTaxAmount(json.getDouble("noTaxPrice"));
        detail.setTaxRate(Double.valueOf(json.getString("taxRate").replace("%", "")));
        detail.setTaxAmount(json.getDouble("goodsTaxAmount"));
        detail.setOrders(json.getInteger("numbers"));
        return detail;
    }
}

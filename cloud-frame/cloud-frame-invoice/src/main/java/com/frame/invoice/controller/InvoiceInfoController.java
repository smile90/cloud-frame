package com.frame.invoice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frame.boot.mybatis.search.SearchBuilder;
import com.frame.boot.mybatis.search.SearchType;
import com.frame.boot.mybatis.search.ValueType;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.invoice.bean.InvoiceQueryParam;
import com.frame.invoice.entity.InvoiceInfo;
import com.frame.invoice.service.InvoiceInfoService;
import com.frame.invoice.service.InvoiceProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/info")
public class InvoiceInfoController {

    @Autowired
    private InvoiceProcessService invoiceProcessService;
    @Autowired
    private InvoiceInfoService invoiceInfoService;

    @RequestMapping("/getInfo")
    public Object getInfo(@Validated InvoiceQueryParam param) {
        return invoiceProcessService.query(param);
    }

    @InitBinder("page")
    public void initUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("page.");
    }

    @GetMapping("/listPage")
    public Object initPage(@ModelAttribute("page") Page<InvoiceInfo> page, @RequestParam Map<String, String> map) {
        SearchBuilder builder = new SearchBuilder<InvoiceInfo>()
                .build("invoice_code", SearchType.EQ, ValueType.STRING, map.get("invoiceCode"))
                .build("invoice_no", SearchType.EQ, ValueType.STRING, map.get("invoiceNo"))
                .build("status", SearchType.IN, ValueType.STRING, map.get("status"));
        return ResponseBean.successContent(invoiceInfoService.page(page, builder.build()));
    }

    @GetMapping("/get/{id}")
    public Object one(@PathVariable("id") String id) {
        return ResponseBean.successContent(invoiceInfoService.getById(id));
    }


}

package com.frame.invoice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.common.frame.base.utils.EmptyUtil;
import com.frame.invoice.entity.InvoiceDetail;
import com.frame.invoice.entity.InvoiceInfo;
import com.frame.invoice.mapper.InvoiceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class InvoiceInfoService extends ServiceImpl<InvoiceInfoMapper, InvoiceInfo> {

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Transactional(rollbackFor = Exception.class)
    public void createInfoAndDetail(InvoiceInfo invoiceInfo, List<InvoiceDetail> invoiceInfoDetails) {
        Date now = new Date();
        if (invoiceInfo != null) {
            invoiceInfo.setCreateTime(now);
            save(invoiceInfo);
        }
        if (EmptyUtil.notEmpty(invoiceInfoDetails)) {
            invoiceInfoDetails.stream().forEach(d -> d.setCreateTime(now));
            invoiceDetailService.saveBatch(invoiceInfoDetails);
        }
    }

    public InvoiceInfo findByCodeAndNo(String invoiceCode, String invoiceNo) {
        return getOne(new QueryWrapper<InvoiceInfo>().eq("invoice_code", invoiceCode)
                .eq("invoice_no", invoiceNo));
    }
}

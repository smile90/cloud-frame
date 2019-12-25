package com.frame.invoice.bean;

import com.frame.invoice.entity.InvoiceDetail;
import com.frame.invoice.entity.InvoiceInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 发票bean
 *
 * @author: duanchangqing90
 * @date: 2019/9/17
 */
@Data
public class InvoiceBean implements Serializable {

    private InvoiceInfo invoiceInfo;
    private List<InvoiceDetail> invoiceDetails;

    public InvoiceBean(InvoiceInfo invoiceInfo, List<InvoiceDetail> invoiceDetails) {
        this.invoiceInfo = invoiceInfo;
        this.invoiceDetails = invoiceDetails;
    }
}

package com.frame.invoice.entity;

import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class InvoiceInfo extends BaseModel {

    private String invoiceNo;

    private String invoiceCode;

    private String buyerName;

    private String buyerTaxNum;

    private String buyerAccount;

    private String buyerContact;

    private String salerName;

    private String salerTaxNum;

    private String salerAccount;

    private String salerContact;

    private String invoiceDate;

    private Double noTaxAmount;

    private Double taxAmount;

    private Double totalTaxAmount;

    private String invoiceComment;

    private String province;

    private String invoiceType;

    private String machineCode;
}

package com.frame.invoice.entity;

import com.frame.boot.mybatis.entity.BaseModel;
import com.frame.common.frame.base.enums.SuccessFail;
import lombok.Data;

@Data
public class InvoiceRequest extends BaseModel {

    private String invoiceNo;

    private String invoiceCode;

    private String invoiceDate;

    private Double noTaxAmount;

    private String requestResult = SuccessFail.INIT.getCode();

    private String comment;
}

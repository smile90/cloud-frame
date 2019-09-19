
package com.frame.invoice.entity;

import com.frame.boot.mybatis.entity.BaseModel;
import lombok.Data;

@Data
public class InvoiceDetail extends BaseModel {

    private String invoiceNo;

    private String invoiceCode;

    private String goodsName;

    private String specifications;

    private String units;

    private Double goodsSum;

    private Double goodsPrice;

    private Double noTaxAmount;

    private Double taxRate;

    private Double taxAmount;

    private Integer orders;
}

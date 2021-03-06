package com.frame.invoice.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询参数
 *
 * @author: duanchangqing90
 * @date: 2019/9/17
 */
@Data
public class InvoiceQueryParam implements Serializable {

    @NotBlank(message = "{queryParam.invoiceCode.notNull}")
    private String invoiceCode;
    @NotBlank(message = "{queryParam.invoiceNo.notNull}")
    private String invoiceNo;

    /**
     * 开票日期：yyyyMMdd
     */
    @NotBlank(message = "{queryParam.invoiceDate.notNull}")
    private String invoiceDate;
    @NotNull(message = "{queryParam.noTaxAmount.notNull}")
    private Double noTaxAmount;

}

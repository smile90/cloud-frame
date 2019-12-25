package com.frame.invoice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvoiceInfo implements Serializable {
    private Long id;

    private Long optimistic;

    private String invoiceNo;

    private String invoiceCode;

    private String buyerName;

    private String buyerTaxNum;

    private String buyerBankName;

    private String buyerAccount;

    private String buyerContact;

    private String buyerPhone;

    private String salerName;

    private String salerTaxNum;

    private String salerBankName;

    private String salerAccount;

    private String salerContact;

    private String salerPhone;

    private String invoceDate;

    private BigDecimal noTaxAmount;

    private BigDecimal taxAmount;

    private BigDecimal totalTaxAmount;

    private String invoiceComment;

    private String province;

    private String invoiceType;

    private String machineCode;

    private Integer orders;

    private String status;

    private String description;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptimistic() {
        return optimistic;
    }

    public void setOptimistic(Long optimistic) {
        this.optimistic = optimistic;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode == null ? null : invoiceCode.trim();
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName == null ? null : buyerName.trim();
    }

    public String getBuyerTaxNum() {
        return buyerTaxNum;
    }

    public void setBuyerTaxNum(String buyerTaxNum) {
        this.buyerTaxNum = buyerTaxNum == null ? null : buyerTaxNum.trim();
    }

    public String getBuyerBankName() {
        return buyerBankName;
    }

    public void setBuyerBankName(String buyerBankName) {
        this.buyerBankName = buyerBankName == null ? null : buyerBankName.trim();
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount == null ? null : buyerAccount.trim();
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact == null ? null : buyerContact.trim();
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone == null ? null : buyerPhone.trim();
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName == null ? null : salerName.trim();
    }

    public String getSalerTaxNum() {
        return salerTaxNum;
    }

    public void setSalerTaxNum(String salerTaxNum) {
        this.salerTaxNum = salerTaxNum == null ? null : salerTaxNum.trim();
    }

    public String getSalerBankName() {
        return salerBankName;
    }

    public void setSalerBankName(String salerBankName) {
        this.salerBankName = salerBankName == null ? null : salerBankName.trim();
    }

    public String getSalerAccount() {
        return salerAccount;
    }

    public void setSalerAccount(String salerAccount) {
        this.salerAccount = salerAccount == null ? null : salerAccount.trim();
    }

    public String getSalerContact() {
        return salerContact;
    }

    public void setSalerContact(String salerContact) {
        this.salerContact = salerContact == null ? null : salerContact.trim();
    }

    public String getSalerPhone() {
        return salerPhone;
    }

    public void setSalerPhone(String salerPhone) {
        this.salerPhone = salerPhone == null ? null : salerPhone.trim();
    }

    public String getInvoceDate() {
        return invoceDate;
    }

    public void setInvoceDate(String invoceDate) {
        this.invoceDate = invoceDate == null ? null : invoceDate.trim();
    }

    public BigDecimal getNoTaxAmount() {
        return noTaxAmount;
    }

    public void setNoTaxAmount(BigDecimal noTaxAmount) {
        this.noTaxAmount = noTaxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getInvoiceComment() {
        return invoiceComment;
    }

    public void setInvoiceComment(String invoiceComment) {
        this.invoiceComment = invoiceComment == null ? null : invoiceComment.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode == null ? null : machineCode.trim();
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
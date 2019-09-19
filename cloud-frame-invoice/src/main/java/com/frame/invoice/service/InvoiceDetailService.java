package com.frame.invoice.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.invoice.entity.InvoiceDetail;
import com.frame.invoice.mapper.InvoiceDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceDetailService extends ServiceImpl<InvoiceDetailMapper, InvoiceDetail> {
}

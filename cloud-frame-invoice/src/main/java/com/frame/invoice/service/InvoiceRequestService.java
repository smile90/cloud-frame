package com.frame.invoice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.invoice.entity.InvoiceRequest;
import com.frame.invoice.mapper.InvoiceRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceRequestService extends ServiceImpl<InvoiceRequestMapper, InvoiceRequest> {
}

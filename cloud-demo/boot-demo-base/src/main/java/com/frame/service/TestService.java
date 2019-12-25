package com.frame.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Slf4j
@Service
public class TestService {

    @Cacheable(value = "test", key = "#test")
    public String test(String test) {
        log.info("test:" + test);
        return "test";
    }

    @Cacheable(value = "test1", key = "#test")
    public String test1(String test) {
        log.info("test1:" + test);
        return "test1";
    }

    @Cacheable(value = "test22", key = "#test22")
    public String test22(String test22) {
        log.info("test22:" + test22);
        return "test22";
    }
}

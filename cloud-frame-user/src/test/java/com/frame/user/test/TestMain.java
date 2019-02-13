package com.frame.user.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;

/**
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        log.info("{}", new BCryptPasswordEncoder().encode("123456"));

        log.info("{}", Duration.ofMinutes(15).getSeconds());
        log.info("{}", Duration.ofMinutes(15).toMillis());
    }

}

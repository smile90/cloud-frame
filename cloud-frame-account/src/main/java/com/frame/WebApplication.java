package com.frame;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ServletComponentScan("com.frame.cloud")
@SpringBootApplication(scanBasePackages = {"com.frame.cloud"},
        exclude = {DruidDataSourceAutoConfigure.class})
//@SpringCloudApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}

package com.frame.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis扩展属性
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis.expir", ignoreInvalidFields = true)
public class RedisExpirProperties {
    /*默认配置*/
    private Long defaultConfig;
    /*指定配置*/
    private Map<String, Long> config = new HashMap<>();
}

package com.frame.user.shiro.cache;

import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * redis缓存管理
 * @author: duanchangqing90
 * @date: 2018/12/28
 */
public class RedisCacheManager implements CacheManager {

    @Setter
    private RedisTemplate<String, Object> redisTemplate;
    @Setter
    private Duration timeout;

    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate, Duration timeout) {
        this.redisTemplate = redisTemplate;
        this.timeout = timeout;
    }

    @Override
    public Cache<String, Object> getCache(String s) throws CacheException {
        return new RedisCache(redisTemplate, timeout);
    }
}

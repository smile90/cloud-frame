package com.frame.user.shiro.cache;

import com.frame.user.constant.RedisKeyConstant;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存
 * @author: duanchangqing90
 * @date: 2018/12/28
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate<K, V> redisTemplate;
    /*默认超时一个小时*/
    private Duration timeout = Duration.ofMillis(60L);

    public ShiroRedisCache(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ShiroRedisCache(RedisTemplate<K, V> redisTemplate, Duration timeout) {
        this.redisTemplate = redisTemplate;
        if (timeout != null) {
            this.timeout = timeout;
        }
    }

    @Override
    public V get(K k) throws CacheException {
        return redisTemplate.opsForValue().get(getCacheKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(getCacheKey(k), v, timeout.getSeconds(), TimeUnit.SECONDS);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = get(k);
        redisTemplate.delete(getCacheKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        Optional.ofNullable(keys()).ifPresent(k -> redisTemplate.delete(k));
    }

    @Override
    public int size() {
        Set<K> keys = keys();
        if (keys != null && !keys.isEmpty()) {
            return keys.size();
        } else {
            return 0;
        }
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        if (keys != null && !keys.isEmpty()) {
            return redisTemplate.opsForValue().multiGet(keys());
        } else {
            return null;
        }
    }

    private K getCacheKey(Object k) {
        return (K) (RedisKeyConstant.SHIRO_CACHE_PRE + k);
    }
}

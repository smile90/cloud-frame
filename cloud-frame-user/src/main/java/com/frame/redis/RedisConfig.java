package com.frame.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis配置
 * @author: duanchangqing90
 * @date: 2018/12/14
 */
@Slf4j
@EnableCaching
@Configuration
@EnableConfigurationProperties({RedisExpirProperties.class})
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisExpirProperties redisExpirProperties;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        RedisSerializationContext.SerializationPair<String> keySerializer = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
        RedisSerializationContext.SerializationPair<Object> valueSerializer = RedisSerializationContext.SerializationPair.fromSerializer(createFastJson2JsonRedisSerializer());
        // 默认缓存配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializer)
                .serializeValuesWith(valueSerializer);
        // 默认超时时间
        if (redisExpirProperties.getDefaultConfig() != null) {
            defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(redisExpirProperties.getDefaultConfig()));
        }
        // 特殊超时时间
        final Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
        if (redisExpirProperties.getConfig() != null && !redisExpirProperties.getConfig().isEmpty()) {
            redisExpirProperties.getConfig().entrySet().stream().forEach(config -> {
                RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                        .prefixKeysWith(config.getKey())
                        .computePrefixWith(CacheKeyPrefix.simple())
                        .entryTtl(Duration.ofSeconds(config.getValue()))
                        .serializeKeysWith(keySerializer)
                        .serializeValuesWith(valueSerializer);
                cacheConfigMap.put(config.getKey(), cacheConfig);
            });
        }
        // 实例化
        if (cacheConfigMap != null && !cacheConfigMap.isEmpty()) {
            return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), defaultCacheConfig, cacheConfigMap);
        } else {
            return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), defaultCacheConfig);
        }
    }

    @Bean
    public RedisTemplate<String, ? extends Object> redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(createFastJson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public FastJson2JsonRedisSerializer createFastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer(Object.class);
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("cache异常：key=[{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("cache异常：key=[{}]", key, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("cache异常：key=[{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("cache异常", e);
            }
        };
        return cacheErrorHandler;
    }

}


package com.frame.user.service;

import com.frame.user.constant.RedisKeyConstant;
import com.frame.user.properties.AuthProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Slf4j
@Service
public class ValidCodeService {

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private DefaultKaptcha kaptchaProducer;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String createValidCode() {
        return kaptchaProducer.createText();
    }

    private void setToCache (String key, String validCode) {
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_VALID_CODE_PRE + key).set(validCode);
        redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_VALID_CODE_PRE + key).expire(authProperties.getLogin().getValidCodeTimeout().getSeconds(), TimeUnit.SECONDS);
    }

    public String createValidCode(String key) {
        String validCode = createValidCode();
        setToCache(key, validCode);
        return validCode;
    }

    public BufferedImage validCodeToImg(String validCode) {
        return kaptchaProducer.createImage(validCode);
    }

    public String getValidCode(String key) {
        return redisTemplate.boundValueOps(RedisKeyConstant.USER_LOGIN_VALID_CODE_PRE + key).get();
    }

    public void deleteValidCode(String key) {
        redisTemplate.delete(RedisKeyConstant.USER_LOGIN_VALID_CODE_PRE + key);
    }

    public boolean valid(String key, String validCode) {
        // 为空，直接返回失败
        if (!StringUtils.hasText(key) || !StringUtils.hasText(validCode)) {
            log.debug("key or validCode is null. key:{}, validCode:{}", key, validCode);
            return false;
        // 进行校验
        } else {
            String cacheValidCode = getValidCode(key);
            log.debug("key:{}, validCode:{}, cacheValidCode:{}", key, validCode, cacheValidCode);
            return StringUtils.hasText(cacheValidCode) && validCode.equalsIgnoreCase(cacheValidCode);
        }
    }
}

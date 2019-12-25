package com.frame.user.config;

import com.frame.user.properties.KaptchaProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置
 *
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaConfig {

    @Autowired
    private KaptchaProperties kaptchaProperties;

    @Bean(name = "captchaProducer")
    public DefaultKaptcha captchaProducer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", kaptchaProperties.getBorder());
        properties.setProperty("kaptcha.session.key", kaptchaProperties.getSessionKey());
        properties.setProperty("kaptcha.textproducer.font.color", kaptchaProperties.getFont().getColor());
        properties.setProperty("kaptcha.textproducer.font.size", String.valueOf(kaptchaProperties.getFont().getSize()));
        properties.setProperty("kaptcha.obscurificator.impl", kaptchaProperties.getObscurificator());
        properties.setProperty("kaptcha.noise.impl", kaptchaProperties.getNoise());
        properties.setProperty("kaptcha.image.width", String.valueOf(kaptchaProperties.getImage().getWidth()));
        properties.setProperty("kaptcha.image.height", String.valueOf(kaptchaProperties.getImage().getHeight()));
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(kaptchaProperties.getChars().getLength()));
        properties.setProperty("kaptcha.textproducer.char.space", String.valueOf(kaptchaProperties.getChars().getSpace()));
        properties.setProperty("kaptcha.background.clear.from", kaptchaProperties.getBackground().getFrom());
        properties.setProperty("kaptcha.background.clear.to", kaptchaProperties.getBackground().getTo());

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }
}
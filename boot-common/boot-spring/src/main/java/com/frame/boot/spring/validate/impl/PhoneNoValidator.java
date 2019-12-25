package com.frame.boot.spring.validate.impl;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 手机号校验
 * @author: duanchangqing90
 * @date: 2019/02/22
 */
public class PhoneNoValidator implements ConstraintValidator<PhoneNo, String> {

    public static final String REGEX_MOBILE = "^(((13[0-9])|(14[0-9])|(15[0-9])|17[0-9])|(18[0,5-9]))\\d{8}$";

    @Override
    public void initialize(PhoneNo constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(phoneNo)) {
            return true;
        }
        return Pattern.matches(REGEX_MOBILE, phoneNo);
    }
}

package com.frame.oauth.service;

import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * 封装权限用户方法
 * @author: smile
 * @date: 2019/07/15
 */
public interface UserService {

    Authentication findUser(String userSource, Object principal);
    Authentication createUser(String userSource, Object principal, Map<String, Object> map);
}

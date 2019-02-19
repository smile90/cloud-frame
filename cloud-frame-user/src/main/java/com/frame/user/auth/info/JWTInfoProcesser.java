package com.frame.user.auth.info;

import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.auth.util.JWTUtil;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 信息处理者
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
@Service
public class JWTInfoProcesser implements AuthenticationInfoProcesser {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    @CachePut(value = "auth:info:JWT", key = "#result.token", condition = "#token != null && #token.token != null", unless="#result == null")
    public AuthenticationToken saveInfo(AuthenticationInfo info) {
        AuthenticationToken token = buildToken(info);
        return token;
    }

    @Override
    @Cacheable(value = "auth:info:JWT", key = "#token.token", condition = "#token != null && #token.token != null", unless="#result == null")
    public AuthenticationInfo getInfo(AuthenticationToken token) {
        if (token == null || !StringUtils.hasText(token.getToken())
            || !StringUtils.hasText(token.getPrincipal())) {
            log.warn("get info is null. token:{}", token);
            return null;
        }
        SysUser sysUser = Optional.ofNullable(sysUserService.findByUsername(token.getPrincipal())).orElseThrow(() -> new AuthException(AuthMsgResult.USER_NOT_EXIT));
        return new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), sysUser.getRealname(),
                sysUserService.getRoles(sysUser.getUsername()), sysUserService.getPermissions(sysUser.getUsername()));
    }

    private AuthenticationToken buildToken(AuthenticationInfo info) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return jwtUtil.createAuthenticationToken(info.getPrincipal() ,info.getRealName(), jwtUtil.getDeviceSource(request), jwtUtil.getHost(request));
    }

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return UserJWTToken.class;
    }
}

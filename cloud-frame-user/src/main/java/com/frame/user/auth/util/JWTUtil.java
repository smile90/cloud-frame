package com.frame.user.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.constant.SystemConstant;
import com.frame.user.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT工具类
 * @author: duanchangqing90
 * @date: 2019/01/10
 */
@Slf4j
@Component
public class JWTUtil {

    @Autowired
    private AuthProperties authProperties;

    private static final String REALNAME_NAME = "realname";

    /**
     * 创建Token
     * @param username
     * @param realname
     * @param deviceSource
     * @return
     */
    public AuthenticationToken createAuthenticationToken(String username, String realname, String deviceSource, String host) {
        log.debug("createAuthenticationToken begin. username:{},realname:{},deviceSource:{},host:{}", username, realname, deviceSource, host);
        if (!StringUtils.hasText(username) || !StringUtils.hasText(realname)) {
            return null;
        }
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getJwt().getSecret());

        LocalDate now = LocalDate.now();
        LocalDate expires = now.plusDays(authProperties.getJwt().getLongTimeout().toDays());
        String jwt = JWT.create()
                .withSubject(username)
                .withIssuer(SystemConstant.SYSTEM_CODE)
                .withIssuedAt(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expires.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withAudience(deviceSource)
                .withClaim(REALNAME_NAME, realname)
                .sign(algorithm);
        DecodedJWT decodedJWT = JWT.decode(jwt);
        UserJWTToken token = new UserJWTToken(username, jwt);
        token.setRealname(realname);
        token.setSignature(decodedJWT.getSignature());
        token.setIssuer(decodedJWT.getIssuer());
        token.setIssuedAt(decodedJWT.getIssuedAt());
        token.setExpiresAt(decodedJWT.getExpiresAt());
        token.setAudience(decodedJWT.getAudience());
        token.setHost(host);
        log.debug("createAuthenticationToken end. username:{},realname:{},deviceSource:{},host:{},token:{}", username, realname, deviceSource, host, token);
        return token;
    }

    /**
     * 刷新Token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        return refreshToken(JWT.decode(token));
    }

    /**
     * 刷新Token
     * @param decodedJWT
     * @return
     */
    public String refreshToken(DecodedJWT decodedJWT) {
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getJwt().getSecret());

        LocalDate now = LocalDate.now();
        LocalDate expires = now.plusDays(authProperties.getJwt().getLongTimeout().toDays());
        String jwt = JWT.create()
                .withSubject(decodedJWT.getSubject())
                .withIssuer(SystemConstant.SYSTEM_CODE)
                .withIssuedAt(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expires.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withAudience(decodedJWT.getClaim(authProperties.getDevice().getDeviceSourceName()).asString())
                .withClaim(REALNAME_NAME, decodedJWT.getClaim(REALNAME_NAME).asString())
                .sign(algorithm);
        return jwt;
    }

    /**
     * 验证Token
     * @param token
     * @return
     */
    public boolean validToken(String token) throws JWTVerificationException {
        log.debug("validToken begin. token:{}", token);
        if (!StringUtils.hasText(token)) {
            log.debug("validToken error. token is empty. token:{}", token);
            return false;
        }
        // 校验Token有效期
        String username = decodedUsername(token);
        if (!StringUtils.hasText(username)) {
            log.debug("validToken error. username is empty. token:{}", token);
            return false;
        }
        // 校验Token，只要不抛异常，则校验成功
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getJwt().getSecret());
        JWT.require(algorithm).build().verify(token);
        log.debug("validToken end. token:{}", token);
        return true;
    }

    /**
     * 获取Token对象
     * @param jwt
     * @return
     */
    public AuthenticationToken decodedAuthenticationToken(String jwt, HttpServletRequest request) {
        log.debug("decodedAuthenticationToken begin. jwt:{}", jwt);
        if (!StringUtils.hasText(jwt)) {
            log.debug("decodedAuthenticationToken error. jwt is null. jwt:{}", jwt);
            return null;
        }
        DecodedJWT decodedJWT = JWT.decode(jwt);
        UserJWTToken token = new UserJWTToken(decodedJWT.getSubject(), jwt);
        token.setRealname(decodedJWT.getClaim(REALNAME_NAME).asString());
        token.setSignature(decodedJWT.getSignature());
        token.setIssuer(decodedJWT.getIssuer());
        token.setIssuedAt(decodedJWT.getIssuedAt());
        token.setExpiresAt(decodedJWT.getExpiresAt());
        token.setAudience(decodedJWT.getAudience());
        token.setHost(request.getRemoteHost());
        log.debug("decodedAuthenticationToken end. token:{}", token);
        return token;
    }

    public String decodedUsername(String token) {
        try {
            if (StringUtils.hasText(token)) {
                DecodedJWT decodedJWT = JWT.decode(token);
                return decodedJWT.getSubject();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("decodedUsername from token error. token:{}", token, e);
            return null;
        }
    }

    public String getDeviceSource(HttpServletRequest request) {
        // 先从请求头取
        String deviceSource = request.getHeader(authProperties.getDevice().getDeviceSourceName());
        if (StringUtils.hasText(deviceSource) && !"null".equalsIgnoreCase(deviceSource)) {
            return deviceSource;
        }
        // 再从请求参数中去
        deviceSource = request.getParameter(authProperties.getDevice().getDeviceSourceName());
        if (StringUtils.hasText(deviceSource) && !"null".equalsIgnoreCase(deviceSource)) {
            return deviceSource;
        }
        return null;
    }

    public String getHost(HttpServletRequest request) {
        return request.getRemoteHost();
    }

    public String getToken(HttpServletRequest request) {
        // 先从请求头取
        String token = request.getHeader(authProperties.getJwt().getTokenName());
        if (StringUtils.hasText(token) && !"null".equalsIgnoreCase(token)) {
            return token;
        }
        // 再从请求参数中去
        token = request.getParameter(authProperties.getJwt().getTokenName());
        if (StringUtils.hasText(token) && !"null".equalsIgnoreCase(token)) {
            return token;
        }
        return null;
    }
}
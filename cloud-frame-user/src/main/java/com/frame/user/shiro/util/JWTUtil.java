package com.frame.user.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.frame.user.properties.AuthProperties;
import com.frame.user.shiro.token.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
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
    public String createToken(String username, String realname, String deviceSource) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(realname)) {
            return null;
        }

        LocalDate now = LocalDate.now();
        LocalDate expires = now.plusDays(authProperties.getJwt().getLongTimeout().toDays());

        Algorithm algorithm = Algorithm.HMAC256(authProperties.getJwt().getSecret());
        //create jwt
        String jwt = JWT.create()
                .withClaim(authProperties.getDevice().getDeviceSourceName(), deviceSource)
                .withClaim(REALNAME_NAME, realname)
                .withSubject(username)
                .withIssuedAt(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expires.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
        log.debug("create token. username:{}, deviceSource:{}, token:{}", username, deviceSource, jwt);
        return jwt;
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
        //create jwt
        String jwt = JWT.create()
                .withClaim(authProperties.getDevice().getDeviceSourceName(), decodedJWT.getClaim(authProperties.getDevice().getDeviceSourceName()).asString())
                .withClaim(REALNAME_NAME, decodedJWT.getClaim(REALNAME_NAME).asString())
                .withSubject(decodedJWT.getSubject())
                .withIssuedAt(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expires.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
        return jwt;
    }

    /**
     * 验证Token
     * @param token
     * @return
     */
    public boolean validToken(String token) throws JWTVerificationException {
        if (!StringUtils.hasText(token)) {
            log.debug("validToken: token or deviceSource is empty. token:{}", token);
            return false;
        }
        // 校验Token有效期
        String username = getUsername(token);
        if (!StringUtils.hasText(username)) {
            log.debug("validToken: username is empty. token:{}", token);
            return false;
        }
        // 校验Token有效期，只要不抛异常，则校验成功
        Algorithm algorithm = Algorithm.HMAC256(authProperties.getJwt().getSecret());
        JWT.require(algorithm).build().verify(token);
        return true;
    }

    /**
     * 获取Token对象
     * @param request
     * @return
     */
    public AuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return new JWTToken(getUsername(token), token, getRealname(token), getDeviceSource(token));
    }

    public String getUsername(String token) {
        try {
            if (StringUtils.hasText(token)) {
                DecodedJWT decodedJWT = JWT.decode(token);
                return decodedJWT.getSubject();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("getUsername from token error. token:{}", token, e);
            return null;
        }
    }

    public String getRealname(String token) {
        try {
            if (StringUtils.hasText(token)) {
                DecodedJWT decodedJWT = JWT.decode(token);
                return decodedJWT.getClaim(REALNAME_NAME).asString();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("getRealname from token error. token:{}", token, e);
            return null;
        }
    }

    public String getDeviceSource(String token) {
        try {
            if (StringUtils.hasText(token)) {
                DecodedJWT decodedJWT = JWT.decode(token);
                return decodedJWT.getClaim(authProperties.getDevice().getDeviceSourceName()).asString();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("getDeviceSource from token error. token:{}", token, e);
            return null;
        }
    }

    public String getDeviceSource(HttpServletRequest request) {
        String deviceSource = request.getParameter(authProperties.getDevice().getDeviceSourceName());
        if (StringUtils.hasText(deviceSource) && !"null".equalsIgnoreCase(deviceSource)) {
            return deviceSource;
        } else {
            return null;
        }
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
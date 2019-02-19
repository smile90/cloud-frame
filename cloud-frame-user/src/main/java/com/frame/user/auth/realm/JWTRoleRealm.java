package com.frame.user.auth.realm;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.info.InfoManager;
import com.frame.user.auth.matcher.SysAuthMatcher;
import com.frame.user.auth.resource.HttpResource;
import com.frame.user.auth.resource.Resource;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.UserJWTToken;
import com.frame.user.auth.util.JWTUtil;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

/**
 * JWT校验
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
public class JWTRoleRealm implements LoginRealm, PermissionsRealm {

    @Setter
    protected InfoManager infoManager;

    @Autowired
    protected SysAuthMatcher sysAuthMatcher;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Class<? extends AuthenticationToken> getAuthenticationTokenClass() {
        return UserJWTToken.class;
    }

    /**
     * 额外对Token进行一次校验
     * @param authToken
     * @param resource
     * @throws AuthException
     */
    @Override
    public void validLogin(AuthenticationToken authToken, Resource resource) throws AuthException {
        log.debug("valid login begin. resource:{},token:{}", resource, authToken);
        if (sysAuthMatcher.matchPermitPath(resource)) {
            log.debug("valid login end. resource:{},token:{}", resource, authToken);
            return ;
        }

        // 未查询到用户，则表示未登录
        Optional.ofNullable(authToken).orElseThrow(() -> new AuthException(AuthMsgResult.NOT_LOGIN_ERROR));
        Optional.ofNullable(infoManager.getInfo(authToken.getClass(), authToken)).orElseThrow(() -> new AuthException(AuthMsgResult.NOT_LOGIN_ERROR));

        // Token有效性校验
        String token = authToken.getToken();
        try {
            jwtUtil.validToken(token);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("validToken: token error. resource:{},token:{}", resource, authToken, e);
            } else {
                log.error("validToken: token error. resource:{},token:{}. {}", resource, authToken, e.getMessage());
            }
            throw new AuthException(AuthMsgResult.NOT_LOGIN_ERROR);
        }
        log.debug("valid login end. resource:{},token:{}", resource, authToken);
    }

    @Override
    public void validPermissions(AuthenticationToken token, Resource resource) throws AuthException {
        log.debug("valid permissions begin. resource:{},token:{}", resource, token);

        if (sysAuthMatcher.matchPermitPath(resource)) {
            log.debug("valid permissions end. resource:{},token:{}", resource, token);
            return ;
        }

        String[] rolesArray = sysAuthMatcher.getPathConfig(((HttpResource) resource).getMethod(), ((HttpResource) resource).getUrl());
        // 路径无需角色
        if (rolesArray == null || rolesArray.length == 0) {
            log.debug("not access auth. resource:{}", resource);
        } else {
            AuthenticationInfo info = Optional.ofNullable(infoManager.getInfo(token.getClass(), token)).orElseThrow(() -> new AuthException(AuthMsgResult.NOT_LOGIN_ERROR));
            log.debug("valid permissions end. resource:{},roles:{},info:{},token:{}", resource, rolesArray, info, token);

            // 匹配角色：有任一角色均可访问
            boolean match = Arrays.stream(rolesArray).anyMatch(role -> info.hasRole(role));
            if (!match) {
                throw new AuthException(AuthMsgResult.NOT_AUTH_ERROR);
            }
        }
    }
}

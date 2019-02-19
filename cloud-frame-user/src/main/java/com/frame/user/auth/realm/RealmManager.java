package com.frame.user.auth.realm;

import com.frame.user.auth.resource.Resource;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.SystemMsgResult;
import com.frame.user.exception.AuthCfgException;
import com.frame.user.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 权限管理器
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
public class RealmManager {

    /*登录校验*/
    private List<LoginRealm> loginRealms = new ArrayList<>();
    /*权限校验*/
    private List<PermissionsRealm> permissionsRealms = new ArrayList<>();

    public void addLoginRealm(LoginRealm realm) {
        loginRealms.add(realm);
    }

    public void addLoginRealm(LoginRealm... realms) {
        for (LoginRealm realm : realms) {
            addLoginRealm(realm);
        }
    }

    public void addPermissionsRealm(PermissionsRealm realm) {
        permissionsRealms.add(realm);
    }

    public void addPermissionsRealm(PermissionsRealm... realms) {
        for (PermissionsRealm realm : realms) {
            addPermissionsRealm(realm);
        }
    }

    /**
     * 登录校验
     * @param token
     */
    public void volidLogin(Class<? extends AuthenticationToken> tokenClass, AuthenticationToken token, Resource resource) {
        if (loginRealms == null || loginRealms.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGIN_REALMS_CFG_ERROR);
        }
        // 只处理匹配的类别
        List<LoginRealm> realms = loginRealms.stream().filter(p -> p.getAuthenticationTokenClass().isAssignableFrom(tokenClass)).collect(Collectors.toList());
        if (realms == null || realms.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGIN_REALMS_CFG_SUPPORTS_ERROR);
        }
        try {
            for (LoginRealm realm : realms) {
                realm.validLogin(token, resource);
            }
        } catch (AuthCfgException e) {
            log.error("login valid config error.", e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.LOGIN_AUTH_ERROR);
        }
    }

    /**
     * 权限校验
     * @param token
     */
    public void volidPermissions(Class<? extends AuthenticationToken> tokenClass, AuthenticationToken token, Resource resource) {
        if (permissionsRealms == null || permissionsRealms.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.PERMISSIONS_REALMS_CFG_ERROR);
        }
        // 只处理匹配的类别
        List<PermissionsRealm> realms = permissionsRealms.stream().filter(p -> p.getAuthenticationTokenClass().isAssignableFrom(tokenClass)).collect(Collectors.toList());
        if (realms == null || realms.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.PERMISSIONS_REALMS_CFG_SUPPORTS_ERROR);
        }

        try {
            for (PermissionsRealm realm : realms) {
                realm.validPermissions(token, resource);
            }
        } catch (AuthCfgException e) {
            log.error("permissions valid config error.", e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.PERMISSIONS_AUTH_ERROR);
        }
    }
}

package com.frame.user.auth.login;

import com.frame.user.auth.info.AuthenticationInfo;
import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.auth.token.LoginToken;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.SystemMsgResult;
import com.frame.user.exception.AuthCfgException;
import com.frame.user.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录管理者
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
public class LoginManager {

    /*登录处理列表*/
    private List<LoginProcesser> loginProcessers = new ArrayList<>();
    /*登出处理列表*/
    private List<LogoutProcesser> logoutProcessers = new ArrayList<>();

    public void addLoginProcess(LoginProcesser processer) {
        loginProcessers.add(processer);
    }

    public void addLoginProcess(LoginProcesser... processers) {
        for (LoginProcesser processer : processers) {
            addLoginProcess(processer);
        }
    }
    public void addLogoutProcess(LogoutProcesser processer) {
        logoutProcessers.add(processer);
    }

    public void addLogoutProcess(LogoutProcesser... processers) {
        for (LogoutProcesser processer : processers) {
            addLogoutProcess(processer);
        }
    }

    /**
     * 登录
     * @param tokenClass
     * @param token
     * @return key：tokenClass，value：info
     */
    public AuthenticationInfo login(Class<? extends LoginToken> tokenClass, LoginToken token) {
        if (loginProcessers == null || loginProcessers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGIN_PROCESS_CFG_ERROR);
        }
        // 只处理匹配的类别
        List<LoginProcesser> processers = loginProcessers.stream().filter(p -> p.getAuthenticationTokenClass().isAssignableFrom(tokenClass)).collect(Collectors.toList());
        if (processers == null || processers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGIN_PROCESS_CFG_SUPPORTS_ERROR);
        }

        // 校验登录
        try {
            AuthenticationInfo info = null;
            for (LoginProcesser processer : processers) {
                info = processer.login(token);
            }
            return info;
        } catch (AuthCfgException e) {
            log.error("login manager config error.", e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.LOGIN_ERROR);
        }
    }

    /**
     * 退出
     * @param token
     */
    public void logout(Class<? extends AuthenticationToken> tokenClass, AuthenticationToken token) {
        if (logoutProcessers == null || logoutProcessers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGOUT_PROCESS_CFG_ERROR);
        }
        // 只处理匹配的类别
        List<LogoutProcesser> processers = logoutProcessers.stream().filter(p -> p.getAuthenticationTokenClass().isAssignableFrom(tokenClass)).collect(Collectors.toList());
        if (processers == null || processers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.LOGOUT_PROCESS_CFG_SUPPORTS_ERROR);
        }

        try {
            for (LogoutProcesser process : processers) {
                process.logout(token);
            }
        } catch (AuthCfgException e) {
            log.error("login manager config error.", e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.LOGOUT_ERROR);
        }
    }
}

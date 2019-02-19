package com.frame.user.auth.info;

import com.frame.user.auth.token.AuthenticationToken;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.enums.SystemMsgResult;
import com.frame.user.exception.AuthCfgException;
import com.frame.user.exception.AuthException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 信息管理
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Slf4j
public class InfoManager {

    /*处理列表*/
    private List<AuthenticationInfoProcesser> infoProcessers = new ArrayList<>();

    public void addInfoProcess(AuthenticationInfoProcesser processer) {
        infoProcessers.add(processer);
    }

    public void addInfoProcess(AuthenticationInfoProcesser... processers) {
        for (AuthenticationInfoProcesser processer : processers) {
            addInfoProcess(processer);
        }
    }

    private List<AuthenticationInfoProcesser> getSupports(Class<? extends AuthenticationToken> tokenClass) {
        if (infoProcessers == null || infoProcessers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.INFO_PROCESS_CFG_ERROR);
        }
        // 只处理匹配的类别
        List<AuthenticationInfoProcesser> processes = infoProcessers.stream().filter(p -> p.getAuthenticationTokenClass().isAssignableFrom(tokenClass)).collect(Collectors.toList());
        if (processes == null || processes.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.INFO_PROCESS_CFG_SUPPORTS_ERROR);
        }
        return processes;
    }

    /**
     * 保存信息
     * @param info
     */
    public Map<AuthenticationToken, AuthenticationInfo> saveInfo(AuthenticationInfo info) {
        if (infoProcessers == null || infoProcessers.isEmpty()) {
            throw new AuthCfgException(SystemMsgResult.INFO_PROCESS_CFG_ERROR);
        }
        Optional.ofNullable(info).orElseThrow(() -> new AuthException(AuthMsgResult.LOGIN_ERROR));

        try {
            Map<AuthenticationToken, AuthenticationInfo> map = new HashMap<>();
            for (AuthenticationInfoProcesser process : infoProcessers) {
                try {
                    // 保存信息
                    AuthenticationToken token = Optional.ofNullable(process.saveInfo(info)).orElseThrow(() -> new AuthException(AuthMsgResult.LOGIN_ERROR));
                    map.put(token, info);
                } catch (Exception e) {
                    log.error("info manager save info error. info:{}", info, e);
                }
            }
            return map;
        } catch (AuthCfgException e) {
            log.error("info manager config error. info:{}", info, e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.LOGIN_ERROR);
        }
    }

    /**
     * 获取信息
     * @param token
     * @return
     */
    public AuthenticationInfo getInfo(Class<? extends AuthenticationToken> tokenClass, AuthenticationToken token) {
        List<AuthenticationInfoProcesser> processes = getSupports(tokenClass);

        try {
            List<AuthenticationInfo> infos = new ArrayList<>();
            for (AuthenticationInfoProcesser process : processes) {
                Optional.ofNullable(process.getInfo(token)).ifPresent(i -> infos.add(i));
            }
            // 获取第一个
            return infos.size() > 0 ? infos.get(0) : null;
        } catch (AuthCfgException e) {
            log.error("info manager config error.", e);
            throw e;
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthException(AuthMsgResult.LOGIN_ERROR);
        }
    }
}

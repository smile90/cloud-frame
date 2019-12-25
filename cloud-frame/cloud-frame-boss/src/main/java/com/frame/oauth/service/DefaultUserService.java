package com.frame.oauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.boot.base.enums.UserStatus;
import com.frame.oauth.exception.OAuthException;
import com.frame.oauth.beans.SysUser;
import com.frame.remote.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 默认实现，只取用户名
 *
 * @author: smile
 * @date: 2019/07/15
 */
@Service
@Slf4j
public class DefaultUserService implements UserService {

    @Autowired
    private RemoteUserService remoteUserService;

    private Set<SimpleGrantedAuthority> defaultAuthorities = new HashSet<>();

    {
        defaultAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication findUser(String userSource, Object principal) {
        ResponseBean responseBean = remoteUserService.findBySource(userSource, String.valueOf(principal));
        log.debug("find user. userSource:{},principal:{},response:{}", userSource, principal, responseBean);
        if (responseBean.getSuccess()) {
            JSONObject result = JSON.parseObject(JSON.toJSONString(responseBean.getContent()));
            if (result.getJSONArray("permissions") != null) {
                Set<SimpleGrantedAuthority> authorities = new HashSet<>();
                for (Object permission : result.getJSONArray("permissions")) {
                    authorities.add(new SimpleGrantedAuthority(String.valueOf(permission)));
                }
                return new UsernamePasswordAuthenticationToken(result.getObject("user", SysUser.class).getUsername(), null, authorities);
            } else {
                return new UsernamePasswordAuthenticationToken(result.getObject("user", SysUser.class).getUsername(), null, defaultAuthorities);
            }
        } else {
            return null;
        }
    }

    @Override
    public Authentication createUser(String userSource, Object principal, Map<String, Object> map) {
        SysUser user = new SysUser();
        user.setUserSource(userSource);
        user.setSourceId(String.valueOf(principal));
        user.setUsername(String.valueOf(principal));
        user.setRealname((String) map.get("name"));
        user.setUserStatus(UserStatus.NORMAL);
        ResponseBean responseBean = remoteUserService.save(user);
        if (responseBean.getSuccess()) {
            return new UsernamePasswordAuthenticationToken(principal, null, defaultAuthorities);
        } else {
            throw new OAuthException(responseBean.getCode(), responseBean.getMsg(), responseBean.getShowMsg());
        }
    }
}

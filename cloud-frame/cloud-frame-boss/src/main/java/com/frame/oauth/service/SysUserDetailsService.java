package com.frame.oauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.boot.base.bean.ResponseBean;
import com.frame.oauth.enums.OAuthMsgResult;
import com.frame.oauth.exception.OAuthException;
import com.frame.oauth.beans.SysUser;
import com.frame.oauth.beans.UserDetails;
import com.frame.remote.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户服务
 *
 * @author: duanchangqing90
 * @date: 2019/03/07
 */
@Slf4j
@Service("sysUserDetailsService")
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseBean responseBean = remoteUserService.findByUsername(username);
        log.debug("find user. username:{},response:{}", username, responseBean);
        JSONObject result = JSON.parseObject(JSON.toJSONString(responseBean.getContent()));
        if (result.getJSONArray("permissions") != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (Object permission : result.getJSONArray("permissions")) {
                authorities.add(new SimpleGrantedAuthority((String) permission));
            }
            SysUser sysUser = result.getObject("user", SysUser.class);
            return new UserDetails(sysUser, authorities);
        } else {
            throw new OAuthException(OAuthMsgResult.OAUTH_GET_USER_INF_ERROR);
        }
    }
}
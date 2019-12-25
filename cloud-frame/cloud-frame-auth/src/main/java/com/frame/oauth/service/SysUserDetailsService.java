package com.frame.oauth.service;

import com.frame.oauth.beans.UserDetails;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务
 *
 * @author: duanchangqing90
 * @date: 2019/03/07
 */
@Service("sysUserDetailsService")
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser != null) {
            Set<SimpleGrantedAuthority> authorities = Optional.ofNullable(sysUserService.getRoles(sysUser.getUserId())).orElse(new HashSet<>()).stream()
                    .map(code -> new SimpleGrantedAuthority(code))
                    .collect(Collectors.toSet());
            return new UserDetails(sysUser, authorities);
        } else {
            throw new AuthException(AuthMsgResult.USER_PWD_ERROR);
        }
    }
}
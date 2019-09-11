package com.frame.oauth.service;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务
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
            Set<SimpleGrantedAuthority> authorities = Optional.ofNullable(sysUserService.getRoles(username)).orElse(new HashSet<>()).stream()
                                .map(code -> new SimpleGrantedAuthority(code))
                                .collect(Collectors.toSet());
            User user = new User(sysUser.getUsername(), sysUser.getPassword(),
                    UserStatus.NORMAL == sysUser.getUserStatus(),
                    UserStatus.EXPIRED != sysUser.getUserStatus(),
                    UserStatus.CREDENTIALS_EXPIRED != sysUser.getUserStatus(),
                    UserStatus.LOCKED != sysUser.getUserStatus(),
                    authorities);
            return user;
        } else {
            throw new AuthException(AuthMsgResult.USER_PWD_ERROR);
        }
    }
}
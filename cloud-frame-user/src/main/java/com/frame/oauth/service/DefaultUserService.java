package com.frame.oauth.service;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.common.frame.base.utils.EmptyUtil;
import com.frame.user.entity.SysUser;
import com.frame.user.service.SysUserService;
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
 * @author: smile
 * @date: 2019/07/15
 */
@Service
public class DefaultUserService implements UserService {

    @Autowired
    private SysUserService sysUserService;

    private Set<SimpleGrantedAuthority> defaultAuthorities = new HashSet<>();
    {
        defaultAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication findUser(String userSource, Object principal) {
        SysUser user = sysUserService.findBySource(userSource, String.valueOf(principal));
        if (user != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            Set<String> permissions = sysUserService.getPermissions(user.getUsername());
            if (EmptyUtil.notEmpty(permissions)) {
                permissions.stream().forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));
                return new UsernamePasswordAuthenticationToken(principal, null, authorities);
            } else {
                return new UsernamePasswordAuthenticationToken(principal, null, defaultAuthorities);
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
        user.setRealname(getName(map));
        user.setUserStatus(UserStatus.NORMAL);
        sysUserService.save(user);
        return new UsernamePasswordAuthenticationToken(principal, null, defaultAuthorities);
    }

    private String getName(Map<String, Object> map) {
        return (String) map.get("name");
    }
}

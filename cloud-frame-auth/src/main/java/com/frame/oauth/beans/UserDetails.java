package com.frame.oauth.beans;

import com.frame.common.frame.base.enums.UserStatus;
import com.frame.user.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@ToString
public class UserDetails extends User {

    public UserDetails(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(),
                UserStatus.NORMAL == sysUser.getUserStatus(),
                UserStatus.EXPIRED != sysUser.getUserStatus(),
                UserStatus.CREDENTIALS_EXPIRED != sysUser.getUserStatus(),
                UserStatus.LOCKED != sysUser.getUserStatus(),
                authorities);
        this.userSource = sysUser.getUserSource();
        this.sourceId = sysUser.getSourceId();
        this.userId = sysUser.getUserId();
        this.userNo = sysUser.getUserNo();
    }

    /*用户来源*/
    @Setter
    @Getter
    private String userSource;
    /*来源ID*/
    @Setter
    @Getter
    private String sourceId;
    /*用户id*/
    @Setter
    @Getter
    private String userId;
    /*用户编号*/
    @Setter
    @Getter
    private String userNo;

}

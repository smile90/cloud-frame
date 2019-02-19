package com.frame.user.config;

import com.frame.user.auth.info.InfoManager;
import com.frame.user.auth.info.JWTInfoProcesser;
import com.frame.user.auth.login.LoginManager;
import com.frame.user.auth.login.LoginProcesser;
import com.frame.user.auth.login.LogoutProcesser;
import com.frame.user.auth.realm.JWTRoleRealm;
import com.frame.user.auth.realm.RealmManager;
import com.frame.user.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 * @author: duanchangqing90
 * @date: 2018/12/27
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfig {

    @Autowired
    private LoginProcesser userPwdLoginProcesser;
    @Autowired
    private LogoutProcesser userJWTLogoutProcesser;

    @Autowired
    private JWTInfoProcesser jwtInfoProcesser;


    @Bean
    public LoginManager loginManager() {
        LoginManager loginManager = new LoginManager();
        // 用户名密码登录
        loginManager.addLoginProcess(userPwdLoginProcesser);
        // jwt登出
        loginManager.addLogoutProcess(userJWTLogoutProcesser);
        return loginManager;
    }

    @Bean
    public InfoManager infoManager() {
        InfoManager infoManager = new InfoManager();
        // 保存：JWT
        infoManager.addInfoProcess(jwtInfoProcesser);
        return infoManager;
    }

    @Bean
    public JWTRoleRealm jWTRoleRealm() {
        JWTRoleRealm realm = new JWTRoleRealm();
        realm.setInfoManager(infoManager());
        return realm;
    }

    @Bean
    public RealmManager realmManager() {
        RealmManager realmManager = new RealmManager();
        // 登录校验：JWT
        realmManager.addLoginRealm(jWTRoleRealm());
        // 权限校验：JWT
        realmManager.addPermissionsRealm(jWTRoleRealm());
        return realmManager;
    }
}
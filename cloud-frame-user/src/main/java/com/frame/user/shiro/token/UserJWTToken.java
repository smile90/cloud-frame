package com.frame.user.shiro.token;

import com.alibaba.fastjson.annotation.JSONField;
import com.auth0.jwt.interfaces.Claim;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jwt令牌
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
@Data
public class UserJWTToken implements AuthenticationToken {

    /*客户标识（可以是用户名、app id等等）*/
    private String clientKey;
    /*真实名称*/
    private String realname;
    /*令牌*/
    private String token;
    /*签名*/
    private String signature;
    /*签发者(JWT令牌此项有值)*/
    private String issuer;
    /*签发时间*/
    private Date issuedAt;
    /*失效时间*/
    private Date ExpiresAt;
    /*接收方(JWT令牌此项有值)*/
    private List<String> audience;
    /*其他参数*/
    private Map<String, String> claims = new HashMap<>();
    /*客户端IP*/
    private String host;

    public UserJWTToken(String clientKey, String token) {
        this.clientKey = clientKey;
        this.token = token;
    }

    public void addClaim(String key, String value) {
        claims.put(key, value);
    }

    @Override
    @JSONField(serialize = false)
    public Object getPrincipal() {
        return this.clientKey;
    }

    @Override
    @JSONField(serialize = false)
    public Object getCredentials() {
        return this.token;
    }
}

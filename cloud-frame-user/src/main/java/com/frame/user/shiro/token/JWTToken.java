package com.frame.user.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwt令牌
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
@Data
public class JWTToken implements AuthenticationToken {

    /*客户标识（可以是用户名、app id等等）*/
    private String clientKey;
    /*令牌*/
    private String token;
    /*真实姓名*/
    private String realname;
    /*设备来源*/
    private String deviceSource;

//    /*消息摘要*/
//    private String digest;
//    /*签发者(JWT令牌此项有值)*/
//    private String issuer;
//    /*签发时间*/
//    private Date issuedAt;
//    /*接收方(JWT令牌此项有值)*/
//    private String audience;
//    /*时间戳*/
//    private String timeStamp;
//    /*访问参数*/
//    private Map<String, String[]> parameters;
//    /*客户端IP*/
//    private String host;

    public JWTToken(String clientKey) {
        this.clientKey = clientKey;
    }

    public JWTToken(String clientKey, String token, String realname, String deviceSource) {
        this.clientKey = clientKey;
        this.token = token;
        this.realname = realname;
        this.deviceSource = deviceSource;
    }

    @Override
    public Object getPrincipal() {
        return this.clientKey;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}

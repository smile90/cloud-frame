package com.frame.user.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * jwt令牌
 * @author: duanchangqing90
 * @date: 2019/01/03
 */
@Data
public class JwtToken implements AuthenticationToken {

    /*客户标识（可以是用户名、app id等等）*/
    private String clientKey;
    /*消息摘要*/
    private String digest;
    /*时间戳*/
    private String timeStamp;
    /*访问参数*/
    private Map<String, String[]> parameters;
    /*客户端IP*/
    private String host;

    @Override
    public Object getPrincipal() {
        return this.clientKey;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }
}

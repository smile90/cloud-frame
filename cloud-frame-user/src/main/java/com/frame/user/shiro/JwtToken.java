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

    private String clientKey;// 客户标识（可以是用户名、app id等等）
    private String digest;// 消息摘要
    private String timeStamp;// 时间戳
    private Map<String, String[]> parameters;// 访问参数
    private String host;// 客户端IP


    @Override
    public Object getPrincipal() {
        return this.clientKey;
    }

    @Override
    public Object getCredentials() {
        return Boolean.TRUE;
    }
}

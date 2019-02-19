package com.frame.user.auth.resource;

import lombok.Data;

/**
 * Http资源
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@Data
public class HttpResource implements Resource {

    /*请求方法类型*/
    private String method;
    /*URL地址*/
    private String url;

    public HttpResource(String method, String url) {
        this.method = method;
        this.url = url;
    }
}

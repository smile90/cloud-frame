package com.frame.client;

import com.alibaba.fastjson.JSONObject;
import com.frame.common.frame.base.bean.ResponseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 权限客户端
 * @author: duanchangqing90
 * @date: 2019/02/14
 */
@FeignClient(value = AuthClient.SERVICE_ID)
public interface AuthClient {

    String SERVICE_ID = "cloud-frame-user";

    /**
     * 登录
     * @param data
     * @return
     */
    @PostMapping(path = "/auth/login")
    ResponseBean login(@RequestBody JSONObject data);

    /**
     * 登出
     * @param data
     * @return
     */
    @PostMapping(path = "/auth/logout")
    ResponseBean logout(@RequestBody JSONObject data);

    /**
     * 校验登录
     * @param data
     * @return
     */
    @PostMapping(path = "/auth/validLogin/jwt")
    ResponseBean validLoginJWT(@RequestBody JSONObject data);

    /**
     * 校验权限
     * @param data
     * @return
     */
    @PostMapping(path = "/auth/validAuth/jwt")
    ResponseBean validAuthJWT(@RequestBody JSONObject data);

    /**
     * 获取信息
     * @param data
     * @return
     */
    @PostMapping(path = "/auth/getInfo/jwt")
    ResponseBean getInfoJWT(@RequestBody JSONObject data);

}
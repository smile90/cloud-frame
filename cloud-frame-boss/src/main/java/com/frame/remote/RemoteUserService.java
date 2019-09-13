package com.frame.remote;

import com.frame.common.frame.base.bean.ResponseBean;
import com.frame.oauth.beans.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cloud-frame-auth")
public interface RemoteUserService {

    @GetMapping("/pub/user/findByUsername/{username}")
    ResponseBean findByUsername(@PathVariable("username") String username);

    @GetMapping("/pub/user/findBySource/{source}/{sourceId}")
    ResponseBean findBySource(@PathVariable("source") String source, @PathVariable("sourceId") String sourceId);

    @PostMapping("/pub/user/save")
    ResponseBean save(@RequestBody SysUser user);

}

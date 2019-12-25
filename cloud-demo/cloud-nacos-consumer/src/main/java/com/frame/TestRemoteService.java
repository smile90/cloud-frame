package com.frame;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cloud-nacos-provider")
public interface TestRemoteService {

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    String hello(@PathVariable("name") String name);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String test(@RequestParam("name") String name);

    @RequestMapping(value = "/testProperties", method = RequestMethod.GET)
    String testProperties();
}

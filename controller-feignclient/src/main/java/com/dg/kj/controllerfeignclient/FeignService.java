package com.dg.kj.controllerfeignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "utd",url = "http://utdallas.edu")
//@FeignClient(value = "internal-service")
public interface FeignService {
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public String getData();
}

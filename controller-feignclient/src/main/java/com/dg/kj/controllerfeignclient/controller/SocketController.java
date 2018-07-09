package com.dg.kj.controllerfeignclient.controller;

import com.dg.kj.controllerfeignclient.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class SocketController {
    @Autowired
    FeignService feignService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String getData() {
        return feignService.getData();
    }
}

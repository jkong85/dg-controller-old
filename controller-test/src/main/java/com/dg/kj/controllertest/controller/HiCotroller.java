package com.dg.kj.controllertest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiCotroller {
    @RequestMapping(value="/hi")
    public String index(@RequestParam String name){
        return "Hi " + name;
    }
}

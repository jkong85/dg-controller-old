package com.dg.kj.controllerzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ControllerZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControllerZuulApplication.class, args);
    }
}

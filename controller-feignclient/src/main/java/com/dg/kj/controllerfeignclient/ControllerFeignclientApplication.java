package com.dg.kj.controllerfeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ControllerFeignclientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ControllerFeignclientApplication.class, args);
    }
}

package com.dg.kj.controllerkubeclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ControllerKubeclientApplication {
    private static DevK8sApiService k8sApiService;
    public static void main(String[] args) {
        //SpringApplication.run(ControllerKubeclientApplication.class, args);

        k8sApiService = new DevK8sApiService();
        k8sApiService.init();
        k8sApiService.listNamespace();
        k8sApiService.listNode();
    }
}

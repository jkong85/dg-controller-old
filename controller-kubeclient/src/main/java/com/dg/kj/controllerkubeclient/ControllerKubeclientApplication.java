package com.dg.kj.controllerkubeclient;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ControllerKubeclientApplication {
    private static DevK8sApiService k8sApiService;  // Fabric8
    private static ApiClient k8sApiClient;
    public static void main(String[] args)throws IOException, ApiException {
        //SpringApplication.run(ControllerKubeclientApplication.class, args);

        // For Fabirc Java Client
//        k8sApiService = new DevK8sApiService();
//        k8sApiService.init();
//        k8sApiService.listNamespace();
//        k8sApiService.listNode();

//        ApiClient client = Config.defaultClient();
//        Configuration.setDefaultApiClient(client);
//
//
//        CoreV1Api api = new CoreV1Api();
//        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
//        for (V1Pod item : list.getItems()) {
//            System.out.println(item.getMetadata().getName());
//        }

//        k8sApiClient = Config.defaultClient();
//        Configuration.setDefaultApClient(k8sApiClient);

    }
}

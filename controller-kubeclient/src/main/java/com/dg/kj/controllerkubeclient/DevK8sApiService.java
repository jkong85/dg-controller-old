package com.dg.kj.controllerkubeclient;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.NodeList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;


public class DevK8sApiService {
    //k8s api封装库调用
    private static KubernetesClient kubernetesClient;
    private static Config config;
    private static String masterIP = "http://192.168.65.3:443";

    //初始化 - 连接k8s api server
    public static void init(){
        String initResult = "Init Failed.";
        try {
            config = new ConfigBuilder().withMasterUrl(masterIP).build();
            kubernetesClient = new DefaultKubernetesClient(config);
            System.out.println("init sucess");
        }catch (Exception e){
            System.out.println("can't init discovery service");
        }
    }

    //列出当前命名空间
    public static NamespaceList listNamespace(){
        NamespaceList namespaceList = new NamespaceList();
        try {
            namespaceList = kubernetesClient.namespaces().list();
            System.out.println("list sucess");
        }catch (Exception e) {
            System.out.println("list failed");
        }
        return namespaceList;
    }

    //列出当前可用节点
    public static NodeList listNode(){
        NodeList nodeList = new NodeList();
        try {
            nodeList = kubernetesClient.nodes().list();
            System.out.println("list sucess");
        }catch (Exception e){
            System.out.println("list failed");
        }
        return nodeList;
    }
}

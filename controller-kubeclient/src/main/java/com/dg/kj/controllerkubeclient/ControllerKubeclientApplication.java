package com.dg.kj.controllerkubeclient;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.auth.ApiKeyAuth;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class ControllerKubeclientApplication {
    private String urlApiServer = "http://172.17.8.101:8080/";
    public static void main(String[] args)throws IOException, ApiException {
        //SpringApplication.run(ControllerKubeclientApplication.class, args);
        ControllerKubeclientApplication client = new ControllerKubeclientApplication();
        client.getK8sApiServer();
    }

    public String getK8sApiServer() throws IOException, ApiException{
        // Solution 1: do all things by myself
        String strNewDeployment = "{\"apiVersion\":\"apps/v1\",\"kind\":\"Deployment\",\"metadata\":{\"name\":\"controller-kubeclient\",\"namespace\":\"default\"},\"spec\":{\"replicas\":1,\"selector\":{\"matchLabels\":{\"app\":\"controller\"}},\"template\":{\"metadata\":{\"labels\":{\"app\":\"controller\"}},\"spec\":{\"containers\":[{\"env\":[{\"name\":\"EUREKA_SERVER_IP\",\"value\":\"10.1.0.78\"}],\"image\":\"jkong85/dg-controller-kubeclient:0.2\",\"name\":\"controller-kubeclient\",\"ports\":[{\"containerPort\":9006}]}],\"nodeSelector\":{\"kubernetes.io/hostname\":\"node1\"}}}}}";
        URI uri = UriComponentsBuilder.fromHttpUrl(urlApiServer)
                .queryParam("jsonString",strNewDeployment)
                .build().encode().toUri();
        RestTemplate restTemplate=new RestTemplate();
        //String data=restTemplate.getForObject(uri,String.class);
        //System.out.println(data);
        restTemplate.postForEntity(uri, strNewDeployment, String.class);


        return null;
    }

    public void createDeployment() throws IOException, ApiException{
             // Solution 2: use official k8s-clint/java
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        String token = "/var/run/secrets/kubernetes.io/serviceaccount/token";
        ApiKeyAuth BearerToken = (ApiKeyAuth) client.getAuthentication("BearerToken");
        BearerToken.setApiKey(token);
        BearerToken.setApiKeyPrefix("token");
        client.setAccessToken(token);
        client.setApiKey(token);
        //client.setApiKeyPrefix("Token");

        CoreV1Api api = new CoreV1Api();
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);

        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }

        /*
apiVersion: apps/v1
kind: Deployment
metadata:
  name: controller-test
spec:
  selector:
    matchLabels:
      app: controller
  replicas: 1
  template:
    metadata:
      labels:
        app: controller
    spec:
      containers:
      - name: controller-test
        image: jkong85/dg-controller-test:0.1
        env:
        - name: EUREKA_SERVER_IP
          value: 10.1.0.78
        ports:
        - containerPort: 9005
      nodeSelector:
        kubernetes.io/hostname: docker-for-desktop
         */

        ExtensionsV1beta1Api extensionsV1beta1Api = new ExtensionsV1beta1Api();

        //V1Deployment deploy = new V1Deployment();
        ExtensionsV1beta1Deployment deploy = new ExtensionsV1beta1Deployment();
        deploy.setApiVersion("extensions/v1beta1");
        V1ObjectMeta deployMeta = new V1ObjectMeta();
        deployMeta.setName("controller-test");
        deploy.setMetadata(deployMeta);
        //V1DeploymentSpec deploySpec = new V1DeploymentSpec();
        ExtensionsV1beta1DeploymentSpec deploySpec = new ExtensionsV1beta1DeploymentSpec();
        V1LabelSelector labelSelector = new V1LabelSelector();
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("app", "controller");
        labelSelector.matchLabels(labelMap);
        deploySpec.setSelector(labelSelector);
        deploySpec.setReplicas(1);
        V1PodTemplateSpec template = new V1PodTemplateSpec();
        V1ObjectMeta template_metadata = new V1ObjectMeta();
        Map<String, String> template_label_map = new HashMap<>();
        template_label_map.put("app", "controller");
        template_metadata.labels(template_label_map);
        V1PodSpec podSpec = new V1PodSpec();
        List<V1Container> listContainer = new ArrayList<>();
        V1Container container1= new V1Container();
        container1.setName("controller-test");
        container1.setImage("jkong85/dg-controller-test:0.1");
        List<V1EnvVar> listEnv = new ArrayList<>();
        V1EnvVar env1 = new V1EnvVar();
        env1.setName("EUREKA_SERVER_IP");
        env1.setValue("10.1.0.78");
        listEnv.add(env1);
        container1.setEnv(listEnv);
        listContainer.add(container1);
        List<V1ContainerPort> listPort = new ArrayList<>();
        V1ContainerPort port1 = new V1ContainerPort();
        port1.setContainerPort(9005);
        listPort.add(port1);
        container1.setPorts(listPort);
        podSpec.setContainers(listContainer);
        Map<String, String> nodeSel = new HashMap<>();
        nodeSel.put("kubernetes.io/hostname", "docker-for-desktop");
        podSpec.setNodeSelector(nodeSel);
        template.setSpec(podSpec);
        template.setMetadata(template_metadata);
        deploySpec.setTemplate(template);
        deploy.setSpec(deploySpec);

        //
        System.out.println("Create deployment with API server");
        extensionsV1beta1Api.createNamespacedDeployment("default", deploy, null);

    }
    public String createPod(){
        String url = urlApiServer;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("logo", new FileSystemResource("C:\\Users\\lixiangke\\Pictures\\Saved Pictures\\jdsadh.jpg"));
        params.add("nickname", "nick");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        String str = restTemplate.postForObject(url, httpEntity, String.class);
        System.out.println(str);

        return null;
    }
}

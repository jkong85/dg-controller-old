package com.dg.kj.controllerkubeclient;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
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
    private String urlApiServer = "http://localhost:8080/";
    public static void main(String[] args)throws IOException, ApiException {
        //SpringApplication.run(ControllerKubeclientApplication.class, args);
        ControllerKubeclientApplication client = new ControllerKubeclientApplication();
        client.getK8sApiServer();
    }

    public String getK8sApiServer() throws IOException, ApiException{
        // Solution 1: do all things by myself
        Map<String,String> map=new HashMap<String,String>();
        map.put("name","123");
        map.put("password","123");
        URI uri = UriComponentsBuilder.fromHttpUrl(urlApiServer)
//                .queryParam("jsonString",JSON.toJSONString(map))
                .build().encode().toUri();
        RestTemplate restTemplate=new RestTemplate();
        String data=restTemplate.getForObject(uri,String.class);
        System.out.println(data);

        // Solution 2: use official k8s-clint/java
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

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
        V1Deployment deploy = new V1Deployment();
        deploy.apiVersion("app/v1");
        V1ObjectMeta deployMeta = new V1ObjectMeta();
        deployMeta.name("controller-test");
        deploy.setMetadata(deployMeta);
        V1DeploymentSpec deploySpec = new V1DeploymentSpec();
        deploySpec.setReplicas(1);
        V1LabelSelector labelSelector = new V1LabelSelector();
        Map<String, String> labelMap = new HashMap<>();
        labelMap.put("app", "controller");
        labelSelector.matchLabels(labelMap);
        deploySpec.setSelector(labelSelector);
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
        podSpec.containers(listContainer);
        //
        Map<String, String> nodeSel = new HashMap<>();
        nodeSel.put("kubernetes.io/hostname", "docker-for-desktop");
        podSpec.nodeSelector(nodeSel);
        template.spec(podSpec);
        template.metadata(template_metadata);
        deploySpec.template(template);

        return null;
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

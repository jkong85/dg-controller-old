package com.dg.kj.controllerkubeclient;

import com.alibaba.fastjson.JSON;
import io.kubernetes.client.ApiException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
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
    private String urlApiServer = "http://localhost:8001/";
    public static void main(String[] args)throws IOException, ApiException {
        SpringApplication.run(ControllerKubeclientApplication.class, args);
        ControllerKubeclientApplication client = new ControllerKubeclientApplication();
        client.getK8sApiServer();
    }

    public String getK8sApiServer() throws ApiException{
        Map<String,String> map=new HashMap<String,String>();
        map.put("name","123");
        map.put("password","123");
        URI uri = UriComponentsBuilder.fromHttpUrl(urlApiServer)
//                .queryParam("jsonString",JSON.toJSONString(map))
                .build().encode().toUri();
        RestTemplate restTemplate=new RestTemplate();
        String data=restTemplate.getForObject(uri,String.class);
        System.out.println(data);



        CoreV1Api api = new CoreV1Api();
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }

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

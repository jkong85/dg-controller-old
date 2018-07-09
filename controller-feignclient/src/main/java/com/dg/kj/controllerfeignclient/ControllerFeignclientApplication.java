package com.dg.kj.controllerfeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class ControllerFeignclientApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ControllerFeignclientApplication.class, args);
        ControllerFeignclientApplication instance = new ControllerFeignclientApplication();
        instance.getK8sApiServer();
    }

    public String getK8sApiServer(){
        Map<String,String> map=new HashMap<String,String>();
        map.put("name","123");
        map.put("password","123");
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8001/")
//                .queryParam("jsonString",JSON.toJSONString(map))
//                .queryParam("token","12122222111")
                .build().encode().toUri();
        RestTemplate restTemplate=new RestTemplate();
        String data=restTemplate.getForObject(uri,String.class);
        System.out.println(data);
        return null;
    }
}

package com.dazhi.client.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumeController {
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("hello")
    public String getHello(String name) {
        String url = "http://nacos-jar-test/base";
        return restTemplate.getForObject(url, String.class);
    }
}

package com.dazhi.client.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("base")
public class BaseController {

//    @Resource
//    public NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping
    public String sayHello(){
        return "hello";
    }
}

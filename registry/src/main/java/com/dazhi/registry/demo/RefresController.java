package com.dazhi.registry.demo;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("refres")
public class RefresController {

    @Resource
    private ApplicationContext ctx;

    @GetMapping
    public void refres() {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) ctx.getEnvironment();
        Set<String> key = new HashSet<>();
        key.add("useLocalCache");
        ctx.publishEvent(new EnvironmentChangeEvent(this.ctx, key));
    }
}

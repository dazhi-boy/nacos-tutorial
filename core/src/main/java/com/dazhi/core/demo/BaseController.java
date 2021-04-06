package com.dazhi.core.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("base")
@RefreshScope
public class BaseController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @GetMapping
    public boolean get() {
        return useLocalCache;
    }
}

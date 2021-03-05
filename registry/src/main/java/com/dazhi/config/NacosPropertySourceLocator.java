package com.dazhi.config;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

@Order(0)
public class NacosPropertySourceLocator implements PropertySourceLocator {
    public NacosPropertySourceLocator(NacosConfigManager nacosConfigManager) {
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        return null;
    }
}

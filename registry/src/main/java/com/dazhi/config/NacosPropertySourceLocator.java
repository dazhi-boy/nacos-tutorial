package com.dazhi.config;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Order(0)
public class NacosPropertySourceLocator implements PropertySourceLocator {
    private static final Logger log = LoggerFactory.getLogger(NacosPropertySourceLocator.class);

    private static final String MYLOCAL_PROPERTY_SOURCE_NAME = "MYLOCAL";

    public NacosPropertySourceLocator(NacosConfigManager nacosConfigManager) {
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        CompositePropertySource composite = new CompositePropertySource(
                MYLOCAL_PROPERTY_SOURCE_NAME);
        String dataIdPrefix = environment.getProperty("spring.application.name");
        String fileExtension = ".yaml";
        PropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();

        // 调用http请求nacos获取配置信息
        // http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos-jar-test.yml&group=DEFAULT_GROUP
        Map<String, Object> source = new HashMap();
        source.put("useLocalCache", true);
        PropertySource propertySource = new NacosPropertySource("DEFAULT_GROUP","nacos-tutorial-registry",source,new Date(),true);
        composite.addPropertySource(propertySource);
        return composite;
    }
}

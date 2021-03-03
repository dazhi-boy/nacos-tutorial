package com.dazhi.nacos;

import com.dazhi.naming.pojo.NacosDiscoveryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnClass({ NacosDiscoveryProperties.class })
public class NacosConfigServerAutoConfiguration {

//    @Autowired(required = false)
//    private NacosDiscoveryProperties properties;
}

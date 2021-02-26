package com.dazhi.nacos;

import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(
        proxyBeanMethods = false
)
public class NacosServiceRegistryAutoConfiguration {

    // 这里注入服务注册类
    @Bean
    public NacosServiceRegistry nacosServiceRegistry() {
        return new NacosServiceRegistry();
    }

    @Bean
    public NacosAutoServiceRegistration nacosAutoServiceRegistration(NacosServiceRegistry registry,
                                                                     AutoServiceRegistrationProperties autoServiceRegistrationProperties) {
        return new NacosAutoServiceRegistration(registry, autoServiceRegistrationProperties);
    }
}

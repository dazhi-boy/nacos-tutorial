package com.dazhi.config;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnProperty(
        name = {"spring.cloud.nacos.config.enabled"},
        matchIfMissing = true
)
public class NacosConfigAutoConfiguration {
    public NacosConfigAutoConfiguration() {
    }

    @Bean
    public NacosConfigProperties nacosConfigProperties(ApplicationContext context) {
        return context.getParent() != null && BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context.getParent(), NacosConfigProperties.class).length > 0 ? (NacosConfigProperties)BeanFactoryUtils.beanOfTypeIncludingAncestors(context.getParent(), NacosConfigProperties.class) : new NacosConfigProperties();
    }

    @Bean
    public NacosRefreshProperties nacosRefreshProperties() {
        return new NacosRefreshProperties();
    }

    @Bean
    public NacosRefreshHistory nacosRefreshHistory() {
        return new NacosRefreshHistory();
    }

    @Bean
    public NacosConfigManager nacosConfigManager(NacosConfigProperties nacosConfigProperties) {
        return new NacosConfigManager(nacosConfigProperties);
    }

    @Bean
    public NacosContextRefresher nacosContextRefresher(NacosConfigManager nacosConfigManager, NacosRefreshHistory nacosRefreshHistory) {
        return new NacosContextRefresher(nacosConfigManager, nacosRefreshHistory);
    }
}

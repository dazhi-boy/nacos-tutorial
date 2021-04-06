package com.dazhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.context.annotation.Bean;

@ConditionalOnWebApplication
@ConditionalOnClass({Endpoint.class})
@ConditionalOnProperty(
        name = {"spring.cloud.nacos.config.enabled"},
        matchIfMissing = true
)
public class NacosConfigEndpointAutoConfiguration {
    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private NacosRefreshHistory nacosRefreshHistory;

    public NacosConfigEndpointAutoConfiguration() {
    }

    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    @Bean
    public NacosConfigEndpoint nacosConfigEndpoint() {
        return new NacosConfigEndpoint(null, this.nacosRefreshHistory);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledHealthIndicator("nacos-config")
    public NacosConfigHealthIndicator nacosConfigHealthIndicator() {
        return new NacosConfigHealthIndicator(null);
    }
}

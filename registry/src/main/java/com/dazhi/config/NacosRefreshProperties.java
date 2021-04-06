package com.dazhi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class NacosRefreshProperties {
    @Value("${spring.cloud.nacos.config.refresh.enabled:true}")
    private boolean enabled = true;

    public NacosRefreshProperties() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

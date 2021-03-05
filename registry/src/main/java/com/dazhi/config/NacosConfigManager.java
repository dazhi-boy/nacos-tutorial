package com.dazhi.config;

import com.dazhi.naming.api.config.ConfigService;
import com.dazhi.naming.api.config.NacosFactory;

import java.util.Objects;

public class NacosConfigManager {
    private static ConfigService service = null;
    private NacosConfigProperties nacosConfigProperties;

    public NacosConfigManager(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        createConfigService(nacosConfigProperties);
    }

    static ConfigService createConfigService(NacosConfigProperties nacosConfigProperties) {
        if (Objects.isNull(service)) {
            Class var1 = NacosConfigManager.class;
            synchronized(NacosConfigManager.class) {
                try {
                    if (Objects.isNull(service)) {
                        service = NacosFactory.createConfigService(nacosConfigProperties.assembleConfigServiceProperties());
                    }
                } catch (Exception var4) {
                }
            }
        }

        return service;
    }
}

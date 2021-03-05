package com.dazhi.naming.api.config;

import java.util.Properties;

public class NacosFactory {
    public static ConfigService createConfigService(Properties properties) throws Exception {
        return ConfigFactory.createConfigService(properties);
    }
}

package com.dazhi.naming.api.config;

import java.lang.reflect.Constructor;
import java.util.Properties;

public class ConfigFactory {
    public static ConfigService createConfigService(Properties properties) throws Exception {
        try {
            Class<?> driverImplClass = Class.forName("com.dazhi.naming.api.config.NacosConfigService");
            Constructor constructor = driverImplClass.getConstructor(Properties.class);
            ConfigService vendorImpl = (ConfigService) constructor.newInstance(properties);
            return vendorImpl;
        } catch (Throwable e) {
            throw new Exception();
        }
    }
}

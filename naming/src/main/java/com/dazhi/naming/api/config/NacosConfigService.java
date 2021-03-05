package com.dazhi.naming.api.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class NacosConfigService implements ConfigService {

    public NacosConfigService(Properties properties) {
    }
    @Override
    public String getConfig(String dataId, String group, long timeoutMs) {
        return null;
    }

    @Override
    public String getConfigAndSignListener(String dataId, String group, long timeoutMs, Listener listener) {
        return null;
    }

    @Override
    public void addListener(String dataId, String group, Listener listener) {

    }

    @Override
    public boolean publishConfig(String dataId, String group, String content) {
        return false;
    }

    @Override
    public boolean removeConfig(String dataId, String group) {
        return false;
    }

    @Override
    public void removeListener(String dataId, String group, Listener listener) {

    }

    @Override
    public String getServerStatus() {
        return null;
    }
}

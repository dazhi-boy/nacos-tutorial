package com.dazhi.config;

import com.dazhi.naming.api.config.ConfigService;
import com.dazhi.naming.api.config.Constants;
import com.dazhi.naming.api.config.Listener;
import com.dazhi.naming.api.config.PropertyKeyConst;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Properties;

public class NacosConfigService implements ConfigService {

//    private HttpAgent agent;
    private String namespace;
    private String encode;
    private ClientWorker worker;

    public NacosConfigService(Properties properties) {
        String encodeTmp = properties.getProperty(PropertyKeyConst.ENCODE);
        if (StringUtils.isBlank(encodeTmp)) {
            encode = Constants.ENCODE;
        } else {
            encode = encodeTmp.trim();
        }
        initNamespace(properties);
//        agent = new MetricsHttpAgent(new ServerHttpAgent(properties));
//        agent.start();
        worker = new ClientWorker(properties);
    }

    private void initNamespace(Properties properties) {
        namespace = "";
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
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
        worker.addTenantListeners(dataId, group, Arrays.asList(listener));
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

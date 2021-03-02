package com.dazhi.nacos;

import com.dazhi.naming.pojo.Instance;
import com.dazhi.naming.pojo.NamingService;

import java.util.Properties;

public class NacosNamingService implements NamingService {
    public static final int DEFAULT_CLIENT_BEAT_THREAD_COUNT = Runtime.getRuntime()
            .availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() / 2
            : 1;

    private String namespace;

    private String endpoint;

    private String serverList;

    private BeatReactor beatReactor;
    private NamingProxy serverProxy;

    public NacosNamingService(String serverList) {
        Properties properties = new Properties();
        init(properties);
    }

    private void init(Properties properties) {
        serverProxy = new NamingProxy(namespace, endpoint, serverList, properties);
        beatReactor = new BeatReactor(serverProxy, DEFAULT_CLIENT_BEAT_THREAD_COUNT);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {
        BeatInfo beatInfo = new BeatInfo();
        beatInfo.setServiceName("nacos-tutorial-jar");
        beatInfo.setIp(instance.getIp());
        beatInfo.setPort(instance.getPort());
        beatInfo.setCluster(instance.getClusterName());
        beatInfo.setWeight(instance.getWeight());
        beatInfo.setMetadata(instance.getMetadata());
        beatInfo.setScheduled(false);
        beatInfo.setPeriod(instance.getInstanceHeartBeatInterval());

        beatReactor.addBeatInfo("nacos-tutorial-jar", beatInfo);
    }
}

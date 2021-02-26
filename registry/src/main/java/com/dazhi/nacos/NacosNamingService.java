package com.dazhi.nacos;

import com.dazhi.naming.pojo.Instance;
import com.dazhi.naming.pojo.NamingService;

public class NacosNamingService implements NamingService {
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

//        beatReactor.addBeatInfo("nacos-tutorial-jar", beatInfo);
    }
}

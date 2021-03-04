package com.dazhi.nacos;

import com.dazhi.naming.pojo.Instance;
import com.dazhi.naming.pojo.NacosDiscoveryProperties;
import com.dazhi.naming.pojo.NacosNamingService;
import com.dazhi.naming.pojo.NamingService;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NacosServiceRegistry implements ServiceRegistry<Registration> {
    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    private String group = "DEFAULT_GROUP";

    private String clusterName = "DEFAULT";

    private NamingService namingService = new NacosNamingService("nacos-tutorial-jar");

    public NacosServiceRegistry(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        this.namingService = nacosDiscoveryProperties.namingServiceInstance();
    }

    @Override
    public void register(Registration registration) {
//        String serviceId = registration.getServiceId();
        Instance instance = getNacosInstanceFromRegistration(registration);

        namingService.registerInstance(nacosDiscoveryProperties.getService(), group, instance);
    }

    @Override
    public void deregister(Registration registration) {

    }

    @Override
    public void close() {

    }

    @Override
    public void setStatus(Registration registration, String status) {

    }

    @Override
    public <T> T getStatus(Registration registration) {
        return null;
    }

    private Instance getNacosInstanceFromRegistration(Registration registration) {
        Instance instance = new Instance();
        instance.setIp("127.0.0.1");
        instance.setPort(18848);
        instance.setWeight(1);
        instance.setClusterName("DEFAULT");
        instance.setMetadata(null);

        return instance;
    }
}

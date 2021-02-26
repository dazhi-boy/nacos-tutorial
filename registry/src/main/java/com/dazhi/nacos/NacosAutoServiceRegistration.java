package com.dazhi.nacos;

import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

public class NacosAutoServiceRegistration extends AbstractAutoServiceRegistration<Registration> {

    protected NacosAutoServiceRegistration(ServiceRegistry<Registration> serviceRegistry, AutoServiceRegistrationProperties autoServiceRegistrationProperties) {
        super(serviceRegistry, autoServiceRegistrationProperties);
    }

    @Override
    protected Object getConfiguration() {
        return null;
    }

    @Override
    protected boolean isEnabled() {
        return true;
    }

    @Override
    protected Registration getRegistration() {
        return null;
    }

    @Override
    protected Registration getManagementRegistration() {
        return null;
    }
}

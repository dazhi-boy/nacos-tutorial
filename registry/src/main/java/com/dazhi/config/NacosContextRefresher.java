package com.dazhi.config;

import com.dazhi.naming.api.config.AbstractSharedListener;
import com.dazhi.naming.api.config.ConfigService;
import com.dazhi.naming.api.config.Listener;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class NacosContextRefresher implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {
    public ApplicationContext applicationContext;
    private AtomicBoolean ready = new AtomicBoolean(false);
    private static final AtomicLong REFRESH_COUNT = new AtomicLong(0L);
    public Map<String, Listener> listenerMap = new ConcurrentHashMap(16);

    private NacosRefreshHistory nacosRefreshHistory;
    private ConfigService configService;

    public NacosContextRefresher(NacosConfigManager nacosConfigManager, NacosRefreshHistory refreshHistory) {
//        this.nacosConfigProperties = nacosConfigManager.getNacosConfigProperties();
        this.nacosRefreshHistory = refreshHistory;
//        this.configService = nacosConfigManager.getConfigService();
//        this.isRefreshEnabled = this.nacosConfigProperties.isRefreshEnabled();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (this.ready.compareAndSet(false, true)) {
            this.registerNacosListenersForApplications();
        }
    }

    private void registerNacosListenersForApplications() {
        System.out.println("123456789");
//        if (this.isRefreshEnabled()) {
            Iterator var1 = NacosPropertySourceRepository.getAll().iterator();

            while(var1.hasNext()) {
                NacosPropertySource propertySource = (NacosPropertySource)var1.next();
                if (propertySource.isRefreshable()) {
                    String dataId = propertySource.getDataId();
                    this.registerNacosListener(propertySource.getGroup(), dataId);
                }
            }
//        }

    }

    /*public boolean isRefreshEnabled() {
        if (null == this.nacosConfigProperties) {
            return this.isRefreshEnabled;
        } else {
            return this.nacosConfigProperties.isRefreshEnabled() && !this.isRefreshEnabled ? false : this.isRefreshEnabled;
        }
    }*/

    public void registerNacosListener(final String groupKey, final String dataKey) {
        String key = NacosPropertySourceRepository.getMapKey(dataKey, groupKey);
        Listener listener = new AbstractSharedListener() {
                public void innerReceive(String dataId, String group, String configInfo) {
                    NacosContextRefresher.refreshCountIncrement();
                    NacosContextRefresher.this.nacosRefreshHistory.addRefreshRecord(dataId, group, configInfo);
                    NacosContextRefresher.this.applicationContext.publishEvent(new RefreshEvent(this, (Object)null, "Refresh Nacos config"));
//                    if (NacosContextRefresher.log.isDebugEnabled()) {
//                        NacosContextRefresher.log.debug(String.format("Refresh Nacos config group=%s,dataId=%s,configInfo=%s", group, dataId, configInfo));
//                    }

                }
            };
        listenerMap.put("1",listener);
        try {
            this.configService.addListener("nacos-jar-test", "DEFAULT_GROUP", listener);
//            listener.receiveConfigInfo("useLocalCache: false\n" +
//                    "haha: haha");
        } catch (Exception var6) {
        }

    }
    public static void refreshCountIncrement() {
        REFRESH_COUNT.incrementAndGet();
    }
}

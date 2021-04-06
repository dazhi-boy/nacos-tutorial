package com.dazhi.config;


import com.dazhi.config.NacosContextRefresher;
import com.dazhi.naming.api.config.AbstractSharedListener;
import com.dazhi.naming.api.config.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class CacheData {
    @Autowired
    NacosContextRefresher nacosContextRefresher;

//    public CacheData(ConfigFilterChainManager configFilterChainManager, String s, String dataId, String group, String tenant) {
//    }

    public boolean isInitializing() {
        return isInitializing;
    }

    public void setInitializing(boolean isInitializing) {
        this.isInitializing = isInitializing;
    }

    public String getMd5() {
        return md5;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CacheData(String name, String dataId, String group) {
        if (null == dataId || null == group) {
            throw new IllegalArgumentException("dataId=" + dataId + ", group=" + group);
        }
        this.name = name;
        this.dataId = dataId;
        this.group = group;
        this.isInitializing = true;
        listeners = new CopyOnWriteArrayList<ManagerListenerWrap>();
    }

    // ==================

    private final String name;
    public final String dataId;
    public final String group;

    private volatile String md5;
    private final CopyOnWriteArrayList<ManagerListenerWrap> listeners;
    /**
     * whether use local config
     */
    private volatile boolean isUseLocalConfig = false;
    /**
     * last modify time
     */
    private volatile long localConfigLastModified;
    private volatile String content;
    private int taskId;
    private volatile boolean isInitializing = true;
    private String type;

    public void checkListenerMd5() {
        for (ManagerListenerWrap wrap : listeners) {
            safeNotifyListener(dataId, group, content, type, md5, wrap);
        }
    }

    public void addListener(Listener listener) {
        if (null == listener) {
            throw new IllegalArgumentException("listener is null");
        }
        ManagerListenerWrap wrap = (listener instanceof AbstractConfigChangeListener) ?
                new ManagerListenerWrap(listener, md5, content) : new ManagerListenerWrap(listener, md5);

        if (listeners.addIfAbsent(wrap)) {
        }
    }

    private void safeNotifyListener(final String dataId, final String group, final String content, final String type,
                                    final String md5, final ManagerListenerWrap listenerWrap) {
        final Listener listener = listenerWrap.listener;
        listener.receiveConfigInfo("useLocalCache: false\n" +
                    "haha: haha");
//        NacosContextRefresher nacosContextRefresher = new NacosContextRefresher(null,new NacosRefreshHistory());
//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        nacosContextRefresher.setApplicationContext(wac);
//        nacosContextRefresher.registerNacosListener("DEFAULT_GROUP","nacos-tutorial-registry");
    }

}
class ManagerListenerWrap {
    final Listener listener;
    String lastCallMd5 = "";
    String lastContent = null;

    ManagerListenerWrap(Listener listener) {
        this.listener = listener;
    }

    ManagerListenerWrap(Listener listener, String md5) {
        this.listener = listener;
        this.lastCallMd5 = md5;
    }

    ManagerListenerWrap(Listener listener, String md5, String lastContent) {
        this.listener = listener;
        this.lastCallMd5 = md5;
        this.lastContent = lastContent;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ManagerListenerWrap other = (ManagerListenerWrap) obj;
        return listener.equals(other.listener);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

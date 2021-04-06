package com.dazhi.naming.api.config;

import java.util.concurrent.Executor;

public abstract class AbstractSharedListener implements Listener {

    private volatile String dataId;
    private volatile String group;

    public final void fillContext(String dataId, String group) {
        this.dataId = dataId;
        this.group = group;
    }

    @Override
    public final void receiveConfigInfo(String configInfo) {
        innerReceive(dataId, group, configInfo);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    /**
     * receive
     *
     * @param dataId     data ID
     * @param group      group
     * @param configInfo content
     */
    public abstract void innerReceive(String dataId, String group, String configInfo);
}

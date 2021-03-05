package com.dazhi.naming.api.config;

import java.util.concurrent.Executor;

public interface Listener {
    /**
     * Get executor for execute this receive
     *
     * @return Executor
     */
    Executor getExecutor();

    /**
     * Receive config info
     *
     * @param configInfo config info
     */
    void receiveConfigInfo(final String configInfo);
}

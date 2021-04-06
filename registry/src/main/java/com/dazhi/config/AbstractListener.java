package com.dazhi.config;

import com.dazhi.naming.api.config.Listener;

import java.util.concurrent.Executor;

public abstract class AbstractListener implements Listener {

    /**
     * Use default executor
     */
    @Override
    public Executor getExecutor() {
        return null;
    }

}

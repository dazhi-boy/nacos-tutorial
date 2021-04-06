package com.dazhi.config;

public abstract class AbstractConfigChangeListener extends AbstractListener {
    /**
     * handle config change
     * @param event
     */
    public abstract void receiveConfigChange(final ConfigChangeEvent event);

    @Override
    public void receiveConfigInfo(final String configInfo) {}
}

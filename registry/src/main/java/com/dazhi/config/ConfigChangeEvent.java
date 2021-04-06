package com.dazhi.config;

import java.util.Collection;
import java.util.Map;

public class ConfigChangeEvent {
    private Map<String, ConfigChangeItem> data;

    public ConfigChangeEvent(Map<String, ConfigChangeItem> data) {
        this.data = data;
    }

    public ConfigChangeItem getChangeItem(String key) {
        return data.get(key);
    }

    public Collection<ConfigChangeItem> getChangeItems() {
        return data.values();
    }

}

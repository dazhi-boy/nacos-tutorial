package com.dazhi.config;

import org.springframework.core.env.MapPropertySource;

import java.util.Date;
import java.util.Map;

public class NacosPropertySource extends MapPropertySource {
    private final String group;
    private final String dataId;
    private final Date timestamp;
    private final boolean isRefreshable;

    NacosPropertySource(String group, String dataId, Map<String, Object> source, Date timestamp, boolean isRefreshable) {
        super(String.join(",", dataId, group), source);
        this.group = group;
        this.dataId = dataId;
        this.timestamp = timestamp;
        this.isRefreshable = isRefreshable;
    }

    public String getGroup() {
        return this.group;
    }

    public String getDataId() {
        return this.dataId;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public boolean isRefreshable() {
        return this.isRefreshable;
    }
}

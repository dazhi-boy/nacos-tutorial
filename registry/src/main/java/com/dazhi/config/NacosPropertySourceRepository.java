package com.dazhi.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NacosPropertySourceRepository {
    private static final ConcurrentHashMap<String, NacosPropertySource> NACOS_PROPERTY_SOURCE_REPOSITORY = new ConcurrentHashMap();

    private NacosPropertySourceRepository() {
    }

    public static List<NacosPropertySource> getAll() {
        return new ArrayList(NACOS_PROPERTY_SOURCE_REPOSITORY.values());
    }

    /** @deprecated */
    @Deprecated
    public static void collectNacosPropertySources(NacosPropertySource nacosPropertySource) {
        NACOS_PROPERTY_SOURCE_REPOSITORY.putIfAbsent(nacosPropertySource.getDataId(), nacosPropertySource);
    }

    /** @deprecated */
    @Deprecated
    public static NacosPropertySource getNacosPropertySource(String dataId) {
        return (NacosPropertySource)NACOS_PROPERTY_SOURCE_REPOSITORY.get(dataId);
    }

    public static void collectNacosPropertySource(NacosPropertySource nacosPropertySource) {
        NACOS_PROPERTY_SOURCE_REPOSITORY.putIfAbsent(getMapKey(nacosPropertySource.getDataId(), nacosPropertySource.getGroup()), nacosPropertySource);
    }

    public static NacosPropertySource getNacosPropertySource(String dataId, String group) {
        return (NacosPropertySource)NACOS_PROPERTY_SOURCE_REPOSITORY.get(getMapKey(dataId, group));
    }

    public static String getMapKey(String dataId, String group) {
        return String.join(",", String.valueOf(dataId), String.valueOf(group));
    }
}

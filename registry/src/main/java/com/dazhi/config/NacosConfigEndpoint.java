package com.dazhi.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Endpoint(
        id = "nacos-config"
)
public class NacosConfigEndpoint {
    private final NacosConfigProperties properties;
    private final NacosRefreshHistory refreshHistory;
    private ThreadLocal<DateFormat> dateFormat = ThreadLocal.withInitial(() -> {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    });

    public NacosConfigEndpoint(NacosConfigProperties properties, NacosRefreshHistory refreshHistory) {
        this.properties = properties;
        this.refreshHistory = refreshHistory;
    }

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap(16);
        result.put("NacosConfigProperties", this.properties);
        List<NacosPropertySource> all = NacosPropertySourceRepository.getAll();
        List<Map<String, Object>> sources = new ArrayList();
        Iterator var4 = all.iterator();

        while(var4.hasNext()) {
            NacosPropertySource ps = (NacosPropertySource)var4.next();
            Map<String, Object> source = new HashMap(16);
            source.put("dataId", ps.getDataId());
            source.put("lastSynced", ((DateFormat)this.dateFormat.get()).format(ps.getTimestamp()));
            sources.add(source);
        }

        result.put("Sources", sources);
        result.put("RefreshHistory", this.refreshHistory.getRecords());
        return result;
    }
}

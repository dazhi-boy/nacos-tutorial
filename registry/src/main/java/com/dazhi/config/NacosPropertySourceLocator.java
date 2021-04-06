package com.dazhi.config;

import com.dazhi.naming.pojo.HttpHeaderConsts;
import com.dazhi.naming.pojo.HttpMethod;
import com.dazhi.naming.pojo.NamingProxy;
import com.dazhi.naming.utils.HttpClient;
import com.dazhi.naming.utils.UtilAndComs;
import com.dazhi.naming.utils.UuidUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

@Order(0)
public class NacosPropertySourceLocator implements PropertySourceLocator {
    private static final Logger log = LoggerFactory.getLogger(NacosPropertySourceLocator.class);

    private static final String MYLOCAL_PROPERTY_SOURCE_NAME = "MYLOCAL";

    public NacosPropertySourceLocator(NacosConfigManager nacosConfigManager) {
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        CompositePropertySource composite = new CompositePropertySource(
                MYLOCAL_PROPERTY_SOURCE_NAME);
        String dataIdPrefix = environment.getProperty("spring.application.name");
        String fileExtension = ".yaml";
        PropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();

        // 调用http请求nacos获取配置信息
        String api = "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos-tutorial-registry.yml&group=DEFAULT_GROUP";
        String result = callServer(api, null, null, null, HttpMethod.GET);
        String[] split = result.split("/r/n");
        for (String spl : split){
            System.out.println(spl);
        }
        Map<String, Object> source = new HashMap();
        source.put("useLocalCache", true);
        PropertySource propertySource = new NacosPropertySource("DEFAULT_GROUP","nacos-tutorial-registry",source,new Date(),true);
        composite.addPropertySource(propertySource);
//        this.loadExtConfiguration(composite);
        return composite;
    }

//    private void loadExtConfiguration(CompositePropertySource compositePropertySource) {
//        List<Config> extConfigs = this.nacosConfigProperties.getExtensionConfigs();
//        if (!CollectionUtils.isEmpty(extConfigs)) {
//            this.checkConfiguration(extConfigs, "extension-configs");
//            this.loadNacosConfiguration(compositePropertySource, extConfigs);
//        }
//
//    }

    public String callServer(String api, Map<String, String> params, String body, String curServer, String method) {
        long start = System.currentTimeMillis();
        long end = 0;
//        injectSecurityInfo(params);
        List<String> headers = builderHeaders();

        String url = api;

        HttpClient.HttpResult result = HttpClient.request(url, headers, params, body, UtilAndComs.ENCODING, method);
        end = System.currentTimeMillis();

//        MetricsMonitor.getNamingRequestMonitor(method, url, String.valueOf(result.code))
//                .observe(end - start);

        if (HttpURLConnection.HTTP_OK == result.code) {
            return result.content;
        }

        if (HttpURLConnection.HTTP_NOT_MODIFIED == result.code) {
            return StringUtils.EMPTY;
        }
        return null;
    }
    public List<String> builderHeaders() {
        List<String> headers = Arrays.asList(
                HttpHeaderConsts.CLIENT_VERSION_HEADER, "v1",
                HttpHeaderConsts.USER_AGENT_HEADER, "v1",
                "Accept-Encoding", "gzip,deflate,sdch",
                "Connection", "Keep-Alive",
                "RequestId", UuidUtils.generateUuid(), "Request-Module", "Naming");
        return headers;
    }
}

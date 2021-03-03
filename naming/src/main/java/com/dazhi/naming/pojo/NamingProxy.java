package com.dazhi.naming.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazhi.naming.utils.HttpClient;
import com.dazhi.naming.utils.UtilAndComs;
import com.dazhi.naming.utils.UuidUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.*;

public class NamingProxy {
    private String namespaceId;
    public static String WEB_CONTEXT = "/nacos";
    public static String NACOS_URL_BASE = WEB_CONTEXT + "/v1/ns";
    public static String NACOS_URL_INSTANCE = NACOS_URL_BASE + "/instance";


    private static final int DEFAULT_SERVER_PORT = 8848;
    private int serverPort = DEFAULT_SERVER_PORT;

    private List<String> serverList = new ArrayList<String>();
    private List<String> serversFromEndpoint = new ArrayList<String>();

    public NamingProxy(String namespaceId, String endpoint, String serverList, Properties properties) {

        this.namespaceId = namespaceId;
        if (StringUtils.isNotEmpty(serverList)) {
            this.serverList = Arrays.asList(serverList.split(","));
            if (this.serverList.size() == 1) {
//                this.nacosDomain = serverList;
            }
        }
    }

    public JSONObject sendBeat(BeatInfo beatInfo, boolean lightBeatEnabled) {
        Map<String, String> params = new HashMap<String, String>(8);
        String body = StringUtils.EMPTY;
        if (!lightBeatEnabled) {
            try {
                body = "beat=" + URLEncoder.encode(JSON.toJSONString(beatInfo), "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        params.put(CommonParams.NAMESPACE_ID, namespaceId);
        params.put(CommonParams.SERVICE_NAME, beatInfo.getServiceName());
        params.put(CommonParams.CLUSTER_NAME, beatInfo.getCluster());
        params.put("ip", beatInfo.getIp());
        params.put("port", String.valueOf(beatInfo.getPort()));
        String result = reqAPI(NACOS_URL_BASE + "/instance/beat", params, body, HttpMethod.PUT.toString());
        return JSON.parseObject(result);
    }

    public void registerService(String serviceName, String groupName, Instance instance) {
        final Map<String, String> params = new HashMap<String, String>(9);
        params.put(CommonParams.NAMESPACE_ID, namespaceId);
        params.put(CommonParams.SERVICE_NAME, serviceName);
        params.put(CommonParams.GROUP_NAME, groupName);
        params.put(CommonParams.CLUSTER_NAME, instance.getClusterName());
        params.put("ip", instance.getIp());
        params.put("port", String.valueOf(instance.getPort()));
        params.put("weight", String.valueOf(instance.getWeight()));
        params.put("enable", String.valueOf(instance.isEnabled()));
        params.put("healthy", String.valueOf(instance.isHealthy()));
        params.put("ephemeral", String.valueOf(instance.isEphemeral()));
        params.put("metadata", JSON.toJSONString(instance.getMetadata()));

        reqAPI(NACOS_URL_INSTANCE, params, HttpMethod.POST.toString());
    }

    public String reqAPI(String api, Map<String, String> params, String method) {
        return reqAPI(api, params, StringUtils.EMPTY, method);
    }

    public String reqAPI(String api, Map<String, String> params, String body, String method) {
        return reqAPI(api, params, body, getServerList(), method);
    }

    private List<String> getServerList() {
        List<String> snapshot = serversFromEndpoint;
        if (!CollectionUtils.isEmpty(serverList)) {
            snapshot = serverList;
        }
        return snapshot;
    }

    public String reqAPI(String api, Map<String, String> params, String body, List<String> servers, String method) {
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(servers.size());
        for (int i = 0; i < servers.size(); i++) {
            String server = servers.get(index);
            return callServer(api, params, body, server, method);
//                index = (index + 1) % servers.size();
        }
        return null;
    }

    public String callServer(String api, Map<String, String> params, String body, String curServer, String method) {
        long start = System.currentTimeMillis();
        long end = 0;
//        injectSecurityInfo(params);
        List<String> headers = builderHeaders();

        String url;
        if (curServer.startsWith(UtilAndComs.HTTPS) || curServer.startsWith(UtilAndComs.HTTP)) {
            url = curServer + api;
        } else {
            if (!curServer.contains(UtilAndComs.SERVER_ADDR_IP_SPLITER)) {
                curServer = curServer + UtilAndComs.SERVER_ADDR_IP_SPLITER + serverPort;
            }
            url = HttpClient.getPrefix() + curServer + api;
        }

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

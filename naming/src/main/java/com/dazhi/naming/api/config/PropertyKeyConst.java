package com.dazhi.naming.api.config;

public class PropertyKeyConst {

    public final static String IS_USE_CLOUD_NAMESPACE_PARSING = "isUseCloudNamespaceParsing";

    public final static String IS_USE_ENDPOINT_PARSING_RULE = "isUseEndpointParsingRule";

    public final static String ENDPOINT = "endpoint";

    public final static String ENDPOINT_PORT = "endpointPort";

    public final static String NAMESPACE = "namespace";

    public final static String USERNAME = "username";

    public final static String PASSWORD = "password";

    public final static String ACCESS_KEY = "accessKey";

    public final static String SECRET_KEY = "secretKey";

    public final static String RAM_ROLE_NAME = "ramRoleName";

    public final static String SERVER_ADDR = "serverAddr";

    public final static String CONTEXT_PATH = "contextPath";

    public final static String CLUSTER_NAME = "clusterName";

    public final static String ENCODE = "encode";

    public final static String CONFIG_LONG_POLL_TIMEOUT = "configLongPollTimeout";

    public final static String CONFIG_RETRY_TIME = "configRetryTime";

    public final static String MAX_RETRY = "maxRetry";

    public final static String ENABLE_REMOTE_SYNC_CONFIG = "enableRemoteSyncConfig";

    public final static String NAMING_LOAD_CACHE_AT_START = "namingLoadCacheAtStart";

    public final static String NAMING_CLIENT_BEAT_THREAD_COUNT = "namingClientBeatThreadCount";

    public final static String NAMING_POLLING_THREAD_COUNT = "namingPollingThreadCount";

    /**
     * Get the key value of some variable value from the system property
     */
    public static class SystemEnv {

        public static final String ALIBABA_ALIWARE_ENDPOINT_PORT = "ALIBABA_ALIWARE_ENDPOINT_PORT";

        public static final String ALIBABA_ALIWARE_NAMESPACE = "ALIBABA_ALIWARE_NAMESPACE";

        public static final String ALIBABA_ALIWARE_ENDPOINT_URL = "ALIBABA_ALIWARE_ENDPOINT_URL";
    }

}

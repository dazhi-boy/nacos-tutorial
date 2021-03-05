package com.dazhi.naming.api.config;

public interface ConfigService {

    /**
     * Get config
     *
     * @param dataId    dataId
     * @param group     group
     * @param timeoutMs read timeout
     * @return config value
     */
    String getConfig(String dataId, String group, long timeoutMs);

    /**
     * Get config and register Listener
     *
     * If you want to pull it yourself when the program starts to get the configuration for the first time,
     * and the registered Listener is used for future configuration updates, you can keep the original
     * code unchanged, just add the system parameter: enableRemoteSyncConfig = "true" ( But there is network overhead);
     * therefore we recommend that you use this interface directly
     *
     * @param dataId    dataId
     * @param group     group
     * @param timeoutMs read timeout
     * @return config value
     */
    String getConfigAndSignListener(String dataId, String group, long timeoutMs, Listener listener);

    /**
     * Add a listener to the configuration, after the server modified the
     * configuration, the client will use the incoming listener callback.
     * Recommended asynchronous processing, the application can implement the
     * getExecutor method in the ManagerListener, provide a thread pool of
     * execution. If provided, use the main thread callback, May block other
     * configurations or be blocked by other configurations.
     *
     * @param dataId   dataId
     * @param group    group
     * @param listener listener
     */
    void addListener(String dataId, String group, Listener listener);

    /**
     * Publish config.
     *
     * @param dataId  dataId
     * @param group   group
     * @param content content
     * @return Whether publish
     */
    boolean publishConfig(String dataId, String group, String content);

    /**
     * Remove config
     *
     * @param dataId dataId
     * @param group  group
     * @return whether remove
     */
    boolean removeConfig(String dataId, String group);

    /**
     * Remove listener
     *
     * @param dataId   dataId
     * @param group    group
     * @param listener listener
     */
    void removeListener(String dataId, String group, Listener listener);

    /**
     * Get server status
     *
     * @return whether health
     */
    String getServerStatus();

}

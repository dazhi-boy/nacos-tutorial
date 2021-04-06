package com.dazhi.config;

import com.dazhi.naming.api.config.Listener;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.cloud.endpoint.event.RefreshEvent;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ClientWorker {
    public ClientWorker(Properties properties) {
        init(properties);

        executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.alibaba.nacos.client.Worker.我自己加的");
                t.setDaemon(true);
                return t;
            }
        });
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.alibaba.nacos.client.Worker.longPolling.");
                t.setDaemon(true);
                return t;
            }
        });
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    checkConfigInfo();
                } catch (Throwable e) {
                }
            }
        }, 1L, 10L, TimeUnit.MILLISECONDS);
    }

    private void init(Properties properties) {

//        timeout = Math.max(NumberUtils.toInt(properties.getProperty(PropertyKeyConst.CONFIG_LONG_POLL_TIMEOUT),
//                Constants.CONFIG_LONG_POLL_TIMEOUT), Constants.MIN_CONFIG_LONG_POLL_TIMEOUT);
//
//        taskPenaltyTime = NumberUtils.toInt(properties.getProperty(PropertyKeyConst.CONFIG_RETRY_TIME), Constants.CONFIG_RETRY_TIME);
//
//        enableRemoteSyncConfig = Boolean.parseBoolean(properties.getProperty(PropertyKeyConst.ENABLE_REMOTE_SYNC_CONFIG));
    }

    public void checkConfigInfo() {
        // 分任务
        int listenerSize = cacheMap.get().size();
        listenerSize = 2;
        // 向上取整为批数
        int longingTaskCount = (int) Math.ceil(listenerSize / 3000d);
//        int longingTaskCount = (int) Math.ceil(listenerSize / 3000);
        if (longingTaskCount > currentLongingTaskCount) {
            for (int i = (int) currentLongingTaskCount; i < longingTaskCount; i++) {
                // 要判断任务是否在执行 这块需要好好想想。 任务列表现在是无序的。变化过程可能有问题
                executorService.execute(new LongPollingRunnable(i));
            }
            currentLongingTaskCount = longingTaskCount;
        }
    }

    public void addTenantListeners(String dataId, String group, List<? extends Listener> listeners) {
        CacheData cache = addCacheDataIfAbsent(dataId, group, "");
        for (Listener listener : listeners) {
            cache.addListener(listener);
        }
    }

    public CacheData addCacheDataIfAbsent(String dataId, String group, String tenant) {
        CacheData cache = null;
//                cache = new CacheData(configFilterChainManager, "fixed-127.0.0.1_8848", dataId, group, tenant);
                // fix issue # 1317
//                    String[] ct = getServerConfig(dataId, group, tenant, 3000L);
//                    cache.setContent(ct[0]);
        Map<String, CacheData> copy = new HashMap<String, CacheData>(cacheMap.get());
            copy.put("nacos-jar-test+DEFAULT_GROUP", cache);
            cacheMap.set(copy);

        MetricsMonitor.getListenConfigCountMonitor().set(cacheMap.get().size());

        return cache;
    }

    class LongPollingRunnable implements Runnable {
        private int taskId;

        public LongPollingRunnable(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.println("这里不知道在干嘛");
            List<CacheData> cacheDatas = new ArrayList<CacheData>();
            CacheData cacheData = new CacheData("fixed-127.0.0.1_8848", "nacos-jar-test.yml", "DEFAULT_GROUP");
            cacheDatas.add(cacheData);
            List<String> inInitializingCacheList = new ArrayList<String>();
//            checkUpdateDataIds(cacheDatas, inInitializingCacheList);
//请求

            cacheData.checkListenerMd5();
        }
    }

    final ScheduledExecutorService executor;
    final ScheduledExecutorService executorService;
    public static double currentLongingTaskCount = 0;
//    private final ConfigFilterChainManager configFilterChainManager;

    private final AtomicReference<Map<String, CacheData>> cacheMap = new AtomicReference<Map<String, CacheData>>(
            new HashMap<String, CacheData>());


    public static void main(String[] args) {
        int a = 2;
        int b = 3000;
        int longingTaskCount = (int) Math.ceil(2d / 3000d);
        System.out.println(longingTaskCount);
    }
}

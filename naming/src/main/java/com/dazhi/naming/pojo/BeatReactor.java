package com.dazhi.naming.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class BeatReactor {

    private NamingProxy serverProxy;

    private boolean lightBeatEnabled = false;

    private ScheduledExecutorService executorService;

    public BeatReactor(NamingProxy serverProxy, int threadCount){
        this.serverProxy = serverProxy;
        executorService = new ScheduledThreadPoolExecutor(threadCount, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("com.alibaba.nacos.naming.beat.sender");
                return thread;
            }
        });
    }

    public void addBeatInfo(String serviceName, BeatInfo beatInfo) {
        executorService.schedule(new BeatTask(beatInfo), beatInfo.getPeriod(), TimeUnit.MILLISECONDS);
    }

    class BeatTask implements Runnable {
        BeatInfo beatInfo;

        public BeatTask(BeatInfo beatInfo) {
            this.beatInfo = beatInfo;
        }

        @Override
        public void run() {
            if (beatInfo.isStopped()) {
                return;
            }
            long nextTime = beatInfo.getPeriod();
            JSONObject result = serverProxy.sendBeat(beatInfo, BeatReactor.this.lightBeatEnabled);
            long interval = result.getIntValue("clientBeatInterval");
            boolean lightBeatEnabled = false;
            if (result.containsKey(CommonParams.LIGHT_BEAT_ENABLED)) {
                lightBeatEnabled = result.getBooleanValue(CommonParams.LIGHT_BEAT_ENABLED);
            }
            BeatReactor.this.lightBeatEnabled = lightBeatEnabled;
            if (interval > 0) {
                nextTime = interval;
            }
            int code = 10200;
            if (result.containsKey(CommonParams.CODE)) {
                code = result.getIntValue(CommonParams.CODE);
            }
            if (code == 20404) {
                Instance instance = new Instance();
                instance.setPort(beatInfo.getPort());
                instance.setIp(beatInfo.getIp());
//                    instance.setWeight(beatInfo.getWeight());
                instance.setMetadata(beatInfo.getMetadata());
                instance.setClusterName(beatInfo.getCluster());
                instance.setServiceName(beatInfo.getServiceName());
                instance.setInstanceId(instance.getInstanceId());
//                    instance.setEphemeral(true);
                try {
                    serverProxy.registerService(beatInfo.getServiceName(),
                            "", instance);
                } catch (Exception ignore) {
                }
            }
            executorService.schedule(new BeatTask(beatInfo), nextTime, TimeUnit.MILLISECONDS);
        }
    }
}

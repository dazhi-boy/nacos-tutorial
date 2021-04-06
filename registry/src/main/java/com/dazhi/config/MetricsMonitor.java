package com.dazhi.config;

import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;

public class MetricsMonitor {
    private static Gauge nacosMonitor = Gauge.build()
            .name("nacos_monitor").labelNames("module", "name")
            .help("nacos_monitor").register();

    private static Histogram nacosClientRequestHistogram = Histogram.build().labelNames("module", "method", "url", "code")
            .name("nacos_client_request").help("nacos_client_request")
            .register();


    public static Gauge.Child getServiceInfoMapSizeMonitor() {
        return nacosMonitor.labels("naming", "serviceInfoMapSize");
    }

    public static Gauge.Child getDom2BeatSizeMonitor() {
        return nacosMonitor.labels("naming", "dom2BeatSize");
    }

    public static Gauge.Child getListenConfigCountMonitor() {
        return nacosMonitor.labels("naming", "listenConfigCount");
    }

    public static Histogram.Timer getConfigRequestMonitor(String method, String url, String code) {
        return nacosClientRequestHistogram.labels("config", method, url, code).startTimer();
    }

    public static Histogram.Child getNamingRequestMonitor(String method, String url, String code) {
        return nacosClientRequestHistogram.labels("naming", method, url, code);
    }
}

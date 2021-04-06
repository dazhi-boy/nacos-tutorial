package com.dazhi.config;

import com.dazhi.naming.api.config.ConfigService;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;

public class NacosConfigHealthIndicator extends AbstractHealthIndicator {
//    private final ConfigService configService;

    public NacosConfigHealthIndicator(ConfigService configService) {
//        this.configService = configService;
    }

    protected void doHealthCheck(Builder builder) throws Exception {
//        String status = this.configService.getServerStatus();
        String status = "UP";
        builder.status(status);
        byte var4 = -1;
        switch(status.hashCode()) {
            case 2715:
                if (status.equals("UP")) {
                    var4 = 0;
                }
                break;
            case 2104482:
                if (status.equals("DOWN")) {
                    var4 = 1;
                }
        }

        switch(var4) {
            case 0:
                builder.up();
                break;
            case 1:
                builder.down();
                break;
            default:
                builder.unknown();
        }

    }
}

package com.dbn.cloud.gateway.server;

import com.dbn.cloud.platform.common.config.GlobalFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(defaultConfiguration = GlobalFeignConfig.class)
@SpringBootApplication
public class CloudGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayServerApplication.class, args);
    }

}

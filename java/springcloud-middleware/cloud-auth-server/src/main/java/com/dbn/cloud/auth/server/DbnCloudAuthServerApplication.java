package com.dbn.cloud.auth.server;

import com.dbn.cloud.auth.uua.UuaServerConfig;
import com.dbn.cloud.platform.common.config.GlobalFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = GlobalFeignConfig.class)
@Import(UuaServerConfig.class)
@SpringBootApplication
public class DbnCloudAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbnCloudAuthServerApplication.class, args);
    }

}

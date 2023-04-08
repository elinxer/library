package com.elinxer.cloud.library.server;

import com.dbn.cloud.platform.common.config.GlobalFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = GlobalFeignConfig.class)
@SpringBootApplication
@EnableScheduling
public class CloudLibraryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudLibraryServerApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }

}

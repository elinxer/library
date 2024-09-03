package com.dbn.cloud.platform.logstash;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Slf4j
public class LogstashConfig implements ApplicationRunner {

    @Value("${logstash.enable:false}")
    private Boolean enable;

    @Value("${logstash.host:null}")
    private String host;

    @Value("${spring.application.name:''}")
    private String applicationName;

    @Value("${spring.profiles.active}")
    private String activePro;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("Logstash TCP init.");

        // 判断是否开启lostash
        if (!enable) {
            log.info("Logstash configuration is not turned on.");
            return;
        }

        // 判断是否设置host,未设置host则退出配置
        if (null == host) {
            log.warn("Failed to configure a logstash: 'host' attribute is not specified and no embedded appender could be configured.");
            return;
        }

        LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(loggerContext);
        // 最关键处：配置appname为es中的index
        // 必须去配置logstash配置文件，可以直接获取此处变量，每个fields就是一个变量
        // DEMO: index => "%{index_name}"
        Map<String, String> fields = new HashMap<>();
        fields.put("app_name", applicationName);
        fields.put("index_name", "applog-" + applicationName + "." + activePro + "."+ DateUtil.format(new Date(), "yyyy-MM-dd"));
        fields.put("env", activePro);

        encoder.setCustomFields(JSON.toJSONString(fields));
        encoder.start();

        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.addDestination(host);
        appender.setContext(loggerContext);

        appender.setEncoder(encoder);
        appender.start();

        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(appender);

    }
}

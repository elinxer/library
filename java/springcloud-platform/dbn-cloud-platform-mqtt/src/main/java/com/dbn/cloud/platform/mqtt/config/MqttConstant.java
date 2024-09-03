package com.dbn.cloud.platform.mqtt.config;

import java.util.UUID;

public class MqttConstant {

    /**
     * SSL协议前缀
     */
    public static final String SSL_PREFIX = "ssl://";

    /**
     * TCP协议前缀
     */
    public static final String TCP_PREFIX = "tcp://";

    /**
     * 默认配置
     */
    public static final String CA_KEY_PATH = "crt/AmazonRootCA1.pem";

    /**
     * 默认配置
     */
    public static final String CER_KEY_PATH = "crt/5772d8174b-certificate.pem.crt";

    /**
     * 默认配置
     */
    public static final String PRIVATE_KEY_PATH = "crt/5772d8174b-private.pem";

    /**
     * CLIENT ID SUFFIX
     */
    public static final String CLIENT_ID_SUFFIX = "_" + UUID.randomUUID().toString();

    /**
     * PAHO_SYSTEM
     */
    public static final String PAHO_SYSTEM_TOPIC_PREFIX = "$SYS/brokers/";

    /**
     * AWS_SYSTEM
     */
    public static final String AWS_CONNECTION_DIS_PREFIX = "$aws/events/presence/";

    /**
     * DOLLAR_MARK
     */
    public static final String DOLLAR_MARK = "$";
}
package com.dbn.cloud.platform.mqtt.wrapper;

/**
 * @author elinx
 */
public interface IMqttListenerWrapper {

    /**
     * 实时消息通知
     *
     * @param topic   消息主题
     * @param payload 消息体
     */
    void onMessage(String topic, byte[] payload);

    /**
     * 系统消息通知
     *
     * @param topic   消息主题
     * @param payload 消息体
     */
    void onSystemMessage(String topic, byte[] payload);

}

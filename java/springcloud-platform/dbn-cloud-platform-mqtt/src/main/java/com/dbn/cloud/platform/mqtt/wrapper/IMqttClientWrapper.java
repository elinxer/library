package com.dbn.cloud.platform.mqtt.wrapper;

/**
 * @author elinx
 */
public interface IMqttClientWrapper {

    /**
     * 订阅消息
     *
     * @param topic 消息主题
     */
    void subscribe(String topic);

    /**
     * 取消订阅
     *
     * @param topic 消息主题
     */
    void unsubscribe(String topic);

    /**
     * 发布消息
     *
     * @param topic   消息主题
     * @param payload 消息体
     */
    void publish(String topic, byte[] payload);

    /**
     * 主题订阅回调
     *
     * @param topic            主题
     * @param iMessageCallBack 消息回调
     */
    void subscribe(String topic, IMessageCallBack iMessageCallBack);

}
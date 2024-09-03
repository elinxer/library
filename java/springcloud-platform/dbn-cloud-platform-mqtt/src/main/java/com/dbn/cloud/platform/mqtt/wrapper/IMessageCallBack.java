package com.dbn.cloud.platform.mqtt.wrapper;

public interface IMessageCallBack {
    void process(String topic, byte[] message);
}

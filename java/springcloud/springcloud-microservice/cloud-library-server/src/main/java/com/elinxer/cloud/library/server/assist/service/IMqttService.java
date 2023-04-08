package com.elinxer.cloud.library.server.assist.service;

public interface IMqttService {

    boolean subscribe(String clientId);

    boolean unsubscribe(String clientId);

    boolean registerClient(String clientId) throws Exception;

}

package com.dbn.cloud.gateway.server.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public interface SysClientService {

    public Map getClient(String clientId);

    List<Map> listByClientId(Long clientId);
}

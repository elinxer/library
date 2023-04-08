package com.elinxer.cloud.library.server.common;


import lombok.Data;

@Data
public class AppEnvParamManager {

    private static volatile AppEnvParamManager instance = null;


    public AppEnvParamManager() {
    }

    /**
     * Double check
     */
    public static AppEnvParamManager getInstance() {
        if (instance == null) {
            synchronized (AppEnvParamManager.class) {
                if (instance == null) {
                    instance = new AppEnvParamManager();
                }
            }
        }
        return instance;
    }

    private String[] envParams;
    private String mqttClientId;
    private String mqttSecretKey;

}

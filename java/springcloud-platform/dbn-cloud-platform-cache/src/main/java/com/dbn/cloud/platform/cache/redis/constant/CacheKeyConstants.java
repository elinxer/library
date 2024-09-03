package com.dbn.cloud.platform.cache.redis.constant;

public class CacheKeyConstants {

    /**
     * 农场id
     */
    public static String DATACENTER_STATSCREEN_FARMIDS = "dataCenter:statScreen:farmIds:";
    /**
     * 农场设备总数
     */
    public static String DATACENTER_STATSCREEN_DEVICETOTAL = "dataCenter:statScreen:deviceTotal:";

    /**
     * 天气信息
     */
    public static String DATACENTER_HOMEPAGE_WEATHERINFO = "dataCenter:homePage:weatherInfo:";

    /**
     * 在线农机
     */
    public static String WEBSOCKET_MACHINE_ONLINE = "websocket:machine:online:";
    /**
     * farm_info 模块下的农机缓存Key头
     * key+id
     */
    public final static String AMG_CACHE_KEY = "farm_info:db_cache:ag_machinery:";

    /**
     * farm_info 模块下的农具缓存Key头
     * key+id
     */
    public final static String AMI_CACHE_KEY = "farm_info:db_cache:ag_implements:";

    /**
     * farm_info 在线作业列表
     * key+id
     */
    public final static String TASK_ONLINE_KEY = "websocket:taskOnlineList:";
    /**
     * farm_info 在线作业列表巡检超过时间阈值次数
     * key+taskId
     */
    public final static String TASKOPERATE_INSPECTION = "farm_info:taskOperateInspection:";
}

package com.dbn.cloud.platform.core.id;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnowShortUtil {

    /**
     * 起始的时间戳
     **/
    private final static long START_STAMP = 1618989785089L;

    /**
     * 序列号占用的位数
     **/
    private long SEQUENCE_BIT = 4; //= 8; //序列号占用的位数
    private long MACHINE_BIT = 2; // = 3;   //机器标识占用的位数
    private long DATACENTER_BIT = 2; // = 3;//数据中心占用的位数

    private final Map<String, Long> lastStampMap = new ConcurrentHashMap<>();
    private final Map<String, Long> sequenceMap = new ConcurrentHashMap<>();

    // 产生类型数量
    private Integer typeNumber = 999999999;

    /**
     * 每一部分的最大值
     */
    /*private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);*/
    private long MAX_SEQUENCE; //= -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private long MACHINE_LEFT;// = SEQUENCE_BIT;
    private long DATACENTER_LEFT;// = SEQUENCE_BIT + MACHINE_BIT;
    private long TIMESTMP_LEFT;// = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;

    // private long datacenterId;  //数据中心
    //private long machineId;     //机器标识
    //private long sequence = 0L; //序列号
    //private long lastStmp = -1L;//上一次时间戳

    SnowShortUtil() {
        MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
        MACHINE_LEFT = SEQUENCE_BIT;
        DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        TIMESTMP_LEFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;
    }

    private static SnowShortUtil snowFlake = null;

    static {
        snowFlake = new SnowShortUtil();
    }

    public static synchronized long nextId() {
        return snowFlake.getNextId();
    }

    public long getNextId() {
        String type = "common";
        Long machineId = 1L;
        Long datacenterId = 1L;
        return getNextId(type, machineId, datacenterId);
    }

    public long getNextId(String type, Long machineId, Long datacenterId) {
        synchronized (type.intern()) {
            long currStamp = getNewStamp();
            Long lastStamp = lastStampMap.get(type);
            if (lastStamp == null) {
                lastStamp = -1L;
            }
            Long sequence = sequenceMap.get(type);
            if (sequence == null) {
                sequence = 0L;
            }
            //时钟回拨
            if (currStamp < lastStamp) {
                currStamp = lastStamp + 1;
            }
            if (currStamp == lastStamp) {
                //相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    currStamp = getNextMill(lastStamp);
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }
            lastStamp = currStamp;
            //限制type的个数

            if (lastStampMap.size() > typeNumber) {
                lastStampMap.clear();
            }
            //限制type的个数
            if (sequenceMap.size() > typeNumber) {
                sequenceMap.clear();
            }
            lastStampMap.put(type, lastStamp);
            sequenceMap.put(type, sequence);

            return (currStamp - START_STAMP) << TIMESTMP_LEFT //时间戳部分
                    | datacenterId << DATACENTER_LEFT       //数据中心部分
                    | machineId << MACHINE_LEFT             //机器标识部分
                    | sequence;                             //序列号部分
        }
    }

    private long getNextMill(Long lastStamp) {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {
        return System.currentTimeMillis();
    }
}
package com.dbn.cloud.platform.mqtt.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author elinx
 */
public class MqttTopicUtil {

    /**
     * 补全topic组成
     *
     * @param temp 字符模板
     * @param s    补全字符参数
     * @return String
     */
    public static String combine(String temp, String s) {
        return String.format(temp, s);
    }

    /**
     * 解析topic动态部分
     *
     * @param temp  字符模板
     * @param topic 补全字符参数
     * @return Map
     */
    public static List<String> parseTopic(String temp, String topic) {
        String[] tempArr = temp.split("/");
        String[] topicArr = topic.split("/");
        List<String> list = new ArrayList<>();
        for (int i=0; i < tempArr.length; i++) {
            String tempStr = tempArr[i];
            if ("%s".equals(tempStr)) {
                list.add(topicArr[i]);
            }
        }
        return list;
    }

//    public static void main(String[] args) {
//        List<String> list = parseTopic("vehicle/%s/xxxx/%s", "vehicle/xxxx33333/xxxx/44444444");
//        System.out.println(list);
//    }


}

package com.zhiteer.demo.javaapi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 可以单独运行此类
 *
 * Java遍历Map对象的四种方式
 *
 * https://www.cnblogs.com/fqfanqi/p/6187085.html
 *
 */
public class DemoMap {


    /**
     * 打印 map 1
     */
    public static void prtMap1() {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map.put(1, 22);
        map.put(2, 33);
        map.put(3, 44);
        map.put(4, 55);

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<Integer, Integer>  map2 = new HashMap<>();

        map2.put(1, 1111);

        map.putAll(map2);
        System.out.println(map);

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

    }

    /**
     * 打印 map 2
     */
    public static void prtMap2() {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map.put(1, 22);
        map.put(2, 33);
        map.put(3, 44);
        map.put(4, 55);

        //遍历map中的键
        for (Integer key : map.keySet()) {
            System.out.println("Key = " + key);
        }

        //遍历map中的值
        for (Integer value : map.values()) {
            System.out.println("Value = " + value);
        }

    }

    /**
     * 使用Iterator遍历
     *
     * 使用泛型
     */
    public static void prtMap3() {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map.put(1, 22);
        map.put(2, 33);
        map.put(3, 44);
        map.put(4, 55);

        Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<Integer, Integer> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

    }


    public static void main(String[] args) {

        System.out.println("map 1 ====================\n");

        DemoMap.prtMap1();

        System.out.println("map 2 ====================\n");

        DemoMap.prtMap2();

        System.out.println("map 3 ====================\n");

        DemoMap.prtMap3();

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "elinx");
        map.put(2, "linxin");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=>" + entry.getValue());
        }

    }


}



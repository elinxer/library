package com.zhiteer.demo.javaapi;


import java.security.SecureRandom;
import java.util.*;

class DemoUser implements Comparable<DemoUser> {

    private String name; //姓名

    private int age; // 年龄


    public DemoUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // getter && setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }

    @Override
    public int compareTo(DemoUser user) {

        //重写Comparable接口的compareTo方法，
        // 根据年龄升序排列，降序修改相减顺序即可

        //return this.age - user.getAge();
        return user.getAge() - this.age;
    }

}

/**
 * 有序集合
 */
public class DemoList {

    /**
     * List demo 采用对象摸索
     */
    public static void ListDemo() {
        Map<String, Object> json = new HashMap<>();
        SecureRandom rand = new SecureRandom();

        json.put("alarm", 1);
        json.put("temperature", rand.nextFloat() * 100.0f);
        json.put("humidity", rand.nextFloat() * 100.0f);
        json.put("smokeConcentration", rand.nextFloat() * 100.0f);

        DemoServiceData serviceData = new DemoServiceData();

        serviceData.setServiceData(json);

        System.out.println("serviceData.toString():\n");
        System.out.println(serviceData.toString());


        List<DemoServiceData> list = new ArrayList<>();

        list.add(serviceData);

        System.out.println(list);
    }

    /**
     * List 按类型基础排序
     */
    public static void DemoListSort() {

        List<Integer> list = new ArrayList<>();

        list.add(5);
        list.add(13);
        list.add(4);
        list.add(9);

        // 升序
        Collections.sort(list);
        System.out.println(list.toString());

        // 降序
        Collections.reverse(list);
        System.out.println(list.toString());

    }

    /**
     * List 对象排序
     */
    public static void DemoListObject() {

        List<DemoUser> list = new ArrayList<DemoUser>();
        list.add(new DemoUser("张三", 5));
        list.add(new DemoUser("李四", 30));
        list.add(new DemoUser("王五", 19));
        list.add(new DemoUser("陈十七", 17)); // 陈十七永远十七岁

        Collections.sort(list); // 按年龄排序

        System.out.println(list.toString());

    }

    public static void DemoListNi() {

        List<DemoUser> list = new ArrayList<>();

        list.add(new DemoUser("张三", 5));
        list.add(new DemoUser("李四", 30));
        list.add(new DemoUser("王五", 19));
        list.add(new DemoUser("陈十七", 17)); // 陈十七永远十七岁

        list.sort((u1, u2) -> {
            int diff = u1.getAge() - u2.getAge();
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            }
            return 0; //相等为0
        }); // 按年龄排序

        System.out.println(list.toString());

    }

    /**
     * @param argv argv
     */
    public static void main(String[] argv) {

        System.out.println("List ========================\n");

        DemoList.ListDemo();

        System.out.println("List sort ========================\n");

        DemoList.DemoListSort();

        System.out.println("List Object sort ========================\n");

        DemoList.DemoListObject();

        System.out.println("List anonymous sort ========================\n");

        DemoList.DemoListNi();


        List<Integer> list = new ArrayList<>();

        list.add(10);
        list.add(13);
        list.add(5);

        int index = list.get(0);

        System.out.println(index);

    }

}

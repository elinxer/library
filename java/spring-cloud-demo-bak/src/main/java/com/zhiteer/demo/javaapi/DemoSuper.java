package com.zhiteer.demo.javaapi;


class Person {

    public static void prt(String s) {
        System.out.println(s);
    }

    Person() {
        prt("父类·无参数构造方法： " + "A Person.");
    }

    Person(String name) {
        prt("父类·含一个参数的构造方法： " + "A person's name is " + name);
    }
}


class Student extends Person {
    private String school;

    public Student() {
        super();
    }

    public Student(String school) {
        //this(); 同一个方法中，this不能和super同时存在.
        super(school);
    }
}

/**
 * 可以单独运行此类
 */
public class DemoSuper {
    public static void main(String[] args) {
        Student stu = new Student();
        Student stu2 = new Student("广东工业大学");
    }
}
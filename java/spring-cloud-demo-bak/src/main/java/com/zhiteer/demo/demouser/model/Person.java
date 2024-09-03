package com.zhiteer.demo.demouser.model;

public class Person {


    private Integer id;

    private String name;

    private Integer age;

    // 个人身份证关联
    private IdCard card;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public IdCard getCard() {
        return card;
    }

    public void setCard(IdCard card) {
        this.card = card;
    }

    // 省略setter和getter方法
    @Override
    public String toString() {
        return "Person[id=" + id + ",name=" + name + ",age=" + age + ",card="
                + card + "]";
    }

}
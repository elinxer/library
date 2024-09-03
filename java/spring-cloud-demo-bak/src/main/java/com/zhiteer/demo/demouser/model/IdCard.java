package com.zhiteer.demo.demouser.model;

public class IdCard {

    private Integer id;

    private String code;


    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Integer getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 为方便测试，重写了toString方法
     */
    @Override
    public String toString() {
        return "Idcard [id=" + id + ",code=" + code + "]";
    }
}
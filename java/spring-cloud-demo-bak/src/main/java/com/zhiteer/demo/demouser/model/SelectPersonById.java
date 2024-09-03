package com.zhiteer.demo.demouser.model;

import lombok.Data;

/**
 * @author elinx
 */
@Data
public class SelectPersonById {

    private Integer id;
    private String name;
    private Integer age;
    private String code;

    @Override
    public String toString() {
        return "Person [id=" +id+",name=" +name+ ",age=" +age+ ",code=" +code+ "]";
    }

}

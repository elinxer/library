package com.zhiteer.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**

 CREATE TABLE `User` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
 `age` int DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci

 */



@Data
@NoArgsConstructor
public class DemoUser2Model {

    private Long id;

    private String name = "";
    private Integer age = 0;

    public DemoUser2Model(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}

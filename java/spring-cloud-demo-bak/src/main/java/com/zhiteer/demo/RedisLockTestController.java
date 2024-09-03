package com.zhiteer.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisLockTestController {


    @RequestMapping("/redis/hello")
    public String index() {
        return "hello word!";
    }



}

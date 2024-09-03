package com.zhiteer.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/index")
    public String index() {
        return "hello word!";
    }

    @RequestMapping("/test")
    public String test() {



        return "test";
    }

}

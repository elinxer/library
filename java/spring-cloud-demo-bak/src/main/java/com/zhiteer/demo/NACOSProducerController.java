package com.zhiteer.demo;

import com.zhiteer.demo.model.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/nacos/provider")
public class NACOSProducerController {


    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name);
        return "Demo Provider helloGet : " + name;
    }

    @GetMapping("/get")
    public String helloGet(@RequestParam String name) {
        log.info("invoked name = " + name);
        return "Demo Provider helloGet : " + name;
    }

    @PostMapping("/post")
    public TestModel helloPost(@RequestBody TestModel model) {
        log.info("Demo Provider invoked model = " + model.toString());
        model.setName("mock name");
        model.setGender("mock gender");
        return model;
    }


}

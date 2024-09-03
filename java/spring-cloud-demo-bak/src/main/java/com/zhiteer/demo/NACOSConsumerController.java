package com.zhiteer.demo;

import com.zhiteer.demo.client.DemoClient;
import com.zhiteer.demo.client.DemoSelfClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/nacos/consumer")
public class NACOSConsumerController {


    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    DemoClient demoClient;

    @Autowired
    DemoSelfClient demoSelfClient;

    @RequestMapping("/test1")
    public String test1() {
        return "hallow world";
    }

    @GetMapping("/helloGet")
    public String helloGet(@RequestParam String name){
        return demoClient.helloGet(name);
    }

    @GetMapping("/self/get")
    public String selfGet(@RequestParam String name){
        return demoSelfClient.helloGet(name);
    }

}

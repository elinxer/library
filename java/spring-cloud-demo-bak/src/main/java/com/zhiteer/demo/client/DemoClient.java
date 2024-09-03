package com.zhiteer.demo.client;


import com.zhiteer.demo.model.TestModel;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


// service name
@FeignClient("haoxing")
public interface DemoClient {


    @GetMapping(value = "/helloGet")
    String helloGet(@RequestParam("name") String name);

    @Headers("Content-Type: application/json")
    @PostMapping(value = "/helloPost")
    String helloPost(@RequestBody TestModel model);


}

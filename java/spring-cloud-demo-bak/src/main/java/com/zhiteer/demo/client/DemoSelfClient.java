package com.zhiteer.demo.client;


import com.zhiteer.demo.model.TestModel;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// 这是一个服务器提供的的消费句柄，
// 其他服务提供者启用多个，方便整合调用

// service name
//@FeignClient("demo2")
//@FeignClient("demo3")
//@FeignClient("demo4")
//@FeignClient("demo5")

@FeignClient("demo")
public interface DemoSelfClient {


    @GetMapping(value = "/nacos/provider/get")
    String helloGet(@RequestParam("name") String name);




    @Headers("Content-Type: application/json")
    @PostMapping(value = "/nacos/provider/post")
    String helloPost(@RequestBody TestModel model);


}

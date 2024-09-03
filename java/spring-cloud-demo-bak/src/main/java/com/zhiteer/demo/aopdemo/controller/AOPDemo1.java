package com.zhiteer.demo.aopdemo.controller;

import com.alibaba.fastjson.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo
 *
 * @author Elinx<yangdongsheng03>
 */
@RestController
@RequestMapping("/aopdemo1")
public class AOPDemo1 {


    @GetMapping(value = "/getTest")
    public JSONObject aopTest() {
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }


    @PostMapping(value = "/postTest")
    public JSONObject aopTest2(@RequestParam("id") String id) {
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }

}

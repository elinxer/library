package com.zhiteer.demo.aopdemo.controller;

import com.zhiteer.demo.aopdemo.annotation.PermissionAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @author Elinx<yangdongsheng03>
 */
@RestController
@RequestMapping("/PermissionsAnnotation")
public class PermissionsAnnotation {

    @PermissionAnnotation()
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public JSONObject getGroupList(@RequestBody JSONObject request) {

        // request content
        // post raw
        // Content-Type:application/json
        // {"id": "123"}

        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }

}

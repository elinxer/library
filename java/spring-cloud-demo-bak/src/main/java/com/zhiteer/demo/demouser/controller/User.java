package com.zhiteer.demo.demouser.controller;

import com.zhiteer.demo.demouser.ResponseData;
import com.zhiteer.demo.demouser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class User {
    @Autowired
    private UserService userService;


    @PostMapping("/count")
    public Map<String, Object> count() throws Exception {

        Map<String, Object> result = new HashMap<>();

        result.put("msg", "ok");
        result.put("method", "@ResponseBody");
        result.put("data", userService.count());

        return result;
    }

    @PostMapping("/count2")
    public ResponseData count2() throws Exception {

        Map<String, Object> result = new HashMap<>();

        result.put("msg", "ok");
        result.put("errorCode", "@ResponseBody");
        result.put("data", userService.count());

        ResponseData data = new ResponseData();

        data.setErrorCode(200);
        data.setMsg("success");
        data.setData(result);

        return data;
    }

}

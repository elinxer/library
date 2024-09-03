package com.zhiteer.demo.demouser.controller;

import com.zhiteer.demo.demouser.ResponseData;
import com.zhiteer.demo.demouser.model.MyUser;
import com.zhiteer.demo.demouser.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/myuser")
@RestController
public class MyUser2 {


    @Autowired
    private MyUserService myUserService;


    @RequestMapping("/user")
    public ResponseData user(@RequestParam Integer id) throws Exception {

        ResponseData data = new ResponseData();

        MyUser result = myUserService.selectUserById(id);

        data.setResult(result);

        return data;
    }

}

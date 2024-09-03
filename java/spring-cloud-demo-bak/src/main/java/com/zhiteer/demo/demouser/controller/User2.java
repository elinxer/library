package com.zhiteer.demo.demouser.controller;

import com.zhiteer.demo.demouser.ResponseData;
import com.zhiteer.demo.demouser.model.User;
import com.zhiteer.demo.demouser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user2")
public class User2 {

    @Autowired
    private UserService userService;


    @PostMapping("/selectOne")
    public Object selectOne(@RequestParam Integer id) throws Exception {
        User result = userService.selectByPrimaryKey(id);

        ResponseData object = new ResponseData();

        object.setResult(result);

        return object;
    }

}

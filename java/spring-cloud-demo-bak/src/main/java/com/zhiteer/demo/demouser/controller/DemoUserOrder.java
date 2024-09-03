package com.zhiteer.demo.demouser.controller;


import com.zhiteer.demo.demouser.ResponseData;
import com.zhiteer.demo.demouser.model.MyUser;
import com.zhiteer.demo.demouser.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author elinx
 */
@RestController
@RequestMapping("/DemoUserOrder")
public class DemoUserOrder {

    @Autowired
    UserOrderService userOrderService;


    @RequestMapping("/selectUserOrdersById1")
    public ResponseData selectUserOrdersById1(@RequestBody MyUser user) {

        ResponseData data = new ResponseData();

        MyUser personObj = userOrderService.selectUserOrdersById1(user.getId());

        data.setResult(personObj);

        return data;
    }

    @RequestMapping("/selectUserOrdersById2")
    public ResponseData selectUserOrdersById2(@RequestBody MyUser user) {

        ResponseData data = new ResponseData();

        MyUser personObj = userOrderService.selectUserOrdersById2(user.getId());

        data.setResult(personObj);

        return data;
    }


    @RequestMapping("/selectUserOrdersById3")
    public ResponseData selectUserOrdersById3(@RequestBody MyUser user) {

        ResponseData data = new ResponseData();

        Object personObj = userOrderService.selectUserOrdersById3(user.getId());

        data.setResult(personObj);

        return data;
    }


}

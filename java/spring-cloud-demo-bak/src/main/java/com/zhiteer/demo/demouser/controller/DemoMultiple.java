package com.zhiteer.demo.demouser.controller;


import com.zhiteer.demo.demouser.ResponseData;
import com.zhiteer.demo.demouser.model.IdCard;
import com.zhiteer.demo.demouser.model.Person;
import com.zhiteer.demo.demouser.model.SelectPersonById;
import com.zhiteer.demo.demouser.service.IdCardDaoService;
import com.zhiteer.demo.demouser.service.PersonDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author elinx
 */
@RestController
@RequestMapping("/DemoMultiple")
public class DemoMultiple {


    @Autowired
    IdCardDaoService idCardDaoService;

    @Autowired
    PersonDaoService personDaoService;


    @RequestMapping("/selectCodeById")
    public ResponseData selectCodeById() throws Exception {

        ResponseData data = new ResponseData();

        IdCard card = idCardDaoService.selectCodeById(1);
        data.setResult(card);

        return data;
    }

    /**
     * 连表查询 - 底层xml关联表 IDcard
     *
     * 一对一
     *
     * @param person
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectPersonById1")
    public ResponseData selectPersonById1(@RequestBody Person person) throws Exception {

        ResponseData data = new ResponseData();

        Person personObj = personDaoService.selectPersonById1(person.getId());

        data.setResult(personObj);

        return data;
    }

    /**
     * 连表查询 - 底层xml关联表 IDcard
     *
     * 一对一 第二种
     *
     * @param person Person
     * @return ResponseData
     * @throws Exception 异常
     */
    @RequestMapping("/selectPersonById2")
    public ResponseData selectPersonById2(@RequestBody Person person) throws Exception {

        ResponseData data = new ResponseData();

        Person personObj = personDaoService.selectPersonById2(person.getId());

        data.setResult(personObj);

        return data;
    }

    @RequestMapping("/selectPersonById3")
    public ResponseData selectPersonById3(@RequestBody Person person) throws Exception {

        ResponseData data = new ResponseData();

        SelectPersonById personObj = personDaoService.selectPersonById3(person.getId());

        data.setResult(personObj);

        return data;
    }


}

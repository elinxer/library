package com.zhiteer.demo;


import com.zhiteer.demo.model.DemoUser2Model;
import com.zhiteer.demo.model.mapper.DemoUser2Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoUser2Tests {

    @Autowired
    private DemoUser2Mapper userMapper;

    @Test
    @Rollback
    public void testUserMapper() throws Exception {

        // insert一条数据，并select出来验证
        Integer res = userMapper.insert("elinx", 20);
        DemoUser2Model u = userMapper.findByName("elinx");

        Assert.assertEquals(20, u.getAge().intValue());


        // update一条数据，并select出来验证
//        u.setAge(30);
//        userMapper.update(u);
//        u = userMapper.findByName("AAA");
//        Assert.assertEquals(30, u.getAge().intValue());
//        // 删除这条数据，并select验证
//        userMapper.delete(u.getId());
//        u = userMapper.findByName("AAA");
//        Assert.assertEquals(null, u);



    }

}






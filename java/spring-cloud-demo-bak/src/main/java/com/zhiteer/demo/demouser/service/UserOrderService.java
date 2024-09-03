package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.MyUser;
import com.zhiteer.demo.demouser.model.SelectUserOrdersById;

import java.util.List;

/**
 * Dao接口，提供调用
 * @author elinx
 */
public interface UserOrderService {


    public MyUser selectUserOrdersById1(Integer uid);


    public MyUser selectUserOrdersById2(Integer uid);


    public List<SelectUserOrdersById> selectUserOrdersById3(Integer uid);


}

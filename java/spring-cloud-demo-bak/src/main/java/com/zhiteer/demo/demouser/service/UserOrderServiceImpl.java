package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.UserOrderDao;
import com.zhiteer.demo.demouser.model.MyUser;
import com.zhiteer.demo.demouser.model.SelectUserOrdersById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dao接口，提供调用
 * @author elinx
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderDao userOrderDao;


    @Override
    public MyUser selectUserOrdersById1(Integer id) {
        return userOrderDao.selectUserOrdersById1(id);
    }

    @Override
    public MyUser selectUserOrdersById2(Integer id) {
        return userOrderDao.selectUserOrdersById2(id);
    }

    @Override
    public List<SelectUserOrdersById> selectUserOrdersById3(Integer id) {
        return userOrderDao.selectUserOrdersById3(id);
    }


}

package com.zhiteer.demo.demouser.dao;


import com.zhiteer.demo.demouser.model.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author elinx
 */
@Repository("ordersDao")
@Mapper
public interface OrdersDao {

    /**
     * 111
     * @param id
     * @return
     */
    public List<Orders> selectOrdersById(Integer id);

}
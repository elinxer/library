package com.zhiteer.demo.demouser.dao;

import com.zhiteer.demo.demouser.model.MyUser;
import com.zhiteer.demo.demouser.model.SelectUserOrdersById;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @author elinx
 */
@Mapper
public interface UserOrderDao {

    /**
     * selectOrdersById1
     * @param id
     * @return
     */
    public MyUser selectUserOrdersById1(Integer id);

    /**
     * selectUserOrdersById2
     * @param id
     * @return
     */
    public MyUser selectUserOrdersById2(Integer id);

    /**
     * selectUserOrdersById2
     * @param id
     * @return
     */
    public List<SelectUserOrdersById> selectUserOrdersById3(Integer id);

}
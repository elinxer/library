package com.zhiteer.demo.demouser.dao;


import com.zhiteer.demo.demouser.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tools.49db.cn
 * @version 1.0
 * @date 2021-04-12
 */

@Mapper
public interface UserMapper {

    long countByExample();

    User selectByPrimaryKey(Integer id);

}
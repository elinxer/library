package com.zhiteer.demo.demouser.dao;

import com.zhiteer.demo.demouser.model.MyUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MyUserMapper {

    MyUser selectUserById(Integer key);

    List<MyUser> selectAllUser();

    int addUser();

    int updateUser();

    int deleteUser();


}
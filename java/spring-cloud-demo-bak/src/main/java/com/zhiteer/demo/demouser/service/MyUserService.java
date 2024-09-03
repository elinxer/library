package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.MyUser;

import java.util.List;


public interface MyUserService {


	MyUser selectUserById(Integer key) throws Exception;


	List<MyUser> selectAllUser() throws Exception;




}

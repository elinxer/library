package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.MyUserMapper;
import com.zhiteer.demo.demouser.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserServiceImpl implements MyUserService {

	@Autowired
	private MyUserMapper myUserMapper;

	@Override
	public MyUser selectUserById(Integer key) throws Exception {
		return myUserMapper.selectUserById(key);
	}

	@Override
	public List<MyUser> selectAllUser() throws Exception {
		return null;
	}
}

package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.*;
import com.zhiteer.demo.demouser.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	//默认主键倒叙
	private static final String DEFAULT_SORT = "desc";

	@Override
	public User selectByPrimaryKey(Integer key) throws Exception {
		return userMapper.selectByPrimaryKey(key);
	}

	@Override
	public long count() throws Exception {
		return userMapper.countByExample();
	}

	
}

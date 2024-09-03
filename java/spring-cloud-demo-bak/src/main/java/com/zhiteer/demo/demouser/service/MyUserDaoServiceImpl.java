package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.MyUserDao;
import com.zhiteer.demo.demouser.dao.MyUserMapper;
import com.zhiteer.demo.demouser.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyUserDaoServiceImpl implements MyUserDaoService {

	@Autowired
	private MyUserDao myUserDao;

	@Override
	public MyUser selectUserById(Integer key) throws Exception {
		return myUserDao.selectUserById(key);
	}

	@Override
	public List<MyUser> selectAllUser() throws Exception {
		return myUserDao.selectAllUser();
	}

	@Override
	public List<MyUser> selectAllUserPage() throws Exception {
		return myUserDao.selectAllUser();
	}

	@Override
	public List<Map<String, Object>> selectAllUserMap() throws Exception {
		return myUserDao.selectAllUserMap();
	}

	@Override
	public int addUser(MyUser record) throws Exception {
		return myUserDao.addUser(record);
	}

	@Override
	public int updateUser(MyUser record) throws Exception {
		return myUserDao.updateUser(record);
	}

	@Override
	public int deleteUser(Integer id) throws Exception {
		return myUserDao.deleteUser(id);
	}

	@Override
	public int updateUserDemo(MyUser record) throws Exception {
		return myUserDao.updateUserDemo(record);
	}

	@Override
	public List<MyUser> selectUserLike(Map<String, Object> param) throws Exception {
		return myUserDao.selectUserLike(param);
	}

	@Override
	public List<MyUser> selectAllUserResultMap() throws Exception {
		return myUserDao.selectAllUserResultMap();
	}


}

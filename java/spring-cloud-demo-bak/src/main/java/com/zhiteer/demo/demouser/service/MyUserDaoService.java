package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.MyUser;

import java.util.List;
import java.util.Map;

/**
 * Dao接口，提供调用
 */
public interface MyUserDaoService {

	MyUser selectUserById(Integer key) throws Exception;

	List<MyUser> selectAllUser() throws Exception;

	List<MyUser> selectAllUserPage() throws Exception;

	List<MyUser> selectAllUserResultMap() throws Exception;

	// xml 采用 map模式存储 resultType="map"
	List<Map<String, Object>> selectAllUserMap() throws Exception;

	int addUser(MyUser record) throws Exception;

	int updateUser(MyUser record) throws Exception;

	int deleteUser(Integer id) throws Exception;

	int updateUserDemo(MyUser record) throws Exception;

	List<MyUser> selectUserLike(Map<String, Object> param) throws Exception;

}

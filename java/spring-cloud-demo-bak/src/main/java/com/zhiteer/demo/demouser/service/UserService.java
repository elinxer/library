package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.User;

public interface UserService {

	/**
	 * 根据主键查询
	 * @return
	 * @throws Exception
	 */
	User selectByPrimaryKey(Integer key) throws Exception;

	/**
	 * 统计数量
	 * @return
	 * @throws Exception
	 */
	long count() throws Exception;

}

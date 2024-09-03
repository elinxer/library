package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.IdCard;

/**
 * Dao接口，提供调用
 */
public interface IdCardDaoService {

	IdCard selectCodeById(Integer id) throws Exception;

}

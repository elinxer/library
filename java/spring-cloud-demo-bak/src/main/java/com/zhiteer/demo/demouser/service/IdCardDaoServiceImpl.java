package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.IdCardDao;
import com.zhiteer.demo.demouser.model.IdCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IdCardDaoServiceImpl implements IdCardDaoService {

	@Autowired
	private IdCardDao idCardDao;


	@Override
	public IdCard selectCodeById(Integer id) throws Exception {
		return idCardDao.selectCodeById(id);
	}

}

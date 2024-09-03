package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.dao.PersonDao;
import com.zhiteer.demo.demouser.model.Person;
import com.zhiteer.demo.demouser.model.SelectPersonById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author elinx
 */
@Service
public class PersonDaoServiceImpl implements PersonDaoService {

	@Autowired
	private PersonDao personDao;

	@Override
	public Person selectPersonById1(Integer id) {
		return personDao.selectPersonById1(id);
	}

	@Override
	public Person selectPersonById2(Integer id) {
		return personDao.selectPersonById2(id);
	}

	@Override
	public SelectPersonById selectPersonById3(Integer id) {
		return personDao.selectPersonById3(id);
	}

}

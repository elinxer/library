package com.zhiteer.demo.demouser.dao;

import com.zhiteer.demo.demouser.model.Person;
import com.zhiteer.demo.demouser.model.SelectPersonById;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonDao {

    public Person selectPersonById1(Integer id);

    public Person selectPersonById2(Integer id);

    public SelectPersonById selectPersonById3(Integer id);

}
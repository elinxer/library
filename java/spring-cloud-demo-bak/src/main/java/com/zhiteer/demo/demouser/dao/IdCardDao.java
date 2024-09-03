package com.zhiteer.demo.demouser.dao;

import com.zhiteer.demo.demouser.model.IdCard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdCardDao {

    IdCard selectCodeById(Integer i);


}
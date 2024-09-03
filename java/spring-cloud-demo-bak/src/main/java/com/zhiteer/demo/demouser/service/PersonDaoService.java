package com.zhiteer.demo.demouser.service;

import com.zhiteer.demo.demouser.model.Person;
import com.zhiteer.demo.demouser.model.SelectPersonById;

/**
 * Dao接口，提供调用
 * @author elinx
 */
public interface PersonDaoService {

    /**
     * 级联方法一对一 第一种
     *
     * @param id 主键ID
     * @return Person
     * @throws Exception 异常
     */
    public Person selectPersonById1(Integer id) throws Exception;

    /**
     * 级联方法一对一 第二种
     *
     * @param id 主键ID
     * @return Person
     * @throws Exception 异常
     */
    public Person selectPersonById2(Integer id) throws Exception;

    /**
     * 级联方法一对一 第三种
     *
     * @param id 主键ID
     * @return Person
     * @throws Exception 异常
     */
    public SelectPersonById selectPersonById3(Integer id) throws Exception;


}

package com.zhiteer.demo.model.mapper;

import com.zhiteer.demo.model.DemoUser2Model;
import org.apache.ibatis.annotations.*;


@Mapper
public interface DemoUser2Mapper {

    @Select("SELECT * FROM user WHERE name = #{name} limit 1")
    DemoUser2Model findByName(@Param("name") String name);

    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE user SET age=#{age} WHERE name=#{name}")
    void update(DemoUser2Model user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);

}


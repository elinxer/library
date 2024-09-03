package com.dbn.cloud.auth.server.dao;


import com.dbn.cloud.platform.security.entity.SysService;
import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysServiceDao extends BaseDao<SysService> {

    @Insert("insert into sys_service(parent_id,name,path,sort,create_time ,update_time,is_service) values (#{parentId}, #{name}, #{path},#{sort},#{createTime},#{updateTime},#{isService})")
    int save(SysService service);

    int updateByPrimaryKey(SysService service);

    @Select("select id,parent_id parentId , name, path, sort, create_time createTime , update_time updateTime,is_service isService from sys_service t where t.id = #{id}")
    SysService findById(Long id);

    @Delete("delete from sys_service where id = #{id}")
    int delete(Long id);

    @Delete("delete from sys_service where parent_id = #{id}")
    int deleteByParentId(Long id);

    @Select("select id,parent_id parentId , name, path, sort, create_time createTime , update_time updateTime,is_service isService  from sys_service t order by t.sort")
    List<SysService> findAll();

    @Select("select id,parent_id parentId , name, path, sort, create_time createTime , update_time updateTime,is_service isService  from sys_service t where t.is_service = 1 order by t.sort")
    List<SysService> findOnes();

}

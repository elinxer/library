package com.elinxer.cloud.library.server.assist.domain.dao;

import com.dbn.cloud.platform.web.crud.dao.BaseDao;
import com.elinxer.cloud.library.server.assist.domain.entity.UserDevice;
import com.elinxer.cloud.library.server.assist.domain.vo.device.DeviceUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDeviceDao extends BaseDao<UserDevice> {


    @Select("SELECT d.*,ud.user_id FROM `tbl_devices` d \n" +
            "LEFT JOIN tbl_assist_user_devices ud on ud.device_no=d.serial_no\n" +
            "WHERE d.category=\"screen\" and d.deleted=0 and user_id=${userId}")
    List<DeviceUserVo> getUserDevices(@Param("userId") Long userId);

    @Select("SELECT d.*,ud.created_at as activeDate,ud.user_id,ds.task_no,ds.area,ds.mileage,ds.duration FROM `tbl_assist_user_devices` ud \n" +
            "LEFT JOIN tbl_devices d on ud.device_no=d.serial_no \n" +
            "LEFT JOIN tbl_assist_device_summary ds on ud.device_no=ds.device_no " +
            "WHERE d.category=\"screen\" and d.deleted=0 and user_id= ${userId} and serial_no='${deviceNo}' limit 1")
    @ResultType(value = DeviceUserVo.class)
    DeviceUserVo getUserDeviceDetail(@Param("deviceNo") String deviceNo, @Param("userId") Long userId);

}

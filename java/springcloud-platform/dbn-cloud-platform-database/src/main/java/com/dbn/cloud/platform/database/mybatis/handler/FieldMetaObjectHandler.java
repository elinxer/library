package com.dbn.cloud.platform.database.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private static String CREATE_TIME = "createTime";
    private static String UPDATE_TIME = "updateTime";
    private static String CREATE_USER = "createUserId";
    private static String UPDATE_USER = "updateUserId";
    private static String DELETED = "deleted";
    private static String VERSION = "version";
    private static String CREATED_AT = "createdAt";
    private static String UPDATED_AT = "updatedAt";

    @Override
    public void insertFill(MetaObject metaObject) {

        Set<String> columnSet = new HashSet<>(Arrays.asList(metaObject.getGetterNames()));

        if (columnSet.contains(UPDATE_USER)) {
//            LoginAppUser user = SysUserUtils.getLoginAppUser();
//            if (Objects.nonNull(user)) {
//                fillValue(metaObject, CREATE_USER, () -> user.getId());
//            }
        }

        if (columnSet.contains(CREATE_TIME)) {
            fillValue(metaObject, CREATE_TIME, () -> getDateValue(metaObject.getSetterType(CREATE_TIME)));
        }
        if (columnSet.contains(UPDATE_TIME)) {
            fillValue(metaObject, UPDATE_TIME, () -> getDateValue(metaObject.getSetterType(UPDATE_TIME)));
        }
        if (columnSet.contains(DELETED)) {
            fillValue(metaObject, DELETED, () -> getDateValue(metaObject.getSetterType(DELETED)));
        }
        if (columnSet.contains(VERSION)) {
            fillValue(metaObject, VERSION, () -> getDateValue(metaObject.getSetterType(VERSION)));
        }

        if (columnSet.contains(CREATED_AT)) {
            fillValue(metaObject, CREATED_AT, () -> getDateValue(metaObject.getSetterType(CREATED_AT)));
        }
        if (columnSet.contains(UPDATED_AT)) {
            fillValue(metaObject, UPDATED_AT, () -> getDateValue(metaObject.getSetterType(UPDATED_AT)));
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Set<String> columnSet = new HashSet<>(Arrays.asList(metaObject.getGetterNames()));

        if (columnSet.contains(UPDATE_USER)) {
//            LoginAppUser user = SysUserUtils.getLoginAppUser();
//            if (Objects.nonNull(user)) {
//                fillValue(metaObject, UPDATE_USER, () -> user.getId());
//            }
        }

        if (columnSet.contains(UPDATE_TIME)) {
            fillValue(metaObject, UPDATE_TIME, () -> getDateValue(metaObject.getSetterType(UPDATE_TIME)));
        }
        if (columnSet.contains(UPDATED_AT)) {
            fillValue(metaObject, UPDATED_AT, () -> getDateValue(metaObject.getSetterType(UPDATED_AT)));
        }
    }

    private void fillValue(MetaObject metaObject, String fieldName, Supplier<Object> valueSupplier) {
        if (!metaObject.hasGetter(fieldName)) {
            return;
        }
        Object sidObj = metaObject.getValue(fieldName);
        if (sidObj == null && metaObject.hasSetter(fieldName) && valueSupplier != null) {
            if (fieldName.equals(VERSION)) {
                setFieldValByName(fieldName, 0, metaObject);
            } else {
                setFieldValByName(fieldName, valueSupplier.get(), metaObject);
            }
        }
    }

    private Object getDateValue(Class<?> setterType) {
        if (Date.class.equals(setterType)) {
            return new Date();
        } else if (LocalDateTime.class.equals(setterType)) {
            return LocalDateTime.now();
        } else if (Timestamp.class.equals(setterType)) {
            return new Date();
        } else if (Long.class.equals(setterType)) {
            return System.currentTimeMillis();
        } else if (Integer.class.equals(setterType)) {
            return 0;
        }
        return null;
    }


}

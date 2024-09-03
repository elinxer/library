package com.dbn.cloud.platform.web.crud.common;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dbn.cloud.platform.common.utils.DateUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * 通用的条件生成器，具体使用见： dbn-cloud-widsom项目
 *
 * @author elinx
 * @since 1.0
 */
public class Condition {

    /**
     * 等于
     */
    public static final String EQ = "_eq";
    /**
     * 不等于
     */
    public static final String NE = "_ne";
    /**
     * 大于等于
     */
    public static final String GE = "_ge";
    /**
     * 大于
     */
    public static final String GT = "_gt";
    /**
     * 小于
     */
    public static final String LT = "_lt";
    /**
     * 小于等于
     */
    public static final String LE = "_le";
    /**
     * 模糊
     */
    public static final String LIKE = "_like";
    /**
     * 左模糊
     */
    public static final String LIKE_LEFT = "_likeLeft";
    /**
     * 右模糊
     */
    public static final String LIKE_RIGHT = "_likeRight";

    /**
     * 开始时间 后续
     */
    public static final String ST = "_st";
    /**
     * 结束时间 后续
     */
    public static final String ED = "_ed";


    public static QueryWrapper buildCondition(Map<String, Object> condition, QueryWrapper qw) {
        if (MapUtil.isNotEmpty(condition)) {
            //拼装区间
            for (Map.Entry<String, Object> field : condition.entrySet()) {
                String key = field.getKey();
                Object value = field.getValue();
                if (ObjectUtil.isEmpty(value)) {
                    continue;
                }
                if (key.endsWith(GE)) {
                    String beanField = StrUtil.subBefore(key, GE, true);
                    qw.ge(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(GT)) {
                    String beanField = StrUtil.subBefore(key, GT, true);
                    qw.gt(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(LT)) {
                    String beanField = StrUtil.subBefore(key, LT, true);
                    qw.lt(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(LE)) {
                    String beanField = StrUtil.subBefore(key, LE, true);
                    qw.le(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(NE)) {
                    String beanField = StrUtil.subBefore(key, NE, true);
                    qw.ne(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(EQ)) {
                    String beanField = StrUtil.subBefore(key, EQ, true);
                    qw.eq(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(LIKE)) {
                    String beanField = StrUtil.subBefore(key, LIKE, true);
                    qw.like(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(LIKE_LEFT)) {
                    String beanField = StrUtil.subBefore(key, LIKE_LEFT, true);
                    qw.likeLeft(getDbField(beanField, qw.getEntityClass()), value);
                } else if (key.endsWith(LIKE_RIGHT)) {
                    String beanField = StrUtil.subBefore(key, LIKE_RIGHT, true);
                    qw.likeRight(getDbField(beanField, qw.getEntityClass()), value);
                }
            }
        }
        return qw;
    }

    public static String getDbField(String beanField, Class<?> clazz) {

        Field field = ReflectUtil.getField(clazz, beanField);
        TableField tf = field.getAnnotation(TableField.class);
        if (tf != null && StrUtil.isNotEmpty(tf.value())) {
            return tf.value();
        }
        TableId ti = field.getAnnotation(TableId.class);
        if (ti != null && StrUtil.isNotEmpty(ti.value())) {
            return ti.value();
        }
        return "";
    }

    public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> condition, Class<T> clazz) {
        QueryWrapper qw = new QueryWrapper();
        qw.setEntityClass(clazz);
        return buildCondition(condition, qw);
    }
}

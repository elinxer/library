package com.dbn.cloud.platform.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Bean转化工具类
 *
 * @author elinx
 * @since 1.0
 */
@Slf4j
public class BeanConvertUtils {

    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            //log.error("convert error ", e);
        }
        return targetObject;
    }

    public static <T> List<T> sourceToTarget(Collection<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        List targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            //log.error("convert error ", e);
        }

        return targetList;
    }


    public static <T> List<T> toTarget(Collection<?> sourceList, Class<T> target) {
        return sourceToTarget(sourceList, target);
    }


    public static <T> Set<T> sourceToTarget(Set<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        Set targetList = new HashSet<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                T targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            //log.error("convert error ", e);
        }

        return targetList;
    }

}

package com.dbn.cloud.platform.validation.bean;


import com.dbn.cloud.platform.common.bean.Bean;

/**
 * @author elinx
 */
public interface ValidateBean extends Bean {
    /**
     * 尝试验证此bean,如果验证未通过
     *
     * @param group 验证分组
     * @param <T>   当前对象类型
     * @return 当前对象
     */
    default <T extends ValidateBean> T tryValidate(Class... group) {
        BeanValidator.tryValidate(this, group);
        return (T) this;
    }
}

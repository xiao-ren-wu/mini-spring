package org.ywb.spring.framework.beans;

import lombok.Getter;
import lombok.Setter;
import org.ywb.spring.framework.core.FactoryBean;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/4 7:41
 * @since 1.8
 */
@Getter
@Setter
public class BeanWrapper extends FactoryBean {

    private BeanPostProcessor postProcessor;

    private Object wrapperInstance;

    /**
     * 最原始的对象，通过反射直接new出来的。
     */
    private Object originInstance;

    public BeanWrapper(Object originInstance) {
        this.wrapperInstance = originInstance;
        this.originInstance = originInstance;
    }

    public Object getWrapperInstance() {
        return this.wrapperInstance;
    }

    Class<?> getWrappedClass() {
        return this.wrapperInstance.getClass();
    }
}

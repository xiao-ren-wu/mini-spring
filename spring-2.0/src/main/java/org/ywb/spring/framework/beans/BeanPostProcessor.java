package org.ywb.spring.framework.beans;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/5 7:42
 * @since 1.8
 * <p>
 * 用于事件监听
 */
public class BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }
}

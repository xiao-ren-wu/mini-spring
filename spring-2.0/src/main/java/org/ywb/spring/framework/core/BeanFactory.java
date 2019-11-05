package org.ywb.spring.framework.core;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/4 7:31
 * @since 1.8
 */
public interface BeanFactory {
    /**
     * 通过bean的名称从容器中获取bean
     *
     * @param beanName bean的名称
     * @return bean
     */
    Object getBean(String beanName);
}

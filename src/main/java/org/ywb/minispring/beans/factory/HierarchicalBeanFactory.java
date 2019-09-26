package org.ywb.minispring.beans.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.Nullable;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/9/25 7:54
 * @since 1.8
 *
 * 继承{@link BeanFactory}
 * 在BeanFactory定义功能的基础上增加了对parentFactory的支持
 *
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    /**
     * Return the parent bean factory, or {@code null} if there is none.
     */
    @Nullable
    BeanFactory getParentBeanFactory();

    /**
     * Return whether the local bean factory contains a bean of the given name,
     * ignoring beans defined in ancestor contexts.
     * <p>This is an alternative to {@code containsBean}, ignoring a bean
     * of the given name from an ancestor bean factory.
     * @param name the name of the bean to query
     * @return whether a bean with the given name is defined in the local factory
     * @see BeanFactory#containsBean
     */
    boolean containsLocalBean(String name);

}


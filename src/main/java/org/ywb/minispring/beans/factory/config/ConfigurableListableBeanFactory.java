package org.ywb.minispring.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.*;
import org.springframework.lang.Nullable;

import java.util.Iterator;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/9/25 8:25
 * @since 1.8
 *
 * BeanFactory配置清单，指定忽略类型以及接口等
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * Ignore the given dependency type for autowiring:
     * for example, String. Default is none.
     * @param type the dependency type to ignore
     */
    void ignoreDependencyType(Class<?> type);

    /**
     * Ignore the given dependency interface for autowiring.
     * <p>This will typically be used by application contexts to register
     * dependencies that are resolved in other ways, like BeanFactory through
     * BeanFactoryAware or ApplicationContext through ApplicationContextAware.
     * <p>By default, only the BeanFactoryAware interface is ignored.
     * For further types to ignore, invoke this method for each type.
     * @param ifc the dependency interface to ignore
     * @see org.springframework.beans.factory.BeanFactoryAware
     * @see org.springframework.context.ApplicationContextAware
     */
    void ignoreDependencyInterface(Class<?> ifc);

    /**
     * Register a special dependency type with corresponding autowired value.
     * <p>This is intended for factory/context references that are supposed
     * to be autowirable but are not defined as beans in the factory:
     * e.g. a dependency of type ApplicationContext resolved to the
     * ApplicationContext instance that the bean is living in.
     * <p>Note: There are no such default types registered in a plain BeanFactory,
     * not even for the BeanFactory interface itself.
     * @param dependencyType the dependency type to register. This will typically
     * be a base interface such as BeanFactory, with extensions of it resolved
     * as well if declared as an autowiring dependency (e.g. ListableBeanFactory),
     * as long as the given value actually implements the extended interface.
     * @param autowiredValue the corresponding autowired value. This may also be an
     * implementation of the {@link org.springframework.beans.factory.ObjectFactory}
     * interface, which allows for lazy resolution of the actual target value.
     */
    void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

    /**
     * Determine whether the specified bean qualifies as an autowire candidate,
     * to be injected into other beans which declare a dependency of matching type.
     * <p>This method checks ancestor factories as well.
     * @param beanName the name of the bean to check
     * @param descriptor the descriptor of the dependency to resolve
     * @return whether the bean should be considered as autowire candidate
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     */
    boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor)
            throws NoSuchBeanDefinitionException;

    /**
     * Return the registered BeanDefinition for the specified bean, allowing access
     * to its property values and constructor argument value (which can be
     * modified during bean factory post-processing).
     * <p>A returned BeanDefinition object should not be a copy but the original
     * definition object as registered in the factory. This means that it should
     * be castable to a more specific implementation type, if necessary.
     * <p><b>NOTE:</b> This method does <i>not</i> consider ancestor factories.
     * It is only meant for accessing local bean definitions of this factory.
     * @param beanName the name of the bean
     * @return the registered BeanDefinition
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * defined in this factory
     */
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * Return a unified view over all bean names managed by this factory.
     * <p>Includes bean definition names as well as names of manually registered
     * singleton instances, with bean definition names consistently coming first,
     * analogous to how type/annotation specific retrieval of bean names works.
     * @return the composite iterator for the bean names view
     * @since 4.1.2
     * @see #containsBeanDefinition
     * @see #registerSingleton
     * @see #getBeanNamesForType
     * @see #getBeanNamesForAnnotation
     */
    Iterator<String> getBeanNamesIterator();

    /**
     * Clear the merged bean definition cache, removing entries for beans
     * which are not considered eligible for full metadata caching yet.
     * <p>Typically triggered after changes to the original bean definitions,
     * e.g. after applying a {@link BeanFactoryPostProcessor}. Note that metadata
     * for beans which have already been created at this point will be kept around.
     * @since 4.2
     * @see #getBeanDefinition
     * @see #getMergedBeanDefinition
     */
    void clearMetadataCache();

    /**
     * Freeze all bean definitions, signalling that the registered bean definitions
     * will not be modified or post-processed any further.
     * <p>This allows the factory to aggressively cache bean definition metadata.
     */
    void freezeConfiguration();

    /**
     * Return whether this factory's bean definitions are frozen,
     * i.e. are not supposed to be modified or post-processed any further.
     * @return {@code true} if the factory's configuration is considered frozen
     */
    boolean isConfigurationFrozen();

    /**
     * Ensure that all non-lazy-init singletons are instantiated, also considering
     * {@link org.springframework.beans.factory.FactoryBean FactoryBeans}.
     * Typically invoked at the end of factory setup, if desired.
     * @throws BeansException if one of the singleton beans could not be created.
     * Note: This may have left the factory with some beans already initialized!
     * Call {@link #destroySingletons()} for full cleanup in this case.
     * @see #destroySingletons()
     */
    void preInstantiateSingletons() throws BeansException;

}


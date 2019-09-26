package org.ywb.minispring.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/9/25 8:09
 * @since 1.8
 *
 * 提供各种条件获取Bean的配置清单
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * Check if this bean factory contains a bean definition with the given name.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @param beanName the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     * @see #containsBean
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * Return the number of beans defined in the factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the number of beans defined in the factory
     */
    int getBeanDefinitionCount();

    /**
     * Return the names of all beans defined in this factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the names of all beans defined in this factory,
     * or an empty array if none defined
     */
    String[] getBeanDefinitionNames();

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
     * be it singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     * @param type the generically typed class or interface to match
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @since 4.2
     * @see #isTypeMatch(String, ResolvableType)
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, ResolvableType)
     */
    String[] getBeanNamesForType(ResolvableType type);

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
     * be it singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeanNamesForType(type, true, true)}.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all bean names
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class)
     */
    String[] getBeanNamesForType(@Nullable Class<?> type);

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all bean names
     * @param includeNonSingletons whether to include prototype or scoped beans too
     * or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether to initialize <i>lazy-init singletons</i> and
     * <i>objects created by FactoryBeans</i> (or by factory methods with a
     * "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     * eagerly initialized to determine their type: So be aware that passing in "true"
     * for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beanNamesForTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class, boolean, boolean)
     */
    String[] getBeanNamesForType(@Nullable Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of getBeansOfType matches all kinds of beans, be it
     * singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeansOfType(type, true, true)}.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @since 1.1.2
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class)
     */
    <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException;

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too
     * or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether to initialize <i>lazy-init singletons</i> and
     * <i>objects created by FactoryBeans</i> (or by factory methods with a
     * "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     * eagerly initialized to determine their type: So be aware that passing in "true"
     * for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @see FactoryBean#getObjectType
     * @see BeanFactoryUtils#beansOfTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class, boolean, boolean)
     */
    <T> Map<String, T> getBeansOfType(@Nullable Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
            throws BeansException;

    /**
     * Find all names of beans which are annotated with the supplied {@link Annotation}
     * type, without creating corresponding bean instances yet.
     * <p>Note that this method considers objects created by FactoryBeans, which means
     * that FactoryBeans will get initialized in order to determine their object type.
     * @param annotationType the type of annotation to look for
     * @return the names of all matching beans
     * @since 4.0
     * @see #findAnnotationOnBean
     */
    String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

    /**
     * Find all beans which are annotated with the supplied {@link Annotation} type,
     * returning a Map of bean names with corresponding bean instances.
     * <p>Note that this method considers objects created by FactoryBeans, which means
     * that FactoryBeans will get initialized in order to determine their object type.
     * @param annotationType the type of annotation to look for
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws BeansException if a bean could not be created
     * @since 3.0
     * @see #findAnnotationOnBean
     */
    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException;

    /**
     * Find an {@link Annotation} of {@code annotationType} on the specified bean,
     * traversing its interfaces and super classes if no annotation can be found on
     * the given class itself.
     * @param beanName the name of the bean to look for annotations on
     * @param annotationType the type of annotation to look for
     * @return the annotation of the given type if found, or {@code null} otherwise
     * @throws NoSuchBeanDefinitionException if there is no bean with the given name
     * @since 3.0
     * @see #getBeanNamesForAnnotation
     * @see #getBeansWithAnnotation
     */
    @Nullable
    <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
            throws NoSuchBeanDefinitionException;

}

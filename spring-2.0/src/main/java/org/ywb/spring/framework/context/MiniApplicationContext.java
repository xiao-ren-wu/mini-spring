package org.ywb.spring.framework.context;

import lombok.val;
import org.ywb.spring.framework.annotation.MiniController;
import org.ywb.spring.framework.annotation.MiniResource;
import org.ywb.spring.framework.annotation.MiniService;
import org.ywb.spring.framework.beans.BeanDefinition;
import org.ywb.spring.framework.beans.BeanPostProcessor;
import org.ywb.spring.framework.beans.BeanWrapper;
import org.ywb.spring.framework.context.support.BeanDefinitionReader;
import org.ywb.spring.framework.core.BeanFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/4 7:30
 * @since 1.8
 */
public class MiniApplicationContext implements BeanFactory {

    private String[] configLocations;

    private BeanDefinitionReader reader;

    /**
     * 用于保存配置信息
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    /**
     * 存储Bean的容器
     */
    private Map<String, Object> beanCacheMap = new HashMap<>();

    /**
     * 存储包装bean的容器
     */
    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<>();

    public MiniApplicationContext(String... locations) {
        this.configLocations = locations;
        this.refresh();

    }


    public void refresh() {
        //todo 1. 定位
        this.reader = new BeanDefinitionReader(configLocations);

        //todo 2. 加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();

        //todo 3. 注册
        doRegistry(beanDefinitions);

        //todo 4. 依赖注入
        doAutowired();
    }

    private void doAutowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    public void populateBean(String beanName,Object instance){
        Class<?> aClass = instance.getClass();
        if(!aClass.isAnnotationPresent(MiniController.class)||aClass.isAnnotationPresent(MiniService.class)){
            return;
        }
        Field[] fields = aClass.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            if(!field.isAnnotationPresent(MiniResource.class)){
                continue;
            }
            MiniResource annotation = field.getAnnotation(MiniResource.class);
            String resourceBeanName = annotation.value().trim();
            if("".equals(resourceBeanName)){
                resourceBeanName = field.getType().getName();
            }
            try {
                field.set(instance,this.beanWrapperMap.get(resourceBeanName).getWrapperInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 真正的将BeanDefinitions注册到beanDefinitionMap
     *
     * @param beanDefinitions 所有的bean全路径名列表
     */
    private void doRegistry(List<String> beanDefinitions) {
        for (String className : beanDefinitions) {
            try {
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }
                BeanDefinition beanDefinition = reader.registerBean(className);
                if (Objects.isNull(beanDefinition)) {
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
                }

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    this.beanDefinitionMap.put(anInterface.getName(), beanDefinition);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过读取BeanDefinition中的信息
     * 然后通过反射机制创建一个实例并返回
     * Spring的做法是：不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
     * 装饰器模式：
     * 1. 保留原来的OOP 关系
     * 2. 需要对他进行拓展，增强
     *
     * @param beanName bean的名称
     * @return wrapperBean
     */
    @Override
    public Object getBean(String beanName) {
        BeanPostProcessor beanPostProcessor = new BeanPostProcessor();
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = this.instanceBean(beanDefinition);
        if (Objects.isNull(instance)) {
            return null;
        }
        // 在实例初始化以前调用一次
        beanPostProcessor.postProcessBeforeInitialization(instance, beanName);

        BeanWrapper beanWrapper = new BeanWrapper(instance);
        beanWrapper.setPostProcessor(beanPostProcessor);
        this.beanWrapperMap.put(beanName, beanWrapper);

        // 在实例初始化以后调用一次
        beanPostProcessor.postProcessAfterInitialization(instance, beanName);

        populateBean(beanName,instance);

        return this.beanWrapperMap.get(beanName).getWrapperInstance();
    }

    /**
     * 获取最原始的bean
     *
     * @param beanDefinition 原始bean的描述
     * @return origin bean
     */
    private Object instanceBean(BeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            if (this.beanCacheMap.containsKey(className)) {
                instance = this.beanCacheMap.get(className);
            } else {
                Class<?> aClass = Class.forName(className);
                instance = aClass.newInstance();
                this.beanCacheMap.put(className, instance);
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return instance;
    }
}

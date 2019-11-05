package org.ywb.spring.framework.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/4 7:40
 * @since 1.8
 * 用来存储配置文件的信息，相当于保存在内存中的配置
 */
@Getter
@Setter
public class BeanDefinition {

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;
}

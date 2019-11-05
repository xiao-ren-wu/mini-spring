package org.ywb.spring.framework.context.support;

import org.ywb.spring.framework.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/4 7:38
 * @since 1.8
 * <p>
 * 对配置文件进行查找读取解析
 */
public class BeanDefinitionReader {

    private Properties config = new Properties();

    private List<String> registryBeanClasses;

    private static final String SCAN_PACKAGE = "scanPackage";

    public BeanDefinitionReader(String... locations) {
        InputStream inputStream = this.getClass().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    public List<String> loadBeanDefinitions() {
        return this.registryBeanClasses;
    }

    /**
     * 递归扫描所有相关联的class并且将每个类的全路径保存到一个List中
     *
     * @param packageName 扫描的包
     */
    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(Objects.requireNonNull(url).getFile());
        for (File file : Objects.requireNonNull(classDir.listFiles())) {
            if (file.isDirectory()) {
                doScanner(packageName + "." + file.getName());
            } else {
                registryBeanClasses.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

    /**
     * 每注册一个Bean，就返回一个BeanDefinitions
     *
     * @param className beanName
     * @return {@link BeanDefinition}
     */
    public BeanDefinition registerBean(String className) {
        if (!this.registryBeanClasses.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(
                    className.substring(className.lastIndexOf(".") + 1).toLowerCase()
            );
            return beanDefinition;
        }
        return null;
    }

    public Properties getConfig() {
        return this.config;
    }

}

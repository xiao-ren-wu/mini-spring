package org.ywb.spring.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author WenboYu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @date 2019/11/5 8:20
 * @since 1.8
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface MiniResource {
    String value() default "";
}

package com.liyz.common.export.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释:导出自定义注解
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/9 10:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Export {

    String name() default "";

    int sort() default 0;

    String type() default "String";
}

package com.liyz.common.base.annotation;


import com.liyz.common.base.enums.DesensitizationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注释:自定义脱敏注解
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/14 19:51
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {

    DesensitizationType value() default DesensitizationType.SELF_DEFINITION;

    int beginIndex() default 0;

    int endIndex() default 0;
}

package com.liyz.common.controller.annotation;

import com.liyz.common.controller.constant.LimitType;

import java.lang.annotation.*;

/**
 * 注释: mapping限流注解
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/10 13:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MappingLimit {

    /**
     * 默认为每秒只能访问多少次
     *
     * @return
     */
    double count();

    /**
     * 限流的key，相同的key共享访问次数，但是如果type=ip，则key会被系统自动设置为ip
     *
     * @return
     */
    String key() default "default";

    /**
     * 限流功能描述，方便日后维护
     *
     * @return
     */
    String description()  default "";

    /**
     * 限流的类型，暂时只有两种
     * 1.针对ip进行限流，这个用法慎用，在客户群里比较大的时候，这个可能会带来性能的影响，会有很多不同的key存在缓存中（常见用法：用于发送短信，限制刻意刷短信）
     * 2.针对总次数，这个是业内比较常见的限流方式，这个mapping只能并发访问固定次数
     *
     * @return
     */
    LimitType type() default LimitType.TOTAL;
}

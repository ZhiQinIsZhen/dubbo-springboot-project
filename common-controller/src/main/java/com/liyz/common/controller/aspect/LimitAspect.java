package com.liyz.common.controller.aspect;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.exception.RemoteServiceException;
import com.liyz.common.controller.HttpRequestUtil;
import com.liyz.common.controller.annotation.MappingLimit;
import com.liyz.common.controller.constant.LimitType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 注释: 限流切面类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/10 13:50
 */
@Slf4j
@ConditionalOnExpression("${spring.limit}")
@Aspect
@Configuration
@Order(1)
public class LimitAspect {

    private static ThreadLocal<Double> permitsPerSecond = new ThreadLocal<>();

    private static volatile LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .initialCapacity(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key){
                    return RateLimiter.create(permitsPerSecond.get());
                }
            });

    /**
     * 切点
     */
    @Pointcut("@annotation(com.liyz.common.controller.annotation.MappingLimit)")
    public void aspect() {}

    /**
     * 这里可以有几个方式的优化，可以根据一些情况
     * 1.由于我们的{@link MappingLimit} key有默认值，如果大家都不填写，可能这个有默认值的key的限流则会变为该应用的总并发限流，
     *   可以修改其默认限流的获取key的方式，可用通过 class 上的mapping值+ method上的mapping值，但是则全局限流就要重新做
     * 2.由于我这边限流的统计缓存全部放在内存中的，虽然有5分钟的失效时间，但是在高并发的场景下，建议大家不要用ip级的限流
     *   ，如果确实想做，可以单独起一个限流服务，专门放这些数据，同时也可以用这些数据做一些统计，可以统计出哪个客户没有通过
     *   h5页面访问api，可以结合布隆过滤器进行黑名单处理
     * 3.由于这里没有控制该注解只能加在有 {@link RequestMapping} {@link org.springframework.web.bind.annotation.GetMapping}
     *   等注解上面的限制，所以这个注解同时可以加在一般的方法上甚至是私有方法上，大家可以做一个限制或者判断，有助于良好的
     *   开发习惯
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MappingLimit limit = method.getAnnotation(MappingLimit.class);
        boolean isMapping = method.isAnnotationPresent(RequestMapping.class) || method.isAnnotationPresent(GetMapping.class)
                || method.isAnnotationPresent(PostMapping.class) || method.isAnnotationPresent(PutMapping.class)
                || method.isAnnotationPresent(DeleteMapping.class);
        String key = limit.key();
        LimitType type = limit.type();
        double count = limit.count();
        if (count > 0 && isMapping) {
            Boolean flag = Boolean.TRUE;
            try {
                if (LimitType.IP == type) {
                    key = HttpRequestUtil.getIpAddress();
                }
                permitsPerSecond.set(count);
                RateLimiter rateLimiter = caches.getUnchecked(key);
                flag = rateLimiter.tryAcquire();
            } catch (Exception e) {
                log.error("限流出错了,key:{}", key, e);
            }
            if (!flag) {
                log.error("key:{} --> 触发了限流，每秒只能允许 {} 次访问", key, count);
                throw new RemoteServiceException(CommonCodeEnum.LimitCount);
            }
        }
        return joinPoint.proceed();
    }

}

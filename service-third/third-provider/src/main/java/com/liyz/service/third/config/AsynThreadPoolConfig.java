package com.liyz.service.third.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/20 11:21
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsynThreadPoolConfig {

    private static volatile ExecutorService threadPool;

    /**
     * 异步线程池
     *
     * @return
     */
    public static final ExecutorService getInstance() {
        if (threadPool == null) {
            synchronized (AsynThreadPoolConfig.class) {
                threadPool = new ThreadPoolExecutor
                        (
                                2,
                                10,
                                60,
                                TimeUnit.SECONDS,
                                new LinkedBlockingQueue<Runnable>()
                        );
            }
        }
        return threadPool;
    }

}

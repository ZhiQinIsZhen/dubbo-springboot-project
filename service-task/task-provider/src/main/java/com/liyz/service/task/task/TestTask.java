package com.liyz.service.task.task;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.liyz.common.task.annotation.ElasticSimpleJob;
import com.liyz.common.task.constant.TaskConstant;
import com.liyz.service.task.handler.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 22:13
 */
@Slf4j
@ElasticSimpleJob(cron = "0 */1 * * * ?", description = "账户异常检查task", monitorPort = 1008, dataSource = TaskConstant.DEFAULT_DATASOURCE)
public class TestTask implements SimpleJob {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public void execute(ShardingContext shardingContext) {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
            log.info("userInfo:{}", JSON.toJSONString(userInfoService.getById(1L)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("TestTask:used {} ms", System.currentTimeMillis() - start);
    }
}

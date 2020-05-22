package com.liyz.service.task.task;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.liyz.common.task.annotation.ElasticSimpleJob;
import com.liyz.common.task.constant.TaskConstant;
import com.liyz.service.member.remote.RemoteUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 22:13
 */
@Slf4j
@ElasticSimpleJob(cron = "0 */1 * * * ?", description = "账户异常检查task", shardingTotalCount = 3, monitorPort = 1008,
        shardingItemParameters = "0=1,1=2,2=3", dataSource = TaskConstant.DEFAULT_DATASOURCE)
public class TestTask implements SimpleJob {

    @DubboReference(version = "1.0.0")
    RemoteUserInfoService remoteUserInfoService;

    @Override
    public void execute(ShardingContext shardingContext) {
        long start = System.currentTimeMillis();
        try {
            log.info("userInfo:{}", JSON.toJSONString(remoteUserInfoService.getByUserId(Long.valueOf(shardingContext.getShardingItem()) + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("TestTask:used {} ms", System.currentTimeMillis() - start);
    }
}

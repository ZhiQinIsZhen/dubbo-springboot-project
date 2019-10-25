package com.liyz.service.file.task;

import com.liyz.service.file.config.FileSnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 15:10
 */
//@Service
@Slf4j
public class Test {

    @Autowired
    FileSnowflakeIdUtil fileSnowflakeIdUtil;

    @Scheduled(initialDelay = 5000, fixedRate = 10000)
    public void monitor() {
        log.info("snowflake monitor : {}", fileSnowflakeIdUtil.getId());
    }
}

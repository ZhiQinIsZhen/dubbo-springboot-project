package com.liyz.service.task.provider;

import com.liyz.service.task.remote.RemoteTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 21:14
 */
@Slf4j
@Service(version = "1.0.0")
public class RemoteTaskServiceImpl implements RemoteTaskService {

    @Override
    public void test() {
       log.info("开始test了");
    }
}

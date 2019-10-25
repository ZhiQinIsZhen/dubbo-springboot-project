package com.liyz.service.file.config;

import com.liyz.common.base.util.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 14:44
 */
@Slf4j
@Configuration
public class FileSnowflakeIdUtil extends SnowflakeIdUtil {

    @Value("${data.center.id}")
    private long dataCenterId;


    public FileSnowflakeIdUtil() throws UnknownHostException {
        setDatacenterId(dataCenterId);
    }

    public long getId() {
        return getNextId();
    }

    public static void main(String[] args) throws UnknownHostException {
        log.info("HostAddress:{}", InetAddress.getLocalHost().getHostAddress());
    }
}

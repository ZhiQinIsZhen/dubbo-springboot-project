package com.liyz.service.datasource.constant;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/6 11:55
 */
public interface DatasourceConstant {

    Integer RULE_TOTAL_NUM_TYPE = 10010001;

    Integer RULE_WARN_TYPE = 10020001;

    Integer MOBILE_MSG_TYPE = 0;
    Integer EMAIL_TYPE = 1;

    Integer DEFAULT_EXPIRE_TIME_DAY = 15;

    String REDIS_KEY_MSG_TEMPLATE = "template:msg:";
}

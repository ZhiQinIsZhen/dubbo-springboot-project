package com.liyz.service.datasource.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/5 9:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DatasourceEnum {

    /**
     * 数据操作类型
     */
    public enum OperationType {
        INSERT,
        UPDATE,
        DELETE;
    }
}

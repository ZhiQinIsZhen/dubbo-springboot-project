package com.liyz.service.datasource.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/4 19:18
 */
@Data
public class DynamicRuleDO implements Serializable {
    private static final long serialVersionUID = 8780704015528709534L;

    private String column;

    private String table;

    private String condition;
}

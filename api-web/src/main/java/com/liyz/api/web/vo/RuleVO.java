package com.liyz.api.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 16:47
 */
@Data
public class RuleVO implements Serializable {
    private static final long serialVersionUID = 8075567086359662974L;

    private Integer id;

    private Integer tableId;

    private String tableName;

    private String name;

    private Integer type;

    private String columns;

    private String columnType;

    private String value;

    private String projectLeader;

    private String leaderEmail;

    private Integer isInactive;

    private String corn;

    private Date createTime;

    private Date updateTime;
}

package com.liyz.service.datasource.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 15:31
 */
@Data
@Table(name = "rule")
public class RuleDO implements Serializable {
    private static final long serialVersionUID = -5890037609727151715L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "columns")
    private String columns;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "value")
    private String value;

    @Column(name = "project_leader")
    private String projectLeader;

    @Column(name = "leader_email")
    private String leaderEmail;

    @Column(name = "is_inactive")
    private Integer isInactive;

    @Column(name = "corn")
    private String corn;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}

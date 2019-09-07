package com.liyz.service.datasource.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 16:28
 */
@Data
@Table(name = "data_table")
public class DataTableDO implements Serializable {
    private static final long serialVersionUID = -6497283036048000413L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "url")
    private String url;

    @Column(name = "driver")
    private String driver;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}

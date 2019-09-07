package com.liyz.service.datasource.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 16:31
 */
@Data
public class DataTableBO implements Serializable {
    private static final long serialVersionUID = 479660070332044383L;

    private Integer tableId;

    private String url;

    private String driver;

    private String username;

    private String password;
}

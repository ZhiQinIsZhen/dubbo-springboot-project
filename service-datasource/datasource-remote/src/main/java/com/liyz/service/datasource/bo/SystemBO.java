package com.liyz.service.datasource.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 11:22
 */
@Data
public class SystemBO implements Serializable {
    private static final long serialVersionUID = -3819114104442496291L;

    private Integer tableId;

    private String tableName;
}

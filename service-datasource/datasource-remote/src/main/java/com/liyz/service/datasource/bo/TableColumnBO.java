package com.liyz.service.datasource.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 11:24
 */
@Data
public class TableColumnBO implements Serializable {
    private static final long serialVersionUID = 4400710674305272711L;

    private String field;

    private String type;

    private String collation;

    private String empty;

    private String key;

    private String defaultValue;

    private String extra;

    private String privileges;

    private String comment;
}

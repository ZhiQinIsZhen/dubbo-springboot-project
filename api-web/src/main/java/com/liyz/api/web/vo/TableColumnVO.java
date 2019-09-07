package com.liyz.api.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 14:48
 */
@Data
public class TableColumnVO implements Serializable {
    private static final long serialVersionUID = 3407063210984918461L;

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

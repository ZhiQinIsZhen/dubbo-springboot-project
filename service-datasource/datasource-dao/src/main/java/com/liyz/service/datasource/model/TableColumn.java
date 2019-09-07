package com.liyz.service.datasource.model;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/30 16:34
 */
@Data
public class TableColumn implements Serializable {
    private static final long serialVersionUID = -1984576866450404874L;

    @Column(name = "Field")
    private String field;

    @Column(name = "Type")
    private String type;

    @Column(name = "Collation")
    private String collation;

    @Column(name = "Null")
    private String empty;

    @Column(name = "Key")
    private String key;

    @Column(name = "Default")
    private String defaultValue;

    @Column(name = "Extra")
    private String extra;

    @Column(name = "Privileges")
    private String privileges;

    @Column(name = "Comment")
    private String comment;
}

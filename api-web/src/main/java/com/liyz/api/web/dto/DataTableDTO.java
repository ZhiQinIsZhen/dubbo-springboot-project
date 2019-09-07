package com.liyz.api.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/3 15:32
 */
@Data
public class DataTableDTO implements Serializable {
    private static final long serialVersionUID = -615812131158672928L;

    private Integer tableId;

    private String url;

    private String driver;

    private String username;

    private String password;
}

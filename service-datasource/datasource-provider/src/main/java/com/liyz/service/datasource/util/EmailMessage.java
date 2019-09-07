package com.liyz.service.datasource.util;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/6 10:12
 */
@Data
@Builder
public class EmailMessage implements Serializable {
    private static final long serialVersionUID = -7688579438072180123L;

    private String address;

    private String subject;

    private List<String> params;

    private String content;
}

package com.liyz.service.third.analysis.bo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/19 19:09
 */
@Data
@Builder
public class PageBO implements Serializable {
    private static final long serialVersionUID = 518386127139288980L;

    private Long total;

    private Integer pages;

    private Integer pageNum;

    private Integer pageSize;

    private Boolean hasNextPage;
}

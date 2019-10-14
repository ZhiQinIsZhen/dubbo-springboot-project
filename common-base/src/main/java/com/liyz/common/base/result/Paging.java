package com.liyz.common.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/14 15:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging implements Serializable {
    private static final long serialVersionUID = 1400571815379443999L;

    @JSONField(ordinal = 1)
    private Long total;

    @JSONField(ordinal = 2)
    private Integer pages;

    @JSONField(ordinal = 3)
    private Integer pageNum;

    @JSONField(ordinal = 4)
    private Integer pageSize;

    @JSONField(ordinal = 5)
    private Boolean hasNextPage;
}

package com.liyz.service.third.analysis.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/17 11:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrBaseBO {

    private String id;

    private String name;

    private String cell;

    private String strategy_id;
}

package com.liyz.service.third.analysis.bo;

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
 * @date 2019/10/17 11:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrQueryBO<T extends BrBaseBO> implements Serializable {
    private static final long serialVersionUID = -6266610285169805390L;

    private String apiName;

    private String tokenid;

    private T reqData;
}

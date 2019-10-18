package com.liyz.service.third.analysis.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/23 9:35
 */
@Data
@XStreamAlias(value = "conditions")
public class PyConditionsBO implements Serializable {
    private static final long serialVersionUID = 6726548701291585429L;

    private PyConditionBO condition;
}

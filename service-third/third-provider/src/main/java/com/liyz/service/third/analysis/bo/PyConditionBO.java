package com.liyz.service.third.analysis.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/23 9:37
 */
@Data
@XStreamAlias(value = "condition")
public class PyConditionBO implements Serializable {
    private static final long serialVersionUID = -7795716277045896853L;

    @XStreamAsAttribute()
    private String queryType;

    @XStreamImplicit(itemFieldName="item")
    private List<PyItemBO> item;
}

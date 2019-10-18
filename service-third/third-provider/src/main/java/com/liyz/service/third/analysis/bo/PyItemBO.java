package com.liyz.service.third.analysis.bo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/23 9:40
 */
@Data
@XStreamAlias(value = "item")
public class PyItemBO implements Serializable {
    private static final long serialVersionUID = -4130829217627023741L;

    private String name;

    private String value;
}

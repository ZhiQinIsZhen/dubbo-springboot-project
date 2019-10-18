package com.liyz.service.third.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/12 17:42
 */
@Data
public class ThirdDataPageBO implements Serializable {
    private static final long serialVersionUID = 2804547770640761962L;

    private String keyWord;

    private Integer thirdType;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String name;

    private String dataType;

    private String mobile;
}

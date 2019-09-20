package com.liyz.service.third.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/17 16:46
 */
@Data
@Table(name = "third_data")
public class ThirdDataDO {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "third_type")
    private String thirdType;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "name")
    private String name;

    @Column(name = "no")
    private String no;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "value")
    private String value;
}

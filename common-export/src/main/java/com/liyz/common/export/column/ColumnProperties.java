package com.liyz.common.export.column;

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
 * @date 2019/1/24 22:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnProperties implements Serializable {
    private static final long serialVersionUID = 7293092019840482564L;

    private String colName;

    private String colType;

    private String colField;
}

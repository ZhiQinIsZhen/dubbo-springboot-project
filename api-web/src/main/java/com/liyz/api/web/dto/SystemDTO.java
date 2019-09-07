package com.liyz.api.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 13:58
 */
@Data
public class SystemDTO implements Serializable {
    private static final long serialVersionUID = 4546478267880032082L;

    @NotNull(message = "表ID不能为空", groups = {Table.class, Construction.class})
    private Integer tableId;

    @NotBlank(message = "表名称不能为空", groups = {Construction.class})
    private String tableName;

    public interface Table {}

    public interface Construction {}
}

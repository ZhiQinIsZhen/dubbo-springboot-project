package com.liyz.api.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 16:47
 */
@Data
public class RuleDTO implements Serializable {
    private static final long serialVersionUID = 8075567086359662974L;

    @NotNull(message = "表ID不能为空", groups = {Update.class, Delete.class})
    private Integer id;

    @NotNull(message = "表ID不能为空", groups = {Add.class})
    private Integer tableId;

    @NotNull(message = "表名不能为空", groups = {Add.class})
    private String tableName;

    @NotNull(message = "规则不能为空", groups = {Add.class})
    private String name;

    @NotNull(message = "类型", groups = {Add.class})
    private Integer type;

    private String columns;

    @NotNull(message = "列表达式", groups = {Add.class})
    private String columnType;

    @NotNull(message = "列表示式值", groups = {Add.class})
    private String value;

    private String projectLeader;

    private String leaderEmail;

    private Integer isInactive;

    @NotNull(message = "执行时间（corn表达式）", groups = {Add.class})
    private String corn;

    private Date createTime;

    private Date updateTime;

    public interface Add {}

    public interface Update {}

    public interface Delete {}
}

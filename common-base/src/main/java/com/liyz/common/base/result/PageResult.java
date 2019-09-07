package com.liyz.common.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;
import com.liyz.common.base.enums.CommonCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 注释:分页消息体
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/30 10:45
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 491504189274405094L;

    @JSONField(ordinal = 1)
    private String code;

    @JSONField(ordinal = 2)
    private String message;

    @JSONField(ordinal = 3)
    private Long total;

    @JSONField(ordinal = 4)
    private Integer pages;

    @JSONField(ordinal = 5)
    private Integer pageNum;

    @JSONField(ordinal = 6)
    private Integer pageSize;

    @JSONField(ordinal = 7)
    private Boolean hasNextPage;

    @JSONField(ordinal = 8)
    private List<T> data;

    public static <T> PageResult<T> success(PageInfo<T> data) {
        return new PageResult<>(data);
    }

    public static <T> PageResult<T> error(String code, String message) {
        return new PageResult<T>(code, message);
    }

    public PageResult(PageInfo<T> data) {
        this.setData(data.getList());
        this.total = data.getTotal();
        this.pages = data.getPages();
        this.hasNextPage = data.isHasNextPage();
        this.pageNum = data.getPageNum();
        this.pageSize = data.getPageSize();
        this.code = CommonCodeEnum.success.getCode();
        this.message = CommonCodeEnum.success.getMessage();
    }

    public PageResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.total = 0L;
        this.pages = 0;
        this.pageNum = 0;
        this.pageSize = 0;
        this.hasNextPage = false;

    }
}

package com.liyz.common.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.enums.ServiceCodeEnum;
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
    private Paging paging;

    @JSONField(ordinal = 4)
    private List<T> data;

    public static <T> PageResult<T> success(PageInfo<T> data) {
        return new PageResult<>(data);
    }

    public static <T> PageResult<T> error(String code, String message) {
        return new PageResult<T>(code, message);
    }

    public static <T> PageResult<T> error(ServiceCodeEnum codeEnum) {
        return new PageResult<T>(codeEnum.getCode(), codeEnum.getMessage());
    }

    public PageResult(PageInfo<T> data) {
        this.setData(data.getList());
        Paging paging = Paging.builder()
                .total(data.getTotal())
                .pages(data.getPages())
                .hasNextPage(data.isHasNextPage())
                .pageNum(data.getPageNum())
                .pageSize(data.getPageSize())
                .build();
        this.paging = paging;
        this.code = CommonCodeEnum.success.getCode();
        this.message = CommonCodeEnum.success.getMessage();
    }

    public PageResult(String code, String message) {
        this.code = code;
        this.message = message;
        Paging paging = Paging.builder()
                .total(0L)
                .pages(0)
                .hasNextPage(false)
                .pageNum(0)
                .pageSize(0)
                .build();
        this.paging = paging;
    }
}

package com.liyz.api.web.dto;

import com.liyz.api.web.dto.page.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:25
 */
@Data
public class ThirdDataPageDTO extends PageBaseDTO {
    private static final long serialVersionUID = -5638744414597677595L;

    @ApiModelProperty(value = "关键词", example = "仟金顶")
    private String keyWord;

    @NotNull(groups = Common.class, message = "第三方类型不能为空")
    @ApiModelProperty(value = "第三方类型", example = "10011005")
    private Integer thirdType;

    public interface Common {}
}

package com.liyz.api.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.liyz.api.web.dto.RuleDTO;
import com.liyz.api.web.dto.page.PageBaseDTO;
import com.liyz.api.web.vo.RuleVO;
import com.liyz.common.base.result.PageResult;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.controller.annotation.Limit;
import com.liyz.common.controller.annotation.Limits;
import com.liyz.common.controller.constant.LimitType;
import com.liyz.service.datasource.bo.RuleBO;
import com.liyz.service.datasource.remote.RemoteRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 16:04
 */
@Api(value = "规则", tags = "规则")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Reference(version = "1.0.0")
    RemoteRuleService remoteRuleService;

    @PostMapping("/add")
    public Result<Boolean> add(@Validated(RuleDTO.Add.class) @RequestBody RuleDTO ruleDTO) {
        Boolean result = remoteRuleService.add(CommonConverterUtil.beanConverter(ruleDTO, RuleBO.class));
        return Result.success(result);
    }

    @PostMapping("/update")
    public Result<Boolean> update(@Validated(RuleDTO.Update.class) @RequestBody RuleDTO ruleDTO) {
        Boolean result = remoteRuleService.updateById(CommonConverterUtil.beanConverter(ruleDTO, RuleBO.class));
        return Result.success(result);
    }

    @PostMapping("/delete")
    public Result<Boolean> delete(@Validated(RuleDTO.Delete.class) @RequestBody RuleDTO ruleDTO) {
        Boolean result = remoteRuleService.deleteById(ruleDTO.getId());
        return Result.success(result);
    }

    @Limits(value = {@Limit(count = 0.1, type = LimitType.IP), @Limit(count = 10)})
    @GetMapping("/all")
    public Result<List<RuleVO>> all() {
        List<RuleBO> boList = remoteRuleService.list();
        return Result.success(CommonConverterUtil.ListConverter(boList, RuleVO.class));
    }

    @GetMapping("/page")
    public PageResult<RuleVO> page(@Validated({PageBaseDTO.Page.class}) PageBaseDTO pageBaseDTO) {
        PageInfo<RuleBO> boPageInfo = remoteRuleService.listByPage(pageBaseDTO.getPageNum(), pageBaseDTO.getPageSize());
        return PageResult.success(CommonConverterUtil.PageConverter(boPageInfo, RuleVO.class));
    }
}

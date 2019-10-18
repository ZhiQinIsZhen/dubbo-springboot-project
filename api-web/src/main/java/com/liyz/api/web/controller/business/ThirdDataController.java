package com.liyz.api.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.liyz.api.web.dto.ThirdDataPageDTO;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.result.PageResult;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.security.annotation.Anonymous;
import com.liyz.service.third.bo.ThirdDataPageBO;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.remote.RemoteThirdDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:22
 */
@Api(value = "第三方数据接口", tags = "第三方数据接口")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/data")
public class ThirdDataController {

    @Reference(version = "1.0.0")
    RemoteThirdDataService remoteThirdDataService;

    @Anonymous
    @ApiOperation(value = "查询第三方接口信息--分页查询", notes = "查询第三方接口信息--分页查询")
    @GetMapping("/page")
    public PageResult getThirdDataByPage(@Validated({ThirdDataPageDTO.Common.class}) ThirdDataPageDTO thirdDataPageDTO) {
        ThirdType thirdType = ThirdType.getByCode(thirdDataPageDTO.getThirdType());
        if (thirdType == null) {
            return PageResult.error(CommonCodeEnum.validated);
        }
        PageInfo<Object> pageInfo = remoteThirdDataService.queryPage(CommonConverterUtil.beanConverter(thirdDataPageDTO, ThirdDataPageBO.class), thirdType);
        return PageResult.success(pageInfo);
    }

    @Anonymous
    @ApiOperation(value = "查询第三方接口信息--单条", notes = "查询第三方接口信息--单条")
    @GetMapping("/one")
    public Result getThirdData(@Validated({ThirdDataPageDTO.Common.class}) ThirdDataPageDTO thirdDataPageDTO) {
        ThirdType thirdType = ThirdType.getByCode(thirdDataPageDTO.getThirdType());
        if (thirdType == null) {
            return Result.error(CommonCodeEnum.validated);
        }
        Object object = remoteThirdDataService.query(CommonConverterUtil.beanConverter(thirdDataPageDTO, ThirdDataPageBO.class), thirdType);
        if (object == null) {
            return Result.success("查询无结果");
        }
        return Result.success(object);
    }
}

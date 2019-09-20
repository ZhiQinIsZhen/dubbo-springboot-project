package com.liyz.api.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.liyz.api.web.dto.ThirdDataPageDTO;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.result.PageResult;
import com.liyz.common.security.annotation.Anonymous;
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

import java.util.HashMap;
import java.util.Map;

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
    @ApiOperation(value = "查询第三方接口信息", notes = "查询第三方接口信息")
    @GetMapping("/page")
    public PageResult getThirdData(@Validated({ThirdDataPageDTO.Common.class}) ThirdDataPageDTO thirdDataPageDTO) {
        ThirdType thirdType = ThirdType.getByCode(thirdDataPageDTO.getThirdType());
        if (thirdType == null) {
            return PageResult.error(CommonCodeEnum.validated);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", thirdDataPageDTO.getKeyWord());
        PageInfo<Object> pageInfo = remoteThirdDataService.queryPage(map, thirdType, thirdDataPageDTO.getPageNum());
        return PageResult.success(pageInfo);
    }
}

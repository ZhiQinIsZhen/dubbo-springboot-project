package com.liyz.api.web.controller.business;

import com.liyz.api.web.dto.DataTableDTO;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.datasource.bo.DataTableBO;
import com.liyz.service.datasource.remote.RemoteDataTableService;
import com.liyz.service.datasource.remote.RemoteSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 16:48
 */
@Api(value = "数据源", tags = "数据源")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/datasource")
public class DatasourceController {

    @DubboReference(version = "1.0.0")
    RemoteDataTableService remoteDataTableService;
    @DubboReference(version = "1.0.0")
    RemoteSystemService remoteSystemService;

    @GetMapping("/all")
    public Result<List<DataTableBO>> all() {
        List<DataTableBO> list = remoteDataTableService.getAll();
        return Result.success(list);
    }

    @PostMapping("/test")
    public Result<Integer> test(@RequestBody DataTableDTO dataTableDTO) {
        if (Objects.isNull(dataTableDTO.getTableId())) {
            if (StringUtils.isBlank(dataTableDTO.getUrl()) || StringUtils.isBlank(dataTableDTO.getDriver())
                    || StringUtils.isBlank(dataTableDTO.getUsername()) || StringUtils.isBlank(dataTableDTO.getPassword())) {
                return Result.error(CommonCodeEnum.validated);
            }
        }
        return Result.success(remoteSystemService.test(CommonConverterUtil.beanConverter(dataTableDTO, DataTableBO.class)));
    }
}

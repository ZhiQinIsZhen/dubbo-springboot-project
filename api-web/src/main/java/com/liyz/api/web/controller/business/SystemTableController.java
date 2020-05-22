package com.liyz.api.web.controller.business;

import com.liyz.api.web.dto.SystemDTO;
import com.liyz.api.web.vo.TableColumnVO;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.datasource.bo.SystemBO;
import com.liyz.service.datasource.bo.TableColumnBO;
import com.liyz.service.datasource.remote.RemoteSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/2 13:53
 */
@Api(value = "表数据", tags = "表数据")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/table")
public class SystemTableController {

    @DubboReference(version = "1.0.0")
    RemoteSystemService remoteSystemService;

    @GetMapping("/all")
    public Result<List<String>> all(@ApiParam(value = "参数", required = true)
                                        @Validated(SystemDTO.Table.class) SystemDTO systemDTO) {
        List<String> list = remoteSystemService.selectTables(CommonConverterUtil.beanConverter(systemDTO, SystemBO.class), Boolean.FALSE);
        return Result.success(list);
    }

    @GetMapping("/refresh/all")
    public Result<List<String>> refresh(@ApiParam(value = "参数", required = true)
                                    @Validated(SystemDTO.Table.class) SystemDTO systemDTO) {
        List<String> list = remoteSystemService.selectTables(CommonConverterUtil.beanConverter(systemDTO, SystemBO.class), Boolean.TRUE);
        return Result.success(list);
    }

    @GetMapping("/construct")
    public Result<List<TableColumnVO>> construct(@ApiParam(value = "参数", required = true)
                                        @Validated(SystemDTO.Construction.class) SystemDTO systemDTO) {
        List<TableColumnBO> boList = remoteSystemService.selectColumns(CommonConverterUtil.beanConverter(systemDTO, SystemBO.class));
        return Result.success(CommonConverterUtil.ListConverter(boList, TableColumnVO.class));
    }
}

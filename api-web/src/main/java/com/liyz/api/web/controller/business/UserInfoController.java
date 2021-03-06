package com.liyz.api.web.controller.business;

import com.github.pagehelper.PageInfo;
import com.liyz.api.web.dto.page.PageBaseDTO;
import com.liyz.api.web.vo.UserInfoVO;
import com.liyz.common.base.remote.bo.JwtUserBO;
import com.liyz.common.base.result.PageResult;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.security.annotation.Anonymous;
import com.liyz.common.security.util.LoginInfoUtil;
import com.liyz.service.member.bo.UserInfoBO;
import com.liyz.service.member.remote.RemoteUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.cluster.support.FailfastCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 注释: 用户
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 22:34
 */
@Api(value = "用户信息", tags = "用户信息")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
})
@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @DubboReference(version = "${version}", cluster = FailfastCluster.NAME)
    RemoteUserInfoService remoteUserInfoService;

    @Autowired
    LoginInfoUtil loginInfoUtil;


    @GetMapping("/info")
    public Result<UserInfoVO> info() {
        JwtUserBO jwtUserBO = loginInfoUtil.getUser();
        return Result.success(CommonConverterUtil.beanConverter(jwtUserBO, UserInfoVO.class));
    }

    @GetMapping("/id")
    public Result<Long> id() {
        JwtUserBO jwtUserBO = loginInfoUtil.getUser();
        return Result.success(Objects.isNull(jwtUserBO) ? null : jwtUserBO.getUserId());
    }

    @GetMapping("/page")
    public PageResult<UserInfoVO> page(@Validated({PageBaseDTO.Page.class}) PageBaseDTO pageBaseDTO) {
        if (Objects.isNull(pageBaseDTO)) {
            pageBaseDTO = new PageBaseDTO();
        }
        PageInfo<UserInfoBO> boPageInfo = remoteUserInfoService.pageList(pageBaseDTO.getPageNum(), pageBaseDTO.getPageSize());
        PageInfo<UserInfoVO> voPageInfo = CommonConverterUtil.PageConverter(boPageInfo, UserInfoVO.class);
        return PageResult.success(voPageInfo);
    }

    @Anonymous
    @GetMapping("/test")
    public Result<Boolean> test() {
        return Result.success(remoteUserInfoService.test());
    }
}

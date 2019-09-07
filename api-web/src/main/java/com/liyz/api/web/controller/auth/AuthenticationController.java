package com.liyz.api.web.controller.auth;

import com.liyz.api.web.dto.LoginDTO;
import com.liyz.api.web.vo.LoginVO;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.result.Result;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.controller.HttpRequestUtil;
import com.liyz.common.security.annotation.Anonymous;
import com.liyz.common.security.util.JwtAuthenticationUtil;
import com.liyz.common.security.util.JwtTokenAnalysisUtil;
import com.liyz.common.security.util.LoginInfoUtil;
import com.liyz.service.member.bo.UserInfoBO;
import com.liyz.service.member.constant.MemberEnum;
import com.liyz.service.member.remote.RemoteUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注释:登录鉴权
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/27 15:28
 */
@Api(value = "用户鉴权", tags = "用户鉴权")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 401, message = "暂无授权"),
        @ApiResponse(code = 404, message = "找不到资源"),
        @ApiResponse(code = 500, message = "服务器内部错误")
        })
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenAnalysisUtil jwtTokenAnalysisUtil;
    @Autowired
    LoginInfoUtil loginInfoUtil;

    @Reference(version = "${version}")
    RemoteUserInfoService remoteUserInfoService;

    @Anonymous
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated({LoginDTO.Login.class}) @RequestBody
                                             LoginDTO loginDTO) {
        HttpServletRequest httpServletRequest = HttpRequestUtil.getRequest();
        LiteDeviceResolver resolver = new LiteDeviceResolver();
        Device device = resolver.resolveDevice(httpServletRequest);
        String ip = HttpRequestUtil.getIpAddress(httpServletRequest);
        log.info("用户登陆了，ip:{}", ip);
        if (!doAuth(loginDTO)) {
            return Result.error(CommonCodeEnum.LoginFail);
        }
        LoginVO loginVO = loginToken(device);
        return Result.success(loginVO);
    }

    private boolean doAuth(LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginDTO.getLoginName(), loginDTO.getLoginPwd());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            log.error("认证出错 method : {}", "doAuth");
            return false;
        }
    }

    private LoginVO loginToken(Device device) {
        MemberEnum.DeviceEnum deviceEnum ;
        if(device.isMobile()){
            deviceEnum = MemberEnum.DeviceEnum.MOBILE;
        }else{
            deviceEnum = MemberEnum.DeviceEnum.WEB;
        }
        UserInfoBO userInfo = loginInfoUtil.getUser();
        Date date = remoteUserInfoService.kickDownLine(userInfo.getUserId(), deviceEnum);
        final UserDetails userDetails = JwtAuthenticationUtil.create(userInfo);
        final String token = jwtTokenAnalysisUtil.generateToken(userDetails, device, date, userInfo.getUserId());
        Date expirationDateFromToken = jwtTokenAnalysisUtil.getExpirationDateFromToken(token);
        Long expirationDate = expirationDateFromToken.getTime();
        LoginVO loginVO = CommonConverterUtil.beanConverter(userInfo, LoginVO.class);
        loginVO.setExpirationDate(expirationDate);
        loginVO.setToken(token);
        return loginVO;
    }
}

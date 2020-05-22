package com.liyz.service.member.provider;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.common.base.util.DateUtil;
import com.liyz.service.member.bo.UserInfoBO;
import com.liyz.service.member.constant.MemberEnum;
import com.liyz.service.member.exception.RemoteMemberServiceException;
import com.liyz.service.member.handler.UserInfoService;
import com.liyz.service.member.model.UserInfoDO;
import com.liyz.service.member.remote.RemoteUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 注释:用户信息
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 15:46
 */
@Slf4j
@DubboService(version = "1.0.0")
public class RemoteUserInfoServiceImpl implements RemoteUserInfoService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public UserInfoBO getByUserId(Long userId) {
        UserInfoDO userInfoDO = userInfoService.getById(userId);
        if (Objects.isNull(userInfoDO)) {
            throw new RemoteMemberServiceException(CommonCodeEnum.NoData);
        }
        return CommonConverterUtil.beanConverter(userInfoDO, UserInfoBO.class);
    }

    @Override
    public PageInfo<UserInfoBO> pageList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<UserInfoDO> doList = userInfoService.listAll();
        PageInfo<UserInfoDO> doPageInfo = new PageInfo<>(doList);
        PageInfo<UserInfoBO> boPageInfo = CommonConverterUtil.PageConverter(doPageInfo, UserInfoBO.class);
        return boPageInfo;
    }

    @Override
    public UserInfoBO getByCondition(UserInfoBO userInfoBO) {
        UserInfoDO userInfoDO = null;
        try {
            userInfoDO = userInfoService.getOne(CommonConverterUtil.beanConverter(userInfoBO, UserInfoDO.class));
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanConverter(userInfoDO, UserInfoBO.class);
    }

    @Override
    public Date kickDownLine(Long userId, MemberEnum.DeviceEnum deviceEnum) {
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userId);
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDateTime localDateTime = DateUtil.minusTime(nowLocalDateTime, 10, ChronoUnit.MINUTES);
        Date tokenTime = DateUtil.convertLocalDateTimeToDate(localDateTime);
        if (MemberEnum.DeviceEnum.WEB == deviceEnum) {
            userInfoBO.setWebTokenTime(tokenTime);
        } else if (MemberEnum.DeviceEnum.MOBILE == deviceEnum) {
            userInfoBO.setAppTokenTime(tokenTime);
        } else {
            userInfoBO.setWebTokenTime(tokenTime);
            userInfoBO.setAppTokenTime(tokenTime);
        }
        userInfoService.updateById(CommonConverterUtil.beanConverter(userInfoBO, UserInfoDO.class));
        return DateUtil.convertLocalDateTimeToDate(nowLocalDateTime);
    }

    @Override
    public Boolean test() {
        log.info(".......test inter......");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}
        log.info(".......test end......");
        return Boolean.TRUE;
    }
}

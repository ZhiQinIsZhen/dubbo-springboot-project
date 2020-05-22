package com.liyz.service.member.remote;

import com.github.pagehelper.PageInfo;
import com.liyz.service.member.bo.UserInfoBO;
import com.liyz.service.member.constant.MemberEnum;
import com.sun.istack.internal.NotNull;

import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/28 14:29
 */
public interface RemoteUserInfoService {

    UserInfoBO getByUserId(@NotNull Long userId);

    PageInfo<UserInfoBO> pageList(Integer page, Integer size);

    UserInfoBO getByCondition(@NotNull UserInfoBO userInfoBO);

    Date kickDownLine(@NotNull Long userId, @NotNull MemberEnum.DeviceEnum deviceEnum);

    Boolean test();
}

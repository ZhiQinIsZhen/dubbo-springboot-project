package com.liyz.service.task.handler;

import com.liyz.common.dao.service.AbstractService;
import com.liyz.service.task.model.UserInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 15:44
 */
@Slf4j
@Service
public class UserInfoService extends AbstractService<UserInfoDO> {

    @Override
    public UserInfoDO getById(Long id) {

        return super.getById(id);
    }

    public void refreshRedis(@NotNull UserInfoDO userInfoDO) {
        refreshRedis(userInfoDO, false);
    }

    public void refreshRedis(@NotNull UserInfoDO userInfoDO, @NotNull boolean isSelect) {
        if (isSelect) {

        }
    }
}

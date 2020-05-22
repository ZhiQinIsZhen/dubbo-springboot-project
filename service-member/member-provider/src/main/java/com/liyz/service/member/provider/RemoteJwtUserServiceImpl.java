package com.liyz.service.member.provider;

import com.liyz.common.base.remote.RemoteJwtUserService;
import com.liyz.common.base.remote.bo.JwtUserBO;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.member.handler.UserInfoService;
import com.liyz.service.member.model.UserInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 注释:鉴权实现类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/11/20 10:40
 */
@Slf4j
@DubboService(version = "1.0.0")
public class RemoteJwtUserServiceImpl implements RemoteJwtUserService {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public JwtUserBO getByLoginName(String loginName) {
        UserInfoDO userInfoDO = null;
        try {
            UserInfoDO param = new UserInfoDO();
            param.setLoginName(loginName);
            userInfoDO = userInfoService.getOne(param);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        return CommonConverterUtil.beanCopy(userInfoDO, JwtUserBO.class);
    }
}

package com.liyz.service.third.provider;

import com.github.pagehelper.PageInfo;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.exception.RemoteThirdServiceException;
import com.liyz.service.third.handler.QccThirdServiceHandler;
import com.liyz.service.third.handler.service.ThirdService;
import com.liyz.service.third.remote.RemoteThirdDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:35
 */
@Slf4j
@Service(version = "1.0.0")
public class RemoteThirdDataServiceImpl implements RemoteThirdDataService {

    @Override
    public Object query(Map<String, Object> map, ThirdType thirdType) {
        ThirdService thirdService = null;
        switch (thirdType) {
            case QCC_QYJZ:
                thirdService = new QccThirdServiceHandler();
                break;
            default:
                break;
        }
        if (thirdService == null) {
            throw new RemoteThirdServiceException(CommonCodeEnum.validated);
        }
        try {
            return thirdService.query(null, map, null, HttpMethod.GET, thirdType);
        } catch (Exception e) {
            log.error("qcc 企业模糊查询失败", e);
            throw new RemoteThirdServiceException(CommonCodeEnum.ThirdServiceError);
        }
    }

    @Override
    public PageInfo<Object> queryPage(Map<String, Object> map, ThirdType thirdType, int pageNo) {
        ThirdService thirdService = null;
        switch (thirdType) {
            case QCC_QYMH:
                thirdService = new QccThirdServiceHandler();
                break;
            default:
                break;
        }
        if (thirdService == null) {
            return null;
        }
        try {
            return thirdService.queryPage(null, map, null, HttpMethod.GET, thirdType, pageNo, 20);
        } catch (Exception e) {
            log.error("qcc 企业模糊查询失败", e);
            throw new RemoteThirdServiceException(CommonCodeEnum.ThirdServiceError);
        }
    }
}

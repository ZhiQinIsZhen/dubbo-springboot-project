package com.liyz.service.third.provider;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.service.third.bo.ThirdDataPageBO;
import com.liyz.service.third.constant.ThirdType;
import com.liyz.service.third.exception.RemoteThirdServiceException;
import com.liyz.service.third.handler.*;
import com.liyz.service.third.handler.service.ThirdService;
import com.liyz.service.third.remote.RemoteThirdDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:35
 */
@Slf4j
@DubboService(version = "1.0.0")
public class RemoteThirdDataServiceImpl implements RemoteThirdDataService {

    @Override
    public Object query(ThirdDataPageBO thirdDataPageBO, ThirdType thirdType) {
        ThirdService thirdService = null;
        HttpMethod httpMethod = null;
        Map<String, Object> map = new HashMap<>();
        switch (thirdType) {
            case QCC_QYJZ:
                thirdService = new QccThirdServiceHandler(false);
                map.put("keyword", thirdDataPageBO.getKeyWord());
                httpMethod = HttpMethod.GET;
                break;
            case PY_GRFQZ:
                if (StringUtils.isBlank(thirdDataPageBO.getName())) {
                    throw new RemoteThirdServiceException(CommonCodeEnum.validated);
                }
                thirdService = new PyThirdServiceHandler(false, thirdDataPageBO.getDataType());
                map.put("documentNo", thirdDataPageBO.getKeyWord());
                map.put("name", thirdDataPageBO.getName());
                map.put("phone", thirdDataPageBO.getMobile());
                httpMethod = HttpMethod.GET;
                break;
            case BR_GRFQZ:
                if (StringUtils.isBlank(thirdDataPageBO.getName()) || StringUtils.isBlank(thirdDataPageBO.getMobile())) {
                    throw new RemoteThirdServiceException(CommonCodeEnum.validated);
                }
                thirdService = new BrThirdServiceHandler(false);
                map.put("id", thirdDataPageBO.getKeyWord());
                map.put("name", thirdDataPageBO.getName());
                map.put("cell", thirdDataPageBO.getMobile());
                httpMethod = HttpMethod.GET;
                break;
            case TD_GRFQZ:
                if (StringUtils.isBlank(thirdDataPageBO.getName()) || StringUtils.isBlank(thirdDataPageBO.getMobile())) {
                    throw new RemoteThirdServiceException(CommonCodeEnum.validated);
                }
                thirdService = new TdThirdServiceHandler(false);
                map.put("id_number", thirdDataPageBO.getKeyWord());
                map.put("account_name", thirdDataPageBO.getName());
                map.put("account_mobile", thirdDataPageBO.getMobile());
                httpMethod = HttpMethod.POST;
                break;
            default:
                break;
        }
        if (thirdService == null) {
            throw new RemoteThirdServiceException(CommonCodeEnum.validated);
        }
        try {
            return thirdService.query(null, map, null, httpMethod, thirdType);
        } catch (Exception e) {
            log.error("qcc 企业模糊查询失败 参数:{}", JSON.toJSONString(thirdDataPageBO), e);
            throw new RemoteThirdServiceException(CommonCodeEnum.ThirdServiceError);
        }
    }

    @Override
    public PageInfo<Object> queryPage(ThirdDataPageBO thirdDataPageBO, ThirdType thirdType) {
        ThirdService thirdService = null;
        HttpMethod httpMethod = null;
        Map<String, Object> map = new HashMap<>();
        switch (thirdType) {
            case QCC_QYMH:
                thirdService = new QccThirdServiceHandler(false);
                map.put("keyword", thirdDataPageBO.getKeyWord());
                httpMethod = HttpMethod.GET;
                break;
            case QCC_DWTZ:
                thirdService = new QccThirdServiceHandler(false);
                map.put("searchKey", thirdDataPageBO.getKeyWord());
                httpMethod = HttpMethod.GET;
                break;
            case FH_GRXQ:
                if (StringUtils.isBlank(thirdDataPageBO.getName())) {
                    throw new RemoteThirdServiceException(CommonCodeEnum.validated);
                }
                thirdService = new FhThirdServiceHandler(false);
                map.put("pname", thirdDataPageBO.getName());
                map.put("idcardNo", thirdDataPageBO.getKeyWord());
                if (StringUtils.isNotBlank(thirdDataPageBO.getDataType())) {
                    map.put("datatype", thirdDataPageBO.getDataType());
                }
                httpMethod = HttpMethod.GET;
                break;
            case FH_QYXQ:
                map.put("q", thirdDataPageBO.getKeyWord());
                if (StringUtils.isNotBlank(thirdDataPageBO.getDataType())) {
                    map.put("datatype", thirdDataPageBO.getDataType());
                }
                thirdService = new FhThirdServiceHandler(false, thirdDataPageBO.getDataType());
                httpMethod = HttpMethod.GET;
                break;
            default:
                break;
        }
        if (thirdService == null) {
            return null;
        }
        try {
            return thirdService.queryPage(null, map, null, httpMethod, thirdType,
                    thirdDataPageBO.getPageNum(), thirdDataPageBO.getPageSize());
        } catch (Exception e) {
            log.error("third-service 查询失败参数:{}", JSON.toJSONString(thirdDataPageBO), e);
            throw new RemoteThirdServiceException(CommonCodeEnum.ThirdServiceError);
        }
    }
}

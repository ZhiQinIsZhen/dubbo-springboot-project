package com.liyz.service.third.handler.service;

import com.github.pagehelper.PageInfo;
import com.liyz.service.third.constant.ThirdType;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/17 16:27
 */
public interface ThirdService {

    Object query(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body, ThirdType thirdType)
            throws Exception;

    Object query(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body, HttpMethod method,
                 ThirdType thirdType) throws Exception;

    PageInfo<Object> queryPage(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                               ThirdType thirdType, int pageNo) throws Exception;

    PageInfo<Object> queryPage(Map<String, String> heads, Map<String, Object> params, Map<String, Object> body,
                               HttpMethod method, ThirdType thirdType, int pageNo, int pageSize) throws Exception;
}

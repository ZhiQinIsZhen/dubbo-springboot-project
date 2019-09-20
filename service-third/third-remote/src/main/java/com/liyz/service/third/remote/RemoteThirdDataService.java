package com.liyz.service.third.remote;

import com.github.pagehelper.PageInfo;
import com.liyz.service.third.constant.ThirdType;

import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:35
 */
public interface RemoteThirdDataService {

    Object query(Map<String, Object> map, ThirdType thirdType);

    PageInfo<Object> queryPage(Map<String, Object> map, ThirdType thirdType, int pageNo);
}

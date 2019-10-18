package com.liyz.service.third.remote;

import com.github.pagehelper.PageInfo;
import com.liyz.service.third.bo.ThirdDataPageBO;
import com.liyz.service.third.constant.ThirdType;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 17:35
 */
public interface RemoteThirdDataService {

    Object query(ThirdDataPageBO thirdDataPageBO, ThirdType thirdType);

    PageInfo<Object> queryPage(ThirdDataPageBO thirdDataPageBO, ThirdType thirdType);
}

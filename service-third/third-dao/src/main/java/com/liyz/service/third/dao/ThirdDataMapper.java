package com.liyz.service.third.dao;

import com.liyz.common.dao.mapper.Mapper;
import com.liyz.service.third.model.ThirdDataDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/17 16:48
 */
public interface ThirdDataMapper extends Mapper<ThirdDataDO> {

    int replaceList(@Param("list") List<ThirdDataDO> list);
}

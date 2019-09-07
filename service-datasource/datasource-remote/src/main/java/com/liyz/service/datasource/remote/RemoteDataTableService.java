package com.liyz.service.datasource.remote;

import com.liyz.service.datasource.bo.DataTableBO;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 16:33
 */
public interface RemoteDataTableService {

    List<DataTableBO> getAll();
}

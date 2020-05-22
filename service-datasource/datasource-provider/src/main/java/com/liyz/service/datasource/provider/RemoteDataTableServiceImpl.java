package com.liyz.service.datasource.provider;

import com.liyz.common.base.enums.CommonCodeEnum;
import com.liyz.common.base.util.CommonConverterUtil;
import com.liyz.service.datasource.bo.DataTableBO;
import com.liyz.service.datasource.dao.DataTableMapper;
import com.liyz.service.datasource.exception.RemoteDatasourceServiceException;
import com.liyz.service.datasource.model.DataTableDO;
import com.liyz.service.datasource.remote.RemoteDataTableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 16:35
 */
@Slf4j
@DubboService(timeout = 2000, version = "1.0.0", actives = 1000)
public class RemoteDataTableServiceImpl implements RemoteDataTableService {

    @Autowired
    DataTableMapper dataTableMapper;

    @Override
    public List<DataTableBO> getAll() {
        try {
            List<DataTableDO> doList = dataTableMapper.selectAll();
            return CommonConverterUtil.ListConverter(doList, DataTableBO.class);
        } catch (Exception e) {
            log.error("查询出错", e);
        }
        throw new RemoteDatasourceServiceException(CommonCodeEnum.RemoteServiceFail);
    }

}

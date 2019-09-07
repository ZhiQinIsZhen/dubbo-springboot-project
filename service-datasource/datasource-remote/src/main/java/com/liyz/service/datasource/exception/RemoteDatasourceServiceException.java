package com.liyz.service.datasource.exception;

import com.liyz.common.base.enums.ServiceCodeEnum;
import com.liyz.common.base.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/29 14:39
 */
@Slf4j
public class RemoteDatasourceServiceException extends RemoteServiceException {

    private static final long serialVersionUID = 6574646237803730595L;

    public RemoteDatasourceServiceException() {
    }

    public RemoteDatasourceServiceException(String message) {
        super(message);
    }

    public RemoteDatasourceServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteDatasourceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteDatasourceServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteDatasourceException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}

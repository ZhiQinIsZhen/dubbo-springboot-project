package com.liyz.service.task.exception;

import com.liyz.common.base.enums.ServiceCodeEnum;
import com.liyz.common.base.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 21:05
 */
@Slf4j
public class RemoteTaskServiceException extends RemoteServiceException {
    private static final long serialVersionUID = 6574646237803730595L;

    public RemoteTaskServiceException() {
    }

    public RemoteTaskServiceException(String message) {
        super(message);
    }

    public RemoteTaskServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteTaskServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteTaskServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteMemberException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}

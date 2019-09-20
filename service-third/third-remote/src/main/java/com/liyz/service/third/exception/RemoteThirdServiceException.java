package com.liyz.service.third.exception;

import com.liyz.common.base.enums.ServiceCodeEnum;
import com.liyz.common.base.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/20 15:10
 */
@Slf4j
public class RemoteThirdServiceException extends RemoteServiceException {

    private static final long serialVersionUID = 6574646237803730595L;

    public RemoteThirdServiceException() {
    }

    public RemoteThirdServiceException(String message) {
        super(message);
    }

    public RemoteThirdServiceException(String message, String code) {
        super(message, code);
    }

    public RemoteThirdServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteThirdServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum);
        log.info("remoteMemberException {}  {}", serviceCodeEnum.getCode(), serviceCodeEnum.getMessage());
    }
}

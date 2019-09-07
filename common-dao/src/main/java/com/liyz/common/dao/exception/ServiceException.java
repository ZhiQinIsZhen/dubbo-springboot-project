package com.liyz.common.dao.exception;

/**
 * 注释: sql异常类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/28 18:10
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    /**
     * 构造方法
     *
     * @param message 异常描述
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param message 异常描述
     * @param cause   异常栈
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

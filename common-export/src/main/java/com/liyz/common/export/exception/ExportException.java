package com.liyz.common.export.exception;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/9 10:31
 */
public class ExportException extends RuntimeException {

    public ExportException(String msg) {
        super(msg);
    }

    public ExportException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

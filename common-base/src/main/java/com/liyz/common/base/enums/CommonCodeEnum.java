package com.liyz.common.base.enums;

/**
 * 注释: 返回信息状态码
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/5 16:05
 */
public enum  CommonCodeEnum implements ServiceCodeEnum {

    success("0", "成功"),
    validated("10000", "参数校验失败"),
    UnckowException("10001", "未知异常"),
    AuthorizationFail("10002", "认证失败"),
    AuthorizationTimeOut("10003", "认证超时"),
    LoginFail("10004", "用户名或者密码错误"),
    RemoteServiceFail("10005", "服务异常"),
    NoData("10006", "暂无数据"),
    NonTable("10007", "该表不存在"),
    NonDatasource("10008", "该库不存在"),
    ParameterError("10009", "参数异常"),
    ;

    private String code;

    private String message;

    CommonCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.spf.common.enums;

/**
 * Created by SPF on 2017/7/19.
 */
public enum CommonResultCode {

    SUCCESS("200","执行成功"),

    NULL_ARGUMENT_EXCEPTION("comm_10_0001", "参数为空"),

    /**
     * 参数个数错误
     */
    ILLEGAL_NUMBER_OF_ARGUMENT("comm_10_0101", "参数个数错误"),
    /**
     * 参数类型错误
     */
    ILLEGAL_ARGUMENT_TYPE("comm_10_0201", "参数类型错误"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT_EXCEPTION("comm_10_0999", "非法参数"),

    /**
     * 数据库异常
     */
    DB_EXCEPRION("comm_10_1999", "数据库异常"),

    /**
     * 业务异常
     */
    BIZ_EXCEPRION("comm_10_8999", "业务异常"),

    /**
     * 系统异常(系统的基础异常)
     */
    SYS_EXCEPRION("comm_10_9999", "系统异常"),

    /**
     * 系统异常(系统内部异常，比如工具类常，组件内部异常)
     */
    NEST_EXCEPRION("comm_20_9999", "系统异常"),

    /**
     * 网络异常
     */
    NET_EXCEPRION("comm_90_9999", "网络异常"),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPRION("comm_99_9999", "未知异常");

    private String code;

    private String maseege;

    CommonResultCode(String code, String maseege) {
        this.code = code;
        this.maseege = maseege;
    }

    public String getCode() {
        return code;
    }

    public String getMaseege() {
        return maseege;
    }
}

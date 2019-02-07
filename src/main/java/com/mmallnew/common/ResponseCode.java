package com.mmallnew.common;

/**
 * 枚举类，用于枚举user的状态码
 *
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 14:45 2019/2/7
 */
public enum ResponseCode {

    /**
     *
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(2, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(3, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

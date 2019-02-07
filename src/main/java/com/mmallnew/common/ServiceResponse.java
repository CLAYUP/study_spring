package com.mmallnew.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 用户服务端响应对象
 * 注解作用：在序列化时，去除空的字段
 *
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 14:30 2019/2/7
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status) {
        this.status = status;
    }

    private ServiceResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static <T> ServiceResponse<T> createBySuccess() {
        return new ServiceResponse<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccessMessage(String msg) {
        return new ServiceResponse<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data) {
        return new ServiceResponse<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServiceResponse<T> createBySuccess(String msg, T data) {
        return new ServiceResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServiceResponse<T> createByError() {
        return new ServiceResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServiceResponse<T> createByErrorMessage(String errorMsg) {
        return new ServiceResponse<>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ServiceResponse<T> createByErrorCodeMessage(int errorCode, String errorMsg) {
        return new ServiceResponse<>(errorCode, errorMsg);
    }

    /**
     * 注解：防止序列化
     *
     * @return :boolean
     * @author :Y.
     * @date :15:03 2019/2/7
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

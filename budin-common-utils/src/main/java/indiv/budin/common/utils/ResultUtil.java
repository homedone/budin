package indiv.budin.common.utils;

import indiv.budin.common.constants.CommonCode;
import indiv.budin.common.utif.ResultCode;

import java.io.Serializable;

public class ResultUtil<T> implements Serializable {

    /**
     * 状态
     */
    public boolean status;
    /**
     * 状态码
     */
    public Integer code;
    /**
     * 返回信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public ResultUtil() {

    }

    public ResultUtil(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
        this.status = resultCode.isSuccess();
    }

    public static <Type> ResultUtil<Type> successWithData(Type data) {
        return new ResultUtil<>(CommonCode.SUCCESS, data);
    }

    public static <Type> ResultUtil<Type> successWithoutData() {
        return new ResultUtil<>(CommonCode.SUCCESS, null);
    }

    public static <Type> ResultUtil<Type> fail() {
        return new ResultUtil<>(CommonCode.FAIL, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return status;
    }
}

package com.ji.jichat.common.pojo;


import cn.hutool.core.lang.Assert;
import com.ji.jichat.common.enums.ErrorCodeEnum;
import com.ji.jichat.common.exception.ServiceException;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用返回
 *
 * @param <T> 数据泛型
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     *
     * @see ErrorCodeEnum#getCode()
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     *
     * @see ErrorCodeEnum#getMsg() ()
     */
    private String msg;


    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!Objects.equals(ErrorCodeEnum.SUCCESS.getCode(), code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCodeEnum errorCodeEnum) {
        return error(errorCodeEnum.getCode(), errorCodeEnum.getMsg());
    }

    public static <Void> CommonResult<Void> success() {
        CommonResult<Void> result = new CommonResult<>();
        result.code = ErrorCodeEnum.SUCCESS.getCode();
        result.msg = "success";
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = ErrorCodeEnum.SUCCESS.getCode();
        result.data = data;
        result.msg = "success";
        return result;
    }

    private static boolean isSuccess(Integer code) {
        return Objects.equals(code, ErrorCodeEnum.SUCCESS.getCode());
    }

    private boolean isSuccess() {
        return isSuccess(code);
    }

    // ========= 和 Exception 异常体系集成 =========

    /**
     * 判断是否有异常。如果有，则抛出 {@link ServiceException} 异常
     */
    public void checkError() throws ServiceException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new ServiceException(code, msg);
    }


}

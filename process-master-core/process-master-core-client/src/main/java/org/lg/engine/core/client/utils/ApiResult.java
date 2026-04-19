package org.lg.engine.core.client.utils;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApiResult<T> implements Results<T> , Serializable {
    private static transient I18nMessageLoader messageLoader;
    protected int code;
    protected String msg;
    protected T data;

    public static I18nMessageLoader messageLoader() {
        return messageLoader;
    }

    public static void messageLoader(I18nMessageLoader messageLoader) {
        if (messageLoader != null) {
            ApiResult.messageLoader = messageLoader;
        }

    }

    public <D> ApiResult<D> cast() {
        return new ApiResult(this.msg, this.code);
    }

    public <D> ApiResult<D> copy() {
        return new ApiResult(this.msg, this.code);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(data);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult();
    }

    public static <T> ApiResult<T> fail(String message, int errorCode, Object... args) {
        if (message == null) {
            message = messageLoader.getMessage(errorCode, args);
        }

        return new ApiResult(message, errorCode);
    }

    public static <T> ApiResult<T> fail(String message, int errorCode) {
        return new ApiResult(message, errorCode);
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult(message);
    }

    public static <T> ApiResult<T> fail(int errorCode) {
        return new ApiResult(errorCode, new Object[0]);
    }

    public static <T> ApiResult<T> fail(int errorCode, Object... args) {
        return new ApiResult(errorCode, args);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> ApiResult<T> code(int code) {
        return code == 0 ? new ApiResult() : new ApiResult(code, new Object[0]);
    }

    public boolean isSuccess() {
        return this.code == 0 || this.code == 200;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {

        return JSON.toJSONString(this);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }


    public ApiResult(ErrorCode errorCode) {
        this.code = errorCode.code;
        this.msg = messageLoader.getMessage(errorCode, new Object[0]);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(int errorCode, Object... args) {
        this.code = errorCode;
        this.msg = messageLoader.getMessage(errorCode, args);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(ErrorCode errorCode, Object... args) {
        this.code = errorCode.code;
        this.msg = messageLoader.getMessage(errorCode, args);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult() {
        this.code = 200;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(T data) {
        this.code = 200;
        this.data = data;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(T data, int code) {
        this(data, (String) null, code);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(String msg, int code) {
        this(null, msg, code);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(String msg) {
        this(null, (Object) msg);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public ApiResult(T data, String msg, int code) {
        this.code = code;
        this.data = data;
        this.msg = msg == null ? messageLoader.getMessage(code, new Object[0]) : msg;
    }


    static {
        messageLoader = I18nMessageLoader.NOOP;
    }
}
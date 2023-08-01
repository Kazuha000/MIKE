package com.cqupt.mike.until;

import java.io.Serializable;

/**
 * 响应结果
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    //状态码，为200则成功
    private int resultCode;
    //页面message
    private String message;
    //数据
    private T data;

    public Result() {
    }

    public Result(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
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

    @Override
    public String toString() {
        return "Result{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

package com.zhu.facepay.domain.res;

import lombok.Data;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName ResultData.java
 * @Description TODO
 * @createTime 2022年11月21日 18:57:00
 */
@Data
public class ResultData<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
}


package com.spf.common.result;

/**
 * Created by SPF on 2017/7/19.
 */
public class  BizResult<T> extends SimpleResult {

    private T data;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

package com.spf.common.result;

import com.spf.common.enums.CommonResultCode;

import java.io.Serializable;

/**
 * Created by SPF on 2017/7/19.
 */
public class SimpleResult implements Serializable {

    private String code;

    private String message;

    private String description;

    public void setFail(CommonResultCode commonResultCode,String description) {
        this.code = commonResultCode.getCode();
        this.message = commonResultCode.getMaseege();
        this.description = description;
    }

    public void setSuccess(String description) {
        this.code = "200";
        this.message = "success";
        this.description = description;
    }

    public void setSuccess() {
        this.code = "200";
        this.message = "success";
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}

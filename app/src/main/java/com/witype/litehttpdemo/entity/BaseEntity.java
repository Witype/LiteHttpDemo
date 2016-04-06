package com.witype.litehttpdemo.entity;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class BaseEntity {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Integer.parseInt(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

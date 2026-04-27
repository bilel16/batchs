package com.bna.habil.infrastructure.security.model;


import java.util.ArrayList;

public class ResponseHabil {
    private Integer returnCode;

    private String message;

    private Object data = new ArrayList<>();

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseHabil() {
        super();
    }

    public ResponseHabil(Integer returnCode, String message, Object data) {
        super();
        this.returnCode = returnCode;
        this.message = message;
        this.data = data;
    }

}

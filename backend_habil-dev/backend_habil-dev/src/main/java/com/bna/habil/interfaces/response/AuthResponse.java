package com.bna.habil.interfaces.response;

import com.bna.habil.domain.entities.extra.AuthData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    @JsonProperty
    private int returnCode;

    @JsonProperty
    private String message;

    @JsonProperty
    private AuthData habilData;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthData getHabilData() {
        return habilData;
    }

    public void setHabilData(AuthData habilData) {
        this.habilData = habilData;
    }

    @Override
    public String toString() {
        return "AuthResponse [returnCode=" + returnCode + ", message=" + message + ", habilData=" + habilData + "]";
    }

    public AuthResponse(int returnCode, String message, AuthData habilData) {
        super();
        this.returnCode = returnCode;
        this.message = message;
        this.habilData = habilData;
    }

    public AuthResponse() {
        super();
    }


}

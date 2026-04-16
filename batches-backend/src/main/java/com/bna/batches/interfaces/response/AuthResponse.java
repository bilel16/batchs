package com.bna.batches.interfaces.response;

import com.bna.batches.domain.entities.extra.AuthData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    @JsonProperty private int returnCode;
    @JsonProperty private String message;
    @JsonProperty private AuthData habilData;

    @Override
    public String toString() {
        return "AuthResponse [returnCode=" + returnCode + ", message=" + message + ", habilData=" + habilData + "]";
    }
}

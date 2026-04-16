package com.bna.batches.domain.entities.extra;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body sent to the external authentication service.
 */
public class AuthUser {

    @JsonProperty private String adresseIp;
    @JsonProperty private String codeApp;

    public AuthUser(String adresseIp, String codeApp) {
        this.adresseIp = adresseIp;
        this.codeApp = codeApp;
    }

    public String getAdresseIp() { return adresseIp; }
    public void setAdresseIp(String adresseIp) { this.adresseIp = adresseIp; }
    public String getCodeApp() { return codeApp; }
    public void setCodeApp(String codeApp) { this.codeApp = codeApp; }

    @Override
    public String toString() {
        return "{\"adresseIp\":\"" + adresseIp + "\",\"codeApp\":\"" + codeApp + "\"}";
    }
}

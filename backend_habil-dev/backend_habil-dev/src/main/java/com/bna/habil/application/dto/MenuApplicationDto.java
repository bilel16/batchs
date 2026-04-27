package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuApplicationDto {
    private String codAppApp;

    private String codMenuMenu;

    private String libMenuMenu;

    @Override
    public String toString() {
        return "MenuApplication{" +
                "codAppApp='" + codAppApp + '\'' +
                ", codMenuMenu='" + codMenuMenu + '\'' +
                ", libMenuMenu='" + libMenuMenu + '\'' +
                '}';
    }

}

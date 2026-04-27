package com.bna.habil.application.dto;

import lombok.Data;

@Data
public class AddApplicationDto {
    private String codApp;
    private String libApp;
    private String libLab;

    private boolean central;
    private boolean regional;
    private boolean agence;
}

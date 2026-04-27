package com.bna.habil.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PackDto {
    private String codPackPack;
    private String libPackPack;
    private String descPack;
    private String codNivhPfl;
    private String codCatpPfl;
    private Integer boolActifPack;
    private Date datCrePack;
    private String userCrePack;
}

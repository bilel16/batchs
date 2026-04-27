package com.bna.habil.application.dto;

import lombok.Data;

@Data
public class PackProfilDto {
    private String codPackPack;
    private String codPflPfl;
    private String codTstrcTstrc;
    private Integer boolEtat;

    // Optional: Include related entity names for display
    private String libPackPack;    // Pack label
    private String libPflPfl;      // Profile label
}

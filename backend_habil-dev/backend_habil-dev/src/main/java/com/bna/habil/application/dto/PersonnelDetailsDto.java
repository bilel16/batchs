package com.bna.habil.application.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonnelDetailsDto {
    private String mat;

    private String nom_prenom;

    private String email;

    private Boolean cod_stat_user;

    private Integer cod_strc_strc;

    private Integer cod_tstr_tstr;

    private String libelleStructure;

    // Constructor without libelleStructure for backward compatibility
    public PersonnelDetailsDto(String mat, String nomComplet, String email,
                               Boolean codStatUser, Integer codStrcStrc, Integer codTstrTstr) {
        this(mat, nomComplet, email, codStatUser, codStrcStrc, codTstrTstr, null);
    }
}

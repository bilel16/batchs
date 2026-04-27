package com.bna.habil.domain.entities.entitiesId;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilMenuApplicationId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String codAppApp;
    private String codMenuMenu;
    private String codPflPfl;
    private String codTstrcTstrc;

}
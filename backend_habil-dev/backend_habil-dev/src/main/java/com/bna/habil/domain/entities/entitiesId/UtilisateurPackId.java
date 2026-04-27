package com.bna.habil.domain.entities.entitiesId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurPackId implements Serializable {
    private String numMatrUser;
    private String codPackPack;
}
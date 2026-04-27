package com.bna.habil.domain.entities.entitiesId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Composite Key Class
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackProfilId implements Serializable {
    private String codPackPack;
    private String codPflPfl;
}
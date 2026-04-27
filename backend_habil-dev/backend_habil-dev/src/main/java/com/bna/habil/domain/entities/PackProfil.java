package com.bna.habil.domain.entities;

import com.bna.habil.domain.entities.entitiesId.PackProfilId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PACK_PROFIL", schema = "habil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PackProfilId.class)
public class PackProfil {

    @Id
    @Column(name = "COD_PACK_PACK", length = 100)
    private String codPackPack;

    @Id
    @Column(name = "COD_PFL_PFL", length = 100)
    private String codPflPfl;

    @Column(name = "COD_TSTRC_TSTRC", length = 20)
    private String codTstrcTstrc;

    @Column(name = "BOOL_ETAT")
    private Integer boolEtat;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_PACK_PACK", insertable = false, updatable = false)
    private Pack pack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_PFL_PFL", insertable = false, updatable = false)
    private Profil profil;
}
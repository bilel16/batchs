package com.bna.habil.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PACK", schema = "habil")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pack {

    @Id
    @Column(name = "COD_PACK_PACK", length = 100)
    private String codPackPack;

    @Column(name = "LIB_PACK_PACK", length = 100, nullable = false)
    private String libPackPack;

    @Column(name = "DESC_PACK", length = 255)
    private String descPack;

    @Column(name = "COD_NIVH_PFL", length = 10)
    private String codNivhPfl;

    @Column(name = "COD_CATP_PFL", length = 10)
    private String codCatpPfl;

    @Column(name = "BOOL_ACTIF_PACK")
    private Integer boolActifPack;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE_PACK")
    private Date datCrePack;

    @Column(name = "USER_CRE_PACK", length = 10)
    private String userCrePack;

    // Relationships
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PackProfil> packProfils = new ArrayList<>();

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UtilisateurPack> utilisateurPacks = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        datCrePack = new Date();
        if (boolActifPack == null) {
            boolActifPack = 1;
        }
    }

}
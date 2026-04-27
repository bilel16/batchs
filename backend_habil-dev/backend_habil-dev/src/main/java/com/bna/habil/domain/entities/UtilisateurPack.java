package com.bna.habil.domain.entities;

import com.bna.habil.domain.entities.entitiesId.UtilisateurPackId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "UTILISATEUR_PACK", schema = "habil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UtilisateurPackId.class)
public class UtilisateurPack {

    @Id
    @Column(name = "NUM_MATR_USER", length = 100)
    private String numMatrUser;

    @Id
    @Column(name = "COD_PACK_PACK", length = 100)
    private String codPackPack;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEB_AFFECT")
    private Date datDebAffect;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN_AFFECT")
    private Date datFinAffect;

    @Column(name = "BOOL_ETAT_AFFECT")
    private Integer boolEtatAffect;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_AFFECT_PACK")
    private Date datAffectPack;

    @Column(name = "USER_AFFECT_PACK", length = 10)
    private String userAffectPack;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUM_MATR_USER", insertable = false, updatable = false)
    private Personnel personnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COD_PACK_PACK", insertable = false, updatable = false)
    private Pack pack;

    @PrePersist
    protected void onCreate() {
        datAffectPack = new Date();
    }
}

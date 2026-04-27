package com.bna.habil.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TYPE_STRUCTURE")
public class TypeStructure implements Serializable {

    @Id
    @Column(name = "COD_TSTR_TSTR")
    private Integer code;

    @Column(name = "LIB_TSTR_TSTR")
    private String libelle;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_MAJ")
    private Date dateMaj;
}
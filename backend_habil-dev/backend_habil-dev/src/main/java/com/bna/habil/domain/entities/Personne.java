package com.bna.habil.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serial;

@Getter
@Entity
@Table(name = "PERSONNE", schema = "smile")
public class Personne implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    private Integer num_seq_pers;

    private String num_pce_pers;

    private int cod_tpce_tpce;

    private String nom_nom_pers;

    private String nom_prn_pers;

    private String adr_mail_pers;


    public String getAdr_mail_pers() {
        return adr_mail_pers;
    }

    public void setAdr_mail_pers(String adr_mail_pers) {
        this.adr_mail_pers = adr_mail_pers;
    }

    public void setNum_seq_pers(Integer num_seq_pers) {
        this.num_seq_pers = num_seq_pers;
    }

    public int getNum_seq_pers() {
        return num_seq_pers;
    }

    public void setNum_seq_pers(int num_seq_pers) {
        this.num_seq_pers = num_seq_pers;
    }

    public String getNum_pce_pers() {
        return num_pce_pers;
    }

    public void setNum_pce_pers(String num_pce_pers) {
        this.num_pce_pers = num_pce_pers;
    }

    public int getCod_tpce_tpce() {
        return cod_tpce_tpce;
    }

    public void setCod_tpce_tpce(int cod_tpce_tpce) {
        this.cod_tpce_tpce = cod_tpce_tpce;
    }

    public String getNom_nom_pers() {
        return nom_nom_pers;
    }

    public void setNom_nom_pers(String nom_nom_pers) {
        this.nom_nom_pers = nom_nom_pers;
    }

    public String getNom_prn_pers() {
        return nom_prn_pers;
    }

    public void setNom_prn_pers(String nom_prn_pers) {
        this.nom_prn_pers = nom_prn_pers;
    }


}
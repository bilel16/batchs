package com.bna.habil.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "PERSONNEL")
public class Personnel {
    @Id
    @Column(name = "num_matr_user")
    private String mat;

    private Boolean cod_stat_user;

    private Integer cod_strc_strc;

    @Column(name = "NUM_CIN_USER")
    private String cin;

    @Column(name = "COD_TYP_USER")
    private String cod_typ;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_STAT_USER")
    private Date dat_stat;

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public Boolean getCod_stat_user() {
        return cod_stat_user;
    }

    public void setCod_stat_user(Boolean cod_stat_user) {
        this.cod_stat_user = cod_stat_user;
    }

    public int getCod_strc_strc() {
        return cod_strc_strc;
    }

    public void setCod_strc_strc(int cod_strc_strc) {
        this.cod_strc_strc = cod_strc_strc;
    }
}

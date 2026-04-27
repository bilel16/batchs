package com.bna.smile.model.clotureDomaine.model;

public class StatSouscriptionVo {
    public StatSouscriptionVo() {
    }
    
    private Long cod_prd_plc;
    private String lib_prd_plc;
    private Long nbr_Sousc_att;
    private String mnt_Sousc_att;
    private Long nbr_Sousc_Val;
    private String mnt_Sousc_Val;
    private Long nbr_Sousc_Rej;
    private String mnt_Sousc_Rej;

    public void setCod_prd_plc(Long cod_prd_plc) {
        this.cod_prd_plc = cod_prd_plc;
    }

    public Long getCod_prd_plc() {
        return cod_prd_plc;
    }

    public void setLib_prd_plc(String lib_prd_plc) {
        this.lib_prd_plc = lib_prd_plc;
    }

    public String getLib_prd_plc() {
        return lib_prd_plc;
    }

    public void setNbr_Sousc_att(Long nbr_Sousc_att) {
        this.nbr_Sousc_att = nbr_Sousc_att;
    }

    public Long getNbr_Sousc_att() {
        return nbr_Sousc_att;
    }

    public void setMnt_Sousc_att(String mnt_Sousc_att) {
        this.mnt_Sousc_att = mnt_Sousc_att;
    }

    public String getMnt_Sousc_att() {
        return mnt_Sousc_att;
    }

    public void setNbr_Sousc_Val(Long nbr_Sousc_Val) {
        this.nbr_Sousc_Val = nbr_Sousc_Val;
    }

    public Long getNbr_Sousc_Val() {
        return nbr_Sousc_Val;
    }

    public void setMnt_Sousc_Val(String mnt_Sousc_Val) {
        this.mnt_Sousc_Val = mnt_Sousc_Val;
    }

    public String getMnt_Sousc_Val() {
        return mnt_Sousc_Val;
    }

    public void setNbr_Sousc_Rej(Long nbr_Sousc_Rej) {
        this.nbr_Sousc_Rej = nbr_Sousc_Rej;
    }

    public Long getNbr_Sousc_Rej() {
        return nbr_Sousc_Rej;
    }

    public void setMnt_Sousc_Rej(String mnt_Sousc_Rej) {
        this.mnt_Sousc_Rej = mnt_Sousc_Rej;
    }

    public String getMnt_Sousc_Rej() {
        return mnt_Sousc_Rej;
    }
}

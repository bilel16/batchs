package com.bna.smile.web.gestioncaisse.view;

import com.bna.commun.model.DetailCaisDevAg;

public class DetailCaisseStructureView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
  private DetailCaisDevAg detailCaisDevAg;
  private String libelleDevise;
  private String montInitDcda;
  private String montCvinDcda;
  private String montActuDcda;
  private String montCvacDcda;
  private String montFinDcda;
  private String montCvfnDcda;
    
  
  public DetailCaisseStructureView() {
  }

    public void setDetailCaisDevAg(DetailCaisDevAg detailCaisDevAg) {
        this.detailCaisDevAg = detailCaisDevAg;
    }

    public DetailCaisDevAg getDetailCaisDevAg() {
        return detailCaisDevAg;
    }

    public void setMontInitDcda(String montInitDcda) {
        this.montInitDcda = montInitDcda;
    }

    public String getMontInitDcda() {
        return montInitDcda;
    }

    public void setMontCvinDcda(String montCvinDcda) {
        this.montCvinDcda = montCvinDcda;
    }

    public String getMontCvinDcda() {
        return montCvinDcda;
    }

    public void setMontActuDcda(String montActuDcda) {
        this.montActuDcda = montActuDcda;
    }

    public String getMontActuDcda() {
        return montActuDcda;
    }

    public void setMontCvacDcda(String montCvacDcda) {
        this.montCvacDcda = montCvacDcda;
    }

    public String getMontCvacDcda() {
        return montCvacDcda;
    }

    public void setMontFinDcda(String montFinDcda) {
        this.montFinDcda = montFinDcda;
    }

    public String getMontFinDcda() {
        return montFinDcda;
    }

    public void setMontCvfnDcda(String montCvfnDcda) {
        this.montCvfnDcda = montCvfnDcda;
    }

    public String getMontCvfnDcda() {
        return montCvfnDcda;
    }

    public void setLibelleDevise(String libelleDevise) {
        this.libelleDevise = libelleDevise;
    }

    public String getLibelleDevise() {
        return libelleDevise;
    }
}

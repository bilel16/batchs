package com.bna.smile.model.domainecontratcompte.procuration.model;

import java.util.Date;

import com.bna.commun.model.ContratCptId;
import com.oxia.fwk.core.ValueObject;

public class MandatRecherche extends ValueObject {

    private ContratCptId contratCptId;
    private String codEtat;
    private Long codStrcConcer;
    private String libStrcConcer;
    private String codEtatAttente;
    private String codMenu;
    private Date dateDeb;
    private Date dateFin;
    


    public MandatRecherche() {
    }

    public void setContratCptId(ContratCptId contratCptId) {
        this.contratCptId = contratCptId;
    }

    public ContratCptId getContratCptId() {
        return contratCptId;
    }

    public void setCodEtat(String codEtat) {
        this.codEtat = codEtat;
    }

    public String getCodEtat() {
        return codEtat;
    }

    public void setCodStrcConcer(Long codStrcConcer) {
        this.codStrcConcer = codStrcConcer;
    }

    public Long getCodStrcConcer() {
        return codStrcConcer;
    }

    public void setCodEtatAttente(String codEtatAttente) {
        this.codEtatAttente = codEtatAttente;
    }

    public String getCodEtatAttente() {
        return codEtatAttente;
    }

    public void setCodMenu(String codMenu) {
        this.codMenu = codMenu;
    }

    public String getCodMenu() {
        return codMenu;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setLibStrcConcer(String libStrcConcer) {
        this.libStrcConcer = libStrcConcer;
    }

    public String getLibStrcConcer() {
        return libStrcConcer;
    }
}

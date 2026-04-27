package com.bna.smile.model.domainecommun.model;

import com.oxia.fwk.core.ValueObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'encapsuler les parametres (Client et qualité) 
 * et de renvoyer la liste des personnes qui sont en relation avec 
 * ce client en cette qualité
 * @author : Mdimagh Lassaad
 * @since : 28/06/07
 */
public class ParamListPersonneQualiteClientVo extends ValueObject{
 private Long numSeqPers; // numero sequentiel du client
 private Long codQualQual; // le code de la qualité
 private List listePersonneClient = new ArrayList(); // liste des pers_Cli
    public ParamListPersonneQualiteClientVo() {
    }

    public void setNumSeqPers(Long numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public Long getNumSeqPers() {
        return numSeqPers;
    }

    public void setCodQualQual(Long codQualQual) {
        this.codQualQual = codQualQual;
    }

    public Long getCodQualQual() {
        return codQualQual;
    }

    public void setListePersonneClient(List listePersonneClient) {
        this.listePersonneClient = listePersonneClient;
    }

    public List getListePersonneClient() {
        return listePersonneClient;
    }
}

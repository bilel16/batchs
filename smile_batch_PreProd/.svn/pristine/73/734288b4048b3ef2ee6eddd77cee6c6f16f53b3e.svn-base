package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.TypeModification;
import com.oxia.fwk.core.ValueObject;
/**
 * Classe de données pour la modification de la relation Client Personne
 * @author Mdimagh Med Lassaad
 * @since  02/07/07 
 */
public class ParamModificationQualitePersClientVo extends ValueObject {
    private Long numSeqPers; // numero sequentiel du client
    private Long codQualQual; // le code de la qualité
    private TypeModification typeModification;
    private String matriculeUser;
    private List listePersonneClient = new ArrayList(); // liste des pers_Cli
    
    public ParamModificationQualitePersClientVo() {
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

    public void setTypeModification(TypeModification typeModification) {
        this.typeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return typeModification;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public String getMatriculeUser() {
        return matriculeUser;
    }

    public void setListePersonneClient(List listePersonneClient) {
        this.listePersonneClient = listePersonneClient;
    }

    public List getListePersonneClient() {
        return listePersonneClient;
    }
}

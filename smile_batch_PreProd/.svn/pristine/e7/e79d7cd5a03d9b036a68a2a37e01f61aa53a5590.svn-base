package com.bna.smile.model.domainecaisse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oxia.fwk.core.ValueObject;

/**
 * classe qui pêrmet d'extraire la liste des caisses d'une structure 
 * pour une journée si la date est garnie
 * @author Mdimagh Med Lassaad
 * @since 26/03/2008
 * 
 */
public class ListeCaisseStructureVo extends ValueObject{

    private Long numSeqSjc;
    private Long codeStructure;
    private Date dateJournee;
    private String codeStatus;
    private String typeCaisse;
    private String numMatriculeUser;
    
    private List listeCaisseStructure = new ArrayList(0);
    
    public ListeCaisseStructureVo() {
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setDateJournee(Date dateJournee) {
        this.dateJournee = dateJournee;
    }

    public Date getDateJournee() {
        return dateJournee;
    }

    public void setListeCaisseStructure(List listeCaisseStructure) {
        this.listeCaisseStructure = listeCaisseStructure;
    }

    public List getListeCaisseStructure() {
        return listeCaisseStructure;
    }


    public void setNumMatriculeUser(String numMatriculeUser) {
        this.numMatriculeUser = numMatriculeUser;
    }

    public String getNumMatriculeUser() {
        return numMatriculeUser;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setTypeCaisse(String typeCaisse) {
        this.typeCaisse = typeCaisse;
    }

    public String getTypeCaisse() {
        return typeCaisse;
    }

    public void setNumSeqSjc(Long numSeqSjc) {
        this.numSeqSjc = numSeqSjc;
    }

    public Long getNumSeqSjc() {
        return numSeqSjc;
    }
}

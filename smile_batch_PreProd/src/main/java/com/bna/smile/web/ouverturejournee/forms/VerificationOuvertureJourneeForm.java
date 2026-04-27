package com.bna.smile.web.ouverturejournee.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.struts.action.ActionForm;

public class VerificationOuvertureJourneeForm extends ActionForm {
    public VerificationOuvertureJourneeForm() {
    }
   
    private InitialisationView initialisationView = new InitialisationView();
 
    private String dateDerniereJournee;
    private String nouvelleDatejournee;

    private String codeStructure;
    private String matricule;
    private Collection listesRenBatch     = new ArrayList(0);
    private Collection listesRenAtt     = new ArrayList(0);
    private Collection listeSouscription = new ArrayList(0);
    private Collection liquidPart     = new ArrayList(0);
    private Collection liquidAvantEch     = new ArrayList(0);
    private Collection liquidAEcheance     = new ArrayList(0);
    private Collection listesInteretServi     = new ArrayList(0);
    private Collection liquiTraitSoir     = new ArrayList(0);
    private String existPlacement="false";
    private Collection listeContratPlacement5Jour;
    private Collection listeContratPlacementAEche;
    
    public void setCodeStructure(String codeStructure) {
        this.codeStructure = codeStructure;
    }

    public String getCodeStructure() {
        return codeStructure;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

  
    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setDateDerniereJournee(String dateDerniereJournee) {
        this.dateDerniereJournee = dateDerniereJournee;
    }

    public String getDateDerniereJournee() {
        return dateDerniereJournee;
    }

    public void setNouvelleDatejournee(String nouvelleDatejournee) {
        this.nouvelleDatejournee = nouvelleDatejournee;
    }

    public String getNouvelleDatejournee() {
        return nouvelleDatejournee;
    }

   

    public void setListeSouscription(Collection listeSouscription) {
        this.listeSouscription = listeSouscription;
    }

    public Collection getListeSouscription() {
        return listeSouscription;
    }

    public void setLiquidPart(Collection liquidPart) {
        this.liquidPart = liquidPart;
    }

    public Collection getLiquidPart() {
        return liquidPart;
    }

    public void setLiquidAvantEch(Collection liquidAvantEch) {
        this.liquidAvantEch = liquidAvantEch;
    }

    public Collection getLiquidAvantEch() {
        return liquidAvantEch;
    }

    public void setLiquidAEcheance(Collection liquidAEcheance) {
        this.liquidAEcheance = liquidAEcheance;
    }

    public Collection getLiquidAEcheance() {
        return liquidAEcheance;
    }

    public void setListesInteretServi(Collection listesInteretServi) {
        this.listesInteretServi = listesInteretServi;
    }

    public Collection getListesInteretServi() {
        return listesInteretServi;
    }

    public void setLiquiTraitSoir(Collection liquiTraitSoir) {
        this.liquiTraitSoir = liquiTraitSoir;
    }

    public Collection getLiquiTraitSoir() {
        return liquiTraitSoir;
    }

    public void setExistPlacement(String existPlacement) {
        this.existPlacement = existPlacement;
    }

    public String getExistPlacement() {
        return existPlacement;
    }


    public void setListeContratPlacement5Jour(Collection listeContratPlacement5Jour) {
        this.listeContratPlacement5Jour = listeContratPlacement5Jour;
    }

    public Collection getListeContratPlacement5Jour() {
        return listeContratPlacement5Jour;
    }

    public void setListeContratPlacementAEche(Collection listeContratPlacementAEche) {
        this.listeContratPlacementAEche = listeContratPlacementAEche;
    }

    public Collection getListeContratPlacementAEche() {
        return listeContratPlacementAEche;
    }

    public void setListesRenBatch(Collection listesRenBatch) {
        this.listesRenBatch = listesRenBatch;
    }

    public Collection getListesRenBatch() {
        return listesRenBatch;
    }

    public void setListesRenAtt(Collection listesRenAtt) {
        this.listesRenAtt = listesRenAtt;
    }

    public Collection getListesRenAtt() {
        return listesRenAtt;
    }
}

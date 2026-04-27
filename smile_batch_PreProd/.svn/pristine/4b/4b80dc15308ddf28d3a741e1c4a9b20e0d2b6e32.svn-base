package com.bna.smile.web.commun.model;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.Personne;

import com.oxia.fwk.core.ValueObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pouvoir extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private Personne demandeur;
    private String typePouvoir;//"":Aucun pouvoir;N:demandeur est inconnu;T:Titulaire du compte;C:Membre Co-Titilaire;M:Mandataire;I:personne incappable
    private Mandat mandat;
    private List listMandatPersonne  = new ArrayList();
    private List listMandatOperation = new ArrayList();
    private List listCotitulaire = new ArrayList();
    private String codPieceAnnexe;
    private String numPieceAnnexe;
    public Pouvoir() {
    }

    public void setTypePouvoir(String typePouvoir) {
        this.typePouvoir = typePouvoir;
    }

    public String getTypePouvoir() {
        return typePouvoir;
    }

    public void setListMandatPersonne(List listMandatPersonne) {
        this.listMandatPersonne = listMandatPersonne;
    }

  

    public void setListMandatOperation(List listMandatOperation) {
        this.listMandatOperation = listMandatOperation;
    }

    public void setListCotitulaire(List listCotitulaire) {
        this.listCotitulaire = listCotitulaire;
    }

    public List getListCotitulaire() {
        return listCotitulaire;
    }

    public List getListMandatPersonne() {
        return listMandatPersonne;
    }

    public List getListMandatOperation() {
        return listMandatOperation;
    }

    public void setDemandeur(Personne demandeur) {
        this.demandeur = demandeur;
    }

    public Personne getDemandeur() {
        return demandeur;
    }

    public void setMandat(Mandat mandat) {
        this.mandat = mandat;
    }

    public Mandat getMandat() {
        return mandat;
    }
    public  PersonneDemandeur chargerPouvoir(PersonneDemandeur personneDemandeur) {
        
        //recuperation de l'objet pouvoir  de la session
       // personneDemandeur.setNomNomPersDemandeur("");
       // personneDemandeur.setNomPrnPersDemandeur("");
        personneDemandeur.setTypePouvoir("");
        personneDemandeur.setMessageTexte("");
 
        personneDemandeur.setTypePouvoir(this.getTypePouvoir());
        ///si personne inexistante
        if(this.getTypePouvoir().equals("N")){
           personneDemandeur.setMessageTexte("Le demandeur est non reconnu en tant que personne dans la banque."); 
        }else if(this.getTypePouvoir().equals("I")){
            personneDemandeur.setMessageTexte("Le demandeur est une personne incappable"); 
        }else if(this.getTypePouvoir().equals("D")){
           personneDemandeur.setMessageTexte("Le demandeur est dèjà décédé");            
        
        }else if(this.getTypePouvoir().equals("")){
            personneDemandeur.setMessageTexte("Aucun pouvoir."); 
        }else {
            personneDemandeur.setTypePouvoir(this.getTypePouvoir()); 
            //si titulaire
            if(this.getTypePouvoir().equals("T")){
                personneDemandeur.setMessageTexte("Titulaire du compte"); 
            }else if(this.getTypePouvoir().equals("C")){
                personneDemandeur.setMessageTexte("Membre Co-Titilaire"); 
            }else if(this.getTypePouvoir().equals("M")){
             if (this.getMandat()!=null){
                if(this.getMandat().getCodTypMand().equals("G")){
                    // mandat général 
                     personneDemandeur.setMessageTexte(" dossier Mandat choisi : " +  this.getMandat().getNumDemMand()+", type : " + this.getMandat().getCodTypMand() +" , Signature : " + this.getMandat().getCodSignMand());  
                }else{
                    // mandat spécial ou juridique
                    MandatOperation mandatOperation = (MandatOperation) this.getListMandatOperation().get(0);
                    personneDemandeur.setMessageTexte(" dossier Mandat choisi : " +  this.getMandat().getNumDemMand()+", type : " + this.getMandat().getCodTypMand() +" , Signature : " +  mandatOperation.getCodSignMaop() + " (selon opération appropriée)");  
                    
                }
              }// Fin if mandat null
            }  
        }
        //affectation du nom prenom demandeur 
        // if(this.getDemandeur().getNumSeqPers()!=null){
             personneDemandeur.setCodTpceTpceDemandeur(this.getDemandeur().getTypePiece().getCodTpceTpce().toString());
             personneDemandeur.setNumPcePersDemandeur(this.getDemandeur().getNumPcePers());
             personneDemandeur.setNomNomPersDemandeur(this.getDemandeur().getNomNomPers());
             personneDemandeur.setNomPrnPersDemandeur(this.getDemandeur().getNomPrnPers());            
             //personneDemandeur.setNumSeqDemandeur(this.getDemandeur().getNumSeqPers().toString());
             
        // }
          
        return personneDemandeur;
    }

    public void setCodPieceAnnexe(String codPieceAnnexe) {
        this.codPieceAnnexe = codPieceAnnexe;
    }

    public String getCodPieceAnnexe() {
        return codPieceAnnexe;
    }

    public void setNumPieceAnnexe(String numPieceAnnexe) {
        this.numPieceAnnexe = numPieceAnnexe;
    }

    public String getNumPieceAnnexe() {
        return numPieceAnnexe;
    }
}

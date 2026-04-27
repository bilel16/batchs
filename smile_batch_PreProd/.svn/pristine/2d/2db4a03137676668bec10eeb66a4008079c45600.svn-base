package com.bna.smile.web.operationguichet.view;

import com.bna.commun.model.MontantMiseDiposition;

import com.bna.commun.util.DateHandler;
import com.bna.commun.util.FormuleHandler;
import com.bna.commun.util.StrHandler;

import com.bna.smile.model.constant.Constants;

import java.util.Date;

public class ValidationMiseAdispositionView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    public ValidationMiseAdispositionView() {
    }
    
    
    
    private MontantMiseDiposition montantMiseDiposition = new MontantMiseDiposition();
   

    //-----------Type Mise à disposition
     private String codTypeMmad;
     private String numMmadMmad;
     
     //---------- Titre 
     private String titre ="";
      //------ Emetteur
       private String codTpemMmad;
       private String numNpemMmad;
       private String nomEmetMmad;
       private String nomPremMmad;
  
    //----- Bénéficiaire
      private String codTpceMmad;
      private String numPceMmad;
      private String nomNomMmad;
      private String nomPrnMmad;
      
    //------ Structure 
     private String codStrcStrcE;
     private String libStrcStrcE;
     private String codStrcStrcR;
     private String libStrcStrcR;
     
    //------Devise
    private String codDevDev;
    private String libDevDev;
    
   //------- Date 
    private String datMmadMmad;
    private String datValMmad;
   
   //------- Montant 
    private String montantDinars;
    private String tauxCourMmad;
    private String montantDevise;
    
    //--- pays 
    private String codPaysPays;
    private String libPaysPays;
    
    private String codRtpeMmad;
    private String libOrigMmad;
    private String messageTexte;

    public void setMontantMiseDiposition(MontantMiseDiposition montantMiseDiposition) {
        this.montantMiseDiposition = montantMiseDiposition;
    }

    public MontantMiseDiposition getMontantMiseDiposition() {
        return montantMiseDiposition;
    }

    public void setCodDevDev(String codDevDev) {
        this.codDevDev = codDevDev;
    }

    public String getCodDevDev() {
        String codeDevise;
        if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getDevise()!=null && montantMiseDiposition.getDevise().getCodDevDev() != null ){
            codeDevise = montantMiseDiposition.getDevise().getCodDevDev().toString();
        }else{
            codeDevise ="";
        }
        return codeDevise;
    }

    public void setLibDevDev(String libDevDev) {
        this.libDevDev = libDevDev;
    }

    public String getLibDevDev() {
    
        String libelle;
        if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getDevise() != null && montantMiseDiposition.getDevise().getLibDevDev() != null){
            libelle = montantMiseDiposition.getDevise().getLibDevDev();
            
        }else {
            libelle = "";
        }
        
        return libelle;
    }

    public void setMontantDinars(String montantDinars) {
        this.montantDinars = montantDinars;
    }

    public String getMontantDinars() {
    String montant;
    if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getMontMontMmad() != null){
        montant = StrHandler.formatMontant(montantMiseDiposition.getMontMontMmad(),3);
        
    }else { 
    montant = "";
    }
        return montant;
    }


    public void setCodStrcStrcE(String codStrcStrcE) {
        this.codStrcStrcE = codStrcStrcE;
    }

    public String getCodStrcStrcE() {
        return codStrcStrcE;
    }

    public void setCodStrcStrcR(String codStrcStrcR) {
        this.codStrcStrcR = codStrcStrcR;
    }

    public String getCodStrcStrcR() {
        String code;
        if (montantMiseDiposition != null && montantMiseDiposition.getStructureByCodEmetStrc() !=null ){
            code = montantMiseDiposition.getStructureByCodEmetStrc().getCodStrcStrc().toString();
        }else {
            code ="";
        }
        return (code);
        
    }

    public void setLibStrcStrcE(String libStrcStrcE) {
    
        this.libStrcStrcE = libStrcStrcE;
    }

    public String getLibStrcStrcE() {
     String libelle;
     if (montantMiseDiposition != null && montantMiseDiposition.getStructureByCodEmetStrc() !=null ){
         libelle = montantMiseDiposition.getStructureByCodEmetStrc().getLibStrcStrc();
     }else {
         libelle = "";
     }
        return libelle;
    }

    public void setMessageTexte(String messageTexte) {
        this.messageTexte = messageTexte;
    }

    public String getMessageTexte() {
        return messageTexte;
    }

 
    public void setNumPceMmad(String numPceMmad) {
    
        this.numPceMmad = numPceMmad;
    }

    public String getNumPceMmad() {
       String numero;
        if (montantMiseDiposition != null && montantMiseDiposition.getNumPceMmad() != null){
            numero = montantMiseDiposition.getNumPceMmad();
        }else {
            numero ="";
            
        }
        return numero;
    }

    public void setCodTpceMmad(String codTpceMmad) {
    
        this.codTpceMmad = codTpceMmad;
    }

    public String getCodTpceMmad() {
        String code;
        if (montantMiseDiposition != null && montantMiseDiposition.getCodTpceMmad()  != null){
         code = montantMiseDiposition.getCodTpceMmad().toString() ;
        }else{
            code ="";
        }
        return code;
    }

    public void setNomNomMmad(String nomNomMmad) {
        this.nomNomMmad = nomNomMmad;
    }

    public String getNomNomMmad() {
      String nom;
      if (montantMiseDiposition != null && montantMiseDiposition.getNomNomMmad() != null){
          nom = montantMiseDiposition.getNomNomMmad();
      }else{
          nom = "";
      }
     
        return nom;
    }

    public void setNomPrnMmad(String nomPrnMmad) {
        this.nomPrnMmad = nomPrnMmad;
    }

    public String getNomPrnMmad() {
        String nom;
        if (montantMiseDiposition != null && montantMiseDiposition.getNomPrnMmad()  != null){
        nom = montantMiseDiposition.getNomPrnMmad();
        } else {
            nom ="";
        }
        return nom;
    }

    public void setCodTpemMmad(String codTpemMmad) {
        this.codTpemMmad = codTpemMmad;
    }

    public String getCodTpemMmad() {
        String code;
        if (montantMiseDiposition != null && montantMiseDiposition.getCodTpemMmad() != null){
            code = montantMiseDiposition.getCodTpemMmad().toString();
        }else {
            code = "";
        }
        return code;
    }

    public void setNumNpemMmad(String numNpemMmad) {
        this.numNpemMmad = numNpemMmad;
    }

    public String getNumNpemMmad() {
        String numero;
        if (montantMiseDiposition != null && montantMiseDiposition.getNumNpemMmad() != null){
            numero = montantMiseDiposition.getNumNpemMmad();
        } else {
            numero = "";
        }
        return numero;
    }

    public void setNomEmetMmad(String nomEmetMmad) {
        this.nomEmetMmad = nomEmetMmad;
    }

    public String getNomEmetMmad() {
        
        String nom;
        if (montantMiseDiposition != null && montantMiseDiposition.getNomEmetMmad() != null){
            nom =  montantMiseDiposition.getNomEmetMmad() ;
        } else {
            nom ="";
        }
        return nom;
    }

    public void setNomPremMmad(String nomPremMmad) {
        this.nomPremMmad = nomPremMmad;
    }

    public String getNomPremMmad() {
        String nom;
        if (montantMiseDiposition != null && montantMiseDiposition.getNomPremMmad() != null){
         nom = montantMiseDiposition.getNomPremMmad() ;
        } else {
            nom= "";
        }
        return nom;
    }

    public void setCodRtpeMmad(String codRtpeMmad) {
        this.codRtpeMmad = codRtpeMmad;
    }

    public String getCodRtpeMmad() {
        return codRtpeMmad;
    }

    public void setLibOrigMmad(String libOrigMmad) {
        this.libOrigMmad = libOrigMmad;
    }

    public String getLibOrigMmad() {
        return libOrigMmad;
    }

    public void setLibStrcStrcR(String libStrcStrcR) {
        this.libStrcStrcR = libStrcStrcR;
    }

    public String getLibStrcStrcR() {
        return libStrcStrcR;
    }

    public void setCodTypeMmad(String codTypeMmad) {
        this.codTypeMmad = codTypeMmad;
    }

    public String getCodTypeMmad() {
        return codTypeMmad;
    }

    public void setTauxCourMmad(String tauxCourMmad) {
        this.tauxCourMmad = tauxCourMmad;
    }

    public String getTauxCourMmad() {
        String cours;
        if (montantMiseDiposition != null && montantMiseDiposition.getTauxCourMmad() != null){
            cours = StrHandler.formatMontant(montantMiseDiposition.getTauxCourMmad(),2);
        } else {
            cours = "";
        }
     
        return cours;
        
    }



    public void setCodPaysPays(String codPaysPays) {
        this.codPaysPays = codPaysPays;
    }

    public String getCodPaysPays() {
        return codPaysPays;
    }

    public void setLibPaysPays(String libPaysPays) {
        this.libPaysPays = libPaysPays;
    }

    public String getLibPaysPays() {
        return libPaysPays;
    }

    public void setMontantDevise(String montantDevise) {
        this.montantDevise = montantDevise;
    }

    public String getMontantDevise() {
        String montant;
        if (montantMiseDiposition != null && montantMiseDiposition.getMontDevMmad() != null){
            montant = StrHandler.formatMontant(montantMiseDiposition.getMontDevMmad(),2);
        } else {
            montant = "";
        }
        
        return montant;
        
    }


    public void setDatMmadMmad(String datMmadMmad) {
        this.datMmadMmad = datMmadMmad;
    }

    public String getDatMmadMmad() {
    String date;
    if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getDatMmadMmad() != null ){
        date = DateHandler.dateToStr( montantMiseDiposition.getDatMmadMmad());
    } else {
        date ="";
    }
        return date;
    }

    public void setNumMmadMmad(String numMmadMmad) {
        this.numMmadMmad = numMmadMmad;
    }

    public String getNumMmadMmad() {
     String numero;
     if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()!= null ){
         numero = montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad() ;
     }else {
         numero ="";
     }
     
        return numero;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        StringBuffer titre = new StringBuffer() ;
        if (montantMiseDiposition != null &&  montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad() != null ){
            if (montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad().equalsIgnoreCase(Constants.CODE_MISE_A_DISPOSITION)){
                titre.append("Mise à disposition numéro : ");
            }else if (montantMiseDiposition.getMontantMiseDipositionId().getCodTypeMmad().equalsIgnoreCase(Constants.CODE_MONEY_GRAM)){
                titre.append( "MoneyGram numéro :");
            
            } else {
                titre.append("Mise à disposition numéro : ");
            }
    
         titre.append(montantMiseDiposition.getMontantMiseDipositionId().getNumMmadMmad()); 
        }else{
           titre.append("");
       }
        return titre.toString();
    }

    public void setDatValMmad(String datValMmad) {
        this.datValMmad = datValMmad;
    }

    public String getDatValMmad() {
        String date;
        if (montantMiseDiposition.getMontantMiseDipositionId() != null && montantMiseDiposition.getDatMmadMmad() != null ){
            date = DateHandler.dateToStr( montantMiseDiposition.getDatMmadMmad());
        } else {
            date ="";
        }
            return date;
        
    }
}

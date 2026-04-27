package com.bna.smile.web.commun.forms;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.smile.model.constant.Constants;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Collections;
import java.util.Iterator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class RechercheMandatPouvoirForm  extends ActionForm {
    public RechercheMandatPouvoirForm() {
    }
    
    private List listMandats                              = new ArrayList();
    private List listMandatsPersonne                      = new ArrayList();
    private List listdesOperations                        = new ArrayList();
    private List listdesMandatsPersonneChoisi             = new ArrayList();//elle contient les numero seqPers
    private List listdesMandatairesChoisi                 = new ArrayList();//elle contient les mandatPersonne cochés
    
    private List  listdesOperationsChoisi                 = new ArrayList();
    private List  listCotitulaire                         = new ArrayList();
    private List listdesCotitulaireChoisi                 = new ArrayList();//elle contient les numero seqPers des cotit cochés
    private List listMembreCotitulaireChoisi              = new ArrayList();//elle contient les menbres cotitulaires cochés
    private String typeSignatureCotitulaire;
    private String typeCotitulaire;
    private String numMandChoisi;
    private Mandat mandatChoisi;
    private String refMand;
    private String reqCode;
    private String codeAgence;
    private String codeProduit;
    private String numCCpt;
    private Personne personneDemandeur;
    private String numSeqPersDemandeur;
    private String codTpceTpce;
    private String numPcePers;
   
    private String codeOperation;
    private String typePouvoir;
    private String messageTexte;
    private String numeroOperationChoisi;
    private String nombreOperation;
    private String typeMandat;
    private Integer nombreMinimumMandataire;
    private String typeSignature;
    private MandatOperation mandatOperationChoisi;
    private String actionError;
    private String erreurDemandeur;
    private String actionFormAppelante;
    
   // private String numeroMandatOperation;
    
    public void clearForm() {
        listMandats          = null;
        listMandatsPersonne = null;
        listdesMandatsPersonneChoisi          = new ArrayList();
        listdesOperations                     = new ArrayList();
        listdesOperationsChoisi               = new ArrayList();
        mandatChoisi =null;
        refMand = "";
        typePouvoir ="";
        messageTexte = "";
        numMandChoisi ="";
        numeroOperationChoisi="";
        typeMandat="";
        nombreMinimumMandataire=0;
        typeSignature="";
        mandatOperationChoisi       =null;
        listdesMandatairesChoisi    =new ArrayList();
        listdesCotitulaireChoisi    =new ArrayList();
        listMembreCotitulaireChoisi =new ArrayList();
        personneDemandeur           =null;
        numSeqPersDemandeur ="";
        nombreOperation     ="";
        typeSignatureCotitulaire="";
        typeCotitulaire ="";
        actionError="";
        erreurDemandeur="";
       // numeroMandatOperation="";
    }


   
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
                                 
       ActionErrors errors = new ActionErrors();
    try{
       
        boolean testChoixPersonne =false;
        int nombrePersonne = 0;
        if (reqCode.equalsIgnoreCase("validate")) { 
         //--------------- CAS MANDATAIRE ------------------------///
         if (typePouvoir.equals(Constants.COD_TYPE_POUVOIR_MANDATAIRE)){
            //--(1)-----verification si le  nombre des signataires est valide pour executer cette opération 
            ///recherche du nombre des signataires
            listdesMandatairesChoisi = getListSigantaireCoche();
            nombrePersonne = listdesMandatairesChoisi.size();
            
            ///extraire le nombre minimum des signataire si mandat speciale si autre valeurs déja affectés
            if(typeMandat.equals(Constants.COD_TYPE_MAND_SPECIAL)) {
                if ((numeroOperationChoisi == null || numeroOperationChoisi.equals("")) ){
                     errors.add("PouvoirError",  new ActionMessage("rechercheMandatPouvoir.pasOperation"));
                }else {
                     rechercherMandatOperationChoisi();
                     nombreMinimumMandataire = mandatOperationChoisi.getNbrMinMaop().intValue();
                     typeSignature           = mandatOperationChoisi.getCodSignMaop(); 
                }
            }
            /// si signature conjointe verification du nombre des signataires 
             if (typeSignature.equals(Constants.COD_TYPE_SIGNATURE_CONJOINTE) && nombrePersonne < nombreMinimumMandataire.intValue()  ){
                   errors.add("PouvoirError", new ActionMessage("rechercheMandatPouvoir.nombrePersonneMinGeneral"));
              }
             if(nombrePersonne==0){
                 errors.add("PouvoirError", new ActionMessage("rechercheMandatPouvoir.pasDePersonne"));
             }
         }else if (typePouvoir.equals(Constants.COD_TYPE_POUVOIR_COTITULAIRE)){
         //----------------------- CAS CO-TITULAIRE -----------------------------//
          listMembreCotitulaireChoisi =getListCotitulaireCoche();
           int  nombreCotitulaire = listMembreCotitulaireChoisi.size();
            if (typeSignatureCotitulaire.equals(Constants.COD_TYPE_SIGNATURE_CONJOINTE)
                && nombreCotitulaire !=  listCotitulaire.size()){
                errors.add("PouvoirError", new ActionMessage("rechercheMandatPouvoir.nombreCotitulaire"));
            }
         }else if (typePouvoir.equals(Constants.COD_TYPE_POUVOIR_TITULAIRE)){
         //----------------------- CAS TITULAIRE -----------------------------//
         
         }
       }
       if(errors.size()>0){
           actionError="O";
          //faire initialiser liste des mandataire choisie à ""
          int size = listdesMandatsPersonneChoisi.size();
          listdesMandatsPersonneChoisi.clear();
          for(int i=0;i<size;i++){
               listdesMandatsPersonneChoisi.add("");
          }
           //faire initialiser liste des mandat operation choisie à ""
           size = listdesOperationsChoisi.size();
           listdesOperationsChoisi.clear();
           for(int i=0;i<size;i++){
                listdesOperationsChoisi.add("");
           }
           //faire initialiser liste des cotitulaires choisie à ""
           size = listdesCotitulaireChoisi.size();
           listdesCotitulaireChoisi.clear();
           for(int i=0;i<size;i++){
                listdesCotitulaireChoisi.add("");
           }
           
       }
       
       } catch (Exception e) {
          System.out.println("Erreur validate form --------- " + e.getMessage());
       }
       return errors;
   }

    private void rechercherMandatOperationChoisi() {
        Iterator it = listdesOperations.iterator();
        //-----------   Rechercher le mandatOperation
        for(;it.hasNext();  ){
        MandatOperation mandatOperation = (MandatOperation) it.next();
            if (mandatOperation.getMandatOperationId().getNumMaopMaop().equals(Long.valueOf(numeroOperationChoisi))
            && mandatOperation.getMandatOperationId().getCodOperOper().equals(Long.valueOf(codeOperation))
            && mandatOperation.getMandatOperationId().getNumMandMand().equals(Long.valueOf(numMandChoisi))){
                mandatOperationChoisi = mandatOperation;         
                break;
            }
            
        }
    }

    private List getListSigantaireCoche() {
        List listMandataireCoches = new ArrayList();
        int nombrePersonne=0;
        //--------------Calculer le nombre des personnnes -----------------
        for(Iterator it =listdesMandatsPersonneChoisi.iterator();it.hasNext(); ){
            String NumeroPersonne = (String) it.next();
            if (!NumeroPersonne.equals("")){
                nombrePersonne++;
                  Iterator itPersMandat = listMandatsPersonne.iterator();
                 //---------- Recherche Mandat Personne dans liste MandatPersonne
                   for (;itPersMandat.hasNext(); ) {     
                     MandatPersonne mandatPersonne= (MandatPersonne) itPersMandat.next();
                     if (mandatPersonne.getPersonne().getNumSeqPers().equals(Long.valueOf(NumeroPersonne))){
                         listMandataireCoches.add(mandatPersonne) ;
                         break;
                     }
                   }// fin for 
            }
        }// fin for
        
     
        return listMandataireCoches;
    }
    
    private List getListCotitulaireCoche(){
        List listCotitulaireCoches = new ArrayList();
        int nombrePersonne=0;
        //--------------Calculer le nombre des personnnes -----------------
        for(Iterator it =listdesCotitulaireChoisi.iterator();it.hasNext(); ){
            String NumeroPersonne = (String) it.next();
            if (!NumeroPersonne.equals("")){
                nombrePersonne++;
                  Iterator itCotit = listCotitulaire.iterator();
                 //---------- Recherche l'entite cotitulaire cochée dans liste des cotitulaires
                   for (;itCotit.hasNext(); ) {     
                     CoTitulaire coTitulaire= (CoTitulaire) itCotit.next();
                     if (coTitulaire.getPersonne().getNumSeqPers().equals(Long.valueOf(NumeroPersonne))){
                         listCotitulaireCoches.add(coTitulaire) ;
                         break;
                     }
                   }// fin for 
            }
        }// fin for
        
        
        return listCotitulaireCoches;
        
    }    

    
    
       
    public void setRefMand(String refMand) {
        this.refMand = refMand;
    }

    public String getRefMand() {
        return refMand;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setNumCCpt(String numCCpt) {
        this.numCCpt = numCCpt;
    }

    public String getNumCCpt() {
        return numCCpt;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
    }

    public String getCodeOperation() {
        return codeOperation;
    }

    public void setMessageTexte(String messageTexte) {
        this.messageTexte = messageTexte;
    }

    public String getMessageTexte() {
        return messageTexte;
    }

    public void setCodTpceTpce(String codTpceTpce) {
        this.codTpceTpce = codTpceTpce;
    }

    public String getCodTpceTpce() {
        return codTpceTpce;
    }

    public void setNumPcePers(String numPcePers) {
        this.numPcePers = numPcePers;
    }

    public String getNumPcePers() {
        return numPcePers;
    }

    public void setTypePouvoir(String typePouvoir) {
        this.typePouvoir = typePouvoir;
    }

    public String getTypePouvoir() {
        return typePouvoir;
    }


    public Collection getListCotitulaire() {
        return listCotitulaire;
    }

    public void setNumMandChoisi(String numMandChoisi) {
        this.numMandChoisi = numMandChoisi;
    }

    public String getNumMandChoisi() {
        return numMandChoisi;
    }



   

    public void setNumeroOperationChoisi(String numeroOperationChoisi) {
        this.numeroOperationChoisi = numeroOperationChoisi;
    }

    public String getNumeroOperationChoisi() {
        return numeroOperationChoisi;
    }

    


 
    public void setTypeMandat(String typeMandat) {
        this.typeMandat = typeMandat;
    }

    public String getTypeMandat() {
        return typeMandat;
    }


   

    public void setNombreMinimumMandataire(Integer nombreMinimumMandataire) {
        this.nombreMinimumMandataire = nombreMinimumMandataire;
    }

    public Integer getNombreMinimumMandataire() {
        return nombreMinimumMandataire;
    }

    public void setTypeSignature(String typeSignature) {
        this.typeSignature = typeSignature;
    }

    public String getTypeSignature() {
        return typeSignature;
    }



    public void setMandatOperationChoisi(MandatOperation mandatOperationChoisi) {
        this.mandatOperationChoisi = mandatOperationChoisi;
    }

    public MandatOperation getMandatOperationChoisi() {
        return mandatOperationChoisi;
    }


   
    public void setPersonneDemandeur(Personne personneDemandeur) {
        this.personneDemandeur = personneDemandeur;
    }

    public Personne getPersonneDemandeur() {
        return personneDemandeur;
    }

    public void setNumSeqPersDemandeur(String numSeqPersDemandeur) {
        this.numSeqPersDemandeur = numSeqPersDemandeur;
    }

    public String getNumSeqPersDemandeur() {
        return numSeqPersDemandeur;
    }

    public void setNombreOperation(String nombreOperation) {
        this.nombreOperation = nombreOperation;
    }

    public String getNombreOperation() {
        return nombreOperation;
    }

    public void setListMandats(List listMandats) {
        this.listMandats = listMandats;
    }

    public List getListMandats() {
        return listMandats;
    }

    public void setListMandatsPersonne(List listMandatsPersonne) {
        this.listMandatsPersonne = listMandatsPersonne;
    }

    public List getListMandatsPersonne() {
        return listMandatsPersonne;
    }

    public void setListdesOperations(List listdesOperations) {
        this.listdesOperations = listdesOperations;
    }

    public List getListdesOperations() {
        return listdesOperations;
    }

    public void setListdesMandatsPersonneChoisi(List listdesMandatsPersonneChoisi) {
        this.listdesMandatsPersonneChoisi = listdesMandatsPersonneChoisi;
    }

    public List getListdesMandatsPersonneChoisi() {
        return listdesMandatsPersonneChoisi;
    }

   

    public void setListdesOperationsChoisi(List listdesOperationsChoisi) {
        this.listdesOperationsChoisi = listdesOperationsChoisi;
    }

    public List getListdesOperationsChoisi() {
        return listdesOperationsChoisi;
    }

    public void setListCotitulaire(List listCotitulaire) {
        this.listCotitulaire = listCotitulaire;
    }

    public void setListdesCotitulaireChoisi(List listdesCotitulaireChoisi) {
        this.listdesCotitulaireChoisi = listdesCotitulaireChoisi;
    }

    public List getListdesCotitulaireChoisi() {
        return listdesCotitulaireChoisi;
    }

    public void setTypeSignatureCotitulaire(String typeSignatureCotitulaire) {
        this.typeSignatureCotitulaire = typeSignatureCotitulaire;
    }

    public String getTypeSignatureCotitulaire() {
        return typeSignatureCotitulaire;
    }

    public void setTypeCotitulaire(String typeCotitulaire) {
        this.typeCotitulaire = typeCotitulaire;
    }

    public String getTypeCotitulaire() {
        return typeCotitulaire;
    }

    public void setListMembreCotitulaireChoisi(List listMembreCotitulaireChoisi) {
        this.listMembreCotitulaireChoisi = listMembreCotitulaireChoisi;
    }

    public List getListMembreCotitulaireChoisi() {
        return listMembreCotitulaireChoisi;
    }

    public void setActionError(String actionError) {
        this.actionError = actionError;
    }

    public String getActionError() {
        return actionError;
    }

    public void setErreurDemandeur(String erreurDemandeur) {
        this.erreurDemandeur = erreurDemandeur;
    }

    public String getErreurDemandeur() {
        return erreurDemandeur;
    }

    public void setMandatChoisi(Mandat mandatChoisi) {
        this.mandatChoisi = mandatChoisi;
    }

    public Mandat getMandatChoisi() {
        return mandatChoisi;
    }


    public void setActionFormAppelante(String actionFormAppelante) {
        this.actionFormAppelante = actionFormAppelante;
    }

    public String getActionFormAppelante() {
        return actionFormAppelante;
    }

    public void setListdesMandatairesChoisi(List listdesMandatairesChoisi) {
        this.listdesMandatairesChoisi = listdesMandatairesChoisi;
    }

    public List getListdesMandatairesChoisi() {
        return listdesMandatairesChoisi;
    }
}

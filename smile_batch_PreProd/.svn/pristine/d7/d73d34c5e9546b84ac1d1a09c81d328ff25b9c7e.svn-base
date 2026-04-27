package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.CaisseStructure;
import com.bna.commun.model.CaisseStructureId;
import com.bna.commun.model.MouvementsCaisses;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecaisse.traitement.InsertMouvementCaisseTrt;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.util.List;

/**
 * Classe permet la Prise En Charge des versements
 * @author Mdimagh Lassaad 
 * @since 28/02/2008
 */
public class PecVersementTrt extends Traitement {
    Context context = ContextHandler.getContext();

    public PecVersementTrt() {
    }
    
    public IValueObject perform (IValueObject vo){
     OperationMoyPay operationMoyPay = (OperationMoyPay) vo;
     try{
         InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
         
         operationMoyPay =   (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);
         
         
        ///--------------------------------------------------------------
        ///----------- Insertion mouvement caisse -----------------------
        ///--------------------------------------------------------------
         MouvementsCaisses   mouvementCaisse = new  MouvementsCaisses ();
         mouvementCaisse.setDatMvtMc(operationMoyPay.getDatOperOmp());
         mouvementCaisse.setCodSensMc("C");
         mouvementCaisse.setDevise(operationMoyPay.getDevise());
         mouvementCaisse.setPersonnel(operationMoyPay.getPersonnelInitiateur());
         mouvementCaisse.setTache(operationMoyPay.getTache());
                //------------------------------//
                //----- Verifier la caisse -----//
                
         ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
         CaisseStructureId caisseStructureId = new CaisseStructureId();
         caisseStructureId.setDatJrnJrn(DateHandler.strToDate(DateHandler.dateToStr(operationMoyPay.getDatOperOmp())));
         caisseStructureId.setNumCaisAc(operationMoyPay.getAffectationCaisseStructure().getNumCaisAc());
         caisseStructureId.setCodStrcStrc(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
         CaisseStructure caisseStructureRech  = (CaisseStructure) searchEngine.get(CaisseStructure.class,caisseStructureId);
         
         if (caisseStructureRech != null || caisseStructureRech.getCodStatCsag().equals("1")){
           mouvementCaisse.setCaisseStructure(caisseStructureRech);
         } else {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             if (caisseStructureRech != null ){
                 StringBuffer text = 
                     new StringBuffer("Erreur dans PecVersementTrt : Caisse inexistante ");
             } else {
             StringBuffer text = 
                 new StringBuffer("Erreur dans PecVersementTrt : la caisse num :"+ operationMoyPay.getAffectationCaisseStructure().getNumCaisAc()+ " est désactivée.");
             }
             erreur.setCode("200");
             erreur.setKey("caisse");
             operationMoyPay.addError(erreur);
             return (operationMoyPay);
             
         }
                 
        
         mouvementCaisse.setLibOperMc("Versement");
         mouvementCaisse.setNumMvtrMc(operationMoyPay.getNumOperOmp());
         mouvementCaisse.setCodStatMc(Long.valueOf(0));
              
        if (operationMoyPay.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)){
             mouvementCaisse.setMontMvtMc(operationMoyPay.getMontDinOmp());
         }else {
             mouvementCaisse.setMontMvtMc(operationMoyPay.getMontDevOmp());
         }
       
        InsertMouvementCaisseTrt insertMVTCaisseTrt = new InsertMouvementCaisseTrt();
        insertMVTCaisseTrt.setSecurityFlag(false);
        mouvementCaisse = (MouvementsCaisses) insertMVTCaisseTrt.exec(mouvementCaisse);
        
        if (mouvementCaisse.hasError()){
             operationMoyPay.addError(mouvementCaisse.getErrors().get(0));
        }
        
        return operationMoyPay;
          
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("Erreur dans PecVersementTrt : ");
         text.append(e.toString());
         erreur.setCode("200");
         erreur.setDescription(text.toString());
         erreur.setKey("caisse");
         operationMoyPay.addError(erreur);
         return (operationMoyPay);
        }
 
    }
    
    public void genCroText(ValueObject vo){
        
    }
    
    public String getNumeroTache(IValueObject vo){
        OperationMoyPay operationMoyPay = (OperationMoyPay) vo;
        if (operationMoyPay.getTache().getOperation() != null && operationMoyPay.getTache().getOperation().getCodOperOper() != null){
            if ( operationMoyPay.getTache().getOperation().getCodOperOper() .equals(Constants.COD_OPER_VERSEMENT)){
                return "10101";    
            }else if (operationMoyPay.getTache().getOperation().getCodOperOper() .equals(Constants.COD_OPER_VERSEMENT_DEPLACE)){
                return "10301";
            }
        }
        return "10101";
    }
}

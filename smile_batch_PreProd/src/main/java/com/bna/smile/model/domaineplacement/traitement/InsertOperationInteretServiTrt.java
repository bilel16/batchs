package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class InsertOperationInteretServiTrt extends Traitement{
    public InsertOperationInteretServiTrt() {
    }
    public IValueObject perform (IValueObject vo ) {
        
            ParamInsertInteret paramInsertInteret = (ParamInsertInteret)vo;
            OperationMoyPay  operationMoyPayInserer = new OperationMoyPay();
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay 
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
                insertOperationMoyPayTrt.setVerifDomaine(false);
                operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(paramInsertInteret.getOperationMoyPay()); 
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOperationInteretServiTrt : ");
                text.append(e.toString());
                erreur.setCode("300");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertOperationInteretServiTrt");
                operationMoyPayInserer.addError(erreur);
                throw new RuntimeException();
            }   
        }
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamInsertInteret paramInsertInteret = (ParamInsertInteret)vo;
        OperationMoyPay operationMoyPay = paramInsertInteret.getOperationMoyPay();
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
        return structureDomaine;
    }
    
    public void genCroText(ValueObject vo) {
            
            ParamInsertInteret paramInsertInteret = (ParamInsertInteret)vo;
            OperationMoyPay operationMoyPay = paramInsertInteret.getOperationMoyPay();
            InteretServi interetServi =new InteretServi();
            interetServi = paramInsertInteret.getInteretServi();
            
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf((operationMoyPay.getNumOperOmp())));
              this.setLibRefCro("smile.placement.Int.Servi");
              this.setDatValCro(operationMoyPay.getDatValOmp());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());              
              this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
              this.setCodEtatCro(0);              
       //       this.setCodeProduit(operationMoyPay.getCodRefbOmp()); // a verifier avec ramzi
              this.setCodeProduit(operationMoyPay.getProduit().getCodPrdPrd().toString()); // 
              this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setDatExecCro(operationMoyPay.getDatSystOmp()); // date system
              this.setTypeOperationCro("O");
              this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach().longValue());
              this.setCodRefcOmp(operationMoyPay.getNumOperOmp());
              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
             
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
            StringBuffer contratCPT =new StringBuffer("");
                 // contratClient
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                 contratCPT.append(";");
                 cro.append("numCptBna=");
                 cro.append(contratCPT.toString());
            if(this.getOperationId().equals(Constants.OPER_INT_PRE_SOUSC_PLAC.toString())){
                cro.append("INTERET_SERVI.MONT_ISRV_ISRV=");
                cro.append(interetServi.getMontIsrvIsrv() +";");
                cro.append("INTERET_SERVI.MONT_BRUT_ISRV=");
                cro.append(interetServi.getMontBrutIsrv() +";");
                cro.append("INTERET_SERVI.MONT_IRC_ISRV=");
                cro.append(interetServi.getMontIrcIsrv()+";");
                cro.append("INTERET_SERVI.NUM_ISRV_ISRV=");
                cro.append(interetServi.getNumIsrvIsrv() +";");
            }else if(this.getOperationId().equals(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST.toString())){
                cro.append("INTERET_SERVI.MONT_ISRV_ISRV_617=");
                cro.append(interetServi.getMontIsrvIsrv() +";");
                cro.append("INTERET_SERVI.MONT_BRUT_ISRV_617=");
                cro.append(interetServi.getMontBrutIsrv() +";");
                cro.append("INTERET_SERVI.MONT_IRC_ISRV_617=");
                cro.append(interetServi.getMontIrcIsrv() +";");
                cro.append("INTERET_SERVI.NUM_ISRV_ISRV_617=");
                cro.append(interetServi.getNumIsrvIsrv() +";");
                
                
                ExtourneInteretServiTrt extourneInteretServiTrt = new ExtourneInteretServiTrt();
                extourneInteretServiTrt.setVerifDomaine(false);
                extourneInteretServiTrt.exec(paramInsertInteret);
            }else if(this.getOperationId().equals(Constants.OPER_INT_PRE_SOUSC_PLAC_SBDV.toString())){
                cro.append("INTERET_SERVI.MONT_ISRV_ISRV=");
                cro.append(interetServi.getMontIsrvIsrv() +";");
                cro.append("INTERET_SERVI.MONT_BRUT_ISRV=");
                cro.append(interetServi.getMontBrutIsrv() +";");
                cro.append("INTERET_SERVI.MONT_IRC_ISRV=");
                cro.append(interetServi.getMontIrcIsrv() +";");
                cro.append("INTERET_SERVI.NUM_ISRV_ISRV=");
                cro.append(interetServi.getNumIsrvIsrv() +";");
                cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_AN="); // (30) abonnement non constaté pour l'année précédente en cas d chauvauchement d année entre la date comptable et la date souscription
                cro.append(paramInsertInteret.getMontAbonnCorrectionAnnee() +";");
                cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_MOI="); // (28)abonnement non constaté pour le mois précédent en cas d chauvauchement d mois entre la date comptable et la date souscription
                cro.append(paramInsertInteret.getMontAbonnCorrectionMois() +";");
                System.out.println(paramInsertInteret.getMontAbonnCorrectionAnnee()+paramInsertInteret.getMontAbonnCorrectionMois());
                cro.append("ABONNEMENT_PLACEMENT.MONT_CORRECT_TOT=");// (30+28) abonnement non constaté total 
                cro.append(paramInsertInteret.getMontAbonnCorrectionAnnee()+paramInsertInteret.getMontAbonnCorrectionMois() +";");
            }
            
            cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
            cro.append(interetServi.getContratPlacement().getNumSeqCpla() +";");
            
            if (operationMoyPay.getNumMoypOmp()!= null){
                    // categorie personne cas du BC/CAT
                    cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                    cro.append(operationMoyPay.getNumMoypOmp() +";");
                }
             
            this.setCroText(cro.toString());
        }   
}

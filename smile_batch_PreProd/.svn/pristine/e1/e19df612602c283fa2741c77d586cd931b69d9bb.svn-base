package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class InsertOpMoyPaySouscriptionPlacTrt extends Traitement{
private OperationMoyPay  operationMoyPayInserer = new OperationMoyPay();
    public InsertOpMoyPaySouscriptionPlacTrt() {
    }
    public IValueObject perform (IValueObject vo ) {
        
            DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo;
            
            
            this.setCroFlag(true); 
            
            try {           
                ///*** insertion dans la table Operation_Moy_Pay 
                InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt(); 
                operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(detailsOperationPlacement.getOperationMoyPay()); 
                return operationMoyPayInserer;
            } 
            catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertOpMoyPaySouscriptionPlacTrt : ");
                text.append(e.toString());
                erreur.setCode("298");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertOpMoyPaySouscriptionPlacTrt");
                operationMoyPayInserer.addError(erreur);
                throw new RuntimeException();
            }   
        }
    public String getNumeroTache(IValueObject vo) {
        
    	return (Constants.CODE_RESSOURCE_GENERALE);
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo; 
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(detailsOperationPlacement.getStructure().getCodStrcStrc());
        return structureDomaine;
    }

    public void genCroText(ValueObject vo) {
            
            DetailsOperationPlacement  detailsOperationPlacement  = (DetailsOperationPlacement )vo;
            ContratPlacement contratPlacement = detailsOperationPlacement.getContratPlacement();             
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              //???????????????detailsOperationPlacement dan refcro??????????
              String str = contratPlacement.getNumSeqCpla().toString();
              this.setNumRefCro(Long.valueOf(operationMoyPayInserer.getNumOperOmp()));
              if(detailsOperationPlacement.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_APRE)
              || detailsOperationPlacement.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_AVAN)
              ){
                  this.setLibRefCro("smile.placement.Renouv");  
              }else {
                  this.setLibRefCro("smile.placement.Sousc");
              }
             
              this.setDatValCro(detailsOperationPlacement.getDatValDopl());
              this.setCodeStructInitiatrice(contratPlacement.getContratCpt().getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(contratPlacement.getContratCpt().getStructure().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
              this.setOperationId(detailsOperationPlacement.getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(detailsOperationPlacement.getDatCompDopl()); // date comptable
              
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setDatExecCro(detailsOperationPlacement.getDatOperDopl()); // date system
              this.setCodTachTach(Constants.COD_TACHE_VALID_PLAC);
              this.setCodRefcOmp(detailsOperationPlacement.getOperationMoyPay().getCodRefcOmp());
              
              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
            StringBuffer contratCPT =new StringBuffer("");
            // contratClient
            contratCPT.append(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
            contratCPT.append(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
            contratCPT.append(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
            contratCPT.append(";");
            cro.append("numCptBna=");
            cro.append(contratCPT.toString());
          
            //numero contrat placement
            cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
            cro.append(contratPlacement.getNumSeqCpla() +";");
            //montant   
            cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA=");
            cro.append(contratPlacement.getMontCapCpla() +";");
            // Type paiement des interets
            cro.append("CONTRAT_PLACEMENT.COD_PINT_CPLA=");
            cro.append(contratPlacement.getCodPintCpla()+";");
            // taux
            cro.append("CONTRAT_PLACEMENT.NUM_TAUI_CPLA=");
            cro.append(contratPlacement.getNumTauiCpla()+";");
            // duree
            cro.append("CONTRAT_PLACEMENT.NUM_NBRJ_CPLA=");
            cro.append(contratPlacement.getNumNbrjCpla()+";");
            
            if (contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                 ){
                // categorie personne cas du BC/CAT
                if(contratPlacement.getNumBcCpla() != null){
                    cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                    cro.append(contratPlacement.getNumBcCpla() +";"); 
                }
                Personne pers = contratPlacement.getPersonne();
                if(pers.getCategoriePersonne() != null){
                    cro.append("CATEGORIE_PERSONNE.COD_CATP_CATP=");
                    cro.append(pers.getCategoriePersonne().getCodCatpCatp() +";");  
                }
                
            }
                this.setCroText(cro.toString());
        }   
}

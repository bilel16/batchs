package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatOperationTrt;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;

import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * validation de la recuperation Bon de caisse.
 * @author El arbi hassine
 * @param DetailsBc
 * @return DetailsBc
 * 
 */
 
public class ValiderRecuperationTrt extends Traitement{
  
   
    
    public ValiderRecuperationTrt() {
    }
        
    public IValueObject perform (IValueObject vo ) {
     
       ParamLiquidation paramLiquidation  = (ParamLiquidation )vo;               
             
       try{ 
            this.setCroFlag(true);                     
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice"); 
                ///*** MAJ du montant actualisé dans le contrat compte                    
             ContratCptId contratCptId =paramLiquidation.getOperationMoyPayRecupBc().getContratCpt().getContratCptId();
             ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
                /* Charger le ContratCpt existante */
             ContratCpt contratCpt =(ContratCpt) searchEngine.get(ContratCpt.class,contratCptId);
             paramLiquidation.setContratCpt(contratCpt);
             ContratCptSold contratCptSold = new ContratCptSold();
             contratCptSold.setContratCpt(contratCpt);              
             contratCptSold.setSolde(Long.valueOf(paramLiquidation.getDetailBc().getContratPlacement().getMontCapCpla()));
             contratCptSold.setSens("C");
             UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
             contratCpt = (ContratCpt)updateSoldTrt.exec(contratCptSold);
              
              InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
              OperationMoyPay   operationMoyPayInserer = (OperationMoyPay)insertOperationMoyPayTrt.exec(paramLiquidation.getOperationMoyPayRecupBc()); 
              
              crudService.update(paramLiquidation.getDetailBc()); 
            }
         catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValiderRecuperationTrt  "+e.getMessage());;
                paramLiquidation.addError(erreur);
                logger.error("Exception" +    "" +": ",e);   
                throw new RuntimeException(e);
        } 
        return (paramLiquidation);
    }
    
    public void genCroText(ValueObject vo) {
        ParamLiquidation paramLiquidation  = (ParamLiquidation )vo;            
                
          /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

           Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    com.oxia.security.abc.model.Personnel user = null;
                    if (obj instanceof UserDetails) {
                        user = (com.oxia.security.abc.model.Personnel)obj;
                   }
          
          this.setNumRefCro(Long.valueOf(paramLiquidation.getOperationMoyPayRecupBc().getNumOperOmp()));
          this.setLibRefCro("SMILE.placement.RecupBC");
          this.setDatValCro(paramLiquidation.getOperationMoyPayRecupBc().getDatValOmp());
          this.setCodeStructInitiatrice(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc().toString());              
          this.setCodStrcImpt(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
          this.setCodEtatCro(0);              
          this.setCodeProduit(paramLiquidation.getDetailBc().getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
          this.setOperationId(Constants.COD_OPER_RECUP_BC_PLAC.toString());
          this.setDateOperation(paramLiquidation.getOperationMoyPayRecupBc().getDatOperOmp());
          SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
          formater=new SimpleDateFormat("HH:mm:ss");
          String heureString = formater.format(new Date());
          this.setHeureOperation(heureString);
          this.setTypeOperationCro("O");
          this.setCodTachTach(Constants.COD_TACH_RECUP_BC_PLAC);
          if (paramLiquidation.getDetailBc()!=null)
            this.setCodRefcOmp("BC N° " + paramLiquidation.getDetailBc().getNumBcDbc().toString());
          this.setDatExecCro(new Date());

          this.setNumCinUser(user.getNumMatrUser());
          this.setCodTypUser(user.getMatriculeTyp());
          //this.setCodTypUser();  
          //this.setNumCinUser();
          
             /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
        StringBuffer cro=new StringBuffer("");
            
            // contratClient
        cro.append("numCptBna=");
        cro.append(StrHandler.lpad(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               
            
        if (paramLiquidation.getDetailBc().getContratPlacement().getNumSeqCpla()!= null){
                cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
                cro.append(paramLiquidation.getDetailBc().getContratPlacement().getNumSeqCpla() +";");
            }
        
        if(paramLiquidation.getDetailBc().getNumBcDbc() != null){
            cro.append("DATAILS_BC.NUM_BC_DBC=");
            cro.append(paramLiquidation.getDetailBc().getNumBcDbc()  +";");
        }
        
        cro.append("CONTRAT_PLACEMENT.MONT_CAP_CPLA=");
        cro.append(paramLiquidation.getDetailBc().getContratPlacement().getMontCapCpla() +";");
        
       
       this.setCroText(cro.toString());
    }

     
    
    public String getNumeroTache(ValueObject vo) {
        return(Constants.COD_OPER_RECUP_BC_PLAC.toString()+
        StrHandler.lpad(Constants.COD_TACH_RECUP_BC_PLAC.toString(),'0',2));
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamLiquidation paramLiquidation  = (ParamLiquidation )vo;     
        structureDomaine.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
        structureDomaine.setCodStrcStrc(paramLiquidation.getDetailBc().getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }

   
}

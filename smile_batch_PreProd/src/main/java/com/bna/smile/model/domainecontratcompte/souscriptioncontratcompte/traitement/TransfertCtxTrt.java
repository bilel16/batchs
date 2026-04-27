package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailEtatContratId;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetCommissionTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratCptACtx;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class TransfertCtxTrt extends Traitement{
    public TransfertCtxTrt() {
    }
    
    OperationMoyPay operationMoyPayExec=new OperationMoyPay();
    ContratCpt contratCpt=new ContratCpt();
   
    public IValueObject perform(IValueObject vo) {
     
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteriaDetailEtatContrat = searchEngine.createCriteria();
            ICriteria criteriaMotifEtat = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            SequenceDAO sequenceDAO = 
                (SequenceDAO)context.getBean("sequenceDAO");
            ContratCptACtx contratCptACtx= (ContratCptACtx)vo;
            contratCpt = contratCptACtx.getContratCpt();
            MotifEtat motifEtat=new MotifEtat(); 
            
            /*mise a jour detail etat contrat*/
             criteriaDetailEtatContrat.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                 contratCpt.getContratCptId().getCodStrcStrc()));
             criteriaDetailEtatContrat.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                 contratCpt.getContratCptId().getCodPrdPrd()));
             criteriaDetailEtatContrat.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                 contratCpt.getContratCptId().getNumCcptCcpt()));
            List listeDetailEtatContrat = searchEngine.find(DetailEtatContrat.class, criteriaDetailEtatContrat);     
            DetailEtatContrat detailEtatContrat1=new DetailEtatContrat();
            if (listeDetailEtatContrat != null && listeDetailEtatContrat.size() > 0){
                Iterator iterator= listeDetailEtatContrat.iterator();
                for(;iterator.hasNext();) {
                    detailEtatContrat1=(DetailEtatContrat) iterator.next();
                    if (detailEtatContrat1.getDatFinDetc()==null){
                        detailEtatContrat1.setDatFinDetc(new Date());
                        crudService.update(detailEtatContrat1); 
                    }
                }
             }
           
            /*creation d'un nouveau etat contrat*/
            
            DetailEtatContrat detailEtatContrat=new DetailEtatContrat();
            DetailEtatContratId detailEtatContratId=new DetailEtatContratId();
            
            detailEtatContratId.setCodPrdPrd( contratCpt.getContratCptId().getCodPrdPrd());
            detailEtatContratId.setCodStrcStrc( contratCpt.getContratCptId().getCodStrcStrc());
            detailEtatContratId.setNumCcptCcpt( contratCpt.getContratCptId().getNumCcptCcpt());
            detailEtatContratId.setNumDetcDetc(sequenceDAO.getSequenceDetailEtatContrat());
            
            detailEtatContrat.setDetailEtatContratId(detailEtatContratId);
            detailEtatContrat.setDatDebDetc(new Date());
            Listes listes =new Listes();
            criteriaMotifEtat.add(expression.eq("motifEtatId.codEtatEcon", 
                                         Constants.COD_ETAT_CPT_TCONTENTIEU ));
            
            List l = searchEngine.find(MotifEtat.class, criteriaMotifEtat);
            if (l != null && l.size() > 0) {
            motifEtat=(MotifEtat)l.get(0);}
            
           
            detailEtatContrat.setContratCpt(contratCpt);
            detailEtatContrat.setMotifEtat(motifEtat);
            crudService.create(detailEtatContrat);
            
            /*mise a jour contrat*/
            UpdateSoldTrt updateSoldTrt=new UpdateSoldTrt();
            ContratCptSold contratCptSold=new ContratCptSold();
            contratCptSold.setContratCpt(contratCpt);
            contratCptSold.setSolde(contratCpt.getMontSoldCcpt());
            contratCptSold.setSens("D");
            updateSoldTrt.exec(contratCptSold);
            
            contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_TCONTENTIEU);
            contratCpt.setDatCtxCcpt(new Date());
            crudService.update(contratCpt);
            
            /*insrtion operation moyen de paiement*/
            InsertOperationMoyPayTrt insertOperationMoyPayTrt=new InsertOperationMoyPayTrt();
            operationMoyPayExec=(OperationMoyPay )insertOperationMoyPayTrt.exec(contratCptACtx.getOperationMoyPay());
           
            
            
            this.setCroFlag(true); 
            return contratCpt;

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur lors transfert CTX ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("TransfertCtx");
            contratCpt.addError(erreur);
            logger.error("Exception : ",e);   
           
            return (contratCpt);
        }
    }
    public void genCroText(ValueObject vo) {
            ContratCptACtx contratCptACtx= (ContratCptACtx)vo;   
            GetCommissionTrt getCommissionTrt = new GetCommissionTrt();
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */
              
              this.setNumRefCro(Long.valueOf(operationMoyPayExec.getNumOperOmp()));
              this.setLibRefCro("smile.operation_trans_ctx");
              this.setDatValCro(contratCptACtx.getOperationMoyPay().getDatValOmp());
              this.setCodeStructInitiatrice(contratCptACtx.getOperationMoyPay().getStructureInitiatrice().getCodStrcStrc().toString());
              //this.setTypeCro("F");
              this.setCodEtatCro(0);
              //this.setCodHistCro(1);
              this.setCodeProduit(contratCptACtx.getOperationMoyPay().getContratCpt().getContratCptId().getCodPrdPrd().toString());
              this.setOperationId(contratCptACtx.getOperationMoyPay().getTache().getOperation().getCodOperOper().toString());
              this.setDateOperation(contratCptACtx.getOperationMoyPay().getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);                    
              this.setNumCinUser(contratCptACtx.getOperationMoyPay().getPersonnelInitiateur().getNumMatrUser().toString());
              this.setTypeOperationCro("O");
              
                
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
                
                StringBuffer cro=new StringBuffer("");
                
                // contratClient
                cro.append("numCptBna=");
                cro.append(StrHandler.lpad(contratCptACtx.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+
                           StrHandler.lpad(contratCptACtx.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+
                           StrHandler.lpad(contratCptACtx.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+"; ");
                              
                
                cro.append("mont_Sold_Ccpt=");
                cro.append(contratCptACtx.getContratCpt().getMontSoldCcpt()+";");
           
                    
               this.setCroText(cro.toString());
         
        }  
    public String getNumeroTache(ValueObject vo) {
        ContratCptACtx contratCptACtx= (ContratCptACtx)vo;
        
        return(contratCptACtx.getOperationMoyPay().getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(contratCptACtx.getOperationMoyPay().getTache().getTacheId().getCodTachTach().toString(),'0',2));
        
        
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ContratCptACtx contratCptACtx= (ContratCptACtx)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(contratCptACtx.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
    
}

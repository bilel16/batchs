package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailEtatContratId;
import com.bna.commun.model.MotifEtat;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratACloturer;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CloturerContratTrt extends Traitement{
    public CloturerContratTrt() {
    }
    
   
    public IValueObject perform(IValueObject vo) {
        ContratACloturer contratACloturer = (ContratACloturer)vo;
        ContratCpt contratCpt=contratACloturer.getContratCpt();
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
            
            detailEtatContrat.setContratCpt(contratCpt);
            Listes listes =new Listes();
            MotifEtat motifEtat=new MotifEtat(); 
            /*cas prise en charge*/
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("PC")){
                
                criteriaMotifEtat.add(expression.eq("motifEtatId.codMotfMeta", 
                                             Constants.MOTIF_ATT_CLO ));
                criteriaMotifEtat.add(expression.eq("motifEtatId.codEtatEcon", 
                                             Constants.COD_ETAT_CPT_SEMIACTIF ));
                
                List l = searchEngine.find(MotifEtat.class, criteriaMotifEtat);
                if (l != null && l.size() > 0) {
                motifEtat=(MotifEtat)l.get(0);}
            
            }
            /*cas validation cloture*/
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("VC")){
                
               criteriaMotifEtat.add(expression.eq("motifEtatId.codEtatEcon", 
                                             Constants.COD_ETAT_CPT_RESILIE ));
                
                List l = searchEngine.find(MotifEtat.class, criteriaMotifEtat);
                if (l != null && l.size() > 0) {
                motifEtat=(MotifEtat)l.get(0);}
            
            }
            /*cas annulation de cloture*/
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("AC")){
                
                criteriaMotifEtat.add(expression.eq("motifEtatId.codMotfMeta", 
                                             Constants.MOTIF_ANN_CLO ));
                criteriaMotifEtat.add(expression.eq("motifEtatId.codEtatEcon", 
                                             Constants.COD_ETAT_CPT_VALID ));
                
                List l = searchEngine.find(MotifEtat.class, criteriaMotifEtat);
                if (l != null && l.size() > 0) {
                motifEtat=(MotifEtat)l.get(0);}
            
            }
            detailEtatContrat.setMotifEtat(motifEtat);
            crudService.create(detailEtatContrat);   
            /*mise a jour contrat*/
                if (contratACloturer.getCocdtrait().equalsIgnoreCase("PC")){
                contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_SEMIACTIF);
            } 
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("VC")){
                contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_RESILIE);
            }
            if (contratACloturer.getCocdtrait().equalsIgnoreCase("AC")){
                contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_VALID);
            }
            
            contratCpt.setDatCloCcpt(new Date());
            crudService.update(contratCpt);
            
            /*insertion trace clôture*/
            
            InsertTraceContratTrt insertTraceContratTrt=new InsertTraceContratTrt();
            insertTraceContratTrt.exec(contratACloturer.getTraceContrat());
            this.setCroFlag(false); 
            return contratCpt;

        } catch (Exception e) {
          
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur lors de la cloture du contrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("clotureContrat");
            contratCpt.addError(erreur);
            logger.error("Exception : ",e);   
           
            return (contratCpt);
            
        }
    }
    public void genCroText(ValueObject vo) {
           
         
        }  
    public String getNumeroTache(ValueObject vo) {
        ContratACloturer contratACloturer = (ContratACloturer)vo;
        
        return(contratACloturer.getTraceContrat().getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(contratACloturer.getTraceContrat().getTache().getTacheId().getCodTachTach().toString(),'0',2));
        
        
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ContratACloturer contratACloturer = (ContratACloturer)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(contratACloturer.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}

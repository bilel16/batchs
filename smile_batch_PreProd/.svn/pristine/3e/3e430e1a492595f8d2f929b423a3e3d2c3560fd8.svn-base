package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailEtatContratId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ContratABloquer;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ITraitement;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class BloquerContratCptTrt extends Traitement implements ITraitement{
    public BloquerContratCptTrt() {
    }
    Context context = ContextHandler.getContext();
  
    public IValueObject perform(IValueObject vo) {
        ContratABloquer contratABloquer = (ContratABloquer)vo;
        ContratCpt contratCpt=contratABloquer.getContratCpt();
        try {

            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteriaDetailEtatContrat = searchEngine.createCriteria();
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
            detailEtatContrat.setMotifEtat(contratABloquer.getMotifEtat());
            crudService.create(detailEtatContrat);     
            /*mise a jour contrat*/
            if (contratABloquer.getMotifEtat().getMotifEtatId().getCodMotfMeta().longValue()==Constants.MOTIF_BLOC_JUDIC){
                contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_SEMIACTIF);
            }else{
                contratCpt.setCodEtatCcpt(Constants.COD_ETAT_CPT_BLOQUE);
            }
            contratCpt.setDatBlocCcpt(new Date());
            crudService.update(contratCpt);
            
            /*insertion de la trace de blocage*/
            
             InsertTraceContratTrt insertTraceContratTrt=new InsertTraceContratTrt();
             insertTraceContratTrt.exec(contratABloquer.getTraceContrat());
            this.setCroFlag(false); 
            this.sychronisationPascal(contratABloquer);  
            return contratCpt;

 
        } catch (Exception e) {
           
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur lors du blocage du contrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("BlocageContrat");
            contratCpt.addError(erreur);
            logger.error("Exception : ",e);   
         
            return (contratCpt);
        }
    }
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        ContratABloquer contratABloquer = (ContratABloquer)vo;
        
        return(contratABloquer.getTraceContrat().getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(contratABloquer.getTraceContrat().getTache().getTacheId().getCodTachTach().toString(),'0',2));
        
        
    }
    public void genererSynchronisationPascal(ValueObject vo) { 
    
        
        ContratABloquer contratABloquer = (ContratABloquer)vo;
        
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
        this.setCodeOperationSynch(contratABloquer.getTraceContrat().getTache().getTacheId().getCodOperOper());
        this.setCodeTacheSynch(contratABloquer.getTraceContrat().getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(contratABloquer.getContratCpt().getContratCptId().getCodStrcStrc());
        
        /*partie variable*/
        String partieVariable ="";
        String numCompte = StrHandler.lpad(contratABloquer.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(contratABloquer.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+"O";
       
        partieVariable=numCompte;
        
         System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }  
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ContratABloquer contratABloquer = (ContratABloquer)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(contratABloquer.getContratCpt().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}

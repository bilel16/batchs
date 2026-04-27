package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.Blocage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.NatureBlocage;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.MontantBlocage;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class BloquerMntTrt  extends Traitement{
    public BloquerMntTrt() {
    }
   
   
    public IValueObject perform(IValueObject vo) {
        MontantBlocage montantBlocage=(MontantBlocage)vo;
        ContratCpt contratCpt=montantBlocage.getContratCpt();
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
                        
            /*creation d'un nouveau blocage*/
            NatureBlocage natureBlocage=new NatureBlocage();
            Blocage blocage=new Blocage();
            natureBlocage.setCodNatuBloc(montantBlocage.getCodNatureBlocage());
            blocage.setContratCpt(montantBlocage.getContratCpt());
            blocage.setDatDebBloc(new Date());
            blocage.setMntBlocBloc(montantBlocage.getMontantBlocage());
            blocage.setNatureBlocage(natureBlocage);
            crudService.create(blocage);   
            montantBlocage.setBlocage(blocage);
            /*MAj du contrat Compte*/
            if (contratCpt.getMontBlocCcpt()==null){
            contratCpt.setMontBlocCcpt(montantBlocage.getMontantBlocage());
            }else{
            
             }
             contratCpt.setMontBlocCcpt(contratCpt.getMontBlocCcpt()+montantBlocage.getMontantBlocage());
             
             crudService.update(contratCpt);
            /*insertion trace blocage montant*/
            
            InsertTraceContratTrt insertTraceContratTrt=new InsertTraceContratTrt();
            insertTraceContratTrt.exec(montantBlocage.getTraceContrat());
            this.sychronisationPascal(montantBlocage);  
            this.setCroFlag(false); 
            
            return contratCpt;

        } catch (Exception e) {
           
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur lors du blocage du montant ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("BlocageMontant");
            contratCpt.addError(erreur);
            logger.error("Exception : ",e);   
           
            return (contratCpt);
        }
    }
    public void genCroText(ValueObject vo) {
           
}
    public String getNumeroTache(ValueObject vo) {
        MontantBlocage montantBlocage=(MontantBlocage)vo;
        
        return(montantBlocage.getTraceContrat().getTache().getTacheId().getCodOperOper().toString()+
        StrHandler.lpad(montantBlocage.getTraceContrat().getTache().getTacheId().getCodTachTach().toString(),'0',2));
        
        
    }
    public void genererSynchronisationPascal(ValueObject vo) { 
    
        
        MontantBlocage montantBlocage=(MontantBlocage)vo;
        
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
        this.setCodeOperationSynch(montantBlocage.getTraceContrat().getTache().getTacheId().getCodOperOper());
        this.setCodeTacheSynch(montantBlocage.getTraceContrat().getTache().getTacheId().getCodTachTach());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(montantBlocage.getContratCpt().getContratCptId().getCodStrcStrc());
        
        /*partie variable*/
        String partieVariable ="";
        String numCompte = StrHandler.lpad(montantBlocage.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(montantBlocage.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
        String numBloc=StrHandler.lpad(montantBlocage.getBlocage().getNumBlocBloc().toString(),'0',5);
        String dateblocage = myformat.format(new Date())   ; 
        String reference="CA                  ";                    
        String montantBloc=StrHandler.lpad(montantBlocage.getMontantBlocage().toString(),'0',15); 
        String matricule=StrHandler.lpad(montantBlocage.getTraceContrat().getPersonnel().getNumMatrUser(),'0',4);
        partieVariable=numCompte+numBloc+dateblocage+montantBloc+reference+matricule;
        
         System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        MontantBlocage montantBlocage=(MontantBlocage)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(montantBlocage.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
}
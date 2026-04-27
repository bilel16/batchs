package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.LivretEpargne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.Livrets;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class MiseAJourLivretEpargneTrt extends Traitement{
    public MiseAJourLivretEpargneTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        Livrets livrets = (Livrets)vo;
        LivretEpargne NouvlivretEpargne = new LivretEpargne();
        LivretEpargne AnclivretEpargne=new LivretEpargne();
        LivretEpargne livretEpargneRetour=new LivretEpargne();
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
               
            /**mise a jour de l'ancien livret**/
            if (livrets.getAncienLivret().getNumLivrLive()!=null) {   
            AnclivretEpargne=livrets.getAncienLivret();
            AnclivretEpargne.setDatFinLive(new Date());
            AnclivretEpargne.setCodEtatLive("H");
            crudService.update(AnclivretEpargne);
            }
                            
            /**insrtion du nouveau livret**/
             NouvlivretEpargne=livrets.getNouveauLivret();
             InsertLivretEpargneTrt insertLivretEpargneTrt=new InsertLivretEpargneTrt();
             livretEpargneRetour=(LivretEpargne)insertLivretEpargneTrt.exec(NouvlivretEpargne);
             /**mise à jour du num livret ds contrat**/
             ContratCpt contratCpt=new ContratCpt();
             contratCpt=livrets.getAncienLivret().getContratCpt();
            
             contratCpt.setNumLivrCcpt((NouvlivretEpargne.getNumLivrLive()).toString() );
             crudService.update(contratCpt);
                 this.setCroFlag(false); 
                this.sychronisationPascal(livrets);  
            return livretEpargneRetour;
            }
               catch (Exception e) {
                  com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                  StringBuffer text = 
                      new StringBuffer("Erreur dans MiseAJourLivretEpargneTrt : ");
                  text.append(e.toString());
                  erreur.setCode("200");
                  erreur.setDescription(text.toString());
                  erreur.setKey("MiseAJourLivretEpargneTrt");
                  livretEpargneRetour.addError(erreur);
                  logger.error("Exception : ",e);   
                 
                  return (livretEpargneRetour);
              }
    }
    public void genCroText(ValueObject vo) {
           
    }
    public String getNumeroTache(ValueObject vo) {
        
        return(Constants.COD_OPER_RENOUV_LIV.toString()+
        StrHandler.lpad(Constants.COD_TACH_RENOUV_LIV.toString(),'0',2));
        
        
    }
    public void genererSynchronisationPascal(ValueObject vo) { 
    
        
        Livrets livrets = (Livrets)vo;
        
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
        this.setCodeOperationSynch(Constants.COD_OPER_RENOUV_LIV);
        this.setCodeTacheSynch(Constants.COD_TACH_RENOUV_LIV);
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(livrets.getAncienLivret().getContratCpt().getContratCptId().getCodStrcStrc());
        
        /*partie variable*/
        String partieVariable ="";
        String numCompte = StrHandler.lpad(livrets.getAncienLivret().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(livrets.getAncienLivret().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
        String numlivret=StrHandler.lpad(livrets.getNouveauLivret().getNumLivrLive().toString(),'0',8);
        partieVariable=numCompte+numlivret;
        
         System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }  
}

package com.bna.smile.model.clotureDomaine.traitement;


import com.bna.commun.traitements.OuvrirClotureJourneeTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.JourneeVo;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;



public class ClotureJourneeSmileTrt extends Traitement{
    public ClotureJourneeSmileTrt() {
    }
    public IValueObject perform(IValueObject vo) {
        
        this.setCroFlag(false);
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria         = searchEngine.createCriteria();
        IExpression expression     = searchEngine.createExpression();
        JournStrucDomVo journStrucDomVo = (JournStrucDomVo)vo;
       // JourneeStructureDomaine journeeStructureDomaine=this.getJourneeStructureDomaine(journStrucDomVo.getJourneeStructureDomaineId());
        
    try{
           if(this.checkClotureJournee()){
              
              JourneeVo journeeVo = OuvrirClotureJourneeTrt.clotureOuvertureJournee(journStrucDomVo.getJourneeStructureId(),journStrucDomVo.getMatriculeInitiateur());
              journStrucDomVo.setNouvelleJournee(journeeVo.getDateJourneeOuverte());
           
          }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("ClotureDomaineTrt");
                      journStrucDomVo.addError(erreur);    
                     
                } 
         
       }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans ClotureJourneeSmileTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ClotureJourneeSmileTrt");
            journStrucDomVo.addError(erreur);
            logger.error(" *** Erreur lors de la ClotureJourneeSmileTrt concernant l'agence "+journStrucDomVo.getJourneeStructureId().getCodStrcStrc()+" : ", e);
            throw new RuntimeException(e);
           
        }
        return(journStrucDomVo);
    }
    public void genCroText(ValueObject vo) {
   
    }
    public String getNumeroTache(ValueObject vo) {
        return(Constants.COD_OPER_CLO_DOM+
        StrHandler.lpad(Constants.COD_TACH_CLO_DOM,'0',2));      
    }
}

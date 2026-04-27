package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.InteretServi;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.clotureDomaine.model.StatPlacementObjectVO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;

import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetListBcRecupereTrt extends Traitement{
    public GetListBcRecupereTrt() {
    }
    
 /**
  * chercher le detail bon de caisse 
  * pour un contrat placement donnée donnée
  * @param DetailsBc
  * @return DetailsBc
  * 
  */
    public ValueObject perform(IValueObject vo ){
        Context context = ContextHandler.getContext();
        ParamBonCaisse ParamBonCaisse = (ParamBonCaisse)vo;
        Listes listeBcRecupere = new Listes();
        
    try{        
       
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria critere = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        
        this.setCroFlag(false);

        if (ParamBonCaisse.getContratPlacement().getNumSeqCpla()!=null)      
            critere.add(expression.eq("contratPlacement.numSeqCpla",ParamBonCaisse.getContratPlacement().getNumSeqCpla()));
        
        if (ParamBonCaisse.getDateDebut()!=null ) {
                critere.add(expression.ge("DateRecaBc", 
                                           ParamBonCaisse.getDateDebut()));
         }
         if (ParamBonCaisse.getDateFin()!=null) {
                critere.add(expression.lt("DateRecaBc", 
                                           ParamBonCaisse.getDateFin()));
            }   
       
            

        List l = searchEngine.find(DetailsBc.class, critere);
        if(l!=null && l.size()>0){
        /*test pour une agence*/
        if(ParamBonCaisse.getCodeStructure()!=null){
        List lsiteParAg =new ArrayList();
            for (Iterator it = l.iterator(); it.hasNext(); ) {
                DetailsBc detailsBc = (DetailsBc)it.next(); 
                if (detailsBc.getBonDeCaisse().getStructure().getCodStrcStrc().longValue()==
                    ParamBonCaisse.getCodeStructure().longValue()){
                        lsiteParAg.add(detailsBc);
                    }
            
        }
            listeBcRecupere.setList(lsiteParAg);
        }else {
            listeBcRecupere.setList(l);
        }
        }   
           
         
        }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListBcRecupereTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListBcRecupereTrt");
                            logger.error("Exception : ",e);   
                            listeBcRecupere.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeBcRecupere);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}

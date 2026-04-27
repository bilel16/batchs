package com.bna.smile.model.domaineplacement.traitement;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;

import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.util.Date;
import java.util.List;

public class GetListAvancRembLiquidByEtatTrt extends Traitement{
    public GetListAvancRembLiquidByEtatTrt() {
    }
    
 /**
  * Charger la liste des Avances qui ont un etat donné 
  * pour une agence donnée.
  * @param PrimitiveVO
  * @return listeAvancRembLiquid
  * 
  */
    public ValueObject perform(IValueObject vo ){
        
        ParamAvanRembLiq paramAvanRembLiq = (ParamAvanRembLiq)vo;
        Listes listeAvancRembLiquid = new Listes();
        
    try{        
       
        ICriteria   criteria       = getSearchEngine().createCriteria();
        IExpression expression     = getSearchEngine().createExpression();
        this.setCroFlag(false);
//        if (paramAvanRembLiq.getCodStrcStrc()!=null)      
//            criteria.add(expression.like("contratPlacement.numSeqCpla",paramAvanRembLiq.getCodStrcStrc()+"%"));
        if (paramAvanRembLiq.getNumSeqCpla()!=null)      
            criteria.add(expression.eq("contratPlacement.numSeqCpla",paramAvanRembLiq.getNumSeqCpla()));
        if (paramAvanRembLiq.getCodEtatArl()!=null) { 
            if (paramAvanRembLiq.getCodEtatArl().equalsIgnoreCase("R")){ ///* Remboursée
                criteria.add(expression.isNotNull("datReelArl"));
            }else  if (paramAvanRembLiq.getCodEtatArl().equalsIgnoreCase("ER")){ ///* Echu en attente de remboursement
                    criteria.add(expression.isNull("datReelArl"));
                    criteria.add(expression.le("datPrevArl",new Date()));
                }else{ if (paramAvanRembLiq.getCodEtatArl().equalsIgnoreCase("V")){ ///* valide 
                        criteria.add(expression.isNull("datReelArl"));
                        criteria.add(expression.eq("codEtatArl",paramAvanRembLiq.getCodEtatArl()));
                    }else  if (paramAvanRembLiq.getCodEtatArl().equalsIgnoreCase("VNR")){ ///* valide et non remboursé   
                             criteria.add(expression.isNull("datReelArl"));
                             criteria.add(expression.ge("datPrevArl",new Date()));
                             criteria.add(expression.eq("codEtatArl",Constants.ETAT_ARL_VALIDEE));
                        }else criteria.add(expression.eq("codEtatArl",paramAvanRembLiq.getCodEtatArl()));///* en Attente
                }
        }
        if (paramAvanRembLiq.getCodToprtArl()!=null)      
            criteria.add(expression.eq("codToprArl",paramAvanRembLiq.getCodToprtArl()));
        if (paramAvanRembLiq.getTypeLiquidation()!=null)      
            criteria.add(expression.isNotNull("codTyplArl"));
        
  //      if (paramAvanRembLiq.getListStrcStrc()!= null )
//            criteria.add(expression.in("contratPlacement.contratCpt.contratCptId.codStrcCcpt",paramAvanRembLiq.getListStrcStrc()));
        if (paramAvanRembLiq.getDateDebut()!=null)      
            criteria.add(expression.ge("datArlArl",paramAvanRembLiq.getDateDebut()));
        if (paramAvanRembLiq.getDateFin()!=null)      
            criteria.add(expression.le("datArlArl",paramAvanRembLiq.getDateFin()));
//        if (paramAvanRembLiq.getNumSeqPers()!=null)      
//            criteria.add(expression.eq("contratPlacement.personne.numSeqPers",paramAvanRembLiq.getNumSeqPers()));


        List l = getSearchEngine().find(AvancRembLiquid.class, criteria);
        if(l!=null && l.size()>0)
            listeAvancRembLiquid.setList(l);
         
        }catch(Exception e) {
                            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                            StringBuffer text = new StringBuffer("Erreur dans GetListAvancRembLiquidByEtatTrt : ");
                            text.append(e.toString());
                            erreur.setCode("100");
                            erreur.setDescription(text.toString());
                            erreur.setKey("GetListAvancRembLiquidByEtatTrt");
                            logger.error("Exception : ",e);   
                            listeAvancRembLiquid.addError(erreur);
                            throw new RuntimeException(e);
                           
            }
       return(listeAvancRembLiquid);
    }
    
    public void genCroText(ValueObject vo) {
    
    }  
}

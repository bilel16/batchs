package com.bna.smile.model.domaineguichet.traitement;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.constant.Constants;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetProvisionTrt extends Traitement{
    public GetProvisionTrt() {
    }
    
    /**
     * Methode permettant le calcule de la provision d'un contrat donné 
     * @param vo : ContratCpt
     * @return   : PrimitiveVO
     * @autor    : Youssef BOUSSEN 
     */
    public ValueObject perform(IValueObject vo) {
    
        this.setCroFlag(false);      
        ContratCpt contratCpt = (ContratCpt)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
    try{

        Long provision = new Long(0);

        provision = contratCpt.getMontSoldCcpt();
        
        primitiveVO.setVLong(provision);    
        return (primitiveVO);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans GetProvisionTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("GetProvisionTrt");
              primitiveVO.addError(erreur);
              return (primitiveVO);
          }
    }


    public void genCroText(ValueObject vo) {
    }
    
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}

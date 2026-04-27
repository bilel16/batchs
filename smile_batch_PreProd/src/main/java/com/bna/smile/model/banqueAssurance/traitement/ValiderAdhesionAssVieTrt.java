package com.bna.smile.model.banqueAssurance.traitement;

import com.bna.commun.model.DetailAdhesion;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValiderAdhesionAssVieTrt extends Traitement{
    public ValiderAdhesionAssVieTrt() {
    }
    /**
     * Fonction qui permet de determiner la liste des adhesion assurance vie pour un client / par période / selon l etat (valide, attente...)
     * @Author : lamia jerbi
     * @since 09/09/2010
     */
    public IValueObject perform (IValueObject vo ){  
     
       ParamAdhesion paramAdhesion = (ParamAdhesion)vo; 
       CreatAdhesionAssVieTrt creatAdhesionAssVieTrt = new CreatAdhesionAssVieTrt();
       CreatDetailAdhesionAssVieTrt creatDetailAdhesionAssVieTrt =new CreatDetailAdhesionAssVieTrt();
       DetailAdhesion detailAdhesion=new DetailAdhesion();  
    try{
       
           creatAdhesionAssVieTrt.exec(paramAdhesion.getAdhesionAssVie());
           
           detailAdhesion.setAdhesionAssVie(paramAdhesion.getAdhesionAssVie());
           detailAdhesion.setCodEtatDadh(Constants.COD_ETA_VALID_ASSUR_VIE);
           detailAdhesion.setContratCpt(paramAdhesion.getAdhesionAssVie().getContratCpt());
           detailAdhesion.setDatDebDadh(paramAdhesion.getDateComptable());
           
           creatDetailAdhesionAssVieTrt.exec(detailAdhesion);
      
        return (paramAdhesion); 
       
       }catch(Exception e){
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = 
              new StringBuffer("Erreur dans ValiderAdhesionAssVieTrt : ");
          text.append(e.toString());
          erreur.setCode("200");
          erreur.setDescription(text.toString());
          erreur.setKey("ValiderAdhesionAssVieTrt");
          logger.error("Exception : ",e);  
          throw new RuntimeException(e);
        }
     }
        
        public void genCroText(ValueObject vo){
            }
        public String getNumeroTache(IValueObject vo) {
            return(Constants.COD_OPER_ADH_ASSUR_VIE.toString()+Constants.COD_TACH_PEC);
        }
    }
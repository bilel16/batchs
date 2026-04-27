package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class VerifDateLimiteTrt extends Traitement{
   
    private static final Logger logger = Logger.getLogger(VerifDateLimiteTrt.class);

    public VerifDateLimiteTrt() {
    }
    
    
    /**
     * Fonction qui verifie si un contrat peut etre transferé ou changer 
     * de catégorie ou de régime.
     * @param   ContratCpt  :  contratCpt
     * @return  PrimitiveVO :  boolean      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
    public IValueObject perform(IValueObject vo) {
    
            Context context = ContextHandler.getContext();     
            ContratCpt contratCpt = (ContratCpt)vo;
            PrimitiveVO primitiveVO=new PrimitiveVO();
            primitiveVO.setVBool(false);///* valeur par defaut
    try{                
            DetailCatCpt detailCatCpt=new DetailCatCpt();
            GetCategorieContratTrt getCategorieContratTrt=new GetCategorieContratTrt();
            detailCatCpt = (DetailCatCpt)getCategorieContratTrt.exec(contratCpt.getContratCptId()) ;
    
            Long codRgm = new Long(detailCatCpt.getCategorie().getRegime().getRegimeId().getCodRgmRgm());       

            Date dateFinEpargne =  contratCpt.getDatOuvCcpt();
            //dateFinEpargne.setYear(dateFinEpargne.getYear()+codRgm.intValue());
            Date d=new Date();
            d.setDate(dateFinEpargne.getDate());
            d.setMonth(dateFinEpargne.getMonth());
            d.setYear(dateFinEpargne.getYear()+codRgm.intValue());
        
            Integer nbrMois  = DateHandler.getMonthsBetween(new Date(),d);
            if ((contratCpt.getContratCptId().getCodPrdPrd().intValue()== Constants.COD_PRD_PRD_PEL.intValue() || contratCpt.getContratCptId().getCodPrdPrd().intValue()== Constants.COD_PRD_PRD_PEM.intValue() )&& (nbrMois.intValue() >= Constants.COD_PRD_PRD_LIM_PEL) ){///* 3 mois pour le PEM & PEL
                primitiveVO.setVBool(true);
            }
            if (contratCpt.getContratCptId().getCodPrdPrd().intValue()== Constants.COD_PRD_PRD_PEE.intValue() && (nbrMois.intValue() >= Constants.COD_PRD_PRD_LIM_PEE) ){///* 2 ans pour le PEE
                primitiveVO.setVBool(true);
            }
            
            return(primitiveVO);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = new StringBuffer("Erreur dans VerifDateLimiteTrt : ");
                text.append(e.toString());
                erreur.setCode("600");
                erreur.setDescription(text.toString());
                erreur.setKey("VerifDateLimiteTrt");
                primitiveVO.addError(erreur);
                logger.error("Exception dans VerifDateLimiteTrt concernant l'agence "+contratCpt.getStructure().getCodStrcStrc()+" : ",e);
                return (primitiveVO);
        }

       
    }
    public void genCroText(ValueObject vo) {
        
    }
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}

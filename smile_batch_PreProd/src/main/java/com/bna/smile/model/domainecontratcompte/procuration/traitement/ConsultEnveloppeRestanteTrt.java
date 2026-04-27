package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import com.bna.commun.model.MandatOperation;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.traitement.GetDateDerniereOperationTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ConsultEnveloppeRestanteTrt extends Traitement{
//    private static final Logger logger = Logger.getLogger(ConsultEnveloppeRestanteTrt.class);

    public ConsultEnveloppeRestanteTrt() {
    }
    
    /**
     * Methode permettant calcule l'enveloppe restante d'une operation sur un mandat 
     * @param vo : MandatOperation
     * @return PrimitiveVO
     */
    public IValueObject perform(IValueObject vo) {
        
        Context context = ContextHandler.getContext();
        MandatOperation mandatOperation = (MandatOperation)vo;
        PrimitiveVO primitiveVO = new PrimitiveVO();
    try{

        GetDateDerniereOperationTrt getDateDerniereOperationTrt = new GetDateDerniereOperationTrt();
        PrimitiveVO  dateDernOper = (PrimitiveVO) getDateDerniereOperationTrt.exec(mandatOperation); 

        DebutDernierePeriodeTrt debutDernierePeriodeTrt=new DebutDernierePeriodeTrt();
        PrimitiveVO  datDebDernPer = (PrimitiveVO) debutDernierePeriodeTrt.exec(mandatOperation);
        
        if (new Double(DateHandler.getDaysBetween(dateDernOper.getVDate(),datDebDernPer.getVDate())).intValue()>=0) {
            /* RAZ du montant utilisé */
            mandatOperation.setMontUtilMaop(Long.valueOf(0));
            UpdateMandatOperationTrt updateMandatOperationTrt =  new UpdateMandatOperationTrt();
            mandatOperation=(MandatOperation) updateMandatOperationTrt.exec(mandatOperation);
        }
        
        primitiveVO.setVLong(Long.valueOf(mandatOperation.getMontElimMaop().intValue()-mandatOperation.getMontUtilMaop().intValue()));    
        return (primitiveVO);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans ConsultEnveloppeRestanteTrt : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("ConsultEnveloppeRestanteTrt");
              primitiveVO.addError(erreur);
              logger.error("Exception: ",e);
              return (primitiveVO);
          }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}

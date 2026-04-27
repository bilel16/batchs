package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;

import com.bna.commun.model.DetailRenouvellementMandat;
import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
/**
 * Classe  pour la prise en charge totale de la Création 
 * d'un DetailRenouvellementMandat
 * @author BOUSSEN Youssef & KRIAA Hatem
 * @date 16/05/2007
 */
public class InsertDetailRenouvellementMandatTrt extends Traitement{

    

    public InsertDetailRenouvellementMandatTrt() {
    }
    
    /**
     * methode permettant l'insertion d'un nouveau DetailRenouvellementMandat
     * et de fermer l'ancien (date fin = date systeme) s'il existe
     * @param vo : Mandat
     * @return DetailRenouvellementMandat
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        Mandat mandat = (Mandat)vo;
        /* si le mandat existe deja */
        //if (mandat.getDetailRenouvellementMandats() != null &&  mandat.getDetailRenouvellementMandats().size() > 0) {
        DetailRenouvellementMandat detailRenouvellementMandat = new DetailRenouvellementMandat();
try{
            /* Update le DetailRenouvellementMandat precedent */
            UpdateDetailRenouvellementMandatTrt updateDetailRenouvellementMandatTrt =  new UpdateDetailRenouvellementMandatTrt();
            DetailRenouvellementMandat drm =  (DetailRenouvellementMandat)updateDetailRenouvellementMandatTrt.exec(mandat);
        //}
        /* insertion du DetailRenouvellementMandat dans la BD */

        MandatDAO mandatDAO = (MandatDAO)context.getBean("mandatDAO");

        detailRenouvellementMandat.setMandat(mandat);
        detailRenouvellementMandat.setDatFinmDrm(mandat.getDatFinMand());
        detailRenouvellementMandat.setNumSeqDrm(mandatDAO.getSequenceDetailRenouvellementMandat());
        detailRenouvellementMandat.setDatDebDrm(new Date());
        
        if (mandat.getCodEdemMand()==null ){/// cas validation renouvellement
         detailRenouvellementMandat.setCodEtatDrm("V");
        }
        if (mandat.getCodEdemMand()!=null && mandat.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_PRE_REN) ){/// cas saisie renouvellement
         detailRenouvellementMandat.setCodEtatDrm("S");
        }
        if (mandat.getCodEdemMand()!=null && mandat.getCodEdemMand().equalsIgnoreCase(Constants.COD_ETAT_MAND_ATT_VAL_REN)  ){/// cas prévalidation renouvellement
         detailRenouvellementMandat.setCodEtatDrm("P");
        }

        if (mandat.getDatFinMand()!=null){
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
            crudService.create(detailRenouvellementMandat);
        }
        return (detailRenouvellementMandat);

    }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur InsertDetailRenouvellementMandatTrt  ");
            text.append(e.toString());
            erreur.setCode("500");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertDetailRenouvellementMandatTrt");
            detailRenouvellementMandat.addError(erreur);
            logger.error(" *** Erreur lors de InsertDetailRenouvellementMandatTrt concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
            return (detailRenouvellementMandat);
        } 
    }
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}

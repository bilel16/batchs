package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.dao.ModificationDonneeClientDAO;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamListeMineursDevenusMajeursVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe permet d'extraire la liste des mineurs devenus majeur pour 
 * les personnes qui ont des contrats dans le structure
 * @since 23/05/2008
 * @author Mdimagh Med Lassaad
 */
public class GetListeDesMineursDevenusMajeursTrt extends Traitement {
    public GetListeDesMineursDevenusMajeursTrt() {
    }
    public IValueObject perform (IValueObject vo) {
        ParamListeMineursDevenusMajeursVo paramListeVo = 
            (ParamListeMineursDevenusMajeursVo)vo;
     try{
        Context context = ContextHandler.getContext();
        ModificationDonneeClientDAO modificationDonneeClientDAO = 
            (ModificationDonneeClientDAO)context.getBean("modificationDonneeClientDAO");
      
        List liste = modificationDonneeClientDAO.getListeDesMineursDevenusMajeurs(paramListeVo.getCodeStructure(),DateHandler.strToDate(DateHandler.dateToStr(paramListeVo.getDateJour())));
        paramListeVo.setListeDesMineursDevenusMajeurs(liste);
      
    return (paramListeVo);
    
    
    
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("Erreur dans GetListeDesMineursDevenusMajeursTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("GetListeDesMineursDevenusMajeursTrt");

        paramListeVo.addError(erreur);
        return (paramListeVo);
    }
    }

    public void genCroText (ValueObject vo){
    
    }
    

}

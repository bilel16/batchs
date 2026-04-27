package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.dao.ModificationDonneeClientDAO;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRecherchePersonneVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de rechercher une personne par le nom et prenom ou raison sociale et sigle
 * @author Mdimagh Med Lassaad
 * @since 28/05/2008
 */
public class RecherchePersonneParNomTrt extends Traitement {
    public RecherchePersonneParNomTrt() {
    }
    public IValueObject perform (IValueObject vo) {
        ParamRecherchePersonneVo paramListeVo = 
            (ParamRecherchePersonneVo)vo;
     try{
        Context context = ContextHandler.getContext();
        ModificationDonneeClientDAO modificationDonneeClientDAO = 
            (ModificationDonneeClientDAO)context.getBean("modificationDonneeClientDAO");
            
        List liste = new ArrayList(0);
        if(paramListeVo.getTypePersonne().equals("0")){
          liste = modificationDonneeClientDAO.getListePersonneParNom(paramListeVo.getNom(),paramListeVo.getPrenom());
        }else {
          liste = modificationDonneeClientDAO.getListePersonneParRaisonSociale(paramListeVo.getRaisonSociale(), paramListeVo.getSigle());    
        }
        
        paramListeVo.setListeDesPersonnes(liste);
      
    return (paramListeVo);
    
    
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("Erreur dans RecherchePersonneParNomTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("RecherchePersonneParNomTrt");

        paramListeVo.addError(erreur);
        return (paramListeVo);
    }
    }

    public void genCroText (ValueObject vo){
    
    }
      
    
}

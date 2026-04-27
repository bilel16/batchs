package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;


import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertPieceAnnexeTrt extends Traitement{

   

    public InsertPieceAnnexeTrt() {
    }

    /** méthode d'insertion  d'une nouvelle piece annexe qui prend en argument la classe PieceAnnexe et retourne un valueObject PieceAnnexe
     * @param   ValueObject : PieceAnnexe
     * @return  ValueObject : PieceAnnexe
     */
    public IValueObject perform(IValueObject vo) {
        PieceAnnexe pieceAnnexe = (PieceAnnexe)vo;
        try {
          Context context = ContextHandler.getContext();
          this.setCroFlag(false);
          
          
          CRUDservice crudservice = (CRUDservice)context.getBean("crudservice");
          crudservice.create(pieceAnnexe);
          
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans InsertPieceAnnexeTrt : ");
                text.append(e.toString());
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertPieceAnnexeTrt");
                pieceAnnexe.addError(erreur);
                logger.error("Exception : ",e);   
                throw new RuntimeException(e);  
              
            }   
        return (pieceAnnexe);

    }

    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);      
    }

}

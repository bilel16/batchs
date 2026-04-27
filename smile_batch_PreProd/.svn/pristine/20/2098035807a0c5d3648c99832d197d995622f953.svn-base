package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Cette  methode permet d'inserer le detail categorie contrat ( produit Epargne).
 * @param (contratCpt); Categorie
 * @return detailCatContrat : l'objet detailCatContrat inseré
 */
public class InsertDetailCatContratTrt extends Traitement{
   

    public InsertDetailCatContratTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");

        DetailCatCpt detailCatCpt = new DetailCatCpt();
        this.setCroFlag(false);
        ParamDetailCatCpt paramDetailCatCpt = (ParamDetailCatCpt)vo;
        try {
            
           SequenceDAO sequenceDAO = 
                (SequenceDAO)context.getBean("sequenceDAO");
            
            detailCatCpt.setContratCpt(paramDetailCatCpt.getContratCpt());
            detailCatCpt.setCategorie(paramDetailCatCpt.getCategorie());
            detailCatCpt.setNumDccDcc(sequenceDAO.getSequenceCategorieContrat().toString());
            detailCatCpt.setDatDebDcc(new Date());            
            detailCatCpt.setCodVersDcc(paramDetailCatCpt.getTypeVersementEpargne());
            crudService.create(detailCatCpt);
            return detailCatCpt;
            } catch (Exception e) {
                             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                             StringBuffer text = 
                                 new StringBuffer("Erreur dans InsertDetailCatContratTrt : ");
                             text.append(e.toString());
                             erreur.setCode("100");
                             erreur.setDescription(text.toString());
                             erreur.setKey("InsertDetailCatContrat");
                             detailCatCpt.addError(erreur);
                             logger.error("Erreur au niveau de l'agence <<" + paramDetailCatCpt.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);         
                             return (detailCatCpt);
              }    

    }
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}

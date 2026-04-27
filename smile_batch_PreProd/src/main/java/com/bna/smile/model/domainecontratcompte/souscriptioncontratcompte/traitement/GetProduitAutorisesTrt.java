package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.ProduitDAO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetProduitAutorisesTrt extends Traitement{
   

    public GetProduitAutorisesTrt() {
    }

    /**
     * methode permettant la recherche des produits autorisés
     * pour unr personne donnée
     * @param vo : ParamPers
     * @return liste
     */
    public IValueObject perform(IValueObject vo) {

        ParamPers paramPers = (ParamPers)vo;
        Context context = ContextHandler.getContext();
        Listes liste = new Listes();
        List l = new ArrayList();
        this.setCroFlag(false);
    try{
        ProduitDAO executeAStatement = (ProduitDAO)context.getBean("produitDAO");
        if(paramPers.getCas()==null ||paramPers.getCas()==""){
            if ((paramPers.getCodPaysPays().equalsIgnoreCase("LBY"))&&(paramPers.getBoolResPers() == 0)){// libyen non resident
                l = executeAStatement.getListLibyen(paramPers);
            }else{
                l = executeAStatement.getList(paramPers);
            }
           
        }else if(paramPers.getCas()=="personnel"){
                l = executeAStatement.getListCptPersonnel(paramPers);
              }        
        
        liste.setList(l);
        return liste;
        } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("Erreur dans GetProduitAutorises : ");
                 text.append(e.toString());
                 erreur.setCode("100");
                 erreur.setDescription(text.toString());
                 erreur.setKey("GetProduitAutorises");
                 liste.addError(erreur);
                 logger.error("Exception : ",e);  
                 return (liste);
        } 
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}

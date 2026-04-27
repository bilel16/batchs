package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.Categorie;
import com.bna.commun.model.CategorieId;
import com.bna.commun.model.Regime;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamEpargne;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ChargerRgmCatEpargneTrt extends Traitement{
    
    public ChargerRgmCatEpargneTrt() {
    }

    /**
     * Fonction qui permet de charger la liste des régime du plan épargne d'un produit spécifique, de charger la liste
     * des catégories epargne de ce régime selon le choix du régime. le choix de la catégorie permet d'extraire un objet catégorie 
     * afin d'extraire les infos nécéssaires.
     * @param ParamEpargne      : codRgmRgm ( régime épargne ) , codPrdPrd ( code produit), codCatCat (categoriePersonne)
     * @return ListRgmCatEpargne : liste des régime, liste des catégorie, catégorie epargne.      
     * @Author : El arbi hassine
     */
    public IValueObject perform(IValueObject vo) {
        ListRgmCatEpargne listRgmCatEpargne = new ListRgmCatEpargne();
        ParamEpargne paramEpargne = (ParamEpargne)vo;
        
        try {
            
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteriaRgm = searchEngine.createCriteria();
            ICriteria criteriaCat = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            
            List listeRgmEpargne = new ArrayList();
            List listeCatEpargne = new ArrayList();
            Categorie cat = null;
             this.setCroFlag(false);
            /*Charger tous les régimes d'épargne*/

            if (paramEpargne.getCodPrdPrd() != null) {
                criteriaRgm.add(expression.eq("regimeId.codPrdPrd", 
                                              paramEpargne.getCodPrdPrd()));

                listeRgmEpargne = searchEngine.find(Regime.class, criteriaRgm);


                criteriaCat.add(expression.eq("categorieId.codPrdPrd", 
                                              paramEpargne.getCodPrdPrd()));

                if ((paramEpargne.getCodRgmRgm()!=null && paramEpargne.getCodRgmRgm().equals("")) || paramEpargne.getCodRgmRgm()==null ) {
                    Regime regime = (Regime)listeRgmEpargne.get(0);
                    criteriaCat.add(expression.eq("categorieId.codRgmRgm", 
                                                  regime.getRegimeId().getCodRgmRgm()));
                } else {
                    criteriaCat.add(expression.eq("categorieId.codRgmRgm", 
                                                  new Long(paramEpargne.getCodRgmRgm())));
                }
                listeCatEpargne = 
                        searchEngine.find(Categorie.class, criteriaCat);

                if (paramEpargne.getCodCatCat().equals("")) {
                    cat = (Categorie)listeCatEpargne.get(0);
                } else {
                    CategorieId categorieId = new CategorieId();
                    categorieId.setCodPrdPrd(paramEpargne.getCodPrdPrd());
                    categorieId.setCodRgmRgm(new Long(paramEpargne.getCodRgmRgm()));
                    categorieId.setCodCatCat(paramEpargne.getCodCatCat());

                    cat = (Categorie)searchEngine.get(Categorie.class, categorieId);
                }


                /*Charger le VO listRgmCatEpargne*/
                listRgmCatEpargne.setListRgmEpargne(listeRgmEpargne);
                listRgmCatEpargne.setListCatEpargne(listeCatEpargne);
                listRgmCatEpargne.setCategorie(cat);

            }
            return listRgmCatEpargne;

            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur dans ChargerRgmCatEpargneTrt : ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                erreur.setKey("ChargerRgmCatEpargne");
                listRgmCatEpargne.addError(erreur);
                logger.error("Exception : ",e);  
                return (listRgmCatEpargne);
            }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}

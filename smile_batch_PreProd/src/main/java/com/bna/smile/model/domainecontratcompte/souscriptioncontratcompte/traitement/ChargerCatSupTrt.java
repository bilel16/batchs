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

public class ChargerCatSupTrt extends Traitement{
 
    

    public ChargerCatSupTrt() {
    }
    
    
    /**
     * Méthode qui permet de charger la liste des categories du plan épargne d'un produit spécifique, de charger la liste
     * des catégories (supérieures) epargne de ce régime selon le choix du régime.
     * @param   ParamEpargne      : codRgmRgm ( régime épargne ) , codPrdPrd ( code produit), codCatCat (categoriePersonne)
     * @return  ListRgmCatEpargne :  liste des catégories, catégorie epargne.      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
     public IValueObject perform(IValueObject vo) {

        try {
            ParamEpargne paramEpargne = (ParamEpargne)vo;
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteriaRgm = searchEngine.createCriteria();
            ICriteria criteriaCat = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

            ListRgmCatEpargne listRgmCatEpargne = new ListRgmCatEpargne();
            List listeRgmEpargne = new ArrayList();
            List listeCatEpargne = new ArrayList();
            Categorie cat = null;

            /*Charger tous les régimes d'épargne qui peuvent etre affectés au contrat*/

            if (paramEpargne.getCodPrdPrd() != null) {
                criteriaRgm.add(expression.eq("regimeId.codPrdPrd",paramEpargne.getCodPrdPrd()));

                listeRgmEpargne = searchEngine.find(Regime.class, criteriaRgm);

                criteriaCat.add(expression.eq("categorieId.codPrdPrd", paramEpargne.getCodPrdPrd()));

                if ((paramEpargne.getCodRgmRgm()!= null && paramEpargne.getCodRgmRgm().equals("")) || paramEpargne.getCodRgmRgm()== null) {///Charger les catégorie du régime spécifié
                    Regime regime = (Regime)listeRgmEpargne.get(0);
                    criteriaCat.add(expression.eq("categorieId.codRgmRgm", regime.getRegimeId().getCodRgmRgm()));
                } else {
                    criteriaCat.add(expression.eq("categorieId.codRgmRgm", new Long(paramEpargne.getCodRgmRgm())));
                }
                
                if (paramEpargne.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM) || paramEpargne.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE) ){
                    criteriaCat.add(expression.ge("categorieId.codCatCat", paramEpargne.getCodCatCat()));/// les categories superieures pour le PEE et PEM
                    if(paramEpargne.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                        criteriaCat.add(expression.eq("codTypCat", "N"));/// les categories superieures pour le PEE et PEM
                        criteriaCat.add(expression.ge("codCorrCat", paramEpargne.getCodCorrCat()));/// les categories superieures pour le PEE et PEM
                    }     
                }
                 listeCatEpargne = searchEngine.find(Categorie.class, criteriaCat);

                if (paramEpargne.getCodCatCat().equals("")) {
                    cat = (Categorie)listeCatEpargne.get(0);
                } else {
                    CategorieId categorieId = new CategorieId();
                    categorieId.setCodPrdPrd(paramEpargne.getCodPrdPrd());
                    categorieId.setCodRgmRgm(new Long(paramEpargne.getCodRgmRgm()));
                    categorieId.setCodCatCat(paramEpargne.getCodCatCat());

                    cat =  (Categorie)searchEngine.get(Categorie.class, categorieId);
                }


                /*Charger le VO listRgmCatEpargne*/
                listRgmCatEpargne.setListRgmEpargne(listeRgmEpargne);
                listRgmCatEpargne.setListCatEpargne(listeCatEpargne);
                listRgmCatEpargne.setCategorie(cat);

            }
            return listRgmCatEpargne;

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("Erreur dans ChargerCatSupTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ChargerCatSupTrt");
            vo.addError(erreur);
            logger.error(" *** Erreur lors de ChargerCatSupTrt ", e);
            return null;
        }
    }



    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}

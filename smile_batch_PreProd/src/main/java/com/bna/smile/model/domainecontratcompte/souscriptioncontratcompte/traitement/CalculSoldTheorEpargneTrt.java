package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Categorie;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class CalculSoldTheorEpargneTrt extends Traitement{

    public CalculSoldTheorEpargneTrt() {
    }
    
    
    /**
     * Fonction qui permet de calculer le solde théorique qu'un contrat d'epagrne ayant
     * une catégories donnée et un régime donnée doit avoir aujourd'hui.
     * @param   ParamDetailCatCpt      : ContratCpt , Categorie 
     * @return  PrimitiveVO :  Double      
     * @Author : BOUSSEN Youssef & KRIAA Hatem
     */
     public IValueObject perform(IValueObject vo) {

        PrimitiveVO primitiveVO=new PrimitiveVO();

    try  {
            ParamDetailCatCpt paramDetailCatCpt = (ParamDetailCatCpt)vo;
            Categorie cat=new Categorie();
            
            if (paramDetailCatCpt.getCategorie().getMontVersCat() == null){///* si la catégorie est deja chargée

                ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
                ICriteria  criteriaCat = searchEngine.createCriteria();
                IExpression expression = searchEngine.createExpression();

                
                criteriaCat.add(expression.eq("categorieId.codCatCat", paramDetailCatCpt.getCategorie().getCategorieId().getCodCatCat()));
                criteriaCat.add(expression.eq("categorieId.codRgmRgm", paramDetailCatCpt.getCategorie().getCategorieId().getCodRgmRgm()));
                criteriaCat.add(expression.eq("categorieId.codPrdPrd", paramDetailCatCpt.getCategorie().getCategorieId().getCodPrdPrd()));
                
                List listeCatEpargne = new ArrayList();
    
                listeCatEpargne = searchEngine.find(Categorie.class, criteriaCat);
    
                if (listeCatEpargne.size()>=0) {
                    cat = (Categorie)listeCatEpargne.get(0);
                }
            }else{
                cat = paramDetailCatCpt.getCategorie();
            }
            /* Date ouverture contrat */
            Date    dateOuv  = paramDetailCatCpt.getContratCpt().getDatOuvCcpt();
            Integer nbrMois  = DateHandler.getMonthsBetween(dateOuv,new Date())+1;
            Double  tauxMois = (Constants.TMM).doubleValue()/1200;
            
            Double sold1  = (Math.pow(new Double(1+tauxMois),new Double(nbrMois)));
            Double soldTheo = ((sold1.doubleValue()-1)*cat.getMontVersCat()/tauxMois);
            soldTheo=Math.min(soldTheo,Double.valueOf(cat.getMontCaptCat().toString()));
            
            primitiveVO.setVDouble(soldTheo);
            return(primitiveVO);

    
            } catch (Exception e)  {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = new StringBuffer("Erreur dans CalculSoldTheorEpargneTrt : ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                erreur.setKey("CalculSoldTheorEpargneTrt");
                vo.addError(erreur);
                logger.error(" *** Erreur lors de CalculSoldTheorEpargneTrt", e);
                return (primitiveVO);
            } finally  {
            }

    }
    
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}

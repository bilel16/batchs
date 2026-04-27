package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.TypeCarte;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Extraire la liste des types de carte qui sont éligible pour un contrat donné. 
 * @author Ramzi
 * @param ContratCpt
 * @return Listes : de TypeCarte
 * @since 28/06/2007
 * 
 */
public class GetCartesEligibleContratTrt  extends Traitement{
    public GetCartesEligibleContratTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        ContratCpt contratCpt = (ContratCpt)vo;
        Listes listeTypCart = new Listes();
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
            //critere type carte encore valide 
            criteria.add(expression.eq("codEtatTcar", "V"));
            
            // determiner code produit contrat
            String codeProduit = contratCpt.getContratCptId().getCodPrdPrd().toString();
            // determiner type personne PP/PM
            String typePersonne = contratCpt.getClient().getTypePers().getCodTperTper();
            
            String [] critTypePers = new String [2];
            critTypePers[0] = Constants.COD_TPER_TCAR_TP;
            ///si PM
            if(typePersonne.equals(Constants.COD_TPER_TCAR_PM)){
                //ajout criteres du type personne PM
                critTypePers[1] = Constants.COD_TPER_TCAR_PM;
                criteria.add(expression.in("codTperTcar", critTypePers));
                
                //ajout criteres produit du contrat dans la liste des produits eligibles
                criteria.add(expression.like("libPrdmTcar", "%"+codeProduit+"%"));
            }   
            ///si PP ou Cotitulaire
            else{
                //ajout criteres du type personne PM,PP
                critTypePers[1] = Constants.COD_TPER_TCAR_PP;
                criteria.add(expression.in("codTperTcar", critTypePers));
                
                //ajout criteres produit du contrat dans la liste des produits eligibles
                criteria.add(expression.like("libPrdpTcar", "%"+codeProduit+"%"));   
            }
            
            List list = searchEngine.find(TypeCarte.class, criteria);
            listeTypCart.setList(list);
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetCartesEligibleContratTrt "+e.getMessage());;
                listeTypCart.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return listeTypCart;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}

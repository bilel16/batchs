package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.PersonneTypeCarteCpt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.TypeCarteCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Vérifier s’il existe une carte de type donnée valide pour un porteur donné sur un contrat donné.
 * @author Ramzi
 * @param PersonneTypeCarteCpt
 * @return CarteBancaire
 * @since 21/06/2007
 * 
 */
public class VerifPossedeTypeCarteTrt extends Traitement{
    public VerifPossedeTypeCarteTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        PersonneTypeCarteCpt  personneTypeCarteCpt  = (PersonneTypeCarteCpt )vo;
        CarteBancaire carteBancaire = new CarteBancaire();
  
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
            PersonneStrc personneStrc = personneTypeCarteCpt.getPersonneStrc();
            TypeCarteCpt typeCarteCpt = personneTypeCarteCpt.getTypeCarteCpt();
            Long typeCarte = typeCarteCpt.getTypeCarte();
            ContratCpt contratCpt = typeCarteCpt.getContratCpt();
            
            //critère porteur si not null
            if(personneStrc.getCodTpceTpce()!=null && personneStrc.getNumPcePers()!=null){
             criteria.add(expression.eq("codTpceCarb",personneStrc.getCodTpceTpce()));
             criteria.add(expression.eq("numPceCarb",personneStrc.getNumPcePers()));
            } 
            //critère contrat
            criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd",contratCpt.getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc",contratCpt.getContratCptId().getCodStrcStrc()));
            criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",contratCpt.getContratCptId().getNumCcptCcpt()));
            
            //critère type carte 
            ///si carte CIB à PUCE + verifier s'il existe une CIB ancienne
            if(typeCarte.equals(Long.valueOf(Constants.COD_TCAR_TCAR_CIBT))){
                Long[] tpCarte = {Long.valueOf(Constants.COD_TCAR_TCAR_CIBT),Long.valueOf(Constants.COD_TCAR_TCAR_CIBT_ANCIEN)};   
                criteria.add(expression.in("typeCarte.codTcarTcar",tpCarte)); 
            }else{
                criteria.add(expression.eq("typeCarte.codTcarTcar",typeCarte));
            }
            
            //critère 1 de validiter:  datFinCarb >= date du jour
            criteria.add(expression.ge("datFinCarb",DateHandler.strToDate(DateHandler.dateJour())));
            
            //critère 2 de validiter:  codEtatCarb est remise
            criteria.add(expression.eq("codEtatCarb",Constants.COD_ETAT_CARB_CarteRemise));
            
            // requete
            List list = searchEngine.find(CarteBancaire.class, criteria);
            
            if(list!=null && list.size()>0){
                carteBancaire = (CarteBancaire) list.get(0);
            }
             
            
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("VerifPossedeTypeCarteTrt "+e.getMessage());;
                carteBancaire.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return carteBancaire;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}

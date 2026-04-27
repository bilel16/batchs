package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.PersonneTypeCarteCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Vérifier s’il existe une demande de carte de même type en cours d’exécution  pour un porteur donné sur un contrat donné.
 * @author Ramzi
 * @param PersonneTypeCarteCpt:: PersonneStrc : le porteur,  TypeCarteCpt : type de carte et contrat
 * @return DemandeCarte
 * @since 19/06/2007
 * 
 */
public class VerifDemandeCarteEnCoursTrt extends Traitement{
    public VerifDemandeCarteEnCoursTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        PersonneTypeCarteCpt personneTypeCarteCpt = (PersonneTypeCarteCpt)vo;
        DemandeCarte demandeCarte = new DemandeCarte();
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
            //critères personne
            PersonneStrc personneStrc = personneTypeCarteCpt.getPersonneStrc();
            GetPersonneTrt  getPersonneTrt = new GetPersonneTrt();  
            Personne personne = (Personne) getPersonneTrt.exec(personneStrc);
            criteria.add(expression.eq("codTpceDcar",personne.getTypePiece().getCodTpceTpce()));
            criteria.add(expression.eq("numPceDcar",personne.getNumPcePers()));
            
            //critère contrat
            ContratCpt contratCpt = personneTypeCarteCpt.getTypeCarteCpt().getContratCpt();
            criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd",contratCpt.getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc",contratCpt.getContratCptId().getCodStrcStrc()));
            criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt",contratCpt.getContratCptId().getNumCcptCcpt()));
            
            //critère type carte 
             criteria.add(expression.eq("typeCarte.codTcarTcar",personneTypeCarteCpt.getTypeCarteCpt().getTypeCarte()));
            
            //critère sur etat de la carte :
            String [] valEtat = {Constants.COD_ETAT_DCAR_Attente,Constants.COD_ETAT_DCAR_AttenteDR,Constants.COD_ETAT_DCAR_AttenteScm,Constants.COD_ETAT_DCAR_AttenteScc,Constants.COD_ETAT_DCAR_AttenteGarantie,Constants.COD_ETAT_DCAR_PrevaliderDR,Constants.COD_ETAT_DCAR_PrevaliderScm,Constants.COD_ETAT_DCAR_PrevaliderScc,Constants.COD_ETAT_DCAR_Valider,Constants.COD_ETAT_DCAR_CarteRecu};
            criteria.add(expression.in("codEtatDcar",valEtat));
             
            List list = searchEngine.find(DemandeCarte.class, criteria);
            if(list != null &&  list.size()>0){
                demandeCarte = (DemandeCarte) list.get(0);
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");            
            erreur.setDescription("VerifDemandeCarteEnCoursTrt " + 
                                  e.getMessage());
            demandeCarte.addError(erreur);
            logger.error("Exception : ",e);
            throw new RuntimeException(e); 
        }
        return demandeCarte;
       
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
}

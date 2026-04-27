package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.OppositionMoyenPaiement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.OppositionMoyPaiementDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Extraire la liste des oppositions sur moyen de paiement.
 * @author Lamia
 * @return Listes : des oppositions
 * @since 14/04/2008
 *
 */
public class GetListOppositionTrt extends Traitement{
    public GetListOppositionTrt() {
    }
    
    public IValueObject perform(IValueObject vo) throws Exception{
      
        Listes listeOppositionsMoyPaie =new Listes();
        List listSelonNaturMP = new ArrayList();
     
        ParamRechercheOpposition paramRechercheOpposition = (ParamRechercheOpposition)vo; 
        
        
        try {
        
            Context context = ContextHandler.getContext();
            OppositionMoyPaiementDAO oppositionMoyPaiementDAO = 
                (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            
//------------------------------------------------------Selon le type opération : opposition / main levée
             if (paramRechercheOpposition.getTypeOper() != null &&
                 paramRechercheOpposition.getTypeOper() != "" ) {
                 criteria.add(expression.eq("codEtatOpmp", 
                                            paramRechercheOpposition.getTypeOper()));
             }
//-----------------------------------------------------Selon le type moyen paiement (cheque, carte, espèce, livret...)
            if (paramRechercheOpposition.getTypeMoyPaie() != null &&
                paramRechercheOpposition.getTypeMoyPaie() != 0 ) {
                criteria.add(expression.eq("typeMoyenPaiement.codMoypTmoy", 
                                           paramRechercheOpposition.getTypeMoyPaie()));
            }
        //----------------------------------------------recherche par période
            if (paramRechercheOpposition.getDateDebutConsult() != null) {
                criteria.add(expression.ge("oppositionMoyenPaiementId.datOperOpmp", 
                                           paramRechercheOpposition.getDateDebutConsult()));
            }
            if (paramRechercheOpposition.getDateFinConsult() != null) {
                criteria.add(expression.lt("oppositionMoyenPaiementId.datOperOpmp", 
                                           paramRechercheOpposition.getDateFinConsult()));
            }
        //------------------------------------recherche par type pièce num pièce demandeur
            if (paramRechercheOpposition.getTypPceDemd() != null) {
                criteria.add(expression.eq("typePiece.codTpceTpce", 
                                           paramRechercheOpposition.getTypPceDemd()));
            }
            if (paramRechercheOpposition.getNumPceDemd() != null && !paramRechercheOpposition.getNumPceDemd().equals("") ) {
                criteria.add(expression.eq("numPceOpmp", 
                                           paramRechercheOpposition.getNumPceDemd()));
            }
        //----------------------------------------------recherche par num contrat cpt
            if (paramRechercheOpposition.getCodPrdPrd() != null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                           paramRechercheOpposition.getCodPrdPrd()));
            }
            if (paramRechercheOpposition.getCodStrcStrc() != null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           paramRechercheOpposition.getCodStrcStrc()));
            }
            if (paramRechercheOpposition.getNumCcptCcpt() != null) {
                criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                           paramRechercheOpposition.getNumCcptCcpt()));
            }

            if (paramRechercheOpposition.getNumMoypTmoy() != null) {
                criteria.add(expression.eq("oppositionMoyenPaiementId.numMoypOpmp", 
                                           paramRechercheOpposition.getNumMoypTmoy()));
            }
           
       List l = searchEngine.find(OppositionMoyenPaiement.class, criteria);
         // cas de chèque ou de carte, faut vérifier la nature choisit
       /*             if(paramRechercheOpposition.getTypeMoyPaie() == 3 || 
                        paramRechercheOpposition.getTypeMoyPaie() == 1){
                            if (l != null && l.size() > 0) {
                                for (Iterator itOppMP = l.iterator(); itOppMP.hasNext();) {
                                    OppositionMoyenPaiement oppMP =(OppositionMoyenPaiement)itOppMP.next();
                                    if(paramRechercheOpposition.getTypeMoyPaie() == 3){ // moyen paie == carte
                                         PrimitiveVO  primitiveVO  = new PrimitiveVO();
                                         primitiveVO.setVString(oppMP.getOppositionMoyenPaiementId().getNumMoypOpmp());
                //-----------------------------recherche de la carte selon num moy paie, pour vérifier sa nature
                                         GetCarteBancaireTrt getCarteBancaireTrt= new GetCarteBancaireTrt();
                                         CarteBancaire carteBancaire=(CarteBancaire)getCarteBancaireTrt.exec(primitiveVO);
                                         if(paramRechercheOpposition.getNatureMoyPaie().equals
                                                        (carteBancaire.getCarteBancaireId().getCodBinTcar().toString())){
                                                   listSelonNaturMP.add(oppMP);
                                               }
                       //-----------------------------------------------------
                                    }else if(paramRechercheOpposition.getTypeMoyPaie() == 1){ // moy paie == chèque
                                            //  OppositionMoyPaiementDAO oppositionMoyPaiementDAO= (OppositionMoyPaiementDAO)context.getBean("oppositionMoyPaiementDAO");
                                              String naturCheque = oppositionMoyPaiementDAO.getNatureCheque(oppMP.getOppositionMoyenPaiementId().getNumMoypOpmp());
                                              if(naturCheque!= null){
                                                if(paramRechercheOpposition.getNatureMoyPaie().equals
                                                               (naturCheque)){
                                                    if (paramRechercheOpposition.getNumMoypTmoy() != null) {
                                                    //------recherche par numéro moyen paie/ ccpt
                                                            if(isDernierEtatMoyPaiement(oppositionMoyPaiementDAO,paramRechercheOpposition)){
                                                                listSelonNaturMP.add(oppMP); 
                                                            }else {
                                                                // le dernier etat (opération : O/L) ne correspond pas à l'operation recherhcé
                                                                 listSelonNaturMP.add(new ArrayList()); //retourne liste vide
                                                            }
                                                        }else{
                                                        //-------autre critère de recherche
                                                          logger.info("Le numéro moyen de paiement est vide -- NULL");
                                                          listSelonNaturMP.add(oppMP);
                                                        }
                                                }else{
                                                    logger.info("La nature du chèque ne correspond pas au critère de recherche.");
                                                }
                                            }else{// Aucune vérification sur la nature de chèque (permettre les données migrés d'etre pris en compte ----07/05/08)
                                               
                                             if (paramRechercheOpposition.getNumMoypTmoy() != null) {
                                                         //------recherche par numéro moyen paie/ ccpt
                                                         if(isDernierEtatMoyPaiement(oppositionMoyPaiementDAO,paramRechercheOpposition)){
                                                             listSelonNaturMP.add(oppMP); 
                                                         }else {
                                                             // le dernier etat (opération : O/L) ne correspond pas à l'operation recherhcé
                                                              listSelonNaturMP.add(new ArrayList()); //retourne liste vide
                                                         }
                                                     }else {
                                                         listSelonNaturMP.add(oppMP);         
                                                     }
                                             }
                                    }
                                }// end for
                               
                             }// fin if
                        listeOppositionsMoyPaie.setList(listSelonNaturMP);
                    }else {*/
                        listeOppositionsMoyPaie.setList(l); 
                  //  }
       } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetListOppositionTrt "+e.getMessage());;
                listeOppositionsMoyPaie.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return listeOppositionsMoyPaie;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
 public boolean isDernierEtatMoyPaiement(OppositionMoyPaiementDAO oppMoyPaiementDAO,ParamRechercheOpposition paramRechercheOpp){
       
        boolean etat = false;
 
        try{
        List listDernierEtatMoyenPaiement = 
                             oppMoyPaiementDAO.getDernierEtatMoyPaiement(paramRechercheOpp.getTypeMoyPaie().toString(),
                                                                         paramRechercheOpp.getNumMoypTmoy(),
                                                                         paramRechercheOpp.getCodStrcStrc().toString(),
                                                                         paramRechercheOpp.getCodPrdPrd().toString(),
                                                                         paramRechercheOpp.getNumCcptCcpt().toString());
                           
                       ListOrderedMap dernierEtatMoyenPaiement = null;
                       if(listDernierEtatMoyenPaiement.size()>0 && listDernierEtatMoyenPaiement!= null){
                           dernierEtatMoyenPaiement = (ListOrderedMap) listDernierEtatMoyenPaiement.get(0);
                          String codEtat = (String)(dernierEtatMoyenPaiement.getValue(0));
                          if(codEtat.equals(paramRechercheOpp.getTypeOper())){
                              etat = true;
                              }else{
                                  etat = false;
                              }
                       }
        return etat;
    } catch (Exception e) {
             logger.error("Exception : ",e);
             throw new RuntimeException(e);       
     }
}

}
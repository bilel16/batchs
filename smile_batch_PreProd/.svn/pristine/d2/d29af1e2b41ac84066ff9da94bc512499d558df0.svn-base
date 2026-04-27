package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement :permet de donner la liste de toutes les demandes de chèque par agence et par etat
 * Rmq : on peut donner G : etat genéral pour  retourner que la liste générale des demandes ( toutes les etats ).
 * ou laisser null le paramètre etat afin de retourner toutes les listes des demandes
 * @author El arbi hassine
 * @since 07/06/2007
 * 
 */
public class GetListDemandesChequesTrt extends Traitement{
    public GetListDemandesChequesTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();

        List listeGenerale;
        List listeAttente = new ArrayList();
        List listeValidee = new ArrayList();
        List listeRejetee = new ArrayList();
        List listeTotSatisfaite = new ArrayList();
        List listePartSatisfaite = new ArrayList();
        List listeTotDelivree = new ArrayList();
        List listePartDelivree = new ArrayList();
        List listeEnvoyeeDR_DCCI = new ArrayList();

        try {
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();

           if(paramDemandeCheque.getContratPersonne()!= null){
            if (paramDemandeCheque.getContratPersonne().getContratCptId().getCodStrcStrc() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                           paramDemandeCheque.getContratPersonne().getContratCptId().getCodStrcStrc()));
            }

            if (paramDemandeCheque.getContratPersonne().getContratCptId().getCodPrdPrd() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                           paramDemandeCheque.getContratPersonne().getContratCptId().getCodPrdPrd()));
            }

            if (paramDemandeCheque.getContratPersonne().getContratCptId().getNumCcptCcpt() != 
                null) {
                criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                           paramDemandeCheque.getContratPersonne().getContratCptId().getNumCcptCcpt()));
            }
           }
           
            if(paramDemandeCheque.getCompteInterne()!= null){
            
                criteria.add(expression.eq("compteInterne.compteInterneId.codPrdPrd", 
                                           paramDemandeCheque.getCompteInterne().getCompteInterneId().getCodPrdPrd()));
                criteria.add(expression.eq("compteInterne.compteInterneId.codStrcStrc", 
                                           paramDemandeCheque.getCompteInterne().getCompteInterneId().getCodStrcStrc()));
                criteria.add(expression.eq("compteInterne.compteInterneId.numCptiCpti", 
                                           paramDemandeCheque.getCompteInterne().getCompteInterneId().getNumCptiCpti()));           
            
            }

            if(paramDemandeCheque.getContratPersonne()!= null){
             if (paramDemandeCheque.getContratPersonne().getPersonneId().getNumPcePers() != 
                null && 
                paramDemandeCheque.getContratPersonne().getPersonneId().getCodTpceTpce() != 
                null) {
                criteria.add(expression.eq("numPceDchq", 
                                           paramDemandeCheque.getContratPersonne().getPersonneId().getNumPcePers()));
                criteria.add(expression.eq("codTpceDchq", 
                                           paramDemandeCheque.getContratPersonne().getPersonneId().getCodTpceTpce()));
            }
            }

            if (paramDemandeCheque.getNumDemande() != null) {
                criteria.add(expression.eq("numDemDchq", 
                                           paramDemandeCheque.getNumDemande()));
            }

            if (paramDemandeCheque.getDateDebut() != null) {
                criteria.add(expression.ge("datDemDchq", 
                                           paramDemandeCheque.getDateDebut()));
            }
            if (paramDemandeCheque.getDateFin() != null) {
                criteria.add(expression.le("datDemDchq", 
                                           paramDemandeCheque.getDateFin()));
            }            
           
            List l = searchEngine.find(DemandeCheque.class, criteria);
            if (l != null && l.size() > 0) {
                listeGenerale = l;
                // parcourir les demandes de cheque pour extraires les diiférentes demandes selon l'etat
                for (Iterator itDemande = l.iterator(); itDemande.hasNext(); 
                ) {
                    DemandeCheque demandeCheque = 
                        (DemandeCheque)itDemande.next();

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_ATTENTE)) {
                        // traitement des demandes en attente 
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE)) {
                            listeAttente.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_VALIDEE)) {
                        // traitement des demandes validees
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                            listeValidee.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_REJETEE)) {
                        // traitement des demandes rejetees
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                            listeRejetee.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                        // traitement des demandes totalement satisfaite
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                            listeTotSatisfaite.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                        // traitement des demandes parteillement satisfaite
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                            listePartSatisfaite.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                        // traitement des demandes totalement délivrées
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                            listeTotDelivree.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                        // traitement des demandes partiellement  délivrées
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                            listePartDelivree.add(demandeCheque);
                        }
                    }

                    if (paramDemandeCheque.getEtatDemande() == null || 
                        paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                        // traitement des demandes envoyee vers DR/.DCCCI
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                            listeEnvoyeeDR_DCCI.add(demandeCheque);
                        }
                    }

                } // fin for  
                listesDemandesCheques.setListeGenerale(listeGenerale);
                listesDemandesCheques.setListeAttente(listeAttente);
                listesDemandesCheques.setListeValidee(listeValidee);
                listesDemandesCheques.setListeRejetee(listeRejetee);
                listesDemandesCheques.setListeTotSatisfaite(listeTotSatisfaite);
                listesDemandesCheques.setListePartSatisfaite(listePartSatisfaite);
                listesDemandesCheques.setListeTotDelivree(listeTotDelivree);
                listesDemandesCheques.setListePartDelivree(listePartDelivree);
                listesDemandesCheques.setListeEnvoyeeDR_DCCI(listeEnvoyeeDR_DCCI);

            }

            return (listesDemandesCheques);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListDemandesChequesTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListDemandesCheques");
            listesDemandesCheques.addError(erreur);
            logger.error("Exception : ",e); 
            return (listesDemandesCheques);
        }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }  
}

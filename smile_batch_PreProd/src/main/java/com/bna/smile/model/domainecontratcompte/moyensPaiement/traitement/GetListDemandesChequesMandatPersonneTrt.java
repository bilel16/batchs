package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeChequeMandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecommun.traitement.GetDetailContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe de traitement :permet de donner les listes des demandes de chèque par etat, sur un contrat donnée, un demandeur donnée et un
 * mandat donnée 
 * : attente, validée,rejetée, tot.Satisfaite, part.Satisfaite, tot.délivré
 * part.délivré, envoyée DR/DCCCI
 * Rmq : on peut donner 20 : etat genéral pour  retourner que la liste générale des demandes ( toutes les etats ).
 * ou laisser null le paramètre etat afin de retourner toutes les listes des demandes
 * @author El arbi hassine
 * @since 15/06/2007
 * 
 */
public class GetListDemandesChequesMandatPersonneTrt extends Traitement{
    public GetListDemandesChequesMandatPersonneTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        ParamDemandeCheque paramDemandeCheque = (ParamDemandeCheque)vo;
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();

        List listeGenerale = new ArrayList();
        List listeAttente = new ArrayList();
        List listeValidee = new ArrayList();
        List listeRejetee = new ArrayList();
        List listeTotSatisfaite = new ArrayList();
        List listePartSatisfaite = new ArrayList();
        List listeTotDelivree = new ArrayList();
        List listePartDelivree = new ArrayList();
        List listeEnvoyeeDR_DCCI = new ArrayList();
        List listDemChqMPersonne = new ArrayList();
        try {
            this.setCroFlag(false);
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();


            // verification de l'etat du contrat
            GetDetailContratTrt getDetailContratTrt = 
                new GetDetailContratTrt();
            ContratCptId contratCptId = new ContratCptId();

            contratCptId.setCodStrcStrc(new Long(paramDemandeCheque.getContratPersonne().getContratCptId().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(paramDemandeCheque.getContratPersonne().getContratCptId().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(paramDemandeCheque.getContratPersonne().getContratCptId().getNumCcptCcpt()));

            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratTrt.exec(contratCptId);
            //test si contrat existant et valide
            if (contratCpt.getContratCptId() != null && 
                contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {

                if (paramDemandeCheque.getContratPersonne().getPersonneId() != 
                    null) {
                    //--------------------------------------------------
                    //---- Recherche la personne
                    //--------------------------------------------------
                    Personne personne = new Personne();
                    PersonneStrc personneS = 
                        (PersonneStrc)paramDemandeCheque.getContratPersonne().getPersonneId();

                    PersonneService PersonneService = 
                        (PersonneService)context.getBean("PersonneService");
                    GetPersonneTrt getPersonne = new GetPersonneTrt();
                    personne = (Personne)getPersonne.exec(personneS);
                    if (personne.getNumSeqPers() != 
                        null) { // si la personne existe
                        criteria.add(expression.eq("demandeChequeMandatPersonneId.numSeqPers", 
                                                   personne.getNumSeqPers()));
                    }
                }

                criteria.add(expression.eq("demandeChequeMandatPersonneId.numMandMand", 
                                           paramDemandeCheque.getNumMandMand()));


                List l = 
                    searchEngine.find(DemandeChequeMandatPersonne.class, criteria);
                if (l != null && l.size() > 0) {
                    listDemChqMPersonne = l;
                    // parcourir les demandes de cheque pour extraires les diiférentes demandes selon l'etat
                    for (Iterator itDemande = l.iterator(); 
                         itDemande.hasNext(); ) {
                        DemandeChequeMandatPersonne demandeChequeMandatPersonne = 
                            (DemandeChequeMandatPersonne)itDemande.next();

                        if (demandeChequeMandatPersonne.getDemandeCheque().getTypeConfection().getCodConfConf().equals(paramDemandeCheque.getTypeConfection())) {
                            listeGenerale.add(demandeChequeMandatPersonne.getDemandeCheque());

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_ATTENTE)) {
                                // traitement des demandes en attente 
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE)) {
                                    listeAttente.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_VALIDEE)) {
                                // traitement des demandes validees
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                                    listeValidee.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_REJETEE)) {
                                // traitement des demandes rejetees
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                                    listeRejetee.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                                // traitement des demandes totalement satisfaite
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                                    listeTotSatisfaite.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                                // traitement des demandes parteillement satisfaite
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                                    listePartSatisfaite.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                                // traitement des demandes totalement délivrées
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                                    listeTotDelivree.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                                // traitement des demandes partiellement  délivrées
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                                    listePartDelivree.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
                            }

                            if (paramDemandeCheque.getEtatDemande() == null || 
                                paramDemandeCheque.getEtatDemande().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                                // traitement des demandes envoyee vers DR/.DCCCI
                                if (demandeChequeMandatPersonne.getDemandeCheque().getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                                    listeEnvoyeeDR_DCCI.add(demandeChequeMandatPersonne.getDemandeCheque());
                                }
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
                    listesDemandesCheques.setListeDemChqMandatPersonne(listDemChqMPersonne);
                }
            }

            return (listesDemandesCheques);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans GetListDemandesChequesMandatPersonneTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetListDemandesChequesMandatPersonne");            
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

package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratRejete;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailMandatPersonne;
import com.bna.commun.model.LivretEpargne;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersOperationCompte;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Méhode permettant le rejet d'un contrat donnée
 * @param contratCptId
 * @return contratCptId 
 */
public class RejeterContratTrt extends Traitement{
   
    
    public RejeterContratTrt() {
    }

    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");

        ContratCptId contratCptIdAttente = (ContratCptId)vo;
        /*------------------------------- Suppression des mandats --------------------------------------------------*/
        ///extraire mandats sur le contrat en attente
        GetContratMandatTrt getContratMandatTrt = new GetContratMandatTrt();
        ContratCptId contratCptId = new ContratCptId();
        MandatRecherche mandatRecherche = new MandatRecherche();
        try {
          if(this.checkClotureJournee()){
            this.setCroFlag(false);  
            ContratCptMandat contratCptMandat = new ContratCptMandat();
            contratCptId.setCodPrdPrd(new Long(contratCptIdAttente.getCodPrdPrd()));
            contratCptId.setCodStrcStrc(new Long(contratCptIdAttente.getCodStrcStrc()));
            contratCptId.setNumCcptCcpt(contratCptIdAttente.getNumCcptCcpt());
            mandatRecherche.setContratCptId(contratCptId);

            contratCptMandat = (ContratCptMandat)getContratMandatTrt.exec(mandatRecherche);
            List mandats = contratCptMandat.getListeMandat();

            ///suuppression mandats
            if (!mandats.equals(null) && mandats.size() > 0) {
                Iterator iteratorMandat = mandats.iterator();
                for (; iteratorMandat.hasNext(); ) {
                    Mandat mandat = (Mandat)iteratorMandat.next();

                    ///suuppression des mandats operation
                    Set mandatOpers = mandat.getMandatOperations();
                    if (mandatOpers != null && mandatOpers.size() > 0) {
                        Iterator iteratorMandOper = mandatOpers.iterator();
                        for (; iteratorMandOper.hasNext(); ) {
                            MandatOperation mandatOper = 
                                (MandatOperation)iteratorMandOper.next();
                            crudService.remove(mandatOper);
                        }
                    }

                    ///suuppression des mandats personne
                    Set mandatPerss = mandat.getMandatPersonnes();
                    if (mandatPerss != null && mandatPerss.size() > 0) {
                        Iterator iteratorMandPers = mandatPerss.iterator();
                        for (; iteratorMandPers.hasNext(); ) {
                            MandatPersonne mandatPers = 
                                (MandatPersonne)iteratorMandPers.next();
                            ///suuppression des detail_mandat_personne
                            Set detailMandPerss = 
                                mandatPers.getDetailMandatPersonnes();
                            if (detailMandPerss != null && 
                                detailMandPerss.size() > 0) {
                                Iterator iteratorDetailMandPers = 
                                    detailMandPerss.iterator();
                                for (; iteratorDetailMandPers.hasNext(); ) {
                                    DetailMandatPersonne detailMandPers = 
                                        (DetailMandatPersonne)iteratorDetailMandPers.next();
                                    crudService.remove(detailMandPers);
                                }
                            }
                            ///suuppression des mandat_pers_operation_compte
                            Set mandatPersOperComptes = 
                                mandatPers.getMandatPersOperationComptes();
                            if (mandatPersOperComptes != null && 
                                mandatPersOperComptes.size() > 0) {
                                Iterator iteratorMandatPersOperCompte = 
                                    mandatPersOperComptes.iterator();
                                for (; iteratorMandatPersOperCompte.hasNext(); 
                                ) {
                                    MandatPersOperationCompte mandatPersOperCompte = 
                                        (MandatPersOperationCompte)iteratorMandatPersOperCompte.next();
                                    crudService.remove(mandatPersOperCompte);
                                }
                            }
                            crudService.remove(mandatPers);
                        }
                    }
                    
                    ICriteria criteriaTraceMandat = searchEngine.createCriteria();
                    IExpression expression = searchEngine.createExpression();
                    criteriaTraceMandat.add(expression.eq("mandat.numMandMand", mandat.getNumMandMand()));
                    
                    List l2 = searchEngine.find(TraceMandat.class, criteriaTraceMandat);
                                
                    Iterator listeTrace = l2.iterator();
                    if (l2 != null && l2.size() > 0) {
                        for (; listeTrace.hasNext();) {
                            TraceMandat traceMandat = (TraceMandat)listeTrace.next();
                            crudService.remove(traceMandat);
                        }
                    }    
                    
                    crudService.remove(mandat);
                }
            } // Fin if liste des mandats vide
            /*------------------------------- Supression du contrat et ses dérivés --------------------------------------------------*/
            //Suppression de la table Detail_cat_cpt
            ///extraire du detail categorie contrat sur contrat en attente
            ICriteria criteriaDetailContrat = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                    contratCptId.getCodStrcStrc()));
            criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                    contratCptId.getCodPrdPrd()));
            criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                    contratCptId.getNumCcptCcpt()));
            List liste = 
                searchEngine.find(DetailCatCpt.class, criteriaDetailContrat);
            DetailCatCpt detailCatCpt = new DetailCatCpt();
            if (liste != null && liste.size() > 0) {
                detailCatCpt = (DetailCatCpt)liste.get(0);
                crudService.remove(detailCatCpt);
            } 
            
            //Suppression de la table Detail_cat_cpt
            ///extraire du detail etat contrat
            List listeDetailEtatContrat = 
                searchEngine.find(DetailEtatContrat.class, 
                                  criteriaDetailContrat);
            DetailEtatContrat detailEtatContrat = new DetailEtatContrat();
            if (listeDetailEtatContrat != null && 
                listeDetailEtatContrat.size() > 0) {
                detailEtatContrat = 
                        (DetailEtatContrat)listeDetailEtatContrat.get(0);
                crudService.remove(detailEtatContrat);
            } 
            
                //Mise à jour de la table livretEpargne
                ///extraire du detail categorie contrat sur contrat en attente
                ICriteria criteriaLivret = searchEngine.createCriteria();
                IExpression expressionLivret = 
                    searchEngine.createExpression();
                criteriaLivret.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                 contratCptId.getCodStrcStrc()));
                criteriaLivret.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                 contratCptId.getCodPrdPrd()));
                criteriaLivret.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                 contratCptId.getNumCcptCcpt()));
                criteriaLivret.add(expression.eq("codEtatLive", "V"));
                List listeLivret = 
                    searchEngine.find(LivretEpargne.class, criteriaLivret);
                LivretEpargne livretEpargne = new LivretEpargne();
                if (listeLivret != null && listeLivret.size() > 0) {
                    livretEpargne = (LivretEpargne)listeLivret.get(0);
                    ///suppression                    
                    crudService.remove(livretEpargne);
                } 
            
            
            // supression de la trace contrat
             List listeTraceContrat = 
                 searchEngine.find(TraceContrat.class, 
                                   criteriaDetailContrat);
             TraceContrat traceContrat = new TraceContrat();
             if (listeTraceContrat != null && 
                 listeTraceContrat.size() > 0) {
                 traceContrat = (TraceContrat)listeTraceContrat.get(0);
                 crudService.remove(traceContrat);
             } 
            
            //Suppression du contrat en attente
            ContratCpt contratCptAttente = 
                (ContratCpt)searchEngine.get(ContratCpt.class, 
                                             contratCptIdAttente);
            crudService.remove(contratCptAttente);
            /*------------------------------- Suppression du client créé comme entité co-titulaire --------------------------------------------------*/
            if (contratCptAttente.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                ///extraire des menbres cotitulaires
                ICriteria criteriaCotitulaire = searchEngine.createCriteria();
                IExpression expressionCotitulaire = 
                    searchEngine.createExpression();
                criteriaCotitulaire.add(expressionCotitulaire.eq("coTitulaireId.numSeqCli", 
                                                                 contratCptAttente.getClient().getNumSeqPers()));
                List listeMembreCotit = 
                    searchEngine.find(CoTitulaire.class, criteriaCotitulaire);
                Iterator iteratorMembreCotit = listeMembreCotit.iterator();
                if (listeMembreCotit != null && listeMembreCotit.size() > 0) {
                    for (; iteratorMembreCotit.hasNext(); ) {
                        CoTitulaire cotit = 
                            (CoTitulaire)iteratorMembreCotit.next();
                        crudService.remove(cotit);
                    }
                }
            }
            /*------------------------------ suppression du client s'il n'a pas de relation avec la banque ----------------*/
            int nombreContratClient = 
                contratCptAttente.getClient().getContratCpts().size();
            if (nombreContratClient == 0) {

                //Suppression des enregistrement de la table  pers_client
                ICriteria criteriaPersCli = searchEngine.createCriteria();
                IExpression expressionPersCli = 
                    searchEngine.createExpression();
                criteriaPersCli.add(expressionPersCli.eq("persClientId.numSeqCli", 
                                                         contratCptAttente.getClient().getNumSeqPers()));
                List listePersCli = 
                    searchEngine.find(PersClient.class, criteriaPersCli);
                Iterator iteratorPersCli = listePersCli.iterator();
                if (listePersCli != null && listePersCli.size() > 0) {
                    for (; iteratorPersCli.hasNext(); ) {
                        PersClient persCli = 
                            (PersClient)iteratorPersCli.next();
                        crudService.remove(persCli);
                    }
                }
                //Suppression des enregistrement de la table  client                                                     
                Client cli = (Client)contratCptAttente.getClient();
                crudService.remove(cli);
                //Suppression de la personne de type Co_titulaire 
                if (contratCptAttente.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                    Personne persCotit = 
                        (Personne)contratCptAttente.getClient().getPersonne();
                    crudService.remove(persCotit);
                }
            }
            
            // insertion dans la table Contrat_rejete 
            ContratRejete contratRejete = new ContratRejete();
            contratRejete.setDatCreCptr(contratCptAttente.getDatOuvCcpt());
            contratRejete.setDatRejCptr(DateHandler.strToDate(DateHandler.dateJour()));
            contratRejete.setCodStrcCptr(contratCptAttente.getContratCptId().getCodStrcStrc());
            contratRejete.setCodPrdCptr(contratCptAttente.getContratCptId().getCodPrdPrd());
            contratRejete.setNumCcptCptr(contratCptAttente.getContratCptId().getNumCcptCcpt());            
            contratRejete.setLibIntiCptr(contratCptAttente.getNomIntiCcpt());
            contratRejete.setNumMatrCptr(Long.valueOf(traceContrat.getPersonnel().getNumMatrUser()));
            crudService.create(contratRejete);
           // Fin controle fin de journee
            }else{
                        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                        StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                        erreur.setCode("100");
                        erreur.setDescription(text.toString());
                        erreur.setKey("InsertDemandeCheque");
                        contratCptId.addError(erreur);        
                    }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans RejeterContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("RejeterContrat");
            contratCptId.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + contratCptIdAttente.getCodStrcStrc() + ">>. Exception : ",e);              
            throw new RuntimeException(e);             
        }
        return contratCptId;
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("201");    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ContratCptId contratCptIdAttente = (ContratCptId)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(contratCptIdAttente.getCodStrcStrc());
        return structureDomaine;
    }
}

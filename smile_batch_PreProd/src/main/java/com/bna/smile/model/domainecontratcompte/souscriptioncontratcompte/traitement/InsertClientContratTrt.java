package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.LivretEpargne;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.PersClientId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.SeqProduitAgence;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailEtatContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class InsertClientContratTrt extends Traitement {
    public InsertClientContratTrt() {
    }

    /**
     * Cette  methode permet d'inserer un contrat donné ainsi que
     * le client rattaché s'il n'existe pas.
     * @param (contratCpt)ValueObject
     * @return contratCpt : l'objet contrat inseré
     * @author : El arbi hassine 
     */
    public IValueObject perform(IValueObject vo) {
        
        CRUDservice crudService = (CRUDservice)Context.getInstance().getSpringContext().getBean("crudservice");
        PersonneService personneService = 
            (PersonneService)Context.getInstance().getSpringContext().getBean("personneService");
        SouscriptionContratCompteService souscriptionContratCompteService = 
            (SouscriptionContratCompteService)Context.getInstance().getSpringContext().getBean("souscriptionContratCompteService");
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;
        PersonneDAO  personneDAO = (PersonneDAO)Context.getInstance().getSpringContext().getBean("personneDAO");        
        try {
          if(this.checkClotureJournee()){
            this.setCroFlag(false);
            /* Client inexistant */
            InsertClientTrt insertClientTrt = new InsertClientTrt();
            paramInsertContrat.getContratCpt().setClient((Client)(insertClientTrt.exec(paramInsertContrat.getContratCpt().getClient())));


            /* Rechercher la sequence relative au produit et à la structure donnée */
            criteria.add(expression.eq("seqProduitAgenceId.codPrdPrd", 
                                       paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd()));
            criteria.add(expression.eq("seqProduitAgenceId.codStrcStrc", 
                                       paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc()));
            List l = searchEngine.find(SeqProduitAgence.class, criteria);

            if ((l != null && l.size() > 0) || paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_CHEQUE_PERSONNEL)) {
              if(!paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_CHEQUE_PERSONNEL)){
                SeqProduitAgence seqProduitAgence = (SeqProduitAgence)l.get(0); 
                /* Incrementer la sequence */  
                
                long valeur = seqProduitAgence.getNumVseqSpa().intValue() + 1;
                // verifier si le contrat existe déja avec ce numéro, si oui alors incrementer le numéro, sinon le prendre... 
                while(personneDAO.verifExistContratCompte(paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd(),paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc(),valeur) ){
                    valeur++;                    
                }
                seqProduitAgence.setNumVseqSpa(Long.valueOf((valeur)));
                /* MAJ de la sequence */
                crudService.update(seqProduitAgence);
                /* Inserer le N° du ContratCpt*/
                paramInsertContrat.getContratCpt().getContratCptId().setNumCcptCcpt(new Long(seqProduitAgence.getNumVseqSpa().intValue()));
              }
                /* insertion du ContratCpt dans la BD */
                crudService.create(paramInsertContrat.getContratCpt());


                if (paramInsertContrat.getPersonneTuteur() != null) {
                    // si tuteur existe
                    if (paramInsertContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
                        // si le client est une pesronne mineur.   
                        Personne personneTuteur = new Personne();
                        personneTuteur = paramInsertContrat.getPersonneTuteur();
                        
                        // verifier l'existance de la relation entre le tuteur et le mineur.
                        boolean existPersClient = 
                            personneService.verifierExistancePersClient(personneTuteur.getNumSeqPers(), 
                                                                        paramInsertContrat.getContratCpt().getClient().getNumSeqPers(), 
                                                                        Constants.COD_QUALIT_TUTEUR);
                        if (!existPersClient) {
                            PersClientId persClientId = new PersClientId();
                            PersClient persClient = new PersClient();
                            persClientId.setNumSeqCli(paramInsertContrat.getContratCpt().getClient().getNumSeqPers());
                            persClientId.setNumSeqPers(personneTuteur.getNumSeqPers());
                            persClientId.setCodQualQual(Constants.COD_QUALIT_TUTEUR);
                            persClient.setPersClientId(persClientId);
                            PersClient persClientRetour = 
                                (PersClient)souscriptionContratCompteService.insertPersonneClient(persClient);
                        }
                    }
                }

                //##################traitement du cas cotitulaire  ######################################################

                if (paramInsertContrat.getListCotitulaire().size() > 0 || 
                    paramInsertContrat.getListCotitulaire() != null) {
                    for (Iterator it = 
                         paramInsertContrat.getListCotitulaire().iterator(); 
                         it.hasNext(); ) {
                        CoTitulaire coTitulaire = (CoTitulaire)it.next();
                        if (coTitulaire.getCoTitulaireId().getNumSeqPers() != 
                            null) {
                            coTitulaire.getCoTitulaireId().setNumSeqCli(paramInsertContrat.getContratCpt().getClient().getNumSeqPers());
                            coTitulaire.setClient(paramInsertContrat.getContratCpt().getClient());
                            /* insertion des cotitulaires dans la table cotitulaire dans la BD */
                            crudService.create(coTitulaire);
                        }
                    }
                }
                //##################Fin traitement du cas cotitulaire######################################################

                /* ##################création Detail_etat_contrat ###################################*/


                ParamDetailEtatContrat paramDetailEtatContrat = 
                    new ParamDetailEtatContrat();
                paramDetailEtatContrat.setMotifEtat(paramInsertContrat.getMotifEtat());
                paramDetailEtatContrat.setContratCpt(paramInsertContrat.getContratCpt());
                DetailEtatContrat detailEtatContrat = 
                    (DetailEtatContrat)souscriptionContratCompteService.insertDetailEtatContrat(paramDetailEtatContrat);

                /*##################    ################## ################## Fin de creation du Detail_etat_contrat ################## ##################*/

                /* ##################création Detail_CAT_CPT : detail catégorie contrat en cas de produit epargne#######*/
                if (paramInsertContrat.getCategorie().getCategorieId() != 
                    null) {
                    DetailCatCpt detailCatCpt = new DetailCatCpt();
                    ParamDetailCatCpt paramDetailCatCpt = 
                        new ParamDetailCatCpt();
                    paramDetailCatCpt.setCategorie(paramInsertContrat.getCategorie());
                    paramDetailCatCpt.setContratCpt(paramInsertContrat.getContratCpt());
                    paramDetailCatCpt.setTypeVersementEpargne(paramInsertContrat.getTypeVersementEpargne());
                    detailCatCpt = 
                            (DetailCatCpt)souscriptionContratCompteService.insertDetailCatContrat(paramDetailCatCpt);
                }
                /*##################    ################## ################## Fin de creation du Detail_CAT_CPT ################## ##################*/
                /* ##################création LivretEpargne : en cas de produit epargne Etude 121/ 177  #######*/
                if (paramInsertContrat.getContratCpt().getNumLivrCcpt() != 
                    null || 
                    paramInsertContrat.getContratCpt().getNumLivrCcpt() != 
                    "") {
                    if (paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_EPS) || 
                        paramInsertContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)) {
                        InsertLivretEpargneTrt insertLivretEpargneTrt = 
                            new InsertLivretEpargneTrt();
                        LivretEpargne livretEpargne = new LivretEpargne();
                        livretEpargne.setNumLivrLive(paramInsertContrat.getContratCpt().getNumLivrCcpt());
                        livretEpargne.setContratCpt(paramInsertContrat.getContratCpt());
                        livretEpargne.setDatDebLive(new Date());
                        livretEpargne.setCodEtatLive("V");
                        livretEpargne = (LivretEpargne)insertLivretEpargneTrt.exec(livretEpargne);                        
                        
                    }
                }

                /*##################    ################## ################## Fin de creation du Detail_CAT_CPT ################## ##################*/
                 /* ##################  Insertion dans la table Trace_contrat  #######*/    
                  if(!paramInsertContrat.getContratCpt().hasError()){
                  TraceContrat traceContrat = new TraceContrat();                  
                  Tache tache = new Tache();
                  TacheId tacheId = new TacheId();
                  tacheId.setCodOperOper(Constants.OPER_DEMANDE_SOUSC_COMPTE);
                  tacheId.setCodTachTach(Constants.TACHE_DEMANDE_SOUSC_COMPTE);
                  tache.setTacheId(tacheId);                 
                  traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_ATT);                
                  traceContrat.setPersonnel(paramInsertContrat.getPersonnel());
                  traceContrat.setContratCpt(paramInsertContrat.getContratCpt());                
                  traceContrat.setTache(tache);
                  InsertTraceContratTrt insertTraceContratTrt = 
                      new InsertTraceContratTrt();
                  TraceContrat traceContratRetour = 
                      (TraceContrat)insertTraceContratTrt.exec(traceContrat);
                  }
                
                
                  /*##################    ################## ################## Fin de l'insertion trace_contrat ################## ##################*/
                   
                   
                   }else{
                               com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                               StringBuffer text = new StringBuffer("pas de séquence disponible pour ce produit, Veuillez contacter l'administrateur SVP ...");            
                               erreur.setCode("100");
                               erreur.setDescription(text.toString());
                               erreur.setKey("sequence");
                               paramInsertContrat.getContratCpt().addError(erreur);        
                           }
          // Fin contrôle fin de journée  
           }else{
                       com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                       StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                       erreur.setCode("100");
                       erreur.setDescription(text.toString());
                       erreur.setKey("InsertDemandeCheque");
                       paramInsertContrat.getContratCpt().addError(erreur);        
                   }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans InsertClientContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("InsertClientContrat");
            paramInsertContrat.getContratCpt().addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" +paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);   
            throw new RuntimeException(e);  
        }
        return paramInsertContrat.getContratCpt();
    }
 
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("101");    
    }
 
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramInsertContrat.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
 
 
}

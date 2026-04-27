package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.DetailEtatContrat;
import com.bna.commun.model.DetailEtatContratId;
import com.bna.commun.model.DetailSeqProduit;
import com.bna.commun.model.DetailSeqProduitId;
import com.bna.commun.model.LivretEpargne;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.SeqProduitAgence;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.PersonneDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Méhode de validition d'un contrat en attente
 * @param contratAttente
 * @return contratValide :nouveau numéro du contrat validé
 */
public class ValiderContratTrt extends Traitement{
    

    public ValiderContratTrt() {
    }
    
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        PersonneDAO  personneDAO = (PersonneDAO)Context.getInstance().getSpringContext().getBean("personneDAO");     
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ISearchEngine searchEngine=(ISearchEngine)context.getBean("searchEngine");
        ContratCptId contratCptIdAttente = (ContratCptId)vo;
        ContratCpt contratCptValide = new ContratCpt();
        ContratCpt contratCptAttente = (ContratCpt)searchEngine.get(ContratCpt.class, contratCptIdAttente);
        try {
            this.setCroFlag(false);
            Long numContratAttente = contratCptIdAttente.getNumCcptCcpt();
            if (contratCptIdAttente.getCodPrdPrd().equals(Constants.COD_COMPTE_VERT) || contratCptIdAttente.getCodPrdPrd().equals(Constants.COD_COMPTE_INTERNE_VIR) || contratCptIdAttente.getCodPrdPrd().equals(Constants.COD_COMPTE_CHEQUE_PERSONNEL) 
                || contratCptIdAttente.getCodPrdPrd().equals(Constants.COD_COMPTE_ECONOMIE_SUR_SALAIRE) || contratCptIdAttente.getCodPrdPrd().equals(Constants.COD_COMPTE_ALLOC_TOURISTIQUE)  ) {
                contratCptAttente.setCodEtatCcpt("V");
                crudService.update(contratCptAttente);
                return contratCptAttente;
            } else {
                Long newNumContrat = 
                    getNumeroContratValide(contratCptValide, contratCptIdAttente.getCodStrcStrc(), 
                                           contratCptIdAttente.getCodPrdPrd());

                if (newNumContrat != null && !contratCptValide.hasError()) {
                    //insertion nouveau contrat   
                    
                    // verifier si le contrat n'est pas encore validé par une autre personne...
                    if(personneDAO.verifExistContratCompte(contratCptIdAttente.getCodPrdPrd(),contratCptIdAttente.getCodStrcStrc(),contratCptIdAttente.getNumCcptCcpt())){  
                    // copy du contratAttente vers une autre zone memoire                   
                    contratCptValide = (ContratCpt)contratCptAttente.clone();
                    // copy du contratIdAttente vers une autre zone memoire
                    ContratCptId contratCptIdValide = new ContratCptId();
                    contratCptIdValide = 
                            (ContratCptId)contratCptIdAttente.clone();

                    contratCptValide.setContratCptId(contratCptIdValide);
                    contratCptIdValide.setNumCcptCcpt(newNumContrat);
                    contratCptValide.setCodEtatCcpt("V");
                    crudService.create(contratCptValide);

                    contratCptValide.setCompagnieNavigations(null);
                    contratCptValide.setDetailEtatContrats(null);
                    contratCptValide.setDetailCatCpts(null);
                    contratCptValide.setContratCpts(null);
                    contratCptValide.setOppositionMoyenPaiements(null);
                    contratCptValide.setBlocages(null);
                    contratCptValide.setChequiers(null);
                    contratCptValide.setDemandeCheques(null);
                    contratCptValide.setOperationComptes(null);
                    contratCptValide.setCarteBancaires(null);
                    contratCptValide.setMouvementChanges(null);
                    contratCptValide.setDemandeCartes(null);
                    contratCptValide.setInciCcpts(null);
                    contratCptValide.setSousDelegataires(null);
                    contratCptValide.setMandats(null);
                    contratCptValide.setLivretEpargne(null);


                    //Mise à jour des mandats --> sur le nouveau contrat
                    ///extraire mandats valides sur le contrat en attente
                    GetContratMandatTrt getContratMandatTrt = new GetContratMandatTrt();
                    MandatRecherche mandatRecherche = new MandatRecherche();
                    ContratCptMandat contratCptMandat = new ContratCptMandat();

                    mandatRecherche.setContratCptId(contratCptIdAttente);
                    
                    contratCptMandat = (ContratCptMandat)getContratMandatTrt.exec(mandatRecherche);                  
                    
                    if(!contratCptMandat.hasError()){
                    List mandats = contratCptMandat.getListeMandat();
                    ///modification mandats                    
                    if (mandats != null && mandats.size() > 0) {
                        Iterator iterator = mandats.iterator();
                        for (; iterator.hasNext(); ) {
                            Mandat mandat = (Mandat)iterator.next();
                            mandat.setContratCpt(contratCptValide);
                            crudService.update(mandat);
                            
                        }
                    }
                    }else{
                        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                        StringBuffer text = 
                            new StringBuffer("Erreur dans ValiderContratTrt : ");                        
                        erreur.setCode("100");
                        erreur.setDescription(text.toString());
                        erreur.setKey("ValiderContrat");
                        contratCptValide.addError(erreur);
                    }
                    //Mise à jour de la table Detail_cat_cpt
                    ///extraire du detail categorie contrat sur contrat en attente
                    ICriteria criteriaDetailContrat = 
                        searchEngine.createCriteria();
                    IExpression expression = searchEngine.createExpression();
                    criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                            contratCptIdAttente.getCodStrcStrc()));
                    criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                            contratCptIdAttente.getCodPrdPrd()));
                    criteriaDetailContrat.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                            numContratAttente));
                    List liste = 
                        searchEngine.find(DetailCatCpt.class, criteriaDetailContrat);
                    DetailCatCpt detailCatCpt = new DetailCatCpt();
                    if (liste != null && liste.size() > 0) {
                        detailCatCpt = (DetailCatCpt)liste.get(0);
                        ///maj direct detail
                        detailCatCpt.setContratCpt(contratCptValide);
                        crudService.update(detailCatCpt);
                    } 
                    
                     
                    
                    //Mise à jour de la table Detail_cat_cpt
                    ///extraire du detail categorie contrat
                    List listeDetailEtatContrat = 
                        searchEngine.find(DetailEtatContrat.class, 
                                          criteriaDetailContrat);
                    DetailEtatContrat detailEtatContrat = 
                        new DetailEtatContrat();
                    if (listeDetailEtatContrat != null && 
                        listeDetailEtatContrat.size() > 0) {
                        detailEtatContrat = 
                                (DetailEtatContrat)listeDetailEtatContrat.get(0);
                        ///maj detail
                        crudService.remove(detailEtatContrat);
                        DetailEtatContrat detailEtatContratNew = 
                            new DetailEtatContrat();
                        //BeanUtils.copyProperties(detailEtatContratNew,detailEtatContrat);  
                        detailEtatContratNew = 
                                (DetailEtatContrat)detailEtatContrat.clone();

                        DetailEtatContratId detailEtatContratIdNew = 
                            new DetailEtatContratId();
                        detailEtatContratIdNew = 
                                (DetailEtatContratId)detailEtatContrat.getDetailEtatContratId().clone();
                        detailEtatContratIdNew.setNumCcptCcpt(newNumContrat);

                        detailEtatContratNew.setDetailEtatContratId(detailEtatContratIdNew);
                        crudService.create(detailEtatContratNew);
                    } 


                    //Mise à jour de la table livretEpargne
                    ///extraire du detail categorie contrat sur contrat en attente
                    ICriteria criteriaLivret = searchEngine.createCriteria();
                    IExpression expressionLivret = 
                        searchEngine.createExpression();
                    criteriaLivret.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                     contratCptIdAttente.getCodStrcStrc()));
                    criteriaLivret.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                     contratCptIdAttente.getCodPrdPrd()));
                    criteriaLivret.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                     numContratAttente));
                    criteriaLivret.add(expression.eq("codEtatLive", "V"));
                    List listeLivret = 
                        searchEngine.find(LivretEpargne.class, criteriaLivret);
                    LivretEpargne livretEpargne = new LivretEpargne();
                    if (listeLivret != null && listeLivret.size() > 0) {
                        livretEpargne = (LivretEpargne)listeLivret.get(0);
                        ///maj direct detail
                        livretEpargne.setContratCpt(contratCptValide);
                        crudService.update(livretEpargne);
                    }

                    
                    //Mise à jour de la table Trace_contrat
                    ///extraire le tracecontrat sur le contrat en attente
                    ICriteria criteriaTrace = searchEngine.createCriteria();
                    IExpression expressionTrace = 
                        searchEngine.createExpression();
                    criteriaTrace.add(expression.eq("contratCpt.contratCptId.codStrcStrc", 
                                                     contratCptIdAttente.getCodStrcStrc()));
                    criteriaTrace.add(expression.eq("contratCpt.contratCptId.codPrdPrd", 
                                                     contratCptIdAttente.getCodPrdPrd()));
                    criteriaTrace.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", 
                                                     numContratAttente));
                    criteriaTrace.add(expression.eq("codEtatTrc", "A"));
                    List listeTrace = 
                        searchEngine.find(TraceContrat.class, criteriaTrace);
                    TraceContrat traceContrat = new TraceContrat();
                    if (listeTrace != null && listeTrace.size() > 0) {
                        traceContrat = (TraceContrat)listeTrace.get(0);
                        ///maj direct detail
                        traceContrat.setContratCpt(contratCptValide);
                        crudService.update(traceContrat);
                    } 
                    //Suppression du contrat en attentet
                    crudService.remove(contratCptAttente); 
                    }else{
                        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                        StringBuffer text = new StringBuffer("Ce contrat compte est déja validé par un autre utilisateur...");            
                        erreur.setCode("100");
                        erreur.setDescription(text.toString());
                        erreur.setKey("ValiderContratTrt");
                        contratCptValide.addError(erreur);
                    }
                 } 
                
            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ValiderContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("ValiderContrat");
            contratCptValide.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + contratCptValide.getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);      
            throw new RuntimeException(e);  
            
        }
        return contratCptValide;

    }

    /**
     * Méhode qui retourne le nouveau numéro d'un contrat suite à une validation
     * @param codAgence,codProduit
     * @return nouveau numéro du contrat validé
     */
    public Long getNumeroContratValide(ContratCpt contratCptValide, 
                                       Long codAgence, 
                                       Long codProduit) throws Exception {

        
        try{
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        Context context = ContextHandler.getContext();
        PersonneDAO  personneDAO = (PersonneDAO)Context.getInstance().getSpringContext().getBean("personneDAO");   
        CRUDservice crudService  = (CRUDservice)Context.getInstance().getSpringContext().getBean("crudservice");            
        ICriteria criteriaSeq  = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        criteriaSeq.add(expression.eq("seqProduitAgenceId.codStrcStrc", 
                                      codAgence));
        criteriaSeq.add(expression.eq("seqProduitAgenceId.codPrdPrd", 
                                      codProduit));
        List liste = searchEngine.find(SeqProduitAgence.class, criteriaSeq);
        /*si la sequence existe*/
        SeqProduitAgence sequence = new SeqProduitAgence();
        if (liste != null && liste.size() > 0) {
            sequence = (SeqProduitAgence)liste.get(0);
        } else {
            System.out.println("Séquence non disponible pour cette agence sur ce produit contrat compte!");
            return null;
        }

        Long t = 
            getNextSequence(contratCptValide, codProduit, sequence.getNumSeqSpa());

        if (!contratCptValide.hasError()) {
                  
            while(personneDAO.verifExistContratCompte(codProduit,codAgence,t) ){                
                t = getNextSequence(contratCptValide, codProduit, t);
            }
            sequence.setNumSeqSpa(t);
            crudService.update(sequence);
            return (sequence.getNumSeqSpa());
        } else
            return null;
        
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" + codAgence + ">>. Exception dans ValiderContratTrt / Methode : getNumeroContratValide:  ",e);  
               throw new RuntimeException(e);               
        }   

    }

    /**
     * Méhode qui retourne le prochain numero valide d'un produit  pour la mise à jour de la table SeqProduitAgence, 
     * @param codAgence,codProduit, numero en cours
     * @return nouveau numéro du contrat validé
     */
    public Long getNextSequence(ContratCpt contratCptValide, Long codProduit, 
                                Long numeroContratActuel) throws Exception {

        Context context = ContextHandler.getContext();
        Long valeurSeqRetour = Long.valueOf(0);
        CRUDservice crudService = (CRUDservice)Context.getInstance().getSpringContext().getBean("crudservice");
       
    try{      
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        DetailSeqProduit detailSeqProduit = new DetailSeqProduit();
        ICriteria criteriaSeq = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        criteriaSeq.add(expression.eq("detailSeqProduitId.codPrdPrd", 
                                      codProduit));
        criteriaSeq.add(expression.le("numVminDsqp", numeroContratActuel));
        criteriaSeq.add(expression.ge("numVmaxDsqp", numeroContratActuel));


        List liste = searchEngine.find(DetailSeqProduit.class, criteriaSeq);


        DetailSeqProduit nextDetail = new DetailSeqProduit();
        DetailSeqProduitId detailSeqProduitId = new DetailSeqProduitId();

        if (liste != null && liste.size() > 0) {
            detailSeqProduit = (DetailSeqProduit)liste.get(0);
            //------ compraison, entre le numero actuel et la valeur max de la plage 

            if (numeroContratActuel.equals(detailSeqProduit.getNumVmaxDsqp())) {
                //---------------- chercher la plage suivante et retourner le min de la plage
                detailSeqProduitId.setCodPrdPrd(codProduit);
                detailSeqProduitId.setNumMrgDsqp(detailSeqProduit.getDetailSeqProduitId().getNumMrgDsqp() + 
                                                 1);
                nextDetail = 
                        (DetailSeqProduit)searchEngine.get(DetailSeqProduit.class, 
                                                           detailSeqProduitId);
                if (nextDetail.getDetailSeqProduitId() != null) {

                    valeurSeqRetour = nextDetail.getNumVminDsqp();

                } else {
                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Erreur dans ValiderContratTrt/getNumeroProchain : ");
                    text.append("Aucune plage trouvée pour le prochain numero de = " + 
                                numeroContratActuel + " pour le produit : " + codProduit );
                    erreur.setCode("getNumeroProchain");
                    erreur.setDescription(text.toString());
                    erreur.setKey("Metier");
                    contratCptValide.addError(erreur);

                }
            } else {
                //--------- faire une incrementation de la valeur courante
                valeurSeqRetour = numeroContratActuel + 1;
            }

        } else { //----------- pas de plage disponible pour ce numero actuel            
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ValiderContratTrt/getNumeroProchain : ");
            text.append("Aucune plage trouvée pour le numero = " + 
                        numeroContratActuel + " pour le produit : " + codProduit );
            erreur.setCode("getNumeroProchain");
            erreur.setDescription(text.toString());
            erreur.setKey("Metier");
            contratCptValide.addError(erreur);


        }
        
        } catch (Exception e) {
               logger.error("Exception dans ValiderContratTrt /Methode : getNextSequence: ",e);  
               throw new RuntimeException(e);               
        }  
        
        return valeurSeqRetour;

    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

   
}

package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.List;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.PersClient;
import com.bna.commun.model.PersClientId;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecommun.traitement.InsertPersonneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamInsertContrat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.service.SouscriptionContratCompteService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class MAJContratClientTransfertEpergne extends Traitement{
  
    public MAJContratClientTransfertEpergne() {
    }
    
    /**
     * Cette  methode permet de transferer un contrat (epargne) donné d'une personne
     * à une autre 
     * ainsi que la création de cette nouvelle personne si elle n'existe pas, 
     * la création du client rattaché s'il n'existe pas 
     * et la création de la table pers_client dans le cas d'un titulaire
     * @param (ParamInsertContrat)ValueObject  (Contrat.ContratCptId,Contrat.Client.NumSeqPers,Contrat.Client.Personne.NumSeqPers,Tuteur dans le cas d'un mineur)
     * @return contratCpt : l'objet contrat inseré
     * @autor Youssef BOUSSEN & Hatem KRIAA
     * @date le 15/06/2007
     */
    public IValueObject perform(IValueObject vo) {

        ContratCpt cpt =new ContratCpt();
    try{
        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        PersonneService personneService =  (PersonneService)context.getBean("personneService");
        SouscriptionContratCompteService souscriptionContratCompteService = (SouscriptionContratCompteService)context.getBean("souscriptionContratCompteService");
        ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();

        ParamInsertContrat paramInsertContrat = (ParamInsertContrat)vo;


        /* Charger le contrat */
         cpt = (ContratCpt)searchEngine.get(ContratCpt.class, paramInsertContrat.getContratCpt().getContratCptId());
        /* Charger client */
         Client client=new Client();
        Personne personne=new Personne();
        
        /* Charger personne */
        if (paramInsertContrat.getContratCpt().getClient().getPersonne().getNumSeqPers()!=null){
          personne=(Personne)searchEngine.get(Personne.class, paramInsertContrat.getContratCpt().getClient().getPersonne().getNumSeqPers());
        }else{
            criteria.add(expression.eq("numPcePers", paramInsertContrat.getContratCpt().getClient().getPersonne().getNumPcePers()));
            criteria.add(expression.eq("typePiece.codTpceTpce", paramInsertContrat.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()));

            List listPersonne = searchEngine.find(Personne.class, criteria);
            /* si la personne existe */
            if (listPersonne != null && listPersonne.size() > 0) {
                personne = (Personne)listPersonne.get(0);
            }
        }
         if (personne.getNumSeqPers()==null){/* personne inexistante */
            InsertPersonneTrt insertPersonneTrt= new InsertPersonneTrt();
            personne=(Personne)insertPersonneTrt.exec(paramInsertContrat.getContratCpt().getClient().getPersonne());
         }
         
            client=(Client)searchEngine.get(Client.class, personne.getNumSeqPers());
        
        if (client==null){/* Client inexistant */
             cpt.getClient().setPersonne(personne); 
             InsertClientTrt insertClientTrt = new InsertClientTrt();
             cpt.setClient((Client)(insertClientTrt.exec(cpt.getClient())));/// création d'un nouveau client
        }else {
            cpt.setClient(client);
        }
 
   
           /* MAJ du lien ContratCpt-Client dans la BD */
            crudService.update(cpt);
            paramInsertContrat.setContratCpt(cpt);


            if (paramInsertContrat.getPersonneTuteur() != null) {// si tuteur existe
                    /// si le client est une pesronne mineur. 
                if (cpt.getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
                      
                    Personne personneTuteur = new Personne();
                    personneTuteur = paramInsertContrat.getPersonneTuteur();
               
                    /// verifier l'existance de la relation entre le tuteur et le mineur.
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

        return (cpt);
        }
           catch (Exception e) {
              com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
              StringBuffer text = 
                  new StringBuffer("Erreur dans MAJContratClientTransfertEpergne : ");
              text.append(e.toString());
              erreur.setCode("200");
              erreur.setDescription(text.toString());
              erreur.setKey("MAJContratClientTransfertEpergne");
              cpt.addError(erreur);
              logger.error(" *** Erreur lors de  MAJContratClientTransfertEpergne", e);
              return (cpt);
          }

    }
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }

}

    


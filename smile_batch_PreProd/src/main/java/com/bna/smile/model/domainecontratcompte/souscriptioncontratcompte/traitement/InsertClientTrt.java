package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.Client;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.service.ClientService;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class InsertClientTrt extends Traitement{
   
    

    public InsertClientTrt() {
    }

    /**
     * méthode d'insertion  d'une nouveau client en prend en argument unVo client
     * et retourne un valueObject AjoutPersonneClientContratVo
     *
     * @param vo Client
     * @return vo AjoutPersonneClientContratVo
     */
    public IValueObject perform(IValueObject vo) {
        Context context = ContextHandler.getContext();
        //Client client = (Client)vo;
        Personne personne = new Personne();
        PersonneService personneService = 
            (PersonneService)context.getBean("personneService");
        ClientService clientService = 
            (ClientService)context.getBean("clientService");
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

        Long typePiece;
        String numeroPiece;
        String textErreur = new String();
        this.setCroFlag(false);

        // Verifier l'existance des données de la personnne 
    try{
        

        typePiece = ((Client)vo).getPersonne().getTypePiece().getCodTpceTpce();
        numeroPiece =  ((Client)vo).getPersonne().getNumPcePers();
        boolean testPersonne = 
            personneService.verifierExistancePersonne(typePiece, numeroPiece);

        /* si la personne existe : retourner l'objet Personne de la base*/
        if (testPersonne) {
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(typePiece);
            personneStrc.setNumPcePers(numeroPiece);

            personne = (Personne)personneService.getPersonne(personneStrc);
             ((Client)vo).setPersonne(personne);
        } else { /* Insertion de la Personne */
            personneService.insertPersonne( ((Client)vo).getPersonne());
        }

        boolean testClient = 
            clientService.verifierExistanceClient( ((Client)vo).getPersonne().getNumSeqPers());
            
        if (!testClient){
            /* L'insertion du client */
             ((Client)vo).setNumSeqPers( ((Client)vo).getPersonne().getNumSeqPers());
             ((Client)vo).setDatRelClt(new Date());
             ((Client)vo).setCodEtatClt(Constants.COD_ETAT_CLT_ATT);
             crudService.create( ((Client)vo));
        }else{
            crudService.update( ((Client)vo));
        }


        
   } catch (Exception e) {
                    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Erreur dans InsertClientTrt : ");
                    text.append(e.toString());
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("InsertClient");
                    vo.addError(erreur);
                    logger.error("Exception : ",e);   
                   throw new RuntimeException(e);                      
     }   
        return (vo);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}

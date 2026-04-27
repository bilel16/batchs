package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Client;
import com.bna.commun.model.Personne;
import com.bna.commun.model.TypePers;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.service.ClientService;
import com.bna.smile.model.domainecommun.service.PersonneService;
import com.bna.smile.model.domainecommun.traitement.VerifierExistancePersonneTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class InsertCltTrt extends Traitement{
   

    public InsertCltTrt() {
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
        Personne personne = new Personne();
        PersonneService personneService = (PersonneService)context.getBean("personneService");
        ClientService clientService = (ClientService)context.getBean("clientService");
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

        Long typePiece;
        String numeroPiece;
        this.setCroFlag(false);

        // Verifier l'existance des données de la personnne 
    try{

        typePiece = ((Client)vo).getPersonne().getTypePiece().getCodTpceTpce();
        numeroPiece =  ((Client)vo).getPersonne().getNumPcePers();

        VerifierExistancePersonneTrt verifierExistancePersonneTrt = new VerifierExistancePersonneTrt();
        PrimitiveVO  primitiveVO = (PrimitiveVO) verifierExistancePersonneTrt.exec(((Client)vo).getPersonne());

        boolean testPersonne = primitiveVO.isVBool();
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

        boolean testClient = clientService.verifierExistanceClient(personne.getNumSeqPers());
        if (!testClient){
            /* L'insertion du client */
             ((Client)vo).setNumSeqPers (personne.getNumSeqPers());
             ((Client)vo).setDatRelClt(new Date());
             ((Client)vo).setCodEtatClt(Constants.COD_ETAT_CLT_ATT);
             
             TypePers typePers= new TypePers();
             typePers.setCodTperTper(Constants.DEFAULT_COD_TPER_TPER);
            ((Client)vo).setTypePers(typePers);
               
                HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                hibernateTemplate.evict(vo);                
                hibernateTemplate.evict(personne);
                crudService.create(((Client)vo));

             
        }else{
             crudService.update( ((Client)vo));
        }
       
   } catch (Exception e) {
                    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                    StringBuffer text = new StringBuffer("Erreur dans InsertCltTrt : ");
                    text.append(e.toString());
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("InsertClt");                    
                    vo.addError(erreur);
                    throw new RuntimeException(e);
     } 
        return ( vo);
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
}

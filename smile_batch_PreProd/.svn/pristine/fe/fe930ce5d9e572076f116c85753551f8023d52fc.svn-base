package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.ModificationDonnees;
import com.bna.commun.model.ModificationDonneesId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePers;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneCptTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonnelTrt;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationTypeCompteVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet de faire les corrections des données client
 * @author Mdimagh
 * @since 10/10/2008
 */
public class ModificationTypeCompteTrt extends Traitement {
    
    Logger logger = Logger.getLogger(ModifierDonneesClientTrt.class);
       
        public ModificationTypeCompteTrt() {
        }
       

    public IValueObject perform (IValueObject vo) {
      
        ParamModificationTypeCompteVo ParamModificationVo = 
            (ParamModificationTypeCompteVo)vo;
        boolean testExistClient = true;
        Context context = ContextHandler.getContext();
        try {
            logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Evénement : entrée Trt");

          if(this.checkClotureJournee()){
            StringBuffer texteModification = new StringBuffer("");
            
          
            
            //---------------------------------------------------------------------//
            //---------- Preparer la table Modification ---------------//
            //---------------------------------------------------------------------//

            ModificationDonnees modificationDonnees = 
                new ModificationDonnees();
            TypeModification typeModification = new TypeModification();
            typeModification.setCodCodModf(Long.valueOf(26));
            
            modificationDonnees.setTypeModification(typeModification);
           
            GetPersonnelTrt getPersonnelTrt = new GetPersonnelTrt();
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(ParamModificationVo.getMatricule().toString());
            personnel = (Personnel)getPersonnelTrt.exec(personnel);
            modificationDonnees.setPersonnel(personnel);
            
            Structure structure = new Structure();
            structure.setCodStrcStrc(Long.valueOf(ParamModificationVo.getCodeStructure()));
            modificationDonnees.setStructure(structure);

            ModificationDonneesId modificationDonneesId = 
                new ModificationDonneesId();
            modificationDonneesId.setDatModModd(new Date());

            //--------------------- Fin Preparation ---------------------------------//
            
            //-------------------------------------------------------------//
            //------------- sauvgarde des données anciennes  --------------//
            //-------------------------------------------------------------//
    
             GetContratCptByIdCmd getContratCpt = new GetContratCptByIdCmd();
             ContratCpt contrat = new ContratCpt();
             contrat.setContratCptId(ParamModificationVo.getContratCpt().getContratCptId());
             contrat = (ContratCpt) getContratCpt.execute(contrat);
    
    
            if (contrat != null ){
                   texteModification.append("# Numéro de compte : ");
                   texteModification.append(StrHandler.lpad(contrat.getContratCptId().getCodStrcStrc().toString(),'0',3));
                   texteModification.append(StrHandler.lpad(contrat.getContratCptId().getCodPrdPrd().toString(),'0',4 ));
                   texteModification.append(StrHandler.lpad(contrat.getContratCptId().getNumCcptCcpt().toString(),'0',6));
                   texteModification.append("# Numéro de l'entité : ");
                   texteModification.append(contrat.getClient().getNumSeqPers());
                   texteModification.append(" #  Intitulé du compte : ");
                   if(contrat.getNomIntiCcpt()  !=null){
                     texteModification.append(contrat.getNomIntiCcpt());
                   }
                   texteModification.append("#Client :");
                   texteModification.append(contrat.getClient().getPersonne().getNomNomPers());
                   texteModification.append(" ");
                   texteModification.append(contrat.getClient().getPersonne().getNomPrnPers());
                 
                   contrat.setNomIntiCcpt(ParamModificationVo.getContratCpt().getNomIntiCcpt());
                   
                   
                   //-------------------------------------------------//
                   //------ Verifier si la persone est client --------//
                   GetPersonneCptTrt getPersonneCpt = new GetPersonneCptTrt();
                   PersonneStrc personneStrc = new PersonneStrc();
                   personneStrc.setCodTpceTpce(ParamModificationVo.getPersonne().getTypePiece().getCodTpceTpce());
                   personneStrc.setNumPcePers(ParamModificationVo.getPersonne().getNumPcePers());
                   
                  PersonneCpt  personneCpt = (PersonneCpt) getPersonneCpt.exec(personneStrc);
                   if (personneCpt != null && personneCpt.getClient() != null){
                       contrat.setClient(personneCpt.getClient());
                   }else {
                       testExistClient = false;
                       Client client = new Client();
                       //--Type personne
                       Personne personne = new Personne() ;
                       
                       GetPersonneByNumSeqPersTrt getPersonneByNumSeq = new GetPersonneByNumSeqPersTrt();
                       getPersonneByNumSeq.setSecurityFlag(false);
                       personne = (Personne) getPersonneByNumSeq.exec(ParamModificationVo.getPersonne());
                       
                       
                       TypePers typepersonne = new TypePers();
                       typepersonne = personne.getCategoriePersonne().getTypePers();
                       client.setNumSeqPers(personne.getNumSeqPers());
                       client.setTypePers(typepersonne);
                       //---Structure
                       Structure structureClient = new Structure();
                       structureClient.setCodStrcStrc(ParamModificationVo.getCodeStructure());
                       client.setStructure(structureClient);
                       //--- Date Relation
                       client.setDatRelClt(DateHandler.strToDate(DateHandler.dateJour())); 
                       client.setCodEtatClt(Constants.COD_ETAT_CLT_ACTIF);
                       client.setNumFiscClt(StrHandler.lpad("",'0',10));
                      
                       client.setBoolDeclClt(Long.valueOf(0));
                       client.setPersonne(personne);
                       contrat.setClient(client);
                       
                       
                   }
             }  
            
             
         SequenceDAO sequenceDao = 
             (SequenceDAO)context.getBean("sequenceDAO");
         modificationDonneesId.setNumModModd(sequenceDao.getSequenceModificationDonnees());
         modificationDonnees.setModificationDonneesId(modificationDonneesId);

         if (texteModification.length()>999){
           modificationDonnees.setLibModModd(texteModification.toString().substring(0,998));
         }else{
           modificationDonnees.setLibModModd(texteModification.toString());   
         }
       
         Personne p = new Personne();
         p.setNumSeqPers(contrat.getClient().getPersonne().getNumSeqPers());
         modificationDonnees.setPersonne(p);
       
         CRUDservice crudService = 
             (CRUDservice)context.getBean("crudservice");
             if (testExistClient == false){
                 crudService.create(contrat.getClient());
             }
            crudService.update(contrat);  
            crudService.create(modificationDonnees);
            this.sychronisationPascal(ParamModificationVo); 
            
           
            logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Evénement : Sortie Trt normale");            
        return ParamModificationVo;
        
        }else{
          com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
          StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
          erreur.setCode("100");
          erreur.setDescription(text.toString());
          erreur.setKey("ModificationTypeCompteTrt");
          ParamModificationVo.addError(erreur); 
          

          return (ParamModificationVo);
        }     
    } catch (Exception e) {
        System.out.println(e.toString());
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text =
          new StringBuffer("Erreur dans ModificationIntituleCompteTrt : ");
        text.append(e.toString());
        text.append(e.getCause().getCause().getMessage())    ;
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("ModificationTypeCompteTrt");
        logger.info("Structure: "+ ParamModificationVo.getCodeStructure().toString()+" Matricule: "+ParamModificationVo.getMatricule().toString()+" Exception : " +  e.toString());
        
        ParamModificationVo.addError(erreur);
        throw new RuntimeException(e);  
        //return (ParamModificationVo);
    }
 }

 
    public void genCroText(ValueObject vo){
        
    
    }
    
    public String  getNumeroTache (IValueObject vo) {
        return  Constants.RESS_MODIF_TYPE_CPT ;
    }
    
    public void genererSynchronisationPascal(ValueObject vo) {   
        
       ParamModificationTypeCompteVo paramModificationDonneesVo = (ParamModificationTypeCompteVo)vo;
       logger.debug("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatricule().toString()+" Entrée : genererSynchronisationPascal");
       DateFormat myformat = new SimpleDateFormat("ddMMyy"); 
       StringBuffer partieVariable =new StringBuffer();
       GetPersonneByNumSeqPersTrt  getPersonneByNumSeqPersTrt  = new GetPersonneByNumSeqPersTrt ();
       Personne pers =  (Personne)getPersonneByNumSeqPersTrt.exec(paramModificationDonneesVo.getPersonne());
       this.setDateOperationSynch(new Date());
      
       this.setCodeStructureSynch(paramModificationDonneesVo.getContratCpt().getContratCptId().getCodStrcStrc());
            
       this.setCodeOperationSynch(Constants.COD_OPER_MODIF_TYPE_CPT);
       this.setCodeTacheSynch(Constants.COD_TACHE_MODIF_TYPE_CPT);
           
           //-- Données du contrat 
        if (paramModificationDonneesVo.getContratCpt()!= null && paramModificationDonneesVo.getContratCpt().getContratCptId() != null){
          partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
          partieVariable.append(StrHandler.lpad(paramModificationDonneesVo.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
        }
         
          partieVariable.append(1);
          partieVariable.append(determinerTitre(pers));
          
              if (pers.getNomPrnPers() != null){
                  String nom = new String();
               if(pers.getNomPrnPers().length()>20){
                   nom = pers.getNomPrnPers().substring(0,19);
               }else {
                   nom = pers.getNomPrnPers();
               }
               partieVariable.append(StrHandler.rpad(nom ,' ',20));
              }
              //----- Prénom
              if (pers.getNomNomPers() != null){
               String prenom = new String();
               if(pers.getNomNomPers().length() >20){
                   prenom = pers.getNomNomPers().substring(0,19);
               }else{
                   prenom =pers.getNomNomPers();
               }
               partieVariable.append(StrHandler.rpad(prenom ,' ',20));
              } 
              //-- nom du père
            if (pers.getNomPrnPers() != null){
             String prenom = new String();
             if(pers.getNomPrnPers().length() >20){
                 prenom = pers.getNomPrnPers().substring(0,19);
             }else{
                 prenom = pers.getNomPrnPers();
             }
             partieVariable.append(StrHandler.rpad(prenom ,' ',20));
            }  
            //-- telephone
             String telephone = "        " ;
             if (pers.getNumTelPers() != null){
              if(pers.getNumTelPers().length()>8)            
                  telephone = pers.getNumTelPers().substring(0,8);
              else
                  telephone = StrHandler.lpad(pers.getNumTelPers(),'0',8);
              }
              partieVariable.append(telephone);
          
           //-- adresse
            String rue = "                                        ";
            String ville = "                    ";  
            String codePays= "     ";
            String codePostal = "     ";
            if(pers.getAdresseResid().getRue() != null){
                if(pers.getAdresseResid().getRue().length() <= 40) 
                   rue = StrHandler.rpad(pers.getAdresseResid().getRue(),' ',40);       
                else rue = pers.getAdresseResid().getRue().substring(0,40);
            }
            partieVariable.append(rue); 
            
            if(pers.getAdresseResid().getVille() != null)
              ville = StrHandler.rpad(pers.getAdresseResid().getVille(),' ',20); 
            partieVariable.append(ville);
            
            if(pers.getAdresseResid().getCodCpCp() != null)
              codePostal = StrHandler.lpad(pers.getAdresseResid().getCodCpCp(),'0',5); 
            partieVariable.append(codePostal);
            
            if(pers.getAdresseResid().getCodPaysPays() != null)
               codePays = StrHandler.lpad(pers.getAdresseResid().getCodPaysPays(),'0',5); 
            partieVariable.append(codePays);
          
          //-- date de naissance 
           if(pers.getDatNaisPers()!=null){
               partieVariable.append(myformat.format(pers.getDatNaisPers()));
           }else{
               partieVariable.append("111111");
           }
          
          //--- lieu de naissance
           String lieuNais    = "                    ";
          
               if(pers.getLibNaisPers() != null){
                   if(pers.getLibNaisPers().length() <= 20) 
                      lieuNais = StrHandler.rpad(pers.getLibNaisPers(),' ',20);         
                   else lieuNais = pers.getLibNaisPers().substring(0,20); 
               } 
           partieVariable.append(StrHandler.rpad(lieuNais,' ',20));
          ///---residence
               String residence = " ";
               if(pers.getBoolResPers() != null ){ 
                   if(pers.getBoolResPers().toString().equals("1"))
                       residence = "R";
                  else if(pers.getBoolResPers().toString().equals("0"))
                       residence = "N";
               }
          partieVariable.append(residence);
          //-- type piece et num piece
           String typePiece="";
           if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_PASS))
               typePiece = "P";
           else if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CSEJ))
               typePiece = "S";
           else  typePiece = "C";
           partieVariable.append(typePiece);
           
           String numpiece="0000000000";
           if (pers.getNumPcePers()!=null){
                numpiece=pers.getNumPcePers();
           }
           partieVariable.append(StrHandler.lpad(numpiece,'0',10));
        //-- date delivrance
         if (pers.getDatDlvPers()!=null){
             partieVariable.append(myformat.format(pers.getDatDlvPers()));
         }else{
             partieVariable.append("111111"); 
         }
        //-lieu de délivrance
         String lieuDeliv    = determinerLieuDeliv(pers);
        partieVariable.append(lieuDeliv);
        //-activité et profession
        String libProfession  = "000000000000000";
        if(pers.getProfession().getProfessionId() != null){             
           libProfession = StrHandler.lpad(pers.getProfession().getProfessionId().getCodProfProf().toString(),'0',15);
        }
        partieVariable.append(libProfession);
        String activite = "0000000";
        if(pers.getActivite().getActiviteId() != null){
        activite = StrHandler.lpad(pers.getActivite().getActiviteId().getCodCactCact(),'0',2) + 
        StrHandler.lpad(pers.getActivite().getActiviteId().getCodSactSact().toString(),'0',2) + 
        StrHandler.lpad(pers.getActivite().getActiviteId().getCodActAct(),'0',3);
        }
        partieVariable.append(activite);
        String numFisc = "            ";
        //partieVariable.append(numFisc);
        
        //-resident o/n
        String resident = " ";
        if(pers.getBoolResPers() != null ){ 
            if(pers.getBoolResPers().toString().equals("1"))
                resident = "R";
           else if(pers.getBoolResPers().toString().equals("0"))
                resident = "N";
        }
        //partieVariable.append(resident);
        //-nationalité
        String nationalite = "   ";
          if(pers.getPaysByCodNat1Pays() != null ) 
              nationalite =pers.getPaysByCodNat1Pays().getCodPaysPays().toString();
        //partieVariable.append(nationalite);
        
        String releve = " "; 
        if(paramModificationDonneesVo.getContratCpt().getBoolRelvCpt() != null )
            releve =paramModificationDonneesVo.getContratCpt().getBoolRelvCpt().toString();
        
        String filler=numFisc+resident+nationalite+releve+"        ";
        partieVariable.append(filler);
        //-liste cotit
        CoTitulaire cotitulaire=new CoTitulaire();
        for(Iterator it = paramModificationDonneesVo.getListeMembreEntiteCotit().iterator(); it.hasNext();){
        cotitulaire = (CoTitulaire) it.next();
            partieVariable.append(StrHandler.lpad(cotitulaire.getPersonne().getNumPcePers(),'0',10));
        }
        System.out.println(partieVariable);
        logger.debug("Structure: "+ paramModificationDonneesVo.getCodeStructure().toString()+" Matricule: "+paramModificationDonneesVo.getMatricule().toString()+" Sortie : genererSynchronisationPascal le texte est : "+partieVariable.toString()+" de longeur "+partieVariable.length()) ;
        
        this.setTextSynch(partieVariable.toString());
            
           
    }
    private String determinerTitre(Personne personne)  {
        String codeTitre = "1";
        String titre ="";
        if (personne.getLibTitrPers()!=null) {
           titre =personne.getLibTitrPers();
        }
             if(titre.equals("M."))
               codeTitre = "1";
             else if(titre.equals("Mme")) 
               codeTitre = "2";
             else if(titre.equals("Mlle")) 
               codeTitre = "3";
           
        return codeTitre;        
    }       
    private String determinerLieuDeliv(Personne personne){
       
        GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
        String  lieuDeliv = "                    ";
        if(personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_MIN_EMANCIPE)
           ||(personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) )
           ||(personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) )){
           
          
           if(personne.getGouvernorat() != null ){
            Gouvernorat gouv =   (Gouvernorat)getGouvernoratTrt.exec(personne.getGouvernorat());   
           
           if(gouv!= null && gouv.getCodGouvGouv() != null)
             lieuDeliv = StrHandler.rpad(gouv.getLibGouvGouv(),' ',20);
           }                
       }
       return lieuDeliv;    
    
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamModificationTypeCompteVo ParamModificationVo = 
            (ParamModificationTypeCompteVo)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
        structureDomaine.setCodStrcStrc(ParamModificationVo.getCodeStructure());
        return structureDomaine;
    }
}  

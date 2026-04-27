package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.SynchronisePascal;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceContrat;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.service.ICrudService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetContratMandatTrt;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;
import com.bna.smile.model.domainecommun.traitement.GetListMembreCotitulaireTrt;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecommun.traitement.GetProfessionByIdTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.MandatRecherche;
import com.bna.smile.model.domainecontratcompte.procuration.model.TraceMandataireSyncVO;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.MiseAJourMandatTraceTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamValidationContrat;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/** Fichier: TraitementValidationContratTrt.java
 * @version 1.0.0 du 16/04/2007
 * Copyright(c) 2007 BNA (www.bna.com.tn)
 * Classe: TraitementValidationContratTrt
 * package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande
 * @author : El arbi hassine
 */
public class TraitementValidationContratTrt  extends Traitement{
    
    
    public TraitementValidationContratTrt() {
    }


    /**
     * Cette  methode permet de traiter les differentes transactions lors de la 
     * validation d'un contrat.
     * @param (ParamValidationContrat) ValueObject
     * @return contratCpt : l'objet contrat 
     */
    public IValueObject perform(IValueObject vo)  {
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine=(ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        ContratCpt contratCptValide = new ContratCpt();
        ParamValidationContrat paramValidationContrat = 
            (ParamValidationContrat)vo;
            
        

      try {
         if(this.checkClotureJournee()){
            
            if (paramValidationContrat.getPersonne() != null ) {
                crudService.update(paramValidationContrat.getPersonne());
            }
            if (paramValidationContrat.getClient() != null ) {
                paramValidationContrat.getClient().setCodEtatClt(Constants.COD_ETAT_CLT_ACTIF);
                crudService.update(paramValidationContrat.getClient());
            }
            if (paramValidationContrat.getContratCpt() != null) {
                crudService.update(paramValidationContrat.getContratCpt());
            }
            /*  if(paramValidationContrat.getTuteur()!=null){
             crudService.update(paramValidationContrat.getTuteur());
         }*/
            if (paramValidationContrat.getDetailCatCpt() != null) {
                crudService.update(paramValidationContrat.getDetailCatCpt());
            }
            if (paramValidationContrat.getCoTitulaire() != null) {
                crudService.update(paramValidationContrat.getCoTitulaire());
            }

            ValiderContratTrt validerContratTrt = new ValiderContratTrt();
            contratCptValide = 
                    (ContratCpt)validerContratTrt.exec(paramValidationContrat.getContratCpt().getContratCptId());

            if (!contratCptValide.hasError()) {                
                /* insertion de la trace contrat*/
                TraceContrat traceContrat = new TraceContrat();
                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                tacheId.setCodOperOper(Constants.OPER_VALIDATION_COMPTE);
                tacheId.setCodTachTach(Constants.TACHE_VALIDATION_COMPTE);
                tache.setTacheId(tacheId);               
                traceContrat.setCodEtatTrc(Constants.COD_ETAT_CPT_VALID);                
                traceContrat.setPersonnel(paramValidationContrat.getPersonnel());
                traceContrat.setContratCpt(contratCptValide);                
                traceContrat.setTache(tache);
                InsertTraceContratTrt insertTraceContratTrt = 
                    new InsertTraceContratTrt();
                TraceContrat traceContratRetour = 
                    (TraceContrat)insertTraceContratTrt.exec(traceContrat);
                    
                paramValidationContrat.setContratCpt(contratCptValide);
                
                this.sychronisationPascal(paramValidationContrat);     
           
            
            
            if(paramValidationContrat.getPersonne()!= null &&  paramValidationContrat.getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
               
                GetContratMandatTrt getContratMandatTrt = new GetContratMandatTrt();
                MandatRecherche mandatRecherche = new MandatRecherche();
                ContratCptMandat contratCptMandat = new ContratCptMandat();

                mandatRecherche.setContratCptId(paramValidationContrat.getContratCpt().getContratCptId());
                
                contratCptMandat = (ContratCptMandat)getContratMandatTrt.exec(mandatRecherche);                
                List mandats = contratCptMandat.getListeMandat(); 
                if (mandats != null && mandats.size() > 0) {
                  Mandat m = (Mandat)mandats.get(0); 
                    //extraire le trace mandat du mandat  concerné
                     ICriteria criteriaTtrMandat = searchEngine.createCriteria();
                     IExpression expression = searchEngine.createExpression();
                     
                     
                     criteriaTtrMandat.add(expression.eq("mandat.numMandMand", m.getNumMandMand()));
                     
                     List l2 = searchEngine.find(TraceMandat.class, criteriaTtrMandat);
                     TraceMandat traceMandat = new TraceMandat();                
                     if (l2 != null && l2.size() > 0) {
                         traceMandat  = (TraceMandat)l2.get(0);
                     }    
                     MiseAJourMandatTraceTrt miseAJourMandatTraceTrt = new MiseAJourMandatTraceTrt();
                     
                     for (Iterator it1 = m.getMandatPersonnes().iterator();it1.hasNext(); ) {
                         MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                         if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                             TraceMandataireSyncVO traceMandataireSyncVO = new TraceMandataireSyncVO();
                             traceMandataireSyncVO.setTraceMandat(traceMandat);
                             traceMandataireSyncVO.setMandatPersonne(mandatPersonne);
                             miseAJourMandatTraceTrt.sychronisationPascal(traceMandataireSyncVO);                        
                             
                         }
                     }
                }
                
            }
            
            
                if(paramValidationContrat.getCoTitulaire()!= null ){
                    genererSynchronisationPascalCotitulaire(paramValidationContrat); 
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
            
         // Fin contrôle fin de journée  
          }else{
                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                      StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                      erreur.setCode("100");
                      erreur.setDescription(text.toString());
                      erreur.setKey("InsertDemandeCheque");
                      contratCptValide.addError(erreur);        
                  }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans TraitementValidationContratTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("TraitementValidationContrat");
            contratCptValide.addError(erreur);  
            logger.error("Erreur au niveau de l'agence <<" + paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);              
            throw new RuntimeException(e);  
            
            
        }
        return contratCptValide;
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return ("201");    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamValidationContrat paramValidationContrat = 
            (ParamValidationContrat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
    
    public void genererSynchronisationPascal(ValueObject vo) {   
       
       
       ParamValidationContrat paramValidationContrat = (ParamValidationContrat)vo;
       GetPersonneByNumSeqPersTrt  getPersonneByNumSeqPersTrt  = new GetPersonneByNumSeqPersTrt ();
       DateFormat myformat = new SimpleDateFormat("ddMMyy");
       Personne pers =  (Personne)getPersonneByNumSeqPersTrt.exec(paramValidationContrat.getContratCpt().getClient().getPersonne());
       this.setCodeOperationSynch(Constants.OPER_VALIDATION_COMPTE);
       this.setCodeTacheSynch(Constants.TACHE_VALIDATION_COMPTE);
       this.setDateOperationSynch(paramValidationContrat.getContratCpt().getDatOuvCcpt());
       this.setCodeStructureSynch(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc());
    
       String numCompte = StrHandler.lpad(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                          StrHandler.lpad(paramValidationContrat.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
        
       
       
       String titre =  determinerTitre(paramValidationContrat);
       String nameEtPrenom = determinerNomPrenom(paramValidationContrat);
       String pouvoirSignature = determinerPouvoirSynature(paramValidationContrat);
       String pouvoir = pouvoirSignature.substring(0, pouvoirSignature.length()-1);
       String signature = pouvoirSignature.substring(pouvoirSignature.length() -1, pouvoirSignature.length());
       String typeCompte = determinerTypeCompte(paramValidationContrat);
       String numLivretCptLie = determinerNumLivretCptLie(paramValidationContrat);
       String IdEpargne = "           ";  // ?????
       
        
        String d = myformat.format(paramValidationContrat.getContratCpt().getDatOuvCcpt());
        String dateOuverture  = d;
       
       
       // nom du père : 
       String nomPere = "                    ";
       if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnpPers() != null){ 
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnpPers().length() <= 20) 
              nomPere = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnpPers(),' ',20);         
           else nomPere = paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnpPers().substring(0,20);           
       }
       
        String rue = "                                        ";
        String ville = "                    ";  
        String codePays= "     ";
        String codePostal = "     ";
        
       if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){ 
              
       if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getRue() != null){
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getRue().length() <= 40) 
              rue = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getRue(),' ',40);       
           else rue = paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getRue().substring(0,40);
       }
            
       if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getVille() != null)
         ville = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getVille(),' ',20); 
       
       
       if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getCodCpCp() != null)
         codePostal = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getCodCpCp(),'0',5); 
       
       
       if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getCodPaysPays() != null)
          codePays = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseResid().getCodPaysPays(),'0',5); 
      
       }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
                      
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getRue() != null){
               if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getRue().length() <= 40) 
                  rue = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getRue(),' ',40);       
               else rue = paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getRue().substring(0,40);               
           }
           
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getVille() != null)
           ville = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getVille(),' ',20);
           
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getCodCpCp() != null)
           codePostal = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getCodCpCp(),'0',5);
           
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getCodPaysPays() != null)
            codePays = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getAdresseProf().getCodPaysPays(),'0',5); 
           
          }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){
                      
           if(paramValidationContrat.getContratCpt().getAdresseCorresp().getRue() != null){
               if(paramValidationContrat.getContratCpt().getAdresseCorresp().getRue().length() <= 40) 
                  rue = StrHandler.rpad(paramValidationContrat.getContratCpt().getAdresseCorresp().getRue(),' ',40);    
               else rue = paramValidationContrat.getContratCpt().getAdresseCorresp().getRue().substring(0,40);     
           } 
            
            
           if(paramValidationContrat.getContratCpt().getAdresseCorresp().getVille() != null)
           ville = StrHandler.rpad(paramValidationContrat.getContratCpt().getAdresseCorresp().getVille(),' ',20); 
           
           if(paramValidationContrat.getContratCpt().getAdresseCorresp().getCodCpCp() != null)
           codePostal = StrHandler.lpad(paramValidationContrat.getContratCpt().getAdresseCorresp().getCodCpCp(),'0',5); 
           
           if(paramValidationContrat.getContratCpt().getAdresseCorresp().getCodPaysPays() != null)
            codePays = StrHandler.lpad(paramValidationContrat.getContratCpt().getAdresseCorresp().getCodPaysPays(),'0',5); 
           
       }
      
      String telephone = "        " ;
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumTelPers() != null){
        
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumTelPers().length()>8)            
            telephone = paramValidationContrat.getContratCpt().getClient().getPersonne().getNumTelPers().substring(0,8);
        else
            telephone = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumTelPers(),'0',8);
      } 
         
      
      String rcs = determinerRcs(paramValidationContrat);      
      String typeNumPiece = determinerTypeNumPiece(paramValidationContrat);
      String dateDeliv    = determinerDateDeliv(paramValidationContrat);
      String lieuDeliv    = determinerLieuDeliv(paramValidationContrat);
      
      String dateNais    = "      ";
      String lieuNais    = "                    ";
      String libProfession  = "000000000000000";
      if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
          if(paramValidationContrat.getContratCpt().getClient().getPersonne().getDatNaisPers() != null)
            dateNais = myformat.format(paramValidationContrat.getContratCpt().getClient().getPersonne().getDatNaisPers());
          if(pers.getLibNaisPers() != null){
              if(pers.getLibNaisPers().length() <= 20) 
                 lieuNais = StrHandler.rpad(pers.getLibNaisPers(),' ',20);         
              else lieuNais = pers.getLibNaisPers().substring(0,20); 
          } 
             
          if(pers.getProfession().getProfessionId() != null)             
             libProfession = StrHandler.lpad(pers.getProfession().getProfessionId().getCodProfProf().toString(),'0',15);
      }
      
      String formeJuridique = "00000";
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getFormeJuridique() != null && !paramValidationContrat.getContratCpt().getClient().getPersonne().getFormeJuridique().getCodFjFj().equals("999999"))
          formeJuridique = StrHandler.lpad(pers.getFormeJuridique().getCodFjFj(),'0',5);
          
      String activite = "0000000";
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getActivite().getActiviteId() != null)
          activite = StrHandler.lpad(pers.getActivite().getActiviteId().getCodCactCact(),'0',2) + StrHandler.lpad(pers.getActivite().getActiviteId().getCodSactSact().toString(),'0',2) + StrHandler.lpad(pers.getActivite().getActiviteId().getCodActAct(),'0',3);
      
      String categorieEpargne = determinerCategorieEpargne(paramValidationContrat); 
      String numLivretEpargne = "0000000";
      if(paramValidationContrat.getContratCpt().getNumLivrCcpt() != null)
          numLivretEpargne = StrHandler.lpad(paramValidationContrat.getContratCpt().getNumLivrCcpt(),'0',7);
           
      
      String categorieSocioProf = "    " ;  
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCatSocProf() != null)
            categorieSocioProf =  StrHandler.rpad(pers.getCatSocProf().getCodCsprCspr(),' ',4);
      
      String idClient = "0000000000000"; 
      if(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals("115") && paramValidationContrat.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS) )
          idClient = StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumPcePers(),'0',13);
     
     String categorieClient = determinerCategorieClient(paramValidationContrat);
     
     String numFisc = "            ";
     if(paramValidationContrat.getContratCpt().getClient().getNumFiscClt() != null ){
        if(paramValidationContrat.getContratCpt().getClient().getNumFiscClt().length()>12)
            numFisc = paramValidationContrat.getContratCpt().getClient().getNumFiscClt().substring(0,12);
        else
          numFisc = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getNumFiscClt(),' ',12);
     }   
      
      String releve = " "; 
      if(paramValidationContrat.getContratCpt().getBoolRelvCpt() != null )
          releve =paramValidationContrat.getContratCpt().getBoolRelvCpt().toString();
      
      String residence = " ";
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getBoolResPers() != null ){ 
          if(paramValidationContrat.getContratCpt().getClient().getPersonne().getBoolResPers().toString().equals("1"))
              residence = "R";
         else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getBoolResPers().toString().equals("0"))
              residence = "N";
      }
      
      String nationalite = "   ";
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getPaysByCodNat1Pays() != null ) 
            nationalite =pers.getPaysByCodNat1Pays().getCodPaysPays().toString();
      
      String mntRemiseCheque = determinerMntRemiseCheque(paramValidationContrat);
      
      String devise = paramValidationContrat.getContratCpt().getDevise().getCodDevDev().toString();
      
     String partieVariable =  numCompte + titre + nameEtPrenom + pouvoir + typeCompte + signature + numLivretCptLie +
                              IdEpargne + dateOuverture + nomPere + rue + ville + codePostal + codePays + telephone + rcs + 
                              typeNumPiece + dateDeliv +   lieuDeliv + dateNais + lieuNais + libProfession + formeJuridique + activite +
                              categorieEpargne + numLivretEpargne + categorieSocioProf + idClient + categorieClient + numFisc + residence + 
                              nationalite + releve + "        " + mntRemiseCheque + devise;
                              
      
           
      this.setTextSynch(partieVariable);
        
    }
    
    
    private String determinerTitre(ParamValidationContrat paramValidationContrat)  {
        String codeTitre = " ";
        if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){ 
          String titre = paramValidationContrat.getContratCpt().getClient().getPersonne().getLibTitrPers();
           if(titre != null){
             if(titre.equals("M."))
               codeTitre = "1";
             else if(titre.equals("Mme")) 
               codeTitre = "2";
             else codeTitre = "3";
           }
        }    
        return codeTitre;        
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
    
    private String determinerPouvoirSynature(ParamValidationContrat paramValidationContrat){
       String pouvoir = "0S";
       
        
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
              pouvoir = "1S";        
                
        ///extraire mandats valides sur le contrat en attente pour les personne morales et pers phy incapables
        }else  if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC)
            || paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) 
            || paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)  ){
            
            GetContratMandatTrt getContratMandatTrt = new GetContratMandatTrt();
            MandatRecherche mandatRecherche = new MandatRecherche();
            ContratCptMandat contratCptMandat = new ContratCptMandat();

            mandatRecherche.setContratCptId(paramValidationContrat.getContratCpt().getContratCptId());
            mandatRecherche.setCodEtat("S");
            contratCptMandat = (ContratCptMandat)getContratMandatTrt.exec(mandatRecherche);
        
            List mandats = contratCptMandat.getListeMandat();
            if (mandats != null && mandats.size() > 0) {
                
                for (Iterator iterator = mandats.iterator(); iterator.hasNext(); ) {
                    Mandat mandat = (Mandat)iterator.next();
                    pouvoir = (Long.valueOf(mandat.getMandatPersonnes().size())).toString() + mandat.getCodSignMand();
                    if(pouvoir != "0S")
                      break;
                }
            }
    
    }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){
         if(paramValidationContrat.getCoTitulaire() != null && paramValidationContrat.getCoTitulaire().getClient().getNumSeqPers() != null){
            Listes listMembresCotit = new Listes();        
            GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = new GetListMembreCotitulaireCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input          
            personneStrc.setCodTpceTpce(Constants.COD_NUM_ORDRE);
            personneStrc.setNumPcePers(paramValidationContrat.getCoTitulaire().getClient().getPersonne().getNumPcePers());
            personneStrc.setCodStrcStrc(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc());
            listMembresCotit = (Listes)getListMembreCotitulaireCmd.execute(personneStrc);
            pouvoir = (Long.valueOf(listMembresCotit.getList().size())).toString() + paramValidationContrat.getCoTitulaire().getCodSigCoti();
         } 

        
     }
    
     return pouvoir;
    }


    private String determinerTypeCompte(ParamValidationContrat paramValidationContrat){
       String typeCompte = "1";  // individuel
        if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){
            typeCompte = paramValidationContrat.getCoTitulaire().getCodTcotCoti();
            if (typeCompte.equals("I"))
                typeCompte = "2" ;// Indivis
            else if(typeCompte.equals("J"))
                typeCompte = "3" ;// Indivis
        }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
                  typeCompte = "4" ;// Morale
              }
        
    return typeCompte;
    
    }

    private String determinerNumLivretCptLie(ParamValidationContrat paramValidationContrat) {
       String numLivret = "000000000";
       if(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_VERT)){
         // compte vert 165
          numLivret = "000000101";
       }else if(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_ECONOMIE_SUR_SALAIRE)){
            // compte economie sur salire : 195
             numLivret = "000000195";
       }
      return numLivret;
    }
    
  
    private String determinerRcs(ParamValidationContrat paramValidationContrat){
      String  rcs = "             " ;
       if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
           // si personne morale
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)){
               rcs  = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumPcePers(),' ',13);               
           }
           
       }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
           PieceAnnexe pieceAnnexe = new PieceAnnexe();
            if (paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes() != null && paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().size() > 0) {
                for (Iterator it = paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                     pieceAnnexe = (PieceAnnexe)it.next();                     
                     if( pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().equals(Constants.COD_RCS) ){                        
                         rcs = StrHandler.rpad(pieceAnnexe.getPieceAnnexeId().getNumPcePian(),' ',13);    
                         break;
                     }                    
                }
            }
           
       }
       return rcs;
    }
    
    private String determinerTypeNumPiece(ParamValidationContrat paramValidationContrat){
      String  typeNumPiece = " 0000000000" ;
       
       if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
           if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC)
              ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR) )
              ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_MIN_EMANCIPE) )){
           // personne physique etrangres
           PieceAnnexe pieceAnnexe = new PieceAnnexe();
            if (paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes() != null && paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().size() > 0) {
                for (Iterator it = paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                     pieceAnnexe = (PieceAnnexe)it.next();                     
                     if( pieceAnnexe.getDatFvalPian()!=null && pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){                         
                         
                         if(pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().equals(Constants.COD_PASS))
                             typeNumPiece = "P";
                         else if(pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().equals(Constants.COD_CSEJ))
                             typeNumPiece = "S";
                         
                         typeNumPiece = typeNumPiece + StrHandler.lpad(pieceAnnexe.getPieceAnnexeId().getNumPcePian(),'0',10);    
                         break;
                     }                    
                }
            }
           
       }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_MIN_EMANCIPE)
                 ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) )
                 ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) )){
               
                 typeNumPiece = "C" + StrHandler.lpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNumPcePers(),'0',10); 
                
             }
       
    }
    
    return typeNumPiece;
    
}


    private String determinerDateDeliv(ParamValidationContrat paramValidationContrat){
      String  dateDeliv = "      " ;
       DateFormat myformat = new SimpleDateFormat("ddMMyy");
        if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
            if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC)
               ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR) )
               ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_MIN_EMANCIPE) )){
            // personne physique etrangres
            PieceAnnexe pieceAnnexe = new PieceAnnexe();
             if (paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes() != null && paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().size() > 0) {
                 for (Iterator it = paramValidationContrat.getContratCpt().getClient().getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                      pieceAnnexe = (PieceAnnexe)it.next();                     
                      if( pieceAnnexe.getDatFvalPian()!=null && pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){                           
                          dateDeliv = myformat.format(pieceAnnexe.getDatDelvPian());
                          break;
                      }                    
                 }
             }
            
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_MIN_EMANCIPE)
                  ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) )
                  ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) )){
                
                  dateDeliv = myformat.format(paramValidationContrat.getContratCpt().getClient().getPersonne().getDatDlvPers());
                 
              }
        
        }        
        return dateDeliv;
        
   }


    private String determinerLieuDeliv(ParamValidationContrat paramValidationContrat){
       // GetPersonneByNumSeqPersTrt  getPersonneByNumSeqPersTrt  = new GetPersonneByNumSeqPersTrt ();
        GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
        String  lieuDeliv = "                    ";
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_MIN_EMANCIPE)
           ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ) )
           ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) )){
           
           //Personne pers =  (Personne)getPersonneByNumSeqPersTrt.exec(paramValidationContrat.getContratCpt().getClient().getPersonne());
           if(paramValidationContrat.getPersonne().getGouvernorat() != null ){
            Gouvernorat gouv =   (Gouvernorat)getGouvernoratTrt.exec(paramValidationContrat.getPersonne().getGouvernorat());   
           
           if(gouv!= null && gouv.getCodGouvGouv() != null)
             lieuDeliv = StrHandler.rpad(gouv.getLibGouvGouv(),' ',20);
           }                
       }
       return lieuDeliv;    
    
    }
    
    
    private String determinerCategorieEpargne(ParamValidationContrat paramValidationContrat){
        
        String categorieEpargne = "    ";
        if(paramValidationContrat.getContratCpt().getCatCcptCcpt() !=null ){
            categorieEpargne = StrHandler.lpad(paramValidationContrat.getContratCpt().getCatCcptCcpt(),' ',4);
        }
       return categorieEpargne; 
    }
  
    private String determinerCategorieClient(ParamValidationContrat paramValidationContrat) {
       String categorieClient= " ";
       
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC)
           ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR) )
           ||(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_MIN_EMANCIPE) )){
           
            categorieClient = "5";

        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_PHY_TUN_MAJ)){
            categorieClient = "1";            
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
            categorieClient = "4"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_MIN_EMANCIPE)
                 ||paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_MIN_EMANCIPE)){
            categorieClient = "3"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC)){
                 categorieClient = "2";         
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("6"))){
                 categorieClient = "A"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("7"))){
                 categorieClient = "B"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("8"))){
                 categorieClient = "C"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("9"))){
                 categorieClient = "D"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("10"))){
                 categorieClient = "E"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("11"))){
                 categorieClient = "F"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("12"))){
                 categorieClient = "G"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("13"))){
                 categorieClient = "H"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("14"))){
                 categorieClient = "I"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("15"))){
                 categorieClient = "J"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("16"))){
                 categorieClient = "k"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("17"))){
                 categorieClient = "L"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("18"))){
                 categorieClient = "M"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("19"))){
                 categorieClient = "N"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("20"))){
                 categorieClient = "O"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("21"))){
                 categorieClient = "P"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("22"))){
                 categorieClient = "Q"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("23"))){
                 categorieClient = "R"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("24"))){
                 categorieClient = "S"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("25"))){
                 categorieClient = "T"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("26"))){
                 categorieClient = "U"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("27"))){
                 categorieClient = "V"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("28"))){
                 categorieClient = "W"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("30"))){
                 categorieClient = "Y"; 
        }else if(paramValidationContrat.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(String.valueOf("31"))){
                 categorieClient = "Z"; 
        }
        // reste la categorie morale et cotitulaire
        return categorieClient;
  }
  
    private String determinerMntRemiseCheque(ParamValidationContrat paramValidationContrat){
       String mnt = "               ";
       if(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_VERT)
          ||paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().equals(Constants.COD_COMPTE_ECONOMIE_SUR_SALAIRE)){
         // compte vert 165
          mnt = StrHandler.rpad(paramValidationContrat.getContratCpt().getMontSminCcpt().toString(),' ',15);
       }  
      return mnt;
    }  
    
    private String getLibProfession (Personne pers){
        
        String libProf = ""        ;
        GetProfessionByIdTrt getProfessionByIdTrt = new GetProfessionByIdTrt();
        Profession profession = new Profession();
        ProfessionId professionId = new ProfessionId();
        professionId.setCodProfProf(Long.valueOf(pers.getProfession().getProfessionId().getCodProfProf()));
        professionId.setCodGproGpro(Long.valueOf(pers.getProfession().getProfessionId().getCodGproGpro()));
        profession.setProfessionId(professionId);
        profession = (Profession)getProfessionByIdTrt.exec(profession);
        if (profession != null && profession.getLibProfProf() != null) {
             libProf = profession.getLibProfProf();
             
             if(libProf.length()>= 16)
                 libProf = libProf.substring(0,15);
             else  
                 libProf = StrHandler.rpad(libProf,' ',15);
        } 
        
        return libProf;
    }
    
 private String determinerNomPrenom (ParamValidationContrat paramValidationContrat){
     
     String nomPrenom = " ";
     String nom = " ";
     String prenom = " ";
     if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)){
      
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers().length() <= 20) 
         nom = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers(),' ',20);         
      else nom = paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers().substring(0,20);
      
      if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnPers().length() <= 20) 
          prenom = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnPers(),' ',20);          
      else prenom = paramValidationContrat.getContratCpt().getClient().getPersonne().getNomPrnPers().substring(0,20);
          
      nomPrenom = nom + prenom;
      
    }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){          
        if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomRsPers().length() <= 40)
           nomPrenom = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomRsPers(),' ',40) ;
        else nomPrenom = paramValidationContrat.getContratCpt().getClient().getPersonne().getNomRsPers().substring(0,40);

                              
    }else if(paramValidationContrat.getContratCpt().getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE )){ 
         if(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers().length() <= 40)
            nomPrenom = StrHandler.rpad(paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers(),' ',40) ;
         else nomPrenom = paramValidationContrat.getContratCpt().getClient().getPersonne().getNomNomPers().substring(0,40);
    }        
   return  nomPrenom;
 }
    
    public void genererSynchronisationPascalCotitulaire(ValueObject vo) { 
    
        ParamValidationContrat paramValidationContrat = (ParamValidationContrat)vo;        
        Context context = ContextHandler.getContext();
        DateFormat myformat = new SimpleDateFormat("ddMMyy");
        String numCompte = StrHandler.lpad(paramValidationContrat.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(paramValidationContrat.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
        
       /*partie variable*/
       
        String partieVariable ="";        
        String datedebPouv = "      ";
        String dateFinPouv = "      ";
        String montLimt="000000000000000";        
        
        datedebPouv=myformat.format(paramValidationContrat.getContratCpt().getDatOuvCcpt());
       
        /*recherche des membres cotitulaire*/
         Listes listMembresCotit = new Listes(); 
         GetListMembreCotitulaireTrt getListMembreCotitulaireTrt= new GetListMembreCotitulaireTrt();
         PersonneStrc personneStrc = new PersonneStrc();
         personneStrc.setCodTpceTpce(paramValidationContrat.getCoTitulaire().getClient().getPersonne().getTypePiece().getCodTpceTpce());
         personneStrc.setNumPcePers(paramValidationContrat.getCoTitulaire().getClient().getPersonne().getNumPcePers());
         personneStrc.setCodStrcStrc(Long.valueOf(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc()));
         listMembresCotit = (Listes)getListMembreCotitulaireTrt.perform(personneStrc);                
          
        for (Iterator it2 = listMembresCotit.getList().iterator();it2.hasNext(); ) {            
            CoTitulaire cotitulaire = (CoTitulaire)it2.next();
            SynchronisePascal synchronisePascal = new  SynchronisePascal();
            if (cotitulaire.getPersonne().getNumSeqPers()!=null) { 
        
                String qualite="               ";                
                GetPersonneByNumSeqPersTrt  getPersonneByNumSeqPersTrt  = new GetPersonneByNumSeqPersTrt ();
                Personne personne=new Personne();
                personne.setNumSeqPers(cotitulaire.getPersonne().getNumSeqPers());
                Personne pers =  (Personne)getPersonneByNumSeqPersTrt.exec(personne);
                String vNom = "";
                if (pers.getNomNomPers()!=null){
                    if (pers.getNomNomPers().length()>19){
                        vNom = pers.getNomNomPers().substring(0,19);
                    }else{
                        vNom = pers.getNomNomPers();
                    }
                 }else {
                            logger.error(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ "  >>  Nom Personne == null");
                        }
                String vPrenom = "";
                if (pers.getNomPrnPers()!=null){
                    if (pers.getNomPrnPers().length()>19){
                        vPrenom = pers.getNomPrnPers().substring(0,19);
                    }else{
                        vPrenom = pers.getNomPrnPers();
                    }
                }else {
                            logger.error(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ " >> Prénom Personne == null");
                        }
                String typePiece="";
                String numPiece=pers.getNumPcePers();
                if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CIN.toString()))
                    typePiece = "C";
                else {
                    if(pers.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_NUM_ORDRE.toString())){
                        if(pers.getPieceAnnexes().size()>0){
                            for (Iterator it = pers.getPieceAnnexes().iterator(); it.hasNext(); ){
                                PieceAnnexe pieceAnnexe=(PieceAnnexe) it.next();
                                if (pieceAnnexe.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_PASS.toString())){
                                    typePiece = "P";
                                    numPiece = pieceAnnexe.getPieceAnnexeId().getNumPcePian();
                                    break;
                                }else{ 
                                    if(pieceAnnexe.getTypePiece().getCodTpceTpce().toString().equals(Constants.COD_CSEJ.toString())){
                                        typePiece = "S";
                                        numPiece = pieceAnnexe.getPieceAnnexeId().getNumPcePian();
                                    }
                                }
                            }
                        }
                    }                   
                    else  typePiece = "C";
                }
                
                String libNaisPers ="";
                if(pers.getLibNaisPers()!=null){
                    libNaisPers=pers.getLibNaisPers();
                }else {
                            logger.debug(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ " >> getLibNaisPers == null");
                        }
                Date datNaisPers =new Date();
                if(pers.getDatNaisPers()!=null){
                    datNaisPers=pers.getDatNaisPers()  ;
                }else {
                            logger.error(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc().toString()+numCompte+ " >> getDatNaisPers == null");
                        }
                
                partieVariable= numCompte+
                StrHandler.lpad(numPiece,'0',10)+
                typePiece+
                determinerTitre(pers)+
                StrHandler.rpad(vNom,' ',20) + 
                StrHandler.rpad(vPrenom,' ',20)+
                myformat.format(datNaisPers)+
                StrHandler.rpad(libNaisPers,' ',20)+
                datedebPouv+dateFinPouv+
                StrHandler.lpad(paramValidationContrat.getPersonnel().getNumMatrUser(),'0',4)+
                montLimt+qualite
                ;
                        
               
               logger.debug("debut sauvgarde synchronistaionPascal");               
        
               Tache tache = new Tache();
               TacheId tacheId = new TacheId();
               tacheId.setCodOperOper(Long.valueOf("3"));
               tacheId.setCodTachTach(Long.valueOf("3"));
               tache.setTacheId(tacheId);  
               synchronisePascal.setTache(tache);
               synchronisePascal.setDatOperSynp(new Date());                
               Structure strc = new Structure();
               strc.setCodStrcStrc(paramValidationContrat.getContratCpt().getContratCptId().getCodStrcStrc());
               synchronisePascal.setStructure(strc);
               synchronisePascal.setCodEtatSynp("0");                
               synchronisePascal.setCodValSynp(partieVariable);
        
               ICrudService curService =(ICrudService)context.getBean("CURService");               
               curService.create(synchronisePascal);
               System.out.println("insertion de synchronistaionPascal");
               logger.debug("fin sauvgarde synchronistaionPascal");      
            }
            
        }//fin for
        
    }  
    
    
}

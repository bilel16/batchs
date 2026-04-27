package com.bna.smile.web.commun.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Adresse;
import com.bna.commun.model.CatSocProf;
import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Employeur;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Groupe;
import com.bna.commun.model.NiveauInstruction;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.PieceAnnexeId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.RegimeMatrimonial;
import com.bna.commun.model.Segment;
import com.bna.commun.model.SegmentId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetActiviteByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetCatSocProfCmd;
import com.bna.smile.model.domainecommun.commande.GetCategoriePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetEmployeurByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetFormeJuridiqueCmd;
import com.bna.smile.model.domainecommun.commande.GetGouvernoratCmd;
import com.bna.smile.model.domainecommun.commande.GetGroupeCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneByNumSeqPersCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.GetProfessionByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetRegimeMatrimonialCmd;
import com.bna.smile.model.domainecommun.commande.GetSegmentCmd;
import com.bna.smile.model.domainecommun.commande.GetTypeModificationCmd;
import com.bna.smile.model.domainecommun.commande.GetTypePieceCmd;
import com.bna.smile.model.domainecommun.commande.ModificationDonneesClientCmd;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetListeContratsAmodifierCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamListContratsAmodifierVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamPers;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ModificationDonneesClientForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.PieceAnnexeView;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.util.ContratCptView;

public class ModificationDonneesClientAction extends DispatchAction {
    public ModificationDonneesClientAction() {
    }
    private static final Logger logger = Logger.getLogger(ModificationDonneesClientAction.class);
    public ActionForward initierPageModificationClient(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        
        
       
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationContratCompteForm"
        sessionUtil.removeSession(request,"modificationDonneesClientForm");

        ModificationDonneesClientForm modificationDonneesClientForm = 
            (ModificationDonneesClientForm)form;
        String typePers = new String();
       
             ActionMessages actionMessages = new ActionMessages();
     try{
        /*test sur l'etat du domaine*/
         StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
         Boolean bool= SmileUtil.testDomaineOuvert(structureDomaine);
         
        GetTypeModificationCmd getTypeModificationCmd = 
            new GetTypeModificationCmd();
        TypeModification typeModification = new TypeModification();
        typeModification.setCodCodModf(Long.valueOf(modificationDonneesClientForm.getCodeModification()));
        typeModification = 
                (TypeModification)getTypeModificationCmd.execute(typeModification);
        modificationDonneesClientForm.setLibelleModification(typeModification.getLibModfModf());
        modificationDonneesClientForm.setTypeModification(typeModification);
        modificationDonneesClientForm.setMatriculeUser(paramAgence.getNumMatrUser().toString());
        modificationDonneesClientForm.setDateActuelle(DateHandler.dateJour());
        // -------------------------------------------
        if (modificationDonneesClientForm.getTypePersonne() != null) {
            typePers = modificationDonneesClientForm.getTypePersonne();
        }
        modificationDonneesClientForm.clearForm();
        modificationDonneesClientForm.setTypePersonne(typePers);

        if (modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_RES) || 
            modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_CORR)) {
            return mapping.findForward("modificationAdresse");
        } else if (typePers != null && typePers.equals("PM")) {
            return mapping.findForward("modificationPersMoral");
        } else if(modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_MATRICULE_PM)){
            return mapping.findForward("modificationMatrFiscal");
        }else  
        {
            return mapping.findForward("initierPage");
        }
     }catch(Exception e ){
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction / disp :initierPage: ");
         text.append(e.toString());
         erreur.setCode("200");
         erreur.setDescription(text.toString());

         ActionMessage actionMessage = 
             new ActionMessage("exception.generique", 
                               erreur.getDescription());
         actionMessages.add("Erreur ", actionMessage);
         this.saveMessages(request, actionMessages);
         return mapping.findForward("error");
         
     }
    

    }

    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        ModificationDonneesClientForm modificationDonneesClientForm = 
            (ModificationDonneesClientForm)form;

        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
            //-------------------------------------------------------
            //------- recherche de la personne ----------------------
            //-------------------------------------------------------
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getTypePiece()));
            personneStrc.setNumPcePers(modificationDonneesClientForm.getNumeroPiece());
            
            if (!paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)){
             personneStrc.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));
            }
            
            personneStrc.setCasModificationDonnees("OUI");
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneCpt personneCpt = 
                (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            //------- Fin de la recherche de la personne

            if (personneCpt.getPersonne() != null) {
                Personne personne = personneCpt.getPersonne();
                modificationDonneesClientForm.setTestExistPersonne("existe");
                modificationDonneesClientForm.setPersonne(personneCpt.getPersonne());
                //---------------------------------------------------------------------------//
                //-----------Verifier si la personne est cliente de l'agence ----------------//
                //---------------------------------------------------------------------------//
                      
                if (personneCpt.getListeContratCpt().size() == 0 ){ //--- La personne n'est pas titulaire d'un contrat il faut verifier s'il n'est pas mandataire à un contrat
                     boolean test = false;
                     //----------------------------------------------------------//
                     //------ Recherche si la personne posséde un mandat 
                     //------ pour un contrat de l'agence
                     GetListContratMandataireCmd getListContratMandataire = new GetListContratMandataireCmd();
                     Listes listeDesMandat = (Listes) getListContratMandataire.execute(personneStrc);
                       //-------------------------------------------------------------
                       //-------- La personne n'est pas mandataire sur aucun contrat
                      if (listeDesMandat.getList().size() > 0) { 
                          test =true;
                      }

                      
                    //----------------------------------------------------------//
                    //------ Recherche si la personne participe à un enetité co-titulaire qui a des contrats dans l'agence 
                     GetListCotitulairePersonneCmd listCotitulaireCmd = new GetListCotitulairePersonneCmd();
                     Listes listeCotitulaire = (Listes) listCotitulaireCmd.execute(personneStrc);
                     
                     if (test == false && listeCotitulaire.getList() != null && listeCotitulaire.getList().size()>0 ){
                         
                         PersonneStrc personneStrcCotit = new PersonneStrc();
                         //---------- Boucler sur l'ensemble des cotitulaires qu'il participe
                          for (Iterator ite = listeCotitulaire.getList().iterator(); ite.hasNext(); ) {
                              CoTitulaire coTitulaire =(CoTitulaire) ite.next();
                              //-- verifier si le co-titulaire possede un contrat 
                              Personne pers = new Personne();
                              pers.setNumSeqPers(coTitulaire.getClient().getNumSeqPers());
                             
                              GetPersonneByNumSeqPersCmd getPersonneByNumSeqPersCmd = new GetPersonneByNumSeqPersCmd();
                              pers = (Personne) getPersonneByNumSeqPersCmd.execute(pers);
                             
                              personneStrcCotit.setCodTpceTpce(pers.getTypePiece().getCodTpceTpce());
                              personneStrcCotit.setNumPcePers(pers.getNumPcePers());
                              personneStrcCotit.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));
                               
                               PersonneCpt personneCptcotit = 
                                  (PersonneCpt)getPersonneCptCmd.execute(personneStrcCotit);
                               
                               //--- verifier si la personne co-titulaire possede un contrat dans cette agence
                               if (personneCptcotit.getListeContratCpt()!= null && personneCptcotit.getListeContratCpt().size() >0 ){
                                   test =true;
                               }
                               
                          }
                         
                     }
                                        
                    if (test == false) { 
                           String pers = new String("PP");
                           if (modificationDonneesClientForm.getTypePersonne().equals(pers)){
                              modificationDonneesClientForm.setTestIsClientAgence("N");
                              return mapping.findForward("initierPage");
                           }else {
                              modificationDonneesClientForm.setTestIsClientAgence("N");
                              return mapping.findForward("modificationPersMoral");
                           }
                    
                    //--------------------------------------------------------//
                    //--- la personne est soit mandataire ou participe à une entite cotiti sur des contrats de l'agence
                    
                    } else {
                      modificationDonneesClientForm.setTestIsClientAgence("O");
                    }
                    
                    
                }else {
                    modificationDonneesClientForm.setTestIsClientAgence("O");
                }
                //------------------------------------------------------------------------------//
                //--------- Fin vérification si la personne est cliente ------------------------//
                
                if (personne.getTypePiece() != null) {
                   
                    //------------------------------------------------------------------------------------------------//
                    //----- verification du type de la personne dans le cas d'une recherche par le numéro sequentiel--//
                    //------------------------------------------------------------------------------------------------//
                    if (modificationDonneesClientForm.getTypePiece() != null && Long.valueOf(modificationDonneesClientForm.getTypePiece()).equals(Constants.COD_NUM_ORDRE)){
                        //-----------------------------------------//
                        //----- pour personne physique : s'il a saisi un numero ordre et la personne n'est pas physique
                        String pers = new String("PP");
                         if (modificationDonneesClientForm.getTypePersonne().equals(pers)){
                          String cons = new String (Constants.PERSPHYSIQUE);
                             if (!personne.getCategoriePersonne().getTypePers().getCodTperTper().equals(cons)){
                                modificationDonneesClientForm.setTestTypePersonne("N");
                                return mapping.findForward("initierPage");
                             }
                         }
                        
                        //-----------------------------------------//
                        //----- pour personne morale : s'il a saisi un numero ordre et la personne n'est pas une pers morale
                        
                        if (modificationDonneesClientForm.getTypePersonne().equals("PM")){
                         if (!personne.getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
                            modificationDonneesClientForm.setTestTypePersonne("N");
                            return mapping.findForward("modificationPersMoral");
                         }
                        } 
                    }
                    
                    if (personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE) && 
                        (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID)))) {
                        modificationDonneesClientForm.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce().toString());
                    }
                    modificationDonneesClientForm.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce().toString());
                    if (! (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE)))){
                     modificationDonneesClientForm.setAncCodTpceTpce(personne.getTypePiece().getCodTpceTpce().toString());
                    }
                }
                if (personne.getNumPcePers() != null) {
                    if (personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE) && 
                        (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID)))) {
                        modificationDonneesClientForm.setNumPcePers("");
                    } else {
                        modificationDonneesClientForm.setNumPcePers(personne.getNumPcePers());
                    }
                    modificationDonneesClientForm.setAncNumPcePers(personne.getNumPcePers());
                    Long var = Constants.COD_RCS;
                    if (personne.getTypePiece().getCodTpceTpce().equals(var)) {
                        extractRcs(modificationDonneesClientForm, 
                                   personne.getNumPcePers());

                    }
                }
                //--------------------------------------//
                //----- Date et lieu de delivrance en cas de piece CIN
                if (!personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE)) {
                    if (personne.getDatDlvPers() != null) {
                        modificationDonneesClientForm.setDatDlvPers(DateHandler.dateToStr(personne.getDatDlvPers()));
                    }
                    if (personne.getGouvernorat() != null && personne.getGouvernorat().getCodGouvGouv() != null ) {
                        GetGouvernoratCmd getGouvernoratCmd = 
                            new GetGouvernoratCmd();
                        Gouvernorat gouvernorat = new Gouvernorat();
                        
                        gouvernorat.setCodGouvGouv(personne.getGouvernorat().getCodGouvGouv());
                        gouvernorat = 
                                (Gouvernorat)getGouvernoratCmd.execute(gouvernorat);

                        modificationDonneesClientForm.setCodGouvGouv(gouvernorat.getCodGouvGouv().toString());
                        modificationDonneesClientForm.setLibGouvGouv(gouvernorat.getLibGouvGouv());
                    }
                }
                if (personne.getNomNomPers() != null) {
                    modificationDonneesClientForm.setNomPersonne(personne.getNomNomPers());
                    modificationDonneesClientForm.setNomNomPers(personne.getNomNomPers());

                }
                if (personne.getNomRsPers() != null) {
                    modificationDonneesClientForm.setNomRsPers(personne.getNomRsPers());
                    modificationDonneesClientForm.setRaisonSocial(personne.getNomRsPers());
                }
                if (personne.getLibSiglPers() != null) {
                    modificationDonneesClientForm.setSigle(personne.getLibSiglPers());
                    modificationDonneesClientForm.setLibSiglPers(personne.getLibSiglPers());
                }
                if (personne.getNomPrnPers() != null) {
                    modificationDonneesClientForm.setPrenomPersonne(personne.getNomPrnPers());
                    modificationDonneesClientForm.setNomPrnPers(personne.getNomPrnPers());
                }
                if (personne.getLibTitrPers() != null) {
                    modificationDonneesClientForm.setLibTitrPers(personne.getLibTitrPers());
                    modificationDonneesClientForm.setAncienLibTitrPers(personne.getLibTitrPers());
                }

                if (personne.getDatNaisPers() != null) {


                    modificationDonneesClientForm.setDatNaisPers(DateHandler.dateToStr(personne.getDatNaisPers()));
                }

                if (personne.getCategoriePersonne() != null && 
                    personne.getCategoriePersonne().getCodCatpCatp() != null) {
                    modificationDonneesClientForm.setCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                }

                if (personne.getFormeJuridique() != null && 
                    personne.getFormeJuridique().getCodFjFj() != null) {
                    modificationDonneesClientForm.setCodFjFj(personne.getFormeJuridique().getCodFjFj());
                }
                //----------------------------------------------------------//
                //------------- Identifiant secondaire ---------------------//

                modificationDonneesClientForm.getListDesPiecesSecondaire().clear();
                for (Iterator it = personne.getPieceAnnexes().iterator(); 
                     it.hasNext(); ) {
                    PieceAnnexe pieceAnnexe = (PieceAnnexe)it.next();
                    Date dateFin = DateHandler.strToDate(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                    Date dateJour = DateHandler.strToDate(DateHandler.dateToStr(new Date()));
                    //-------------------------------------------------------//
                    //------------ Verifier si la date fin n'est pas expiré < date du jours
                    if ((pieceAnnexe.getDatFvalPian() ==null )   || (dateFin.equals(dateJour) ) || (dateFin.after(dateJour) ) ) {
                    PieceAnnexeView pieceAnnexeView = new PieceAnnexeView();
                    pieceAnnexeView.setPieceAnnexe(pieceAnnexe);
                    pieceAnnexeView.setDateDelivrance(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                    pieceAnnexeView.setDateFinValidite(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                    
                    modificationDonneesClientForm.getListDesPiecesSecondaire().add(pieceAnnexeView);
                     //------------- Verifier si la personne est commerçante 
                    }
                    
                    if (pieceAnnexe.getTypePiece().getCodTpceTpce().equals(Long.valueOf(Constants.COD_RCS))) {
                        modificationDonneesClientForm.setTestPersCommercante("OUI");
                    }

                } // fin for 

                if (personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_CIN)) {
                    PieceAnnexe pieceAnnexe = new PieceAnnexe();
                    PieceAnnexeView pieceAnnexeView = new PieceAnnexeView();
                    PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
                    pieceAnnexeId.setNumSeqPers(personne.getNumSeqPers());
                    pieceAnnexeId.setCodTpceTpce(Constants.COD_CIN);
                    TypePiece tp = new TypePiece();
                    tp.setCodTpceTpce(Constants.COD_CIN);
                    tp.setLibSiglTpce("CIN");
                    pieceAnnexe.setTypePiece(tp);
                    pieceAnnexeId.setNumPcePian(personne.getNumPcePers());
                    pieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
                    pieceAnnexe.setDatDelvPian(personne.getDatDlvPers());
                    pieceAnnexeView.setPieceAnnexe(pieceAnnexe);
                    pieceAnnexeView.setDateDelivrance(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                    GetGouvernoratCmd getGouvernoratCmd = 
                        new GetGouvernoratCmd();
                    Gouvernorat gouvernorat = new Gouvernorat();
                    gouvernorat.setCodGouvGouv(personne.getGouvernorat().getCodGouvGouv());
                    gouvernorat = 
                            (Gouvernorat)getGouvernoratCmd.execute(gouvernorat);
                    pieceAnnexeView.setCodeGouvernorat(gouvernorat.getCodGouvGouv().toString());
                    pieceAnnexeView.setLibelleGouvernorat(gouvernorat.getLibGouvGouv());
                    
                    modificationDonneesClientForm.getListDesPiecesSecondaire().add(pieceAnnexeView);
                }

                //--------------------------------------------------------//
                //------------- Qualité de la personne --------------------//
                if (personne.getPaysByCodNat1Pays() != null) {
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(personne.getPaysByCodNat1Pays().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    modificationDonneesClientForm.setCodeNationalite(pays.getCodPaysPays());
                    modificationDonneesClientForm.setLibelleNationalite(pays.getLibNatPays());
                }
                if (personne.getBoolResPers() != null) {
                    modificationDonneesClientForm.setBoolResPers(personne.getBoolResPers().toString());
                }
                if (personne.getSegment() != null) {
                    GetSegmentCmd getSegmentCmd = new GetSegmentCmd();
                    SegmentId segmentId = new SegmentId();
                    Segment segment = new Segment();

                    segmentId.setCodSegSeg(personne.getSegment().getSegmentId().getCodSegSeg());
                    segmentId.setCodCsegCseg(personne.getSegment().getSegmentId().getCodCsegCseg());
                    segmentId.setCodSsegSseg(personne.getSegment().getSegmentId().getCodSsegSseg());
                    segment.setSegmentId(segmentId);
                    segment = (Segment)getSegmentCmd.execute(segment);

                    modificationDonneesClientForm.setCodSegSeg(segment.getSegmentId().getCodSegSeg().toString());
                    modificationDonneesClientForm.setCodCsegCseg(segment.getSegmentId().getCodCsegCseg().toString());
                    modificationDonneesClientForm.setCodSsegSseg(segment.getSegmentId().getCodSsegSseg().toString());
                    modificationDonneesClientForm.setLibSegSeg(segment.getLibSegSeg());
                }

                //--------------------------------------------------------//
                //------------- adresse de residence ---------------------//
                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_RES))) {

                    if (personne.getAdresseResid() != null) {
                        // Immeuble
                        if (personne.getAdresseResid().getImmeuble() != null) {
                            modificationDonneesClientForm.setImmeubleRes(personne.getAdresseResid().getImmeuble());
                        }
                        // Rue
                        if (personne.getAdresseResid().getRue() != null) {
                            modificationDonneesClientForm.setRueRes(personne.getAdresseResid().getRue());
                        }

                        // Cite
                        if (personne.getAdresseResid().getCite() != null) {
                            modificationDonneesClientForm.setCiteRes(personne.getAdresseResid().getCite());
                        }


                        // Pays 
                        if (personne.getAdresseResid().getCodPaysPays() != 
                            null) {
                            modificationDonneesClientForm.setCodPaysPaysRes(personne.getAdresseResid().getCodPaysPays());
                            GetPaysCmd getPaysCmd = new GetPaysCmd();
                            Pays pays = new Pays();
                            pays.setCodPaysPays(personne.getAdresseResid().getCodPaysPays());
                            pays = (Pays)getPaysCmd.execute(pays);
                            if (pays.getLibPaysPays() != null) {
                                modificationDonneesClientForm.setLibPaysPaysRes(pays.getLibPaysPays());
                            }
                        }

                        // Code Postal
                        if (personne.getAdresseResid().getCodCpCp() != null) {
                            modificationDonneesClientForm.setCodCpCpRes(personne.getAdresseResid().getCodCpCp());
                            // si le pays est la tunisie extraire le libelle du code postal
                            if ((personne.getAdresseResid().getCodPaysPays() != 
                                 null) && 
                                (personne.getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                                GetCodePostalCmd getCodePostalCmd = 
                                    new GetCodePostalCmd();
                                CodePostal codePostal = new CodePostal();
                                codePostal.setCodCpCp(Long.valueOf(personne.getAdresseResid().getCodCpCp()));
                                codePostal = 
                                        (CodePostal)getCodePostalCmd.execute(codePostal);
                                modificationDonneesClientForm.setLibCpCpRes(codePostal.getLibCpCp());

                                // gouvernerat
                                modificationDonneesClientForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                modificationDonneesClientForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());


                            }
                        }
                    } // Fin adresse de résidence

                    //------------------------------------------------------//
                    //------------- adresse professionnelle------------------//
                    if (personne.getAdresseProf() != null) {
                        // Immeuble
                        if (personne.getAdresseProf().getImmeuble() != null) {
                            modificationDonneesClientForm.setImmeubleProf(personne.getAdresseProf().getImmeuble());
                        }
                        // Rue
                        if (personne.getAdresseProf().getRue() != null) {
                            modificationDonneesClientForm.setRueProf(personne.getAdresseProf().getRue());
                        }

                        // Cite
                        if (personne.getAdresseProf().getCite() != null) {
                            modificationDonneesClientForm.setCiteProf(personne.getAdresseProf().getCite());
                        }

                        // Ville
                        if (personne.getAdresseProf().getVille() != null) {
                            modificationDonneesClientForm.setVilleProf(personne.getAdresseProf().getVille());
                        }

                        // Pays 
                        if (personne.getAdresseProf().getCodPaysPays() != 
                            null) {
                            modificationDonneesClientForm.setCodPaysPaysProf(personne.getAdresseProf().getCodPaysPays());
                            GetPaysCmd getPaysCmd = new GetPaysCmd();
                            Pays pays = new Pays();
                            pays.setCodPaysPays(personne.getAdresseProf().getCodPaysPays());
                            pays = (Pays)getPaysCmd.execute(pays);
                            if (pays.getLibPaysPays() != null) {
                                modificationDonneesClientForm.setLibPaysPaysProf(pays.getLibPaysPays());
                            }
                        }

                        // Code Postal
                        if (personne.getAdresseProf().getCodCpCp() != null) {
                            modificationDonneesClientForm.setCodCpCpProf(personne.getAdresseProf().getCodCpCp());
                            // si le pays est la tunisie extraire le libelle du code postal
                            if (personne.getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE)) {
                                GetCodePostalCmd getCodePostalCmd = 
                                    new GetCodePostalCmd();
                                CodePostal codePostal = new CodePostal();
                                codePostal.setCodCpCp(Long.valueOf(personne.getAdresseProf().getCodCpCp()));
                                codePostal = 
                                        (CodePostal)getCodePostalCmd.execute(codePostal);
                                modificationDonneesClientForm.setLibCpCpProf(codePostal.getLibCpCp());

                                // gouvernerat
                                modificationDonneesClientForm.setCodGouvGouvProf(codePostal.getGouvernorat().getCodGouvGouv().toString());
                                modificationDonneesClientForm.setLibGouvGouvProf(codePostal.getGouvernorat().getLibGouvGouv());

                            }
                        }
                    } // Fin adresse professionnelle
                } // ------------------------------------- Fin Adr Res & Prof 

                //--------------------------------------------------------------------//
                //------------- adresse de correspondance des contrats ------------------//
                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {
                    if (personneCpt.getListeContratCpt() != null && 
                        personneCpt.getListeContratCpt().size() > 0) {
                        List listeDesContrat = new ArrayList();
                        for (Iterator it = 
                             personneCpt.getListeContratCpt().iterator(); 
                             it.hasNext(); ) {
                            ContratCpt contratCpt = (ContratCpt)it.next();
                            String cleContrat = 
                                contratCpt.getContratCptId().getCodStrcStrc() + 
                                "_" + 
                                contratCpt.getContratCptId().getCodPrdPrd() + 
                                "*" + 
                                contratCpt.getContratCptId().getNumCcptCcpt();
                            ContratCptView contratCptView = 
                                new ContratCptView();
                            contratCptView.setCleContrat(cleContrat);
                            contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                            contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                           '0', 
                                                                           6));
                            contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                         '0', 
                                                                         3));
                            contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 
                                                                          4));
                            contratCptView.setContratCpt(contratCpt);
                            listeDesContrat.add(contratCptView);
                        }
                        modificationDonneesClientForm.setListeDesContrats(listeDesContrat);
                        for (int i = 0; 
                             i < modificationDonneesClientForm.getListeDesContrats().size(); 
                             i++)
                            modificationDonneesClientForm.getListeDesContratsAmodifierAdresse().add("");
                    }

                } //----------------------------------------- Fin Adr Correspondance -------------------------------

                //---------------------------------------------------------------//
                //------------- Secteur Profession et activite ------------------//
                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ACTIVITE))) {
                    if (personne.getCodSectPers() != null) {
                        modificationDonneesClientForm.setCodSectPers(personne.getCodSectPers());
                    }

                    if (personne.getProfession() != null) {
                        modificationDonneesClientForm.setCodProfProf(personne.getProfession().getProfessionId().getCodProfProf().toString());
                        modificationDonneesClientForm.setCodGproGpro(personne.getProfession().getProfessionId().getCodGproGpro().toString());
                        GetProfessionByIdCmd getProfessionByIdCmd = 
                            new GetProfessionByIdCmd();
                        Profession profession = new Profession();
                        ProfessionId professionId = new ProfessionId();
                        professionId.setCodProfProf(Long.valueOf(modificationDonneesClientForm.getCodProfProf()));
                        professionId.setCodGproGpro(Long.valueOf(modificationDonneesClientForm.getCodGproGpro()));
                        profession.setProfessionId(professionId);
                        profession = 
                                (Profession)getProfessionByIdCmd.execute(profession);
                        if (profession != null && 
                            profession.getLibProfProf() != null) {
                            modificationDonneesClientForm.setLibProfession(profession.getLibProfProf());
                        }

                    }

                    if (personne.getActivite() != null) {
                        modificationDonneesClientForm.setCodActAct(personne.getActivite().getActiviteId().getCodActAct().toString());
                        modificationDonneesClientForm.setCodCactCact(personne.getActivite().getActiviteId().getCodCactCact().toString());
                        modificationDonneesClientForm.setCodSactSact(personne.getActivite().getActiviteId().getCodSactSact().toString());

                        GetActiviteByIdCmd getActiviteByIdCmd = 
                            new GetActiviteByIdCmd();
                        Activite activite = new Activite();
                        ActiviteId activiteId = new ActiviteId();
                        activiteId.setCodActAct(modificationDonneesClientForm.getCodActAct());
                        activiteId.setCodCactCact(modificationDonneesClientForm.getCodCactCact());
                        activiteId.setCodSactSact(Long.valueOf(modificationDonneesClientForm.getCodSactSact()));
                        activite.setActiviteId(activiteId);

                        activite = 
                                (Activite)getActiviteByIdCmd.execute(activite);
                        if (activite != null && 
                            activite.getLibActAct() != null) {
                            modificationDonneesClientForm.setLibActivite(activite.getLibActAct());
                        }
                    }
                    
                    if (personne.getEmployeur()!= null && personne.getEmployeur().getCodEmpEmp() != null ){
                        Employeur employeur = new  Employeur();
                        employeur.setCodEmpEmp( personne.getEmployeur().getCodEmpEmp() );
                        
                        GetEmployeurByIdCmd getEmployeurByIdCmd = new GetEmployeurByIdCmd();
                        employeur =(Employeur) getEmployeurByIdCmd.execute(employeur);
                        modificationDonneesClientForm.setLibEmployeur(employeur.getLibEmpEmp());
                        
                    }
                    
                    if (personne.getRevRevPers()!= null ){
                        modificationDonneesClientForm.setRevRevPers(StrHandler.formatmnt(personne.getRevRevPers()));
                    }
                    
                } //------------------------- Fin activite --------------------------------

                //---------------------------------------------------------------//
                //------------------------ Contact ------------------------------// 
                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CONTACT))) {
                    modificationDonneesClientForm.setNumTelPers(personne.getNumTelPers());
                    modificationDonneesClientForm.setNumFaxPers(personne.getNumFaxPers());
                    modificationDonneesClientForm.setAdrMailPers(personne.getAdrMailPers());
                    modificationDonneesClientForm.setAdrWebPers(personne.getAdrWebPers());
                    modificationDonneesClientForm.setAdrSwiftPers(personne.getAdrSwiftPers());
                    modificationDonneesClientForm.setAdrTlxPers(personne.getAdrTlxPers());
                } //---------------------------- Fin contact ------------------------------------


                //---------------------------------------------------------------//
                //------------------------ Données Complemantaire  --------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_COMPLEMENTAIRE))) {
                    // situation familiale ( etat civile)
                    if (personne.getCodSitfPers() != null) {
                        modificationDonneesClientForm.setCodSitfPers(personne.getCodSitfPers());
                    }
                    // deuxième nationalité
                    if (personne.getPaysByCodNat2Pays() != null) {
                        Pays pays = new Pays();
                        pays.setCodPaysPays(personne.getPaysByCodNat2Pays().getCodPaysPays());
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        pays = (Pays)getPaysCmd.execute(pays);

                        modificationDonneesClientForm.setCodeNationalite2(pays.getCodPaysPays());
                        modificationDonneesClientForm.setLibelleNationalite2(pays.getLibNatPays());
                    }
                    // Régime matrimonial
                    if (personne.getRegimeMatrimonial() != null) {
                        RegimeMatrimonial regimeMatrimonial = 
                            new RegimeMatrimonial();
                        regimeMatrimonial.setCodRmatRmat(personne.getRegimeMatrimonial().getCodRmatRmat());
                        GetRegimeMatrimonialCmd getRegimeMatrimonialCmd = 
                            new GetRegimeMatrimonialCmd();
                        regimeMatrimonial = 
                                (RegimeMatrimonial)getRegimeMatrimonialCmd.execute(regimeMatrimonial);

                        modificationDonneesClientForm.setCodRmatRmat(regimeMatrimonial.getCodRmatRmat());
                        modificationDonneesClientForm.setLibRmatRmat(regimeMatrimonial.getLibRmatRmat());
                    }
                    // Niveau Instruction
                    if (personne.getNiveauInstruction() != null) {

                        modificationDonneesClientForm.setCodNiviNivi(personne.getNiveauInstruction().getCodNiviNivi());
                        modificationDonneesClientForm.setLibNiviNivi(personne.getNiveauInstruction().getLibNiviNivi());
                    }

                    if (personneCpt.getClient() != null) {
                        modificationDonneesClientForm.setClient(personneCpt.getClient());
                        if (personneCpt.getClient().getCodDoanClt() != null) {
                            modificationDonneesClientForm.setCodDoanClt(personneCpt.getClient().getCodDoanClt());
                        }
                        if (personneCpt.getClient().getNumFiscClt() != null) {
                            modificationDonneesClientForm.setNumFiscClt(personneCpt.getClient().getNumFiscClt());
                            if (Constants.verifMatriculeFiscal(personneCpt.getClient().getNumFiscClt())) {

                                extractMatriculeFiscal(modificationDonneesClientForm, 
                                                       personneCpt);
                            } else {
                                StringBuffer ch = new StringBuffer();
                                ch.append("L'ancien matricule fiscal est : ");
                                ch.append(personneCpt.getClient().getNumFiscClt());

                                modificationDonneesClientForm.setChaineMatriculeFisc(ch.toString());
                            }

                        }
                    }
                }


                //---------------------------------------------------------------//
                //------------------- Forme juridique Pers Morale  --------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_FORME_JUR_PM))) {

                    if (personne.getFormeJuridique() != null) {
                        modificationDonneesClientForm.setCodFjFj(personne.getFormeJuridique().getCodFjFj().toString());
                        if (personne.getFormeJuridique().getLibFjFj() != 
                            null) {
                            modificationDonneesClientForm.setLibelleFormeJuridique((personne.getFormeJuridique().getLibFjFj()));
                        }
                    }

                    if (personne.getCategoriePersonne() != null) {

                        ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd = 
                            new ChargerTypeCatPcePersonneCmd();
                        TypeCatPers typeCatPersVo = new TypeCatPers();
                        ListTypeCatTpce listTypeCatTpce = 
                            new ListTypeCatTpce();
                        typeCatPersVo.setCodTperTper(personne.getCategoriePersonne().getTypePers().getCodTperTper());
                        listTypeCatTpce = 
                                (ListTypeCatTpce)chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);

                        if (listTypeCatTpce.getListCatPers() != null) {
                            modificationDonneesClientForm.setListeCategoriePersonne(listTypeCatTpce.getListCatPers());
                        }

                        if (listTypeCatTpce.getListeCategp_Formj() != null) {
                            modificationDonneesClientForm.setListeFormeJuridique(listTypeCatTpce.getListeCategp_Formj());
                        }

                        if (personne.getCategoriePersonne().getLibCatpCatp() != 
                            null) {
                            modificationDonneesClientForm.setLibelleCategoriePersonne(personne.getCategoriePersonne().getLibCatpCatp());
                        }
                        modificationDonneesClientForm.setCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                        modificationDonneesClientForm.setCodFjFj(personne.getFormeJuridique().getCodFjFj());
                    }
                }

                //-----------------------------------------------------------------------//
                //------------------- Nomination Complementaire PP  ---------------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOMINAT_COMP))) {

                    if (personne.getNomNomoPers() != null) {
                        modificationDonneesClientForm.setNomNomoPers(personne.getNomNomoPers());
                    }
                    if (personne.getNomPrnoPers() != null) {
                        modificationDonneesClientForm.setNomPrnoPers(personne.getNomPrnoPers());
                    }
                    if (personne.getNomNomaPers() != null) {
                        modificationDonneesClientForm.setNomNomaPers(personne.getNomNomaPers());
                    }

                    if (personne.getNomPrnaPers() != null) {
                        modificationDonneesClientForm.setNomPrnaPers(personne.getNomPrnaPers());
                    }

                    if (personne.getNomNommPers() != null) {
                        modificationDonneesClientForm.setNomNommPers(personne.getNomNommPers());
                    }

                    if (personne.getNomPrnmPers() != null) {
                        modificationDonneesClientForm.setNomPrnmPers(personne.getNomPrnmPers());
                    }
                }

                //-----------------------------------------------------------------------//
                //------------------- Données sociales Personne physique  ---------------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_SOCIALE))) {

                    if (personne.getNbrEnfPers() != null) {
                        modificationDonneesClientForm.setNbrEnfPers(personne.getNbrEnfPers().toString());
                    }

                    if (personne.getNumAffsPers() != null) {
                        modificationDonneesClientForm.setNumAffsPers(personne.getNumAffsPers());
                    }

                    if (personne.getDatAffsPers() != null) {
                        modificationDonneesClientForm.setDatAffspers(DateHandler.dateToStr(personne.getDatAffsPers()));
                    }
                    if (personne.getCatSocProf() != null && 
                        personne.getCatSocProf().getCodCsprCspr() != null) {
                        CatSocProf catSocProf = new CatSocProf();
                        GetCatSocProfCmd getCatSocProfCmd = 
                            new GetCatSocProfCmd();
                        catSocProf.setCodCsprCspr(personne.getCatSocProf().getCodCsprCspr());
                        catSocProf = 
                                (CatSocProf)getCatSocProfCmd.execute(catSocProf);
                        modificationDonneesClientForm.setCodCsprCspr(catSocProf.getCodCsprCspr());
                        modificationDonneesClientForm.setLibCsprCspr(catSocProf.getLibCsprCspr());

                    }
                }
                //---------------------------------------------------------------//
                //-------- Données code est matricles pour les PM  --------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_MATRICULE_PM))) {
                    if (personneCpt.getClient() != null) {

                        modificationDonneesClientForm.setClient(personneCpt.getClient());
                        if (personneCpt.getClient().getCodDoanClt() != null) {
                            modificationDonneesClientForm.setCodDoanClt(personneCpt.getClient().getCodDoanClt());
                        }
                        if (personneCpt.getClient().getNumFiscClt() != null) {
                            modificationDonneesClientForm.setNumFiscClt(personneCpt.getClient().getNumFiscClt());
                            if (Constants.verifMatriculeFiscal(personneCpt.getClient().getNumFiscClt())) {
                                extractMatriculeFiscal(modificationDonneesClientForm, 
                                                       personneCpt);
                            } else {
                                StringBuffer ch = new StringBuffer();
                                ch.append("L'ancien matricule fiscal est : ");
                                ch.append(personneCpt.getClient().getNumFiscClt());

                                modificationDonneesClientForm.setChaineMatriculeFisc(ch.toString());
                            }
                        }
                        if (personneCpt.getClient().getNumBctClt() != null) {
                            modificationDonneesClientForm.setNumBctClt(personneCpt.getClient().getNumBctClt());
                        }
                        if (personneCpt.getClient().getNumRnePers() != null) {
                            modificationDonneesClientForm.setNumRnePers(personneCpt.getClient().getNumRnePers());
                        }


                    }
                    if (personne.getNumJortPers() != null) {
                        modificationDonneesClientForm.setNumJortMoral(personne.getNumJortPers());
                    }

                    if (personne.getDatJortPers() != null) {
                        modificationDonneesClientForm.setDatJortMoral(DateHandler.dateToStr(personne.getDatJortPers()));
                    }

                    if (personne.getNumDecrPers() != null) {
                        modificationDonneesClientForm.setNumDecretMoral(personne.getNumDecrPers());
                    }

                    if (personne.getDatDecrPers() != null) {
                        modificationDonneesClientForm.setDatDecretMoral(DateHandler.dateToStr(personne.getDatDecrPers()));
                    }

                    if (personne.getDatPmePers() != null) {
                        modificationDonneesClientForm.setDateCreationMoral(DateHandler.dateToStr(personne.getDatPmePers()));
                    }

                    if (personne.getNumLpmePers() != null) {
                        modificationDonneesClientForm.setNumLoiCreMoral(personne.getNumLpmePers());
                    }
                    if (personne.getDateExpPers() != null) {
                        modificationDonneesClientForm.setDateActMoral(DateHandler.dateToStr(personne.getDateExpPers()));
                    }

                }

                //---------------------------------------------------------------//
                //-------- Données code est matricles pour les PM  --------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CAPITAL_GROUP))) {
                    if (personneCpt.getClient() != null) {
                        modificationDonneesClientForm.setClient(personneCpt.getClient());
                        if (personneCpt.getClient().getGroupe() != null) {
                            GetGroupeCmd getGroupeCmd = new GetGroupeCmd();
                            Groupe groupe = new Groupe();
                            groupe.setCodGrpGrp(personneCpt.getClient().getGroupe().getCodGrpGrp());
                            groupe = (Groupe)getGroupeCmd.execute(groupe);
                            modificationDonneesClientForm.setCodGrpGrp(groupe.getCodGrpGrp().toString());
                            modificationDonneesClientForm.setNomRsGrp(groupe.getNomRsGrp());
                        }
                    }

                    if (personne.getDatCapPers() != null) {
                        modificationDonneesClientForm.setDatCapPers(DateHandler.dateToStr(personne.getDatCapPers()));
                    }
                    if (personne.getMontCapPers() != null) {
                        modificationDonneesClientForm.setMontCapPers(StrHandler.formatmnt(personne.getMontCapPers().doubleValue()));
                    }
                }
                
                
                //---------------------------------------------------------------//
                //-------- Changement de categorie pour PP-------  --------------// 

                if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE))) {
                   
                    modificationDonneesClientForm.setNouvelleCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                    ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd =  new ChargerTypeCatPcePersonneCmd();
                    TypeCatPers typeCatPersVo = new TypeCatPers();
                    ListTypeCatTpce listTypeCatTpce =  new ListTypeCatTpce();
                    
                    typeCatPersVo.setCodTperTper(personne.getCategoriePersonne().getTypePers().getCodTperTper());
                    listTypeCatTpce =  (ListTypeCatTpce) chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);

                    if (listTypeCatTpce.getListCatPers() != null) {
                       List liste = new ArrayList();
                       for(Iterator it = listTypeCatTpce.getListCatPers().iterator(); it.hasNext();){
                           CategoriePersonne categorie = (CategoriePersonne) it.next();
                           //-----------------------------------------------------//
                           //------ Faire les combinaison  possible-----------------//
                           
                           if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR)){
                               if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)||
                                    categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp()) ){
                                   liste.add(categorie);                                   
                               }
                           } else if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.MINEUR_EMANCIPE)){
                                            
                               if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR) ||
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE) ||
                                    categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())){
                                   liste.add(categorie);                                   
                               }
                          } else if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.MINEUR)){
                                  
                                if (personne.getPaysByCodNat1Pays().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE)){              
                                  
                                   if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR) ||
                                        categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)|| 
                                        categorie.getCodCatpCatp().equals(Constants.MINEUR_EMANCIPE)||
                                        categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())) {
                                        
                                        liste.add(categorie);                                   
                                   }
                               
                                } else {
                                   
                                    if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR) ||
                                         categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)|| 
                                         categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR )||
                                         categorie.getCodCatpCatp().equals(Constants.MINEUR_EMANCIPE)||
                                         categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MINEUR_EMANCIPE)||
                                         categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE)||
                                         categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())){
                                         
                                        liste.add(categorie);                                   
                                    }
                                    
                                }
                           } else if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR)){
                              
                               if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR) ||
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)||
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE) ||
                                    categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())){
                                   
                                   liste.add(categorie);                                   
                               }
                               
                               
                               
                           } else if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MINEUR_EMANCIPE)){
                               
                               if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR) ||
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)|| 
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR )||
                                    categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE ) ||
                                    categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())){
                                    
                                   liste.add(categorie);                                   
                               }
                               
                           } else if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE)){
                             
                               if ( categorie.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE) ||
                                    categorie.getCodCatpCatp().equals(personne.getCategoriePersonne().getCodCatpCatp())){
                                   liste.add(categorie);                                   
                               }
                               
                           }
                           
                       }
                    
                        modificationDonneesClientForm.setListeCategoriePersonne(liste);
                    }

              
                    if (personne.getCategoriePersonne().getLibCatpCatp() != 
                        null) {
                        modificationDonneesClientForm.setLibelleCategoriePersonne(personne.getCategoriePersonne().getLibCatpCatp());
                    }
                    //modificationDonneesClientForm.setCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
                   
                }
                

            } // Fin personne trouvé
            if (modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_RES) || 
                modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_CORR)) {
                return mapping.findForward("modificationAdresse");
            } else if(modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_MATRICULE_PM)){
                return mapping.findForward("modificationMatrFiscal");
            }else if (modificationDonneesClientForm.getTypePersonne() != 
                       null && 
                       modificationDonneesClientForm.getTypePersonne().equals("PM")) {
                return mapping.findForward("modificationPersMoral");
            } else {
                return mapping.findForward("initierPage");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction / disp :RecherchePersonne: ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    private void extractMatriculeFiscal(ModificationDonneesClientForm modificationDonneesClientForm, 
                                        PersonneCpt personneCpt)  throws Exception {
        String matriculeFiscal = personneCpt.getClient().getNumFiscClt();

        modificationDonneesClientForm.setNumMatriculeFisc(matriculeFiscal.substring(0, 
                                                                                    7));
        modificationDonneesClientForm.setCleMatriculeFisc(matriculeFiscal.substring(7, 
                                                                                    8));
        modificationDonneesClientForm.setCodeTvaFisc(matriculeFiscal.substring(8, 
                                                                               9));
        modificationDonneesClientForm.setCodeCategorieFisc(matriculeFiscal.substring(9, 
                                                                                     10));
        modificationDonneesClientForm.setNumEtabFisc(matriculeFiscal.substring(10, 
                                                                               13));
    }

    private void extractRcs(ModificationDonneesClientForm modificationDonneesClientForm, 
                            String rcs) throws Exception{
        modificationDonneesClientForm.setCodeRcs(rcs.substring(0, 1));
        modificationDonneesClientForm.setAnneeRcs(rcs.substring(rcs.length() - 
                                                                4));

        if (Integer.valueOf(modificationDonneesClientForm.getAnneeRcs()) < 
            2004) {
            modificationDonneesClientForm.setCodeTribunal(rcs.substring(1, 2));
            modificationDonneesClientForm.setNumeroRcs(rcs.substring(2, 9));

        } else {
            modificationDonneesClientForm.setCodeTribunal(rcs.substring(1, 3));
            modificationDonneesClientForm.setNumeroRcs(rcs.substring(3, 9));

        }
    }

    public ActionForward validation(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
      
        ModificationDonneesClientForm modificationDonneesClientForm = 
            (ModificationDonneesClientForm)form;
        ParamModificationDonneesVo paramModificationDonneesVo = 
            new ParamModificationDonneesVo();
        Personne personne = modificationDonneesClientForm.getPersonne();
        boolean testValidation = true;
        ActionMessages actionMessages = new ActionMessages();
        Adresse adresseCorrespondance = new Adresse();
        String messageConfirmation = new String();
        String separteur = System.getProperty("line.separator");
        
        try {
            //------------- Modification de l'adresse ----------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_RES))) {
                
                StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
                StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
                //-----------------------------------------------------------------------------//
                //----------- Ancienne Adresse résidence-------------------------------------//
                if (personne.getAdresseResid() !=null ){
                    textAnc.append(" //*  Adresse de résidence : *// ");
                    if (personne.getAdresseResid().getImmeuble() != null ) {
                        textAnc.append(" Immeuble : "+ personne.getAdresseResid().getImmeuble()+"; ");
                    }
                    if (personne.getAdresseResid().getRue()  != null ) {
                        textAnc.append(" Rue : "+ personne.getAdresseResid().getRue()+"; ");
                    }
                    if (personne.getAdresseResid().getCite()  != null ) {
                        textAnc.append(" Cité : "+ personne.getAdresseResid().getCite()+"; ");
                    }
                    if (personne.getAdresseResid().getCodCpCp()  != null ) {
                        textAnc.append(" Code Postal : "+ personne.getAdresseResid().getCodCpCp()+"; ");
                    }
                    if (personne.getAdresseResid().getCodPaysPays()  != null ) {
                        Pays pays = new Pays();
                        pays.setCodPaysPays(personne.getAdresseResid().getCodPaysPays() );
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        pays = (Pays) getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null ){
                         textAnc.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                        }
                    }
                 } // fin if adrese res
                
                //-----------------------------------------------------------------------------------------//
                //----------- Nouvelle Adresse résidence-------------------------------------//
               
                    textNew.append(" //*  Adresse de résidence : *// ");
                    if (modificationDonneesClientForm.getImmeubleRes() != null ) {
                        textNew.append(" Immeuble : "+ modificationDonneesClientForm.getImmeubleRes()+"; ");
                    }
                    if (modificationDonneesClientForm.getRueRes()  != null ) {
                        textNew.append(" Rue : "+ modificationDonneesClientForm.getRueRes()+"; ");
                    }
                    if (modificationDonneesClientForm.getCiteRes()  != null ) {
                        textNew.append(" Cité : "+ modificationDonneesClientForm.getCiteRes()+"; ");
                    }
                    if (modificationDonneesClientForm.getCodCpCpRes()  != null ) {
                        textNew.append(" Code Postal : "+ modificationDonneesClientForm.getCodCpCpRes()+"; ");
                    }
                    if (modificationDonneesClientForm.getLibPaysPaysRes() != null ) {
                       textNew.append(" Pays : "+ modificationDonneesClientForm.getLibPaysPaysRes() +"; ");
                    }
                
                if(personne.getAdresseResid() != null){
                    personne.getAdresseResid().setImmeuble(modificationDonneesClientForm.getImmeubleRes());
                    personne.getAdresseResid().setRue(modificationDonneesClientForm.getRueRes());
                    personne.getAdresseResid().setVille(modificationDonneesClientForm.getVilleRes());
                    personne.getAdresseResid().setCite(modificationDonneesClientForm.getCiteRes());
                    personne.getAdresseResid().setCodPaysPays(modificationDonneesClientForm.getCodPaysPaysRes());
                    personne.getAdresseResid().setCodCpCp(modificationDonneesClientForm.getCodCpCpRes());
                }
                    //-------------------------------------------------------------------------------//
                    //----------- Ancienne Adresse professionnelle -----------------//
                    
                    
                if (personne.getAdresseProf() !=null ){
                    textAnc.append(" //*  Adresse Professionelle : *// ");
                    if (personne.getAdresseProf().getImmeuble() != null ) {
                        textAnc.append(" Immeuble : "+ personne.getAdresseProf().getImmeuble()+"; ");
                    }
                    if (personne.getAdresseProf().getRue()  != null ) {
                        textAnc.append(" Rue : "+ personne.getAdresseProf().getRue()+"; ");
                    }
                    if (personne.getAdresseProf().getCite()  != null ) {
                        textAnc.append(" Cité : "+ personne.getAdresseProf().getCite()+"; ");
                    }
                    if (personne.getAdresseProf().getCodCpCp()  != null ) {
                        textAnc.append(" Code Postal : "+ personne.getAdresseProf().getCodCpCp()+"; ");
                    }
                    if (personne.getAdresseProf().getCodPaysPays()  != null ) {
                        Pays pays = new Pays();
                        pays.setCodPaysPays(personne.getAdresseProf().getCodPaysPays() );
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        pays = (Pays) getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null ){
                         textAnc.append(" Pays : "+ pays.getLibPaysPays()+"; ");
                        }
                     }
                }
                
                //-----------------------------------------------------------------------------------------//
                //----------- Nouvelle Adresse professionnelle-------------------------------//
                
                    textNew.append(" //* Adresse de professionnelle : *// ");
                    if (modificationDonneesClientForm.getImmeubleProf() != null ) {
                        textNew.append(" Immeuble : "+ modificationDonneesClientForm.getImmeubleProf()+"; ");
                    }
                    if (modificationDonneesClientForm.getRueProf()  != null ) {
                        textNew.append(" Rue : "+ modificationDonneesClientForm.getRueProf()+"; ");
                    }
                    if (modificationDonneesClientForm.getCiteProf()  != null ) {
                        textNew.append(" Cité : "+ modificationDonneesClientForm.getCiteProf()+"; ");
                    }
                    if (modificationDonneesClientForm.getCodCpCpProf()  != null ) {
                        textNew.append(" Code Postal : "+ modificationDonneesClientForm.getCodCpCpProf()+"; ");
                    }
                    if (modificationDonneesClientForm.getLibPaysPaysProf() != null ) {
                       textNew.append(" Pays : "+ modificationDonneesClientForm.getLibPaysPaysProf() +"; ");
                    }
                
                Adresse adrProf = new Adresse();
                if (modificationDonneesClientForm.getImmeubleProf() != null) {
                    adrProf.setImmeuble(modificationDonneesClientForm.getImmeubleProf());
                }
                if (modificationDonneesClientForm.getRueProf() != null) {
                    adrProf.setRue(modificationDonneesClientForm.getRueProf());
                }

                if (modificationDonneesClientForm.getVilleProf() != null) {
                    adrProf.setVille(modificationDonneesClientForm.getVilleProf());
                }

                if (modificationDonneesClientForm.getCiteProf() != null) {
                    adrProf.setCite(modificationDonneesClientForm.getCiteProf());
                }

                if (modificationDonneesClientForm.getCodPaysPaysProf() != 
                    null) {
                    adrProf.setCodPaysPays(modificationDonneesClientForm.getCodPaysPaysProf());
                }

                if (modificationDonneesClientForm.getCodCpCpProf() != null) {
                    adrProf.setCodCpCp(modificationDonneesClientForm.getCodCpCpProf());
                }
                personne.setAdresseProf(adrProf);
                
                modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
                modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());

            }
            //-----------------------------------------------------------------------//
            //---------- Modification de l'adresse de correspondance ----------------//
            //-----------------------------------------------------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ADR_CORR))) {

                StringBuffer text = new StringBuffer();
                text.append(" L'adresse est : ");
                                if (modificationDonneesClientForm.getCodPaysPaysCorresp() !=    null) {
                                    adresseCorrespondance.setCodPaysPays(modificationDonneesClientForm.getCodPaysPaysCorresp());
                                    text.append(" Pays : "+modificationDonneesClientForm.getLibPaysPaysCorresp()+" ;");

                                }
                                
                                if (modificationDonneesClientForm.getCodCpCpCorresp() !=           null) {
                                    adresseCorrespondance.setCodCpCp(modificationDonneesClientForm.getCodCpCpCorresp());
                                    if(!modificationDonneesClientForm.getCodCpCpCorresp().equalsIgnoreCase("")){
                                     text.append(" Code Postal : "+modificationDonneesClientForm.getCodCpCpCorresp()+" ;");
                                    }
                                }
                                
                                if (modificationDonneesClientForm.getVilleCorresp() != null) {
                                    adresseCorrespondance.setVille(modificationDonneesClientForm.getVilleCorresp());
                                    if (!modificationDonneesClientForm.getVilleCorresp().equalsIgnoreCase("")){
                                     text.append(" Ville : "+modificationDonneesClientForm.getVilleCorresp()+" ;");
                                    }
                                }
                                
                                if (modificationDonneesClientForm.getImmeubleCorresp() !=    null) {
                                    adresseCorrespondance.setImmeuble(modificationDonneesClientForm.getImmeubleCorresp());
                                    if (!modificationDonneesClientForm.getImmeubleCorresp().equalsIgnoreCase("")){
                                    text.append(" Immeuble : "+modificationDonneesClientForm.getImmeubleCorresp()+" ;");
                                    }
                                }
                                if (modificationDonneesClientForm.getRueCorresp() != null) {
                                    adresseCorrespondance.setRue(modificationDonneesClientForm.getRueCorresp());
                                    if (!modificationDonneesClientForm.getRueCorresp().equalsIgnoreCase("")){
                                    text.append(" Rue : "+modificationDonneesClientForm.getRueCorresp()+" ;");
                                    }
                                }
                            

                                if (modificationDonneesClientForm.getCiteCorresp() != null) {
                                    adresseCorrespondance.setCite(modificationDonneesClientForm.getCiteCorresp());
                                    if(!modificationDonneesClientForm.getCiteCorresp().equalsIgnoreCase("")){
                                    text.append(" Cité : "+modificationDonneesClientForm.getCiteCorresp()+" ;");
                                    }

                                }
                            
                                modificationDonneesClientForm.setLibelleConfirmation2(text.toString());
                // modificationDonneesClientForm.getContratModifie().setAdresseCorresp(adresseCorrespondance);
          
            }
            //-----------------------------------------------------------------------//
            //------------- Changement  Identifiant   ----------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID))) {
                
                StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
                StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
                
               
                //--------------------------------------------------------------------- 
                //-- verifier si une personne existe déja avec le type et le numero
                if (!(modificationDonneesClientForm.getCodTpceTpce().equals(modificationDonneesClientForm.getAncCodTpceTpce())) || 
                    !(modificationDonneesClientForm.getNumPcePers().equals(modificationDonneesClientForm.getAncNumPcePers()))) {

                    PersonneStrc personneStrc = new PersonneStrc();
                    personneStrc.setCodTpceTpce(Long.valueOf(Constants.COD_CIN));
                    personneStrc.setNumPcePers(modificationDonneesClientForm.getNumPcePers());

                    GetPersonneCptCmd getPersonneCptCmd = 
                        new GetPersonneCptCmd();
                    PersonneCpt personneCpt = 
                        (PersonneCpt)getPersonneCptCmd.execute(personneStrc);

                    if (personneCpt.getPersonne() != null) {
                        testValidation = false;
                        modificationDonneesClientForm.setMessage("IdPrincipalExist");
                    } else {
                    
                        GetTypePieceCmd getTypePieceCmd = new GetTypePieceCmd();
                        TypePiece  typePieceAnc  = new TypePiece(); 
                        TypePiece  typePieceNew = new TypePiece();
                        typePieceAnc.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getTypePiece()));
                        typePieceNew.setCodTpceTpce(Constants.COD_CIN);
                        
                        typePieceAnc = (TypePiece) getTypePieceCmd.execute(typePieceAnc);
                        textAnc.append(" Type Pièce : "+typePieceAnc.getLibTpceTpce()+" ; numéro  : " +modificationDonneesClientForm.getNumeroPiece() );
                        typePieceNew = (TypePiece) getTypePieceCmd.execute(typePieceNew);
                        textNew.append(" Type Pièce : "+typePieceNew.getLibTpceTpce()+" ; numéro : "+modificationDonneesClientForm.getNumPcePers() );
                        if (personne.getNumPcePers() != null ){
                            
                        }
                        personne.setNumPcePers(StrHandler.lpad(modificationDonneesClientForm.getNumPcePers(),'0',8));
                        personne.getTypePiece().setCodTpceTpce(Constants.COD_CIN);
                        textNew.append(" date de délivrance : "+ modificationDonneesClientForm.getDatDlvPers()+" ; ");
                        personne.setDatDlvPers(DateHandler.strToDate(modificationDonneesClientForm.getDatDlvPers()));
                        Gouvernorat gouvernorat = new Gouvernorat();
                        textNew.append(" lieu de délivrance : "+ modificationDonneesClientForm.getLibGouvGouv()+" ; ");
                        gouvernorat.setCodGouvGouv(Long.valueOf(modificationDonneesClientForm.getCodGouvGouv()));
                        personne.setGouvernorat(gouvernorat);
                        
                        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
                        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
                    }
                } //fin  verifier si une personne existe


            }
            //-----------------------------------------------------------------------//
            //------------- Modification Identifiant Principal  ----------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENTIFIANT))) {
              
                testValidation = validationIdentifiantPrincipal(modificationDonneesClientForm, personne, testValidation);
                
            }
            //--------- Ajout d'une pièce annexe  -----------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_AJOUT_PIECE))) {
                
             
                testValidation = validationAjoutNouvellePieceAnnexe(modificationDonneesClientForm, paramModificationDonneesVo, testValidation);
                
            }
            //-----------------------------------------------------------------------//
            //--------- Modification Identifiant secondaire Pièce annexes -----------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_IDENT_SEC))) {
               testValidation =  validationIPieceAnnexeEtIdSecondaire(modificationDonneesClientForm, paramModificationDonneesVo, personne);

            }
            //-----------------------------------------------------------------------//
            //------------- Modification du nom et prenom  ----------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOM))) {
                                validationNomPrenom(modificationDonneesClientForm, personne);
            }

            //-----------------------------------------------------------------------//
            //---------- Modification de l'activite ---------------------------------//
            //-----------------------------------------------------------------------//
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_ACTIVITE))) {
                validationActivete(modificationDonneesClientForm, personne, separteur);
                
            }

            //---------------------------------------------------------------//
            //------------------------ Contact ------------------------------// 
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CONTACT))) {
                validationContact(modificationDonneesClientForm, personne);
                
            }

            //---------------------------------------------------------------//
            //------------------------ Qualité  -----------------------------// 
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_QUALITE))) {
                
                validationQualite(modificationDonneesClientForm, personne);
            }
            //---------------------------------------------------------------//
            //------------------------  Complémentaire   --------------------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_COMPLEMENTAIRE))) {
               
                validationDonneesComplementaire(modificationDonneesClientForm, paramModificationDonneesVo, personne);   
                
            }

            //-----------------------------------------------------------------//
            //----------------  nominations complementaires -----------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_NOMINAT_COMP))) {
                validationNominationComplementaire(modificationDonneesClientForm, personne);   
            }

            //-----------------------------------------------------------------//
            //----------------  Données sociales        -----------------------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_SOCIALE))) {
                validationDonneesSociale(modificationDonneesClientForm, personne);
            }

            //---------------------------------------------------------------//
            //-----------  Raison social pour les personne Morale------------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_RAISON_SOCIALE_PM))) {
                validationRaisonSocialPersonneMorale(modificationDonneesClientForm, personne);
                
            }

            //---------------------------------------------------------------//
            //-----------  Forme juridique pour les personnes Morales   -----// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_FORME_JUR_PM))) {
                
                validationFormeJuridique(modificationDonneesClientForm, personne);
                
            }


            //---------------------------------------------------------------//
            //-----------  Code et Matricule pour les personnes Morales -----// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_MATRICULE_PM))) {
                
                validationCodeMatricule(modificationDonneesClientForm, paramModificationDonneesVo, personne);
            }

            //---------------------------------------------------------------//
            //-----------  Modification Groupe et Capital--------------------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CAPITAL_GROUP))) {
                validationGroupeCapital(modificationDonneesClientForm, paramModificationDonneesVo, personne);
                
            }
            
            //---------------------------------------------------------------//
            //-----------  Changement Categorie -----------------------------// 

            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE))) {
                 testValidation = validationChangementCategoriePersonne(modificationDonneesClientForm, paramModificationDonneesVo, personne, testValidation );
                
            }
            //-----------------------------------------------------------//
            //-------------- Affecter la structure-----------------------//
             ParamAgence paramAgence = 
                 (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
             
             paramModificationDonneesVo.setCodeStructure(paramAgence.getCodStrcStrc());
             ModificationDonneesClientCmd modificationDonneesClientCmd = 
                new ModificationDonneesClientCmd();
            //---------------------------------------------------------------------//
            //--------------- Lancer la modification  -----------------------------//
            //---------------------------------------------------------------------//
            if (testValidation) {
                paramModificationDonneesVo.setPersonneModifie(personne);
                paramModificationDonneesVo.setTypeModification(modificationDonneesClientForm.getTypeModification());
                paramModificationDonneesVo.setMatriculeUser(modificationDonneesClientForm.getMatriculeUser());

                //-------- cas de modification @dresse coresspondance
                List l = 
                    modificationDonneesClientForm.getListeDesContratsAmodifierAdresse();
                if (modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_CORR)) {
                    String vContratChoisi;
                    String vcodStrcStrc;
                    String vcodPrdPrd;
                    String vnumCcptCcpt;
                    StringBuffer text = new StringBuffer();
                    text.append("Le(s) contrat(s) modifié(s) : ");
                    for (Iterator it = 
                         modificationDonneesClientForm.getListeDesContratsAmodifierAdresse().iterator(); 
                         it.hasNext(); ) {
                        vContratChoisi = (String)it.next();
                        //--- execution si un contrat existe
                        if (!vContratChoisi.equals("")) {
                            vcodStrcStrc = "";
                            vcodPrdPrd = "";
                            vnumCcptCcpt = "";
                            vcodStrcStrc = 
                                    vContratChoisi.substring(0, vContratChoisi.indexOf("_"));
                            vcodPrdPrd = 
                                    vContratChoisi.substring(vContratChoisi.indexOf("_") + 
                                                             1, 
                                                             vContratChoisi.indexOf("*"));
                            vnumCcptCcpt = 
                                    vContratChoisi.substring(vContratChoisi.indexOf("*") + 
                                                             1, 
                                                             vContratChoisi.length());
                            
                            text.append(StrHandler.lpad(vcodStrcStrc,'0',3) + " "+ StrHandler.lpad(vcodPrdPrd,'0',4)+" "+StrHandler.lpad(vnumCcptCcpt,'0',6)+", ");
                           
                            //--------------------- rechercher le contrat ----------------------------
                            for (Iterator itCpt = 
                                 modificationDonneesClientForm.getListeDesContrats().iterator(); 
                                 itCpt.hasNext(); ) {
                                ContratCptView contratCptView = 
                                    (ContratCptView)itCpt.next();

                                if (contratCptView.getContratCpt().getContratCptId().getCodStrcStrc().equals(Long.valueOf(vcodStrcStrc)) && 
                                    contratCptView.getContratCpt().getContratCptId().getCodPrdPrd().equals(Long.valueOf(vcodPrdPrd)) && 
                                    contratCptView.getContratCpt().getContratCptId().getNumCcptCcpt().equals(Long.valueOf(vnumCcptCcpt))) {
                                    contratCptView.getContratCpt().setAdresseCorresp(adresseCorrespondance);
                                    paramModificationDonneesVo.setContratModifie(contratCptView.getContratCpt());
                                    
                                    
                                } //end if 
                            } // end for   
                            //---------------------Fin recherche contrat -----------------------------        


                            paramModificationDonneesVo = 
                                    (ParamModificationDonneesVo)modificationDonneesClientCmd.execute(paramModificationDonneesVo);
                        } //-- fin si contrat existe
                        
                    }modificationDonneesClientForm.setLibelleConfirmation1(text.toString());
                } else {

                    if (modificationDonneesClientForm.getContratModifie() != 
                        null) {
                        paramModificationDonneesVo.setContratModifie(modificationDonneesClientForm.getContratModifie());
                    }

                  
                   
                    paramModificationDonneesVo = 
                            (ParamModificationDonneesVo)modificationDonneesClientCmd.execute(paramModificationDonneesVo);
                }

                if (!paramModificationDonneesVo.hasError()) {

                    StringBuffer text = new StringBuffer();
                    text.append("La ");
                    text.append(modificationDonneesClientForm.getTypeModification().getLibModfModf());
                    if (! modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_ID))){
                      
                      if (modificationDonneesClientForm.getTypeModification().getCodTypModf().equalsIgnoreCase("personne")) {
                        text.append(" pour  " );
                        if (modificationDonneesClientForm.getPersonne().getLibTitrPers() != null ){
                            text.append(modificationDonneesClientForm.getPersonne().getLibTitrPers() +" ");
                        }
                        if (modificationDonneesClientForm.getNomPersonne()  != null  && ! modificationDonneesClientForm.getNomPersonne().equals("") ){
                            text.append(modificationDonneesClientForm.getNomPersonne()+" ");
                        }
                        if (modificationDonneesClientForm.getPrenomPersonne()  != null && ! modificationDonneesClientForm.getPrenomPersonne().equals("")  ){
                              text.append(modificationDonneesClientForm.getPrenomPersonne()+" ");
                         }
                         if (modificationDonneesClientForm.getRaisonSocial()  != null && ! modificationDonneesClientForm.getRaisonSocial().equals("")  ){
                             text.append(modificationDonneesClientForm.getRaisonSocial()+" ");
                         }
                    
                        text.append(" ayant le type de pièce ");
                        
                        GetTypePieceCmd getTypePiece = new GetTypePieceCmd();
                        TypePiece typePiece = new TypePiece();
                        typePiece.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getTypePiece()));
                        typePiece=  (TypePiece)getTypePiece.execute(typePiece);
                        text.append(typePiece.getLibTpceTpce());
                        
                        text.append(" avec le numéro : ");
                        text.append(modificationDonneesClientForm.getNumeroPiece());

                      } else if (modificationDonneesClientForm.getTypeModification().getCodTypModf().equalsIgnoreCase("contrat")) {
                        text.append(" pour le contrat ");
                        text.append(modificationDonneesClientForm.getContratModifie().getContratCptId().getCodStrcStrc());
                        text.append(" ");
                        text.append(modificationDonneesClientForm.getContratModifie().getContratCptId().getCodPrdPrd());
                        text.append(" ");
                        text.append(modificationDonneesClientForm.getContratModifie().getContratCptId().getNumCcptCcpt());
                      }
                    }else {// fin if changement Id
                     text = new StringBuffer();
                     text.append("Le ");
                     text.append(modificationDonneesClientForm.getTypeModification().getLibModfModf());
                    
                    }
                    text.append(" a été effectuée avec succès");
                    modificationDonneesClientForm.setLibelleConfirmation(text.toString());
                    return mapping.findForward("confirmationModification");

                    //************* Une erreur est survenu ******************** 
                } else {
                    List listErreur = paramModificationDonneesVo.getErrors();                    
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                     }    
                     this.saveMessages(request, actionMessages);
                     return mapping.findForward("error");                       
                }
            } // fin if testValidation
            if (modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_RES) || 
                modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_CORR)) {
                return mapping.findForward("modificationAdresse");

            } else if (modificationDonneesClientForm.getTypePersonne() != 
                       null && 
                       modificationDonneesClientForm.getTypePersonne().equals("PM")) {
                return mapping.findForward("modificationPersMoral");
            } else {
                return mapping.findForward("initierPage");
            }
       
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            if (modificationDonneesClientForm.getTypePersonne() != null && 
                modificationDonneesClientForm.getTypePersonne().equals("PM")) {
                return mapping.findForward("error");
            } else {
                return mapping.findForward("error");
            }
        }

    }

    private boolean validationAjoutNouvellePieceAnnexe(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                       ParamModificationDonneesVo paramModificationDonneesVo, 
                                                       boolean testValidation) {
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
        
        for (Iterator it = 
             modificationDonneesClientForm.getListDesPiecesSecondaire().iterator(); 
             it.hasNext(); ) {
            PieceAnnexeView anciennePieceView = 
                (PieceAnnexeView)it.next();
            PieceAnnexe anciennePiece = 
                anciennePieceView.getPieceAnnexe();

            if (anciennePiece.getPieceAnnexeId().getNumSeqPers().equals(Long.valueOf(modificationDonneesClientForm.getPersonne().getNumSeqPers())) && 
                anciennePiece.getPieceAnnexeId().getCodTpceTpce().equals(Long.valueOf(modificationDonneesClientForm.getCodeTypePieceAnnexe()))) {
                testValidation = false;
                modificationDonneesClientForm.setMessage("PieceExiste");
                break;
            }
        }// Fin for    
        if (testValidation){
            PieceAnnexe nouvellePieceAnnexe = 
                new PieceAnnexe();
            PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
            pieceAnnexeId.setNumPcePian(modificationDonneesClientForm.getNumeroPieceAnnexe());
            pieceAnnexeId.setNumSeqPers(modificationDonneesClientForm.getPersonne().getNumSeqPers());
            pieceAnnexeId.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getCodeTypePieceAnnexe()));
            nouvellePieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
            nouvellePieceAnnexe.setDatDelvPian(DateHandler.strToDate(modificationDonneesClientForm.getDateDelivrancePieceAnnexe()));
            nouvellePieceAnnexe.setDatFvalPian(DateHandler.strToDate(modificationDonneesClientForm.getDateFinValiditePieceAnnexe()));
            paramModificationDonneesVo.setNouvellePieceAnnexe(nouvellePieceAnnexe);
           
            GetTypePieceCmd getTypePieceCmd = new GetTypePieceCmd();
            TypePiece  typePiece = new TypePiece();
            typePiece.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getCodeTypePieceAnnexe()));
            typePiece =(TypePiece) getTypePieceCmd.execute(typePiece);
            textNew.append(" Le type de la pièce : " +  typePiece.getLibSiglTpce()+"; ");
            textNew.append(" Le numéro de la pièce : " + modificationDonneesClientForm.getNumeroPieceAnnexe()+"; " );
            textNew.append(" date de délivrance : " + modificationDonneesClientForm.getDateDelivrancePieceAnnexe()+"; " );
            textNew.append(" date de fin de validité : " +modificationDonneesClientForm.getDateFinValiditePieceAnnexe()+"; " );
        }
        
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
        return testValidation;
    }

    private boolean validationIPieceAnnexeEtIdSecondaire(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                      ParamModificationDonneesVo paramModificationDonneesVo, 
                                                      Personne personne) {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
        
        //------- Chercher la pièce annexe modifiée parmis la liste des pièces --------
        if (!modificationDonneesClientForm.getCodeTypePieceAnnexe().equalsIgnoreCase(String.valueOf(Constants.COD_CIN))) {
            for (Iterator it = 
                 modificationDonneesClientForm.getListDesPiecesSecondaire().iterator(); 
                 it.hasNext(); ) {
                PieceAnnexeView anciennePieceView = 
                    (PieceAnnexeView)it.next();
                PieceAnnexe anciennePiece = 
                    anciennePieceView.getPieceAnnexe();

                if (anciennePiece.getPieceAnnexeId().getNumSeqPers().equals(Long.valueOf(modificationDonneesClientForm.getPersonne().getNumSeqPers())) && 
                    anciennePiece.getPieceAnnexeId().getNumPcePian().equals(modificationDonneesClientForm.getAncNumeroPieceAnnexe()) && 
                    anciennePiece.getPieceAnnexeId().getCodTpceTpce().equals(Long.valueOf(modificationDonneesClientForm.getCodeTypePieceAnnexe()))) {
                  
                      if (anciennePiece.getTypePiece() != null && anciennePiece.getTypePiece().getLibSiglTpce() !=null ){
                          textAnc.append(" Le type de la pièce est : " + anciennePiece.getTypePiece().getLibSiglTpce()+" ; ");
                          textNew.append(" Le type de la pièce est : " + anciennePiece.getTypePiece().getLibSiglTpce()+" ; "); 
                      }
                      
                    
                     if (anciennePiece.getPieceAnnexeId() != null && anciennePiece.getPieceAnnexeId().getNumPcePian() != null ){
                           textAnc.append(" Le numéro de la pièce est : " + anciennePiece.getPieceAnnexeId().getNumPcePian()+" ; ");
                     }
                     
                     if (modificationDonneesClientForm.getNumeroPieceAnnexe() != null ){
                          textNew.append(" Le numéro de la pièce est : " + modificationDonneesClientForm.getNumeroPieceAnnexe()+" ; ");
                     }  
                    if (anciennePiece.getDatDelvPian() != null){
                        textAnc.append(" La date de délivrance  : " + DateHandler.dateToStr(anciennePiece.getDatDelvPian())+" ; ");
                    }
                    if (anciennePiece.getDatDelvPian() != null){
                       textNew.append(" La date de délivrance  : " + modificationDonneesClientForm.getDateDelivrancePieceAnnexe()+" ; ");
                    }   
                    
                    if (anciennePiece.getDatFvalPian() != null){
                            textAnc.append(" La date de fin de validité : " + DateHandler.dateToStr(anciennePiece.getDatFvalPian())+" ; ");
                    }
                    if (anciennePiece.getDatDelvPian() != null){
                           textNew.append(" La date de fin de validité  : " + modificationDonneesClientForm.getDateFinValiditePieceAnnexe()+" ; ");
                    }
               
                    paramModificationDonneesVo.setNumeroPieceAnnexeAncien(anciennePiece.getPieceAnnexeId().getNumPcePian());
                    paramModificationDonneesVo.setNumeroPieceAnnexenouvelle(modificationDonneesClientForm.getNumeroPieceAnnexe());
                    paramModificationDonneesVo.setNumSeqPesAnnexe(anciennePiece.getPieceAnnexeId().getNumSeqPers());
                    paramModificationDonneesVo.setCodeTypePieceAnnexe(anciennePiece.getPieceAnnexeId().getCodTpceTpce());
                    paramModificationDonneesVo.setDateDelivranceAnnexe(DateHandler.strToDate(modificationDonneesClientForm.getDateDelivrancePieceAnnexe()));
                    paramModificationDonneesVo.setDateFinValiditeAnnexe(DateHandler.strToDate(modificationDonneesClientForm.getDateFinValiditePieceAnnexe()));
                    
              } // fin if 
            } // fin for

        } else { // la modification d'une CIN
          boolean testValidation = true ;
             //--------------------------------------------------------------------- 
             //-- verifier si une personne existe déja avec le type et le numero
             if  (! modificationDonneesClientForm.getNumeroPieceAnnexe().equals(modificationDonneesClientForm.getAncNumPcePers())) {
    
                 PersonneStrc personneStrc = new PersonneStrc();
                 personneStrc.setCodTpceTpce(Long.valueOf(Constants.COD_CIN));
                 personneStrc.setNumPcePers(modificationDonneesClientForm.getNumeroPieceAnnexe());
    
                 GetPersonneCptCmd getPersonneCptCmd = 
                     new GetPersonneCptCmd();
                 PersonneCpt personneCpt = 
                     (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
    
                 if (personneCpt.getPersonne() != null) {
                     testValidation = false;
                     modificationDonneesClientForm.setMessage("IdPrincipalExist");
                     return false;
                 }
             } //fin  verifier si une personne existe
            
            
            if (testValidation) {
            
                    if (personne.getNumPcePers()!= null){
                        textAnc.append(" Le type de la pièce : C.I.N ;");
                        textNew.append(" Le type de la pièce : C.I.N ; " );
                    }
                    
                    if (personne.getNumPcePers()!= null){
                        textAnc.append(" Le numéro de la pièce : "+personne.getNumPcePers() +"; " );
                    }
                    if (modificationDonneesClientForm.getNumeroPieceAnnexe() != null){
                        textNew.append(" Le numéro de la pièce : "+modificationDonneesClientForm.getNumeroPieceAnnexe()+"; " );
                    }
                    
                    personne.setNumPcePers(modificationDonneesClientForm.getNumeroPieceAnnexe());
                    
                    if (personne.getDatDlvPers() != null){
                        textAnc.append(" Date de délivrance : "+ DateHandler.dateToStr(personne.getDatDlvPers()) +"; " );
                    }
                    if (modificationDonneesClientForm.getDateDelivrancePieceAnnexe() != null){
                        textNew.append(" Date de délivrance : " + modificationDonneesClientForm.getDateDelivrancePieceAnnexe() +"; " );
                    }
                    personne.setDatDlvPers(DateHandler.strToDate(modificationDonneesClientForm.getDateDelivrancePieceAnnexe()));
                    
                    if (personne.getGouvernorat() != null && personne.getGouvernorat().getLibGouvGouv() != null ){
                        textAnc.append(" Le gouvernorat : "+ personne.getGouvernorat().getLibGouvGouv()+"; "  );
                    }
                    if (modificationDonneesClientForm.getLieuDelivrance()  != null){
                        textNew.append(" Le gouvernorat : "+modificationDonneesClientForm.getLieuDelivrance() +"; "  );
                    }
                    
                    Gouvernorat gouvernorat = new Gouvernorat();
                    gouvernorat.setCodGouvGouv(Long.valueOf(modificationDonneesClientForm.getCodGouvGouvPiece()));
                    personne.setGouvernorat(gouvernorat);
                
                    PieceAnnexe nouvellePieceAnnexe = new PieceAnnexe();
                    PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
                    pieceAnnexeId.setNumPcePian(modificationDonneesClientForm.getNumeroPieceAnnexe());
                    pieceAnnexeId.setNumSeqPers(modificationDonneesClientForm.getPersonne().getNumSeqPers());
                    pieceAnnexeId.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getCodeTypePieceAnnexe()));
                    nouvellePieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
                    paramModificationDonneesVo.setCodeTypePieceAnnexe(Constants.COD_CIN);
                    paramModificationDonneesVo.setNouvellePieceAnnexe(nouvellePieceAnnexe);
            }// fin test Validation          
        }// fin cin
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
        return true;
    }

    private void validationCodeMatricule(ModificationDonneesClientForm modificationDonneesClientForm, 
                                         ParamModificationDonneesVo paramModificationDonneesVo, 
                                         Personne personne) throws Exception {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        
                
        if (modificationDonneesClientForm.getClient() != null) {
            Client client = modificationDonneesClientForm.getClient();
            
            if (client.getCodDoanClt() != null ){
                textAnc.append(" Code en douane : "+ client.getCodDoanClt()+"; ");
            }
            if (modificationDonneesClientForm.getCodDoanClt() != null && (! modificationDonneesClientForm.getCodDoanClt().equals("")) ){
                textNew.append(" Code en douane : "+ modificationDonneesClientForm.getCodDoanClt()+"; ");
            }
        
            if (modificationDonneesClientForm.getCodDoanClt() != null) {
                    client.setCodDoanClt(modificationDonneesClientForm.getCodDoanClt());
             }
             
            if (client.getNumFiscClt() != null ){
                textAnc.append(" Matricule fiscal : "+ client.getNumFiscClt()+"; ");
            }
            if (modificationDonneesClientForm.getNumFiscClt() != null && (! modificationDonneesClientForm.getNumFiscClt().equals("")) ){
                textNew.append(" Matricule fiscal : "+ modificationDonneesClientForm.getNumFiscClt()+"; ");
            }          
            if (modificationDonneesClientForm.getNumFiscClt() !=    null) {
                    client.setNumFiscClt(modificationDonneesClientForm.getNumFiscClt());
             }
        
            if (client.getNumBctClt() != null ){
                textAnc.append(" Numéro BCT : "+ client.getNumBctClt()+"; ");
            }
            if (modificationDonneesClientForm.getNumBctClt() != null && (! modificationDonneesClientForm.getNumBctClt().equals("")) ){
                textNew.append(" Numéro BCT : "+ modificationDonneesClientForm.getNumBctClt()+"; ");
            }
            
            if (modificationDonneesClientForm.getNumBctClt() !=  null) {
                    client.setNumBctClt(modificationDonneesClientForm.getNumBctClt());
             }
           
            if (client.getNumRnePers() != null ){
                textAnc.append(" Identifiant National (RNE) : "+ client.getNumRnePers()+"; ");
            }
            if (modificationDonneesClientForm.getNumRnePers() != null && (! modificationDonneesClientForm.getNumRnePers().equals("")) ){
                textNew.append(" Identifiant National (RNE) : "+ modificationDonneesClientForm.getNumRnePers()+"; ");
            }
            
            if (modificationDonneesClientForm.getNumRnePers() != null) {
                personne.setNumRnePers(modificationDonneesClientForm.getNumRnePers());
            }
            if (modificationDonneesClientForm.getNumRnePers() !=  null) {
                    client.setNumRnePers(modificationDonneesClientForm.getNumRnePers());
             }
          
            paramModificationDonneesVo.setClientModifie(client);
        }
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationGroupeCapital(ModificationDonneesClientForm modificationDonneesClientForm, 
                                         ParamModificationDonneesVo paramModificationDonneesVo, 
                                         Personne personne) throws Exception  {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        if (personne.getDatCapPers() != null ){
            textAnc.append(" Date de variation du capital : " + DateHandler.dateToStr(personne.getDatCapPers())+"; " );
        }
        
        if (modificationDonneesClientForm.getDatCapPers()!= null && (! modificationDonneesClientForm.getDatCapPers().equals("")) ){
          textNew.append(" Date de variation du capital : " + modificationDonneesClientForm.getDatCapPers() +"; ");
        }
        
        
        if (modificationDonneesClientForm.getDatCapPers() != null &&(! modificationDonneesClientForm.getDatCapPers().equals(""))) {
            personne.setDatCapPers(DateHandler.strToDate(modificationDonneesClientForm.getDatCapPers()));
        }
        
        if (personne.getMontCapPers() != null ){
            textAnc.append(" Montant du capital : " +personne.getMontCapPers()+"; " );
        }
        
        if (modificationDonneesClientForm.getMontCapPers()!= null && (! modificationDonneesClientForm.getMontCapPers().equals("")) ){
          textNew.append(" Montant du capital : " +modificationDonneesClientForm.getMontCapPers()+"; " );
        }
        
        if (modificationDonneesClientForm.getMontCapPers() != null && (!modificationDonneesClientForm.getMontCapPers().equals(""))) {
            personne.setMontCapPers(Long.valueOf(StrHandler.strToMnt(modificationDonneesClientForm.getMontCapPers())));
        }
        if (modificationDonneesClientForm.getClient() != null) {
            Client client = modificationDonneesClientForm.getClient();
                    
          if (client.getGroupe() != null  && client.getGroupe().getNomRsGrp() != null ){
              textAnc.append(" Groupe : " + client.getGroupe().getNomRsGrp()+"; " );
          }
          
          if (modificationDonneesClientForm.getNomRsGrp()!= null && (! modificationDonneesClientForm.getNomRsGrp().equals("")) ){
              textNew.append(" Groupe : " + modificationDonneesClientForm.getNomRsGrp() +"; ");
          }
        
          if (modificationDonneesClientForm.getCodGrpGrp() !=null &&(! modificationDonneesClientForm.getNomRsGrp().equals(""))) {          
               Groupe groupe = new Groupe();
               groupe.setCodGrpGrp(Long.valueOf(modificationDonneesClientForm.getCodGrpGrp()));
               client.setGroupe(groupe);
          }else {
              client.setGroupe(null);
          }
                paramModificationDonneesVo.setClientModifie(client);
            
        }
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }


    private boolean validationChangementCategoriePersonne(ModificationDonneesClientForm modificationDonneesClientForm, 
                                         ParamModificationDonneesVo paramModificationDonneesVo, 
                                         Personne personne , boolean testValidation) throws Exception  {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        
        GetCategoriePersonneCmd getCategoriePersonneCmd = new GetCategoriePersonneCmd();
        CategoriePersonne       ancCetegoriePersonne = new CategoriePersonne();
        CategoriePersonne       newCetegoriePersonne = new CategoriePersonne();
        
        if (personne.getCategoriePersonne().getLibCatpCatp()  != null ){
            textAnc.append(" Catégorie : " + personne.getCategoriePersonne().getLibCatpCatp()+"; " );
        }
        
             
        if ( modificationDonneesClientForm.getNouvelleCodCatpCatp()  != null ){
            newCetegoriePersonne.setCodCatpCatp(modificationDonneesClientForm.getNouvelleCodCatpCatp());
            newCetegoriePersonne = (CategoriePersonne)getCategoriePersonneCmd.execute(newCetegoriePersonne);
            
            textNew.append(" Catégorie : " + newCetegoriePersonne.getLibCatpCatp() +"; ");
        }
        
        personne.getCategoriePersonne().setCodCatpCatp(modificationDonneesClientForm.getNouvelleCodCatpCatp());

        textAnc.append(" Identifiant : " + personne.getTypePiece().getLibSiglTpce() +" : " +personne.getNumPcePers());
        
        //--------------------------------------------------------------------- 
        //-- verifier si une personne existe déja avec le type et le numero
        if (modificationDonneesClientForm.getCodCatpCatp().equals(Constants.MINEUR) ||
            modificationDonneesClientForm.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR) ||
            modificationDonneesClientForm.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE) ||
            modificationDonneesClientForm.getCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MINEUR_EMANCIPE)){
            
            if (modificationDonneesClientForm.getAncCodTpceTpce().equals(Constants.COD_CIN.toString()) && ! modificationDonneesClientForm.getNumPcePers().equals("")) {
    
                PersonneStrc personneStrc = new PersonneStrc();
                personneStrc.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getAncCodTpceTpce()));
                personneStrc.setNumPcePers(modificationDonneesClientForm.getNumPcePers());
    
                GetPersonneCptCmd getPersonneCptCmd =  new GetPersonneCptCmd();
                PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
    
                if (personneCpt.getPersonne() != null) {
                    testValidation = false;
                    modificationDonneesClientForm.setMessage("IdPrincipalExist");
                    return false;
                 
                }else {
                    personne.getTypePiece().setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getAncCodTpceTpce()));
                    personne.setNumPcePers(modificationDonneesClientForm.getNumPcePers());
                    personne.setDatDlvPers(DateHandler.strToDate(modificationDonneesClientForm.getDatDlvPers()));
                    Gouvernorat gouv = new Gouvernorat();
                    gouv.setCodGouvGouv(Long.valueOf(modificationDonneesClientForm.getCodGouvGouv()));
                    personne.setGouvernorat(gouv);
                }
        
             //---------------------------------------------------------------------------------//
             //--------- Dans le cas d'un passeport ou  carte séjour pour les pp etrangère -----//
             //---------------------------------------------------------------------------------//
             
            } else if (!modificationDonneesClientForm.getAncCodTpceTpce().equals(Constants.COD_CIN.toString()) && ! modificationDonneesClientForm.getNumPcePers().equals("")){
            
                if (testValidation){
                    PieceAnnexe nouvellePieceAnnexe =  new PieceAnnexe();
                    PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
                    pieceAnnexeId.setNumPcePian(modificationDonneesClientForm.getNumPcePers() );
                    pieceAnnexeId.setNumSeqPers(modificationDonneesClientForm.getPersonne().getNumSeqPers());
                    pieceAnnexeId.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getAncCodTpceTpce()));
                    nouvellePieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
                    nouvellePieceAnnexe.setDatDelvPian(DateHandler.strToDate(modificationDonneesClientForm.getDatDlvPers() ));
                    nouvellePieceAnnexe.setDatFvalPian(DateHandler.strToDate(modificationDonneesClientForm.getDateFinValiditePieceAnnexe()));
                    paramModificationDonneesVo.setNouvellePieceAnnexe(nouvellePieceAnnexe);
                }
            }
         } // Fin cas Mineur       
        //-------------------------------------------------------------------//    
        //----- Si la personne est étrangère et elle devient Tunisienne -----//    
        //-------------------------------------------------------------------//    
        
        if (! (personne.getPaysByCodNat1Pays().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE)) ){
            if (modificationDonneesClientForm.getNouvelleCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR)||
                modificationDonneesClientForm.getNouvelleCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE)||
                modificationDonneesClientForm.getNouvelleCodCatpCatp().equals(Constants.MINEUR_EMANCIPE)){
                
                    //personne.setPaysByCodNat2Pays(personne.getPaysByCodNat1Pays());
                    personne.getPaysByCodNat1Pays().setCodPaysPays(Constants.COD_PAYS_TUNISIE);
                    personne.getFormeJuridique().setCodFjFj(Constants.COD_FORME_JURI_PERS_PHYS_TUNISIENNE);
                    modificationDonneesClientForm.setCodeNationalite(Constants.COD_PAYS_TUNISIE);
                    modificationDonneesClientForm.setCodFjFj(Constants.COD_FORME_JURI_PERS_PHYS_TUNISIENNE);
                    
             }
            
        }
        
        if (modificationDonneesClientForm.getAncCodTpceTpce() != null && ! modificationDonneesClientForm.getAncCodTpceTpce().equals("")){
            GetTypePieceCmd getTypePieceCmd = new GetTypePieceCmd();
            TypePiece typePiece = new TypePiece();
            typePiece.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getAncCodTpceTpce()));
            typePiece = (TypePiece) getTypePieceCmd.execute(typePiece);
            
            textNew.append(" Identifiant : " + typePiece.getLibSiglTpce() +" : " +modificationDonneesClientForm.getNumPcePers());
        }

        verifListeContratAmodifier(modificationDonneesClientForm);
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
        
        //-------------------------------------------------------//
        //----------- Verification des modification  -----------//
        // cas 1
         if (modificationDonneesClientForm.getNouvelleCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE) ||
             modificationDonneesClientForm.getNouvelleCodCatpCatp().equals(Constants.PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE) ){
              modificationDonneesClientForm.setMessageCreationMandat("true");
             }
       
        return true;
    }

    private void validationFormeJuridique(ModificationDonneesClientForm modificationDonneesClientForm, 
                                          Personne personne) throws Exception {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        GetCategoriePersonneCmd getCategoriePersonneCmd = new GetCategoriePersonneCmd();
        CategoriePersonne       ancCetegoriePersonne = new CategoriePersonne();
        CategoriePersonne       newCetegoriePersonne = new CategoriePersonne();
        
        GetFormeJuridiqueCmd    getFormeJuridique  = new GetFormeJuridiqueCmd();
        FormeJuridique          ancFormJuridique  = new FormeJuridique();
        FormeJuridique          newFormJuridique  = new FormeJuridique();
        
        if ( personne.getCategoriePersonne() != null &&  personne.getCategoriePersonne().getCodCatpCatp() != null ){
            ancCetegoriePersonne.setCodCatpCatp(personne.getCategoriePersonne().getCodCatpCatp());
            ancCetegoriePersonne = (CategoriePersonne)getCategoriePersonneCmd.execute(ancCetegoriePersonne);
            textAnc.append(" Catégorie : " + ancCetegoriePersonne.getLibCatpCatp() +"; ");
        }
        
        if ( modificationDonneesClientForm.getCodCatpCatp()  != null ){
            
            newCetegoriePersonne.setCodCatpCatp(modificationDonneesClientForm.getCodCatpCatp());
            newCetegoriePersonne = (CategoriePersonne)getCategoriePersonneCmd.execute(newCetegoriePersonne);
            
            textNew.append(" Catégorie : " + newCetegoriePersonne.getLibCatpCatp() +"; ");
        }
        personne.getCategoriePersonne().setCodCatpCatp(modificationDonneesClientForm.getCodCatpCatp());


        if ( personne.getFormeJuridique()!= null &&  personne.getFormeJuridique().getCodFjFj() != null ){
            ancFormJuridique.setCodFjFj (personne.getFormeJuridique().getCodFjFj());
            ancFormJuridique = (FormeJuridique) getFormeJuridique.execute(ancFormJuridique);
            textAnc.append(" Forme juridique : " + ancFormJuridique.getLibFjFj() +"; ");
        }
        
        if ( modificationDonneesClientForm.getCodFjFj() != null ){
            
            newFormJuridique.setCodFjFj (modificationDonneesClientForm.getCodFjFj());
            newFormJuridique = (FormeJuridique) getFormeJuridique.execute(newFormJuridique);
            
            textNew.append(" Forme juridique : " + newFormJuridique.getLibFjFj() +"; ");
        }
        
        
        personne.getFormeJuridique().setCodFjFj(modificationDonneesClientForm.getCodFjFj());
               
        verifListeContratAmodifier(modificationDonneesClientForm);
        
             
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationRaisonSocialPersonneMorale(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                      Personne personne) {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        if (personne.getNomRsPers() != null ){
          textAnc.append(" Raison sociale : " +personne.getNomRsPers() +"; " );
        }
        
        if (personne.getNomRsPers() != null ){
          textNew.append(" Raison sociale : " +modificationDonneesClientForm.getNomRsPers() +"; " );
        }
        
        personne.setNomRsPers(modificationDonneesClientForm.getNomRsPers());
        
        if (personne.getLibSiglPers() != null ){
          textAnc.append(" Sigle : " +personne.getLibSiglPers() +"; " );
        }
        
        if (modificationDonneesClientForm.getLibSiglPers() != null ){
          textNew.append(" Sigle : " +modificationDonneesClientForm.getLibSiglPers() +"; " );
        }
        
        personne.setLibSiglPers(modificationDonneesClientForm.getLibSiglPers());
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private boolean validationIdentifiantPrincipal(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                   Personne personne, 
                                                   boolean testValidation) throws Exception  {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        //--------------------------------------------------------------------- 
        //-- verifier si une personne existe déja avec le type et le numero
        if (!(modificationDonneesClientForm.getCodTpceTpce().equals(modificationDonneesClientForm.getAncCodTpceTpce())) || 
            !(modificationDonneesClientForm.getNumPcePers().equals(modificationDonneesClientForm.getAncNumPcePers()))) {

            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(modificationDonneesClientForm.getCodTpceTpce()));
            personneStrc.setNumPcePers(modificationDonneesClientForm.getNumPcePers());

            GetPersonneCptCmd getPersonneCptCmd = 
                new GetPersonneCptCmd();
            PersonneCpt personneCpt = 
                (PersonneCpt)getPersonneCptCmd.execute(personneStrc);

            if (personneCpt.getPersonne() != null) {
                testValidation = false;
                modificationDonneesClientForm.setMessage("IdPrincipalExist");
            }
        } //fin  verifier si une personne existe
        
        if ( personne.getNumPcePers() != null ){
            textAnc.append(" numéro de la pièce : " +personne.getNumPcePers() +"; " );
        }
        if(modificationDonneesClientForm.getNumPcePers()!= null ){
            textNew.append(" numéro de la pièce : " + modificationDonneesClientForm.getNumPcePers()+"; " )  ;
        }
        personne.setNumPcePers(modificationDonneesClientForm.getNumPcePers());
        Long l = 
            Long.valueOf(modificationDonneesClientForm.getCodTpceTpce());
            TypePiece tp = new TypePiece();
            tp.setCodTpceTpce(l);
            personne.setTypePiece(tp);
        
        if (personne.getDatDlvPers() != null ){
           textAnc.append(" Date de délivrance : " + DateHandler.dateToStr(personne.getDatDlvPers()) +"; " );
        }
        
        if (modificationDonneesClientForm.getDatDlvPers() != null ){
            textNew.append(" Date de délivrance : " + modificationDonneesClientForm.getDatDlvPers() +"; " );
        }
        personne.setDatDlvPers(DateHandler.strToDate(modificationDonneesClientForm.getDatDlvPers()));
        
        if (personne.getGouvernorat() != null && personne.getGouvernorat().getCodGouvGouv() != null ){
            Gouvernorat gouvernorat = new Gouvernorat();
            gouvernorat.setCodGouvGouv(Long.valueOf(personne.getGouvernorat().getCodGouvGouv()));
            GetGouvernoratCmd getGouvernoratCmd  = new GetGouvernoratCmd();
            gouvernorat = (Gouvernorat) getGouvernoratCmd.execute (gouvernorat);
            textAnc.append(" Gouvernorat : " +gouvernorat.getLibGouvGouv() +"; " );
        }
        
        Gouvernorat gouvernorat = new Gouvernorat();
        gouvernorat.setCodGouvGouv(Long.valueOf(modificationDonneesClientForm.getCodGouvGouv()));
        personne.setGouvernorat(gouvernorat);
        
        if (modificationDonneesClientForm.getLibGouvGouv() != null ){
            textNew.append(" Gouvernorat : " + modificationDonneesClientForm.getLibGouvGouv() +"; " );
        }
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
        return testValidation;
    }

    private void validationDonneesSociale(ModificationDonneesClientForm modificationDonneesClientForm, 
                                          Personne personne)  throws Exception {
        StringBuffer textAnc   = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew  = new StringBuffer(" Les nouvelles données :");
        
        // les noms secondaire
        
        //-- Nombre d'enfant
        if (personne.getNbrEnfPers() != null ){
            textAnc.append(" Nombre d'enfant : "+personne.getNbrEnfPers() +"; ");
        }
        
        if (modificationDonneesClientForm.getNbrEnfPers()!= null){
            textNew.append(" Nombre d'enfant : "+modificationDonneesClientForm.getNbrEnfPers()+"; " );
        }
        personne.setNbrEnfPers(Long.valueOf(modificationDonneesClientForm.getNbrEnfPers()));
        
        //-- numéro sociale
         if (personne.getNumAffsPers() != null ){
             textAnc.append(" Numéro affiliation sociale : "+personne.getNumAffsPers()+"; " );
         }
         
         if (modificationDonneesClientForm.getNumAffsPers()!= null){
             textNew.append(" Numéro affiliation sociale : "+modificationDonneesClientForm.getNumAffsPers()+"; " );
         }
        personne.setNumAffsPers(modificationDonneesClientForm.getNumAffsPers());
        
        //-- date affiliation sociale
         if (personne.getDatAffsPers() != null ){
             textAnc.append(" Date affiliation sociale : "+DateHandler.dateToStr(personne.getDatAffsPers()) +"; ");
         }
         
         if (modificationDonneesClientForm.getDatAffspers()!= null){
             textNew.append(" Date affiliation sociale : "+modificationDonneesClientForm.getDatAffspers()+"; " );
         }
        personne.setDatAffsPers(DateHandler.strToDate((modificationDonneesClientForm.getDatAffspers())));
        
        CatSocProf catSocProf = new CatSocProf();
        catSocProf.setCodCsprCspr(modificationDonneesClientForm.getCodCsprCspr());
        personne.setCatSocProf(catSocProf);
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationContact(ModificationDonneesClientForm modificationDonneesClientForm, 
                                   Personne personne) throws Exception {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données :" );
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
        
        //-- telephone
        if (personne.getNumTelPers() != null){
            textAnc.append(" Téléphone : " +personne.getNumTelPers() );
        }
        if (modificationDonneesClientForm.getNumTelPers() != null ){
            textNew.append(" Télephone : "+modificationDonneesClientForm.getNumTelPers() );
        }
        personne.setNumTelPers(modificationDonneesClientForm.getNumTelPers());
        
        //-- fax
         if (personne.getNumFaxPers() != null){
             textAnc.append(" Fax : " +personne.getNumFaxPers() );
         }
         if (modificationDonneesClientForm.getNumFaxPers() != null){
             textNew.append(" Fax : "+modificationDonneesClientForm.getNumFaxPers() );
         }
        personne.setNumFaxPers(modificationDonneesClientForm.getNumFaxPers());
        //-- mail 
        
         if (personne.getAdrMailPers() != null){
             textAnc.append(" Mail : " +personne.getAdrMailPers() );
         }
         if (modificationDonneesClientForm.getAdrMailPers() != null){
             textNew.append(" Mail : "+modificationDonneesClientForm.getAdrMailPers() );
         }
        personne.setAdrMailPers(modificationDonneesClientForm.getAdrMailPers());
        
        //-- swift 
         if (personne.getAdrSwiftPers() != null){
             textAnc.append(" SWIFT : " +personne.getAdrSwiftPers() );
         }
         if (modificationDonneesClientForm.getAdrSwiftPers() != null){
             textNew.append(" SWIFT : "+modificationDonneesClientForm.getAdrSwiftPers() );
         } 
        personne.setAdrSwiftPers(modificationDonneesClientForm.getAdrSwiftPers());
        
        //--- Telex
         if (personne.getAdrTlxPers() != null){
             textAnc.append(" Telex : " +personne.getAdrTlxPers() );
         }
         if (modificationDonneesClientForm.getAdrTlxPers()  != null){
             textNew.append(" Telex : "+modificationDonneesClientForm.getAdrTlxPers() );
         } 
        personne.setAdrTlxPers(modificationDonneesClientForm.getAdrTlxPers());
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationNomPrenom(ModificationDonneesClientForm modificationDonneesClientForm, 
                                     Personne personne) throws Exception   {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données :");
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
        if(personne.getLibTitrPers()!=null){
        textAnc.append(" Titre : "+personne.getLibTitrPers());
        
        }
        textNew.append(" Titre : "+modificationDonneesClientForm.getLibTitrPers());
        personne.setLibTitrPers(modificationDonneesClientForm.getLibTitrPers());
        
        if(personne.getNomNomPers()!=null){
        textAnc.append(" Nom : "+personne.getNomNomPers());
      
        }
        textNew.append(" Nom : "+modificationDonneesClientForm.getNomNomPers());
        personne.setNomNomPers(modificationDonneesClientForm.getNomNomPers());
        
        if (personne.getNomPrnPers()!=null){
        textAnc.append(" Prénom : "+personne.getNomPrnPers());
        }
        textNew.append(" Prénom : "+modificationDonneesClientForm.getNomPrnPers());
        personne.setNomPrnPers(modificationDonneesClientForm.getNomPrnPers());
        
        if (personne.getNomRsPers()  != null){
          textAnc.append(" Raison sociale : "+personne.getNomRsPers());
         }
        if(modificationDonneesClientForm.getNomRsPers() != null && !modificationDonneesClientForm.getNomRsPers().equals("")){
        textNew.append(" Raison sociale : "+modificationDonneesClientForm.getNomRsPers());
        personne.setNomRsPers(modificationDonneesClientForm.getNomRsPers());
        }
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationActivete(ModificationDonneesClientForm modificationDonneesClientForm, 
                                    Personne personne, String separteur) throws Exception  {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données : " );
        StringBuffer textNew = new StringBuffer(" Les nouvelles données : " );
        
        //--secteur
        if (personne.getCodSectPers() != null){
         if (personne.getCodSectPers().equalsIgnoreCase("P")){
            textAnc.append(" Secteur : public; "+separteur);
         }else {
             textAnc.append(" Secteur : privé; "+separteur);
         }
        }
        
        personne.setCodSectPers(modificationDonneesClientForm.getCodSectPers());
        if (modificationDonneesClientForm.getCodSectPers().equalsIgnoreCase("P")){
           textNew.append(" Secteur : public; "+ separteur);
        }else {
            textNew.append(" Secteur : privé; "+separteur);
        }
        
        //--Profession
        if (personne.getProfession() != null && 
            modificationDonneesClientForm.getCodProfProf() != null) {
            textAnc.append(" Profession : " + personne.getProfession().getLibProfProf()+"; ");
            
            ProfessionId professionId = new ProfessionId();
            Profession profession = new Profession();

            professionId.setCodProfProf(Long.valueOf(modificationDonneesClientForm.getCodProfProf()));
            professionId.setCodGproGpro(Long.valueOf(modificationDonneesClientForm.getCodGproGpro()));
            textNew.append(" Profession : "+ modificationDonneesClientForm.getLibProfession()+"; ");
            profession.setProfessionId(professionId);
            personne.setProfession(profession);
        }
        //--Activite
        Activite activite = new Activite();
        ActiviteId activiteId = new ActiviteId();
        textAnc.append(" Activité : " + personne.getActivite().getLibActAct()+"; "+separteur);
        
        activiteId.setCodActAct(modificationDonneesClientForm.getCodActAct());
        activiteId.setCodCactCact(modificationDonneesClientForm.getCodCactCact());
        activiteId.setCodSactSact(Long.valueOf(modificationDonneesClientForm.getCodSactSact()));
        
        textNew.append(" Activité : "+ modificationDonneesClientForm.getLibActivite()+"; "+separteur);
        activite.setActiviteId(activiteId);
        personne.setActivite(activite);
        
        // Employeur
        if (personne.getEmployeur() != null ){
            textAnc.append(" Employeur : " + personne.getEmployeur().getLibEmpEmp()+"; "+separteur);
        }
        if (modificationDonneesClientForm.getCodEmpEmp()!= null && (!modificationDonneesClientForm.getCodEmpEmp().equalsIgnoreCase(""))){
        Employeur employeur = new Employeur();
        textNew.append(" Employeur : "+ modificationDonneesClientForm.getLibEmployeur() +"; "+separteur);
        employeur.setCodEmpEmp(Long.valueOf(modificationDonneesClientForm.getCodEmpEmp()));
        personne.setEmployeur(employeur);

        }
        
        if (personne.getRevRevPers() != null ){
           textAnc.append(" Revenu : " +StrHandler.formatmnt(personne.getRevRevPers())+"; ");
        }
        
        if (modificationDonneesClientForm.getRevRevPers()!= null && (!modificationDonneesClientForm.getRevRevPers().equalsIgnoreCase(""))){
           textNew.append(" Revenu : "+ StrHandler.formatmnt(Long.valueOf(modificationDonneesClientForm.getRevRevPers())) +"; "+separteur);
           personne.setRevRevPers(Double.valueOf(modificationDonneesClientForm.getRevRevPers())); 
       }
       
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationQualite(ModificationDonneesClientForm modificationDonneesClientForm, 
                                   Personne personne) throws Exception {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données :" );
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
    
    
        if (personne.getPaysByCodNat1Pays()!=null && personne.getPaysByCodNat1Pays().getLibNatPays() != null ){
            textAnc.append(" Nationalité : " + personne.getPaysByCodNat1Pays().getLibNatPays() +"; ");
        }
        
        if (modificationDonneesClientForm.getLibelleNationalite()!=null){
            textNew.append(" Nationalité : "+ modificationDonneesClientForm.getLibelleNationalite()+"; ");
        }
        
        if (personne.getBoolResPers()!= null){
         if (personne.getBoolResPers().equals(Constants.COD_RESIDENT)){
            textAnc.append(" Résidence : résident; "  );
         } else {
            textAnc.append(" Résidence : non résident; "  );
         }
        }
        
        if (modificationDonneesClientForm.getBoolResPers()!= null){
          if (modificationDonneesClientForm.getBoolResPers().equals(Constants.COD_RESIDENT)){
             textNew.append(" Résidence : résident; "  );
          } else {
             textNew.append(" Résidence : non résident; "  );
          }
        }
        
        personne.setBoolResPers(Long.valueOf(modificationDonneesClientForm.getBoolResPers()));
        

        
        Pays paysNationalite = new Pays();
        paysNationalite.setCodPaysPays(modificationDonneesClientForm.getCodeNationalite());
        personne.setPaysByCodNat1Pays(paysNationalite);
        
        if (personne.getSegment() != null && personne.getSegment().getLibSegSeg()!=null){
            textAnc.append(" Segment : " + personne.getSegment().getLibSegSeg()+"; " );
        }
        
        if (modificationDonneesClientForm.getCodSegSeg() != null && 
            (!modificationDonneesClientForm.getCodSegSeg().equals("")) && 
            modificationDonneesClientForm.getCodSsegSseg() != null && 
            (!modificationDonneesClientForm.getCodSsegSseg().equals("")) && 
            modificationDonneesClientForm.getCodCsegCseg() != null && 
            (!modificationDonneesClientForm.getCodCsegCseg().equals(""))) {
            Segment segment = new Segment();
            SegmentId segmentId = new SegmentId();
            segmentId.setCodSegSeg(Long.valueOf(modificationDonneesClientForm.getCodSegSeg()));
            segmentId.setCodSsegSseg(Long.valueOf(modificationDonneesClientForm.getCodSsegSseg()));
            segmentId.setCodCsegCseg(Long.valueOf(modificationDonneesClientForm.getCodCsegCseg()));
            
            textNew.append(" Segment : "+ modificationDonneesClientForm.getLibSegSeg()+"; " );
            segment.setSegmentId(segmentId);
            personne.setSegment(segment);
        
        }
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
        verifListeContratAmodifier(modificationDonneesClientForm);
    }

    private void validationDonneesComplementaire(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                 ParamModificationDonneesVo paramModificationDonneesVo, 
                                                 Personne personne) throws Exception {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données : " );
        StringBuffer textNew = new StringBuffer(" Les nouvelles données : ");
        
        // Situation familiale
        if (personne.getCodSitfPers() != null ){
            if (personne.getCodSitfPers().equalsIgnoreCase("M")){
                textAnc.append(" Situation familiale : marié(e);" );
            } else if  (personne.getCodSitfPers().equalsIgnoreCase("D")){
                textAnc.append(" Situation familiale : divorcé(e);" );
            }else if (personne.getCodSitfPers().equalsIgnoreCase("C")){
                textAnc.append(" Situation familiale : célibataire;" );
            }else if (personne.getCodSitfPers().equalsIgnoreCase("V")){
                textAnc.append(" Situation familiale : Voeuf(ve);" );
            }else {
                textAnc.append(" Situation familiale : Non indiquée; ");
            }
        }    
            
        if (modificationDonneesClientForm.getCodSitfPers() != null ){
            if (modificationDonneesClientForm.getCodSitfPers().equalsIgnoreCase("M")){
                textNew.append(" Situation familiale : marié(e);" );
            } else if  (modificationDonneesClientForm.getCodSitfPers().equalsIgnoreCase("D")){
                textNew.append(" Situation familiale : divorcé(e);" );
            }else if (modificationDonneesClientForm.getCodSitfPers().equalsIgnoreCase("C")){
                textNew.append(" Situation familiale : célibataire;" );
            }else if (modificationDonneesClientForm.getCodSitfPers().equalsIgnoreCase("V")){
                    textNew.append(" Situation familiale : Voeuf(ve);" );
            }else {
                textNew.append(" Situation familiale : Non indiquée; ");
            }
        }
        personne.setCodSitfPers(modificationDonneesClientForm.getCodSitfPers());
        //  deuxième Nationalité
        if (personne.getPaysByCodNat2Pays() != null && personne.getPaysByCodNat2Pays().getLibNatPays() != null ){
            textAnc.append(" Deuxième nationalité : "+ personne.getPaysByCodNat2Pays().getLibNatPays()+"; ");
        }
        
        if (modificationDonneesClientForm.getLibelleNationalite2() != null ){
            textNew.append(" Deuxième nationalité : "+ modificationDonneesClientForm.getLibelleNationalite2()+"; ");
        }
        
        Pays paysNationalite2 = new Pays();
        paysNationalite2.setCodPaysPays(modificationDonneesClientForm.getCodeNationalite2());
        personne.setPaysByCodNat2Pays(paysNationalite2);

        // Régime Matrimonial
        
        if (personne.getRegimeMatrimonial() != null && personne.getRegimeMatrimonial().getLibRmatRmat() != null){
            textAnc.append(" Régime matrimoniale : "+ personne.getRegimeMatrimonial().getLibRmatRmat()+"; ");
        }
        
        if (modificationDonneesClientForm.getLibRmatRmat()  != null && (! (modificationDonneesClientForm.getLibRmatRmat().equalsIgnoreCase("")) )){
            textNew.append(" Régime matrimoniale : "+ modificationDonneesClientForm.getLibRmatRmat()+"; ");
        }
        RegimeMatrimonial regimeMatrimonial = new RegimeMatrimonial();
        regimeMatrimonial.setCodRmatRmat(modificationDonneesClientForm.getCodRmatRmat());
        personne.setRegimeMatrimonial(regimeMatrimonial);

        // Niveau instruction
        if (personne.getNiveauInstruction() != null && personne.getNiveauInstruction().getLibNiviNivi()!=null){
            textAnc.append(" Niveau d'instruction : "+ personne.getNiveauInstruction().getLibNiviNivi()+"; ");
        }
        
        if (modificationDonneesClientForm.getLibNiviNivi() !=null){
            textNew.append(" Niveau d'instruction : "+ modificationDonneesClientForm.getLibNiviNivi()+"; ");
        }
        
        NiveauInstruction niveauInstruction = new NiveauInstruction();
        niveauInstruction.setCodNiviNivi(modificationDonneesClientForm.getCodNiviNivi());
        personne.setNiveauInstruction(niveauInstruction);

        if (modificationDonneesClientForm.getClient() != null) {
            Client client = modificationDonneesClientForm.getClient();
            if (modificationDonneesClientForm.getCodDoanClt() !=  null ||  modificationDonneesClientForm.getNumFiscClt() !=  null) {
                
                if(client.getCodDoanClt() != null){
                    textAnc.append(" Code en douane : "+client.getCodDoanClt()+"; " );
                }
                if (modificationDonneesClientForm.getCodDoanClt()!= null && (! modificationDonneesClientForm.getCodDoanClt().equalsIgnoreCase(""))){
                    textNew.append(" Code en douane : "+ modificationDonneesClientForm.getCodDoanClt() +"; ");
                }
                
                if (client.getNumFiscClt() != null && (!client.getNumFiscClt().equals("0000000000000")) ) {
                    textAnc.append(" Matricule fiscale : "+client.getNumFiscClt() +"; ");
                }
               
                if (modificationDonneesClientForm.getNumFiscClt() != null && (!modificationDonneesClientForm.getNumFiscClt().equals("0000000000000")) && (! modificationDonneesClientForm.getNumFiscClt().equalsIgnoreCase("")) ){
                    textNew.append(" Matricule fiscale : "+modificationDonneesClientForm.getNumFiscClt() +"; ");
                }
                client.setCodDoanClt(modificationDonneesClientForm.getCodDoanClt());
                client.setNumFiscClt(modificationDonneesClientForm.getNumFiscClt());
            }
            paramModificationDonneesVo.setClientModifie(client);
        }
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void validationNominationComplementaire(ModificationDonneesClientForm modificationDonneesClientForm, 
                                                    Personne personne) throws Exception {
        StringBuffer textAnc = new StringBuffer(" Les anciennes données :" );
        StringBuffer textNew = new StringBuffer(" Les nouvelles données :");
        
        // les noms secondaire
        //-- nom origine
        if (personne.getNomNomoPers()!= null){
           textAnc.append(" Nom origine : " + personne.getNomNomoPers()+"; ")  ;
        }
        if (modificationDonneesClientForm.getNomNomoPers()!= null  ){
            textNew.append(" Nom origine : " + modificationDonneesClientForm.getNomNomoPers()+"; ")  ;
        }
        personne.setNomNomoPers(modificationDonneesClientForm.getNomNomoPers());
        //-- prenom origine
        if (personne.getNomPrnoPers()!= null){
            textAnc.append(" Prenom origine : " + personne.getNomPrnoPers()+"; ")  ;
        }
        if (modificationDonneesClientForm.getNomNomoPers()!= null  ){
             textNew.append(" Prenom origine : " + modificationDonneesClientForm.getNomPrnoPers()+"; ")  ;
        }      
        personne.setNomPrnoPers(modificationDonneesClientForm.getNomPrnoPers());
        
        //--- prenom attribué
        if (personne.getNomPrnaPers()!= null){
            textAnc.append(" Prenom attribué : " + personne.getNomPrnaPers()+"; ")  ;
        }
        if (modificationDonneesClientForm.getNomPrnaPers()!= null  ){
             textNew.append(" Prenom attribué : " + modificationDonneesClientForm.getNomPrnaPers()+"; ")  ;
        }
        personne.setNomPrnaPers(modificationDonneesClientForm.getNomPrnaPers());
        
        //-- nom attribué
         if (personne.getNomNomaPers()!= null){
             textAnc.append(" Nom attribué : " + personne.getNomNomaPers()+"; ")  ;
         }
         if (modificationDonneesClientForm.getNomNomaPers()!= null  ){
              textNew.append(" Nom attribué : " + modificationDonneesClientForm.getNomNomaPers()+"; ")  ;
         }
        personne.setNomNomaPers(modificationDonneesClientForm.getNomNomaPers());
        
        //-- nom de la mere 
         if (personne.getNomNommPers()!= null){
             textAnc.append(" Nom de la mère : " + personne.getNomNommPers()+"; ")  ;
         }
         if (modificationDonneesClientForm.getNomNommPers()!= null  ){
              textNew.append(" Nom de la mère : " + modificationDonneesClientForm.getNomNommPers()+"; ")  ;
         }
        personne.setNomNommPers(modificationDonneesClientForm.getNomNommPers());
        
        //-- Prenom de la mere
         if (personne.getNomPrnmPers()!= null){
             textAnc.append(" Prenom de la mère : " + personne.getNomPrnmPers()+"; ")  ;
         }
         if (modificationDonneesClientForm.getNomPrnmPers()!= null  ){
              textNew.append(" Prenom de la mère : " + modificationDonneesClientForm.getNomPrnmPers()+"; ")  ;
         }
        personne.setNomPrnmPers(modificationDonneesClientForm.getNomPrnmPers());
        
        modificationDonneesClientForm.setLibelleConfirmation1(textAnc.toString());
        modificationDonneesClientForm.setLibelleConfirmation2(textNew.toString());
    }

    private void verifListeContratAmodifier(ModificationDonneesClientForm modificationDonneesClientForm) throws Exception {
        //------------------------------------------------------------------------------//
        //--------------- verifier les contrats de la personne --------------------------//
        //------------------------------------------------------------------------------//
        modificationDonneesClientForm.getListDesContratAmodifier().clear();
        GetListeContratsAmodifierCmd getListeContratsAmodifierCmd =   new GetListeContratsAmodifierCmd();
        Long age = Long.valueOf("0");
        String categorie = "";
        
        if (!modificationDonneesClientForm.getTypePersonne().equals("PM")) {
            age = getAge(DateHandler.strToDate(modificationDonneesClientForm.getDatNaisPers()));
            if (modificationDonneesClientForm.getTypeModification().getCodCodModf().equals(Long.valueOf(Constants.COD_MODIF_CHANGEMENT_CATEGORIE))) {
               categorie = modificationDonneesClientForm.getNouvelleCodCatpCatp();
            }else{
                categorie = modificationDonneesClientForm.getCodCatpCatp();
            }
        }else{
            categorie = modificationDonneesClientForm.getCodCatpCatp();
        }
        
        String codePays = modificationDonneesClientForm.getCodeNationalite();
        String residence = modificationDonneesClientForm.getBoolResPers();
        String formeJuridique = modificationDonneesClientForm.getCodFjFj();
        
        
        String codeTypePiece = modificationDonneesClientForm.getTypePiece();
        String numeroPiece   = modificationDonneesClientForm.getNumeroPiece();

        ParamPers paramPers = new ParamPers();
        paramPers.setAge(age.intValue());
        paramPers.setBoolResPers(Integer.valueOf(residence));
        paramPers.setCodPaysPays(codePays);
        paramPers.setCodFjFj(formeJuridique);
        paramPers.setCodCatpCatp(categorie);

        ParamListContratsAmodifierVo paramListVo =  new ParamListContratsAmodifierVo();

        paramListVo.setParampers(paramPers);

        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(Long.valueOf(codeTypePiece));
        personneStrc.setNumPcePers(numeroPiece);

        paramListVo.setPersonneStrc(personneStrc);
        paramListVo = (ParamListContratsAmodifierVo)getListeContratsAmodifierCmd.execute(paramListVo);
       
        if (paramListVo.getListContratAmodifier() != null && paramListVo.getListContratAmodifier().size() > 0) {
            for (Iterator it =  paramListVo.getListContratAmodifier().iterator(); it.hasNext(); ) {
                ContratCptView contratCptView = new ContratCptView();
                ContratCpt contratCpt = (ContratCpt)it.next();
                contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                               '0', 6));
                contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                             '0', 3));
                contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                              '0', 4));
                modificationDonneesClientForm.getListDesContratAmodifier().add(contratCptView);

            } // fin for
        } // fin if

    }

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        ModificationDonneesClientForm modificationDonneesClientForm = 
            (ModificationDonneesClientForm)form;
        ActionMessages actionMessages = new ActionMessages();

       try{
        String typePers = new String("");

        if (modificationDonneesClientForm.getTypePersonne() != null) {
            typePers = modificationDonneesClientForm.getTypePersonne();
        }

        modificationDonneesClientForm.clearForm();
        modificationDonneesClientForm.setTypePersonne(typePers);

        if (modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_RES) || 
            modificationDonneesClientForm.getCodeModification().equals(Constants.COD_MODIF_ADR_CORR)) {
            return mapping.findForward("modificationAdresse");

        } else if (modificationDonneesClientForm.getTypePersonne() != null && 
                   modificationDonneesClientForm.getTypePersonne().equals("PM")) {
            return mapping.findForward("modificationPersMoral");
        } else {
            return mapping.findForward("initierPage");
        }
        
        } catch(Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction / disp :rechercherAdresseContrat: ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");       
        }
    }

    public ActionForward rechercherAdresseContrat(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        ModificationDonneesClientForm modificationDonneesClientForm = 
            (ModificationDonneesClientForm)form;
    ActionMessages actionMessages = new ActionMessages();

  try{
        modificationDonneesClientForm.setImmeubleCorresp(null);
        modificationDonneesClientForm.setRueCorresp(null);
        modificationDonneesClientForm.setVilleCorresp(null);
        modificationDonneesClientForm.setCodPaysPaysCorresp(null);
        modificationDonneesClientForm.setLibPaysPaysCorresp(null);
        modificationDonneesClientForm.setCodCpCpCorresp(null);
        modificationDonneesClientForm.setLibCpCpCorresp(null);

        for (Iterator it = 
             modificationDonneesClientForm.getListeDesContrats().iterator(); 
             it.hasNext(); ) {
            ContratCptView contratCptView = (ContratCptView)it.next();

            if (contratCptView.getContratCpt().getContratCptId().getCodStrcStrc().equals(Long.valueOf(modificationDonneesClientForm.getCodeStructureChoisi())) && 
                contratCptView.getContratCpt().getContratCptId().getCodPrdPrd().equals(Long.valueOf(modificationDonneesClientForm.getCodePrdChoisi())) && 
                contratCptView.getContratCpt().getContratCptId().getNumCcptCcpt().equals(Long.valueOf(modificationDonneesClientForm.getNumCompteChoisi()))) {

                modificationDonneesClientForm.setContratModifie(contratCptView.getContratCpt());
                if (contratCptView.getContratCpt().getAdresseCorresp() != 
                    null) {
                    Adresse adrCorrespondance = 
                        (Adresse)contratCptView.getContratCpt().getAdresseCorresp();
                    if (adrCorrespondance.getImmeuble() != null) {
                        modificationDonneesClientForm.setImmeubleCorresp(adrCorrespondance.getImmeuble());
                    }
                    if (adrCorrespondance.getRue() != null) {
                        modificationDonneesClientForm.setRueCorresp(adrCorrespondance.getRue());
                    }
                    if (adrCorrespondance.getVille() != null) {
                        modificationDonneesClientForm.setVilleCorresp(adrCorrespondance.getVille());
                    }
                    if (adrCorrespondance.getCite() != null) {
                        modificationDonneesClientForm.setCiteCorresp(adrCorrespondance.getCite());
                    }
                    if (adrCorrespondance.getCodPaysPays() != null) {
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        Pays pays = new Pays();
                        pays.setCodPaysPays(adrCorrespondance.getCodPaysPays());
                        pays = (Pays)getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null) {
                            modificationDonneesClientForm.setCodPaysPaysCorresp(adrCorrespondance.getCodPaysPays());
                            modificationDonneesClientForm.setLibPaysPaysCorresp(pays.getLibPaysPays());
                        }
                    } // fin pays


                    if (adrCorrespondance.getCodCpCp() != null) {
                        modificationDonneesClientForm.setCodCpCpCorresp(adrCorrespondance.getCodCpCp());

                        // si le pays est la tunisie extraire le libelle du code postal
                        if ((adrCorrespondance.getCodPaysPays() != null) && 
                            (adrCorrespondance.getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                            GetCodePostalCmd getCodePostalCmd = 
                                new GetCodePostalCmd();
                            CodePostal codePostal = new CodePostal();
                            codePostal.setCodCpCp(Long.valueOf(adrCorrespondance.getCodCpCp()));
                            codePostal = 
                                    (CodePostal)getCodePostalCmd.execute(codePostal);
                            modificationDonneesClientForm.setLibCpCpCorresp(codePostal.getLibCpCp());

                            // gouvernerat
                            modificationDonneesClientForm.setCodGouvGouvCorresp(codePostal.getGouvernorat().getCodGouvGouv().toString());
                            modificationDonneesClientForm.setLibGouvGouvCorresp(codePostal.getGouvernorat().getLibGouvGouv());
                        }

                    } /// fin adresse postal

                }

            }

        } // fin for
        modificationDonneesClientForm.setChoixContrat("OUI");
        return mapping.findForward("modificationAdresse");
  } catch(Exception e){
      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
      StringBuffer text = 
          new StringBuffer("la transaction est Interrompu, une erreur dans ModifierDonneesClientAction / disp :rechercherAdresseContrat: ");
      text.append(e.toString());
      erreur.setCode("200");
      erreur.setDescription(text.toString());

      ActionMessage actionMessage = 
          new ActionMessage("exception.generique", 
                            erreur.getDescription());
      actionMessages.add("Erreur ", actionMessage);
      this.saveMessages(request, actionMessages);
      return mapping.findForward("error");     
  }
  
}

    public ActionForward printModification(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException
                                                                     {
                                                                     
            ModificationDonneesClientForm modificationDonneesClientForm = 
                (ModificationDonneesClientForm)form;
                          try {
                                    CommonReportVO valueObject = new CommonReportVO();
                                    ParamAgence paramAgence = new ParamAgence();
                                    paramAgence =
                                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                                    Map parameters = new HashMap();
                                    String pMODIF1 = "MODIF1";
                                    String pMODIF2 = "MODIF2";
                                    String pMODIF3 = "MODIF3";
                                    String pLibEtat = "P_LIB_ETAT";
                                    String pMatrUser = "P_NUM_MATR_USER";
                                    String pLogo = "P_PATH";
                                    
                                    String pCcpt = "CCPT";
                                    String vCcpt ="";
                                    StringBuffer str = new StringBuffer();
                                    List listCcpt = modificationDonneesClientForm.getListDesContratAmodifier();
                                    for (Iterator it =  listCcpt.iterator(); it.hasNext(); ) {
                                         ContratCptView contratCptView = (ContratCptView)it.next();
                                           str.append(contratCptView.getCodeAgence()); str.append(" ");
                                           str.append(contratCptView.getCodeProduit()); str.append(" ");
                                           str = str.append(contratCptView.getNumeroCompte());
                                           str.append("\n");
                                       }
                                    vCcpt = str.toString();
                                    parameters.put(pCcpt, vCcpt);
                                    String vLibEtat = modificationDonneesClientForm.getLibelleModification();
                                    String vMatrUser = paramAgence.getNumMatrUser().toString();
                                    String vLogo = getServlet().getServletContext().getRealPath("")+ "\\reporting\\";
                                    String vMODIF1 = modificationDonneesClientForm.getLibelleConfirmation();
                                    String vMODIF2 = modificationDonneesClientForm.getLibelleConfirmation1();
                                    String vMODIF3 = modificationDonneesClientForm.getLibelleConfirmation2();
                                    parameters.put(pMatrUser, vMatrUser);
                                    parameters.put(pLibEtat, vLibEtat);
                                    parameters.put(pMODIF1, vMODIF1);
                                    parameters.put(pMODIF2, vMODIF2);
                                    parameters.put(pMODIF3, vMODIF3);
                                    parameters.put(pLogo, vLogo);
                                   
                                   valueObject.setParams(parameters);
                                   valueObject.setNomReport("EtatModification");
                                 //  valueObject.setList(modificationDonneesClientForm.getListDesContratAmodifier());
                                 //  valueObject.setRootFolder(getServlet().getServletContext().getRealPath("")+ "\\reporting\\");
                                 //  PrinterCmd printer = new PrinterCmd();

                               //    valueObject = (CommonReportVO) printer.execute(valueObject);
                                   request.getSession().setAttribute("CommonPrintVo",valueObject);
                                   request.setAttribute("print","1");
                                   
                                   response.setContentType("application/pdf");
                                   response.setContentLength(valueObject.getContent().length);
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(valueObject.getContent(), 0, valueObject.getContent().length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           } catch (Exception e) {
                                
                                   e.printStackTrace();
                           }
                    
            return mapping.findForward("confirmationModification");
                                                                          
        }


    public  Long getAge(Date DateNaissance) {
     Double d = (getDaysBetween(DateNaissance,DateHandler.strToDate(DateHandler.dateJour())));
     Long nombreJours = d.longValue();
     Long age = (nombreJours / 365);
     return (age);
    
      
    }
    public  double getDaysBetween(Date first, Date second) {

        double milliElapsed = second.getTime() - first.getTime();
        double daysElapsed = (milliElapsed / 24F / 3600F / 1000F);
        return (Math.round(daysElapsed * 100F) / 100F);
    }
}

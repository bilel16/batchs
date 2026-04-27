package com.bna.smile.web.pilotage.actions;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetActiviteByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetProfessionByIdCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesCartesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeChequeDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetDetailCategorieContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetPersClientCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.pilotage.commande.GetDonneeClientCmd;
import com.bna.smile.model.pilotage.model.ParamClientVo;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.util.DemandeCarteView;
import com.bna.smile.web.moyenPaiement.demandeChequier.forms.RechercheDemandesChequesForm;
import com.bna.smile.web.moyenPaiement.demandeChequier.util.DemandeChequeView;
import com.bna.smile.web.pilotage.forms.PilotageForm;
import com.bna.smile.web.placement.forms.ConsultationPlacementForm;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.procuration.util.ContratCptView;


import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class PilotageAction extends DispatchAction{
    public PilotageAction() {
    }
    ParamAgence paramAgence = new ParamAgence();
    Personne personne = new Personne();
    Client client = new Client();
    private static final Logger logger = Logger.getLogger(PilotageAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {


        ActionMessages actionMessages = new ActionMessages();
        try {
            PilotageForm pilotageForm = (PilotageForm)form;
            pilotageForm.clearForm();
           
            return mapping.findForward("success");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans PilotageAction / initierPage : ");
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

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
         PilotageForm pilotageForm = 
            (PilotageForm)form;
        try {
            
            pilotageForm.clearForm();
            ContratCpt premierContrat = new ContratCpt();
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            pilotageForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
            GetListContratMandataireCmd getListContratMandataireCmd = 
                new GetListContratMandataireCmd();
            GetListCotitulairePersonneCmd getListCotitulairePersonneCmd = 
                new GetListCotitulairePersonneCmd();
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());
            Listes listesCptsMandataire = new Listes();
            Listes listEntiteCotit = new Listes();
            PersonneStrc personneStrc = new PersonneStrc();
            //personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            // si la recherche est effectuée par type et num piece
            
            personneStrc.setCodTpceTpce(new Long(pilotageForm.getTypePieceId()));
            personneStrc.setNumPcePers(pilotageForm.getNumPieceId());
            personneRechercheContratVo.setPersonneStrc(personneStrc);
            //personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
            GetListContratCmd getListContratCmd = new GetListContratCmd();                
            listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
           
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        pilotageForm.setAlert("ClientInexistant");
                    } else {
                       if (listesCpts.getList().size() > 0) {
                        // affecter la liste des contrats à la liste de collection Tag
                        List listeDesContratsView = new ArrayList();
                        List listeDesContratsEpargView = new ArrayList();
                        List listeDesContratsEpargLieView = new ArrayList();
                        List listeDesContratsViewCtx = new ArrayList();
                        for (Iterator it = listesCpts.getList().iterator(); 
                             it.hasNext(); ) {
                            ContratCpt contratCpt = (ContratCpt)it.next();
                            String cleContrat = 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                '0', 3) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                '0', 4) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                '0', 6);
                            ContratCptView contratCptView = new ContratCptView();
                            contratCptView.setCleContrat(cleContrat);
                            
                            contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                            //contratCptView.setCodeAgence(contratCpt.getStructure().getCodStrcStrc().toString().);
                            contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                           '0', 
                                                                           6));
                            contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                         '0', 3));
                            contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 4));
                            contratCptView.setContratCpt(contratCpt);
                            if (contratCpt.getCodEtatCcpt().equalsIgnoreCase("V")){
                                if(contratCpt.getContratCptId().getCodPrdPrd().intValue()==121){
                                    listeDesContratsEpargView.add(contratCptView);
                                }else if(contratCpt.getContratCptId().getCodPrdPrd().intValue()==105||
                                         contratCpt.getContratCptId().getCodPrdPrd().intValue()==111||
                                         contratCpt.getContratCptId().getCodPrdPrd().intValue()==177){
                                    listeDesContratsEpargLieView.add(contratCptView);
                                }else{
                                listeDesContratsView.add(contratCptView);
                                }
                            }else if(contratCpt.getCodEtatCcpt().equalsIgnoreCase("T")){
                                listeDesContratsViewCtx.add(contratCptView);
                            }
                        }
                        
                        
                        pilotageForm.setListeContrats(listeDesContratsView);
                        pilotageForm.setListeContratsEparg(listeDesContratsEpargView);
                        pilotageForm.setListeContratsEpargLie(listeDesContratsEpargLieView);
                        pilotageForm.setListeContratCtx(listeDesContratsViewCtx);
                        
                        //  ************************************************************
    
                       
                        premierContrat = (ContratCpt)listesCpts.getList().get(0);
                        client = premierContrat.getClient();
                        personne = premierContrat.getClient().getPersonne();
                        pilotageForm.setPersonne(personne);
                        pilotageForm.setClient(client);
                        if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                            pilotageForm.setNomId(personne.getNomNomPers());
                            pilotageForm.setPrenomId(personne.getNomPrnPers());
                            pilotageForm.setOpenTabsheetClient("true");
                            affecterDonnesPersonnePhysique(pilotageForm, 
                                                           client);
                            //extaction du tuteur en cas d'un client mineur
                            if (personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
                                GetPersClientCmd getPersClientCmd = 
                                    new GetPersClientCmd();
                                PersonneCpt personneCpt = new PersonneCpt();
                                Personne tuteur = new Personne();
                                personneCpt.setClient(client);
                                personneCpt.setCodQualQual(Constants.COD_QUALIT_TUTEUR);
                                tuteur = 
                                        (Personne)getPersClientCmd.execute(personneCpt);
                                if (tuteur != null) {
                                    pilotageForm.setOpenTabsheetTuteur("true");
                                    affecterDonnesPersonneTuteur(pilotageForm, 
                                                                 tuteur);
                                }
                            }
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            pilotageForm.setNomId(personne.getNomRsPers());
                            pilotageForm.setPrenomId(personne.getLibSiglPers());
                            pilotageForm.setOpenTabsheetMorale("true");
                            affecterDonnesPersonneMorale(pilotageForm, 
                                                         client);
    
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                            pilotageForm.setNomId(personne.getNomNomPers());
                            affecterDonnesPersonneCotitulaire(pilotageForm, 
                                                              personne);
                        }
                        /*mandataire sur les contrats suivants */
                        listesCptsMandataire = 
                                (Listes)getListContratMandataireCmd.execute(personneStrc);
                        pilotageForm.setListeContratsMandataire(listesCptsMandataire.getList());
    
                        /* cotitulaire dans les entités cotitulaire suivantes  */
                        listEntiteCotit = 
                                (Listes)getListCotitulairePersonneCmd.execute(personneStrc);
                        pilotageForm.setListeEntiteCotit(listEntiteCotit.getList());
    
                        if (listesCpts.getList().size() == 1) {
                            affecterDonnesContrat(pilotageForm, 
                                                  premierContrat);
                            pilotageForm.setContratCpt(premierContrat);                      
                            pilotageForm.setOpenTabsheetContrat("true");
                        }
                    }else{
                        pilotageForm.setAlert("ClientInexistant");
                    }
                /*recherche des données client*/ 
                
                ParamClientVo paramClientVo=new ParamClientVo(); 
                paramClientVo.setCodStrcStrc(paramAgence.getCodStrcStrc());
                paramClientVo.setCodTpcePers(new Long(pilotageForm.getTypePieceId()));
                paramClientVo.setNumPcePers(pilotageForm.getNumPieceId());
                GetDonneeClientCmd getDonneeClientCmd=new GetDonneeClientCmd();
                
                ParamClientVo paramClientVoretour=(ParamClientVo)getDonneeClientCmd.execute(paramClientVo);
                if (paramClientVoretour == null || paramClientVoretour.hasError()) {
                       List listErreur = paramClientVoretour.getErrors();
                       for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                           com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                           ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                           actionMessages.add("Erreur ", actionMessage);
                       }
                       this.saveMessages(request, actionMessages);
                      
                       return mapping.findForward("error");
                }else{
                affecterDonneeEnCour(paramClientVoretour,pilotageForm);
                }
            }
            }else {                   
                     List listErreur = listesCpts.getErrors();
                     //ActionMessages actionMessages = new ActionMessages();
                     for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                         com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                         ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                     }    
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");                    
                }            

            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :rechercherPersonne ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +pilotageForm.getCodStrcRech()+ ">>. Exception : ",e);  
            //    logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

    }
    public ActionForward imprimerVision(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        PilotageForm pilotageForm = 
           (PilotageForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            parameters.put("P_LIBELLE_ETAT", "VISION GLOBAL CLIENT");
            parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
            parameters.put("P_DAT_COMPTABLE",paramAgence.getDateComptable());
            parameters.put("P_COD_STRC_STRC", 
                           paramAgence.getCodStrcStrc().toString());
            parameters.put("P_TYPE_PIECE", 
                           pilotageForm.getTypePieceId().toString());
            parameters.put("P_NUM_PIECE", 
                           pilotageForm.getNumPieceClt().toString());
            parameters.put("P_NOM", 
                           pilotageForm.getNomId().toString());
            parameters.put("P_PRENOM", 
                           pilotageForm.getPrenomId().toString());
            parameters.put("P_PROFESSION", 
                           pilotageForm.getProfessionClt().toString());
            parameters.put("P_ACTIVITE", 
                           pilotageForm.getActiviteClt().toString());
            parameters.put("P_ADR", 
                           pilotageForm.getAdresse().toString());
            parameters.put("P_GOUV", 
                           pilotageForm.getGouvertnorat().toString());
            parameters.put("P_RESID", 
                           pilotageForm.getResidentClt().toString());
                           
                           
            parameters.put("P_NBR_DEM_CHQ", 
                           pilotageForm.getNbrDemChq().toString());   
            parameters.put("P_NBR_DEM_CART", 
                           pilotageForm.getNbrDemCart().toString());   
            parameters.put("P_NBR_DEM_OPP", 
                           pilotageForm.getNbrDemOpp().toString());   
            parameters.put("P_NBR_DEPOT", 
                           pilotageForm.getNbrDepot().toString());  
            parameters.put("P_NBR_EPARG", 
                           pilotageForm.getNbrDepot().toString());  
            parameters.put("P_NBR_ENG", 
                           pilotageForm.getNbrEng().toString());  
            parameters.put("P_NBR_PLAC", 
                           pilotageForm.getNbrPlacement().toString());  
            parameters.put("P_NBR_INTER", 
                           pilotageForm.getNbrInter().toString());  
            parameters.put("P_MNT_PLAC", 
                           pilotageForm.getMntPlacement().toString());
            parameters.put("P_MNT_DEP", 
                           pilotageForm.getMntDepot().toString());  
            parameters.put("P_MNT_EPARG", 
                           pilotageForm.getMntEparg().toString()); 
            parameters.put("P_MNT_ENG", 
                           pilotageForm.getMntEng().toString()); 

            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper                   
            valueObject.setNomReport("recap_domaine_plc");
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("clotDomPlac");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomPlacementAction / Dispatch Action :imprimerRecapPlac ");
            text.append(e.toString());
            logger.error("Exception : ", e);
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
  
    public void affecterDonnesPersonneCotitulaire(ActionForm form, 
                                                  Personne personne) throws Exception{

       

        PilotageForm pilotageForm = 
           (PilotageForm)form;


            Listes listMembresCotit = new Listes();
            GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = 
                new GetListMembreCotitulaireCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input
            personneStrc.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce());
            personneStrc.setNumPcePers(personne.getNumPcePers());
            personneStrc.setCodStrcStrc(new Long(pilotageForm.getCodeAgance()));
            listMembresCotit = 
                    (Listes)getListMembreCotitulaireCmd.execute(personneStrc);

            if (listMembresCotit.getList() != null && 
                listMembresCotit.getList().size() > 0) {
                CoTitulaire cotitulaire = 
                    (CoTitulaire)listMembresCotit.getList().get(0);
                pilotageForm.setTypeCotit(cotitulaire.getCodTcotCoti());
                pilotageForm.setTypeSignature(cotitulaire.getCodSigCoti());
                pilotageForm.setListeMembreEntiteCotit(listMembresCotit.getList());
                pilotageForm.setOpenTabsheetCotitulaire("true");
            }

      

    }
    public void affecterDonnesPersonnePhysique(ActionForm form, 
                                               Client client) throws Exception{

        
        PilotageForm pilotageForm = 
           (PilotageForm)form;

                pilotageForm.setTypePersonneClt(client.getTypePers().getLibTperTper());
                pilotageForm.setCategoriePersonneClt(client.getPersonne().getCategoriePersonne().getLibCatpCatp());            
                pilotageForm.setTypePieceClt(client.getPersonne().getTypePiece().getLibSiglTpce());
                pilotageForm.setDateDelivClt(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
                pilotageForm.setNumPieceClt(client.getPersonne().getNumPcePers());
                
                if (client.getPersonne().getGouvernorat() != null) {
                    GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                    Gouvernorat gouvernorat = new Gouvernorat();
                    gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                    gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                    pilotageForm.setLieuDelivClt(gouvernorat.getLibGouvGouv().toString());
                    pilotageForm.setCodLieuDelivClt(gouvernorat.getCodGouvGouv().toString());
                }
                
                 PieceAnnexe pieceAnnexe = new PieceAnnexe();
                  if (client.getPersonne().getPieceAnnexes() != null && client.getPersonne().getPieceAnnexes().size() > 0) {
                      for (Iterator it = client.getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                           pieceAnnexe = (PieceAnnexe)it.next();                     
                           if(pieceAnnexe.getDatFvalPian()!= null &&  pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                               pilotageForm.setTypePieceAnnexe((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                               pilotageForm.setDateDellivPiann(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                               pilotageForm.setDateFinPian(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                               pilotageForm.setNumPieceAnnexe(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                               break;
                           }                    
                      }
                  }

                pilotageForm.setTitrePersClt(client.getPersonne().getLibTitrPers());
                pilotageForm.setNomPersClt(client.getPersonne().getNomNomPers());
                pilotageForm.setPrenomPersClt(client.getPersonne().getNomPrnPers());
                pilotageForm.setNomPereClt(client.getPersonne().getNomPrnpPers());

                pilotageForm.setDateNaisClt(DateHandler.dateToStr(client.getPersonne().getDatNaisPers()));
                pilotageForm.setLieuNaisClt(client.getPersonne().getLibNaisPers());
               
                if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                    //extraire la nationalité
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);                
                    pilotageForm.setNationaliteClt(pays.getLibNatPays());                   
                }
                
                pilotageForm.setResidentClt(client.getPersonne().getBoolResPers().toString());                
                pilotageForm.setSexeClt(client.getPersonne().getCodSexPers());
                if(client.getPersonne().getPaysByCodNaisPays()!= null){
                  pilotageForm.setPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getLibPaysPays());
                  pilotageForm.setCodPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getCodPaysPays());
                }
                pilotageForm.setSitFamilialeClt(client.getPersonne().getCodSitfPers());
                pilotageForm.setSectActiviteClt(client.getPersonne().getCodSectPers());
                
                if (client.getPersonne().getProfession() != null) {
                // extraire la profession
                    pilotageForm.setCodGroupProfClt(client.getPersonne().getProfession().getProfessionId().getCodGproGpro().toString());
                    pilotageForm.setCodProfClt(client.getPersonne().getProfession().getProfessionId().getCodProfProf().toString());            
                    GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                    Profession profession = new Profession();
                    ProfessionId professionId = new ProfessionId();
                    professionId.setCodProfProf(Long.valueOf(pilotageForm.getCodProfClt()));
                    professionId.setCodGproGpro(Long.valueOf(pilotageForm.getCodGroupProfClt()));
                    profession.setProfessionId(professionId);
                    profession = (Profession)getProfessionByIdCmd.execute(profession);
                    if (profession != null && profession.getLibProfProf() != null) {
                        pilotageForm.setProfessionClt(profession.getLibProfProf());
                    }
                }
                
                if (client.getPersonne().getActivite() != null) {
                    // extraire l'activité
                    pilotageForm.setCodActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodActAct().toString());
                    pilotageForm.setCodClasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodCactCact().toString());
                    pilotageForm.setCodSclasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodSactSact().toString());

                    GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                    Activite activite = new Activite();
                    ActiviteId activiteId = new ActiviteId();
                    activiteId.setCodActAct(pilotageForm.getCodActiviteClt());
                    activiteId.setCodCactCact(pilotageForm.getCodClasActiviteClt());
                    activiteId.setCodSactSact(Long.valueOf(pilotageForm.getCodSclasActiviteClt()));
                    activite.setActiviteId(activiteId);
                    activite = (Activite)getActiviteByIdCmd.execute(activite);
                    if (activite != null && activite.getLibActAct() != null) {
                        pilotageForm.setActiviteClt(activite.getLibActAct());
                    }
                }
                
                // adresse de résidence
                if (client.getPersonne().getAdresseResid() != null) {
                    // Immeuble
                    if (client.getPersonne().getAdresseResid().getImmeuble() != null) {
                        pilotageForm.setImmeubleAdrResid(client.getPersonne().getAdresseResid().getImmeuble());
                    }
                    // Rue
                    if (client.getPersonne().getAdresseResid().getRue() != null) {
                        pilotageForm.setRueAdrResid(client.getPersonne().getAdresseResid().getRue());
                    }

                    // Cite
                    if (client.getPersonne().getAdresseResid().getCite() != null) {
                        pilotageForm.setCiteAdrResid(client.getPersonne().getAdresseResid().getCite());
                    }

                    // Pays 
                    if (client.getPersonne().getAdresseResid().getCodPaysPays() != null) {
                        pilotageForm.setCodPayAdrResid(client.getPersonne().getAdresseResid().getCodPaysPays());
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        Pays pays = new Pays();
                        pays.setCodPaysPays(client.getPersonne().getAdresseResid().getCodPaysPays());
                        pays = (Pays)getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null) {
                            pilotageForm.setPaysAdrResid(pays.getLibPaysPays());
                        }
                    }
                    // Code Postal
                    if (client.getPersonne().getAdresseResid().getCodCpCp() != null) {
                        pilotageForm.setCodePostalAdrResid(client.getPersonne().getAdresseResid().getCodCpCp());
                        // si le pays est la tunisie extraire le libelle du code postal
                        if ((client.getPersonne().getAdresseResid().getCodPaysPays() != null) && 
                            (client.getPersonne().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                            GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                            CodePostal codePostal = new CodePostal();
                            codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseResid().getCodCpCp()));
                            codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                            pilotageForm.setLibPostalAdrResid(codePostal.getLibCpCp());

                            // gouvernerat
                            pilotageForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                            pilotageForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());
                        }
                    }
                } // Fin adresse de résidence
                             
            
                 // adresse professionnelle
                 if (client.getPersonne().getAdresseProf() != null) {
                     // Immeuble
                     if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                         pilotageForm.setImmeubleAdrProf(client.getPersonne().getAdresseProf().getImmeuble());
                     }
                     // Rue
                     if (client.getPersonne().getAdresseProf().getRue() != null) {
                         pilotageForm.setRueAdrProf(client.getPersonne().getAdresseProf().getRue());
                     }

                     // Cite
                     if (client.getPersonne().getAdresseProf().getCite() != null) {
                         pilotageForm.setCiteAdrProf(client.getPersonne().getAdresseProf().getCite());
                     }

                     // Pays 
                     if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                         pilotageForm.setCodPayAdrProf(client.getPersonne().getAdresseProf().getCodPaysPays());
                         GetPaysCmd getPaysCmd = new GetPaysCmd();
                         Pays pays = new Pays();
                         pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                         pays = (Pays)getPaysCmd.execute(pays);
                         if (pays.getLibPaysPays() != null) {
                             pilotageForm.setPaysAdrProf(pays.getLibPaysPays());
                         }
                     }
                     // Code Postal
                     if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                         pilotageForm.setCodePostalAdrProf(client.getPersonne().getAdresseProf().getCodCpCp());
                         // si le pays est la tunisie extraire le libelle du code postal
                         if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                             (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                             GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                             CodePostal codePostal = new CodePostal();
                             codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                             codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                             pilotageForm.setLibPostalAdrProf(codePostal.getLibCpCp());

                             // gouvernerat
                             pilotageForm.setCodGouvGouvProf(codePostal.getGouvernorat().getCodGouvGouv().toString());
                             pilotageForm.setLibGouvGouvProf(codePostal.getGouvernorat().getLibGouvGouv());
                         }
                     }
                 } // Fin adresse professionnelle
                  String adr= pilotageForm.getImmeubleAdrProf()+" "+pilotageForm.getRueAdrProf()+" "+
                              pilotageForm.getCiteAdrProf()+" "+pilotageForm.getLibPostalAdrProf();
        pilotageForm.setAdresse(adr); 
        pilotageForm.setGouvertnorat(pilotageForm.getLibGouvGouvProf());
        pilotageForm.setNumTelMoral(client.getPersonne().getNumTelPers());
        pilotageForm.setNumFaxMoral(client.getPersonne().getNumFaxPers());
        pilotageForm.setAdrMailMoral(client.getPersonne().getAdrMailPers());
        pilotageForm.setAdrWebMoral(client.getPersonne().getAdrWebPers());
        pilotageForm.setAdrSwiftMoral((client.getPersonne().getAdrSwiftPers()));
        pilotageForm.setAdrTelexMoral(client.getPersonne().getAdrTlxPers());
            


    }
    public void affecterDonnesPersonneTuteur(ActionForm form, 
                                             Personne tuteur) throws Exception{

       


        PilotageForm pilotageForm = 
           (PilotageForm)form;


            pilotageForm.setTypePersonneTuteur("Personne physique individuelle");
            pilotageForm.setCategoriePersonneTuteur(tuteur.getCategoriePersonne().getLibCatpCatp());

            pilotageForm.setTypePieceTut(tuteur.getTypePiece().getLibSiglTpce());
            pilotageForm.setDateDelivTuteur(DateHandler.dateToStr(tuteur.getDatDlvPers()));
            pilotageForm.setNumPieceTut(tuteur.getNumPcePers());
            
             if (tuteur.getGouvernorat() != null) {
                 GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                 Gouvernorat gouvernorat = new Gouvernorat();
                 gouvernorat.setCodGouvGouv(tuteur.getGouvernorat().getCodGouvGouv());
                 gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                 pilotageForm.setLieuDelivTuteur(gouvernorat.getLibGouvGouv().toString());
                 pilotageForm.setCodLieuDelivTuteur(gouvernorat.getCodGouvGouv().toString());
             }else {
                 logger.error("tuteur.getGouvernorat() == null");
             }
             
              PieceAnnexe pieceAnnexe = new PieceAnnexe();
               if (tuteur.getPieceAnnexes() != null && tuteur.getPieceAnnexes().size() > 0) {
                   for (Iterator it = tuteur.getPieceAnnexes().iterator(); it.hasNext(); ) {
                        pieceAnnexe = (PieceAnnexe)it.next();                     
                        if(pieceAnnexe.getDatFvalPian()!= null &&  pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                            pilotageForm.setTypePieceAnnexeTuteur((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                            pilotageForm.setDateDellivPiannTuteur(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                            pilotageForm.setDateFinPianTuteur(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                            pilotageForm.setNumPieceAnnexeTuteur(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                            break;
                        }                    
                   }
               } 

             pilotageForm.setTitrePersTuteur(tuteur.getLibTitrPers());
             pilotageForm.setNomPersTuteur(tuteur.getNomNomPers());
             pilotageForm.setPrenomPersTuteur(tuteur.getNomPrnPers());
             pilotageForm.setNomPereTuteur(tuteur.getNomPrnpPers());

             pilotageForm.setDateNaisTuteur(DateHandler.dateToStr(tuteur.getDatNaisPers()));
             pilotageForm.setLieuNaisTuteur(tuteur.getLibNaisPers());
             
             
             if (tuteur.getPaysByCodNat1Pays() != null) {
                 //extraire la nationalité
                 GetPaysCmd getPaysCmd = new GetPaysCmd();
                 Pays pays = new Pays();
                 pays.setCodPaysPays(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
                 pays = (Pays)getPaysCmd.execute(pays);                
                 pilotageForm.setNationaliteTuteur(pays.getLibNatPays());
                 pilotageForm.setCodNationaliteTuteur(pays.getCodPaysPays());
             }else {
                 logger.error("Pays Tuteur null >> tuteur.getPaysByCodNat1Pays() == null");
             }
             
             pilotageForm.setPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getLibPaysPays());
             pilotageForm.setCodPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
             pilotageForm.setResidentTuteur(tuteur.getBoolResPers().toString());
             pilotageForm.setSexeTuteur(tuteur.getCodSexPers());
             
             pilotageForm.setSectActiviteTuteur(tuteur.getCodSectPers());
             pilotageForm.setSitFamilialeTuteur(tuteur.getCodSitfPers());
             pilotageForm.setFormeJuridiqueTuteur(tuteur.getFormeJuridique().getCodFjFj());

             
             if (tuteur.getProfession() != null) {
             // extraire la profession
                 pilotageForm.setCodGroupProfTuteur(tuteur.getProfession().getProfessionId().getCodGproGpro().toString());
                 pilotageForm.setCodProfTuteur(tuteur.getProfession().getProfessionId().getCodProfProf().toString());            
                 GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                 Profession profession = new Profession();
                 ProfessionId professionId = new ProfessionId();
                 professionId.setCodProfProf(Long.valueOf(pilotageForm.getCodProfTuteur()));
                 professionId.setCodGproGpro(Long.valueOf(pilotageForm.getCodGroupProfTuteur()));
                 profession.setProfessionId(professionId);
                 profession = (Profession)getProfessionByIdCmd.execute(profession);
                 if (profession != null && profession.getLibProfProf() != null) {
                     pilotageForm.setProfessionTuteur(profession.getLibProfProf());
                 }
             }else {
                 logger.error("Aucune Profession pour le Tuteur >> tuteur.getProfession() == null");
             }
             
             if (tuteur.getActivite() != null) {
                 // extraire l'activité
                 pilotageForm.setCodActiviteTuteur(tuteur.getActivite().getActiviteId().getCodActAct().toString());
                 pilotageForm.setCodClasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodCactCact().toString());
                 pilotageForm.setCodSclasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodSactSact().toString());

                 GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                 Activite activite = new Activite();
                 ActiviteId activiteId = new ActiviteId();
                 activiteId.setCodActAct(pilotageForm.getCodActiviteTuteur());
                 activiteId.setCodCactCact(pilotageForm.getCodClasActiviteTuteur());
                 activiteId.setCodSactSact(Long.valueOf(pilotageForm.getCodSclasActiviteTuteur()));
                 activite.setActiviteId(activiteId);
                 activite = (Activite)getActiviteByIdCmd.execute(activite);
                 if (activite != null && activite.getLibActAct() != null) {
                     pilotageForm.setActiviteTuteur(activite.getLibActAct());
                 }
             }else {
                 logger.error("Aucune Activité pour le Tuteur >> tuteur.getActivite() == null");
             }
             
             // adresse de résidence
             if (tuteur.getAdresseResid() != null) {
                 // Immeuble
                 if (tuteur.getAdresseResid().getImmeuble() != null) {
                     pilotageForm.setImmeubleAdrResidTuteur(tuteur.getAdresseResid().getImmeuble());
                 }
                 // Rue
                 if (tuteur.getAdresseResid().getRue() != null) {
                     pilotageForm.setRueAdrResidTuteur(tuteur.getAdresseResid().getRue());
                 }

                 // Cite
                 if (tuteur.getAdresseResid().getCite() != null) {
                     pilotageForm.setCiteAdrResidTuteur(tuteur.getAdresseResid().getCite());
                 }
                 // Pays 
                 if (tuteur.getAdresseResid().getCodPaysPays() != null) {
                     pilotageForm.setCodPayAdrResidTuteur(tuteur.getAdresseResid().getCodPaysPays());
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     Pays pays = new Pays();
                     pays.setCodPaysPays(tuteur.getAdresseResid().getCodPaysPays());
                     pays = (Pays)getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null) {
                         pilotageForm.setPaysAdrResidTuteur(pays.getLibPaysPays());
                     }
                 }
                 // Code Postal
                 if (tuteur.getAdresseResid().getCodCpCp() != null) {
                     pilotageForm.setCodePostalAdrResidTuteur(tuteur.getAdresseResid().getCodCpCp());
                     // si le pays est la tunisie extraire le libelle du code postal
                     if ((tuteur.getAdresseResid().getCodPaysPays() != null) && 
                         (tuteur.getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                         GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                         CodePostal codePostal = new CodePostal();
                         codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseResid().getCodCpCp()));
                         codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                         pilotageForm.setLibPostalAdrResidTuteur(codePostal.getLibCpCp());

                         // gouvernerat
                         pilotageForm.setCodGouvGouvResTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                         pilotageForm.setLibGouvGouvResTuteur(codePostal.getGouvernorat().getLibGouvGouv());
                     }
                 }
             } // Fin adresse de résidence
              else {
                               logger.error("Aucune Adresse de résidence pour le Tuteur >> tuteur.getAdresseResid() == null");
                           }
                          
             
              // adresse professionnelle
              if (tuteur.getAdresseProf() != null) {
                  // Immeuble
                  if (tuteur.getAdresseProf().getImmeuble() != null) {
                      pilotageForm.setImmeubleAdrProfTuteur(tuteur.getAdresseProf().getImmeuble());
                  }
                  // Rue
                  if (tuteur.getAdresseProf().getRue() != null) {
                      pilotageForm.setRueAdrProfTuteur(tuteur.getAdresseProf().getRue());
                  }
                  // Cite
                  if (tuteur.getAdresseProf().getCite() != null) {
                      pilotageForm.setCiteAdrProfTuteur(tuteur.getAdresseProf().getCite());
                  }

                  // Pays 
                  if (tuteur.getAdresseProf().getCodPaysPays() != null) {
                      pilotageForm.setCodPayAdrProfTuteur(tuteur.getAdresseProf().getCodPaysPays());
                      GetPaysCmd getPaysCmd = new GetPaysCmd();
                      Pays pays = new Pays();
                      pays.setCodPaysPays(tuteur.getAdresseProf().getCodPaysPays());
                      pays = (Pays)getPaysCmd.execute(pays);
                      if (pays.getLibPaysPays() != null) {
                          pilotageForm.setPaysAdrProfTuteur(pays.getLibPaysPays());
                      }
                  }
                  // Code Postal
                  if (tuteur.getAdresseProf().getCodCpCp() != null) {
                      pilotageForm.setCodePostalAdrProfTuteur(tuteur.getAdresseProf().getCodCpCp());
                      // si le pays est la tunisie extraire le libelle du code postal
                      if ((tuteur.getAdresseProf().getCodPaysPays() != null) && 
                          (tuteur.getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                          GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                          CodePostal codePostal = new CodePostal();
                          codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseProf().getCodCpCp()));
                          codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                          pilotageForm.setLibPostalAdrProfTuteur(codePostal.getLibCpCp());

                          // gouvernerat
                          pilotageForm.setCodGouvGouvProfTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                          pilotageForm.setLibGouvGouvProfTuteur(codePostal.getGouvernorat().getLibGouvGouv());
                      }
                  }
              } // Fin adresse professionnelle
               else {
                                logger.error("Aucune adresse professionnelle pour le Tuteur >> tuteur.getAdresseProf() == null");
                            }
              
           

    }
    public void affecterDonnesPersonneMorale(ActionForm form, Client client) throws Exception{


       
        PilotageForm pilotageForm = 
           (PilotageForm)form;


            pilotageForm.setTypePersonneMoral(client.getTypePers().getLibTperTper());
            pilotageForm.setCategoriePersonneMoral(client.getPersonne().getCategoriePersonne().getLibCatpCatp());

            pilotageForm.setTypePieceMoral(client.getPersonne().getTypePiece().getLibSiglTpce());
            pilotageForm.setDateDelivMoral(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
            pilotageForm.setNumPieceMoral(client.getPersonne().getNumPcePers());

            if (client.getPersonne().getGouvernorat() != null) {
                GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                Gouvernorat gouvernorat = new Gouvernorat();
                gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                pilotageForm.setLieuDelivMoral(gouvernorat.getLibGouvGouv().toString());
                pilotageForm.setCodLieuDelivMoral(gouvernorat.getCodGouvGouv().toString());
                pilotageForm.setTypeDelivMoral("G");
            } 
            
            pilotageForm.setRaisonSocialMoral(client.getPersonne().getNomRsPers());
            pilotageForm.setSigleMoral(client.getPersonne().getLibSiglPers());

            
             if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                 //extraire la nationalité
                 GetPaysCmd getPaysCmd = new GetPaysCmd();
                 Pays pays = new Pays();
                 pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                 pays = (Pays)getPaysCmd.execute(pays);                
                 pilotageForm.setNationaliteMoral(pays.getLibNatPays());
                //pilotageForm.setCodNationaliteMoral(pays.getCodPaysPays());
             }
            pilotageForm.setResidenceMoral(client.getPersonne().getBoolResPers().toString());
            pilotageForm.setSecteurActMoral(client.getPersonne().getCodSectPers());

            if (client.getPersonne().getActivite() != null) {
                pilotageForm.setActiviteMoral(client.getPersonne().getActivite().getLibActAct());
            }            
            
            // adresse professionnelle
            if (client.getPersonne().getAdresseProf() != null) {
                // Immeuble
                if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                    pilotageForm.setImmeubleAdrResidMoral(client.getPersonne().getAdresseProf().getImmeuble());
                }
                // Rue
                if (client.getPersonne().getAdresseProf().getRue() != null) {
                    pilotageForm.setRueAdrResidMoral(client.getPersonne().getAdresseProf().getRue());
                }

                // Cite
                if (client.getPersonne().getAdresseProf().getCite() != null) {
                    pilotageForm.setCiteAdrResidMoral(client.getPersonne().getAdresseProf().getCite());
                }

                // Pays 
                if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                    pilotageForm.setCodPayAdrResidMoral(client.getPersonne().getAdresseProf().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        pilotageForm.setPaysAdrResidMoral(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                    pilotageForm.setCodePostalAdrResidMoral(client.getPersonne().getAdresseProf().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                        (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        pilotageForm.setLibPostalAdrResidMoral(codePostal.getLibCpCp());

                        // gouvernerat
                        pilotageForm.setCodGouvGouvMoral(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        pilotageForm.setLibGouvGouvMoral(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse professionnelle            
            String adr= pilotageForm.getImmeubleAdrResidMoral()+" "+pilotageForm.getRueAdrResidMoral()+" "+
                        pilotageForm.getCiteAdrResidMoral()+" "+pilotageForm.getLibPostalAdrResidMoral();
            pilotageForm.setAdresse(adr); 
            pilotageForm.setGouvertnorat(pilotageForm.getLibGouvGouvMoral());
            //Informations des dates  
            pilotageForm.setDateCreationMoral(DateHandler.dateToStr(client.getPersonne().getDatPmePers()));
            pilotageForm.setDateActMoral(DateHandler.dateToStr(client.getPersonne().getDateExpPers()));
            pilotageForm.setNumLoiCreMoral(client.getPersonne().getNumLpmePers());
            pilotageForm.setNumJortMoral(client.getPersonne().getNumJortPers());
            pilotageForm.setDatJortMoral(DateHandler.dateToStr(client.getPersonne().getDatJortPers()));
            pilotageForm.setNumDecretMoral(client.getPersonne().getNumDecrPers());
            pilotageForm.setDatDecretMoral(DateHandler.dateToStr(client.getPersonne().getDatDecrPers()));
            pilotageForm.setNumTelMoral(client.getPersonne().getNumTelPers());
            pilotageForm.setNumFaxMoral(client.getPersonne().getNumFaxPers());
            pilotageForm.setAdrMailMoral(client.getPersonne().getAdrMailPers());
            pilotageForm.setAdrWebMoral(client.getPersonne().getAdrWebPers());
            pilotageForm.setAdrSwiftMoral((client.getPersonne().getAdrSwiftPers()));
            pilotageForm.setAdrTelexMoral(client.getPersonne().getAdrTlxPers());

            

    }
    public void affecterDonneeEnCour(ParamClientVo paramClientVo,ActionForm form){
        PilotageForm pilotageForm = 
           (PilotageForm)form;
        Double montDepot=Double.valueOf("0");
        Double montCtx=Double.valueOf("0");
       
        pilotageForm.setNbrDemChq(paramClientVo.getNbrDemChq()); 
        pilotageForm.setNbrDemCart(paramClientVo.getNbrDemCart()); 
        pilotageForm.setNbrDemOpp(paramClientVo.getNbrDemOpp());
        pilotageForm.setNbrDepot(paramClientVo.getNbrDepot());
        pilotageForm.setNbrCtx(paramClientVo.getNbrCtx());
        pilotageForm.setNbrEparg(paramClientVo.getNbrEparg());
        pilotageForm.setNbrEpargLie(paramClientVo.getNbrEpargLie());
        pilotageForm.setNbrEng(paramClientVo.getNbrEng());
        pilotageForm.setNbrPlacement(paramClientVo.getNbrPlacement());
        pilotageForm.setMntPlacement(paramClientVo.getMntPlacement());
        montDepot=paramClientVo.getMntDepot();
        montCtx=paramClientVo.getMntCtx();
        if (montDepot<0){
            montDepot=montDepot*(-1);
            pilotageForm.setSigDepot("DB");
        }else{
            pilotageForm.setSigDepot("CR");
        }
        if (montCtx<0){
            montCtx=montCtx*(-1);
            pilotageForm.setSigCtx("DB");
        }else{
            pilotageForm.setSigCtx("CR");
        }
        pilotageForm.setMntDepot(StrHandler.formatmnt(Math.abs(paramClientVo.getMntDepot())));
        pilotageForm.setMntCtx(StrHandler.formatmnt(Math.abs(paramClientVo.getMntCtx())));
        pilotageForm.setMntEng(paramClientVo.getMntEng());
        pilotageForm.setMntEparg(paramClientVo.getMntEparg());
        pilotageForm.setMntEparglie(paramClientVo.getMntEpargLie());
        pilotageForm.setNbrInter(paramClientVo.getNbrInterdict());
        pilotageForm.setMntFacilte(paramClientVo.getMntFacilDepot());
        
    }
    public void affecterDonnesContrat(ActionForm form, ContratCpt contratCpt) throws Exception{

        
        PilotageForm pilotageForm = 
           (PilotageForm)form;

            GetDetailCategorieContratCmd getDetailCategorieContratCmd = new GetDetailCategorieContratCmd();
            DetailCatCpt detailCatCpt = new DetailCatCpt();
            ContratCptId contratCptId = new ContratCptId();
            
            pilotageForm.setCodeProduitCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                            '0', 
                                                                            4));
            pilotageForm.setCodePrdCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                        '0', 
                                                                        4));
            pilotageForm.setCodeStructureCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                              '0', 
                                                                              3));
            pilotageForm.setNumCompteCpt(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                          '0', 
                                                                          6));
            
            pilotageForm.setCodeDeviseCpt(contratCpt.getDevise().getCodDevDev().toString());
            pilotageForm.setLibDeviseCpt(contratCpt.getDevise().getLibDevDev());
            pilotageForm.setLibelleProduitCpt(contratCpt.getProduit().getLibPrdPrd());
            pilotageForm.setDateOuvertureCpt(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
            pilotageForm.setIntituleCompteCpt(contratCpt.getNomIntiCcpt());

            pilotageForm.setTypePieceCpt(contratCpt.getClient().getPersonne().getTypePiece().getLibSiglTpce());
            pilotageForm.setNomCpt(pilotageForm.getNomId());
            pilotageForm.setNumFiscaleCpt(contratCpt.getClient().getNumFiscClt());
            pilotageForm.setNumPieceCpt(contratCpt.getClient().getPersonne().getNumPcePers());
            pilotageForm.setCodeDouaneCpt(contratCpt.getClient().getCodDoanClt());
            pilotageForm.setDateRelationCpt(DateHandler.dateToStr(contratCpt.getClient().getDatRelClt()));
            pilotageForm.setPrenomCpt(pilotageForm.getPrenomId());
            pilotageForm.setNumBctCpt(contratCpt.getClient().getNumBctClt());
            pilotageForm.setNumRnePers(contratCpt.getClient().getNumRnePers());
            
            // adresse de correspondance
            if (contratCpt.getAdresseCorresp() != null) {
                // Immeuble
                if (contratCpt.getAdresseCorresp().getImmeuble() != null) {
                    pilotageForm.setImmeubleCpt(contratCpt.getAdresseCorresp().getImmeuble());
                }
                // Rue
                if (contratCpt.getAdresseCorresp().getRue() != null) {
                    pilotageForm.setRueCpt(contratCpt.getAdresseCorresp().getRue());
                }

                // Cite
                if (contratCpt.getAdresseCorresp().getCite() != null) {
                    pilotageForm.setCiteCpt(contratCpt.getAdresseCorresp().getCite());
                }

                // Pays 
                if (contratCpt.getAdresseCorresp().getCodPaysPays() != null) {
                    pilotageForm.setCodPayCpt(contratCpt.getAdresseCorresp().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(contratCpt.getAdresseCorresp().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        pilotageForm.setPaysCpt(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (contratCpt.getAdresseCorresp().getCodCpCp() != null) {
                    pilotageForm.setCodePostalCpt(contratCpt.getAdresseCorresp().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((contratCpt.getAdresseCorresp().getCodPaysPays() != null) && 
                        (contratCpt.getAdresseCorresp().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(contratCpt.getAdresseCorresp().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        pilotageForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                        
                        // gouvernerat
                        pilotageForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        pilotageForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse de correspondance
            
             if(contratCpt.getCodPerCpt() != null)
               pilotageForm.setPeridiciteCpt(contratCpt.getCodPerCpt());
             if(contratCpt.getCodFoncCpt()!= null)
              pilotageForm.setFonctionementCpt(contratCpt.getCodFoncCpt());
             if(contratCpt.getBoolRelvCpt() != null)
               pilotageForm.setReleveCpt(contratCpt.getBoolRelvCpt().toString());             
             
            contratCptId.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd());
            contratCptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
            contratCptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
            
            detailCatCpt = (DetailCatCpt)getDetailCategorieContratCmd.execute(contratCptId);
            
            
            if(detailCatCpt.getNumDccDcc()!=null){
                pilotageForm.setCodeCategorieEpargne(detailCatCpt.getCategorie().getLibCatCat());
                pilotageForm.setCodeRegimeEpargne(detailCatCpt.getCategorie().getRegime().getLibRgmRgm());
                pilotageForm.setMntCapitaliseEpargne(detailCatCpt.getCategorie().getMontCaptCat().toString());
                pilotageForm.setMntVersementEpargne(detailCatCpt.getCategorie().getMontVersCat().toString());
                pilotageForm.setNumLivretEpargne(contratCpt.getNumLivrCcpt());
                pilotageForm.setPlanEpargne("true");
            }else{
                pilotageForm.setPlanEpargne("false");
                StringBuffer text = new StringBuffer(" Aucun détail catégorie pour le compte :");
                text.append(pilotageForm.getCodStrcRech()); text.append(pilotageForm.getCodPrdRech());
                text.append(pilotageForm.getNumCompteCpt()); text.append(" >> aucun enregistrement dans la table detail_cat_cpt, ou bien la date fin n'est pas vide");
                logger.debug(text.toString());
            }
     
       

    }
    public ActionForward detailVision(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
         PilotageForm pilotageForm = (PilotageForm)form;
        try {
        /*demande cheques*/
        ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
        ContratPersonne contratPersonne = new ContratPersonne();
        ContratCptId contratCptId = new ContratCptId();
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(new Long(pilotageForm.getTypePieceId()));
        personneStrc.setNumPcePers(pilotageForm.getNumPieceId());
        contratCptId.setCodStrcStrc(new Long(pilotageForm.getCodeAgance()));
        contratPersonne.setContratCptId(contratCptId);
        contratPersonne.setPersonneId(personneStrc);
        paramDemandeCheque.setContratPersonne(contratPersonne);
        ListesDemandesCheques listesDemandesCheques = 
            new ListesDemandesCheques();
        GetListDemandesChequesCmd getListDemandesChequesCmd = 
            new GetListDemandesChequesCmd();
        listesDemandesCheques = 
                (ListesDemandesCheques)getListDemandesChequesCmd.execute(paramDemandeCheque);
        if (listesDemandesCheques.getListeGenerale() != null && 
            listesDemandesCheques.getListeGenerale().size() > 0) {
            List listeAttenteView = new ArrayList();
            listeAttenteView = 
                    traiterListedemandes(listesDemandesCheques.getListeGenerale(), 
                                         pilotageForm);
            pilotageForm.setListeDemandesCheques(listeAttenteView);
        }
        
        /*demande carte*/
        ParamRechercheDemandeCarte paramRecherche=new ParamRechercheDemandeCarte();
        PersonneStrc porteur = new PersonneStrc();
        porteur.setCodTpceTpce(new Long(pilotageForm.getTypePieceId()));
        porteur.setNumPcePers(pilotageForm.getNumPieceId());
       /* paramRecherche.setPorteur(porteur);
        GetListDemandesCartesCmd getListDemandesCartesCmd = new GetListDemandesCartesCmd(); 
        Listes listes = (Listes) getListDemandesCartesCmd.execute(paramRecherche);
        List listDemandesCarte = new ArrayList();
        listDemandesCarte = listes.getList();
        List viewList = new ArrayList(); 
        viewList = setDemandesCartesView(listDemandesCarte);
        pilotageForm.setListeDemandesCartes(viewList);*/
        
        /*contrat placement*/
         ParamDemandeDecision paramDemandeDecision = 
             new ParamDemandeDecision();
         GetListContratsPlacementCmd getListContratsPlacementCmd = 
             new GetListContratsPlacementCmd();
         Listes listContratPlacement = new Listes();
        List listCplacView = new ArrayList();

         
         GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
         Personne pers = (Personne)getPersonneCmd.execute(porteur);
         paramDemandeDecision.setNumSeqPers(pers.getNumSeqPers());
         paramDemandeDecision.setCodEtatContrat("V");
        listContratPlacement = 
                (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
        listCplacView = 
           traiterListeContratPlacement(listContratPlacement.getList(), pilotageForm);
        pilotageForm.setListeContratPlacement(listCplacView);
        return mapping.findForward("detailVision");
        
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :detailVision ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        logger.error("Erreur au niveau de l'agence <<" +pilotageForm.getCodStrcRech()+ ">>. Exception : ",e);  
    //    logger.error("Exception : ",e); 
        ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");
    }

    }
    public List traiterListeContratPlacement(List listContrats, ActionForm form) {
        PilotageForm pilotageForm = (PilotageForm)form;
        List listContratsPlacementView = new ArrayList();
        Double sommeMontant =Double.valueOf("0");
        try {
            if (listContrats != null && listContrats.size() > 0) {

                for (Iterator it = listContrats.iterator(); it.hasNext(); ) {
                    ContratPlacement contratPlacement = 
                        (ContratPlacement)it.next();
                    ContratPlacementView contratPlacementView = 
                        new ContratPlacementView();
                    contratPlacementView.setContratPlacement(contratPlacement);
                    contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
                    contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                    contratPlacementView.setDuree(contratPlacement.getNumNbrjCpla().toString());
                    contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                    contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                    contratPlacementView.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
                    contratPlacementView.setLibPrdPrd(contratPlacement.getProduitPlacement().getLibAbrPlc());
                    if (contratPlacement.getCodPintCpla().equals("PRE")) {
                        contratPlacementView.setCodPintCpla("PRE");
                        contratPlacementView.setLibPintCpla("A L'AVANCE");
                    } else {
                        contratPlacementView.setCodPintCpla("POST");
                        contratPlacementView.setLibPintCpla("A TERME ECHU");
                    }
                    contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                    contratPlacementView.setTauIRC(contratPlacement.getNumTircCpla().toString());
                    if (contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) || 
                        contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)) {
                        if (contratPlacement.getNumBcCpla() != null) {
                            contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
                        } else {
                            logger.error("Le numéro de BC est vide");
                        }
                    }
                    contratPlacementView.setTypeFaveurCpla(contratPlacement.getCodFavCpla());
                    if (contratPlacement.getContratCpt() != null) {
                        contratPlacementView.setNumCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3) + 
                                                            " " + StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) + 
                                                            " " + StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                    
                        contratPlacementView.setIntituleCpt(contratPlacement.getContratCpt().getNomIntiCcpt());
                    }

                    Personne pers = contratPlacement.getPersonne();
                    if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { // cas RCS affichage de libSiglPers 
                        contratPlacementView.setNomClient(pers.getLibSiglPers());
                        contratPlacementView.setPrenomClient(pers.getNomRsPers());
                    } else {
                        contratPlacementView.setNomClient(pers.getNomNomPers());
                        contratPlacementView.setPrenomClient(pers.getNomPrnPers());
                    }
                    sommeMontant = sommeMontant + contratPlacement.getMontCapCpla().doubleValue();
                    listContratsPlacementView.add(contratPlacementView);
                } // Fin For 
              //consultationPlacementForm.setSommeCapital(StrHandler.formatmnt(sommeMontant.doubleValue()));
            }
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeContratPlacement:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listContratsPlacementView;
    }
    private List setDemandesCartesView(List list){
        
        List viewList = new ArrayList(); 
        Iterator it = list.iterator();
        DemandeCarte demandeCarte = new DemandeCarte();
        
        for(;it.hasNext();){
            demandeCarte = (DemandeCarte)it.next();
            if (!demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_CarteRemis)){
            DemandeCarteView demandeCarteview = new DemandeCarteView();
            demandeCarteview.setDemandeCarte(demandeCarte);
            viewList.add(demandeCarteview); 
            }
        }  
        return viewList;
    }
    public List traiterListedemandes(List listDemandes, ActionForm form) {

        PilotageForm pilotageForm = 
           (PilotageForm)form;
        List listDemandeChequeView = new ArrayList();
        Context context = ContextHandler.getContext();
        DemandeChequeDAO demandeChequeDAO = 
            (DemandeChequeDAO)context.getBean("demandeChequeDAO");

        boolean ajouter = true;
        if (listDemandes != null && listDemandes.size() > 0) {


            for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                DemandeChequeView demandeChequeView = new DemandeChequeView();
                DemandeCheque demandeCheque = (DemandeCheque)it.next();
               
                demandeChequeView.setDemandeCheque(demandeCheque);
                demandeChequeView.setDateDemande(DateHandler.dateToStr(demandeCheque.getDatDemDchq()));

               
                    if (demandeCheque.getCodEtatDchq() != null) {
                        if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ATTENTE)) {
                            demandeChequeView.setEtatDemande("Attente");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_VALIDEE)) {
                            demandeChequeView.setEtatDemande("Validée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Tot Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE)) {
                            demandeChequeView.setEtatDemande("rejetée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Tot satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_SATISFAITE)) {
                            demandeChequeView.setEtatDemande("Part satisfaite");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_PART_DELIVREE)) {
                            demandeChequeView.setEtatDemande("Part Délivrée");
                        } else if (demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_ENVOYEE_DR)) {
                            demandeChequeView.setEtatDemande("Envoyée DGR/DCCI");
                        }
                    }
                    if (demandeCheque.getCodTpceDchq().equals(Constants.COD_CIN))
                        demandeChequeView.setTypePieceDem("CIN");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_RCS))
                        demandeChequeView.setTypePieceDem("RCS");
                    else if (demandeCheque.getCodTpceDchq().equals(Constants.COD_NUM_ORDRE))
                        demandeChequeView.setTypePieceDem("NUM");

                    demandeChequeView.setCodeAgence(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                    '0', 3));
                    demandeChequeView.setCodeProduit(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                     '0', 4));
                    demandeChequeView.setNumeroCompte(StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                      '0', 6));
                    
                    
                    if (demandeCheque.getBoolWebDchq() != null) {
                       if(demandeCheque.getBoolWebDchq().equals(Long.valueOf("1")))
                          demandeChequeView.setBoolWebDchq("Web");
                       else if(demandeCheque.getBoolWebDchq().equals(Long.valueOf("2")))
                          demandeChequeView.setBoolWebDchq("SMS");
                    }else demandeChequeView.setBoolWebDchq("Smile");  
                if (demandeCheque.getCodEtatDchq() != null) {
                    if (!demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_TOT_DELIVREE)) {
                    listDemandeChequeView.add(demandeChequeView);
                    }
                }
            } // Fin For  
        }
        return listDemandeChequeView;

    }


}

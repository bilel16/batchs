
package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Adresse;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.PieceAnnexeId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.Regime;
import com.bna.commun.model.RegimeId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tribunal;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetActiviteByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetGouvernoratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetProfessionByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetRibCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetTribunalTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerRgmCatEpargneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetDetailCategorieContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetPersClientCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.RejeterContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.TraitementValidationContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ListRgmCatEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamEpargne;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamValidationContrat;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.bna.smile.web.souscription.forms.ValidationContratCompteForm;

import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


public class ValidationContratCompteAction extends DispatchAction {

    /**
     * <B> Action de la page  souscriptionContratCompte.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.souscription.actions
     *
     * @author Hassine
     * @version le 03/05/2007
     */
    ParamAgence paramAgence = new ParamAgence();
    Personne personne = new Personne();
    Client client = new Client();
    private static final Logger logger = Logger.getLogger(ValidationContratCompteAction.class);
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
       
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "validationContratCompteForm"
        sessionUtil.removeSession(request,"validationContratCompteForm");
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
        try {
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");/// structure de l'agent qui fait la saisie
            /* garnir les informations sur l'agence (apartir du Login) */  
            validationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            validationContratCompteForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
            validationContratCompteForm.setChoix("2");
            validationContratCompteForm.clearForm();
            validationContratCompteForm.setNumPieceId("");
            validationContratCompteForm.setNumCcptRech("");
            validationContratCompteForm.setCodPrdRech("");
            validationContratCompteForm.setNumPieceId("");
            validationContratCompteForm.setDateDebut("");
            validationContratCompteForm.setDateFin("");
            
          //verification de l'habilitation sur cet operation
           StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
           boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            String d = myformat.format(new Date());
            validationContratCompteForm.setDateActuelle(d);
            
            
            return mapping.findForward("success");
            } catch (Exception e) {
                 ActionMessages actionMessages = new ActionMessages();
                                 ActionMessage actionMessage = 
                                     new ActionMessage("exception.generique", 
                                                       e.getMessage() );
                                 actionMessages.add("Erreur ", actionMessage);   
                                 this.saveMessages(request, actionMessages);
                                 logger.error("Exception : ",e);
                                 return mapping.findForward("error");  
           }
      
    }
  
    public ActionForward nouvelleRecherche(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
         
        ActionMessages actionMessages = new ActionMessages();
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
        try {
           
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            validationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            validationContratCompteForm.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());            
            validationContratCompteForm.clearForm();
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            String d = myformat.format(new Date());
            validationContratCompteForm.setDateActuelle(d);
            
            
            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :initierPage ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
              //  logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
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
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
         
        try {
           
            validationContratCompteForm.clearForm();
            ContratCpt premierContrat = new ContratCpt();
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            validationContratCompteForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());           
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());            
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            // si la recherche est effectuée par type et num piece
            if (validationContratCompteForm.getChoixRecherche().equals("typePiece")) {
                personneStrc.setCodTpceTpce(new Long(validationContratCompteForm.getTypePieceId()));
                personneStrc.setNumPcePers(validationContratCompteForm.getNumPieceId());                
                personneRechercheContratVo.setPersonneStrc(personneStrc);
                personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_ATT);
                GetListContratCmd getListContratCmd = new GetListContratCmd();
                listesCpts = (Listes)getListContratCmd.execute((IValueObject)personneRechercheContratVo);
                
            } else if (validationContratCompteForm.getChoixRecherche().equals("contrat")) {
                // si la recherche est effectuée par num de contrat
                GetDetailContratCmd getDetailContratCmd = 
                    new GetDetailContratCmd();
                ContratCptId contratCptId = new ContratCptId();
                ContratCpt contratCpt = new ContratCpt();
                contratCptId.setCodPrdPrd(new Long(validationContratCompteForm.getCodPrdRech()));
                contratCptId.setNumCcptCcpt(new Long(validationContratCompteForm.getNumCcptRech()));
                contratCptId.setCodStrcStrc(paramAgence.getCodStrcStrc());                
                contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
                if (contratCpt.getContratCptId() != null && contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_ATT) ) {
                    listesCpts.getList().add(contratCpt);
                    listesCpts.setList(new ArrayList());
                    listesCpts.getList().add(contratCpt);
                    personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                    validationContratCompteForm.setCodeEtatCpt(contratCpt.getCodEtatCcpt());
                    validationContratCompteForm.setContratCpt(contratCpt);
                }
            }else if(validationContratCompteForm.getChoixRecherche().equals("periode")){
                // si la recherche est effectuée par période
                 
                 personneRechercheContratVo.setDateDebut(DateHandler.strToDate(validationContratCompteForm.getDateDebut()));
                 personneRechercheContratVo.setDateFin(DateHandler.strToDate(validationContratCompteForm.getDateFin())); 
                 personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_ATT);                 
                 personneRechercheContratVo.setCodeAgence(paramAgence.getCodStrcStrc().toString());    
                 GetListContratCmd getListContratCmd = new GetListContratCmd();
                 listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);                
            }
            
                if(!listesCpts.hasError()){                
                     if (listesCpts.getList() == null ||listesCpts.getList().size()==0 ) {
                         validationContratCompteForm.setAlert("ClientInexistant");
                     } else {
                         if (listesCpts.getList().size() > 0) {
                             // affecter la liste des contrats à la liste de collection Tag
                             List listeDesContratsView = new ArrayList();
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
                                 listeDesContratsView.add(contratCptView);
                             }
                             validationContratCompteForm.setListeContrats(listeDesContratsView);

                             //  ************************************************************

                             validationContratCompteForm.setAlert("ClientExistant");
                             if (validationContratCompteForm.getChoixRecherche().equals("contrat") 
                              || ((validationContratCompteForm.getChoixRecherche().equals("typePiece")
                              ||validationContratCompteForm.getChoixRecherche().equals("periode"))&& listesCpts.getList().size() == 1)){
                               premierContrat = (ContratCpt)listesCpts.getList().get(0);
                               verifierTypePersonne(validationContratCompteForm,premierContrat);
                             }
                            
                             if (listesCpts.getList().size() == 1) {
                                 affecterDonnesContrat(validationContratCompteForm, 
                                                       premierContrat);
                                 validationContratCompteForm.setOpenTabsheetContrat("true");
                                 validationContratCompteForm.setContratCpt(premierContrat);
                                 
                             }
                         }else{
                             validationContratCompteForm.setAlert("ClientInexistant");
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
                    new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :rechercherPersonne : Un champs Obligatoire n'est pas garni au niveau de la base de données...");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
             //   logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }


    public void verifierTypePersonne(ActionForm form, ContratCpt contratCpt) throws Exception{
        
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;

        client = contratCpt.getClient();
        personne = contratCpt.getClient().getPersonne();
        validationContratCompteForm.setPersonne(personne);
        validationContratCompteForm.setClient(client);  
        validationContratCompteForm.setNomId("");
        validationContratCompteForm.setPrenomId("");
        validationContratCompteForm.setEtatClient(client.getCodEtatClt());
        
        if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
            validationContratCompteForm.setNomId(personne.getNomNomPers());
            validationContratCompteForm.setPrenomId(personne.getNomPrnPers());
            validationContratCompteForm.setOpenTabsheetClient("true");
            validationContratCompteForm.setOpenTabsheetMorale("false");
            validationContratCompteForm.setOpenTabsheetCotitulaire("false");
            validationContratCompteForm.setOpenTabsheetTuteur("false");
            affecterDonnesPersonnePhysique(validationContratCompteForm,client);
            //extaction du tuteur en ca d'un client mineur
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
                    validationContratCompteForm.setOpenTabsheetTuteur("true");
                    validationContratCompteForm.setTuteur(tuteur);
                    affecterDonnesPersonneTuteur(validationContratCompteForm, 
                                                 tuteur);
                }
            }
        } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
            validationContratCompteForm.setNomId(personne.getNomRsPers());
            validationContratCompteForm.setPrenomId(personne.getLibSiglPers());
            validationContratCompteForm.setOpenTabsheetMorale("true");
            validationContratCompteForm.setOpenTabsheetClient("false");
            validationContratCompteForm.setOpenTabsheetTuteur("false");
            validationContratCompteForm.setOpenTabsheetCotitulaire("false");
            affecterDonnesPersonneMorale(validationContratCompteForm, 
                                         client);

        } else if (client.getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
            validationContratCompteForm.setNomId(personne.getNomNomPers());
            affecterDonnesPersonneCotitulaire(validationContratCompteForm, personne);
            validationContratCompteForm.setOpenTabsheetMorale("false");
            validationContratCompteForm.setOpenTabsheetClient("false");
            validationContratCompteForm.setOpenTabsheetTuteur("false");
        }
        
        
    }
    
    
    
    public void affecterDonnesPersonnePhysique(ActionForm form, 
                                               Client client) throws Exception{

        

            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;
         try{
            validationContratCompteForm.setTypePersonneClt(client.getTypePers().getLibTperTper());
            validationContratCompteForm.setCategoriePersonneClt(client.getPersonne().getCategoriePersonne().getLibCatpCatp());

            validationContratCompteForm.setTypePieceClt(client.getPersonne().getTypePiece().getLibSiglTpce());
            validationContratCompteForm.setCodTypePieceId(client.getPersonne().getTypePiece().getCodTpceTpce().toString());
            validationContratCompteForm.setDateDelivClt(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
            validationContratCompteForm.setNumPieceClt(client.getPersonne().getNumPcePers());
            validationContratCompteForm.setCodeTypePersClt(client.getTypePers().getCodTperTper());
            validationContratCompteForm.setCodeCategoriePersClt(client.getPersonne().getCategoriePersonne().getCodCatpCatp());
            
            if (client.getPersonne().getGouvernorat() != null) {
                GetGouvernoratCmd getGouvernoratCmd = new GetGouvernoratCmd();
                Gouvernorat gouvernorat = new Gouvernorat();
                gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                gouvernorat = (Gouvernorat)getGouvernoratCmd.execute(gouvernorat);

                validationContratCompteForm.setLieuDelivClt(gouvernorat.getLibGouvGouv().toString());
                validationContratCompteForm.setCodLieuDelivClt(gouvernorat.getCodGouvGouv().toString());
            }
            
             PieceAnnexe pieceAnnexe = new PieceAnnexe();
              if (client.getPersonne().getPieceAnnexes() != null && client.getPersonne().getPieceAnnexes().size() > 0) {
                  for (Iterator it = client.getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                       pieceAnnexe = (PieceAnnexe)it.next();                     
                       if( pieceAnnexe.getDatFvalPian()!=null && pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                           validationContratCompteForm.setTypePieceAnnexe((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                           validationContratCompteForm.setDateDellivPiann(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                           validationContratCompteForm.setDateFinPian(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                           validationContratCompteForm.setNumPieceAnnexe(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                           break;
                       }                    
                  }
              }

            validationContratCompteForm.setTitrePersClt(client.getPersonne().getLibTitrPers());            
            validationContratCompteForm.setNomPersClt(client.getPersonne().getNomNomPers());
            validationContratCompteForm.setPrenomPersClt(client.getPersonne().getNomPrnPers());
            validationContratCompteForm.setNomPereClt(client.getPersonne().getNomPrnpPers());

            validationContratCompteForm.setDateNaisClt(DateHandler.dateToStr(client.getPersonne().getDatNaisPers()));
            validationContratCompteForm.setLieuNaisClt(client.getPersonne().getLibNaisPers());
           
            if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                //extraire la nationalité
                GetPaysCmd getPaysCmd = new GetPaysCmd();
                Pays pays = new Pays();
                pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                pays = (Pays)getPaysCmd.execute(pays);                
                validationContratCompteForm.setNationaliteClt(pays.getLibNatPays());
                validationContratCompteForm.setCodNationaliteClt(pays.getCodPaysPays());
            }
            
            validationContratCompteForm.setResidentClt(client.getPersonne().getBoolResPers().toString());
            validationContratCompteForm.setSexeClt(client.getPersonne().getCodSexPers());
            
            if(client.getPersonne().getPaysByCodNaisPays()!=null && client.getPersonne().getPaysByCodNaisPays()!= null )
              validationContratCompteForm.setPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getLibPaysPays());
            if(client.getPersonne().getPaysByCodNaisPays() != null && client.getPersonne().getPaysByCodNaisPays().getCodPaysPays() != null)
               validationContratCompteForm.setCodPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getCodPaysPays());
            
            validationContratCompteForm.setSitFamilialeClt(client.getPersonne().getCodSitfPers());
            validationContratCompteForm.setSectActiviteClt(client.getPersonne().getCodSectPers());
            
            if (client.getPersonne().getProfession() != null) {
            // extraire la profession
                validationContratCompteForm.setCodGroupProfClt(client.getPersonne().getProfession().getProfessionId().getCodGproGpro().toString());
                validationContratCompteForm.setCodProfClt(client.getPersonne().getProfession().getProfessionId().getCodProfProf().toString());            
                GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                Profession profession = new Profession();
                ProfessionId professionId = new ProfessionId();
                professionId.setCodProfProf(Long.valueOf(validationContratCompteForm.getCodProfClt()));
                professionId.setCodGproGpro(Long.valueOf(validationContratCompteForm.getCodGroupProfClt()));
                profession.setProfessionId(professionId);
                profession = (Profession)getProfessionByIdCmd.execute(profession);
                if (profession != null && profession.getLibProfProf() != null) {
                    validationContratCompteForm.setProfessionClt(profession.getLibProfProf());
                }
            }
            
            if (client.getPersonne().getActivite() != null) {
                // extraire l'activité
                validationContratCompteForm.setCodActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodActAct().toString());
                validationContratCompteForm.setCodClasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodCactCact().toString());
                validationContratCompteForm.setCodSclasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodSactSact().toString());

                GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                Activite activite = new Activite();
                ActiviteId activiteId = new ActiviteId();
                activiteId.setCodActAct(validationContratCompteForm.getCodActiviteClt());
                activiteId.setCodCactCact(validationContratCompteForm.getCodClasActiviteClt());
                activiteId.setCodSactSact(Long.valueOf(validationContratCompteForm.getCodSclasActiviteClt()));
                activite.setActiviteId(activiteId);
                activite = (Activite)getActiviteByIdCmd.execute(activite);
                if (activite != null && activite.getLibActAct() != null) {
                    validationContratCompteForm.setActiviteClt(activite.getLibActAct());
                }
            }
            
            // adresse de résidence
            if (client.getPersonne().getAdresseResid() != null) {
                // Immeuble
                if (client.getPersonne().getAdresseResid().getImmeuble() != null) {
                    validationContratCompteForm.setImmeubleAdrResid(client.getPersonne().getAdresseResid().getImmeuble());
                }
                // Rue
                if (client.getPersonne().getAdresseResid().getRue() != null) {
                    validationContratCompteForm.setRueAdrResid(client.getPersonne().getAdresseResid().getRue());
                }

                // Cite
                if (client.getPersonne().getAdresseResid().getCite() != null) {
                    validationContratCompteForm.setCiteAdrResid(client.getPersonne().getAdresseResid().getCite());
                }

                // Pays 
                if (client.getPersonne().getAdresseResid().getCodPaysPays() != null) {
                    validationContratCompteForm.setCodPayAdrResid(client.getPersonne().getAdresseResid().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getAdresseResid().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        validationContratCompteForm.setPaysAdrResid(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (client.getPersonne().getAdresseResid().getCodCpCp() != null) {
                    validationContratCompteForm.setCodePostalAdrResid(client.getPersonne().getAdresseResid().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((client.getPersonne().getAdresseResid().getCodPaysPays() != null) && 
                        (client.getPersonne().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseResid().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        validationContratCompteForm.setLibPostalAdrResid(codePostal.getLibCpCp());

                        // gouvernerat
                        validationContratCompteForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        validationContratCompteForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse de résidence
                         
     
             // adresse professionnelle
             if (client.getPersonne().getAdresseProf() != null) {
                 // Immeuble
                 if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                     validationContratCompteForm.setImmeubleAdrProf(client.getPersonne().getAdresseProf().getImmeuble());
                 }
                 // Rue
                 if (client.getPersonne().getAdresseProf().getRue() != null) {
                     validationContratCompteForm.setRueAdrProf(client.getPersonne().getAdresseProf().getRue());
                 }

                 // Cite
                 if (client.getPersonne().getAdresseProf().getCite() != null) {
                     validationContratCompteForm.setCiteAdrProf(client.getPersonne().getAdresseProf().getCite());
                 }

                 // Pays 
                 if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                     validationContratCompteForm.setCodPayAdrProf(client.getPersonne().getAdresseProf().getCodPaysPays());
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     Pays pays = new Pays();
                     pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                     pays = (Pays)getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null) {
                         validationContratCompteForm.setPaysAdrProf(pays.getLibPaysPays());
                     }
                 }
                 // Code Postal
                 if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                     validationContratCompteForm.setCodePostalAdrProf(client.getPersonne().getAdresseProf().getCodCpCp());
                     // si le pays est la tunisie extraire le libelle du code postal
                     if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                         (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                         GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                         CodePostal codePostal = new CodePostal();
                         codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                         codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                         validationContratCompteForm.setLibPostalAdrProf(codePostal.getLibCpCp());

                         // gouvernerat
                         validationContratCompteForm.setCodGouvGouvProf(codePostal.getGouvernorat().getCodGouvGouv().toString());
                         validationContratCompteForm.setLibGouvGouvProf(codePostal.getGouvernorat().getLibGouvGouv());
                     }
                 }
             } // Fin adresse professionnelle
     
            
              validationContratCompteForm.setNumTelPers(client.getPersonne().getNumTelPers());
              validationContratCompteForm.setNumFaxpers(client.getPersonne().getNumFaxPers());
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : affecterDonnesPersonnePhysique :  ",e);  
               throw new RuntimeException(e);                   
        }   

    }

    public void affecterDonnesContrat(ActionForm form, ContratCpt contratCpt) throws Exception{

        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
            
            try{
            
            
            GetDetailCategorieContratCmd getDetailCategorieContratCmd = new GetDetailCategorieContratCmd();
            DetailCatCpt detailCatCpt = new DetailCatCpt();
            ContratCptId contratCptId = new ContratCptId();
            
            validationContratCompteForm.setCodeProduitCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                            '0', 
                                                                            4));
            validationContratCompteForm.setCodePrdCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                        '0', 
                                                                        4));
            validationContratCompteForm.setCodeStructureCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                              '0', 
                                                                              3));
            validationContratCompteForm.setNumCompteCpt(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                          '0', 
                                                                          6));
            validationContratCompteForm.setCodeDeviseCpt(contratCpt.getDevise().getCodDevDev().toString());
            validationContratCompteForm.setLibDeviseCpt(contratCpt.getDevise().getLibDevDev());
            validationContratCompteForm.setLibelleProduitCpt(contratCpt.getProduit().getLibPrdPrd());
            validationContratCompteForm.setDateOuvertureCpt(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
            validationContratCompteForm.setIntituleCompteCpt(contratCpt.getNomIntiCcpt());

            validationContratCompteForm.setTypePieceCpt(contratCpt.getClient().getPersonne().getTypePiece().getLibSiglTpce());
            validationContratCompteForm.setNomCpt(validationContratCompteForm.getNomId());
            validationContratCompteForm.setNumFiscaleCpt(contratCpt.getClient().getNumFiscClt());
            validationContratCompteForm.setNumPieceCpt(contratCpt.getClient().getPersonne().getNumPcePers());
            validationContratCompteForm.setCodeDouaneCpt(contratCpt.getClient().getCodDoanClt());
            validationContratCompteForm.setDateRelationCpt(DateHandler.dateToStr(contratCpt.getClient().getDatRelClt()));
            validationContratCompteForm.setPrenomCpt(validationContratCompteForm.getPrenomId());
            validationContratCompteForm.setNumBctCpt(contratCpt.getClient().getNumBctClt());
            validationContratCompteForm.setNumRnePers(contratCpt.getClient().getNumRnePers());
            
            // adresse de correspondance
            if (contratCpt.getAdresseCorresp() != null) {
                // Immeuble
                if (contratCpt.getAdresseCorresp().getImmeuble() != null) {
                    validationContratCompteForm.setImmeubleCpt(contratCpt.getAdresseCorresp().getImmeuble());
                }
                // Rue
                if (contratCpt.getAdresseCorresp().getRue() != null) {
                    validationContratCompteForm.setRueCpt(contratCpt.getAdresseCorresp().getRue());
                }

                // Cite
                if (contratCpt.getAdresseCorresp().getCite() != null) {
                    validationContratCompteForm.setCiteCpt(contratCpt.getAdresseCorresp().getCite());
                }

                // Pays 
                if (contratCpt.getAdresseCorresp().getCodPaysPays() != null) {
                    validationContratCompteForm.setCodPayCpt(contratCpt.getAdresseCorresp().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(contratCpt.getAdresseCorresp().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        validationContratCompteForm.setPaysCpt(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (contratCpt.getAdresseCorresp().getCodCpCp() != null) {
                    validationContratCompteForm.setCodePostalCpt(contratCpt.getAdresseCorresp().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((contratCpt.getAdresseCorresp().getCodPaysPays() != null) && 
                        (contratCpt.getAdresseCorresp().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(contratCpt.getAdresseCorresp().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        validationContratCompteForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                        
                        // gouvernerat
                        validationContratCompteForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        validationContratCompteForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse de correspondance
            
             if(contratCpt.getCodPerCpt() != null)
               validationContratCompteForm.setPeridiciteCpt(contratCpt.getCodPerCpt());
             if(contratCpt.getCodFoncCpt() != null)  
               validationContratCompteForm.setFonctionementCpt(contratCpt.getCodFoncCpt());
             if(contratCpt.getBoolRelvCpt()!= null)
               validationContratCompteForm.setReleveCpt(contratCpt.getBoolRelvCpt().toString());             
             
            contratCptId.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd());
            contratCptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
            contratCptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
            detailCatCpt = (DetailCatCpt)getDetailCategorieContratCmd.execute(contratCptId);
            validationContratCompteForm.setNumLivretEpargne(contratCpt.getNumLivrCcpt());
           
            if(detailCatCpt.getNumDccDcc()!=null){
                validationContratCompteForm.setTypeRequest("ouverture");
                garnirParmEpargne(validationContratCompteForm,detailCatCpt);
                validationContratCompteForm.setDetailCatCpt(detailCatCpt);
                validationContratCompteForm.setCodeCategorieEpargne(detailCatCpt.getCategorie().getCategorieId().getCodCatCat());
                validationContratCompteForm.setCodeRegimeEpargne(detailCatCpt.getCategorie().getRegime().getRegimeId().getCodRgmRgm().toString());
                validationContratCompteForm.setMntCapitaliseEpargne(detailCatCpt.getCategorie().getMontCaptCat().toString());
                validationContratCompteForm.setMntVersementEpargne(detailCatCpt.getCategorie().getMontVersCat().toString());
                
                validationContratCompteForm.setPlanEpargne("true");
            }else{
                validationContratCompteForm.setPlanEpargne("false");
                StringBuffer text = new StringBuffer(" Aucun détail catégorie pour le compte :");
                text.append(validationContratCompteForm.getCodStrcRech()); text.append(validationContratCompteForm.getCodPrdRech());
                text.append(validationContratCompteForm.getNumCompteCpt()); text.append(" >> aucun enregistrement dans la table detail_cat_cpt");
                logger.debug(text.toString());
            }
            
            } catch (Exception e) {
                   logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : affecterDonnesContrat :  ",e);  
                   throw new RuntimeException(e);                  
            }
     
       

    }


    public void affecterDonnesPersonneTuteur(ActionForm form, 
                                             Personne tuteur) throws Exception{
        


            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;
            try{
            validationContratCompteForm.setTypePersonneTuteur("Personne physique individuelle");
            validationContratCompteForm.setCategoriePersonneTuteur(tuteur.getCategoriePersonne().getCodCatpCatp());

            validationContratCompteForm.setTypePieceTut(tuteur.getTypePiece().getCodTpceTpce().toString());            
            validationContratCompteForm.setDateDelivTuteur(DateHandler.dateToStr(tuteur.getDatDlvPers()));
            validationContratCompteForm.setNumPieceTut(tuteur.getNumPcePers());
            
            if (tuteur.getGouvernorat() != null) {
                GetGouvernoratCmd getGouvernoratCmd = new GetGouvernoratCmd();
                Gouvernorat gouvernorat = new Gouvernorat();
                gouvernorat.setCodGouvGouv(tuteur.getGouvernorat().getCodGouvGouv());
                gouvernorat = (Gouvernorat)getGouvernoratCmd.execute(gouvernorat);

                validationContratCompteForm.setLieuDelivTuteur(gouvernorat.getLibGouvGouv().toString());
                validationContratCompteForm.setCodLieuDelivTuteur(gouvernorat.getCodGouvGouv().toString());
            }
           
            /*if (tuteur.getPieceAnnexes() != null && tuteur.getPieceAnnexes().size() > 0) {
                List l = (List)tuteur.getPieceAnnexes();
                PieceAnnexe pieceAnnexe = (PieceAnnexe)l.get(0);
                validationContratCompteForm.setTypePieceAnnexeTuteur((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                validationContratCompteForm.setDateDellivPiannTuteur(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                validationContratCompteForm.setDateFinPianTuteur(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                validationContratCompteForm.setNumPieceAnnexeTuteur(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
            }*/
            
             PieceAnnexe pieceAnnexe = new PieceAnnexe();
              if (tuteur.getPieceAnnexes() != null && tuteur.getPieceAnnexes().size() > 0) {
                  for (Iterator it = tuteur.getPieceAnnexes().iterator(); it.hasNext(); ) {
                       pieceAnnexe = (PieceAnnexe)it.next();                     
                       if( pieceAnnexe.getDatFvalPian()!=null && pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                           validationContratCompteForm.setTypePieceAnnexeTuteur((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                           validationContratCompteForm.setDateDellivPiannTuteur(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                           validationContratCompteForm.setDateFinPianTuteur(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                           validationContratCompteForm.setNumPieceAnnexeTuteur(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                           break;
                       }                    
                  }
              }

            validationContratCompteForm.setTitrePersTuteur(tuteur.getLibTitrPers());
            validationContratCompteForm.setNomPersTuteur(tuteur.getNomNomPers());
            validationContratCompteForm.setPrenomPersTuteur(tuteur.getNomPrnPers());
            validationContratCompteForm.setNomPereTuteur(tuteur.getNomPrnpPers());

            validationContratCompteForm.setDateNaisTuteur(DateHandler.dateToStr(tuteur.getDatNaisPers()));
            validationContratCompteForm.setLieuNaisTuteur(tuteur.getLibNaisPers());
           
            
            if (tuteur.getPaysByCodNat1Pays() != null) {
                //extraire la nationalité
                GetPaysCmd getPaysCmd = new GetPaysCmd();
                Pays pays = new Pays();
                pays.setCodPaysPays(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
                pays = (Pays)getPaysCmd.execute(pays);                
                validationContratCompteForm.setNationaliteTuteur(pays.getLibNatPays());
                validationContratCompteForm.setCodNationaliteTuteur(pays.getCodPaysPays());
            }
            
            validationContratCompteForm.setPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getLibPaysPays());
            validationContratCompteForm.setCodPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
            validationContratCompteForm.setResidentTuteur(tuteur.getBoolResPers().toString());
            validationContratCompteForm.setSexeTuteur(tuteur.getCodSexPers());
            
            validationContratCompteForm.setSectActiviteTuteur(tuteur.getCodSectPers());
            validationContratCompteForm.setSitFamilialeTuteur(tuteur.getCodSitfPers());
            validationContratCompteForm.setFormeJuridiqueTuteur(tuteur.getFormeJuridique().getCodFjFj());

            
            if (tuteur.getProfession() != null) {
            // extraire la profession
                validationContratCompteForm.setCodGroupProfTuteur(tuteur.getProfession().getProfessionId().getCodGproGpro().toString());
                validationContratCompteForm.setCodProfTuteur(tuteur.getProfession().getProfessionId().getCodProfProf().toString());            
                GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                Profession profession = new Profession();
                ProfessionId professionId = new ProfessionId();
                professionId.setCodProfProf(Long.valueOf(validationContratCompteForm.getCodProfTuteur()));
                professionId.setCodGproGpro(Long.valueOf(validationContratCompteForm.getCodGroupProfTuteur()));
                profession.setProfessionId(professionId);
                profession = (Profession)getProfessionByIdCmd.execute(profession);
                if (profession != null && profession.getLibProfProf() != null) {
                    validationContratCompteForm.setProfessionTuteur(profession.getLibProfProf());
                }
            }
            
            if (tuteur.getActivite() != null) {
                // extraire l'activité
                validationContratCompteForm.setCodActiviteTuteur(tuteur.getActivite().getActiviteId().getCodActAct().toString());
                validationContratCompteForm.setCodClasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodCactCact().toString());
                validationContratCompteForm.setCodSclasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodSactSact().toString());

                GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                Activite activite = new Activite();
                ActiviteId activiteId = new ActiviteId();
                activiteId.setCodActAct(validationContratCompteForm.getCodActiviteTuteur());
                activiteId.setCodCactCact(validationContratCompteForm.getCodClasActiviteTuteur());
                activiteId.setCodSactSact(Long.valueOf(validationContratCompteForm.getCodSclasActiviteTuteur()));
                activite.setActiviteId(activiteId);
                activite = (Activite)getActiviteByIdCmd.execute(activite);
                if (activite != null && activite.getLibActAct() != null) {
                    validationContratCompteForm.setActiviteTuteur(activite.getLibActAct());
                }
            }
            
            // adresse de résidence
            if (tuteur.getAdresseResid() != null) {
                // Immeuble
                if (tuteur.getAdresseResid().getImmeuble() != null) {
                    validationContratCompteForm.setImmeubleAdrResidTuteur(tuteur.getAdresseResid().getImmeuble());
                }
                // Rue
                if (tuteur.getAdresseResid().getRue() != null) {
                    validationContratCompteForm.setRueAdrResidTuteur(tuteur.getAdresseResid().getRue());
                }

                // Cite
                if (tuteur.getAdresseResid().getCite() != null) {
                    validationContratCompteForm.setCiteAdrResidTuteur(tuteur.getAdresseResid().getCite());
                }
                // Pays 
                if (tuteur.getAdresseResid().getCodPaysPays() != null) {
                    validationContratCompteForm.setCodPayAdrResidTuteur(tuteur.getAdresseResid().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(tuteur.getAdresseResid().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        validationContratCompteForm.setPaysAdrResidTuteur(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (tuteur.getAdresseResid().getCodCpCp() != null) {
                    validationContratCompteForm.setCodePostalAdrResidTuteur(tuteur.getAdresseResid().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((tuteur.getAdresseResid().getCodPaysPays() != null) && 
                        (tuteur.getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseResid().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        validationContratCompteForm.setLibPostalAdrResidTuteur(codePostal.getLibCpCp());

                        // gouvernerat
                        validationContratCompteForm.setCodGouvGouvResTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        validationContratCompteForm.setLibGouvGouvResTuteur(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse de résidence
                         
            
             // adresse professionnelle
             if (tuteur.getAdresseProf() != null) {
                 // Immeuble
                 if (tuteur.getAdresseProf().getImmeuble() != null) {
                     validationContratCompteForm.setImmeubleAdrProfTuteur(tuteur.getAdresseProf().getImmeuble());
                 }
                 // Rue
                 if (tuteur.getAdresseProf().getRue() != null) {
                     validationContratCompteForm.setRueAdrProfTuteur(tuteur.getAdresseProf().getRue());
                 }
                 // Cite
                 if (tuteur.getAdresseProf().getCite() != null) {
                     validationContratCompteForm.setCiteAdrProfTuteur(tuteur.getAdresseProf().getCite());
                 }

                 // Pays 
                 if (tuteur.getAdresseProf().getCodPaysPays() != null) {
                     validationContratCompteForm.setCodPayAdrProfTuteur(tuteur.getAdresseProf().getCodPaysPays());
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     Pays pays = new Pays();
                     pays.setCodPaysPays(tuteur.getAdresseProf().getCodPaysPays());
                     pays = (Pays)getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null) {
                         validationContratCompteForm.setPaysAdrProfTuteur(pays.getLibPaysPays());
                     }
                 }
                 // Code Postal
                 if (tuteur.getAdresseProf().getCodCpCp() != null) {
                     validationContratCompteForm.setCodePostalAdrProfTuteur(tuteur.getAdresseProf().getCodCpCp());
                     // si le pays est la tunisie extraire le libelle du code postal
                     if ((tuteur.getAdresseProf().getCodPaysPays() != null) && 
                         (tuteur.getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                         GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                         CodePostal codePostal = new CodePostal();
                         codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseProf().getCodCpCp()));
                         codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                         validationContratCompteForm.setLibPostalAdrProfTuteur(codePostal.getLibCpCp());

                         // gouvernerat
                         validationContratCompteForm.setCodGouvGouvProfTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                         validationContratCompteForm.setLibGouvGouvProfTuteur(codePostal.getGouvernorat().getLibGouvGouv());
                     }
                 }
             } // Fin adresse professionnelle
             
              } catch (Exception e) {
                     logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : affecterDonnesPersonneTuteur :  ",e);  
                     throw new RuntimeException(e);                  
              }
             
        
    }

    public void affecterDonnesPersonneMorale(ActionForm form, Client client) throws Exception{


        
            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;
        try{
            validationContratCompteForm.setTypePersonneMoral(client.getTypePers().getLibTperTper());
            validationContratCompteForm.setCategoriePersonneMoral(client.getPersonne().getCategoriePersonne().getLibCatpCatp());
            validationContratCompteForm.setCodeCategoriePersClt(client.getPersonne().getCategoriePersonne().getCodCatpCatp());
            validationContratCompteForm.setTypePieceMoral(client.getPersonne().getTypePiece().getLibSiglTpce());
            validationContratCompteForm.setCodTypePieceId(client.getPersonne().getTypePiece().getCodTpceTpce().toString());
            validationContratCompteForm.setDateDelivMoral(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
            validationContratCompteForm.setNumPieceMoral(client.getPersonne().getNumPcePers());            
            
            if (client.getPersonne().getGouvernorat() != null) {
                GetGouvernoratCmd getGouvernoratCmd = new GetGouvernoratCmd();
                Gouvernorat gouvernorat = new Gouvernorat();
                gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                gouvernorat = (Gouvernorat)getGouvernoratCmd.execute(gouvernorat);

                validationContratCompteForm.setLieuDelivMoral(gouvernorat.getLibGouvGouv().toString());
                validationContratCompteForm.setCodLieuDelivMoral(gouvernorat.getCodGouvGouv().toString());
                validationContratCompteForm.setTypeDelivMoral("G");
            }
        if (client.getPersonne().getTribunal() != null) {
            GetTribunalTrt getTribunalTrt = new GetTribunalTrt();
            Tribunal tribunal = new Tribunal();
            tribunal.setCodTribTrib(client.getPersonne().getTribunal().getCodTribTrib());
            tribunal = (Tribunal)getTribunalTrt.exec(tribunal);

            validationContratCompteForm.setLieuDelivMoral(tribunal.getLibTribTrib().toString());
            validationContratCompteForm.setCodLieuDelivMoral(tribunal.getCodTribTrib().toString());
            validationContratCompteForm.setTypeDelivMoral("T");
        } 
           
            validationContratCompteForm.setRaisonSocialMoral(client.getPersonne().getNomRsPers());
            validationContratCompteForm.setSigleMoral(client.getPersonne().getLibSiglPers());
            
            if (client.getPersonne().getActivite() != null) {
                // extraire l'activité
                validationContratCompteForm.setCodActiviteMoral(client.getPersonne().getActivite().getActiviteId().getCodActAct().toString());
                validationContratCompteForm.setCodClasActiviteMoral(client.getPersonne().getActivite().getActiviteId().getCodCactCact().toString());
                validationContratCompteForm.setCodSclasActiviteMoral(client.getPersonne().getActivite().getActiviteId().getCodSactSact().toString());

                GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                Activite activite = new Activite();
                ActiviteId activiteId = new ActiviteId();
                activiteId.setCodActAct(validationContratCompteForm.getCodActiviteMoral());
                activiteId.setCodCactCact(validationContratCompteForm.getCodClasActiviteMoral());
                activiteId.setCodSactSact(Long.valueOf(validationContratCompteForm.getCodSclasActiviteMoral()));
                activite.setActiviteId(activiteId);
                activite = (Activite)getActiviteByIdCmd.execute(activite);
                if (activite != null && activite.getLibActAct() != null) {
                    validationContratCompteForm.setActiviteMoral(activite.getLibActAct());
                }
            }
            
           
             if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                 //extraire la nationalité
                 GetPaysCmd getPaysCmd = new GetPaysCmd();
                 Pays pays = new Pays();
                 pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                 pays = (Pays)getPaysCmd.execute(pays);                
                 validationContratCompteForm.setNationaliteMoral(pays.getLibNatPays());
                //validationContratCompteForm.setCodNationaliteMoral(pays.getCodPaysPays());
             }
            
            
            // adresse professionnelle
            if (client.getPersonne().getAdresseProf() != null) {
                // Immeuble
                if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                    validationContratCompteForm.setImmeubleAdrResidMoral(client.getPersonne().getAdresseProf().getImmeuble());
                }
                // Rue
                if (client.getPersonne().getAdresseProf().getRue() != null) {
                    validationContratCompteForm.setRueAdrResidMoral(client.getPersonne().getAdresseProf().getRue());
                }

                // Cite
                if (client.getPersonne().getAdresseProf().getCite() != null) {
                    validationContratCompteForm.setCiteAdrResidMoral(client.getPersonne().getAdresseProf().getCite());
                }

                // Pays 
                if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                    validationContratCompteForm.setCodPayAdrResidMoral(client.getPersonne().getAdresseProf().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        validationContratCompteForm.setPaysAdrResidMoral(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                    validationContratCompteForm.setCodePostalAdrResidMoral(client.getPersonne().getAdresseProf().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                        (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        validationContratCompteForm.setLibPostalAdrResidMoral(codePostal.getLibCpCp());

                        // gouvernerat
                        validationContratCompteForm.setCodGouvGouvMoral(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        validationContratCompteForm.setLibGouvGouvMoral(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse professionnelle
             validationContratCompteForm.setSecteurActMoral(client.getPersonne().getCodSectPers());
             validationContratCompteForm.setResidenceMoral(client.getPersonne().getBoolResPers().toString());
             
            
            //Informations des dates  
            if(client.getPersonne().getDatPmePers() != null)
             validationContratCompteForm.setDateCreationMoral(DateHandler.dateToStr(client.getPersonne().getDatPmePers()));
            if(client.getPersonne().getDateExpPers() != null)
              validationContratCompteForm.setDateActMoral(DateHandler.dateToStr(client.getPersonne().getDateExpPers()));
            if(client.getPersonne().getDatJortPers() != null)
              validationContratCompteForm.setNumLoiCreMoral(client.getPersonne().getNumLpmePers());
            if(client.getPersonne().getNumJortPers() != null)
              validationContratCompteForm.setNumJortMoral(client.getPersonne().getNumJortPers());
            if(client.getPersonne().getDatJortPers() != null)
              validationContratCompteForm.setDatJortMoral(DateHandler.dateToStr(client.getPersonne().getDatJortPers()));
            if(client.getPersonne().getNumDecrPers() != null)
              validationContratCompteForm.setNumDecretMoral(client.getPersonne().getNumDecrPers());
            if(client.getPersonne().getDatDecrPers() != null)
              validationContratCompteForm.setDatDecretMoral(DateHandler.dateToStr(client.getPersonne().getDatDecrPers()));
            validationContratCompteForm.setNumTelMoral(client.getPersonne().getNumTelPers());
            validationContratCompteForm.setNumFaxMoral(client.getPersonne().getNumFaxPers());
            validationContratCompteForm.setAdrMailMoral(client.getPersonne().getAdrMailPers());
            validationContratCompteForm.setAdrWebMoral(client.getPersonne().getAdrWebPers());
            validationContratCompteForm.setAdrSwiftMoral((client.getPersonne().getAdrSwiftPers()));
            validationContratCompteForm.setAdrTelexMoral(client.getPersonne().getAdrTlxPers());
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : affecterDonnesPersonneMorale :  ",e);  
               throw new RuntimeException(e);                   
        } 
       
    }

    public void affecterDonnesPersonneCotitulaire(ActionForm form, 
                                                  Personne personne) throws Exception{

        
            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;

         try{
            Listes listMembresCotit = new Listes();
            GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = 
                new GetListMembreCotitulaireCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input
            personneStrc.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce());
            personneStrc.setNumPcePers(personne.getNumPcePers());
            personneStrc.setCodStrcStrc(new Long(validationContratCompteForm.getCodeAgance()));
            listMembresCotit = 
                    (Listes)getListMembreCotitulaireCmd.execute(personneStrc);

            if (listMembresCotit.getList() != null && 
                listMembresCotit.getList().size() > 0) {
                CoTitulaire cotitulaire = 
                    (CoTitulaire)listMembresCotit.getList().get(0);
                validationContratCompteForm.setCoTitulaire(cotitulaire);
                validationContratCompteForm.setTypeCotit(cotitulaire.getCodTcotCoti());
                validationContratCompteForm.setTypeSignature(cotitulaire.getCodSigCoti());
                validationContratCompteForm.setListeMembreEntiteCotit(listMembresCotit.getList());
                validationContratCompteForm.setOpenTabsheetCotitulaire("true");
            }
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : affecterDonnesPersonneCotitulaire :  ",e);  
               throw new RuntimeException(e);                   
        } 
       
    }
  
    public ActionForward choisirContrat(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
        ActionMessages actionMessages = new ActionMessages();
     try{
        GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
        ContratCpt contratChoisi = new ContratCpt();
        String vContratChoisi = 
            validationContratCompteForm.getCleContratChoisi();
        String vcodStrcStrc = "";
        String vcodPrdPrd = "";
        String vnumCcptCcpt = "";
        vcodStrcStrc = vContratChoisi.substring(0, 3);
        vcodPrdPrd = vContratChoisi.substring(3, 7);
        vnumCcptCcpt = vContratChoisi.substring(7, vContratChoisi.length());

        ContratCptId contratCptId = new ContratCptId();
        contratCptId.setCodPrdPrd(new Long(vcodPrdPrd));
        contratCptId.setNumCcptCcpt(new Long(vnumCcptCcpt));
        contratCptId.setCodStrcStrc(new Long(vcodStrcStrc));

        contratChoisi = (ContratCpt)getDetailContratCmd.execute(contratCptId);
        
        if(!contratChoisi.hasError()){
            if((validationContratCompteForm.getChoixRecherche().equals("periode") ||validationContratCompteForm.getChoixRecherche().equals("typePiece")) && validationContratCompteForm.getListeContrats().size() > 1){
                verifierTypePersonne(validationContratCompteForm,contratChoisi);              
            }
            affecterDonnesContrat(validationContratCompteForm, contratChoisi);
            validationContratCompteForm.setContratCpt(contratChoisi);
            validationContratCompteForm.setOpenTabsheetContrat("true");
            
        }else {                   
                List listErreur = contratChoisi.getErrors();
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
                new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :choisirContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
         //   logger.error("Exception : ",e); 
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    
    }


    public Personne majPersonnePhysique(ActionForm form, Personne personne) throws Exception {

          ValidationContratCompteForm validationContratCompteForm = 
           (ValidationContratCompteForm)form;   
      try{
          if(personne!=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
             
            
            // pays naissance
            Pays paysNais = new Pays();
            paysNais.setCodPaysPays(validationContratCompteForm.getCodPaysNaisClt());
            
            //Gouvernorat
            Gouvernorat gouvernorat = new Gouvernorat();            
            gouvernorat.setCodGouvGouv(new Long(validationContratCompteForm.getCodLieuDelivClt()));

            // Activite
            Activite activite = new Activite();
            ActiviteId activiteId = new ActiviteId();
            activiteId.setCodActAct(validationContratCompteForm.getCodActiviteClt()); 
            activiteId.setCodSactSact(new Long(validationContratCompteForm.getCodSclasActiviteClt()));
            activiteId.setCodCactCact(validationContratCompteForm.getCodClasActiviteClt());
            activite.setActiviteId(activiteId);

            //profession
            Profession profession = new Profession();
            ProfessionId professionId = new ProfessionId();
            professionId.setCodGproGpro(new Long(validationContratCompteForm.getCodGroupProfClt()));
            professionId.setCodProfProf(new Long(validationContratCompteForm.getCodProfClt())); 
            profession.setProfessionId(professionId);
            

            //Adresse de résidence
            Adresse adresseResidence = new Adresse();
            adresseResidence.setImmeuble(validationContratCompteForm.getImmeubleAdrResid());
            adresseResidence.setRue(validationContratCompteForm.getRueAdrResid());
            adresseResidence.setCite(validationContratCompteForm.getCiteAdrResid());            
            adresseResidence.setCodPaysPays(validationContratCompteForm.getCodPayAdrResid());
            adresseResidence.setCodCpCp(validationContratCompteForm.getCodePostalAdrResid());

            //Adresse professionnelle
            Adresse adresseProfessionnelle = new Adresse();
            adresseProfessionnelle.setImmeuble(validationContratCompteForm.getImmeubleAdrProf());
            adresseProfessionnelle.setRue(validationContratCompteForm.getRueAdrProf());
            adresseProfessionnelle.setCite(validationContratCompteForm.getCiteAdrProf());            
            adresseProfessionnelle.setCodPaysPays(validationContratCompteForm.getCodPayAdrProf());
            adresseProfessionnelle.setCodCpCp(validationContratCompteForm.getCodePostalAdrProf());

            //Piece annexe si elle existe 
            Set listPieceAnn = new HashSet();
            if (!validationContratCompteForm.getTypePieceAnnexe().equals("") && 
                !validationContratCompteForm.getNumPieceAnnexe().equals("") && 
                !validationContratCompteForm.getDateDellivPiann().equals("") && 
                !validationContratCompteForm.getDateFinPian().equals("")) {
                PieceAnnexe pieceAnnexe = new PieceAnnexe();
                PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
                pieceAnnexeId.setCodTpceTpce(new Long(validationContratCompteForm.getTypePieceAnnexe()));
                pieceAnnexeId.setNumPcePian(validationContratCompteForm.getNumPieceAnnexe());
                pieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
                pieceAnnexe.setDatDelvPian(myformat.parse(validationContratCompteForm.getDateDellivPiann()));
                pieceAnnexe.setDatFvalPian(myformat.parse(validationContratCompteForm.getDateFinPian()));
                listPieceAnn = new HashSet();
                listPieceAnn.add(pieceAnnexe);
                personne.setPieceAnnexes(listPieceAnn);
            }
            //#########################################################################################
            //Instantiation de la personne            
            
            personne.setDatDlvPers(myformat.parse(validationContratCompteForm.getDateDelivClt()));            
            personne.setPaysByCodNaisPays(paysNais);           
            personne.setActivite(activite);
            personne.setProfession(profession);
            personne.setNomNomPers(validationContratCompteForm.getNomPersClt());
            personne.setNomPrnPers(validationContratCompteForm.getPrenomPersClt());
            personne.setLibTitrPers(validationContratCompteForm.getTitrePersClt());
            personne.setCodSexPers(validationContratCompteForm.getSexeClt());            
            personne.setLibNaisPers(validationContratCompteForm.getLieuNaisClt());
            personne.setCodSitfPers(validationContratCompteForm.getSitFamilialeClt());           
            personne.setNomPrnpPers(validationContratCompteForm.getNomPereClt());            
            personne.setAdresseResid(adresseResidence);
            personne.setAdresseProf(adresseProfessionnelle);
            personne.setGouvernorat(gouvernorat);
            personne.setNumTelPers(validationContratCompteForm.getNumTelPers());
            personne.setNumFaxPers(validationContratCompteForm.getNumFaxpers());             
            
          }
           } catch (Exception e) {
                  logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : MajPersonnePhysique:  ",e);  
                  throw new RuntimeException(e);               
           }   
            return personne;

       }


    public Personne majPersonnePhysiqueTuteur(ActionForm form ,Personne personneTuteur) throws Exception{

       
            ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
        try{
           DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
                        
           
            // pays naissance
            Pays paysNaisTuteur = new Pays();
            paysNaisTuteur.setCodPaysPays(validationContratCompteForm.getCodPaysNaisTuteur());
            // nationalite
            Pays paysNatTuteur = new Pays();
            paysNatTuteur.setCodPaysPays(validationContratCompteForm.getCodNationaliteTuteur());
            //Gouvernorat
            Gouvernorat gouvernoratTuteur = new Gouvernorat();
            gouvernoratTuteur.setCodGouvGouv(new Long(validationContratCompteForm.getCodLieuDelivTuteur()));

            // Activite
            Activite activiteTuteur = new Activite();
            ActiviteId activiteIdTuteur = new ActiviteId();
            activiteIdTuteur.setCodActAct(validationContratCompteForm.getCodActiviteTuteur()); //validationContratCompteForm.getCodActiviteClt());
            activiteIdTuteur.setCodSactSact(new Long(validationContratCompteForm.getCodSclasActiviteTuteur()));
            activiteIdTuteur.setCodCactCact(validationContratCompteForm.getCodClasActiviteTuteur());
            activiteTuteur.setActiviteId(activiteIdTuteur);

            //profession
            Profession professionTuteur = new Profession();
            ProfessionId professionIdTuteur = new ProfessionId();
            professionIdTuteur.setCodGproGpro(new Long(validationContratCompteForm.getCodProfTuteur()));
            professionIdTuteur.setCodProfProf(new Long(validationContratCompteForm.getCodProfTuteur())); //new Integer(validationContratCompteForm.getCodProfClt()));
            professionTuteur.setProfessionId(professionIdTuteur);

            //Adresse de résidence
            Adresse adresseResidenceTuteur = new Adresse();
            adresseResidenceTuteur.setImmeuble(validationContratCompteForm.getImmeubleAdrResidTuteur());
            adresseResidenceTuteur.setRue(validationContratCompteForm.getRueAdrResidTuteur());
            adresseResidenceTuteur.setCite(validationContratCompteForm.getCiteAdrResidTuteur());            
            adresseResidenceTuteur.setCodPaysPays(validationContratCompteForm.getCodPayAdrResidTuteur());
            adresseResidenceTuteur.setCodCpCp(validationContratCompteForm.getCodePostalAdrResidTuteur());

            //Adresse professionnelle
            Adresse adresseProfessionnelleTuteur = new Adresse();
            adresseProfessionnelleTuteur.setImmeuble(validationContratCompteForm.getImmeubleAdrProfTuteur());
            adresseProfessionnelleTuteur.setRue(validationContratCompteForm.getRueAdrProfTuteur());
            adresseProfessionnelleTuteur.setCite(validationContratCompteForm.getCiteAdrProfTuteur());            
            adresseProfessionnelleTuteur.setCodPaysPays(validationContratCompteForm.getCodPayAdrProfTuteur());
            adresseProfessionnelleTuteur.setCodCpCp(validationContratCompteForm.getCodePostalAdrProfTuteur());

            //Piece annexe si elle existe 
            Set listPieceAnnTuteur = new HashSet();
            if (!validationContratCompteForm.getTypePieceAnnexeTuteur().equals("") && 
                !validationContratCompteForm.getNumPieceAnnexeTuteur().equals("") && 
                !validationContratCompteForm.getDateDellivPiannTuteur().equals("") && 
                !validationContratCompteForm.getDateFinPianTuteur().equals("")) {
                PieceAnnexe pieceAnnexeTuteur = new PieceAnnexe();
                PieceAnnexeId pieceAnnexeIdTuteur = new PieceAnnexeId();
                pieceAnnexeIdTuteur.setCodTpceTpce(new Long(validationContratCompteForm.getTypePieceAnnexeTuteur()));
                pieceAnnexeIdTuteur.setNumPcePian(validationContratCompteForm.getNumPieceAnnexeTuteur());
                pieceAnnexeTuteur.setPieceAnnexeId(pieceAnnexeIdTuteur);
                pieceAnnexeTuteur.setDatDelvPian(myformat.parse(validationContratCompteForm.getDateDellivPiannTuteur()));
                pieceAnnexeTuteur.setDatFvalPian(myformat.parse(validationContratCompteForm.getDateFinPianTuteur()));
                //listPieceAnn = new HashSet();
                listPieceAnnTuteur.add(pieceAnnexeTuteur);
                personneTuteur.setPieceAnnexes(listPieceAnnTuteur);
            }
            //#########################################################################################
            //Instantiation de la personne
            
            personneTuteur.setDatDlvPers(myformat.parse(validationContratCompteForm.getDateDelivTuteur()));           
            personneTuteur.setPaysByCodNaisPays(paysNaisTuteur);
            personneTuteur.setPaysByCodNat1Pays(paysNatTuteur);
            personneTuteur.setActivite(activiteTuteur);
            personneTuteur.setProfession(professionTuteur);
            personneTuteur.setNomNomPers(validationContratCompteForm.getNomPersTuteur());
            personneTuteur.setNomPrnPers(validationContratCompteForm.getPrenomPersTuteur());
            personneTuteur.setLibTitrPers(validationContratCompteForm.getTitrePersTuteur());
            personneTuteur.setCodSexPers(validationContratCompteForm.getSexeTuteur());
            personneTuteur.setDatNaisPers((myformat.parse(validationContratCompteForm.getDateNaisTuteur())));
            personneTuteur.setLibNaisPers(validationContratCompteForm.getLieuNaisTuteur());
            personneTuteur.setCodSitfPers(validationContratCompteForm.getSitFamilialeTuteur());
            personneTuteur.setBoolResPers(new Long(validationContratCompteForm.getResidentTuteur()));
            personneTuteur.setNomPrnpPers(validationContratCompteForm.getNomPereTuteur());            
            personneTuteur.setAdresseResid(adresseResidenceTuteur);
            personneTuteur.setAdresseProf(adresseProfessionnelleTuteur);
            personneTuteur.setGouvernorat(gouvernoratTuteur);
           
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : MajPersonnePhysiqueTuteur:  ",e);  
               throw new RuntimeException(e);               
        }   
            return personneTuteur;
       
    }


    public Personne majPersonneMorale(ActionForm form,Personne personneMorale) throws Exception{
          
        ValidationContratCompteForm validationContratCompteForm = 
        (ValidationContratCompteForm)form;
          
       try{
          if(personneMorale!=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            
            
            //Gouvernorat
              //Gouvernorat / tribunal
               Gouvernorat gouvernoratMorale = new Gouvernorat();
               Tribunal tribunal = new Tribunal();
              if(validationContratCompteForm.getTypeDelivMoral().equals("G")){              
                gouvernoratMorale.setCodGouvGouv(Long.valueOf(validationContratCompteForm.getCodLieuDelivMoral()));
              }else{                
                  tribunal.setCodTribTrib(Long.valueOf(validationContratCompteForm.getCodLieuDelivMoral()));
              }
            
            
            // Activite
            Activite activiteMorale = new Activite();
            ActiviteId activiteId = new ActiviteId();
            activiteId.setCodActAct(validationContratCompteForm.getCodActiviteMoral()); //validationContratCompteForm.getCodActiviteClt());
            activiteId.setCodSactSact(new Long(validationContratCompteForm.getCodSclasActiviteMoral()));
            activiteId.setCodCactCact(validationContratCompteForm.getCodClasActiviteMoral());
            activiteMorale.setActiviteId(activiteId);

            //Adresse professionnelle
            Adresse adresseProfessionnelleMorale = new Adresse();
            adresseProfessionnelleMorale.setImmeuble(validationContratCompteForm.getImmeubleAdrResidMoral());
            adresseProfessionnelleMorale.setRue(validationContratCompteForm.getRueAdrResidMoral());
            adresseProfessionnelleMorale.setCite(validationContratCompteForm.getCiteAdrResidMoral());            
            adresseProfessionnelleMorale.setCodPaysPays(validationContratCompteForm.getCodPayAdrResidMoral());
            adresseProfessionnelleMorale.setCodCpCp(validationContratCompteForm.getCodePostalAdrResidMoral());


            //#########################################################################################
            //Instantiation de la personne morale

            personneMorale.setDatDlvPers(myformat.parse(validationContratCompteForm.getDateDelivMoral()));
            personneMorale.setActivite(activiteMorale);
            personneMorale.setAdresseProf(adresseProfessionnelleMorale);
            if(validationContratCompteForm.getTypeDelivMoral().equals("G")){      
               personneMorale.setGouvernorat(gouvernoratMorale);
            }else{
               personneMorale.setTribunal(tribunal);
            }
            personneMorale.setNomRsPers(validationContratCompteForm.getRaisonSocialMoral());
            personneMorale.setLibSiglPers(validationContratCompteForm.getSigleMoral());
            personneMorale.setCodSectPers(validationContratCompteForm.getSecteurActMoral());
            personneMorale.setBoolResPers(new Long(validationContratCompteForm.getResidenceMoral()));
            //Informations complémentaires  
           
            
            if(!validationContratCompteForm.getDateActMoral().equals(""))
              personneMorale.setDateExpPers(myformat.parse(validationContratCompteForm.getDateActMoral()));
            if(!validationContratCompteForm.getDatDecretMoral().equals(""))
              personneMorale.setDatDecrPers(myformat.parse(validationContratCompteForm.getDatDecretMoral()));
            if(!validationContratCompteForm.getDatJortMoral().equals(""))
              personneMorale.setDatJortPers(myformat.parse(validationContratCompteForm.getDatJortMoral()));
            if(!validationContratCompteForm.getDateCreationMoral().equals(""))
              personneMorale.setDatPmePers(myformat.parse(validationContratCompteForm.getDateCreationMoral()));
            personneMorale.setNumJortPers(validationContratCompteForm.getNumJortMoral());
            personneMorale.setNumJortPers(validationContratCompteForm.getNumJortMoral());
            personneMorale.setNumDecrPers(validationContratCompteForm.getNumDecretMoral());
            personneMorale.setNumLpmePers(validationContratCompteForm.getNumLoiCreMoral());

            personneMorale.setNumTelPers(validationContratCompteForm.getNumTelMoral());
            personneMorale.setNumFaxPers(validationContratCompteForm.getNumFaxMoral());
            personneMorale.setAdrMailPers(validationContratCompteForm.getAdrMailMoral());
            personneMorale.setAdrWebPers(validationContratCompteForm.getAdrWebMoral());
            personneMorale.setAdrSwiftPers(validationContratCompteForm.getAdrSwiftMoral());
            personneMorale.setAdrTlxPers(validationContratCompteForm.getAdrTelexMoral());
            
          }
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : MajPersonneMorale :  ",e);  
               throw new RuntimeException(e);               
        }   
        
            return personneMorale;
            
        
    }

    public CoTitulaire majPersonneCotitulaire(ActionForm form,CoTitulaire personneCotitulaire) throws Exception{

        
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
         try{
          if(personneCotitulaire!=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            //Instanciation de la personne CoTitulaire             
            personneCotitulaire.setCodTcotCoti(validationContratCompteForm.getTypeCotit());
            personneCotitulaire.setCodSigCoti(validationContratCompteForm.getTypeSignature());
          }
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : majPersonneCotitulaire :  ",e);  
               throw new RuntimeException(e);                  
        }
            return personneCotitulaire;
       
    }

    public ContratCpt majContrat(ActionForm form,ContratCpt contratCpt) throws Exception{

         ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;
        try{
          if(contratCpt!=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            
            
             contratCpt.setNomIntiCcpt(validationContratCompteForm.getIntituleCompteCpt());
             //adresse : 
             Adresse adresse = new Adresse();
             adresse.setImmeuble(validationContratCompteForm.getImmeubleCpt());
             adresse.setRue(validationContratCompteForm.getRueCpt());
             adresse.setCite(validationContratCompteForm.getCiteCpt());             
             adresse.setCodPaysPays(validationContratCompteForm.getCodPayCpt());
             adresse.setCodCpCp(validationContratCompteForm.getCodePostalCpt());
             contratCpt.setAdresseCorresp(adresse);
             //--------------------------------------------------------------------------
             //devise
             Devise devise = new Devise();
             devise.setCodDevDev(new Long(validationContratCompteForm.getCodeDeviseCpt()));
             contratCpt.setDevise(devise);
             contratCpt.setCatCcptCcpt(validationContratCompteForm.getCodeCategorieEpargne());
             
              String catEp = "";
              if(contratCpt.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                  catEp = StrHandler.lpad(validationContratCompteForm.getCodeRegimeEpargne(),'0',2) + validationContratCompteForm.getCodeCategorieEpargne();                
              }else if(contratCpt.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM)){
                        catEp = StrHandler.lpad(validationContratCompteForm.getCodeCategorieEpargne(),' ',3);
                    }else if(contratCpt.getContratCptId().getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)){
                             catEp = StrHandler.lpad(validationContratCompteForm.getCodeCategorieEpargne(),' ',2) + validationContratCompteForm.getCodeRegimeEpargne();
                          }
              
              contratCpt.setCatCcptCcpt(catEp);
             
             
             contratCpt.setNumLivrCcpt(validationContratCompteForm.getNumLivretEpargne());
             contratCpt.setCodPerCpt(validationContratCompteForm.getPeridiciteCpt());
             contratCpt.setCodFoncCpt(validationContratCompteForm.getFonctionementCpt());
             contratCpt.setBoolRelvCpt(Long.valueOf(validationContratCompteForm.getReleveCpt()));
          }
          
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : majContrat :  ",e);  
               throw new RuntimeException(e);                  
        }
        
             return contratCpt;        
       
    }

    public Client majClient(ActionForm form,Client client) throws Exception{

        
          if(client !=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;
            
            //client.setNumBctClt(validationContratCompteForm.getNumBctCpt());
            client.setNumFiscClt(validationContratCompteForm.getNumFiscaleCpt());
            client.setCodDoanClt(validationContratCompteForm.getCodeDouaneCpt());
            //client.setNumRnePers(validationContratCompteForm.getNumRnePers());
          }   
            return client;
        
      
    }

    public DetailCatCpt majDetailCatCpt(ActionForm form,DetailCatCpt detailCatCpt) throws Exception{

      
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;
        
       try{ 
        if(detailCatCpt!=null){
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            
            
            RegimeId regimeId = new RegimeId();
            Regime regime = new Regime();
            
            detailCatCpt.getCategorie().getCategorieId().setCodCatCat(validationContratCompteForm.getCodeCategorieEpargne());
            detailCatCpt.getCategorie().getCategorieId().setCodRgmRgm(new Long(validationContratCompteForm.getCodeRegimeEpargne()));
            
            regimeId.setCodPrdPrd(new Long(validationContratCompteForm.getCodePrdCpt()));
            regimeId.setCodRgmRgm(new Long(validationContratCompteForm.getCodeRegimeEpargne()));            
            regime.setRegimeId(regimeId);
            
            detailCatCpt.getCategorie().setRegime(regime);
            detailCatCpt.getCategorie().setMontCaptCat(new Long(validationContratCompteForm.getMntCapitaliseEpargne()));
            detailCatCpt.getCategorie().setMontVersCat(new Long(validationContratCompteForm.getMntVersementEpargne()));            
            
        }
        
        } catch (Exception e) {
               logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception dans validationContratCompteAction / Methode : majDetailCatCpt :  ",e);  
               throw new RuntimeException(e);                  
        }
        
            return detailCatCpt;
       
    }

    public ActionForward validerTransaction(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
       
        ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form; 
        ActionMessages actionMessages = new ActionMessages();    
        try{
            
            TraitementValidationContratCmd traitementValidationContratCmd= new TraitementValidationContratCmd();    
            ParamValidationContrat paramValidationContrat = new ParamValidationContrat();
            paramValidationContrat.setEtatClient(validationContratCompteForm.getEtatClient());  
            if (validationContratCompteForm.getClient().getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
               
                 Personne personne = majPersonnePhysique(validationContratCompteForm, validationContratCompteForm.getPersonne());
                 paramValidationContrat.setPersonne(personne);
               
                if (validationContratCompteForm.getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)) {
                  //Personne Tuteur = majPersonnePhysiqueTuteur(validationContratCompteForm, validationContratCompteForm.getTuteur());
                  paramValidationContrat.setTuteur(validationContratCompteForm.getTuteur());
                }
            } else {
                if (validationContratCompteForm.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                 
                    Personne  personne = majPersonneMorale(validationContratCompteForm,validationContratCompteForm.getPersonne());
                    paramValidationContrat.setPersonne(personne);
                
                } else {
                    if (validationContratCompteForm.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                    CoTitulaire  coTitulaire = 
                        majPersonneCotitulaire(validationContratCompteForm,validationContratCompteForm.getCoTitulaire());
                        paramValidationContrat.setCoTitulaire(coTitulaire);
                    }
                }
            }
            
            Client client = majClient(validationContratCompteForm,validationContratCompteForm.getClient());
            ContratCpt contratCpt = majContrat(validationContratCompteForm,validationContratCompteForm.getContratCpt());
            DetailCatCpt detailCatCpt = majDetailCatCpt(validationContratCompteForm,validationContratCompteForm.getDetailCatCpt());
                        
            paramValidationContrat.setClient(client);
            paramValidationContrat.setContratCpt(contratCpt);
            paramValidationContrat.setDetailCatCpt(detailCatCpt);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(validationContratCompteForm.getNumMatriculeUser());     
            paramValidationContrat.setPersonnel(personnel);
            
            ValueObject vo = (ValueObject)traitementValidationContratCmd.execute(paramValidationContrat);
             
            if(!vo.hasError()){
              ContratCpt contratValide = (ContratCpt)vo;
               
                if(contratValide!=null)
                  validationContratCompteForm.setTransactionReussite("OUI");
                  String message = "                         Le contrat N° "+ 
                    StrHandler.lpad(contratValide.getContratCptId().getCodStrcStrc().toString(),'0',3) +
                    " "+ StrHandler.lpad(contratValide.getContratCptId().getCodPrdPrd().toString(),'0',4)+
                    " "+ StrHandler.lpad(contratValide.getContratCptId().getNumCcptCcpt().toString(),'0',6)+ 
                    " au nom du "+ 
                    " "+ validationContratCompteForm.getNomCpt() +
                    " "+ validationContratCompteForm.getPrenomCpt()+
                    " a été validé avec succès. ";
                  validationContratCompteForm.setContratCpt(contratValide);  
                  validationContratCompteForm.setLibelleConfirmation(message);
                    /*
                   impression directe du contrat compte validé
                   */
                   try
                   {
                   ContratCptId contratCptid= contratValide.getContratCptId();
                   CommonReportVO valueObject = new CommonReportVO();
                   ParamAgence paramAgence = new ParamAgence();
                   paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                   Map parameters = new HashMap();
                   
                   String pCodStrcStrc ="";
                   String pCodProd = "";
                   
                   String pNumContratCpt = "";
                   String pLibEtat = "P_LIB_ETAT";
                   String pMatrUser = "P_NUM_MATR_USER";
                   
                   String pCle   = "P_CLE";
                          //-------------------------------------paramètre RIB
                                GetRibCmd getRibCmd = new GetRibCmd();
                                IValueObject voCcpt = (IValueObject)contratValide;
                                PrimitiveVO rib = (PrimitiveVO)getRibCmd.execute(voCcpt);
                                String pRib = "P_RIB";
                                String vRib = rib.getVString();
                                parameters.put(pRib, vRib);
                           //------------------------------------------------------
                   String vLibEtat =""; 
                   String vMatrUser = paramAgence.getNumMatrUser().toString();
                   String vCodStrcStrc = contratCptid.getCodStrcStrc().toString();
                   String vCodProd = contratCptid.getCodPrdPrd().toString();
                   String vNumContratCpt = contratCptid.getNumCcptCcpt().toString();
             /*      String vCle = Constants.determinerCle(
                   StrHandler.lpad(contratCptid.getCodStrcStrc().toString(),'0',3),
                   StrHandler.lpad(contratCptid.getCodPrdPrd().toString(),'0',4),
                   StrHandler.lpad(contratCptid.getNumCcptCcpt().toString(),'0',6)); */
                  String vCle = Constants.determinerCle(
                                 StrHandler.lpad(vCodStrcStrc,'0',3),
                                 StrHandler.lpad(vCodProd,'0',4),
                                 StrHandler.lpad(vNumContratCpt,'0',6));
                   if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)||contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)||contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM)){
                     pCodStrcStrc = "pCodStrcStrc";
                     pCodProd = "pCodPrdPrd";
                     pNumContratCpt = "pNumCcptCcpt";
                     
                     
                     if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)|| contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM) ){
                             String pLibNCatCat="pLibNCatCat";
                             String vLibNCatCat=validationContratCompteForm.getCodeCategorieEpargne();
                             parameters.put(pLibNCatCat,vLibNCatCat);
                             
                              String pLibNRgmRgm="pLibNRgmRgm";
                              String vLibNRgmRgm=validationContratCompteForm.getCodeRegimeEpargne();
                              parameters.put(pLibNRgmRgm,vLibNRgmRgm);
                              
                              String pMnt2Capt="pMnt2Capt";
                              Long vMnt2Capt=(Long.valueOf(validationContratCompteForm.getMntCapitaliseEpargne()))*2;
                              parameters.put(pMnt2Capt,vMnt2Capt);
                              
                              String pMnt3Capt="pMnt3Capt";
                              Long vMnt3Capt=(Long.valueOf(validationContratCompteForm.getMntCapitaliseEpargne()))*3;
                              parameters.put(pMnt3Capt,vMnt3Capt);
                              
                              String pMontVersMens="pMontVersMens";
                              Long vMontVersMens= new Long( validationContratCompteForm.getMntVersementEpargne());
                              parameters.put(pMontVersMens,vMontVersMens);
                             vLibEtat = "Souscription Contrat compte Epargne";
                             valueObject.setNomReport("ContratCptEpgn");
                             request.setAttribute("print","2");
                             
                         }else if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                         
                         // Impression pour CompteEpargne Etudes 'FAIEZ'
                             vLibEtat = "Souscription Contrat Compte Epargne Etudes 'FAIEZ'";
                             valueObject.setNomReport("ContratCpt_PEE");
                             request.setAttribute("print","3");
                             parameters.put("COD_PAYS_PAYS", validationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays());
                             parameters.put("ADR_VIL_CCPT", validationContratCompteForm.getLibGouvGouvCpt());
                             parameters.put("ADR_CP_CCPT", validationContratCompteForm.getCodePostalCpt()+" "+validationContratCompteForm.getLibCodePostalCpt());
                             parameters.put("P_NOM_TUTEUR",
                                            validationContratCompteForm.getNomPersTuteur()+" "+validationContratCompteForm.getPrenomPersTuteur());
                             parameters.put("NUM_PCE_TUT", validationContratCompteForm.getNumPieceTut());
                             parameters.put("LIB_SIGL_TPCE",validationContratCompteForm.getTypePieceTut());
                             parameters.put("LIB_PROFESSION", validationContratCompteForm.getProfessionTuteur());
                           
                         }
                  }else{
                      pCodStrcStrc = "P_COD_STRC_STRC";
                      pCodProd = "P_COD_PRD_PRD";
                      pNumContratCpt = "P_NUM_CCPT_CCPT";
                      vLibEtat = "Souscription Contrat compte";
                      //-------------------------------------------------------------------IMPRESSION Personne Morale 
                              String pCodTpceTpce   = "P_COD_TPCE_TPCE";
                              String pLibActivite   = "LIB_ACTIVITE";
                              String pCodPaysPays   = "COD_PAYS_PAYS";
                              String pAdrVilCcpt   = "ADR_VIL_CCPT";
                              String pAdrCpCcpt   = "ADR_CP_CCPT";
                              String pMatrFisc   = "MATR_FISC";
                              //------------------------------------------
                               String vCodTpceTpce   = "";
                               String vCodPaysPays   = "";
                 
                              vCodPaysPays   = validationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays();
                               String vAdrVilCcpt   = "";
                              vAdrVilCcpt   = validationContratCompteForm.getLibGouvGouvCpt();
                               String vAdrCpCcpt   = "";
                              vAdrCpCcpt   = validationContratCompteForm.getCodePostalCpt()+" "+validationContratCompteForm.getLibCodePostalCpt();
                              
                              parameters.put(pCodPaysPays, vCodPaysPays);
                              parameters.put(pAdrVilCcpt, vAdrVilCcpt);
                              parameters.put(pAdrCpCcpt, vAdrCpCcpt);
                               
                            
                              
                         if(validationContratCompteForm.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
                              
                                 //---------------------------------------------------------------IMPRESSION Personne mineur
                                  String pLibProfession  = "LIB_PROFESSION";
                                  String pNomTuteur  = "P_NOM_TUTEUR";
                                  String pNumPceTut   = "NUM_PCE_TUT";
                                  String pLibTpceTut  = "LIB_SIGL_TPCE";
                                  String vNomTuteur  = "";
                                 String vNumPceTut   = "";
                                 String vLibTpceTut  = "";
                                 String vLibProfession  = "";
                              
                                 vNomTuteur  = validationContratCompteForm.getNomPersTuteur()+" "+validationContratCompteForm.getPrenomPersTuteur();
                                
                                 vNumPceTut   = validationContratCompteForm.getNumPieceTut();
                                 vLibTpceTut  = validationContratCompteForm.getTypePieceTut();
                                 vLibProfession  = validationContratCompteForm.getProfessionTuteur();
                                                            
                                 parameters.put(pNomTuteur, vNomTuteur);
                                 parameters.put(pNumPceTut, vNumPceTut);
                                 parameters.put(pLibTpceTut, vLibTpceTut);
                                 parameters.put(pLibProfession, vLibProfession);
                                 if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_EPS)){
                                         valueObject.setNomReport("ContratCpt_PMin121"); 
                                     }else {
                                              valueObject.setNomReport("ContratCpt_PMin");  }
                                 request.setAttribute("print","1");
                             }else{
                             
                                     if((validationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==9)
                                        ||(    (validationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==11) 
                                               && (validationContratCompteForm.getContratCpt().getClient().getTypePers().getCodTperTper().equals("2")))
                                         ){
                                         String vLibActivite   = "";
                                         String vMatrFisc   = "";
                                         
                                         vCodTpceTpce   = validationContratCompteForm.getTypePieceMoral();
                                         vLibActivite   = validationContratCompteForm.getActiviteMoral();
                                         vMatrFisc = validationContratCompteForm.getContratCpt().getClient().getNumFiscClt();
                                         
                                         parameters.put(pCodTpceTpce, vCodTpceTpce);
                                         parameters.put(pLibActivite, vLibActivite);
                                         parameters.put(pMatrFisc, vMatrFisc);
                                         valueObject.setNomReport("ContratCpt_PersMoral");
                                         request.setAttribute("print","1");
                                     }else{
                                         if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_EPS)){
                                                 valueObject.setNomReport("ContratCpt121"); 
                                             }else {
                                                      valueObject.setNomReport("ContratCpt");}
                                         request.setAttribute("print","1");
                                     }
                      }
                      //------------------------------------------------------------------- 
                   
                  }
                   parameters.put(pMatrUser, vMatrUser);
                   parameters.put(pLibEtat, vLibEtat);
                   parameters.put(pCodStrcStrc, vCodStrcStrc);
                   parameters.put(pCodProd, vCodProd);
                   parameters.put(pNumContratCpt, vNumContratCpt);
                   parameters.put(pCle, vCle);
                   valueObject.setParams(parameters);
                   parameters=null;
                   request.getSession().setAttribute("CommonPrintVo",valueObject);   
                      }
           
                   catch(Exception e)
                   {
                   e.printStackTrace();
                   }
               
            }else {                   
                        List listErreur = vo.getErrors();
                        //ActionMessages actionMessages = new ActionMessages();
                        for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                            com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }    
                       this.saveMessages(request, actionMessages);
                       return mapping.findForward("error");                    
             }
            
                return mapping.findForward("confirmationValidationContrat"); 
            } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = 
                     new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :validerTransaction ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
             //    logger.error("Exception : ",e); 
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }    
    }


   
    public void garnirParmEpargne(ActionForm form, DetailCatCpt detailCatCpt) throws Exception{

       
            ValidationContratCompteForm validationContratCompteForm = 
                (ValidationContratCompteForm)form;


            ChargerRgmCatEpargneCmd chargerRgmCatEpargneCmd = 
                new ChargerRgmCatEpargneCmd();
            
            ParamEpargne paramEpargne = new ParamEpargne(); //Vo input

            paramEpargne.setCodPrdPrd(new Long(validationContratCompteForm.getCodeProduitCpt()));
            paramEpargne.setCodCatCat(detailCatCpt.getCategorie().getCategorieId().getCodCatCat());
            paramEpargne.setCodRgmRgm(detailCatCpt.getCategorie().getRegime().getRegimeId().getCodRgmRgm().toString());
           

            ListRgmCatEpargne listRgmCatEpargne = 
                new ListRgmCatEpargne(); //Vo output 

            listRgmCatEpargne = 
                    (ListRgmCatEpargne)chargerRgmCatEpargneCmd.execute(paramEpargne);

            
            validationContratCompteForm.setListRegimeEpargne(null);
            validationContratCompteForm.setListRegimeEpargne(listRgmCatEpargne.getListRgmEpargne());
            validationContratCompteForm.setListCategorieEpargne(null);
            validationContratCompteForm.setListCategorieEpargne(listRgmCatEpargne.getListCatEpargne());      

            if (listRgmCatEpargne.getCategorie() != null) {
                validationContratCompteForm.setCodeCategorieEpargne(listRgmCatEpargne.getCategorie().getCategorieId().getCodCatCat());
                validationContratCompteForm.setMntVersementEpargne(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
                validationContratCompteForm.setMntCapitaliseEpargne(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
            }
                    

    }

    public ActionForward modifierParmEpargne(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
        ActionMessages actionMessages = new ActionMessages();    
        ValidationContratCompteForm validationContratCompteForm = 
            (ValidationContratCompteForm)form;

        try {           

            ChargerRgmCatEpargneCmd chargerRgmCatEpargneCmd = 
                new ChargerRgmCatEpargneCmd();
            
            ParamEpargne paramEpargne = new ParamEpargne(); //Vo input

            paramEpargne.setCodPrdPrd(new Long(validationContratCompteForm.getCodeProduitCpt()));
            paramEpargne.setCodRgmRgm(validationContratCompteForm.getCodeRegimeEpargne());

            if (validationContratCompteForm.getTypeRequest().equals("choixCategorie")) {
                paramEpargne.setCodCatCat(validationContratCompteForm.getCodeCategorieEpargne());
            }else{
                paramEpargne.setCodCatCat("");
            }

            ListRgmCatEpargne listRgmCatEpargne = 
                new ListRgmCatEpargne(); //Vo output 

            listRgmCatEpargne = (ListRgmCatEpargne)chargerRgmCatEpargneCmd.execute(paramEpargne);
            if(!listRgmCatEpargne.hasError()){
                if (validationContratCompteForm.getTypeRequest().equals("choixRegime")) {
                    validationContratCompteForm.setListCategorieEpargne(null);
                    validationContratCompteForm.setListCategorieEpargne(listRgmCatEpargne.getListCatEpargne());
                }

                if (listRgmCatEpargne.getCategorie() != null) {
                    validationContratCompteForm.setCodeCategorieEpargne(listRgmCatEpargne.getCategorie().getCategorieId().getCodCatCat());
                    validationContratCompteForm.setMntVersementEpargne(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
                    validationContratCompteForm.setMntCapitaliseEpargne(listRgmCatEpargne.getCategorie().getMontCaptCat().toString());
                }
                validationContratCompteForm.setPlanEpargne("true"); 
            }else {                   
                   List listErreur = listRgmCatEpargne.getErrors();
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
                     new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :modifierParamEpargne ");
                 text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
           //      logger.error("Exception : ",e); 
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }    

    }


    public ActionForward rejeterContrat(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
     
     ValidationContratCompteForm validationContratCompteForm = 
               (ValidationContratCompteForm)form; 
       ActionMessages actionMessages = new ActionMessages();   
      
       try{ 
        
        ContratCpt contratCpt = new ContratCpt();
         RejeterContratCmd  rejeterContratCmd = new RejeterContratCmd();
         if (validationContratCompteForm.getContratCpt().getContratCptId()!=null){
            ValueObject vo = (ValueObject)rejeterContratCmd.execute(validationContratCompteForm.getContratCpt().getContratCptId());             
             ContratCptId contratCptId = (ContratCptId)vo;
             
            if(!contratCptId.hasError()){
                 if (contratCptId!=null){
                 
                     validationContratCompteForm.setTransactionReussite("OUI");
                    
                 }
             }else {                   
                    List listErreur = contratCptId.getErrors();
                     //ActionMessages actionMessages = new ActionMessages();
                     for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                         com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                         ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                     }    
                     this.saveMessages(request, actionMessages);
                     return mapping.findForward("error");                    
             }         
         
         }
           return mapping.findForward("success");            
       
                 
     } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :rejeterContrat ");
                text.append(e.toString());
                erreur.setCode("200");
                 erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech() + ">>. Exception : ",e);  
        //        logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
      }    
   
   }
   
   
    public  ActionForward printContratCpt(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {
           
           ValidationContratCompteForm validationContratCompteForm = (ValidationContratCompteForm)form; 
           ContratCptId contratCptid= validationContratCompteForm.getContratCpt().getContratCptId();
           try {
                String typePersonne = validationContratCompteForm.getContratCpt().getClient().getTypePers().getCodTperTper(); 
                    //ContratCptId contratCptid= contratValide.getContratCptId();
                    CommonReportVO valueObject = new CommonReportVO();
                    ParamAgence paramAgence = new ParamAgence();
                    paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    Map parameters = new HashMap();
                    String pCodStrcStrc ="";
                    String pCodProd = "";
                    String pNumContratCpt = "";
                    String pLibEtat = "P_LIB_ETAT";
                    String pMatrUser = "P_NUM_MATR_USER";
                    String pCle   = "P_CLE";
                    
                    //------------------paramètres cod postal et gouvernorat
                parameters.put("ADR_VIL_CCPT",validationContratCompteForm.getContratCpt().getAdresseCorresp().getVille());
                parameters.put("ADR_CP_CCPT", validationContratCompteForm.getContratCpt().getAdresseCorresp().getCodCpCp());
                //-------------------------------------paramètre RIB
                      GetRibCmd getRibCmd = new GetRibCmd();
                      IValueObject voCcpt = (IValueObject)validationContratCompteForm.getContratCpt();
                      PrimitiveVO rib = (PrimitiveVO)getRibCmd.execute(voCcpt);
                      parameters.put("P_RIB", rib.getVString());
                 //------------------------------------------------------
                    String vLibEtat =""; 
                    String vMatrUser = paramAgence.getNumMatrUser().toString();
                    String vCodStrcStrc = contratCptid.getCodStrcStrc().toString();
                    String vCodProd = contratCptid.getCodPrdPrd().toString();
                    String vNumContratCpt = contratCptid.getNumCcptCcpt().toString();
                    String vCle = Constants.determinerCle(
                                       StrHandler.lpad(vCodStrcStrc,'0',3),
                                       StrHandler.lpad(vCodProd,'0',4),
                                       StrHandler.lpad(vNumContratCpt,'0',6));
                    if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)||contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)||contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM)){
                      pCodStrcStrc = "pCodStrcStrc";
                      pCodProd = "pCodPrdPrd";
                      pNumContratCpt = "pNumCcptCcpt";
                      
                       if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)|| contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEM) ){
                              if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)){
                                vLibEtat = "Souscription Contrat Compte Epargne Logement 'Malek'";
                              }else {
                                  vLibEtat = "Souscription Contrat Compte Epargne Ménage 'Farah'"; 
                              }
                                valueObject.setNomReport("ContratCptEpgn");
                                request.setAttribute("print","1");
                                parameters.put("pLibNCatCat",validationContratCompteForm.getCodeCategorieEpargne());
                                parameters.put("pLibNRgmRgm",validationContratCompteForm.getCodeRegimeEpargne());
                                Long vMnt2Capt=(new Long(validationContratCompteForm.getMntCapitaliseEpargne())*2);
                                parameters.put("pMnt2Capt",vMnt2Capt);
                                Long vMnt3Capt=(new Long(validationContratCompteForm.getMntCapitaliseEpargne())*3);
                                parameters.put("pMnt3Capt",vMnt3Capt);
                                Long vMontVersMens= new Long( validationContratCompteForm.getMntVersementEpargne());
                                parameters.put("pMontVersMens",vMontVersMens);
                                
                            }else if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                            
                            // Impression pour CompteEpargne Etudes 'FAIEZ'
                                vLibEtat = "Souscription Contrat Compte Epargne Etudes 'FAIEZ'";
                                valueObject.setNomReport("ContratCpt_PEE");
                                request.setAttribute("print","1");
                                parameters.put("COD_PAYS_PAYS", validationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays());
                                parameters.put("P_NOM_TUTEUR",
                                               validationContratCompteForm.getNomPersTuteur()+" "+validationContratCompteForm.getPrenomPersTuteur());
                                parameters.put("NUM_PCE_TUT", validationContratCompteForm.getNumPieceTut());
                                parameters.put("LIB_SIGL_TPCE",validationContratCompteForm.getTypePieceTut());
                                parameters.put("LIB_PROFESSION", validationContratCompteForm.getProfessionTuteur());
                              
                            }
                    }else{
                        pCodStrcStrc = "P_COD_STRC_STRC";
                        pCodProd = "P_COD_PRD_PRD";
                        pNumContratCpt = "P_NUM_CCPT_CCPT";
                        vLibEtat = "Souscription Contrat compte";
                        //-------------------------------------------------------------------IMPRESSION Personne Morale 
                                String pCodTpceTpce   = "P_COD_TPCE_TPCE";
                                String pLibActivite   = "LIB_ACTIVITE";
                                String pCodPaysPays   = "COD_PAYS_PAYS";
                                
                                String pMatrFisc   = "MATR_FISC";
                                //------------------------------------------
                                 String vCodTpceTpce   = "";
                                 String vCodPaysPays   = "";
                        
                                vCodPaysPays   = validationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays();
                                parameters.put(pCodPaysPays, vCodPaysPays);
                                
                           if(validationContratCompteForm.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
                                
                                   //---------------------------------------------------------------IMPRESSION Personne mineur
                                    String pNomTuteur  = "P_NOM_TUTEUR";
                                  
                                    String pNumPceTut   = "NUM_PCE_TUT";
                                    String pLibTpceTut  = "LIB_SIGL_TPCE";
                                    String vNomTuteur  = "";
                                   String vNumPceTut   = "";
                                    String vLibTpceTut  = "";
                                    String pLibProfession  = "LIB_PROFESSION";
                                    String vLibProfession  = "";
                                
                                   vNomTuteur  = validationContratCompteForm.getNomPersTuteur()+" "+validationContratCompteForm.getPrenomPersTuteur();
                                  
                                   vNumPceTut   = validationContratCompteForm.getNumPieceTut();
                                   vLibTpceTut  = validationContratCompteForm.getTypePieceTut();
                                   vLibProfession  = validationContratCompteForm.getProfessionTuteur();
                                                              
                                   parameters.put(pNomTuteur, vNomTuteur);
                                   parameters.put(pNumPceTut, vNumPceTut);
                                   parameters.put(pLibTpceTut, vLibTpceTut);
                                   parameters.put(pLibProfession, vLibProfession);
                                   valueObject.setNomReport("ContratCpt_PMin");  
                              
                               }else{
                               
                                       if((validationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==9)
                                          ||(    (validationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==11) 
                                                 && (validationContratCompteForm.getContratCpt().getClient().getTypePers().getCodTperTper().equals("2")))
                                           ){
                                           String vLibActivite   = "";
                                           String vMatrFisc   = "";
                                           
                                           vCodTpceTpce   = validationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce().toString();
                                           vLibActivite   = validationContratCompteForm.getContratCpt().getClient().getPersonne().getActivite().getLibActAct();
                                           vMatrFisc = validationContratCompteForm.getContratCpt().getClient().getNumFiscClt();
                                           
                                           parameters.put(pCodTpceTpce, vCodTpceTpce);
                                           parameters.put(pLibActivite, vLibActivite);
                                           parameters.put(pMatrFisc, vMatrFisc);
                                           valueObject.setNomReport("ContratCpt_PersMoral");
                                      }else{
                                           if(typePersonne.equals("3")){ // entité cotitualire
                                               valueObject.setNomReport("ContratCptCOT"); 
                                               }else {
                                                   valueObject.setNomReport("ContratCpt");
                                                 }
                                       }
                        }
                        //------------------------------------------------------------------- 
                                         
                    }
                     parameters.put(pMatrUser, vMatrUser);
                     parameters.put(pLibEtat, vLibEtat);
                     parameters.put(pCodStrcStrc, vCodStrcStrc);
                     parameters.put(pCodProd, vCodProd);
                     parameters.put(pNumContratCpt, vNumContratCpt);
                     parameters.put(pCle, vCle);
                     
                    valueObject.setParams(parameters);
                    parameters=null;
                    request.getSession().setAttribute("CommonPrintVo",valueObject);                   
                    request.setAttribute("print","1");                  
                     return mapping.findForward("confirmationValidationContrat");
            }
           catch (Exception e) {
                           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                           StringBuffer text = 
                               new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :printContratCpt ");
                           text.append(e.toString());
                           erreur.setCode("200");
                           erreur.setDescription(text.toString());
                           logger.error("Erreur au niveau de l'agence <<" +validationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
                           ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                           ActionMessages actionMessages = new ActionMessages();
                           actionMessages.add("Erreur ", actionMessage);
                           this.saveMessages(request, actionMessages);
                           return mapping.findForward("error");
                       }
       } 
   
}
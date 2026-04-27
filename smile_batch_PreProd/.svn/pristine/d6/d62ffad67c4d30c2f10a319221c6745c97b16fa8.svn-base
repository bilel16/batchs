
package com.bna.smile.web.souscription.actions;


import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;

import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;


import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;

import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetActiviteByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;

import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;

import com.bna.smile.model.domainecommun.commande.GetPaysCmd;
import com.bna.smile.model.domainecommun.commande.GetProfessionByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetRibCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.model.domainecommun.traitement.GetGouvernoratTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetDetailCategorieContratCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetPersClientCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.UpdateContratCptTrt;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.reporting.commande.PrinterCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;


import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

import java.io.File;
import java.io.IOException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

import org.hibernate.type.BlobType;


public class ConsultationContratCompteAction extends DispatchAction {

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
    private static final Logger logger = Logger.getLogger(ConsultationContratCompteAction.class);
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationContratCompteForm"
        sessionUtil.removeSession(request,"consultationContratCompteForm");
        Context context = ContextHandler.getContext();
        ActionMessages actionMessages =   new ActionMessages();
        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
     
            
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            consultationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            consultationContratCompteForm.setChoix("0");
            consultationContratCompteForm.clearForm();
            consultationContratCompteForm.setNumPieceId("");
            consultationContratCompteForm.setNumCcptRech("");
            consultationContratCompteForm.setCodPrdRech("");
            consultationContratCompteForm.setNumPieceId("");
            
          if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))) {
                    // agence
               consultationContratCompteForm.setCodTypeStructure("A");
               consultationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
         } else if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("4")) || 
                     paramAgence.getCodTstrcTstrc().equals(Long.valueOf("2"))
                     || paramAgence.getCodTstrcTstrc().equals(Long.valueOf("5"))){
                    // D.régionale
             consultationContratCompteForm.setCodStrcRech("");     
             consultationContratCompteForm.setCodTypeStructure("R");
                    // recherche de la liste des agences concernées
             PlacementDAO plcDao =(PlacementDAO)context.getBean("placementDAO");
             consultationContratCompteForm.setListAgConcernees(plcDao.getListStructureConcernes(paramAgence.getCodStrcStrc()));
        }
            
            
            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :initierPage ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
             //   logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
    public ActionForward initierPageEdit(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationContratCompteForm"
        sessionUtil.removeSession(request,"consultationContratCompteForm"); 
     
        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
     
            
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            consultationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            consultationContratCompteForm.setChoixEdit("0");
            consultationContratCompteForm.clearForm();
            consultationContratCompteForm.setDateDebRecherch("");
            consultationContratCompteForm.setDateFinRecherch("");
            consultationContratCompteForm.setNumPieceId("");
            consultationContratCompteForm.setNbrJours("");
            return mapping.findForward("edit");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :initierPageEdit ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
           //     logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
 
 
    public ActionForward initierPageConsultContratBanque(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
            consultationContratCompteForm.setNumPieceId(""); 
            consultationContratCompteForm.setListeContrats(null);
            return mapping.findForward("ConsultationCptBanque");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :initierPageConsultContratBanque ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
           //     logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
    
    
    
    
    public ActionForward initierPageNotification(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {     
            
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            consultationContratCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            consultationContratCompteForm.setChoix("0");
            consultationContratCompteForm.clearForm();
            consultationContratCompteForm.setDateDebRecherch("");
            consultationContratCompteForm.setDateFinRecherch("");
            consultationContratCompteForm.setNumPieceId("");
            consultationContratCompteForm.setDateDebut("");
            consultationContratCompteForm.setDateFin("");
            
            DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
            String d = myformat.format(new Date());
            consultationContratCompteForm.setDateActuelle(d);
                
            return mapping.findForward("notification");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :initierPageNotification ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
          //      logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
  /**
     * Cette méthode permet d'imprimer la liste des contrats compte selon l'etat du contrat et le critère de recherche
     * Lamia jerbi
     */
    
    public ActionForward imprimerCcptSelonChoix(ActionMapping mapping, 
                                                          ActionForm form, 
                                                          HttpServletRequest request, 
                                                          HttpServletResponse response) throws IOException, 
                                                                                               ServletException {
             ConsultationContratCompteForm consultationContratCompteForm = 
                 (ConsultationContratCompteForm)form;
             ActionMessages actionMessages = new ActionMessages();
             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 
                 /*---------------------------------------------------------------------*/
                 String pCodStrcStrc = "P_COD_STRC_STRC";
                 String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
                /*------------------------------------------------------------------*/
                  String pCodTpceTpce = "P_COD_TPCE_TPCE";
                  String pNumTpceTpce = "P_NUM_PCE_PERS";
                 String vCodTpceTpce = "";
                 String vNumTpceTpce = "";
                /*------------------------------------------------------------------*/
                 String pDateDeb = "P_DATE_DEB";
                 String pDateFin = "P_DATE_FIN";
                 String vDateFin="";
                 String vDateDeb="";
                 /*-----------------------------------------------------------------*/
                 
                 String pEtat = "P_VALIDE";
                 String pMatrUser = "P_NUM_MATR_USER";
                 String vMatrUser = paramAgence.getNumMatrUser().toString();
                 String pLibEtat="P_LIB_ETAT";
                 String vLibEtat="";
                 String vEtat="";
                 
              if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_ATT.toString())){
                       vLibEtat = "Liste des contrats comptes (En attente)";
                       vEtat=Constants.COD_ETAT_CPT_ATT.toString();
                       }else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID.toString())){
                               vLibEtat = "Liste des contrats comptes (Validés)";
                               vEtat=Constants.COD_ETAT_CPT_VALID.toString();
                               } else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_BLOQUE.toString())){
                                       vLibEtat = "Liste des contrats comptes (Bloqués)";
                                       vEtat=Constants.COD_ETAT_CPT_BLOQUE.toString();
                                       }else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_RESILIE.toString())){
                                        vLibEtat = "Liste des contrats comptes (Résiliés)";
                                        vEtat=Constants.COD_ETAT_CPT_RESILIE.toString();
                                       }else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_SEMIACTIF.toString())){
                                           vLibEtat = "Liste des contrats comptes (Semi-actif)";
                                           vEtat=Constants.COD_ETAT_CPT_SEMIACTIF.toString();
                                           }else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_TCONTENTIEU.toString())){
                                                  vLibEtat = "Liste des contrats comptes (Transférés au contentieux)";
                                                  vEtat=Constants.COD_ETAT_CPT_TCONTENTIEU.toString(); 
                                                   }else if (consultationContratCompteForm.getChoixEtatCcpt().equals(Constants.COD_ETAT_CPT_REJETE.toString())){
                                                            vLibEtat = "Liste des contrats comptes (Annulés)";
                                                            vEtat=Constants.COD_ETAT_CPT_REJETE.toString();
                                                            }else {
                                                                       vLibEtat = "Liste de tous les contrats comptes";
                                                                       vEtat="";
                                                                   }
                
                 parameters.put(pLibEtat, vLibEtat);
                 parameters.put(pCodStrcStrc, vCodStrcStrc);
                 parameters.put(pEtat, vEtat);  
                 parameters.put(pMatrUser, vMatrUser);
                 if (consultationContratCompteForm.getChoixEdit().equals("1")) {
                       
                         if(!consultationContratCompteForm.getDateDebRecherch().equals("") ||
                            !consultationContratCompteForm.getDateFinRecherch().equals("")){
                                vDateFin=consultationContratCompteForm.getDateFinRecherch();
                                vDateDeb=consultationContratCompteForm.getDateDebRecherch();
                                parameters.put(pDateDeb,vDateDeb);
                                parameters.put(pDateFin,vDateFin);
                                valueObject.setNomReport("listeContratSanNbrJour");
                            }else {
                                 valueObject.setNomReport("listeContratSD");
                                 }
                      
                     } else if (consultationContratCompteForm.getChoixEdit().equals("0")) {
                                             vCodTpceTpce = consultationContratCompteForm.getTypePieceId();
                                            vNumTpceTpce = consultationContratCompteForm.getNumPieceId();
                                            parameters.put(pCodTpceTpce,vCodTpceTpce);
                                            parameters.put(pNumTpceTpce,vNumTpceTpce);
                                            if(!consultationContratCompteForm.getChoixEtatCcpt().equals("0")){
                                                valueObject.setNomReport("listeContratTP");
                                               }else {
                                                    valueObject.setNomReport("listeContratTP_SE");
                                                    }
                                           }else if (consultationContratCompteForm.getChoixEdit().equals("2")) {
                                                       int  nbreJour=Integer.parseInt(consultationContratCompteForm.getNbrJours())*(-1);         
                                                       vDateFin=DateHandler.dateToStr(paramAgence.getDateOp());
                                                   
                                                    String pNbrJour = "P_NBR_JOUR";
                                                    String vNbrJour = "0";
                                                       vNbrJour=consultationContratCompteForm.getNbrJours();
                                                       parameters.put(pNbrJour,vNbrJour);
                                                       parameters.put(pDateFin,vDateFin);
                                                       valueObject.setNomReport("listeContrat_nbr_j");
                                                     }
                 
                 valueObject.setParams(parameters);
                 
                 //valueObject.setTypeImpression("F");/*P : printer , F: file */
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
                 
             return mapping.findForward("edit");
             } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("L'impression de la liste des contrats a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
                 text.append("Exception au niveau de l'agence:"); text.append(consultationContratCompteForm.getCodStrcRech());
                 text.append(". Exception :"); text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             logger.error(text.toString(),e);   
             return mapping.findForward("error");
             }                                                                                
                                                                                               
         }
    public ActionForward rechercherPersonneEdit(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ConsultationContratCompteForm consultationContratCompteForm = 
           (ConsultationContratCompteForm)form;
        try {
        int nbreJour;
        consultationContratCompteForm.clearForm();
        ContratCpt premierContrat = new ContratCpt();
        /* garnir les informations sur l'agence (apartir du Login) */
        paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        consultationContratCompteForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());
        PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
        Listes listesCpts = new Listes();
        listesCpts.setList(new ArrayList());
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
        // si la recherche est effectuée par type et num piece
        if(!paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)){ 
        // si une structure differente de la DAJ alors on recherche par cod structure
            personneRechercheContratVo.setCodeAgence(consultationContratCompteForm.getCodStrcRech());   
        }
        
        if (consultationContratCompteForm.getChoixEdit().equals("0")) {
            personneStrc.setCodTpceTpce(new Long(consultationContratCompteForm.getTypePieceId()));
            personneStrc.setNumPcePers(consultationContratCompteForm.getNumPieceId());
            personneRechercheContratVo.setPersonneStrc(personneStrc);
            if (!consultationContratCompteForm.getChoixEtatCcpt().equals("0")) {
                 personneRechercheContratVo.setEtatContrat(consultationContratCompteForm.getChoixEtatCcpt());
                 }
            
            GetListContratCmd getListContratCmd = new GetListContratCmd();                
            listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
        } else if(consultationContratCompteForm.getChoixEdit().equals("1")) {
                    personneRechercheContratVo.setEtatContrat(consultationContratCompteForm.getChoixEtatCcpt());
                    personneRechercheContratVo.setDateDebut(DateHandler.strToDate(consultationContratCompteForm.getDateDebRecherch()));
                    personneRechercheContratVo.setDateFin(DateHandler.strToDate(consultationContratCompteForm.getDateFinRecherch()));
                    //personneRechercheContratVo.setCodeAgence(consultationContratCompteForm.getCodStrcRech());   
                    GetListContratCmd getListContratCmd = new GetListContratCmd();                
                    listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
                  }else if(consultationContratCompteForm.getChoixEdit().equals("2")) {
                        personneRechercheContratVo.setEtatContrat(consultationContratCompteForm.getChoixEtatCcpt());
                        nbreJour=Integer.parseInt(consultationContratCompteForm.getNbrJours())*(-1);         
                        personneRechercheContratVo.setDateDebut(DateHandler.addJour(paramAgence.getDateOp(),nbreJour));
                        personneRechercheContratVo.setDateFin(paramAgence.getDateOp());
                        //personneRechercheContratVo.setCodeAgence(consultationContratCompteForm.getCodStrcRech());   
                        GetListContratCmd getListContratCmd = new GetListContratCmd();                
                        listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
                      }
                                                                                     
        if(!listesCpts.hasError()){  
                if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                    consultationContratCompteForm.setAlert("ClientInexistant");
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
                    consultationContratCompteForm.setListeContrats(listeDesContratsView);
        
                    //  ************************************************************
        
                    consultationContratCompteForm.setAlert("ClientExistant");
                    premierContrat = (ContratCpt)listesCpts.getList().get(0);
                    client = premierContrat.getClient();
                    personne = premierContrat.getClient().getPersonne();
                    consultationContratCompteForm.setPersonne(personne);
                    consultationContratCompteForm.setClient(client);
                    if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                        consultationContratCompteForm.setNomId(personne.getNomNomPers());
                        consultationContratCompteForm.setPrenomId(personne.getNomPrnPers());
                    } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        consultationContratCompteForm.setNomId(personne.getNomRsPers());
                        consultationContratCompteForm.setPrenomId(personne.getLibSiglPers());
                     } else if (client.getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                        consultationContratCompteForm.setNomId(personne.getNomNomPers());
                    }

                }else{
                    consultationContratCompteForm.setAlert("ClientInexistant");
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
    return mapping.findForward("edit");
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :rechercherPersonneEdit ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
   //     logger.error("Exception : ",e); 
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
         ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
            
            consultationContratCompteForm.clearForm();
            ContratCpt premierContrat = new ContratCpt();
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            consultationContratCompteForm.setCodeAgance(consultationContratCompteForm.getCodStrcRech());
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
            personneStrc.setCodStrcStrc(Long.valueOf(consultationContratCompteForm.getCodStrcRech()));
            // si la recherche est effectuée par type et num piece
            if (consultationContratCompteForm.getChoix().equals("0")) {
                personneStrc.setCodTpceTpce(new Long(consultationContratCompteForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationContratCompteForm.getNumPieceId());
                personneRechercheContratVo.setPersonneStrc(personneStrc);
                personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
                GetListContratCmd getListContratCmd = new GetListContratCmd();                
                listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
            } else if (consultationContratCompteForm.getChoix().equals("1")) {
                // si la recherche est effectuée par num de contrat
                GetDetailContratCmd getDetailContratCmd = 
                    new GetDetailContratCmd();
                ContratCptId contratCptId = new ContratCptId();
                ContratCpt contratCpt = new ContratCpt();
                contratCptId.setCodPrdPrd(new Long(consultationContratCompteForm.getCodPrdRech()));
                contratCptId.setNumCcptCcpt(new Long(consultationContratCompteForm.getNumCcptRech()));
                contratCptId.setCodStrcStrc(Long.valueOf(consultationContratCompteForm.getCodStrcRech()));
                contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
                if (contratCpt.getContratCptId() != null) {
                    listesCpts.getList().add(contratCpt);
                    listesCpts.setList(new ArrayList());
                    listesCpts.getList().add(contratCpt);
                    personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                    consultationContratCompteForm.setContratCpt(contratCpt);
                }
            }
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        consultationContratCompteForm.setAlert("ClientInexistant");
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
                        consultationContratCompteForm.setListeContrats(listeDesContratsView);
    
                        //  ************************************************************
    
                        consultationContratCompteForm.setAlert("ClientExistant");
                        premierContrat = (ContratCpt)listesCpts.getList().get(0);
                        client = premierContrat.getClient();
                        personne = premierContrat.getClient().getPersonne();
                        consultationContratCompteForm.setPersonne(personne);
                        consultationContratCompteForm.setClient(client);
                        if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                            consultationContratCompteForm.setNomId(personne.getNomNomPers());
                            consultationContratCompteForm.setPrenomId(personne.getNomPrnPers());
                            consultationContratCompteForm.setOpenTabsheetClient("true");
                            affecterDonnesPersonnePhysique(consultationContratCompteForm, 
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
                                    consultationContratCompteForm.setOpenTabsheetTuteur("true");
                                    affecterDonnesPersonneTuteur(consultationContratCompteForm, 
                                                                 tuteur);
                                }
                            }
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            consultationContratCompteForm.setNomId(personne.getNomRsPers());
                            consultationContratCompteForm.setPrenomId(personne.getLibSiglPers());
                            consultationContratCompteForm.setOpenTabsheetMorale("true");
                            affecterDonnesPersonneMorale(consultationContratCompteForm, 
                                                         client);
    
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                            consultationContratCompteForm.setNomId(personne.getNomNomPers());
                            affecterDonnesPersonneCotitulaire(consultationContratCompteForm, 
                                                              personne);
                        }
                        /*mandataire sur les contrats suivants */
                        listesCptsMandataire = 
                                (Listes)getListContratMandataireCmd.execute(personneStrc);
                        consultationContratCompteForm.setListeContratsMandataire(listesCptsMandataire.getList());
    
                        /* cotitulaire dans les entités cotitulaire suivantes  */
                        listEntiteCotit = 
                                (Listes)getListCotitulairePersonneCmd.execute(personneStrc);
                        consultationContratCompteForm.setListeEntiteCotit(listEntiteCotit.getList());
    
                        if (listesCpts.getList().size() == 1) {
                            affecterDonnesContrat(consultationContratCompteForm, 
                                                  premierContrat);
                            consultationContratCompteForm.setContratCpt(premierContrat);                      
                            consultationContratCompteForm.setOpenTabsheetContrat("true");
                        }
                    }else{
                        consultationContratCompteForm.setAlert("ClientInexistant");
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
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
            //    logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

    }


    public ActionForward rechercherPersonneNotification(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
         ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
            
            consultationContratCompteForm.clearForm();
            ContratCpt premierContrat = new ContratCpt();
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            consultationContratCompteForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
            
            personneRechercheContratVo.setTypePersonne(Constants.PERSMORALE);
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());            
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            // si la recherche est effectuée par type et num piece
            if (consultationContratCompteForm.getChoix().equals("0")) {
                personneStrc.setCodTpceTpce(new Long(consultationContratCompteForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationContratCompteForm.getNumPieceId());
                personneRechercheContratVo.setPersonneStrc(personneStrc);
                personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
                GetListContratCmd getListContratCmd = new GetListContratCmd();                
                listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
            } else if (consultationContratCompteForm.getChoix().equals("1")) {
                // si la recherche est effectuée par num de contrat
                GetDetailContratCmd getDetailContratCmd = 
                    new GetDetailContratCmd();
                ContratCptId contratCptId = new ContratCptId();
                ContratCpt contratCpt = new ContratCpt();
                contratCptId.setCodPrdPrd(new Long(consultationContratCompteForm.getCodPrdRech()));
                contratCptId.setNumCcptCcpt(new Long(consultationContratCompteForm.getNumCcptRech()));
                contratCptId.setCodStrcStrc(paramAgence.getCodStrcStrc());
                contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
                if (contratCpt.getContratCptId() != null) {
                    listesCpts.getList().add(contratCpt);
                    listesCpts.setList(new ArrayList());
                    listesCpts.getList().add(contratCpt);
                    personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());
                    consultationContratCompteForm.setContratCpt(contratCpt);
                }
            }
            
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        consultationContratCompteForm.setAlert("ClientInexistant");
                    } else {
                       if (listesCpts.getList().size() > 0 ) {
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
                            if(contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
                              contratCptView.setContratCpt(contratCpt);
                              listeDesContratsView.add(contratCptView);
                            }
                          }
                          
                        if(listeDesContratsView.size()>0){  
                           consultationContratCompteForm.setListeContrats(listeDesContratsView);
                           consultationContratCompteForm.setAlert("ClientExistant");
                                              
                            premierContrat = (ContratCpt)listesCpts.getList().get(0);
                            client = premierContrat.getClient();
                            personne = premierContrat.getClient().getPersonne();
                            consultationContratCompteForm.setPersonne(personne);
                            consultationContratCompteForm.setClient(client);
                            consultationContratCompteForm.setNomId(personne.getNomRsPers());
                            consultationContratCompteForm.setPrenomId(personne.getLibSiglPers());                         
                         
                        }else consultationContratCompteForm.setAlert("ClientInexistant");
                        
                    }else{
                        consultationContratCompteForm.setAlert("ClientInexistant");
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

            return mapping.findForward("notification");
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :rechercherPersonneNotification ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
        //       logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

    }


    public ActionForward rechercherListeContratBanque(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
         ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        try {
                        
            consultationContratCompteForm.setListeContrats(null);
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();            
            personneRechercheContratVo.setTypePersonne(Constants.PERSMORALE);
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());            
            PersonneStrc personneStrc = new PersonneStrc();
           
            // si la recherche est effectuée par type et num piece
            
                personneStrc.setCodTpceTpce(new Long(consultationContratCompteForm.getTypePieceId()));
                personneStrc.setNumPcePers(consultationContratCompteForm.getNumPieceId());
                personneRechercheContratVo.setPersonneStrc(personneStrc);                
                GetListContratCmd getListContratCmd = new GetListContratCmd();                
                listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
            
            
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        consultationContratCompteForm.setAlert("ClientInexistant");
                    } else {
                       if (listesCpts.getList().size() > 0 ) {
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
                          
                        if(listeDesContratsView.size()>0){  
                           consultationContratCompteForm.setListeContrats(listeDesContratsView);
                           consultationContratCompteForm.setAlert("ClientExistant");                                                                                             
                         
                        }else consultationContratCompteForm.setAlert("ClientInexistant");
                        
                    }else{
                        consultationContratCompteForm.setAlert("ClientInexistant");
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

            return mapping.findForward("ConsultationCptBanque");
           
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationContratCompteAction / Dispatch Action :rechercherListeContratBanque ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
         //       logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

    }


    public void affecterDonnesPersonnePhysique(ActionForm form, 
                                               Client client) throws Exception{

        
                ConsultationContratCompteForm consultationContratCompteForm = 
                    (ConsultationContratCompteForm)form;

                consultationContratCompteForm.setTypePersonneClt(client.getTypePers().getLibTperTper());
                consultationContratCompteForm.setCategoriePersonneClt(client.getPersonne().getCategoriePersonne().getLibCatpCatp());            
                consultationContratCompteForm.setTypePieceClt(client.getPersonne().getTypePiece().getLibSiglTpce());
                consultationContratCompteForm.setDateDelivClt(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
                consultationContratCompteForm.setNumPieceClt(client.getPersonne().getNumPcePers());
                
                if (client.getPersonne().getGouvernorat() != null) {
                    GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                    Gouvernorat gouvernorat = new Gouvernorat();
                    gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                    gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                    consultationContratCompteForm.setLieuDelivClt(gouvernorat.getLibGouvGouv().toString());
                    consultationContratCompteForm.setCodLieuDelivClt(gouvernorat.getCodGouvGouv().toString());
                }
                
                 PieceAnnexe pieceAnnexe = new PieceAnnexe();
                  if (client.getPersonne().getPieceAnnexes() != null && client.getPersonne().getPieceAnnexes().size() > 0) {
                      for (Iterator it = client.getPersonne().getPieceAnnexes().iterator(); it.hasNext(); ) {
                           pieceAnnexe = (PieceAnnexe)it.next();                     
                           if(pieceAnnexe.getDatFvalPian()!= null &&  pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                               consultationContratCompteForm.setTypePieceAnnexe((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                               consultationContratCompteForm.setDateDellivPiann(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                               consultationContratCompteForm.setDateFinPian(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                               consultationContratCompteForm.setNumPieceAnnexe(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                               break;
                           }                    
                      }
                  }

                consultationContratCompteForm.setTitrePersClt(client.getPersonne().getLibTitrPers());
                consultationContratCompteForm.setNomPersClt(client.getPersonne().getNomNomPers());
                consultationContratCompteForm.setPrenomPersClt(client.getPersonne().getNomPrnPers());
                consultationContratCompteForm.setNomPereClt(client.getPersonne().getNomPrnpPers());

                consultationContratCompteForm.setDateNaisClt(DateHandler.dateToStr(client.getPersonne().getDatNaisPers()));
                consultationContratCompteForm.setLieuNaisClt(client.getPersonne().getLibNaisPers());
               
                if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                    //extraire la nationalité
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);                
                    consultationContratCompteForm.setNationaliteClt(pays.getLibNatPays());                   
                }
                
                consultationContratCompteForm.setResidentClt(client.getPersonne().getBoolResPers().toString());                
                consultationContratCompteForm.setSexeClt(client.getPersonne().getCodSexPers());
                if(client.getPersonne().getPaysByCodNaisPays()!= null){
                  consultationContratCompteForm.setPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getLibPaysPays());
                  consultationContratCompteForm.setCodPaysNaisClt(client.getPersonne().getPaysByCodNaisPays().getCodPaysPays());
                }
                consultationContratCompteForm.setSitFamilialeClt(client.getPersonne().getCodSitfPers());
                consultationContratCompteForm.setSectActiviteClt(client.getPersonne().getCodSectPers());
                
                if (client.getPersonne().getProfession() != null) {
                // extraire la profession
                    consultationContratCompteForm.setCodGroupProfClt(client.getPersonne().getProfession().getProfessionId().getCodGproGpro().toString());
                    consultationContratCompteForm.setCodProfClt(client.getPersonne().getProfession().getProfessionId().getCodProfProf().toString());            
                    GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                    Profession profession = new Profession();
                    ProfessionId professionId = new ProfessionId();
                    professionId.setCodProfProf(Long.valueOf(consultationContratCompteForm.getCodProfClt()));
                    professionId.setCodGproGpro(Long.valueOf(consultationContratCompteForm.getCodGroupProfClt()));
                    profession.setProfessionId(professionId);
                    profession = (Profession)getProfessionByIdCmd.execute(profession);
                    if (profession != null && profession.getLibProfProf() != null) {
                        consultationContratCompteForm.setProfessionClt(profession.getLibProfProf());
                    }
                }
                
                if (client.getPersonne().getActivite() != null) {
                    // extraire l'activité
                    consultationContratCompteForm.setCodActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodActAct().toString());
                    consultationContratCompteForm.setCodClasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodCactCact().toString());
                    consultationContratCompteForm.setCodSclasActiviteClt(client.getPersonne().getActivite().getActiviteId().getCodSactSact().toString());

                    GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                    Activite activite = new Activite();
                    ActiviteId activiteId = new ActiviteId();
                    activiteId.setCodActAct(consultationContratCompteForm.getCodActiviteClt());
                    activiteId.setCodCactCact(consultationContratCompteForm.getCodClasActiviteClt());
                    activiteId.setCodSactSact(Long.valueOf(consultationContratCompteForm.getCodSclasActiviteClt()));
                    activite.setActiviteId(activiteId);
                    activite = (Activite)getActiviteByIdCmd.execute(activite);
                    if (activite != null && activite.getLibActAct() != null) {
                        consultationContratCompteForm.setActiviteClt(activite.getLibActAct());
                    }
                }
                
                // adresse de résidence
                if (client.getPersonne().getAdresseResid() != null) {
                    // Immeuble
                    if (client.getPersonne().getAdresseResid().getImmeuble() != null) {
                        consultationContratCompteForm.setImmeubleAdrResid(client.getPersonne().getAdresseResid().getImmeuble());
                    }
                    // Rue
                    if (client.getPersonne().getAdresseResid().getRue() != null) {
                        consultationContratCompteForm.setRueAdrResid(client.getPersonne().getAdresseResid().getRue());
                    }

                    // Cite
                    if (client.getPersonne().getAdresseResid().getCite() != null) {
                        consultationContratCompteForm.setCiteAdrResid(client.getPersonne().getAdresseResid().getCite());
                    }

                    // Pays 
                    if (client.getPersonne().getAdresseResid().getCodPaysPays() != null) {
                        consultationContratCompteForm.setCodPayAdrResid(client.getPersonne().getAdresseResid().getCodPaysPays());
                        GetPaysCmd getPaysCmd = new GetPaysCmd();
                        Pays pays = new Pays();
                        pays.setCodPaysPays(client.getPersonne().getAdresseResid().getCodPaysPays());
                        pays = (Pays)getPaysCmd.execute(pays);
                        if (pays.getLibPaysPays() != null) {
                            consultationContratCompteForm.setPaysAdrResid(pays.getLibPaysPays());
                        }
                    }
                    // Code Postal
                    if (client.getPersonne().getAdresseResid().getCodCpCp() != null) {
                        consultationContratCompteForm.setCodePostalAdrResid(client.getPersonne().getAdresseResid().getCodCpCp());
                        // si le pays est la tunisie extraire le libelle du code postal
                        if ((client.getPersonne().getAdresseResid().getCodPaysPays() != null) && 
                            (client.getPersonne().getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                            GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                            CodePostal codePostal = new CodePostal();
                            codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseResid().getCodCpCp()));
                            codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                            consultationContratCompteForm.setLibPostalAdrResid(codePostal.getLibCpCp());

                            // gouvernerat
                            consultationContratCompteForm.setCodGouvGouvRes(codePostal.getGouvernorat().getCodGouvGouv().toString());
                            consultationContratCompteForm.setLibGouvGouvRes(codePostal.getGouvernorat().getLibGouvGouv());
                        }
                    }
                } // Fin adresse de résidence
                             
            
                 // adresse professionnelle
                 if (client.getPersonne().getAdresseProf() != null) {
                     // Immeuble
                     if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                         consultationContratCompteForm.setImmeubleAdrProf(client.getPersonne().getAdresseProf().getImmeuble());
                     }
                     // Rue
                     if (client.getPersonne().getAdresseProf().getRue() != null) {
                         consultationContratCompteForm.setRueAdrProf(client.getPersonne().getAdresseProf().getRue());
                     }

                     // Cite
                     if (client.getPersonne().getAdresseProf().getCite() != null) {
                         consultationContratCompteForm.setCiteAdrProf(client.getPersonne().getAdresseProf().getCite());
                     }

                     // Pays 
                     if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                         consultationContratCompteForm.setCodPayAdrProf(client.getPersonne().getAdresseProf().getCodPaysPays());
                         GetPaysCmd getPaysCmd = new GetPaysCmd();
                         Pays pays = new Pays();
                         pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                         pays = (Pays)getPaysCmd.execute(pays);
                         if (pays.getLibPaysPays() != null) {
                             consultationContratCompteForm.setPaysAdrProf(pays.getLibPaysPays());
                         }
                     }
                     // Code Postal
                     if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                         consultationContratCompteForm.setCodePostalAdrProf(client.getPersonne().getAdresseProf().getCodCpCp());
                         // si le pays est la tunisie extraire le libelle du code postal
                         if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                             (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                             GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                             CodePostal codePostal = new CodePostal();
                             codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                             codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                             consultationContratCompteForm.setLibPostalAdrProf(codePostal.getLibCpCp());

                             // gouvernerat
                             consultationContratCompteForm.setCodGouvGouvProf(codePostal.getGouvernorat().getCodGouvGouv().toString());
                             consultationContratCompteForm.setLibGouvGouvProf(codePostal.getGouvernorat().getLibGouvGouv());
                         }
                     }
                 } // Fin adresse professionnelle
            
                  consultationContratCompteForm.setNumTelPers(client.getPersonne().getNumTelPers());
                  consultationContratCompteForm.setNumFaxpers(client.getPersonne().getNumFaxPers());
                  
            


    }

    public void affecterDonnesContrat(ActionForm form, ContratCpt contratCpt) throws Exception{

        
            ConsultationContratCompteForm consultationContratCompteForm = 
                (ConsultationContratCompteForm)form;
            
            GetDetailCategorieContratCmd getDetailCategorieContratCmd = new GetDetailCategorieContratCmd();
            DetailCatCpt detailCatCpt = new DetailCatCpt();
            ContratCptId contratCptId = new ContratCptId();
            
            consultationContratCompteForm.setCodeProduitCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                            '0', 
                                                                            4));
            consultationContratCompteForm.setCodePrdCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                        '0', 
                                                                        4));
            consultationContratCompteForm.setCodeStructureCpt(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                              '0', 
                                                                              3));
            consultationContratCompteForm.setNumCompteCpt(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                          '0', 
                                                                          6));
            
            consultationContratCompteForm.setCodeDeviseCpt(contratCpt.getDevise().getCodDevDev().toString());
            consultationContratCompteForm.setLibDeviseCpt(contratCpt.getDevise().getLibDevDev());
            consultationContratCompteForm.setLibelleProduitCpt(contratCpt.getProduit().getLibPrdPrd());
            consultationContratCompteForm.setDateOuvertureCpt(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
            consultationContratCompteForm.setIntituleCompteCpt(contratCpt.getNomIntiCcpt());

            consultationContratCompteForm.setTypePieceCpt(contratCpt.getClient().getPersonne().getTypePiece().getLibSiglTpce());
            consultationContratCompteForm.setNomCpt(consultationContratCompteForm.getNomId());
            consultationContratCompteForm.setNumFiscaleCpt(contratCpt.getClient().getNumFiscClt());
            consultationContratCompteForm.setNumPieceCpt(contratCpt.getClient().getPersonne().getNumPcePers());
            consultationContratCompteForm.setCodeDouaneCpt(contratCpt.getClient().getCodDoanClt());
            consultationContratCompteForm.setDateRelationCpt(DateHandler.dateToStr(contratCpt.getClient().getDatRelClt()));
            consultationContratCompteForm.setPrenomCpt(consultationContratCompteForm.getPrenomId());
            consultationContratCompteForm.setNumBctCpt(contratCpt.getClient().getNumBctClt());
            consultationContratCompteForm.setNumRnePers(contratCpt.getClient().getNumRnePers());
            
            // adresse de correspondance
            if (contratCpt.getAdresseCorresp() != null) {
                // Immeuble
                if (contratCpt.getAdresseCorresp().getImmeuble() != null) {
                    consultationContratCompteForm.setImmeubleCpt(contratCpt.getAdresseCorresp().getImmeuble());
                }
                // Rue
                if (contratCpt.getAdresseCorresp().getRue() != null) {
                    consultationContratCompteForm.setRueCpt(contratCpt.getAdresseCorresp().getRue());
                }

                // Cite
                if (contratCpt.getAdresseCorresp().getCite() != null) {
                    consultationContratCompteForm.setCiteCpt(contratCpt.getAdresseCorresp().getCite());
                }

                // Pays 
                if (contratCpt.getAdresseCorresp().getCodPaysPays() != null) {
                    consultationContratCompteForm.setCodPayCpt(contratCpt.getAdresseCorresp().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(contratCpt.getAdresseCorresp().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        consultationContratCompteForm.setPaysCpt(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (contratCpt.getAdresseCorresp().getCodCpCp() != null) {
                    consultationContratCompteForm.setCodePostalCpt(contratCpt.getAdresseCorresp().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((contratCpt.getAdresseCorresp().getCodPaysPays() != null) && 
                        (contratCpt.getAdresseCorresp().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(contratCpt.getAdresseCorresp().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        consultationContratCompteForm.setLibCodePostalCpt(codePostal.getLibCpCp());
                        
                        // gouvernerat
                        consultationContratCompteForm.setCodGouvGouvCpt(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        consultationContratCompteForm.setLibGouvGouvCpt(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse de correspondance
            
             if(contratCpt.getCodPerCpt() != null)
               consultationContratCompteForm.setPeridiciteCpt(contratCpt.getCodPerCpt());
             if(contratCpt.getCodFoncCpt()!= null)
              consultationContratCompteForm.setFonctionementCpt(contratCpt.getCodFoncCpt());
             if(contratCpt.getBoolRelvCpt() != null)
               consultationContratCompteForm.setReleveCpt(contratCpt.getBoolRelvCpt().toString());             
             
            contratCptId.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd());
            contratCptId.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt());
            contratCptId.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
            
            detailCatCpt = (DetailCatCpt)getDetailCategorieContratCmd.execute(contratCptId);
            
            
            if(detailCatCpt.getNumDccDcc()!=null){
                consultationContratCompteForm.setCodeCategorieEpargne(detailCatCpt.getCategorie().getLibCatCat());
                consultationContratCompteForm.setCodeRegimeEpargne(detailCatCpt.getCategorie().getRegime().getLibRgmRgm());
                consultationContratCompteForm.setMntCapitaliseEpargne(detailCatCpt.getCategorie().getMontCaptCat().toString());
                consultationContratCompteForm.setMntVersementEpargne(detailCatCpt.getCategorie().getMontVersCat().toString());
                consultationContratCompteForm.setNumLivretEpargne(contratCpt.getNumLivrCcpt());
                consultationContratCompteForm.setPlanEpargne("true");
            }else{
                consultationContratCompteForm.setPlanEpargne("false");
                StringBuffer text = new StringBuffer(" Aucun détail catégorie pour le compte :");
                text.append(consultationContratCompteForm.getCodStrcRech()); text.append(consultationContratCompteForm.getCodPrdRech());
                text.append(consultationContratCompteForm.getNumCompteCpt()); text.append(" >> aucun enregistrement dans la table detail_cat_cpt, ou bien la date fin n'est pas vide");
                logger.debug(text.toString());
            }
     
       

    }


    public void affecterDonnesPersonneTuteur(ActionForm form, 
                                             Personne tuteur) throws Exception{

       


            ConsultationContratCompteForm consultationContratCompteForm = 
                (ConsultationContratCompteForm)form;

            consultationContratCompteForm.setTypePersonneTuteur("Personne physique individuelle");
            consultationContratCompteForm.setCategoriePersonneTuteur(tuteur.getCategoriePersonne().getLibCatpCatp());

            consultationContratCompteForm.setTypePieceTut(tuteur.getTypePiece().getLibSiglTpce());
            consultationContratCompteForm.setDateDelivTuteur(DateHandler.dateToStr(tuteur.getDatDlvPers()));
            consultationContratCompteForm.setNumPieceTut(tuteur.getNumPcePers());
            
             if (tuteur.getGouvernorat() != null) {
                 GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                 Gouvernorat gouvernorat = new Gouvernorat();
                 gouvernorat.setCodGouvGouv(tuteur.getGouvernorat().getCodGouvGouv());
                 gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                 consultationContratCompteForm.setLieuDelivTuteur(gouvernorat.getLibGouvGouv().toString());
                 consultationContratCompteForm.setCodLieuDelivTuteur(gouvernorat.getCodGouvGouv().toString());
             }else {
                 logger.error("tuteur.getGouvernorat() == null");
             }
             
              PieceAnnexe pieceAnnexe = new PieceAnnexe();
               if (tuteur.getPieceAnnexes() != null && tuteur.getPieceAnnexes().size() > 0) {
                   for (Iterator it = tuteur.getPieceAnnexes().iterator(); it.hasNext(); ) {
                        pieceAnnexe = (PieceAnnexe)it.next();                     
                        if(pieceAnnexe.getDatFvalPian()!= null &&  pieceAnnexe.getDatFvalPian().getTime()>new Date().getTime()){
                            consultationContratCompteForm.setTypePieceAnnexeTuteur((pieceAnnexe.getPieceAnnexeId().getCodTpceTpce().toString()));
                            consultationContratCompteForm.setDateDellivPiannTuteur(DateHandler.dateToStr(pieceAnnexe.getDatDelvPian()));
                            consultationContratCompteForm.setDateFinPianTuteur(DateHandler.dateToStr(pieceAnnexe.getDatFvalPian()));
                            consultationContratCompteForm.setNumPieceAnnexeTuteur(pieceAnnexe.getPieceAnnexeId().getNumPcePian());
                            break;
                        }                    
                   }
               } 

             consultationContratCompteForm.setTitrePersTuteur(tuteur.getLibTitrPers());
             consultationContratCompteForm.setNomPersTuteur(tuteur.getNomNomPers());
             consultationContratCompteForm.setPrenomPersTuteur(tuteur.getNomPrnPers());
             consultationContratCompteForm.setNomPereTuteur(tuteur.getNomPrnpPers());

             consultationContratCompteForm.setDateNaisTuteur(DateHandler.dateToStr(tuteur.getDatNaisPers()));
             consultationContratCompteForm.setLieuNaisTuteur(tuteur.getLibNaisPers());
             
             
             if (tuteur.getPaysByCodNat1Pays() != null) {
                 //extraire la nationalité
                 GetPaysCmd getPaysCmd = new GetPaysCmd();
                 Pays pays = new Pays();
                 pays.setCodPaysPays(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
                 pays = (Pays)getPaysCmd.execute(pays);                
                 consultationContratCompteForm.setNationaliteTuteur(pays.getLibNatPays());
                 consultationContratCompteForm.setCodNationaliteTuteur(pays.getCodPaysPays());
             }else {
                 logger.error("Pays Tuteur null >> tuteur.getPaysByCodNat1Pays() == null");
             }
             
             consultationContratCompteForm.setPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getLibPaysPays());
             consultationContratCompteForm.setCodPaysNaisTuteur(tuteur.getPaysByCodNat1Pays().getCodPaysPays());
             consultationContratCompteForm.setResidentTuteur(tuteur.getBoolResPers().toString());
             consultationContratCompteForm.setSexeTuteur(tuteur.getCodSexPers());
             
             consultationContratCompteForm.setSectActiviteTuteur(tuteur.getCodSectPers());
             consultationContratCompteForm.setSitFamilialeTuteur(tuteur.getCodSitfPers());
             consultationContratCompteForm.setFormeJuridiqueTuteur(tuteur.getFormeJuridique().getCodFjFj());

             
             if (tuteur.getProfession() != null) {
             // extraire la profession
                 consultationContratCompteForm.setCodGroupProfTuteur(tuteur.getProfession().getProfessionId().getCodGproGpro().toString());
                 consultationContratCompteForm.setCodProfTuteur(tuteur.getProfession().getProfessionId().getCodProfProf().toString());            
                 GetProfessionByIdCmd getProfessionByIdCmd = new GetProfessionByIdCmd();
                 Profession profession = new Profession();
                 ProfessionId professionId = new ProfessionId();
                 professionId.setCodProfProf(Long.valueOf(consultationContratCompteForm.getCodProfTuteur()));
                 professionId.setCodGproGpro(Long.valueOf(consultationContratCompteForm.getCodGroupProfTuteur()));
                 profession.setProfessionId(professionId);
                 profession = (Profession)getProfessionByIdCmd.execute(profession);
                 if (profession != null && profession.getLibProfProf() != null) {
                     consultationContratCompteForm.setProfessionTuteur(profession.getLibProfProf());
                 }
             }else {
                 logger.error("Aucune Profession pour le Tuteur >> tuteur.getProfession() == null");
             }
             
             if (tuteur.getActivite() != null) {
                 // extraire l'activité
                 consultationContratCompteForm.setCodActiviteTuteur(tuteur.getActivite().getActiviteId().getCodActAct().toString());
                 consultationContratCompteForm.setCodClasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodCactCact().toString());
                 consultationContratCompteForm.setCodSclasActiviteTuteur(tuteur.getActivite().getActiviteId().getCodSactSact().toString());

                 GetActiviteByIdCmd getActiviteByIdCmd = new GetActiviteByIdCmd();
                 Activite activite = new Activite();
                 ActiviteId activiteId = new ActiviteId();
                 activiteId.setCodActAct(consultationContratCompteForm.getCodActiviteTuteur());
                 activiteId.setCodCactCact(consultationContratCompteForm.getCodClasActiviteTuteur());
                 activiteId.setCodSactSact(Long.valueOf(consultationContratCompteForm.getCodSclasActiviteTuteur()));
                 activite.setActiviteId(activiteId);
                 activite = (Activite)getActiviteByIdCmd.execute(activite);
                 if (activite != null && activite.getLibActAct() != null) {
                     consultationContratCompteForm.setActiviteTuteur(activite.getLibActAct());
                 }
             }else {
                 logger.error("Aucune Activité pour le Tuteur >> tuteur.getActivite() == null");
             }
             
             // adresse de résidence
             if (tuteur.getAdresseResid() != null) {
                 // Immeuble
                 if (tuteur.getAdresseResid().getImmeuble() != null) {
                     consultationContratCompteForm.setImmeubleAdrResidTuteur(tuteur.getAdresseResid().getImmeuble());
                 }
                 // Rue
                 if (tuteur.getAdresseResid().getRue() != null) {
                     consultationContratCompteForm.setRueAdrResidTuteur(tuteur.getAdresseResid().getRue());
                 }

                 // Cite
                 if (tuteur.getAdresseResid().getCite() != null) {
                     consultationContratCompteForm.setCiteAdrResidTuteur(tuteur.getAdresseResid().getCite());
                 }
                 // Pays 
                 if (tuteur.getAdresseResid().getCodPaysPays() != null) {
                     consultationContratCompteForm.setCodPayAdrResidTuteur(tuteur.getAdresseResid().getCodPaysPays());
                     GetPaysCmd getPaysCmd = new GetPaysCmd();
                     Pays pays = new Pays();
                     pays.setCodPaysPays(tuteur.getAdresseResid().getCodPaysPays());
                     pays = (Pays)getPaysCmd.execute(pays);
                     if (pays.getLibPaysPays() != null) {
                         consultationContratCompteForm.setPaysAdrResidTuteur(pays.getLibPaysPays());
                     }
                 }
                 // Code Postal
                 if (tuteur.getAdresseResid().getCodCpCp() != null) {
                     consultationContratCompteForm.setCodePostalAdrResidTuteur(tuteur.getAdresseResid().getCodCpCp());
                     // si le pays est la tunisie extraire le libelle du code postal
                     if ((tuteur.getAdresseResid().getCodPaysPays() != null) && 
                         (tuteur.getAdresseResid().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                         GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                         CodePostal codePostal = new CodePostal();
                         codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseResid().getCodCpCp()));
                         codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                         consultationContratCompteForm.setLibPostalAdrResidTuteur(codePostal.getLibCpCp());

                         // gouvernerat
                         consultationContratCompteForm.setCodGouvGouvResTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                         consultationContratCompteForm.setLibGouvGouvResTuteur(codePostal.getGouvernorat().getLibGouvGouv());
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
                      consultationContratCompteForm.setImmeubleAdrProfTuteur(tuteur.getAdresseProf().getImmeuble());
                  }
                  // Rue
                  if (tuteur.getAdresseProf().getRue() != null) {
                      consultationContratCompteForm.setRueAdrProfTuteur(tuteur.getAdresseProf().getRue());
                  }
                  // Cite
                  if (tuteur.getAdresseProf().getCite() != null) {
                      consultationContratCompteForm.setCiteAdrProfTuteur(tuteur.getAdresseProf().getCite());
                  }

                  // Pays 
                  if (tuteur.getAdresseProf().getCodPaysPays() != null) {
                      consultationContratCompteForm.setCodPayAdrProfTuteur(tuteur.getAdresseProf().getCodPaysPays());
                      GetPaysCmd getPaysCmd = new GetPaysCmd();
                      Pays pays = new Pays();
                      pays.setCodPaysPays(tuteur.getAdresseProf().getCodPaysPays());
                      pays = (Pays)getPaysCmd.execute(pays);
                      if (pays.getLibPaysPays() != null) {
                          consultationContratCompteForm.setPaysAdrProfTuteur(pays.getLibPaysPays());
                      }
                  }
                  // Code Postal
                  if (tuteur.getAdresseProf().getCodCpCp() != null) {
                      consultationContratCompteForm.setCodePostalAdrProfTuteur(tuteur.getAdresseProf().getCodCpCp());
                      // si le pays est la tunisie extraire le libelle du code postal
                      if ((tuteur.getAdresseProf().getCodPaysPays() != null) && 
                          (tuteur.getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                          GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                          CodePostal codePostal = new CodePostal();
                          codePostal.setCodCpCp(Long.valueOf(tuteur.getAdresseProf().getCodCpCp()));
                          codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                          consultationContratCompteForm.setLibPostalAdrProfTuteur(codePostal.getLibCpCp());

                          // gouvernerat
                          consultationContratCompteForm.setCodGouvGouvProfTuteur(codePostal.getGouvernorat().getCodGouvGouv().toString());
                          consultationContratCompteForm.setLibGouvGouvProfTuteur(codePostal.getGouvernorat().getLibGouvGouv());
                      }
                  }
              } // Fin adresse professionnelle
               else {
                                logger.error("Aucune adresse professionnelle pour le Tuteur >> tuteur.getAdresseProf() == null");
                            }
              
           

    }

    public void affecterDonnesPersonneMorale(ActionForm form, Client client) throws Exception{


       
            ConsultationContratCompteForm consultationContratCompteForm = 
                (ConsultationContratCompteForm)form;

            consultationContratCompteForm.setTypePersonneMoral(client.getTypePers().getLibTperTper());
            consultationContratCompteForm.setCategoriePersonneMoral(client.getPersonne().getCategoriePersonne().getLibCatpCatp());

            consultationContratCompteForm.setTypePieceMoral(client.getPersonne().getTypePiece().getLibSiglTpce());
            consultationContratCompteForm.setDateDelivMoral(DateHandler.dateToStr(client.getPersonne().getDatDlvPers()));
            consultationContratCompteForm.setNumPieceMoral(client.getPersonne().getNumPcePers());

            if (client.getPersonne().getGouvernorat() != null) {
                GetGouvernoratTrt getGouvernoratTrt = new GetGouvernoratTrt();
                Gouvernorat gouvernorat = new Gouvernorat();
                gouvernorat.setCodGouvGouv(client.getPersonne().getGouvernorat().getCodGouvGouv());
                gouvernorat = (Gouvernorat)getGouvernoratTrt.exec(gouvernorat);

                consultationContratCompteForm.setLieuDelivMoral(gouvernorat.getLibGouvGouv().toString());
                consultationContratCompteForm.setCodLieuDelivMoral(gouvernorat.getCodGouvGouv().toString());
                consultationContratCompteForm.setTypeDelivMoral("G");
            } 
            
            consultationContratCompteForm.setRaisonSocialMoral(client.getPersonne().getNomRsPers());
            consultationContratCompteForm.setSigleMoral(client.getPersonne().getLibSiglPers());

            
             if (client.getPersonne().getPaysByCodNat1Pays() != null) {
                 //extraire la nationalité
                 GetPaysCmd getPaysCmd = new GetPaysCmd();
                 Pays pays = new Pays();
                 pays.setCodPaysPays(client.getPersonne().getPaysByCodNat1Pays().getCodPaysPays());
                 pays = (Pays)getPaysCmd.execute(pays);                
                 consultationContratCompteForm.setNationaliteMoral(pays.getLibNatPays());
                //consultationContratCompteForm.setCodNationaliteMoral(pays.getCodPaysPays());
             }
            consultationContratCompteForm.setResidenceMoral(client.getPersonne().getBoolResPers().toString());
            consultationContratCompteForm.setSecteurActMoral(client.getPersonne().getCodSectPers());

            if (client.getPersonne().getActivite() != null) {
                consultationContratCompteForm.setActiviteMoral(client.getPersonne().getActivite().getLibActAct());
            }            
            
            // adresse professionnelle
            if (client.getPersonne().getAdresseProf() != null) {
                // Immeuble
                if (client.getPersonne().getAdresseProf().getImmeuble() != null) {
                    consultationContratCompteForm.setImmeubleAdrResidMoral(client.getPersonne().getAdresseProf().getImmeuble());
                }
                // Rue
                if (client.getPersonne().getAdresseProf().getRue() != null) {
                    consultationContratCompteForm.setRueAdrResidMoral(client.getPersonne().getAdresseProf().getRue());
                }

                // Cite
                if (client.getPersonne().getAdresseProf().getCite() != null) {
                    consultationContratCompteForm.setCiteAdrResidMoral(client.getPersonne().getAdresseProf().getCite());
                }

                // Pays 
                if (client.getPersonne().getAdresseProf().getCodPaysPays() != null) {
                    consultationContratCompteForm.setCodPayAdrResidMoral(client.getPersonne().getAdresseProf().getCodPaysPays());
                    GetPaysCmd getPaysCmd = new GetPaysCmd();
                    Pays pays = new Pays();
                    pays.setCodPaysPays(client.getPersonne().getAdresseProf().getCodPaysPays());
                    pays = (Pays)getPaysCmd.execute(pays);
                    if (pays.getLibPaysPays() != null) {
                        consultationContratCompteForm.setPaysAdrResidMoral(pays.getLibPaysPays());
                    }
                }
                // Code Postal
                if (client.getPersonne().getAdresseProf().getCodCpCp() != null) {
                    consultationContratCompteForm.setCodePostalAdrResidMoral(client.getPersonne().getAdresseProf().getCodCpCp());
                    // si le pays est la tunisie extraire le libelle du code postal
                    if ((client.getPersonne().getAdresseProf().getCodPaysPays() != null) && 
                        (client.getPersonne().getAdresseProf().getCodPaysPays().equals(Constants.COD_PAYS_TUNISIE))) {
                        GetCodePostalCmd getCodePostalCmd = new GetCodePostalCmd();
                        CodePostal codePostal = new CodePostal();
                        codePostal.setCodCpCp(Long.valueOf(client.getPersonne().getAdresseProf().getCodCpCp()));
                        codePostal = (CodePostal)getCodePostalCmd.execute(codePostal);
                        consultationContratCompteForm.setLibPostalAdrResidMoral(codePostal.getLibCpCp());

                        // gouvernerat
                        consultationContratCompteForm.setCodGouvGouvMoral(codePostal.getGouvernorat().getCodGouvGouv().toString());
                        consultationContratCompteForm.setLibGouvGouvMoral(codePostal.getGouvernorat().getLibGouvGouv());
                    }
                }
            } // Fin adresse professionnelle            
            
            //Informations des dates  
            consultationContratCompteForm.setDateCreationMoral(DateHandler.dateToStr(client.getPersonne().getDatPmePers()));
            consultationContratCompteForm.setDateActMoral(DateHandler.dateToStr(client.getPersonne().getDateExpPers()));
            consultationContratCompteForm.setNumLoiCreMoral(client.getPersonne().getNumLpmePers());
            consultationContratCompteForm.setNumJortMoral(client.getPersonne().getNumJortPers());
            consultationContratCompteForm.setDatJortMoral(DateHandler.dateToStr(client.getPersonne().getDatJortPers()));
            consultationContratCompteForm.setNumDecretMoral(client.getPersonne().getNumDecrPers());
            consultationContratCompteForm.setDatDecretMoral(DateHandler.dateToStr(client.getPersonne().getDatDecrPers()));
            consultationContratCompteForm.setNumTelMoral(client.getPersonne().getNumTelPers());
            consultationContratCompteForm.setNumFaxMoral(client.getPersonne().getNumFaxPers());
            consultationContratCompteForm.setAdrMailMoral(client.getPersonne().getAdrMailPers());
            consultationContratCompteForm.setAdrWebMoral(client.getPersonne().getAdrWebPers());
            consultationContratCompteForm.setAdrSwiftMoral((client.getPersonne().getAdrSwiftPers()));
            consultationContratCompteForm.setAdrTelexMoral(client.getPersonne().getAdrTlxPers());

            

    }

    public void affecterDonnesPersonneCotitulaire(ActionForm form, 
                                                  Personne personne) throws Exception{

       

            ConsultationContratCompteForm consultationContratCompteForm = 
                (ConsultationContratCompteForm)form;


            Listes listMembresCotit = new Listes();
            GetListMembreCotitulaireCmd getListMembreCotitulaireCmd = 
                new GetListMembreCotitulaireCmd();
            PersonneStrc personneStrc = new PersonneStrc(); //Vo input
            personneStrc.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce());
            personneStrc.setNumPcePers(personne.getNumPcePers());
            personneStrc.setCodStrcStrc(new Long(consultationContratCompteForm.getCodeAgance()));
            listMembresCotit = 
                    (Listes)getListMembreCotitulaireCmd.execute(personneStrc);

            if (listMembresCotit.getList() != null && 
                listMembresCotit.getList().size() > 0) {
                CoTitulaire cotitulaire = 
                    (CoTitulaire)listMembresCotit.getList().get(0);
                consultationContratCompteForm.setTypeCotit(cotitulaire.getCodTcotCoti());
                consultationContratCompteForm.setTypeSignature(cotitulaire.getCodSigCoti());
                consultationContratCompteForm.setListeMembreEntiteCotit(listMembresCotit.getList());
                consultationContratCompteForm.setOpenTabsheetCotitulaire("true");
            }

      

    }
    
    public ActionForward choisirContrat(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        ActionMessages actionMessages = new ActionMessages();
     try{   
        GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
        ContratCpt contratChoisi = new ContratCpt();
        String vContratChoisi = 
            consultationContratCompteForm.getCleContratChoisi();
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
            affecterDonnesContrat(consultationContratCompteForm, contratChoisi);
            consultationContratCompteForm.setContratCpt(contratChoisi);
            consultationContratCompteForm.setOpenTabsheetContrat("true");
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
            logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
    //        logger.error("Exception : ",e); 
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }    
    }

    
    public ActionForward validerNotification(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        ConsultationContratCompteForm consultationContratCompteForm = 
            (ConsultationContratCompteForm)form;
        UpdateContratCptTrt updateContratTrt = new UpdateContratCptTrt();
        ActionMessages actionMessages = new ActionMessages();
     try{   
        GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
        ContratCpt contratChoisi = new ContratCpt();
        String vContratChoisi = 
            consultationContratCompteForm.getCleContratChoisi();
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
            contratChoisi.setDatNotfCcpt(DateHandler.strToDate(consultationContratCompteForm.getDateNotification()));
            // mettre le fonctionnement du compte à debit / credit
            contratChoisi.setCodFoncCpt(String.valueOf("2"));
            ContratCpt cpt = (ContratCpt)updateContratTrt.exec(contratChoisi);
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
             
        return mapping.findForward("notification");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans validationContratCompteAction / Dispatch Action :choisirContrat ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
     //       logger.error("Exception : ",e); 
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
        ConsultationContratCompteForm consultationContratCompteForm = 
               (ConsultationContratCompteForm)form; 
           ContratCptId contratCptid= consultationContratCompteForm.getContratCpt().getContratCptId();
           
           StringBuffer text = 
               new StringBuffer("L'impression du contrat compte a été interrompue l'hors de la consultation de celui-ci, veuillez transmettre ce message à l'équipe informatique: ");
           text.append("Agence:"); text.append(consultationContratCompteForm.getCodStrcRech());
           
        try {
             String typePersonne = consultationContratCompteForm.getContratCpt().getClient().getTypePers().getCodTperTper();
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
                 
                 //------------------paramètres cod postal 
     //        parameters.put("ADR_VIL_CCPT",consultationContratCompteForm.getContratCpt().getAdresseCorresp().getVille());
             parameters.put("ADR_CP_CCPT", consultationContratCompteForm.getContratCpt().getAdresseCorresp().getCodCpCp());
             //-------------------------------------paramètre RIB
                   GetRibCmd getRibCmd = new GetRibCmd();
                   IValueObject voCcpt = (IValueObject)consultationContratCompteForm.getContratCpt();
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
                          
                            parameters.put("pLibNCatCat",consultationContratCompteForm.getCodeCategorieEpargne());
                            parameters.put("pLibNRgmRgm",consultationContratCompteForm.getCodeRegimeEpargne());
                            if(!consultationContratCompteForm.getMntVersementEpargne().equals("")) {
                                  Long vMontVersMens= new Long(consultationContratCompteForm.getMntVersementEpargne());
                                  parameters.put("pMontVersMens",vMontVersMens);
                                }else {
                                    text.append(". Le Montant des versements pour ce compte épargne est vide, vérifier le détail de la catégorie du contrat");
                                }
                            
                            Long vMnt2Capt=(new Long(consultationContratCompteForm.getMntCapitaliseEpargne())*2);
                            parameters.put("pMnt2Capt",vMnt2Capt);
                            Long vMnt3Capt=(new Long(consultationContratCompteForm.getMntCapitaliseEpargne())*3);
                            parameters.put("pMnt3Capt",vMnt3Capt); 
                            if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEL)){
                            vLibEtat = "Souscription Contrat Compte Epargne Logement 'Malek' ";
                            }else{
                                vLibEtat = "Souscription Contrat Compte Epargne Ménage 'Farah' ";
                            }
                            valueObject.setNomReport("ContratCptEpgn");
                            request.setAttribute("print","1");
                             
                         }else if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_PEE)){
                         
                         // Impression pour CompteEpargne Etudes 'FAIEZ'
                             vLibEtat = "Souscription Contrat Compte Epargne Etudes 'FAIEZ'";
                             valueObject.setNomReport("ContratCpt_PEE");
                             request.setAttribute("print","1");
                             parameters.put("COD_PAYS_PAYS", consultationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays());
                             parameters.put("P_NOM_TUTEUR",
                                            consultationContratCompteForm.getNomPersTuteur()+" "+consultationContratCompteForm.getPrenomPersTuteur());
                             parameters.put("NUM_PCE_TUT", consultationContratCompteForm.getNumPieceTut());
                             parameters.put("LIB_SIGL_TPCE",consultationContratCompteForm.getTypePieceTut());
                             parameters.put("LIB_PROFESSION", consultationContratCompteForm.getProfessionTuteur());
                           
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
                     
                             vCodPaysPays   = consultationContratCompteForm.getContratCpt().getAdresseCorresp().getCodPaysPays();
                             parameters.put(pCodPaysPays, vCodPaysPays);
                             
                        if(consultationContratCompteForm.getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)){
                             
                                //---------------------------------------------------------------IMPRESSION Personne mineur
                                 String pNomTuteur  = "P_NOM_TUTEUR";
                               
                                 String pNumPceTut   = "NUM_PCE_TUT";
                                 String pLibTpceTut  = "LIB_SIGL_TPCE";
                                 String vNomTuteur  = "";
                                String vNumPceTut   = "";
                                 String vLibTpceTut  = "";
                                 String pLibProfession  = "LIB_PROFESSION";
                                 String vLibProfession  = "";
                             
                                vNomTuteur  = consultationContratCompteForm.getNomPersTuteur()+" "+consultationContratCompteForm.getPrenomPersTuteur();
                               
                                vNumPceTut   = consultationContratCompteForm.getNumPieceTut();
                                vLibTpceTut  = consultationContratCompteForm.getTypePieceTut();
                                vLibProfession  = consultationContratCompteForm.getProfessionTuteur();
                                                           
                                parameters.put(pNomTuteur, vNomTuteur);
                                parameters.put(pNumPceTut, vNumPceTut);
                                parameters.put(pLibTpceTut, vLibTpceTut);
                                parameters.put(pLibProfession, vLibProfession);
                                if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_EPS)){
                                        valueObject.setNomReport("ContratCpt_PMin121"); 
                                    }else {
                                            valueObject.setNomReport("ContratCpt_PMin");  }
                           
                            }else{
                            
                                    if((consultationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==9)
                                       ||(    (consultationContratCompteForm.getContratCpt().getClient().getPersonne().getTypePiece().getCodTpceTpce()==11) 
                                              && (consultationContratCompteForm.getContratCpt().getClient().getTypePers().getCodTperTper().equals("2")))
                                        ){
                                        String vLibActivite   = "";
                                        String vMatrFisc   = "";
                                        
                                        vCodTpceTpce   = consultationContratCompteForm.getTypePieceMoral();
                                        vLibActivite   = consultationContratCompteForm.getActiviteMoral();
                                        vMatrFisc = consultationContratCompteForm.getContratCpt().getClient().getNumFiscClt();
                                        
                                        parameters.put(pCodTpceTpce, vCodTpceTpce);
                                        parameters.put(pLibActivite, vLibActivite);
                                        parameters.put(pMatrFisc, vMatrFisc);
                                        valueObject.setNomReport("ContratCpt_PersMoral");
                                         }else{
                                                if(contratCptid.getCodPrdPrd().equals(Constants.COD_PRD_PRD_EPS)){
                                                        valueObject.setNomReport("ContratCpt121"); 
                                                    }else {
                                                        if(typePersonne.equals("3")){ // entité cotitualire
                                                            valueObject.setNomReport("ContratCptCOT"); 
                                                            }else {
                                                                valueObject.setNomReport("ContratCpt");
                                                              }
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
                 return mapping.findForward("success");
         }
           catch (Exception e) {
                           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                           text.append(". Exception :"); text.append(e.toString());
                           text.append(e.toString());
                           erreur.setCode("200");
                           erreur.setDescription(text.toString());
                           logger.error("Erreur au niveau de l'agence <<" +consultationContratCompteForm.getCodStrcRech()+ ">>. Exception : ",e);  
                           ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                           ActionMessages actionMessages = new ActionMessages();
                           actionMessages.add("Erreur ", actionMessage);
                           this.saveMessages(request, actionMessages);
                           return mapping.findForward("error");
                       }
                   
       } 

}

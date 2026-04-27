package com.bna.smile.web.commun.actions;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ExonerationCltTva;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceExoTva;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetListExonerationTvaCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.commande.UpdateExonerationTvaCmd;
import com.bna.smile.model.domainecommun.commande.CreatExoneartionTvaCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.ParamExonerationCltTva;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneByNumSeqPersTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ExonerationTvaForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.commun.view.ExonerationCltTvaView;

import com.bna.smile.web.commun.view.InitialisationView;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Collection;

import java.util.Iterator;
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

/**
 * @author lamia
 * @since 20/11/2008
 */
public class ExonerationTvaAction extends DispatchAction{
    public ExonerationTvaAction() {
    }
    private static final Logger logger = Logger.getLogger(ExonerationTvaAction.class);
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
       StringBuffer text = 
           new StringBuffer("L'initialisation de la prise en charge de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
       ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
       SessionUtil sessionUtil =new SessionUtil();
       String forward = new String();   
       StructureDomaine structureDomaine = new StructureDomaine();
       
   
       try{
           //Suppression des anciens Bean de type Form de la session, SAUF "exenorationTvaForm"
           sessionUtil.removeSession(request,"exonerationTvaForm");                                                                
           //---affectation du parametre de session code agence, matricule personnel et date du jour
           ParamAgence paramAgence = 
                  (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
           
           structureDomaine.setCodDomDomm(Constants.COD_DOM_CLIENT);
           structureDomaine.setCodStrcStrc(paramAgence.getCodStrcStrc()); 
           exonerationTvaForm.clearForm();
           if(paramAgence != null){
           exonerationTvaForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
           exonerationTvaForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
           exonerationTvaForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
           }
           if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("CONSULT")) {
                        exonerationTvaForm.getInitialisationView().setLibelleOperation("Consultation Exonération TVA");
                        exonerationTvaForm.getParamConsult().setChoix("0");
                        exonerationTvaForm.getParamConsult().setDateDebutconsult(paramAgence.getDateComptable());
                        exonerationTvaForm.getParamConsult().setDateFinconsult(paramAgence.getDateComptable());
                        forward = "pageRecherche";
                     }else {
           
                           if (SmileUtil.testDomaineOuvert(structureDomaine)){
                               
                               if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("PEC")) {
                                   exonerationTvaForm.getInitialisationView().setLibelleOperation("Prise En Charge Exonération TVA");
                                   exonerationTvaForm.getExonerationCltTvaView().setNumTauxEtva("0");
                                   exonerationTvaForm.getExonerationCltTvaView().setDatCreEtva(paramAgence.getDateComptable());
                                         Structure struct = new Structure();
                                         struct.setCodStrcStrc(paramAgence.getCodStrcStrc());
                                         exonerationTvaForm.getExonerationCltTva().setStructure(struct);
                                   forward = "initierPage";
                                     }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("VALID")) {
                                             exonerationTvaForm.getInitialisationView().setLibelleOperation("Validation Exonération TVA");
                                             exonerationTvaForm.getParamConsult().setDateDebutconsult(paramAgence.getDateComptable());
                                             exonerationTvaForm.getParamConsult().setDateFinconsult(paramAgence.getDateComptable());
                                             exonerationTvaForm.getParamConsult().setChoix("0");
                                             forward = "pageRecherche";
                                            }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("MODIF")) {
                                                        exonerationTvaForm.getInitialisationView().setLibelleOperation("Modification Exonération TVA");
                                                        exonerationTvaForm.getParamConsult().setChoix("0");
                                                        exonerationTvaForm.getParamConsult().setDateDebutconsult(paramAgence.getDateComptable());
                                                        exonerationTvaForm.getParamConsult().setDateFinconsult(paramAgence.getDateComptable());
                                                        forward = "pageRecherche";
                                                    }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("ANNUL")) {
                                                              exonerationTvaForm.getInitialisationView().setLibelleOperation("Annulation Exonération TVA");
                                                              exonerationTvaForm.getParamConsult().setChoix("0");
                                                              exonerationTvaForm.getParamConsult().setDateDebutconsult(paramAgence.getDateComptable());
                                                              exonerationTvaForm.getParamConsult().setDateFinconsult(paramAgence.getDateComptable());
                                                              forward = "pageRecherche";
                                                          }
                                   }else{
                                              ActionMessages actionMessages = new ActionMessages();
                                              List listErreur = structureDomaine.getErrors();                    
                                              for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                                                  com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                                                  ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                                                  actionMessages.add("Erreur ", actionMessage);
                                              }    
                                              this.saveMessages(request, actionMessages);
                                              return mapping.findForward("error");
                        
                                          } 
                     }

       } catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date()); text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
     
       return mapping.findForward(forward);                                                                 
   }  
   
   public ActionForward  initierPageRecherche (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
                                                                         
        StringBuffer text = 
             new StringBuffer("L'initialisation de la recherche de la liste des éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;   
       
       try {
           exonerationTvaForm.clearListeExoneration(); 
           exonerationTvaForm.getParamConsult().setChoix("0");
           exonerationTvaForm.getParamConsult().setDateDebutconsult(exonerationTvaForm.getInitialisationView().getDateComptable());
           exonerationTvaForm.getParamConsult().setDateFinconsult(exonerationTvaForm.getInitialisationView().getDateComptable());
           
           if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("VALID")) {
                  exonerationTvaForm.getInitialisationView().setLibelleOperation("Validation Exonération TVA");
                        }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("MODIF")) {
                                    exonerationTvaForm.getInitialisationView().setLibelleOperation("Modification Exonération TVA");
                                 }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("ANNUL")) {
                                          exonerationTvaForm.getInitialisationView().setLibelleOperation("Annulation Exonération TVA");
                                     }else  if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("CONSULT")) {
                                                  exonerationTvaForm.getInitialisationView().setLibelleOperation("Consultation Exonération TVA");
                                                }else {
                                                     text.append("Code opération null");  }
                                                                        
        return mapping.findForward("pageRecherche");       
       }catch (Exception e) {
           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date()); text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
    }
    public ActionForward annulerOperation(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
       StringBuffer text = 
           new StringBuffer("L'annulation de l'opération sur Exénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
       ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
       String forward = new String();     
       try{

       ParamAgence paramAgence = 
              (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
       exonerationTvaForm.clear();
       
       if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("PEC")) {
           exonerationTvaForm.getParamConsult().clear();
           exonerationTvaForm.getExonerationCltTvaView().setNumTauxEtva("0");
           exonerationTvaForm.getExonerationCltTvaView().setDatCreEtva(paramAgence.getDateJours());
                 Structure struct = new Structure();
                 struct.setCodStrcStrc(paramAgence.getCodStrcStrc());
                 exonerationTvaForm.getExonerationCltTva().setStructure(struct);
           forward = "initierPage";
             }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("VALID")) {
                   forward = "pageRecherche";
                    }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("MODIF")) {
                            forward = "pageRecherche";
                            }else if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("ANNUL")) {
                                 forward = "pageRecherche";
                                  }else  if (exonerationTvaForm.getInitialisationView().getCodeOperation().equals("CONSULT")) {
                                         forward = "pageRecherche";
                                          }else {
                                                 text.append("Code opération null");  }
                 
       } catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date());  text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
       return mapping.findForward(forward);                                                                 
    }                                                                     
                                                                         
    public ActionForward rechercherClient(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
       StringBuffer text = 
            new StringBuffer("La recherche du client l'hors de la prise en charge de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
       ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
       exonerationTvaForm.clearNomPrenomForm();
       Listes l = new Listes();
       ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition();
       GetListExonerationTvaCmd getListExonerationTvaCmd= new GetListExonerationTvaCmd();
        try{
            PersonneStrc personneStrc = new PersonneStrc();
            PersonneCpt personneCpt = new PersonneCpt();
         //   personneStrc.setCodStrcStrc(Long.valueOf(exonerationTvaForm.getInitialisationView().getCodeAgence()));
            
            if(exonerationTvaForm.getParamConsult() != null){
                personneStrc.setCodTpceTpce(Long.valueOf(exonerationTvaForm.getParamConsult().getTypPcePers()));
                paramRecherche.setTypPceDemd(Long.valueOf(exonerationTvaForm.getParamConsult().getTypPcePers()));
                
                if (exonerationTvaForm.getParamConsult().getTypPcePers().equals(Constants.COD_CIN)) {
                    personneStrc.setNumPcePers(StrHandler.lpad(exonerationTvaForm.getParamConsult().getNumPcePers(), '0', 8));
                    paramRecherche.setNumPceDemd(StrHandler.lpad(exonerationTvaForm.getParamConsult().getNumPcePers(),'0',8));
                     }else {
                              personneStrc.setNumPcePers(exonerationTvaForm.getParamConsult().getNumPcePers());
                              paramRecherche.setNumPceDemd(exonerationTvaForm.getParamConsult().getNumPcePers());
                              }
            }else {
                     logger.error(" l'objet paramConsult == null ");
                 }
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            
            if (personneCpt.getPersonne() != null) {
                    exonerationTvaForm.getExonerationCltTva().setClient(personneCpt.getClient());
                    if (personneCpt.getPersonne().getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { /* cas RCS affichage de libSiglPers */
                        exonerationTvaForm.setNomNomPers(personneCpt.getPersonne().getLibSiglPers());
                        exonerationTvaForm.setNomPrnPers(personneCpt.getPersonne().getNomRsPers());
                         } else {
                                exonerationTvaForm.setNomNomPers(personneCpt.getPersonne().getNomNomPers());
                                exonerationTvaForm.setNomPrnPers(personneCpt.getPersonne().getNomPrnPers());
                            }
                exonerationTvaForm.setMatriculeFiscal(personneCpt.getClient().getNumFiscClt());
                exonerationTvaForm.setPersonneExist(true);
                exonerationTvaForm.setListContratCpt(personneCpt.getListeContratCpt());
                }else{
                    exonerationTvaForm.setPersonneExist(false);
                    logger.info("Personne inexistante");
                }
                
            exonerationTvaForm.setListeExonerationTva(null);
                       
            l = (Listes)getListExonerationTvaCmd.execute(paramRecherche);
            if(l != null && l.getList() != null ) {
                 if(l.getList().size()!=0){
                  exonerationTvaForm.setListeExonerationTva(l.getList());
                        Collection<ExonerationCltTva> listExoTva = l.getList();
                        for(ExonerationCltTva exoCltTva : listExoTva ){
                            exonerationTvaForm.setEtatExonerationExist(exoCltTva.getCodEtatEtva());
                        }
                 }
                }else {
                    logger.debug("liste retourné vide");
                }
          
        }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date());  text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
       return mapping.findForward("initierPage");
 }
 
    public ActionForward priseEnChargeExonerationTva(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        StringBuffer text = 
            new StringBuffer("La prise en charge de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
        ExonerationCltTva exonerationCltTva = new ExonerationCltTva();
        ParamExonerationCltTva paramExoCltTva = new ParamExonerationCltTva();
        try{
          exonerationCltTva = exonerationTvaForm.getExonerationCltTva();
          exonerationCltTva.setCodEtatEtva(Constants.COD_ETAT_ETVA_ATTENTE);
          exonerationCltTva.setNumTauxEtva(Double.valueOf(0));
          exonerationCltTva.setDatDebEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatDebEtva()));
          exonerationCltTva.setDatFinEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatFinEtva()));
          exonerationCltTva.setDatCreEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatCreEtva()));
          CreatExoneartionTvaCmd creatExoneartionTvaCmd= new CreatExoneartionTvaCmd();
          paramExoCltTva.setExonerationCltTva(exonerationCltTva);
          paramExoCltTva.setTraceExoTva(gererTraceOperation(exonerationCltTva.getDatCreEtva(), exonerationCltTva, Constants.COD_TACH_PEC_ETVA ,Constants.COD_OPER_CRE_ETVA,exonerationTvaForm.getInitialisationView()) );
          
          ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)creatExoneartionTvaCmd.execute(paramExoCltTva);
          if(paramExonerationCltTva.getExonerationCltTva().getCodEtatEtva().equals(Constants.COD_ETAT_ETVA_ATTENTE)){
                 imprimerExonerationCltTva(paramExonerationCltTva.getExonerationCltTva(),exonerationTvaForm,request);
             }
          if(!paramExonerationCltTva.hasError()){
              remplirLibelleConfirmation(exonerationTvaForm);
          }else{
                  text.append("Erreur dans l'objet de retour du traitement 'exoCltTva'");
                  text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
                  text.append(". Exception :"); text.append(paramExonerationCltTva.getErrorMessage());
                  logger.error(text.toString());
                  com.oxia.fwk.core.Error erreur = paramExonerationCltTva.getErrors().get(0);
                  ActionMessages actionMessages = new ActionMessages();
                  ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                  actionMessages.add("Erreur ", actionMessage);
                  this.saveMessages(request, actionMessages);
              return mapping.findForward("error");
              }
         }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date()); text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");
       }    
       return mapping.findForward("pageConfirmation");
    }
    
    public void imprimerExonerationCltTva(ExonerationCltTva exonerationCltTva,ActionForm form, HttpServletRequest request ){
           
      try{
      
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
        CommonReportVO valueObject = new CommonReportVO();
        Map parameters = new HashMap();
        String libParametre;
        String valParametre;
        String pLibEtat="P_LIB_ETAT";
        
        StringBuffer txtLibEtat =     new StringBuffer("Suspension TVA ");
        StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
        txtNomFichJasper.append("ExonerationTVA");
        txtNomFichJasper.append(File.separatorChar);
        if(exonerationCltTva != null ){
                if(exonerationCltTva.getNumSeqEtva() != null){
                    libParametre = "P_NUM_SEQ_ETVA";
                    valParametre = exonerationCltTva.getNumSeqEtva().toString();
                    parameters.put(libParametre,valParametre);
                }
            if(!exonerationCltTva.getCodEtatEtva().equalsIgnoreCase("")){
                if(exonerationCltTva.getCodEtatEtva().equals("A")){
                    txtLibEtat.append("(En attente de validation)");
                }else if(exonerationCltTva.getCodEtatEtva().equals("V")){
                            txtLibEtat.append("(Validée)");
                        }else {
                            txtLibEtat.append("---Etat non adéquat---");
                        }
                      
            }
        }
        if(exonerationTvaForm.getInitialisationView() != null ){
        // Ajout du parametre matricule utilisateur
         libParametre= "P_NUM_MATR_USER";
         valParametre = exonerationTvaForm.getInitialisationView().getNumMatrUser();
         parameters.put(libParametre,valParametre);
        }
        String vLibEtat;
        vLibEtat = txtLibEtat.toString();
        parameters.put(pLibEtat,vLibEtat);
        
        valueObject.setParams(parameters);
        parameters = null;
        // indiquer le nom du fichier jasper  
         txtNomFichJasper.append("pecExonerationTva");
         valueObject.setNomReport(txtNomFichJasper.toString());  
        request.getSession().setAttribute("CommonPrintVo",valueObject);
        request.setAttribute("print","1");
    } catch (Exception e) {
       throw new RuntimeException(e);  
    } 
    
    }
      
 public TraceExoTva gererTraceOperation(Date dateOperation, ExonerationCltTva exonerationCltTva, Long codTach , Long codOper, InitialisationView initialisationView){
   
   try{
     TraceExoTva traceExoTva = new TraceExoTva();
     TacheId tacheId = new TacheId();
     Tache tache = new Tache();
     Personnel personnel = new Personnel();
     Structure struct = new Structure();
   
     struct.setCodStrcStrc(Long.valueOf(initialisationView.getCodeAgence()));
     personnel.setNumMatrUser(initialisationView.getNumMatrUser());
     // ajout de la structure pour gérer le domaine
     personnel.setStructure(struct);
     traceExoTva.setPersonnel(personnel);
     
     tacheId.setCodOperOper(codOper);
     tacheId.setCodTachTach(codTach);
     tache.setTacheId(tacheId);
     traceExoTva.setTache(tache);
         
     traceExoTva.setExonerationCltTva(exonerationCltTva);
     traceExoTva.setDatOperTret(new Date());
     traceExoTva.setDatCompTret(dateOperation);
     return traceExoTva; 
     
     } catch (Exception e) {
        throw new RuntimeException(e);  
     } 
     
 }
  public ActionForward rechercherListeExonerationTva(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, 
                                   HttpServletResponse response) throws IOException, 
                                                                        ServletException {
       StringBuffer text = 
           new StringBuffer("La recherche de la liste des éxénorations TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
       ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
       exonerationTvaForm.setListeExonerationTva(null);
       exonerationTvaForm.setListeExonerationTvaView(null);
       Listes l = new Listes();
     
       ExonerationCltTvaView exonerationTvaView = new ExonerationCltTvaView();
      
       ParamRechercheOpposition paramRecherche = new ParamRechercheOpposition();
       GetListExonerationTvaCmd getListExonerationTvaCmd= new GetListExonerationTvaCmd();
       
       try{
         
          if(exonerationTvaForm.getInitialisationView() != null ){
                  if(!exonerationTvaForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DCOMPT)){
                  // l'utilisateur est connecté depuis une agence, la recherche retourne les exonerations de cette agence
                      paramRecherche.setCodStrcStrc(Long.valueOf(exonerationTvaForm.getInitialisationView().getCodeAgence()));
                         }else {
                             // l'utilisateur est connecté depuis la direction de comptabilité pour les opérations modification, annulation et consultation;
                             // la recherche retourne toutes les exonerations de la banque sauf au cas du choix (2): Par agence
                                if(exonerationTvaForm.getParamConsult().getChoix().equals("2")){
                                       paramRecherche.setCodStrcStrc(Long.valueOf(exonerationTvaForm.getParamConsult().getCodStrcStrc()));
                                   }
                          }
           
              }else {
                    logger.debug(" exonerationTvaForm.getInitialisationView() == null ");
                  }
                  
        // meme traitement independement de la structure de l utilisateur
        // Pour la validation, rechercher les exo Tva en attente (etat =A ), Pour la modif et l annul, rechercher les exo Tva validé et modifié (test au niveau trt, etat = M ou V)
        // Pour la consultation, rechercher les exo Tva selon l etat choisit par l utilisateur
           if(exonerationTvaForm.getInitialisationView().getCodeOperation().equals("VALID")){
               paramRecherche.setEtat("A");
                }else if(exonerationTvaForm.getInitialisationView().getCodeOperation().equals("MODIF")
                            || exonerationTvaForm.getInitialisationView().getCodeOperation().equals("ANNUL")){
                            paramRecherche.setEtat("MA");
                      }else if(exonerationTvaForm.getInitialisationView().getCodeOperation().equals("CONSULT")){
                            if(!exonerationTvaForm.getParamConsult().getEtat().equals("")){
                                paramRecherche.setEtat(exonerationTvaForm.getParamConsult().getEtat());
                                }else {
                                    logger.info("Aucun Etat n'est choisit");
                                }
                      }
        // recherche selon le choix :: critère de recherche
        
           if(exonerationTvaForm.getParamConsult().getChoix().equals("0")){
               paramRecherche.setTypPceDemd(Long.valueOf(exonerationTvaForm.getParamConsult().getTypPcePers()));
               paramRecherche.setNumPceDemd(exonerationTvaForm.getParamConsult().getNumPcePers());
               }else if(exonerationTvaForm.getParamConsult().getChoix().equals("1")){
                             paramRecherche.setDateDebutConsult(
                                                      DateHandler.strToDate(exonerationTvaForm.getParamConsult().getDateDebutconsult()));
                             paramRecherche.setDateFinConsult(DateHandler.addJour
                                                     (DateHandler.strToDate(exonerationTvaForm.getParamConsult().getDateFinconsult()), 1));
                             }
        // commande qui retourne la liste selon les critères de recherche
           l = (Listes)getListExonerationTvaCmd.execute(paramRecherche);
          
           Collection listExonerationTvaView = new ArrayList();
           // traitement de la liste retourné pour affichage
           
           if((l != null)&& l.getList()!= null){
               if(l.getList().size()!=0){
                   exonerationTvaForm.setListeExonerationTva(l.getList());
                   Collection<ExonerationCltTva> listExoTva = l.getList();
                   for(ExonerationCltTva exonerationCltTva : listExoTva ){
                       if(exonerationCltTva != null){
                          exonerationTvaView = créerExonerationCltTvaView(exonerationCltTva); 
                          exonerationTvaForm.setExonerationCltTvaView(exonerationTvaView);
                            }else {logger.debug("Exoneration tva vide");}
                       if(listExonerationTvaView != null){
                          listExonerationTvaView.add(exonerationTvaView);
                            }else {logger.debug("Exoneration tva view vide");}
                   }
                   if(listExonerationTvaView != null){ 
                     exonerationTvaForm.setListeExonerationTvaView(listExonerationTvaView);
                        }else {logger.debug("listExonerationTvaView vide");}
               }
           }else {
               logger.debug("liste retourné par la commande getListExonerationTvaCmd  est vide");
           }
     
       }catch (Exception e) {

          text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
          text.append(". Date :"); text.append(new Date());  text.append(e.toString());
          ActionMessages actionMessages = new ActionMessages();
          ActionMessage actionMessage = 
              new ActionMessage("exception.generique", 
                                text.toString());
          actionMessages.add("Erreur ", actionMessage);   
          this.saveMessages(request, actionMessages);
          logger.error(text.toString(),e);
          return mapping.findForward("error");

      }    
      return mapping.findForward("pageRecherche");
   }
    public ActionForward afficherExonerationTva(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
         StringBuffer text = 
             new StringBuffer("L' affichage de l'éxénoration TVA choisie a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
         ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
         try{
             exonerationTvaForm.clearNomPrenomForm();
             ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             exonerationTvaForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
              
             if (!exonerationTvaForm.getRefExoTvaChoisi().equals(null) && 
                 !exonerationTvaForm.getRefExoTvaChoisi().equals("")) {
                 Collection<ExonerationCltTva> listExoTva = exonerationTvaForm.getListeExonerationTva();
                 for(ExonerationCltTva exonerationCltTva : listExoTva ){
                     if(exonerationCltTva.getCodRefaEtva().equals(exonerationTvaForm.getRefExoTvaChoisi())){
                         exonerationTvaForm.setExonerationCltTvaView(créerExonerationCltTvaView(exonerationCltTva));
                         exonerationCltTva = remplirDonneesClient(exonerationTvaForm,exonerationCltTva);
                         exonerationTvaForm.setExonerationCltTva(exonerationCltTva);
                      }
                    }
                 }else {
                     logger.debug("Aucune exonération choisie pour etre traitée");
                        }
           
         }catch (Exception e) {

            text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
            text.append(". Date :"); text.append(new Date());  text.append(e.toString());
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  text.toString());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error(text.toString(),e);
            return mapping.findForward("error");

        }    
        return mapping.findForward("initierPage");
     }
    
public ExonerationCltTvaView remplirDonneesPersonne(ExonerationCltTva exonerationCltTva){
        ExonerationCltTvaView exoCltTvaView = new ExonerationCltTvaView();
        GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt(); 
        Personne pers =new Personne();
        pers.setNumSeqPers(exonerationCltTva.getClient().getNumSeqPers());   
        pers = (Personne)getPersonneByNumSeqPersTrt.exec(pers);
        
        if(pers != null){
        exoCltTvaView.setTypePiece(pers.getTypePiece().getLibTpceTpce().toString());
        exoCltTvaView.setNumPiece(pers.getNumPcePers());
        
        if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { /* cas RCS affichage de libSiglPers */
             exoCltTvaView.setNomPersonne(pers.getLibSiglPers());
             exoCltTvaView.setPrenomPersonne(pers.getNomRsPers());
             } else {
                    exoCltTvaView.setNomPersonne(pers.getNomNomPers());
                    exoCltTvaView.setPrenomPersonne(pers.getNomPrnPers());
                }
        }
       return exoCltTvaView;
    }
    
public ExonerationCltTva remplirDonneesClient(ExonerationTvaForm form,ExonerationCltTva exonerationCltTva){
    ExonerationTvaForm exonerationTvaForm =form;
    GetPersonneByNumSeqPersTrt getPersonneByNumSeqPersTrt = new GetPersonneByNumSeqPersTrt(); 
    Personne pers =new Personne();
    pers.setNumSeqPers(exonerationCltTva.getClient().getNumSeqPers());   
    pers = (Personne)getPersonneByNumSeqPersTrt.exec(pers);
    Set<Client> setClt =pers.getClients();
    for(Client clt : setClt){
        exonerationTvaForm.setMatriculeFiscal(clt.getNumFiscClt());
        Set<ContratCpt> setCcpt = clt.getContratCpts();
        Collection listCcpt = new ArrayList();
        for(ContratCpt Ccpt : setCcpt){
            if(Ccpt.getCodEtatCcpt().equals("V")){
                listCcpt.add(Ccpt);
            }
        }
        exonerationTvaForm.setListContratCpt(listCcpt);
        exonerationCltTva.setClient(clt);
         }
    exonerationTvaForm.getParamConsult().setTypPcePers(pers.getTypePiece().getCodTpceTpce().toString());
    exonerationTvaForm.getParamConsult().setNumPcePers(pers.getNumPcePers());;  
    if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { /* cas RCS affichage de libSiglPers */
         exonerationTvaForm.setNomNomPers(pers.getLibSiglPers());
         exonerationTvaForm.setNomPrnPers(pers.getNomRsPers());
         } else {
                exonerationTvaForm.setNomNomPers(pers.getNomNomPers());
                exonerationTvaForm.setNomPrnPers(pers.getNomPrnPers());
            }
  
   return exonerationCltTva;
}

public ExonerationCltTvaView créerExonerationCltTvaView(ExonerationCltTva exonerationCltTva){

    ExonerationCltTvaView exonerationCltTvaView = new ExonerationCltTvaView();
    
    exonerationCltTvaView = remplirDonneesPersonne(exonerationCltTva);
    exonerationCltTvaView.setDatCreEtva(DateHandler.dateToStr(exonerationCltTva.getDatCreEtva()));
    exonerationCltTvaView.setDatFinEtva(DateHandler.dateToStr(exonerationCltTva.getDatFinEtva()));
    exonerationCltTvaView.setNumTauxEtva("0");
    exonerationCltTvaView.setDatDebEtva(DateHandler.dateToStr(exonerationCltTva.getDatDebEtva()));
    exonerationCltTvaView.setDatAnnEtva(DateHandler.dateToStr(exonerationCltTva.getDatAnnEtva()));
    exonerationCltTvaView.setDatValcEtva(DateHandler.dateToStr(exonerationCltTva.getDatValcEtva()));
    exonerationCltTvaView.setDatModEtva(DateHandler.dateToStr(exonerationCltTva.getDatModEtva()));
    exonerationCltTvaView.setCodRefaEtva(exonerationCltTva.getCodRefaEtva());
    exonerationCltTvaView.setCodEtatEtva(exonerationCltTva.getCodEtatEtva());
    return exonerationCltTvaView;
    }
public ParamExonerationCltTva mettreAjourExonerationCltTva(ExonerationCltTva exonerationCltTva,Date dateOperation,Long codTach , Long codOper, InitialisationView initialisationView ){
    
    ParamExonerationCltTva paramExonerationCltTva = new ParamExonerationCltTva();
    paramExonerationCltTva.setTraceExoTva(gererTraceOperation(dateOperation, exonerationCltTva,codTach ,codOper,initialisationView ));
    paramExonerationCltTva.setExonerationCltTva(exonerationCltTva);
    paramExonerationCltTva.setCodeOperation(codOper.toString());
    paramExonerationCltTva.setCodeTache(codTach.toString());
    
    UpdateExonerationTvaCmd updateExonerationTvaCmd= new UpdateExonerationTvaCmd();
    paramExonerationCltTva = (ParamExonerationCltTva)updateExonerationTvaCmd.execute(paramExonerationCltTva);
    return paramExonerationCltTva;
}
   public ActionForward validerExonerationTva(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        StringBuffer text = 
            new StringBuffer("La validation de la prise en charge de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
        try{
            ExonerationCltTva exonerationCltTva = new ExonerationCltTva();    
            exonerationCltTva = exonerationTvaForm.getExonerationCltTva();
            exonerationCltTva.setCodEtatEtva(Constants.COD_ETAT_ETVA_VALIDE);
            ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            exonerationCltTva.setDatValcEtva(DateHandler.strToDate(paramAgence.getDateComptable()));
            exonerationCltTva.setDatDebEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatDebEtva()));
            exonerationCltTva.setDatFinEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatFinEtva()));
            ParamExonerationCltTva paramExonerationCltTva = mettreAjourExonerationCltTva (exonerationCltTva,exonerationCltTva.getDatValcEtva(),Constants.COD_TACH_VAL_ETVA, Constants.COD_OPER_CRE_ETVA, exonerationTvaForm.getInitialisationView());
     
           if(paramExonerationCltTva.getExonerationCltTva().getCodEtatEtva().equals(Constants.COD_ETAT_ETVA_VALIDE)){
                  imprimerExonerationCltTva(paramExonerationCltTva.getExonerationCltTva(),exonerationTvaForm,request);
              }
            if(!paramExonerationCltTva.hasError()){
                remplirLibelleConfirmation(exonerationTvaForm);
                }else{
                    logger.error("Erreur dans l'objet de retour 'exonerationCltTva' -- validation exo tva");
                    }
            
        }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date()); text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
       return mapping.findForward("pageConfirmation");
    }
public void remplirLibelleConfirmation( ExonerationTvaForm form){
    StringBuffer textConfirmation = new StringBuffer("");
    if (form.getInitialisationView().getCodeOperation().equals("PEC")) {
        textConfirmation.append("La prise en charge de l'exonération TVA pour le client ");
          }else if (form.getInitialisationView().getCodeOperation().equals("VALID")) {
                  textConfirmation.append("La validation de l'exonération TVA pour le client ");
                 }else if (form.getInitialisationView().getCodeOperation().equals("MODIF")) {
                           textConfirmation.append("La modification de l'exonération TVA pour le client ");
                         }else if (form.getInitialisationView().getCodeOperation().equals("ANNUL")) {
                                   textConfirmation.append("L'annulation de l'exonération TVA pour le client ");
                               }else  if (form.getInitialisationView().getCodeOperation().equals("CONSULT")) {
                                          textConfirmation.append("La consultation de l'exonération TVA pour le client ");
                                       }else {
                                              textConfirmation.append("---Code opération vide---");  }

    textConfirmation.append(form.getNomNomPers());textConfirmation.append(" ");
    textConfirmation.append(form.getNomPrnPers());textConfirmation.append(" a été effectuée avec succès.");
    form.setLibelleConfirmation(textConfirmation.toString());
}
    public ActionForward annulerExonerationTva(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        StringBuffer text = 
            new StringBuffer("L'annulation de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
        try{
            ExonerationCltTva exonerationCltTva = new ExonerationCltTva();    
            exonerationCltTva = exonerationTvaForm.getExonerationCltTva();
            exonerationCltTva.setCodEtatEtva(Constants.COD_ETAT_ETVA_ANNULE);
            ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            exonerationCltTva.setDatAnnEtva(DateHandler.strToDate(paramAgence.getDateComptable()));
           ParamExonerationCltTva paramExonerationCltTva = mettreAjourExonerationCltTva (exonerationCltTva,exonerationCltTva.getDatAnnEtva(),  Constants.COD_TACH_ANNUL_ETVA, Constants.COD_OPER_ANNUL_ETVA, exonerationTvaForm.getInitialisationView());
         
            if(!paramExonerationCltTva.hasError()){
                remplirLibelleConfirmation(exonerationTvaForm);
                }else{
                
                    text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
                    text.append(". Exception :"); text.append(exonerationCltTva.getErrorMessage());
                    logger.error("Erreur dans l'objet de retour 'exonerationCltTva' -- annulation exo tva" + text.toString());
                    com.oxia.fwk.core.Error erreur = exonerationCltTva.getErrors().get(0);
                    ActionMessages actionMessages = new ActionMessages();
                    ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }
            
        }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date());  text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
       return mapping.findForward("pageConfirmation");
    }
    public ActionForward modifierExonerationTva(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        StringBuffer text = 
            new StringBuffer("L'annulation de l'éxénoration TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
        ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
        try{
            ExonerationCltTva exonerationCltTva = new ExonerationCltTva();    
            exonerationCltTva = exonerationTvaForm.getExonerationCltTva();
            exonerationCltTva.setCodEtatEtva(Constants.COD_ETAT_ETVA_MODIF);
            ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            exonerationCltTva.setDatModEtva(DateHandler.strToDate(paramAgence.getDateComptable()));
            exonerationCltTva.setDatFinEtva(DateHandler.strToDate(exonerationTvaForm.getExonerationCltTvaView().getDatFinEtva()));
            
            ParamExonerationCltTva paramExonerationCltTva = mettreAjourExonerationCltTva (exonerationCltTva,exonerationCltTva.getDatModEtva(), Constants.COD_TACH_MODIF_ETVA,Constants.COD_OPER_MODIF_ETVA,  exonerationTvaForm.getInitialisationView());
            
            if(!paramExonerationCltTva.hasError()){
                remplirLibelleConfirmation(exonerationTvaForm);
                }else{
                    text.append("Erreur dans l'objet de retour du traitement 'exonerationCltTva' -- modification exo tva");
                    text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
                    text.append(". Exception :"); text.append(exonerationCltTva.getErrorMessage());
                    logger.error(text.toString());
                    com.oxia.fwk.core.Error erreur = exonerationCltTva.getErrors().get(0);
                    ActionMessages actionMessages = new ActionMessages();
                    ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }  
            
        }catch (Exception e) {

           text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
           text.append(". Date :"); text.append(new Date()); text.append(e.toString());
           ActionMessages actionMessages = new ActionMessages();
           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 text.toString());
           actionMessages.add("Erreur ", actionMessage);   
           this.saveMessages(request, actionMessages);
           logger.error(text.toString(),e);
           return mapping.findForward("error");

       }    
       return mapping.findForward("pageConfirmation");
    }  
  //    
   public ActionForward imprimerListeExonerationTva(ActionMapping mapping, 
                                                             ActionForm form, 
                                                             HttpServletRequest request, 
                                                             HttpServletResponse response) throws IOException, 
                                                                                                  ServletException {
     StringBuffer text =     new StringBuffer("L'impression de la liste des éxénorations TVA a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
     ExonerationTvaForm exonerationTvaForm = (ExonerationTvaForm)form;
     try{
           CommonReportVO valueObject = new CommonReportVO();
           Map parameters = new HashMap();
           String libParametre;
           String valParametre;
           String pLibEtat="P_LIB_ETAT";
           String vLibEtat="";
           StringBuffer txtLibEtat =     new StringBuffer("Liste des exonérations TVA");
           StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
           txtNomFichJasper.append("ExonerationTVA");
           txtNomFichJasper.append(File.separatorChar);
           vLibEtat="";
           
            if(exonerationTvaForm.getInitialisationView() != null ){
                    // Ajouter code agence comme critère de recherche si la structure de l utilisateur est l agence
                    // Ajouter code agence comme critère de recherche si la structure de l utilisateur est la dir. Comptabilité et le choix de recherche est par agence (2)
                    libParametre = "P_COD_STRC_STRC";
                    if(!exonerationTvaForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DCOMPT)){
                    // l'utilisateur est connecté depuis une agence, la recherche retourne les exonerations de cette agence
                               valParametre = exonerationTvaForm.getInitialisationView().getCodeAgence();
                               parameters.put(libParametre,valParametre);
                           }else {
                               // l'utilisateur est connecté depuis la direction de comptabilité 
                                   if(exonerationTvaForm.getParamConsult().getChoix().equals("2")){
                                         valParametre = exonerationTvaForm.getParamConsult().getCodStrcStrc();
                                         parameters.put(libParametre,valParametre);
                                     }
                              }
                    // Ajout du parametre matricule utilisateur
                     libParametre= "P_NUM_MATR_USER";
                     valParametre = exonerationTvaForm.getInitialisationView().getNumMatrUser();
                     parameters.put(libParametre,valParametre);
                    // Ajouter le paramètre Etat exo TVA (libParametre = "P_ETAT") independement du choix et de la structure de l'utilisateur
                     if(!exonerationTvaForm.getParamConsult().getEtat().equals("")){
                          char[] valEta = exonerationTvaForm.getParamConsult().getEtat().toCharArray();
                         
                          switch(valEta[0]){
                              case Constants.COD_ETA_ETVA_VALID : txtLibEtat.append(" (Valides)");
                                         txtNomFichJasper.append("ListExoTvaValide");
                                       break;
                              case Constants.COD_ETA_ETVA_ATTENT : txtLibEtat.append(" (En Attentes)");
                                         txtNomFichJasper.append("ListExoTvaAttente");
                                       break;
                              case Constants.COD_ETA_ETVA_MODIF : txtLibEtat.append(" (Modifiées)");
                                         txtNomFichJasper.append("ListExoTvaModifie");
                                        break;
                              case Constants.COD_ETA_ETVA_ANNUL : txtLibEtat.append(" (Annulées)");
                                         txtNomFichJasper.append("ListExoTvaAnnule");
                                        break;
                              default : txtNomFichJasper.append("ListExoTva");
                                        break;
                          }
                           valEta = null;
                           libParametre = "P_ETAT";
                           valParametre = exonerationTvaForm.getParamConsult().getEtat(); 
                           parameters.put(libParametre,valParametre);
                           }else {
                           // l'etat choisit est vide (tous etat confondu)
                               txtNomFichJasper.append("ListExoTva");;
                            }
                }else {
                      logger.debug(" exonerationTvaForm.getInitialisationView() == null ");
                    }
            
          
           if(exonerationTvaForm.getParamConsult().getChoix().equals("0")){
                   txtLibEtat.append(" par pièce d'identification");
                   if(exonerationTvaForm.getParamConsult().getEtat().equals("")){
                       if(!exonerationTvaForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DCOMPT)){
                           txtNomFichJasper.append("Piece");
                           }else {
                               // Dans ce cas,la requette change,
                               //on ne teste pas sur le code agence 
                               //et on affiche l'agence qui a fait des opérations de suspension tva pour ce client
                               txtNomFichJasper.append("PieceDC");
                           }
                   }else {
                       txtNomFichJasper.append("Piece");
                   }
                   
                   if(exonerationTvaForm.getParamConsult() != null ){
                           if(!exonerationTvaForm.getParamConsult().getNumPcePers().equals("")){
                               libParametre = "P_COD_TPCE_TPCE";
                               valParametre = exonerationTvaForm.getParamConsult().getTypPcePers();
                               parameters.put(libParametre,valParametre);
                               libParametre = "P_NUM_PCE";
                               valParametre = exonerationTvaForm.getParamConsult().getNumPcePers();
                               parameters.put(libParametre,valParametre);
                           }
                   }
               }else if(exonerationTvaForm.getParamConsult().getChoix().equals("1")){
                                 txtLibEtat.append(" par période");
                                 if(!exonerationTvaForm.getInitialisationView().getCodeAgence().equals(Constants.COD_STRC_DCOMPT)){
                                         txtNomFichJasper.append("Periode");
                                     }else {
                                         // Dans ce cas,la requette change,
                                         //on ne teste pas sur le code agence 
                                         //et on affiche l'agence qui a fait des opérations de suspension tva pour ce client
                                         txtNomFichJasper.append("PeriodeDC");
                                     }
                                 
                                 if(exonerationTvaForm.getParamConsult() != null ){
                                     if(!exonerationTvaForm.getParamConsult().getDateDebutconsult().equals("") &&
                                        !exonerationTvaForm.getParamConsult().getDateFinconsult().equals("")){
                                         libParametre = "P_DATE_DEB";
                                         valParametre = exonerationTvaForm.getParamConsult().getDateDebutconsult();
                                         parameters.put(libParametre,valParametre);
                                         libParametre = "P_DATE_FIN";
                                         valParametre = exonerationTvaForm.getParamConsult().getDateFinconsult();
                                         parameters.put(libParametre,valParametre);
                                        }
                                 }
                             }else if(exonerationTvaForm.getParamConsult().getChoix().equals("2")){
                                 txtLibEtat.append(" par agence");
                                 txtNomFichJasper.append("Agence");
                                } 
               
           // Titre du fichier à imprimer selon l'etat exo TVA et le choix (par periode...)
           vLibEtat = txtLibEtat.toString();
           parameters.put(pLibEtat,vLibEtat);
           
           valueObject.setParams(parameters);
           parameters = null;
           // indiquer le nom du fichier jasper                   
            valueObject.setNomReport(txtNomFichJasper.toString());  
           request.getSession().setAttribute("CommonPrintVo",valueObject);
           request.setAttribute("print","1");
           
       }catch (Exception e) {

          text.append("Exception au niveau de l'agence:"); text.append(exonerationTvaForm.getInitialisationView().getCodeAgence());
          text.append(". Date :"); text.append(new Date());  text.append(e.toString());
          ActionMessages actionMessages = new ActionMessages();
          ActionMessage actionMessage = 
              new ActionMessage("exception.generique", 
                               text.toString());
          actionMessages.add("Erreur ", actionMessage);   
          this.saveMessages(request, actionMessages);
          logger.error(text.toString(),e);
          return mapping.findForward("error");

       }
       return mapping.findForward("pageRecherche");                                                                                         
                                                                                                
                                                                                                
   }

}

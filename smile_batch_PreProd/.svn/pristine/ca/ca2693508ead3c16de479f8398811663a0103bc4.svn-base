package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.CaisseStrc;
import com.bna.commun.model.CaisseStrcId;
import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.JourneeCaisse;
import com.bna.commun.model.JourneeCaisseId;
import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.SessionJrnCaisse;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecaisse.commande.CreationCaisseVacationCmd;
import com.bna.smile.model.domainecaisse.commande.GetListMouvementCaisseCmd;
import com.bna.smile.model.domainecaisse.commande.GetListeSessionJrnCaisseCmd;
import com.bna.smile.model.domainecaisse.commande.InsertListMouvementSessionCaisseCmd;
import com.bna.smile.model.domainecaisse.commande.UpdateListMouvementCaisseCmd;
import com.bna.smile.model.domainecaisse.commande.UpdateSessionJrnCaisseCmd;
import com.bna.smile.model.domainecaisse.commande.ValidAlimentationCaisseCmd;
import com.bna.smile.model.domainecaisse.dao.CaisseDAO;
import com.bna.smile.model.domainecaisse.model.ListeCaisseStructureVo;
import com.bna.smile.model.domainecaisse.model.ParamMvtCaisse;
import com.bna.smile.model.domainecaisse.model.SessionJrnCaissePrVac;
import com.bna.smile.model.domainecaisse.traitement.GetListSessionJrnCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.ValidAlimentationCaisseTrt;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.operationguichet.form.CaisseForm;
import com.bna.smile.web.operationguichet.view.DetailSessionCaisseView;
import com.bna.smile.web.operationguichet.view.MouvementSessionCaisseView;

import com.oxia.fwk.context.Context;

import fr.improve.struts.taglib.layout.datagrid.Datagrid;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import org.springframework.orm.hibernate3.HibernateTemplate;


public class CaisseAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @author BOUSSEN Youssef & JERBI Lamia
     * @version le 24/03/2011
     */
     private static final Logger logger = Logger.getLogger(CaisseAction.class);
     ActionMessages actionMessages = new ActionMessages();
     Long codeOperation;
     Long codTach;
     public ActionForward initierPageCaisse(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {
                                                                    
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
    try{
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); 
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
        SmileUtil.testDomaineOuvert(structureDomaine);
        caisseForm.clearFormCaisse();
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultMandantContratForm"
        sessionUtil.removeSession(request,"caisseForm"); 

        return mapping.findForward( "initCaisse");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CaisseAction / Dispatch Action :initierPageCaisse ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    
    
    public ActionForward afficherSessionCaissesByUser(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        caisseForm.clearFormCaisse();
        caisseForm.setLibelleOperation("Ouverture Caisse");
        try {

                GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd = new GetListeSessionJrnCaisseCmd();
                ListeCaisseStructureVo listeCaisseStructureVo = new ListeCaisseStructureVo();
                ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                listeCaisseStructureVo.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
                listeCaisseStructureVo.setCodeStructure(paramAgence.getCodStrcStrc());
                listeCaisseStructureVo.setCodeStatus(Constants.STATUS_CAISSE_INITIALISE);
                listeCaisseStructureVo.setNumMatriculeUser(paramAgence.getNumMatrUser());
                listeCaisseStructureVo.setTypeCaisse(Constants.TYPE_CAISSE_PRINCIPALE);
            
                listeCaisseStructureVo = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(listeCaisseStructureVo);
                if (!listeCaisseStructureVo.hasError()) {
                    if (listeCaisseStructureVo != null && listeCaisseStructureVo.getListeCaisseStructure().size()>0) {
                        caisseForm.setListeCaisses(listeCaisseStructureVo.getListeCaisseStructure());
                        caisseForm.setAlertAfficheCaisse("True");
                    } else {
                        caisseForm.setAlertAfficheCaisse("False");
                    }

                } else {
                    List listErreur = listeCaisseStructureVo.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }
                return mapping.findForward("ouvertureCaisse");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("300");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }


    
    public ActionForward afficherDetailsSessionCaissesByUser(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        int numcaisseCourante=0;
        try {
            caisseForm.getListeDetailSessionCaisses().clear();
            SessionJrnCaisse sessionJrnCaisse = new SessionJrnCaisse();
            for (Iterator it = caisseForm.getListeCaisses().iterator();it.hasNext(); ) { 
                 sessionJrnCaisse = (SessionJrnCaisse)it.next();
                if (sessionJrnCaisse.getNumSeqSjc().intValue()==Long.valueOf(caisseForm.getNumCaisseChoisi()).intValue()){
                    numcaisseCourante=sessionJrnCaisse.getNumSeqSjc().intValue();
                    for (Iterator it1 = sessionJrnCaisse.getDetailSessionCaisses().iterator(); it1.hasNext();) {
                        DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it1.next();
                        DetailSessionCaisseView detailSessionCaisseView =new DetailSessionCaisseView();
                        detailSessionCaisseView.setLibSiglDev(detailSessionCaisse.getDevise().getLibSiglDev());
                        detailSessionCaisseView.setCodDevDev(detailSessionCaisse.getDevise().getCodDevDev().toString());
                        Context context = ContextHandler.getContext();
                        CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                        String mdd = caisseDAO.formaterMnt(Double.valueOf(detailSessionCaisse.getMontDebDsc())+ Double.valueOf(detailSessionCaisse.getMontToteDsc())- Double.valueOf(detailSessionCaisse.getMontTotsDsc()),detailSessionCaisse.getDevise().getCodDevDev());
                        detailSessionCaisseView.setMontDebDsc(caisseDAO.formaterMnt(detailSessionCaisse.getMontDebDsc(),detailSessionCaisse.getDevise().getCodDevDev()));
                        detailSessionCaisseView.setCodNatuDsc(detailSessionCaisse.getCodNatuDsc());
                        detailSessionCaisseView.setMontFinDsc(mdd);
                        caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisseView);
                     }
                    caisseForm.getCaisseView().setNumCaisCais(sessionJrnCaisse.getNumSeqSjc().toString());
                    // ancien matricule
                    caisseForm.setAncienMatricule(sessionJrnCaisse.getPersonnel().getNumMatrUser());
                }
            }
            ///*** la garniture de la liste des montants a allouer a la caisse de vacation apartir des devises existantes   
            if (caisseForm.getLibelleOperation().equalsIgnoreCase("OuvertureCaisseVacation") || (caisseForm.getLibelleOperation().equalsIgnoreCase("EnvoiFondInterCaisse"))){
                Datagrid dsc_datagrid = Datagrid.getInstance();
                dsc_datagrid.setData(new ArrayList());
                dsc_datagrid.setDataClass(DetailSessionCaisseView.class);
                dsc_datagrid.setData(caisseForm.getListeDetailSessionCaisses());
                caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
                if (caisseForm.getLibelleOperation().equalsIgnoreCase("EnvoiFondInterCaisse")&& caisseForm.getCodetrait().equalsIgnoreCase("InterCaisse")){
                    ///*** affichage des autres caisses (cible)
                    caisseForm.getListeCaisse().clear();
                    ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
                    GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
                    ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
                    paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
                    paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
                    paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
                    paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
                    ListeCaisseStructureVo paramCaisseStructureCible = new ListeCaisseStructureVo();
                    paramCaisseStructureCible = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
                    for (Iterator it3 = paramCaisseStructureCible.getListeCaisseStructure().iterator(); it3.hasNext();) {
                         SessionJrnCaisse sessionCaisse0 = (SessionJrnCaisse)it3.next();
                         if (sessionCaisse0.getNumSeqSjc().intValue()!=numcaisseCourante){
                            caisseForm.getListeCaisse().add(sessionCaisse0.getJourneeCaisse().getCaisseStrc().getCaisseStrcId());
                         }
                    }
                    return mapping.findForward("envoiInterCaisse");

                }else { if (caisseForm.getCodetrait().equalsIgnoreCase("ExtraCaisse")){
                        return mapping.findForward("envoiExternCaisse");  
                    }else
                    return mapping.findForward("ouvrirCaisseVacation");   
                }
            }else{
                return mapping.findForward("ouvertureCaisse");                
            }

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("300");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

 
    
    public ActionForward validerOuvertureCaisse(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            caisseForm.getListeDetailSessionCaisses().clear();
            for (Iterator it = caisseForm.getListeCaisses().iterator();it.hasNext(); ) { 
                SessionJrnCaisse sessionJrnCaisse = (SessionJrnCaisse)it.next();
                if (sessionJrnCaisse.getNumSeqSjc().intValue()==Long.valueOf(caisseForm.getNumCaisseChoisi()).intValue()){
                    sessionJrnCaisse.setCodStatSjc(Constants.STATUS_CAISSE_OUVERTE);
                    UpdateSessionJrnCaisseCmd updateSessionJrnCaisseCmd= new UpdateSessionJrnCaisseCmd();
                    sessionJrnCaisse= (SessionJrnCaisse)updateSessionJrnCaisseCmd.execute(sessionJrnCaisse);
                    caisseForm.setLibelleConfirmation("La Caisse N° "+sessionJrnCaisse.getJourneeCaisse().getCaisseStrc().getCaisseStrcId().getNumCaisCais()+" de l'agence "+sessionJrnCaisse.getJourneeCaisse().getCaisseStrc().getCaisseStrcId().getCodStrcStrc()+" est ouverte avec succes .");
                }
            }
            return mapping.findForward("confirmerCaisse");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("300");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }
    
    public ActionForward initiPageChangerCaissier(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        SessionUtil sessionUtil =new SessionUtil();
       
        try {
        //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
        sessionUtil.removeSession(request,"caisseForm"); 
        
            ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
            SmileUtil.testDomaineOuvert(structureDomaine);
            caisseForm.clearFormCaisse();
            if( paramAgence != null && paramAgence.getNumMatrUser() != null){
            caisseForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            caisseForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
            caisseForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            }
            
            caisseForm.setTitrePage("Changement de caissier");
            //Remplir la liste des caisses existantes
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
            
            ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
            paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
            paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
            paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_INITIALISE);
            paramCaisseStructure.setTypeCaisse(Constants.TYPE_CAISSE_PRINCIPALE);
            
            paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
            
            if (!paramCaisseStructure.hasError()) {
                if (paramCaisseStructure != null && 
                    paramCaisseStructure.getListeCaisseStructure().size()>0) {
                    caisseForm.setListeCaisses(paramCaisseStructure.getListeCaisseStructure());
                } 
            }
            SessionJrnCaisse sessionCaisse = (SessionJrnCaisse) caisseForm.getListeCaisses().get(0);
            caisseForm.getCaisseView().setNumCaisCais(sessionCaisse.getJourneeCaisse().getJourneeCaisseId().getNumCaisCais().toString());
            // ancien matricule
             caisseForm.setAncienMatricule(sessionCaisse.getPersonnel().getNumMatrUser());
             
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du changement du caissier a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
            text.append("Exception au niveau de l'agence:"); 
            text.append(caisseForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
           // erreur.setCode("298");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
       return mapping.findForward( "changementCaissier");
    }

    public ActionForward initPageEnvoiInterCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        SessionUtil sessionUtil =new SessionUtil();
        
        try {
            //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
            sessionUtil.removeSession(request,"caisseForm"); 
            caisseForm.setLibelleOperation("EnvoiFondInterCaisse");
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
            SmileUtil.testDomaineOuvert(structureDomaine);
            caisseForm.clearFormCaisse();
            if( paramAgence != null && paramAgence.getNumMatrUser() != null){
            caisseForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            caisseForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
            caisseForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            }
            
            caisseForm.setTitrePage("Envoi Inter Caisses");
            //Remplir la liste des caisses existantes
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
            
            ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
            paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
            paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
            paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
            paramCaisseStructure.setNumMatriculeUser(paramAgence.getNumMatrUser());
            paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
          
            if (!paramCaisseStructure.hasError()) {
                if (paramCaisseStructure != null && 
                    paramCaisseStructure.getListeCaisseStructure().size()>0) {
                    caisseForm.setListeCaisses(paramCaisseStructure.getListeCaisseStructure());
                } 
            }
            
            
            if (caisseForm.getListeCaisses().size()!=0){
                SessionJrnCaisse sessionCaisse = (SessionJrnCaisse) caisseForm.getListeCaisses().get(0);
                caisseForm.getCaisseView().setNumCaisCais(sessionCaisse.getNumSeqSjc().toString());
                
                if (caisseForm.getCodetrait().equalsIgnoreCase("InterCaisse")){
                    ///*** affichage des autres caisses (cible)
                    ListeCaisseStructureVo paramCaisseStructureCible = new ListeCaisseStructureVo();
                    paramCaisseStructure.setNumMatriculeUser(null);
                    paramCaisseStructureCible = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
                    for (Iterator it3 = paramCaisseStructureCible.getListeCaisseStructure().iterator(); it3.hasNext();) {
                         SessionJrnCaisse sessionCaisse0 = (SessionJrnCaisse)it3.next();
                         if (sessionCaisse0.getNumSeqSjc().intValue()!=sessionCaisse.getNumSeqSjc().intValue()){
                            caisseForm.getListeCaisse().add(sessionCaisse0.getJourneeCaisse().getCaisseStrc().getCaisseStrcId());
                         }
                    }
                }/*else if ("ExtraCaisse"){
                    
                    
                }*/
               // ancien matricule
                 caisseForm.setAncienMatricule(sessionCaisse.getPersonnel().getNumMatrUser());
                // afficher le détails de la première session caisse de la liste
                 for (Iterator it1 = sessionCaisse.getDetailSessionCaisses().iterator(); it1.hasNext();) {
                      DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it1.next();
                      DetailSessionCaisseView detailSessionCaisseView =new DetailSessionCaisseView();
                      detailSessionCaisseView.setLibSiglDev(detailSessionCaisse.getDevise().getLibSiglDev());
                      detailSessionCaisseView.setCodDevDev(detailSessionCaisse.getDevise().getCodDevDev().toString());
                      Context context = ContextHandler.getContext();
                      CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                      String mdd = caisseDAO.formaterMnt(Double.valueOf(detailSessionCaisse.getMontDebDsc())+ Double.valueOf(detailSessionCaisse.getMontToteDsc())- Double.valueOf(detailSessionCaisse.getMontTotsDsc()),detailSessionCaisse.getDevise().getCodDevDev());
                      detailSessionCaisseView.setMontDebDsc(caisseDAO.formaterMnt(detailSessionCaisse.getMontDebDsc(),detailSessionCaisse.getDevise().getCodDevDev()));
                      detailSessionCaisseView.setCodNatuDsc(detailSessionCaisse.getCodNatuDsc());
                      detailSessionCaisseView.setMontFinDsc(mdd);
                      caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisseView);
                  }
            }
            ///*** initialisation datagrid
            Datagrid dsc_datagrid = Datagrid.getInstance();
          //  caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
            dsc_datagrid.setData(new ArrayList());
            dsc_datagrid.setDataClass(DetailSessionCaisseView.class);
            dsc_datagrid.setData(caisseForm.getListeDetailSessionCaisses());
            caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'Envoi Inter Caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
            text.append("Exception au niveau de l'agence:"); 
            text.append(caisseForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
           // erreur.setCode("298");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
        if (caisseForm.getCodetrait().equalsIgnoreCase("InterCaisse")){
            return mapping.findForward("envoiInterCaisse");
        }else {
            caisseForm.setTitrePage("PEC Envoi Externe Inter Caisses");
           return mapping.findForward("envoiExternCaisse");
        }
    }
    
    public ActionForward initPecAlimExternCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
    
         ActionMessages actionMessages = new ActionMessages();
         CaisseForm caisseForm = (CaisseForm)form;
         SessionUtil sessionUtil =new SessionUtil();
         List listesMouvement = new ArrayList();
         try {
         //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
         sessionUtil.removeSession(request,"caisseForm"); 
         
             ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
             SmileUtil.testDomaineOuvert(structureDomaine);
             caisseForm.clearFormCaisse();
             if( paramAgence != null && paramAgence.getNumMatrUser() != null){
                 caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
                 }
                 
             caisseForm.setTitrePage("P.E.C Alimentation Externe");
          
             GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
             
             // rechercher la liste des sessions ouvertes par l'utilisateur
             ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
                    paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
                    paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
                    paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
                    paramCaisseStructure.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
             paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
             if (!paramCaisseStructure.hasError()) {
                   if (paramCaisseStructure != null && paramCaisseStructure.getListeCaisseStructure().size()>0) {
                       caisseForm.setListeCaisses(paramCaisseStructure.getListeCaisseStructure());
                   }
             }
              
             listesMouvement =
                 rechercherMouvementParStructure(paramAgence.getCodStrcStrc(),
                                              Constants.COD_OPER_ENV_EXTERN_CAISSE, DateHandler.strToDate(paramAgence.getDateComptable()));
                                            
             // créer la liste des mvts d'alimentation à insérer 
               for (Iterator iter = listesMouvement.iterator(); iter.hasNext(); ) {
                    MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)iter.next();
                  
                   // affecter la caisse origine dan l mvt caisse a insérer
                    MouvementSessionCaisse mvtCaisse = new MouvementSessionCaisse();
                    CaisseStrc caisseStrc = new CaisseStrc();
                    CaisseStrcId caisseStrcId = new CaisseStrcId();
                    caisseStrcId.setNumCaisCais(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getJourneeCaisseId().getNumCaisCais());
                    caisseStrc.setCaisseStrcId(caisseStrcId);
                    mvtCaisse.setCaisseStrc(caisseStrc);
                 //   mvtCaisse.setSessionJrnCaisse(sessionJrnCaisseRecept);
                    mvtCaisse.setNumMvtMvtc(mouvementSessionCaisse.getNumMvtMvtc());
                    mvtCaisse.setMontMvtMvtc(mouvementSessionCaisse.getMontMvtMvtc());
                    mvtCaisse.setDevise(mouvementSessionCaisse.getDevise());
                    mvtCaisse.setStructure(mouvementSessionCaisse.getStructure());
                    mvtCaisse.setCodStatMvtc(mouvementSessionCaisse.getCodStatMvtc());
                    mvtCaisse.setMouvementSessionCaisseMere(mouvementSessionCaisse);
                    mvtCaisse.setCodNatdMvtc(mouvementSessionCaisse.getCodNatdMvtc());
                    caisseForm.getListeMouvementsAinserer().add(mvtCaisse);
               }
             if (listesMouvement != null && listesMouvement.size()>0) {
                     caisseForm.setListeMouvements(listesMouvement);
                     caisseForm.setListeMouvementsView(traiterListeMouvementCaisse(listesMouvement));
                     for (int i = 0; i < caisseForm.getListeMouvements().size(); i++)
                          caisseForm.getIndexMvtsChoisis().add("");
                 } 
             
             
       
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("L'initialisation de la prise en chagre de l'Alimentation externe de caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
             text.append("Exception au niveau de l'agence:"); 
             text.append(caisseForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :"); text.append(e.toString());
            // erreur.setCode("298");
             erreur.setDescription(text.toString());
             logger.error(text.toString(),e); 
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }
         return mapping.findForward( "alimExternCaisse");     
    
     }
    
    public ActionForward initPecCommandeCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
    
         ActionMessages actionMessages = new ActionMessages();
         CaisseForm caisseForm = (CaisseForm)form;
         SessionUtil sessionUtil =new SessionUtil();
         
         try {
             //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
             sessionUtil.removeSession(request,"caisseForm"); 
             caisseForm.setLibelleOperation("EnvoiFondInterCaisse");
             ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
             SmileUtil.testDomaineOuvert(structureDomaine);
             caisseForm.clearFormCaisse();
             if( paramAgence != null && paramAgence.getNumMatrUser() != null){
             caisseForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
             caisseForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
             caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
             caisseForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
             }
             
             caisseForm.setTitrePage("Prise En Charge Commande  Caisse");
             //Remplir la liste des caisses existantes
             GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
             
             ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
             paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
             paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
             paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
             paramCaisseStructure.setNumMatriculeUser(paramAgence.getNumMatrUser());
             paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
           
             if (!paramCaisseStructure.hasError()) {
                 if (paramCaisseStructure != null && 
                     paramCaisseStructure.getListeCaisseStructure().size()>0) {
                     caisseForm.setListeCaisses(paramCaisseStructure.getListeCaisseStructure());
                 } 
             }
             
             
             if (caisseForm.getListeCaisses().size()!=0){
                 SessionJrnCaisse sessionCaisse = (SessionJrnCaisse) caisseForm.getListeCaisses().get(0);
                 caisseForm.getCaisseView().setNumCaisCais(sessionCaisse.getNumSeqSjc().toString());
                 
               // ancien matricule
                  caisseForm.setAncienMatricule(sessionCaisse.getPersonnel().getNumMatrUser());
                 // afficher le détails de la première session caisse de la liste
                  for (Iterator it1 = sessionCaisse.getDetailSessionCaisses().iterator(); it1.hasNext();) {
                       DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it1.next();
                       DetailSessionCaisseView detailSessionCaisseView =new DetailSessionCaisseView();
                       detailSessionCaisseView.setLibSiglDev(detailSessionCaisse.getDevise().getLibSiglDev());
                       detailSessionCaisseView.setCodDevDev(detailSessionCaisse.getDevise().getCodDevDev().toString());
                       Context context = ContextHandler.getContext();
                       CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                       String mdd = caisseDAO.formaterMnt(Double.valueOf(detailSessionCaisse.getMontDebDsc())+ Double.valueOf(detailSessionCaisse.getMontToteDsc())- Double.valueOf(detailSessionCaisse.getMontTotsDsc()),detailSessionCaisse.getDevise().getCodDevDev());
                       detailSessionCaisseView.setMontDebDsc(caisseDAO.formaterMnt(detailSessionCaisse.getMontDebDsc(),detailSessionCaisse.getDevise().getCodDevDev()));
                       detailSessionCaisseView.setCodNatuDsc(detailSessionCaisse.getCodNatuDsc());
                       detailSessionCaisseView.setMontFinDsc(mdd);
                       caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisseView);
                   }
             }
             ///*** initialisation datagrid
             Datagrid dsc_datagrid = Datagrid.getInstance();
           //  caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
             dsc_datagrid.setData(new ArrayList());
             dsc_datagrid.setDataClass(DetailSessionCaisseView.class);
             dsc_datagrid.setData(caisseForm.getListeDetailSessionCaisses());
             caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
             
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("La prise en charge de la commande caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
             text.append("Exception au niveau de l'agence:"); 
             text.append(caisseForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :"); text.append(e.toString());
            // erreur.setCode("298");
             erreur.setDescription(text.toString());
             logger.error(text.toString(),e); 
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }
         return mapping.findForward( "pecCmdCaisse");     
    
     }
     
     
    public ActionForward initPageAlimentationCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        SessionUtil sessionUtil =new SessionUtil();
        List listesMouvement = new ArrayList();
        try {
        //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
        sessionUtil.removeSession(request,"caisseForm"); 
        
            ParamAgence paramAgence = 
                   (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
            SmileUtil.testDomaineOuvert(structureDomaine);
            caisseForm.clearFormCaisse();
            if( paramAgence != null && paramAgence.getNumMatrUser() != null){
                caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
                }
                
            caisseForm.setTitrePage("Alimentation Inter Caisse");
         
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
            
            // rechercher la liste des sessions ouvertes par l'utilisateur
            ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
                   paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
                   paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
                   paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
                   paramCaisseStructure.setNumMatriculeUser(paramAgence.getNumMatrUser().toString());
            paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
           
            List listMouvementSession = new ArrayList();
            
            if (!paramCaisseStructure.hasError()) {
                  if (paramCaisseStructure != null && paramCaisseStructure.getListeCaisseStructure().size()>0) {
                      for (Iterator it = paramCaisseStructure.getListeCaisseStructure().iterator(); it.hasNext(); ) {
                                SessionJrnCaisse sessionJrnCaisseRecept = (SessionJrnCaisse)it.next();
                                listMouvementSession =
                                    rechercherMouvementParCaisse(sessionJrnCaisseRecept.getJourneeCaisse().getJourneeCaisseId().getNumCaisCais(),
                                                                 Constants.COD_OPER_ENV_INTER_CAISSE, DateHandler.strToDate(paramAgence.getDateComptable()));
                                if(listMouvementSession != null && listMouvementSession.size()>0){
                                    // liste des mvts d'envoi à mettre à jour
                                    listesMouvement.addAll(listMouvementSession);
                                                                        
                                   // créer la liste des mvts d'alimentation à insérer 
                                     for (Iterator iter = listMouvementSession.iterator(); iter.hasNext(); ) {
                                          MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)iter.next();
                                        
                                         // affecter la caisse origine dan l mvt caisse a insérer
                                          MouvementSessionCaisse mvtCaisse = new MouvementSessionCaisse();
                                          CaisseStrc caisseStrc = new CaisseStrc();
                                          CaisseStrcId caisseStrcId = new CaisseStrcId();
                                          caisseStrcId.setNumCaisCais(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getJourneeCaisseId().getNumCaisCais());
                                          caisseStrc.setCaisseStrcId(caisseStrcId);
                                          mvtCaisse.setCaisseStrc(caisseStrc);
                                          mvtCaisse.setSessionJrnCaisse(sessionJrnCaisseRecept);
                                          mvtCaisse.setNumMvtMvtc(mouvementSessionCaisse.getNumMvtMvtc());
                                          mvtCaisse.setMontMvtMvtc(mouvementSessionCaisse.getMontMvtMvtc());
                                          mvtCaisse.setDevise(mouvementSessionCaisse.getDevise());
                                          mvtCaisse.setStructure(mouvementSessionCaisse.getStructure());
                                          mvtCaisse.setCodStatMvtc(mouvementSessionCaisse.getCodStatMvtc());
                                          mvtCaisse.setMouvementSessionCaisseMere(mouvementSessionCaisse);
                                          mvtCaisse.setCodNatdMvtc(mouvementSessionCaisse.getCodNatdMvtc());
                                          caisseForm.getListeMouvementsAinserer().add(mvtCaisse);
                                     }
                                }
                           }
                       } 
                   } 
             
            if (listesMouvement != null && listesMouvement.size()>0) {
                    caisseForm.setListeMouvements(listesMouvement);
                    caisseForm.setListeMouvementsView(traiterListeMouvementCaisse(caisseForm.getListeMouvementsAinserer()));
                    for (int i = 0; i < caisseForm.getListeMouvements().size(); i++)
                         caisseForm.getIndexMvtsChoisis().add("");
                } 
           
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de l'Alimentation inter caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
            text.append("Exception au niveau de l'agence:"); 
            text.append(caisseForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
           // erreur.setCode("298");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
       return mapping.findForward( "alimInterCaisse");
    }
    
    
    public List rechercherMouvementParCaisse(Long numCaisse, Long codeOper, Date dateMvt){
    
        GetListMouvementCaisseCmd getListMouvementCaisseCmd =  new GetListMouvementCaisseCmd();
        ParamMvtCaisse paramMvtCaisse = new ParamMvtCaisse();
        Listes listesMouvement = new Listes();
        
        paramMvtCaisse.setCodeOperation(codeOper);
        paramMvtCaisse.setNumeroCais(numCaisse);
        // etat =0
        paramMvtCaisse.setEtat(Constants.STATUS_MVT_ENVOI);
        paramMvtCaisse.setDateMvt(dateMvt);
        
        listesMouvement = (Listes)getListMouvementCaisseCmd.execute(paramMvtCaisse);
        
        return (listesMouvement.getList());
        
    }
    public List rechercherMouvementParStructure(Long numStructure, Long codeOper, Date dateMvt){
    
        GetListMouvementCaisseCmd getListMouvementCaisseCmd =  new GetListMouvementCaisseCmd();
        ParamMvtCaisse paramMvtCaisse = new ParamMvtCaisse();
        Listes listesMouvement = new Listes();
        
        paramMvtCaisse.setCodeOperation(codeOper);
        paramMvtCaisse.setCodeStructure(numStructure);
        // etat =0
        paramMvtCaisse.setEtat(Constants.STATUS_MVT_ENVOI);
        paramMvtCaisse.setDateMvt(dateMvt);
        
        listesMouvement = (Listes)getListMouvementCaisseCmd.execute(paramMvtCaisse);
        
        return (listesMouvement.getList());
        
    }
    public Collection traiterListeMouvementCaisse(List listMouvementCaisse) {

        Collection listMouvementView = new ArrayList();
        try{
            if(listMouvementCaisse != null ){
             if(listMouvementCaisse.size() != 0){
                 for (Iterator it = listMouvementCaisse.iterator(); it.hasNext(); ) {
                 
                     MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                      if( mouvementSessionCaisse != null ){
                          MouvementSessionCaisseView mouvementSessionCaisseView = new MouvementSessionCaisseView();
                         
                          mouvementSessionCaisseView = traiterMouvementSessionCaisse(mouvementSessionCaisse);
                          listMouvementView.add(mouvementSessionCaisseView);
                          mouvementSessionCaisseView = null;
                         }
               }
              }
            }
            
        } catch (Exception e) {
                   logger.error("Exception Methode : traiterListeMouvementCaisse:  ",e);  
                   throw new RuntimeException(e);               
            } 
    return listMouvementView;
   }

 
    public MouvementSessionCaisseView traiterMouvementSessionCaisse(MouvementSessionCaisse mouvementSessionCaisse){
        
       MouvementSessionCaisseView mouvementSessionCaisseView = new MouvementSessionCaisseView();
        
       try{
       
       mouvementSessionCaisseView.setNumMouvement(mouvementSessionCaisse.getNumMvtMvtc().toString());
       mouvementSessionCaisseView.setCaisOriginView(mouvementSessionCaisse.getCaisseStrc().getCaisseStrcId().getNumCaisCais().toString());
       mouvementSessionCaisseView.setMontantMvtView(StrHandler.formatMontant(Math.abs(mouvementSessionCaisse.getMontMvtMvtc().longValue()),mouvementSessionCaisse.getDevise().getNbrDecDev()));
       mouvementSessionCaisseView.setDevMvt(mouvementSessionCaisse.getDevise().getLibDevDev());
       mouvementSessionCaisseView.setCaisReceptMvt(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getJourneeCaisseId().getNumCaisCais().toString());
       mouvementSessionCaisseView.setTypCaisReceptMvt(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getCaisseStrc().getCodTypCais());
       if(mouvementSessionCaisse.getStructure()!=null && mouvementSessionCaisse.getStructure().getCodStrcStrc()!=null){///*** structure receptrice
        mouvementSessionCaisseView.setCodeStructure(mouvementSessionCaisse.getStructure().getCodStrcStrc().toString());
       }
       if(mouvementSessionCaisse.getSessionJrnCaisse().getCodTypSjc().equals(Constants.TYPE_CAISSE_PRINCIPALE)){
          mouvementSessionCaisseView.setNatCaisReceptMvt("Principale");
           }else if(mouvementSessionCaisse.getSessionJrnCaisse().getCodTypSjc().equals(Constants.TYPE_CAISSE_VACATION)){
               mouvementSessionCaisseView.setNatCaisReceptMvt("Vacation");
           }
      } catch (Exception e) {
                 logger.error("Exception Methode : traiterMouvementSessionCaisse:  ",e);  
                 throw new RuntimeException(e);               
          } 
       
    return mouvementSessionCaisseView;
   }
    
    
    public ActionForward initPageValidEnvoiInterCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        SessionUtil sessionUtil =new SessionUtil();

        try {
        //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
        sessionUtil.removeSession(request,"caisseForm"); 
        
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
            SmileUtil.testDomaineOuvert(structureDomaine);
            caisseForm.clearFormCaisse();
            if( paramAgence != null && paramAgence.getNumMatrUser() != null){
                caisseForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
                caisseForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
                caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
                caisseForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            }
            
            caisseForm.setTitrePage("Validation Envoi Externe");
         
            GetListMouvementCaisseCmd getListMouvementCaisseCmd =  new GetListMouvementCaisseCmd();
            ParamMvtCaisse paramMvtCaisse = new ParamMvtCaisse();
            Listes listesMouvement = new Listes();
                                 
            paramMvtCaisse.setCodeOperation(Constants.COD_OPER_ENV_EXTERN_CAISSE);
            paramMvtCaisse.setCodeStructure(paramAgence.getCodStrcStrc());
            paramMvtCaisse.setEtat(                    Constants.STATUS_PEC_ENVOI_EXTERN);///*** etat =A
                                 
            listesMouvement = (Listes)getListMouvementCaisseCmd.execute(paramMvtCaisse);

            if (listesMouvement.getList() != null && listesMouvement.getList().size()>0) {
                    caisseForm.setListeMouvements(listesMouvement.getList());
                    caisseForm.setListeMouvementsView(traiterListeMouvementCaisse(listesMouvement.getList()));
                    for (int i = 0; i < caisseForm.getListeMouvements().size(); i++){
                         caisseForm.getIndexMvtsChoisis().add("");
                    }
                } 
           
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de l'envoi externe inter caisse a été interrompu, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
            text.append("Exception au niveau de l'agence:"); 
            text.append(caisseForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 
            ActionMessage actionMessage = new ActionMessage(" .generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
       return mapping.findForward( "validEnvoiExternCaisse");
    }

    public ActionForward validerAlimentationExterneCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        List listMvtsAmaj = new ArrayList();
        List listMvtsAinserer = new ArrayList();
        ParamMvtCaisse paramMvtCaisse = new ParamMvtCaisse();
        ValidAlimentationCaisseCmd validAlimentationCaisseCmd = new ValidAlimentationCaisseCmd();
        
         try{
         
         // affecter la caisse réceptrice aux mouvements
         
               // index = liste des Mvts cochés
               List index = caisseForm.getIndexMvtsChoisis();
               // récupérer les Mvts cochés
                for (int i = 0; i < index.size(); i++) {
                   if (!index.get(i).equals("")) {
                        for (Iterator it = caisseForm.getListeMouvements().iterator(); it.hasNext(); ) {
                           MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                           if(mouvementSessionCaisse.getNumMvtMvtc().longValue() == Long.valueOf(index.get(i).toString())){
                               mouvementSessionCaisse.setCodStatMvtc("1");
                               listMvtsAmaj.add(mouvementSessionCaisse);
                              
                               // liste des details session caisse origine à mettre à jour :: soustraire le montant envoyé
                               // Principe: le detail caisse par rapport à la devise va être mis à jour par la somme des mvt par rapport à cette même devise
                               // lorsque on ajoute un détail à la liste des details à mettre à jour dans la base, on vérifie qu'il n'est pas déjà ajouté
                               
                                DetailSessionCaisse detailCaisseMvt = new DetailSessionCaisse();
                                for (Iterator iterDet = mouvementSessionCaisse.getSessionJrnCaisse().getDetailSessionCaisses().iterator(); iterDet.hasNext(); ) {
                                  DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)iterDet.next();
                                   if(mouvementSessionCaisse.getDevise().getCodDevDev().longValue() == detailSessionCaisse.getDevise().getCodDevDev().longValue()
                                      && mouvementSessionCaisse.getCodNatdMvtc().equals(detailSessionCaisse.getCodNatuDsc())){
                                       detailCaisseMvt = detailSessionCaisse;
                                    if(caisseForm.getListeDetailSessionCaisseOrigine().size()>0){
                                      boolean exist = false;
                                       for (Iterator iterList = caisseForm.getListeDetailSessionCaisseOrigine().iterator(); iterList.hasNext(); ) {
                                          DetailSessionCaisse detailAjoute = (DetailSessionCaisse)iterList.next();
                                           if(detailSessionCaisse.getNumDscDsc().longValue() == detailAjoute.getNumDscDsc().longValue()){
                                                 exist =true;
                                               // le mouvement concerne une devise qui existe dejà dans la liste des détails à mettre à jour
                                               // faire la somme des montants 
                                               detailAjoute.setMontTotsDsc(detailAjoute.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                               // mettre à jour le montant envoyé
                                               if(detailAjoute.getMontEnvDsc() != null){
                                                   detailAjoute.setMontEnvDsc(detailAjoute.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                                  }
                                             }
                                            } 
                                        if( !exist ){
                                           detailSessionCaisse.setMontTotsDsc(detailSessionCaisse.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                            if(detailSessionCaisse.getMontEnvDsc() != null){
                                                detailSessionCaisse.setMontEnvDsc(detailSessionCaisse.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                               }
                                           caisseForm.getListeDetailSessionCaisseOrigine().add(detailSessionCaisse);
                                        }
                                    }else{
                                       detailSessionCaisse.setMontTotsDsc(detailSessionCaisse.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                       if(detailSessionCaisse.getMontEnvDsc() != null){
                                            detailSessionCaisse.setMontEnvDsc(detailSessionCaisse.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                           }
                                       caisseForm.getListeDetailSessionCaisseOrigine().add(detailSessionCaisse);
                                    }
                                   }
                                }
                           }
                        }
                         for (Iterator iter = caisseForm.getListeMouvementsAinserer().iterator(); iter.hasNext(); ) {
                            MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)iter.next();
                           if(mouvementSessionCaisse.getNumMvtMvtc() != null){
                            if(mouvementSessionCaisse.getNumMvtMvtc().longValue() == Long.valueOf(index.get(i).toString())){
                                // modifier MouvementSessionCaisse pour l inserer
                                mouvementSessionCaisse.setNumMvtMvtc(null);
                                mouvementSessionCaisse.setCodStatMvtc("1");
                                TacheId tacheId = new TacheId();
                                Tache tache =new Tache();
                                tacheId.setCodOperOper(Constants.COD_OPER_ALIM_INTER_CAISSE);
                                tacheId.setCodTachTach(Constants.COD_TACH_OUV_CAISSE_VAC);
                                tache.setTacheId(tacheId);
                                mouvementSessionCaisse.setTache(tache);
                                mouvementSessionCaisse.setLibOperMvtc("Alim. inter. caisses");
                                mouvementSessionCaisse.setDatMvtMvtc(DateHandler.strToDate(caisseForm.getInitialisationView().getDateComptable()));
                                mouvementSessionCaisse.setCodSensMvtc(Constants.COD_SENS_CR);
                                mouvementSessionCaisse.setDatSystMvtc(new Date());
                                listMvtsAinserer.add(mouvementSessionCaisse);
                                
                                // liste des details session caisse réceptrice soit à mettre à jour ::ajouter le montant envoyé (si le detail existe), sinon à inserer si le details n existe pa
                                 boolean detailExist = false;
                                 for (Iterator iterDet = mouvementSessionCaisse.getSessionJrnCaisse().getDetailSessionCaisses().iterator(); iterDet.hasNext(); ) {
                                   DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)iterDet.next();
                                    if(mouvementSessionCaisse.getDevise().getCodDevDev().longValue() == detailSessionCaisse.getDevise().getCodDevDev().longValue()
                                       &&  mouvementSessionCaisse.getCodNatdMvtc().equals(detailSessionCaisse.getCodNatuDsc())  ){
                                      detailExist = true; // il exist un détail par rappor a la devise du mvt
                                      if(caisseForm.getListeDetailSessionCaisses().size() > 0){
                                          boolean detailListexist = false;
                                          for (Iterator iterList = caisseForm.getListeDetailSessionCaisses().iterator(); iterList.hasNext(); ) {
                                              DetailSessionCaisse detailAjoute = (DetailSessionCaisse)iterList.next();
                                              if(detailSessionCaisse.getNumDscDsc().longValue() == detailAjoute.getNumDscDsc().longValue()){
                                                  detailAjoute.setMontToteDsc(detailAjoute.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                                  detailListexist = true;
                                              }
                                          }
                                          if(!detailListexist){
                                              detailSessionCaisse.setMontToteDsc(detailSessionCaisse.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                              caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisse);
                                          }
                                      }else {
                                          detailSessionCaisse.setMontToteDsc(detailSessionCaisse.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                          caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisse);
                                      }
                                    }
                                 }
                                 if(!detailExist){
                                     DetailSessionCaisse detailCaisse =new DetailSessionCaisse();
                                     detailCaisse.setMontDebDsc(mouvementSessionCaisse.getMontMvtMvtc());
                                     detailCaisse.setMontToteDsc(Long.valueOf(0));
                                     detailCaisse.setMontTotsDsc(Long.valueOf(0));
                                     detailCaisse.setCodNatuDsc(mouvementSessionCaisse.getCodNatdMvtc());
                                     Devise devise = new Devise();
                                     devise.setCodDevDev(mouvementSessionCaisse.getDevise().getCodDevDev());
                                     detailCaisse.setDevise(devise);
                                     detailCaisse.setSessionJrnCaisse(mouvementSessionCaisse.getSessionJrnCaisse());
                                     caisseForm.getListeDetailCaisseAinserer().add(detailCaisse);
                                 }
                            }
                           }
                         }
                     }
               }
               
               paramMvtCaisse.setListMouvementForUpdate(listMvtsAmaj);
               paramMvtCaisse.setListMouvementForInsert(listMvtsAinserer);
               // caisse origine
               //liste des détails/devise à mettre à jour
                paramMvtCaisse.setListDetailCaissOriginForUpdate(caisseForm.getListeDetailSessionCaisseOrigine());
               
               //caisse réceptrice
               //liste des détails/devise à mettre à jour
                paramMvtCaisse.setListDetailCaissReceptForUpdate(caisseForm.getListeDetailSessionCaisses());
               //liste des détails/devise à insèrer
                paramMvtCaisse.setListDetailCaissReceptForInsert(caisseForm.getListeDetailCaisseAinserer());
                
                paramMvtCaisse = (ParamMvtCaisse)validAlimentationCaisseCmd.execute(paramMvtCaisse); 
               
               if (!paramMvtCaisse.hasError()) {
                   StringBuffer message = new StringBuffer("");
                   message.append("L'alimentation inter caisses a été effectuée avec succés.");
                   caisseForm.setLibelleConfirmation(message.toString());
                   caisseForm.setTitreConfirmation("Confirmation alimentation inter caisses");
               } else {
                   List listErreur = paramMvtCaisse.getErrors();
                   for (Iterator ite = listErreur.iterator(); ite.hasNext(); ) {
                       com.oxia.fwk.core.Error erreur =  (com.oxia.fwk.core.Error)ite.next();
                       ActionMessage actionMessage =   new ActionMessage("exception.generique",  erreur.getDescription());
                       actionMessages.add("Erreur ", actionMessage);
                   }
                   this.saveMessages(request, actionMessages);
                   return mapping.findForward("error");
               }
                                  
           } catch (Exception e) {
                                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                                      StringBuffer text = 
                                          new StringBuffer("La validation de l'Alimentation inter caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
                                      text.append("Exception au niveau de l'agence:"); 
                                      text.append(caisseForm.getInitialisationView().getCodeAgence());
                                      text.append(". Exception :"); text.append(e.toString());
                                      erreur.setDescription(text.toString());
                                      logger.error(text.toString(),e); 
                                      ActionMessage actionMessage = 
                                          new ActionMessage("exception.generique", 
                                                            erreur.getDescription());
                                      actionMessages.add("Erreur ", actionMessage);
                                      this.saveMessages(request, actionMessages);
                                      return mapping.findForward("error");
                                  }
       return mapping.findForward( "confirmerCaisse");                               
     
    }
    
    
    public ActionForward validerAlimentationInterCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        List listMvtsAmaj = new ArrayList();
        List listMvtsAinserer = new ArrayList();
        ParamMvtCaisse paramMvtCaisse = new ParamMvtCaisse();
        ValidAlimentationCaisseCmd validAlimentationCaisseCmd = new ValidAlimentationCaisseCmd();
        
         try{
               // index = liste des Mvts cochés
               List index = caisseForm.getIndexMvtsChoisis();
               // récupérer les Mvts cochés
                for (int i = 0; i < index.size(); i++) {
                   if (!index.get(i).equals("")) {
                        for (Iterator it = caisseForm.getListeMouvements().iterator(); it.hasNext(); ) {
                           MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                           if(mouvementSessionCaisse.getNumMvtMvtc().longValue() == Long.valueOf(index.get(i).toString())){
                               mouvementSessionCaisse.setCodStatMvtc("1");
                               listMvtsAmaj.add(mouvementSessionCaisse);
                              
                               // liste des details session caisse origine à mettre à jour :: soustraire le montant envoyé
                               // Principe: le detail caisse par rapport à la devise va être mis à jour par la somme des mvt par rapport à cette même devise
                               // lorsque on ajoute un détail à la liste des details à mettre à jour dans la base, on vérifie qu'il n'est pas déjà ajouté
                               
                                DetailSessionCaisse detailCaisseMvt = new DetailSessionCaisse();
                                for (Iterator iterDet = mouvementSessionCaisse.getSessionJrnCaisse().getDetailSessionCaisses().iterator(); iterDet.hasNext(); ) {
                                  DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)iterDet.next();
                                   if(mouvementSessionCaisse.getDevise().getCodDevDev().longValue() == detailSessionCaisse.getDevise().getCodDevDev().longValue()
                                      && mouvementSessionCaisse.getCodNatdMvtc().equals(detailSessionCaisse.getCodNatuDsc())){
                                       detailCaisseMvt = detailSessionCaisse;
                                    if(caisseForm.getListeDetailSessionCaisseOrigine().size()>0){
                                      boolean exist = false;
                                       for (Iterator iterList = caisseForm.getListeDetailSessionCaisseOrigine().iterator(); iterList.hasNext(); ) {
                                          DetailSessionCaisse detailAjoute = (DetailSessionCaisse)iterList.next();
                                           if(detailSessionCaisse.getNumDscDsc().longValue() == detailAjoute.getNumDscDsc().longValue()){
                                                 exist =true;
                                               // le mouvement concerne une devise qui existe dejà dans la liste des détails à mettre à jour
                                               // faire la somme des montants 
                                               detailAjoute.setMontTotsDsc(detailAjoute.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                               // mettre à jour le montant envoyé
                                               if(detailAjoute.getMontEnvDsc() != null){
                                                   detailAjoute.setMontEnvDsc(detailAjoute.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                                  }
                                             }
                                            } 
                                        if( !exist ){
                                           detailSessionCaisse.setMontTotsDsc(detailSessionCaisse.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                            if(detailSessionCaisse.getMontEnvDsc() != null){
                                                detailSessionCaisse.setMontEnvDsc(detailSessionCaisse.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                               }
                                           caisseForm.getListeDetailSessionCaisseOrigine().add(detailSessionCaisse);
                                        }
                                    }else{
                                       detailSessionCaisse.setMontTotsDsc(detailSessionCaisse.getMontTotsDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                       if(detailSessionCaisse.getMontEnvDsc() != null){
                                            detailSessionCaisse.setMontEnvDsc(detailSessionCaisse.getMontEnvDsc() - mouvementSessionCaisse.getMontMvtMvtc());
                                           }
                                       caisseForm.getListeDetailSessionCaisseOrigine().add(detailSessionCaisse);
                                    }
                                   }
                                }
                           }
                        }
                         for (Iterator iter = caisseForm.getListeMouvementsAinserer().iterator(); iter.hasNext(); ) {
                            MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)iter.next();
                           if(mouvementSessionCaisse.getNumMvtMvtc() != null){
                            if(mouvementSessionCaisse.getNumMvtMvtc().longValue() == Long.valueOf(index.get(i).toString())){
                                // modifier MouvementSessionCaisse pour l inserer
                                mouvementSessionCaisse.setNumMvtMvtc(null);
                                mouvementSessionCaisse.setCodStatMvtc("1");
                                TacheId tacheId = new TacheId();
                                Tache tache =new Tache();
                                tacheId.setCodOperOper(Constants.COD_OPER_ALIM_INTER_CAISSE);
                                tacheId.setCodTachTach(Constants.COD_TACH_OUV_CAISSE_VAC);
                                tache.setTacheId(tacheId);
                                mouvementSessionCaisse.setTache(tache);
                                mouvementSessionCaisse.setLibOperMvtc("Alim. inter. caisses");
                                mouvementSessionCaisse.setDatMvtMvtc(DateHandler.strToDate(caisseForm.getInitialisationView().getDateComptable()));
                                mouvementSessionCaisse.setCodSensMvtc(Constants.COD_SENS_CR);
                                mouvementSessionCaisse.setDatSystMvtc(new Date());
                                listMvtsAinserer.add(mouvementSessionCaisse);
                                
                                // liste des details session caisse réceptrice soit à mettre à jour ::ajouter le montant envoyé (si le detail existe), sinon à inserer si le details n existe pa
                                 boolean detailExist = false;
                                 for (Iterator iterDet = mouvementSessionCaisse.getSessionJrnCaisse().getDetailSessionCaisses().iterator(); iterDet.hasNext(); ) {
                                   DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)iterDet.next();
                                    if(mouvementSessionCaisse.getDevise().getCodDevDev().longValue() == detailSessionCaisse.getDevise().getCodDevDev().longValue()
                                       &&  mouvementSessionCaisse.getCodNatdMvtc().equals(detailSessionCaisse.getCodNatuDsc())  ){
                                      detailExist = true; // il exist un détail par rappor a la devise du mvt
                                      if(caisseForm.getListeDetailSessionCaisses().size() > 0){
                                          boolean detailListexist = false;
                                          for (Iterator iterList = caisseForm.getListeDetailSessionCaisses().iterator(); iterList.hasNext(); ) {
                                              DetailSessionCaisse detailAjoute = (DetailSessionCaisse)iterList.next();
                                              if(detailSessionCaisse.getNumDscDsc().longValue() == detailAjoute.getNumDscDsc().longValue()){
                                                  detailAjoute.setMontToteDsc(detailAjoute.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                                  detailListexist = true;
                                              }
                                          }
                                          if(!detailListexist){
                                              detailSessionCaisse.setMontToteDsc(detailSessionCaisse.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                              caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisse);
                                          }
                                      }else {
                                          detailSessionCaisse.setMontToteDsc(detailSessionCaisse.getMontToteDsc() + mouvementSessionCaisse.getMontMvtMvtc());
                                          caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisse);
                                      }
                                    }
                                 }
                                 if(!detailExist){
                                     DetailSessionCaisse detailCaisse =new DetailSessionCaisse();
                                     detailCaisse.setMontDebDsc(mouvementSessionCaisse.getMontMvtMvtc());
                                     detailCaisse.setMontToteDsc(Long.valueOf(0));
                                     detailCaisse.setMontTotsDsc(Long.valueOf(0));
                                     detailCaisse.setCodNatuDsc(mouvementSessionCaisse.getCodNatdMvtc());
                                     Devise devise = new Devise();
                                     devise.setCodDevDev(mouvementSessionCaisse.getDevise().getCodDevDev());
                                     detailCaisse.setDevise(devise);
                                     detailCaisse.setSessionJrnCaisse(mouvementSessionCaisse.getSessionJrnCaisse());
                                     caisseForm.getListeDetailCaisseAinserer().add(detailCaisse);
                                 }
                            }
                           }
                         }
                     }
               }
               
               paramMvtCaisse.setListMouvementForUpdate(listMvtsAmaj);
               paramMvtCaisse.setListMouvementForInsert(listMvtsAinserer);
               // caisse origine
               //liste des détails/devise à mettre à jour
                paramMvtCaisse.setListDetailCaissOriginForUpdate(caisseForm.getListeDetailSessionCaisseOrigine());
               
               //caisse réceptrice
               //liste des détails/devise à mettre à jour
                paramMvtCaisse.setListDetailCaissReceptForUpdate(caisseForm.getListeDetailSessionCaisses());
               //liste des détails/devise à insèrer
                paramMvtCaisse.setListDetailCaissReceptForInsert(caisseForm.getListeDetailCaisseAinserer());
                
                paramMvtCaisse = (ParamMvtCaisse)validAlimentationCaisseCmd.execute(paramMvtCaisse); 
               
               if (!paramMvtCaisse.hasError()) {
                   StringBuffer message = new StringBuffer("");
                   message.append("L'alimentation inter caisses a été effectuée avec succés.");
                   caisseForm.setLibelleConfirmation(message.toString());
                   caisseForm.setTitreConfirmation("Confirmation alimentation inter caisses");
               } else {
                   List listErreur = paramMvtCaisse.getErrors();
                   for (Iterator ite = listErreur.iterator(); ite.hasNext(); ) {
                       com.oxia.fwk.core.Error erreur =  (com.oxia.fwk.core.Error)ite.next();
                       ActionMessage actionMessage =   new ActionMessage("exception.generique",  erreur.getDescription());
                       actionMessages.add("Erreur ", actionMessage);
                   }
                   this.saveMessages(request, actionMessages);
                   return mapping.findForward("error");
               }
                                  
           } catch (Exception e) {
                                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                                      StringBuffer text = 
                                          new StringBuffer("La validation de l'Alimentation inter caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
                                      text.append("Exception au niveau de l'agence:"); 
                                      text.append(caisseForm.getInitialisationView().getCodeAgence());
                                      text.append(". Exception :"); text.append(e.toString());
                                      erreur.setDescription(text.toString());
                                      logger.error(text.toString(),e); 
                                      ActionMessage actionMessage = 
                                          new ActionMessage("exception.generique", 
                                                            erreur.getDescription());
                                      actionMessages.add("Erreur ", actionMessage);
                                      this.saveMessages(request, actionMessages);
                                      return mapping.findForward("error");
                                  }
       return mapping.findForward( "confirmerCaisse");                               
     
  }
    
    public ActionForward validerValidationEnvoiExternInterCaisse(ActionMapping mapping, ActionForm form,  HttpServletRequest request, 
                              HttpServletResponse response) throws IOException,  ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        List listMvtsAmaj = new ArrayList();
         
         try{
            // index = liste des Mvts cochés
            List index = caisseForm.getIndexMvtsChoisis();
            // récupérer les Mvts cochés
            for (int i = 0; i < index.size(); i++) {
                if (!index.get(i).equals("")) {
                    for (Iterator it = caisseForm.getListeMouvements().iterator(); it.hasNext(); ) {
                        MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)it.next();
                        if(mouvementSessionCaisse.getNumMvtMvtc().longValue() == Long.valueOf(index.get(i).toString())){
                            mouvementSessionCaisse.setCodStatMvtc(Constants.STATUS_MVT_ENVOI);///*** status 0
                            listMvtsAmaj.add(mouvementSessionCaisse);
                        }
                    }
                }
            }
            Listes listMvtsMaj = new Listes();       
            listMvtsMaj.setList(listMvtsAmaj);
            UpdateListMouvementCaisseCmd updateListMouvementCaisseCmd = new UpdateListMouvementCaisseCmd();
            listMvtsMaj = (Listes)updateListMouvementCaisseCmd.execute(listMvtsMaj);               
               
            caisseForm.setLibelleConfirmation("La Validation des Envois Externe sont effectués avec succes .");
                                  
           } catch (Exception e) {
                                      com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                                      StringBuffer text = 
                                          new StringBuffer("La validation de l'Envoi Extern inter caisse a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
                                      text.append("Exception au niveau de l'agence:"); 
                                      text.append(caisseForm.getInitialisationView().getCodeAgence());
                                      text.append(". Exception :"); text.append(e.toString());
                                      erreur.setDescription(text.toString());
                                      logger.error(text.toString(),e); 
                                      ActionMessage actionMessage = 
                                          new ActionMessage("exception.generique", 
                                                            erreur.getDescription());
                                      actionMessages.add("Erreur ", actionMessage);
                                      this.saveMessages(request, actionMessages);
                                      return mapping.findForward("error");
                                  }
       return mapping.findForward( "confirmerCaisse");                               
     
    }

    public ActionForward validerChangerCaissier(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            caisseForm.getListeDetailSessionCaisses().clear();
            for (Iterator it = caisseForm.getListeCaisses().iterator();it.hasNext(); ) { 
                SessionJrnCaisse sessionJrnCaisse = (SessionJrnCaisse)it.next();
                if (sessionJrnCaisse.getJourneeCaisse().getJourneeCaisseId().getNumCaisCais().intValue()==Long.valueOf(caisseForm.getNumCaisseChoisi()).intValue()){
                    Personnel personnel = new Personnel();
                    personnel.setNumMatrUser(caisseForm.getMatrCaissier());
                    sessionJrnCaisse.setPersonnel(personnel);
                    UpdateSessionJrnCaisseCmd updateSessionJrnCaisseCmd= new UpdateSessionJrnCaisseCmd();
                    sessionJrnCaisse= (SessionJrnCaisse)updateSessionJrnCaisseCmd.execute(sessionJrnCaisse); 
                    if (!sessionJrnCaisse.hasError()) {
                        StringBuffer message = new StringBuffer("");
                        message.append("Le nouveau caissier de la caisse N°");
                        message.append(caisseForm.getNumCaisseChoisi());
                        message.append(" est: ");
                        message.append(caisseForm.getNomPrnUserAjax());message.append(" (");
                        message.append(caisseForm.getMatrCaissier());message.append(")");
                        caisseForm.setLibelleConfirmation(message.toString());
                        caisseForm.setTitreConfirmation("Confirmation changement de caissier");
                    } else {
                        List listErreur = sessionJrnCaisse.getErrors();
                        for (Iterator ite = listErreur.iterator(); ite.hasNext(); ) {
                            com.oxia.fwk.core.Error erreur =  (com.oxia.fwk.core.Error)ite.next();
                            ActionMessage actionMessage =   new ActionMessage("exception.generique",  erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                    }
                }
            }
         return mapping.findForward("confirmerCaisse");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("caisse");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }





    public ActionForward initOuvertureCaisseVacation(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {
                                                                   
       
        ActionMessages actionMessages = new ActionMessages();
        CaisseForm caisseForm = (CaisseForm)form;
        SessionUtil sessionUtil =new SessionUtil();
       
        try {
            //Suppression des anciens Bean de type Form de la session, SAUF "caisseForm"
            sessionUtil.removeSession(request,"caisseForm"); 
            caisseForm.setLibelleOperation("OuvertureCaisseVacation");
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_GUICHET);
            SmileUtil.testDomaineOuvert(structureDomaine);
            caisseForm.clearFormCaisse();
            if( paramAgence != null && paramAgence.getNumMatrUser() != null){
            caisseForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            caisseForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            caisseForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
            caisseForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            }
            
            caisseForm.setTitrePage("Ouverture Caisse de Vacation");
            //Remplir la liste des caisses existantes
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd =  new GetListeSessionJrnCaisseCmd();
            
            ListeCaisseStructureVo paramCaisseStructure = new ListeCaisseStructureVo();
            paramCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
            paramCaisseStructure.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
            paramCaisseStructure.setCodeStatus(Constants.STATUS_CAISSE_OUVERTE);
            paramCaisseStructure.setTypeCaisse(Constants.TYPE_CAISSE_PRINCIPALE);
            paramCaisseStructure = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(paramCaisseStructure);
          
            if (!paramCaisseStructure.hasError()) {
                if (paramCaisseStructure != null && 
                    paramCaisseStructure.getListeCaisseStructure().size()>0) {
                    caisseForm.setListeCaisses(paramCaisseStructure.getListeCaisseStructure());
                } 
            }
            if (caisseForm.getListeCaisses().size()!=0){
                SessionJrnCaisse sessionCaisse = (SessionJrnCaisse) caisseForm.getListeCaisses().get(0);
                caisseForm.getCaisseView().setNumCaisCais(sessionCaisse.getNumSeqSjc().toString());
                // ancien matricule
                 caisseForm.setAncienMatricule(sessionCaisse.getPersonnel().getNumMatrUser());
                
                // afficher le détails de la première session caisse de la liste
                 for (Iterator it1 = sessionCaisse.getDetailSessionCaisses().iterator(); it1.hasNext();) {
                      DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it1.next();
                      DetailSessionCaisseView detailSessionCaisseView =new DetailSessionCaisseView();
                      detailSessionCaisseView.setLibSiglDev(detailSessionCaisse.getDevise().getLibSiglDev());
                      detailSessionCaisseView.setCodDevDev(detailSessionCaisse.getDevise().getCodDevDev().toString());
                      Context context = ContextHandler.getContext();
                      CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                      String mdd = caisseDAO.formaterMnt(Double.valueOf(detailSessionCaisse.getMontDebDsc())+ Double.valueOf(detailSessionCaisse.getMontToteDsc())- Double.valueOf(detailSessionCaisse.getMontTotsDsc()),detailSessionCaisse.getDevise().getCodDevDev());
                      detailSessionCaisseView.setMontDebDsc(caisseDAO.formaterMnt(detailSessionCaisse.getMontDebDsc(),detailSessionCaisse.getDevise().getCodDevDev()));
                      detailSessionCaisseView.setCodNatuDsc(detailSessionCaisse.getCodNatuDsc());
                      detailSessionCaisseView.setMontFinDsc(mdd);
                      caisseForm.getListeDetailSessionCaisses().add(detailSessionCaisseView);
                  }
            }
            ///*** initialisation datagrid
            Datagrid dsc_datagrid = Datagrid.getInstance();
          //  caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
            dsc_datagrid.setData(new ArrayList());
            dsc_datagrid.setDataClass(DetailSessionCaisseView.class);
            dsc_datagrid.setData(caisseForm.getListeDetailSessionCaisses());
            caisseForm.setListeDetailSessionCaissesGrid(dsc_datagrid);
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'Ouverture Caisse de Vacation a été interrompue, Veuillez transmettre ce message à l'équipe maintenance informatique: ");
            text.append("Exception au niveau de l'agence:"); 
            text.append(caisseForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
           // erreur.setCode("298");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
       return mapping.findForward( "ouvrirCaisseVacation");
    }
    
    
    public ActionForward validerOuvertureCaisseDeVacation(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            Collection dgAdd = caisseForm.getListeDetailSessionCaissesGrid().getDataWithState("");
            Collection dgSel = caisseForm.getListeDetailSessionCaissesGrid().getDataWithState("selected");
            Collection listDetailSessionCaisse = new ArrayList();
            Collection listDetailSessionCaisseView = new ArrayList();
            listDetailSessionCaisseView.addAll(dgAdd);
            listDetailSessionCaisseView.addAll(dgSel);
            
            ///*** charger la sessionJrnCaisse choisis
            SessionJrnCaisse sessionJrnCaisse= new SessionJrnCaisse();
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd = new GetListeSessionJrnCaisseCmd();
            ListeCaisseStructureVo listeCaisseStructureVo = new ListeCaisseStructureVo();
            listeCaisseStructureVo.setNumSeqSjc(Long.valueOf(caisseForm.getNumeroSessionCaisseChoisi()));
            listeCaisseStructureVo = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(listeCaisseStructureVo);
            sessionJrnCaisse = (SessionJrnCaisse)listeCaisseStructureVo.getListeCaisseStructure().get(0);
            SessionJrnCaisse sessionJrnCaisseVac = new SessionJrnCaisse();
            SessionJrnCaissePrVac sessionJrnCaissePrVac = new SessionJrnCaissePrVac();
            
            Context context = ContextHandler.getContext();
            
            ///*** affecter le personnel a la nouvelle caisse de vacation
            Personnel personnel =new Personnel();
            personnel.setNumMatrUser(caisseForm.getMatrCaissier());
            sessionJrnCaisseVac.setPersonnel(personnel);
            
            for (Iterator it = listDetailSessionCaisseView.iterator(); it.hasNext(); ) {
                DetailSessionCaisse detailSessionCaisseVac = new DetailSessionCaisse();
                DetailSessionCaisseView detailSessionCaisseView = (DetailSessionCaisseView)it.next();
                if (detailSessionCaisseView.getMontAllDsc() != null && !detailSessionCaisseView.getMontAllDsc().equalsIgnoreCase("") ) { // verifier si les données obligatoires sont saisies
                    ///*** nbr decimal de la devise pour faire le formatage -1
                    CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                    Long nbrDecimal = caisseDAO.getNbrDecimalDevise(Long.valueOf(detailSessionCaisseView.getCodDevDev()));

                    detailSessionCaisseVac.setMontDebDsc(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(detailSessionCaisseView.getMontAllDsc())).doubleValue()*Math.pow(10,nbrDecimal.intValue())).longValue()));
                    detailSessionCaisseVac.setMontToteDsc(Long.valueOf(0));
                    detailSessionCaisseVac.setMontTotsDsc(Long.valueOf(0));
                    detailSessionCaisseVac.setCodNatuDsc(detailSessionCaisseView.getCodNatuDsc());
                    Devise devise = new Devise();
                    devise.setCodDevDev(Long.valueOf(detailSessionCaisseView.getCodDevDev()));
                    detailSessionCaisseVac.setDevise(devise);
                    listDetailSessionCaisse.add(detailSessionCaisseVac);
                    
                    ///*** MAJ des details de la caisse principale
                    for (Iterator it0 = sessionJrnCaisse.getDetailSessionCaisses().iterator(); it0.hasNext(); ) {
                        DetailSessionCaisse detailSessionCaisse =(DetailSessionCaisse)it0.next();
                        if (detailSessionCaisse.getDevise().getCodDevDev().intValue()==devise.getCodDevDev().intValue()){
                            detailSessionCaisse.setMontTotsDsc(Long.valueOf(new Long(new Double(((new Double(StrHandler.strWithoutBlanck(detailSessionCaisse.getMontTotsDsc().toString())).doubleValue())+(new Double(detailSessionCaisseVac.getMontDebDsc()).doubleValue())) ).longValue())));
                        }
                    }
                }
            }
            ///*** affectation de la caisse principale
            sessionJrnCaisse.setCodStatSjc(Constants.STATUS_CAISSE_FERMER);
            sessionJrnCaisse.setDatCloSjc(new Date());
            sessionJrnCaissePrVac.setSessionJrnCaissePr(sessionJrnCaisse); 
            ///*** affectation de la caisse de vacation
            sessionJrnCaissePrVac.setListDetailSessionCaisseVac(listDetailSessionCaisse);
            sessionJrnCaisseVac.setCodTypSjc(Constants.TYPE_CAISSE_VACATION);
            sessionJrnCaisseVac.setCodStatSjc(Constants.STATUS_CAISSE_OUVERTE);
            sessionJrnCaisseVac.setDatInitSjc(new Date());
            sessionJrnCaisseVac.setNumSeqSjc(null);
            sessionJrnCaisseVac.setDatCloSjc(null);
            sessionJrnCaisseVac.setJourneeCaisse(sessionJrnCaisse.getJourneeCaisse());
            sessionJrnCaissePrVac.setSessionJrnCaisseVac(sessionJrnCaisseVac);

            CreationCaisseVacationCmd creationCaisseVacationCmd = new CreationCaisseVacationCmd();
            sessionJrnCaissePrVac = (SessionJrnCaissePrVac)creationCaisseVacationCmd.execute(sessionJrnCaissePrVac);
            dgAdd.clear();
            dgSel.clear();
            caisseForm.setLibelleConfirmation("La Caisse de Vacation de la Caisse Principale N° "+sessionJrnCaisse.getJourneeCaisse().getCaisseStrc().getCaisseStrcId().getNumCaisCais()+" affectee au personnel N° "+caisseForm.getMatrCaissier()+"  est ouverte avec succes .");

         return mapping.findForward("confirmerCaisse");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("caisse");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

   
    
    
    public ActionForward validEnvoiInterCaisses(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        CaisseForm caisseForm = (CaisseForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            Collection dgAdd = caisseForm.getListeDetailSessionCaissesGrid().getDataWithState("");
            Collection dgSel = caisseForm.getListeDetailSessionCaissesGrid().getDataWithState("selected");
            ArrayList listMouvementSessionCaisse = new ArrayList();
            Collection listDetailSessionCaisseView = new ArrayList();
            listDetailSessionCaisseView.addAll(dgAdd);
            listDetailSessionCaisseView.addAll(dgSel);
            
            ///*** charger la sessionJrnCaisse choisis
            SessionJrnCaisse sessionJrnCaisse= new SessionJrnCaisse();
            GetListeSessionJrnCaisseCmd getListeSessionJrnCaisseCmd = new GetListeSessionJrnCaisseCmd();
            ListeCaisseStructureVo listeCaisseStructureVo = new ListeCaisseStructureVo();
            listeCaisseStructureVo.setNumSeqSjc(Long.valueOf(caisseForm.getNumeroSessionCaisseChoisi()));
            listeCaisseStructureVo = (ListeCaisseStructureVo)getListeSessionJrnCaisseCmd.execute(listeCaisseStructureVo);
            sessionJrnCaisse = (SessionJrnCaisse)listeCaisseStructureVo.getListeCaisseStructure().get(0);
            
            Context context = ContextHandler.getContext();
            
            ///*** affecter le personnel a la nouvelle caisse de vacation
            Personnel personnel =new Personnel();
            personnel.setNumMatrUser(caisseForm.getMatrCaissier());
            
            for (Iterator it = listDetailSessionCaisseView.iterator(); it.hasNext(); ) {
                MouvementSessionCaisse mouvementSessionCaisse = new MouvementSessionCaisse();
                DetailSessionCaisseView detailSessionCaisseView = (DetailSessionCaisseView)it.next();
                if (detailSessionCaisseView.getMontAllDsc() != null && !detailSessionCaisseView.getMontAllDsc().equalsIgnoreCase("")) { // verifier si les données obligatoires sont saisies
                    ///*** nbr decimal de la devise pour faire le formatage -1
                    CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
                    Long nbrDecimal = caisseDAO.getNbrDecimalDevise(Long.valueOf(detailSessionCaisseView.getCodDevDev()));

                    ///*** garnir le mouvement_session_caisse 
                    mouvementSessionCaisse.setMontMvtMvtc(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(detailSessionCaisseView.getMontAllDsc())).doubleValue()*Math.pow(10,nbrDecimal.intValue())).longValue()));
                    mouvementSessionCaisse.setDatMvtMvtc(sessionJrnCaisse.getJourneeCaisse().getJourneeCaisseId().getDatJrnJrn());
                    mouvementSessionCaisse.setCodSensMvtc(Constants.COD_SENS_DB);
                    
                    mouvementSessionCaisse.setSessionJrnCaisse(sessionJrnCaisse);
                    mouvementSessionCaisse.setDatSystMvtc(new Date());

                    ///*** gernir la caisse cible (envoi inter caisse)
                    CaisseStrc caisseStrc = new CaisseStrc();
                    CaisseStrcId caisseStrcId =new CaisseStrcId();                    
                    caisseStrcId.setCodStrcStrc(sessionJrnCaisse.getPersonnel().getStructure().getCodStrcStrc());

                    Tache tache = new Tache();
                    TacheId tacheId = new TacheId();

                    if (caisseForm.getCodetrait().equalsIgnoreCase("InterCaisse")){
                        mouvementSessionCaisse.setCodStatMvtc("0");///*** mouvement en attente
                        mouvementSessionCaisse.setStructure(sessionJrnCaisse.getPersonnel().getStructure());
                        mouvementSessionCaisse.setLibOperMvtc("Env Inter Cais");
                        tacheId.setCodTachTach(Constants.COD_TACH_ENV_INTER_CAISSE);
                        tacheId.setCodOperOper(Constants.COD_OPER_ENV_INTER_CAISSE);
                        caisseStrcId.setNumCaisCais(Long.valueOf(caisseForm.getNumCaisseCible()));///*** caisse cible selectionné
                        caisseForm.setLibelleConfirmation("Les Envois Inter-Caisses vers la Caisse N° "+caisseForm.getNumCaisseCible()+" sont effectués avec succes .");
                    }else{///*** PEC envoi extern
                        mouvementSessionCaisse.setCodStatMvtc(Constants.STATUS_PEC_ENVOI_EXTERN);///*** mouvement en attente de validation de pec envoi extern
                        Structure strc = new Structure();
                        strc.setCodStrcStrc(Long.valueOf(caisseForm.getCodeStructure()));
                        mouvementSessionCaisse.setStructure(strc);///*** structure cible                        
                        mouvementSessionCaisse.setLibOperMvtc("PEC Env Extern");
                        tacheId.setCodTachTach(Constants.COD_TACH_ENV_EXTERN_CAISSE);
                        tacheId.setCodOperOper(Constants.COD_OPER_ENV_EXTERN_CAISSE);
                        caisseStrcId.setNumCaisCais(sessionJrnCaisse.getJourneeCaisse().getJourneeCaisseId().getNumCaisCais());
                        caisseForm.setLibelleConfirmation("La PEC des Envois Externe vers la Structure N° "+caisseForm.getCodeStructure()+" sont effectués avec succes .");
                    }
                    
                    caisseStrc.setCaisseStrcId(caisseStrcId);
                    mouvementSessionCaisse.setCaisseStrc(caisseStrc);

                    tache.setTacheId(tacheId);
                    mouvementSessionCaisse.setTache(tache);

                    Devise devise = new Devise();
                    devise.setCodDevDev(Long.valueOf(detailSessionCaisseView.getCodDevDev()));
                    mouvementSessionCaisse.setDevise(devise);
                    listMouvementSessionCaisse.add(mouvementSessionCaisse);
                    
                }
            }

            InsertListMouvementSessionCaisseCmd insertListMouvementSessionCaisseCmd = new InsertListMouvementSessionCaisseCmd();
            Listes listeMouv = new Listes ();
            listeMouv.setList(listMouvementSessionCaisse);
            listeMouv = (Listes)insertListMouvementSessionCaisseCmd.execute(listeMouv);
            dgAdd.clear();
            dgSel.clear();

         return mapping.findForward("confirmerCaisse");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("caisse");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

       
    
    
    
    
    
    
    
}
package com.bna.smile.web.placement.actions;

import com.bna.commun.model.Client;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.MandPersOperPlacId;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.model.TypePiece;

import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;

import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.procuration.commande.ConsultEnveloppeRestanteCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListDemandesDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.PecSouscriptionPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderSouscriptionPlacementCmd;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.placement.forms.CreationContratPlacementForm;

import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.domaineplacement.commande.CreateContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetDemandeDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.GetDetailsOperationPlacCmd;
import com.bna.smile.model.domaineplacement.commande.UpdateContratPlacementCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import com.oxia.fwk.context.Context;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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

import org.springframework.orm.hibernate3.HibernateTemplate;

public class CreationContratPlacementAction extends DispatchAction {
    /**
     * <B> Action de la page  creationContratPlacement.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.placement.actions
     *
     * @version le 25/02/2009
     * @modify le 25/02/2009
     */
     
    private static final Logger logger = Logger.getLogger(CreationContratPlacementAction.class);
  
    public  ActionForward initSouscriPlacement(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
         SessionUtil sessionUtil =new SessionUtil();
         String forward = new String();   
         ContratPlacementView contratPlacementView = new ContratPlacementView();
         
         try {
             //Suppression des anciens Bean de type Form de la session, SAUF "CreationContratPlacementForm"
             sessionUtil.removeSession(request,"creationContratPlacementForm"); 
             //---affectation du parametre de session code agence, matricule personnel et date du jour
             ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
            SmileUtil.testDomaineOuvert(structureDomaine);
             creationContratPlacementForm.clearForm();
             creationContratPlacementForm.clearPouvoir();
             if( paramAgence != null){
             creationContratPlacementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
             creationContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
             creationContratPlacementForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
             creationContratPlacementForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
             }
             creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC.toString());
             
                if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")) {
                     creationContratPlacementForm.setTitrePage("Souscription Contrat Placement");
                     
                     //Remplir la liste des demandes decisions valides
                     GetListDemandesDecisionCmd getListDemandesDecisionCmd =  new GetListDemandesDecisionCmd();
                     Listes listDemandeDecision = new Listes();
                     ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                     String [] tabEtat = new String []{Constants.ETAT_DEM_DECIS_VALIDE};
                     paramDemandeDecision.setCodEtatDemd(tabEtat);
                     paramDemandeDecision.setStructureDemande(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
                     
                     paramDemandeDecision.setNatureDemande(Constants.NATURE_DEMD_SOUSC);
                     paramDemandeDecision.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
                     
                     listDemandeDecision = (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);
                     
                     if( listDemandeDecision != null ){
                        if( listDemandeDecision.getList() != null ){
                         Collection listDdeDecisionView =    traiterListedemandesDecision(listDemandeDecision.getList(),creationContratPlacementForm);
                         creationContratPlacementForm.setListeDemandesValides(listDdeDecisionView);
                         }
                        }
                 forward = "initCreatPlac";
//----------------------------------------------------------------------------------------------------------------valid
                 }else if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")) {
                         creationContratPlacementForm.setTitrePage("Validation Contrat Placement");
                         
                         //Remplir la liste des contrat placements en attente
                         
                         GetListContratsPlacementCmd getListContratsPlacementCmd = new GetListContratsPlacementCmd();
                         ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                         String [] tabEtat = new String []{Constants.ETAT_CONTRAT_PLAC_ATTENTE};
                         paramDemandeDecision.setCodEtatDemd(tabEtat);
                         Long [] tabStruct = new Long []{Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence())};
                         paramDemandeDecision.setCodStrcStrc(tabStruct);
                         paramDemandeDecision.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
                         Listes listContratPlacement = new Listes();
                         
                         listContratPlacement = (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
                         
                         if( listContratPlacement != null ){
                            if(  listContratPlacement.getList() != null){
                             Collection listCplaView =    traiterListeContratPlacment(creationContratPlacementForm, listContratPlacement.getList());
                             creationContratPlacementForm.setListeContratPlacAtt(listCplaView);
                             }
                            }
                          forward = "initCreatPlac";
//-----------------------------------------------------------------------------------------------------------------valid
                     }
                 
        
             return mapping.findForward(forward);
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("L'initialisation de la création du contrat Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
             text.append("Exception au niveau de l'agence:"); 
             text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :"); text.append(e.toString());
             erreur.setCode("298");
             erreur.setDescription(text.toString());
             logger.error(text.toString(),e); 
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }

     }
    public  ActionForward initRenouvellementPlac(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
         SessionUtil sessionUtil =new SessionUtil();
              
         try {
             //Suppression des anciens Bean de type Form de la session, SAUF "CreationContratPlacementForm"
             sessionUtil.removeSession(request,"creationContratPlacementForm"); 
             //---affectation du parametre de session code agence, matricule personnel et date du jour
             ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
             
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
             SmileUtil.testDomaineOuvert(structureDomaine);
             creationContratPlacementForm.clearForm();
             creationContratPlacementForm.clearPouvoir();
             if( paramAgence != null){
             creationContratPlacementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
             creationContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
             creationContratPlacementForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
             creationContratPlacementForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
             }
        
            if(creationContratPlacementForm.getNatureOp().equals("renouv")){
                 if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PECREN")) {
                     creationContratPlacementForm.setTitrePage("P.E.C Renouvellement Contrat Placement");
                     
                     //Remplir la liste des demandes de renouvellement valides et seulement celles aprés échéances
                     GetListDemandesDecisionCmd getListDemandesDecisionCmd =  new GetListDemandesDecisionCmd();
                     Listes listDemandeDecision = new Listes();
                     ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                     String [] tabEtat = new String []{Constants.ETAT_DEM_DECIS_VALIDE};
                     paramDemandeDecision.setCodEtatDemd(tabEtat);
                     paramDemandeDecision.setStructureDemande(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
                     
                     paramDemandeDecision.setNatureDemande(Constants.NATURE_DEMD_RENOUV);
                     paramDemandeDecision.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable())); 
                     listDemandeDecision = (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);
                     
                     if( listDemandeDecision != null ){
                        if( listDemandeDecision.getList() != null ){
                         Collection listDdeDecisionView =    traiterListedemandesRenouvellement(listDemandeDecision.getList(),creationContratPlacementForm);
                         creationContratPlacementForm.setListeDemandesValides(listDdeDecisionView);
                         }
                        }
              
    //----------------------------------------------------------------------------------------------------------------valid
                 }else if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALIDREN")) {
                         creationContratPlacementForm.setTitrePage("Validation Renouvellement Contrat Placement");
                         
                         //Remplir la liste des contrat placements en attente
                         
                         GetListContratsPlacementCmd getListContratsPlacementCmd = new GetListContratsPlacementCmd();
                         ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                         String [] tabEtat = new String []{Constants.ETAT_CPLAC_ATT_RENOUVEL};
                         paramDemandeDecision.setCodEtatDemd(tabEtat);
                         
                         Long [] tabStruct = new Long []{Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence())};
                         paramDemandeDecision.setCodStrcStrc(tabStruct);
                         
                         paramDemandeDecision.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
                         Listes listContratPlacement = new Listes();
                         
                         listContratPlacement = (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
                         
                         if( listContratPlacement != null ){
                            if(  listContratPlacement.getList() != null){
                             Collection listCplaView =    traiterListeContratPlacment(creationContratPlacementForm, listContratPlacement.getList());
                             creationContratPlacementForm.setListeContratPlacAtt(listCplaView);
                              }
                            }
                      
    //-----------------------------------------------------------------------------------------------------------------valid
                     }
            }else if(creationContratPlacementForm.getNatureOp().equals("forcage")){
                // Forçage renouvellement
                 creationContratPlacementForm.setTitrePage("Forçage Renouvellement Contrat Placement");
                 
                //Remplir la liste des demandes de renouvellement valides et non traitées par batch (provision non disponible)
                GetListDemandesDecisionCmd getListDemandesDecisionCmd =  new GetListDemandesDecisionCmd();
                Listes listDemandeDecision = new Listes();
                ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                String [] tabEtat = new String []{Constants.ETAT_DEM_RENOUV_FORCE};
                paramDemandeDecision.setCodEtatDemd(tabEtat);
                paramDemandeDecision.setStructureDemande(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
                
                listDemandeDecision = (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);
                
                if( listDemandeDecision != null ){
                   if( listDemandeDecision.getList() != null ){
                    Collection listDdeDecisionView =    traiterDemandesRenouvellement(listDemandeDecision.getList(),creationContratPlacementForm);
                    creationContratPlacementForm.setListeDemandesValides(listDdeDecisionView);
                    }
                   }
               }else {
                   // annulation demande renouvellement
                    creationContratPlacementForm.setTitrePage("Rejet Renouvellement Contrat Placement");
                    
                    //Remplir la liste des demandes de renouvellement valides et non encor traitées, autre que BC/BCDC??
                    GetListDemandesDecisionCmd getListDemandesDecisionCmd =  new GetListDemandesDecisionCmd();
                    Listes listDemandeDecision = new Listes();
                    ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
                    String [] tabEtat = new String []{Constants.ETAT_DEM_DECIS_VALIDE};
                    paramDemandeDecision.setCodEtatDemd(tabEtat);
                    paramDemandeDecision.setStructureDemande(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
                    paramDemandeDecision.setNatureDemande(Constants.NATURE_DEMD_RENOUV);
                    
                    listDemandeDecision = (Listes)getListDemandesDecisionCmd.execute(paramDemandeDecision);
                    
                    if( listDemandeDecision != null ){
                      if( listDemandeDecision.getList() != null ){
                       Collection listDdeDecisionView =    traiterDemandesRenouvellement(listDemandeDecision.getList(),creationContratPlacementForm);
                       creationContratPlacementForm.setListeDemandesValides(listDdeDecisionView);
                       }
                      }
               }
           return mapping.findForward("initRenouvelPlac");
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("L'initialisation du renouvellement du contrat Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
             text.append("Exception au niveau de l'agence:"); 
             text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :"); text.append(e.toString());
             erreur.setCode("317");
             erreur.setDescription(text.toString());
             logger.error(text.toString(),e); 
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }

     }
    /**
     * Fonction qui retourne la liste des demandes à valides (View)
     */

public void traiterCB_contratPlacement(ContratPlacementView contratPlacementView){
    TraitementConditionBanque traitCB_souscri = new TraitementConditionBanque();
    TraitementConditionBanque traitCB_interet = new TraitementConditionBanque();
    TraitementConditionBanque traitCB_general = new TraitementConditionBanque();
    try{  
    if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_GENERAL)){
      if(contratPlacementView.getCodPrdPrd() != null){
        
        if(contratPlacementView.getCodPintCpla().equals("PRE")){
        // appel conditions de banque general opération versement interet pré compté (320)
         traitCB_interet = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
          }else{
                // appel conditions de banque general opération versement interet post compté (613)
                 traitCB_interet = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
            }
            contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
            contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
            // ajout sign e marge !!
            contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
            contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
        //    contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
            contratPlacementView.setTauGeneral(traitCB_interet.getTauxInteret());
        // appel conditions de banque general opération souscription
          traitCB_souscri = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd(), Constants.COD_OPER_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
          contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
        } 
    }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_FAVEUR)){
        // appel conditions de banque opération souscription
        traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.COD_OPER_SOUSC_PLAC.toString());
        contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
        if(contratPlacementView.getCodPintCpla().equals("PRE")){
        // appel conditions de banque general opération versement interet pré compté (320)
         traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
               // appel conditions de banque general 
         traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
           }else{
                // appel conditions de banque general opération versement interet post compté (613)
                 traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                // appel conditions de banque general
                traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
            }
        contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
        contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
        contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
      }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_INDEXE)){
          // demande avec taux indexé au TMM
          // appel conditions de banque opération souscription
          traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.COD_OPER_SOUSC_PLAC.toString());
          contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
          if(contratPlacementView.getCodPintCpla().equals("PRE")){
          // appel conditions de banque opération versement interet pré compté (320)
               traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                // appel conditions de banque general 
                traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
            }else{
                  // appel conditions de banque opération versement interet post compté (613)
                 traitCB_interet =  appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                  // appel conditions de banque general
                  traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                  }
              contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
              contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
              // ajout sign e marge !!
              contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
              contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
           //   contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
              contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
      }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_PREFERENTIEL)){
          // demande avec taux indexé au TMM
          // appel conditions de banque opération souscription
          traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.COD_OPER_SOUSC_PLAC.toString());
          contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
          if(contratPlacementView.getCodPintCpla().equals("PRE")){
          // appel conditions de banque opération versement interet pré compté (320)
               traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                // appel conditions de banque general 
                traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
            }else{
                  // appel conditions de banque opération versement interet post compté (613)
                 traitCB_interet =  appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                  // appel conditions de banque general
                  traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                  }
              contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
              contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
              // ajout sign e marge !!
              contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
              contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
           //   contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
              contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
      }
      /*
     if(contratPlacementView.getPrdCcptCpla().equals(new String("0121"))){
             contratPlacementView.setDateValeurTaux(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDayAfterNdays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7)));
             contratPlacementView.setDateValeur(DateHandler.dateToStr(CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7)));
         }*/
    } catch (Exception e) {
               logger.error("Exception Methode : traiterCB_contratPlacement:  ",e);  
               throw new RuntimeException(e);               
        } 
    traitCB_souscri = null;
    traitCB_interet = null;
    traitCB_general = null;
}

    public void traiterCB_renouvelPlacement(ContratPlacementView contratPlacementView, ActionForm form){
        TraitementConditionBanque traitCB_souscri = new TraitementConditionBanque();
        TraitementConditionBanque traitCB_interet = new TraitementConditionBanque();
        TraitementConditionBanque traitCB_general = new TraitementConditionBanque();
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        try{  
            if(contratPlacementView.getTypeRenouvellement().equals("2")){
                creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.OPER_RENOUVEL_PLAC_APRE.toString());
            }else{
                creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.OPER_RENOUVEL_PLAC_AVAN.toString()); 
            }
        if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_GENERAL)){
          if(contratPlacementView.getCodPrdPrd() != null){
            
            if(contratPlacementView.getCodPintCpla().equals("PRE")){
            // appel conditions de banque general opération versement interet pré compté (320)
             traitCB_interet = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
              }else{
                    // appel conditions de banque general opération versement interet post compté (613)
                     traitCB_interet = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                }
                contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
                contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
                // ajout sign e marge !!
                contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
                contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
            //    contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
                contratPlacementView.setTauGeneral(traitCB_interet.getTauxInteret());
            // appel conditions de banque general opération souscription
             if(contratPlacementView.getTypeRenouvellement().equals("2")){
              traitCB_souscri = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd(), Constants.OPER_RENOUVEL_PLAC_APRE.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
             }else {
                 traitCB_souscri = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd(), Constants.OPER_RENOUVEL_PLAC_AVAN.toString() , contratPlacementView.getDatEchePlacRenouvele(),contratPlacementView.getPrdCcptCpla());
             }
              contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
            } 
        }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_FAVEUR)){
            // appel conditions de banque opération souscription
          if(contratPlacementView.getTypeRenouvellement().equals("2")){
            traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_APRE.toString());
          }else {
              traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_AVAN.toString()); 
          }
            contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
            if(contratPlacementView.getCodPintCpla().equals("PRE")){
            // appel conditions de banque general opération versement interet pré compté (320)
             traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                   // appel conditions de banque general 
             traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
               }else{
                    // appel conditions de banque general opération versement interet post compté (613)
                     traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                    // appel conditions de banque general
                    traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                }
            contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
            contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
            contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
          }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_INDEXE)){
              // demande avec taux indexé au TMM
              // appel conditions de banque opération souscription
               if(contratPlacementView.getTypeRenouvellement().equals("2")){
                 traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_APRE.toString());
               }else {
                   traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_AVAN.toString()); 
               }
              contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
              if(contratPlacementView.getCodPintCpla().equals("PRE")){
              // appel conditions de banque opération versement interet pré compté (320)
                   traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                    // appel conditions de banque general 
                    traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                }else{
                      // appel conditions de banque opération versement interet post compté (613)
                     traitCB_interet =  appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                      // appel conditions de banque general
                      traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                      }
                  contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
                  contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
                  // ajout sign e marge !!
                  contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
                  contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
               //   contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
                  contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
          }else if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_PREFERENTIEL)){
              // demande avec taux indexé au TMM
              // appel conditions de banque opération souscription
               if(contratPlacementView.getTypeRenouvellement().equals("2")){
                 traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_APRE.toString());
               }else {
                   traitCB_souscri = appelCB_Cpla(contratPlacementView,Constants.OPER_RENOUVEL_PLAC_AVAN.toString()); 
               }
              contratPlacementView.setDateValeur(traitCB_souscri.getDatevaleur());
              if(contratPlacementView.getCodPintCpla().equals("PRE")){
              // appel conditions de banque opération versement interet pré compté (320)
                   traitCB_interet = appelCB_Cpla(contratPlacementView,Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                    // appel conditions de banque general 
                    traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                }else{
                      // appel conditions de banque opération versement interet post compté (613)
                     traitCB_interet =  appelCB_Cpla(contratPlacementView,Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                      // appel conditions de banque general
                      traitCB_general = appelConditionBanqueGeneral(contratPlacementView.getCodPrdPrd() , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , contratPlacementView.getDateSouscription(),contratPlacementView.getPrdCcptCpla());
                      }
                  contratPlacementView.setNumTauiCpla(traitCB_interet.getTauxInteret());
                  contratPlacementView.setDateValeurTaux(traitCB_interet.getDatevaleur());
                  // ajout sign e marge !!
                  contratPlacementView.setSigneMargeCpla(traitCB_interet.getSigneMarge());
                  contratPlacementView.setNumMargeCpla(String.valueOf(traitCB_interet.getValeurMarge()));
               //   contratPlacementView.setTmm(String.valueOf(traitCB_interet.getTmm()));
                  contratPlacementView.setTauGeneral(traitCB_general.getTauxInteret());
          }
         /*   if(contratPlacementView.getPrdCcptCpla().equals(new String("0121"))){
                    contratPlacementView.setDateValeurTaux(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDayAfterNdays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7)));
                    contratPlacementView.setDateValeur(DateHandler.dateToStr(CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7)));
                }*/
        } catch (Exception e) {
                   logger.error("Exception Methode : traiterCB_renouvelPlacement:  ",e);  
                   throw new RuntimeException(e);               
            } 
        traitCB_souscri = null;
        traitCB_interet = null;
        traitCB_general = null;
    }

public DemandeDecisionView traiterDemandeDecision(DemandeDecision demandeDecision, ActionForm form){
    
    DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
    CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
    demandeDecisionView.setDemandeDecision(demandeDecision);

    demandeDecisionView.setNumRefdDemd(demandeDecision.getNumRefdDemd().toString());
    demandeDecisionView.setDatCreDemd(DateHandler.dateToStr(demandeDecision.getDatCreDemd()));
    demandeDecisionView.setMontPlaDemd(StrHandler.formatmnt(Math.abs(demandeDecision.getMontPlaDemd().doubleValue())));
    demandeDecisionView.setCodTypPaiement(demandeDecisionView.getDemandeDecision().getCodPintDemd());
    demandeDecisionView.setDateValeur(DateHandler.dateToStr(demandeDecision.getDatValDemd()));
    if(demandeDecision.getCodSbdvDemd() != null){
      if(demandeDecision.getCodSbdvDemd().equals("1")){ // demande sous bonne date valeur
       demandeDecisionView.setCodSbdvDemd("1");
      }else {
          demandeDecisionView.setCodSbdvDemd("0");
      }
    }else {
        demandeDecisionView.setCodSbdvDemd("0");
    }
    Date datePermise;
    String moisCompt=creationContratPlacementForm.getInitialisationView().getDateComptable().substring(3,5);
    String moisValeur=DateHandler.dateToStr(demandeDecision.getDatValDemd()).substring(3,5);
    if(Long.valueOf(moisValeur).intValue() != Long.valueOf(moisCompt).intValue()){
       demandeDecisionView.setCodSbdvDemd("2");
       }
   
       if(demandeDecision.getCodSbdvDemd() != null){
            if(demandeDecision.getCodSbdvDemd().equals("1")){ // demande sous bonne date valeur
             datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-10);
          //   System.out.println("datePermise    ::::   "+DateHandler.dateToStr(datePermise));
               if(datePermise.compareTo(demandeDecision.getDatValDemd()) <= 0 ){
                       // date valeur < = (date permise == comptable - 3 jours ouvrables)
                      demandeDecisionView.setBoolDatePermise(true);
                    }else {
                        demandeDecisionView.setBoolDatePermise(false);
                    }
                
              }else {
                if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_GENERAL)){
                        datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-1);
                            if(datePermise.compareTo(demandeDecision.getDatValDemd()) < 0 ){
                                   demandeDecisionView.setBoolDatePermise(true);  
                              }else {
                                  demandeDecisionView.setBoolDatePermise(false); 
                              }}else{
                          datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-2);
                            if(datePermise.compareTo(demandeDecision.getDatValDemd()) <= 0 ){
                                    // date valeur < = (date permise == comptable - 2 jours ouvrables)
                                   demandeDecisionView.setBoolDatePermise(true);
                                 }else {
                                     demandeDecisionView.setBoolDatePermise(false);
                                 }
                      }
              }
           }else {
                    if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_GENERAL)){
                            datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-1);
                                if(datePermise.compareTo(demandeDecision.getDatValDemd()) <= 0 ){
                                       demandeDecisionView.setBoolDatePermise(true);  
                                  }else {
                                      demandeDecisionView.setBoolDatePermise(false); 
                                  }
                                  }else{
                                    datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-2);
                                        if(datePermise.compareTo(demandeDecision.getDatValDemd()) <= 0 ){
                                               demandeDecisionView.setBoolDatePermise(true);  
                                          }else {
                                              demandeDecisionView.setBoolDatePermise(false); 
                                          }
                        }         
                }
  
    datePermise = null;  
    if(demandeDecision.getContratCpt() != null){
    demandeDecisionView.setStrcCcptDemd(StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
    demandeDecisionView.setPrdCcptDemd(StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
    demandeDecisionView.setNumCcptDemd(StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
    
    // Verifier Si l etat du contrat compte est toujours valide et vérifier la provision (le solde)
    ContratCpt ccpt = demandeDecision.getContratCpt();
    if(ccpt != null){
      demandeDecisionView.setEtatCcptDemd(ccpt.getCodEtatCcpt());
      Personne pers = ccpt.getClient().getPersonne();
      if(pers.getTypePiece().getCodTpceTpce().equals(Long.valueOf(9))){ // personne morale
        demandeDecisionView.setMatriculeFiscal(ccpt.getClient().getNumFiscClt());
      }else {
          demandeDecisionView.setMatriculeFiscal("PersonnePHYSIQUE"); 
      }
    /*  if(pers.getAgentEconomique()!= null){
          pers.getAgentEconomique().getLibAgEcon();
          demandeDecisionView.setHasCodEconomicAgent(true);
      }else {
          demandeDecisionView.setHasCodEconomicAgent(false);
      }*/
       if(pers.getCategoriePersonne() != null){
            demandeDecisionView.setCategorieDemandeur(pers.getCategoriePersonne().getLibCatpCatp());
              if(pers.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATP_COTITU)){ // entité cotitulaire 
               demandeDecisionView.setCodPieceClient(Constants.COD_NUM_ORDRE.toString());
               demandeDecisionView.setNumPieceClient(ccpt.getClient().getNumSeqPers().toString());
                  }else {
                      demandeDecisionView.setCodPieceClient(pers.getTypePiece().getCodTpceTpce().toString());
                      demandeDecisionView.setNumPieceClient(pers.getNumPcePers()); 
                  }
             }else {
                 logger.debug("pers.getCategoriePersonne() == null");
             }
      
          if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { // cas RCS affichage de libSiglPers 
              demandeDecisionView.setNomClient(pers.getLibSiglPers());
              demandeDecisionView.setPrenomClient(pers.getNomRsPers());
             } else {
                    demandeDecisionView.setNomClient(pers.getNomNomPers());
                    demandeDecisionView.setPrenomClient(pers.getNomPrnPers());
                }
      
      if(ccpt.getMontSoldCcpt() < 0 ){
          demandeDecisionView.setSoldeCompte(StrHandler.formatmnt(Math.abs(ccpt.getMontSoldCcpt().doubleValue()))+" DB");
      }else{
          demandeDecisionView.setSoldeCompte(StrHandler.formatmnt(Math.abs(ccpt.getMontSoldCcpt().doubleValue()))+" CR");
      }
     
     if(demandeDecision.getMontPlaDemd() <= ccpt.getProvision(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()))){
           demandeDecisionView.setBoolProvision(false);
            }else {
                demandeDecisionView.setBoolProvision(true);
            }
     if(ccpt.getProduit().getCodTprdPrd().equals("DCV")
         || ccpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(103))
         || ccpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(121))
         ){
           demandeDecisionView.setBoolNotForcing(true); // aucun forçage de l'opération pr les comptes 103 , 121 et en DC
       }else {
           demandeDecisionView.setBoolNotForcing(false);
       }
          ccpt = null;
          pers = null; 
      }//---------------------------------------------------------------------------------------
       
    }else { // la demande ne dispose pas de contrat compte
     demandeDecisionView.setStrcCcptDemd(creationContratPlacementForm.getInitialisationView().getCodeAgence());
    }
    demandeDecisionView.setNumDureDemd(demandeDecision.getNumDureDemd().toString());
    demandeDecisionView.setLibPrdPlc(demandeDecision.getProduitPlacement().getLibPrdPlc());
    demandeDecisionView.setCodPrdPrd(demandeDecision.getProduitPlacement().getCodPrdPlc().toString());
    demandeDecisionView.setNomNomDemd(demandeDecision.getNomNomDemd());
    demandeDecisionView.setNomPrnDemd(demandeDecision.getNomPrnDemd());
     // si la demande a été validée par la DT, le taux accordée est le taux offert par la DT
    if(demandeDecision.getStructure().getCodStrcStrc().equals(Constants.COD_DIR_TRESORERIE)){
        demandeDecisionView.setTauxAccorde(demandeDecision.getNumToffDemd().toString());
        }else { //sinon le taux accordé est le taux général
            demandeDecisionView.setTauxAccorde(demandeDecision.getNumTaugDemd().toString());
        }
    
    demandeDecisionView.setTauxIrc(demandeDecision.getNumTircDemd().toString());
    if(demandeDecision.getCodFavDemd() != null){
    if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_GENERAL)){
            demandeDecisionView.setCodeTypeFaveur(Constants.COD_FAV_GENERAL);
        }else if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_FAVEUR)){
              demandeDecisionView.setCodeTypeFaveur(Constants.COD_FAV_FAVEUR);
            }else if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_INDEXE)){
                demandeDecisionView.setCodeTypeFaveur(Constants.COD_FAV_INDEXE);
                demandeDecisionView.setSignMarge(demandeDecision.getCodMargDemd());
                demandeDecisionView.setTauxMarge(demandeDecision.getNumMargDemd().toString());
            }else if(demandeDecision.getCodFavDemd().equals(Constants.COD_FAV_PREFERENTIEL)){
              demandeDecisionView.setCodeTypeFaveur(Constants.COD_FAV_PREFERENTIEL);
            }
    }else{
       logger.debug("demandeDecision.getCodFavDemd() == null pour la demande "+demandeDecision.getNumRefdDemd().toString());
    }
    
    return demandeDecisionView;
}

public Collection traiterListedemandesDecision(List listDemandes, ActionForm form) {
  Collection listDemandeDecisionView = new ArrayList();
  CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
    
  try{
        if(listDemandes != null ){
        if(listDemandes.size() != 0){
        
         for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
           DemandeDecision demandeDecision = (DemandeDecision)it.next();
            if( demandeDecision != null ){
                DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
               
                demandeDecisionView = traiterDemandeDecision(demandeDecision,creationContratPlacementForm);
              
                listDemandeDecisionView.add(demandeDecisionView);
                demandeDecisionView = null;
                 
            }
          }
         }
        }
     
    } catch (Exception e) {
               logger.error("Exception Methode : traiterListedemandesDecision:  ",e);  
               throw new RuntimeException(e);               
        } 
        return listDemandeDecisionView;
    }


 public Collection traiterListedemandesRenouvellement(List listDemandes, ActionForm form) {
      Collection listDemandeDecisionView = new ArrayList();
      CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        
      try{
            if(listDemandes != null ){
            if(listDemandes.size() != 0){
            
             for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
               DemandeDecision demandeDecision = (DemandeDecision)it.next();
                if( demandeDecision != null ){
                    DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
                    if ((demandeDecision.getCodTyprDemd().equals(Long.valueOf("1")) 
                          && ( demandeDecision.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                         || demandeDecision.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC))) // demande renouv. av. eche, prd= BC/BCDC
                         || demandeDecision.getCodTyprDemd().equals(Long.valueOf("2")) // demand renouv. ap. eche, autre prd
                        ){
                    demandeDecisionView = traiterDemandeDecision(demandeDecision,creationContratPlacementForm);
                    demandeDecisionView.getContratPlacement().setNumSeqCpla(demandeDecision.getContratPlacement().getNumSeqCpla());
                    demandeDecisionView.setDateEchAncienContrat(DateHandler.dateToStr(demandeDecision.getContratPlacement().getDatEcheCpla()));
                          // la vérification que l'anicen contrat de placement est liquidé se fait au niveau de la demande
                   if(!demandeDecision.getContratPlacement().getCodEtatCpla().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                       demandeDecisionView.setLibEtatDemd(Constants.ETAT_CONTRAT_PLAC_ECHULIQ);
                   }

                    listDemandeDecisionView.add(demandeDecisionView);
                    demandeDecisionView = null;
                  }   
                }
              }
             }
            }
         
        } catch (Exception e) {
                   logger.error("Exception Methode : traiterListedemandesDecision:  ",e);  
                   throw new RuntimeException(e);               
            } 
            return listDemandeDecisionView;
        }

    public Collection traiterDemandesRenouvellement(List listDemandes, ActionForm form) {
         Collection listDemandeDecisionView = new ArrayList();
         CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
           
         try{
               if(listDemandes != null ){
               if(listDemandes.size() != 0){
               
                for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                  DemandeDecision demandeDecision = (DemandeDecision)it.next();
                   if( demandeDecision != null ){
                       DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
                     
                       demandeDecisionView = traiterDemandeDecision(demandeDecision,creationContratPlacementForm);
                       demandeDecisionView.getContratPlacement().setNumSeqCpla(demandeDecision.getContratPlacement().getNumSeqCpla());
                       demandeDecisionView.setDateEchAncienContrat(DateHandler.dateToStr(demandeDecision.getContratPlacement().getDatEcheCpla()));
                             // la vérification que l'anicen contrat de placement est liquidé se fait au niveau de la demande
                      if(!demandeDecision.getContratPlacement().getCodEtatCpla().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                          demandeDecisionView.setLibEtatDemd(Constants.ETAT_CONTRAT_PLAC_ECHULIQ);
                      }
                    listDemandeDecisionView.add(demandeDecisionView);
                    demandeDecisionView = null;
                   }
                 }
                }
               }
            
           } catch (Exception e) {
                      logger.error("Exception Methode : traiterListedemandesDecision:  ",e);  
                      throw new RuntimeException(e);               
               } 
               return listDemandeDecisionView;
           }
 public TraitementConditionBanque appelConditionBanqueGeneral(String codeProduit , String codeOperation , String dateReference,String codePrdCompte){
     
     TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
     
     StringBuffer str = new StringBuffer();
     str.append("Exception Methode : appelConditionBanqueGeneral: ");
   try{  
    
         traitementConditionBanque.setCodPrdPrd(codeProduit);
         traitementConditionBanque.setCodOperOper(codeOperation);
     
         traitementConditionBanque.setNumCcptCcpt("");
         traitementConditionBanque.setCodStrcStrc("");
         traitementConditionBanque.setCodPrdCpt(codePrdCompte);
         traitementConditionBanque.setCodTpceTpce("");
         traitementConditionBanque.setNumPcePers("");
         traitementConditionBanque.setIdContrat("");
         traitementConditionBanque.setMontant("");
         traitementConditionBanque.setNbUnites("");
         traitementConditionBanque.setDateReference(dateReference);
           
     traitementConditionBanque.getCB();
     } catch (Exception e) {
                logger.error(str.toString(),e);  
                throw new RuntimeException(e);               
         } 
     return traitementConditionBanque;
 }
public TraitementConditionBanque appelConditionBanque(DemandeDecisionView demandeDecisionView, String codeOp){
    
    TraitementConditionBanque traitementConditionBanque= new TraitementConditionBanque();
    
    DemandeDecisionView dmdeDecisionView = demandeDecisionView;
    StringBuffer str = new StringBuffer();
    str.append("Exception Methode : appelConditionBanque: ");
  try{  
 
    traitementConditionBanque.setCodPrdPrd(dmdeDecisionView.getCodPrdPrd());
    traitementConditionBanque.setCodOperOper(codeOp);
    traitementConditionBanque.setNumCcptCcpt(dmdeDecisionView.getNumCcptDemd());
    traitementConditionBanque.setCodStrcStrc(dmdeDecisionView.getStrcCcptDemd());
    traitementConditionBanque.setCodPrdCpt(dmdeDecisionView.getPrdCcptDemd());
    traitementConditionBanque.setCodTpceTpce(dmdeDecisionView.getCodPieceClient());
    traitementConditionBanque.setNumPcePers(dmdeDecisionView.getNumPieceClient());
    traitementConditionBanque.setIdContrat(dmdeDecisionView.getNumRefdDemd().toString());
    traitementConditionBanque.setMontant(dmdeDecisionView.getMontPlaDemd());
    traitementConditionBanque.setNbUnites(dmdeDecisionView.getNumDureDemd());
    if(codeOp.equals(Constants.OPER_RENOUVEL_PLAC_AVAN.toString())){
     traitementConditionBanque.setDateReference(dmdeDecisionView.getDateEchAncienContrat());
    }else {
        traitementConditionBanque.setDateReference(dmdeDecisionView.getDateValeur());
    }
    traitementConditionBanque.getCB();
    } catch (Exception e) {
               logger.error(str.toString(),e);  
               throw new RuntimeException(e);               
        } 
    return traitementConditionBanque;
}

    public TraitementConditionBanque appelCB_Cpla(ContratPlacementView contratPlacementView, String codeOp){
        
        TraitementConditionBanque traitementConditionBanque= new TraitementConditionBanque();
        
        ContratPlacementView cPlaView = contratPlacementView;
        StringBuffer str = new StringBuffer();
        str.append("Exception Methode : appelConditionBanque: ");
      try{  
        
        traitementConditionBanque.setCodPrdPrd(cPlaView.getCodPrdPrd());
        traitementConditionBanque.setCodOperOper(codeOp);
        traitementConditionBanque.setNumCcptCcpt(cPlaView.getNumCcptCpla());
        traitementConditionBanque.setCodStrcStrc(cPlaView.getStrcCcptCpla());
        traitementConditionBanque.setCodPrdCpt(cPlaView.getPrdCcptCpla());
        traitementConditionBanque.setCodTpceTpce(cPlaView.getCodPieceClient());
        traitementConditionBanque.setNumPcePers(cPlaView.getNumPieceClient());
        traitementConditionBanque.setIdContrat(cPlaView.getRefDossierCB());
        traitementConditionBanque.setMontant(cPlaView.getMontCapCpla());
        traitementConditionBanque.setNbUnites(cPlaView.getDuree());
        if(codeOp.equals(Constants.OPER_RENOUVEL_PLAC_AVAN.toString())){
            traitementConditionBanque.setDateReference(cPlaView.getDatEchePlacRenouvele());
        }else{
        traitementConditionBanque.setDateReference(cPlaView.getDateSouscription());
        }
        traitementConditionBanque.getCB();
        } catch (Exception e) {
                   logger.error(str.toString(),e);  
                   throw new RuntimeException(e);               
            } 
        return traitementConditionBanque;
    }
public  ActionForward afficherDemandeChoisie(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, 
                               HttpServletResponse response) throws IOException, 
                                                                    ServletException {

         ActionMessages actionMessages = new ActionMessages();
         CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
         ContratPlacementView contratPlacementView = new ContratPlacementView();
        
         try {
             String forward =new String();
             Collection<DemandeDecisionView> listDdeValide;
             listDdeValide = creationContratPlacementForm.getListeDemandesValides();
             
             creationContratPlacementForm.clearPouvoir();
              for(DemandeDecisionView demandeDecisionView : listDdeValide ){
                 if(demandeDecisionView != (null)){
                    if(demandeDecisionView.getNumRefdDemd().equals(creationContratPlacementForm.getNumDemandeChoisi())){
                        //afficher les données contrat placement a partir de la demande
                    if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")) {
                        contratPlacementView = creerContratPlacementView(demandeDecisionView, creationContratPlacementForm);
                           forward = "initCreatPlac";
                       }else{
                           contratPlacementView = renouvelerContratPlacementView(demandeDecisionView, creationContratPlacementForm);
                           forward= "initRenouvelPlac";
                       }
                        creationContratPlacementForm.setContratPlacementView(contratPlacementView);
                        creationContratPlacementForm.setDemandeDecisionView(demandeDecisionView);
                        creationContratPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(demandeDecisionView.getDemandeDecision().getTypePiece().getCodTpceTpce().toString());
                        creationContratPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(demandeDecisionView.getDemandeDecision().getNumNpceDemd());
                        creationContratPlacementForm.getContratView().setCodStrcStrc(demandeDecisionView.getStrcCcptDemd());
                        creationContratPlacementForm.getContratView().setCodPrdPrd(demandeDecisionView.getPrdCcptDemd());
                        creationContratPlacementForm.getContratView().setNumCcptCcpt(demandeDecisionView.getNumCcptDemd());
                    }
                 }
             }
             return mapping.findForward(forward);
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("L'affichage de la demande du contrat Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
             text.append("Exception au niveau de l'agence:"); 
             text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
             text.append(". Exception :"); text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             logger.error(text.toString(),e); 
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");
         }

     }

public  ContratPlacementView creerContratPlacementView(DemandeDecisionView demandeDecisionView, ActionForm form){
    ContratPlacementView contratPlacementView = new ContratPlacementView();
    CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
    TraitementConditionBanque traitementConditionBanque= new TraitementConditionBanque();   
    try{
     //   contratPlacementView.setNumSeqCpla(demandeDecisionView.getNumRefdDemd());
        contratPlacementView.setDatCreCpla(creationContratPlacementForm.getInitialisationView().getDateComptable());
        int nbreJour=Integer.parseInt(demandeDecisionView.getNumDureDemd());
        Date dateSousc = DateHandler.strToDate(demandeDecisionView.getDateValeur());
      
       if(demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
           contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(DateHandler.addJour(dateSousc,nbreJour-1)));
       }else {
           contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(DateHandler.addJour(dateSousc,nbreJour)));
       }
      
        contratPlacementView.setDuree(demandeDecisionView.getNumDureDemd());
        contratPlacementView.setMontCapCpla(demandeDecisionView.getMontPlaDemd());

        contratPlacementView.setTauIRC(demandeDecisionView.getTauxIrc());
        contratPlacementView.setLibPrdPrd(demandeDecisionView.getLibPrdPlc());
        contratPlacementView.setCodPrdPrd(demandeDecisionView.getCodPrdPrd()); // code produit placement
        contratPlacementView.setDateSouscription(demandeDecisionView.getDateValeur());
        contratPlacementView.setCodPintCpla(demandeDecisionView.getCodTypPaiement());
        // calcul des montants au niveau JS
        
        if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_GENERAL)){
             // demande général
               contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_GENERAL);
                 
                   if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                    String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                  
                   if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                   // appel conditions de banque general opération versement interet pré compté (320)
                    traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                    contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                    contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                           // ajout sign e marge !!
                    contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                    contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                    contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
               //     contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                       }else{
                           // appel conditions de banque general opération versement interet post compté (613)
                            traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                            contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                            contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur()); 
                           // ajout sign e marge !!
                           contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                           contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                           contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                           contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                       }
                   // appel conditions de banque general opération souscription
                     traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.COD_OPER_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                     contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                     codPrd= null;
                   }
                }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_FAVEUR)){ //faveur (taux fixe)
                   contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_FAVEUR);
                         // appel conditions de banque opération souscription
                          traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.COD_OPER_SOUSC_PLAC.toString());
                          contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                         if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                         // appel conditions de banque opération versement interet pré compté (320)
                          traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                          contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                          contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                 // appel conditions de banque general opération versement interet pré compté (320)
                                 if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                 String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                 traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                 codPrd= null;
                                 contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret()); 
                                 }
                             }else{
                                 // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                  contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                 // appel conditions de banque general opération versement interet post compté (613)
                                 if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                 String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                 traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                 codPrd= null;
                                 contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret()); 
                                 }
                             }
                         
                         
                     }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_INDEXE)) {
                             // demande avec taux indexé au TMM
                         contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_INDEXE); 
                             // appel conditions de banque opération souscription
                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.COD_OPER_SOUSC_PLAC.toString());
                              contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                                 if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                                 // appel conditions de banque opération versement interet pré compté (320)
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                                  contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                  contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                  contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                  contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                               //   contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                         // appel conditions de banque general opération versement interet pré compté (320)
                                         if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                         String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                         codPrd= null;
                                         contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                         }
                                     }else{
                                         // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                          traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                          contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                         // ajout sign e marge !!
                                         contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                         contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                    //     contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                         // appel conditions de banque general opération versement interet post compté (613)
                                         if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                         String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                         codPrd= null;
                                         contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                         }
                                     }
                            
                          }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_PREFERENTIEL)) {
                             // demande avec taux préférentiel
                             contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_PREFERENTIEL); 
                             // appel conditions de banque opération souscription
                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.COD_OPER_SOUSC_PLAC.toString());
                              contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                                 if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                                 // appel conditions de banque opération versement interet pré compté (320)
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                                  contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                  contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                  contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                  contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                //  contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                         // appel conditions de banque general opération versement interet pré compté (320)
                                         if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                         String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                         codPrd= null;
                                         contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                         }
                                     }else{
                                         // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                          traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                          contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                         // ajout sign e marge !!
                                         contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                         contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                    //   contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                         // appel conditions de banque general opération versement interet post compté (613)
                                         if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                         String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                         codPrd= null;
                                         contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                         }
                                     }
                            
                          }else {
                             logger.debug("demandeDecisionView.getCodeTypeFaveur() est vide");
                         }
       
        if(demandeDecisionView.getCodSbdvDemd().equals("0")){
            contratPlacementView.setCodSbdvCpla("0");
            creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC.toString());
        }else  if(demandeDecisionView.getCodSbdvDemd().equals("2")){
            contratPlacementView.setCodSbdvCpla("2");
            creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC.toString());
        }else {
            contratPlacementView.setCodSbdvCpla("1");
            creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC_SBDV.toString());
        }
        return contratPlacementView;
    }catch (Exception e) {
         logger.error("Exception Methode : creerContratPlacementView(demandeDecisionView):  ",e);  
        throw new RuntimeException(e);  
     } 
}

    public  ContratPlacementView renouvelerContratPlacementView(DemandeDecisionView demandeDecisionView, ActionForm form){
        ContratPlacementView contratPlacementView = new ContratPlacementView();
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        TraitementConditionBanque traitementConditionBanque= new TraitementConditionBanque();   
        try{
            contratPlacementView.setNumSeqCpla(demandeDecisionView.getContratPlacement().getNumSeqCpla().toString());
            contratPlacementView.setDatCreCpla(creationContratPlacementForm.getInitialisationView().getDateComptable());
            int nbreJour=Integer.parseInt(demandeDecisionView.getNumDureDemd());
            Date dateSousc = DateHandler.strToDate(demandeDecisionView.getDateValeur());
            contratPlacementView.setDuree(demandeDecisionView.getNumDureDemd());
            contratPlacementView.setMontCapCpla(demandeDecisionView.getMontPlaDemd());
            GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
            ContratPlacement cpla = (ContratPlacement)getContratPlacementCmd.execute(demandeDecisionView.getContratPlacement());
                        
            if(cpla.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                || cpla.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                ){ // produit placement
                 Context context = ContextHandler.getContext();
                 PlacementDAO placementDAO= (PlacementDAO)context.getBean("placementDAO");
                 if(placementDAO.verifierLigneDetailBc(cpla.getNumBcCpla(),cpla.getNumSeqCpla())){
                     if(placementDAO.verifierRecuperationBc(cpla.getNumBcCpla(),cpla.getNumSeqCpla()) == true){
                         creationContratPlacementForm.setVerifRecuperationBC(true); // BC récupéré
                     }else {
                         creationContratPlacementForm.setVerifRecuperationBC(false);
                     }
                 }else {
                         creationContratPlacementForm.setVerifRecuperationBC(false);
                     } 
                }else {
                    creationContratPlacementForm.setVerifRecuperationBC(true); // BC considéré comme récupéré pour autre prd placement
                }
            
            contratPlacementView.setTauIRC(demandeDecisionView.getTauxIrc());
            contratPlacementView.setLibPrdPrd(demandeDecisionView.getLibPrdPlc());
            contratPlacementView.setCodPrdPrd(demandeDecisionView.getCodPrdPrd()); // code produit placement
            contratPlacementView.setDateSouscription(demandeDecisionView.getDateValeur());
            //S'il s'agit d'un renouvellement après échéance, il y aura p.e.c renouvel. et valid., sinon, il sera traité par le batch, sauf cas BC/BCDC
             if(demandeDecisionView.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                 creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.OPER_RENOUVEL_PLAC_APRE.toString());
            //     contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(DateHandler.addJour(dateSousc,nbreJour)));     
             }else{
                 creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.OPER_RENOUVEL_PLAC_AVAN.toString()); 
              }
            contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(DateHandler.addJour(dateSousc,nbreJour-1)));     
            contratPlacementView.setCodPintCpla(demandeDecisionView.getCodTypPaiement());
            // calcul des montants au niveau JS
         if(!creationContratPlacementForm.getNatureOp().equals("annul")){
           if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_GENERAL)){
                 // demande général
                   contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_GENERAL);
                     
                       if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                        String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                      
                       if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                       // appel conditions de banque general opération versement interet pré compté (320)
                        traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                        contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                        contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                               // ajout sign e marge !!
                        contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                        contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                        contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                   //     contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                           }else{
                               // appel conditions de banque general opération versement interet post compté (613)
                                traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur()); 
                               // ajout sign e marge !!
                               contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                               contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                               contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                               contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                           }
                       // appel conditions de banque general opération renouvellement
                       if(demandeDecisionView.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                         traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_RENOUVEL_PLAC_APRE.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                       }else {
                           traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_RENOUVEL_PLAC_AVAN.toString() , demandeDecisionView.getDateEchAncienContrat(),demandeDecisionView.getPrdCcptDemd()); 
                       }
                         contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                         codPrd= null;
                       }
                    }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_FAVEUR)){ //faveur (taux fixe)
                       contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_FAVEUR);
                             // appel conditions de banque opération renouvellement
                              if(demandeDecisionView.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_APRE.toString());
                              }else{
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_AVAN.toString());
                              }
                              
                              contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                             if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                             // appel conditions de banque opération versement interet pré compté (320)
                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                              contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                              contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                     // appel conditions de banque general opération versement interet pré compté (320)
                                     if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                     String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                     traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                     codPrd= null;
                                     contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret()); 
                                     }
                                 }else{
                                     // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                      traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                      contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                     // appel conditions de banque general opération versement interet post compté (613)
                                     if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                     String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                     traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                     codPrd= null;
                                     contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret()); 
                                     }
                                 }
                             
                             
                         }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_INDEXE)) {
                                 // demande avec taux indexé au TMM
                             contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_INDEXE); 
                                 // appel conditions de banque opération renouvellement
                                  if(demandeDecisionView.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_APRE.toString());
                                  }else{
                                      traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_AVAN.toString());
                                  }
                                  
                                  contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                                     if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                                     // appel conditions de banque opération versement interet pré compté (320)
                                      traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                                      contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                      contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                      contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                      contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                   //   contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                             // appel conditions de banque general opération versement interet pré compté (320)
                                             if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                             String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                             traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                             codPrd= null;
                                             contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                             }
                                         }else{
                                             // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                              contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                             // ajout sign e marge !!
                                             contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                             contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                        //     contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                             // appel conditions de banque general opération versement interet post compté (613)
                                             if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                             String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                             traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                             codPrd= null;
                                             contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                             }
                                         }
                                
                              }else if(demandeDecisionView.getCodeTypeFaveur().equals(Constants.COD_FAV_PREFERENTIEL)) {
                                 // demande avec taux préférentiel
                                 contratPlacementView.setTypeFaveurCpla(Constants.COD_FAV_PREFERENTIEL); 
                                 // appel conditions de banque opération renouvellement
                                  if(demandeDecisionView.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                                  traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_APRE.toString());
                                  }else{
                                      traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_RENOUVEL_PLAC_AVAN.toString());
                                  }
                                  
                                  contratPlacementView.setDateValeur(traitementConditionBanque.getDatevaleur());
                                     if(demandeDecisionView.getCodTypPaiement().equals("PRE")){
                                     // appel conditions de banque opération versement interet pré compté (320)
                                      traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_PRE_SOUSC_PLAC.toString());
                                      contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                      contratPlacementView.setDateValeurTaux(traitementConditionBanque.getDatevaleur());
                                      contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                      contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                    //  contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                             // appel conditions de banque general opération versement interet pré compté (320)
                                             if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                             String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                             traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_PRE_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                             codPrd= null;
                                             contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                             }
                                         }else{
                                             // appel conditions de banque opération versement interet post compté (613) :: aucune insertion des inetrets au niveau d la base
                                              traitementConditionBanque = appelConditionBanque(demandeDecisionView, Constants.OPER_INT_POST_SOUSC_PLAC.toString());
                                              contratPlacementView.setNumTauiCpla(traitementConditionBanque.getTauxInteret());
                                             // ajout sign e marge !!
                                             contratPlacementView.setSigneMargeCpla(traitementConditionBanque.getSigneMarge());
                                             contratPlacementView.setNumMargeCpla(String.valueOf(traitementConditionBanque.getValeurMarge()));
                                        //   contratPlacementView.setTmm(String.valueOf(traitementConditionBanque.getTmm()));
                                             // appel conditions de banque general opération versement interet post compté (613)
                                             if(demandeDecisionView.getDemandeDecision().getProduitPlacement() != null){
                                             String codPrd = demandeDecisionView.getDemandeDecision().getProduitPlacement().getCodPrdPlc().toString();
                                             traitementConditionBanque = appelConditionBanqueGeneral(codPrd , Constants.OPER_INT_POST_SOUSC_PLAC.toString() , demandeDecisionView.getDateValeur(),demandeDecisionView.getPrdCcptDemd());
                                             codPrd= null;
                                             contratPlacementView.setTauGeneral(traitementConditionBanque.getTauxInteret());
                                             }
                                         }
                                
                              }else {
                                 logger.debug("demandeDecisionView.getCodeTypeFaveur() est vide");
                             }
         }
           /*
            if(demandeDecisionView.getPrdCcptDemd().equals(new String("0121"))){
                 contratPlacementView.setDateValeurTaux(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDayAfterNdays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7))); 
                 contratPlacementView.setDateValeur(DateHandler.dateToStr(CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(contratPlacementView.getDateValeur()),7)));
               }*/
            return contratPlacementView;
        }catch (Exception e) {
             logger.error("Exception Methode : renouvelerContratPlacementView(demandeDecisionView):  ",e);  
            throw new RuntimeException(e);  
         } 
    }
    public  ActionForward rejeterPlacement(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws IOException, 
                                                                           ServletException {
                
    ActionMessages actionMessages = new ActionMessages();
    StringBuffer text = 
         new StringBuffer("Le rejet du Contrat Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
    CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
    
        PecSouscriptionPlacementCmd pecSouscriptionPlacementCmd =  new PecSouscriptionPlacementCmd();
        ParamContratPlacement paramContratPlacement = new ParamContratPlacement();
        DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
    try {
        DemandeDecision demandeDecision = new DemandeDecision();
        ContratPlacement contratPlacement = new ContratPlacement();
        demandeDecision = creationContratPlacementForm.getDemandeDecisionView().getDemandeDecision();
        creationContratPlacementForm.setRejet("R");
        if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")
            || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PECREN")
        ){
             paramContratPlacement.setTypeOperation("RPEC");
                  // demande decision etat == R
                   //affectation de la demande de decision ( il faut mettre à jour l'etat demande à rejetée )
                           demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_REJETEE); 
                           demandeDecision.setDatRejDemd(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
                           paramContratPlacement.setDemandeDecision(demandeDecision);
              }else if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")
                  || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALIDREN")
              ){
                  // etat contrat placement rejeté, date rejet inseré au lieu de la dat validation
                  paramContratPlacement.setTypeOperation("RVALID");
                  if(creationContratPlacementForm.getContratPlacementView() != null){
                       contratPlacement = creationContratPlacementForm.getContratPlacementView().getContratPlacement();
                  }
                  contratPlacement.setCodEtatCpla(Constants.ETAT_CPLA_REJETE);
                  contratPlacement.setDatRejCpla(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
                  contratPlacement.setLibMrejCpla("Rejet de l'opération initié par l'utilisateur");
                  paramContratPlacement.setContratPlacement(contratPlacement);
              }
        
      
    
       
        // pour remplir getNumeroDomaine
        Structure structure = new Structure();
        structure.setCodStrcStrc(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
        detailsOperationPlacement.setStructure(structure);
        paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement); // pour remplir getNumeroDomaine
        
        contratPlacement = 
                (ContratPlacement)pecSouscriptionPlacementCmd.execute(paramContratPlacement);
        
        if (!contratPlacement.hasError()) {
            StringBuffer message = new StringBuffer("");
            if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")
              || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")
            ){
                message.append(" L'opération de rejet de la souscription du placement N° ");
            }else {
                message.append(" L'opération de rejet du renouvellement du placement N° ");
                message.append(creationContratPlacementForm.getContratPlacementView().getNumSeqCpla());
                message.append(" par le placement N° ");
            }
            if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")
               || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PECREN")
            ){
                 message.append(demandeDecision.getNumRefdDemd().toString());
             }else if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")
                      || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALIDREN")
             ){
                    message.append(contratPlacement.getNumSeqCpla().toString());
                }
          
            message.append( " a été effectuée avec succès.");
            creationContratPlacementForm.setLibelleConfirmation(message.toString());
            creationContratPlacementForm.setTitreConfirmation("Confirmation de rejet de la création du contrat placement");
        } else {
            List listErreur = contratPlacement.getErrors();
            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                com.oxia.fwk.core.Error erreur = 
                    (com.oxia.fwk.core.Error)it.next();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
            }
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
        String forward = new String();
            if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")
                || creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")
                 ) {
                        forward = "confirmationContratPlac";
                       }else{
                         forward= "confirmationRenouvelPlac";  
                       }
                    return mapping.findForward(forward); 
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        text.append("Exception au niveau de l'agence:"); 
        text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
        text.append(". Exception :"); text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        logger.error(text.toString(),e); 
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");
    }

    }
 public  ActionForward validationCreationCpla(ActionMapping mapping, ActionForm form, 
                                   HttpServletRequest request, 
                                   HttpServletResponse response) throws IOException, 
                                                                        ServletException {

          
             ActionMessages actionMessages = new ActionMessages();
             StringBuffer text = 
                  new StringBuffer("");
             CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
             InteretServi interetServi = new InteretServi();
             ParamContratPlacement paramContratPlacement = new ParamContratPlacement();
             ContratPlacement contratPlacement = new ContratPlacement();
             OperationMoyPay opMoyPay = new OperationMoyPay();
             
             ValiderSouscriptionPlacementCmd validerSouscriptionPlacementCmd =  new ValiderSouscriptionPlacementCmd();
    if(isTokenValid(request)){ 
       resetToken(request);
             try {
                
            // mettre à jour le contrat placement (etat = V)
              contratPlacement = creationContratPlacementForm.getContratPlacementView().getContratPlacement();
              contratPlacement.setCodEtatCpla(Constants.ETAT_CONTRAT_PLAC_VALIDE);
              contratPlacement.setDatVldCpla(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
              paramContratPlacement.setContratPlacement(contratPlacement);
                 
              //DetailsOperationPlacement
               DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
               GetDetailsOperationPlacCmd getDetailsOperationPlacCmd = new GetDetailsOperationPlacCmd();
               detailsOperationPlacement.setContratPlacement(contratPlacement);
               DetailsOperationPlacement detailsOpPlac = (DetailsOperationPlacement)getDetailsOperationPlacCmd.execute(detailsOperationPlacement);

              if(detailsOpPlac != null){
               if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC)
                    || detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC_SBDV)
                        ){
                              Context context = ContextHandler.getContext();
                              HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                              hibernateTemplate.evict(detailsOpPlac);
                     // modifier detailsOperationPlacement pour l inserer a la validation du contrat
                      DetailsOperationPlacement detailsOperationPlacementNew = modifierDonneesDetailsOperationPlacement(creationContratPlacementForm,detailsOpPlac);
                              // insertion operation moyen de paiement SOUSCRIPTION / INTERETS SERVIS
                              opMoyPay = affecterDonneesOperationMoyenPaiement(creationContratPlacementForm);
                              detailsOperationPlacementNew.setOperationMoyPay(opMoyPay);
                              
                              paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacementNew);
                          }else {
                              logger.debug("detailsOperationPlacementOld.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC) retourn vide");
                              text.append("Aucun details Operations pour l'operation souscription");
                          }
                   }else {
                       // renouvellement
                        if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_APRE)
                        || detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_AVAN)
                        ){
                                      Context context = ContextHandler.getContext();
                                      HibernateTemplate  hibernateTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
                                      hibernateTemplate.evict(detailsOpPlac);
                             // modifier detailsOperationPlacement pour l inserer a la validation du contrat
                              DetailsOperationPlacement detailsOperationPlacementNew = modifierDonneesDetailsOperationPlacement(creationContratPlacementForm,detailsOpPlac);
                                       
                                      // insertion operation moyen de paiement SOUSCRIPTION / INTERETS SERVIS
                                      opMoyPay = affecterDonneesOperationMoyenPaiement(creationContratPlacementForm);
                                      detailsOperationPlacementNew.setOperationMoyPay(opMoyPay);
                                      
                                      paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacementNew);
                              }else {
                                      logger.debug("detailsOperationPlacementOld.getTache().getOperation().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_APRE) // et AVAN retourn vide");
                                      text.append("Aucun details Operations pour l'operation renouvellement");
                                  }
                   }
                  }else {
                      text.append("Le contratPlacement n 'a pas de Details-Operations. ");
                      logger.debug("detailsOpPlac == null");
                  }
               
             
              //Modification solde du compte -- extrat comptable -- au niveau traitement
              
              // affectation des interêts servis  // les interets payables a terme echu sont servis selon la durée (batch ou a la liquidation)
               interetServi = 
                          affecterDonneesInteretServi(creationContratPlacementForm);
                  interetServi.setContratPlacement(contratPlacement);
                  paramContratPlacement.setInteretServi(interetServi);
            
                contratPlacement = 
                        (ContratPlacement)validerSouscriptionPlacementCmd.execute(paramContratPlacement);
               
                creationContratPlacementForm.getContratPlacementView().setContratPlacement(contratPlacement);
                
//----------------------------------------------------------------------------------Impression
// avis d'opération
                imprimerAvisOperation(creationContratPlacementForm,request);
                 // PRECOMPTE
                 if(contratPlacement.getCodPintCpla().equals("PRE")){
                       creationContratPlacementForm.setInteretServi(interetServi);
                   //    imprimerRetenueAlaSource(contratPlacement,interetServi,request); 
                   }
//----------------------------------------------------------------------------------Impression
                 
                     
                 if (!contratPlacement.hasError()) {
                     StringBuffer message = new StringBuffer("");
                     ContratPlacementView cplacView = creationContratPlacementForm.getContratPlacementView();
                     if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                         message.append(" L'opération de validation de la souscription du placement N° ");
                     }else {
                         message.append(" L'opération de validation du renouvellement du placement N° ");
                         message.append(creationContratPlacementForm.getContratPlacementView().getNumPlacRenouvele());
                         message.append(" par le placement N° ");
                     }
                     message.append(contratPlacement.getNumSeqCpla().toString());
                     message.append( " a été effectuée avec succès.");
                     message.append(" Capital: ");message.append(cplacView.getMontCapCpla());
                     message.append(" (Millimes); Durée: ");message.append(cplacView.getDuree());
                     message.append(" (Jours); Taux d'intérêts: ");message.append(cplacView.getNumTauiCpla());
                     message.append("; Type de paiement des Intérêts: ");message.append(cplacView.getLibPintCpla());
                     message.append("; Montant des Intérêts Bruts: ");message.append(cplacView.getMontTauInt());
                     message.append(" (Millimes); Montant de l'IRC: ");message.append(cplacView.getMontTauIRC());
                     message.append(" (Millimes); Montant Net des Intérêts: ");message.append(cplacView.getMontNetTauInt());
                     message.append(" (Millimes);");
                     creationContratPlacementForm.setLibelleConfirmation(message.toString());
                     creationContratPlacementForm.setTitreConfirmation("Confirmation de validation de la création du contrat placement");
                     
                 } else {
                     List listErreur = contratPlacement.getErrors();
                     for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                         com.oxia.fwk.core.Error erreur = 
                             (com.oxia.fwk.core.Error)it.next();
                         ActionMessage actionMessage = 
                             new ActionMessage("exception.generique", 
                                               erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                     }
                     this.saveMessages(request, actionMessages);
                     return mapping.findForward("error");
                 }
          
               String forward = new String();
                   if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")) {
                               forward = "confirmationContratPlac";
                              }else{
                                forward= "confirmationRenouvelPlac";  
                              }
                           return mapping.findForward(forward);  
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 text.append("La validation du contrat Placement a été interrompue. Veuillez transmettre ce message à l'équipe informatique: Exception au niveau de l'agence:"); 
                 text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
                 text.append(". Exception :"); text.append(e.toString());
                 erreur.setCode("200");
                 erreur.setDescription(text.toString());
                 logger.error(text.toString(),e); 
                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique", 
                                       erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }
            }else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                text.append("L'opération de création du contrat placement a été déjà validée");
                erreur.setDescription(text.toString());
                logger.error(text.toString()); 
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
  }
    /**
     * Fonction qui retourne un objet DetailsOperationPlacement
     * appelé par l'Action qui valide le contrat placement 
     */

    public DetailsOperationPlacement modifierDonneesDetailsOperationPlacement(ActionForm form, DetailsOperationPlacement detailsOpPlac ) {
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        DetailsOperationPlacement detailsOperationPlacement = detailsOpPlac;
    try{
     
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        tacheId.setCodOperOper(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
        tacheId.setCodTachTach(Long.valueOf("2"));
        tache.setTacheId(tacheId);                
        detailsOperationPlacement.setTache(tache);
        tache = null; tacheId =null;
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(creationContratPlacementForm.getInitialisationView().getNumMatrUser());
        detailsOperationPlacement.setPersonnel(personnel);
        personnel = null;
        Structure structure = new Structure();
        structure.setCodStrcStrc(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
        detailsOperationPlacement.setStructure(structure);
        structure = null;
        detailsOperationPlacement.setDatOperDopl(creationContratPlacementForm.getInitialisationView().getDateOp());
        detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
        if (!creationContratPlacementForm.getContratPlacementView().getDateValeur().equals("")) {
            detailsOperationPlacement.setDatValDopl(DateHandler.strToDate(creationContratPlacementForm.getContratPlacementView().getDateValeur()));
        } else {
               logger.error("creationContratPlacementForm.getContratPlacementView().getDateValeur().equals(\"\")");  
           }
       
    } catch (Exception e) {
                logger.error("Exception dans souscriptionContratPlacementAction / Methode : modifierDonneesDetailsOperationPlacement:  ",e);  
                throw new RuntimeException(e);               
         } 
        return detailsOperationPlacement;
    }
  
    /**
     * Fonction qui retourne la liste des contrats placements en attentes (View)
     */

    public Collection traiterListeContratPlacment(ActionForm form, List listContratPlacement) {
     
       Collection listCplaView = new ArrayList();
       CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
       
       try{
              Collection<ContratPlacement> listCplaAttent;
              listCplaAttent = listContratPlacement;
              for(ContratPlacement contratPlacement : listCplaAttent ){
                  ContratPlacementView contratPlacementView = new ContratPlacementView();
                  contratPlacementView.setContratPlacement(contratPlacement);
                  contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString()); 
                  contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                  contratPlacementView.setDuree(contratPlacement.getNumNbrjCpla().toString());
                  contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                  contratPlacementView.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
                  contratPlacementView.setLibPrdPrd(contratPlacement.getProduitPlacement().getLibPrdPlc());
                  
                  if(contratPlacement.getCodPintCpla().equals("PRE")){
                      contratPlacementView.setCodPintCpla("PRE");
                      contratPlacementView.setLibPintCpla("A L'AVANCE");
                      }else {
                          contratPlacementView.setCodPintCpla("POST");
                          contratPlacementView.setLibPintCpla("A TERME ECHU");
                      }
                  // calcul des montants au niveau JS
                  contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
                  contratPlacementView.setTauIRC(contratPlacement.getNumTircCpla().toString());
                  
                if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                       || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                        ){
                  if(contratPlacement.getNumBcCpla() != null){
                      contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
                      }else {
                        logger.error("Le numéro de BC est vide");
                        }
                }
                  if(contratPlacement.getContratCpt() != null){
                  
                   contratPlacementView.setStrcCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
                   contratPlacementView.setPrdCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
                   contratPlacementView.setNumCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                   contratPlacementView.setTypeFaveurCpla(contratPlacement.getCodFavCpla());
                    /*
                     * Verifier Si l etat du contrat compte est toujours valide et vérifier la provision (le solde)
                    */
                     ContratCpt contratCpt = contratPlacement.getContratCpt();
                     if(contratCpt != null){
                         contratPlacementView.setEtatCcptCpla(contratCpt.getCodEtatCcpt());
                         Personne pers = contratPlacement.getPersonne();
                        if(pers.getCategoriePersonne() != null){
                           contratPlacementView.setCodTypsSous(pers.getCategoriePersonne().getLibCatpCatp());
                          
                          if(pers.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATP_COTITU)){ // entité cotitulaire 
                           contratPlacementView.setCodPieceClient(Constants.COD_NUM_ORDRE.toString());
                           contratPlacementView.setNumPieceClient(contratCpt.getClient().getNumSeqPers().toString());
                              }else {
                                  contratPlacementView.setCodPieceClient(pers.getTypePiece().getCodTpceTpce().toString());
                                  contratPlacementView.setNumPieceClient(pers.getNumPcePers()); 
                              }
                         }else {
                             logger.debug("pers.getCategoriePersonne() == null");
                         }
                          if (pers.getTypePiece().getCodTpceTpce().equals(Constants.COD_RCS)) { // cas RCS affichage de libSiglPers 
                              contratPlacementView.setNomClient(pers.getLibSiglPers());
                              contratPlacementView.setPrenomClient(pers.getNomRsPers());
                             } else {
                                    contratPlacementView.setNomClient(pers.getNomNomPers());
                                    contratPlacementView.setPrenomClient(pers.getNomPrnPers());
                                }
                        if(contratCpt.getMontSoldCcpt() < 0 ){
                             contratPlacementView.setSoldeCompte(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue()))+" DB");
                         }else{
                             contratPlacementView.setSoldeCompte(StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue()))+" CR");
                         }
                         if(contratPlacement.getMontCapCpla() <= contratCpt.getProvision(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()))){
                                   contratPlacementView.setBoolProvision(false);
                               }else {
                                   contratPlacementView.setBoolProvision(true);
                               }
                         if(contratCpt.getProduit().getCodTprdPrd().equals("DCV")
                            || contratCpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(103))
                            || contratCpt.getContratCptId().getCodPrdPrd().equals(Long.valueOf(121))
                           ){
                                   contratPlacementView.setBoolNotForcing(true); // aucun forçage de l'opération pr les comptes 103, 121 et en DC
                               }else {
                                   contratPlacementView.setBoolNotForcing(false);
                               }
                         }//---------------------------------------------------------------------------------------
                  
                  }
                  if(contratPlacement.getCodSbdvCpla() != null){
                      if(contratPlacement.getCodSbdvCpla().equals("1")){
                          contratPlacementView.setCodSbdvCpla("1");
                      }else if(contratPlacement.getCodSbdvCpla().equals("2")){
                              contratPlacementView.setCodSbdvCpla("2");
                          }else {
                              contratPlacementView.setCodSbdvCpla("0");
                          }
                  }
                
                  String moisCompt=creationContratPlacementForm.getInitialisationView().getDateComptable().substring(3,5);
                  String moisValeur=DateHandler.dateToStr(contratPlacement.getDatValCpla()).substring(3,5);
                if(Long.valueOf(moisValeur).intValue() != Long.valueOf(moisCompt).intValue()){ 
                      contratPlacementView.setCodSbdvCpla("2");
                  }
                  if(contratPlacement.getCodSbdvCpla() != null && contratPlacement.getCodSbdvCpla().equals("1")){// modifié l 21.01.2010
                     Date datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-10);
                          if(datePermise.compareTo(contratPlacement.getDatValCpla()) <= 0 ){
                                contratPlacementView.setBoolDatePermise(true);  
                                    }else {
                                        contratPlacementView.setBoolDatePermise(false); 
                                    }
                          datePermise = null;  
                      }else {
                        if(!contratPlacement.getCodFavCpla().equals(Constants.COD_FAV_GENERAL)){
                          Date datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-2);
                          if(datePermise.compareTo(contratPlacement.getDatValCpla()) <= 0 ){
                                contratPlacementView.setBoolDatePermise(true);  
                                    }else {
                                        contratPlacementView.setBoolDatePermise(false); 
                                    }
                          datePermise = null; 
                        }else{
                            Date datePermise  = CalanderHandler.getDateOuvrableBeforeNDays(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()),-1);
                            if(datePermise.compareTo(contratPlacement.getDatValCpla()) <= 0 ){
                                  contratPlacementView.setBoolDatePermise(true);  
                                      }else {
                                          contratPlacementView.setBoolDatePermise(false); 
                                      }
                            datePermise = null; 
                        }
                      }
                
            
                  // necessaire pr charger CB
                  if(contratPlacement.getDemandeDecision() != null && contratPlacement.getDemandeDecision().getNumRefdDemd() != null){
                      contratPlacementView.setDateSouscription(DateHandler.dateToStr(contratPlacement.getDemandeDecision().getDatValDemd()));
                   if(contratPlacement.getContratPlacementByNumSqcrCpla() != null){
                      contratPlacementView.setDatEchePlacRenouvele(DateHandler.dateToStr(contratPlacement.getContratPlacementByNumSqcrCpla().getDatEcheCpla()));
                   }
                      contratPlacementView.setRefDossierCB(contratPlacement.getDemandeDecision().getNumRefdDemd().toString()); 
                     
                     if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALIDREN")) {
                         contratPlacementView.setNumPlacRenouvele(contratPlacement.getDemandeDecision().getContratPlacement().getNumSeqCpla().toString());
                         if(contratPlacement.getDemandeDecision().getCodTyprDemd().equals(Long.valueOf("2"))){
                             contratPlacementView.setTypeRenouvellement("2"); 
                         }else {
                             contratPlacementView.setTypeRenouvellement("1");
                         }
                        
                     }
                 }else {
                      logger.error("Le contrat placement n'est pas affecté à aucune demande -- Set des demandes vides");
                  }

                  listCplaView.add(contratPlacementView);
              }
        } catch (Exception e) {
                   logger.error("Exception Methode : traiterListeContratPlacment:  ",e);  
                   throw new RuntimeException(e);               
            } 
  
    return listCplaView;
    
    }
    
    //afficher le Contrat Placement choisi pour le valider
     public  ActionForward afficherContratPlacement(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {

              saveToken(request); // sauvegarde une clé (variable) par session au niveau du formulaire
              ActionMessages actionMessages = new ActionMessages();
              CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
              String forward = new String();
              try {
                 
                  Collection<ContratPlacementView> listCplaAttente;
                  listCplaAttente = creationContratPlacementForm.getListeContratPlacAtt();
                  for(ContratPlacementView contratPlacementView : listCplaAttente ){
                      if(contratPlacementView != null){
                         if(contratPlacementView.getNumSeqCpla().equals(creationContratPlacementForm.getNumCplaChoisi())){
                             //afficher les données contrat placement a partir du contrat placement view choisi
                              if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                                  traiterCB_contratPlacement(contratPlacementView);
                              }else {
                                  traiterCB_renouvelPlacement(contratPlacementView,creationContratPlacementForm);
                              }
                          if(contratPlacementView.getCodSbdvCpla() != null){
                             if(contratPlacementView.getCodSbdvCpla().equals("1")){
                                 creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC_SBDV.toString());
                             }else {
                                 creationContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_SOUSC_PLAC.toString());
                             }
                          }
                              creationContratPlacementForm.setContratPlacementView(contratPlacementView);
                              creationContratPlacementForm.setNumSeqCpla(creationContratPlacementForm.getNumCplaChoisi());
                         }
                      }
                  }
                  if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")) {
                      forward = "initCreatPlac";
                     }else{
                         forward= "initRenouvelPlac";
                     }  
                  return mapping.findForward(forward);
              } catch (Exception e) {
                  com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                  StringBuffer text = 
                      new StringBuffer("L'affichage du contrat Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
                  text.append("Exception au niveau de l'agence:"); 
                  text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
                  text.append(". Exception :"); text.append(e.toString());
                  erreur.setCode("200");
                  erreur.setDescription(text.toString());
                  logger.error(text.toString(),e); 
                  ActionMessage actionMessage = 
                      new ActionMessage("exception.generique", 
                                        erreur.getDescription());
                  actionMessages.add("Erreur ", actionMessage);
                  this.saveMessages(request, actionMessages);
                  return mapping.findForward("error");
              }

          }

    /***
     * Fonction qui retourne un objet InteretServi
     * appelé par l'Action qui insére le contrat placement dans la base
     */

    public InteretServi affecterDonneesInteretServi(ActionForm form) {

        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        InteretServi interetServi = new InteretServi();
        try{
        interetServi.setDatIsrvIsrv(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
        interetServi.setDatValIsrv(DateHandler.strToDate(creationContratPlacementForm.getContratPlacementView().getDateValeurTaux()));
        // date valeur a partir CB !!
        interetServi.setMontIsrvIsrv(Long.valueOf(StrHandler.strWithoutBlanck(creationContratPlacementForm.getContratPlacementView().getMontNetTauInt().replace('.',' '))));
        interetServi.setMontIrcIsrv(Long.valueOf(StrHandler.strWithoutBlanck(creationContratPlacementForm.getContratPlacementView().getMontTauIRC().replace('.',' '))));
        interetServi.setMontBrutIsrv(Long.valueOf(StrHandler.strWithoutBlanck(creationContratPlacementForm.getContratPlacementView().getMontTauInt().replace('.',' '))));
        } catch (Exception e) {
               logger.error("Exception dans CreationContratPlacementAction / Methode : affecterDonnéesInteretServi:  ",e);  
               throw new RuntimeException(e);               
        } 
        return interetServi;
    }


    /**
     * Fonction qui retourne un objet DetailsOperationPlacement
     * appelé par l'Action qui insére le contrat placement dans la base
     */

    public DetailsOperationPlacement affecterDonneesDetailsOperationPlacement(ActionForm form) {
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
    try{
       String numPieceSouscrip = creationContratPlacementForm.getDemandeDecisionView().getDemandeDecision().getNumNpceDemd();
       Long codPieceSouscrip = Long.valueOf(creationContratPlacementForm.getDemandeDecisionView().getDemandeDecision().getTypePiece().getCodTpceTpce());
       
       if (numPieceSouscrip != null && !numPieceSouscrip.equals("")) {
            TypePiece typePieceSouscripteur = new TypePiece();
            typePieceSouscripteur.setCodTpceTpce(codPieceSouscrip);
            detailsOperationPlacement.setTypePieceByCodTpssTpce(typePieceSouscripteur);
            detailsOperationPlacement.setNumNpssDopl(numPieceSouscrip);
            typePieceSouscripteur = null;
            numPieceSouscrip= null ;
            codPieceSouscrip =null ;
        }

        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        tacheId.setCodOperOper(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
        tacheId.setCodTachTach(Long.valueOf("1"));
        tache.setTacheId(tacheId);                
        detailsOperationPlacement.setTache(tache);
        tache = null; tacheId =null;
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(creationContratPlacementForm.getInitialisationView().getNumMatrUser());
        detailsOperationPlacement.setPersonnel(personnel);
        personnel = null;
        Structure structure = new Structure();
        structure.setCodStrcStrc(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
        detailsOperationPlacement.setStructure(structure);
        structure = null;
        detailsOperationPlacement.setDatOperDopl(creationContratPlacementForm.getInitialisationView().getDateOp());
        detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
        if (!creationContratPlacementForm.getContratPlacementView().getDateValeur().equals("")) {
            detailsOperationPlacement.setDatValDopl(DateHandler.strToDate(creationContratPlacementForm.getContratPlacementView().getDateValeur()));
        } else {
               logger.error("creationContratPlacementForm.getContratPlacementView().getDateValeur().equals(\"\")");     
           }
        detailsOperationPlacement.setMontDopDopl(Long.valueOf((StrHandler.strWithoutBlanck(creationContratPlacementForm.getDemandeDecisionView().getMontPlaDemd().replace('.',' ')))));
        
    } catch (Exception e) {
                logger.error("Exception dans souscriptionContratPlacementAction / Methode : affecterDonnéesDetailsOperationPlacement:  ",e);  
                throw new RuntimeException(e);               
         } 
        return detailsOperationPlacement;
    }
    /**
         * Action qui permet d'insérer le contrat placement dans la base
         * appelé par la fonction JS: validerSouscriptionPlacement()
         * renvoi vers une page de confirmation : confirmationContratPlacement.jsp
         */

    public ActionForward validationPECPlacement(ActionMapping mapping, 
                                                         ActionForm form, 
                                                         HttpServletRequest request, 
                                                         HttpServletResponse response) throws IOException, 
                                                                                              ServletException {

        ActionMessages actionMessages = new ActionMessages();
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        PecSouscriptionPlacementCmd pecSouscriptionPlacementCmd =  new PecSouscriptionPlacementCmd();
        CreateContratPlacementCmd createContratPlacementCmd = new CreateContratPlacementCmd();
        ParamContratPlacement paramContratPlacement = new ParamContratPlacement();

        try {
            ContratPlacement contratPlacement = new ContratPlacement();
            DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
            DemandeDecision demandeDecision = new DemandeDecision();

            if(!creationContratPlacementForm.getNatureOp().equals("annul")){
                // vérifier la date valeur
                 contratPlacement = affecterDonneesContratPlacement(creationContratPlacementForm);
                 paramContratPlacement.setContratPlacement(contratPlacement);
                //affectation de la demande de decision ( il faut mettre à jour l'etat demande à traitée )
                 // demande traitée                  
                 demandeDecision = creationContratPlacementForm.getDemandeDecisionView().getDemandeDecision();
                 demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_TRAITE); // etat demande = traitée                            
                 paramContratPlacement.setDemandeDecision(demandeDecision);
                // affectation du detail opération placement
                 detailsOperationPlacement = 
                         affecterDonneesDetailsOperationPlacement(creationContratPlacementForm);
                 ///*** Cas d'operation faite par un mandataire
                  if(creationContratPlacementForm.getPouvoir().getTypePouvoir().equals("M")){
                   Set listeMandPersOperPlac = new HashSet(0);
                   for (Iterator it = creationContratPlacementForm.getPouvoir().getListMandatPersonne().iterator();it.hasNext(); ) { 
                       MandatPersonne mandatPersonne = (MandatPersonne)it.next();
                       
                       MandPersOperPlac mandPersOperPlac = new MandPersOperPlac();
                       MandPersOperPlacId mandPersOperPlacId = new MandPersOperPlacId();
                       mandPersOperPlacId.setNumMandMand(mandatPersonne.getMandat().getNumMandMand());               
                       mandPersOperPlacId.setNumSeqPers(mandatPersonne.getPersonne().getNumSeqPers());
                       mandPersOperPlac.setMandPersOperPlacId(mandPersOperPlacId);
                       
                       listeMandPersOperPlac.add(mandPersOperPlac);
                   }
                      detailsOperationPlacement.setMandPersOperPlacs(listeMandPersOperPlac);

                   if(creationContratPlacementForm.getPouvoir().getListMandatOperation() != null && creationContratPlacementForm.getPouvoir().getListMandatOperation().size()>0 ){
                          MandatOperation mandatOperation = (MandatOperation)creationContratPlacementForm.getPouvoir().getListMandatOperation().get(0);
                          detailsOperationPlacement.setMandatOperation(mandatOperation);                  
                      }
                  }else if(creationContratPlacementForm.getPouvoir().getTypePouvoir().equals("C")){ // co titulaire
                   // cas cotitulaire
                    if(creationContratPlacementForm.getPouvoir().getListCotitulaire()!=null && creationContratPlacementForm.getPouvoir().getListCotitulaire().size()>0 ){
                        CoTitulaire cotitulaire = (CoTitulaire)creationContratPlacementForm.getPouvoir().getListCotitulaire().get(0);
                        detailsOperationPlacement.setCoTitulaire(cotitulaire);
                    }       
                  }
                 paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement);
                 
                 if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                    || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                     ){
                     paramContratPlacement.setNumSeqBc(Long.valueOf(creationContratPlacementForm.getNumSeqBCAjax()));
                     }else {
                         paramContratPlacement.setNumSeqBc(null);
                     }
             }else {
                 demandeDecision = creationContratPlacementForm.getDemandeDecisionView().getDemandeDecision();
                 demandeDecision.setCodEtatDemd(Constants.ETAT_DEM_DECIS_REJETEE); // etat demande = rejetée    
                 demandeDecision.setDatRejDemd(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
                 demandeDecision.setLibRmqDemd("Renouvellement annulé");
                 paramContratPlacement.setDemandeDecision(demandeDecision);
             }
             
          if(creationContratPlacementForm.getNatureOp().equals("renouv") || creationContratPlacementForm.getNatureOp().equals("sousc")){
                // PEC renouvellement  
                paramContratPlacement.setTypeOperation("PEC");
                contratPlacement = (ContratPlacement)pecSouscriptionPlacementCmd.execute(paramContratPlacement);
          
            }else if(creationContratPlacementForm.getNatureOp().equals("forcage")){
                 // validation forçage renouvellement 
                  OperationMoyPay opMoyPay = new OperationMoyPay();
                  InteretServi interetServi = new InteretServi();
                  creationContratPlacementForm.getContratPlacementView().setContratPlacement(paramContratPlacement.getContratPlacement());
                // insertion operation moyen de paiement SOUSCRIPTION / INTERETS SERVIS
                  opMoyPay = affecterDonneesOperationMoyenPaiement(creationContratPlacementForm,paramContratPlacement.getDetailsOperationPlacement());
                  paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(opMoyPay);
                  paramContratPlacement.getDetailsOperationPlacement().getTache().getTacheId().setCodTachTach(Long.valueOf("2"));;
                // affectation des interêts servis  // les interets payables a terme echu sont servis selon la durée (batch ou a la liquidation)
                  interetServi = 
                            affecterDonneesInteretServi(creationContratPlacementForm);
                    interetServi.setContratPlacement(contratPlacement);
                    paramContratPlacement.setInteretServi(interetServi);
                 
                  contratPlacement = (ContratPlacement)createContratPlacementCmd.execute(paramContratPlacement);
                  
                //----------------------------------------------------------------------------------Impression
                 if (!contratPlacement.hasError()) {
                // avis d'opération
                 imprimerAvisOperation(creationContratPlacementForm,request);
                  // PRECOMPTE
                 if(contratPlacement.getCodPintCpla().equals("PRE")){
                    creationContratPlacementForm.setInteretServi(interetServi);
                           }
                 }
                //----------------------------------------------------------------------------------Impression
            }else {
                // annulation renouvellement
                paramContratPlacement.setTypeOperation("RPEC");
                contratPlacement = (ContratPlacement)pecSouscriptionPlacementCmd.execute(paramContratPlacement);
              }
            if (!contratPlacement.hasError()) {
                StringBuffer message = new StringBuffer("");
               
                if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")){
                    message.append(" L'opération de prise en charge de la souscription du placement N° ");
                    message.append(contratPlacement.getNumSeqCpla().toString());
                    message.append( " a été effectuée avec succès et en attente de validation par le chef d'agence.");
                    message.append(" Capital: ");message.append(contratPlacement.getMontCapCpla());
                    message.append(" (Millimes); Durée: ");message.append(contratPlacement.getNumNbrjCpla());
                    message.append(" (Jours); Taux d'intérêts: ");message.append(contratPlacement.getNumTauiCpla());
                    message.append("; Type de paiement des Intérêts: ");message.append(contratPlacement.getCodPintCpla());
                    message.append(";");
                    creationContratPlacementForm.setLibelleConfirmation(message.toString());
                    creationContratPlacementForm.setTitreConfirmation("Confirmation de prise en charge des données du placement");
                   }else {
                    if(creationContratPlacementForm.getNatureOp().equals("forcage")){
                        message.append(" L'opération de forçage du renouvellement du placement N° ");
                        message.append(creationContratPlacementForm.getContratPlacementView().getNumSeqCpla());
                        message.append(" par le placement N° ");
                        message.append(contratPlacement.getNumSeqCpla().toString());
                        message.append( " a été effectuée avec succès.");
                        message.append(" Capital: ");message.append(contratPlacement.getMontCapCpla());
                        message.append(" (Millimes); Durée: ");message.append(contratPlacement.getNumNbrjCpla());
                        message.append(" (Jours); Taux d'intérêts: ");message.append(contratPlacement.getNumTauiCpla());
                        message.append("; Type de paiement des Intérêts: ");message.append(contratPlacement.getCodPintCpla());
                        message.append(";");
                        creationContratPlacementForm.setLibelleConfirmation(message.toString());
                        creationContratPlacementForm.setTitreConfirmation("Confirmation de validation de la création du contrat placement");
                    }else {
                            message.append(" L'opération de prise en charge du renouvellement du placement N° ");
                            message.append(creationContratPlacementForm.getContratPlacementView().getNumSeqCpla());
                            message.append(" par le placement N° ");
                            message.append(contratPlacement.getNumSeqCpla().toString());
                            message.append( " a été effectuée avec succès et en attente de validation par le chef d'agence.");
                            message.append(" Capital: ");message.append(contratPlacement.getMontCapCpla());
                            message.append(" (Millimes); Durée: ");message.append(contratPlacement.getNumNbrjCpla());
                            message.append(" (Jours); Taux d'intérêts: ");message.append(contratPlacement.getNumTauiCpla());
                            message.append("; Type de paiement des Intérêts: ");message.append(contratPlacement.getCodPintCpla());
                            message.append(";");
                            creationContratPlacementForm.setLibelleConfirmation(message.toString());
                            creationContratPlacementForm.setTitreConfirmation("Confirmation de prise en charge des données du placement");
                            }
                }
               
             
               
                
                 } else {
                List listErreur = contratPlacement.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext();) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
String forward = new String();
            if (creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")) {
                forward = "confirmationContratPlac";
               }else{
                 forward= "confirmationRenouvelPlac";
               }
            return mapping.findForward(forward);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La confirmation de la souscription au contrat de placement a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
            text.append("Exception au niveau de l'agence:"); 
            text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :"); text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(),e); 

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    } 
    /**
     * Fonction qui retourne un objet ContratPlacement
     * appelé par l'Action qui insére le contrat placement dans la base
     */

    public ContratPlacement affecterDonneesContratPlacement(ActionForm form) {

        ContratPlacement contratPlacement = new ContratPlacement();
        //  CompteTitre compteTitre= new CompteTitre();
        ContratPlacementView  contratPlacementView =new ContratPlacementView();
        DemandeDecisionView demandeDecisionView = new DemandeDecisionView();
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        try{
            contratPlacementView = creationContratPlacementForm.getContratPlacementView();
            demandeDecisionView = creationContratPlacementForm.getDemandeDecisionView();
            
         // cas du BC / CAT
         if(contratPlacementView != null && demandeDecisionView != null){
          Personne personne = new Personne();
          ContratCpt contratCpt = new ContratCpt();
          Client client = new Client();
          contratCpt = demandeDecisionView.getDemandeDecision().getContratCpt();
          contratPlacement.setContratCpt(contratCpt);
          client = contratCpt.getClient();
          personne.setNumSeqPers(client.getNumSeqPers());
         
          contratPlacement.setNumSeqCpla(demandeDecisionView.getDemandeDecision().getNumRefdDemd());
          contratPlacement.setPersonne(personne);
          contratPlacement.setDemandeDecision(demandeDecisionView.getDemandeDecision());
          contratPlacement.setProduitPlacement(demandeDecisionView.getDemandeDecision().getProduitPlacement()); // code produit placement
          contratPlacement.setDatCreCpla(DateHandler.strToDate(contratPlacementView.getDatCreCpla()));
          contratPlacement.setDatEcheCpla(DateHandler.strToDate(contratPlacementView.getDatEcheCpla()));
          contratPlacement.setNumNbrjCpla(demandeDecisionView.getDemandeDecision().getNumDureDemd());
          contratPlacement.setNumTircCpla(demandeDecisionView.getDemandeDecision().getNumTircDemd()); 
              if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                 || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
                  ){
                    contratPlacement.setNumBcCpla(Long.valueOf(contratPlacementView.getNumBcCpla()));
                     }
          contratPlacement.setNumTauiCpla(Double.valueOf(contratPlacementView.getNumTauiCpla()));
          if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")){
              contratPlacement.setCodEtatCpla(Constants.ETAT_CONTRAT_PLAC_ATTENTE); 
             }else {
              contratPlacement.setCodEtatCpla(Constants.ETAT_CPLAC_ATT_RENOUVEL);  // en attente de renouvellement
             // liaison avec l ancien contrat placement renouvelable
              GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
              ContratPlacement contratPlacementRenouvele = new ContratPlacement();
              contratPlacementRenouvele.setNumSeqCpla(Long.valueOf(contratPlacementView.getNumSeqCpla()));
              contratPlacement.setContratPlacementByNumSqcrCpla(contratPlacementRenouvele);
              ContratPlacement ccpla =(ContratPlacement) getContratPlacementCmd.execute(contratPlacementRenouvele);
              ccpla.setCodErenCpla(Long.valueOf("2"));
               if(!ccpla.hasError()){
                  UpdateContratPlacementCmd updateContratPlacementCmd = new UpdateContratPlacementCmd();
                  updateContratPlacementCmd.execute(ccpla);
               }else {
                  logger.error(ccpla.getErrorMessage());
                 }
          }
        
          contratPlacement.setCodPintCpla(contratPlacementView.getCodPintCpla());
          contratPlacement.setDatValCpla(DateHandler.strToDate(contratPlacementView.getDateSouscription()));
             
          if(contratPlacement.getCodPintCpla().equals("POST")){
              // tester si la durée dépasse une année, remplir la prochaine date d pay d int
               GregorianCalendar calendrier = new GregorianCalendar();
               calendrier.setTime(contratPlacement.getDatValCpla());
               if(!contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)
                 || !creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PECREN")
                ){
                   calendrier.add(GregorianCalendar.DATE,365);
               }else {
                   calendrier.add(GregorianCalendar.DATE,364); // cas BNA placement ou contrat renouvelé
               }
              
              if(contratPlacement.getDatEcheCpla().after(calendrier.getTime())){
                  contratPlacement.setDatPintCpla(DateHandler.strToDate(DateHandler.dateToStr(calendrier.getTime())));
               }
              }
          contratPlacement.setMontActuCpla(Long.valueOf((StrHandler.strWithoutBlanck(contratPlacementView.getMontCapCpla().replace('.',' ')))));
          contratPlacement.setMontCapCpla(contratPlacement.getMontActuCpla());
         contratPlacement.setCodFavCpla(contratPlacementView.getTypeFaveurCpla());
         if(contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_GENERAL)
            || contratPlacementView.getTypeFaveurCpla().equals(Constants.COD_FAV_INDEXE)){
             contratPlacement.setNumMargCpla(new Double(contratPlacementView.getNumMargeCpla()));
             contratPlacement.setCodMargCpla(contratPlacementView.getSigneMargeCpla());
             }
         }
         contratPlacement.setCodErenCpla(Long.valueOf("0"));
         if(contratPlacementView.getCodSbdvCpla() != null ){
         if(contratPlacementView.getCodSbdvCpla().equals("0")){
             contratPlacement.setCodSbdvCpla("0");
         }else if(contratPlacementView.getCodSbdvCpla().equals("2")){
                 contratPlacement.setCodSbdvCpla("2");
             }else {
                 contratPlacement.setCodSbdvCpla("1");
             }
         }else {
             contratPlacement.setCodSbdvCpla("0");
         }
         return contratPlacement;
        }catch (Exception e) {
             logger.error("Exception Methode : affecterDonneesContratPlacement:  ",e);  
            throw new RuntimeException(e);  
         } 
         
        
    }
    
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (ActionForm form){
    
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        ContratPlacementView contratPlacementView = creationContratPlacementForm.getContratPlacementView();
        ContratPlacement contratPlacement = contratPlacementView.getContratPlacement();
        OperationMoyPay operationMoyPay = new OperationMoyPay();          
        
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser(creationContratPlacementForm.getInitialisationView().getNumMatrUser());
        Operation operation = new Operation();    
        
        Structure structure = new Structure();    
        structure.setCodStrcStrc(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
        operationMoyPay.setStructureInitiatrice(structure);
        operationMoyPay.setStructureReceptrice(structure);
        
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
         
        ContratCpt contratCpt = contratPlacement.getContratCpt();
        operationMoyPay.setContratCpt(contratCpt);

        if (creationContratPlacementForm.getContratPlacementView().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) ||
            creationContratPlacementForm.getContratPlacementView().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC) )
          {
          if(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumBcCpla() != null){
            operationMoyPay.setNumMoypOmp(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumBcCpla().toString());
          }else {
              logger.error("Le numéro BC est vide");
          }
        }
         
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        operation.setCodOperOper(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        tacheId.setCodTachTach(Long.valueOf("2"));
        
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        String str = contratPlacement.getNumSeqCpla().toString();
        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
          || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
        ){
            str = str+" "+contratPlacement.getNumBcCpla().toString();
        }
        operationMoyPay.setCodRefbOmp(str);
        operationMoyPay.setDatOperOmp(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
        operationMoyPay.setDatSystOmp(new Date()); // avec le time ok
       
        // ça sert a rien d inserer dans MandPersOperMoy alors qu on insere dejà dans MandPersOperPlac
        
        // Affecter mandat opération pour mettre a jour l enveloppe
         //DetailsOperationPlacement
          DetailsOperationPlacement detailsOperationPlacement = new DetailsOperationPlacement();
          GetDetailsOperationPlacCmd getDetailsOperationPlacCmd = new GetDetailsOperationPlacCmd();
          detailsOperationPlacement.setContratPlacement(contratPlacement);
          DetailsOperationPlacement detailsOpPlac = (DetailsOperationPlacement)getDetailsOperationPlacCmd.execute(detailsOperationPlacement);
         if(detailsOpPlac != null && detailsOpPlac.getNumSeqDopl() != null){
             if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                 if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC)
                    || detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC_SBDV)
                 ){
                     if(detailsOpPlac.getMandatOperation() != null){
                         operationMoyPay.setMandatOperation(detailsOpPlac.getMandatOperation());
                     }
                     if(detailsOpPlac.getMandPersOperPlacs().size() != 0){ // mandataire
                       operationMoyPay.setCodDemOmp("M");
                         }else if(detailsOpPlac.getCoTitulaire() != null){ // cotitulaire
                             operationMoyPay.setCodDemOmp("C");
                         }else {
                             operationMoyPay.setCodDemOmp("T");
                         }
                 }
             }else {
                 if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_APRE)
                 || detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_AVAN)
                 ){
                     if(detailsOpPlac.getMandatOperation() != null){
                         operationMoyPay.setMandatOperation(detailsOpPlac.getMandatOperation());
                     }
                     if(detailsOpPlac.getMandPersOperPlacs().size() != 0){ // mandataire
                       operationMoyPay.setCodDemOmp("M");
                         }else if(detailsOpPlac.getCoTitulaire() != null){ // cotitulaire
                             operationMoyPay.setCodDemOmp("C");
                         }else {
                             operationMoyPay.setCodDemOmp("T");
                         }
                 }
             }
            
         }else {
             
         }

        DemandeDecision demdRetour = contratPlacement.getDemandeDecision();
        if(demdRetour != null && demdRetour.getNumRefdDemd() != null){
                 operationMoyPay.setTypePieceDemandeur(demdRetour.getTypePiece());
                 operationMoyPay.setNumPcedOmp(demdRetour.getNumNpceDemd());
                 operationMoyPay.setNomNomdOmp(demdRetour.getNomNomDemd());
                 operationMoyPay.setNomPrndOmp(demdRetour.getNomPrnDemd());
         }else {
             logger.error("Le contrat placement n'est pas affecté à aucune demande -- Set des demandes vides");
         }
        demdRetour = null;
        operationMoyPay.setDatValOmp(DateHandler.strToDate(contratPlacementView.getDateValeur()));
        operationMoyPay.setMontDinOmp(contratPlacement.getMontCapCpla());
       
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
     // solde avant  -- montsoldccpt / champ, non pa foreign key
        operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
        
        operationMoyPay.setCodRefcOmp(str.substring(str.length()-8,str.length())); // refc pr le CRO -- num seq cpla
        operationMoyPay.setCodRefmOmp(contratPlacement.getNumSeqCpla().toString());
        str = null;
        operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt()-contratPlacement.getMontCapCpla());
     
        Produit prd = new Produit();
        prd.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc());
        operationMoyPay.setProduit(prd); // COD_PRD_OMP rempli avec le code produit placement
        prd=null;
        TypeMoyenPaiement typMoyPay = new TypeMoyenPaiement();
        typMoyPay.setCodMoypTmoy(Constants.COD_TMOY_ESPECE);
        operationMoyPay.setTypeMoyenPaiement(typMoyPay);
        typMoyPay =null;

    // numseqcli  numseqpers  a verifier (co titulair)
    
        // en cas de forçage de l'opération, provision non disponible
        if(!contratPlacementView.isBoolProvision()){
             operationMoyPay.setBoolForcOmp(Long.valueOf(0)); 
         }else {
             operationMoyPay.setBoolForcOmp(Long.valueOf(1)); 
         }
        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
           || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
            ){ 
          // cas BC
           operationMoyPay.setNumMoypOmp(contratPlacement.getNumBcCpla().toString());
            }
            
    return operationMoyPay;
    } 

    public OperationMoyPay affecterDonneesOperationMoyenPaiement (ActionForm form, DetailsOperationPlacement detailsOpPlac){
    
        CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
        ContratPlacementView contratPlacementView = creationContratPlacementForm.getContratPlacementView();
        ContratPlacement contratPlacement = contratPlacementView.getContratPlacement();
        OperationMoyPay operationMoyPay = new OperationMoyPay();          
        
        Personnel personnelInit = new Personnel();
        personnelInit.setNumMatrUser(creationContratPlacementForm.getInitialisationView().getNumMatrUser());
        Operation operation = new Operation();    
        
        Structure structure = new Structure();    
        structure.setCodStrcStrc(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeAgence()));
        operationMoyPay.setStructureInitiatrice(structure);
        operationMoyPay.setStructureReceptrice(structure);
        
        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
         
        ContratCpt contratCpt = contratPlacement.getContratCpt();
        operationMoyPay.setContratCpt(contratCpt);

        if (creationContratPlacementForm.getContratPlacementView().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) ||
            creationContratPlacementForm.getContratPlacementView().getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC) )
          {
          if(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumBcCpla() != null){
            operationMoyPay.setNumMoypOmp(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumBcCpla().toString());
          }else {
              logger.error("Le numéro BC est vide");
          }
        }
         
        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        operation.setCodOperOper(Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        tacheId.setCodTachTach(Long.valueOf("2"));
        
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        String str = contratPlacement.getNumSeqCpla().toString();
        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
          || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
        ){
            str = str+" "+contratPlacement.getNumBcCpla().toString();
        }
        operationMoyPay.setCodRefbOmp(str);
        operationMoyPay.setDatOperOmp(DateHandler.strToDate(creationContratPlacementForm.getInitialisationView().getDateComptable()));
        operationMoyPay.setDatSystOmp(new Date()); // avec le time ok
       
        // ça sert a rien d inserer dans MandPersOperMoy alors qu on insere dejà dans MandPersOperPlac
        
        // Affecter mandat opération pour mettre a jour l enveloppe
          if(detailsOpPlac != null){
             if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                 if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.COD_OPER_SOUSC_PLAC)){
                     if(detailsOpPlac.getMandatOperation() != null){
                         operationMoyPay.setMandatOperation(detailsOpPlac.getMandatOperation());
                     }
                     if(detailsOpPlac.getMandPersOperPlacs().size() != 0){ // mandataire
                       operationMoyPay.setCodDemOmp("M");
                         }else if(detailsOpPlac.getCoTitulaire() != null){ // cotitulaire
                             operationMoyPay.setCodDemOmp("C");
                         }else {
                             operationMoyPay.setCodDemOmp("T");
                         }
                 }
             }else {
                 if(detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_APRE)
                 || detailsOpPlac.getTache().getTacheId().getCodOperOper().equals(Constants.OPER_RENOUVEL_PLAC_AVAN)
                 ){
                     if(detailsOpPlac.getMandatOperation() != null){
                         operationMoyPay.setMandatOperation(detailsOpPlac.getMandatOperation());
                     }
                     if(detailsOpPlac.getMandPersOperPlacs().size() != 0){ // mandataire
                       operationMoyPay.setCodDemOmp("M");
                         }else if(detailsOpPlac.getCoTitulaire() != null){ // cotitulaire
                             operationMoyPay.setCodDemOmp("C");
                         }else {
                             operationMoyPay.setCodDemOmp("T");
                         }
                 }
             }
            
         }else {
             
         }

        DemandeDecision demdRetour = contratPlacement.getDemandeDecision();
        if(demdRetour != null && demdRetour.getNumRefdDemd() != null){
                 operationMoyPay.setTypePieceDemandeur(demdRetour.getTypePiece());
                 operationMoyPay.setNumPcedOmp(demdRetour.getNumNpceDemd());
                 operationMoyPay.setNomNomdOmp(demdRetour.getNomNomDemd());
                 operationMoyPay.setNomPrndOmp(demdRetour.getNomPrnDemd());
         }else {
             logger.error("Le contrat placement n'est pas affecté à aucune demande -- Set des demandes vides");
         }
        demdRetour = null;
        operationMoyPay.setDatValOmp(DateHandler.strToDate(contratPlacementView.getDateValeur()));
        operationMoyPay.setMontDinOmp(contratPlacement.getMontCapCpla());
       
        operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
     // solde avant  -- montsoldccpt / champ, non pa foreign key
        operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
        
        operationMoyPay.setCodRefcOmp(str.substring(str.length()-8,str.length())); // refc pr le CRO -- num seq cpla
        operationMoyPay.setCodRefmOmp(contratPlacement.getNumSeqCpla().toString());
        str = null;
        operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt()-contratPlacement.getMontCapCpla());
     
        Produit prd = new Produit();
        prd.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc());
        operationMoyPay.setProduit(prd); // COD_PRD_OMP rempli avec le code produit placement
        prd=null;
        TypeMoyenPaiement typMoyPay = new TypeMoyenPaiement();
        typMoyPay.setCodMoypTmoy(Constants.COD_TMOY_ESPECE);
        operationMoyPay.setTypeMoyenPaiement(typMoyPay);
        typMoyPay =null;

    // numseqcli  numseqpers  a verifier (co titulair)
    
        // en cas de forçage de l'opération, provision non disponible
        if(!contratPlacementView.isBoolProvision()){
             operationMoyPay.setBoolForcOmp(Long.valueOf(0)); 
         }else {
             operationMoyPay.setBoolForcOmp(Long.valueOf(1)); 
         }
        if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
           || contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)
            ){ 
          // cas BC
           operationMoyPay.setNumMoypOmp(contratPlacement.getNumBcCpla().toString());
            }
            
    return operationMoyPay;
    } 

   public ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {

       CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
       ActionMessages actionMessages = new ActionMessages();
       try {
           String forward = new String();
          
           creationContratPlacementForm.setMontOperOmp("");
           creationContratPlacementForm.setEnvRestOmp("");
           creationContratPlacementForm.setTypeMandat("");
           PersonneDemandeur personneDemandeur = creationContratPlacementForm.getPersonneDemandeur();
           Pouvoir pouvoir = (Pouvoir)request.getSession().getAttribute("pouvoir");
           if ( pouvoir != null) {
               creationContratPlacementForm.setPouvoir(pouvoir);
               creationContratPlacementForm.setTypeDemandeur(pouvoir.getTypePouvoir());
               personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
               if (pouvoir.getCodPieceAnnexe() != null && 
                   pouvoir.getNumPieceAnnexe() != null) {
                   creationContratPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(pouvoir.getCodPieceAnnexe());
                   creationContratPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(pouvoir.getNumPieceAnnexe());
               }
               if (personneDemandeur.getTypePouvoir().equals("N") || 
                   personneDemandeur.getTypePouvoir().equals("")) {
                   creationContratPlacementForm.getPouvoir().setTypePouvoir("TR");
                   creationContratPlacementForm.setAlertDemandeur("pouvoirInvalide");
               } else{
                   creationContratPlacementForm.setAlertDemandeur("pouvoirValide");
                   if (personneDemandeur.getTypePouvoir().equals("M") && pouvoir.getMandat().getCodTypMand().equalsIgnoreCase("S")){///*** Cas mandat spéciale
                       ConsultEnveloppeRestanteCmd consultEnveloppeRestanteCmd =new ConsultEnveloppeRestanteCmd();
                       MandatOperation mandatOperation = (MandatOperation)pouvoir.getListMandatOperation().get(0);
                       PrimitiveVO primitiveVO = (PrimitiveVO)consultEnveloppeRestanteCmd.execute(mandatOperation);
                       
                       creationContratPlacementForm.setMontOperOmp(mandatOperation.getMontMlimMaop().toString());
                       creationContratPlacementForm.setEnvRestOmp(primitiveVO.getVLong().toString());
                       creationContratPlacementForm.setTypeMandat("S");///*** mandat spéciale
                       
                   }
               }
           } else {
               creationContratPlacementForm.setAlertPouvoir("false");
           }
       
      
         if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("PEC")){
             forward = "initCreatPlac";
         }else {
             forward = "initRenouvelPlac";
         }
          return mapping.findForward(forward);
       } catch (Exception e) {
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("Le chargement du pouvoir a été interrompue, veuillez transmettre ce message à l'équipe informatique:");
           text.append("Exception au niveau de l'agence:"); 
           text.append(creationContratPlacementForm.getInitialisationView().getCodeAgence());
           text.append(". Exception :"); text.append(e.toString());
           erreur.setCode("200");
           erreur.setDescription(text.toString());
           logger.error(text.toString(),e); 

           ActionMessage actionMessage = 
               new ActionMessage("exception.generique", 
                                 erreur.getDescription());
           actionMessages.add("Erreur ", actionMessage);
           this.saveMessages(request, actionMessages);
           return mapping.findForward("error");
       }
   }
    public void imprimerAvisOperation(ActionForm form, 
                                       HttpServletRequest request
                                      ) throws IOException, ServletException {
    
             CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
            try {
            
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 ContratPlacementView cplaView = creationContratPlacementForm.getContratPlacementView();
                 // PRECOMPTE
                 if(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getCodPintCpla().equals("PRE")){
                     txtNomFichJasper.append("AvisOpSouscInt");
                   }else { //POSTCOMPTE
                        if(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getCodFavCpla().equals("I")){
                            // taux indexé
                             txtNomFichJasper.append("AvisOpSouscTauVar");
                        }else {
                            txtNomFichJasper.append("AvisOpSousc");
                            parameters.put("MONT_BRUT_ISRV",cplaView.getMontTauInt()); 
                            parameters.put("MONT_INET_ISRV",cplaView.getMontNetTauInt());
                            parameters.put("MONT_IRC_ISRV",cplaView.getMontTauIRC());
                            }
                     }
                if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                        parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Souscription à un Contrat Placement");
                    }else {
                        parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Renouvellement Contrat Placement");
                    }
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_COD_OPER", Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
                 parameters.put("P_NUM_PLAC",creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumSeqCpla());
                 parameters.put("DUPLICATA", "");
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
                 logger.error("Exception Methode : imprimerAvisOperation:  ",e);  
                 throw new RuntimeException(e);
             }                                                                                

                                                                                          
         }  
 /*   public ActionForward imprimerAvisOperation(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
    
             CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
             ActionMessages actionMessages = new ActionMessages();
            
             try {
            
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 
                 // PRECOMPTE
                 if(creationContratPlacementForm.getContratPlacementView().getContratPlacement().getCodPintCpla().equals("PRE")){
                     txtNomFichJasper.append("AvisOpSouscInt");
                   }else { //POSTCOMPTE
                        txtNomFichJasper.append("AvisOpSousc");
                     }
                if(creationContratPlacementForm.getInitialisationView().getLibelleOperation().equals("VALID")){
                        parameters.put("P_LIB_ETAT", "AVIS DE DEBIT : Souscription à un Contrat Placement");
                    }else {
                        parameters.put("P_LIB_ETAT", "AVIS DE DEBIT : Renouvellement Contrat Placement");
                    }
               
                 
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_COD_OPER", Long.valueOf(creationContratPlacementForm.getInitialisationView().getCodeOperation()));
                 parameters.put("P_NUM_PLAC",creationContratPlacementForm.getContratPlacementView().getContratPlacement().getNumSeqCpla());
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("L'impression a été interrompue : erreur au niveau de la fonction imprimerAvisOperation ");
             text.append(e.getMessage());
             erreur.setCode("298");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
                 this.saveMessages(request, actionMessages);
                 return mapping.findForward("error");
             }                                                                                

             return mapping.findForward("confirmationContratPlac");                                                                                         
         }  

    public void imprimerRetenueAlaSource(ContratPlacement contratPlacement, InteretServi interetServi, HttpServletRequest request) {
    
              try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("certificatRetenuSource");
                 
                 parameters.put("STRC_CPT",contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString());
                 parameters.put("PRD_CPT",contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString());
                 parameters.put("CCPT_CPT",contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 
                 parameters.put("MONT_BRUT",StrHandler.formatmnt(Math.abs(interetServi.getMontBrutIsrv().doubleValue())));
                 parameters.put("MONT_IRC",StrHandler.formatmnt(Math.abs(interetServi.getMontIrcIsrv().doubleValue())));
                 parameters.put("MONT_NET",StrHandler.formatmnt(Math.abs(interetServi.getMontIsrvIsrv().doubleValue())));
                 parameters.put("DAT_SOUSCRIPTION",DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                 StringBuffer periode = new StringBuffer("du ");
                 periode.append(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                 periode.append(" au "); periode.append(DateHandler.dateToStr(contratPlacement.getDatEcheCpla())); 
                 parameters.put("PERIODE",periode.toString());
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
                logger.error("Exception Methode : imprimerRetenueAlaSource:  ",e);  
                throw new RuntimeException(e);
             }                                                                                
                                                                                               
         }  
  */     
    
    public ActionForward imprimerRetenueAlaSource(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
             ActionMessages actionMessages = new ActionMessages();
              try {   
                 CreationContratPlacementForm creationContratPlacementForm = (CreationContratPlacementForm)form;
                
                 ContratPlacement contratPlacement = creationContratPlacementForm.getContratPlacementView().getContratPlacement();
                 InteretServi interetServi = creationContratPlacementForm.getInteretServi();
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("certificatRetenuSource");
                 
                 parameters.put("STRC_CPT",contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString());
                 parameters.put("PRD_CPT",contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString());
                 parameters.put("CCPT_CPT",contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                 
                 parameters.put("MONT_BRUT",StrHandler.formatmnt(Math.abs(interetServi.getMontBrutIsrv().doubleValue())));
                 parameters.put("MONT_IRC",StrHandler.formatmnt(Math.abs(interetServi.getMontIrcIsrv().doubleValue())));
                 parameters.put("MONT_NET",StrHandler.formatmnt(Math.abs(interetServi.getMontIsrvIsrv().doubleValue())));
                 parameters.put("DAT_SOUSCRIPTION",DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                 parameters.put("DUPLICATA",new String(""));
                 StringBuffer periode = new StringBuffer("du ");
                 periode.append(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                 periode.append(" au "); periode.append(DateHandler.dateToStr(contratPlacement.getDatEcheCpla())); 
                 parameters.put("PERIODE",periode.toString());
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("imprimIRC","2");
             }catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = new StringBuffer("L'impression a été interrompue : erreur au niveau de la fonction imprimerRetenueAlaSource ");
                 text.append(e.getMessage());
                 erreur.setCode("298");
                 erreur.setDescription(text.toString());
                 ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
                     actionMessages.add("Erreur ", actionMessage);
                     this.saveMessages(request, actionMessages);
                     return mapping.findForward("error");
             }                                                                                
             return mapping.findForward("confirmationContratPlac");                                                                                                      
         } 
}

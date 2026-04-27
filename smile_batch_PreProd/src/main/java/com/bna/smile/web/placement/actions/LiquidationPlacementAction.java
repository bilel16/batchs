
package com.bna.smile.web.placement.actions;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
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
import com.bna.commun.model.TypePiece;

import com.bna.commun.service.ICrudService;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.domaineplacement.commande.ValiderPECAvancePlacementCmd;


import java.util.Date;

import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineplacement.traitement.GetAvgTMMbetweenDatesTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.ConsultEnveloppeRestanteCmd;
import com.bna.smile.model.domaineplacement.commande.GetAvancRembLiquidByIdCmd;
import com.bna.smile.model.domaineplacement.commande.GetAvgTMMbetweenDatesCmd;
import com.bna.smile.model.domaineplacement.commande.GetContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListAvancRembLiquidByEtatCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListInteretServiCmd;
import com.bna.smile.model.domaineplacement.commande.PecLiquidationAnticipeCmd;
import com.bna.smile.model.domaineplacement.commande.RejeterLiquidationAnticipeCmd;
import com.bna.smile.model.domaineplacement.commande.TraitementLiquidationCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderAvancePlacementCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderLiquidationAnticipeePlacementCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.model.ParamDates;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.domaineplacement.traitement.GetListInteretServiTrt;
import com.bna.smile.model.domaineplacement.traitement.InsertInteretServiTrt;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.placement.forms.AvancRembLiquidValidPlacementForm;

import com.bna.smile.web.placement.forms.SouscriptionContratPlacementForm;
import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;

import com.bna.smile.web.placement.view.InteretServiView;

import com.oxia.fwk.context.Context;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class LiquidationPlacementAction extends DispatchAction {

    /**
     * <B> Action de la page  avanceContratPlacement.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.souscription.actions
     *
 
     */
    public

    ActionForward initierLiquidationPlacement(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        AvancRembLiquidValidPlacementForm avanceContratPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
        
     
            avanceContratPlacementForm.clearFormAvancRembLiquidValidPlacement();
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            avanceContratPlacementForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            avanceContratPlacementForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());    
            avanceContratPlacementForm.setDateActuelle(paramAgence.getDateComptable());    
            avanceContratPlacementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            avanceContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            avanceContratPlacementForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            avanceContratPlacementForm.setDateComptable(paramAgence.getDateComptable());            
            String forward = "";
            
            //verification de l'habilitation sur cette operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);            
            
            
            if(avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("liquidation")){
                avanceContratPlacementForm.setLibelleOperation("PRISE EN CHARGE LIQUIDATION ANTICIPEE D'UN PLACEMENT ");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE.toString());
                forward = "initierLiquidAnticipePlacement";
                if(avanceContratPlacementForm.getCodSbdvDemd().equals("1")){
                    // liquidation sous bonne date valeur
                    avanceContratPlacementForm.setLibelleOperation("PRISE EN CHARGE LIQUIDATION ANTICIPEE SOUS BONNE DATE VALEUR");
                    forward = "liquidationPlacementSBDV";
                }
            }else if(avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("validationLiquidation")){
                avanceContratPlacementForm.setLibelleOperation("VALIDATION LIQUIDATION ANTICIPEE D'UN PLACEMENT");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE.toString());
                afficherListeLiquidationAValider(avanceContratPlacementForm,paramAgence) ;
                forward = "initierValidationLiquidationPlacement";                      
            }else if(avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
                avanceContratPlacementForm.setLibelleOperation("PRISE EN CHARGE RESILIATION D'UN CONTRAT PLACEMENT ");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RESILIATION.toString());
                afficherListeLiquidationAValider(avanceContratPlacementForm,paramAgence) ;
                forward = "initierLiquidAnticipePlacement";                      
            }else if(avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("validationResiliation")){
                avanceContratPlacementForm.setLibelleOperation("VALIDATION RESILIATION D'UN CONTRAT PLACEMENT");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RESILIATION.toString());
                afficherListeLiquidationAValider(avanceContratPlacementForm,paramAgence) ;
                forward = "initierValidationLiquidationPlacement"; 
                
            }
            
                
            return mapping.findForward(forward);         
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidValidPlacementAction / Dispatch Action :initierPage ");
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

    
  
    public ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {


        try {
            AvancRembLiquidValidPlacementForm avanceContratPlacementForm = (AvancRembLiquidValidPlacementForm)form;
            PersonneDemandeur personneDemandeur = avanceContratPlacementForm.getPersonneDemandeur();
            Pouvoir pouvoir = (Pouvoir)request.getSession().getAttribute("pouvoir");
            if (!pouvoir.equals(null)) {
                avanceContratPlacementForm.setPouvoir(pouvoir);
                avanceContratPlacementForm.setTypeDemandeur(pouvoir.getTypePouvoir());
                personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
                if (personneDemandeur.getTypePouvoir().equals("N") || 
                    personneDemandeur.getTypePouvoir().equals("")) {
                    avanceContratPlacementForm.getPouvoir().setTypePouvoir("TR");
                    avanceContratPlacementForm.setAlertDemandeur("pouvoirInvalide");
                } else{
                    avanceContratPlacementForm.setAlertDemandeur("pouvoirValide");
                    if (personneDemandeur.getTypePouvoir().equals("M") && pouvoir.getMandat().getCodTypMand().equalsIgnoreCase("S")){///*** Cas mandat spéciale
                        ConsultEnveloppeRestanteCmd consultEnveloppeRestanteCmd =new ConsultEnveloppeRestanteCmd();
                        MandatOperation mandatOperation = (MandatOperation)pouvoir.getListMandatOperation().get(0);
                        PrimitiveVO primitiveVO = (PrimitiveVO)consultEnveloppeRestanteCmd.execute(mandatOperation);
                        avanceContratPlacementForm.setMontOperOmp(mandatOperation.getMontMlimMaop().toString());
                        avanceContratPlacementForm.setEnvRestOmp(primitiveVO.getVLong().toString());
                        avanceContratPlacementForm.setTypeMandat("S");///*** mandat spéciale
                    }
                }
                
            } else {
                avanceContratPlacementForm.setAlertPouvoir("false");
            }
            
            String forward="";
            if(avanceContratPlacementForm.getCodSbdvDemd().equals("1")){
                // liquidation sous bonne date valeur               
                forward = "liquidationPlacementSBDV";
            }else  forward = "initierLiquidAnticipePlacement";
            
            return mapping.findForward(forward);
                
        } catch (Exception e) {
            System.out.println("Erreur chargerPouvoir  " + e.getMessage());
            return mapping.findForward("error");
        }
    }


    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        //Context context = ContextHandler.getContext();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            /*******recherche du contrat**********/
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            String codStrcStrc = "";
            String codPrdPrd = "";
            String numCcptCcpt = "";
            ContratView contratView = avancRembLiquidValidPlacementForm.getContratView();
            codStrcStrc = avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc();
            codPrdPrd = avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd();
            numCcptCcpt = avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt();
            contratView.setCodStrcStrc(codStrcStrc);
            contratView.setCodPrdPrd(codPrdPrd);
            contratView.setNumCcptCcpt(numCcptCcpt);
            contratCptId.setCodStrcStrc(new Long(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(new Long(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(new Long(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt()));
            contratCpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);

            if (!contratCpt.hasError()) {
                if (contratCpt.getContratCptId() != null) {
                    /* Chargement du contrat si son etat est valide */
                    if (contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {
                        avancRembLiquidValidPlacementForm.setContratCpt(contratCpt);
                        avancRembLiquidValidPlacementForm.getContratView().setContratCpt(contratCpt);
                        avancRembLiquidValidPlacementForm.setSoldeFinal(avancRembLiquidValidPlacementForm.getContratView().getMontSoldFCcpt());
                        avancRembLiquidValidPlacementForm.setAlertContrat("contratValide");

                    } else {
                        avancRembLiquidValidPlacementForm.setAlertContrat("ContratNonvalide");
                        avancRembLiquidValidPlacementForm.setEtatContrat(contratCpt.getCodEtatCcpt());
                    }
                } else
                    avancRembLiquidValidPlacementForm.setAlertContrat("contratInexistant");


            } else {
                List listErreur = contratCpt.getErrors();
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
            
            String forward="";
            if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                // liquidation sous bonne date valeur               
                forward = "liquidationPlacementSBDV";
            }else  forward = "initierLiquidAnticipePlacement";
            return mapping.findForward(forward);

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidValidPlacementAction / Dispatch Action :rechercherContrat ");
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

    public ActionForward rechercherContratsPlacement(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {
       /// Context context = ContextHandler.getContext();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {
            avancRembLiquidValidPlacementForm.setListeContratsPlacement(null);
            ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
            GetListContratsPlacementCmd getListContratsPlacementCmd = new GetListContratsPlacementCmd();
            Listes listContratPlacement = new Listes();
            ContratPersonne contratPersonne = new ContratPersonne();
            ContratCptId contratCptId = new ContratCptId();
            avancRembLiquidValidPlacementForm.setListeContratsPlacement(null);
            if (avancRembLiquidValidPlacementForm.getContratView() != null && 
                !avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd().equals("")) {

                contratCptId.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd()));
                contratCptId.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));
                contratCptId.setNumCcptCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt()));

                contratPersonne.setContratCptId(contratCptId);
            }
            
             paramDemandeDecision.setContratPersonne(contratPersonne);
             paramDemandeDecision.setCodEtatContrat(Constants.ETAT_CONTRAT_PLAC_VALIDE);
            
            listContratPlacement = (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);
            if (!listContratPlacement.hasError()) {
                if (listContratPlacement.getList() != null && 
                    listContratPlacement.getList().size() > 0) {                  
                    
                    List listContratsPlacementView = traiterListeContratsPlacement(listContratPlacement.getList(), avancRembLiquidValidPlacementForm);
                    avancRembLiquidValidPlacementForm.setListeContratsPlacement(listContratsPlacementView);
                    avancRembLiquidValidPlacementForm.setAlertContratPlacement("False");
                } else {
                    avancRembLiquidValidPlacementForm.setAlertContratPlacement("True");
                }
            } else {
                List listErreur = listContratPlacement.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
           
            String forward="";
            if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                // liquidation sous bonne date valeur               
                forward = "liquidationPlacementSBDV";
            }else  forward = "initierLiquidAnticipePlacement";
            return mapping.findForward(forward);         

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidValidPlacementAction / Dispatch Action :rechercherContratsPlacement ");
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


    public List traiterListeContratsPlacement(List listContrats, 
                                              ActionForm form) {

        AvancRembLiquidValidPlacementForm avanceContratPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        List listContratPlacementView = new ArrayList();

        if (listContrats != null && listContrats.size() > 0) {

            for (Iterator it = listContrats.iterator(); it.hasNext(); ) {
                ContratPlacement contratPlacement = 
                    (ContratPlacement)it.next();

                if((contratPlacement.getDatEcheCpla().after(DateHandler.strToDate(avanceContratPlacementForm.getDateComptable())))
                  || (DateHandler.dateToStr(contratPlacement.getDatEcheCpla()).equals(avanceContratPlacementForm.getDateComptable())))  {
                 ContratPlacementView contratPlacementView = 
                    new ContratPlacementView();
                contratPlacementView.setContratPlacement(contratPlacement);
                contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));

                String duree = 
                    Double.valueOf(DateHandler.getDaysBetween(contratPlacement.getDatValCpla(), 
                                                              contratPlacement.getDatEcheCpla())).toString();

                contratPlacementView.setDuree(duree);

                contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                contratPlacementView.setMontActuCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontActuCpla().doubleValue())));
                contratPlacementView.setMontArlCpla(StrHandler.formatmnt(Long.valueOf(new Long(new Double(((contratPlacement.getMontCapCpla().doubleValue())-(contratPlacement.getMontActuCpla().doubleValue())) ).longValue()))));
                
                contratPlacementView.setDateValeur(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
                contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                
                if (contratPlacement.getCodPintCpla().equals("PRE")) {
                    contratPlacementView.setCodPintCpla("a l'avance");
                } else {
                    contratPlacementView.setCodPintCpla("a terme echu");
                }                
                if (contratPlacement.getDatValCpla().before(DateHandler.strToDate("01/04/2012"))){
                if(avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
                    // cas de la résiliation... verifier que les produits placement sont Bc BCDc CAT CATDC
                     double dureePlc = 0; 
                    if(!contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                         dureePlc = DateHandler.getDaysBetween(contratPlacement.getDatValCpla(),DateHandler.strToDate(avanceContratPlacementForm.getInitialisationView().getDateComptable()));
                         if(dureePlc <= 90 ){
                              listContratPlacementView.add(contratPlacementView);
                          }                        
                    }
                }else {
                    if(avanceContratPlacementForm.getCodSbdvDemd().equals("1")){
                      // cas de la liquidation anticipée sous bonne date valeur ( ne concerne que les BNA PLACEMENT...)
                       if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                           listContratPlacementView.add(contratPlacementView);
                       }                    
                    }else listContratPlacementView.add(contratPlacementView);
                }
                }
            }
            } // Fin For 
        }
        return listContratPlacementView;
    }

    public ActionForward afficherContratsPlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ContratPlacement contratPlacementTrouve = new ContratPlacement();
        
        try {
            avancRembLiquidValidPlacementForm.setSommeInterets("0");
            avancRembLiquidValidPlacementForm.setSommeInteretsProrats("0");
            avancRembLiquidValidPlacementForm.setLiquidationPossible("");
            avancRembLiquidValidPlacementForm.setBnaPlc("");
            avancRembLiquidValidPlacementForm.setListeAvance(null);
            avancRembLiquidValidPlacementForm.setListeInteretServi(null);
            avancRembLiquidValidPlacementForm.getContratPlacementView().setBoolAvance("false");
            avancRembLiquidValidPlacementForm.setAlertExistAvance("false");
            avancRembLiquidValidPlacementForm.setCasLiqTotaleApLiqPartielle("false");
            avancRembLiquidValidPlacementForm.setTypeFaveurPlc("");
            Double tauxVariable = Double.valueOf("0");
            
            if (!avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals(null) && 
                !avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals("")) {
                GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
                avancRembLiquidValidPlacementForm.setListeInteretServi(null);
                ContratPlacement contratPlacement = new ContratPlacement();
                contratPlacement.setNumSeqCpla(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi()));
                contratPlacementTrouve = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
                Date dateAvance = new Date();   
                boolean penaliteBnaPlacement = false;
                double duree = 0; 
                if (!contratPlacementTrouve.hasError()) {
                    if (contratPlacementTrouve != null && 
                        contratPlacementTrouve.getNumSeqCpla() != null) {
                        avancRembLiquidValidPlacementForm.setContratPlacement(contratPlacementTrouve);
                        if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1") ){
                            // cas de la liquidation anticipée sous bonne date valeur  
                             if(avancRembLiquidValidPlacementForm.getDateValAjax() != null &&  !avancRembLiquidValidPlacementForm.getDateValAjax().equals("")){
                                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatArlArl(avancRembLiquidValidPlacementForm.getDateValAjax());                                                                                                           
                             }else avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatArlArl(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable());                                            

                        }else avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatArlArl(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable());                                                                                                           
                          //  cas de liquidation anticipée normale 
                        avancRembLiquidValidPlacementForm.setContratPlacementView(affecterContratPlacementView(contratPlacementTrouve,avancRembLiquidValidPlacementForm));                       
                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("True");                          
                       
                          // cas de la liquidation
                          // verifier si la date de la liquidation dépasse 3 mois au minimum en cas de BC et CAT
                        
                        // calculer la duree reele de placement
                        duree = DateHandler.getDaysBetween(contratPlacementTrouve.getDatValCpla(),DateHandler.strToDate(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatArlArl()));
                           
                        if(contratPlacementTrouve.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                            // traitement du cas du BNA PLACEMENT
                             if(contratPlacementTrouve.getCodPintCpla().equals(Constants.PLACEMENT_PRECOMPTE) ){
                                 // un bnaPlacement PRECOMPTE ne peut pas être liquidé par anticipation
                                 avancRembLiquidValidPlacementForm.setBnaPlc("True");  
                                 avancRembLiquidValidPlacementForm.setLiquidationPossible("False");
                             }else {
                                 if(duree <= 90 ){
                                      // liquidation BNA PLACELMENT faite avant 3 mois... ==> pénalisation : le taux appliqué est 2 % 
                                     penaliteBnaPlacement = true; 
                                 }else penaliteBnaPlacement = false; 
                                 
                                    avancRembLiquidValidPlacementForm.setLiquidationPossible("True");
                                    avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(String.valueOf(Long.valueOf((int)duree)));
                                    avancRembLiquidValidPlacementForm.setPenalitePlacementBnaPlc(penaliteBnaPlacement);
                                    
                                   if(!contratPlacementTrouve.getMontActuCpla().equals(contratPlacementTrouve.getMontCapCpla()) && avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("T") ){                                                           
                                       // verifier s'il ya lieu des liquidation anticipées partielle : le capital n'est pas egal au montant actualisé
                                       avancRembLiquidValidPlacementForm.setCasLiqTotaleApLiqPartielle("true");
                                       avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setMontArlArl(StrHandler.formatmnt(Math.abs(contratPlacementTrouve.getMontActuCpla().doubleValue())));
                                   }
                                    
                             }
                            
                        }else{
                          // traitement des autres produits...     BC / CAT                    
                           
                           
                           if(duree <= 90 && !avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
                                // liquidation doit se faire au minumum apres 3 mois...
                               avancRembLiquidValidPlacementForm.setLiquidationPossible("False");
                           }else{
                              avancRembLiquidValidPlacementForm.setLiquidationPossible("True");
                              avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(String.valueOf(Long.valueOf((int)duree-1) ));
                              //avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatArlArl(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable());
                              
                              afficherListeAvance(avancRembLiquidValidPlacementForm) ; 
                           }  
                        }
                        
                    } else {
                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("False");
                    }

                } else {
                    List listErreur = contratPlacementTrouve.getErrors();
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
           
            
            if (avancRembLiquidValidPlacementForm.getLiquidationPossible().equals("True")) {
                ///*** marge de majoration pour une avance (Cond Banq)
                
               if(contratPlacementTrouve.getCodPintCpla().equals(Constants.PLACEMENT_PRECOMPTE)){
                  if(!avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation"))
                    avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE.toString()); 
                  else avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION.toString()); 
                    
               }else {
                  // placement postcompté
                   if(!avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation"))
                     avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE.toString()); 
                   else avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION.toString()); 
                   
                   // rechercher la liste des interets servis... et extraire la dernière date de versement d'interet / le montant total des intéret servis
                   avancRembLiquidValidPlacementForm.setDateLastInteretServi("");
                   List listIntView = new ArrayList();
                   Date dateIntertMax = null;
                   GetListInteretServiCmd getListInteretServiCmd = new GetListInteretServiCmd();
                   Listes listInt = (Listes)getListInteretServiCmd.execute(contratPlacementTrouve);
                   Double montInteret = Double.valueOf(0);  
                   Double montInteretProrat = Double.valueOf(0);
                   int nbrInteretservi =0;
                   if(listInt != null &&  listInt.getList() != null &&  listInt.getList().size()>0){
                       for (Iterator it = listInt.getList().iterator(); it.hasNext(); ) {
                               InteretServi interetServi = (InteretServi)it.next();                    
                             if(interetServi.getCodTypIsrv() != null &&  interetServi.getCodTypIsrv().equalsIgnoreCase("P")){
                               InteretServiView interetServiView =  new InteretServiView();
                               interetServiView.setNumIsrvIsrv(interetServi.getNumIsrvIsrv().toString());
                               interetServiView.setDatIsrvIsrv(DateHandler.dateToStr(interetServi.getDatIsrvIsrv()));
                               interetServiView.setDatValIsrv(DateHandler.dateToStr(interetServi.getDatValIsrv()));
                               interetServiView.setMontBrutIrc(StrHandler.formatmnt(Math.abs(interetServi.getMontBrutIsrv().doubleValue())));
                               interetServiView.setMontIrcIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIrcIsrv().doubleValue())));
                               interetServiView.setMontIsrvIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIsrvIsrv().doubleValue())));                               
                                nbrInteretservi = nbrInteretservi +1;
                               
                               if(dateIntertMax != null){
                                 if(interetServi.getDatIsrvIsrv().after(dateIntertMax))
                                     dateIntertMax = interetServi.getDatIsrvIsrv();
                               }else dateIntertMax = interetServi.getDatIsrvIsrv();
                               
                               listIntView.add(interetServiView); 
                               
                               montInteret =Math.abs(interetServi.getMontBrutIsrv().doubleValue()) + montInteret; 
                               
                            }
                       } 
                        
                        // verifier s'il s'agit d'une liquidation partielle OU le cas d'une liquidation totale après des liquidations partielles : pour extraire le montant des intérêt servis pour le capital qui va être liquidé
                       if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P") || avancRembLiquidValidPlacementForm.getCasLiqTotaleApLiqPartielle().equals("true")){
                            montInteretProrat =  Math.rint(((new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                                     1000).longValue())) * 365 * Double.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla())/36000)* nbrInteretservi) ;
                          
                           if(!montInteretProrat.equals(Double.valueOf(0)))
                             avancRembLiquidValidPlacementForm.setSommeInteretsProrats(StrHandler.formatmnt(Math.abs(montInteretProrat)));
                             avancRembLiquidValidPlacementForm.setTexteLiqPartielle("Intérêt servi pour le capital de " + avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl());
                             avancRembLiquidValidPlacementForm.setNbreInteretServi(String.valueOf(nbrInteretservi));

                       }
                       if(!montInteret.equals(Double.valueOf(0)))
                         avancRembLiquidValidPlacementForm.setSommeInterets(StrHandler.formatmnt(Math.abs(montInteret)));
                         
                       avancRembLiquidValidPlacementForm.setListeInteretServi(listIntView);                       
                       avancRembLiquidValidPlacementForm.setDateLastInteretServi(DateHandler.dateToStr(dateIntertMax));
                                                                    
                   }
                  
               }
               
                TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
                
                if(!avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
                    // cas de la liquidation Anticipée...
                    if(penaliteBnaPlacement ){     
                        // taux fixe appliqué en cas de liquidation bna placement avant 3 mois...( 0 % ).                        
                        avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(traitementConditionBanque.getTauxInteret()));                     
                        
                    }else{
                     
                        if (avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("G") ||
                            avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("F") ||
                            avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("P"))
                        {///*** Taux général
                            avancRembLiquidValidPlacementForm.setTypeFaveurPlc("");
                            // si la condition est ponctuelle, le taux est fixe, on le récupére a partir de ValMarge/ modif 20.04.2010
                            if(traitementConditionBanque.getSignMarge() != null){
                                // le cas où le taux n'est pas indexé mais présenté sous forme d'un Taux + une marge (à partir de la CB)
                                Double t = (Double.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla())+Double.valueOf(traitementConditionBanque.getSignMarge()));
                                Double t1 = Math.abs(t); 
                                avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(t1));
                                }else{
                                   //le taux est une constante sans marge !!
                                    Double t = Double.valueOf(traitementConditionBanque.getTauxInteret());
                                    Double t1 = Math.abs(t); 
                                    avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(t1));
                                }                          
                            
                        }else{///*** Taux variable
                            
                           
                            if(contratPlacementTrouve.getCodMargCpla().equalsIgnoreCase("+"))
                                tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm) + contratPlacementTrouve.getNumMargCpla());
                            else tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm) - contratPlacementTrouve.getNumMargCpla());                            
                            
                            if(traitementConditionBanque.getSignMarge() != null){
                               avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(tauxVariable +Double.valueOf(traitementConditionBanque.getSignMarge())));
                            }else {
                               avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(traitementConditionBanque.getTauxInteret()));
                            }
                        }  
                    }
               }else{
                   // cas de la résiliation...
                   // application soit de la condition générale ::: taux de résiliation = 0%
                   // soit appliquer une condition de banque préférenteille plafonnée à 2 %....
                   avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(traitementConditionBanque.getTauxInteret()));     
                   
               } 
                
            }  
            }
            
            String forward="";
            if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                // liquidation sous bonne date valeur               
                forward = "liquidationPlacementSBDV";
            }else  forward = "initierLiquidAnticipePlacement";
            
            return mapping.findForward(forward);           
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans avancRembLiquidValidPlacementAction / Dispatch Action :afficherContratsPlacement ");
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


    public ContratPlacementView affecterContratPlacementView(ContratPlacement contratPlacement, ActionForm form) {

        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;

        ContratPlacementView contratPlacementView = new ContratPlacementView();
        contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
        contratPlacementView.setMontCapCpla(contratPlacement.getMontCapCpla().toString());
        contratPlacementView.setMontActuCpla(StrHandler.formatmnt(Math.abs((contratPlacement.getMontActuCpla().doubleValue()))));
        contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
        contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
        contratPlacementView.setDateValeur(DateHandler.dateToStr(contratPlacement.getDatValCpla()));
        contratPlacementView.setCodEtatCpla(contratPlacement.getCodEtatCpla());
        contratPlacementView.setCodPintCpla(contratPlacement.getCodPintCpla());                
        contratPlacementView.setTauIRC(contratPlacement.getNumTircCpla().toString());
        if (contratPlacement.getNumBcCpla()!=null)///*** cas CAT (pas de N° de bon de caisse)
          contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
        
        contratPlacementView.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
        contratPlacementView.setTypeFaveurCpla(contratPlacement.getCodFavCpla().toString());        
        contratPlacementView.setDuree(contratPlacement.getNumNbrjCpla().toString());
        
        if( Long.valueOf(new Long(new Double(((contratPlacement.getMontCapCpla().doubleValue())-(contratPlacement.getMontActuCpla().doubleValue())) ).longValue())) > Long.valueOf("0")){
            contratPlacementView.setBoolAvance("true");
          
        }else contratPlacementView.setBoolAvance("False");
        
        avancRembLiquidValidPlacementForm.setContratPlacementView(contratPlacementView);
        
        if(contratPlacement.getCodFavCpla().equalsIgnoreCase("I")){
            // calculer le taux moyen du placement...
            Double T = Double.valueOf(0);
             if(contratPlacement.getCodMargCpla().equalsIgnoreCase("+")){
               T = calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm) + contratPlacement.getNumMargCpla();                 
             }else T = calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm) - contratPlacement.getNumMargCpla();
            
            contratPlacementView.setNumTauiCpla(T.toString());
        }else{
           contratPlacementView.setNumTauiCpla(contratPlacement.getNumTauiCpla().toString());
        }
        
        
        
        return (contratPlacementView);
    }
    

   

    public DetailsOperationPlacement affecterDonneesDetailsOperationPlacement(ActionForm form) {
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        
        DetailsOperationPlacement detailsOperationPlacement =  new DetailsOperationPlacement();


        if (!avancRembLiquidValidPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur().equals(null) && 
            !avancRembLiquidValidPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur().equals("")) {
            TypePiece typePieceSouscripteur = new TypePiece();
            typePieceSouscripteur.setCodTpceTpce(Long.valueOf(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));
            detailsOperationPlacement.setTypePieceByCodTpssTpce(typePieceSouscripteur);
            detailsOperationPlacement.setNumNpssDopl(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur());
        }
        detailsOperationPlacement.setDatOperDopl(new Date()); 

        Tache tache = new Tache();
        TacheId tacheId = new TacheId();        
        
        if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("liquidation")){
            if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("T")){
                tacheId.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE); 
                if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                   // liquidation SBDV
                    tacheId.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV);
                }
            }else if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P")){
                   tacheId.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE);    
                   if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                        // liquidation SBDV
                      tacheId.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV);
                   }
             }             
            tacheId.setCodTachTach(Constants.COD_TACHE_DEMANDE_LIQUIDATION_ANTICIPE);
        }
        
        
         if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("validationLiquidation")){            
            tacheId.setCodOperOper(Long.valueOf(avancRembLiquidValidPlacementForm.getOperInteret()));
            tacheId.setCodTachTach(Constants.COD_TACHE_DEMANDE_LIQUIDATION_ANTICIPE);
        } 
        
        if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){            
            if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
              tacheId.setCodOperOper(Constants.COD_OPER_RESILIATION);
              tacheId.setCodTachTach(Long.valueOf("1"));
            }
        }
        
        if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("validationResiliation")){            
              tacheId.setCodOperOper(Long.valueOf(avancRembLiquidValidPlacementForm.getOperInteret()));
              tacheId.setCodTachTach(Long.valueOf("1"));
        }
        
        tache.setTacheId(tacheId);                
        detailsOperationPlacement.setTache(tache);

        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(avancRembLiquidValidPlacementForm.getInitialisationView().getNumMatrUser());
        detailsOperationPlacement.setPersonnel(personnel);

        Structure structure = new Structure();
        structure.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeAgence()));
        detailsOperationPlacement.setStructure(structure);
        detailsOperationPlacement.setDatOperDopl(avancRembLiquidValidPlacementForm.getInitialisationView().getDateOp());
        
         
         TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
         detailsOperationPlacement.setDatValDopl(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));          
         avancRembLiquidValidPlacementForm.setDateValeur(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));///*** affecter la date valeur a la forme pour ne pas reiterer la getCB
                
        detailsOperationPlacement.setMontDopDopl(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                                     1000).longValue()));
        detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
        
        if (!avancRembLiquidValidPlacementForm.getContratPlacement().equals(null)) {
            detailsOperationPlacement.setContratPlacement(avancRembLiquidValidPlacementForm.getContratPlacement());
        }
        return detailsOperationPlacement;
    }

    /**
      * Fonction qui retourne un objet AvancRembLiquid
      * appelé par l'Action qui insére l' AvancRembLiquid dans la base
      */

    public AvancRembLiquid affecterDonneesAvancRembLiquid(ActionForm form, 
                                                          ContratPlacement contratPlacement) {

        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();

        if (!avancRembLiquidValidPlacementForm.getContratPlacementView().equals(null)) {
            avancRembLiquid.setContratPlacement(contratPlacement);
        }

        avancRembLiquid.setDatArlArl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatArlArl()));
        avancRembLiquid.setMontArlArl(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                          1000).longValue()));
       
   
      
        avancRembLiquid.setCodToprArl(Constants.CODE_LIQUIDATION_ANTICIPE);
        
        if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
           avancRembLiquid.setCodToprArl(Constants.CODE_RESILIATION_PLAC);
        }    
        
        avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
        
        avancRembLiquid.setCodTyplArl(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation());      
         
         if(contratPlacement.getCodPintCpla().equals(Constants.PLACEMENT_PRECOMPTE)){
             // si type de payement precompté alors les interets seront perçus
              avancRembLiquid.setCodTypiArl("P"); ///*** type d'interet (percus)  
               if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontantDebourserBrut().equals("")) {
                   avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontantDebourserBrut())).doubleValue() * 
                                                                   1000));                  
               }
                     
           }else{
                if(avancRembLiquidValidPlacementForm.getTypePost().equalsIgnoreCase("versement"))
                  avancRembLiquid.setCodTypiArl("S"); ///*** type d'interet (servi) 
                else avancRembLiquid.setCodTypiArl("P"); ///*** type d'interet (a percevoir) 
                
                 if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir().equals("")) {
                    avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir())).doubleValue() * 
                                                                   1000));                  
                  }
              }              
              
              if(!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getNumTauiArl().equals("")) {
                avancRembLiquid.setNumTauiArl(new Double(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getNumTauiArl()).doubleValue());
              }
              
             if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                // Liquidation SBDV
                 avancRembLiquid.setCodSbdvArl("1");                
             }else avancRembLiquid.setCodSbdvArl("0");
              
        return avancRembLiquid;
    }


    public ActionForward clearPage(ActionMapping mapping, ActionForm form, 
                                   HttpServletRequest request, 
                                   HttpServletResponse response) throws IOException, 
                                                                        ServletException {
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {

            avancRembLiquidValidPlacementForm.clearFormAvancRembLiquidValidPlacement();

            return mapping.findForward("initValiderSouscriptionPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer(" ");
            text.append(e.getMessage());
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


 

    public List traiterListeAvance(Set listAvance, ActionForm form) {

            AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
            List listAvanceView = new ArrayList();

            if (listAvance != null && listAvance.size() > 0) {

                for (Iterator it = listAvance.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it.next();
                    if(avancRembLiquid.getDatReelArl()==null  ){///*** verifier si l'avance n'est pas deja remboursée
                        AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                        avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                        avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                        avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                        avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                        String duree = Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),avancRembLiquid.getDatPrevArl()))) + 1).toString();
                        avancRembLiquidView.setDuree(duree);
                        avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                        avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                        avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                        avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));

                        
                        listAvanceView.add(avancRembLiquidView);
                    }
                } // Fin For 
            }
            
            return listAvanceView;

        }
   
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (ActionForm form){
        
            AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
            
            OperationMoyPay operationMoyPay = new OperationMoyPay();          
            Personnel personnelInit = new Personnel();
            personnelInit.setNumMatrUser(avancRembLiquidValidPlacementForm.getInitialisationView().getNumMatrUser());
            Operation operation = new Operation();    
            Structure structureInit = new Structure();    
            structureInit.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeAgence()));
             
            Structure structureRecep = new Structure();    
            structureRecep.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));           
             
            ContratCpt contratCpt =new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt()));
            contratCptId.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));
            contratCpt.setContratCptId(contratCptId);
            operationMoyPay.setContratCpt(contratCpt);
              
            operationMoyPay.setStructureInitiatrice(structureInit);
            operationMoyPay.setStructureReceptrice(structureRecep);
            
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
            ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
            
            Devise devise = new Devise();
            devise.setCodDevDev(cpt.getDevise().getCodDevDev());
            operationMoyPay.setDevise(devise);
            
            Produit produitPlacOmp =new Produit();
            
            
            operationMoyPay.setCodRefbOmp(avancRembLiquidValidPlacementForm.getNumContratPlacChoisi());
            operationMoyPay.setCodRefmOmp(avancRembLiquidValidPlacementForm.getAvancRembLiquid().getNumSeqArl().toString());

            operationMoyPay.setCodRefcOmp("");
            produitPlacOmp.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd()));
            operationMoyPay.setProduit(produitPlacOmp);
            
            if (avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla()!=null && avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla().toString()!="")
            operationMoyPay.setNumMoypOmp(avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla().toString());

            operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
            operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
            operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
            
            //operationMoyPay.setCodRefcOmp(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation());
            //operation.setCodOperOper(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation()));
            if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("T")){
                operation.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE);     
                if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                   // liquidation SBDV
                    operation.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV);
                }
            }else if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P")){
                operation.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE);  
                if(avancRembLiquidValidPlacementForm.getCodSbdvDemd().equals("1")){
                   // liquidation SBDV
                    operation.setCodOperOper(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV);
                }
            }else if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
               // resiliation
                operation.setCodOperOper(Constants.COD_OPER_RESILIATION);            
            }
            
            Tache tache = new Tache();
            tache.setOperation(operation);
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(operation.getCodOperOper());            
            tacheId.setCodTachTach(Constants.COD_TACHE_VALIDATION_LIQUIDATION_ANTICIPE);
           
            tache.setTacheId(tacheId);
            operationMoyPay.setTache(tache);
            
            operationMoyPay.setDatOperOmp(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable()));
            operationMoyPay.setDatSystOmp(new Date());           
            operationMoyPay.setDatValOmp(avancRembLiquidValidPlacementForm.getDateValeur());
            if (cpt !=null){
                operationMoyPay.setMontSoldCcpt(cpt.getMontSoldCcpt());
            }else
                operationMoyPay.setMontSoldCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getMontSoldCcpt()));

            operationMoyPay.setMontDinOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 1000).longValue()));
            operationMoyPay.setCodDemOmp("T");
            operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
            operationMoyPay.setMontApreOmp(avancRembLiquidValidPlacementForm.getAvancRembLiquid().getContratPlacement().getContratCpt().getMontSoldCcpt() + avancRembLiquidValidPlacementForm.getAvancRembLiquid().getMontArlArl());
            operationMoyPay.setLibMotfOmp("Liquidation placement");
            
            if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("T")){
                operationMoyPay.setLibMotfOmp("Liquidation Anticipée totale");   
            }else if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P")){
                operationMoyPay.setLibMotfOmp("Liquidation Anticipée Partielle");              
            }else if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
                operationMoyPay.setLibMotfOmp("Résiliation placement");         
            }
          
            return operationMoyPay;
        }    


   

    public TraitementConditionBanque getCB(ActionForm form) {
    
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

        try {
            traitementConditionBanque.setCodOperOper(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation());
          ///  traitementConditionBanque.setCodOperOper("301");
            traitementConditionBanque.setCodPrdPrd(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd());
            traitementConditionBanque.setNumCcptCcpt(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt());
            traitementConditionBanque.setCodStrcStrc(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc());
            traitementConditionBanque.setCodPrdCpt(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd());
         
         
         ContratCptId contratCptId = new ContratCptId();
         contratCptId.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd()));
         contratCptId.setNumCcptCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt()));
         contratCptId.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));
         
          
         GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
         ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);  
         
       
         if (avancRembLiquidValidPlacementForm.getPersonneDemandeur().getTypePouvoir().equals("C")){
              traitementConditionBanque.setCodTpceTpce(avancRembLiquidValidPlacementForm.getContratPlacement().getPersonne().getTypePiece().getCodTpceTpce().toString());
              traitementConditionBanque.setNumPcePers(Constants.COD_NUM_ORDRE.toString());            
         }else{
               traitementConditionBanque.setCodTpceTpce(cpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
               traitementConditionBanque.setNumPcePers(cpt.getClient().getPersonne().getNumPcePers().toString());            
         }
       
            traitementConditionBanque.setIdContrat(avancRembLiquidValidPlacementForm.getNumContratPlacChoisi());
            if (avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl()!=null && !avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl().equalsIgnoreCase("")){
                traitementConditionBanque.setMontant(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                           1000).longValue()).toString());
            }
            traitementConditionBanque.setNbUnites(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDuree());
         
            traitementConditionBanque.setDateReference(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatArlArl());
            
            if(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd().equals(String.valueOf(Constants.COD_PRD_BNAPLC_PLAC))){
              // le cas de la liquidation d'un bna placement ( possibilité d'une liquidation avec un taux initial)
              // donc passer comme paramètres le taux initial et la marge initiale
              traitementConditionBanque.setMargeInitiale(0.0f);
              traitementConditionBanque.setTauxInitial(Float.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla()).floatValue());
            }
            
            traitementConditionBanque.getCB();
            
           
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
                text.append("Exception au niveau de l'agence:"); text.append(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeAgence());
                text.append(". Exception Cond Bq:"); text.append(e.getMessage());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
            }
        return traitementConditionBanque;   
    
    } 
  
  
     
    public ActionForward validationLiqAnticipePlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {


        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        //ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        
        PecLiquidationAnticipeCmd pecLiquidationAnticipeCmd = new PecLiquidationAnticipeCmd();
        DetailsOperationPlacement detailsOperationPlacement =  new DetailsOperationPlacement();
        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
        ParamContratPlacement paramContratPlacement =  new ParamContratPlacement();

        try {
            ContratPlacement contratPlacement = new ContratPlacement();
            GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
            contratPlacement.setNumSeqCpla(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi()));
            contratPlacement = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
            if(contratPlacement.getCodEtatCpla().equalsIgnoreCase(Constants.ETAT_CONTRAT_PLAC_VALIDE)){
            // le contrat placement n'est pas encore pris en charge pour une liquidation anticipée à travers un autre USER ....
            ///*** Garnir l'objet detailsOperationPlacement
            detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(avancRembLiquidValidPlacementForm);
            detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
            paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement);

            ///*** Garnir l'objet avancRembLiquid
            avancRembLiquid = affecterDonneesAvancRembLiquid(avancRembLiquidValidPlacementForm,contratPlacement);
            paramContratPlacement.setAvancRembLiquid(avancRembLiquid);
            ///*** montant actualisé dans le contrat placement suite a une avance
            /*Long montActu =Long.valueOf(new Long(new Double(((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getContratPlacementView().getMontActuCpla())).doubleValue())-(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue())) * 
                                                          1000).longValue()));
            paramContratPlacement.getAvancRembLiquid().getContratPlacement().setMontActuCpla(montActu);*/

            ///*** Cas d'operation faite par un mandataire
             if(avancRembLiquidValidPlacementForm.getPouvoir().getTypePouvoir().equals("M")){
              Set listeMandPersOperPlac = new HashSet(0);
              for (Iterator it = avancRembLiquidValidPlacementForm.getPouvoir().getListMandatPersonne().iterator();it.hasNext(); ) { 
                  MandatPersonne mandatPersonne = (MandatPersonne)it.next();                  
                  MandPersOperPlac mandPersOperPlac = new MandPersOperPlac();
                  MandPersOperPlacId mandPersOperPlacId = new MandPersOperPlacId();
                  mandPersOperPlacId.setNumMandMand(mandatPersonne.getMandat().getNumMandMand());               
                  mandPersOperPlacId.setNumSeqPers(mandatPersonne.getPersonne().getNumSeqPers());
                  mandPersOperPlac.setMandPersOperPlacId(mandPersOperPlacId);
                  
                  listeMandPersOperPlac.add(mandPersOperPlac);
              }
                 paramContratPlacement.getDetailsOperationPlacement().setMandPersOperPlacs(listeMandPersOperPlac);
                 if (avancRembLiquidValidPlacementForm.getTypeMandat().equalsIgnoreCase("S")){///*** Cas mandat spéciale
                    paramContratPlacement.getDetailsOperationPlacement().setMandatOperation((MandatOperation)avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation().get(0));
                 }
                 /*if(avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation() != null && avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation().size()>0 ){
                     MandatOperation mandatOperation = (MandatOperation)avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation().get(0);
                     operationMoyPay.setMandatOperation(mandatOperation);                  
                 }*/
             }
             
                  ///*** Cas d'operation faite par un cotitulaire
                  if(avancRembLiquidValidPlacementForm.getPouvoir().getTypePouvoir().equals("C")){
                      // cas cotitulaire
                       if(avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire()!=null && avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire().size()>0 ){
                           CoTitulaire cotitulaire = (CoTitulaire)avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire().get(0);
                           paramContratPlacement.getDetailsOperationPlacement().setCoTitulaire(cotitulaire);
                       }         
                  }            

             ///*** Insertion dans la Base (AVANC_REMB_LIQUID + DETAILS_OPERATION_PLACEMENT + MAND_PERS_OPER_PLAC) + MAJ du CONTRAT_PLACEMENT
             avancRembLiquid =(AvancRembLiquid)pecLiquidationAnticipeCmd.execute(paramContratPlacement);

            if (!avancRembLiquid.hasError()) {
                String message = "";                
                
                if(!avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("resiliation")){
                message = 
                        " L'opération de Prise en charge de la liquidation anticipée  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement N° "+contratPlacement.getNumSeqCpla()+
                        " a été effectuée avec succès et en attente de la validation par le chef d'agence...";
                        
                } else {
                    message = 
                            " L'opération de Prise en charge de la résiliation  N° " + avancRembLiquid.getNumSeqArl().toString() + " Du Contrat Placement N° "+contratPlacement.getNumSeqCpla()+
                            " a été effectuée avec succès et en attente de la validation par le chef d'agence...";         
                }    
                
                avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                return mapping.findForward("confirmationLiqAnticipePlacement");
            } else {
                List listErreur = avancRembLiquid.getErrors();
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
            }else{
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer(" Le placement est déjà liquidé et en attente de validation par le chef d'agence ... ");
                text.append(erreur.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());

                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer(" ");
            text.append(e.getMessage());
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
    
    public void afficherListeLiquidationAValider(ActionForm form,ParamAgence paramAgence) {

            AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
            ActionMessages actionMessages = new ActionMessages();

            try {

                    GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                    Listes listes = new Listes();
                    ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                    paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
                    paramAvanRembLiq.setCodStrcStrc(paramAgence.getCodStrcStrc());                    
                    paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);    
                    
                    if(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("validationResiliation")){
                        paramAvanRembLiq.setCodToprtArl(Constants.CODE_RESILIATION_PLAC);
                    }
                    
                    listes = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);
                    
                     if (listes.getList() != null && listes.getList().size() > 0) {
                            
                        List listeLiqView = new ArrayList();
                        Set listesLiqSet =new HashSet();
                        listesLiqSet.addAll(listes.getList());                                                          
                        listeLiqView = traiterListeLiquidation(listesLiqSet,avancRembLiquidValidPlacementForm);                                
                                
                        avancRembLiquidValidPlacementForm.setListeLiquidationAnticipe(listeLiqView);
                        avancRembLiquidValidPlacementForm.setAlertExistLiquidation("True");
                      } else {
                           avancRembLiquidValidPlacementForm.setAlertExistLiquidation("False");
                      }
     
                   
                
            } catch (Exception e) {
                throw new RuntimeException(e);  
            }
        }

    
    public List traiterListeLiquidation(Set listLiquidation, ActionForm form) {

            AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
            List listAvanceView = new ArrayList();

       try { 
            if (listLiquidation != null && listLiquidation.size() > 0) {

                for (Iterator it = listLiquidation.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it.next();                    
                        if(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().equals(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeAgence()))){
                            if(!avancRembLiquid.getContratPlacement().getCodEtatCpla().equalsIgnoreCase(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
                                AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                                avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                                avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                                avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                                avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                                avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                                avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                                avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                                avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                              
                                listAvanceView.add(avancRembLiquidView);   
                            }
                    } 
                } 
            }
            
            return listAvanceView;
        }
     catch (Exception e) {
        throw new RuntimeException(e);   
       
    }

}



    public ActionForward afficherInfoLiquidation(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        Context context = ContextHandler.getContext();
    try {
        if (!avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals(null) && 
            !avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals("")) {
            GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd = new GetAvancRembLiquidByIdCmd();
            AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
            avancRembLiquidValidPlacementForm.setListeInteretServi(null);
            avancRembLiquidValidPlacementForm.setSommeInterets("0");
            avancRembLiquidValidPlacementForm.setSommeInteretsProrats("0");
            avancRembLiquidValidPlacementForm.setListeAvance(null);
            avancRembLiquidValidPlacementForm.setListeInteretServi(null);
            avancRembLiquidValidPlacementForm.getContratPlacementView().setBoolAvance("false");
            avancRembLiquidValidPlacementForm.setAlertExistAvance("false");
            avancRembLiquidValidPlacementForm.setCasLiqTotaleApLiqPartielle("false");
            avancRembLiquidValidPlacementForm.setTypeFaveurPlc("");
            Double tauxVariable = Double.valueOf(0);
            avancRembLiquid.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroLiqChoisi()));
            avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);
            boolean penaliteBnaPlacement = false;
            
            if(avancRembLiquid != null){
                avancRembLiquidValidPlacementForm.setContratPlacement(avancRembLiquid.getContratPlacement());
                avancRembLiquidValidPlacementForm.setCodSbdvDemd(avancRembLiquid.getCodSbdvArl());
                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                avancRembLiquidValidPlacementForm.setContratPlacementView(affecterContratPlacementView(avancRembLiquid.getContratPlacement(),avancRembLiquidValidPlacementForm));
                
                if (avancRembLiquid.getContratPlacement() != null && avancRembLiquid.getContratPlacement().getNumSeqCpla() != null) {
                    afficherListeAvance(avancRembLiquidValidPlacementForm) ;                   
                }
                Date dateLiquidation = (avancRembLiquid.getDatArlArl());
                double duree =  DateHandler.getDaysBetween(avancRembLiquid.getContratPlacement().getDatValCpla(),avancRembLiquid.getDatArlArl());
                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(String.valueOf(Long.valueOf((int)duree-1) ));
                                                
                avancRembLiquidValidPlacementForm.getContratView().setCodStrcStrc(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString());
                avancRembLiquidValidPlacementForm.getContratView().setCodPrdPrd(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString());
                avancRembLiquidValidPlacementForm.getContratView().setNumCcptCcpt(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                avancRembLiquidValidPlacementForm.getContratView().setMontSoldCcpt(avancRembLiquid.getContratPlacement().getContratCpt().getMontSoldCcpt().toString());
                avancRembLiquidValidPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getNumPcePers());
                avancRembLiquidValidPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getTypePiece().getCodTpceTpce().toString());

                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                avancRembLiquidValidPlacementForm.setNumContratPlacChoisi(avancRembLiquid.getContratPlacement().getNumSeqCpla().toString());
                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setTypeLiquidation(avancRembLiquid.getCodTyplArl());
                
                if(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) || avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)){
                  // afficher le numéro du BC en cas de bon de caisse...
                  avancRembLiquidValidPlacementForm.setNumBc(avancRembLiquid.getContratPlacement().getNumBcCpla().toString());         
                  avancRembLiquidValidPlacementForm.setAlertNumBc("");
                }
                
                avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE.toString()); 
                if(avancRembLiquid.getCodTyplArl().equalsIgnoreCase("R")){
                    // cas de la résiliation...
                     avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION.toString()); 
                }
                
                Date dateIntertMax = null;
                
                if(avancRembLiquid.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_POSTCOMPTE)){
                   // placement postcompté
                    avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE.toString()); 
                    
                    if(avancRembLiquid.getCodTyplArl().equalsIgnoreCase("R")){
                        // cas de la résiliation...
                         avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION.toString()); 
                    }
                    // cas de liquidation totale ap des liquidations partielles :
                     if(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                      if(!avancRembLiquid.getContratPlacement().getMontActuCpla().equals(avancRembLiquid.getContratPlacement().getMontCapCpla()) && avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("T") ){                              
                          
                            avancRembLiquidValidPlacementForm.setCasLiqTotaleApLiqPartielle("true");
                      }
                     }
                    
                    // rechercher la liste des interets servis...
                    avancRembLiquidValidPlacementForm.setDateLastInteretServi("");
                    List listIntView = new ArrayList();
                    
                    GetListInteretServiCmd getListInteretServiCmd = new GetListInteretServiCmd();
                    Listes listInt = (Listes)getListInteretServiCmd.execute(avancRembLiquid.getContratPlacement());
                    Double montInteret = Double.valueOf(0);
                    Double montInteretProrat = Double.valueOf(0);
                    int nbrInteretservi =0;
                    avancRembLiquidValidPlacementForm.setSommeInterets("0");
                    if(listInt.getList() != null && listInt.getList().size()>0){
                        for (Iterator it = listInt.getList().iterator(); it.hasNext(); ) {
                                InteretServi interetServi = (InteretServi)it.next(); 
                                if( interetServi.getCodTypIsrv()!= null && interetServi.getCodTypIsrv().equalsIgnoreCase("P")){
                                    InteretServiView interetServiView =  new InteretServiView();
                                    interetServiView.setNumIsrvIsrv(interetServi.getNumIsrvIsrv().toString());
                                    interetServiView.setDatIsrvIsrv(DateHandler.dateToStr(interetServi.getDatIsrvIsrv()));
                                    interetServiView.setDatValIsrv(DateHandler.dateToStr(interetServi.getDatValIsrv()));
                                    interetServiView.setMontBrutIrc(StrHandler.formatmnt(Math.abs(interetServi.getMontBrutIsrv().doubleValue())));
                                    interetServiView.setMontIrcIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIrcIsrv().doubleValue())));
                                    interetServiView.setMontIsrvIsrv(StrHandler.formatmnt(Math.abs(interetServi.getMontIsrvIsrv().doubleValue())));                               
                                    nbrInteretservi = nbrInteretservi + 1 ;
                                    
                                    if(dateIntertMax != null){
                                      if(interetServi.getDatIsrvIsrv().after(dateIntertMax))
                                          dateIntertMax = interetServi.getDatIsrvIsrv();
                                    }else dateIntertMax = interetServi.getDatIsrvIsrv();
                                    
                                    listIntView.add(interetServiView);
                                    
                                    montInteret =Math.abs(interetServi.getMontBrutIsrv().doubleValue()) + montInteret;  
                             }
                        } 
                        if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P") || avancRembLiquidValidPlacementForm.getCasLiqTotaleApLiqPartielle().equals("true")){
                            montInteretProrat =  (( avancRembLiquid.getMontArlArl().doubleValue() * 365 * Double.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla())/36000)* nbrInteretservi) ;
                           
                            if(!montInteretProrat.equals(Double.valueOf(0)))
                              avancRembLiquidValidPlacementForm.setSommeInteretsProrats(StrHandler.formatmnt(Math.abs(montInteretProrat)));
                            avancRembLiquidValidPlacementForm.setTexteLiqPartielle("Intérêt servi pour le capital de " + avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl());
                        }
                        
                        if(!montInteret.equals(Double.valueOf(0)))
                          avancRembLiquidValidPlacementForm.setSommeInterets(StrHandler.formatmnt(Math.abs(montInteret)));
                        
                        avancRembLiquidValidPlacementForm.setListeInteretServi(listIntView);
                        avancRembLiquidValidPlacementForm.setDateLastInteretServi(DateHandler.dateToStr(dateIntertMax)); 
                        
                    }
                   
                 
                    avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(String.valueOf(Long.valueOf((int)duree-1) ));
                }
                
                if(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                    if(avancRembLiquid.getContratPlacement().getCodPintCpla().equals(Constants.PLACEMENT_POSTCOMPTE) ){
                        if(duree <= 90 ){
                             // liquidation BNA PLACELMENT faite avant 3 mois... ==> pénalisation : le taux appliqué est 2 % 
                            penaliteBnaPlacement = true; 
                        }else penaliteBnaPlacement = false;                        
                         
                        avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(String.valueOf(Long.valueOf((int)duree)));
                        avancRembLiquidValidPlacementForm.setPenalitePlacementBnaPlc(penaliteBnaPlacement);
                    }   
                }
                
                TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
                
                if(penaliteBnaPlacement  || avancRembLiquid.getCodTyplArl().equalsIgnoreCase("R")){     
                    // taux fixe appliqué en cas de liquidation bna placement avant 3 mois...( 0 % ).
                    // Ou bien dans le cas de la résiliation...
                    avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(traitementConditionBanque.getTauxInteret()));
                    
                }else{
                
                    if (avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("G") ||
                        avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("F") ||
                        avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("P"))
                    {///*** Taux général
                        avancRembLiquidValidPlacementForm.setTypeFaveurPlc("");
                        // si la condition est ponctuelle, le taux est fixe, on le récupére a partir de ValMarge/ modif 20.04.2010
                        if(traitementConditionBanque.getSignMarge() != null){
                           avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(Math.abs(Double.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla())+Double.valueOf(traitementConditionBanque.getSignMarge()))));
                        }else{
                            avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(Math.abs(Double.valueOf(traitementConditionBanque.getTauxInteret()))));
                        }
                        
                    }else{///*** Taux variable
                                               
                        if(avancRembLiquid.getContratPlacement().getCodMargCpla().equalsIgnoreCase("+"))
                            tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm)) + avancRembLiquid.getContratPlacement().getNumMargCpla();
                        else tauxVariable = Math.abs(calculerMoyenneTauxPlacement(avancRembLiquidValidPlacementForm)) - avancRembLiquid.getContratPlacement().getNumMargCpla();                            
                        
                        if(traitementConditionBanque.getSignMarge() != null){
                            avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(tauxVariable+Double.valueOf(traitementConditionBanque.getSignMarge())));
                        }else{
                            avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(traitementConditionBanque.getTauxInteret()));
                        }
                    }
                }
                avancRembLiquidValidPlacementForm.setDateValeurInteret(traitementConditionBanque.getDatevaleur());
                avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setNumTauiArl(avancRembLiquidValidPlacementForm.getValMarge());
                
                if(!avancRembLiquid.getCodTyplArl().equalsIgnoreCase("R")){
                    if(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) || avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)){
                        // verifier si le BC est récuperé avant de valider la liquidation anticipée...
                        PlacementDAO plcDao = (PlacementDAO)context.getBean("placementDAO");
                        avancRembLiquidValidPlacementForm.setBcRecupere(plcDao.verifierRecuperationBc(avancRembLiquid.getContratPlacement().getNumBcCpla(),avancRembLiquid.getContratPlacement().getNumSeqCpla()));
                    }
               }else avancRembLiquidValidPlacementForm.setBcRecupere(true);
            }
        }
        
        return mapping.findForward("initierValidationLiquidationPlacement");      

    } catch (Exception e) {
        ActionMessage actionMessage = new ActionMessage("exception.generique", e.getMessage() );
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        return mapping.findForward("error");  
    }
    }


    public ActionForward confirmationValidLiquidationPlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
    
    try {
            ContratPlacement contratPlacement = new ContratPlacement();
            GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
            
        
                if (!avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals(null) && 
                        !avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals("")) {
                        GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd = new GetAvancRembLiquidByIdCmd();
                        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
                        avancRembLiquid.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroLiqChoisi()));
                        avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);
                        
                        if(avancRembLiquid != null){
                        
                        contratPlacement.setNumSeqCpla(avancRembLiquid.getContratPlacement().getNumSeqCpla());
                        contratPlacement = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
                        if(contratPlacement.getCodEtatCpla().equalsIgnoreCase(Constants.ETAT_CPT_PLC_ATT_LIQUID) || contratPlacement.getCodEtatCpla().equalsIgnoreCase(Constants.ETAT_CPT_PLC_ATT_RESILIATION) ){ 
                         
                        avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                        avancRembLiquid.setCodTyplArl(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation());
                        avancRembLiquid.getContratPlacement().setMontActuCpla(Long.valueOf(new Long(new Double(((new Double(avancRembLiquid.getContratPlacement().getMontActuCpla()).doubleValue())-((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue())) * 1000)).longValue())));
                        avancRembLiquid.setMontArlArl(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 1000).longValue()));
                          
                        Double montantBrut = Double.valueOf(0); 
                        Double montantIRC = Double.valueOf(0);
                        Double montantNet = Double.valueOf(0);
                        
                          if(avancRembLiquidValidPlacementForm.getOperInteret().equals(String.valueOf(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE))
                             || avancRembLiquidValidPlacementForm.getOperInteret().equals(String.valueOf(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION))){
                            // si type de payement precompté alors les interets seront perçus (OP 322)  OU  (OP 630 ristourne suite à une résiliation)                    
                            // le client va débourser un montant puisqu'il a empoché les intérêts à l'avance
                            if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontantDebourserBrut().equals("")) {
                               montantNet = (new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontantDebourserBrut())).doubleValue() * 
                                                                         1000));     
                               
                               montantBrut = (new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontBrutLiq())).doubleValue() * 
                                                                               1000));  
                                                                               
                               avancRembLiquid.setMontInetArl(Double.valueOf(Math.round(montantNet))); 
                               avancRembLiquid.setMontBrutArl(Double.valueOf(Math.round(montantBrut)));
                               montantIRC = Double.valueOf(0);
                               
                            }                   
                          
                          }else{                      
                              //operation de versement d'intéret pour le client
                             if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir().equals("")) {
                                montantNet = (new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir())).doubleValue() * 
                                                                                 1000));
                                                            
                              avancRembLiquid.setMontInetArl(Double.valueOf(Math.round(montantNet)));                      
                              
                              if(avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC)
                                 || avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_CAT_PLAC)
                                 || avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BNAPLC_PLAC)){
                                 
                                 
                                 
                                  montantIRC    = (new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontantReelIRC())).doubleValue() * 
                                                                                 1000));  
                                 
                                  montantBrut = (new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontBrutLiq())).doubleValue() * 
                                                                                 1000));  
                              }else{
                              // montant IRc = 0 pour les produit de placement en dinars convertibles
                                  montantBrut = montantNet;
                                  montantIRC = Double.valueOf(0);
                              }
                              
                                 avancRembLiquid.setMontBrutArl(Double.valueOf(Math.round(montantBrut)));   
                                 avancRembLiquid.setMontIrcArl(Double.valueOf(Math.round(montantIRC)));
        
                             }
                              
                          } 
                          
                        if(!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getNumTauiArl().equals("")) {
                          avancRembLiquid.setNumTauiArl(new Double(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getNumTauiArl()).doubleValue());
                        } 
                        avancRembLiquid.setDatValiArl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateValeurInteret()));
                        
                        
                        avancRembLiquidValidPlacementForm.setContratPlacement(avancRembLiquid.getContratPlacement());
                        
                        DetailsOperationPlacement detailsOperationPlacement0 = new DetailsOperationPlacement();
                
                       ///****mandat operation
                        for (Iterator it = avancRembLiquid.getDetailsOperationPlacements().iterator(); it.hasNext(); ){
                            detailsOperationPlacement0 = (DetailsOperationPlacement)it.next();
                        }
                        
                        avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(String.valueOf(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE));
                        if(avancRembLiquid.getCodSbdvArl().equals("1")){
                           // liquidation SBDV
                           if(avancRembLiquid.getCodTyplArl().equalsIgnoreCase("T"))
                              avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(String.valueOf(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV));
                           else if(avancRembLiquid.getCodTyplArl().equalsIgnoreCase("P"))
                              avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(String.valueOf(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV));
                        }
                        if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
                            // cas de la résiliation
                             avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(String.valueOf(Constants.COD_OPER_RESILIATION));
        
                        }
                        DetailsOperationPlacement detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(avancRembLiquidValidPlacementForm);            
                        avancRembLiquid.setDatArlArl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable()));
                        detailsOperationPlacement.setMandatOperation(detailsOperationPlacement0.getMandatOperation());
                        //detailsOperationPlacement.setDatCompDopl(avancRembLiquid.getDatArlArl());
                        detailsOperationPlacement.setMontDopDopl(avancRembLiquid.getMontArlArl());
                       
                        ///*** Garnir operationMoyPay
                        avancRembLiquidValidPlacementForm.setAvancRembLiquid(avancRembLiquid);
                        
                        OperationMoyPay operationMoyPayNew = affecterDonneesOperationMoyenPaiement(avancRembLiquidValidPlacementForm);
                        detailsOperationPlacement.setOperationMoyPay(operationMoyPayNew);
                        
                        avancRembLiquid.getDetailsOperationPlacements().add(detailsOperationPlacement);
                
                        TraitementLiquidationCmd traitementLiquidationCmd = new TraitementLiquidationCmd();
                        ParamLiquidation paramLiquidation = new ParamLiquidation();
                        paramLiquidation.setAvancRembLiquid(avancRembLiquid);
                        paramLiquidation.setContratPlacement(avancRembLiquid.getContratPlacement());
                        paramLiquidation.setDateComptLiquidation(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatArlArl()));
                        paramLiquidation.setDureeReelPlacement(Long.valueOf(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDuree()));
                        paramLiquidation.setInteretReelPlacement(avancRembLiquid.getMontBrutArl()); 
                        paramLiquidation.setDateComptableAg(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
                        paramLiquidation.setOperationInteretLiq(avancRembLiquidValidPlacementForm.getOperInteret());
                        paramLiquidation.setMntIrc(montantIRC);                
                        paramLiquidation.setTauxInteretPlacement(new Double(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla()).doubleValue());
                        //new Double(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getNumTauiArl()).doubleValue());
                        if(!avancRembLiquidValidPlacementForm.getSommeInterets().equals("")){
                          paramLiquidation.setMontInteretServi(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getSommeInterets())).doubleValue() * 1000));
                        }
                        
                        if(avancRembLiquidValidPlacementForm.getCasLiqTotaleApLiqPartielle().equals("true")){
                           // traitement du cas d'une liquidation anticipé totale suite à une liquidation anticipée partielle 
                            paramLiquidation.setMontInteretServi(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getSommeInteretsProrats())).doubleValue() * 1000));
                        }
                        
                        paramLiquidation.setMntAPercevoirAVerser(avancRembLiquid.getMontInetArl());
                        if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("P") && !avancRembLiquidValidPlacementForm.getSommeInteretsProrats().equals(""))
                          paramLiquidation.setMontInteretProratat(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getSommeInteretsProrats())).doubleValue() * 1000));
                        
                        //*** Insertion dans la Base (AVANC_REMB_LIQUID + DETAILS_OPERATION_PLACEMENT + MAND_PERS_OPER_PLAC) + MAJ du CONTRAT_PLACEMENT
                        avancRembLiquid =(AvancRembLiquid)traitementLiquidationCmd.execute(paramLiquidation);
                        imprimerAvisLiquidationAnticipee(avancRembLiquid,request,avancRembLiquidValidPlacementForm);
                
                    if (!avancRembLiquid.hasError()) {
                        
                        String message = "";
                        
                        if(!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
                            message = " L'opération de validation de la liquidation anticipée  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                                      " a été effectuée avec succès.";
                        }else{
                            message = " L'opération de résiliation de placement  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                                      " a été effectuée avec succès.";
                        }
                        avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                        return mapping.findForward("confirmationLiqAnticipePlacement");
                    } else {
                        List listErreur = avancRembLiquid.getErrors();
                        for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                            com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = 
                            new ActionMessage("exception.generique", erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }
                        this.saveMessages(request, actionMessages);
                       
                    }
                    }else{
                        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                        StringBuffer text = 
                            new StringBuffer(" La validation de la liquidation/Resiliation est déjà éffectuée par le chef d'agence ... ");
                        text.append(erreur.toString());
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
                    
            }
            return mapping.findForward("error");
        } catch (Exception e) {
            ActionMessage actionMessage = new ActionMessage("exception.generique", e.getMessage() );
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }
    }

    public void afficherListeAvance(ActionForm form) {

        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {

                GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                Listes listesAvances = new Listes();
                ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeAgence()));
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);         
                paramAvanRembLiq.setNumSeqCpla(avancRembLiquidValidPlacementForm.getContratPlacement().getNumSeqCpla());
                listesAvances = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);
                if (!listesAvances.hasError()) {
                        if (listesAvances.getList() != null && listesAvances.getList().size() > 0) {
                        
                            List listeAvanceView = new ArrayList();
                            Set listesAvancesSet =new HashSet();
                            listesAvancesSet.addAll(listesAvances.getList());
                            listeAvanceView = traiterListeAvance(listesAvancesSet,avancRembLiquidValidPlacementForm);
                            
                            avancRembLiquidValidPlacementForm.setListeAvance(listeAvanceView);
                            avancRembLiquidValidPlacementForm.setAlertExistAvance("True");
                        } else {
                            avancRembLiquidValidPlacementForm.setAlertExistAvance("False");
                        }
    
                } else {
                    List listErreur = listesAvances.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
        }
    } 
    
    
    public void imprimerAvisLiquidationAnticipee(AvancRembLiquid avancRembLiquid,HttpServletRequest request,ActionForm form) {
    
             AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("AvisLiquidationAnticipe");
                 parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : LIQUIDATION ANTICIPEE");
                 if(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
                     parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : RESILIATION CONTRAT DE PLACEMENT");
                 }
                 
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_NUM_SEQ_LIQ", avancRembLiquid.getNumSeqArl());
                 parameters.put("P_NUM_OPER_OPER", avancRembLiquidValidPlacementForm.getOperInteret());
                 parameters.put("DUPLICATA", "");
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("");
             text.append(e.getMessage());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
             }                                                                               
                                                                                               
         }    

 
    //
    
     public ActionForward genererAlerteRecupBc(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {
                                                                                      
         AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
         ActionMessages actionMessages = new ActionMessages();
         Context context = ContextHandler.getContext();
         
         
         return mapping.findForward("alerteRecupBc");

        

     }

    
    public ActionForward rejeterLiquidationPlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
         ActionMessages actionMessages = new ActionMessages();
         AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
         try {
         
         if (!avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals(null) && 
                 !avancRembLiquidValidPlacementForm.getNumeroLiqChoisi().equals("")) {
                 GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd = new GetAvancRembLiquidByIdCmd();
                 AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
                 avancRembLiquid.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroLiqChoisi()));
                 avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);
                 
                 if(avancRembLiquid != null){

                   avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_REJETEE);
                   avancRembLiquid.getContratPlacement().setCodEtatCpla(Constants.ETAT_CONTRAT_PLAC_VALIDE);
                   ParamContratPlacement paramContratPlacement = new ParamContratPlacement();
                   paramContratPlacement.setAvancRembLiquid(avancRembLiquid);
                   RejeterLiquidationAnticipeCmd rejeterLiquidationAnticipeCmd= new RejeterLiquidationAnticipeCmd();
                   avancRembLiquid = (AvancRembLiquid)rejeterLiquidationAnticipeCmd.execute(paramContratPlacement);
                   
                   
                         
                         String message = "";
                         
                         if(!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getTypeLiquidation().equalsIgnoreCase("R")){
                             message = " L'opération de rejet de la liquidation anticipée  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                                       " a été effectuée avec succès.";
                         }else{
                             message = " L'opération de rejet de la résiliation   N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                                       " a été effectuée avec succès.";
                             
                         }
                         avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                     
                 }
                 
                 }         
         
         } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("la transaction est Interrompu, une erreur dans avancRembLiquidValidPlacementAction / Dispatch Action :rejeterLiquidationPlacement ");
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
                                                                                        
         return mapping.findForward("confirmationLiqAnticipePlacement");                                                                                 
     }
     
     
     
    public Double calculerMoyenneTauxPlacement(ActionForm form) {
    
      AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
         double taux = Double.valueOf(0);
      try {    
        
        ParamDates paramDates = new ParamDates();
        paramDates.setDateDebut(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getContratPlacementView().getDateValeur()));
        paramDates.setDateFin(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatArlArl()));
        paramDates.setInterval(Constants.INTERVAL_TMM_JOURNALIER);
        GetAvgTMMbetweenDatesCmd getAvgTMMbetweenDates = new GetAvgTMMbetweenDatesCmd();
        PrimitiveVO primitiveVO = (PrimitiveVO)getAvgTMMbetweenDates.execute(paramDates); 
        taux = (primitiveVO.getVDouble().doubleValue());        
      
     }catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = new StringBuffer("");
         text.append(e.getMessage());
         erreur.setCode("200");
         erreur.setDescription(text.toString());
         ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
     }  
         return (taux);
                                                                                       
     }
     
     
}

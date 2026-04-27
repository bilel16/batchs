
package com.bna.smile.web.placement.actions;


import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.Devise;
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
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;

import com.bna.smile.model.constant.Constants;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domaineplacement.commande.ValiderPECAvancePlacementCmd;


import java.util.Date;

import com.bna.commun.util.StrHandler;
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
import com.bna.smile.model.domaineplacement.commande.ValiderAvancePlacementCmd;
import com.bna.smile.model.domaineplacement.model.ParamContratPlacement;
import com.bna.smile.model.domaineplacement.model.ParamDates;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.view.ContratView;
import com.bna.smile.web.placement.forms.AvancRembLiquidValidPlacementForm;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.domaineplacement.commande.UpdateAvanceRembLiquCmd;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.domaineplacement.traitement.UpdateAvanceRembLiquTrt;
import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;

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

public class AvancRembLiquidValidPlacementAction extends DispatchAction {

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

    ActionForward initierAvancePlacement(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        AvancRembLiquidValidPlacementForm avanceContratPlacementForm = 
            (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
        
       /*     ParamDates paramDates = new ParamDates();
            paramDates.setDateDebut((DateHandler.addJour(new Date(),-400)));
            paramDates.setDateFin(new Date());
            paramDates.setInterval("M");
            GetAvgTMMbetweenDates getAvgTMMbetweenDates = new GetAvgTMMbetweenDates();
            PrimitiveVO primitiveVO = (PrimitiveVO)getAvgTMMbetweenDates.exec(paramDates);
        */
            ///avanceContratPlacementForm.setTypeForm("Avance");
            avanceContratPlacementForm.clearFormAvancRembLiquidValidPlacement();
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            avanceContratPlacementForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            avanceContratPlacementForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
            avanceContratPlacementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            avanceContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            avanceContratPlacementForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            avanceContratPlacementForm.setDateComptable(paramAgence.getDateComptable());
            if (avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("Avance")){
                avanceContratPlacementForm.setLibelleOperation("PEC Avance sur Capital");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_AVANCE_PLAC.toString());
                return mapping.findForward("initierAvancePlacement");
            }else {
                avanceContratPlacementForm.setLibelleOperation("Remboursement d'une Avance sur Capital");
                avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_REMB_AVANCE_PLAC.toString());
                return mapping.findForward("remboursementAvancePlacement");
            }
            
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


    
    public ActionForward initierValidAvancePlacement(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        AvancRembLiquidValidPlacementForm avanceContratPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            avanceContratPlacementForm.clearFormAvancRembLiquidValidPlacement();
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            avanceContratPlacementForm.getInitialisationView().setDateOp(paramAgence.getDateOp());
            avanceContratPlacementForm.getInitialisationView().setDateComptable(paramAgence.getDateComptable());
            avanceContratPlacementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            avanceContratPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            avanceContratPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_AVANCE_PLAC.toString());
            avanceContratPlacementForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            /*ContratPlacementView contratPlacementView = new ContratPlacementView();
            contratPlacementView.setCodPrdPrd("1001");
            avanceContratPlacementForm.setContratPlacementView(contratPlacementView);*/
            avanceContratPlacementForm.setLibelleOperation("Validation Avance sur Capital");
            avanceContratPlacementForm.setDateComptable(paramAgence.getDateComptable());
            afficherListeAvanceAValider(avanceContratPlacementForm,paramAgence) ;           
            return mapping.findForward("initierValidAvancePlacement");
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidValidPlacementAction / Dispatch Action :initierValidAvancePlacement ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
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
                } else if(personneDemandeur.getTypePouvoir().equals("I")){
                    //demandeur incapable
                     avanceContratPlacementForm.setAlertDemandeur("demandeurIncapable");
                    }else{
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
                //return mapping.findForward("affichListContratPlac");         
            } else {
                avanceContratPlacementForm.setAlertPouvoir("false");
            }
                if (avanceContratPlacementForm.getTypeForm().equalsIgnoreCase("Avance")){
                    return mapping.findForward("initierAvancePlacement");
                }else {
                    return mapping.findForward("remboursementAvancePlacement");
                }
            
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

            /*   String forward="";

               if(avancRembLiquidValidPlacementForm.getTypeForm().equals("validerSouscription")){
                forward = "initValiderSouscriptionPlacement";
               }else if(avancRembLiquidValidPlacementForm.getTypeForm().equals("Avance")){
                   forward = "initierAvancePlacement";
                   }*/
             if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("Avance")){
                 return mapping.findForward("initierAvancePlacement");
             }else {
                 return mapping.findForward("remboursementAvancePlacement");
             }

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
            if (avancRembLiquidValidPlacementForm.getTypeForm().equals("validerSouscription")) {
                if (avancRembLiquidValidPlacementForm.getChoixValidPlac().equals("0")) {
                    paramDemandeDecision.setContratPersonne(contratPersonne);
                }
                paramDemandeDecision.setCodEtatContrat(Constants.ETAT_CONTRAT_PLAC_ATTENTE);
            } else {
                paramDemandeDecision.setContratPersonne(contratPersonne);
                paramDemandeDecision.setCodEtatContrat(Constants.ETAT_CONTRAT_PLAC_VALIDE);
            }
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
            String forward = "";

                if (avancRembLiquidValidPlacementForm.getTypeForm().equals("Avance")) {
                    forward = "initierAvancePlacement";
                }else {
                    if (avancRembLiquidValidPlacementForm.getTypeForm().equals("RemboursementAv")) {
                        forward = "remboursementAvancePlacement";
                    }
                }
            

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
                ContratPlacement contratPlacement = (ContratPlacement)it.next();
                if (verifEligibilite(contratPlacement)){
                    ContratPlacementView contratPlacementView =  new ContratPlacementView();
                    contratPlacementView.setContratPlacement(contratPlacement);
                    contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
                    contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
    
                    String duree = Double.valueOf(DateHandler.getDaysBetween(contratPlacement.getDatCreCpla(), 
                                                                  contratPlacement.getDatEcheCpla())).toString();
    
                    contratPlacementView.setDuree(duree);
    
                    contratPlacementView.setMontCapCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontCapCpla().doubleValue())));
                    contratPlacementView.setMontActuCpla(StrHandler.formatmnt(Math.abs(contratPlacement.getMontActuCpla().doubleValue())));
                    contratPlacementView.setMontArlCpla(StrHandler.formatmnt(Long.valueOf(new Long(new Double(((contratPlacement.getMontCapCpla().doubleValue())-(contratPlacement.getMontActuCpla().doubleValue())) ).longValue()))));
                    
                    if (contratPlacement.getCodPintCpla().equals("PRE")) {
                        contratPlacementView.setCodPintCpla("a l'avance");
                    } else {
                        contratPlacementView.setCodPintCpla("a terme echu");
                    }
                    listContratPlacementView.add(contratPlacementView);
                }// Fin If 
            }// Fin For 
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

            if (!avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals(null) && 
                !avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals("")) {
                GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
                ContratPlacement contratPlacement = new ContratPlacement();
                contratPlacement.setNumSeqCpla(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi()));
                contratPlacementTrouve = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
                Date dateAvance = new Date();
                if (!contratPlacementTrouve.hasError()) {
                    if (contratPlacementTrouve != null && contratPlacementTrouve.getNumSeqCpla() != null ) {
                        avancRembLiquidValidPlacementForm.setContratPlacement(contratPlacementTrouve);
                        avancRembLiquidValidPlacementForm.setContratPlacementView(affecterContratPlacementView(contratPlacementTrouve));

                        /*  if(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd()=="1001"){
                     avancRembLiquidValidPlacementForm.setAlertBC("True");
                 }
                 else{
                     avancRembLiquidValidPlacementForm.setAlertBC("False");
                 }*/
                        dateAvance = DateHandler.strToDate(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable());
                        double duree = DateHandler.getDaysBetween(dateAvance, contratPlacementTrouve.getDatEcheCpla())+1;

                        if (duree < 15) {
                            duree = 15;
                        }
                        avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(Long.valueOf(Math.round(duree)).toString());
                        avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDatPrevArl(DateHandler.dateToStr(contratPlacementTrouve.getDatEcheCpla()));


                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("True");
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
            }

            if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("Avance")){
                ///*** marge de majoration pour une avance (Cond Banq)
                avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_PERSEPT_INTERET_AVANCE_PLAC.toString());
                TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
                if (avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("G") ||
                    avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("F") ||
                    avancRembLiquidValidPlacementForm.getContratPlacementView().getTypeFaveurCpla().equalsIgnoreCase("P"))
                {///*** Taux général
                    avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(Double.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla())+Double.valueOf(traitementConditionBanque.getSignMarge())));
                }else{///*** Taux variable
                    ParamDates paramDates = new ParamDates();
                    paramDates.setDateDebut(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getContratPlacementView().getDatCreCpla()));
                    paramDates.setDateFin(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
                    paramDates.setInterval(Constants.INTERVAL_TMM_MENSUEL);
                    GetAvgTMMbetweenDatesCmd getAvgTMMbetweenDates = new GetAvgTMMbetweenDatesCmd();
                    PrimitiveVO primitiveVO = (PrimitiveVO)getAvgTMMbetweenDates.execute(paramDates);
                    
                    avancRembLiquidValidPlacementForm.setValMarge(String.valueOf(primitiveVO.getVDouble()+Double.valueOf(traitementConditionBanque.getSignMarge())-avancRembLiquidValidPlacementForm.getContratPlacement().getNumMargCpla()));
                    avancRembLiquidValidPlacementForm.getContratPlacementView().setNumTauiCpla(String.valueOf(primitiveVO.getVDouble()-avancRembLiquidValidPlacementForm.getContratPlacement().getNumMargCpla()));///***suite reclamation ahlem le 19/10/2009
                }
                
                avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_AVANCE_PLAC.toString());
                return mapping.findForward("initierAvancePlacement");
            }else {
                avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_REMB_AVANCE_PLAC.toString());
                return mapping.findForward("remboursementAvancePlacement");
            }
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


    public boolean  verifEligibilite(ContratPlacement contratPlacement) {
        
        boolean isEligible = false;
        Long [] produitPlacEligible = new Long [4]; 
        produitPlacEligible[0] = Constants.COD_PRD_BC_PLAC;
        produitPlacEligible[1] = Constants.COD_PRD_CAT_PLAC;
        produitPlacEligible[2] = Constants.COD_PRD_BCDC_PLAC;
        produitPlacEligible[3] = Constants.COD_PRD_CATDC_PLAC;

        for(int i=0;i<produitPlacEligible.length;i++){ 
            if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(produitPlacEligible[i])){
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }
    public ContratPlacementView affecterContratPlacementView(ContratPlacement contratPlacement) {

        ContratPlacementView contratPlacementView = new ContratPlacementView();
        contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
        contratPlacementView.setMontCapCpla(contratPlacement.getMontCapCpla().toString());
        contratPlacementView.setMontActuCpla(StrHandler.formatmnt(Math.abs((contratPlacement.getMontActuCpla().doubleValue()))));
        contratPlacementView.setDatEcheCpla(DateHandler.dateToStr(contratPlacement.getDatEcheCpla()));
        contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));
        contratPlacementView.setCodEtatCpla(contratPlacement.getCodEtatCpla());
        contratPlacementView.setCodPintCpla(contratPlacement.getCodPintCpla());
        contratPlacementView.setNumTauiCpla(contratPlacement.getNumTauiCpla().toString());
        contratPlacementView.setTauIRC(Constants.TAUX_IRC.toString());
        if (contratPlacement.getNumBcCpla()!=null)///*** cas CAT (pas de N° de bon de caisse)
        contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
        contratPlacementView.setCodPrdPrd(contratPlacement.getProduitPlacement().getCodPrdPlc().toString());
        contratPlacementView.setTypeFaveurCpla(contratPlacement.getCodFavCpla().toString());        

        return (contratPlacementView);
    }

    /*
     * Action qui permet d'insérer l'avance sur capital dans la base
     * appelé par la fonction JS: validerAvancePlacement()
     * renvoi vers une page de confirmation : confirmationAvanceContratPlacement.jsp
     */

    public ActionForward validationAvancePlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        //ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ContratPlacement contratPlacement = new ContratPlacement();
        ValiderPECAvancePlacementCmd avancePlacementCmd = new ValiderPECAvancePlacementCmd();
        DetailsOperationPlacement detailsOperationPlacement =  new DetailsOperationPlacement();
        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
        ParamContratPlacement paramContratPlacement =  new ParamContratPlacement();

        try {

            contratPlacement = avancRembLiquidValidPlacementForm.getContratPlacement();
            ///*** Garnir l'objet detailsOperationPlacement
            detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(avancRembLiquidValidPlacementForm);
            detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
            paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement);

            ///*** Garnir l'objet avancRembLiquid
            avancRembLiquid = affecterDonneesAvancRembLiquid(avancRembLiquidValidPlacementForm,contratPlacement);
            paramContratPlacement.setAvancRembLiquid(avancRembLiquid);
            ///*** montant actualisé dans le contrat placement suite a une avance
            Long montActu =Long.valueOf(new Long(new Double(((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getContratPlacementView().getMontActuCpla())).doubleValue())-(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue())) * 
                                                          1000).longValue()));
            paramContratPlacement.getAvancRembLiquid().getContratPlacement().setMontActuCpla(montActu);

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
             avancRembLiquid =(AvancRembLiquid)avancePlacementCmd.execute(paramContratPlacement);
             imprimerPECAvanceSurCapital(avancRembLiquid,request);///
            if (!avancRembLiquid.hasError()) {
                String message = "";
                message = 
                        " L'opération de Prise en charege de l'avance sur capital  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement N° "+contratPlacement.getNumSeqCpla()+
                        " a été effectuée avec succès.";
                avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                return mapping.findForward("confirmationAvancRembLiquidValidPlacement");
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
        tacheId.setCodOperOper(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation()));

        if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("ValidAv")){
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_AVANC_PLAC);
        }else{
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_REMB_AVANC_PLAC);
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

        avancRembLiquid.setDatArlArl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable()));
        avancRembLiquid.setMontArlArl(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                          1000).longValue()));
        avancRembLiquid.setDatPrevArl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDatPrevArl()));

         if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("Avance")){
             //avancRembLiquid.setDatPrevArl(new Date());         
             contratPlacement.setMontActuCpla((contratPlacement.getMontActuCpla()) - (avancRembLiquid.getMontArlArl()));  ///*** MAJ du montant actuel du placement
             avancRembLiquid.setCodToprArl(Constants.CODE_AVANCE);
             avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
             
             if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontInetArl().equals("")) {
                 avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontInetArl())).doubleValue() * 
                                                           1000));
                 avancRembLiquid.setCodTypiArl("P"); ///*** type d'interet (percus)   
             }
             if (!avancRembLiquid.getCodTypiArl().equalsIgnoreCase("")) {
                 avancRembLiquid.setNumTauiArl(new Double(avancRembLiquidValidPlacementForm.getValMarge()).doubleValue());
             }else avancRembLiquid.setNumTauiArl(new Double(0));
         
         }else{///*** cas du remboursement
            ///*** date de remboursement : 
            avancRembLiquid.setDatReelArl(new Date());
            ///*** remboursement liée a quelle avance
            AvancRembLiquid avancRemb = new AvancRembLiquid();
            avancRemb.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumAvanceChoisi()));
            avancRembLiquid.setAvancRembLiquid(avancRemb);
            avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
            avancRembLiquid.setCodToprArl(Constants.CODE_REMBOURSEMENT_AVANCE);
            contratPlacement.setMontActuCpla((contratPlacement.getMontActuCpla()) + (avancRembLiquid.getMontArlArl()));  ///*** MAJ du montant actuel du placement
             if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntRecu().equals("0.000") ) {///*** Montant du trop percu
                 avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntRecu())).doubleValue() * 1000));
                 avancRembLiquid.setCodTypiArl("S"); ///*** type d'interet (servis)
             }else{
                if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir().equals("0.000")){///*** Montant a percevoir
                    avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir())).doubleValue() * 1000));
                    avancRembLiquid.setCodTypiArl("P"); ///*** type d'interet (percus)
                }else{
                    avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontIntAPercevoir())).doubleValue() * 1000));
                    avancRembLiquid.setCodTypiArl(""); ///*** pas d'interet                     
                }
             }
             if (!avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla().equals("")) {
                 ///avancRembLiquid.setNumTauiArl(new Double(new Double(avancRembLiquidValidPlacementForm.getValMarge()).doubleValue()));
                  avancRembLiquid.setNumTauiArl(new Double(new Double(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontInetArl()).doubleValue()));
             }else avancRembLiquid.setNumTauiArl(new Double(0));

         }

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


    public ActionForward afficherListeAvance(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
       /// Context context = ContextHandler.getContext();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ContratPlacement contratPlacementTrouve = new ContratPlacement();

        try {

            if (!avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals(null) && 
                !avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi().equals("")) {
                GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
                ContratPlacement contratPlacement = new ContratPlacement();
                contratPlacement.setNumSeqCpla(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi()));
                contratPlacementTrouve = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
                if (!contratPlacementTrouve.hasError()) {
                    if (contratPlacementTrouve != null && contratPlacementTrouve.getNumSeqCpla() != null) {

                        GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                        Listes listesAvances = new Listes();
                        ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                        paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                        paramAvanRembLiq.setNumSeqCpla(contratPlacementTrouve.getNumSeqCpla());
                	paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);
                        listesAvances = (Listes)getListAvancRembLiquidByEtatCmd.execute(paramAvanRembLiq);

                        if (listesAvances != null && listesAvances.getList() != null && listesAvances.getList().size() > 0) {
                            Set listesAvancesSet =new HashSet();
                            listesAvancesSet.addAll(listesAvances.getList());
                            List listeAvanceView = traiterListeAvance(listesAvancesSet,avancRembLiquidValidPlacementForm);
                            avancRembLiquidValidPlacementForm.setListeAvance(listeAvanceView);
                            avancRembLiquidValidPlacementForm.setAlertExistAvance("True");
                        } else {
                            avancRembLiquidValidPlacementForm.setListeAvance(null);
                            avancRembLiquidValidPlacementForm.setAlertExistAvance("False");
                        }
                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("True");
                        
                    } else {
                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("False");
                    }

                } else {
                    List listErreur = contratPlacementTrouve.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                        ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);
                    }
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");
                }
            }
            return mapping.findForward("remboursementAvancePlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("");
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

    try{
        if (listAvance != null && listAvance.size() > 0) {

            for (Iterator it = listAvance.iterator(); it.hasNext(); ) {
                AvancRembLiquid avancRembLiquid = (AvancRembLiquid)it.next();
                if(avancRembLiquid.getDatReelArl()==null && !(avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("RemboursementAv") && !avancRembLiquid.getCodEtatArl().equalsIgnoreCase("V")) && avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString().equalsIgnoreCase(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc())){///*** verifier si l'avance n'est pas deja remboursée
                    AvancRembLiquidView avancRembLiquidView =  new AvancRembLiquidView();
                    avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                    avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                    avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                    avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                    avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                    String duree = Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(),avancRembLiquid.getDatPrevArl())+1))).toString();
                    avancRembLiquidView.setDuree(duree);
                    avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                    avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                    avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                    avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));

                    for (Iterator it1 = avancRembLiquid.getDetailsOperationPlacements().iterator();it1.hasNext(); ) { 
                        DetailsOperationPlacement detailsOperationPlacement = (DetailsOperationPlacement)it1.next();
                        PersonneStrc personneStrc= new PersonneStrc();
                        personneStrc.setCodTpceTpce(detailsOperationPlacement.getTypePieceByCodTpssTpce().getCodTpceTpce());
                        personneStrc.setNumPcePers(detailsOperationPlacement.getNumNpssDopl());
                        GetPersonneCmd getPersonneCmd =new GetPersonneCmd();
                        Personne personne = (Personne)getPersonneCmd.execute(personneStrc);
                        avancRembLiquidView.setLibRelation(personne.getNomPrnPers()+" "+personne.getNomNomPers());
                        break;
                    }
                   // avancRembLiquidValidPlacementForm.getContratView().setCodDevDev(avancRembLiquid.getContratPlacement().getContratCpt().getDevise().getCodDevDev().toString());
                 /*   avancRembLiquidValidPlacementForm.getContratView().setCodStrcStrc(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString());
                    avancRembLiquidValidPlacementForm.getContratView().setCodPrdPrd(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString());
                    avancRembLiquidValidPlacementForm.getContratView().setNumCcptCcpt(avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                    avancRembLiquidValidPlacementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getTypePiece().getCodTpceTpce().toString());
                    avancRembLiquidValidPlacementForm.getPersonneDemandeur().setNumPcePersDemandeur(avancRembLiquid.getContratPlacement().getPersonne().getNumPcePers());
                */
                    listAvanceView.add(avancRembLiquidView);
                }
            } // Fin For 
        }
             } catch (Exception e) {
                 com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                 StringBuffer text = new StringBuffer("");
                 text.append(e.getMessage());
                 erreur.setCode("300");
                 erreur.setDescription(text.toString());

                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique (traiterListeAvance) :", 
                                       erreur.getDescription());
             }
        return listAvanceView;

    }



    public ActionForward afficherAvance(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
                                                                                        
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ContratPlacement contratPlacementTrouve = new ContratPlacement();

        try {

            if (!avancRembLiquidValidPlacementForm.getNumAvanceChoisi().equals(null) && 
                !avancRembLiquidValidPlacementForm.getNumAvanceChoisi().equals("")) {
                GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
                ContratPlacement contratPlacement = new ContratPlacement();
                contratPlacement.setNumSeqCpla(Long.valueOf(avancRembLiquidValidPlacementForm.getNumeroContratPlacChoisi()));
                contratPlacementTrouve = (ContratPlacement)getContratPlacementCmd.execute(contratPlacement);
                Date dateAvance = new Date();
                if (!contratPlacementTrouve.hasError()) {
                    if (contratPlacementTrouve != null && 
                        contratPlacementTrouve.getNumSeqCpla() != null) {
                        avancRembLiquidValidPlacementForm.setContratPlacement(contratPlacementTrouve);
                        avancRembLiquidValidPlacementForm.setContratPlacementView(affecterContratPlacementView(contratPlacementTrouve));

                        /*  if(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd()=="1001"){
                     avancRembLiquidValidPlacementForm.setAlertBC("True");
                 }
                 else{
                     avancRembLiquidValidPlacementForm.setAlertBC("False");
                 }*/
                        dateAvance = DateHandler.strToDate(avancRembLiquidValidPlacementForm.getInitialisationView().getDateComptable());
                        double duree = DateHandler.getDaysBetween(dateAvance, contratPlacementTrouve.getDatEcheCpla());

                    /*    if (duree < 15) {
                            duree = 15;
                        }*/
                        avancRembLiquidValidPlacementForm.getAvancRembLiquidView().setDuree(Long.valueOf(Math.round(duree)).toString());


                        avancRembLiquidValidPlacementForm.setAlertAfficheContrat("True");
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
            }
                return mapping.findForward("remboursementAvancePlacement");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("300");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
}


    public void afficherListeAvanceAValider(ActionForm form,ParamAgence paramAgence) {

        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();

        try {

                GetListAvancRembLiquidByEtatCmd  getListAvancRembLiquidByEtatCmd= new GetListAvancRembLiquidByEtatCmd();
                Listes listesAvances = new Listes();
                ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
                paramAvanRembLiq.setCodStrcStrc(paramAgence.getCodStrcStrc());
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);		                
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
                            avancRembLiquidValidPlacementForm.setListeAvance(null);
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







    public ActionForward validationRembAvancePlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;

        ContratPlacement contratPlacement = new ContratPlacement();
        ValiderPECAvancePlacementCmd validerPECAvancePlacementCmd = new ValiderPECAvancePlacementCmd();
        DetailsOperationPlacement detailsOperationPlacement =  new DetailsOperationPlacement();
        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
        ParamContratPlacement paramContratPlacement =  new ParamContratPlacement();

        try {

            contratPlacement = avancRembLiquidValidPlacementForm.getContratPlacement();
            ///*** Garnir l'objet detailsOperationPlacement
            detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(avancRembLiquidValidPlacementForm);
            detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
            paramContratPlacement.setDetailsOperationPlacement(detailsOperationPlacement);

            ///*** Garnir l'objet avancRembLiquid
            avancRembLiquid = affecterDonneesAvancRembLiquid(avancRembLiquidValidPlacementForm,contratPlacement);
            paramContratPlacement.setAvancRembLiquid(avancRembLiquid);
            ///*** montant actualisé dans le contrat placement suite a un remboursement avance
             if (!avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla().equals("")) {
                Long montActu =Long.valueOf(new Long(new Double(((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getContratPlacementView().getMontActuCpla())).doubleValue())+(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue())) * 1000).longValue()));
                paramContratPlacement.getAvancRembLiquid().getContratPlacement().setMontActuCpla(montActu);
             }
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

            /*     if(guichetRetraitForm.getPouvoir().getListMandatOperation() != null && guichetRetraitForm.getPouvoir().getListMandatOperation().size()>0 ){
                     MandatOperation mandatOperation = (MandatOperation)guichetRetraitForm.getPouvoir().getListMandatOperation().get(0);
                     operationMoyPay.setMandatOperation(mandatOperation);                  
                 }*/
             }
             
             OperationMoyPay operationMoyPayNew = affecterDonneesOperationMoyenPaiement (avancRembLiquidValidPlacementForm);
             paramContratPlacement.getDetailsOperationPlacement().setOperationMoyPay(operationMoyPayNew);
             
            if(!avancRembLiquid.getCodTypiArl().equalsIgnoreCase("")){///*** s'il y a des interets
                 ///*** date valeur Interet
                 String stemp = avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation();
                 if(avancRembLiquid.getCodTypiArl().equalsIgnoreCase("S")){ ///*** interets servis
                    avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_REMB_INTERET_REMB_AVANCE_PLAC.toString());
                 }else{
                    avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_PERSEPT_INTERET_REMB_AVANCE_PLAC.toString());                 
                 }
                 TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
                 paramContratPlacement.getAvancRembLiquid().setDatValiArl(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
                 avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(stemp);
            }
             ///*** Insertion dans la Base (AVANC_REMB_LIQUID + DETAILS_OPERATION_PLACEMENT + MAND_PERS_OPER_PLAC) + MAJ du CONTRAT_PLACEMENT
             avancRembLiquid =(AvancRembLiquid)validerPECAvancePlacementCmd.execute(paramContratPlacement);
             imprimerRembAvanceSurCapital(avancRembLiquid,request);

            if (!avancRembLiquid.hasError()) {
                String message = "";
                message = 
                        " L'opération de Remboursement N° " + avancRembLiquid.getNumSeqArl().toString() + "  de l'avance sur capital  N° " + avancRembLiquid.getAvancRembLiquid().getNumSeqArl().toString() + " sur le Contrat Placement N° "+contratPlacement.getNumSeqCpla()+
                        " a été effectuée avec succès.";
                avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                return mapping.findForward("confirmationAvancRembLiquidValidPlacement");
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("");
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


    public void imprimerRembAvanceSurCapital(AvancRembLiquid avancRembLiquid,HttpServletRequest request) {
    

             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("RembAvanceSurCapital");
                 parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Remboursement Avance sur Capital");
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_NUM_AVANCE", avancRembLiquid.getNumSeqArl());
                 if (avancRembLiquid.getCodTypiArl().equalsIgnoreCase("S")){
                     parameters.put("P_LIB_INTERET", "Remboursement Interet (Trop perçu)");
                 }else{
                     parameters.put("P_LIB_INTERET", "Perception Interet (Retard)");
                 }
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


    
    public ActionForward validationValidAvancePlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
  
    try {

        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
        avancRembLiquid.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumAvanceChoisi()));
        GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd= new GetAvancRembLiquidByIdCmd();
        avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);

        avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
        avancRembLiquid.getContratPlacement().setMontActuCpla(Long.valueOf(new Long(new Double(((new Double(avancRembLiquid.getContratPlacement().getMontActuCpla()).doubleValue())-((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue())) * 1000)).longValue())));
        avancRembLiquid.setMontArlArl(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 1000).longValue()));
        if (!avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontInetArl().equals("")) {
            avancRembLiquid.setMontInetArl(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontInetArl())).doubleValue() * 1000));
        }
            DetailsOperationPlacement detailsOperationPlacement0 = new DetailsOperationPlacement();
        
        ///****mandat operation
        for (Iterator it = avancRembLiquid.getDetailsOperationPlacements().iterator(); it.hasNext(); ) {
            detailsOperationPlacement0 = (DetailsOperationPlacement)it.next();
        }
        
        DetailsOperationPlacement detailsOperationPlacement = affecterDonneesDetailsOperationPlacement(avancRembLiquidValidPlacementForm);
            
        detailsOperationPlacement.setMandatOperation(detailsOperationPlacement0.getMandatOperation());

///        TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
///        detailsOperationPlacement.setDatValDopl(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));

        ///*** date valeur Interet
        String stemp = avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation();
        avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_PERSEPT_INTERET_AVANCE_PLAC.toString());                 
        TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
        avancRembLiquid.setDatValiArl(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
        
        avancRembLiquidValidPlacementForm.getInitialisationView().setCodeOperation(stemp);

        detailsOperationPlacement.setDatCompDopl(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
        detailsOperationPlacement.setMontDopDopl(avancRembLiquid.getMontArlArl());
        ///*** Garnir operationMoyPay
        avancRembLiquidValidPlacementForm.setAvancRembLiquid(avancRembLiquid);
        OperationMoyPay operationMoyPayNew = affecterDonneesOperationMoyenPaiement (avancRembLiquidValidPlacementForm);
        detailsOperationPlacement.setOperationMoyPay(operationMoyPayNew);
        
        avancRembLiquid.getDetailsOperationPlacements().add(detailsOperationPlacement);

        ValiderAvancePlacementCmd validerAvancePlacementCmd = new ValiderAvancePlacementCmd();

        //*** Insertion dans la Base (AVANC_REMB_LIQUID + DETAILS_OPERATION_PLACEMENT + MAND_PERS_OPER_PLAC) + MAJ du CONTRAT_PLACEMENT
        avancRembLiquid =(AvancRembLiquid)validerAvancePlacementCmd.execute(avancRembLiquid);
        imprimerAvanceSurCapital(avancRembLiquid,request);
        
            if (!avancRembLiquid.hasError()) {
                String message = "";
                message = " L'opération de la Validation de l'avance sur capital  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                          " a été effectuée avec succès.";
                avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                return mapping.findForward("confirmationAvancRembLiquidValidPlacement");
            } else {
                List listErreur = avancRembLiquid.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
        } catch (Exception e) {
            ActionMessage actionMessage = new ActionMessage("exception.generique", e.getMessage() );
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }
    }

    public ActionForward annulerValidAvancePlacement(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AvancRembLiquidValidPlacementForm avancRembLiquidValidPlacementForm = (AvancRembLiquidValidPlacementForm)form;
    
    try {

        AvancRembLiquid avancRembLiquid = new AvancRembLiquid();
        avancRembLiquid.setNumSeqArl(Long.valueOf(avancRembLiquidValidPlacementForm.getNumAvanceChoisi()));
        GetAvancRembLiquidByIdCmd getAvancRembLiquidByIdCmd= new GetAvancRembLiquidByIdCmd();
        avancRembLiquid = (AvancRembLiquid)getAvancRembLiquidByIdCmd.execute(avancRembLiquid);

        avancRembLiquid.setCodEtatArl(Constants.ETAT_ARL_REJETEE);

        UpdateAvanceRembLiquCmd updateAvanceRembLiquCmd = new UpdateAvanceRembLiquCmd();
        avancRembLiquid =(AvancRembLiquid)updateAvanceRembLiquCmd.execute(avancRembLiquid);
        
            if (!avancRembLiquid.hasError()) {
                String message = "";
                message = " L'opération d'' Annulation de l'avance sur capital  N° " + avancRembLiquid.getNumSeqArl().toString() + " sur le Contrat Placement " +avancRembLiquid.getContratPlacement().getProduitPlacement().getLibPrdPlc()+" N° "+avancRembLiquid.getContratPlacement().getNumSeqCpla()+
                          " a été effectuée avec succès.";
                avancRembLiquidValidPlacementForm.setLibelleConfirmation(message);
                return mapping.findForward("confirmationAvancRembLiquidValidPlacement");
            } else {
                List listErreur = avancRembLiquid.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
        } catch (Exception e) {
            ActionMessage actionMessage = new ActionMessage("exception.generique", e.getMessage() );
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }
    }
   

    public void imprimerPECAvanceSurCapital(AvancRembLiquid avancRembLiquid,HttpServletRequest request) {
    

             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("AvanceCapitalDemd");
                 parameters.put("P_LIB_ETAT", "Demande Avance sur Capital");
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_NUM_AVANCE", avancRembLiquid.getNumSeqArl());
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidPlacementAction / Dispatch Action :imprimerPECAvanceSurCapital ");
             text.append(e.getMessage());
             erreur.setCode("300");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
             }                                                                                
                                                                                               
         }    

    public void imprimerAvanceSurCapital(AvancRembLiquid avancRembLiquid,HttpServletRequest request) {
    

             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 txtNomFichJasper.append("AvanceSurCapital");
                 parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : Avance sur Capital");
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_NUM_AVANCE", avancRembLiquid.getNumSeqArl());
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = new StringBuffer("la transaction est Interrompu, une erreur dans AvancRembLiquidPlacementAction / Dispatch Action :imprimerAvanceSurCapital ");
             text.append(e.getMessage());
             erreur.setCode("300");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage =  new ActionMessage("exception.generique", erreur.getDescription());
             }                                                                                
                                                                                               
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

        Devise devise = new Devise();
        devise.setCodDevDev(Constants.COD_DEV_DINAR);
        operationMoyPay.setDevise(devise);
         
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
        
        Produit produitPlacOmp =new Produit();
        if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("ValidAv")){
            operationMoyPay.setCodRefbOmp("N° "+StrHandler.lpad(avancRembLiquidValidPlacementForm.getNumContratPlacChoisi(),'0',15).substring(7,15));
            
            operationMoyPay.setCodRefcOmp((StrHandler.lpad(avancRembLiquidValidPlacementForm.getNumContratPlacChoisi(),'0',15)).substring(7,15));
            produitPlacOmp.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratPlacementView().getCodPrdPrd()));
            operationMoyPay.setProduit(produitPlacOmp);
        }else{
            operationMoyPay.setCodRefbOmp("N° "+(StrHandler.lpad(avancRembLiquidValidPlacementForm.getContratPlacement().getNumSeqCpla().toString(),'0',15).substring(7,15)));
            operationMoyPay.setCodRefcOmp(StrHandler.lpad(avancRembLiquidValidPlacementForm.getContratPlacement().getNumSeqCpla().toString(),'0',15).substring(7,15));
            produitPlacOmp.setCodPrdPrd(avancRembLiquidValidPlacementForm.getContratPlacement().getProduitPlacement().getCodPrdPlc());
            operationMoyPay.setProduit(produitPlacOmp);
        }
        if (avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla()!=null && avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla().toString()!="")
        operationMoyPay.setNumMoypOmp(avancRembLiquidValidPlacementForm.getContratPlacement().getNumBcCpla().toString());

        operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
        operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
        operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
        
        //operationMoyPay.setCodRefcOmp(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation());
        operation.setCodOperOper(Long.valueOf(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation()));
        
        Tache tache = new Tache();
        tache.setOperation(operation);
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(operation.getCodOperOper());
        if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("RemboursementAv")){
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_REMB_AVANC_PLAC);
        }else if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("ValidAv")){
            tacheId.setCodTachTach(Constants.COD_TACHE_VALID_AVANC_PLAC);
        }
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        
        operationMoyPay.setDatOperOmp(DateHandler.strToDate(avancRembLiquidValidPlacementForm.getDateComptable()));
        operationMoyPay.setDatSystOmp(new Date());
         ///operationMoyPay.setDatValOmp(new Date());// en attandant l"API" des conditions de banque
      ///  TraitementConditionBanque traitementConditionBanque = getCB(avancRembLiquidValidPlacementForm);
        operationMoyPay.setDatValOmp(avancRembLiquidValidPlacementForm.getDateValeur());
        if (cpt !=null){
            operationMoyPay.setMontSoldCcpt(cpt.getMontSoldCcpt());
        }else
            operationMoyPay.setMontSoldCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getMontSoldCcpt()));

         ///*** Insertion dans la table Mand_pers_oper_moy
      if ( avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("RemboursementAv"))   {
          operationMoyPay.setLibMotfOmp("Remboursement Avance sur Capital Placement");
          TypePiece typePieceDem =  avancRembLiquidValidPlacementForm.getPouvoir().getDemandeur().getTypePiece();
          operationMoyPay.setTypePieceDemandeur(typePieceDem);
          operationMoyPay.setNumPcedOmp(avancRembLiquidValidPlacementForm.getPouvoir().getDemandeur().getNumPcePers());
          operationMoyPay.setNomNomdOmp(avancRembLiquidValidPlacementForm.getPouvoir().getDemandeur().getNomNomPers());
          operationMoyPay.setNomPrndOmp(avancRembLiquidValidPlacementForm.getPouvoir().getDemandeur().getNomPrnPers());
          operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);
          operationMoyPay.setMontDinOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 1000).longValue()));
          operationMoyPay.setCodDemOmp(avancRembLiquidValidPlacementForm.getPouvoir().getTypePouvoir());// type demandeur (Titulaire,CoTitul,Mandataire)
           if (!avancRembLiquidValidPlacementForm.getContratPlacementView().getNumTauiCpla().equals("")) {
            operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double(((new Double(cpt.getMontSoldCcpt()).doubleValue())-(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue()*1000)) ).longValue())));
           }
          if(avancRembLiquidValidPlacementForm.getPouvoir().getTypePouvoir().equals("C")){
              // cas cotitulaire
               if(avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire()!=null && avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire().size()>0 ){
                   CoTitulaire cotitulaire = (CoTitulaire)avancRembLiquidValidPlacementForm.getPouvoir().getListCotitulaire().get(0);
                   operationMoyPay.setCoTitulaire(cotitulaire);
               }         
          }            
         
         if(avancRembLiquidValidPlacementForm.getPouvoir().getTypePouvoir().equals("M")){
         /* Set listeMandPersOperMoy = new HashSet(0);
          for (Iterator it = avancRembLiquidValidPlacementForm.getPouvoir().getListMandatPersonne().iterator();it.hasNext(); ) { 
              MandatPersonne mandatPersonne = (MandatPersonne)it.next();
              
              MandPersOperMoy mandPersOperMoy = new MandPersOperMoy();
              MandPersOperMoyId mandPersOperMoyId = new MandPersOperMoyId();
              mandPersOperMoyId.setNumMandMand(mandatPersonne.getMandat().getNumMandMand());               
              mandPersOperMoyId.setNumSeqPers(mandatPersonne.getPersonne().getNumSeqPers());
              mandPersOperMoyId.setNumOperOmp(operationMoyPay.getNumOperOmp());               
              mandPersOperMoy.setMandPersOperMoyId(mandPersOperMoyId);
              listeMandPersOperMoy.add(mandPersOperMoy);
          }
             operationMoyPay.setMandPersOperMoies(listeMandPersOperMoy);*/
             if(avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation() != null && avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation().size()>0 ){
                 MandatOperation mandatOperation = (MandatOperation)avancRembLiquidValidPlacementForm.getPouvoir().getListMandatOperation().get(0);
                 operationMoyPay.setMandatOperation(mandatOperation);                  
             }
         }
      }else{
          operationMoyPay.setMontDinOmp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 1000).longValue()));
          operationMoyPay.setCodDemOmp("T");
          operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
          operationMoyPay.setMontApreOmp(Long.valueOf(new Long(new Double(((new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getContratView().getMontSoldCcpt())).doubleValue())+(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue()*1000)) ).longValue())));
          operationMoyPay.setLibMotfOmp("Avance sur Capital Placement");
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
/*            if (!avancRembLiquidValidPlacementForm.getPersonneDemandeur().getTypePouvoir().equals("M") && !avancRembLiquidValidPlacementForm.getPersonneDemandeur().getTypePouvoir().equals("C")){
                traitementConditionBanque.setCodTpceTpce(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getCodTpceTpceDemandeur());
                traitementConditionBanque.setNumPcePers(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur());
            }
            if (avancRembLiquidValidPlacementForm.getPersonneDemandeur().getTypePouvoir().equals("M")){
                traitementConditionBanque.setCodTpceTpce(avancRembLiquidValidPlacementForm.getContratPlacement().getContratCpt().getClient().getNumSeqPers().toString());
                traitementConditionBanque.setNumPcePers(avancRembLiquidValidPlacementForm.getContratPlacement().getPersonne().getNumPcePers());            
            }
*/      
        if (avancRembLiquidValidPlacementForm.getTypeForm().equalsIgnoreCase("ValidAv")){
            traitementConditionBanque.setCodTpceTpce(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getCodTpceTpceDemandeur());
            traitementConditionBanque.setNumPcePers(avancRembLiquidValidPlacementForm.getPersonneDemandeur().getNumPcePersDemandeur());
        }else{
            if (avancRembLiquidValidPlacementForm.getPersonneDemandeur().getTypePouvoir().equals("C")){
                traitementConditionBanque.setCodTpceTpce(avancRembLiquidValidPlacementForm.getContratPlacement().getPersonne().getTypePiece().getCodTpceTpce().toString());
                traitementConditionBanque.setNumPcePers(Constants.COD_NUM_ORDRE.toString());            
            }else{
                ContratCpt contratCpt =new ContratCpt();
                ContratCptId contratCptId = new ContratCptId();
                contratCptId.setCodPrdPrd(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodPrdPrd()));
                contratCptId.setNumCcptCcpt(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getNumCcptCcpt()));
                contratCptId.setCodStrcStrc(Long.valueOf(avancRembLiquidValidPlacementForm.getContratView().getCodStrcStrc()));
                contratCpt.setContratCptId(contratCptId);
                
                GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
                ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
                traitementConditionBanque.setCodTpceTpce(cpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
                traitementConditionBanque.setNumPcePers(cpt.getClient().getPersonne().getNumPcePers().toString());            
            }
        }
///            traitementConditionBanque.setIdContrat(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getContratPlacement().getNumSeqCpla().toString());
            traitementConditionBanque.setIdContrat(avancRembLiquidValidPlacementForm.getNumContratPlacChoisi());
            if (avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl()!=null && !avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl().equalsIgnoreCase("")){
                traitementConditionBanque.setMontant(Long.valueOf(new Double(new Double(StrHandler.strWithoutBlanck(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getMontArlArl())).doubleValue() * 
                                                           1000).longValue()).toString());
            }
            traitementConditionBanque.setNbUnites((Long.valueOf(Math.abs(Long.valueOf(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getDuree()).longValue()))).toString());
         
            traitementConditionBanque.setDateReference(avancRembLiquidValidPlacementForm.getDateComptable());
            
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
        
    
 

}

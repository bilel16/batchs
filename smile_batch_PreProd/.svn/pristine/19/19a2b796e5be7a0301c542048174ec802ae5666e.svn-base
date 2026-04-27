package com.bna.smile.web.ouverturejournee.actions;

import com.bna.commun.commande.OuvertureSessionCmd;
import com.bna.commun.commande.OuvrirJourneeStructureCmd;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Structure;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;

import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.JourneeVo;
import com.bna.smile.model.clotureDomaine.commande.GetListJournStructDomCmd;
import com.bna.smile.model.clotureDomaine.model.JournStructDomEtatVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.debutJournee.commande.GetDonneeDebutJourneeCmd;
import com.bna.smile.model.debutJournee.model.DebutJourneeVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.commande.GetInteretServiByIdCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.clotureDomaine.forms.ClotureDomPlacementForm;
import com.bna.smile.web.clotureDomaine.view.OperMoyPayView;
import com.bna.smile.web.commun.model.ParamAgence;


import com.bna.smile.web.ouverturejournee.forms.VerificationOuvertureJourneeForm;

import com.bna.smile.web.placement.view.ContratPlacementView;

import com.bna.smile.web.placement.view.DemandeDecisionView;

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

/**
 * 
 * Classe pour l'ouverture de la journée (" pour la mise en place du 05/05/2008")
 * elle fait appel aux calendriers pour extraire le jour suivant à partir 
 * de la dernière date journée d'une structure.
 * @author Mdimagh Med Lassaad 
 * @since 16/04/2008
 * 
 */
public class VerificationOuvertureJourneeAction extends DispatchAction {
   Logger logger = Logger.getLogger(VerificationOuvertureJourneeAction.class);
    public VerificationOuvertureJourneeAction() {
    }


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        VerificationOuvertureJourneeForm verificationouvertureJourneeForm = 
            (VerificationOuvertureJourneeForm)form;

        ActionMessages actionMessages = new ActionMessages();
        try {
           
            String date = (String) request.getSession().getAttribute("dateDerniereJournee");
            
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            
            verificationouvertureJourneeForm.setNouvelleDatejournee(DateHandler.dateToStr(CalanderHandler.GetNextWorkingDay(DateHandler.strToDate(date))));
            verificationouvertureJourneeForm.setDateDerniereJournee(date);
            verificationouvertureJourneeForm.setCodeStructure(paramAgence.getCodStrcStrc().toString());
            verificationouvertureJourneeForm.setMatricule(paramAgence.getNumMatrUser());
            
            logger.info("Agence "+verificationouvertureJourneeForm.getCodeStructure()+" Matricule "+verificationouvertureJourneeForm.getMatricule()+  " Entrée pour ouverture journée, la dérnière date ouverte est :" + date);
            
           
            return mapping.findForward("ouverture");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans VerificationOuvertureJourneeAction /  initierPage : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.info("Agence "+verificationouvertureJourneeForm.getCodeStructure()+" Matricule "+verificationouvertureJourneeForm.getMatricule()+  " erreur dans VerificationOuvertureJourneeAction /  initierPage :" + e.toString());

            return mapping.findForward("errorOuverture");
        }

    }

    public ActionForward ouvrirJournee(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {


        ActionMessages actionMessages = new ActionMessages();
        VerificationOuvertureJourneeForm verificationOuvertureJourneeForm = 
            (VerificationOuvertureJourneeForm)form;
        
        try {
          
            logger.info("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Entrée methode ouvrirJournee ");
            OuvrirJourneeStructureCmd ouverirJourneeStructurCmd = 
                new OuvrirJourneeStructureCmd();
            Structure structure = new Structure();

            JourneeVo journeeVo = new JourneeVo();
            journeeVo.setDateJourneeOuverte(DateHandler.strToDate(verificationOuvertureJourneeForm.getNouvelleDatejournee()));
            structure.setCodStrcStrc(Long.valueOf(verificationOuvertureJourneeForm.getCodeStructure()));
            journeeVo.setStructure(structure);
            journeeVo.setMatriculeInitiateur(verificationOuvertureJourneeForm.getMatricule());
            journeeVo = 
                    (JourneeVo)ouverirJourneeStructurCmd.execute(journeeVo);
            if (!journeeVo.hasError()) {
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                paramAgence.setDateComptable(DateHandler.dateToStr(journeeVo.getDateJourneeOuverte()));   
                paramAgence.setDateJours(DateHandler.dateToStr(new Date()));
                
                request.getSession().setAttribute("paramAgBNA", paramAgence);
                logger.info("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Sortie sans erreursde la methode ouvrirJournee ");
                
                return mapping.findForward("indexSMILE");

            } else {

                List listErreur = journeeVo.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("errorOuverture");


            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans VerificationOuvertureJourneeAction / ouvrirJournee : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.debug("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Erreur dans la methode ouvrirJournee :"+e.toString());
            
            return mapping.findForward("errorOuverture");

        }
    }
    public ActionForward ouvrirSession(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {


        ActionMessages actionMessages = new ActionMessages();
        VerificationOuvertureJourneeForm verificationOuvertureJourneeForm = 
            (VerificationOuvertureJourneeForm)form;
        
        try {
          
            logger.info("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Entrée methode ouvrirSession ");
            OuvertureSessionCmd ouvertureSessionCmd=new OuvertureSessionCmd();
            Structure structure = new Structure();

            JourneeVo journeeVo = new JourneeVo();
            journeeVo.setDateJourneeOuverte(DateHandler.strToDate(verificationOuvertureJourneeForm.getDateDerniereJournee()));
            structure.setCodStrcStrc(Long.valueOf(verificationOuvertureJourneeForm.getCodeStructure()));
            journeeVo.setStructure(structure);
            journeeVo.setMatriculeInitiateur(verificationOuvertureJourneeForm.getMatricule());
            
            /*donnée de debut de journée*/
            JourneeStructureId journeeStructureId=new JourneeStructureId();
            journeeStructureId.setCodStrcStrc(journeeVo.getStructure().getCodStrcStrc());
            journeeStructureId.setDatJrnJrn(journeeVo.getDateJourneeOuverte());
            GetListJournStructDomCmd getListJournStructDomCmd=new GetListJournStructDomCmd();
            Listes listedomaines=(Listes)getListJournStructDomCmd.execute(journeeStructureId);
            if (listedomaines == null || listedomaines.hasError()) {
               List listErreur = listedomaines.getErrors();
               for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                   com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                   ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                   actionMessages.add("Erreur ", actionMessage);
               }
               this.saveMessages(request, actionMessages);
              
               return mapping.findForward("error");
             }else{
                
                 for (Iterator it2 = listedomaines.getList().iterator(); it2.hasNext(); ) {
                 JournStructDomEtatVo journStructDomEtatVo=(JournStructDomEtatVo)it2.next();
                 if(journStructDomEtatVo.getJourneeStructureDomaine().getJourneeStructureDomaineId().getCodDomDomm().longValue()==Constants.COD_DOM_PLACEMENT){
                     verificationOuvertureJourneeForm.setExistPlacement("true");
                 }
                 }
                
             }
            if (verificationOuvertureJourneeForm.getExistPlacement().equalsIgnoreCase("true")){ 
            GetDonneeDebutJourneeCmd getDonneeDebutJourneeCmd=new GetDonneeDebutJourneeCmd();
            DebutJourneeVo debutJourneeVo=(DebutJourneeVo)getDonneeDebutJourneeCmd.execute(journeeVo);
            
            List listRenBatch =traiterListeOperMoyPay(debutJourneeVo.getListeRenouvBatch());
            verificationOuvertureJourneeForm.setListesRenBatch(listRenBatch);
            
            List listRenAtt =traiterListeOperMoyPay(debutJourneeVo.getListeRenouvAtt());
            verificationOuvertureJourneeForm.setListesRenAtt(listRenAtt);
            
            
            List listCptALiq5Jour=traiterListeContratPlacement(debutJourneeVo.getContratEchu5Jour());
            verificationOuvertureJourneeForm.setListeContratPlacement5Jour(listCptALiq5Jour);
            
            List listCptArrivAEch=traiterListeContratPlacement(debutJourneeVo.getContratArriveeAEche());
            verificationOuvertureJourneeForm.setListeContratPlacementAEche(listCptArrivAEch);
            
            List listLiqBatch=traiterListeContratPlacement(debutJourneeVo.getLiquidTraitSoir());
            verificationOuvertureJourneeForm.setLiquiTraitSoir(listLiqBatch);
            
            traiterListedemandesDecision(debutJourneeVo.getListeSouscAttVAl(),verificationOuvertureJourneeForm);
            
            List listInteretRetour =traiterListeOperMoyPay(debutJourneeVo.getIntPartVerseSoir());
            verificationOuvertureJourneeForm.setListesInteretServi(listInteretRetour);
            }
            /*Ouverture de session*/
            journeeVo =  (JourneeVo)ouvertureSessionCmd.execute(journeeVo);
            if (!journeeVo.hasError()) {
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                paramAgence.setDateComptable(DateHandler.dateToStr(journeeVo.getDateJourneeOuverte()));   
                paramAgence.setDateJours(DateHandler.dateToStr(new Date()));
                
                request.getSession().setAttribute("paramAgBNA", paramAgence);
                logger.info("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Sortie sans erreursde la methode ouvrirJournee ");
                if (verificationOuvertureJourneeForm.getExistPlacement().equalsIgnoreCase("true")){ 
                    return mapping.findForward("debutJournee");
                }else{
                    return mapping.findForward("indexSMILE");
                }
            } else {

                List listErreur = journeeVo.getErrors();
                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);
                return mapping.findForward("errorOuverture");


            }
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans VerificationOuvertureJourneeAction / ouvrirSession : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.debug("Agence "+verificationOuvertureJourneeForm.getCodeStructure()+" Matricule "+verificationOuvertureJourneeForm.getMatricule()+  " Erreur dans la methode ouvrirJournee :"+e.toString());
            
            return mapping.findForward("errorOuverture");

        }
        
    }
    public List traiterListeContratPlacement(List listContrats ) {
        
        List listContratsPlacementView = new ArrayList();

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
                        contratPlacementView.setNumCcptCpla(StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                            '0', 
                                                                            3) + 
                                                            " " + 
                                                            StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                            '0', 
                                                                            4) + 
                                                            " " + 
                                                            StrHandler.lpad(contratPlacement.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                            '0', 
                                                                            6));

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

                    listContratsPlacementView.add(contratPlacementView);
                } // Fin For 
                
            }
            return listContratsPlacementView;
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeContratPlacement:  ", 
                         e);
            throw new RuntimeException(e);
        }

    }
   
    public void traiterListedemandesDecision(List listDemandes, ActionForm form) {

        VerificationOuvertureJourneeForm verificationOuvertureJourneeForm = 
            (VerificationOuvertureJourneeForm)form;
        List listDemandeDecisionAttente = new ArrayList();
      
        try {
            if (listDemandes != null && listDemandes.size() > 0) {

                for (Iterator it = listDemandes.iterator(); it.hasNext(); ) {
                    DemandeDecision demandeDecision = 
                        (DemandeDecision)it.next();

                    DemandeDecisionView demandeDecisionView = 
                        new DemandeDecisionView();
                    demandeDecisionView.setNumCcptDemd(StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodStrcStrc().toString(), 
                                                                       '0', 
                                                                       3) + 
                                                       StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getCodPrdPrd().toString(), 
                                                                       '0', 
                                                                       4) + 
                                                       StrHandler.lpad(demandeDecision.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), 
                                                                       '0', 
                                                                       6));
                    demandeDecisionView.setDemandeDecision(demandeDecision);
                    demandeDecisionView.setCodeTypeFaveur(demandeDecision.getCodFavDemd());
                    demandeDecisionView.setDatCreDemd(DateHandler.dateToStr(demandeDecision.getDatCreDemd()));
                    demandeDecisionView.setCodEtatDemd(demandeDecision.getCodEtatDemd());
                    demandeDecisionView.setCodNdemDemd(demandeDecision.getCodNdemDemd());
                    demandeDecisionView.setMontPlaDemd(StrHandler.formatmnt(Math.abs(demandeDecision.getMontPlaDemd().doubleValue())));
                    if (demandeDecision.getNumToffDemd() != null) {
                        if (demandeDecision.getCodFavDemd().equals("G") || 
                            demandeDecision.getCodFavDemd().equals("F") || 
                            demandeDecision.getCodFavDemd().equals("P"))
                            demandeDecisionView.setTauxAccorde(demandeDecision.getNumToffDemd().toString());
                        else if (demandeDecision.getCodFavDemd().equals("I"))
                            demandeDecisionView.setTauxAccorde("TMM" + 
                                                               demandeDecision.getCodMargDemd() + 
                                                               " " + 
                                                               demandeDecision.getNumToffDemd().toString());
                    }
                    demandeDecisionView.setCodTypPaiement(demandeDecision.getCodPintDemd());
                    demandeDecisionView.setLibCcptCcpt(demandeDecision.getContratCpt().getNomIntiCcpt());

                    listDemandeDecisionAttente.add(demandeDecisionView);


                   
                } // Fin For 
           
            }
            verificationOuvertureJourneeForm.setListeSouscription(listDemandeDecisionAttente);

        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : traiterListedemandesDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }

    }
    public List traiterListeOperMoyPay(List listOperMouPay) {

       
        List listInteretView = new ArrayList();

        try {
            if (listOperMouPay != null && listOperMouPay.size() > 0) {

                for (Iterator it = listOperMouPay.iterator(); it.hasNext(); ) {
                    OperationMoyPay operationMoyPay = 
                        (OperationMoyPay)it.next();

                    OperMoyPayView operMoyPayView = new OperMoyPayView();

                    operMoyPayView.setProduit(operationMoyPay.getProduit().getLibPrdPrd());
                    operMoyPayView.setRelation(operationMoyPay.getNomNomdOmp() + 
                                               " " + 
                                               operationMoyPay.getNomPrndOmp());
                    operMoyPayView.setContratDav(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc() + 
                                                 " " + 
                                                 operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString() + 
                                                 " " + 
                                                 operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString());
                    InteretServi inertetServi=new InteretServi();
                    inertetServi.setNumIsrvIsrv(Long.valueOf(operationMoyPay.getCodRefmOmp()));
                    GetInteretServiByIdCmd getInteretServiByIdCmd=new GetInteretServiByIdCmd();
                    InteretServi inertetServiRetour =(InteretServi)getInteretServiByIdCmd.execute(inertetServi);
                    if (inertetServiRetour!=null){
                         operMoyPayView.setContratPlacemant(inertetServiRetour.getContratPlacement().getNumSeqCpla().toString());
                         operMoyPayView.setEcheance(DateHandler.dateToStr(inertetServiRetour.getContratPlacement().getDatEcheCpla()));
                         operMoyPayView.setMontantPlacement(StrHandler.formatmnt(Math.abs(inertetServiRetour.getContratPlacement().getMontCapCpla())));
                         operMoyPayView.setIrc(StrHandler.formatmnt(Math.abs(inertetServiRetour.getMontIrcIsrv())));
                    }
                    operMoyPayView.setInterertServi(StrHandler.formatmnt(Math.abs(operationMoyPay.getMontDinOmp())));


                    listInteretView.add(operMoyPayView);

                } // Fin For 
                
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = new StringBuffer("");
            text.append(e.getMessage());
            erreur.setCode("300");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique (traiterListeOperMoyPay) :", 
                                  erreur.getDescription());
        }
        return listInteretView;
    }
    public ActionForward imprimerRecapDebutJour(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        VerificationOuvertureJourneeForm verificationOuvertureJourneeForm = 
            (VerificationOuvertureJourneeForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            parameters.put("P_LIB_ETAT", "RECAP DEBUT JOURNEE "+paramAgence.getDateComptable());
            parameters.put("P_NUM_MATR_USER",paramAgence.getNumMatrUser());
            parameters.put("P_DATE_COMPTABLE",paramAgence.getDateComptable());
            parameters.put("P_COD_STRC_STRC",paramAgence.getCodStrcStrc().toString());


            valueObject.setParams(parameters);
            valueObject.setNomDossier("Placement");
            parameters = null;
            // indiquer le nom du fichier jasper                   
            valueObject.setNomReport("recap_debut_journee");
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("debutJournee");
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
}

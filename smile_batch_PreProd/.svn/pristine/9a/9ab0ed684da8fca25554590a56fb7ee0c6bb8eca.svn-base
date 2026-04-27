package com.bna.smile.web.clotureDomaine.actions;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.clotureDomaine.commande.CloturedomPlacementCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailAvancRembLiqCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailContratPlacementCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailOperMoyPayCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailSouscPlacementCmd;
import com.bna.smile.model.clotureDomaine.commande.GetDonneePlacementCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatAvancPlacCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatInteretServiCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatLiquidCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatSouscPlacCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatrenouvPlacCmd;
import com.bna.smile.model.clotureDomaine.commande.UpdateDomaineCmd;
import com.bna.smile.model.clotureDomaine.model.CloturePlacVo;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.clotureDomaine.model.LiquidationVo;
import com.bna.smile.model.clotureDomaine.model.OperMoyPayVo;
import com.bna.smile.model.clotureDomaine.model.PlacementVo;
import com.bna.smile.model.clotureDomaine.model.PramDetailPlacVo;
import com.bna.smile.model.clotureDomaine.model.StatPlacement;
import com.bna.smile.model.clotureDomaine.model.StatPlacementObjectVO;
import com.bna.smile.model.clotureDomaine.traitement.GetDonnePlacementTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domaineplacement.commande.GetInteretServiByIdCmd;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.clotureDomaine.forms.ClotureDomPlacementForm;
import com.bna.smile.web.clotureDomaine.view.OperMoyPayView;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.actions.ConsultationOppMoyPaieAction;
import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.util.ArrayList;
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

public class ClotureDomPlacementAction extends DispatchAction {
    public ClotureDomPlacementAction() {
    }

    private static final Logger logger = 
        Logger.getLogger(ClotureDomPlacementAction.class);
    ParamAgence paramAgence = new ParamAgence();

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {


        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomPlacementForm clotureDomPlacementForm = 
                (ClotureDomPlacementForm)form;

            return null;

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomPlacementAction / initierPage : ");
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

    public ActionForward rechercherDonneePlacemnet(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
        Context context = ContextHandler.getContext();
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            clotureDomPlacementForm.setMyTypeStructure(paramAgence.getCodTstrcTstrc());
            clotureDomPlacementForm.setStructureRech(paramAgence.getCodStrcStrc().toString());
            StatPlacement statPlacement = new StatPlacement();
            statPlacement.setJourneeStructureDomaineId(journeeStructureDomaineId);
            statPlacement.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
            statPlacement.setStructure(paramAgence.getCodStrcStrc());
            /*Demandes Placement*/
            GetDonnePlacementTrt getDonneePlacementTrt = 
                new GetDonnePlacementTrt();
            Listes listDemplacement = 
                (Listes)getDonneePlacementTrt.perform(statPlacement);
            clotureDomPlacementForm.setListeDemandes(listDemplacement.getList());
           
            /*cas d'une agence*/
            if (clotureDomPlacementForm.getMyTypeStructure().equals(Long.valueOf("1"))) {
                /*Souscription Placement*/
                GetStatSouscPlacCmd getStatSouscPlacCmd = 
                    new GetStatSouscPlacCmd();
                PlacementVo placementVo = 
                    (PlacementVo)getStatSouscPlacCmd.execute(statPlacement);
                clotureDomPlacementForm.setListeSouscription(placementVo.getList());
                clotureDomPlacementForm.setNbrGlobSouscVal(placementVo.getNbrGlobSousc());
                clotureDomPlacementForm.setMntGlobSouscVal(placementVo.getMntGlobalSousc());

                /*avnaces placement*/
                GetStatAvancPlacCmd getStatAvancPlacCmd = 
                    new GetStatAvancPlacCmd();
                PlacementVo placementVo1 = 
                    (PlacementVo)getStatAvancPlacCmd.execute(statPlacement);
                clotureDomPlacementForm.setListeAvances(placementVo1.getList());
                clotureDomPlacementForm.setNbrGlobAvancVal(placementVo1.getNbrGlobAvanc());
                clotureDomPlacementForm.setMntGlobAvancVal(placementVo1.getMntGlobalAvanc());
                clotureDomPlacementForm.setNbrGlobRembVal(placementVo1.getNbrGlobRemb());
                clotureDomPlacementForm.setMntGlobRembVal(placementVo1.getMntGlobalRemb());


                /*liquidations*/
                GetStatLiquidCmd getStatLiquidCmd = new GetStatLiquidCmd();
                LiquidationVo liquidationVoRetour = 
                    (LiquidationVo)getStatLiquidCmd.execute(statPlacement);
                if (liquidationVoRetour != null) {
                    clotureDomPlacementForm.setLiquidAvantEch(liquidationVoRetour.getLiquidAvantEch());
                    clotureDomPlacementForm.setLiquidArrivAEcheance(liquidationVoRetour.getLiquidArrivAEcheance());
                    clotureDomPlacementForm.setLiquidPart(liquidationVoRetour.getLiquidPart());
                    clotureDomPlacementForm.setLiquidBtach(liquidationVoRetour.getLiquidTraitBatch());
                    clotureDomPlacementForm.setNbrLiqBatch(liquidationVoRetour.getNbrGlobliqBatch());
                    clotureDomPlacementForm.setMntGlobLiqBatch(liquidationVoRetour.getMntGlobliqBatch());
                    clotureDomPlacementForm.setNbrLiqPart(liquidationVoRetour.getNbrGlobliquidPart());
                    clotureDomPlacementForm.setMntGlobLiqPart(liquidationVoRetour.getMntGlobliquidPart());
                    clotureDomPlacementForm.setNbrLiqAVEcheanceTOT(liquidationVoRetour.getNbrGlobliqAVEch());
                    clotureDomPlacementForm.setMntGlobLiqAVEcheanceTOT(liquidationVoRetour.getMntGlobliqAVEch());

                }


                /*renouvellement placement*/
                GetStatrenouvPlacCmd getStatrenouvPlacCmd = 
                    new GetStatrenouvPlacCmd();
                PlacementVo placementVo2 = 
                    (PlacementVo)getStatrenouvPlacCmd.execute(statPlacement);
                clotureDomPlacementForm.setListesRenouvPlac(placementVo2.getList());
                clotureDomPlacementForm.setNbrGlobRenVal(placementVo2.getNbrGlobRenAPEch());
                clotureDomPlacementForm.setMntGlobRenVal(placementVo2.getMntGlobalRenAPEch());
                clotureDomPlacementForm.setNbrGlobRenBatch(placementVo2.getNbrGlobRenBatch());
                clotureDomPlacementForm.setMntGlobRenBatch(placementVo2.getMntGlobalRenBatch());


                /*interets partiels servis*/
                GetStatInteretServiCmd getStatInteretServiCmd = 
                    new GetStatInteretServiCmd();
                PlacementVo placementVo3 = 
                    (PlacementVo)getStatInteretServiCmd.execute(statPlacement);
                clotureDomPlacementForm.setListesInteretServi(placementVo3.getList());

                clotureDomPlacementForm.setNbrGlobInteretPre(placementVo3.getNbrGlobIntPre());
                clotureDomPlacementForm.setMntGlobInteretPre(placementVo3.getMntGlobalIntPre());

                clotureDomPlacementForm.setNbrGlobInteretPost(placementVo3.getNbrGlobIntPost());
                clotureDomPlacementForm.setMntGlobInteretPost(placementVo3.getMntGlobalIntPost());

                clotureDomPlacementForm.setNbrGlobInteretPart(placementVo3.getNbrGlobIntPart());
                clotureDomPlacementForm.setMntGlobInteretPart(placementVo3.getMntGlobalIntPart());

                clotureDomPlacementForm.setNbrGlobResInt(placementVo3.getNbrGlobResInt());
                clotureDomPlacementForm.setMntGlobalResInt(placementVo3.getMntGlobalResInt());

                clotureDomPlacementForm.setNbrGlobrestit(placementVo3.getNbrGlobrestit());
                clotureDomPlacementForm.setMntGlobalrestit(placementVo3.getMntGlobalrestit());

                clotureDomPlacementForm.setNbrGlobVerIntLiq(placementVo3.getNbrGlobVerIntLiq());
                clotureDomPlacementForm.setMntGlobalVerIntLiq(placementVo3.getMntGlobalVerIntLiq());

                clotureDomPlacementForm.setNbrGloblRecBC(placementVo3.getNbrGloblRecBC());
                clotureDomPlacementForm.setMntGlobalRecBC(placementVo3.getMntGlobalRecBC());

                clotureDomPlacementForm.setNbrGlobPerIntAvanc(placementVo3.getNbrGlobPerIntAvanc());
                clotureDomPlacementForm.setNbrGlobRistIntAvanc(placementVo3.getNbrGlobRistIntAvanc());
                clotureDomPlacementForm.setNbrGlobPerIntCompRemb(placementVo3.getNbrGlobPerIntCompRemb());
                clotureDomPlacementForm.setMntGlobalPerIntAvanc(placementVo3.getMntGlobalPerIntAvanc());
                clotureDomPlacementForm.setMntGlobalRistIntAvanc(placementVo3.getMntGlobalRistIntAvanc());
                clotureDomPlacementForm.setMntGlobalPerIntCompRemb(placementVo3.getMntGlobalPerIntCompRemb());
                clotureDomPlacementForm.setMntGlobalAbonExtInt(placementVo3.getMntGlobalAbonExtInt());
                clotureDomPlacementForm.setNbrGlobAbonExtInt(placementVo3.getNbrGlobAbonExtInt());
                
                clotureDomPlacementForm.setMntGlobalAbonIntPlacPost(placementVo3.getMntGlobalAbonIntPlacPost());
                clotureDomPlacementForm.setNbrGlobAbonIntPlacPost(placementVo3.getNbrGlobAbonIntPlacPost());
                
                clotureDomPlacementForm.setMntGlobalAbonIntPlacPre(placementVo3.getMntGlobalAbonIntPlacPre());
                clotureDomPlacementForm.setNbrGlobAbonIntPlacPre(placementVo3.getNbrGlobAbonIntPlacPre());
                
                clotureDomPlacementForm.setMntGlobalResiliation(placementVo3.getMntGlobalResiliation());
                clotureDomPlacementForm.setNbrGlobResiliation(placementVo3.getNbrGlobResiliation());
                
                clotureDomPlacementForm.setMntGlobalRistIntResi(placementVo3.getMntGlobalRistIntResi());
                clotureDomPlacementForm.setNbrGlobRistIntResi(placementVo3.getNbrGlobRistIntResi());
                
                clotureDomPlacementForm.setMntGlobalVersIntResi(placementVo3.getMntGlobalVersIntResi());
                clotureDomPlacementForm.setNbrGlobVersIntResi(placementVo3.getNbrGlobVersIntResi());
                
                clotureDomPlacementForm.setMntGlobalAbonIntRembAv(placementVo3.getMntGlobalAbonIntRembAv());
                clotureDomPlacementForm.setNbrGlobAbonIntRembAv(placementVo3.getNbrGlobAbonIntRembAv());
                
                clotureDomPlacementForm.setNbrOper641(placementVo3.getNbrOper641());
                clotureDomPlacementForm.setNbrOper642(placementVo3.getNbrOper642());
                clotureDomPlacementForm.setNbrOper643(placementVo3.getNbrOper643());
                clotureDomPlacementForm.setNbrOper644(placementVo3.getNbrOper644());
                clotureDomPlacementForm.setNbrOper645(placementVo3.getNbrOper645());
                clotureDomPlacementForm.setNbrOper615(placementVo3.getNbrOper615());

                clotureDomPlacementForm.setMntOper641(placementVo3.getMntOper641());
                clotureDomPlacementForm.setMntOper642(placementVo3.getMntOper642());
                clotureDomPlacementForm.setMntOper643(placementVo3.getMntOper643());
                clotureDomPlacementForm.setMntOper644(placementVo3.getMntOper644());
                clotureDomPlacementForm.setMntOper645(placementVo3.getMntOper645());
                clotureDomPlacementForm.setMntOper615(placementVo3.getMntOper615());
            }
            /*opposition levee opposition*/


            return mapping.findForward("clotDomPlac");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("rechercherDonneePlacemnet a �t� interrompue, veuillez transmettre ce message � l'�quipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotureDomPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            text.toString().replaceAll("java.lang.RuntimeException:"," ");
            text.toString().replaceAll("java.lang.Exception::"," ");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward detailPlacement(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        Context context = ContextHandler.getContext();
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        clotureDomPlacementForm.clearClotureDomPlacementForm();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        PramDetailPlacVo pramDetailPlacVo = new PramDetailPlacVo();
        ActionMessages actionMessages = new ActionMessages();
        try {
            if (Long.valueOf(clotureDomPlacementForm.getChoix()) <= 
                Long.valueOf("10")) {
                choixdetail(pramDetailPlacVo, clotureDomPlacementForm, 
                            paramAgence);

                DetailSouscPlacementCmd detailSouscPlacementCmd = 
                    new DetailSouscPlacementCmd();
                Listes listDemanSousc = new Listes();
                listDemanSousc = 
                        (Listes)detailSouscPlacementCmd.execute(pramDetailPlacVo);

                if (listDemanSousc.getList() != null && 
                    listDemanSousc.getList().size() > 0) {

                    traiterListedemandesDecision(listDemanSousc.getList(), 
                                                 clotureDomPlacementForm);


                }

            } else if ((Long.valueOf(clotureDomPlacementForm.getChoix()) > 
                       Long.valueOf("10"))&&(Long.valueOf(clotureDomPlacementForm.getChoix()) < 
                       Long.valueOf("15")) ) {
                choixdetail(pramDetailPlacVo, clotureDomPlacementForm, 
                            paramAgence);
                DetailContratPlacementCmd detailContratPlacementCmd = 
                    new DetailContratPlacementCmd();
                Listes listContPlac = new Listes();
                listContPlac = 
                        (Listes)detailContratPlacementCmd.execute(pramDetailPlacVo);

                if (listContPlac.getList() != null && 
                    listContPlac.getList().size() > 0) {

                    traiterListeContratPlacement(listContPlac.getList(), 
                                                 clotureDomPlacementForm);


                }


            } else if (Long.valueOf(clotureDomPlacementForm.getChoix()) > 
                       Long.valueOf("14")) {
                choixdetail(pramDetailPlacVo, clotureDomPlacementForm, 
                            paramAgence);

                DetailSouscPlacementCmd detailSouscPlacementCmd = 
                    new DetailSouscPlacementCmd();
                Listes listDemanSousc = new Listes();
                listDemanSousc = 
                        (Listes)detailSouscPlacementCmd.execute(pramDetailPlacVo);

                if (listDemanSousc.getList() != null && 
                    listDemanSousc.getList().size() > 0) {

                    traiterListedemandesDecision(listDemanSousc.getList(), 
                                                 clotureDomPlacementForm);


                }        
                       
            }
            return mapping.findForward("detailDomPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a �t� interrompue, veuillez transmettre ce message � l'�quipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotureDomPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward detailLiquidation(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        Context context = ContextHandler.getContext();
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        clotureDomPlacementForm.clearClotureDomPlacementForm();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
            paramAvanRembLiq.setCodPrdPrd(clotureDomPlacementForm.getProduitChoisi().toString());
            if (clotureDomPlacementForm.getMyTypeStructure().equals(Long.valueOf("1"))) {
                paramAvanRembLiq.setCodStrcStrc(paramAgence.getCodStrcStrc());
            }
             paramAvanRembLiq.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
          
            if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("1")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);
                paramAvanRembLiq.setTypeLiquidation("P");
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("2")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);
                paramAvanRembLiq.setTypeLiquidation("P");
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("3")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);
                paramAvanRembLiq.setTypeLiquidation("T");
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("4")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_LIQUIDATION_ANTICIPE);
                paramAvanRembLiq.setTypeLiquidation("T");
            }
            DetailAvancRembLiqCmd detailAvancRembLiqCmd = 
                new DetailAvancRembLiqCmd();
            Listes listAvance = new Listes();
            listAvance = 
                    (Listes)detailAvancRembLiqCmd.execute(paramAvanRembLiq);

            if (listAvance.getList() != null && 
                listAvance.getList().size() > 0) {

                traiterListeLiquidation(listAvance.getList(), 
                                        clotureDomPlacementForm);


            }


            return mapping.findForward("detailDomPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a �t� interrompue, veuillez transmettre ce message � l'�quipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotureDomPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward detailAvance(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws IOException, 
                                                                           ServletException {
        Context context = ContextHandler.getContext();
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        clotureDomPlacementForm.clearClotureDomPlacementForm();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAvanRembLiq paramAvanRembLiq = new ParamAvanRembLiq();
            paramAvanRembLiq.setCodPrdPrd(clotureDomPlacementForm.getProduitChoisi().toString());
            if (clotureDomPlacementForm.getMyTypeStructure().equals(Long.valueOf("1"))) {
                paramAvanRembLiq.setCodStrcStrc(paramAgence.getCodStrcStrc());
            }
             paramAvanRembLiq.setDateComptable(DateHandler.strToDate(paramAgence.getDateComptable()));
           
            if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("1")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_EN_ATTENTE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("2")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("3")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_REJETEE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_AVANCE);
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("4")) {
                paramAvanRembLiq.setCodEtatArl(Constants.ETAT_ARL_VALIDEE);
                paramAvanRembLiq.setCodToprtArl(Constants.CODE_REMBOURSEMENT_AVANCE);
            }

            DetailAvancRembLiqCmd detailAvancRembLiqCmd = 
                new DetailAvancRembLiqCmd();
            Listes listAvance = new Listes();
            listAvance = 
                    (Listes)detailAvancRembLiqCmd.execute(paramAvanRembLiq);

            if (listAvance.getList() != null && 
                listAvance.getList().size() > 0) {

                traiterListeAvance(listAvance.getList(), 
                                   clotureDomPlacementForm);


            }


            return mapping.findForward("detailDomPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a �t� interrompue, veuillez transmettre ce message � l'�quipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotureDomPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public ActionForward detailInteret(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
        Context context = ContextHandler.getContext();
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        clotureDomPlacementForm.clearClotureDomPlacementForm();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        ActionMessages actionMessages = new ActionMessages();
        try {
            OperMoyPayVo operMoyPayVo = new OperMoyPayVo();
            operMoyPayVo.setCodprdprd(clotureDomPlacementForm.getProduitChoisi());
            if (clotureDomPlacementForm.getMyTypeStructure().equals(Long.valueOf("1"))) {
                operMoyPayVo.setCodStrcStrc(paramAgence.getCodStrcStrc());
            }
            operMoyPayVo.setDateOperOpm(DateHandler.strToDate(paramAgence.getDateComptable()));
            if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("1")) {
                operMoyPayVo.setCodOperOpm(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST);

            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("2")) {
                operMoyPayVo.setCodOperOpm(Constants.OPER_INT_POST_SOUSC_PLAC);

            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("3")) {
                operMoyPayVo.setCodOperOpm(Constants.OPER_INT_PRE_SOUSC_PLAC);

            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("4")) {
                operMoyPayVo.setCodOperOpm(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE);

            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("5")) {
                operMoyPayVo.setCodOperOpm(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE);

            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("6")) {
                operMoyPayVo.setCodOperOpm(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE);
            }
            DetailOperMoyPayCmd detailOperMoyPayCmd = 
                new DetailOperMoyPayCmd();
            Listes listOperMoyPay = new Listes();
            listOperMoyPay = (Listes)detailOperMoyPayCmd.execute(operMoyPayVo);

            if (listOperMoyPay.getList() != null && 
                listOperMoyPay.getList().size() > 0) {

                traiterListeOperMoyPay(listOperMoyPay.getList(), 
                                       clotureDomPlacementForm);


            }


            return mapping.findForward("detailDomPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la demande choisie a �t� interrompue, veuillez transmettre ce message � l'�quipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotureDomPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
    }

    public void traiterListeOperMoyPay(List listOperMouPay, ActionForm form) {

        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
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
                clotureDomPlacementForm.setListeInteretServi(listInteretView);
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

    }

    public void traiterListeAvance(List listAvance, ActionForm form) {

        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        List listAvanceView = new ArrayList();

        try {
            if (listAvance != null && listAvance.size() > 0) {

                for (Iterator it = listAvance.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = 
                        (AvancRembLiquid)it.next();
                    if (avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString().equalsIgnoreCase(clotureDomPlacementForm.getStructureRech()) && 
                        avancRembLiquid.getContratPlacement().getProduitPlacement().getCodPrdPlc().longValue() == 
                        clotureDomPlacementForm.getProduitChoisi().longValue()) {
                        AvancRembLiquidView avancRembLiquidView = 
                            new AvancRembLiquidView();

                        avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                        if (avancRembLiquid.getCodToprArl().equalsIgnoreCase(Constants.CODE_REMBOURSEMENT_AVANCE)){
                            avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getAvancRembLiquid().getDatArlArl()));
                        }else{
                            avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                        }
                        avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                        avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                        /*String duree = 
                            Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(), 
                                                                                              avancRembLiquid.getDatPrevArl())))).toString();
                        avancRembLiquidView.setDuree(duree);*/
                        avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                        avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                        avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                        avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                        avancRembLiquidView.setLibRelation(avancRembLiquid.getContratPlacement().getContratCpt().getNomIntiCcpt());
                        avancRembLiquidView.setDatReelArl(DateHandler.dateToStr(avancRembLiquid.getDatReelArl()));

                        listAvanceView.add(avancRembLiquidView);
                    }
                } // Fin For 
                clotureDomPlacementForm.setListeDetailsAvance(listAvanceView);
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

    }

    public void traiterListeLiquidation(List listAvance, ActionForm form) {

        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        List listAvanceView = new ArrayList();

        try {
            if (listAvance != null && listAvance.size() > 0) {

                for (Iterator it = listAvance.iterator(); it.hasNext(); ) {
                    AvancRembLiquid avancRembLiquid = 
                        (AvancRembLiquid)it.next();
                    if (avancRembLiquid.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString().equalsIgnoreCase(clotureDomPlacementForm.getStructureRech())) {
                        AvancRembLiquidView avancRembLiquidView = 
                            new AvancRembLiquidView();
                        avancRembLiquidView.setNumSeqArl(avancRembLiquid.getNumSeqArl().toString());
                        avancRembLiquidView.setDatArlArl(DateHandler.dateToStr(avancRembLiquid.getDatArlArl()));
                        avancRembLiquidView.setMontArlArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontArlArl().doubleValue())));
                        avancRembLiquidView.setNumTauiArl(avancRembLiquid.getNumTauiArl().toString());
                        avancRembLiquidView.setMontInetArl(StrHandler.formatmnt(Math.abs(avancRembLiquid.getMontInetArl().doubleValue())));
                        /*String duree = 
                            Long.valueOf(Math.round(Double.valueOf(DateHandler.getDaysBetween(avancRembLiquid.getDatArlArl(), 
                                                                                              avancRembLiquid.getDatPrevArl())))).toString();
                        avancRembLiquidView.setDuree(duree);*/
                        avancRembLiquidView.setDatPrevArl(DateHandler.dateToStr(avancRembLiquid.getDatPrevArl()));
                        avancRembLiquidView.setContratPlacement(avancRembLiquid.getContratPlacement());
                        avancRembLiquidView.setMontActuCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontActuCpla())));
                        avancRembLiquidView.setMontCapCpla(StrHandler.formatmnt(Math.abs(avancRembLiquid.getContratPlacement().getMontCapCpla())));
                        avancRembLiquidView.setLibRelation(avancRembLiquid.getContratPlacement().getContratCpt().getNomIntiCcpt());
                       

                        listAvanceView.add(avancRembLiquidView);
                    }
                } // Fin For 
                clotureDomPlacementForm.setListesLiquidation(listAvanceView);
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

    }

    /**
     * Fonction qui retourne la liste des demandes � afficher
     * appel� par l'Action rechercherDemandesSelonChoix
     */
    public

    void traiterListedemandesDecision(List listDemandes, ActionForm form) {

        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
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


                    clotureDomPlacementForm.setAlertDemandeDecision("False");
                } // Fin For 
            } else {
                clotureDomPlacementForm.setAlertDemandeDecision("True");
            }
            clotureDomPlacementForm.setListeDemandesDecisionAttente(listDemandeDecisionAttente);

        } catch (Exception e) {
            logger.error("Exception dans souscriptionContratPlacementAction / Methode : traiterListedemandesDecision:  ", 
                         e);
            throw new RuntimeException(e);
        }

    }

    public ActionForward cloturerDomainePlac(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomPlacementForm clotureDomPlacementForm = 
                (ClotureDomPlacementForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journStrucDomVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            journStrucDomVo.setMatriculeInitiateur(paramAgence.getNumMatrUser());
            /*garniture des operations placement*/
            if (paramAgence.getCodTstrcTstrc()==1){
            garnirRecapOperatPlac(journStrucDomVo, clotureDomPlacementForm);
            }

            CloturedomPlacementCmd cloturedomPlacementCmd = 
                new CloturedomPlacementCmd();

            JournStrucDomVo journStrucDomVoRet = 
                (JournStrucDomVo)cloturedomPlacementCmd.execute(journStrucDomVo);
            if (journStrucDomVoRet == null || journStrucDomVoRet.hasError()) {
                List listErreur = journStrucDomVoRet.getErrors();
                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it1.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);

                return mapping.findForward("error");
            }

            clotureDomPlacementForm.setLibDom(journStrucDomVoRet.getLibDomaine());
            return mapping.findForward("confirmClotPlac");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward reouvrirDomainePlacement(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomPlacementForm clotureDomPlacementForm = 
                (ClotureDomPlacementForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JourneeStructureDomaine journeeStructureDomaine = 
                new JourneeStructureDomaine();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_PLACEMENT);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journeeStructureDomaine.setJourneeStructureDomaineId(journeeStructureDomaineId);

            UpdateDomaineCmd updateDomaineCmd = new UpdateDomaineCmd();


            journeeStructureDomaine = 
                    (JourneeStructureDomaine)updateDomaineCmd.execute(journeeStructureDomaine);
            if (journeeStructureDomaine == null || 
                journeeStructureDomaine.hasError()) {
                List listErreur = journeeStructureDomaine.getErrors();
                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it1.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);

                return mapping.findForward("error");
            } else {

                return mapping.findForward("indexSMILE");
            }


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomainePlacementAction  : ");
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

    void garnirRecapOperatPlac(JournStrucDomVo journStrucDomVo, 
                               ClotureDomPlacementForm clotureDomPlacementForm) {


        List listeoperPlac = new ArrayList();
        if (clotureDomPlacementForm.getNbrGlobSouscVal() > 0) {
            CloturePlacVo cloturePlacVo = new CloturePlacVo();
            cloturePlacVo.setCodOpertation(Constants.COD_OPER_SOUSC_PLAC);
            cloturePlacVo.setMontant(clotureDomPlacementForm.getMntGlobSouscVal());
            cloturePlacVo.setNombre(clotureDomPlacementForm.getNbrGlobSouscVal());
            listeoperPlac.add(cloturePlacVo);
        }
        if (clotureDomPlacementForm.getNbrGlobAvancVal() > 0) {
            CloturePlacVo cloturePlacVo1 = new CloturePlacVo();
            cloturePlacVo1.setCodOpertation(Constants.COD_OPER_AVANCE_PLAC);
            cloturePlacVo1.setMontant(clotureDomPlacementForm.getMntGlobAvancVal());
            cloturePlacVo1.setNombre(clotureDomPlacementForm.getNbrGlobAvancVal());
            listeoperPlac.add(cloturePlacVo1);
        }
        if (clotureDomPlacementForm.getNbrGlobRembVal() > 0) {
            CloturePlacVo cloturePlacVo2 = new CloturePlacVo();
            cloturePlacVo2.setCodOpertation(Constants.COD_OPER_REMB_AVANCE_PLAC);
            cloturePlacVo2.setMontant(clotureDomPlacementForm.getMntGlobRembVal());
            cloturePlacVo2.setNombre(clotureDomPlacementForm.getNbrGlobRembVal());
            listeoperPlac.add(cloturePlacVo2);
        }
        if (clotureDomPlacementForm.getNbrGlobRenVal() > 0) {
            CloturePlacVo cloturePlacVo3 = new CloturePlacVo();
            cloturePlacVo3.setCodOpertation(Constants.OPER_RENOUVEL_PLAC_APRE);
            cloturePlacVo3.setMontant(clotureDomPlacementForm.getMntGlobRenVal());
            cloturePlacVo3.setNombre(clotureDomPlacementForm.getNbrGlobRenVal());
            listeoperPlac.add(cloturePlacVo3);
        }
        if (clotureDomPlacementForm.getNbrGlobRenBatch() > 0) {
            CloturePlacVo cloturePlacVo8 = new CloturePlacVo();
            cloturePlacVo8.setCodOpertation(Constants.OPER_RENOUVEL_PLAC_AVAN);
            cloturePlacVo8.setMontant(clotureDomPlacementForm.getMntGlobRenBatch());
            cloturePlacVo8.setNombre(clotureDomPlacementForm.getNbrGlobRenBatch());
            listeoperPlac.add(cloturePlacVo8);
        }
        if (clotureDomPlacementForm.getNbrGlobInteretPre() > 0) {
            CloturePlacVo cloturePlacVo4 = new CloturePlacVo();
            cloturePlacVo4.setCodOpertation(Constants.OPER_INT_PRE_SOUSC_PLAC);
            cloturePlacVo4.setMontant(clotureDomPlacementForm.getMntGlobInteretPre());
            cloturePlacVo4.setNombre(clotureDomPlacementForm.getNbrGlobInteretPre());
            listeoperPlac.add(cloturePlacVo4);
        }
        if (clotureDomPlacementForm.getNbrGlobInteretPost() > 0) {
            CloturePlacVo cloturePlacVo5 = new CloturePlacVo();
            cloturePlacVo5.setCodOpertation(Constants.OPER_INT_POST_SOUSC_PLAC);
            cloturePlacVo5.setMontant(clotureDomPlacementForm.getMntGlobInteretPost());
            cloturePlacVo5.setNombre(clotureDomPlacementForm.getNbrGlobInteretPost());
            listeoperPlac.add(cloturePlacVo5);
        }
        if (clotureDomPlacementForm.getNbrGlobInteretPart() > 0) {
            CloturePlacVo cloturePlacVo6 = new CloturePlacVo();
            cloturePlacVo6.setCodOpertation(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST);
            cloturePlacVo6.setMontant(clotureDomPlacementForm.getMntGlobInteretPart());
            cloturePlacVo6.setNombre(clotureDomPlacementForm.getNbrGlobInteretPart());
            listeoperPlac.add(cloturePlacVo6);
        }
        if (clotureDomPlacementForm.getNbrLiqBatch() > 0) {
            CloturePlacVo cloturePlacVo7 = new CloturePlacVo();
            cloturePlacVo7.setCodOpertation(Constants.COD_OPER_LIQUID_AECH_PLAC);
            cloturePlacVo7.setMontant(clotureDomPlacementForm.getMntGlobLiqBatch());
            cloturePlacVo7.setNombre(clotureDomPlacementForm.getNbrLiqBatch());
            listeoperPlac.add(cloturePlacVo7);
        }
        if (clotureDomPlacementForm.getNbrLiqPart() > 0) {
            CloturePlacVo cloturePlacVo9 = new CloturePlacVo();
            cloturePlacVo9.setCodOpertation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE);
            cloturePlacVo9.setMontant(clotureDomPlacementForm.getMntGlobLiqPart());
            cloturePlacVo9.setNombre(clotureDomPlacementForm.getNbrLiqPart());
            listeoperPlac.add(cloturePlacVo9);
        }
        if (clotureDomPlacementForm.getNbrLiqAVEcheanceTOT() > 0) {
            CloturePlacVo cloturePlacVo10 = new CloturePlacVo();
            cloturePlacVo10.setCodOpertation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE);
            cloturePlacVo10.setMontant(clotureDomPlacementForm.getMntGlobLiqAVEcheanceTOT());
            cloturePlacVo10.setNombre(clotureDomPlacementForm.getNbrLiqAVEcheanceTOT());
            listeoperPlac.add(cloturePlacVo10);
        }
        if (clotureDomPlacementForm.getNbrGloblRecBC() > 0) {
            CloturePlacVo cloturePlacVo11 = new CloturePlacVo();
            cloturePlacVo11.setCodOpertation(Constants.COD_OPER_RECUP_BC_PLAC);
            cloturePlacVo11.setMontant(clotureDomPlacementForm.getMntGlobalRecBC());
            cloturePlacVo11.setNombre(clotureDomPlacementForm.getNbrGloblRecBC());
            listeoperPlac.add(cloturePlacVo11);
        }
        if (clotureDomPlacementForm.getNbrGlobResInt() > 0) {
            CloturePlacVo cloturePlacVo12 = new CloturePlacVo();
            cloturePlacVo12.setCodOpertation(Constants.COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE);
            cloturePlacVo12.setMontant(clotureDomPlacementForm.getMntGlobalResInt());
            cloturePlacVo12.setNombre(clotureDomPlacementForm.getNbrGlobResInt());
            listeoperPlac.add(cloturePlacVo12);
        }
        if (clotureDomPlacementForm.getNbrGlobrestit() > 0) {
            CloturePlacVo cloturePlacVo13 = new CloturePlacVo();
            cloturePlacVo13.setCodOpertation(Constants.COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE);
            cloturePlacVo13.setMontant(clotureDomPlacementForm.getMntGlobalrestit());
            cloturePlacVo13.setNombre(clotureDomPlacementForm.getNbrGlobrestit());
            listeoperPlac.add(cloturePlacVo13);
        }
        if (clotureDomPlacementForm.getNbrGlobVerIntLiq() > 0) {
            CloturePlacVo cloturePlacVo14 = new CloturePlacVo();
            cloturePlacVo14.setCodOpertation(Constants.COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE);
            cloturePlacVo14.setMontant(clotureDomPlacementForm.getMntGlobalVerIntLiq());
            cloturePlacVo14.setNombre(clotureDomPlacementForm.getNbrGlobVerIntLiq());
            listeoperPlac.add(cloturePlacVo14);
        }
        if (clotureDomPlacementForm.getNbrGlobPerIntAvanc() > 0) {
            CloturePlacVo cloturePlacVo15 = new CloturePlacVo();
            cloturePlacVo15.setCodOpertation(Constants.COD_OPER_PERSEPT_INTERET_AVANCE_PLAC);
            cloturePlacVo15.setMontant(clotureDomPlacementForm.getMntGlobalPerIntAvanc());
            cloturePlacVo15.setNombre(clotureDomPlacementForm.getNbrGlobPerIntAvanc());
            listeoperPlac.add(cloturePlacVo15);
        }
        if (clotureDomPlacementForm.getNbrGlobRistIntAvanc() > 0) {
            CloturePlacVo cloturePlacVo16 = new CloturePlacVo();
            cloturePlacVo16.setCodOpertation(Constants.COD_OPER_REMB_INTERET_REMB_AVANCE_PLAC);
            cloturePlacVo16.setMontant(clotureDomPlacementForm.getMntGlobalRistIntAvanc());
            cloturePlacVo16.setNombre(clotureDomPlacementForm.getNbrGlobRistIntAvanc());
            listeoperPlac.add(cloturePlacVo16);
        }
        if (clotureDomPlacementForm.getNbrGlobPerIntCompRemb() > 0) {
            CloturePlacVo cloturePlacVo17 = new CloturePlacVo();
            cloturePlacVo17.setCodOpertation(Constants.COD_OPER_PERSEPT_INTERET_REMB_AVANCE_PLAC);
            cloturePlacVo17.setMontant(clotureDomPlacementForm.getMntGlobalPerIntCompRemb());
            cloturePlacVo17.setNombre(clotureDomPlacementForm.getNbrGlobPerIntCompRemb());
            listeoperPlac.add(cloturePlacVo17);
        }
        if (clotureDomPlacementForm.getNbrGlobAbonExtInt() > 0) {
            CloturePlacVo cloturePlacVo18 = new CloturePlacVo();
            cloturePlacVo18.setCodOpertation(Constants.COD_OPER_ABONNE_EXTOURN_PLAC);
            cloturePlacVo18.setMontant(clotureDomPlacementForm.getMntGlobalAbonExtInt());
            cloturePlacVo18.setNombre(clotureDomPlacementForm.getNbrGlobAbonExtInt());
            listeoperPlac.add(cloturePlacVo18);
        }
        if (clotureDomPlacementForm.getNbrGlobAbonIntPlacPost() > 0) {
            CloturePlacVo cloturePlacVo19 = new CloturePlacVo();
            cloturePlacVo19.setCodOpertation(Constants.COD_OPER_ABONNE_INTERET_PLAC_POSTCOMPTE);
            cloturePlacVo19.setMontant(clotureDomPlacementForm.getMntGlobalAbonIntPlacPost());
            cloturePlacVo19.setNombre(clotureDomPlacementForm.getNbrGlobAbonIntPlacPost());
            listeoperPlac.add(cloturePlacVo19);
        }
        if (clotureDomPlacementForm.getNbrGlobAbonIntPlacPre() > 0) {
            CloturePlacVo cloturePlacVo20 = new CloturePlacVo();
            cloturePlacVo20.setCodOpertation(Constants.COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE);
            cloturePlacVo20.setMontant(clotureDomPlacementForm.getMntGlobalAbonIntPlacPre());
            cloturePlacVo20.setNombre(clotureDomPlacementForm.getNbrGlobAbonIntPlacPre());
            listeoperPlac.add(cloturePlacVo20);
        }
        if (clotureDomPlacementForm.getNbrGlobResiliation() > 0) {
            CloturePlacVo cloturePlacVo21 = new CloturePlacVo();
            cloturePlacVo21.setCodOpertation(Constants.COD_OPER_RESILIATION);
            cloturePlacVo21.setMontant(clotureDomPlacementForm.getMntGlobalResiliation());
            cloturePlacVo21.setNombre(clotureDomPlacementForm.getNbrGlobResiliation());
            listeoperPlac.add(cloturePlacVo21);
        }
        if (clotureDomPlacementForm.getNbrGlobRistIntResi() > 0) {
            CloturePlacVo cloturePlacVo22 = new CloturePlacVo();
            cloturePlacVo22.setCodOpertation(Constants.COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION);
            cloturePlacVo22.setMontant(clotureDomPlacementForm.getMntGlobalRistIntResi());
            cloturePlacVo22.setNombre(clotureDomPlacementForm.getNbrGlobRistIntResi());
            listeoperPlac.add(cloturePlacVo22);
        }
        if (clotureDomPlacementForm.getNbrGlobVersIntResi() > 0) {
            CloturePlacVo cloturePlacVo23 = new CloturePlacVo();
            cloturePlacVo23.setCodOpertation(Constants.COD_OPER_VERS_INTERET_SUITE_RESILIATION);
            cloturePlacVo23.setMontant(clotureDomPlacementForm.getMntGlobalVersIntResi());
            cloturePlacVo23.setNombre(clotureDomPlacementForm.getNbrGlobVersIntResi());
            listeoperPlac.add(cloturePlacVo23);
        }
        if (clotureDomPlacementForm.getNbrGlobAbonIntRembAv() > 0) {
            CloturePlacVo cloturePlacVo24 = new CloturePlacVo();
            cloturePlacVo24.setCodOpertation(Constants.COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC);
            cloturePlacVo24.setMontant(clotureDomPlacementForm.getMntGlobalAbonIntRembAv());
            cloturePlacVo24.setNombre(clotureDomPlacementForm.getNbrGlobAbonIntRembAv());
            listeoperPlac.add(cloturePlacVo24);
        }
        if (clotureDomPlacementForm.getNbrOper641() > 0) {
            CloturePlacVo cloturePlacVo25 = new CloturePlacVo();
            cloturePlacVo25.setCodOpertation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV);
            cloturePlacVo25.setMontant(clotureDomPlacementForm.getMntOper641());
            cloturePlacVo25.setNombre(clotureDomPlacementForm.getNbrOper641());
            listeoperPlac.add(cloturePlacVo25);
        }
        if (clotureDomPlacementForm.getNbrOper642() > 0) {
            CloturePlacVo cloturePlacVo26 = new CloturePlacVo();
            cloturePlacVo26.setCodOpertation(Constants.COD_OPER_SOUSC_PLAC_SBDV);
            cloturePlacVo26.setMontant(clotureDomPlacementForm.getMntOper642());
            cloturePlacVo26.setNombre(clotureDomPlacementForm.getNbrOper642());
            listeoperPlac.add(cloturePlacVo26);
        }
        if (clotureDomPlacementForm.getNbrOper643() > 0) {
            CloturePlacVo cloturePlacVo27 = new CloturePlacVo();
            cloturePlacVo27.setCodOpertation(Constants.OPER_INT_PRE_SOUSC_PLAC_SBDV);
            cloturePlacVo27.setMontant(clotureDomPlacementForm.getMntOper643());
            cloturePlacVo27.setNombre(clotureDomPlacementForm.getNbrOper643());
            listeoperPlac.add(cloturePlacVo27);
        }
        if (clotureDomPlacementForm.getNbrOper644() > 0) {
            CloturePlacVo cloturePlacVo28 = new CloturePlacVo();
            cloturePlacVo28.setCodOpertation(Constants.OPER_INT_POST_SOUSC_PLAC_SBDV);
            cloturePlacVo28.setMontant(clotureDomPlacementForm.getMntOper644());
            cloturePlacVo28.setNombre(clotureDomPlacementForm.getNbrOper644());
            listeoperPlac.add(cloturePlacVo28);
        }
        if (clotureDomPlacementForm.getNbrOper645() > 0) {
            CloturePlacVo cloturePlacVo29 = new CloturePlacVo();
            cloturePlacVo29.setCodOpertation(Constants.COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV);
            cloturePlacVo29.setMontant(clotureDomPlacementForm.getMntOper645());
            cloturePlacVo29.setNombre(clotureDomPlacementForm.getNbrOper645());
            listeoperPlac.add(cloturePlacVo29);
        }
        if (clotureDomPlacementForm.getNbrOper615() > 0) {
            CloturePlacVo cloturePlacVo30 = new CloturePlacVo();
            cloturePlacVo30.setCodOpertation(Constants.COD_OPER_ABONNE_AVANC_ECHU_PLAC);
            cloturePlacVo30.setMontant(clotureDomPlacementForm.getMntOper615());
            cloturePlacVo30.setNombre(clotureDomPlacementForm.getNbrOper615());
            listeoperPlac.add(cloturePlacVo30);
        }
        journStrucDomVo.setListeOperationsPlacement(listeoperPlac);


    }

    void choixdetail(PramDetailPlacVo pramDetailPlacVo, 
                     ClotureDomPlacementForm clotureDomPlacementForm, 
                     ParamAgence paramAgence) {

        pramDetailPlacVo.setCodeProduit(clotureDomPlacementForm.getProduitChoisi());
        if (clotureDomPlacementForm.getMyTypeStructure().equals(Long.valueOf("1"))) {
            pramDetailPlacVo.setCodeStructure(paramAgence.getCodStrcStrc());
        }
        if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("1")) { /*dem en attente*/
            pramDetailPlacVo.setDateCreation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("S");
            pramDetailPlacVo.setTypedem("S");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("2")) { /*dem valid�e CG*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("G");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("3")) { /*dem valid�e C PREF*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("SV");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("4")) { /*dem rejet�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("R");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");

        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("5")) { /*dem acc�pt�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("N");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");


        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("6")) { /*dem valid�e par tr�sorie*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
            pramDetailPlacVo.setStructureValid(new Long("900"));
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("7")) { /*souecription en attente*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("8")) { /*Dem renouv avant �ch�ance*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("R");
            pramDetailPlacVo.setCodTyprDemd(new Long("1"));
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("9")) { /*Dem renouv apr�s �ch�ance*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("R");
            pramDetailPlacVo.setCodTyprDemd(new Long("2"));
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("10")) { /*Dem renouv rejet�s*/
            pramDetailPlacVo.setDateRejet(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("R");
            pramDetailPlacVo.setTypedem("R");

        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("11")) { /*souscription valid�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("12")) { /*souscription rejet�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("R");
            pramDetailPlacVo.setTypedem("S");
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("13")) { /*placement arriv� a �ch�ance*/
            pramDetailPlacVo.setDateEcheance(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");

        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("14")) { /* renouvellemnt valid�e*/
            pramDetailPlacVo.setDateCreation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("R");
            
        /*les cas de la tresorerie*/
        } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("15")) { /* demande en attente d'etude*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("SV");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
        }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("16")) { /* demande �tudi�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("E");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
        }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("17")) { /* etude valid�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("EV");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
        }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("18")) { /* demande Notifi�*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("N");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
        }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("19")) { /* demande valid�e*/
            pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
            pramDetailPlacVo.setEtat("V");
            pramDetailPlacVo.setTypedem("S");
            pramDetailPlacVo.setTypeCond("P");
            } else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("20")) { /* renouvel en attente d'etude*/
                pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
                pramDetailPlacVo.setEtat("SV");
                pramDetailPlacVo.setTypedem("R");
                pramDetailPlacVo.setTypeCond("P");
            }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("21")) { /* renouvel �tudi�e*/
                pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
                pramDetailPlacVo.setEtat("E");
                pramDetailPlacVo.setTypedem("R");
                pramDetailPlacVo.setTypeCond("P");
            }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("22")) { /* renouvel valid�e*/
                pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
                pramDetailPlacVo.setEtat("EV");
                pramDetailPlacVo.setTypedem("R");
                pramDetailPlacVo.setTypeCond("P");
            }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("23")) { /* renouvel Notifi�*/
                pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
                pramDetailPlacVo.setEtat("N");
                pramDetailPlacVo.setTypedem("R");
                pramDetailPlacVo.setTypeCond("P");
            }else if (clotureDomPlacementForm.getChoix().equalsIgnoreCase("24")) { /* renouvel valid�e*/
                pramDetailPlacVo.setDatevalidation(DateHandler.strToDate(paramAgence.getDateComptable()));
                pramDetailPlacVo.setEtat("V");
                pramDetailPlacVo.setTypedem("R");
                pramDetailPlacVo.setTypeCond("P");
            }
    }

    public void traiterListeContratPlacement(List listContrats, 
                                             ActionForm form) {
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
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
                            logger.error("Le num�ro de BC est vide");
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
                clotureDomPlacementForm.setListeContratPlacement(listContratsPlacementView);
            }
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeContratPlacement:  ", 
                         e);
            throw new RuntimeException(e);
        }

    }
    public ActionForward imprimerRecapPlac(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ClotureDomPlacementForm clotureDomPlacementForm = 
            (ClotureDomPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            parameters.put("P_LIBELLE_ETAT", "RECAP DOMAINE PLACEMENT");
            parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
            parameters.put("P_DAT_COMPTABLE",paramAgence.getDateComptable());
            parameters.put("P_COD_STRC_STRC", 
                           paramAgence.getCodStrcStrc().toString());


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
}

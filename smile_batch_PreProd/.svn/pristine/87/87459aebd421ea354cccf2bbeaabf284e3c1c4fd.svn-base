
package com.bna.smile.web.placement.actions;

import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DemandeDecision;

import com.bna.commun.model.DetailsBc;
import com.bna.commun.model.DetailsOperationPlacement;

import com.bna.commun.model.Devise;
import com.bna.commun.model.InteretServi;

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
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;

import com.bna.smile.model.domaineplacement.commande.GetContratPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetDetailBcCmd;
import com.bna.smile.model.domaineplacement.commande.GetListAvancRembLiquidByEtatCmd;

import com.bna.smile.model.domaineplacement.commande.GetListBcRecupereCmd;
import com.bna.smile.model.domaineplacement.commande.GetListContratsPlacementCmd;
import com.bna.smile.model.domaineplacement.commande.GetListDemandesDecisionCmd;
import com.bna.smile.model.domaineplacement.commande.GetListInteretServiCmd;
import com.bna.smile.model.domaineplacement.commande.InsertBCCmd;
import com.bna.smile.model.domaineplacement.commande.ValiderRecuperationBcCmd;
import com.bna.smile.model.domaineplacement.commande.VerifBCCmd;
import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamAvanRembLiq;
import com.bna.smile.model.domaineplacement.model.ParamBonCaisse;
import com.bna.smile.model.domaineplacement.model.ParamDemandeDecision;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.placement.forms.AutresOperationsPlacementForm;
import com.bna.smile.web.placement.forms.AvancRembLiquidValidPlacementForm;
import com.bna.smile.web.placement.forms.ConsultationPlacementForm;
import com.bna.smile.web.placement.forms.CreationContratPlacementForm;
import com.bna.smile.web.placement.forms.SouscriptionContratPlacementForm;

import com.bna.smile.web.placement.view.AvancRembLiquidView;
import com.bna.smile.web.placement.view.ContratPlacementView;
import com.bna.smile.web.placement.view.DemandeDecisionView;

import com.bna.smile.web.placement.view.DetailBcView;
import com.bna.smile.web.placement.view.InteretServiView;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.IValueObject;

import java.io.File;
import java.io.IOException;

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

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class AutresOperationsPlacementAction extends DispatchAction {

    /**
     * <B> Action de la page AutresOperationsPlacementAction.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.placement.actions
     *
     * @version le 10/10/2009
     * @modify le 10/10/2009
     */
    private static final

    Logger logger = Logger.getLogger(AutresOperationsPlacementAction.class);


   
    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            //souscriptionContratPlacementForm.clearFormDemandeDecision();
            
             //verification de l'habilitation sur cette operation
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
             boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
             
            autresOperationsPlacementForm.setLibelleOperation("RECUPERATION BON DE CAISSE");
            autresOperationsPlacementForm.setChoix("2");
            autresOperationsPlacementForm.clearFormAutresOperations();
            autresOperationsPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            autresOperationsPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            autresOperationsPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            autresOperationsPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RECUP_BC_PLAC.toString());
            autresOperationsPlacementForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            autresOperationsPlacementForm.setListeContratPlacement(null);
            return mapping.findForward("initAutresOperationsPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
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
    
    
   
    public  ActionForward initierPageConsultBcRecupere(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            //souscriptionContratPlacementForm.clearFormDemandeDecision();
            
             //verification de l'habilitation sur cette operation
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_PLACEMENT);
             boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            autresOperationsPlacementForm.clearFormAutresOperations(); 
            autresOperationsPlacementForm.setLibelleOperation("CONSULTATION DES BONS DE CAISSE RECUPERES");
            autresOperationsPlacementForm.clearFormAutresOperations();
            autresOperationsPlacementForm.getInitialisationView().setDateOp(DateHandler.strToDate(paramAgence.getDateJours()));
            autresOperationsPlacementForm.getInitialisationView().setDateActuelle(paramAgence.getDateComptable());
            autresOperationsPlacementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            autresOperationsPlacementForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RECUP_BC_PLAC.toString());
            autresOperationsPlacementForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            autresOperationsPlacementForm.setListeContratPlacement(null);
            return mapping.findForward("initConsultBcRecupere");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Placement a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
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
   
   
    /**
     * Action qui permet d'afficher une liste des contrats placement selon le choix 
     */
    public

    ActionForward rechercherContratPlacSelonChoix(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {

        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        
        try {
            autresOperationsPlacementForm.setNumeroBcRecupere("");
            autresOperationsPlacementForm.setNumeroBcARecuperer("");
            autresOperationsPlacementForm.setListeContratPlacement(null);
            ParamDemandeDecision paramDemandeDecision = 
                new ParamDemandeDecision();
            GetListContratsPlacementCmd getListContratsPlacementCmd = 
                new GetListContratsPlacementCmd();
            Listes listContratPlacement = new Listes();
            Long[] listeStructure = new Long[50];
            Long [] listProduit = new Long[2];
            String [] listEtats = new String[3];
            PersonneStrc personneStrc = new PersonneStrc();           
            
            listEtats[0] = Constants.ETAT_CPT_PLACEMENT_LIQUIDE;
            listEtats[1] = Constants.ETAT_CPT_PLC_ATT_LIQUID;
            
            listeStructure[0] = Long.valueOf(autresOperationsPlacementForm.getCodStrcRech());
            paramDemandeDecision.setCodStrcStrc(listeStructure);   
            paramDemandeDecision.setCodEtatDemd(listEtats);
            listProduit[0] = Constants.COD_PRD_BC_PLAC;
            listProduit[1] = Constants.COD_PRD_BCDC_PLAC;
            paramDemandeDecision.setListeProduit(listProduit);
            
            if (autresOperationsPlacementForm.getChoix().equals("0")) {
                // traiter le cas de la recherche par type et numéro de pièce
                personneStrc.setCodTpceTpce(new Long(autresOperationsPlacementForm.getTypePieceId()));
                personneStrc.setNumPcePers(autresOperationsPlacementForm.getNumPieceId());
                GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
                Personne pers = (Personne)getPersonneCmd.execute(personneStrc);
                paramDemandeDecision.setNumSeqPers(pers.getNumSeqPers());
            } else if (autresOperationsPlacementForm.getChoix().equals("1")) {
                // traiter le cas de la recherche par numéro de contrat placement
                paramDemandeDecision.setNumSeqCpla(Long.valueOf(autresOperationsPlacementForm.getNumCplaRech()));
            } else if (autresOperationsPlacementForm.getChoix().equals("2")) {
                //traiter le cas de la recherche par contrat compte             
               
                paramDemandeDecision.getContratPersonne().getContratCptId().setCodPrdPrd(Long.valueOf(autresOperationsPlacementForm.getCodPrdRech()));
                paramDemandeDecision.getContratPersonne().getContratCptId().setNumCcptCcpt(Long.valueOf(autresOperationsPlacementForm.getNumCcptRech()));                
             } else if (autresOperationsPlacementForm.getChoix().equals("3")) {
                 paramDemandeDecision.setNumBc(autresOperationsPlacementForm.getNumBcRech());             
             }
             
            listContratPlacement = 
                    (Listes)getListContratsPlacementCmd.execute(paramDemandeDecision);

            if (listContratPlacement.getList() != null && listContratPlacement.getList().size() > 0) {
                List listCplacView = traiterListeContratPlacement(listContratPlacement.getList(), autresOperationsPlacementForm);
                autresOperationsPlacementForm.setListeContratPlacement(listCplacView);
            }
            
            paramDemandeDecision.setDateComptable(DateHandler.strToDate(autresOperationsPlacementForm.getInitialisationView().getDateActuelle()));  
            
            return mapping.findForward("initAutresOperationsPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des contrats placement a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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

    /**
     * Fonction qui retourne la liste des contrats placement à afficher
     * appelé par l'Action rechercherContratPlacSelonChoix
     */
    public List traiterListeContratPlacement(List listContrats, ActionForm form) {

        List listContratsPlacementView = new ArrayList();
        Context context = ContextHandler.getContext();
        try {
        PlacementDAO plcDao = (PlacementDAO)context.getBean("placementDAO");
            if (listContrats != null && listContrats.size() > 0) {

                for (Iterator it = listContrats.iterator(); it.hasNext(); ) {
                    ContratPlacement contratPlacement = 
                        (ContratPlacement)it.next();
                    
                    if(contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BC_PLAC) ||contratPlacement.getProduitPlacement().getCodPrdPlc().equals(Constants.COD_PRD_BCDC_PLAC)){
                       
                     
                       if(plcDao.verifierLigneDetailBc(contratPlacement.getNumBcCpla(),contratPlacement.getNumSeqCpla())){ 
                        
                       if(!plcDao.verifierRecuperationBc(contratPlacement.getNumBcCpla(),contratPlacement.getNumSeqCpla())){                    
                        // verifier si le BC est récuperé
                        ContratPlacementView contratPlacementView = 
                            new ContratPlacementView();
                        contratPlacementView.setContratPlacement(contratPlacement);
                        contratPlacementView.setNumSeqCpla(contratPlacement.getNumSeqCpla().toString());
                        contratPlacementView.setDatCreCpla(DateHandler.dateToStr(contratPlacement.getDatCreCpla()));                        
                        contratPlacementView.setNumBcCpla(contratPlacement.getNumBcCpla().toString());
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
    
                        listContratsPlacementView.add(contratPlacementView);
                    }  
                   }  
                   
                 }
                    
                } // Fin For 
            }
        } catch (Exception e) {
            logger.error("Exception dans consultationPlacementAction/ Methode : traiterListeContratPlacement:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listContratsPlacementView;
    }

 
     
    

    public  ActionForward validerRecuperation(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
    
    
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        
        try {
           GetDetailBcCmd getDetailBcCmd = new GetDetailBcCmd();
           DetailsBc detailBc  = new DetailsBc();
           ContratPlacement cpla = new ContratPlacement();
           GetContratPlacementCmd getContratPlacementCmd = new GetContratPlacementCmd();
           cpla.setNumSeqCpla(Long.valueOf(autresOperationsPlacementForm.getNumPlacement()));
           cpla = (ContratPlacement)getContratPlacementCmd.execute(cpla);
         
           autresOperationsPlacementForm.setContratPlacement(cpla);
           detailBc.setContratPlacement(cpla);
           detailBc.setNumBcDbc(Long.valueOf(autresOperationsPlacementForm.getNumeroBcRecupere()));
           detailBc = (DetailsBc)getDetailBcCmd.execute(detailBc);
           autresOperationsPlacementForm.setAlertDemandeDecision("");
           if(detailBc.getBonDeCaisse()!=null){
               // mettre à jour le detail BC
               OperationMoyPay operationMoyPay = new OperationMoyPay();
               ParamLiquidation paramLiquidation = new ParamLiquidation();
               
               operationMoyPay = affecterDonneesOperationMoyenPaiement (autresOperationsPlacementForm,request);               
               detailBc.setDateRecaBc(DateHandler.strToDate(paramAgence.getDateComptable()));
               paramLiquidation.setDetailBc(detailBc);
               paramLiquidation.setOperationMoyPayRecupBc(operationMoyPay);
               
               ValiderRecuperationBcCmd validerRecuperationBcCmd = new ValiderRecuperationBcCmd();
               paramLiquidation = (ParamLiquidation)validerRecuperationBcCmd.execute(paramLiquidation);
               imprimerAvisOperationRecupBc(form,request);
           }else{
               
               autresOperationsPlacementForm.setAlertDemandeDecision("BcIntrouvalbe");
           }
            
            return mapping.findForward("initAutresOperationsPlacement");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La validation de la Recuperation  du Bon de caisse a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
    
    public OperationMoyPay affecterDonneesOperationMoyenPaiement (ActionForm form,HttpServletRequest request){
        
            AutresOperationsPlacementForm autresOperationsPlacementForm = 
                (AutresOperationsPlacementForm)form;
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            OperationMoyPay operationMoyPay = new OperationMoyPay();          
            Personnel personnelInit = new Personnel();
            personnelInit.setNumMatrUser(paramAgence.getNumMatrUser());
            Operation operation = new Operation();    
            Structure structureInit = new Structure();    
            structureInit.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));
             
            Structure structureRecep = new Structure();    
            structureRecep.setCodStrcStrc(Long.valueOf(paramAgence.getCodStrcStrc()));           
             
            ContratCpt contratCpt =new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodPrdPrd(Long.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt()));
            contratCptId.setCodStrcStrc(Long.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc()));
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
            
            operationMoyPay.setCodRefcOmp(StrHandler.lpad(autresOperationsPlacementForm.getNumPlacement(),'0',15).substring(7,15));
            produitPlacOmp.setCodPrdPrd(Long.valueOf(autresOperationsPlacementForm.getContratPlacement().getProduitPlacement().getCodPrdPlc()));
            operationMoyPay.setProduit(produitPlacOmp);
            
            if (autresOperationsPlacementForm.getContratPlacement().getNumBcCpla()!=null && autresOperationsPlacementForm.getContratPlacement().getNumBcCpla().toString()!="")
            operationMoyPay.setNumMoypOmp(autresOperationsPlacementForm.getContratPlacement().getNumBcCpla().toString());

            operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
            operationMoyPay.setPersonnelInitiateur(personnelInit);/// personne initiatrice seulement au cas de retrait ponctuel
            operationMoyPay.setPersonnelValideur(personnelInit);/// personnel initiatrice = personnel validateur
            
            //operationMoyPay.setCodRefcOmp(avancRembLiquidValidPlacementForm.getInitialisationView().getCodeOperation());
            operation.setCodOperOper(Long.valueOf(Constants.COD_OPER_RECUP_BC_PLAC));
            
            Tache tache = new Tache();
            tache.setOperation(operation);
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(operation.getCodOperOper());            
            tacheId.setCodTachTach(Constants.COD_TACH_RECUP_BC_PLAC);
           
            tache.setTacheId(tacheId);
            operationMoyPay.setTache(tache);
            
            TraitementConditionBanque traitementConditionBanque = getCB(autresOperationsPlacementForm,request);
            
            operationMoyPay.setDatOperOmp(DateHandler.strToDate(paramAgence.getDateComptable()));
            operationMoyPay.setDatSystOmp(new Date()); 
            
            operationMoyPay.setDatValOmp(DateHandler.strToDate("15/03/2012"));
            
           
            operationMoyPay.setTypePieceDemandeur(cpt.getClient().getPersonne().getTypePiece());
            operationMoyPay.setNumPcedOmp(cpt.getClient().getPersonne().getNumPcePers().toString());
            
            traitementConditionBanque.setNumPcePers(cpt.getClient().getPersonne().getNumPcePers().toString());            
            if (cpt !=null)
                operationMoyPay.setMontSoldCcpt(cpt.getMontSoldCcpt());
            
            operationMoyPay.setMontDinOmp(autresOperationsPlacementForm.getContratPlacement().getMontCapCpla());
            operationMoyPay.setCodDemOmp("T");
            operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);
            operationMoyPay.setMontApreOmp(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getMontSoldCcpt() + autresOperationsPlacementForm.getContratPlacement().getMontCapCpla());
            operationMoyPay.setLibMotfOmp("Recuperation BC");
          
          
            return operationMoyPay;
        }    

    public TraitementConditionBanque getCB(ActionForm form,HttpServletRequest request){
    
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();

        try {
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            traitementConditionBanque.getPalierChar().clear();
            traitementConditionBanque.setCodOperOper(String.valueOf(Constants.COD_OPER_RECUP_BC_PLAC));
            
            traitementConditionBanque.setCodPrdPrd(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd()));
            traitementConditionBanque.setNumCcptCcpt(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt()));
            traitementConditionBanque.setCodStrcStrc(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc()));
            traitementConditionBanque.setCodPrdCpt(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getProduitPlacement().getCodPrdPlc()));
   
       
            ContratCpt contratCpt =new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodPrdPrd(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd());
            contratCptId.setNumCcptCcpt(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt());
            contratCptId.setCodStrcStrc(autresOperationsPlacementForm.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
            contratCpt.setContratCptId(contratCptId);
                
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
            ContratCpt cpt = (ContratCpt)getDetailContratCmd.execute(contratCptId);
                
            traitementConditionBanque.setCodTpceTpce(cpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
            traitementConditionBanque.setNumPcePers(cpt.getClient().getPersonne().getNumPcePers().toString());            
            
       
    ///   traitementConditionBanque.setIdContrat(avancRembLiquidValidPlacementForm.getAvancRembLiquidView().getContratPlacement().getNumSeqCpla().toString());
            traitementConditionBanque.setIdContrat(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getNumSeqCpla()));
            
            traitementConditionBanque.setMontant(String.valueOf(autresOperationsPlacementForm.getContratPlacement().getMontCapCpla()));
            
            traitementConditionBanque.setNbUnites((Long.valueOf(Math.abs(Long.valueOf(autresOperationsPlacementForm.getContratPlacement().getNumNbrjCpla()).longValue()))).toString());
            
            if(autresOperationsPlacementForm.getContratPlacement().getCodEtatCpla().equals(Constants.ETAT_CPT_PLACEMENT_LIQUIDE)){
              // cas d'un placement liquidé...
                int comp = DateHandler.strToDate(DateHandler.dateToStr(autresOperationsPlacementForm.getContratPlacement().getDatLiqCpla())).compareTo(DateHandler.strToDate(paramAgence.getDateComptable()));            
                
                if(comp == 0){
                    
                     traitementConditionBanque.setDateReference(DateHandler.dateToStr(autresOperationsPlacementForm.getContratPlacement().getDatEcheCpla()));
                     traitementConditionBanque.getPalierChar().add("34");
                    System.out.println("date ref *********  " + traitementConditionBanque.getDateReference());
                }else if(comp < 0){
                    //cas de la liquidation après echeance
                     traitementConditionBanque.setDateReference(paramAgence.getDateComptable());
                     traitementConditionBanque.getPalierChar().add("35");
                }
            }else{
                // cas d'une liquidation avant échéance...( anticipée)
                 traitementConditionBanque.setDateReference(paramAgence.getDateComptable());
                 traitementConditionBanque.getPalierChar().add("35");
            }
            
            traitementConditionBanque.getCB();
            
            System.out.println("date valeur *********  " + traitementConditionBanque.getDatevaleur());
           
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
                text.append("Exception au niveau de l'agence:"); text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
                text.append(". Exception Cond Bq:"); text.append(e.getMessage());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
            }
        return traitementConditionBanque;   
    
    }   
    
      
    
    public  ActionForward rechercherListeBcRecupere(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {

        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
           
            autresOperationsPlacementForm.setListeBcRecupere(null);
            ParamDemandeDecision paramDemandeDecision = new ParamDemandeDecision();
            GetListBcRecupereCmd getListBcRecupereCmd = new GetListBcRecupereCmd();
            Listes listBcRecup = new Listes();
            ParamBonCaisse paramBonCaisse = new ParamBonCaisse();
            if(!autresOperationsPlacementForm.getDateDebRecherch().equals(""))
              paramBonCaisse.setDateDebut(DateHandler.strToDate(autresOperationsPlacementForm.getDateDebRecherch()));
            if(!autresOperationsPlacementForm.getDateFinRecherch().equals(""))
              paramBonCaisse.setDateFin(DateHandler.strToDate(autresOperationsPlacementForm.getDateFinRecherch()));
            
            if(!autresOperationsPlacementForm.getNumCplaRech().equals("")){
                ContratPlacement cpla = new ContratPlacement();
                cpla.setNumSeqCpla(Long.valueOf(autresOperationsPlacementForm.getNumCplaRech()));
                paramBonCaisse.setContratPlacement(cpla);                
            }
            paramBonCaisse.setCodeStructure(paramAgence.getCodStrcStrc());  
            listBcRecup = (Listes)getListBcRecupereCmd.execute(paramBonCaisse);  
           
            if (listBcRecup.getList() != null && listBcRecup.getList().size() > 0) {
                List listBcRecupView = traiterListeBcRecup(listBcRecup.getList(), autresOperationsPlacementForm);
                autresOperationsPlacementForm.setListeBcRecupere(listBcRecupView);
            }
            
            
            return mapping.findForward("initConsultBcRecupere");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La recherche de la liste des Bc recuperes a généré une erreur, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("298");
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
    
    public List traiterListeBcRecup(List listBcRecup, ActionForm form) {

        List listBcRecupView = new ArrayList();
        Context context = ContextHandler.getContext();
        try {
        
            if (listBcRecup != null && listBcRecup.size() > 0) {

                for (Iterator it = listBcRecup.iterator(); it.hasNext(); ) {
                    DetailsBc detailsBc = (DetailsBc)it.next();
                    
                        DetailBcView detailBcView = new DetailBcView();
                        detailBcView.setNumSeqCpla(detailsBc.getContratPlacement().getNumSeqCpla().toString());
                        detailBcView.setDatCreCpla(DateHandler.dateToStr(detailsBc.getContratPlacement().getDatCreCpla()));                        
                        detailBcView.setNumBcCpla(detailsBc.getNumBcDbc().toString());
                        
                        detailBcView.setLibPrdPrd(detailsBc.getContratPlacement().getProduitPlacement().getLibAbrPlc());                        
                        detailBcView.setDateRecupBc(DateHandler.dateToStr(detailsBc.getDateRecaBc()));                        
                      
                        if (detailsBc.getContratPlacement().getContratCpt() != null) {
                            detailBcView.setNumCcptCpla(StrHandler.lpad(detailsBc.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3) + 
                                                                " " + StrHandler.lpad(detailsBc.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) + 
                                                                " " + StrHandler.lpad(detailsBc.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                        
                            detailBcView.setIntituleCpt(detailsBc.getContratPlacement().getContratCpt().getNomIntiCcpt());
                        }
    
                       
    
                        listBcRecupView.add(detailBcView);
                    }  
                   }  
                   
             
                    
              
        } catch (Exception e) {
            logger.error("Exception dans traiterListeBcRecup/ Methode : traiterListeBcRecup:  ", 
                         e);
            throw new RuntimeException(e);
        }
        return listBcRecupView;
    }  
    
    
    public void imprimerAvisOperationRecupBc(ActionForm form, 
                                       HttpServletRequest request
                                      ) throws IOException, ServletException {
    
             AutresOperationsPlacementForm autresOperationsPlacementForm = 
                 (AutresOperationsPlacementForm)form;
        try {
            
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 StringBuffer txtNomFichJasper =     new StringBuffer(File.separatorChar);
                 txtNomFichJasper.append("Placement");
                 txtNomFichJasper.append(File.separatorChar);
                 
              
                  txtNomFichJasper.append("avisOpRecupBc");
                  
                
              
                 parameters.put("P_LIB_ETAT", "AVIS D'OPERATION : RECUPERATION BON DE CAISSE");
                  
                 parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
                 parameters.put("P_COD_OPER", Constants.COD_OPER_RECUP_BC_PLAC);
                 parameters.put("P_NUM_PLAC",Long.valueOf(autresOperationsPlacementForm.getNumPlacement()));
                 
                 valueObject.setParams(parameters);
                 parameters = null;
                 /// indiquer le nom du fichier jasper                   
                 valueObject.setNomReport(txtNomFichJasper.toString());  
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
             }catch (Exception e) {
                 logger.error("Exception Methode : imprimerAvisOperationRecupBc:  ",e);  
                 throw new RuntimeException(e);
             }                                                                                

                                                                                          
         }  
    public ActionForward initSaisieBC(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        Context context = ContextHandler.getContext();
        autresOperationsPlacementForm.clearFormAutresOperations();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            autresOperationsPlacementForm.setLibelleOperation("SAISIE BON DE CAISSE");
        if (paramAgence.getCodTstrcTstrc().equals(Long.valueOf("1"))){
                 autresOperationsPlacementForm.setCodeAgence(paramAgence.getCodStrcStrc().toString());
                 autresOperationsPlacementForm.setErrorMessage("agence");
            } else {
                   autresOperationsPlacementForm.setCodeAgence("");
                   autresOperationsPlacementForm.setErrorMessage("direction");}
            return mapping.findForward("initSaisieBC");
            //initSitMensCPlac
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation de la saisie des bons de caisse a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
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
    public ActionForward verifNumeroBC(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;

        ParamBonCaisse paramBC=new ParamBonCaisse();
        VerifBCCmd verifBCCmd=new VerifBCCmd();
        autresOperationsPlacementForm.setErrorMessage("");
        //String nbre= autresOperationsPlacementForm.getNumNbreBC();
        paramBC.initi();
        
        try {
            
            if (!autresOperationsPlacementForm.getNumDebBC().equals("")&& autresOperationsPlacementForm.getNumFinBC().equals("")){
                paramBC.setNumBonCaisse(new Long(autresOperationsPlacementForm.getNumDebBC()));
                paramBC= (ParamBonCaisse)verifBCCmd.execute((IValueObject)paramBC);
               if (paramBC.isExistBonCaisse())
                   autresOperationsPlacementForm.setErrorMessage("BCExistantDeb");
            }
            if (!autresOperationsPlacementForm.getNumFinBC().equals("")){
                paramBC.setNumBonCaisse(new Long(autresOperationsPlacementForm.getNumFinBC()));
                paramBC= (ParamBonCaisse)verifBCCmd.execute((IValueObject)paramBC);
                if (paramBC.isExistBonCaisse())
                    autresOperationsPlacementForm.setErrorMessage("BCExistantFin");
                    Long nbreBC=Long.valueOf(autresOperationsPlacementForm.getNumFinBC())- Long.valueOf(autresOperationsPlacementForm.getNumDebBC())+1;
                    autresOperationsPlacementForm.setNumNbreBC(nbreBC.toString());
             }
            /*if (!autresOperationsPlacementForm.getNumDebBC().equals("")&&!autresOperationsPlacementForm.getNumFinBC().equals("")){
                paramBC.setNumBonCaisse(null);
                paramBC.setNumSeqDebBC(new Long(autresOperationsPlacementForm.getNumDebBC()));
                paramBC.setNumSeqDebBC(new Long(autresOperationsPlacementForm.getNumFinBC()));
                paramBC= (ParamBonCaisse)verifBCCmd.execute((IValueObject)paramBC);
                if (paramBC.isExistDetailsBC())
                    autresOperationsPlacementForm.setErrorMessage("INTExistant");
            }*/
            return mapping.findForward("initSaisieBC");  
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La verification des numéro des bons de caisse a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
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
    public ActionForward InsererBC(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        AutresOperationsPlacementForm autresOperationsPlacementForm = 
            (AutresOperationsPlacementForm)form;
        Context context = ContextHandler.getContext();
        String codeAgence =autresOperationsPlacementForm.getCodeAgence();
        ParamBonCaisse paramBC=new ParamBonCaisse();
        VerifBCCmd verifBCCmd=new VerifBCCmd();
        InsertBCCmd insertBCCmd=new InsertBCCmd();
        try {
            if (!autresOperationsPlacementForm.getNumDebBC().equals("")&&!autresOperationsPlacementForm.getNumFinBC().equals("")){
            
                paramBC.setNumBonCaisse(null);
                paramBC.setNumSeqDebBC(new Long(autresOperationsPlacementForm.getNumDebBC()));
                paramBC.setNumSeqDebBC(new Long(autresOperationsPlacementForm.getNumFinBC()));
                paramBC= (ParamBonCaisse)verifBCCmd.execute((IValueObject)paramBC);
                if (paramBC.isExistDetailsBC()){
                    autresOperationsPlacementForm.setErrorMessage("INTExistant");
                    return mapping.findForward("initSaisieBC");  }
                else{
                  paramBC.setNumSeqDebBC(new Long(autresOperationsPlacementForm.getNumDebBC()));
                  paramBC.setNumSeqFinBC(new Long(autresOperationsPlacementForm.getNumFinBC()));  
                  paramBC.setCodeStructure(Long.valueOf(autresOperationsPlacementForm.getCodeAgence()));
                  paramBC=(ParamBonCaisse)insertBCCmd.execute((IValueObject)paramBC);
                  if (paramBC!=null){
                  autresOperationsPlacementForm.setErrorMessage("Validation");
                  return mapping.findForward("initSaisieBC");  }
                }
            }
            return mapping.findForward("initSaisieBC");  
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("La verification des numéro des bons de caisse a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(autresOperationsPlacementForm.getInitialisationView().getCodeAgence());
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
}



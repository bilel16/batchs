package com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListCartesBancairesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesCartesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionCIBCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionLivretCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionBcPlacCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCIBCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCarteBanqueCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionChequesBanqueCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionLivretCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamOpposition;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionChequesTrt;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.certificationCheque.forms.CertificationChequeForm;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.forms.RechercheDemandesCartesForm;

import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms.OppositionMoyenPaiementForm;

import com.bna.smile.web.commun.view.ContratView;

import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions.PecDemandeCarteBancaireAction;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class OppositionMoyenPaiementAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
   
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
       Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
        
        try{
            SessionUtil sessionUtil =new SessionUtil();
            //Suppression des anciens Bean de type Form de la session, SAUF "oppositionMoyenPaiementForm"
            sessionUtil.removeSession(request,"oppositionMoyenPaiementForm"); 
            
            ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            //verification de l'habilitation sur cet operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
            
            Context context = ContextHandler.getContext();
            OppositionMoyenPaiementForm oppositionMoyenPaiementForm = 
                (OppositionMoyenPaiementForm)form;
            oppositionMoyenPaiementForm.clearForm();
    
            //---affectation duparametre de session code agence, maticule personnel et date du jour
            oppositionMoyenPaiementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            oppositionMoyenPaiementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            oppositionMoyenPaiementForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            //----------------------------------------------------------------------------------------//
    
            ///partie reserver pour les libellés legende selon operation
            setLegendeOpposition(oppositionMoyenPaiementForm);
            
            
            
            return mapping.findForward("success");
            } catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
            }
    }

    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
        try {

            OppositionMoyenPaiementForm oppositionMoyenPaiementForm = 
                (OppositionMoyenPaiementForm)form;
            ContratView contratView = 
                oppositionMoyenPaiementForm.getContratView();

            String codStrcStrc = contratView.getCodStrcStrc();
            String codPrdPrd = contratView.getCodPrdPrd();
            String numCcptCcpt = contratView.getNumCcptCcpt();
            String cle = contratView.getCle();

            oppositionMoyenPaiementForm.clearForm();

            //recherche contrat
            contratView.setCodStrcStrc(codStrcStrc);
            contratView.setCodPrdPrd(codPrdPrd);
            contratView.setNumCcptCcpt(numCcptCcpt);
            contratView.setNumCcptCcpt(numCcptCcpt);
            contratView.setCle(cle);

            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(contratView.getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(contratView.getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(contratView.getNumCcptCcpt()));

            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratCmd.execute(contratCptId);

            contratView.setContratCpt(contratCpt);

            if (contratCpt != null && contratCpt.getContratCptId() == null) {
                oppositionMoyenPaiementForm.getContratView().setMessageContratCpt("Contrat Inéxistant, veuillez verifier votre saisie, SVP");
            }
            return mapping.findForward("success");
            } catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
            }

    }

    public ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
        try {
            OppositionMoyenPaiementForm oppositionMoyenPaiementForm = 
                (OppositionMoyenPaiementForm)form;
            PersonneDemandeur personneDemandeur = 
                oppositionMoyenPaiementForm.getPersonneDemandeur();
            Pouvoir pouvoir = 
                (Pouvoir)request.getSession().getAttribute("pouvoir"); /// structure de l'agent 
            personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
            if (pouvoir.getCodPieceAnnexe() != null && 
                pouvoir.getNumPieceAnnexe() != null) {
                oppositionMoyenPaiementForm.getPersonneDemandeur().setCodTpceTpceDemandeur(pouvoir.getCodPieceAnnexe());
                oppositionMoyenPaiementForm.getPersonneDemandeur().setNumPcePersDemandeur(pouvoir.getNumPieceAnnexe());
            }
            return mapping.findForward("success");
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");
        }


    }

    public void setLegendeOpposition(OppositionMoyenPaiementForm oppositionMoyenPaiementForm) throws IOException, 
                                                                                                     ServletException {

     
           Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
           try{
            Long codeOperation = 
                Long.valueOf(oppositionMoyenPaiementForm.getInitialisationView().getCodeOperation());
            String legendeOperation = "";
                if (codeOperation != null && !codeOperation.equals("")) {
                    if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT)) {
                        legendeOperation = "Opposition Chèque";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE)) {
                        legendeOperation = "Opposition Chèque";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_CLIENT)) {
                        legendeOperation = "Opposition Carte";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_BANQUE)) {
                            legendeOperation = "Opposition Carte";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT)) {
                        legendeOperation = "Opposition Livret";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CIB_CLIENT)) {
                        legendeOperation = "Opposition C.I.B";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CHQ)) {
                        legendeOperation = "Levée Opposition Chèque";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_LIVRET)) {
                        legendeOperation = "Levée Opposition Livret";
                    } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CIB)) {
                        legendeOperation = "Levée Opposition C.I.B";
                    }else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_BC_PLAC)) {
                        legendeOperation = "Opposition BC";
                    }else if (codeOperation.equals(Constants.COD_OPER_OPER_LEV_OPP_BC_PLAC)) {
                        legendeOperation = "Levée Opposition BC";
                    }
                    oppositionMoyenPaiementForm.getOppositionMoyPaiementView().setLegendOperation(legendeOperation);
                }

        } catch (Exception e) {
            logger.error("Exception : ",e);
            throw new RuntimeException(e); 
        } 
    }
    public ActionForward validerTransaction(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {
        Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
        try {
            OppositionMoyenPaiementForm oppositionMoyenPaiementForm = 
                (OppositionMoyenPaiementForm)form;
            // effacer le champ alert
            oppositionMoyenPaiementForm.getInitialisationView().setAlert("");
            // effacer le champ alertChoix
            oppositionMoyenPaiementForm.getOppositionMoyPaiementView().setAlertChoix("");
            
            Long codeOperation = 
                Long.valueOf(oppositionMoyenPaiementForm.getInitialisationView().getCodeOperation());
            //extraire pouvoir du demandeur
            Pouvoir pouvoir = 
                (Pouvoir)request.getSession().getAttribute("pouvoir"); 

           //appel des commandes selon operation
            if (codeOperation != null && !codeOperation.equals("")) {
                // remplir l'obljet paramOpposition par les valeurs communes a toutes les opérations:operation,contrat,pouvoir
                ParamOpposition paramOpposition = setInitParamOpposition(oppositionMoyenPaiementForm, pouvoir, codeOperation);
                
               if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_CLIENT)) {    
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                     //si faillite affecter numéro et date jugement
                     if(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp().equals("F")){
                         paramOpposition.setNumJugement(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumJugement());
                         paramOpposition.setDatJugement(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDatJugement());    
                     }
                    paramOpposition.setNumPremierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumPremierCheq());
                    paramOpposition.setNumDernierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumDernierCheq());
                    //charger si  forçage
                    paramOpposition.setForcageEnCirculation(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getForcageEnCirculation());
                    
                    OppositionChequesCmd oppositionChequesCmd = new OppositionChequesCmd();
                    paramOpposition=(ParamOpposition)oppositionChequesCmd.execute(paramOpposition);
                              
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setNumPremierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumPremierCheq());
                    paramOpposition.setNumDernierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumDernierCheq());
                    //si faillite affecter numéro et date jugement
                    if(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp().equals("F")){
                        paramOpposition.setNumJugement(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumJugement());
                        paramOpposition.setDatJugement(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDatJugement());    
                    }
                    //charger si forçage
                    paramOpposition.setForcageEnCirculation(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getForcageEnCirculation());
                    
                    OppositionChequesBanqueCmd oppositionChequesBanqueCmd = new OppositionChequesBanqueCmd();
                    paramOpposition=(ParamOpposition)oppositionChequesBanqueCmd.execute(paramOpposition);
                    
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_CLIENT)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setTypeSupport(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getTypeSupport());
                    if(!oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDuree().equals("")){
                        paramOpposition.setDuree(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDuree());
                    }
                    if(!oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getLieu().equals("")){
                        paramOpposition.setLieu(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getLieu());
                    }
                    paramOpposition.setNumCarte(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCarte());
                    
                    OppositionCarteCmd oppositionCarteCmd = new OppositionCarteCmd();
                    paramOpposition=(ParamOpposition)oppositionCarteCmd.execute(paramOpposition);
                    
                
                   
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_BANQUE)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setTypeSupport(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getTypeSupport());
                    if(!oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDuree().equals("")){
                        paramOpposition.setDuree(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDuree());
                    }
                    if(!oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getLieu().equals("")){
                        paramOpposition.setLieu(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getLieu());
                    }
                    
                    paramOpposition.setNumCarte(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCarte());
                    
                    OppositionCarteBanqueCmd oppositionCarteBanqueCmd = new OppositionCarteBanqueCmd();
                    paramOpposition=(ParamOpposition)oppositionCarteBanqueCmd.execute(paramOpposition);
                   
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setTypeLivret(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getTypeLivret());
                    paramOpposition.setNumLivret(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumLivret());
                    
                    OppositionLivretCmd oppositionLivretCmd = new OppositionLivretCmd();
                    paramOpposition=(ParamOpposition)oppositionLivretCmd.execute(paramOpposition);
                        
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CIB_CLIENT)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setNumCIB(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCIB());
                    
                    OppositionBcPlacCmd oppositionBcPlacCmd = new OppositionBcPlacCmd();
                    paramOpposition=(ParamOpposition)oppositionBcPlacCmd.execute(paramOpposition);
                    
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_BC_PLAC)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setNumBcPlac(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumBcPlac());
                    paramOpposition.setNumBcnPlac(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumBcPlacNew());
                    paramOpposition.setNumSeqBc(oppositionMoyenPaiementForm.getNumSeqBCAjax());
                    
                    OppositionBcPlacCmd oppositionBCPlacCmd = new OppositionBcPlacCmd();
                    paramOpposition=(ParamOpposition)oppositionBCPlacCmd.execute(paramOpposition);
                    
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CHQ)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    //paramOpposition.setMotifOpposition(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getCodMotfOpmp());
                    paramOpposition.setNumPremierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumPremierCheq());
                    paramOpposition.setNumDernierChq(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumDernierCheq());
                    
                    LeveeOppositionChequesCmd LeveeOppositionChequesCmd = new LeveeOppositionChequesCmd();
                    paramOpposition=(ParamOpposition)LeveeOppositionChequesCmd.execute(paramOpposition);
                 
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_LIVRET)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setTypeLivret(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getTypeLivret());
                    paramOpposition.setNumLivret(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumLivret());
                    
                    LeveeOppositionLivretCmd leveeOppositionLivretCmd = new LeveeOppositionLivretCmd();
                    paramOpposition=(ParamOpposition)leveeOppositionLivretCmd.execute(paramOpposition);
                    
                } else if (codeOperation.equals(Constants.COD_OPER_OPER_LEVEE_CIB)) {
                    //remplir paramOpposition par les champs specifiq à cet operation
                    paramOpposition.setNumCIB(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCIB());
                    
                    LeveeOppositionCIBCmd leveeOppositionCIBCmd = new LeveeOppositionCIBCmd();
                    paramOpposition=(ParamOpposition)leveeOppositionCIBCmd.execute(paramOpposition);
            
                }
                
                 
                //Traitement des erreurs
                if(paramOpposition == null || paramOpposition.hasError()){
                    com.oxia.fwk.core.Error erreur = paramOpposition.getErrors().get(0);          
                    //si erreur habilitation ou erreur Technique sur cette operation --> page erreur
                    if(erreur.getCode().equals("Habilitation") || erreur.getCode().equals("Technique")){
                        ActionMessages actionMessages = new ActionMessages();
                        ActionMessage actionMessage = 
                            new ActionMessage("exception.generique", 
                                              erreur.getDescription());
                        actionMessages.add("Erreur ", actionMessage);   
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                    //si erreur Metier  --> la page courante
                    }else{
                       // si cheque en circulation, --> choix forçage
                        if(erreur.getCode().equals("MoyPayEnCirculation")){             
                            oppositionMoyenPaiementForm.getOppositionMoyPaiementView().setAlertChoix(erreur.getDescription()+". Voulez vous forçé l'opération ?");
                        }else{
                            oppositionMoyenPaiementForm.getInitialisationView().setAlert(erreur.getDescription()); 
                        }
                        return mapping.findForward("success");
                    } 
                }else{
                        
                    StringBuffer message1 = new StringBuffer(
                           "L'opération d'"+ oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getLegendOperation()+
                           " numéro "+oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumBcPlac()+oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCarte()+
                           oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumCIB()+oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumLivret()+
                           oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumPremierCheq()+".."+oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumDernierCheq()+
                           " sur le compte " +
                           StrHandler.lpad(oppositionMoyenPaiementForm.getContratView().getCodStrcStrc(), '0', 3) + " "+
                           StrHandler.lpad(oppositionMoyenPaiementForm.getContratView().getCodPrdPrd(), '0', 4) + " "+
                           StrHandler.lpad(oppositionMoyenPaiementForm.getContratView().getNumCcptCcpt(), '0', 6)+
                           " a été effectuée avec succès.");
                    ActionMessage actionMessage = new ActionMessage("exception.generique",message1.toString());
                    ActionMessages actionMessages = new ActionMessages();    
                    actionMessages.add("Msg_validation ", actionMessage);
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("confirmationGenerale");
                }                          
            }
             
            return mapping.findForward("success");
            } catch (Exception e) {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Exception : ",e);
                return mapping.findForward("error");
            }


    }

    private ParamOpposition setInitParamOpposition(OppositionMoyenPaiementForm oppositionMoyenPaiementForm, 
                                               Pouvoir pouvoir ,Long codeOperation) {
        ParamOpposition paramOpposition = new ParamOpposition();
        Logger logger = Logger.getLogger(OppositionMoyenPaiementAction.class);
        try{
        if(codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CHQ_BANQUE) || codeOperation.equals(Constants.COD_OPER_OPER_OPPOSITION_CARTE_BANQUE)){
            paramOpposition.setTypeActeur(Constants.COD_ACTR_OPMP_ChefAgence);  
        }else{
            String typePouvoir = oppositionMoyenPaiementForm.getPersonneDemandeur().getTypePouvoir();
            if(typePouvoir.equals(Constants.COD_TYPE_POUVOIR_INCONNU) || typePouvoir.equals(Constants.COD_TYPE_POUVOIR_AUCUN)){
                paramOpposition.setTypeActeur(Constants.COD_ACTR_OPMP_Tiers);
                paramOpposition.setNumActJudiciaire(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getNumActJuridique());
                paramOpposition.setDatActJudiciaire(DateHandler.strToDate(oppositionMoyenPaiementForm.getOppositionMoyPaiementView().getDatActJuridique()));
            }else{
                paramOpposition.setTypeActeur(pouvoir.getTypePouvoir());   
            }
        }
        paramOpposition.setCodeOperation(oppositionMoyenPaiementForm.getInitialisationView().getCodeOperation());
        paramOpposition.setMatriculeUser(oppositionMoyenPaiementForm.getInitialisationView().getNumMatrUser());
        
        paramOpposition.setContratCpt(oppositionMoyenPaiementForm.getContratView().getContratCpt());
        
        //affectation des champs reservé à l'acteur et son pouvoir
        if(pouvoir != null){
            paramOpposition.setTypePieceActeur(oppositionMoyenPaiementForm.getPersonneDemandeur().getCodTpceTpceDemandeur());
            paramOpposition.setNumPieceActeur(oppositionMoyenPaiementForm.getPersonneDemandeur().getNumPcePersDemandeur());
            
            paramOpposition.setMandat(pouvoir.getMandat());
            paramOpposition.setListMandatOperation(pouvoir.getListMandatOperation());
            paramOpposition.setListMandatPersonne(pouvoir.getListMandatPersonne());
            paramOpposition.setListCotitulaire(pouvoir.getListCotitulaire());
        }
        return paramOpposition;
    
        } catch (Exception e) {
            logger.error("Exception : ",e);
            throw new RuntimeException(e); 
        }
    }


}

package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.CarteBancaireId;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MotifRejet;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.AnnulRenouvCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DelivranceCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DemandeModifPlafondCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DemandeRemplacementCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.DestructionCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.EnvoiDrDqmrpCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.EnvoiScmCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetCartesEligibleContratCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetDemandeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListCartesBancairesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesCartesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.MiseAttenteDemandeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ModifPlafondCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PecDrDqmrpCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PecSccCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.PecScmCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ReceptionCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.RejetDelivCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.RejetDemandeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.RestitutionCarteBancaireCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ValidationDemandeCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheDemandeCarte;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.forms.RechercheDemandesCartesForm;

import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.util.CarteBancaireView;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.util.DemandeCarteView;

import com.bna.smile.web.procuration.util.MandatView;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;


import com.oxia.fwk.searchengine.SearchEngine;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class RechercheDemandesCartesAction extends DispatchAction {
    
    
        private static final Logger logger = Logger.getLogger(RechercheDemandesCartesAction.class);
        
        public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {
                                                                   
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "rechercheDemandesCartesForm"
        sessionUtil.removeSession(request,"rechercheDemandesCartesForm"); 
  
    try{
        Context context = ContextHandler.getContext();
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent
        
         //verification de l'habilitation sur cet operation
         StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
         boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
       
        rechercheDemandesCartesForm.setNumMatrUser(paramAgence.getNumMatrUser().toString());
        rechercheDemandesCartesForm.setCodeAgance(paramAgence.getCodStrcStrc().toString());
        rechercheDemandesCartesForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
        //----------------------------------------------------------------------------//
        rechercheDemandesCartesForm.clearForm();
        rechercheDemandesCartesForm.setChoix("4");
        DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
        String d = myformat.format(new Date());
        rechercheDemandesCartesForm.setDateActuelle(d);
        
        List viewList = new ArrayList(); 
        Listes listes = new Listes();
        
        // remplir parametres de recherche selon cas : code agence, codes etas, titre page 
        ParamRechercheDemandeCarte paramRechercheDemandeCarte = setParamRechercheDemandeCarte(rechercheDemandesCartesForm);
        
        // sauvgarde des parametres de recherche de base (selon operation)
        rechercheDemandesCartesForm.setParamRechercheDemandeCarte(paramRechercheDemandeCarte);
        //affichage la liste des demande ou la liste des carte selon code opération
        Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        Long codTache = Long.valueOf(rechercheDemandesCartesForm.getCodeTache());
        // si validation demande, Prévalidation DR,SCM,SCC, ConsultationDemandeCarte, reception : afficher liste des demande 
        if((codOperation.equals(Constants.COD_OPER_OPER_ValidDemande) && codTache.equals(Constants.COD_TACH_TACH_ValidDemande))|| 
            (codOperation.equals(Constants.COD_OPER_OPER_PrevalidDR) && codTache.equals(Constants.COD_TACH_TACH_PrevalidDR))||
            (codOperation.equals(Constants.COD_OPER_OPER_PrevalidScm) && codTache.equals(Constants.COD_TACH_TACH_PrevalidScm))||
            (codOperation.equals(Constants.COD_OPER_OPER_PrevalidScc) && codTache.equals(Constants.COD_TACH_TACH_PrevalidScc))||
            (codOperation.equals(Constants.COD_OPER_OPER_ConsultationDemandeCarte))||
            (codOperation.equals(Constants.COD_OPER_OPER_RemplaceeCarte) && codTache.equals(Constants.COD_TACH_TACH_RemplaceeCarte))||
            (codOperation.equals(Constants.COD_OPER_OPER_ReceptCarte))){
            GetListDemandesCartesCmd getListDemandesCartesCmd = new GetListDemandesCartesCmd();
            List listDemandesCarte = new ArrayList();
            listes = (Listes) getListDemandesCartesCmd.execute(paramRechercheDemandeCarte);
            listDemandesCarte = listes.getList();
            viewList = setDemandesCartesView(listDemandesCarte);
            rechercheDemandesCartesForm.setListeDemandesCartes(viewList);
            rechercheDemandesCartesForm.setTypeTraitement("DEMANDES");
        }else{
           //si autre que l'operation de annulation renouvellement et DemandeRempl et restitution et demande modif plafond afficher tous si non demander critere num carte
            if(codOperation.equals(Constants.COD_OPER_OPER_AnnulRenouvel) ||
            (codOperation.equals(Constants.COD_OPER_OPER_DemandeRempl) && codTache.equals(Constants.COD_TACH_TACH_DemandeRempl) )|| 
                (codOperation.equals(Constants.COD_OPER_OPER_DemandeModifPlafond) && codTache.equals(Constants.COD_TACH_TACH_DemandeModifPlafond))||
                (codOperation.equals(Constants.COD_OPER_OPER_RestitueeCarte))){
                rechercheDemandesCartesForm.setChoix("2");
            }else{
                GetListCartesBancairesCmd getListCartesBancairesCmd = new GetListCartesBancairesCmd();
                List listCarte = new ArrayList();
                listes = (Listes) getListCartesBancairesCmd.execute(paramRechercheDemandeCarte);
                listCarte = listes.getList();
                viewList = setCartesBancairesView(listCarte);  
                rechercheDemandesCartesForm.setListeCartesBancaires(viewList);

            }
            rechercheDemandesCartesForm.setTypeTraitement("CARTES");
        }
         
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
          
        return mapping.findForward("success"); 
    }
    
    private ParamRechercheDemandeCarte setParamRechercheDemandeCarte(RechercheDemandesCartesForm rechercheDemandesCartesForm){
        
        ParamRechercheDemandeCarte paramRechercheDemandeCarte = new ParamRechercheDemandeCarte();
        //critère code agence
        paramRechercheDemandeCarte.setCodAgence(Long.valueOf(rechercheDemandesCartesForm.getCodStrcRech()));
        
        //critère etats de recherche selon code opération et code tache
        Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        //affecter codeTache la valeur 0 si non affectation au menu 
        Long codTache = Long.valueOf(0);
        if(rechercheDemandesCartesForm.getCodeTache()!=null)
            codTache = Long.valueOf(rechercheDemandesCartesForm.getCodeTache());
        else
            rechercheDemandesCartesForm.setCodeTache("0");
        /// Cas de validation demande
        if(codOperation.equals(Constants.COD_OPER_OPER_ValidDemande) && codTache.equals(Constants.COD_TACH_TACH_ValidDemande)){ 
            //affectation titre et message choi
            rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_ValidDemande); 
            rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
            rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
            rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie); 
            rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande); 
            //critère etat demande : attente ou attente garantie ou prevalidée ou demande de remplacement ou demande modif plafond
             String [] ret = {Constants.COD_ETAT_DCAR_Attente,Constants.COD_ETAT_DCAR_AttenteGarantie,Constants.COD_ETAT_DCAR_PrevaliderDR,Constants.COD_ETAT_DCAR_PrevaliderScm,Constants.COD_ETAT_DCAR_PrevaliderScc,Constants.COD_ETAT_DCAR_DemandeRempl,Constants.COD_ETAT_DCAR_DemandeModifPlafond};   
            paramRechercheDemandeCarte.setEtatsRecherche(ret);
        /// Cas Prévalidation DR
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidDR)&& codTache.equals(Constants.COD_TACH_TACH_PrevalidDR)){ 
            rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_PrevalidDR); 
            rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
            rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
            rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie);
            rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande); 
            //critère etat demande : attenetDQMRP
            String [] ret = {Constants.COD_ETAT_DCAR_AttenteDR};
            paramRechercheDemandeCarte.setEtatsRecherche(ret);
        /// Cas Prévalidation SCM
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidScm)&& codTache.equals(Constants.COD_TACH_TACH_PrevalidScm)){ 
                rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_PrevalidScm); 
                rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
                rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
                rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie);
                rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande); 
                //critère etat demande : attenetDQMRP
                String [] ret = {Constants.COD_ETAT_DCAR_AttenteScm};
                paramRechercheDemandeCarte.setEtatsRecherche(ret);
            /// Cas Prévalidation SCM
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidScc)&& codTache.equals(Constants.COD_TACH_TACH_PrevalidScc)){ 
                rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_PrevalidScc); 
                rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
                rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
                rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie);
                rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande); 
                //critère etat demande : attenetDQMRP
                String [] ret = {Constants.COD_ETAT_DCAR_AttenteScc};
                paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_ReceptCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_ReceptCarte); 
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie); 
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande); 
             // critère demande non de modification
             paramRechercheDemandeCarte.setBoolModifPlafond(Long.valueOf("0")); 
             //critère etat demande : valide,valideRemplaceme
             String [] ret = {Constants.COD_ETAT_DCAR_Valider,Constants.COD_ETAT_DCAR_DemandeRemplValide};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_DelivrCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_DelivrCarte);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
            //critère etat demande : reçu
             String [] ret = {Constants.COD_ETAT_CARB_CarteRecu};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_AnnulRenouvel)){
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_AnnulRenouvel);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
             // critère carte non encore annulées
             paramRechercheDemandeCarte.setBoolAnnulerRenouv(Long.valueOf("0"));  
            //critère non fin periode de 1 mois avant fin validité de la carte 
             Date date = DateHandler.addMonth(DateHandler.strToDate(DateHandler.dateJour()),1);
             paramRechercheDemandeCarte.setDateFinSup(date);
            //critère etat carte bancaire : remise 
             String [] ret = {Constants.COD_ETAT_CARB_CarteRemise};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_DemandeRempl)&& codTache.equals(Constants.COD_TACH_TACH_DemandeRempl)){ 
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_DemandeRempl);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
            //critère non fin validité de la carte 
             Date date = DateHandler.strToDate(DateHandler.dateJour());
             paramRechercheDemandeCarte.setDateFinSup(date);
            //critère etat carte bancaire : remise  
             String [] ret = {Constants.COD_ETAT_CARB_CarteRemise};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_RemplaceeCarte)&& codTache.equals(Constants.COD_TACH_TACH_RemplaceeCarte)){ 
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_RemplaceeCarte);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande);
             //critère etat demande : demande de remplacement valide
             String [] ret = {Constants.COD_ETAT_DCAR_DemandeRemplValide};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_RenouvelAut)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_RenouvelAutomatique);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
            //critère non fin validité de la carte 
             Date date = DateHandler.strToDate(DateHandler.dateJour());
             paramRechercheDemandeCarte.setDateFinSup(date);
             //critere dat fin demande est proche de 2 mois au maximum du date du jour 
                //calcul sysdate + periode d'affichage 
             Date datePeriode = DateHandler.addMonth(DateHandler.strToDate(DateHandler.dateJour()),Integer.parseInt(Constants.PERIODE_RENOUVELLEMNET.toString())); 
             paramRechercheDemandeCarte.setDateFinInf(datePeriode);
            // critère carte non annulées pour renouvellement
             paramRechercheDemandeCarte.setBoolAnnulerRenouv(Long.valueOf("0"));  
            //critère etat carte bancaire : remise 
             String [] ret = {Constants.COD_ETAT_CARB_CarteRemise};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_RestitueeCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_RestitueeCarte);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
            //critère non fin validité de la carte 
             Date date = DateHandler.strToDate(DateHandler.dateJour());
             paramRechercheDemandeCarte.setDateFinSup(date);
            //critère etat carte bancaire : remise 
             String [] ret = {Constants.COD_ETAT_CARB_CarteRemise};
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_DetruireCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_DetruireCarte);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
            //critère etat carte bancaire : restituer,malConfectionner,rejetDelivrance(contrat non valide..),reçu 
             String [] ret = {Constants.COD_ETAT_CARB_CarteRestituee,Constants.COD_ETAT_CARB_CarteMalConfect,Constants.COD_ETAT_CARB_RejetDelivreCarte,Constants.COD_ETAT_CARB_CarteRecu};        
             paramRechercheDemandeCarte.setEtatsRecherche(ret);
             //critere date dernière opération pour critère carte mise pour destruction apres 3 mois de reception sans remise
             Date dateRecept = DateHandler.addMonth(DateHandler.strToDate(DateHandler.dateJour()),Integer.parseInt("-"+Constants.PERIODE_RECEPT_DESTRUCTION.toString())); 
             paramRechercheDemandeCarte.setDateDernOper(dateRecept);
        }else if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationDemandeCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_ConsultationDemandeCarte); 
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Demande); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Demande);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Demande_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroDemande);
             if(rechercheDemandesCartesForm.getReqCode().equals("initierPage")){
                 rechercheDemandesCartesForm.setChoixEtatDemande(Constants.COD_ETAT_DCAR_Attente);
                 String [] ret = {Constants.COD_ETAT_DCAR_Attente};
                 paramRechercheDemandeCarte.setEtatsRecherche(ret);
             }
             //rechercheDemandesCartesForm.setChoixEtat("0");
        }else if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationCarte)){  
             rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_ConsultationCarte);
             rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
             rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
             rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
             rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
             if(rechercheDemandesCartesForm.getReqCode().equals("initierPage")){
                 rechercheDemandesCartesForm.setChoixEtatCarte(Constants.COD_ETAT_CARB_CarteRecu);
                 String [] ret = {Constants.COD_ETAT_CARB_CarteRecu};
                 paramRechercheDemandeCarte.setEtatsRecherche(ret);
             }
             //rechercheDemandesCartesForm.setChoixEtat("0");
        }else if(codOperation.equals(Constants.COD_OPER_OPER_DemandeModifPlafond)&& codTache.equals(Constants.COD_TACH_TACH_DemandeModifPlafond)){ 
                   rechercheDemandesCartesForm.setLibelleOperation(Constants.TITRE_DemandeModifPlafond);
                   rechercheDemandesCartesForm.setLibListeChoix(Constants.Message_Choix_Carte); 
                   rechercheDemandesCartesForm.setLibFieldsetChoix(Constants.Message_libFieldsetChoix_Carte);
                   rechercheDemandesCartesForm.setLibChoisie(Constants.Message_Carte_Choisie);
                   rechercheDemandesCartesForm.setLibChoixNumero(Constants.Message_Choix_NumeroCarte);
                  //critère non fin validité de la carte 
                   Date date = DateHandler.strToDate(DateHandler.dateJour());
                   paramRechercheDemandeCarte.setDateFinSup(date);
                  //critère etat carte bancaire : remise  
                   String [] ret = {Constants.COD_ETAT_CARB_CarteRemise};
                   paramRechercheDemandeCarte.setEtatsRecherche(ret);
        }
       return  paramRechercheDemandeCarte;
    }
    private List setDemandesCartesView(List list){
        
        List viewList = new ArrayList(); 
        Iterator it = list.iterator();
        DemandeCarte demandeCarte = new DemandeCarte();
        
        for(;it.hasNext();){
            demandeCarte = (DemandeCarte)it.next();
            DemandeCarteView demandeCarteview = new DemandeCarteView();
            demandeCarteview.setDemandeCarte(demandeCarte);
            viewList.add(demandeCarteview); 
        }  
        return viewList;
    }
    
    private List setCartesBancairesView(List list){ 
        List viewList = new ArrayList(); 
        Iterator it = list.iterator();
        CarteBancaire carteBancaire = new CarteBancaire();
        
        for(;it.hasNext();){
            carteBancaire = (CarteBancaire)it.next();
            CarteBancaireView carteBancaireView = new CarteBancaireView();
            carteBancaireView.setCarteBancaire(carteBancaire);
            viewList.add(carteBancaireView); 
        }  
        return viewList;
    }
     
    public ActionForward rechercherDemandesSelonChoix(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
    
       RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
         Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
       //pour des raisopns d'appel par d'autre action
       rechercheDemandesCartesForm.setReqCode("rechercherDemandesSelonChoix");
       //sauvgarde des criteres de recherche
       String choix = rechercheDemandesCartesForm.getChoix();
       String numDemande = rechercheDemandesCartesForm.getNumDemande();
       String typePieceId = rechercheDemandesCartesForm.getTypePieceId();
       String numPieceId = rechercheDemandesCartesForm.getNumPieceId();
       String codStrcRech = rechercheDemandesCartesForm.getCodStrcRech();
       String codPrdRech = rechercheDemandesCartesForm.getCodPrdRech();
       String numCcptRech = rechercheDemandesCartesForm.getNumCcptRech();
       String dateDebutRech = rechercheDemandesCartesForm.getDateDebutRech();
       String dateFinRech = rechercheDemandesCartesForm.getDateFinRech();
       String choixEtat = "";
       if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationDemandeCarte))
         choixEtat = rechercheDemandesCartesForm.getChoixEtatDemande();
       else if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationCarte))
         choixEtat = rechercheDemandesCartesForm.getChoixEtatCarte();
    
      
      //clear la page
        rechercheDemandesCartesForm.clearForm();
        
      //reaffectaion du choix
       rechercheDemandesCartesForm.setChoix(choix);
       /*//rechercheDemandesCartesForm.setNumDemande(numDemande);
       //rechercheDemandesCartesForm.setTypePieceId(typePieceId);
       //rechercheDemandesCartesForm.setNumPieceId(numPieceId);
       //rechercheDemandesCartesForm.setCodStrcRech(codStrcRech);
       //rechercheDemandesCartesForm.setCodPrdRech(codPrdRech);
       //rechercheDemandesCartesForm.setNumCcptRech(numCcptRech);
       //rechercheDemandesCartesForm.setDateDebutRech(dateDebutRech);
       //rechercheDemandesCartesForm.setDateFinRech(dateFinRech);*/
       
      
        
        Listes listes = new Listes();
        List viewList = new ArrayList(); 
        
        
        /*//extraire les parametres de base depuis la formbean
        
        ParamRechercheDemandeCarte paramRecherche = new ParamRechercheDemandeCarte();
        rechercheDemandesCartesForm.getParamRechercheDemandeCarte();*/
        
        // remplir de nouveau parametres de recherche de base : code agence, codes etas se lon opération 
        ParamRechercheDemandeCarte paramRecherche = setParamRechercheDemandeCarte(rechercheDemandesCartesForm);
        
        //critère selon choix
            /// si par num piece porteur
        if(choix.equals("0")){
            PersonneStrc porteur = new PersonneStrc();
            porteur.setCodTpceTpce(Long.valueOf(typePieceId));
            porteur.setNumPcePers(numPieceId);
            paramRecherche.setPorteur(porteur);
            rechercheDemandesCartesForm.setTypePieceId(typePieceId);
            rechercheDemandesCartesForm.setNumPieceId(numPieceId);
        }else if(choix.equals("1")){
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(codStrcRech));
            contratCptId.setCodPrdPrd(Long.valueOf(codPrdRech));
            contratCptId.setNumCcptCcpt(Long.valueOf(numCcptRech));
            paramRecherche.setContratCptId(contratCptId);  
            rechercheDemandesCartesForm.setCodStrcRech(codStrcRech);
            rechercheDemandesCartesForm.setCodPrdRech(codPrdRech);
            rechercheDemandesCartesForm.setNumCcptRech(numCcptRech);
        }else if(choix.equals("2")){
            paramRecherche.setNumRecherche(numDemande);
            rechercheDemandesCartesForm.setNumDemande(numDemande);
        }else if(choix.equals("3")){
            if(!dateDebutRech.equals("")){
                paramRecherche.setDateDebutSup(DateHandler.strToDate(dateDebutRech));
                rechercheDemandesCartesForm.setDateDebutRech(dateDebutRech);
            }
            if(!dateFinRech.equals("")){
                paramRecherche.setDateDebutInf(DateHandler.strToDate(dateFinRech));
                rechercheDemandesCartesForm.setDateFinRech(dateFinRech);
            }
        }
        
        //ajouter le critère de l'etat choisi si cas de consultation :demande ou carte
        if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationDemandeCarte) || codOperation.equals(Constants.COD_OPER_OPER_ConsultationCarte)){            
              if(!choixEtat.equals("0") && !choixEtat.equals("21")){
                   //Cas de demande modif plafonds valide --> ajouter le critere BoolModifPlafond=1
                   if(choixEtat.equals("-5")){
                       paramRecherche.setBoolModifPlafond(Long.valueOf(1));
                       String [] ret = {"5"};
                       paramRecherche.setEtatsRecherche(ret); 
                   }else if(choixEtat.equals("5")){
                       paramRecherche.setBoolModifPlafond(Long.valueOf(0));
                       String [] ret = {choixEtat};
                       paramRecherche.setEtatsRecherche(ret); 
                   }else{
                       String [] ret = {choixEtat};
                       paramRecherche.setEtatsRecherche(ret); 
                   }
                   
                    
                    //si cas de carte en circulation  ajouté critere de fin validité
                    if(choixEtat.equals("20")){
                        //critère non fin validité de la carte 
                        Date date = DateHandler.strToDate(DateHandler.dateJour());
                        paramRecherche.setDateFinSup(date);
                    }          
             }else{
                 //cas consultation annulation renouvellement: 
                 if(choixEtat.equals("21")){
                    paramRecherche.setBoolAnnulerRenouv(Long.valueOf("1"));
                 }
                 paramRecherche.setEtatsRecherche(null);
             }
            if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationDemandeCarte))
                rechercheDemandesCartesForm.setChoixEtatDemande(choixEtat);
            else if(codOperation.equals(Constants.COD_OPER_OPER_ConsultationCarte))
                rechercheDemandesCartesForm.setChoixEtatCarte(choixEtat);
        }
        
        //extraire les resultats
        if(rechercheDemandesCartesForm.getTypeTraitement().equals("DEMANDES")){
            GetListDemandesCartesCmd getListDemandesCartesCmd = new GetListDemandesCartesCmd(); 
            listes = (Listes) getListDemandesCartesCmd.execute(paramRecherche);
            List listDemandesCarte = new ArrayList();
            listDemandesCarte = listes.getList();
            viewList = setDemandesCartesView(listDemandesCarte);
            rechercheDemandesCartesForm.setListeDemandesCartes(viewList);
        }else{
            GetListCartesBancairesCmd getListCartesBancairesCmd = new GetListCartesBancairesCmd();
            listes = (Listes) getListCartesBancairesCmd.execute(paramRecherche);
            List listCartesBancaire = new ArrayList();
            listCartesBancaire = listes.getList();
            viewList = setCartesBancairesView(listCartesBancaire);
            rechercheDemandesCartesForm.setListeCartesBancaires(viewList);
        }
            
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
        
        return mapping.findForward("success");
   }
    
    public ActionForward chargerDemandeCarte(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        
        String numChoisie = rechercheDemandesCartesForm.getNumChoisie();
        //clear tabsheat demande carte
        rechercheDemandesCartesForm.clearTabDemande();
        DemandeCarte demandeCarte = null;
        CarteBancaire carteBancaire = null;
        ValueObject vo = new ValueObject();
        GetDemandeCarteCmd  getDemandeCarteCmd = new GetDemandeCarteCmd();
        GetCarteBancaireCmd  getCarteBancaireCmd = new GetCarteBancaireCmd();
        
        if(rechercheDemandesCartesForm.getTypeTraitement().equals("DEMANDES")){ 
            //extraire demande
            PrimitiveVO  demCarte  = new PrimitiveVO();
            demCarte.setVString(numChoisie);
            demandeCarte = (DemandeCarte) getDemandeCarteCmd.execute(demCarte);
            if(demandeCarte.getNumCarDcar()!=null){
                PrimitiveVO  carte  = new PrimitiveVO();
                carte.setVString(demandeCarte.getNumCarDcar().toString());
                carteBancaire = (CarteBancaire) getCarteBancaireCmd.execute(carte);
            }
            vo = demandeCarte;
        }else{
            //extraire carte 
             PrimitiveVO  carte  = new PrimitiveVO();
             carte.setVString(numChoisie);
             carteBancaire = (CarteBancaire) getCarteBancaireCmd.execute(carte);
             demandeCarte = carteBancaire.getDemandeCarte();
             vo = carteBancaire;
        }
        
        
        /*#######################Gestion des errors applicatif & technique si cas de :validation, delivrance, demande remplacement, annulation renouvellement, demande modif plafond####################################################*/
        Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        Long codTache = Long.valueOf(rechercheDemandesCartesForm.getCodeTache());
        if (vo.hasError() && ((codOperation.equals(Constants.COD_OPER_OPER_ValidDemande) && codTache.equals(Constants.COD_TACH_TACH_ValidDemande)) || 
                    (codOperation.equals(Constants.COD_OPER_OPER_DelivrCarte) )|| 
                    (codOperation.equals(Constants.COD_OPER_OPER_DemandeRempl) && codTache.equals(Constants.COD_TACH_TACH_DemandeRempl) )||
                    (codOperation.equals(Constants.COD_OPER_OPER_DemandeModifPlafond) && codTache.equals(Constants.COD_TACH_TACH_DemandeModifPlafond) )||
                    (codOperation.equals(Constants.COD_OPER_OPER_AnnulRenouvel) ))){
            List listError = vo.getErrors();
            com.oxia.fwk.core.Error erreur =(com.oxia.fwk.core.Error)listError.get(0);
            String codError = erreur.getCode();
            MotifRejet motifRejet = new MotifRejet();
            //si contrat non valide
            if(codError.equals("Technique")){
                return mapping.findForward("error");
            }else if(codError.equals("ContratInValide")){
                rechercheDemandesCartesForm.setAlertRecherche("ContratInValide"); 
                rechercheDemandesCartesForm.setEtatContrat(demandeCarte.getContratCpt().getCodEtatCcpt());   
                rechercheDemandesCartesForm.setCodMotifRejet(Constants.COD_MOTF_REJET_ContratNonValide.toString());                
            }else if(codError.equals("MandatInvalide")){
                rechercheDemandesCartesForm.setAlertRecherche("MandatInvalide"); 
                rechercheDemandesCartesForm.setCodMotifRejet(Constants.COD_MOTF_REJET_FinMandat.toString());
            }else if(codError.equals("OperationInvalide")){
                rechercheDemandesCartesForm.setAlertRecherche("OperationInvalide"); 
                rechercheDemandesCartesForm.setCodMotifRejet(Constants.COD_MOTF_REJET_PouvoirInsuffisant.toString());
            }else if(codError.equals("MandataireInvalide")){
                rechercheDemandesCartesForm.setAlertRecherche("MandataireInvalide"); 
                rechercheDemandesCartesForm.setCodMotifRejet(Constants.COD_MOTF_REJET_PouvoirInsuffisant.toString());           
            } 
             //Marquage de rejet si rejet delivrance ou demande
            affichageDemandeCartePage(mapping, form, demandeCarte, carteBancaire);
            if(codOperation.equals(Constants.COD_OPER_OPER_DelivrCarte)){
                if(!rejetDelivCarte(form, request))
                    return mapping.findForward("error");
            }else{          
                if(!rejeterDemandeCarte(form, request))
                    return mapping.findForward("error");    
            }
            
        }else{
        /*########################################################################################################*/
            affichageDemandeCartePage(mapping, form, demandeCarte, carteBancaire);
            rechercheDemandesCartesForm.setOpenTabSheetDemande("true");
        }
    
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
       return mapping.findForward("success");
    }
    public void affichageDemandeCartePage(ActionMapping mapping, ActionForm form, DemandeCarte demandeCarte, CarteBancaire carteBancaire){
      
            RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
            if(carteBancaire != null) {
                rechercheDemandesCartesForm.setCarteBancaire(carteBancaire);
            }
            rechercheDemandesCartesForm.setDemandeCarte(demandeCarte);
            //affectation de etat de la demande 
            // rechercheDemandesCartesForm.setCodEtatDcar(demandeCarte.getCodEtatDcar());
            
            //ContratCpt contratCpt = demandeCarte.getContratCpt(); Probleme de proxi
            GetDetailContratCmd getDetailContratCmd = new GetDetailContratCmd();
            ContratCpt contratCpt = (ContratCpt)getDetailContratCmd.execute(demandeCarte.getContratCpt().getContratCptId());
            
            if (contratCpt.getMontSoldCcpt() != null) {
                System.out.println(contratCpt.getMontSoldCcpt().toString());
                rechercheDemandesCartesForm.setMontSoldCcpt(contratCpt.getMontSoldCcpt().toString());
            }      
            rechercheDemandesCartesForm.setDevise(contratCpt.getDevise().getLibDevDev());
            rechercheDemandesCartesForm.setNomIntiCcpt(contratCpt.getNomIntiCcpt());
            rechercheDemandesCartesForm.setCodTpceTpceClient(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce().toString());
            rechercheDemandesCartesForm.setNumPcePersClient(contratCpt.getClient().getPersonne().getNumPcePers());                   
            
            // affectation du type et du categorie de la personne
            rechercheDemandesCartesForm.setTypePersonne(contratCpt.getClient().getTypePers().getCodTperTper());
            rechercheDemandesCartesForm.setCategoriePersonne(contratCpt.getClient().getPersonne().getCategoriePersonne().getCodCatpCatp());
            
            //-----------------------------------------------------------
            //----------- Si demande sur entite co-titulaire
            if (demandeCarte.getCodDemDcar().equals(Constants.COD_DEM_DCAR_Cotitulaire)) { 
                PersonneStrc personneStrc = new PersonneStrc();
                // chercher les personnes cotitulaires
                GetListMembreCotitulaireCmd getListMembreCotitulaire = new GetListMembreCotitulaireCmd();               
                personneStrc.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                personneStrc.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());               

                Listes lisCotitulaire = (Listes)getListMembreCotitulaire.execute(personneStrc);
                rechercheDemandesCartesForm.setListCotitulaires(lisCotitulaire.getList());
                CoTitulaire cotitulaire = (CoTitulaire)lisCotitulaire.getList().get(0);
                rechercheDemandesCartesForm.setNumSeqCliCotitulaire(cotitulaire.getClient().getNumSeqPers().toString());
                rechercheDemandesCartesForm.setTypeCotitulaire(cotitulaire.getCodTcotCoti());
                rechercheDemandesCartesForm.setTypeSignatureCotitulaire(cotitulaire.getCodSigCoti());

                rechercheDemandesCartesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                rechercheDemandesCartesForm.setCotitulaire(demandeCarte.getCoTitulaire());
                //pecDemandeCarteBancaireForm.setNomPrnPersClient(pecDemandeCarteBancaireForm.getContratCpt().getClient().getPersonne().getNomPrnPers());

                rechercheDemandesCartesForm.setMessageTexte(" Le contrat appartient à une entite co-titulaire.");                        
                rechercheDemandesCartesForm.setOpenTabSheetCotitulaire("true");
                      //----------------------------------------------------------------
                      //--------- Cas d'une personne Morale
             } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                        rechercheDemandesCartesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomRsPers());
                        rechercheDemandesCartesForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getLibSiglPers());                        
             } else { // cas d'une personne physique
                        rechercheDemandesCartesForm.setNomNomPersClient(contratCpt.getClient().getPersonne().getNomNomPers());
                        rechercheDemandesCartesForm.setNomPrnPersClient(contratCpt.getClient().getPersonne().getNomPrnPers());                        
             }
             if (contratCpt.getAdresseCorresp() != null) {
                rechercheDemandesCartesForm.setAdresseCorrespondanceClient(contratCpt.getAdresseCorresp().toString());
             }
            // sauvgarde du contrat en question dans la form bean
            rechercheDemandesCartesForm.setContratCpt(contratCpt);
            
            rechercheDemandesCartesForm.setCodPrdPrd(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(),'0',4));
            rechercheDemandesCartesForm.setCodStrcStrc(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(),'0',3));
            rechercheDemandesCartesForm.setNumCcptCcpt(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(),'0',6));
          //  String varCleCompte = Constants.determinerCle(contratCpt.getContratCptId().getCodStrcStrc().toString(),contratCpt.getContratCptId().getCodPrdPrd().toString(),contratCpt.getContratCptId().getNumCcptCcpt().toString());
           // rechercheDemandesCartesForm.setCleCompte(varCleCompte);
            
            //------- recherche du porteur
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(demandeCarte.getCodTpceDcar());
            personneStrc.setNumPcePers(demandeCarte.getNumPceDcar());
            GetPersonneCptCmd getPersonneCptCmd = new GetPersonneCptCmd();
            PersonneCpt personneCpt = (PersonneCpt)getPersonneCptCmd.execute(personneStrc);
            
            rechercheDemandesCartesForm.setDemandeur(personneCpt.getPersonne());
            rechercheDemandesCartesForm.setIdDemandeur(personneCpt.getPersonne().getNumSeqPers().toString());
            
            rechercheDemandesCartesForm.setTypeDemandeur(demandeCarte.getCodDemDcar());  
            rechercheDemandesCartesForm.setCodTpceTpceDemandeur(demandeCarte.getCodTpceDcar().toString());
            rechercheDemandesCartesForm.setNumPcePersDemandeur(demandeCarte.getNumPceDcar());
            
            if (demandeCarte.getCodTpceDcar().equals(Constants.COD_CIN)) { 
                rechercheDemandesCartesForm.setNomNomPersDemandeur(personneCpt.getPersonne().getNomNomPers());
                rechercheDemandesCartesForm.setNomPrnPersDemandeur(personneCpt.getPersonne().getNomPrnPers());
            }else{
                if(demandeCarte.getNumCarDcar() != null) {
                    //recherche du nom de la carte avant migration         
                    String numCarte=demandeCarte.getNumCarDcar().toString();
                    Context context = ContextHandler.getContext();
                    DemandeCarteDAO demandeCarteDAO = 
                        (DemandeCarteDAO)context.getBean("demandeCarteDAO");            
                    rechercheDemandesCartesForm.setNomNomPersDemandeur(demandeCarteDAO.getNomAncCarte(numCarte));
                }
            }
            
            //si mandataire
            if(demandeCarte.getCodDemDcar().equals(Constants.COD_DEM_DCAR_Mandataire)){
                rechercheDemandesCartesForm.setOpenTabSheetMandat("true");
                //extraire mandat objet de la demande
                Context context = ContextHandler.getContext();
                DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
                Long numMandat = demandeCarteDAO.getNumeroMandatParDemande(demandeCarte.getNumDemDcar());
                
                Mandat mandat = new Mandat();
                GetMandatCmd getMandatCmd = new GetMandatCmd();
                mandat.setNumMandMand(numMandat);
                mandat =  (Mandat)getMandatCmd.execute(mandat);
                //remplir mandat view
                MandatView mandatView = new MandatView();
                mandatView.setDateDebut(DateHandler.dateToStr(mandat.getDatDebMand()));
                mandatView.setDateFin(DateHandler.dateToStr(mandat.getDatFinMand()));
                if (mandat.getCodTypMand().equals("S")){
                    mandatView.setType("Spécial");
                   // rechercheDemandesCartesForm.setOpenTabSheetOperation("true");
                }else if (mandat.getCodTypMand().equals("G")) {
                    mandatView.setType("Général");
                    //rechercheDemandesCartesForm.setOpenTabSheetMandat("true");
                }
                mandatView.setMandat(mandat);
                
                List listeDesMandatsView = new ArrayList();
                listeDesMandatsView.add(mandatView);
                rechercheDemandesCartesForm.setListMandatsConcernesPersonne(listeDesMandatsView);
                if(listeDesMandatsView.size()>0) 
                    rechercheDemandesCartesForm.setOpenTabSheetOperation("true");
                if(mandat.getNumDemMand()!=null){
                    rechercheDemandesCartesForm.setRefMand(mandat.getNumDemMand().toString());
                }
                if(mandat.getCodSignMand() != null){
                    rechercheDemandesCartesForm.setSignatureMandatChoisi(mandat.getCodSignMand());
                }
                if(mandat.getNbrMinMand()!=null){
                    rechercheDemandesCartesForm.setNbrMinMandatChoisi(mandat.getNbrMinMand().toString());
                }
                rechercheDemandesCartesForm.setTypeMandatChoisi(mandat.getCodTypMand());
                GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
                DetailMandat detailMandat = (DetailMandat)getDetailMandatCmd.execute(mandat);
                
               
                //---------------Affichage du text qui specifie le mandat---------------------------------//
                if(mandat.getCodTypMand().equals("S")){
                    if (detailMandat.getListeMandatOperations() != null && detailMandat.getListeMandatOperations().size()>0) {            
                        
                        for (Iterator it = detailMandat.getListeMandatOperations().iterator(); it.hasNext(); ) {               
                            MandatOperation mandatOperation = (MandatOperation)it.next();
                            if(mandatOperation.getOperation().getCodOperOper().equals(Constants.COD_OPER_OPER_PECDemandeCarte)){
                                rechercheDemandesCartesForm.setNumOperation(mandatOperation.getMandatOperationId().getNumMaopMaop().toString());
                                rechercheDemandesCartesForm.setSignatureMandatChoisi(mandatOperation.getCodSignMaop());
                                if(mandatOperation.getNbrMinMaop()!= null){
                                    rechercheDemandesCartesForm.setNbrMinMandatChoisi(mandatOperation.getNbrMinMaop().toString()); 
                                }
                            }                    
                         }
                    } // Fin For  
                }
                
                if(mandat.getCodTypMand().equals("G")){
                    // mandat général 
                     rechercheDemandesCartesForm.setMessageTexte(" dossier Mandat choisi : " +  mandat.getNumDemMand()+", type : " + mandat.getCodTypMand() +" , Signature : " + mandat.getCodSignMand());  
                }else{
                    // mandat spécial ou juridique
                     rechercheDemandesCartesForm.setMessageTexte(" dossier Mandat choisi : " +  mandat.getNumDemMand()+", type : " + mandat.getCodTypMand() +" , Signature : " + rechercheDemandesCartesForm.getSignatureMandatChoisi() + " (selon opération appropriée)");  
                }
                //----------------------------------------------------------------------------------------//    

                 List list = new ArrayList();
                 list.addAll(demandeCarte.getDemandeCarteMandatPersonnes());
                 rechercheDemandesCartesForm.setListMandatsPersonneConcernesDemandeur(list);
                 rechercheDemandesCartesForm.setNbreSignataires(new Integer(list.size()).toString());
                 rechercheDemandesCartesForm.setOpenTabSheetMandat("true");
            }   
            //remplir type carte
            GetCartesEligibleContratCmd getCartesEligibleContratCmd = new GetCartesEligibleContratCmd();
            Listes listes = (Listes) getCartesEligibleContratCmd.execute(rechercheDemandesCartesForm.getContratCpt());
            List l = listes.getList();
            rechercheDemandesCartesForm.setListeTypeCarte(l);
            rechercheDemandesCartesForm.setCodTcarTcar(demandeCarte.getTypeCarte().getCodTcarTcar().toString());
            
            if(demandeCarte.getCodAutDcar() != null){
                rechercheDemandesCartesForm.setCodAutDcar(demandeCarte.getCodAutDcar());
                rechercheDemandesCartesForm.setAvecAutorisation("1");
            }
            if(demandeCarte.getBoolSalDcar() != null ){
                if(demandeCarte.getBoolSalDcar().intValue()==1){
                    rechercheDemandesCartesForm.setSalarie("1");
                }else {
                    rechercheDemandesCartesForm.setSalarie("0");
                }
            }
            if(demandeCarte.getBoolDomsDcar() != null){
                if(demandeCarte.getBoolDomsDcar().intValue()==1){
                    rechercheDemandesCartesForm.setDomicilie("1");
                }else {
                    rechercheDemandesCartesForm.setDomicilie("0");
                }
            }
            
            Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
            Long codTache = Long.valueOf(rechercheDemandesCartesForm.getCodeTache());
            if(demandeCarte.getMontSalDcar() != null )
                rechercheDemandesCartesForm.setMontSalDcar(demandeCarte.getMontSalDcar().toString());
            if(demandeCarte.getMontDachDcar() != null && !codOperation.equals(Constants.COD_OPER_OPER_DemandeModifPlafond) )
                rechercheDemandesCartesForm.setMontDachDcar(demandeCarte.getMontDachDcar().toString());
            if(demandeCarte.getMontDretDcar() != null && !codOperation.equals(Constants.COD_OPER_OPER_DemandeModifPlafond) )
                rechercheDemandesCartesForm.setMontDretDcar(demandeCarte.getMontDretDcar().toString());    
            if(demandeCarte.getMontPretDcar() != null)
                rechercheDemandesCartesForm.setMontPlafRet(demandeCarte.getMontPretDcar().toString());
            if(demandeCarte.getMontPachDcar() != null)
                rechercheDemandesCartesForm.setMontPlafAch(demandeCarte.getMontPachDcar().toString()); 
            if(demandeCarte.getCodNgrnDcar() != null){
                rechercheDemandesCartesForm.setNatureGarantie(demandeCarte.getCodNgrnDcar().toString());
                //affecter au champ constitué : non si etat de la demande est attente,attenteDQMRP,attenteGarantie,prevaliderDR,attenteScm,PrevaliderScm,attenteScc,PrevaliderScc,rejetdemande,rejetDqmrp 
                if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_Attente) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_AttenteDR)|| demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_AttenteGarantie)
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_PrevaliderDR) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_AttenteScm) 
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_PrevaliderScm) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_AttenteScc) 
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_PrevaliderScc)    
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetDemande) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetDemande)
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetDr)
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetScm)
                || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetScc)){
                    rechercheDemandesCartesForm.setConstitue("N"); 
                }else{
                    rechercheDemandesCartesForm.setConstitue("O");
                }
            }
            
            //affectation si carte avec plafond (bollPlafTcar)
             rechercheDemandesCartesForm.setBoolPlafTcar(demandeCarte.getTypeCarte().getBoolPlafTcar().toString());
           //affectation si demande modification plafond (bollModpDcar)
            if(demandeCarte.getBoolModpDcar() != null){
                rechercheDemandesCartesForm.setBoolModpDcar(demandeCarte.getBoolModpDcar().toString());
            }else{
                rechercheDemandesCartesForm.setBoolModpDcar("0");
            }
            //affectation de type opération sur la carte (codOperTcar)
             rechercheDemandesCartesForm.setCodOperTcar(demandeCarte.getTypeCarte().getCodOperTcar());
            
            //affectation etat demande ou carte, date derniere operation, num carte et date fin carte (si carte delivrée)
             Context context = ContextHandler.getContext();
             DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
            // si validation demande, Prévalidation, ConsultationDemandeCarte : afficher etat && libetat && numCarte pour demande carte 
            if(rechercheDemandesCartesForm.getTypeTraitement().equals("DEMANDES")){
             //affectation de etat de la demande 
             rechercheDemandesCartesForm.setCodEtatDcar(demandeCarte.getCodEtatDcar());
             rechercheDemandesCartesForm.setDateDernOperation(DateHandler.dateToStr(demandeCarteDAO.getDateDernièreOperationDemande(demandeCarte.getNumDemDcar())));
             DemandeCarteView demandeCarteview = new DemandeCarteView();
             demandeCarteview.setDemandeCarte(demandeCarte);
             rechercheDemandesCartesForm.setLibEtat(demandeCarteview.getEtatDemandeCarte());
             //affectation numéro carte si il existe
             if(demandeCarte.getNumCarDcar()!=null){
                 rechercheDemandesCartesForm.setNumCarte(demandeCarte.getNumCarDcar().toString()); 
                 rechercheDemandesCartesForm.setDateFinCarte(DateHandler.dateToStr(carteBancaire.getDatFinCarb()));
                 rechercheDemandesCartesForm.setPlafondRetrait(carteBancaire.getMontPretCarb().toString());
                 rechercheDemandesCartesForm.setPlafondAchat(carteBancaire.getMontPachCarb().toString());
             }
             
             //si cas de modif plafond extraire caracteristique carte bancaire
             if(demandeCarte.getBoolModpDcar() != null && demandeCarte.getBoolModpDcar().equals(Long.valueOf("1"))){
                 //recherche de la carte ancienne         
                 PrimitiveVO voCarte = new PrimitiveVO();
                 voCarte.setVString(demandeCarte.getNumCarDcar().toString());
                 GetCarteBancaireCmd getCarteBancaireCmd = new GetCarteBancaireCmd();
                 CarteBancaire carteBancaireOld = (CarteBancaire)getCarteBancaireCmd.execute(voCarte);
                 if(carteBancaireOld!=null && carteBancaireOld.getDatFinCarb()!=null){
                        rechercheDemandesCartesForm.setDateFinCarte(DateHandler.dateToStr(carteBancaireOld.getDatFinCarb()));    
                 }
                 if(carteBancaireOld!=null && carteBancaireOld.getMontPachCarb()!=null){
                           rechercheDemandesCartesForm.setPlafondAchat(carteBancaireOld.getMontPachCarb().toString());    
                 }
                 if(carteBancaireOld!=null && carteBancaireOld.getMontPretCarb()!=null){
                              rechercheDemandesCartesForm.setPlafondRetrait(carteBancaireOld.getMontPretCarb().toString());    
                 }
                 //affectation du acteur modif carte si le cas
                  rechercheDemandesCartesForm.setNomModfDcar(demandeCarte.getNomModpDcar());   
             }
            }else{
                //affectation de etat de la carte
                rechercheDemandesCartesForm.setCodEtatDcar(carteBancaire.getCodEtatCarb());
                String numCarteBancaire=carteBancaire.getCarteBancaireId().getCodBinTcar().toString()+StrHandler.lpad(carteBancaire.getCarteBancaireId().getNumCarbCarb().toString(),'0',10);
                rechercheDemandesCartesForm.setDateDernOperation(DateHandler.dateToStr(demandeCarteDAO.getDateDernièreOperationCarte(numCarteBancaire)));  
                CarteBancaireView carteBancaireView = new CarteBancaireView();
                carteBancaireView.setCarteBancaire(carteBancaire);
                rechercheDemandesCartesForm.setLibEtat(carteBancaireView.getEtatCarteBancaire());
                rechercheDemandesCartesForm.setNumCarte(numCarteBancaire);
                rechercheDemandesCartesForm.setDateFinCarte(DateHandler.dateToStr(carteBancaire.getDatFinCarb()));
                if(carteBancaire.getMontPretCarb()!=null){
                    rechercheDemandesCartesForm.setMontPlafRet(carteBancaire.getMontPretCarb().toString());
                    rechercheDemandesCartesForm.setPlafondRetrait(carteBancaire.getMontPretCarb().toString());
                }
                if(carteBancaire.getMontPachCarb()!=null){
                    rechercheDemandesCartesForm.setMontPlafAch(carteBancaire.getMontPachCarb().toString());
                    rechercheDemandesCartesForm.setPlafondAchat(carteBancaire.getMontPachCarb().toString());
                }
            }
            //affectation du motif rejet si rejet demande ou rejet remplacement
            if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetDemande) ||  demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetRemplacement))
                rechercheDemandesCartesForm.setCodMotifRejet(demandeCarte.getMotifRejet().getCodMotfMrej().toString());
           
           //affectation du motif de remplacement si cod etat demande est : demande remplacement, ou demande remplacement validée, ou demande remplacement rejetée
           if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRempl) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRemplValide) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_RejetRemplacement) || demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_CarteRemplacee) ){
               rechercheDemandesCartesForm.setLibRempCarb(demandeCarte.getLibRempDcar());   
               /*if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_CarteRemplacee)){     
                   rechercheDemandesCartesForm.setNumCarteRemplace(demandeCarteDAO.getNewCarteRemplace(rechercheDemandesCartesForm.getNumCarte()));
                   rechercheDemandesCartesForm.setDateFinCarteRemp(DateHandler.dateToStr(carteBancaire.getCarteBancaire().getDatFinCarb()));
               }*/
           }
           if(carteBancaire != null && carteBancaire.getCodEtatCarb().equals(Constants.COD_ETAT_CARB_CarteRemplacee)){ 
               rechercheDemandesCartesForm.setLibRempCarb(carteBancaire.getLibRempCarb());   
               rechercheDemandesCartesForm.setNumCarteRemplace(carteBancaire.getCarteBancaire().getCarteBancaireId().getCodBinTcar().toString()+StrHandler.lpad(carteBancaire.getCarteBancaire().getCarteBancaireId().getNumCarbCarb().toString(),'0',10));     
               rechercheDemandesCartesForm.setDateFinCarteRemp(DateHandler.dateToStr(carteBancaire.getCarteBancaire().getDatFinCarb()));
           }
           
           //affectation du motif annulation de renouvellement si le cas
            if(carteBancaire != null && carteBancaire.getBoolAnnlCarb() != null && carteBancaire.getBoolAnnlCarb().equals(Constants.BOOL_ANNL_CARB_OUI)){
                rechercheDemandesCartesForm.setNomAnnlCarb(carteBancaire.getNomAnnlCarb());   
            }
            
            String duree = demandeCarteDAO.getDureeCarte(rechercheDemandesCartesForm.getCodTcarTcar());
            rechercheDemandesCartesForm.setDureeCarte(duree);
           
   
    }
    /*private void remplirMotifRemplacement(RechercheDemandesCartesForm rechercheDemandesCartesForm,DemandeCarte demandeCarte){
        String motifRempl = demandeCarte.getLibRempDcar();
        if(motifRempl.equals("OUBLIE"))
        
    }*/
    public ActionForward validerDemandeCarte(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        Personnel per = demandeCarte.getPersonnel();
        /// Cas de validation demande
       // if(codOperation.equals(Constants.COD_OPER_OPER_ValidDemande)){ 
       String numM=per.getNumMatrUser();
       per.setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
            demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
            demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ValidDemande);
            demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ValidDemande);
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_Valider);
            demandeCarte.setDatEnvDcar(DateHandler.timeJour());
            //remplir plafond retrait accordé si le cas 
            if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
                demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
            }
            //remplir plafond achat accordé si le cas 
            if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
                demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
            }
            //remplir garantie si le cas
            if(rechercheDemandesCartesForm.getConstitue().equals("O")){
                 demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
            }
            
            ValidationDemandeCarteCmd validationDemandeCarteCmd = new ValidationDemandeCarteCmd();
            demandeCarte = (DemandeCarte) validationDemandeCarteCmd.execute(demandeCarte);
            
        
      //  }
    
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
       //return mapping.findForward("indexSMILE");

        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward validerDemandeRemplaceCarte(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
            RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
            DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
            Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        
            demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
            demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ValidDemandeRempl);
            demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ValidDemandeRempl);
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_DemandeRemplValide);
            demandeCarte.setDatVrcaDcar(DateHandler.timeJour());
            
            ValidationDemandeCarteCmd validationDemandeCarteCmd = new ValidationDemandeCarteCmd();
            demandeCarte = (DemandeCarte) validationDemandeCarteCmd.execute(demandeCarte);
            
    
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    
    
    public ActionForward rejeterDemandeCarte(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
            rejeterDemandeCarte(form, request);
            return rechercherDemandesSelonChoix(mapping, form, request, response);
    
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

    private boolean rejeterDemandeCarte(ActionForm form, HttpServletRequest request) {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        Long codOperation = Long.valueOf(rechercheDemandesCartesForm.getCodeOperation());
        Long codTache = Long.valueOf(rechercheDemandesCartesForm.getCodeTache());
        MotifRejet motifRejet = new MotifRejet();
        /// Cas de validation 
        if(codOperation.equals(Constants.COD_OPER_OPER_ValidDemande) && codTache.equals(Constants.COD_TACH_TACH_ValidDemande)){ 
            demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
            demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ValidDemande);
            demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ValidDemande);
            //si demande de remplacement rejet remplacement si non rejet demande
            if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRempl)){
                demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetRemplacement);
            }else{
                demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetDemande);
            } 
            
        /// Cas reception carte 
        }else if(codOperation.equals(Constants.COD_OPER_OPER_ReceptCarte)){ 
                demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
                demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ReceptCarte);
                demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ReceptCarte);
                //si demande de remplacement rejet remplacement si non rejet demande
                if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRempl)){
                    demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetRemplacement);
                }else{
                    demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetDemande);
                }        
        /// Cas de prévalidation DR     
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidDR) && codTache.equals(Constants.COD_TACH_TACH_PrevalidDR)){ 
            demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidDR);
            demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidDR);
            demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetDr); 
        /// Cas de prévalidation SCM     
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidScm) && codTache.equals(Constants.COD_TACH_TACH_PrevalidScm)){ 
                demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidScm);
                demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidScm);
                demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetScm); 
        /// Cas de prévalidation SCC     
        }else if(codOperation.equals(Constants.COD_OPER_OPER_PrevalidScc) && codTache.equals(Constants.COD_TACH_TACH_PrevalidScc)){ 
                    demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidScc);
                    demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidScc);
                    demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_RejetScc); 
        }
        motifRejet.setCodMotfMrej(Long.valueOf(rechercheDemandesCartesForm.getCodMotifRejet()));
        demandeCarte.setMotifRejet(motifRejet);
        
        RejetDemandeCarteCmd rejetDemandeCarteCmd = new RejetDemandeCarteCmd();
        demandeCarte = (DemandeCarte) rejetDemandeCarteCmd.execute(demandeCarte);
       
        return true;
    }
    
    public ActionForward attenteGarantie(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ValidDemande);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ValidDemande);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_AttenteGarantie);
        
         //remplir plafond retrait accordé par CA si le cas 
         if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
             demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
         }
         //remplir plafond achat accordé par CA si le cas 
         if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
             demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
         }
         
        demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));    
       
        MiseAttenteDemandeCarteCmd miseAttenteDemandeCarteCmd = new MiseAttenteDemandeCarteCmd();
        demandeCarte = (DemandeCarte) miseAttenteDemandeCarteCmd.execute(demandeCarte);
         

    
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
       //return mapping.findForward("indexSMILE");
       
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward envoiDr(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ValidDemande);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ValidDemande);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_AttenteDR);
 
        //remplir plafond retrait accordé par CA si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par CA si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par CA si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatEnvpDcar(DateHandler.timeJour());
        
        EnvoiDrDqmrpCmd envoiDrDqmrpCmd = new EnvoiDrDqmrpCmd();
        demandeCarte = (DemandeCarte) envoiDrDqmrpCmd.execute(demandeCarte);
        

    
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
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
 }
 
    public ActionForward envoiScm(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidDR);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidDR);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_AttenteScm);
    
        //remplir plafond retrait accordé par SCM si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par SCM si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par SCM si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatEscmDcar(DateHandler.timeJour());
        
        EnvoiScmCmd envoiScmCmd = new EnvoiScmCmd();
        demandeCarte = (DemandeCarte) envoiScmCmd.execute(demandeCarte);
        

    
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
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward envoiScc(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidScm);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidScm);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_AttenteScc);
    
        //remplir plafond retrait accordé par SCC si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par SCC si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par SCC si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatEsccDcar(DateHandler.timeJour());
        
        EnvoiDrDqmrpCmd envoiDrDqmrpCmd = new EnvoiDrDqmrpCmd();
        demandeCarte = (DemandeCarte) envoiDrDqmrpCmd.execute(demandeCarte);
        

    
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
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward preValiderDr(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidDR);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidDR);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_PrevaliderDR);
    
        //remplir plafond retrait accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par CA si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatPrevDcar(DateHandler.timeJour());
        
        PecDrDqmrpCmd pecDrDqmrpCmd = new PecDrDqmrpCmd();
        demandeCarte = (DemandeCarte) pecDrDqmrpCmd.execute(demandeCarte);
        

    
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
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward preValiderScm(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidScm);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidScm);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_PrevaliderScm);
    
        //remplir plafond retrait accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par CA si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatPscmDcar(DateHandler.timeJour());
        
        PecScmCmd pecScmCmd = new PecScmCmd();
        demandeCarte = (DemandeCarte) pecScmCmd.execute(demandeCarte);
        

    
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
    
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward preValiderScc(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_PrevalidScc);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_PrevalidScc);
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_PrevaliderScc);
    
        //remplir plafond retrait accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafRet().equals("")){
            demandeCarte.setMontPretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafRet()));
        }
        //remplir plafond achat accordé par DR/DQMRP si le cas 
        if(!rechercheDemandesCartesForm.getMontPlafAch().equals("")){
            demandeCarte.setMontPachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontPlafAch()));
        }
        //remplir nature garantie à constitué proposé  par CA si le cas
        if(!rechercheDemandesCartesForm.getNatureGarantie().equals("0")){
             demandeCarte.setCodNgrnDcar(Long.valueOf(rechercheDemandesCartesForm.getNatureGarantie()));
        }
       
        demandeCarte.setDatPsccDcar(DateHandler.timeJour());
        
        PecSccCmd pecSccCmd = new PecSccCmd();
        demandeCarte = (DemandeCarte) pecSccCmd.execute(demandeCarte);
        

    
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
    
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward receptionCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
       
        
        
        Context context = ContextHandler.getContext();
        //// si cas de demande de  modification plafond : notification modification
        DemandeCarte demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
       /* if(demandeCarte.getBoolModpDcar() != null && demandeCarte.getBoolModpDcar().equals(Long.valueOf("1"))){
             //recherche de la carte ancienne         
             PrimitiveVO voCarte = new PrimitiveVO();
             voCarte.setVString(demandeCarte.getNumCarDcar().toString());
             GetCarteBancaireTrt getCarteBancaireTrt = new GetCarteBancaireTrt();
             CarteBancaire carteBancaireOld = (CarteBancaire)getCarteBancaireTrt.exec(voCarte);
             //modif plafond
             carteBancaireOld.setMontPretCarb(demandeCarte.getMontPretDcar());
             carteBancaireOld.setMontPachCarb(demandeCarte.getMontPachDcar());  
             
             // envoi des information pour insertion dans la table historique
             demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DemandeModifPlafond);
             demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DemandeModifPlafond);
             demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
             
             demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_ModifPlafondRealise);
             
             // liée la carte avec la nouvelle demande
             carteBancaireOld.setDemandeCarte(demandeCarte);
             
             ModifPlafondCmd modifPlafondCmd = new ModifPlafondCmd();
             carteBancaireOld = (CarteBancaire) modifPlafondCmd.execute(carteBancaireOld);
             //crudService.update(carteBancaireOld);
         }else{*/
            ///Cas de reception carte et reception carte remplacées
            String numCart = "";
            Date datFin = null;
            if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRemplValide)){
                numCart = rechercheDemandesCartesForm.getNumCarteRemplace();
                datFin = DateHandler.strToDate(rechercheDemandesCartesForm.getDateFinCarteRemp());
            }else{
                numCart = rechercheDemandesCartesForm.getNumCarte();     
                datFin = DateHandler.strToDate(rechercheDemandesCartesForm.getDateFinCarte());
            }
           
            //verification du numero carte à receptionner (si type carte valide ou non)
            DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
            String etat = demandeCarteDAO.getEtatTypeCarte(numCart.substring(0, 6));
            String typeCarteDemander = rechercheDemandesCartesForm.getCodTcarTcar();
            String etatAn = demandeCarteDAO.getEtatTypeCarte(typeCarteDemander);
           // Date dateCre = demandeCarteDAO.getDateCreationNewCarte(numCart);
            
            ///code temporaire suite pec carte crée depuis monetique
            Date dateCre = null;
            if(numCart.substring(0, 6).equals("539995") ){
             dateCre = demandeCarteDAO.getDateCarte(numCart);
            }else{
                 dateCre = demandeCarteDAO.getDateCreationNewCarte(numCart);
            }
            if(etat==null){    
                rechercheDemandesCartesForm.setAlert("Le format du numéro de la carte est incorrecte. Veuillez verifier votre saisie."); 
                return mapping.findForward("success");
            }else if(etat.equals("V") &&  etatAn.equals("V")&& !typeCarteDemander.equals(numCart.substring(0, 6))){
                rechercheDemandesCartesForm.setAlert("Le numéro de la carte est invalide. Veuillez verifier votre saisie."); 
                return mapping.findForward("success");
            }else if(dateCre==null){
                rechercheDemandesCartesForm.setAlert("Cette carte n'existe pas. Veuillez verifier votre saisie."); 
                return mapping.findForward("success");    
            }else{  
            
            
                //extraire Carte crée
                ISearchEngine searchEngine = 
                    (SearchEngine)context.getBean("searchEngine");
                
                CarteBancaireId carteBancaireId = new CarteBancaireId();
                carteBancaireId.setCodBinTcar(Long.valueOf(numCart.substring(0,6)));
                carteBancaireId.setNumCarbCarb(Long.valueOf(numCart.substring(6)));
                CarteBancaire carteBancaire = (CarteBancaire)searchEngine.get(CarteBancaire.class, carteBancaireId);
                
                //verif du numero du compte de la demande et de la carte
                if(!carteBancaire.getContratCpt().getContratCptId().equals(demandeCarte.getContratCpt().getContratCptId())){
                    rechercheDemandesCartesForm.setAlert("Cette carte est non affectée a cette demande. Veuillez verifier votre saisie."); 
                    return mapping.findForward("success");    
                }
                
                //verif du date fin validité carte
                if(!DateHandler.dateToStr(datFin).equals(DateHandler.dateToStr(carteBancaire.getDatFinCarb()))){
                    rechercheDemandesCartesForm.setAlert("La date fin de validité est incorrecte. Veuillez verifier votre saisie."); 
                    return mapping.findForward("success");    
                }
                
                carteBancaire = modifierCarteBancaire(rechercheDemandesCartesForm, context, carteBancaire);
                
                
                //creattion de la carte bancaire
               //// CarteBancaire carteBancaire = creerCarteBancaire(rechercheDemandesCartesForm, numCart, datFin);  
                // si reception pour carte mal confectionnée
                if(!rechercheDemandesCartesForm.getMalConfectRecept().equals("")){
                    carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteMalConfect);
                }
                // envoi des information pour insertion dans la table historique
                carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_ReceptCarte);
                carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_ReceptCarte);
                carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
                
                
                ReceptionCarteBancaireCmd receptionCarteBancaireCmd = new ReceptionCarteBancaireCmd();
                carteBancaire = (CarteBancaire) receptionCarteBancaireCmd.execute(carteBancaire);
               
            }
         //}
        
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }

    private CarteBancaire modifierCarteBancaire(RechercheDemandesCartesForm rechercheDemandesCartesForm, 
                                                Context context, 
                                                CarteBancaire carteBancaire) {
        
        
        carteBancaire.setDemandeCarte(rechercheDemandesCartesForm.getDemandeCarte());
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRecu);
        
        carteBancaire.setCodTpceCarb(Long.valueOf(rechercheDemandesCartesForm.getCodTpceTpceDemandeur()));
        carteBancaire.setNumPceCarb(rechercheDemandesCartesForm.getNumPcePersDemandeur());
        return carteBancaire;
    }

    private CarteBancaire creerCarteBancaire(RechercheDemandesCartesForm rechercheDemandesCartesForm, 
                                               String numCart, Date datFin) {
        CarteBancaire carteBancaire = new CarteBancaire();
        carteBancaire.setDemandeCarte(rechercheDemandesCartesForm.getDemandeCarte());
        
        CarteBancaireId carteBancaireId = new CarteBancaireId();
        carteBancaireId.setCodBinTcar(Long.valueOf(numCart.substring(0, 6)));
        carteBancaireId.setNumCarbCarb(Long.valueOf(numCart.substring(6)));
        carteBancaire.setCarteBancaireId(carteBancaireId);
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRecu);
        carteBancaire.setDatCreCarb(DateHandler.timeJour());
        carteBancaire.setDatFinCarb(datFin);
        //setDateFinCarte(carteBancaire,duree);
        carteBancaire.setCodTpceCarb(Long.valueOf(rechercheDemandesCartesForm.getCodTpceTpceDemandeur()));
        carteBancaire.setNumPceCarb(rechercheDemandesCartesForm.getNumPcePersDemandeur());
        carteBancaire.setContratCpt(rechercheDemandesCartesForm.getContratCpt());
        carteBancaire.setDatOperCarb(DateHandler.timeJour());
        carteBancaire.setMontPachCarb(rechercheDemandesCartesForm.getDemandeCarte().getMontPachDcar());
        carteBancaire.setMontPretCarb(rechercheDemandesCartesForm.getDemandeCarte().getMontPretDcar());
        return carteBancaire;
    }

    private void setDateFinCarte(CarteBancaire carteBancaire,String duree) {
        
        //carteBancaire.setDatFinCarb(DateHandler.timeJour());
        carteBancaire.setDatFinCarb(DateHandler.addMonth(DateHandler.timeJour(),Long.valueOf(duree).intValue()*12));
    }
    
    public ActionForward carteMalConfectionne(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
            
        // envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DelivrCarte);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DelivrCarte);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteMalConfect);
        carteBancaire.setDatOperCarb(new Date());
        
        RejetDelivCarteCmd rejetDelivCarteCmd = new RejetDelivCarteCmd();
        carteBancaire = (CarteBancaire) rejetDelivCarteCmd.execute(carteBancaire);
        
        
        
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public boolean rejetDelivCarte(ActionForm form, HttpServletRequest request) throws IOException, 
                                                               ServletException {

        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
        // envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DelivrCarte);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DelivrCarte);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_RejetDelivreCarte);
        carteBancaire.setDatOperCarb(new Date());
        
        RejetDelivCarteCmd rejetDelivCarteCmd = new RejetDelivCarteCmd();
        carteBancaire = (CarteBancaire) rejetDelivCarteCmd.execute(carteBancaire);
        return true;
        
    }
    
    public ActionForward delivranceCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
        // envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DelivrCarte);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DelivrCarte);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRemise);
        carteBancaire.setDatOperCarb(DateHandler.timeJour());
        
        DelivranceCarteBancaireCmd delivranceCarteBancaireCmd = new DelivranceCarteBancaireCmd();
        carteBancaire = (CarteBancaire) delivranceCarteBancaireCmd.execute(carteBancaire);
        ActionMessages actionMessages = new ActionMessages();
        if (carteBancaire == null || carteBancaire.hasError()) {
               List listErreur = carteBancaire.getErrors();
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
        ActionMessages actionMessages = new ActionMessages();
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              e.getMessage());
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        logger.error("Exception : ",e);
        return mapping.findForward("error");

    }       
       //return mapping.findForward("indexSMILE");
       return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
   
    
    public ActionForward annulRenouvCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
        //envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_AnnulRenouvel);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_AnnulRenouvel);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        carteBancaire.setBoolAnnlCarb(Constants.BOOL_ANNL_CARB_OUI);
        carteBancaire.setNomAnnlCarb(rechercheDemandesCartesForm.getNomAnnlCarb());
        carteBancaire.setDatOperCarb(DateHandler.timeJour());
        
        AnnulRenouvCarteBancaireCmd annulRenouvCarteBancaireCmd = new AnnulRenouvCarteBancaireCmd();
        carteBancaire = (CarteBancaire)annulRenouvCarteBancaireCmd.execute(carteBancaire);
        ActionMessages actionMessages = new ActionMessages();
        if (carteBancaire == null || carteBancaire.hasError()) {
               List listErreur = carteBancaire.getErrors();
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
        ActionMessages actionMessages = new ActionMessages();
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              e.getMessage());
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        logger.error("Exception : ",e);
        return mapping.findForward("error");

    }       
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward demandeRemplaceCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte  demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        //test si il existe une demande en cours pour cette carte
        Context context = ContextHandler.getContext();
        DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
        Date dateRemp = demandeCarteDAO.getDateDemandeRemplacement(demandeCarte.getNumCarDcar().toString());
        if(dateRemp!=null){
            rechercheDemandesCartesForm.setAlertRecherche("DemandeRemplacementEnCours"); 
            rechercheDemandesCartesForm.setMessageAlertRecherche("Une demande de remplacement crée le "+DateHandler.dateToStr(dateRemp)+" est déja en cours de traitement."); 
            return mapping.findForward("success");    
        }
        
        demandeCarte.setLibRempDcar(rechercheDemandesCartesForm.getLibRempCarb());
        demandeCarte.setDatDemDcar(DateHandler.timeJour());
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_DemandeRempl);
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DemandeRempl);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DemandeRempl);
        
        //mise à null les dat de la demande mère
        demandeCarte.setDatPrevDcar(null);
        demandeCarte.setDatPscmDcar(null);
        demandeCarte.setDatPsccDcar(null);
        demandeCarte.setDatEnvDcar(null);
        demandeCarte.setDatRecpDcar(null);
        demandeCarte.setDatRemiDcar(null);
        demandeCarte.setDatEnvpDcar(null);
          
        DemandeRemplacementCarteCmd demandeRemplacementCarteCmd = new DemandeRemplacementCarteCmd();
        demandeCarte = (DemandeCarte) demandeRemplacementCarteCmd.execute(demandeCarte);
        
        
        
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }

    public ActionForward demandeModifPlafond(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {
        ActionMessages actionMessages = new ActionMessages(); 
    try { 
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte  demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        //test si il existe une demande en cours pour cette carte
        Context context = ContextHandler.getContext();
        DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
        Date dateModif = demandeCarteDAO.getDateDemandeModifPlafond(demandeCarte.getNumCarDcar().toString());
        if(dateModif!=null){
            rechercheDemandesCartesForm.setAlertRecherche("DemandeModificationEnCours"); 
            rechercheDemandesCartesForm.setMessageAlertRecherche("Une demande de modification crée le "+DateHandler.dateToStr(dateModif)+" est déja en cours de traitement."); 
            return mapping.findForward("success");    
        }
        
        demandeCarte.setMontDretDcar(Long.valueOf(rechercheDemandesCartesForm.getMontDretDcar()));
        demandeCarte.setMontDachDcar(Long.valueOf(rechercheDemandesCartesForm.getMontDachDcar()));
        demandeCarte.setMontSalDcar(Long.valueOf(rechercheDemandesCartesForm.getMontSalDcar()));
        
        if(rechercheDemandesCartesForm.getSalarie()!=null){
            demandeCarte.setBoolSalDcar(Long.valueOf(rechercheDemandesCartesForm.getSalarie()));
        }
        
        demandeCarte.setNomModpDcar(rechercheDemandesCartesForm.getNomModfDcar());
        demandeCarte.setBoolModpDcar(Long.valueOf("1"));
        if(rechercheDemandesCartesForm.getDomicilie()!=null){
            demandeCarte.setBoolDomsDcar(Long.valueOf(rechercheDemandesCartesForm.getDomicilie()));
        }
        demandeCarte.setDatDemDcar(DateHandler.timeJour());
        demandeCarte.setCodEtatDcar(Constants.COD_ETAT_DCAR_DemandeModifPlafond);
        
        demandeCarte.getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        demandeCarte.getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DemandeModifPlafond);
        demandeCarte.getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DemandeModifPlafond);
        
        //mise à null les dat de la demande mère
        demandeCarte.setDatPrevDcar(null);
        demandeCarte.setDatPscmDcar(null);
        demandeCarte.setDatPsccDcar(null);
        demandeCarte.setDatEnvDcar(null);
        demandeCarte.setDatRecpDcar(null);
        demandeCarte.setDatRemiDcar(null);
        demandeCarte.setDatEnvpDcar(null);
          
        DemandeModifPlafondCmd demandeModifPlafondCmd = new DemandeModifPlafondCmd();
        demandeCarte = (DemandeCarte) demandeModifPlafondCmd.execute(demandeCarte);
        
        StringBuffer message1 = new StringBuffer(
               "Une demande de modification plafond sur la carte N° "+demandeCarte.getNumCarDcar().toString()+" a été crée avec succès et en attente de validation.");
        ActionMessage actionMessage = new ActionMessage("exception.generique",message1.toString());
        actionMessages.add("Msg_validation ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("confirmationGenerale");
        
    } catch (Exception e) {
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique", 
                              e.getMessage());
        actionMessages.add("Erreur ", actionMessage);   
        this.saveMessages(request, actionMessages);
        logger.error("Exception : ",e);
        return mapping.findForward("error");

    }       
       
        
       
    }


 /*   public ActionForward remplacementCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        DemandeCarte  demandeCarte = rechercheDemandesCartesForm.getDemandeCarte();
        String newCarte = rechercheDemandesCartesForm.getNumCarteRemplace();
        //test qu'une nouvelle carte est saisie
         Context context = ContextHandler.getContext();
         DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
         Date dateCre = demandeCarteDAO.getDateCreationNewCarte(newCarte);
         if(dateCre==null){
             rechercheDemandesCartesForm.setAlertRecherche("RemplacementNewCarteNonExistant"); 
             return mapping.findForward("success");    
         }
         //recherche de la carte ancienne         
         PrimitiveVO voCarte = new PrimitiveVO();
         voCarte.setVString(demandeCarte.getNumCarDcar().toString());
         GetCarteBancaireCmd getCarteBancaireCmd = new GetCarteBancaireCmd();
         CarteBancaire carteBancaireOld = (CarteBancaire)getCarteBancaireCmd.execute(voCarte);
        
        //recherche de la carte new         
        voCarte.setVString(newCarte);
        CarteBancaire carteBancaireNew = (CarteBancaire)getCarteBancaireCmd.execute(voCarte);
        
        //maj de l'ancienne carte
        carteBancaireOld.setCarteBancaire(carteBancaireNew);
        carteBancaireOld.setDatRempCarb(DateHandler.timeJour());
        carteBancaireOld.setLibRempCarb(rechercheDemandesCartesForm.getLibRempCarb());
        carteBancaireOld.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRemplacee);
        carteBancaireOld.setDatOperCarb(DateHandler.timeJour());
        
        //envoi des information pour insertion dans la table historique
        ///probleme sur nom possibilité de modifier PK d'un objet ds la même session hibernate
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(Constants.COD_OPER_OPER_RemplaceeCarte);
        tacheId.setCodTachTach(Constants.COD_TACH_TACH_RemplaceeCarte);
        tache.setTacheId(tacheId);
       
        Personnel personnel = new Personnel();
        personnel.setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        carteBancaireOld.getDemandeCarte().setTache(tache);
        carteBancaireOld.getDemandeCarte().setPersonnel(personnel);
        
        //maj de la nouvelle demande de remplacement
        carteBancaireNew.getDemandeCarte().setCodEtatDcar(Constants.COD_ETAT_DCAR_CarteRemplacee);
        
        carteBancaireNew.getDemandeCarte().setTache(tache);
        carteBancaireNew.getDemandeCarte().setPersonnel(personnel);

        RemplacementCarteBancaireCmd remplacementCarteBancaireCmd = new RemplacementCarteBancaireCmd();
        carteBancaireOld = (CarteBancaire) remplacementCarteBancaireCmd.execute(carteBancaireOld);
        
        
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
           //return mapping.findForward("indexSMILE");
            return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    */
    public ActionForward restitutionCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
        //envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_RestitueeCarte);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_RestitueeCarte);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteRestituee);
        carteBancaire.setDatOperCarb(DateHandler.timeJour());
        
        RestitutionCarteBancaireCmd restitutionCarteBancaireCmd = new RestitutionCarteBancaireCmd();
        carteBancaire = (CarteBancaire) restitutionCarteBancaireCmd.execute(carteBancaire);
        
        
        
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    public ActionForward destructionCarteBancaire(ActionMapping mapping, ActionForm form, 
                          HttpServletRequest request, 
                          HttpServletResponse response) throws IOException, 
                                                               ServletException {

    try {
        RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
        CarteBancaire carteBancaire = rechercheDemandesCartesForm.getCarteBancaire();
        //envoi des information pour insertion dans la table historique
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodOperOper(Constants.COD_OPER_OPER_DetruireCarte);
        carteBancaire.getDemandeCarte().getTache().getTacheId().setCodTachTach(Constants.COD_TACH_TACH_DetruireCarte);
        carteBancaire.getDemandeCarte().getPersonnel().setNumMatrUser(rechercheDemandesCartesForm.getNumMatrUser());
        
        carteBancaire.setCodEtatCarb(Constants.COD_ETAT_CARB_CarteDetruite);
        carteBancaire.setDatOperCarb(DateHandler.timeJour());
        
        DestructionCarteBancaireCmd destructionCarteBancaireCmd = new DestructionCarteBancaireCmd();
        carteBancaire = (CarteBancaire) destructionCarteBancaireCmd.execute(carteBancaire);
        
        
        
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
       //return mapping.findForward("indexSMILE");
        return rechercherDemandesSelonChoix(mapping, form, request, response);
    }
    
    /**
     * 02-05-2008--------------lamia
     */
    public ActionForward imprimerDemandesCartes(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
         RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {    
             CommonReportVO valueObject = new CommonReportVO();
             ParamAgence paramAgence = new ParamAgence();
             paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             Map parameters = new HashMap();
             String vMatrUser = paramAgence.getNumMatrUser().toString();
             
            /*------------------------------------------------------------------*/
             String pCodStrcStrc = "P_COD_STRC_STRC";
             String pCodProd = "P_COD_PRD_PRD";
             String pNumContratCpt = "P_NUM_CCPT_CCPT";
             
             String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
             String vCodProd = "";
             String vNumContratCpt = "";
            
             /*-----------------------------------------------------------------*/
              String pCodTpceTpce = "P_COD_TPCE_TPCE";
              String pNumTpceTpce = "P_NUM_PCE_PERS";
             String vCodTpceTpce = "";
             String vNumTpceTpce = "";
            /*------------------------------------------------------------------*/
             String pNumDemCarte = "P_NUM_DEM_CARTE";
             String vNumDemCarte = "";
             /*------------------------------------------------------------------*/
             String pDateDeb = "P_DATE_DEB";
             String pDateFin = "P_DATE_FIN";
             String vDateFin="";
             String vDateDeb="";
             /*-----------------------------------------------------------------*/
             String pEtat = "P_VALIDE";
             String pMatrUser = "P_NUM_MATR_USER";
             String pLibEtat="P_LIB_ETAT";
             String vLibEtat="";
             String vEtat="";
             
            if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_Attente)){
                   vLibEtat = "Liste des demandes de cartes (En attente) pour confection ou pour modification";
                   vEtat=Constants.COD_ETAT_DCAR_Attente;
                   }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_Valider)){
                           vLibEtat = "Liste des demandes de cartes (Validées pour confection)";
                           vEtat=Constants.COD_ETAT_DCAR_Valider;
                           }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals("-5")){
                               vLibEtat = "Liste des demandes de cartes (Validées pour modification)";
                               vEtat=Constants.COD_ETAT_DCAR_Valider;
                            } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_AttenteDR)){
                                   vLibEtat = "Liste des demandes de cartes (En attente DR) pour confection ou pour modification";
                                   vEtat=Constants.COD_ETAT_DCAR_AttenteDR;
                            } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_AttenteScm)){
                                    vLibEtat = "Liste des demandes de cartes (En attente Sous Comité Monétique) pour confection ou pour modification";
                                    vEtat=Constants.COD_ETAT_DCAR_AttenteScm;
                            } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_AttenteScc)){
                                    vLibEtat = "Liste des demandes de cartes (En attente Sous Comité de Crédit) pour confection ou pour modification";
                                    vEtat=Constants.COD_ETAT_DCAR_AttenteScc;    
                                   }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_AttenteGarantie)){
                                    vLibEtat = "Liste des demandes de cartes (En attente de constitution de garantie) pour confection ou pour modification";
                                    vEtat=Constants.COD_ETAT_DCAR_AttenteGarantie;
                                   }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_CarteRecu)){
                                       vLibEtat = "Liste des demandes de cartes (Carte reçue) pour confection ou pour modification";
                                       vEtat=Constants.COD_ETAT_DCAR_CarteRecu;
                                       }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_CarteRemis)){
                                              vLibEtat = "Liste des demandes de cartes (Carte remise) pour confection ou pour modification";
                                              vEtat=Constants.COD_ETAT_DCAR_CarteRemis; 
                                               }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_DemandeRempl)){
                                                        vLibEtat = "Liste des demandes de cartes (Demande remplacement) pour confection ou pour modification";
                                                        vEtat=Constants.COD_ETAT_DCAR_DemandeRempl;
                                                       }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_DemandeRemplValide)){
                                                                vLibEtat = "Liste des demandes de cartes (Demande remplacement valide) pour confection ou pour modification";
                                                                vEtat=Constants.COD_ETAT_DCAR_DemandeRemplValide;
                                                               }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_RejetDemande)){
                                                                            vLibEtat = "Liste des demandes de cartes (Demande rejetée Agence) pour confection ou pour modification";
                                                                            vEtat=Constants.COD_ETAT_DCAR_RejetDemande;
                                                                           }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_RejetDr)){
                                                                                    vLibEtat = "Liste des demandes de cartes (Demande rejetée DR) pour confection ou pour modification";
                                                                                    vEtat=Constants.COD_ETAT_DCAR_RejetDr;
                                                                            }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_RejetScm)){
                                                                                                    vLibEtat = "Liste des demandes de cartes (Demande rejetée Sous Comité Monétique) pour confection ou pour modification";
                                                                                                    vEtat=Constants.COD_ETAT_DCAR_RejetScm;
                                                                            }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_RejetScc)){
                                                                                                                   vLibEtat = "Liste des demandes de cartes (Demande rejetée Sous Comité Crédit) pour confection ou pour modification";
                                                                                                                   vEtat=Constants.COD_ETAT_DCAR_RejetScc;
                                                                                       } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_PrevaliderDR)){
                                                                                                    vLibEtat = "Liste des demandes de cartes (Demande prévalidée DR) pour confection ou pour modification";
                                                                                                    vEtat=Constants.COD_ETAT_DCAR_PrevaliderDR;
                                                                                        } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_PrevaliderScm)){
                                                                                                    vLibEtat = "Liste des demandes de cartes (Demande prévalidée Sous Comité Monétique) pour confection ou pour modification";
                                                                                                    vEtat=Constants.COD_ETAT_DCAR_PrevaliderScm;
                                                                                        } else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_PrevaliderScc)){
                                                                                                    vLibEtat = "Liste des demandes de cartes (Demande prévalidée Sous Comité de Crédit) pour confection ou pour modification";
                                                                                                    vEtat=Constants.COD_ETAT_DCAR_PrevaliderScc; 
                                                                                    
                                                                                                       }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_RejetDelivreCarte)){
                                                                                                                vLibEtat = "Liste des demandes de cartes (Carte mal confectionnée) pour confection ou pour modification";
                                                                                                                vEtat=Constants.COD_ETAT_DCAR_RejetDelivreCarte;
                                                                                                                   }else if (rechercheDemandesCartesForm.getChoixEtatDemande().equals(Constants.COD_ETAT_DCAR_CarteRemplacee)){
                                                                                                                            vLibEtat = "Liste des demandes de cartes (Carte remplacée) pour confection ou pour modification";
                                                                                                                            vEtat=Constants.COD_ETAT_DCAR_CarteRemplacee;
                                                                                                                               }else {
                                                                                                                                       vLibEtat = "Liste des demandes de cartes pour confection ou pour modification";
                                                                                                                                       vEtat="";
                                                                                                                                      }
               
             if (rechercheDemandesCartesForm.getChoix().equals("3")) {
                   vDateFin=rechercheDemandesCartesForm.getDateFinRech();
                   vDateDeb=rechercheDemandesCartesForm.getDateDebutRech();
                   parameters.put(pDateDeb,vDateDeb);
                   parameters.put(pDateFin,vDateFin);
                 if (rechercheDemandesCartesForm.getChoixEtatDemande().equals("-5")){
                       valueObject.setNomReport("listDmdeCartBancairModif_Period");
                   }else {
                       valueObject.setNomReport("listDemandeCarteBancaire_Periode"); 
                   }
                 } else if (rechercheDemandesCartesForm.getChoix().equals("1")) {// recherche par num contrat
                 
                            vCodProd= rechercheDemandesCartesForm.getCodPrdRech();
                            vNumContratCpt = rechercheDemandesCartesForm.getNumCcptRech();
                            parameters.put(pCodProd,vCodProd);
                            parameters.put(pNumContratCpt,vNumContratCpt);
                            //----------------------------------------------------------------Avec ou sans etat
                          if (!rechercheDemandesCartesForm.getChoixEtatDemande().equals("0")){
                                  if (rechercheDemandesCartesForm.getChoixEtatDemande().equals("-5")){
                                        valueObject.setNomReport("listDmdeCartBancairModif_ccpt");
                                    }else {
                                        valueObject.setNomReport("listDemandeCarteBancaire_ccpt");
                                    }
                             
                              } else {
                                valueObject.setNomReport("listDemandeCarteBancaire_ccpt_SE");
                             }//--------------------------------------------------------------------------
                          } else if (rechercheDemandesCartesForm.getChoix().equals("0")) { // par Type pièce num pièce
                                        vCodTpceTpce = rechercheDemandesCartesForm.getTypePieceId();
                                        vNumTpceTpce = rechercheDemandesCartesForm.getNumPieceId();
                                        parameters.put(pCodTpceTpce,vCodTpceTpce);
                                        parameters.put(pNumTpceTpce,vNumTpceTpce);
                                        if (!rechercheDemandesCartesForm.getChoixEtatDemande().equals("0")){
                                            if (rechercheDemandesCartesForm.getChoixEtatDemande().equals("-5")){
                                                  valueObject.setNomReport("listDmdeCartBancairModif_TP");
                                              }else {
                                                  valueObject.setNomReport("listDemandeCarteBancaire_TP");
                                              }  
                                           } else {
                                                     valueObject.setNomReport("listDemandeCarteBancaire_TP_SE");
                                                   }
                                    }else if (rechercheDemandesCartesForm.getChoix().equals("2")) { // par numéro demande
                                        vLibEtat = "Détails Demande Carte Bancaire";
                                        vNumDemCarte= rechercheDemandesCartesForm.getNumDemande();
                                        parameters.put(pNumDemCarte,vNumDemCarte);
                                        valueObject.setNomReport("listDemandeCarteBancaire_numDemd");
                                        
                                    }else if (rechercheDemandesCartesForm.getChoix().equals("4")) { //toutes; avec etat
                                       // si l' etat n' est pas aucun
                                        if (!rechercheDemandesCartesForm.getChoixEtatDemande().equals("0")){
                                            if (rechercheDemandesCartesForm.getChoixEtatDemande().equals("-5")){
                                                  valueObject.setNomReport("listDmdCarteBancairModif");
                                              }else {
                                                  valueObject.setNomReport("listDemandeCarteBancaire");
                                              }
                                        }
                                      }
                                    
         
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pCodStrcStrc, vCodStrcStrc);
             parameters.put(pEtat, vEtat);
             
             valueObject.setParams(parameters);
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","1");
             
         return mapping.findForward("success");
         } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :imprimerDemandesSelonChoix ");
         text.append(e.toString());
         erreur.setCode("200");
         erreur.setDescription(text.toString());
         ActionMessage actionMessage = 
             new ActionMessage("exception.generique", 
                               erreur.getDescription());
         actionMessages.add("Erreur ", actionMessage);
         this.saveMessages(request, actionMessages);
         logger.error("Exception : ",e);
         return mapping.findForward("error");
         }                                                                                
                                                                                           
     }
    /**
     * 02-06-2008--------------lamia
     */
    public ActionForward imprimerListeRenouvellementCartes(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
         RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {    
             CommonReportVO valueObject = new CommonReportVO();
             ParamAgence paramAgence = new ParamAgence();
             paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             Map parameters = new HashMap();
             
             /*------------------------------------------------------------------*/
              String pCodStrcStrc = "P_COD_STRC_STRC";
              String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
              String pMatrUser = "P_NUM_MATR_USER";
             String vMatrUser = paramAgence.getNumMatrUser().toString();
              String pLibEtat="P_LIB_ETAT";
              String vLibEtat = "Liste des cartes bancaires à renouveller automatiquement";
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pCodStrcStrc, vCodStrcStrc);
             
              if (rechercheDemandesCartesForm.getChoix().equals("3")) {
                     /*------------------------------------------------------------------*/
                     String pDateDeb = "P_DATE_DEB";
                     String pDateFin = "P_DATE_FIN";
                     String vDateFin="";
                     String vDateDeb="";
                     /*-----------------------------------------------------------------*/
                   vDateFin=rechercheDemandesCartesForm.getDateFinRech();
                   vDateDeb=rechercheDemandesCartesForm.getDateDebutRech();
                   parameters.put(pDateDeb,vDateDeb);
                   parameters.put(pDateFin,vDateFin);
                   valueObject.setNomReport("listRenouv_Carte_periode");
                 } else if (rechercheDemandesCartesForm.getChoix().equals("1")) {// recherche par num contrat
                              String pCodProd = "P_COD_PRD_PRD";
                              String pNumContratCpt = "P_NUM_CCPT_CCPT";
                              String vCodProd = "";
                              String vNumContratCpt = "";
                            vCodProd= rechercheDemandesCartesForm.getCodPrdRech();
                            vNumContratCpt = rechercheDemandesCartesForm.getNumCcptRech();
                            parameters.put(pCodProd,vCodProd);
                            parameters.put(pNumContratCpt,vNumContratCpt);
                            valueObject.setNomReport("listRenouv_Carte_ccpt");
                            
                          } else if (rechercheDemandesCartesForm.getChoix().equals("0")) { // par Type pièce num pièce
                          
                                       /*-----------------------------------------------------------------*/
                                        String pCodTpceTpce = "P_COD_TPCE_TPCE";
                                        String pNumTpceTpce = "P_NUM_PCE_PERS";
                                        String vCodTpceTpce = "";
                                        String vNumTpceTpce = "";
                                        vCodTpceTpce = rechercheDemandesCartesForm.getTypePieceId();
                                        vNumTpceTpce = rechercheDemandesCartesForm.getNumPieceId();
                                        parameters.put(pCodTpceTpce,vCodTpceTpce);
                                        parameters.put(pNumTpceTpce,vNumTpceTpce);
                                         valueObject.setNomReport("listRenouv_Carte_numPce");
                            
                                    }else if (rechercheDemandesCartesForm.getChoix().equals("4")) { // Toutes
                                               valueObject.setNomReport("listRenouv_Carte");
                                            }
         
             valueObject.setParams(parameters);
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","1");
             
         return mapping.findForward("success");
         } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :imprimerListeRenouvellementCartes ");
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
    
    /**
     * 13-06-2008--------------lamia
     */
    public ActionForward imprimerListeCartes(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
         RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {    
             CommonReportVO valueObject = new CommonReportVO();
             ParamAgence paramAgence = new ParamAgence();
             paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             Map parameters = new HashMap();
             String vMatrUser = paramAgence.getNumMatrUser().toString();
             
            /*------------------------------------------------------------------*/
             String pCodStrcStrc = "P_COD_STRC_STRC";
             String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
             
             String pEtat = "P_VALIDE";
             String pMatrUser = "P_NUM_MATR_USER";
             String pLibEtat="P_LIB_ETAT";
             String vLibEtat="";
             String vEtat="";
             
            if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteCree)){
                   vLibEtat = "Liste des cartes bancaires crées";
                   vEtat=Constants.COD_ETAT_CARB_CarteCree;
                   }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteRecu)){
                           vLibEtat = "Liste des cartes bancaires reçues";
                           vEtat=Constants.COD_ETAT_CARB_CarteRecu;
                           } else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteRemise)){
                                   vLibEtat = "Liste des cartes bancaires remises";
                                   vEtat=Constants.COD_ETAT_CARB_CarteRemise;
                                   }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteRemplacee)){
                                    vLibEtat = "Liste des cartes bancaires remplacées";
                                    vEtat=Constants.COD_ETAT_CARB_CarteRemplacee;
                                   }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteCree)){
                                       vLibEtat = "Liste des cartes bancaires crées";
                                       vEtat=Constants.COD_ETAT_CARB_CarteCree;
                                       }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteRestituee)){
                                              vLibEtat = "Liste des cartes bancaires restituées";
                                              vEtat=Constants.COD_ETAT_CARB_CarteRestituee; 
                                               }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteCree)){
                                                        vLibEtat = "Liste des cartes bancaires crées";
                                                        vEtat=Constants.COD_ETAT_CARB_CarteCree;
                                                       }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteDetruite)){
                                                                vLibEtat = "Liste des cartes bancaires détruites";
                                                                vEtat=Constants.COD_ETAT_CARB_CarteDetruite;
                                                               }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_CarteMalConfect)){
                                                                            vLibEtat = "Liste des cartes bancaires mal confectionnées";
                                                                            vEtat=Constants.COD_ETAT_CARB_CarteMalConfect;
                                                                           }else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_RejetDelivreCarte)){
                                                                                    vLibEtat = "Liste des cartes bancaires rejetées";
                                                                                    vEtat=Constants.COD_ETAT_CARB_RejetDelivreCarte;
                                                                                       } else if (rechercheDemandesCartesForm.getChoixEtatCarte().equals(Constants.COD_ETAT_CARB_AnnulRenouvel)){
                                                                                                vLibEtat = "Liste des cartes bancaires dont le renouvellement automatique a été annulé";
                                                                                                vEtat=Constants.COD_ETAT_CARB_AnnulRenouvel;
                                                                                                   } else{
                                                                                                             vLibEtat = "Liste des cartes bancaires (Tous Etats confondus)";
                                                                                                             vEtat="";
                                                                                                             }
               
             if (rechercheDemandesCartesForm.getChoix().equals("3")) {
                     /*------------------------------------------------------------------*/
                     String pDateDeb = "P_DATE_DEB";
                     String pDateFin = "P_DATE_FIN";
                     String vDateFin="";
                     String vDateDeb="";
                     /*-----------------------------------------------------------------*/
                   vDateFin=rechercheDemandesCartesForm.getDateFinRech();
                   vDateDeb=rechercheDemandesCartesForm.getDateDebutRech();
                   parameters.put(pDateDeb,vDateDeb);
                   parameters.put(pDateFin,vDateFin);
                     //----------------------------------------------------------------Avec ou sans etat
                     if (!rechercheDemandesCartesForm.getChoixEtatCarte().equals("0")){
                         valueObject.setNomReport("listCarteBancaire_Periode"); 
                       } else {
                                 valueObject.setNomReport("listCarteBancaire_Periode_SE"); // _SE = Sans Etat ==> pas d'envoi de paramètre etat
                              }//--------------------------------------------------------------------------
                                      
                 } else if (rechercheDemandesCartesForm.getChoix().equals("1")) {// recherche par num contrat
                            String pCodProd = "P_COD_PRD_PRD";
                            String pNumContratCpt = "P_NUM_CCPT_CCPT";
                            String vCodProd = "";
                            String vNumContratCpt = "";
                            vCodProd= rechercheDemandesCartesForm.getCodPrdRech();
                            vNumContratCpt = rechercheDemandesCartesForm.getNumCcptRech();
                            parameters.put(pCodProd,vCodProd);
                            parameters.put(pNumContratCpt,vNumContratCpt);
                            //----------------------------------------------------------------Avec ou sans etat
                          if (!rechercheDemandesCartesForm.getChoixEtatCarte().equals("0")){
                                valueObject.setNomReport("listCarteBancaire_Ccpt");
                              } else {
                                valueObject.setNomReport("listCarteBancaire_Ccpt_SE");
                             }//--------------------------------------------------------------------------
                          } else if (rechercheDemandesCartesForm.getChoix().equals("0")) { // par Type pièce num pièce
                                       /*-----------------------------------------------------------------*/
                                       String pCodTpceTpce = "P_COD_TPCE_TPCE";
                                       String pNumTpceTpce = "P_NUM_PCE_PERS";
                                       String vCodTpceTpce = "";
                                       String vNumTpceTpce = "";
                                       /*------------------------------------------------------------------*/
                                        vCodTpceTpce = rechercheDemandesCartesForm.getTypePieceId();
                                        vNumTpceTpce = rechercheDemandesCartesForm.getNumPieceId();
                                        parameters.put(pCodTpceTpce,vCodTpceTpce);
                                        parameters.put(pNumTpceTpce,vNumTpceTpce);
                                        if (!rechercheDemandesCartesForm.getChoixEtatCarte().equals("0")){
                                            valueObject.setNomReport("listCarteBancaire_numPce");
                                           } else {
                                                     valueObject.setNomReport("listCarteBancaire_numPce_SE");
                                                   }
                                    }
                                    
         
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pCodStrcStrc, vCodStrcStrc);
             parameters.put(pEtat, vEtat);
             
             valueObject.setParams(parameters);
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","1");
             
         return mapping.findForward("success");
         } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :imprimerListeCartes ");
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
    public ActionForward imprimerFicheRenseignement(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws IOException, 
                                                                                           ServletException {
         RechercheDemandesCartesForm rechercheDemandesCartesForm = (RechercheDemandesCartesForm)form;
         ActionMessages actionMessages = new ActionMessages();
         try {    
             CommonReportVO valueObject = new CommonReportVO();
             ParamAgence paramAgence = new ParamAgence();
             paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             Map parameters = new HashMap();
             /*------------------------------------------------------------------*/
              String pCodeTpce = "P_COD_TPCE_TPCE";
              String pNumPce = "P_NUM_PCE_PERS";
              String vCodeTpce = rechercheDemandesCartesForm.getTypePieceId();
              String vNumPce = rechercheDemandesCartesForm.getNumPieceId();
              String pMatrUser = "P_NUM_MATR_USER";
              String vMatrUser = paramAgence.getNumMatrUser().toString();
             
              String pLibEtat="P_LIB_ETAT";
              String vLibEtat = "Fiche de renseignement carte de paiement";
             parameters.put(pMatrUser, vMatrUser);
             parameters.put(pLibEtat, vLibEtat);
             parameters.put(pNumPce, vNumPce);
             parameters.put(pCodeTpce, vCodeTpce);
             valueObject.setNomReport("Fiche_Renseignement_CB");
             valueObject.setParams(parameters);
             request.getSession().setAttribute("CommonPrintVo",valueObject);
             request.setAttribute("print","2");
             
         return mapping.findForward("success");
         } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         StringBuffer text = 
             new StringBuffer("la transaction est Interrompu, une erreur dans RechercheDemandesChequesAction / Dispatch Action :imprimerFicheRenseignement ");
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
  
}

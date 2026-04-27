package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.commande.InsertOperationMoyPayCmd;
import com.bna.commun.conditiondebanque.commande.DemandeConditionCmd;
import com.bna.commun.conditiondebanque.vo.Condition;
import com.bna.commun.conditiondebanque.vo.ConditionBanque;
import com.bna.commun.conditiondebanque.vo.DemandeCondition;
import com.bna.commun.conditiondebanque.vo.DetailConditionBanque;
import com.bna.commun.conditiondebanque.vo.ListConditionVo;
import com.bna.commun.model.AffectationCaisseStructure;
import com.bna.commun.model.CaisseStructure;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MandPersOperMoy;
import com.bna.commun.model.MandPersOperMoyId;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.NomencElemtCondition;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;

import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.RegleGestionContratId;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.model.TypeRegleContrat;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecaisse.commande.GetListeSessionJrnCaisseCmd;
import com.bna.smile.model.domainecaisse.model.ListeCaisseStructureVo;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetRegleGestionContratCmd;


import com.bna.smile.model.domaineguichet.commande.PecVersementCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.moyenPaiement.certificationCheque.forms.CertificationChequeForm;


import com.bna.smile.web.operationguichet.form.VersementMemeAgenceForm;

import com.oxia.fwk.context.Context;

import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

import java.io.IOException;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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

public class VersementMemeAgenceAction extends DispatchAction {
    /**
     * <B> Action de la page  transfertContratCpt.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.operationguichet.actions
     *
     * @author  Mdimagh Med Lassaad 
     * @version le 12/09/2007
     * @MAJ 21/11/2007
     */
    public Context context = ContextHandler.getContext();
    ContratCpt contratCpt = new ContratCpt();
    
    private final static Logger logger = Logger.getLogger(VersementMemeAgenceAction.class);
    
    public ActionForward initPageMenu(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
                                                                          
        logger.debug(" entrée sous le menu versement");                                                                     
              return mapping.findForward("menu");


    }
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        logger.debug(" entrée Initier page");                            
        
        ActionMessages actionMessages = new ActionMessages();
        try {
            ISearchEngine searchEngine  = (SearchEngine)context.getBean("searchEngine");
            
                   
            
            VersementMemeAgenceForm versementMemeAgenceForm = 
                (VersementMemeAgenceForm)form;
            String codeOperation = 
                versementMemeAgenceForm.getInitialisationView().getCodeOperation();

            versementMemeAgenceForm.clearForm();
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            versementMemeAgenceForm.getInitialisationView().setCodeOperation(codeOperation);
            versementMemeAgenceForm.getInitialisationView().setDateOp(new Date());
            versementMemeAgenceForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            versementMemeAgenceForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            versementMemeAgenceForm.getVersementMemeAgenceView().setMntCommVers("0");
            versementMemeAgenceForm.getVersementMemeAgenceView().setMntTvaVers("0");
          
          logger.info("Agence : " + paramAgence.getCodStrcStrc().toString() +" Matricule : "+ paramAgence.getNumMatrUser().toString()+ " entrée page versement  Opération : " +  versementMemeAgenceForm.getInitialisationView().getCodeOperation());
         
          if(versementMemeAgenceForm.getInitialisationView().getCodeOperation().equals(Constants.COD_OPER_VERSEMENT.toString())){
            versementMemeAgenceForm.getContratView().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
               versementMemeAgenceForm.getInitialisationView().setLibelleOperation("Versement");
           }else{
               versementMemeAgenceForm.getInitialisationView().setLibelleOperation("Versement déplacé émis");
           }
           
           //---------------------------------------------------------------------//
           //-------------  Recherche des caisses actives  -----------------------//
           //---------------------------------------------------------------------//
           
            ListeCaisseStructureVo listeCaisseStructureVo = new ListeCaisseStructureVo();
            listeCaisseStructureVo.setCodeStructure(Long.valueOf(paramAgence.getCodStrcStrc()));
            listeCaisseStructureVo.setDateJournee(DateHandler.strToDate(paramAgence.getDateJours()));
            listeCaisseStructureVo.setCodeStatus("1");
            
            GetListeSessionJrnCaisseCmd getListeCaisseStructureCmd = new GetListeSessionJrnCaisseCmd();
            listeCaisseStructureVo = (ListeCaisseStructureVo) getListeCaisseStructureCmd.execute(listeCaisseStructureVo);
            versementMemeAgenceForm.setListeCaisseStructure(listeCaisseStructureVo.getListeCaisseStructure());
            
           return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            erreur.setCode("200");
            erreur.setDescription("Une erreur est survenu dans versementMemeAgenceAction : " + 
                                  e.toString());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);

            return mapping.findForward("success");
        }
    }


    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {


        ActionMessages actionMessages = new ActionMessages();

        VersementMemeAgenceForm versementMemeAgenceForm = 
            (VersementMemeAgenceForm)form;
        /*******recherche du contrat**********/
        try {

            String codStrcStrc = 
                versementMemeAgenceForm.getContratView().getCodStrcStrc();
            String codPrdPrd = 
                versementMemeAgenceForm.getContratView().getCodPrdPrd();
            String numCcptCcpt = 
                versementMemeAgenceForm.getContratView().getNumCcptCcpt();
            String cle = versementMemeAgenceForm.getContratView().getCle();

            versementMemeAgenceForm.clearForm();

            versementMemeAgenceForm.getContratView().setCodStrcStrc(codStrcStrc);
            versementMemeAgenceForm.getContratView().setCodPrdPrd(codPrdPrd);
            versementMemeAgenceForm.getContratView().setNumCcptCcpt(numCcptCcpt);
            versementMemeAgenceForm.getContratView().setCle(cle);
            
            versementMemeAgenceForm.getVersementMemeAgenceView().setMntCommVers("0");
            versementMemeAgenceForm.getVersementMemeAgenceView().setMntTvaVers("0");
            
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(versementMemeAgenceForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(versementMemeAgenceForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(versementMemeAgenceForm.getContratView().getNumCcptCcpt()));


            versementMemeAgenceForm.getContratView().setCodStrcStrc(contratCptId.getCodStrcStrc().toString());
            versementMemeAgenceForm.getContratView().setCodPrdPrd(StrHandler.lpad(contratCptId.getCodPrdPrd().toString(),'0',4));
            versementMemeAgenceForm.getContratView().setNumCcptCcpt(StrHandler.lpad(contratCptId.getNumCcptCcpt().toString(), 
                                                                                    '0', 
                                                                                    6));

            
            //recherche contrat
            GetDetailContratCmd getDetailContratCmd = 
                new GetDetailContratCmd();
            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratCmd.execute(contratCptId);


            if (contratCpt != null && contratCpt.getContratCptId() == null) {
                versementMemeAgenceForm.getContratView().setMessageContratCpt("Contrat Inéxistant, veuillez verifier votre saisie, SVP");

                //------- Verifier s'il y a une restriction sur le contrat -------------------//
            } else {
                versementMemeAgenceForm.getContratView().setContratCpt(contratCpt);

                versementMemeAgenceForm.getVersementMemeAgenceView().setCodDevDev(contratCpt.getDevise().getCodDevDev().toString());
                versementMemeAgenceForm.getVersementMemeAgenceView().setLibDevDev(contratCpt.getDevise().getLibDevDev());
                versementMemeAgenceForm.getContratView().setNbrDecDev(contratCpt.getDevise().getNbrDecDev().toString());
               // Verifier illigibilité du versement sur le contrat           
                if ( ( contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) ||
                     ( contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_SEMIACTIF ))
                   
                    ) {
                
                    //  Verifier la restriction 
                    if( (contratCpt.getBoolRverCcpt()!=null) && (contratCpt.getBoolRverCcpt().equals(Constants.BOOL_RESTRICTION_VERS))) {
                        versementMemeAgenceForm.getPersonneDemandeur().setMessageTexte("Une restriction sur le versement est efféctuée sur ce contrat, veuillez procéder à la vérification du pouvoir.");
                    }
                    //  Affecter la devise du contrat 
                    versementMemeAgenceForm.getVersementMemeAgenceView().setCodDevDev(contratCpt.getDevise().getCodDevDev().toString());
                    versementMemeAgenceForm.getVersementMemeAgenceView().setLibDevDev(contratCpt.getDevise().getLibSiglDev() );
                    versementMemeAgenceForm.getVersementMemeAgenceView().setNbrUnitDev(contratCpt.getDevise().getNbrDecDev().toString());
                    // Affecter le numéro livret 
                    if (contratCpt.getNumLivrCcpt()!= null ){
                     versementMemeAgenceForm.getVersementMemeAgenceView().setNumeroLivret(contratCpt.getNumLivrCcpt());
                    }
                   
                    //----------------- extraire les regles de gestion sur le produit ----------------------//
                    GetRegleGestionContratCmd getRegleGestionContratCmd = 
                        new GetRegleGestionContratCmd();
                    Produit produit = new Produit();
                    Operation operation = new Operation();
                    TypeRegleContrat typeRegleContrat = new TypeRegleContrat();
    
                                
                    
                    RegleGestionContratId regleGestionContratId = 
                        new RegleGestionContratId();
                    regleGestionContratId.setCodPrdPrd(Long.valueOf(versementMemeAgenceForm.getContratView().getContratCpt().getProduit().getCodPrdPrd()));
                    regleGestionContratId.setCodOperOper(Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeOperation()));
                    regleGestionContratId.setCodTypTreg(Long.valueOf(1));
    
                    //RegleGestionContrat regleGestionContrat = 
                      //  (RegleGestionContrat)getRegleGestionContratCmd.execute(regleGestionContratId);
    
            
                    //----------- si l'opérartion est déplacé
                    
                    if (versementMemeAgenceForm.getInitialisationView().getCodeOperation().equals(Constants.COD_OPER_VERSEMENT_DEPLACE.toString())){
                        StringBuffer text = new StringBuffer("");
                        text.append("Agence : "+contratCpt.getStructure().getLibStrcStrc());
                        
                        if (contratCpt.getStructure().getNumTelStrc()!=null){
                            text.append(" (Tél : "+contratCpt.getStructure().getNumTelStrc()+ " )");
                        }
                        text.append(" .");
                        versementMemeAgenceForm.getContratView().setMessageContratCpt(text.toString());
                    }
                    
                    
                    //----------- appel de L'API condition de banque...
                     chargerConditionBanque(versementMemeAgenceForm);
               
                }   else {
                  versementMemeAgenceForm.getPersonneDemandeur().setMessageTexte("Le versement ne peut pas être effectué, l'état du contrat est : " + contratCpt.getCodEtatCcpt() );
                      
                }
                
            }
            return mapping.findForward("success");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }
    
    

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        ActionMessages actionMessages = new ActionMessages();
        VersementMemeAgenceForm versementMemeAgenceForm = 
            (VersementMemeAgenceForm)form;
        try {
            versementMemeAgenceForm.clearForm();
            return mapping.findForward("success");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action annuler---- " + e.getMessage());
            return mapping.findForward("error");
        }

    }

   

    public ActionForward validation(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        ActionMessages actionMessages = new ActionMessages();

        VersementMemeAgenceForm versementMemeAgenceForm = 
            (VersementMemeAgenceForm)form;
            
          //---------------------------------------//
         // trace de l'operation moy de payement  //
        //---------------------------------------//   
        OperationMoyPay operationMoyPay = new OperationMoyPay();

        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");

        operationMoyPay.setDatOperOmp(DateHandler.strToDate(versementMemeAgenceForm.getInitialisationView().getDateActuelle()));
        operationMoyPay.setDatValOmp(DateHandler.strToDate(versementMemeAgenceForm.getInitialisationView().getDateActuelle()));
        
            if ((versementMemeAgenceForm.getPersonneDemandeur().getTypePouvoir()==null) ||
                (! versementMemeAgenceForm.getPersonneDemandeur().getTypePouvoir().equals("T") &&
                ! versementMemeAgenceForm.getPersonneDemandeur().getTypePouvoir().equals("M") &&
                ! versementMemeAgenceForm.getPersonneDemandeur().getTypePouvoir().equals("C") )){
                  operationMoyPay.setCodDemOmp("TR");     
            }else {
                operationMoyPay.setCodDemOmp(versementMemeAgenceForm.getPersonneDemandeur().getTypePouvoir());
            }

        
        Tache tache = new Tache();
        TacheId tacheId = new TacheId();
        tacheId.setCodOperOper(Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeOperation()));
        tacheId.setCodTachTach(Long.valueOf(1));
        tache.setTacheId(tacheId);
        operationMoyPay.setTache(tache);
        
        Personnel persInitiateur = new Personnel();
        persInitiateur.setNumMatrUser(paramAgence.getNumMatrUser().toString());
        operationMoyPay.setPersonnelInitiateur(persInitiateur);
        
               
        ContratCptId contratCptId = new ContratCptId();
        contratCptId.setCodPrdPrd(Long.valueOf(versementMemeAgenceForm.getContratView().getCodPrdPrd()));
        contratCptId.setCodStrcStrc(Long.valueOf(versementMemeAgenceForm.getContratView().getCodStrcStrc()));
        contratCptId.setNumCcptCcpt(Long.valueOf(versementMemeAgenceForm.getContratView().getNumCcptCcpt()));
        ContratCpt contratCpt = new ContratCpt();
        contratCpt.setContratCptId(contratCptId);
        operationMoyPay.setContratCpt(contratCpt);
        
        Devise devise = new Devise();
        devise.setCodDevDev(Long.valueOf(versementMemeAgenceForm.getVersementMemeAgenceView().getCodDevDev()));
        operationMoyPay.setDevise(devise);
        
        
        //contratCptId.
         TypePiece typePieceDemandeur = new TypePiece();
         typePieceDemandeur.setCodTpceTpce(Constants.COD_CIN);
         typePieceDemandeur.setCodTpceTpce(Long.valueOf(versementMemeAgenceForm.getPersonneDemandeur().getCodTpceTpceDemandeur()));
         operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
        if (versementMemeAgenceForm.getPersonneDemandeur().getNumPcePersDemandeur()!= null && (!versementMemeAgenceForm.getPersonneDemandeur().getNumPcePersDemandeur().equals(""))){
            operationMoyPay.setNumPcedOmp(versementMemeAgenceForm.getPersonneDemandeur().getNumPcePersDemandeur());
        }else{
            operationMoyPay.setNumPcedOmp("0");
        }
        
        operationMoyPay.setCodEtatOmp("A");
        operationMoyPay.setCodSensOmp("C");
        
        operationMoyPay.setNomNomdOmp(versementMemeAgenceForm.getPersonneDemandeur().getNomNomPersDemandeur());
        operationMoyPay.setNomPrndOmp(versementMemeAgenceForm.getPersonneDemandeur().getNomPrnPersDemandeur());
        operationMoyPay.setLibMotfOmp(versementMemeAgenceForm.getVersementMemeAgenceView().getMotifOperation());
        operationMoyPay.setCodRefcOmp(versementMemeAgenceForm.getVersementMemeAgenceView().getReferenceClient());
        
        if (versementMemeAgenceForm.getNumeroLivretSaisi()!=null && (!versementMemeAgenceForm.getNumeroLivretSaisi().equals(""))){
        operationMoyPay.setNumMoypOmp(versementMemeAgenceForm.getNumeroLivretSaisi());
        }
        
        if (versementMemeAgenceForm.getVersementMemeAgenceView().getOrigineDesFonds()!=null 
            && (! versementMemeAgenceForm.getVersementMemeAgenceView().getOrigineDesFonds().equals(""))){
           operationMoyPay.setLibOrifOmp(versementMemeAgenceForm.getVersementMemeAgenceView().getOrigineDesFonds());
        }
        
        //---------------------- sauvgarde des montants --------------------------- //
        String montant = versementMemeAgenceForm.getVersementMemeAgenceView().getMontantDinars().replace(".","");
               montant = montant.replace(" ","");
        operationMoyPay.setMontDinOmp(Long.valueOf(montant));
        
        if (!versementMemeAgenceForm.getVersementMemeAgenceView().getCodDevDev().equals(Constants.COD_DEV_DINAR)){
            //--------------------------------------------------//
            //---------- Cas de l'operation meme devise --------//
            if (versementMemeAgenceForm.getVersementMemeAgenceView().getCodDevDev().equals(versementMemeAgenceForm.getContratView().getCodDevDev())){
                montant = versementMemeAgenceForm.getVersementMemeAgenceView().getMontantDevise().replace(".","");
                montant = montant.replace(" ","");
                operationMoyPay.setMontDevOmp(Long.valueOf(montant));
                String cours = versementMemeAgenceForm.getVersementMemeAgenceView().getCoursParite();
                operationMoyPay.setMontCdinOmp(Double.valueOf(cours));
                //operationMoyPay.
            }else{
            
                montant = versementMemeAgenceForm.getVersementMemeAgenceView().getMontantDevise().replace(".","");
                montant = montant.replace(" ","");
                operationMoyPay.setMontDevOmp(Long.valueOf(montant));
                
                montant = versementMemeAgenceForm.getVersementMemeAgenceView().getMontantContreValeur().replace(".","");
                montant = montant.replace(" ","");
                operationMoyPay.setMontCvalOmp(Long.valueOf(montant));
                
                String cours = versementMemeAgenceForm.getVersementMemeAgenceView().getCoursParite();
                operationMoyPay.setMontCdinOmp(Double.valueOf(cours));
                
               montant = versementMemeAgenceForm.getVersementMemeAgenceView().getCoursApplique();
               operationMoyPay.setMontCourOmp(Double.valueOf(montant));
          
                
            }
            //operationMoyPay.set
        }
        Structure structureInitiatrice = new Structure();
        structureInitiatrice.setCodStrcStrc(Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeAgence()));
        operationMoyPay.setStructureInitiatrice(structureInitiatrice);
        //-----------------------------------------------------------------------------------
        //--- Si l'opération est un versement déplacé alors affecter la structure récéptrice
        if (Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeOperation()).equals(Long.valueOf(Constants.COD_OPER_VERSEMENT_DEPLACE))){
            Structure structureReceptrice = new Structure(); 
            structureReceptrice.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc());
            operationMoyPay.setStructureReceptrice(structureReceptrice);
            
        }
        
        //-----------------------------------------------------------------------------//
        //---------- Remplir les information du mandat et des mandats personnes  ------//     
        //-----------------------------------------------------------------------------//
         Pouvoir pouvoir = 
             (Pouvoir)request.getSession().getAttribute("pouvoir"); 
       if (pouvoir != null){
        if (pouvoir.getTypePouvoir().equalsIgnoreCase(Constants.COD_TYPE_POUVOIR_MANDATAIRE)){
            
            
            if (pouvoir.getListMandatOperation()!=null && pouvoir.getListMandatOperation().size()!=0){
                for(Iterator it = pouvoir.getListMandatOperation().iterator();it.hasNext();){
                 MandatOperation mandatOperation = (MandatOperation)pouvoir.getListMandatOperation().get(0);
                 operationMoyPay.setMandatOperation(mandatOperation);
                }
            }
            if (pouvoir.getListMandatPersonne()!=null && pouvoir.getListMandatPersonne().size()!=0 ){
                Collection listeMandatPersonne = pouvoir.getListMandatPersonne();
                
                for(Iterator it = listeMandatPersonne.iterator();it.hasNext();){
                 MandPersOperMoy mandPersOper       = new MandPersOperMoy();
                 MandPersOperMoyId mandPersOperId   = new MandPersOperMoyId();
                    
                 MandatPersonne mandatPersonne = (MandatPersonne)it.next();
                 
                 mandPersOperId.setNumMandMand(mandatPersonne.getMandatPersonneId().getNumMandMand());
                 mandPersOperId.setNumSeqPers(mandatPersonne.getMandatPersonneId().getNumSeqPers());
                 mandPersOper.setMandPersOperMoyId(mandPersOperId);
                    mandPersOper.setMandatPersonne(mandatPersonne);
                
                 operationMoyPay.getMandPersOperMoies().add(mandPersOper);   
                }
           }
        }else if (pouvoir.getTypePouvoir().equalsIgnoreCase(Constants.COD_TYPE_POUVOIR_COTITULAIRE)){
            
        
        }
       }
       //-----------------------------------------------------------------------------------------//
       //-------------------------- Fin mandat --------------------------------------------------//
       //---------------------------------------------------------------------------------------//
       
       
        //--------------------------------------------------------------//
        //---------- Affecter les conditions de banques ---------------//     
        //------------------------------------------------------------//
        operationMoyPay.setDatValOmp(DateHandler.strToDate(versementMemeAgenceForm.getVersementMemeAgenceView().getDatValVers()));
        montant = versementMemeAgenceForm.getVersementMemeAgenceView().getMntTvaVers().replace(".","");
        montant = montant.replace(" ","");
        operationMoyPay.setMontTvaOmp(Long.valueOf(montant));
        
      
       if ( versementMemeAgenceForm.getVersementMemeAgenceView().getListDetailOperMoyPai() != null &&  versementMemeAgenceForm.getVersementMemeAgenceView().getListDetailOperMoyPai().size()  > 0 ){
            operationMoyPay.setDetailOperMoyPaiements( versementMemeAgenceForm.getVersementMemeAgenceView().getListDetailOperMoyPai());
        }
       
       //---------------------------------------------------------------------------------//
       //---------- Affectation Caisse  -------------------------------------------------//
        AffectationCaisseStructure affCaisStr = new AffectationCaisseStructure();
        affCaisStr.setNumCaisAc(Long.valueOf(versementMemeAgenceForm.getNumeroCaisse()));
        operationMoyPay.setAffectationCaisseStructure(affCaisStr);
        
        PecVersementCmd pecVersementCmd =      new PecVersementCmd();
        operationMoyPay =   (OperationMoyPay) pecVersementCmd.execute(operationMoyPay);
        


        if (operationMoyPay.hasError()) {
            List listErreur = operationMoyPay.getErrors();
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

        } else
            versementMemeAgenceForm.getVersementMemeAgenceView().setOperationMoyPay(operationMoyPay);
            return mapping.findForward("confirmation");

    }

    public ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws IOException, 
                                                                             ServletException {

        try {
            VersementMemeAgenceForm versementMemeAgenceForm = 
                (VersementMemeAgenceForm)form;
            PersonneDemandeur personneDemandeur = 
                versementMemeAgenceForm.getPersonneDemandeur();
            Pouvoir pouvoir = 
                (Pouvoir)request.getSession().getAttribute("pouvoir"); /// structure de l'agent 
            personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);
            
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println("Erreur chargerPouvoir  " + e.getMessage());
            return mapping.findForward("error");
        }


    }
    
    public void chargerConditionBanque(ActionForm form){
     
        try{   
        VersementMemeAgenceForm versementMemeAgenceForm =  (VersementMemeAgenceForm) form;
        
        versementMemeAgenceForm.getVersementMemeAgenceView().setDatValVers(DateHandler.dateToStr(new Date()));
        
        Set listDetailOperMoyPai = new HashSet(); 
        DemandeConditionCmd cmd= new DemandeConditionCmd();    
       // DemandeCondition demCond = new DemandeCondition()
       // DemandeCondition( int codPrdPrd,int codOperOper,int numCcptCcpt,int codStrcStrc,int codPrdCpt,int codTpceTpce,String numPcePers,float montant, int nbUnites ,Date dateReference)
        //-----------------------------------------------------------------------------------------//
        //------------ Dans le cas d'une operation sans code du produit ---//
        //-------------PEM /PEL
         DemandeCondition demCond ;
        if ( (versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue() == (Constants.COD_PRD_PRD_PEL).intValue() )
            || (versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue() == (Constants.COD_PRD_PRD_PEM).intValue())){
            demCond = new DemandeCondition    
                    (versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue(),
                    Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeOperation()).intValue(), // code operation
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), // num sous compte
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodStrcStrc().intValue(), // code structure
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue(),   // code produit      
                     0, // code type piece
                     null, // num pièce
                     0,0,versementMemeAgenceForm.getInitialisationView().getDateOp());        
                   
        } else {
           
            demCond = new DemandeCondition    
                    (
                    Long.valueOf(versementMemeAgenceForm.getInitialisationView().getCodeOperation()).intValue(), // code operation
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getNumCcptCcpt().intValue(), // num sous compte
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodStrcStrc().intValue(), // code structure
                     versementMemeAgenceForm.getContratView().getContratCpt().getContratCptId().getCodPrdPrd().intValue(),   // code produit      
                     0, // code type piece
                     null, // num pièce
                     0,0,versementMemeAgenceForm.getInitialisationView().getDateOp());        
            
        }
   
        ListConditionVo v=(ListConditionVo)cmd.execute(demCond);
         if (v.hasError()){
             System.out.print(" erreur liste vide des conditions ou erreur ");
             
         }
        if(v.getListConditionBanque().size()==0) System.out.println("\n \n auccune condition de banque a appliquer \n");
        else{  
                 for(Iterator itCond = v.getListConditionBanque().iterator();itCond.hasNext();){
                       Condition condition = (Condition) itCond.next();
                       List conditionsBanque = condition.getConditionBanque();
                       
                       for(Iterator it = conditionsBanque.iterator();it.hasNext();){
                             
                              ConditionBanque conditionBanque = (ConditionBanque) it.next();                             
                              versementMemeAgenceForm.getVersementMemeAgenceView().setMntTvaVers
                              (Long.valueOf(Float.valueOf(conditionBanque.getTvaCalculePourCommisions()).longValue()).toString()); 
                             
                              List detailsConditionBanque = conditionBanque.getDetailConditionBanque();
                              for(Iterator itde = detailsConditionBanque.iterator();itde.hasNext();){
                                   DetailConditionBanque detailConditionBanque = (DetailConditionBanque) itde.next();
                                   DetailOperMoyPaiement detailOperMoyPaiement = new DetailOperMoyPaiement();
                                   NomencElemtCondition nomencElemtCondition   = new NomencElemtCondition();
                                  if(detailConditionBanque.getCodTecdTecd().equals("D")) {
                                     // garnir la date valeur
                                      System.out.println("new date "+new Date().toString());
                                
                                    versementMemeAgenceForm.getVersementMemeAgenceView().setDatValVers(detailConditionBanque.getDateValeur()); 
                                  }else{ 
                                       nomencElemtCondition.setCodNecdNecd(detailConditionBanque.getCodNecdNecd());
                                       detailOperMoyPaiement.setNomencElemtCondition(nomencElemtCondition);
                                       detailOperMoyPaiement.setCodTypDomp(detailConditionBanque.getCodTecdTecd());
                                       if(detailConditionBanque.getCodTecdTecd().equals("C")){   
                                         versementMemeAgenceForm.getVersementMemeAgenceView().setMntCommVers
                                         (Long.valueOf(Float.valueOf(detailConditionBanque.getValeurCommission()).longValue()).toString());                                            
                                         detailOperMoyPaiement.setMontValDomp(new Long(new Double(new Double(StrHandler.strWithoutBlanck(String.valueOf(detailConditionBanque.getValeurCommission()))).doubleValue()).longValue()));               
                                       }
                                       if(detailConditionBanque.getCodTecdTecd().equals("T")){                                            
                                         detailOperMoyPaiement.setMontValDomp(Long.valueOf(Float.valueOf(detailConditionBanque.getValValVael()).longValue()));
                                       }
                                       listDetailOperMoyPai.add(detailOperMoyPaiement);            
                                       
                                   }
                                   
                                                     
                              }
                       }
                 } 
                 
             versementMemeAgenceForm.getVersementMemeAgenceView().setListDetailOperMoyPai(listDetailOperMoyPai);     
         }
    } catch(Exception e  ){
        
     System.out.println("Erreur dans le chargement de condition de banque "+ e.toString())   ;
    }
  }
 


}


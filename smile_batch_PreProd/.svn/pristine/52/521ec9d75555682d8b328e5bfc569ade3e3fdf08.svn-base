package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;

import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;

import com.bna.commun.model.Personnel;

import com.bna.commun.model.Structure;
import com.bna.commun.model.TypePiece;

import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;


import com.bna.smile.model.domaineguichet.commande.GetListValidationVersementCmd;

import com.bna.smile.model.domaineguichet.commande.ValidationVersementMemeAgenceCmd;
import com.bna.smile.model.domaineguichet.dao.ListVersementVo;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.operationguichet.form.ValidationVersementForm;


import com.bna.smile.web.operationguichet.view.VersementView;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ValidationVersementAction extends DispatchAction {
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
     */
    public Context context = ContextHandler.getContext();
    ContratCpt contratCpt = new ContratCpt();


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ValidationVersementForm confirmationVersementForm = 
                (ValidationVersementForm)form;
          

            confirmationVersementForm.clearForm();
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
          
            confirmationVersementForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            confirmationVersementForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
            
            ListVersementVo listVersementVo = new ListVersementVo();
            //-----------------------------------------------------//
            //----- Versements même agence en attente --------------//
            //-----------------------------------------------------//
             listVersementVo.setTypeVersement(confirmationVersementForm.getTypeVersement());
            if (confirmationVersementForm.getTypeVersement().equals("M")){
                listVersementVo.setCodeOperation(Constants.COD_OPER_VERSEMENT.toString());
                listVersementVo.setCodeTache(Constants.TACHE_PRISE_EN_CHARGE);
                listVersementVo.setDateVersement(DateHandler.strToDate(DateHandler.dateJour())); 
                listVersementVo.setStructurInitiatrice(confirmationVersementForm.getInitialisationView().getCodeAgence());
                listVersementVo.setEtatVersement(Constants.COD_ATTENTE);
                //listVersementVo.setUserInitiateur(confirmationVersementForm.getInitialisationView().getNumMatrUser());
            
            //-----------------------------------------------------//
            //----- Versements autres agences en attente  ----------//
            //-----------------------------------------------------//
                 
            }else if(confirmationVersementForm.getTypeVersement().equals("A")){
                
                listVersementVo.setCodeOperation(Constants.COD_OPER_VERSEMENT_DEPLACE.toString());
                listVersementVo.setCodeTache(Constants.TACHE_PRISE_EN_CHARGE);
                listVersementVo.setDateVersement(DateHandler.strToDate(DateHandler.dateJour())); 
                listVersementVo.setStructurInitiatrice(confirmationVersementForm.getInitialisationView().getCodeAgence());
                listVersementVo.setEtatVersement(Constants.COD_ATTENTE);
           
            //-----------------------------------------------------//
            //----- Versements meme agences valides    --------------//
            //-----------------------------------------------------//
                     
            }else if(confirmationVersementForm.getTypeVersement().equals("CM")){
                    
                listVersementVo.setCodeOperation(Constants.COD_OPER_VERSEMENT.toString());
                listVersementVo.setCodeTache(Constants.TACHE_VALIDATION);
                listVersementVo.setDateVersement(DateHandler.strToDate(DateHandler.dateJour())); 
                listVersementVo.setStructurInitiatrice(confirmationVersementForm.getInitialisationView().getCodeAgence());
                listVersementVo.setEtatVersement(Constants.COD_VALIDATION);

           //-----------------------------------------------------//
           //----- Versements autres agences valides    ----------//
           //-----------------------------------------------------//
                         
            }else if(confirmationVersementForm.getTypeVersement().equals("CA")){
                        
               listVersementVo.setCodeOperation(Constants.COD_OPER_VERSEMENT_DEPLACE.toString());
              listVersementVo.setCodeTache(Constants.TACHE_VALIDATION);
               listVersementVo.setDateVersement(DateHandler.strToDate(DateHandler.dateJour())); 
               listVersementVo.setStructurInitiatrice(confirmationVersementForm.getInitialisationView().getCodeAgence());
               listVersementVo.setEtatVersement(Constants.COD_VALIDATION);
             
          //-----------------------------------------------------//
          //----- Versements Reçus autres agences valides   -----//
          //-----------------------------------------------------//
                                
          }else if(confirmationVersementForm.getTypeVersement().equals("CR")){
                               
             listVersementVo.setCodeOperation(Constants.COD_OPER_VERSEMENT_DEPLACE.toString());
             listVersementVo.setCodeTache(Constants.TACHE_VALIDATION);
             listVersementVo.setDateVersement(DateHandler.strToDate(DateHandler.dateJour())); 
             listVersementVo.setStructurReceptrice(confirmationVersementForm.getInitialisationView().getCodeAgence());
             listVersementVo.setEtatVersement(Constants.COD_VALIDATION);
                    
         }
            
            GetListValidationVersementCmd getListValidationVersementCmd = new GetListValidationVersementCmd();
            listVersementVo = (ListVersementVo)getListValidationVersementCmd.execute(listVersementVo);
            
            
            //------------------------------------------------------------------
            //----------- liste des Versement même agence attente --------------
            //------------------------------------------------------------------
            
            if (confirmationVersementForm.getTypeVersement().equals("M")){
                confirmationVersementForm.getInitialisationView().setLibelleOperation("Validation des versements même agence");
                if (listVersementVo.getListeVersements() != null && listVersementVo.getListeVersements().size()>0){
                 List listVersementView = new ArrayList();
                     for(Iterator it = listVersementVo.getListeVersements().iterator(); it.hasNext(); ){
                         OperationMoyPay operationMoyPay = (OperationMoyPay)it.next();
                         VersementView versementView = new VersementView();
                         versementView.setOperationMoyPay(operationMoyPay);
                         listVersementView.add(versementView);
                         confirmationVersementForm.getListeVersementMemeAgenceChoisi().add("");
                      }
                 confirmationVersementForm.setMessage(listVersementVo.getListeVersements().size()+ " versement(s) en attente de validation.");
                 confirmationVersementForm.setNombreVersMemeAgenceNonValide(listVersementVo.getListeVersements().size());
                 confirmationVersementForm.setListeVersementMemeAgence(listVersementView);
                } else {
                 confirmationVersementForm.setMessage(" Aucun versement à valider. ");
                }
                
                
            //------------------------------------------------------------------
            //-------- liste des Versements autres agences attente --------------
            //------------------------------------------------------------------
            
            } else if (confirmationVersementForm.getTypeVersement().equals("A")){
                confirmationVersementForm.getInitialisationView().setLibelleOperation("Validation des versements pour autres agences");
                if (listVersementVo.getListeVersements() != null && listVersementVo.getListeVersements().size()>0){
                 List listVersementView = new ArrayList();
                     for(Iterator it = listVersementVo.getListeVersements().iterator(); it.hasNext(); ){
                         OperationMoyPay operationMoyPay = (OperationMoyPay)it.next();
                         VersementView versementView = new VersementView();
                         versementView.setOperationMoyPay(operationMoyPay);
                         listVersementView.add(versementView);
                         confirmationVersementForm.getListeVersementMemeAgenceChoisi().add("");
                      }
                 confirmationVersementForm.setMessage(listVersementVo.getListeVersements().size()+ " versement(s) en attente de validation.");
                 confirmationVersementForm.setNombreVersAutreAgenceNonValide(listVersementVo.getListeVersements().size());
                 confirmationVersementForm.setListeVersementAutresAgences(listVersementView);
                } else {
                 confirmationVersementForm.setMessage(" Aucun versement à valider. ");
                }
            
            //-------------------------------------------------------------------
            //-------- Consultation des Versements autres agences validés --------------
            //-------------------------------------------------------------------
            } else if (confirmationVersementForm.getTypeVersement().equals("CM")) {
                confirmationVersementForm.getInitialisationView().setLibelleOperation("Consultation des versements même agence");
                if (listVersementVo.getListeVersements() != null && listVersementVo.getListeVersements().size()>0){
                 List listVersementView = new ArrayList();
                     for(Iterator it = listVersementVo.getListeVersements().iterator(); it.hasNext(); ){
                         OperationMoyPay operationMoyPay = (OperationMoyPay)it.next();
                         VersementView versementView = new VersementView();
                         versementView.setOperationMoyPay(operationMoyPay);
                         listVersementView.add(versementView);
                         
                      }
                 confirmationVersementForm.setMessage(listVersementVo.getListeVersements().size()+ " versement(s) validé(s).");
                 confirmationVersementForm.setNombreVersAutreAgenceNonValide(listVersementVo.getListeVersements().size());
                 confirmationVersementForm.setListeVersementMemeAgenceValide(listVersementView);
                } else {
                 confirmationVersementForm.setMessage(" Aucun versement validé. ");
                }
             
             //-------------------------------------------------------------------
             //-------- Consultation des Versements autres agences validés --------------
             //-------------------------------------------------------------------
            } else if (confirmationVersementForm.getTypeVersement().equals("CA")) {
                confirmationVersementForm.getInitialisationView().setLibelleOperation("Consultation des versements autres agences");
                  if (listVersementVo.getListeVersements() != null && listVersementVo.getListeVersements().size()>0){
                   List listVersementView = new ArrayList();
                      for(Iterator it = listVersementVo.getListeVersements().iterator(); it.hasNext(); ){
                             OperationMoyPay operationMoyPay = (OperationMoyPay)it.next();
                             VersementView versementView = new VersementView();
                             versementView.setOperationMoyPay(operationMoyPay);
                             listVersementView.add(versementView);
                             
                          }
                    confirmationVersementForm.setMessage(listVersementVo.getListeVersements().size()+ " versement(s) validé(s).");
                    confirmationVersementForm.setNombreVersAutreAgenceNonValide(listVersementVo.getListeVersements().size());
                    confirmationVersementForm.setListeVersementAutresAgencesValide(listVersementView);
                  } else {
                   confirmationVersementForm.setMessage(" Aucun versement validé. ");
                  }
               
                 
                //-------------------------------------------------------------------
                //-------- Consultation des Versements autres agences validés --------------
                //-------------------------------------------------------------------
                } else if (confirmationVersementForm.getTypeVersement().equals("CR")) {
                   confirmationVersementForm.getInitialisationView().setLibelleOperation("Consultation des versements reçus autres agences");
                     if (listVersementVo.getListeVersements() != null && listVersementVo.getListeVersements().size()>0){
                      List listVersementView = new ArrayList();
                         for(Iterator it = listVersementVo.getListeVersements().iterator(); it.hasNext(); ){
                                OperationMoyPay operationMoyPay = (OperationMoyPay)it.next();
                                VersementView versementView = new VersementView();
                                versementView.setOperationMoyPay(operationMoyPay);
                                listVersementView.add(versementView);
                                
                             }
                       confirmationVersementForm.setMessage(listVersementVo.getListeVersements().size()+ " versement(s) reçu(s), validé(s).");
                       confirmationVersementForm.setNombreVersAutreAgenceNonValide(listVersementVo.getListeVersements().size());
                       confirmationVersementForm.setListeVersementRecuAutresAgencesValide(listVersementView);
                     } else {
                      confirmationVersementForm.setMessage(" Aucun versement reçu. ");
                     }
                    }       
                 
           
            
            return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            erreur.setCode("200");
            erreur.setDescription("Une erreur est survenu dans confirmationVersementForm - initierPage : " + 
                                  e.toString());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);

            return mapping.findForward("success");
        }
    }


   

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        ActionMessages actionMessages = new ActionMessages();
        ValidationVersementForm validationVersementForm = 
            (ValidationVersementForm)form;
        try {
            validationVersementForm.getListeVersementMemeAgenceChoisi().clear();
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

        ValidationVersementForm validationVersementForm = 
            (ValidationVersementForm)form;
            
          //--------------------------------------------//
         // validation de l'operation moy de payement  //
        //--------------------------------------------//   
        
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        
        
      List listeDesOPerations = new ArrayList();
      //------------------------------------------------------------------//
      //-------------------- Validation Meme agence ----------------------//
      //------------------------------------------------------------------//
      if (validationVersementForm.getTypeVersement().equals("M")){
          for(Iterator it = validationVersementForm.getListeVersementMemeAgenceChoisi().iterator() ; it.hasNext(); ) {
              String numeroOperation = (String)it.next();
              
              if (!numeroOperation.equals("")){
               for(Iterator itVerMemAgence = validationVersementForm.getListeVersementMemeAgence().iterator() ; itVerMemAgence.hasNext(); ) {
                  VersementView versementView = (VersementView) itVerMemAgence.next();
                 
                  if (versementView.getNumeroOperation().equals(numeroOperation)){
                      listeDesOPerations.add(versementView.getOperationMoyPay());
                  }
               }//fin for itVerMemAgence
              }//fin if 
          }// fin for it
      
     }else{ // Fin if cas validation Meme agence
      
      //------------------------------------------------------------------//
      //----------------- Validation pour Autres agences  ----------------//
      //------------------------------------------------------------------//
       for(Iterator it = validationVersementForm.getListeVersementMemeAgenceChoisi().iterator() ; it.hasNext(); ) {
           String numeroOperation = (String)it.next();
           
           if (!numeroOperation.equals("")){
            for(Iterator itVerMemAgence = validationVersementForm.getListeVersementAutresAgences().iterator() ; itVerMemAgence.hasNext(); ) {
               VersementView versementView = (VersementView) itVerMemAgence.next();
              
               if (versementView.getNumeroOperation().equals(numeroOperation)){
                   listeDesOPerations.add(versementView.getOperationMoyPay());
               }
            }//fin for itVerMemAgence
           }//fin if 
       }// fin for it
     
     }  
     
       
       OperationMoyPay operationMoyPay =new OperationMoyPay()  ;
       ValidationVersementMemeAgenceCmd  validationVersementMemeAgenceCmd = new ValidationVersementMemeAgenceCmd();
       
       for(Iterator itOper = listeDesOPerations.iterator() ; itOper.hasNext(); ) {
         operationMoyPay = (OperationMoyPay) itOper.next();
            Personnel personnelValideur = new Personnel();
            personnelValideur.setNumMatrUser(validationVersementForm.getInitialisationView().getNumMatrUser());
            operationMoyPay.setPersonnelValideur(personnelValideur);
         validationVersementMemeAgenceCmd.execute(operationMoyPay);
        }
        
       validationVersementForm.setNombreVersementValides(Integer.valueOf(listeDesOPerations.size()));

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
            return mapping.findForward("confirmation");

    }

  
    /**
     * methode de validation du versement choisi
     * 
     */
     public ActionForward validationVersement(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessagess = new ActionMessages();

        ValidationVersementForm validationVersementForm =  (ValidationVersementForm)form;
        
        OperationMoyPay operationMoyPay =validationVersementForm.getVersementChoisi().getOperationMoyPay()  ;
         
        ValidationVersementMemeAgenceCmd  validationVersementMemeAgenceCmd = new ValidationVersementMemeAgenceCmd();
        
        Personnel personnelValideur = new Personnel();
        personnelValideur.setNumMatrUser(validationVersementForm.getInitialisationView().getNumMatrUser());
        operationMoyPay.setPersonnelValideur(personnelValideur);
        operationMoyPay = (OperationMoyPay) validationVersementMemeAgenceCmd.execute(operationMoyPay);
    
        if (operationMoyPay.hasError()) {
             List listErreur = operationMoyPay.getErrors();
             for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                 com.oxia.fwk.core.Error erreur = 
                     (com.oxia.fwk.core.Error)it.next();
                 ActionMessage actionMessage = 
                     new ActionMessage("exception.generique", 
                                       erreur.getDescription());
                 actionMessagess.add("Erreur ", actionMessage);
             }
             this.saveMessages(request, actionMessagess);
             return mapping.findForward("error");

         } else {
              
              return mapping.findForward("confirmation");
          }

     }
}

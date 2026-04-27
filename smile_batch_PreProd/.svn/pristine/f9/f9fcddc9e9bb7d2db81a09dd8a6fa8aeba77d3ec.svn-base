package com.bna.smile.web.operationguichet.actions;


import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.Devise;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.MontantMiseDipositionId;

import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;

import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;


import com.bna.smile.model.domaineguichet.commande.GetListMontantMADCmd;
import com.bna.smile.model.domaineguichet.commande.InsertMontantMADCmd;

import com.bna.smile.model.domaineguichet.commande.ValidationMiseAdispositionCmd;
import com.bna.smile.model.domaineguichet.model.ListMiseAdispositionVo;
import com.bna.smile.web.commun.model.ParamAgence;


import com.bna.smile.web.operationguichet.form.ValidationMiseAdispositionForm;


import com.bna.smile.web.operationguichet.view.ValidationMiseAdispositionView;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

public class ValidationMiseAdispositionAction extends DispatchAction {
    /**
     * <B> Action de la page  transfertContratCpt.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.operationguichet.actions
     *
     * @author  Mdimagh Med Lassaad 
     * @version le 05/11/2007
     */
    public Context context = ContextHandler.getContext();
    ContratCpt contratCpt = new ContratCpt();


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        GetListMontantMADCmd getListMontantMADCmd = new GetListMontantMADCmd();
        ListMiseAdispositionVo listMiseAdispositionVo = new ListMiseAdispositionVo();
        
        try {
            ValidationMiseAdispositionForm  validationMiseAdispositionForm = 
                (ValidationMiseAdispositionForm) form;
           
            validationMiseAdispositionForm.clearForm();

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
          
            validationMiseAdispositionForm.getInitialisationView().setDateOp(new Date());
            validationMiseAdispositionForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            validationMiseAdispositionForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
          
            if (validationMiseAdispositionForm.getTypeMiseAdisposition().equalsIgnoreCase(Constants.CODE_MISE_A_DISPOSITION)){
            
             validationMiseAdispositionForm.getInitialisationView().setLibelleOperation("Validation des versements Mises à disposition");
                
                        
            }else if (validationMiseAdispositionForm.getTypeMiseAdisposition().equalsIgnoreCase(Constants.CODE_MONEY_GRAM)){
             validationMiseAdispositionForm.getInitialisationView().setLibelleOperation("Validation des versements Moneygram");
                         
            }
            
            listMiseAdispositionVo.setTypeMAD(validationMiseAdispositionForm.getTypeMiseAdisposition());
            listMiseAdispositionVo.setDateMAD(validationMiseAdispositionForm.getInitialisationView().getDateOp());
            listMiseAdispositionVo.setEtatMAD(Constants.COD_ETAT_MISE_DISPOSITION_ATTENTE);
            listMiseAdispositionVo.setStructureInitiatrice(Long.valueOf( validationMiseAdispositionForm.getInitialisationView().getCodeAgence()));
            
            listMiseAdispositionVo =(ListMiseAdispositionVo)  getListMontantMADCmd.execute(listMiseAdispositionVo )   ;
            
            validationMiseAdispositionForm.getListMiseAdisposition().clear();
            //----------------------------------------------------------------------------------//
            //------------ Remplir les mises à disposition ----------------------//
             List l = new ArrayList();
             for (Iterator it = listMiseAdispositionVo.getListMiseAdisposition().iterator(); it.hasNext();){
                MontantMiseDiposition montantMAD = (MontantMiseDiposition) it.next();
                ValidationMiseAdispositionView montantMiseDipositionView = new ValidationMiseAdispositionView();
                montantMiseDipositionView.setMontantMiseDiposition(montantMAD);
                montantMiseDipositionView.setCodTpemMmad(montantMAD.getCodTpemMmad().toString());
                montantMiseDipositionView.setCodTpemMmad(montantMAD.getCodTpemMmad().toString());
                montantMiseDipositionView.setNumNpemMmad(montantMAD.getNumNpemMmad());
                montantMiseDipositionView.setNomEmetMmad(montantMAD.getNomEmetMmad());
                montantMiseDipositionView.setNomPremMmad(montantMAD.getNomPremMmad());
               // montantMiseDipositionView.setco  (montantMAD.getCodTpceMmad().toString())
               // montantMiseDipositionView.setNumMmadMmad(montantMAD.getMontantMiseDipositionId().getNumMmadMmad().toString());
                montantMiseDipositionView.setMontantDinars(StrHandler.formatMontant(montantMAD.getMontMontMmad(),3));
                //montantMAD.getMontMontMmad().toString().toString());
                //------- Structure 
                montantMiseDipositionView.setCodStrcStrcE(montantMAD.getStructureByCodEmetStrc().getCodStrcStrc().toString());
                montantMiseDipositionView.setLibStrcStrcE(montantMAD.getStructureByCodEmetStrc().getLibStrcStrc());
                montantMiseDipositionView.setDatMmadMmad(DateHandler.dateToStr(montantMAD.getDatMmadMmad()));
                l.add(montantMiseDipositionView);
                 
            }
            validationMiseAdispositionForm.setListMiseAdisposition(l);
          
          
           return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            erreur.setCode("200");
            erreur.setDescription("Une erreur est survenu dans ValidationMiseAdispositionAction / InitierPage : " + 
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
        ValidationMiseAdispositionForm validationMiseAdispositionForm = 
            (ValidationMiseAdispositionForm)form;
        try {
            String code = validationMiseAdispositionForm.getTypeMiseAdisposition();
            
            validationMiseAdispositionForm.clearForm();
          
            validationMiseAdispositionForm.setTypeMiseAdisposition(code);
            return mapping.findForward("success");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action : annuler  " + e.getMessage());
            return mapping.findForward("error");
        }

    }
 


    public ActionForward validationMiseAdisposition(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        ActionMessages actionMessages = new ActionMessages();

        ValidationMiseAdispositionForm validationMiseAdispositionForm = 
            (ValidationMiseAdispositionForm)form;
            

        MontantMiseDiposition montantMiseDiposition ;
        montantMiseDiposition = validationMiseAdispositionForm.getMiseAdispositionChoisi().getMontantMiseDiposition();
        
      
        Personnel personnel = new Personnel();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
       personnel.setNumMatrUser(paramAgence.getNumMatrUser());
       montantMiseDiposition.setPersonnelVersement(personnel);
     
        ValidationMiseAdispositionCmd validationMiseAdisposition = new ValidationMiseAdispositionCmd ();
        montantMiseDiposition = (MontantMiseDiposition) validationMiseAdisposition.execute(montantMiseDiposition);
       
      
       if (montantMiseDiposition.hasError()) {
            List listErreur = montantMiseDiposition.getErrors();
            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                com.oxia.fwk.core.Error erreur = 
                    (com.oxia.fwk.core.Error)it.next();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
            }
            this.saveMessages(request, actionMessages);
            return mapping.findForward("success");

        } else
                return mapping.findForward("confirmation");

    }

 
  

}

package com.bna.smile.web.operationguichet.actions;


import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.Devise;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.MontantMiseDipositionId;

import com.bna.commun.model.Structure;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;


import com.bna.smile.model.domaineguichet.commande.AjoutVersementMisAdispositionCmd;
import com.bna.smile.model.domaineguichet.commande.InsertMontantMADCmd;

import com.bna.smile.web.commun.model.ParamAgence;


import com.bna.smile.web.commun.view.InitialisationView;
import com.bna.smile.web.operationguichet.form.VersementMiseAdispositionForm;


import com.bna.smile.web.operationguichet.view.VersementMiseAdispositionView;

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

public class VersementMiseAdispositionAction extends DispatchAction {
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
        try {
            VersementMiseAdispositionForm  versementMiseAdispositionForm = 
                (VersementMiseAdispositionForm) form;
            String codeOperation = 
            versementMiseAdispositionForm.getInitialisationView().getCodeOperation();
            String typeOperation = versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTypeMmad();
            
            versementMiseAdispositionForm.clearForm();

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
          
            versementMiseAdispositionForm.getInitialisationView().setCodeOperation(codeOperation);
            versementMiseAdispositionForm.getInitialisationView().setDateOp(new Date());
            versementMiseAdispositionForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            versementMiseAdispositionForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
          
            versementMiseAdispositionForm.getVersementMiseAdispositionView().setCodStrcStrcE(paramAgence.getCodStrcStrc().toString());
            versementMiseAdispositionForm.getVersementMiseAdispositionView().setCodTypeMmad(typeOperation);
           
            if (versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTypeMmad().equals(Constants.CODE_MISE_A_DISPOSITION)){
             versementMiseAdispositionForm.getInitialisationView().setLibelleOperation("Versement Mise à disposition");
            }else if (versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTypeMmad().equals(Constants.CODE_MONEY_GRAM)){
             versementMiseAdispositionForm.getInitialisationView().setLibelleOperation("Versement Moneygram");
           }
           // versementMiseAdispositionForm.getVersementMiseAdispositionView().setDatMmadMmad(DateHandler.DateFormat(new Date()));
           
           return mapping.findForward("success");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            erreur.setCode("200");
            erreur.setDescription("Une erreur est survenu dans VersementMiseAdispositionAction : " + 
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
        VersementMiseAdispositionForm versementMiseAdispositionForm = 
            (VersementMiseAdispositionForm)form;
        try {
            String code = versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTypeMmad();
            
            versementMiseAdispositionForm.clearForm();
          
            versementMiseAdispositionForm.getVersementMiseAdispositionView().setCodTypeMmad(code);
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

        VersementMiseAdispositionForm versementMiseAdispositionForm = 
            (VersementMiseAdispositionForm)form;
            
      
        MontantMiseDiposition montantMiseDiposition = new MontantMiseDiposition();
        
       //---- Emetteur 
        String  ad = versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTpemMmad();
        Long a = Long.valueOf(ad) ;
        montantMiseDiposition.setCodTpemMmad(a);
        montantMiseDiposition.setNumNpemMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNumNpemMmad());
        montantMiseDiposition.setNomEmetMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNomEmetMmad());
        montantMiseDiposition.setNomPremMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNomPremMmad());
        
        //---- Beneficiaire
        montantMiseDiposition.setCodTpceMmad(Long.valueOf( versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTpceMmad()));
        montantMiseDiposition.setNumPceMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNumPceMmad());
        montantMiseDiposition.setNomNomMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNomNomMmad());
        montantMiseDiposition.setNomPrnMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getNomPrnMmad());
        
        //---- Montant
        String montant = versementMiseAdispositionForm.getVersementMiseAdispositionView().getMontantDinars().replace(".","");
                 montant = montant.replace(" ","");
         montantMiseDiposition.setMontMontMmad(Long.valueOf(montant));
        
        //--- Devise
        Devise devise = new Devise();
        devise.setCodDevDev(Long.valueOf(versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodDevDev()));
        montantMiseDiposition.setDevise(devise);
       
        if (!devise.getCodDevDev().equals(Constants.COD_DEV_DINAR)){
        montant = versementMiseAdispositionForm.getVersementMiseAdispositionView().getMontantDevise().replace(".","");
        montant = montant.replace(" ","");
        montantMiseDiposition.setMontDevMmad(Long.valueOf(montant));
        
        montant = versementMiseAdispositionForm.getVersementMiseAdispositionView().getTauxCourMmad().replace(".","");
        montant = montant.replace(" ","");
        montantMiseDiposition.setTauxCourMmad(Long.valueOf(montant));
        }
        
        //------ Structures 
        Structure structureEmettrice = new Structure();
        structureEmettrice.setCodStrcStrc(Long.valueOf(versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodStrcStrcE()));
        montantMiseDiposition.setStructureByCodEmetStrc(structureEmettrice);
      
        /*
        Structure structureReceptrice = new Structure();
        structureReceptrice.setCodStrcStrc(Long.valueOf(versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodStrcStrcR()));
        montantMiseDiposition.setStructureByCodRecpStrc(structureReceptrice);
        */
       //-- Date 
        montantMiseDiposition.setDatMmadMmad(versementMiseAdispositionForm.getInitialisationView().getDateOp());
       
       //-- Clé
       MontantMiseDipositionId montantMiseDipositionId = new MontantMiseDipositionId();
       montantMiseDipositionId.setCodTypeMmad(versementMiseAdispositionForm.getVersementMiseAdispositionView().getCodTypeMmad());
       montantMiseDiposition.setMontantMiseDipositionId(montantMiseDipositionId);  
      
       AjoutVersementMisAdispositionCmd  ajoutVersementMisAdispositionCmd = new AjoutVersementMisAdispositionCmd();
       montantMiseDiposition = (MontantMiseDiposition) ajoutVersementMisAdispositionCmd.execute(montantMiseDiposition);
      
      
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

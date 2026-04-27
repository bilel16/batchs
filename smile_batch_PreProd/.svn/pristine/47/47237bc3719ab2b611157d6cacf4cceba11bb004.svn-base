package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.MontantMiseDipositionId;
import com.bna.smile.model.domaineguichet.commande.GetMiseAdispositionByPrimaryKeyCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.ValidationMiseAdispositionForm;

import com.bna.smile.web.operationguichet.view.ValidationMiseAdispositionView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * @author  Mdimagh Med Lassaad 
 * @version le 12/09/2007
 */
public class DetailMiseAdispositionAction extends DispatchAction {
    public DetailMiseAdispositionAction() {
    }
    
    public ActionForward detailMiseAdisposition(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        ActionMessages actionMessagess = new ActionMessages();

        ValidationMiseAdispositionForm validationMiseAdispositionForm =  (ValidationMiseAdispositionForm)form;
        
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
       
       MontantMiseDipositionId mId = new  MontantMiseDipositionId();
       mId.setCodTypeMmad(validationMiseAdispositionForm.getTypeMiseAdisposition());
       mId.setNumMmadMmad(validationMiseAdispositionForm.getNumeroOperation());
       
       MontantMiseDiposition montantMAD = new MontantMiseDiposition();
       montantMAD.setMontantMiseDipositionId(mId);
       
       
       GetMiseAdispositionByPrimaryKeyCmd getMontantMADByIdCmd = new GetMiseAdispositionByPrimaryKeyCmd();
        montantMAD = (MontantMiseDiposition)    getMontantMADByIdCmd.execute( montantMAD);
       
        ValidationMiseAdispositionView mView = new  ValidationMiseAdispositionView();
        mView.setMontantMiseDiposition(montantMAD);
        validationMiseAdispositionForm.setMiseAdispositionChoisi(mView);
       
       return mapping.findForward("detailMiseAdisposition");
                
            /*if (montantMAD.hasError()) {
                 List listErreur = montantMAD.getErrors();
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
                 return mapping.findForward("detailMiseAdisposition");

            }*/
   }
        
}

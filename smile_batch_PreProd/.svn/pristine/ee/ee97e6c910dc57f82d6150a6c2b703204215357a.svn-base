package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domaineguichet.commande.ValidationVersementMemeAgenceCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.ValidationVersementForm;

import com.bna.smile.web.operationguichet.view.VersementView;

import java.io.IOException;

import java.util.ArrayList;
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
public class DetailVersementAction extends DispatchAction {
    public DetailVersementAction() {
    }
    
    
    
    public ActionForward detailVersement(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws IOException, 
                                                                         ServletException {
        ActionMessages actionMessagess = new ActionMessages();

        ValidationVersementForm validationVersementForm =  (ValidationVersementForm)form;
        
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        
        OperationMoyPay operationMoyPay =new OperationMoyPay()  ;
        List liseTemp = new ArrayList();
        
      //------------------------------------------------------------------//
      //--------------------  Meme agence ----------------------//
      //------------------------------------------------------------------//
      if (validationVersementForm.getTypeVersement().equals("M")){
         liseTemp = validationVersementForm.getListeVersementMemeAgence();
      } else if (validationVersementForm.getTypeVersement().equals("A")){
         liseTemp = validationVersementForm.getListeVersementAutresAgences() ;
     } else if (validationVersementForm.getTypeVersement().equals("CM")){
         liseTemp = validationVersementForm.getListeVersementMemeAgenceValide()  ;
     }else if (validationVersementForm.getTypeVersement().equals("CA")){
         liseTemp = validationVersementForm.getListeVersementAutresAgencesValide() ;
     }else if (validationVersementForm.getTypeVersement().equals("CR")){
         liseTemp = validationVersementForm.getListeVersementRecuAutresAgencesValide() ;
     }
        
        
        
        if (liseTemp.size()>0){
          for(Iterator it = liseTemp.iterator() ; it.hasNext(); ) {
              VersementView versementView = (VersementView)it.next();
              
              if (validationVersementForm.getNumeroOperationChoisi().equals(versementView.getOperationMoyPay().getNumOperOmp())){
                  operationMoyPay = versementView.getOperationMoyPay();
              }//fin if
          }// fin for it
        }
    
     
      VersementView versement = new VersementView();
      versement.setOperationMoyPay(operationMoyPay);
      validationVersementForm.setVersementChoisi(versement);
      validationVersementForm.setNbrDecOper(operationMoyPay.getDevise().getNbrDecDev().toString());
      validationVersementForm.setNbrDecCpt(operationMoyPay.getContratCpt().getDevise().getNbrDecDev().toString());
      validationVersementForm.getVersementChoisi().setDateOperation(DateHandler.dateToStr(operationMoyPay.getDatOperOmp()) );
    
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

        } else
            return mapping.findForward("detailVersement");

    }
    
    
  
}

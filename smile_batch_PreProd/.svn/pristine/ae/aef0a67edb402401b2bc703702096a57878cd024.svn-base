package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.constant.Constants;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domaineguichet.commande.GetOperationMoyPayCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.ConsultationGuichetRetraitForm;
import com.bna.smile.web.operationguichet.form.GuichetRetraitDeplaceForm;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ConsultGuichetRetraitAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @author  BOUSSEN Youssef 
     * @version le 30/10/2007
     */
     public Context context = ContextHandler.getContext();
     public ActionForward initierPage(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {
            ConsultationGuichetRetraitForm consultationGuichetRetraitForm = (ConsultationGuichetRetraitForm)form;
            if (consultationGuichetRetraitForm.getListRetraitMemeAg()!=null)
            consultationGuichetRetraitForm.getListRetraitMemeAg().clear();
            if (consultationGuichetRetraitForm.getListRetraitDepl()!=null)
            consultationGuichetRetraitForm.getListRetraitDepl().clear();
            if (consultationGuichetRetraitForm.getListRetraitEmis()!=null)
            consultationGuichetRetraitForm.getListRetraitEmis().clear();
            
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            consultationGuichetRetraitForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            consultationGuichetRetraitForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());                
            //guichetRetraitDeplaceForm.setDateVal(guichetRetraitDeplaceForm.getInitialisationView().getDateActuelle());
            consultationGuichetRetraitForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_DEPL_EMIS.toString());
            
           //? guichetRetraitForm.getInitialisationView().setReqCode(guichetRetraitForm.getReqCode());
            // saisir le N° de l'opération
             PrimitiveVO primitiveVO = new PrimitiveVO();
             primitiveVO.setVLong(paramAgence.getCodStrcStrc());
             
                primitiveVO.setVString(Constants.COD_ATTENTE);
                primitiveVO.setVDouble(Double.valueOf(0)); /// agence receprtice               
                GetOperationMoyPayCmd getOperationMoyPayCmd = new GetOperationMoyPayCmd();
                Listes listes = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                consultationGuichetRetraitForm.setListRetraitEmis(listes.getList());            


                primitiveVO.setVString(Constants.COD_PREVALID);
                primitiveVO.setVDouble(Double.valueOf(1)); /// agence emettrice               
                Listes listes2 = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                consultationGuichetRetraitForm.setListRetraitDepl(listes2.getList());

                primitiveVO.setVString(Constants.COD_VALIDATION);
                primitiveVO.setVDouble(Double.valueOf(0)); /// agence emettrice               
                Listes listes3 = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                if (listes3.getList()!=null)
                consultationGuichetRetraitForm.setListRetraitMemeAg(listes3.getList());
                primitiveVO.setVDouble(Double.valueOf(3)); /// agence emettrice (deplacé < mont limite)              
                Listes listes31 = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                if (listes31.getList()!=null)
                consultationGuichetRetraitForm.getListRetraitMemeAg().addAll(listes31.getList());

                primitiveVO.setVString(Constants.COD_ATTENTE);
                primitiveVO.setVDouble(Double.valueOf(2)); /// agence emettrice               
                Listes listes4 = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                if (listes4.getList()!=null)
                consultationGuichetRetraitForm.setListRetraitInitie(listes4.getList());
            
            return mapping.findForward("success");

        } catch (Exception e) {
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }
     }




     public ActionForward detailRetrait(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
     /// appeler la formr d'affichage du retrait
        try {
            GuichetRetraitDeplaceForm guichetRetraitDeplaceForm = (GuichetRetraitDeplaceForm)form;
            
     //            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent
            
            if (guichetRetraitDeplaceForm.getCodetrait().equalsIgnoreCase("VRDEM")){ /// préValidation  retrait deplacé emis par une autre agence
             return mapping.findForward("successEmis");
            }else{
            //if (guichetRetraitDeplaceForm.getCodetrait().equalsIgnoreCase("VRDRE")){/// validation  retrait deplacé recu et déja préValidé
             return mapping.findForward("successRecu");
            }

        } catch (Exception e) {
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }
     }


     
     }

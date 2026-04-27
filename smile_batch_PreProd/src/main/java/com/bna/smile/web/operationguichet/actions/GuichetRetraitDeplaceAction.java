package com.bna.smile.web.operationguichet.actions;

import com.bna.commun.util.ContextHandler;

import com.bna.commun.constant.Constants;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineguichet.commande.GetOperationMoyPayCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.GuichetRetraitDeplaceForm;
import com.bna.smile.web.operationguichet.form.GuichetRetraitForm;

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

public class GuichetRetraitDeplaceAction extends DispatchAction {
    /**
     * <B> Action de la page  transfertContratCpt.jsp  </B>
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * Nom du package : com.bna.smile.web.operationguichet.actions
     *
     * @author  BOUSSEN Youssef 
     * @version le 18/10/2007
     */
    public Context context = ContextHandler.getContext();
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {
            GuichetRetraitDeplaceForm guichetRetraitDeplaceForm = (GuichetRetraitDeplaceForm)form;
            if (guichetRetraitDeplaceForm.getListRetraitEmis()!=null)
            guichetRetraitDeplaceForm.getListRetraitEmis().clear();
            if (guichetRetraitDeplaceForm.getListRetraitRecu()!=null)
            guichetRetraitDeplaceForm.getListRetraitRecu().clear();
            
            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            
            guichetRetraitDeplaceForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
            guichetRetraitDeplaceForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());                
            //guichetRetraitDeplaceForm.setDateVal(guichetRetraitDeplaceForm.getInitialisationView().getDateActuelle());
            guichetRetraitDeplaceForm.getInitialisationView().setCodeOperation(Constants.COD_OPER_RETRAIT_DEPL_EMIS.toString());
            
           //? guichetRetraitForm.getInitialisationView().setReqCode(guichetRetraitForm.getReqCode());
            // saisir le N° de l'opération
             PrimitiveVO primitiveVO = new PrimitiveVO();
             primitiveVO.setVLong(paramAgence.getCodStrcStrc());
             
            if (guichetRetraitDeplaceForm.getCodetrait().equalsIgnoreCase("VRDRE")){ /// préValidation  retrait deplacé emis par une autre agence
                primitiveVO.setVString(Constants.COD_ATTENTE);
                primitiveVO.setVDouble(Double.valueOf(0)); /// agence receprtice               
                GetOperationMoyPayCmd getOperationMoyPayCmd = new GetOperationMoyPayCmd();
                Listes listes = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                guichetRetraitDeplaceForm.setListRetraitEmis(listes.getList());            


            }
            if (guichetRetraitDeplaceForm.getCodetrait().equalsIgnoreCase("VRDEM")){/// validation  retrait deplacé recu et déja préValidé
                primitiveVO.setVString(Constants.COD_PREVALID);
                primitiveVO.setVDouble(Double.valueOf(1)); /// agence emettrice               
                GetOperationMoyPayCmd getOperationMoyPayCmd = new GetOperationMoyPayCmd();
                Listes listes = (Listes)getOperationMoyPayCmd.execute(primitiveVO);
                guichetRetraitDeplaceForm.setListRetraitRecu(listes.getList());
            }
            
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

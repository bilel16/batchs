package com.bna.smile.web.change.actions;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.placement.forms.AutresOperationsPlacementForm;

import com.oxia.fwk.context.Context;

import java.io.IOException;

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

public class CreationMouvementChangeAction extends DispatchAction {
 
    private static final

    Logger logger = Logger.getLogger(CreationMouvementChangeAction.class);

    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        ActionMessages actionMessages = new ActionMessages();

        Context context = ContextHandler.getContext();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
             StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CHANGE);
             boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
             
             return mapping.findForward("");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("L'initialisation du domaine Change a été interrompue, Veuillez transmettre ce message à l'équipe informatique: ");
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            logger.error(text.toString(), e);
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }

    }
    
}

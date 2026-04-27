package com.bna.smile.web.compensation.actions;


import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Produit;
import com.bna.commun.model.RegleGestionContratId;
import com.bna.commun.model.TypeRegleContrat;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratEtatCmd;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetRegleGestionContratCmd;
import com.bna.smile.model.domainecommun.model.ContratCptMandat;
import com.bna.smile.model.domainecommun.model.MandatPersonneMandat;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.compensation.forms.GestionIncidentpaiementForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class GestionIncidentPaiementAction extends DispatchAction{
    
    
   public ActionForward debut(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

        GestionIncidentpaiementForm gestionIncidentpaiementForm = 
            (GestionIncidentpaiementForm)form;
        ActionMessages actionMessages = new ActionMessages();
     try{
        ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent         
        
           
        
        return mapping.findForward("success");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationDemandeChequeAction / Dispatch Action :initierPage ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
           // this.saveMessages(request, actionMessages);
            return mapping.findForward("error");
        }
      

    }    
    public ActionForward rechercherContrat(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {


        ActionMessages actionMessages = new ActionMessages();

        GestionIncidentpaiementForm gestionIncidentpaiementForm = 
            (GestionIncidentpaiementForm)form;
        /*******recherche du contrat**********/
        try {

            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(Long.valueOf(gestionIncidentpaiementForm.getContratView().getCodStrcStrc()));
            contratCptId.setCodPrdPrd(Long.valueOf(gestionIncidentpaiementForm.getContratView().getCodPrdPrd()));
            contratCptId.setNumCcptCcpt(Long.valueOf(gestionIncidentpaiementForm.getContratView().getNumCcptCcpt()));
            //recherche contrat
            GetContratEtatCmd getContratEtatCmd = 
                new GetContratEtatCmd();
                ContratCptMandat contratCptMandat=
           
                (ContratCptMandat)getContratEtatCmd.execute(contratCptId);


            if (contratCptMandat.getContratCpt() != null ) {
                gestionIncidentpaiementForm.getContratView().setMessageContratCpt("Contrat Inéxistant, veuillez verifier votre saisie, SVP");

                //------- Verifier s'il y a une restriction sur le contrat -------------------//
            } else {
                gestionIncidentpaiementForm.getContratView().setContratCpt(contratCptMandat.getContratCpt());
            }
               
            return mapping.findForward("success");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur action ---------- " + e.getMessage());
            return mapping.findForward("error");
        }

    }  
    
   
}

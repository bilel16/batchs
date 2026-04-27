package com.bna.smile.web.commun.actions;

import com.bna.smile.web.commun.forms.PouvoirForm;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.forms.PecDemandeCarteBancaireForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PouvoirAction extends DispatchAction{
    public PouvoirAction() {
    }

    
   /* public  ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

       try{
            PouvoirForm pouvoirForm = 
                (PouvoirForm)form;
            PersonneDemandeur personneDemandeur = pouvoirForm.getPersonneDemandeur();  
            Pouvoir pouvoir = (Pouvoir)request.getSession().getAttribute("pouvoir"); /// structure de l'agent 
        
            personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);           
             
        } catch (Exception e) {
            System.out.println("Erreur pouvoirAction  " + e.getMessage());
            return mapping.findForward("error");
        }   
        
           // ActionForward actionForward = mapping.findForward("error");
            //newActionForward.setPath(actionForward.getPath() + "?id=" + id);
        return mapping.findForward("success");
    }  */
        
}

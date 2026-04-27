package com.bna.smile.web.commun.actions;

import com.bna.commun.model.Personne;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.GetModificationDonneesClientCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.bna.smile.web.commun.forms.ConsultationModificationDonneesForm;
import com.bna.smile.web.commun.forms.ModificationDonneesClientForm;

import java.io.IOException;

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

public class ConsultationModificationDonneesAction extends DispatchAction {
    public ConsultationModificationDonneesAction() {
    }


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        ConsultationModificationDonneesForm consultationModificationDonneesForm = 
            (ConsultationModificationDonneesForm)form;
        consultationModificationDonneesForm.clearForm();
        
        consultationModificationDonneesForm.setDateDebut(DateHandler.dateJour());
        consultationModificationDonneesForm.setDateFin(DateHandler.dateJour());
       
        return mapping.findForward("pageDeConsulatation");


    }

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {

        ConsultationModificationDonneesForm consultationModificationDonneesForm = 
            (ConsultationModificationDonneesForm)form;
        consultationModificationDonneesForm.clearForm();
        return mapping.findForward("pageDeConsulatation");


    }

    public ActionForward rechercheModification(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws IOException, 
                                                                                    ServletException {

    
        ConsultationModificationDonneesForm consultationModificationDonneesForm = 
            (ConsultationModificationDonneesForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try{
        ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
            new ParamRechercheModificationDonneesVo();
        Personne personne = new Personne();
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(Long.valueOf(consultationModificationDonneesForm.getTypePiece()));
        personne.setTypePiece(typePiece);
        personne.setNumPcePers(consultationModificationDonneesForm.getNumeroPiece());

        paramRechercheModificationDonneesVo.setPersonne(personne);
        paramRechercheModificationDonneesVo.setDateDebut(DateHandler.strToDate(consultationModificationDonneesForm.getDateDebut()));
        paramRechercheModificationDonneesVo.setDateFin(DateHandler.strToDate(consultationModificationDonneesForm.getDateFin()));
        GetModificationDonneesClientCmd getModificationDonneesClientCmd = 
            new GetModificationDonneesClientCmd();
        paramRechercheModificationDonneesVo = 
                (ParamRechercheModificationDonneesVo)getModificationDonneesClientCmd.execute(paramRechercheModificationDonneesVo);
        if (paramRechercheModificationDonneesVo.hasError()){
            List listErreur = paramRechercheModificationDonneesVo.getErrors();
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
        }else{
        
        consultationModificationDonneesForm.setPersonne(paramRechercheModificationDonneesVo.getPersonne());
        consultationModificationDonneesForm.setListeDesModifications(paramRechercheModificationDonneesVo.getListeDesModifications());
        
            if (paramRechercheModificationDonneesVo.getPersonne().getCategoriePersonne().getTypePers().getCodTperTper().equals(Constants.PERSMORALE)){
            consultationModificationDonneesForm.setTypePersonne("PM");
            consultationModificationDonneesForm.setNomRsPers(paramRechercheModificationDonneesVo.getPersonne().getNomRsPers());
            consultationModificationDonneesForm.setLibSiglPers(paramRechercheModificationDonneesVo.getPersonne().getLibSiglPers());
            }else{
            consultationModificationDonneesForm.setNomPersonne(paramRechercheModificationDonneesVo.getPersonne().getNomNomPers());
            consultationModificationDonneesForm.setPrenomPersonne(paramRechercheModificationDonneesVo.getPersonne().getNomPrnPers());
            } 
         
        }    
        return mapping.findForward("pageDeConsulatation");
    }catch(Exception e){
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationModificationDonneesAction : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        
        ActionMessage actionMessage = 
            new ActionMessage("exception.generique",erreur.getDescription());
        actionMessages.add("Erreur ", actionMessage);
        this.saveMessages(request, actionMessages);
        return mapping.findForward("pageDeConsulatation");
     }

  }
}

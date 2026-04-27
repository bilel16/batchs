package com.bna.smile.web.clotureDomaine.actions;

import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.clotureDomaine.commande.ClotureJourneeSmileCmd;
import com.bna.smile.model.clotureDomaine.commande.GetListJournStructDomCmd;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.clotureDomaine.model.JournStructDomEtatVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.web.clotureDomaine.forms.ClotureDomaineForm;
import com.bna.smile.web.clotureDomaine.forms.ClotureJourneeForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.actions.ConsultationOppMoyPaieAction;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

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

public class ClotureJourneeAction extends DispatchAction{
    public ClotureJourneeAction() {
    }
    
    private static final Logger logger = Logger.getLogger(ConsultationOppMoyPaieAction.class);
    ParamAgence paramAgence = new ParamAgence();
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

     
       
        

        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            
            JourneeStructureId journeeStructureId=new JourneeStructureId();
            journeeStructureId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
          
            ClotureJourneeForm clotureJourneeForm = 
            (ClotureJourneeForm)form;
            GetListJournStructDomCmd getListJournStructDomCmd=new GetListJournStructDomCmd();
            Listes listedomaines=(Listes)getListJournStructDomCmd.execute(journeeStructureId);
            if (listedomaines == null || listedomaines.hasError()) {
              List listErreur = listedomaines.getErrors();
              for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                  com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                  ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                  actionMessages.add("Erreur ", actionMessage);
              }
              this.saveMessages(request, actionMessages);
             
              return mapping.findForward("error");
            }else{
                clotureJourneeForm.setCloture(true);
                for (Iterator it2 = listedomaines.getList().iterator(); it2.hasNext(); ) {
                JournStructDomEtatVo journStructDomEtatVo=(JournStructDomEtatVo)it2.next();
                if(!journStructDomEtatVo.getEtat().equalsIgnoreCase("Cloturé")){
                    clotureJourneeForm.setCloture(false);
                }
                }
                clotureJourneeForm.setListDomaines(listedomaines.getList());
                return mapping.findForward("initierPage");
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / initierPage : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);

            return mapping.findForward("error");
        }
     
       


    }
    public ActionForward cloturerJourneeSmile(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

     
       
        

        ActionMessages actionMessages = new ActionMessages();
        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            
            JourneeStructureId journeeStructureId=new JourneeStructureId();
            journeeStructureId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
          
            ClotureJourneeForm clotureJourneeForm = (ClotureJourneeForm)form;
            JournStrucDomVo journStrucDomVo=new JournStrucDomVo();
            journStrucDomVo.setJourneeStructureId(journeeStructureId);
            journStrucDomVo.setMatriculeInitiateur(paramAgence.getNumMatrUser());
            ClotureJourneeSmileCmd clotureJourneeSmileCmd=new ClotureJourneeSmileCmd();
            JournStrucDomVo journStrucDomVoRet  = (JournStrucDomVo)clotureJourneeSmileCmd.execute(journStrucDomVo);
            if (journStrucDomVoRet == null || journStrucDomVoRet.hasError()) {
              List listErreur = journStrucDomVoRet.getErrors();
              for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                  com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it1.next();
                  ActionMessage actionMessage = new ActionMessage("exception.generique", erreur.getDescription());
                  actionMessages.add("Erreur ", actionMessage);
              }
              this.saveMessages(request, actionMessages);
             
              return mapping.findForward("error");
            }else{
                clotureJourneeForm.setDatecloturee(paramAgence.getDateComptable());
                clotureJourneeForm.setDateOuverte(DateHandler.dateToStr(journStrucDomVoRet.getNouvelleJournee()));
                return mapping.findForward("clotureJournee");
            }
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / cloturerJourneeSmile : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);

            return mapping.findForward("error");
        }
     
       


    }
}

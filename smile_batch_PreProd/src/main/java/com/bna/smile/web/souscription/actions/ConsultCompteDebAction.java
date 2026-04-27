package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Personne;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratCmd;
import com.bna.smile.model.domainecommun.commande.GetListContratMandataireCmd;
import com.bna.smile.model.domainecommun.commande.GetListCotitulairePersonneCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneRechercheContratVo;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetPersClientCmd;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.procuration.util.ContratCptView;
import com.bna.smile.web.souscription.forms.ConsultCompteDebForm;
import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;

import java.io.IOException;

import java.util.ArrayList;
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

public class ConsultCompteDebAction extends DispatchAction{
    public ConsultCompteDebAction() {
    }
    ParamAgence paramAgence = new ParamAgence();
    Personne personne = new Personne();
    Client client = new Client();
    private static final Logger logger = Logger.getLogger(ConsultationContratCompteAction.class);
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        
        
        
        ActionMessages actionMessages =   new ActionMessages();
        ConsultCompteDebForm consultCompteDebForm = 
            (ConsultCompteDebForm)form;
        try {
     
            
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie

            consultCompteDebForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            consultCompteDebForm.setNumPieceId("");
           
            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultCompteDebAction / Dispatch Action :initierPage ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultCompteDebForm.getCodStrcRech()+ ">>. Exception : ",e);  
             //   logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }
    }
    
    public ActionForward rechercherPersonne(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws IOException, 
                                                                                 ServletException {

        /*************************************commande de rechercher personne*/
         ActionMessages actionMessages = new ActionMessages();
         ConsultCompteDebForm consultCompteDebForm = 
            (ConsultCompteDebForm)form;
        try {
            
            //consultCompteDebForm.clearForm();
            ContratCpt premierContrat = new ContratCpt();
            /* garnir les informations sur l'agence (apartir du Login) */
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
            
            PersonneRechercheContratVo personneRechercheContratVo = new PersonneRechercheContratVo();
            
            Listes listesCpts = new Listes();
            listesCpts.setList(new ArrayList());
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodStrcStrc(paramAgence.getCodStrcStrc());
            personneStrc.setCodTpceTpce(new Long(consultCompteDebForm.getTypePieceId()));
            personneStrc.setNumPcePers(consultCompteDebForm.getNumPieceId());
            personneRechercheContratVo.setPersonneStrc(personneStrc);
            personneRechercheContratVo.setEtatContrat(Constants.COD_ETAT_CPT_VALID);
            personneRechercheContratVo.setDateDebut(DateHandler.strToDate(consultCompteDebForm.getDateDebut()));
            personneRechercheContratVo.setDateFin(DateHandler.strToDate(consultCompteDebForm.getDateFin()));
            GetListContratCmd getListContratCmd = new GetListContratCmd();                
            listesCpts = (Listes)getListContratCmd.execute(personneRechercheContratVo);
            
            if(!listesCpts.hasError()){  
                    if (listesCpts.getList() == null || listesCpts.getList().size()==0) {
                        consultCompteDebForm.setAlert("ClientInexistant");
                    } else {
                       if (listesCpts.getList().size() > 0) {
                        // affecter la liste des contrats à la liste de collection Tag
                        List listeDesContratsView = new ArrayList();
                        for (Iterator it = listesCpts.getList().iterator(); 
                             it.hasNext(); ) {
                            ContratCpt contratCpt = (ContratCpt)it.next();
                            String cleContrat = 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                '0', 3) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                '0', 4) + 
                                StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                '0', 6);
                            ContratCptView contratCptView = new ContratCptView();
                            contratCptView.setCleContrat(cleContrat);
                            
                            contratCptView.setDateContrat(DateHandler.dateToStr(contratCpt.getDatOuvCcpt()));
                            
                            contratCptView.setNumeroCompte(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                                                           '0', 
                                                                           6));
                            contratCptView.setCodeAgence(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                                                         '0', 3));
                            contratCptView.setCodeProduit(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                                                          '0', 4));
                            contratCptView.setContratCpt(contratCpt);
                            listeDesContratsView.add(contratCptView);
                        }
                        consultCompteDebForm.setListeContrats(listeDesContratsView);
    
                        //  ************************************************************
    
                        consultCompteDebForm.setAlert("ClientExistant");
                        premierContrat = (ContratCpt)listesCpts.getList().get(0);
                        client = premierContrat.getClient();
                        personne = premierContrat.getClient().getPersonne();
                        if (client.getTypePers().getCodTperTper().equals(Constants.PERSPHYSIQUE)) {
                            consultCompteDebForm.setNomId(personne.getNomNomPers());
                            consultCompteDebForm.setPrenomId(personne.getNomPrnPers());
                          
                        } else if (client.getTypePers().getCodTperTper().equals(Constants.PERSMORALE)) {
                            consultCompteDebForm.setNomId(personne.getNomRsPers());
                            consultCompteDebForm.setPrenomId(personne.getLibSiglPers());
                        }   
    
                        
                       
                }
             }
            }else {                   
                     List listErreur = listesCpts.getErrors();
                     //ActionMessages actionMessages = new ActionMessages();
                     for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                         com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                         ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                         actionMessages.add("Erreur ", actionMessage);
                     }    
                    this.saveMessages(request, actionMessages);
                    return mapping.findForward("error");                    
                }            

            return mapping.findForward("success");
            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("la transaction est Interrompu, une erreur dans ConsultCompteDebAction / Dispatch Action :rechercherPersonne ");
                text.append(e.toString());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
                logger.error("Erreur au niveau de l'agence <<" +consultCompteDebForm.getCodStrcRech()+ ">>. Exception : ",e);  
            //    logger.error("Exception : ",e); 
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
                this.saveMessages(request, actionMessages);
                return mapping.findForward("error");
            }

    }
}

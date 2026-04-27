package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.Adresse;
import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetStructureCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.InsertCompte519Cmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamCompte519;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

import java.io.IOException;

import java.util.Date;
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


public class SouscriptionContratCompteeAction extends DispatchAction {

    private static final Logger logger = 
        Logger.getLogger(SouscriptionContratCompteeAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        try {

            ParamAgence paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
            Context context = ContextHandler.getContext();
            SouscriptionContratCompteForm souscriptionContratCompteForm = (SouscriptionContratCompteForm)form;
            souscriptionContratCompteForm.setLibelleOperation("Souscription Contrat Compte 519");
            souscriptionContratCompteForm.setCodeStructureCpt(paramAgence.getCodStrcStrc().toString());
            souscriptionContratCompteForm.initialiser519();
            return mapping.findForward("success");
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ", e);
            return mapping.findForward("error");
        }
    }

    public ActionForward validerContratCpt(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        try {

            ActionMessages actionMessages = new ActionMessages();
            ValueObject vo = new ValueObject();
            SouscriptionContratCompteForm souscriptionContratCompteForm = (SouscriptionContratCompteForm)form;
            souscriptionContratCompteForm.setMessage("");
            ParamCompte519 paramCompte519 = new ParamCompte519();
            ContratCpt contrat = new ContratCpt();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodPrdPrd(Long.valueOf(souscriptionContratCompteForm.getCodePrdCpt()));
            contratCptId.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getCodeStructureCpt()));
            contratCptId.setNumCcptCcpt(Long.valueOf(souscriptionContratCompteForm.getNumCompteCpt()));
            contrat.setContratCptId(contratCptId);
            GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
            ContratCpt verifCptExiste = (ContratCpt)getContratCptByIdCmd.execute(contrat);

            // if(verifCptExiste==null){
            Devise devise = new Devise();
            Client client = new Client();
            devise.setCodDevDev(new Long(788));
            client.setNumSeqPers(new Long(34503));
            contrat.setDevise(devise);
            contrat.setClient(client);
            contrat.setDatOuvCcpt(new Date());
            contrat.setCodEtatCcpt(Constants.COD_ETAT_CPT_ATT);
            contrat.setMontSoldCcpt(Long.valueOf(0));
            contrat.setMontAutCcpt(Long.valueOf(0));
            contrat.setMontBlocCcpt(Long.valueOf(0));
            contrat.setMontSdevCcpt(Long.valueOf(0));
            contrat.setMontSminCcpt(Long.valueOf(0));
            contrat.setMontBdevCcpt(Long.valueOf(0));
          
            paramCompte519.setContratCpt(contrat);
            //GetStructureCmd getStructureCmd = new GetStructureCmd();
            //Structure structure = new Structure();
            //structure.setCodStrcStrc(Long.valueOf(souscriptionContratCompteForm.getNumCompteCpt()));
           // structure = (Structure)getStructureCmd.execute(structure);

            //if (structure.getStructure() == null) {
                //souscriptionContratCompteForm.setMessage("StructureInexistante");
                //return mapping.findForward("objectNull");
           // } else {
                if (verifCptExiste == null) {
                    //adresse : 
                   /* Adresse adresseCpt = new Adresse();
                    adresseCpt.setImmeuble(structure.getAdrImmStrc());
                    adresseCpt.setRue(structure.getAdrRueStrc());
                    adresseCpt.setCite(structure.getAdrCitStrc());
                    adresseCpt.setCodCpCp(structure.getCodePostal().getCodCpCp().toString());
  
                    contrat.setAdresseCorresp(adresseCpt);
                    contrat.setNomIntiCcpt(structure.getLibStrcStrc());*/
                   // paramCompte519.setContratCpt(contrat);
                    InsertCompte519Cmd insertCompte519Cmd = new InsertCompte519Cmd();

                    vo = (ValueObject)insertCompte519Cmd.execute(paramCompte519);


                    if (!vo.hasError()) {
                        ContratCpt contratCpt = (ContratCpt)vo;
                        String message =

                        "La demande de souscription numéro  " + 
                        StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                        '0', 3)+
                        StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                        '0', 4)+
                        StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                            '0', 6) + 
                                            //" de " 
                      //  + structure.getLibStrcStrc()+
                        "  a été crée avec succès et en attente de validation par le chef d'agence.";
                        souscriptionContratCompteForm.setLibelleConfirmation(message);
                        return mapping.findForward("confirmationCreationContrat");
                    } else {
                        List listErreur = vo.getErrors();
                        for (Iterator it = listErreur.iterator(); it.hasNext(); 
                        ) {
                            com.oxia.fwk.core.Error erreur = 
                                (com.oxia.fwk.core.Error)it.next();
                            ActionMessage actionMessage = 
                                new ActionMessage("exception.generique", 
                                                  erreur.getDescription());
                            actionMessages.add("Erreur ", actionMessage);
                        }
                        this.saveMessages(request, actionMessages);
                        return mapping.findForward("error");
                    }
                }

                else {
                    souscriptionContratCompteForm.setMessage("ContratCptExistant");
                    return mapping.findForward("objectNull");
                }
            //}
                

        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ", e);
            return mapping.findForward("error");
        }
    }
}


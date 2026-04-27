package com.bna.smile.web.clotureDomaine.actions;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.clotureDomaine.commande.ClotureDomMoyPaiCmd;
import com.bna.smile.model.clotureDomaine.commande.CloturedomPlacementCmd;
import com.bna.smile.model.clotureDomaine.commande.GetDonneeMoyPaiCmd;
import com.bna.smile.model.clotureDomaine.commande.UpdateDomaineCmd;
import com.bna.smile.model.clotureDomaine.model.CloturePlacVo;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.clotureDomaine.model.StatMoyPai;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.moyenPayement.model.Accuse;
import com.bna.smile.web.clotureDomaine.forms.ClotDomMoyPaiForm;
import com.bna.smile.web.clotureDomaine.forms.ClotureDomPlacementForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
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

public class ClotDomMoyPaiAction extends DispatchAction {
    public ClotDomMoyPaiAction() {
    }
    private static final Logger logger = 
        Logger.getLogger(ClotDomMoyPaiAction.class);
    ParamAgence paramAgence = new ParamAgence();
    
    
    public ActionForward getDonneeMoyPai(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {
       
        ClotDomMoyPaiForm clotDomMoyPaiForm = 
            (ClotDomMoyPaiForm)form;
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA");
        try {
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_MOY_PAI);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            StatMoyPai statMoyPai = new StatMoyPai();
            statMoyPai.setJourneeStructureDomaineId(journeeStructureDomaineId);
            statMoyPai.setDateJournee(DateHandler.strToDate(paramAgence.getDateComptable()));
            statMoyPai.setStructure(paramAgence.getCodStrcStrc());
            GetDonneeMoyPaiCmd getDonneeMoyPaiCmd=new GetDonneeMoyPaiCmd();
            StatMoyPai statMoyPaiRet=(StatMoyPai)getDonneeMoyPaiCmd.execute(statMoyPai);
            
            clotDomMoyPaiForm.setNbr822(statMoyPaiRet.getNbr822());
            clotDomMoyPaiForm.setMnt822(statMoyPaiRet.getMnt822());
            clotDomMoyPaiForm.setMntFormate822(StrHandler.formatmnt(Math.abs(statMoyPaiRet.getMnt822())));
            
            clotDomMoyPaiForm.setNbr948(statMoyPaiRet.getNbr948());
            clotDomMoyPaiForm.setMnt948(statMoyPaiRet.getMnt948());
            
            clotDomMoyPaiForm.setNbr947(statMoyPaiRet.getNbr947());
            clotDomMoyPaiForm.setMnt947(statMoyPaiRet.getMnt947());
            
            clotDomMoyPaiForm.setNbr821(statMoyPaiRet.getNbr821());
            clotDomMoyPaiForm.setMnt821(statMoyPaiRet.getMnt821());
            
            clotDomMoyPaiForm.setNbr823(statMoyPaiRet.getNbr823());
            clotDomMoyPaiForm.setMnt823(statMoyPaiRet.getMnt823());
            
            clotDomMoyPaiForm.setNbr824(statMoyPaiRet.getNbr824());
            clotDomMoyPaiForm.setMnt824(statMoyPaiRet.getMnt824());
            
            
           /* if (statMoyPaiRet.getMntVirCompRec()!=null){
            clotDomMoyPaiForm.setMntVirCompStr(StrHandler.formatmnt(Math.abs(statMoyPaiRet.getMntVirCompRec())));
            }
            if (statMoyPaiRet.getMntRejVirRec()!=null){
            clotDomMoyPaiForm.setMntRejVirStr(StrHandler.formatmnt(Math.abs(statMoyPaiRet.getMntRejVirRec())));
            }*/
            clotDomMoyPaiForm.setListeAccuses(statMoyPaiRet.getListeAccusee());
            return mapping.findForward("clotDomMoyPai");

        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("getDonneeMoyPai a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
            text.append("Exception au niveau de l'agence:");
            text.append(clotDomMoyPaiForm.getInitialisationView().getCodeAgence());
            text.append(". Exception :");
            text.append(e.toString());
            erreur.setCode("200");
            text.toString().replaceAll("java.lang.RuntimeException:"," ");
            text.toString().replaceAll("java.lang.Exception::"," ");
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
    
    public ActionForward reouvrirDomMoyPai(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws IOException, 
                                                                                       ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotDomMoyPaiForm clotDomMoyPaiForm = 
                (ClotDomMoyPaiForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JourneeStructureDomaine journeeStructureDomaine = 
                new JourneeStructureDomaine();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_MOY_PAI);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journeeStructureDomaine.setJourneeStructureDomaineId(journeeStructureDomaineId);

            UpdateDomaineCmd updateDomaineCmd = new UpdateDomaineCmd();


            journeeStructureDomaine = 
                    (JourneeStructureDomaine)updateDomaineCmd.execute(journeeStructureDomaine);
            if (journeeStructureDomaine == null || 
                journeeStructureDomaine.hasError()) {
                List listErreur = journeeStructureDomaine.getErrors();
                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it1.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);

                return mapping.findForward("error");
            } else {

                return mapping.findForward("indexSMILE");
            }


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotDomMoyPaiAction  : ");
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
    public ActionForward cloturerDomMoyPai(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotDomMoyPaiForm clotDomMoyPaiForm = 
                (ClotDomMoyPaiForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_MOY_PAI);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journStrucDomVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            journStrucDomVo.setMatriculeInitiateur(paramAgence.getNumMatrUser());
            /*garniture des operations virement*/
            if (paramAgence.getCodTstrcTstrc()==1){
                List listeOperVir = new ArrayList();
                if (Long.valueOf(clotDomMoyPaiForm.getNbr822()) > 0) {
                    CloturePlacVo cloturePlacVo = new CloturePlacVo();
                    cloturePlacVo.setCodOpertation(Constants.COD_OPER_POS_VIR);
                    cloturePlacVo.setMontant(clotDomMoyPaiForm.getMnt822());
                    cloturePlacVo.setNombre(clotDomMoyPaiForm.getNbr822());
                    listeOperVir.add(cloturePlacVo);
                }
                if (Long.valueOf(clotDomMoyPaiForm.getNbr948()) > 0) {
                    CloturePlacVo cloturePlacVo1 = new CloturePlacVo();
                    cloturePlacVo1.setCodOpertation(Constants.COD_OPER_POS_REJ);
                    cloturePlacVo1.setMontant(clotDomMoyPaiForm.getMnt948());
                    cloturePlacVo1.setNombre(clotDomMoyPaiForm.getNbr948());
                    listeOperVir.add(cloturePlacVo1);
                }
                if (Long.valueOf(clotDomMoyPaiForm.getNbr947()) > 0) {
                    CloturePlacVo cloturePlacVo2 = new CloturePlacVo();
                    cloturePlacVo2.setCodOpertation(Constants.COD_OPER_REC_REJ_VIR);
                    cloturePlacVo2.setMontant(clotDomMoyPaiForm.getMnt947());
                    cloturePlacVo2.setNombre(clotDomMoyPaiForm.getNbr947());
                    listeOperVir.add(cloturePlacVo2);
                }
                if (Long.valueOf(clotDomMoyPaiForm.getNbr821()) > 0) {
                    CloturePlacVo cloturePlacVo3 = new CloturePlacVo();
                    cloturePlacVo3.setCodOpertation(Constants.COD_OPER_REC_VIR);
                    cloturePlacVo3.setMontant(clotDomMoyPaiForm.getMnt821());
                    cloturePlacVo3.setNombre(clotDomMoyPaiForm.getNbr821());
                    listeOperVir.add(cloturePlacVo3);
                }
                if (Long.valueOf(clotDomMoyPaiForm.getNbr823()) > 0) {
                    CloturePlacVo cloturePlacVo4 = new CloturePlacVo();
                    cloturePlacVo4.setCodOpertation(Constants.COD_OPER_ENV_REJ_VIR);
                    cloturePlacVo4.setMontant(clotDomMoyPaiForm.getMnt823());
                    cloturePlacVo4.setNombre(clotDomMoyPaiForm.getNbr823());
                    listeOperVir.add(cloturePlacVo4);
                }
                if (Long.valueOf(clotDomMoyPaiForm.getNbr824()) > 0) {
                    CloturePlacVo cloturePlacVo5 = new CloturePlacVo();
                    cloturePlacVo5.setCodOpertation(Constants.COD_OPER_REJ_VIR);
                    cloturePlacVo5.setMontant(clotDomMoyPaiForm.getMnt824());
                    cloturePlacVo5.setNombre(clotDomMoyPaiForm.getNbr824());
                    listeOperVir.add(cloturePlacVo5);
                }
                journStrucDomVo.setListeOperVirement(listeOperVir);
            }
            
            ClotureDomMoyPaiCmd clotureDomMoyPaiCmd = 
                new ClotureDomMoyPaiCmd();


            JournStrucDomVo journStrucDomVoRet = 
                (JournStrucDomVo)clotureDomMoyPaiCmd.execute(journStrucDomVo);
            if (journStrucDomVoRet == null || journStrucDomVoRet.hasError()) {
                List listErreur = journStrucDomVoRet.getErrors();
                for (Iterator it1 = listErreur.iterator(); it1.hasNext(); ) {
                    com.oxia.fwk.core.Error erreur = 
                        (com.oxia.fwk.core.Error)it1.next();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          erreur.getDescription());
                    actionMessages.add("Erreur ", actionMessage);
                }
                this.saveMessages(request, actionMessages);

                return mapping.findForward("error");
            }

            clotDomMoyPaiForm.setLibDom(journStrucDomVoRet.getLibDomaine());
            return mapping.findForward("confirmClotDomMoyPai");


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotDomMoyPaiAction / cloturerDomMoyPai: ");
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

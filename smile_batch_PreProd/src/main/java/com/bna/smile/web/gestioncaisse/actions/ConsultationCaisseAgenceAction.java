package com.bna.smile.web.gestioncaisse.actions;

import com.bna.commun.model.DetailCaisDevAg;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecaisse.commande.GetDetailCaisseStructureCmd;
import com.bna.smile.model.domainecaisse.commande.GetListeSessionJrnCaisseCmd;
import com.bna.smile.model.domainecaisse.model.ListeCaisseStructureVo;
import com.bna.smile.model.domainecaisse.model.SituationDetailCaisseStructureVo;
import com.bna.smile.model.domainecaisse.traitement.GetListSessionJrnCaisseTrt;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.gestioncaisse.forms.ConsultationCaisseAgenceForm;
import com.bna.smile.web.gestioncaisse.view.DetailCaisseStructureView;
import com.bna.smile.web.operationguichet.form.VersementMemeAgenceForm;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * @author MDIMAGH med Lassaad
 * @since 02/04/2008
 * 
 */
public class ConsultationCaisseAgenceAction extends DispatchAction {
    public ConsultationCaisseAgenceAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
   
        ActionMessages actionMessages = new ActionMessages();
        
        try {
                   
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
  
           ConsultationCaisseAgenceForm consultForm = (ConsultationCaisseAgenceForm) form;
           consultForm.clearForm();
           
           consultForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
           consultForm.getInitialisationView().setDateActuelle(DateHandler.dateToStr(paramAgence.getDateOp()));
            
           GetListeSessionJrnCaisseCmd getListCaisse = new GetListeSessionJrnCaisseCmd();
           ListeCaisseStructureVo listCaisseStructure = new ListeCaisseStructureVo();
           
           listCaisseStructure.setCodeStructure(paramAgence.getCodStrcStrc());
           listCaisseStructure.setDateJournee(paramAgence.getDateOp());
           listCaisseStructure = (ListeCaisseStructureVo) getListCaisse.execute(listCaisseStructure);
           
           consultForm.setListeCaisseStructure(listCaisseStructure.getListeCaisseStructure());
           
           
            return mapping.findForward("pageConsultationCaisse");
    
    
        } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         ActionMessage actionMessage = 
             new ActionMessage("exception.generique", 
                               erreur.getDescription());
         erreur.setCode("200");
         erreur.setDescription("Une erreur est survenu dans ConsultationCaisseAgenceAction : " + 
                               e.toString());
         actionMessages.add("Erreur ", actionMessage);
         this.saveMessages(request, actionMessages);
    
         return mapping.findForward("success");
        }
 
 }

    public ActionForward detailCaisse(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
    
        ActionMessages actionMessages = new ActionMessages();
        
        try {
                   
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
    
           ConsultationCaisseAgenceForm consultForm = (ConsultationCaisseAgenceForm) form;
           
           if (consultForm.getNumeroCaisseRech()!=null && !consultForm.getNumeroCaisseRech().equals("")){
               GetDetailCaisseStructureCmd getDetailCaisse = new GetDetailCaisseStructureCmd();
               SituationDetailCaisseStructureVo situationVo = new SituationDetailCaisseStructureVo();
               
               situationVo.setNumeroCaisse(Long.valueOf(consultForm.getNumeroCaisseRech()));
               situationVo.setCodeStructure(Long.valueOf(consultForm.getInitialisationView().getCodeAgence()));
               situationVo.setDateJournee(DateHandler.strToDate(consultForm.getInitialisationView().getDateActuelle()));
               situationVo = (SituationDetailCaisseStructureVo) getDetailCaisse.execute(situationVo);
                
                //------------------------------//
                //-- Si le detail est trouvé ---//
                if (situationVo.getDetailCaisseStructure().getCodStatDcs().equals("1")){
                    consultForm.setLibStatCais("Fonctionnelle");
                }else {
                    consultForm.setLibStatCais("Arretée");
                }
                
                if (situationVo.getCaisseDinars()!= null && situationVo.getCaisseDinars() != null ){
                    consultForm.setMontInitCaisDinars(StrHandler.formatmnt(situationVo.getCaisseDinars().getMontInitCda()));
                    consultForm.setMontActuCaisDinars(StrHandler.formatmnt(situationVo.getCaisseDinars().getMontActuCda()));
                    consultForm.setMontFinCaisDinars(StrHandler.formatmnt(situationVo.getCaisseDinars().getMontFinCda()));
                }
               
                if (situationVo.getCaisseDevises() != null && situationVo.getCaisseDevises() != null  ){
                 if (situationVo.getCaisseDevises().getMontInitCdev() != null){
                   consultForm.setMontInitCdev(StrHandler.formatmnt(situationVo.getCaisseDevises().getMontInitCdev() ));
                 }
                 if (situationVo.getCaisseDevises().getMontActuCdev() != null ){
                  consultForm.setMontActuCdev(StrHandler.formatmnt(situationVo.getCaisseDevises().getMontActuCdev() ));
                 }
                 if (situationVo.getCaisseDevises().getMontFinCdev() != null ){
                   consultForm.setMontFinCdev(StrHandler.formatmnt(situationVo.getCaisseDevises().getMontFinCdev() ));
                 }
                }           
               consultForm.setListDetailDevise( new ArrayList()) ;
               
                
                System.out.println(situationVo.getCaisseDevises().getDetailCaisDevAgs().size());
                
                
                for(Iterator it = situationVo.getCaisseDevises().getDetailCaisDevAgs().iterator();it.hasNext();){
                 DetailCaisDevAg detail = (DetailCaisDevAg) it.next();
                 DetailCaisseStructureView detailCaisseStructureView = new DetailCaisseStructureView();
                 detailCaisseStructureView.setLibelleDevise(detail.getDevise().getCodDevDev()+" "+ detail.getDevise().getLibDevDev());
                 detailCaisseStructureView.setDetailCaisDevAg(detail);
                 detailCaisseStructureView.setMontInitDcda(StrHandler.formatMontant(detail.getMontInitDcda(),detail.getDevise().getNbrDecDev()));
                 detailCaisseStructureView.setMontCvinDcda(StrHandler.formatmnt(detail.getMontCvinDcda()));
                 detailCaisseStructureView.setMontActuDcda(StrHandler.formatMontant(detail.getMontActuDcda(),detail.getDevise().getNbrDecDev()));
                 detailCaisseStructureView.setMontCvacDcda(StrHandler.formatmnt(detail.getMontCvacDcda()));
                 detailCaisseStructureView.setMontFinDcda(StrHandler.formatMontant(detail.getMontFinDcda(),detail.getDevise().getNbrDecDev()));
                 detailCaisseStructureView.setMontCvfnDcda(StrHandler.formatmnt(detail.getMontCvfnDcda()));
                 consultForm.getListDetailDevise().add(detailCaisseStructureView);
                 
               }
            
           }
          
           
           
            return mapping.findForward("pageConsultationCaisse");
    
    
        } catch (Exception e) {
         com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
         ActionMessage actionMessage = 
             new ActionMessage("exception.generique", 
                               erreur.getDescription());
         erreur.setCode("200");
         erreur.setDescription("Une erreur est survenu dans ConsultationCaisseAgenceAction : " + 
                               e.toString());
         actionMessages.add("Erreur ", actionMessage);
         this.saveMessages(request, actionMessages);
    
         return mapping.findForward("error");
        }
    
    }



}

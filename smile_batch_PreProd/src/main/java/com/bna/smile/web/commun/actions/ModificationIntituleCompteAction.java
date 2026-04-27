package com.bna.smile.web.commun.actions;


import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetTypeModificationCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.ModifierIntituleCompteCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationIntituleCompteVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.UpdateContratCptCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ModificationDonneesClientForm;
import com.bna.smile.web.commun.forms.ModificationIntituleCompteForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;

import com.bna.smile.web.procuration.util.ContratCptView;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ModificationIntituleCompteAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
     Logger logger = Logger.getLogger(ModificationIntituleCompteAction.class);
     
    public ActionForward initierPage(ActionMapping mapping, 
                                                               ActionForm form, 
                                                               HttpServletRequest request, 
                                                               HttpServletResponse response) throws IOException, 
                                                                                                   ServletException {
                                                                                                   
           ParamAgence paramAgence = 
           (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
           
           ModificationIntituleCompteForm modifIntituleForm = (ModificationIntituleCompteForm) form;                                                                                        
           
           
                SessionUtil sessionUtil =new SessionUtil();
                //Suppression des anciens Bean de type Form de la session,
                sessionUtil.removeSession(request,"modificationIntituleCompteForm");
                ActionMessages actionMessages = new ActionMessages();
               
              try{
                /*test sur l'etat du domaine*/
                StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
                Boolean bool= SmileUtil.testDomaineOuvert(structureDomaine);
                modifIntituleForm.clearForm();
                if (!paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)){
                   modifIntituleForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
                }
                if(modifIntituleForm.getCodeModification().equalsIgnoreCase(Constants.COD_MODIF_TYPLIQ)){
                    modifIntituleForm.setLibelleModification("Modification type liquidation");
                    return mapping.findForward("typeLiquidationCpt");
                }else{
                GetTypeModificationCmd getTypeModificationCmd = 
                    new GetTypeModificationCmd();
                TypeModification typeModification = new TypeModification();
                typeModification.setCodCodModf(Long.valueOf(modifIntituleForm.getCodeModification()));
                typeModification = 
                   (TypeModification)getTypeModificationCmd.execute(typeModification);
                modifIntituleForm.setLibelleModification(typeModification.getLibModfModf());
                modifIntituleForm.setTypeModification(typeModification);
                return mapping.findForward("initierPage");
                }
                
              }catch(Exception e){
                  com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                  StringBuffer text = 
                      new StringBuffer("la transaction est Interrompu, une erreur dans ModificationIntituleCompteAction / disp :initierPage: ");
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
       
        public ActionForward rechercherContrat(ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws IOException, 
                                                                                                ServletException {

            ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
        
             ModificationIntituleCompteForm modifIntituleForm = (ModificationIntituleCompteForm) form;
            
            GetContratCptByIdCmd getContratCpt = new GetContratCptByIdCmd();
            ContratCpt contrat = new ContratCpt();
            ContratCptId contratId = new ContratCptId();
            
            contratId.setCodStrcStrc(Long.valueOf(modifIntituleForm.getCodStrcRech()));
            contratId.setCodPrdPrd(Long.valueOf(modifIntituleForm.getCodPrdRech()));
            contratId.setNumCcptCcpt(Long.valueOf(modifIntituleForm.getNumCcptRech()));
            modifIntituleForm.clearForm();
            
            modifIntituleForm.setCodStrcRech(StrHandler.lpad(contratId.getCodStrcStrc().toString(),'0',3));
            modifIntituleForm.setCodPrdRech(StrHandler.lpad(contratId.getCodPrdPrd().toString(),'0',4));
            modifIntituleForm.setNumCcptRech(StrHandler.lpad(contratId.getNumCcptCcpt().toString(),'0',6));
            
            contrat.setContratCptId(contratId);
            contrat = (ContratCpt) getContratCpt.execute(contrat);
       
            if (contrat != null && contrat.getNomIntiCcpt() != null){
                modifIntituleForm.setLibelleCpt(contrat.getNomIntiCcpt());
                modifIntituleForm.setContratCpt(contrat);
                if(contrat.getBoolLiqCcpt()!=null){
                    modifIntituleForm.setBoolTypLiq(contrat.getBoolLiqCcpt().toString());
                }else{
                    modifIntituleForm.setAlert("nonGarnie");
                }
               
               
                
            //-------------- Contrat est non valide ----------//
             if (!contrat.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
                 modifIntituleForm.setAlert("nonValide");
             }
           
            }else {
            //------------------ Contrat inexistant ----------------------------//
                modifIntituleForm.setAlert("inexistant");
            }
            if(modifIntituleForm.getCodeModification().equalsIgnoreCase(Constants.COD_MODIF_TYPLIQ)){
               
                return mapping.findForward("typeLiquidationCpt");
            }else{
                return mapping.findForward("initierPage");
            }
        }
        
        
        
    public ActionForward valider(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {

        
        ParamAgence paramAgence = 
        (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
    
         ModificationIntituleCompteForm modifIntituleForm = (ModificationIntituleCompteForm) form;
         ActionMessages actionMessages = new ActionMessages();
        logger.info("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Evénement : entrée Dispatch Action : valider");

       try{ 
         ParamModificationIntituleCompteVo paramModification = new  ParamModificationIntituleCompteVo();
         
         paramModification.setCodeStructure(paramAgence.getCodStrcStrc());
         paramModification.setMatricule(Long.valueOf(paramAgence.getNumMatrUser()));
         modifIntituleForm.getContratCpt().setNomIntiCcpt(modifIntituleForm.getNouveauLibelleCpt());
         paramModification.setContratCpt(modifIntituleForm.getContratCpt());
               
         ModifierIntituleCompteCmd modifierIntituleCompteCmd = new ModifierIntituleCompteCmd();
         paramModification = (ParamModificationIntituleCompteVo) modifierIntituleCompteCmd.execute(paramModification);
         
         if (! paramModification.hasError()){
             StringBuffer text   = new StringBuffer(" La modification de l'intitulé du compte :");
             StringBuffer ancText   = new StringBuffer(" Ancien Intitulé : ");
             StringBuffer newText   = new StringBuffer(" Nouveau Intitulé :");
             
             text.append(modifIntituleForm.getCodStrcRech());
             text.append(modifIntituleForm.getCodPrdRech());
             text.append(modifIntituleForm.getNumCcptRech());
             text.append("  a été effectuée avec succès." );
             
             ancText.append( modifIntituleForm.getLibelleCpt());
             newText.append(modifIntituleForm.getNouveauLibelleCpt());
             
             modifIntituleForm.setLibelleConfirmation1(ancText.toString());
             modifIntituleForm.setLibelleConfirmation2(newText.toString());
             modifIntituleForm.setLibelleConfirmation(text.toString());
             
             logger.info("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Evénement : Terminer Transaction changement intitulé ");

             return mapping.findForward("pageConfirmation");
         } else {
             List listErreur = paramModification.getErrors();                    
             for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                 com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
              }    
              this.saveMessages(request, actionMessages);
              return mapping.findForward("error");             
         }
        
        
      } catch (Exception e) {
            logger.error("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Erreur dans dispatch action valider : "+ e.toString());
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModificationIntituleCompteAction / disp :valider: ");
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
                                                                                            
    public ActionForward validerTypLiq(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {

        
        ParamAgence paramAgence = 
        (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
    
         ModificationIntituleCompteForm modifIntituleForm = (ModificationIntituleCompteForm) form;
         ActionMessages actionMessages = new ActionMessages();
        logger.info("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Evénement : entrée Dispatch Action : valider");

       try{ 
        
         modifIntituleForm.getContratCpt().setBoolLiqCcpt(Long.valueOf(modifIntituleForm.getNouveauBoolTypLiq()));
        
         UpdateContratCptCmd updateContratCptCmd=new UpdateContratCptCmd();      
         ContratCpt contratCptUpdated = new ContratCpt();
         contratCptUpdated = (ContratCpt) updateContratCptCmd.execute(modifIntituleForm.getContratCpt());
         
         if (! contratCptUpdated.hasError()){
             StringBuffer text   = new StringBuffer(" La modification du type de liquidation : ");
             StringBuffer ancText   = new StringBuffer(" Ancien type : ");
             StringBuffer newText   = new StringBuffer(" Nouveau type : ");
             
             text.append(modifIntituleForm.getCodStrcRech());
             text.append(modifIntituleForm.getCodPrdRech());
             text.append(modifIntituleForm.getNumCcptRech());
             text.append("  a été effectuée avec succès." );
             if(modifIntituleForm.getBoolTypLiq()==null){
            	 ancText.append("Non garnie");
             }else if(modifIntituleForm.getBoolTypLiq().equalsIgnoreCase("0")){
                 ancText.append("Par cheque");
             }else{
                 ancText.append("Par remise");
             }
             if(modifIntituleForm.getBoolTypLiq()==null){
            	 newText.append("Non garnie");
             }else if(modifIntituleForm.getNouveauBoolTypLiq().equalsIgnoreCase("0")){
                 newText.append("Par cheque");
             }else{
                 newText.append("Par remise");
             }
            
             
             modifIntituleForm.setLibelleConfirmation1(ancText.toString());
             modifIntituleForm.setLibelleConfirmation2(newText.toString());
             modifIntituleForm.setLibelleConfirmation(text.toString());
             
             logger.info("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Evénement : Terminer Transaction changement type liquidation ");

             return mapping.findForward("pageConfirmation");
         } else {
             List listErreur = contratCptUpdated.getErrors();                    
             for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                 com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                 ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                 actionMessages.add("Erreur ", actionMessage);
              }    
              this.saveMessages(request, actionMessages);
              return mapping.findForward("error");             
         }
        
        
      } catch (Exception e) {
            logger.error("Structure: "+ paramAgence.getCodStrcStrc().toString()+" Matricule: "+paramAgence.getNumMatrUser().toString()+" Erreur dans dispatch action valider : "+ e.toString());
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ModificationIntituleCompteAction / disp :valider: ");
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
    public ActionForward printModification(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException
                                                                     {
                                                                     
            ModificationIntituleCompteForm modifIntituleForm = (ModificationIntituleCompteForm) form;
                          try {
                                    CommonReportVO valueObject = new CommonReportVO();
                                    ParamAgence paramAgence = new ParamAgence();
                                    paramAgence =
                                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                                    Map parameters = new HashMap();
                                    String pMODIF1 = "MODIF1";
                                    String pMODIF2 = "MODIF2";
                                    String pMODIF3 = "MODIF3";
                                    String pLibEtat = "P_LIB_ETAT";
                                    String pMatrUser = "P_NUM_MATR_USER";
                                    String pLogo = "P_PATH";
                                    
                                    String pCcpt = "CCPT";
                                    String vCcpt ="";
                                    StringBuffer str = new StringBuffer();
                                   
                                    vCcpt = str.toString();
                                    parameters.put(pCcpt, vCcpt);
                                    String vLibEtat = modifIntituleForm.getLibelleModification();
                                    String vMatrUser = paramAgence.getNumMatrUser().toString();
                                    String vLogo = getServlet().getServletContext().getRealPath("")+ "\\reporting\\";
                                    String vMODIF1 = modifIntituleForm.getLibelleConfirmation();
                                    String vMODIF2 = modifIntituleForm.getLibelleConfirmation1();
                                    String vMODIF3 = modifIntituleForm.getLibelleConfirmation2();
                                    parameters.put(pMatrUser, vMatrUser);
                                    parameters.put(pLibEtat, vLibEtat);
                                    parameters.put(pMODIF1, vMODIF1);
                                    parameters.put(pMODIF2, vMODIF2);
                                    parameters.put(pMODIF3, vMODIF3);
                                    parameters.put(pLogo, vLogo);
                                   
                                   valueObject.setParams(parameters);
                                   valueObject.setNomReport("EtatModification");
                                 //  valueObject.setList(modificationDonneesClientForm.getListDesContratAmodifier());
                                 //  valueObject.setRootFolder(getServlet().getServletContext().getRealPath("")+ "\\reporting\\");
                                 //  PrinterCmd printer = new PrinterCmd();

                               //    valueObject = (CommonReportVO) printer.execute(valueObject);
                                   request.getSession().setAttribute("CommonPrintVo",valueObject);
                                   request.setAttribute("print","1");
                                   
                                   response.setContentType("application/pdf");

                           } catch (Exception e) {
                                
                                   e.printStackTrace();
                           }
                    
            return mapping.findForward("pageConfirmation");
                                                                          
        }
        
}

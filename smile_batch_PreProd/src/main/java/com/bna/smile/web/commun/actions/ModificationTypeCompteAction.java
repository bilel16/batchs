package com.bna.smile.web.commun.actions;


import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.TypeModification;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetTypeModificationCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.ModificationTypeCompteCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationTypeCompteVo;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.forms.ModificationIntituleCompteForm;
import com.bna.smile.web.commun.forms.ModificationTypeCompteForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ModificationTypeCompteAction  extends DispatchAction  {
    public ModificationTypeCompteAction() {
    }
    
    
  
        public ActionForward initierPage(ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws IOException, 
                                                                                                ServletException {


           ParamAgence paramAgence = 
            (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
       
           ModificationTypeCompteForm modifCompteForm = (ModificationTypeCompteForm) form;
           
           
            SessionUtil sessionUtil =new SessionUtil();
            //Suppression des anciens Bean de type Form de la session,
            sessionUtil.removeSession(request,"modificationTypeCompteForm");
            ActionMessages actionMessages = new ActionMessages();
            
          try{
           /*test sur l'etat du domaine*/
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CLIENT);
            Boolean bool= SmileUtil.testDomaineOuvert(structureDomaine);
          
            modifCompteForm.clearForm();
            
            GetTypeModificationCmd getTypeModificationCmd = 
            new GetTypeModificationCmd();
            TypeModification typeModification = new TypeModification();
            typeModification.setCodCodModf(Long.valueOf(modifCompteForm.getCodeModification()));
            typeModification = 
            (TypeModification)getTypeModificationCmd.execute(typeModification);
            modifCompteForm.setLibelleModification(typeModification.getLibModfModf());
            modifCompteForm.setTypeModification(typeModification);
            if (! paramAgence.getCodStrcStrc().equals(Constants.COD_STRC_DAJ)){
             modifCompteForm.setCodStrcRech(paramAgence.getCodStrcStrc().toString());
            }
            
            return mapping.findForward("initierPage");
            
       }catch(Exception e){
           com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
           StringBuffer text = 
               new StringBuffer("la transaction est Interrompu, une erreur dans ModificationTypeCompteAction / disp :initierPage: ");
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
    
        ModificationTypeCompteForm modifCompteForm = (ModificationTypeCompteForm) form;
        
        GetContratCptByIdCmd getContratCpt = new GetContratCptByIdCmd();
        ContratCpt contrat = new ContratCpt();
        ContratCptId contratId = new ContratCptId();
        
        
        contratId.setCodStrcStrc(Long.valueOf(modifCompteForm.getCodStrcRech()));
        contratId.setCodPrdPrd(Long.valueOf(modifCompteForm.getCodPrdRech()));
        contratId.setNumCcptCcpt(Long.valueOf(modifCompteForm.getNumCcptRech()));
        
        modifCompteForm.clearForm();
        
        modifCompteForm.setCodStrcRech(StrHandler.lpad(contratId.getCodStrcStrc().toString(),'0',3));
        modifCompteForm.setCodPrdRech(StrHandler.lpad(contratId.getCodPrdPrd().toString(),'0',4));
        modifCompteForm.setNumCcptRech(StrHandler.lpad(contratId.getNumCcptCcpt().toString(),'0',6));
        
        
        contrat.setContratCptId(contratId);
        contrat = (ContratCpt) getContratCpt.execute(contrat);
        Collection listeCotitulaire = new ArrayList();
        CoTitulaire cotitulaire = new CoTitulaire();
        if (contrat != null && contrat.getNomIntiCcpt() != null){
           
            modifCompteForm.setLibelleCpt(contrat.getNomIntiCcpt());
            
        //-------------- Contrat est non valide ----------//
         if (contrat.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
            
           if(contrat.getBoolEngCcpt() == null ){
              // le contrat compte ne dispose pas d'engagement
              
            //-------------- Contrat d'une entité co-titulaire ----------//
            if (contrat.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)){
                listeCotitulaire = contrat.getClient().getCoTitulaires();
                modifCompteForm.setContratCpt(contrat);
                
                if (listeCotitulaire.size()>0){
                 modifCompteForm.setListeMembreEntiteCotit(new ArrayList(0));
                   for(Iterator it = listeCotitulaire.iterator(); it.hasNext();){
                   cotitulaire = (CoTitulaire) it.next();
                   modifCompteForm.getListeMembreEntiteCotit().add(cotitulaire);
                     
                   }
                   modifCompteForm.setTypeCotit(cotitulaire.getCodTcotCoti()); 
                   modifCompteForm.setTypeSignature(cotitulaire.getCodSigCoti());
                }  
                
                for (int i = 0; 
                     i < listeCotitulaire.size(); 
                     i++){
                    modifCompteForm.getListeMembreEntiteCotitChoisi().add("");
                     }
            }else {
                modifCompteForm.setAlert("nonCotitulaire");
            }
         }else if ( contrat.getBoolEngCcpt().equals(Long.valueOf("1"))){
             // 
              modifCompteForm.setAlert("engagementEncours");
         }
         
         }else {
             modifCompteForm.setAlert("nonValide");
         }
         
         
       
        }else {
        //------------------ Contrat inexistant ----------------------------//
            modifCompteForm.setAlert("inexistant");
        }
       
       return mapping.findForward("initierPage");
    }
    
    
    public ActionForward valider (ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {

      
        ActionMessages actionMessages = new ActionMessages();
        ParamAgence paramAgence = 
        (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent qui fait la saisie
    
        ModificationTypeCompteForm modifCompteForm = (ModificationTypeCompteForm) form;
        
      try{
        
        ParamModificationTypeCompteVo paramTypeCompteVo = new ParamModificationTypeCompteVo ();
        ModificationTypeCompteCmd modifierCmd = new ModificationTypeCompteCmd();
        
        paramTypeCompteVo.setCodeStructure(paramAgence.getCodStrcStrc());
        paramTypeCompteVo.setMatricule(Long.valueOf(paramAgence.getNumMatrUser()));
        modifCompteForm.getContratCpt().setNomIntiCcpt(modifCompteForm.getNomIntiCcpt());
        paramTypeCompteVo.setContratCpt(modifCompteForm.getContratCpt());
        
        Personne personne = new Personne();
        personne.setNumSeqPers(Long.valueOf(modifCompteForm.getNumSeqPersChoisi()));
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(Long.valueOf(modifCompteForm.getCodTpceTpceChoisi()));
        personne.setTypePiece(typePiece);
        
        personne.setNumPcePers(modifCompteForm.getNumPcePersChoisi());
        paramTypeCompteVo.setPersonne(personne);
        paramTypeCompteVo.setListeMembreEntiteCotit(modifCompteForm.getListeMembreEntiteCotit());
        paramTypeCompteVo = (ParamModificationTypeCompteVo) modifierCmd.execute(paramTypeCompteVo);
        
        if (! paramTypeCompteVo.hasError()){
            StringBuffer text   = new StringBuffer(" Le contrat numéro ");
            text.append(modifCompteForm.getCodStrcRech());
            text.append(modifCompteForm.getCodPrdRech());
            text.append(modifCompteForm.getNumCcptRech());
            text.append("  a été transféré de " );
            text.append( modifCompteForm.getLibelleCpt());
            text.append(" à ");
            text.append(modifCompteForm.getNomIntiCcpt());
            
            
           // StringBuffer ancText   = new StringBuffer(" Ancien Intitulé : ");
           // StringBuffer newText   = new StringBuffer(" Nouveau Intitulé :");
            
                       
            //modifCompteForm.setConfirmation1(ancText.toString());
            //modifCompteForm.setConfirmation2(newText.toString());
            modifCompteForm.setConfirmation(text.toString());
            
         return mapping.findForward("confirmation");
         
        }else {
            
            List listErreur = paramTypeCompteVo.getErrors();                    
            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
             }    
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");      
        }
        
    }catch(Exception e){
       com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
       StringBuffer text = 
           new StringBuffer("la transaction est Interrompu, une erreur dans ModificationTypeCompteAction / disp :valider: ");
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
                                   response.setContentLength(valueObject.getContent().length);
                                   ServletOutputStream ouputStream = response.getOutputStream();
                                   ouputStream.write(valueObject.getContent(), 0, valueObject.getContent().length);
                                   ouputStream.flush();
                                   ouputStream.close();
                           } catch (Exception e) {
                                
                                   e.printStackTrace();
                           }
                    
            return mapping.findForward("pageConfirmation");
                                                                          
        }
        
 
    
}

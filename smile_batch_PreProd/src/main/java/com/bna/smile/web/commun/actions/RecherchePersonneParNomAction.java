package com.bna.smile.web.commun.actions;

import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.TypePiece;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.GetListPieceAnnexeParNumSeqPersCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.RecherchePersonneParNomCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamListePieceAnnexeParNumSeqPersVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRecherchePersonneVo;
import com.bna.smile.web.commun.forms.RecherchePersonneParNomForm;

import com.bna.smile.web.commun.model.ParamAgence;

import java.io.IOException;

import java.util.ArrayList;

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

public class RecherchePersonneParNomAction extends DispatchAction {
   
    private static final Logger logger = Logger.getLogger(RecherchePersonneParNomAction.class);

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
        try {
         rechercheform.setTypePersonne("0");
         rechercheform.setListeDesPersonnes(new ArrayList(0));
         rechercheform.setListeDesPiecesAnnexes(new ArrayList(0));
         rechercheform.setNomNomPers("");
         rechercheform.setNomPrnPers("");
         rechercheform.setNumPcePers("");
         rechercheform.setLibTpceTpce("");
         rechercheform.setNombreRecherche("");
         rechercheform.setNom("");
         rechercheform.setPrenom("");
         rechercheform.setRaisonSociale("");
         rechercheform.setSigle("");
         
         rechercheform.setNomApresRecherche("");
         rechercheform.setPrenomApresRecherche("");  
         rechercheform.setRaisonSocialeApresRecherche("");   
         rechercheform.setSigleApresRecherche(""); 
         
        }catch(Exception e){
            logger.error("Exception : ",e);            
            return mapping.findForward("error");  
        }

            return mapping.findForward("initierPage");
     }
                                                                          
    public ActionForward recherchePersonne (ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
         ActionMessages actionMessages = new ActionMessages();

       try {
       
           rechercheform.setListeDesPersonnes(new ArrayList(0));
           rechercheform.setNombreRecherche("");
           
           RecherchePersonneParNomCmd rechercheNomCmd = new RecherchePersonneParNomCmd();
           ParamRecherchePersonneVo paramRechercheVo = new ParamRecherchePersonneVo();
            
           paramRechercheVo.setNom(rechercheform.getNom());
           paramRechercheVo.setPrenom(rechercheform.getPrenom());
           paramRechercheVo.setSigle(rechercheform.getSigle());
           paramRechercheVo.setRaisonSociale(rechercheform.getRaisonSociale());
           paramRechercheVo.setTypePersonne(rechercheform.getTypePersonne());
           
           paramRechercheVo = (ParamRecherchePersonneVo) rechercheNomCmd.execute(paramRechercheVo);
            
           if (! paramRechercheVo.hasError()) {
               rechercheform.setListeDesPersonnes(paramRechercheVo.getListeDesPersonnes());
               if (paramRechercheVo.getListeDesPersonnes().size() == 0){
                rechercheform.setNombreRecherche("Aucune personne trouvée");
               }else {
                rechercheform.setNombreRecherche(paramRechercheVo.getListeDesPersonnes().size() +" personne(s) trouvée(s)");
               }
               rechercheform.setNomApresRecherche(paramRechercheVo.getNom());
               rechercheform.setPrenomApresRecherche(paramRechercheVo.getPrenom());
               rechercheform.setRaisonSocialeApresRecherche(paramRechercheVo.getRaisonSociale());
               rechercheform.setSigle(paramRechercheVo.getSigle());
               
           } else {
               com.oxia.fwk.core.Error erreur = paramRechercheVo.getErrors().get(0);
               ActionMessage actionMessage = 
                                 new ActionMessage("exception.generique", 
                                     erreur.getDescription());
               actionMessages.add("Erreur ", actionMessage);
                   
               this.saveMessages(request, actionMessages);
               return mapping.findForward("error");  
               
           }
        
        }catch(Exception e){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RecherchePersonneParNomAction / recherchePersonne : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            logger.error("Exception : ",e);           
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }

            System.out.println(rechercheform.getNom());
           return mapping.findForward("initierPage");
     }  
        
    public ActionForward recherchePieceAnnexe (ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
         ActionMessages actionMessages = new ActionMessages();
         
         logger.info("Entree recherchePieceAnnexe ");   
       try {
            GetListPieceAnnexeParNumSeqPersCmd getListePiece = new GetListPieceAnnexeParNumSeqPersCmd();
            ParamListePieceAnnexeParNumSeqPersVo paramListe = new ParamListePieceAnnexeParNumSeqPersVo();
            
            paramListe.setNumSeqPers(Long.valueOf(rechercheform.getNumSeqPersChoisi()));
            
            paramListe = (ParamListePieceAnnexeParNumSeqPersVo) getListePiece.execute(paramListe);
            
           if (! paramListe.hasError()) {
               
               if (paramListe.getListeDesPiecesAnnexes().size() > 0){
                   rechercheform.setListeDesPiecesAnnexes(paramListe.getListeDesPiecesAnnexes());
               }
          
           } else {
               com.oxia.fwk.core.Error erreur = paramListe.getErrors().get(0);
               ActionMessage actionMessage = 
                                 new ActionMessage("exception.generique", 
                                     erreur.getDescription());
               actionMessages.add("Erreur ", actionMessage);
                   
               this.saveMessages(request, actionMessages);
               return mapping.findForward("error");  
               
           }
        
        }catch(Exception e){
            logger.info("Exception recherchePieceAnnexe " +e.toString());   
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RecherchePersonneParNomAction / recherchePieceAnnexe : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            logger.error("Exception : ",e);           
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }

           return mapping.findForward("detailRecherchePersonne");
     }  
     
    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
           
         ActionMessages actionMessages = new ActionMessages();
         
       
       try {
       
           rechercheform.setListeDesPersonnes(new ArrayList(0));
           rechercheform.setNom("");
           rechercheform.setPrenom("");
           rechercheform.setSigle("");
           rechercheform.setRaisonSociale("");
           rechercheform.setTypePersonne("0");
           rechercheform.setNombreRecherche(null);
           
            rechercheform.setNomApresRecherche("");
            rechercheform.setPrenomApresRecherche("");  
            rechercheform.setRaisonSocialeApresRecherche("");   
            rechercheform.setSigleApresRecherche(""); 
            
        }catch(Exception e){
            
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RecherchePersonneParNomAction / annuler : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            logger.error("Exception : ",e);           
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
        }

            return mapping.findForward("initierPage");
     }
        

   
 public ActionForward quitterDetail(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
           
         ActionMessages actionMessages = new ActionMessages();
         
       
           try {
           
                return mapping.findForward("initierPage");
                
           } catch(Exception e){
                
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RecherchePersonneParNomAction / quitterDetail : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  erreur.getDescription());
            actionMessages.add("Erreur ", actionMessage);
            logger.error("Exception : ",e);           
            this.saveMessages(request, actionMessages);
            return mapping.findForward("error");  
         }

          
    }
        
}

package com.bna.smile.web.commun.actions;

import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande.GetListPieceAnnexeParNumSeqPersCmd;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamListePieceAnnexeParNumSeqPersVo;
import com.bna.smile.web.commun.forms.RecherchePersonneParNomForm;

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

/**
 * Classe qui permet de rechercher le détail de la personne
 * @author Mdimagh Med Lassaad
 * @since 02/06/2008
 */
public class DetailRecherchePersonneParNomAction extends DispatchAction  {
    public DetailRecherchePersonneParNomAction() {
    }
    private static final Logger logger = Logger.getLogger(DetailRecherchePersonneParNomAction.class);

    
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
            rechercheform.setListeDesPiecesAnnexes(new ArrayList(0));
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
            logger.debug("Exception recherchePieceAnnexe " +e.toString());   
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans DetailRecherchePersonneParNomAction / recherchePieceAnnexe : ");
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
     
    public ActionForward quitter (ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

         RecherchePersonneParNomForm rechercheform =(RecherchePersonneParNomForm) form;
         ActionMessages actionMessages = new ActionMessages();
         
         logger.info("Quitter recherchePieceAnnexe ");   
       try {
  
            return mapping.findForward("quitter");  
         
        }catch(Exception e){
            logger.debug("Exception quitter recherchePieceAnnexe : " +e.toString());   
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans RecherchePersonneParNomAction / quitter : ");
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

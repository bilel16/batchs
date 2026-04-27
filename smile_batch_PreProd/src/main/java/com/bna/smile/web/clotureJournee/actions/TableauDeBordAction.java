package com.bna.smile.web.clotureJournee.actions;

import com.bna.commun.model.Chequier;
import com.bna.commun.model.JourneeStructure;
import com.bna.commun.model.JourneeStructureId;
import com.bna.commun.util.DateHandler;

import com.bna.smile.model.statistique.commande.GetTableauDeBordCmd;
import com.bna.smile.model.statistique.model.TableauDeBordVo;
import com.bna.smile.web.clotureJournee.forms.ClotureJourneeForm;
import com.bna.smile.web.clotureJournee.forms.TableauDeBordForm;
import com.bna.smile.web.commun.model.ParamAgence;

import com.bna.smile.web.moyenPaiement.demandeChequier.model.ChequierView;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class TableauDeBordAction extends DispatchAction  {
    public TableauDeBordAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

     
       
        
        ActionMessages actionMessages = new ActionMessages();
        try {
           
            //-----------------------------------------------
            //-------- Modificatio données client
              return mapping.findForward("initierPage");
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / initierPage : ");
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

    public ActionForward donneesSouscription(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
                TableauDeBordForm tableauDeBordForm = 
                (TableauDeBordForm)form;
                
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                tableauDeBordForm.setCodeStructure(paramAgence.getCodStrcStrc());
                GetTableauDeBordCmd getTableauDeBord =  new GetTableauDeBordCmd();
                TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
                tableauDeBordVo.setCodeStructure( tableauDeBordForm.getCodeStructure());
                tableauDeBordVo.setChoixRecherche(tableauDeBordForm.getChoixRecherche());
                
                tableauDeBordVo = (TableauDeBordVo) getTableauDeBord.execute(tableauDeBordVo);
                
                tableauDeBordForm.setNombreTatalSouscription(tableauDeBordVo.getNombreTatalSouscription());
                tableauDeBordForm.setListNombreSouscriptionParTypeClient(tableauDeBordVo.getListNombreSouscriptionParTypeClient());
                tableauDeBordForm.setListNombreSouscAttParTypeClient(tableauDeBordVo.getListNombreSouscAttParTypeClient());
                tableauDeBordForm.setListSouscriptionAtt(tableauDeBordVo.getListSouscriptionAttParTypeContrat());
                tableauDeBordForm.setListNombreSouscriptionParTypeContrat(tableauDeBordVo.getListNombreSouscriptionParTypeContrat());
                tableauDeBordForm.setListNombreSignatureParTypeContrat(tableauDeBordVo.getListNombreSignatureParTypeContrat() );
                tableauDeBordForm.setNbrSouscrAtt(tableauDeBordVo.getStatSouscription().getNbrSouscrAtt()); 
                tableauDeBordForm.setNombreTatalSignature(Long.valueOf(calculTotalList(tableauDeBordVo.getListNombreSignatureParTypeContrat())));
                    
                return mapping.findForward("initierPage");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / donneesSouscription : ");
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
    
  
    public ActionForward donneesMandat(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
                TableauDeBordForm tableauDeBordForm = 
                (TableauDeBordForm)form;
                
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                tableauDeBordForm.setCodeStructure(paramAgence.getCodStrcStrc());
                GetTableauDeBordCmd getTableauDeBord =  new GetTableauDeBordCmd();
                TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
                tableauDeBordVo.setCodeStructure( tableauDeBordForm.getCodeStructure());
                tableauDeBordVo.setChoixRecherche(tableauDeBordForm.getChoixRecherche());
                  tableauDeBordVo = (TableauDeBordVo) getTableauDeBord.execute(tableauDeBordVo);
                
                tableauDeBordForm.setNombreMandatCree(tableauDeBordVo.getNombreMandatCree());
                tableauDeBordForm.setListMandatCreationParTypeContrat(tableauDeBordVo.getListMandatCreationParTypeContrat());
                tableauDeBordForm.setListMandatRenouvleParTypeContrat(tableauDeBordVo.getListMandatRenouvellementParTypeContrat());
                tableauDeBordForm.setListMandatModifieParTypeContrat(tableauDeBordVo.getListMandatModificationParTypeContrat());
                tableauDeBordForm.setListMandatAnnuleParTypeContrat(tableauDeBordVo.getListMandatAnnulationParTypeContrat());
                
                tableauDeBordForm.setNombreMandatModifie(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatModificationParTypeContrat())));
                tableauDeBordForm.setNombreMandatRenouvle(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatRenouvellementParTypeContrat())));
                tableauDeBordForm.setNombreMandatAnnule(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatAnnulationParTypeContrat())));
                
                
                return mapping.findForward("initierPage");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / donneesMandat : ");
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


    public ActionForward donneesSupportMoyenPaiement(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        ActionMessages actionMessages = new ActionMessages();
        try {
                TableauDeBordForm tableauDeBordForm = 
                (TableauDeBordForm)form;
                
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                tableauDeBordForm.setCodeStructure(paramAgence.getCodStrcStrc());
                GetTableauDeBordCmd getTableauDeBord =  new GetTableauDeBordCmd();
                TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
                tableauDeBordVo.setCodeStructure( tableauDeBordForm.getCodeStructure());
                tableauDeBordVo.setChoixRecherche(tableauDeBordForm.getChoixRecherche());
                tableauDeBordVo = (TableauDeBordVo) getTableauDeBord.execute(tableauDeBordVo);
                
                //---------------- Validées
                tableauDeBordForm.setListenombreChequierDemandeParType(tableauDeBordVo.getListenombreChequierDemandeParTypeValide());
                tableauDeBordForm.setNombreChequierDemande(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeValide())));
                
                //---------------- En attente
                tableauDeBordForm.setListenombreChequierDemandeParTypeAttente(tableauDeBordVo.getListenombreChequierDemandeParTypeAttente());
                tableauDeBordForm.setNombreChequierDemandeAttente(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeAttente())));
                //---------------- Rejetée
                tableauDeBordForm.setListenombreChequierDemandeParTypeRejeter(tableauDeBordVo.getListenombreChequierDemandeParTypeRejete());
                tableauDeBordForm.setNombreChequierDemandeRejeter(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeRejete())));
            ///--------------------------------------------------------------------------------------///
            ///--------- Données des demandes cartes
            ///--------------------------------------------------------------------------------------///
            
                tableauDeBordForm.setListenombreCarteDemandeParType(tableauDeBordVo.getListenombreCarteDemandeParType());
                tableauDeBordForm.setListenombreCarteRecuParType(tableauDeBordVo.getListenombreCarteRecuParType() );
                tableauDeBordForm.setListenombreCarteDelivreParType(tableauDeBordVo.getListenombreCarteDelivreParType());
                tableauDeBordForm.setListenombreCarteAnnuleParType(tableauDeBordVo.getListenombreCarteAnnuleParType());
                tableauDeBordForm.setListenombreCarteDemandeNonValideParType(tableauDeBordVo.getListenombreCarteDemandeParTypeNonValide());
                tableauDeBordForm.setListenombreCarteRejeteParType(tableauDeBordVo.getListenombreCarteRejeteesParType());
                
                
                tableauDeBordForm.setNombreCarteDemandeValide(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteDemandeParType())));
                tableauDeBordForm.setNombreCarteRecu(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteRecuParType())));   
                tableauDeBordForm.setNombreCarteDelivre(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteDelivreParType())));
                tableauDeBordForm.setNombreCarteAnnule(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteAnnuleParType())));
                tableauDeBordForm.setNombreCarteDemandeNonValide(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteDemandeParTypeNonValide())));
                tableauDeBordForm.setNombreCarteRejete(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreCarteRejeteesParType())));
                
                return mapping.findForward("initierPage");
                
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / donneesSupportMoyenPaiement : ");
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


    public ActionForward donneesOpposition(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
                TableauDeBordForm tableauDeBordForm = 
                (TableauDeBordForm)form;
                
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                tableauDeBordForm.setCodeStructure(paramAgence.getCodStrcStrc());
                GetTableauDeBordCmd getTableauDeBord =  new GetTableauDeBordCmd();
                TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
                tableauDeBordVo.setCodeStructure( tableauDeBordForm.getCodeStructure());
                tableauDeBordVo.setChoixRecherche(tableauDeBordForm.getChoixRecherche());
                tableauDeBordVo = (TableauDeBordVo) getTableauDeBord.execute(tableauDeBordVo);
                
                tableauDeBordForm.setListeOppositionParType(tableauDeBordVo.getListeOppositionParType());
                tableauDeBordForm.setListeLeveOppositionParType(tableauDeBordVo.getListLeveOppositionParType());
                tableauDeBordForm.setNombreOpposition(Long.valueOf(calculTotalList(tableauDeBordVo.getListLeveOppositionParType())));
                tableauDeBordForm.setNombreLeveeOpposition(Long.valueOf(calculTotalList(tableauDeBordVo.getListLeveOppositionParType())));
                
                return mapping.findForward("initierPage");
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / donneesOpposition : ");
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
    
    
    public ActionForward donneesModification(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
                TableauDeBordForm tableauDeBordForm = 
                (TableauDeBordForm)form;
                
                ParamAgence paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                tableauDeBordForm.setCodeStructure(paramAgence.getCodStrcStrc());
                GetTableauDeBordCmd getTableauDeBord =  new GetTableauDeBordCmd();
                TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
                tableauDeBordVo.setCodeStructure( tableauDeBordForm.getCodeStructure());
                tableauDeBordVo.setChoixRecherche(tableauDeBordForm.getChoixRecherche());
                tableauDeBordVo = (TableauDeBordVo) getTableauDeBord.execute(tableauDeBordVo);
           
                tableauDeBordForm.setListeModificationDonneeParType(tableauDeBordVo.getListeModificationDonneeParType());
                tableauDeBordForm.setNombreModificationDonnees(Long.valueOf(calculTotalList(tableauDeBordVo.getListeModificationDonneeParType())));
                
                return mapping.findForward("initierPage");
        
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans TableauDeBordAction / donneesModification : ");
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


    private long calculTotalList(List list ) {
        ListOrderedMap listNbreSign;
        long i = 0 ;
        for(Iterator it = list.iterator();it.hasNext();){
            listNbreSign = (ListOrderedMap)it.next();  
            if ((listNbreSign.getValue(0)).toString() != null && listNbreSign.getValue(1).toString() != null && listNbreSign.getValue(2).toString() != null) {
             
             i =   i + Long.valueOf(listNbreSign.getValue(2).toString());
            
            }
        }
        
        return i;
    }
  
}

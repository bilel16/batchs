package com.bna.smile.web.clotureDomaine.actions;


import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.commun.model.TypeModification;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.clotureDomaine.commande.ClotureDomaineCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailDonneeClotureCmd;
import com.bna.smile.model.clotureDomaine.commande.DetailRelationClientCmd;
import com.bna.smile.model.clotureDomaine.commande.GetDonneeSouscriptionCmd;
import com.bna.smile.model.clotureDomaine.commande.GetStatAssVieCmd;
import com.bna.smile.model.clotureDomaine.commande.UpdateDomaineCmd;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamRechercheModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListDemandesChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListOppositionCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListesDemandesCheques;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamDemandeCheque;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.model.statistique.model.TableauDeBordVo;
import com.bna.smile.web.clotureDomaine.forms.ClotureDomaineForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.actions.ConsultationOppMoyPaieAction;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ClotureDomaineAction extends DispatchAction {
    public ClotureDomaineAction() {
    }

    private static final Logger logger = 
        Logger.getLogger(ConsultationOppMoyPaieAction.class);
    ParamAgence paramAgence = new ParamAgence();

    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {


        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            clotureDomaineForm.setChoixRecherche("1");
            return mapping.findForward("initierPage");

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

    public ActionForward donneesSouscription(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            GetDonneeSouscriptionCmd getDonneeSouscriptionCmd = 
                new GetDonneeSouscriptionCmd();
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            tableauDeBordVo = 
                    (TableauDeBordVo)getDonneeSouscriptionCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {
                /*donnes de souscription*/
                /* clotureDomaineForm.setNombreTatalSouscription(tableauDeBordVo.getNombreTatalSouscription());*/
                clotureDomaineForm.setListNombreSouscriptionParTypeContrat(tableauDeBordVo.getListNombreSouscriptionParTypeContrat());
                clotureDomaineForm.setListSouscriptionAtt(tableauDeBordVo.getListSouscriptionAttParTypeContrat());
                clotureDomaineForm.setListSouscriptionREj(tableauDeBordVo.getListSouscriptionRejParTypeContrat());

                /* clotureDomaineForm.setNbrSouscrVal(tableauDeBordVo.getStatSouscription().getNbrSouscrVal());
                clotureDomaineForm.setNbrSouscrAtt(tableauDeBordVo.getStatSouscription().getNbrSouscrAtt());
                clotureDomaineForm.setNbrSouscrRej(tableauDeBordVo.getStatSouscription().getNbrSouscrRes());*/

                clotureDomaineForm.setListNombreSignatureParTypeContrat(tableauDeBordVo.getListNombreSignatureParTypeContrat());
                clotureDomaineForm.setNombreTatalSignature(Long.valueOf(calculTotalList(tableauDeBordVo.getListNombreSignatureParTypeContrat())));


                return mapping.findForward("clotDomContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            GetDonneeSouscriptionCmd getDonneeSouscriptionCmd = 
                new GetDonneeSouscriptionCmd();
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            tableauDeBordVo = 
                    (TableauDeBordVo)getDonneeSouscriptionCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {

                /*donnes de procuration*/
                clotureDomaineForm.setNombreMandatCree(tableauDeBordVo.getNombreMandatCree());
                clotureDomaineForm.setListMandatCreationParTypeContrat(tableauDeBordVo.getListMandatCreationParTypeContrat());
                clotureDomaineForm.setListMandatRenouvleParTypeContrat(tableauDeBordVo.getListMandatRenouvellementParTypeContrat());
                clotureDomaineForm.setListMandatModifieParTypeContrat(tableauDeBordVo.getListMandatModificationParTypeContrat());
                clotureDomaineForm.setListMandatAnnuleParTypeContrat(tableauDeBordVo.getListMandatAnnulationParTypeContrat());

                clotureDomaineForm.setNombreMandatModifie(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatModificationParTypeContrat())));
                clotureDomaineForm.setNombreMandatRenouvle(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatRenouvellementParTypeContrat())));
                clotureDomaineForm.setNombreMandatAnnule(Long.valueOf(calculTotalList(tableauDeBordVo.getListMandatAnnulationParTypeContrat())));
                /*clotureDomaineForm.setNbrTotMandat(clotureDomaineForm.getNombreMandatCree()+
                clotureDomaineForm.getNombreMandatAnnule()+clotureDomaineForm.getNombreMandatModifie()+
                clotureDomaineForm.getNombreMandatRenouvle());*/
                return mapping.findForward("clotDomContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward donneesSupportMoyenPaiement(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws IOException, 
                                                                                          ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            //clotureDomaineForm.setChoixRecherche("1");    
            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            GetDonneeSouscriptionCmd getDonneeSouscriptionCmd = 
                new GetDonneeSouscriptionCmd();
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            tableauDeBordVo = 
                    (TableauDeBordVo)getDonneeSouscriptionCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {

                /*donnees chequiers*/
                clotureDomaineForm.setListenombreChequierDemandeParType(tableauDeBordVo.getListenombreChequierDemandeParTypeValide());
                // clotureDomaineForm.setNombreChequierDemande(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeValide())));
                clotureDomaineForm.setListenombreChequierDemandeParTypeAttente(tableauDeBordVo.getListenombreChequierDemandeParTypeAttente());
                // clotureDomaineForm.setNombreChequierDemandeAttente(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeAttente())));
                clotureDomaineForm.setListenombreChequierDemandeParTypeRejeter(tableauDeBordVo.getListenombreChequierDemandeParTypeRejete());
                //  clotureDomaineForm.setNombreChequierDemandeRejeter(Long.valueOf(calculTotalList(tableauDeBordVo.getListenombreChequierDemandeParTypeRejete())));
                //                clotureDomaineForm.setNbrTotDemandeCheq(clotureDomaineForm.getNombreChequierDemande()+
                // clotureDomaineForm.getNombreChequierDemandeAttente()+clotureDomaineForm.getNombreChequierDemandeRejeter());
                clotureDomaineForm.setListenombreChequierDemandeParTyperRecu(tableauDeBordVo.getListenombreChequierDemandeParTyperRecu());
                clotureDomaineForm.setListenombreChequierDemandeParTypeDeliv(tableauDeBordVo.getListenombreChequierDemandeParTypeDeliv());

                return mapping.findForward("clotDomContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward donneesOpposition(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            //clotureDomaineForm.setChoixRecherche("1");    
            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            GetDonneeSouscriptionCmd getDonneeSouscriptionCmd = 
                new GetDonneeSouscriptionCmd();
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            tableauDeBordVo = 
                    (TableauDeBordVo)getDonneeSouscriptionCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {
                clotureDomaineForm.setListeOppositionParType(tableauDeBordVo.getListeOppositionParType());
                clotureDomaineForm.setListeLeveOppositionParType(tableauDeBordVo.getListLeveOppositionParType());
                clotureDomaineForm.setNombreOpposition(Long.valueOf(calculTotalList(tableauDeBordVo.getListLeveOppositionParType())));
                clotureDomaineForm.setNombreLeveeOpposition(Long.valueOf(calculTotalList(tableauDeBordVo.getListLeveOppositionParType())));


                return mapping.findForward("clotDomContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward donneesModification(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CLIENT);
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            clotureDomaineForm.setChoixRecherche("5");
            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            GetDonneeSouscriptionCmd getDonneeSouscriptionCmd = 
                new GetDonneeSouscriptionCmd();
            
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            tableauDeBordVo = 
                    (TableauDeBordVo)getDonneeSouscriptionCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {
                clotureDomaineForm.setListeModificationDonneeParType(tableauDeBordVo.getListeModificationDonneeParType());
                clotureDomaineForm.setNombreModificationDonnees(Long.valueOf(calculTotalList(tableauDeBordVo.getListeModificationDonneeParType())));
                clotureDomaineForm.setNbrOper672(tableauDeBordVo.getNbrOper672());  
                clotureDomaineForm.setMntOper672(tableauDeBordVo.getMntOper672());  
                clotureDomaineForm.setNbrOper703(tableauDeBordVo.getNbrOper703());  
                clotureDomaineForm.setMntOper703(tableauDeBordVo.getMntOper703());
                return mapping.findForward("clotDomClient");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
           
          
            
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesModification : ");
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

    public ActionForward cloturerDomaine(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());

            if (clotureDomaineForm.getChoixRecherche().equalsIgnoreCase("5")) {
                journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CLIENT);
            } else {
                journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            }
            
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journStrucDomVo.setJourneeStructureDomaineId(journeeStructureDomaineId);
            journStrucDomVo.setMatriculeInitiateur(paramAgence.getNumMatrUser());
            journStrucDomVo.setNbrSousc(clotureDomaineForm.getNbrSouscrVal().toString());
            journStrucDomVo.setNbrCheq(clotureDomaineForm.getNombreChequierDemande().toString());
            //journStrucDomVo.setNbrCart(clotureDomaineForm.getNombreCarteDemandeValide().toString());
            journStrucDomVo.setNbrMandCre(clotureDomaineForm.getNombreMandatCree().toString());
            journStrucDomVo.setNbrMandMod(clotureDomaineForm.getNombreMandatModifie().toString());
            journStrucDomVo.setNbrMandRen(clotureDomaineForm.getNombreMandatRenouvle().toString());
            journStrucDomVo.setNbrMandAnn(clotureDomaineForm.getNombreMandatAnnule().toString());
            journStrucDomVo.setListeModificationDonneeParType(clotureDomaineForm.getListeModificationDonneeParType());
            journStrucDomVo.setNbrOper672(clotureDomaineForm.getNbrOper672());
            journStrucDomVo.setNbrOper703(clotureDomaineForm.getNbrOper703());
            journStrucDomVo.setMntOper672(clotureDomaineForm.getMntOper672());
            journStrucDomVo.setMntOper703(clotureDomaineForm.getMntOper703());
            
            ClotureDomaineCmd clotureDomaineCmd = new ClotureDomaineCmd();


            JournStrucDomVo journStrucDomVoRet = 
                (JournStrucDomVo)clotureDomaineCmd.execute(journStrucDomVo);
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
            if (journStrucDomVoRet.getDernierDomaine() == 1) {
                clotureDomaineForm.setDatecloturee(DateHandler.dateToStr(journStrucDomVoRet.getJourneeStructureDomaineId().getDatJrnJrn()));
                clotureDomaineForm.setDateOuverte(DateHandler.dateToStr(journStrucDomVoRet.getNouvelleJournee()));
                return mapping.findForward("clotureJournee");
            } else {
                clotureDomaineForm.setLibDom(journStrucDomVoRet.getLibDomaine());
                return mapping.findForward("confirmClotDom");
            }


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward reouvrirDomaine(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws IOException, 
                                                                              ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {
            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;

            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            JourneeStructureDomaineId journeeStructureDomaineId = 
                new JourneeStructureDomaineId();
            JourneeStructureDomaine journeeStructureDomaine = 
                new JourneeStructureDomaine();
            JournStrucDomVo journStrucDomVo = new JournStrucDomVo();
            journeeStructureDomaineId.setCodStrcStrc(paramAgence.getCodStrcStrc());
            if (!clotureDomaineForm.getChoixRecherche().equalsIgnoreCase("5")) {
                journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
            } else {
                journeeStructureDomaineId.setCodDomDomm(Constants.COD_DOM_CLIENT);
            }
            journeeStructureDomaineId.setDatJrnJrn(DateHandler.strToDate(paramAgence.getDateComptable()));
            journeeStructureDomaine.setJourneeStructureDomaineId(journeeStructureDomaineId);
            //journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_OUV);


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
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward detailContrat(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, 
                                       HttpServletResponse response) throws IOException, 
                                                                            ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setCodeProduit(Long.valueOf(clotureDomaineForm.getCodproduit()));
            DetailDonneeClotureCmd detailDonneeClotureCmd = 
                new DetailDonneeClotureCmd();
            tableauDeBordVo = 
                    (TableauDeBordVo)detailDonneeClotureCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {
                clotureDomaineForm.setListDetailSouscrEnAttente(tableauDeBordVo.getListDetailSouscrEnAttente());
                clotureDomaineForm.setListDetailSouscrvalide(tableauDeBordVo.getListDetailSouscrvalide());
                clotureDomaineForm.setListDetailSouscrRej(tableauDeBordVo.getListDetailSouscrRej());


                return mapping.findForward("detailDomaineContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward detailMandat(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws IOException, 
                                                                           ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");

            clotureDomaineForm.setCodeStructure(paramAgence.getCodStrcStrc());
            TableauDeBordVo tableauDeBordVo = new TableauDeBordVo();
            tableauDeBordVo.setCodeStructure(clotureDomaineForm.getCodeStructure());
            tableauDeBordVo.setChoixRecherche(clotureDomaineForm.getChoixRecherche());
            tableauDeBordVo.setCodeProduit(Long.valueOf(clotureDomaineForm.getCodproduit()));
            DetailDonneeClotureCmd detailDonneeClotureCmd = 
                new DetailDonneeClotureCmd();
            tableauDeBordVo = 
                    (TableauDeBordVo)detailDonneeClotureCmd.execute(tableauDeBordVo);
            if (!tableauDeBordVo.hasError()) {
                clotureDomaineForm.setListeCreationMandat(tableauDeBordVo.getListeDetailCreationMandat());
                clotureDomaineForm.setListeModifMandat(tableauDeBordVo.getListeDetailModifMandat());
                clotureDomaineForm.setListeMandatAnn(tableauDeBordVo.getListeDetailAnnulMandat());
                clotureDomaineForm.setListeMandatRen(tableauDeBordVo.getListeDetailRenouvMandat());


                return mapping.findForward("detailDomaineContrat");
            } else {
                List listErreur = tableauDeBordVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward detailMoyenPaiement(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");


            ContratPersonne contratPersonne = new ContratPersonne();
            PersonneStrc personneStrc = new PersonneStrc();
            ParamDemandeCheque paramDemandeCheque = new ParamDemandeCheque();
            ListesDemandesCheques listesDemandesCheques = 
                new ListesDemandesCheques();
            GetListDemandesChequesCmd getListDemandesChequesCmd = 
                new GetListDemandesChequesCmd();
            ContratCptId contratCptId = new ContratCptId();
            contratCptId.setCodStrcStrc(clotureDomaineForm.getCodeStructure());
            contratPersonne.setPersonneId(personneStrc);
            contratPersonne.setContratCptId(contratCptId);
            paramDemandeCheque.setContratPersonne(contratPersonne);
            paramDemandeCheque.setDateDebut(DateHandler.strToDate(paramAgence.getDateComptable()));
            paramDemandeCheque.setDateFin(DateHandler.strToDate(paramAgence.getDateComptable()));
            listesDemandesCheques = 
                    (ListesDemandesCheques)getListDemandesChequesCmd.execute(paramDemandeCheque);
            if (!listesDemandesCheques.hasError()) {
                if (listesDemandesCheques != null && 
                    listesDemandesCheques.getListeAttente().size() > 0) {
                    clotureDomaineForm.setListeChqAttente(listesDemandesCheques.getListeAttente());
                }
                if (listesDemandesCheques != null && 
                    listesDemandesCheques.getListeValidee().size() > 0) {
                    clotureDomaineForm.setListeChqvalide(listesDemandesCheques.getListeValidee());
                }
                if (listesDemandesCheques != null && 
                    listesDemandesCheques.getListeRejetee().size() > 0) {
                    clotureDomaineForm.setListeChqrejete(listesDemandesCheques.getListeRejetee());
                }
                if (listesDemandesCheques != null && 
                    listesDemandesCheques.getListeTotDelivree().size() > 0) {
                    clotureDomaineForm.setListeChqDeliv(listesDemandesCheques.getListeTotDelivree());
                }
                if (listesDemandesCheques != null && 
                    listesDemandesCheques.getListeTotSatisfaite().size() > 0) {
                    clotureDomaineForm.setListeChqRecu(listesDemandesCheques.getListeTotSatisfaite());
                }
                return mapping.findForward("detailDomaineContrat");
            } else {
                List listErreur = listesDemandesCheques.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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


    public ActionForward detailrelationClt(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws IOException, 
                                                                                ServletException {
        ActionMessages actionMessages = new ActionMessages();
        try {

            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");


            ParamRechercheModificationDonneesVo paramRechercheModificationDonneesVo = 
                new ParamRechercheModificationDonneesVo();
            paramRechercheModificationDonneesVo.setDateDebut(DateHandler.strToDate(paramAgence.getDateJours()));
            TypeModification typeModification = new TypeModification();
            typeModification.setCodCodModf(Long.valueOf(clotureDomaineForm.getCodModif()));
            paramRechercheModificationDonneesVo.setTypeModification(typeModification);
            DetailRelationClientCmd detailRelationClientCmd = 
                new DetailRelationClientCmd();
            paramRechercheModificationDonneesVo = 
                    (ParamRechercheModificationDonneesVo)detailRelationClientCmd.execute(paramRechercheModificationDonneesVo);
            if (!paramRechercheModificationDonneesVo.hasError()) {
                clotureDomaineForm.setListeModificationClient(paramRechercheModificationDonneesVo.getListeDesModifications());
                return mapping.findForward("detailDomaineClient");
            } else {
                List listErreur = 
                    paramRechercheModificationDonneesVo.getErrors();

                for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
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
            
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ClotureDomaineAction / donneesSouscription : ");
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

    public ActionForward detailOpposition(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
        ActionMessages actionMessages = new ActionMessages();

        try {
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            ParamRechercheOpposition paramRechercheOpposition = 
                new ParamRechercheOpposition();
            ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
            paramRechercheOpposition.setCodStrcStrc(paramAgence.getCodStrcStrc());
            paramRechercheOpposition.setTypeMoyPaie(Long.valueOf(clotureDomaineForm.getCodMoypTmoy()));
            paramRechercheOpposition.setTypeOper(clotureDomaineForm.getTypeOpp());
            paramRechercheOpposition.setDateDebutConsult(DateHandler.strToDate(paramAgence.getDateJours()));
            paramRechercheOpposition.setDateFinConsult(DateHandler.addJour(DateHandler.strToDate(paramAgence.getDateJours()), 
                                                                           1));
            GetListOppositionCmd getListOppositionCmd = 
                new GetListOppositionCmd();
            Listes l = new Listes();
            l = (Listes)getListOppositionCmd.execute(paramRechercheOpposition);

            if ((l != null) && l.getList().size() != 0) {
                clotureDomaineForm.setListDetailOpposition(l.getList());
            } else {
                logger.error("La liste des oppositions est vide === NULL");
            }


        } catch (Exception e) {

            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", e.getMessage());
            actionMessages.add("Erreur ", actionMessage);
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ", e);
            return mapping.findForward("error");

        }
        return mapping.findForward("detailDomaineContrat");
    }

    private long calculTotalList(List list) {
        ListOrderedMap listNbreSign;
        long i = 0;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            listNbreSign = (ListOrderedMap)it.next();
            if ((listNbreSign.getValue(0)).toString() != null && 
                listNbreSign.getValue(1).toString() != null && 
                listNbreSign.getValue(2).toString() != null) {

                i = i + Long.valueOf(listNbreSign.getValue(2).toString());

            }
        }

        return i;
    }

    public ActionForward imprimerRecapContratCompte(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws IOException, 
                                                                                         ServletException {
        ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            parameters.put("P_LIBELLE_ETAT", "RECAP JOURNEE");
            parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
            parameters.put("P_DAT_JOURNEE", paramAgence.getDateJours());
            parameters.put("P_COD_STRC_STRC", 
                           paramAgence.getCodStrcStrc().toString());


            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper                   
            valueObject.setNomReport("recapClotureDomContrat");
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("clotDomContrat");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :imprimerOperationSurMandat ");
            text.append(e.toString());
            logger.error("Exception : ", e);
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

    public ActionForward imprimerRecapClient(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws IOException, 
                                                                                  ServletException {
        ClotureDomaineForm clotureDomaineForm = (ClotureDomaineForm)form;
        ActionMessages actionMessages = new ActionMessages();
        try {
            CommonReportVO valueObject = new CommonReportVO();
            ParamAgence paramAgence = new ParamAgence();
            paramAgence = 
                    (ParamAgence)request.getSession().getAttribute("paramAgBNA");
            Map parameters = new HashMap();

            parameters.put("P_LIBELLE_ETAT", "RECAP JOURNEE");
            parameters.put("P_NUM_MATR_USER", paramAgence.getNumMatrUser());
            parameters.put("P_DAT_JOURNEE", paramAgence.getDateJours());
            parameters.put("P_COD_STRC_STRC", 
                           paramAgence.getCodStrcStrc().toString());


            valueObject.setParams(parameters);
            parameters = null;
            // indiquer le nom du fichier jasper                   
            valueObject.setNomReport("recapClotureDomClient");
            request.getSession().setAttribute("CommonPrintVo", valueObject);
            request.setAttribute("print", "1");
            return mapping.findForward("clotDomClient");
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationMandatAction / Dispatch Action :imprimerOperationSurMandat ");
            text.append(e.toString());
            logger.error("Exception : ", e);
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

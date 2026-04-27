package com.bna.smile.web.commun.actions;

import com.bna.commun.model.Activite;
import com.bna.commun.model.ActiviteId;
import com.bna.commun.model.CategoriePersonne;
import com.bna.commun.model.FormeJuridique;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.PieceAnnexeId;
import com.bna.commun.model.Profession;
import com.bna.commun.model.ProfessionId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.model.Adresse;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.InsertPersonneCmd;
import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.model.Tuteur;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetTuteurCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;
import com.bna.smile.web.commun.forms.CreationPersonneForm;
import com.bna.smile.web.procuration.forms.ConsultMandantContratForm;
import com.bna.smile.web.souscription.forms.SouscriptionContratCompteForm;

import com.oxia.fwk.context.Context;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class CreationPersonneAction extends DispatchAction {


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {
            CreationPersonneForm creationPersonneForm = 
                (CreationPersonneForm)form;

            String indexElementAppel = 
                request.getParameter("indexElementAppel");
            creationPersonneForm.clearForm();
            creationPersonneForm.setIndexElementAppel(indexElementAppel);
            
            return mapping.findForward("initierCreationPersonne");

        } catch (Exception e) {

            return mapping.findForward("error");
        }
    }


    /*******************************************************/
    public ActionForward appelPageCreationPersonne(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws IOException, 
                                                                                        ServletException {

    CreationPersonneForm creationPersonneForm = (CreationPersonneForm)form;
    creationPersonneForm.clearForm();
    // -- Initier la date du jour
    DateFormat myformat = new SimpleDateFormat("dd/MM/yyyy");
    String dateDuJour = myformat.format(new Date());
    creationPersonneForm.setDateActuelle(dateDuJour);
        
    //---Fin initialisation date
    String typePieceCreer = request.getParameter("typePieceCreer");
    String numeroPieceCreer = request.getParameter("numeroPieceCreer");
    ///String numeroSequenceC =request.getParameter("numeroSequenceCreationPersonne");
     creationPersonneForm.setCodTypePieceClt("");
     creationPersonneForm.setNumPieceClient("");

    
    if (typePieceCreer.equalsIgnoreCase(Constants.COD_NUM_ORDRE.toString())){
        String indexElementAppel;
        indexElementAppel = request.getParameter("indexElementAppel");
        creationPersonneForm.setIndexElementAppel(indexElementAppel);
        creationPersonneForm.setCodTypePieceClt(Constants.COD_NUM_ORDRE.toString());
        
        //-- pour le cas d'une personne Tunsisienne physique 
    
    }else if (typePieceCreer.equals(Constants.COD_CIN.toString())) {
        creationPersonneForm.setCodTypePieceClt(typePieceCreer);
        creationPersonneForm.setNumPieceClient(numeroPieceCreer);
    
    } else { //--pour le cas d'un étranger
       
        creationPersonneForm.setTypePieceAnnexe(typePieceCreer);
        creationPersonneForm.setNumPieceAnnexe(numeroPieceCreer);
     }
    
        return mapping.findForward("initierCreationPersonne");
        
}

    public ActionForward validerCreationPersonne(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws IOException, 
                                                                                      ServletException {

        CreationPersonneForm creationPersonneForm = (CreationPersonneForm)form;
        Personne personne = new Personne();
        ActionMessages actionMessages = new ActionMessages();
        StringBuffer niveauAvancement = new StringBuffer();
        
        
       try{
        // categorie personnne
        niveauAvancement.append(" categorie personnne ");
        CategoriePersonne categoriePersonne = new CategoriePersonne();
        categoriePersonne.setCodCatpCatp(creationPersonneForm.getCategoriePersonneClt());

        // type pièce
        niveauAvancement.append("  type pièce ");
        TypePiece typePiece = new TypePiece();
        typePiece.setCodTpceTpce(new Long(creationPersonneForm.getCodTypePieceClt()));
        
        // pays naissance
        niveauAvancement.append("  pays naissance ");
        Pays paysNais = new Pays();
        paysNais.setCodPaysPays(creationPersonneForm.getCodPaysNaisClt());
        
        // nationalite
         niveauAvancement.append("  nationalite ");
        Pays paysNat = new Pays();
        paysNat.setCodPaysPays(creationPersonneForm.getCodNationaliteClt());

        //Gouvernorat
        niveauAvancement.append("  Gouvernorat ");
        Gouvernorat gouvernorat = new Gouvernorat();
       
        //typePiece.setCodTpceTpce(new Long(souscriptionContratCompteForm.getCodTypePieceId()));
        gouvernorat.setCodGouvGouv(new Long(creationPersonneForm.getCodLieuDelivClt()));

        // Activite
        niveauAvancement.append("  Activite ");
         
        Activite activite = new Activite();
        ActiviteId activiteId = new ActiviteId();
        activiteId.setCodActAct(creationPersonneForm.getCodActiviteClt()); //souscriptionContratCompteForm.getCodActiviteClt());
        activiteId.setCodSactSact(new Long(creationPersonneForm.getCodSclasActiviteClt()));
        activiteId.setCodCactCact(creationPersonneForm.getCodClasActiviteClt());
        activite.setActiviteId(activiteId);

        //profession
        niveauAvancement.append("  profession ");
         
        Profession profession = new Profession();
        ProfessionId professionId = new ProfessionId();
        professionId.setCodGproGpro(new Long(creationPersonneForm.getCodGroupProfPers()));
        professionId.setCodProfProf(new Long(creationPersonneForm.getCodProfPers())); //new Integer(souscriptionContratCompteForm.getCodProfClt()));
        profession.setProfessionId(professionId);

        //forme juridique
        niveauAvancement.append("  forme juridique ");
        
        FormeJuridique formeJuridique = new FormeJuridique();
        formeJuridique.setCodFjFj(creationPersonneForm.getCodeFormeJuridiquePers());

        //Adresse de résidence
         niveauAvancement.append(" Adresse de résidence ");
         
        Adresse adresseResidence = new Adresse();
        adresseResidence.setImmeuble(creationPersonneForm.getImmeubleAdrResid());
        adresseResidence.setRue(creationPersonneForm.getRueAdrResid());
        adresseResidence.setCite(creationPersonneForm.getCiteAdrResid());
        adresseResidence.setVille(creationPersonneForm.getVilleAdrResid());
        adresseResidence.setCodPaysPays(creationPersonneForm.getCodPayAdrResid());
        adresseResidence.setCodCpCp(creationPersonneForm.getCodePostalAdrResid());

        //Adresse professionnelle
         niveauAvancement.append(" Adresse professionnelle ");
         
        Adresse adresseProfessionnelle = new Adresse();
        adresseProfessionnelle.setImmeuble(creationPersonneForm.getImmeubleAdrProf());
        adresseProfessionnelle.setRue(creationPersonneForm.getRueAdrProf());
        adresseProfessionnelle.setCite(creationPersonneForm.getCiteAdrProf());
        adresseProfessionnelle.setVille(creationPersonneForm.getVilleAdrProf());
        adresseProfessionnelle.setCodPaysPays(creationPersonneForm.getCodPayAdrProf());
        adresseProfessionnelle.setCodCpCp(creationPersonneForm.getCodePostalAdrProf());

        //Piece annexe si elle existe 
         niveauAvancement.append(" Piece annexe ");
        Set listPieceAnn = new HashSet();
        if (!creationPersonneForm.getTypePieceAnnexe().equals("") && 
            !creationPersonneForm.getNumPieceAnnexe().equals("") && 
            !creationPersonneForm.getDateDellivPiann().equals("") && 
            !creationPersonneForm.getDateFinPian().equals("")) {
            PieceAnnexe pieceAnnexe = new PieceAnnexe();
            PieceAnnexeId pieceAnnexeId = new PieceAnnexeId();
            pieceAnnexeId.setCodTpceTpce(new Long(creationPersonneForm.getTypePieceAnnexe()));
            pieceAnnexeId.setNumPcePian(creationPersonneForm.getNumPieceAnnexe());
            pieceAnnexe.setPieceAnnexeId(pieceAnnexeId);
            pieceAnnexe.setDatDelvPian(DateHandler.strToDate(creationPersonneForm.getDateDellivPiann()));
            pieceAnnexe.setDatFvalPian(DateHandler.strToDate(creationPersonneForm.getDateFinPian()));
            listPieceAnn = new HashSet();
            listPieceAnn.add(pieceAnnexe);
            personne.setPieceAnnexes(listPieceAnn);
        }
        //#########################################################################################
        //Instantiation de la personne
        niveauAvancement.append(" Instantiation de la personne ");
        personne.setTypePiece(typePiece);
        personne.setNumPcePers(creationPersonneForm.getNumPieceClient());
        personne.setDatDlvPers(DateHandler.strToDate(creationPersonneForm.getDateDelivClt()));
        personne.setCategoriePersonne(categoriePersonne);
        personne.setPaysByCodNaisPays(paysNais);
        personne.setPaysByCodNat1Pays(paysNat);
        personne.setActivite(activite);
        personne.setProfession(profession);
        personne.setNomNomPers(creationPersonneForm.getNomPersClt());
        personne.setNomPrnPers(creationPersonneForm.getPrenomPersClt());
        personne.setLibTitrPers(creationPersonneForm.getTitrePersClt());
        personne.setCodSexPers(creationPersonneForm.getSexeClt());
        personne.setDatNaisPers(DateHandler.strToDate(creationPersonneForm.getDateNaisClt()));
        personne.setLibNaisPers(creationPersonneForm.getLieuNaisClt());
        personne.setCodSitfPers(creationPersonneForm.getSitFamilialeClt());
        personne.setBoolResPers(Long.valueOf(creationPersonneForm.getResidentClt()));
        personne.setNomPrnpPers(creationPersonneForm.getNomPereClt());
        personne.setFormeJuridique(formeJuridique);
        personne.setAdresseResid(adresseResidence);
        personne.setAdresseProf(adresseProfessionnelle);
        personne.setGouvernorat(gouvernorat);

        //#############################################"
        // Information complementaire
         niveauAvancement.append(" Information complementaire");
         
         personne.setNomNomoPers(creationPersonneForm.getNomNomoPers());
         personne.setNomPrnoPers(creationPersonneForm.getNomPrnoPers());
         personne.setNomNomaPers(creationPersonneForm.getNomNomaPers());
         personne.setNomPrnaPers(creationPersonneForm.getNomPrnaPers());
         personne.setNomNommPers(creationPersonneForm.getNomNommPers());
         personne.setNomPrnmPers(creationPersonneForm.getNomPrnmPers());
         personne.setNumTelPers(creationPersonneForm.getNumTelPers());
         personne.setNumFaxPers(creationPersonneForm.getNumFaxpers());
         personne.setAdrMailPers(creationPersonneForm.getAdrMailPers());
         personne.setAdrWebPers(creationPersonneForm.getAdrWebPers());
         personne.setAdrSwiftPers(creationPersonneForm.getAdrSwiftPers());
         personne.setAdrTlxPers(creationPersonneForm.getAdrTlxPers());
         personne.setNbrEnfPers(new Long(creationPersonneForm.getNbrEnfPers()));
         personne.setNumAffsPers(creationPersonneForm.getNumAffsPers());
         
         
        // appel à la commande 
        InsertPersonneCmd insertPersonneCmd = new InsertPersonneCmd();
        personne = (Personne)insertPersonneCmd.execute(personne);
     
        if (personne.hasError()){
            List listErreur = personne.getErrors();                    
            for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                com.oxia.fwk.core.Error erreur = (com.oxia.fwk.core.Error)it.next();
                ActionMessage actionMessage = new ActionMessage("exception.generique",erreur.getDescription());
                actionMessages.add("Erreur ", actionMessage);
             }    
             this.saveMessages(request, actionMessages);
             return mapping.findForward("error");                       
        }
     
        String numeroSeq = request.getParameter("numeroSequenceCreationPersonne");
        creationPersonneForm.setNumero(numeroSeq);
        creationPersonneForm.setNumRetour(personne.getNumSeqPers().toString());
        creationPersonneForm.setEtatInsertion("I");
        return mapping.findForward("initierCreationPersonne");
        
        }catch(Exception e ){
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la transaction est Interrompu, une erreur dans CreationPersonneAction / disp :validerCreationPersonne / niveau :"+niveauAvancement.toString());
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


    public ActionForward saisirInfComplementaires(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {

            return mapping.findForward("popupInfComplementairesCreationPers");

        } catch (Exception e) {

            return mapping.findForward("error");
        }
    }

    public ActionForward sauvgarderInfComplementaires(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {

        try {

            return mapping.findForward("initierCreationPersonne");

        } catch (Exception e) {

            return mapping.findForward("error");
        }
    }

    

}

